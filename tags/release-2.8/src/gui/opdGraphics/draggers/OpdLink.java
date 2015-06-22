/*
 *  opcat2
 *  package: opdGraphics
 *  file:    AroundDragger.java
 */

package gui.opdGraphics.draggers;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.Opcat2;
import gui.checkModule.CheckResult;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.popups.LinkPopup;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.opmEntities.OpmProceduralLink;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.LinkInstance;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;


/**
 *  <p>Abstract class AroundDragger is a superclass for all classes which represents
 *  items that can be dragged around Objects, Processes or States in OPD.
 *  Actually this class provides dragging functionality for edges of all kinds
 *  of OPD links and relations.</p>
 */

public abstract class OpdLink extends AroundDragger
{

/**
 *  Logical entity represents link this dragger belongs to
 */
	protected OpmProceduralLink  myLink;


/**
 *  An ID of the OPD this dragger belongs to
 */
	private long opdId;
	private long entityInOpdId;


/**
 *  <strong>There is no default constructor</strong>
 *  <p>All extending classes have to call <code>super()</code> in their constructors.
 *  @param <code>pEdge1</code> -- OPD graphic component this dragger connected to.
 *  @param <code>pSide1</code> -- The side OPD graphic component this dragger connected to.
 *  @param <code>pParam1</code> -- The relation of coordinates of a <code>mouseClick</code> event to length of the side.
 */
	public OpdLink(Connectable pEdge1, RelativeConnectionPoint cPoint, OpmProceduralLink pLink,
								   long pOpdId, long pEntityInOpdId, OpdProject pProject)

	{
		super(pEdge1, cPoint, pProject);
		myLink = pLink;
		myProject = pProject;
		opdId = pOpdId;
		entityInOpdId = pEntityInOpdId;

		GenericTable config = myProject.getConfiguration();

		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double factor = currentSize/normalSize;
		double size = ((Integer)config.getProperty("DraggerSize")).intValue()*factor;
		setSize((int)size,(int)size);
		setCanLeave(true);
	}


/**
 *  @return Logical entity represents link or line this dragger belongs to.
 */
	public OpmProceduralLink getEntity()
	{
		  return myLink;
	}

	public long getOpdId()
	{
		  return opdId;
	}

	public long getEntityInOpdId()
	{
		  return entityInOpdId;
	}


	//NOTE: I think this code should move to AroundDragger
	public void mousePressed(MouseEvent e)
	{

        if (StateMachine.isWaiting())
        {
            return;
        }

		  Entry myEntry = myProject.getComponentsStructure().getEntry(myLink.getId());
		  Instance myInstance = myEntry.getInstance(new OpdKey(opdId, entityInOpdId));
		  if(e.isShiftDown())
		  {
			  if(myInstance.isSelected())
			  {
				  myProject.removeSelection(myInstance);
			  }
			  else
			  {
				  myProject.addSelection(myInstance, !e.isShiftDown());
			  }
		  }
		  else
		  {
			  if(myInstance.isSelected())
			  {
				  myProject.addSelection(myInstance, false);
			  }
			  else
			  {
				  myProject.addSelection(myInstance, true);
			  }
		  }
		  repaint();

          if (StateMachine.isAnimated())
          {
              return;
          }

		  super.mousePressed(e);
		  return;
	}

	public void mouseReleased(MouseEvent e)
	{
        if (StateMachine.isAnimated() || StateMachine.isWaiting())
        {
            return;
        }

		super.mouseReleased(e);

		if (!moveable)
		{
			return;
		}


		if (edge!=originalEdge)
		{
			LinkEntry le = (LinkEntry)myProject.getComponentsStructure().getEntry(myLink.getId());
			LinkInstance li = (LinkInstance)le.getInstance(new OpdKey(opdId, entityInOpdId));

			CheckResult cr = myProject.switchLinkEdge(li, (OpdConnectionEdge)originalEdge, (OpdConnectionEdge)edge, cPoint);
			if (cr.getResult()== CheckResult.WRONG)
			{
				edge = originalEdge;
				cPoint.setParam(originalPoint.getParam());
				cPoint.setSide(originalPoint.getSide());
				updateDragger();
				JOptionPane.showMessageDialog(Opcat2.getFrame(),cr.getMessage(),"Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
        else
        {
            if (wasDragged)
            {
                ((LinkInstance)myProject.getComponentsStructure().getEntry(myLink.getId()).getInstance(new OpdKey(opdId, entityInOpdId))).setAutoArranged(false);
            }
        }

		// restore original values for undo/redo purposes
		// even if that this instance deleted on switchLinkEdge
		edge = originalEdge;
		cPoint.setParam(originalPoint.getParam());
		cPoint.setSide(originalPoint.getSide());
		updateDragger();
	}

	public void showPopupMenu(int pX, int pY)
	{
		JPopupMenu jpm;
		jpm = new LinkPopup(myProject, myProject.getComponentsStructure().getEntry(myLink.getId()).getInstance(new OpdKey(opdId, entityInOpdId)));
		jpm.show(this, pX, pY);
		return;
	}

	public boolean equals(Object obj)
	{

		OpdLink tempLink;
		if (!(obj instanceof OpdLink))
		{
			return false;
		}

		tempLink = (OpdLink)obj;
		if (tempLink.getOpdId() == opdId && tempLink.getEntityInOpdId() == entityInOpdId)
		{
			return true;
		}
		else
		{
			return false;
		}
	}


}
