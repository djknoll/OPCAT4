package gui.opdGraphics.opdBaseComponents;

import exportedAPI.OpcatConstants;
import gui.controls.*;
import java.util.Enumeration;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPIx.IXCheckResult;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import extensionTools.hio.DrawAppNew;
import gui.Opcat2;
import gui.checkModule.CheckModule;
import gui.checkModule.CheckResult;
import gui.checkModule.CommandWrapper;
import gui.opdGraphics.DrawingArea;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.dialogs.ObjectPropertiesDialog;
import gui.opdGraphics.dialogs.ProcessPropertiesDialog;
import gui.opdGraphics.lines.LinkingLine;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.opmEntities.OpmConnectionEdge;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.Entry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.undo.UndoableAddFundamentalRelation;
import gui.undo.UndoableAddGeneralRelation;
import gui.undo.UndoableAddLink;
import gui.undo.UndoableAddObject;
import gui.undo.UndoableAddProcess;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;


/**
 *  This is an absract class that represents all components that links or relations can be connected to.
 */
public abstract class OpdConnectionEdge extends OpdBaseComponent {
	protected static final double SELECTION_OFFSET = 3.2;

	public static final double FRAMES_PER_SECOND = 8.0;

	/**
	 *  The reference to entry of given component in <a href = "../projectStructure/MainStructure.html" ><code>projectStructure.MainStructure</code></a>
	 */
	protected Entry myEntry;

	/**
	 *  The reference to entity of given component in <a href = "../projectStructure/MainStructure.html" ><code>projectStructure.MainStructure</code></a>
	 */
	protected OpmConnectionEdge myEntity; 

	/**
	 *  <strong>No default constructor for this class.</strong><br>
	 *  <p>This is an abstruct class and cannot be instantinated directely. It calls a superclass <a href = "OpdBaseComponent.html"><code>OpdBaseComponent</code></a>, all subclasses have to call <code>super()</code>
	 *  in their constructors to initialize the defaults and set parameters given as arguments</p>
	 *  @param <code>long pOpdId</code> uniqe identifier of the OPD this component is added to.
	 *  @param <code>long pOpdId</code> uniqe identifier of the component within OPD it is added to.
	 *  @param <code>OpdProject pProject</code> project this component is added to.
	 */
	private Color beforeEventBorderColor ; 
	private String beforeEventToolTip ;
	private boolean colorChanged = false ; 

	private boolean dragged;

	private DrawingArea dArea;

	private LinkingLine lLine;

	private boolean animated;

	private boolean animatedUp;

	private boolean animatedDown;

	private boolean paused;

	private javax.swing.Timer animationTimer;

	private long startTime;

	private long totalTime;

	private Color originalColor;

	//		private final static Color ANIMATION_COLOR = Color.yellow;
	private Color animationColor;

	private long performedTime;

	protected DrawAppNew drawing = gui.Opcat2.drawing;

	/***************hioTeam***************/

	public OpdConnectionEdge(long pOpdId, long pEntityInOpdId,
			OpdProject pProject) {
		super(pOpdId, pEntityInOpdId, pProject);
		animated = false;
		animatedUp = false;
		animatedDown = false;
	}

	/**
	 *  @return The reference to entry of given component in <a href = "../projectStructure/MainStructure.html" ><code>projectStructure.MainStructure</code></a>
	 */

	public OpmConnectionEdge getEntity() {
		return myEntity;
	}

	/**
	 *  @return The reference to entity of given component in <a href = "../projectStructure/MainStructure.html" ><code>projectStructure.MainStructure</code></a>
	 */
	public Entry getEntry() {
		return myEntry;
	}

	public Instance getInstance() {
		return myEntry.getInstance(getOpdKey());
	}

	public void mousePressed(MouseEvent e) {
		if (StateMachine.isWaiting()) {
			return;
		}

		dragged = false;

		dArea = (DrawingArea) myProject.getCurrentOpd().getDrawingArea();
		lLine = dArea.getLinkingLine();

		switch (StateMachine.getCurrentState()) {
		//Selecting the thing
		case StateMachine.USUAL_SELECT:
		case StateMachine.EDIT_CUT:
		case StateMachine.USUAL_MOVE: {
			super.mousePressed(e);
			Instance myInstance = myEntry.getInstance(new OpdKey(getOpdId(),
					getEntityInOpdId()));
			if (e.isShiftDown()) {
				if (myInstance.isSelected()) {
					myProject.removeSelection(myInstance);
				} else {
					myProject.addSelection(myInstance, !e.isShiftDown());
				}
			} else {
				if (myInstance.isSelected()) {
					myProject.addSelection(myInstance, false);
				} else {
					myProject.addSelection(myInstance, true);
				}
			}
			repaint();
			return;
		}

		/*****************hioTeam*****start*******/
		case StateMachine.USUAL_DRAW: {
			if (SwingUtilities.isMiddleMouseButton(e)
					|| SwingUtilities.isRightMouseButton(e)) {
				super.mousePressed(e);

				Instance myInstance = myEntry.getInstance(new OpdKey(
						getOpdId(), getEntityInOpdId()));
				if (e.isShiftDown()) {
					if (myInstance.isSelected()) {
						myProject.removeSelection(myInstance);
					} else {
						myProject.addSelection(myInstance, !e.isShiftDown());
					}
				} else {
					if (myInstance.isSelected()) {
						myProject.addSelection(myInstance, false);
					} else {
						myProject.addSelection(myInstance, true);
					}
				}
				repaint();
				return;
			}

			if (drawing == null)
				drawing = gui.Opcat2.drawing;
			Point eventPoint = SwingUtilities.convertPoint(this, e.getX(), e
					.getY(), myProject.getCurrentOpd().getDrawingArea());
			drawing.ourMousePressed(eventPoint);
			return;
		}
		/*****************hioTeam**end************/

		case StateMachine.ADD_RELATION: {
			lLine.setVisible(true);
			drawLine(e);
			break;
		}

		case StateMachine.ADD_GENERAL_RELATION: {
			lLine.setVisible(true);
			drawLine(e);
			break;
		}

		case StateMachine.ADD_LINK: {
			lLine.setVisible(true);
			drawLine(e);
			if (StateMachine.getDesiredComponent() == OpcatConstants.CONSUMPTION_LINK
					&& this instanceof OpdProcess) {
				StateMachine.setDesiredComponent(OpcatConstants.RESULT_LINK);
			}
			break;
		}
		}

	}

	private void drawLine(MouseEvent e) {

		Point ePoint = SwingUtilities.convertPoint(this, e.getX(), e.getY(),
				dArea);

		int eX = (int) ePoint.getX() - 1;
		int eY = (int) ePoint.getY() - 1;

		Point sPoint = SwingUtilities.convertPoint(getParent(), getX(), getY(),
				dArea);

		int sX = (int) sPoint.getX() + getWidth() / 2;
		int sY = (int) sPoint.getY() + getHeight() / 2;

		int lWidth = Math.abs(eX - sX) + 2;
		int lHeight = Math.abs(eY - sY) + 2;

		lLine.setSize(lWidth, lHeight);

		if (eX <= sX && eY <= sY) {
			lLine.setLeftDirection(true);
			lLine.setLocation(eX, eY);
			return;
		}

		if (eX <= sX && eY >= sY) {
			lLine.setLeftDirection(false);
			lLine.setLocation(eX, sY);
			return;
		}

		if (eX >= sX && eY >= sY) {
			lLine.setLeftDirection(true);
			lLine.setLocation(sX, sY);
			return;
		}

		if (eX >= sX && eY <= sY) {
			lLine.setLeftDirection(false);
			lLine.setLocation(sX, eY);
			return;
		}

		return;
	}

	/**
	 *  <code>MouseEvent.mouseReleased(MouseEvent e)</code> event handler
	 *  Behavior depends on state of <a href = "../opdProject.OpdStateMachine.html"><code>OpdStateMachine</code></a> state. Also sets the mouse cursor.
	 */
	public void mouseReleased(MouseEvent e) {
		GraphicsUtils.setLastMouseEvent(e) ; 
		if (StateMachine.isWaiting()) {
			return;
		}

		lLine.setVisible(false);

		if (StateMachine.getCurrentState() == StateMachine.USUAL_SELECT
				|| StateMachine.getCurrentState() == StateMachine.USUAL_MOVE 
				|| StateMachine.getCurrentState() == StateMachine.EDIT_CUT) {
			super.mouseReleased(e);
			return;
		}

		switch (StateMachine.getCurrentState()) {
		case StateMachine.ADD_STATE: {
			return;
		}
		case StateMachine.ADD_PROCESS: {
			if (isZoomedIn()) {
				addProcess(e.getX(), e.getY());
			}
			return;
		}

		case StateMachine.ADD_OBJECT: {
			if (isZoomedIn()) {
				addObject(e.getX(), e.getY());
			}
			return;
		}
		/****************************HIOTeam***************/
		case StateMachine.USUAL_DRAW: {

			if (SwingUtilities.isMiddleMouseButton(e))
				return;
			if (SwingUtilities.isRightMouseButton(e)) {
				super.mouseReleased(e); //showPopupMenu(e.getX(), e.getY());
			} else {
				drawing.ourMouseReleased(e);
			}
			return;
		}
		/****************************HIOTeam end***************/

		}

		int currState = StateMachine.getCurrentState();

		if (currState == StateMachine.ADD_RELATION
				|| currState == StateMachine.ADD_GENERAL_RELATION
				|| currState == StateMachine.ADD_LINK)

		{
			Component tempComp = dArea.findComponentAt(SwingUtilities
					.convertPoint(this, e.getX(), e.getY(), dArea));

			if (!dragged || tempComp instanceof DrawingArea) {
				StateMachine.reset();
				setCursorForState(e);
				return;
			}

			if (!(tempComp instanceof OpdConnectionEdge)) {
				JOptionPane.showMessageDialog(null,
						"This is not allowed in OPM", "Opcat 2 - Error",
						JOptionPane.ERROR_MESSAGE);
				StateMachine.reset();
				setCursorForState(e);
				dArea.repaint();
				return;
			}

			OpdConnectionEdge target = (OpdConnectionEdge) tempComp;
			RelativeConnectionPoint sourcePoint = GraphicsUtils
					.calculateConnectionPoint(this, target);
			RelativeConnectionPoint targetPoint = GraphicsUtils
					.calculateConnectionPoint(target, this);

			OpdKey sourceKey = new OpdKey(getOpdId(), getEntityInOpdId());
			OpdKey targetKey = new OpdKey(target.getOpdId(), target
					.getEntityInOpdId());
			CommandWrapper cw = new CommandWrapper(this.getEntity().getId(),
					sourceKey, target.getEntity().getId(), targetKey,
					StateMachine.getDesiredComponent(), myProject);

			CheckResult cr = CheckModule.checkCommand(cw);

			if (cr.getResult() == IXCheckResult.WRONG) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(), cr
						.getMessage(), "Opcat2 - Error",
						JOptionPane.ERROR_MESSAGE);
				StateMachine.reset();
				setCursorForState(e);
				dArea.repaint();
				return;
			}

			if (cr.getResult() == IXCheckResult.WARNING) {
				int retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
						cr.getMessage() + "\n Do you want to continue?",
						"Opcat2 - Warning", JOptionPane.YES_NO_OPTION);

				if (retValue == JOptionPane.NO_OPTION) {
					StateMachine.reset();
					setCursorForState(e);
					dArea.repaint();
					return;
				}
			}

			switch (StateMachine.getCurrentState()) {

			case StateMachine.ADD_RELATION: {
				try {

					if (getParent() == target.getParent()) {
						tempContainer = (JLayeredPane) target.getParent();
					} else {
						tempContainer = (JLayeredPane) myProject
								.getCurrentOpd().getDrawingArea();
					}

					sourcePoint = new RelativeConnectionPoint(
							OpcatConstants.S_BORDER, 0.5);
					targetPoint = new RelativeConnectionPoint(
							OpcatConstants.N_BORDER, 0.5);

					FundamentalRelationInstance fr = myProject.addRelation(
							this, sourcePoint, target, targetPoint,
							StateMachine.getDesiredComponent(), tempContainer,
							-1, -1);

					Opcat2.getUndoManager().undoableEditHappened(
							new UndoableEditEvent(myProject,
									new UndoableAddFundamentalRelation(
											myProject, fr)));
					Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
					Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
					myProject.removeSelection();
					myProject.addSelection(fr, true);
					Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);

					StateMachine.reset();
				} catch (Exception e1) {
					System.out
							.println("OpdConnectionEdge.java : Exception ADD_RELATION "
									+ e1);
					e1.printStackTrace();
				}
				break;
			}

			case StateMachine.ADD_GENERAL_RELATION: {
				try {
					if (getParent() == target.getParent()) {
						tempContainer = (JLayeredPane) target.getParent();
					} else {
						tempContainer = (JLayeredPane) myProject
								.getCurrentOpd().getDrawingArea();
					}

					GeneralRelationInstance gr = myProject.addGeneralRelation(
							this, sourcePoint, target, targetPoint,
							tempContainer, StateMachine.getDesiredComponent(),
							-1, -1);

					Opcat2.getUndoManager().undoableEditHappened(
							new UndoableEditEvent(myProject,
									new UndoableAddGeneralRelation(myProject,
											gr)));
					Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
					Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
					myProject.removeSelection();
					myProject.addSelection(gr, true);
					Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
					StateMachine.reset();
				} catch (Exception e1) {
					System.out.println("Exception ADD_GENERAL_RELATION " + e1);
				}
				break;
			}

			case StateMachine.ADD_LINK: {
				try {

					if (getParent() == target.getParent()) {
						tempContainer = (JLayeredPane) target.getParent();
					} else {
						tempContainer =  myProject.getCurrentOpd().getDrawingArea();
					}

					myProject.addLinks/*addLinkConsistent*/(
							this, sourcePoint, target, targetPoint,
							tempContainer, StateMachine.getDesiredComponent(),
							-1, -1);	
					
					StateMachine.reset();
				} catch (Exception e1) {
					System.out
							.println("Exception ADD_LINK at OpdConnection edge "
									+ e1);
					e1.printStackTrace();
				}
				break;
			}
			}
			setCursorForState(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (StateMachine.isWaiting() || StateMachine.isAnimated()) {
			return;
		}

		dragged = true;
		switch (StateMachine.getCurrentState()) {
		case StateMachine.USUAL_SELECT:
		case StateMachine.USUAL_MOVE: {
			super.mouseDragged(e);
			return;
		}
		case StateMachine.ADD_RELATION:
		case StateMachine.ADD_GENERAL_RELATION:
		case StateMachine.ADD_LINK:
			drawLine(e);
			break;

		/********************hioTeam*****start***********/
		case StateMachine.USUAL_DRAW: {

			if (SwingUtilities.isRightMouseButton(e)
					|| SwingUtilities.isMiddleMouseButton(e))
				return;
			Point eventPoint = SwingUtilities.convertPoint(this, e.getX(), e
					.getY(), myProject.getCurrentOpd().getDrawingArea());
			//convert the point to drawingArea coordinates
			drawing.ourMouseDragged(eventPoint);
			return;
		}
		/********************hioTeam end****************/

		}

		return;
	}

	/**
	 * Draws selection, just draws small squares in corners and at the middle of
	 * the edges.
	 */
	public void drawSelection(Graphics2D g) {
		Stroke oldStroke = g.getStroke();

		g.setStroke(new BasicStroke());

		GenericTable config = myProject.getConfiguration();
		double currentSize = ((Integer) config.getProperty("CurrentSize"))
				.doubleValue();
		double normalSize = ((Integer) config.getProperty("NormalSize"))
				.doubleValue();
		double factor = currentSize / normalSize;
		int selOffset = Math.round((float) (SELECTION_OFFSET * factor));
		int dSelOffset = 2 * selOffset;

		g.setColor(Color.white);
		g.fillRect(0, 0, dSelOffset, dSelOffset);
		g.fillRect(getActualWidth() / 2 - selOffset, 0, dSelOffset, dSelOffset);
		g.fillRect(getActualWidth() - 1 - selOffset * 2, 0, dSelOffset,
				dSelOffset);
		g
				.fillRect(0, getActualHeight() / 2 - selOffset, dSelOffset,
						dSelOffset);
		g.fillRect(0, getActualHeight() - 1 - dSelOffset, dSelOffset,
				dSelOffset);
		g.fillRect(getActualWidth() / 2 - selOffset, getActualHeight() - 1
				- dSelOffset, dSelOffset, dSelOffset);
		g.fillRect(getActualWidth() - 1 - dSelOffset, getActualHeight() - 1
				- dSelOffset, dSelOffset, dSelOffset);
		g.fillRect(getActualWidth() - 1 - dSelOffset, getActualHeight() / 2
				- selOffset, dSelOffset, dSelOffset);

		g.setColor(Color.black);
		g.drawRect(0, 0, dSelOffset, dSelOffset);
		g.drawRect(getActualWidth() / 2 - selOffset, 0, dSelOffset, dSelOffset);
		g
				.drawRect(getActualWidth() - 1 - dSelOffset, 0, dSelOffset,
						dSelOffset);
		g
				.drawRect(0, getActualHeight() / 2 - selOffset, dSelOffset,
						dSelOffset);
		g.drawRect(0, getActualHeight() - 1 - dSelOffset, dSelOffset,
				dSelOffset);
		g.drawRect(getActualWidth() / 2 - selOffset, getActualHeight() - 1
				- dSelOffset, dSelOffset, dSelOffset);
		g.drawRect(getActualWidth() - 1 - dSelOffset, getActualHeight() - 1
				- dSelOffset, dSelOffset, dSelOffset);
		g.drawRect(getActualWidth() - 1 - dSelOffset, getActualHeight() / 2
				- selOffset, dSelOffset, dSelOffset);

		g.setStroke(oldStroke);
	}

	public abstract void fitToContent();
	
	private void addObject(int x, int y) {
		OpmObject sampleObjOpm = new OpmObject(0, "");
		ObjectEntry sampleObjEntry = new ObjectEntry(sampleObjOpm, myProject);
		ObjectInstance sampleObjInstance = new ObjectInstance(sampleObjEntry,
				new OpdKey(0, 0), null, myProject, false);
		OpdObject sampleObjOpd = (OpdObject) sampleObjInstance
				.getConnectionEdge();
		ObjectPropertiesDialog od = new ObjectPropertiesDialog(sampleObjOpd,
				sampleObjEntry, myProject, true);

		if (od.showDialog()) {
			ObjectInstance oi = myProject.addObject(x, y, this, -1, -1, false);

			OpmObject newObject = (OpmObject) oi.getEntry().getLogicalEntity();
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
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}

		StateMachine.reset();
		return;
	}

	private void addProcess(int x, int y) {
		OpmProcess sampleProcOpm = new OpmProcess(0, "");
		ProcessEntry sampleProcEntry = new ProcessEntry(sampleProcOpm,
				myProject);
		ProcessInstance sampleProcInstance = new ProcessInstance(
				sampleProcEntry, new OpdKey(0, 0), null, myProject);
		OpdProcess sampleProcOpd = (OpdProcess) sampleProcInstance
				.getConnectionEdge();

		ProcessPropertiesDialog pd = new ProcessPropertiesDialog(sampleProcOpd,
				sampleProcEntry, myProject, true);

		if (pd.showDialog()) {
			ProcessInstance pi = myProject.addProcess(x, y, this, -1, -1);

			OpmProcess newProcess = (OpmProcess) pi.getEntry()
					.getLogicalEntity();
			newProcess.copyPropsFrom(sampleProcOpm);
			pi.copyPropsFrom(sampleProcInstance);
			pi.update();
			pi.getConnectionEdge().fitToContent();

			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			Opcat2.getUndoManager().undoableEditHappened(
					new UndoableEditEvent(myProject, new UndoableAddProcess(
							myProject, pi)));
			Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
			Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
			myProject.removeSelection();
			myProject.addSelection(pi, true);
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}

		StateMachine.reset();
		return;
	}

	public synchronized boolean isAnimated() {
		return animated;
	}

	public synchronized boolean isAnimatedUp() {
		return animatedUp;
	}

	public synchronized boolean isAnimatedDown() {
		return animatedDown;
	}

	public synchronized void pauseAnimaition() {
		if (!(animatedDown || animatedUp) || paused) {
			return;
		}

		paused = true;
		animationTimer.stop();
		performedTime = (new GregorianCalendar()).getTime().getTime()
				- startTime;
	}

	public synchronized void continueAnimaition() {
		if (!(animatedDown || animatedUp) || !paused) {
			return;
		}

		startTime = (new GregorianCalendar()).getTime().getTime()
				- performedTime;
		animationTimer.start();
		paused = false;
	}

	public synchronized void animate(long time) {
		if (animated) {
			if (time == 0) {
				animatedDown = false;
				animatedUp = false;
				stopColorChangeImmediately(animationColor);
			}
			return;
		}

		animated = true;

		animationColor = muchBrighter(getBorderColor());
		originalColor = getBackgroundColor();

		if (this.isZoomedIn()) {
			animationColor = muchBrighter(animationColor);
		}

		animationTimer = new javax.swing.Timer(
				(int) (1000 / FRAMES_PER_SECOND), change2animationColor);

		if (time == 0) {
			setBackgroundColor(animationColor);
			repaint();
			return;
		}

		animatedUp = true;
		startTime = (new GregorianCalendar()).getTime().getTime();
		totalTime = time;
		animationTimer.setInitialDelay(0);
		animationTimer.start();
	}

	public synchronized void stopAnimation(long time) {
		if (!animated) {
			return;
		}

		if (paused) {
			paused = false;
		}

		animationTimer.stop();

		if (time == 0) {
			setBackgroundColor(originalColor);
			repaint();
			animated = false;
			animatedDown = false;
			animatedUp = false;
			return;
		}

		animatedDown = true;
		animatedUp = false;
		animationTimer = new javax.swing.Timer(
				(int) (1000 / FRAMES_PER_SECOND), change2originalColor);
		startTime = (new GregorianCalendar()).getTime().getTime();
		totalTime = time;
		animationColor = getBackgroundColor();
		animationTimer.setInitialDelay(0);
		animationTimer.start();
	}

	public synchronized long getRemainedAnimationTime() {
		if (!animatedDown && !animatedUp) {
			return 0;
		}

		if (paused) {
			return totalTime - performedTime;
		}

		return totalTime + startTime
				- (new GregorianCalendar()).getTime().getTime();
	}

	public synchronized long getTotalAnimationTime() {
		if (!animatedDown && !animatedUp) {
			return 0;
		}

		return totalTime;
	}

	private void stopColorChangeImmediately(Color targetColor) {
		animationTimer.stop();
		setBackgroundColor(targetColor);
		repaint();
	}

	Action change2animationColor = new AbstractAction() {
		public synchronized void actionPerformed(java.awt.event.ActionEvent e) {

			long timeLeft = totalTime + startTime
					- (new GregorianCalendar()).getTime().getTime();
			;

			if (timeLeft <= 0) {
				stopColorChangeImmediately(animationColor);
				animatedUp = false;
				return;
			}

			double param = 1.0 - (double) (timeLeft) / (double) (totalTime);
			setBackgroundColor(new Color(
					originalColor.getRed()
							+ (int) ((double) (animationColor.getRed() - originalColor
									.getRed()) * param),
					originalColor.getGreen()
							+ (int) ((double) (animationColor.getGreen() - originalColor
									.getGreen()) * param),
					originalColor.getBlue()
							+ (int) ((double) (animationColor.getBlue() - originalColor
									.getBlue()) * param)));
			repaint();
		}
	};

	Action change2originalColor = new AbstractAction() {
		public synchronized void actionPerformed(java.awt.event.ActionEvent e) {

			long timeLeft = totalTime + startTime
					- (new GregorianCalendar()).getTime().getTime();

			if (timeLeft <= 0) {
				stopColorChangeImmediately(originalColor);
				animated = false;
				animatedDown = false;
				return;
			}

			double param = 1.0 - (double) (timeLeft) / (double) (totalTime);
			setBackgroundColor(new Color(
					animationColor.getRed()
							+ (int) ((double) (originalColor.getRed() - animationColor
									.getRed()) * param),
					animationColor.getGreen()
							+ (int) ((double) (originalColor.getGreen() - animationColor
									.getGreen()) * param),
					animationColor.getBlue()
							+ (int) ((double) (originalColor.getBlue() - animationColor
									.getBlue()) * param)));
			repaint();

		}
	};

	private Color muchBrighter(Color origColor) {
		int r = origColor.getRed();
		int g = origColor.getGreen();
		int b = origColor.getBlue();

		return new Color(r + (255 - r) / 2, g + (255 - g) / 2, b + (255 - b)
				/ 2);
	}

	public void mouseClicked(MouseEvent e)
	{
		if (colorChanged) {
			setToolTipText(beforeEventToolTip) ; 
			setBorderColor(beforeEventBorderColor);
			colorChanged = false ; 
		}
		repaint(); 
		super.mouseClicked(e) ; 
		if (e.isControlDown()) 
		{
			myEntry.ShowUrl() ;
			return ; 
		}
	}
	
	public void mouseExited(MouseEvent e){
		if (colorChanged) {
			setToolTipText(beforeEventToolTip) ; 
			setBorderColor(beforeEventBorderColor);
			colorChanged = false ; 
		}			
		repaint(); 
	}	
	
	
	
	public void mouseEntered(MouseEvent e) {
		
		super.mouseEntered(e); 		
		if (myEntry.hasURL()) {		
			beforeEventBorderColor = getBorderColor() ; 
			beforeEventToolTip = getToolTipText() ; 
			setToolTipText("Ctrl-LeftMouse to Activate URL");
			setBorderColor((Color) myProject.getConfiguration().getProperty("UrlColor"));
			colorChanged = true ; 
			repaint(); 
		}
	}		

}