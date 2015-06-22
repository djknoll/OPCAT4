package gui.opdGraphics.opdBaseComponents;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXNode;
import extensionTools.hio.CursorFactory;
import extensionTools.opltoopd.MyException;
import gui.opdGraphics.GraphicsUtils;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

/**
 *  <p><code>OpdBaseComponent</code> is a superclass of all OPD
 * components except links and non funadamental relations.
 * It implements all common graphic activities such as resizing, dragging,
 * mouse event handlers and defines interfaces for common properties.
 *  <p>For writing your own custom OPD graphic element you have only to inherit this
 * OpdBaseComponent and implement following methods that describe your component
 * properties<br>
 * public abstract void  paint(Graphics g); - paints the component<br>
 * public abstract void callPropertiesDialog(); - calls the Dialog Window which allows
 * to edit component's properties. This method is invoked when user performs double click on
 * the component.<br>
 * public abstract boolean inResize(int pX, int pY); - Test if the point in resizing area i.e.
 * if user drags mouse in this area it resizes the component.<br>
 * public abstract boolean inMove(int pX, int pY); - Test if the point in moviing area i.e.
 * if user drags mouse in this area it moves the component.<br>
 * public abstract int whichBorder(int pX, int pY); - Returns one of constants which determines
 * on which border point (pX, pY) lays.<br>
 */
public abstract class OpdBaseComponent extends BaseGraphicComponent implements
		IXNode {

	private long opdId;

	private long entityInOpdId;

	/**
	 *  <strong>No default constructor for this class.</strong><br>
	 *  <p>This is an abstract class and cannot be instantinated directely. It initializes default values, all subclasses have to call <code>super()</code>
	 *  in their constructors to initialize the defaults and set parameters given as arguments</p>
	 *  @param <code>pOpdId long</code>, uniqe identifier of the OPD this component is added to.
	 *  @param <code>pOpdId long</code>, uniqe identifier of the component within OPD it is added to.
	 *  @param <code>pProject OpdProject</code>, project this component is added to.
	 */
	public OpdBaseComponent(long pOpdId, long pEntityInOpdId,
			OpdProject pProject) {
		super(pProject);
		opdId = pOpdId;
		entityInOpdId = pEntityInOpdId;
		GenericTable config = myProject.getConfiguration();
		//		currentFont = (Font)config.getProperty("ThingFont");

		backgroundColor = (Color) config.getProperty("BackgroundColor");
		textColor = (Color) config.getProperty("TextColor");
		borderColor = Color.black;
		setMinimumSize(new Dimension(((Integer) config
				.getProperty("MinimalThingWidth")).intValue(),
				((Integer) config.getProperty("MinimalThingHeight")).intValue()));
		selected = false;

		double normalSize = ((Integer) config.getProperty("NormalSize"))
				.doubleValue();
		double currentSize = ((Integer) config.getProperty("CurrentSize"))
				.doubleValue();
		double factor = currentSize / normalSize;
		double width = ((Integer) config.getProperty("ThingWidth")).intValue()
				* factor;
		double height = ((Integer) config.getProperty("ThingHeight"))
				.intValue()
				* factor;

		setSize((int) width, (int) height);
		setLocation(0, 0);
	}

	/**
	 *  @return ID of the OPD this component belongs to
	 */
	public long getOpdId() {
		return opdId;
	}

	/**
	 *  @return ID of the component within current OPD
	 */
	public long getEntityInOpdId() {
		return entityInOpdId;
	}

	public OpdKey getOpdKey() {
		return new OpdKey(opdId, entityInOpdId);
	}

	/**
	 *  @return project this component belongs to
	 */
	public OpdProject getProject() {
		return myProject;
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
		
		

		if (StateMachine.getCurrentState() == StateMachine.USUAL_SELECT
				|| StateMachine.getCurrentState() == StateMachine.USUAL_MOVE
				|| StateMachine.getCurrentState() == StateMachine.USUAL_DRAW || //*********HIOTeam
				StateMachine.getCurrentState() == StateMachine.IN_SELECTION 
				|| StateMachine.getCurrentState() == StateMachine.EDIT_CUT) {
			super.mouseReleased(e);
			return;
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (StateMachine.isWaiting() || StateMachine.isAnimated()) {
			return;
		}

		switch (StateMachine.getCurrentState()) {
		case StateMachine.USUAL_SELECT:
		case StateMachine.USUAL_MOVE: {
			super.mouseDragged(e);
			return;
		}
		}

		return;
	}

	/**
	 *  <code>MouseEvent.mouseEntered(MouseEvent e)</code> event handler
	 *  Behavior depends on state of <a href = "../opdProject.OpdStateMachine.html"><code>OpdStateMachine</code></a> state. Also sets the mouse cursor.
	 */
	public void mouseEntered(MouseEvent e) {
		if (StateMachine.isWaiting() || StateMachine.isAnimated()) {
			return;
		}
		setCursorForState(e);
	
	}

	/**
	 *  <code>MouseMotionEvent.mouseMoved(MouseMotionEvent e)</code> event handler
	 *  Behavior depends on state of <a href = "../opdProject.OpdStateMachine.html"><code>OpdStateMachine</code></a> state.
	 */
	public void mouseMoved(MouseEvent e) {
		if (StateMachine.isWaiting() || StateMachine.isAnimated()) {
			return;
		}

		setCursorForState(e);
	}

	protected void setCursorForState(MouseEvent e) {
		switch (StateMachine.getCurrentState()) {
		case StateMachine.USUAL_DRAW:
			/***********HIOTeam*************** */
			Cursor drawCursor = CursorFactory.getDrawCursor();
			setCursor(drawCursor);
			break;
			/** *********HIOTeam*************** */
		case StateMachine.ADD_PROCESS:
		case StateMachine.ADD_OBJECT:
			if (isZoomedIn()) {
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			} else {
				setCursorForLocation(e);
			}
			break;

		case StateMachine.ADD_RELATION:
		case StateMachine.ADD_GENERAL_RELATION:
		case StateMachine.ADD_LINK:
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			break;
		case StateMachine.USUAL_SELECT:
		case StateMachine.USUAL_MOVE:
			setCursorForLocation(e);
			break;
		}
	}

	public void showPopupMenu(int pX, int pY) {
	}

	public IXNode getParentIXNode() {
		return null;
	}

	public Enumeration getRelatedIXLines() {
		return null;
	}
	
}