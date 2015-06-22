package gui.opdGraphics;

import exportedAPI.OpdKey;
import extensionTools.hio.CursorFactory;
import extensionTools.hio.DrawAppNew;
import gui.Opcat2;
import gui.license.ILicense;
import gui.opdGraphics.dialogs.ObjectPropertiesDialog;
import gui.opdGraphics.dialogs.ProcessPropertiesDialog;
import gui.opdGraphics.lines.LinkingLine;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdGraphics.popups.OpdPopup;
import gui.opdProject.GenericTable;
import gui.opdProject.IllegalPassException;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.Selection;
import gui.opdProject.StateMachine;
import gui.opdProject.consistency.ConsistencyAction;
import gui.opdProject.consistency.ConsistencyAbstractChecker;
import gui.opdProject.consistency.ConsistencyFactory;
import gui.opdProject.consistency.ConsistencyResult;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.undo.UndoableAddObject;
import gui.undo.UndoableAddProcess;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;

import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;


/******************HIOTeam*********************/
/**
 *  <p>
 */

public class DrawingArea extends JDesktopPane implements MouseListener,
		MouseMotionListener, Scrollable, OpcatContainer {
	protected OpdProject myProject;

	private int m_XDifference, m_YDifference;

	private LinkingLine lLine;

	private int startSelX, startSelY;

	Container c;

	private SelectionRectangle selRect;

	private Opd myOpd;

	private DrawAppNew drawing = gui.Opcat2.drawing;

	/******************HIOTeam*********************/

	public DrawingArea(OpdProject pProject, Opd opd) {
		super();
		this.setDoubleBuffered(true);
		myProject = pProject;
		myOpd = opd;
		setLayout(null);
		GenericTable config = myProject.getConfiguration();
		setBackground((Color) config.getProperty("BackgroundColor"));

		lLine = new LinkingLine();
		lLine.setVisible(false);

		this.add(lLine, JLayeredPane.DRAG_LAYER);

		selRect = new SelectionRectangle(myProject);
		selRect.setVisible(true);
		this.add(selRect, JLayeredPane.DRAG_LAYER);

		//		selected  = new Hashtable();
		//		nonselected  = new Hashtable();
		//setBorder(BorderFactory.createEtchedBorder());
	}

	// we overwrite this function to avoid sun internal exception
	public void printComponent(Graphics g) {
		if (this.getComponentCount() == 1) {
			//			g.setColor(new Color(230, 230, 230));
			//			g.fillRect(0, 0, getWidth(), getHeight());
			return;
		} else {
			super.printComponent(g);
		}
	}

	public void mouseClicked(MouseEvent e) {

		if (StateMachine.isWaiting()) {
			return;
		}
		//Event evt ; 
		//keyDown(evt , )
	}

	public void mouseDragged(MouseEvent e) {

		if (StateMachine.isWaiting()) {
			return;
		}

		if (StateMachine.getCurrentState() == StateMachine.USUAL_SELECT) {
			try {
				StateMachine.go(StateMachine.IN_SELECTION, 0);
			} catch (IllegalPassException ipe) {
				ipe.printStackTrace();
			}

			int minX = Math.min(startSelX, e.getX());
			int minY = Math.min(startSelY, e.getY());
			int maxX = Math.max(startSelX, e.getX());
			int maxY = Math.max(startSelY, e.getY());
			getSelection().initSelection(this, null);
			selRect.setLocation(minX, minY);
			selRect.setSize(maxX - minX, maxY - minY);
			getSelection().inSelection();
			return;
		}

		/** **************HIOTeam Start *********** */
		if (StateMachine.getCurrentState() == StateMachine.USUAL_DRAW) {
			if (SwingUtilities.isRightMouseButton(e)
					|| SwingUtilities.isMiddleMouseButton(e))
				return;
			Point eventPoint = new Point(e.getPoint());
			drawing.ourMouseDragged(eventPoint);
			return;
		}
		/** **************HIOTeam End*********** */

		if (StateMachine.getCurrentState() == StateMachine.IN_SELECTION) {
			int minX = Math.min(startSelX, e.getX());
			int minY = Math.min(startSelY, e.getY());
			int maxX = Math.max(startSelX, e.getX());
			int maxY = Math.max(startSelY, e.getY());
			selRect.setLocation(minX, minY);
			selRect.setSize(maxX - minX, maxY - minY);
			myOpd.getSelection().inSelection();
			return;
		}

		//			}
		//			else if(Opcat2.getDrawingAreaOnMouseDragAction().equals("move"))
		//			{
		if (StateMachine.getCurrentState() == StateMachine.USUAL_MOVE) {
			c = getParent();
			if (c instanceof JViewport) {
				JViewport jv = (JViewport) c;
				Point p = jv.getViewPosition();
				int newX = p.x - (e.getX() - m_XDifference);
				int newY = p.y - (e.getY() - m_YDifference);
				int maxX = getWidth() - jv.getWidth();
				int maxY = getHeight() - jv.getHeight();
				if (newX < 0)
					newX = 0;
				if (newX > maxX)
					newX = maxX;
				if (newY < 0)
					newY = 0;
				if (newY > maxY)
					newY = maxY;
				jv.setViewPosition(new Point(newX, newY));
			}
			return;
		}
	}

	public void mousePressed(MouseEvent e) {
		if (StateMachine.isWaiting()) {
			return;
		}

		if (StateMachine.getCurrentState() == StateMachine.USUAL_MOVE) {
			myProject.removeSelection();
			setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			m_XDifference = e.getX();
			m_YDifference = e.getY();
			return;
		}

		if ((StateMachine.getCurrentState() == StateMachine.USUAL_SELECT) || (StateMachine.getCurrentState() == StateMachine.EDIT_CUT)) {
			myProject.removeSelection();
			startSelX = e.getX();
			startSelY = e.getY();
			selRect.setVisible(true);
			return;
		}

		/** ****start*****HIOTeam******************** */
		if (StateMachine.getCurrentState() == StateMachine.USUAL_DRAW) {
			if (SwingUtilities.isRightMouseButton(e)
					|| SwingUtilities.isMiddleMouseButton(e)) {
				myProject.removeSelection();
				startSelX = e.getX();
				startSelY = e.getY();
				selRect.setVisible(true);
				return;
			}
			if (drawing == null)
				drawing = gui.Opcat2.drawing;
			Point eventPoint = new Point(e.getPoint());
			drawing.ourMousePressed(eventPoint);
		}
		/** **end**********HIOTeam******************** */

	}

	public void mouseMoved(MouseEvent e) {
	}

	public LinkingLine getLinkingLine() {
		return lLine;
	}

	public void mouseReleased(MouseEvent e) {
		ILicense license = Opcat2.getLicense();
		GraphicsUtils.setLastMouseEvent(e) ; 
		
		if (StateMachine.isWaiting()) {
			return;
		}

		if (e.isPopupTrigger() && !StateMachine.isAnimated()) {
			showPopupMenu(e.getX(), e.getY());
			return;
		}

		switch (StateMachine.getCurrentState()) {
		/** *********HIOTeam******************** */
		case StateMachine.USUAL_DRAW:
			if (SwingUtilities.isMiddleMouseButton(e))
				return;
			if (SwingUtilities.isRightMouseButton(e)) {
				showPopupMenu(e.getX(), e.getY());
				return;
			}
			drawing.ourMouseReleased(e);
			return;
		/** *********HIOTeam end******************** */  
		case StateMachine.IN_SELECTION: {
			getSelection().finishSelection();
			selRect.setVisible(false);
			// add code for selection of entities
			selRect.setSize(0, 0);
			try {
				StateMachine.go(StateMachine.USUAL_SELECT, 0);
			} catch (IllegalPassException ipe) {
				ipe.printStackTrace();
			}
			break;
		}
		case StateMachine.ADD_PROCESS: {
			if (!license.getPolicyOfficer().canAddThing(myProject))	{
				actUponLicenseViolation();
				return;
			}
			OpmProcess sampleProcOpm = new OpmProcess(0, "");
			ProcessEntry sampleProcEntry = new ProcessEntry(sampleProcOpm,
					myProject);
			ProcessInstance sampleProcInstance = new ProcessInstance(
					sampleProcEntry, new OpdKey(0, 0), null, myProject);
			OpdProcess sampleProcOpd = (OpdProcess) sampleProcInstance
					.getConnectionEdge();

			ProcessPropertiesDialog pd = new ProcessPropertiesDialog(
					sampleProcOpd, sampleProcEntry, myProject, true);

			if (pd.showDialog()) {
				ProcessInstance pi = myProject.addProcess(e.getX(), e.getY(),
						this, -1, -1);

				OpmProcess newProcess = (OpmProcess) pi.getEntry()
						.getLogicalEntity();
				newProcess.copyPropsFrom(sampleProcOpm);
				pi.copyPropsFrom(sampleProcInstance);
				pi.update();
				pi.getConnectionEdge().fitToContent();

				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				Opcat2.getUndoManager().undoableEditHappened(
						new UndoableEditEvent(myProject,
								new UndoableAddProcess(myProject, pi)));
				Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
				Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
				myProject.removeSelection();
				myProject.addSelection(pi, true);

				if (pd.isAddHelpON()) {
					ConsistencyResult checkResult = new ConsistencyResult();
		   			ConsistencyAbstractChecker checker = (ConsistencyAbstractChecker) (new ConsistencyFactory(myProject, ConsistencyAction.ADDITION, checkResult)).create() ; 
					checker.check() ; 
					if (!checkResult.isConsistent()) { 
						checker.deploy(checkResult) ; 
					}					
				}
			}

			StateMachine.reset();
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			break;
		}
		case StateMachine.ADD_OBJECT: {
			if (!license.getPolicyOfficer().canAddThing(myProject))	{
				actUponLicenseViolation();
				return;
			}
			OpmObject sampleObjOpm = new OpmObject(0, "");
			ObjectEntry sampleObjEntry = new ObjectEntry(sampleObjOpm,
					myProject);
			ObjectInstance sampleObjInstance = new ObjectInstance(
					sampleObjEntry, new OpdKey(0, 0), null, myProject, false);
			OpdObject sampleObjOpd = (OpdObject) sampleObjInstance
					.getConnectionEdge();

			ObjectPropertiesDialog od = new ObjectPropertiesDialog(
					sampleObjOpd, sampleObjEntry, myProject, true);

			if (od.showDialog()) {
				ObjectInstance oi = myProject.addObject(e.getX(), e.getY(),
						this, -1, -1, false);
				//reuseComment
				if (oi == null)	{
					return;
				}
				//endReuseComment
				OpmObject newObject = (OpmObject) oi.getEntry()
						.getLogicalEntity();
				newObject.copyPropsFrom(sampleObjOpm);
				oi.copyPropsFrom(sampleObjInstance);
				oi.update();
				oi.getConnectionEdge().fitToContent();

				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				Opcat2.getUndoManager().undoableEditHappened(
						new UndoableEditEvent(myProject, new UndoableAddObject(
								myProject, oi)));
				Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
				Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
				myProject.removeSelection();
				myProject.addSelection(oi, true);
				if (od.isAddHelpON()) {
					ConsistencyResult checkResult = new ConsistencyResult();
					ConsistencyAbstractChecker checker = (ConsistencyAbstractChecker) (new ConsistencyFactory(myProject, ConsistencyAction.ADDITION, checkResult)).create() ; 
					checker.check() ; 
					if (!checkResult.isConsistent()) { 
						checker.deploy(checkResult) ; 
					}					
				}
				
			}

			StateMachine.reset();

			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			break;
		}

		default: {
			StateMachine.reset();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			break;
		}
		}
				

	}

	public void mouseEntered(MouseEvent e) {
		if (StateMachine.isWaiting()) {
			return;
		}

		switch (StateMachine.getCurrentState()) {
		case StateMachine.USUAL_SELECT:
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			break;
		case StateMachine.EDIT_CUT:
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)) ; 
			break ; 
		/*************************HIOTeam************** */
		case StateMachine.USUAL_DRAW:
			setCursor(CursorFactory.getDrawCursor());

			break;
		/*************************HIOTeam End************** */
		case StateMachine.USUAL_MOVE:
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			break;
		case StateMachine.ADD_PROCESS:
		case StateMachine.ADD_OBJECT:
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case StateMachine.ADD_LINK:
		case StateMachine.ADD_RELATION:
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			break;
		}
	}

	public void mouseExited(MouseEvent e) {
	}

	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	public int getScrollableBlockIncrement(Rectangle r, int orientation,
			int direction) {
		Dimension size;

		size = getPreferredSize();
		if (orientation == SwingConstants.VERTICAL) {
			return (int) (size.getHeight() / 10);
		} else {
			return (int) (size.getWidth() / 10);
		}

	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public int getScrollableUnitIncrement(Rectangle r, int orientation,
			int direction) {
		Dimension size;

		size = getPreferredSize();
		if (orientation == SwingConstants.VERTICAL) {
			return (int) (size.getHeight() / 50);
		} else {
			return (int) (size.getWidth() / 50);
		}

	}

	protected void showPopupMenu(int x, int y) {
		JPopupMenu jpm = new OpdPopup(myProject, x, y);
		Point showPoint = GraphicsUtils.findPlace4Menu(this, new Point(x, y),
				jpm.getPreferredSize());
		jpm.show(this, (int) showPoint.getX(), (int) showPoint.getY());
	}

	public Container getContainer() {
		return this;
	}

	public BaseGraphicComponent[] graphicalSelectionComponents() {
		return myOpd.getSelection().graphicalSelectionComponents();
	}

	public Enumeration getGraphicalSelection() {
		return null;
	}

	public Selection getSelection() {
		return myOpd.getSelection();
	}

	public SelectionRectangle getSelectionRectangle() {
		return selRect;
	}

	public void paint(Graphics g) {
		//reuseComment
		super.paint(g);
		if (myOpd.getReusedSystemPath() != null) {
			g.setColor(Color.black);
			int height = this.getHeight();
			int width = this.getWidth();
			if (myOpd.getOpdId() == 1) {
				g.drawRect(0, height / 2, width, 3);
				g.fillRect(0, height / 2, width, 3);
			} else {
				g.drawRect(0, height / 10, width, 3);
				g.fillRect(0, height / 10, width, 3);
			}
		}

		//endReuseComment
	}

	/**
	 * A set of actions which happens if the user violated the license terms.
	 * For instance, setting the cursor back to default and showing an error
	 * message to the user.
	 */
	protected void actUponLicenseViolation()	{
		StateMachine.reset();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		Opcat2.showMessage("License Error", Opcat2.getLicense().getLicenseError(), JOptionPane.ERROR_MESSAGE);
	}
}