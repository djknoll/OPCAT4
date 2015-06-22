package gui.opdGraphics.draggers;

import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.Opcat2;
import gui.checkModule.CheckResult;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.OpdCardinalityLabel;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.popups.GeneralRelationPopup;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.opmEntities.OpmGeneralRelation;
import gui.projectStructure.Entry;
import gui.projectStructure.GeneralRelationEntry;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.Instance;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


public abstract class  OpdRelationDragger extends AroundDragger
{
	protected OpdCardinalityLabel label;
	private int dX;
	private int dY;

	protected OpmGeneralRelation myRelation;

/**
 *  An ID of the OPD this dragger belongs to
 */
	protected long opdId;
	protected long entityInOpdId;


	public OpdRelationDragger(Connectable pEdge, RelativeConnectionPoint pPoint, OpmGeneralRelation pRelation,
											  long pOpdId, long pEntityInOpdId, OpdProject pProject)
	{
		super(pEdge, pPoint, pProject);
		myRelation = pRelation;
		myProject = pProject;
		opdId = pOpdId;
		entityInOpdId = pEntityInOpdId;
		setCanLeave(true);

		super.updateDragger();
		label = new OpdCardinalityLabel(null, pProject);
		dX = 20;
		dY = 20;
		label.addMouseListener(label);
		label.addMouseMotionListener(label);
		label.setLocation(getX()+dX, getY() + dY);

		GenericTable config = myProject.getConfiguration();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double factor = currentSize/normalSize;
		double size = ((Integer)config.getProperty("DraggerSize")).intValue()*factor;
		setSize((int)size,(int)size);

	}

		public OpdCardinalityLabel getOpdCardinalityLabel()
		{
		  return label;
		}

		public void setText(String text)
		{
		  label.setText(text);
		}

		public String getText()
		{
		  return label.getText();
		}

	public void mouseDragged(MouseEvent e)
	{
        if (StateMachine.isAnimated() || StateMachine.isWaiting())
        {
            return;
        }

		dX = label.getX() - getX();
		dY = label.getY() - getY();
		super.mouseDragged(e);
		Point convPoint;
		convPoint = SwingUtilities.convertPoint(this, e.getX(), e.getY(), (JComponent)edge);
		if (this.isCanLeave() && !edge.isInAdjacentArea((int)convPoint.getX(), (int)convPoint.getY()))
		{
		  label.setLocation(getX() + dX, getY() + dY);
		}
	}

	public void updateDragger()
	{
		dX = label.getX() - getX();
		dY = label.getY() - getY();
		super.updateDragger();
		label.setLocation(getX() + dX, getY() + dY);
	}

		public OpmGeneralRelation getEntity()
	{
		  return myRelation;
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

		  Entry myEntry = myProject.getComponentsStructure().getEntry(myRelation.getId());
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
			GeneralRelationEntry gre = (GeneralRelationEntry)myProject.getComponentsStructure().getEntry(myRelation.getId());
			GeneralRelationInstance gri = (GeneralRelationInstance)gre.getInstance(new OpdKey(opdId, entityInOpdId));

			CheckResult cr = myProject.switchGeneralRelationEdge(gri, (OpdConnectionEdge)originalEdge, (OpdConnectionEdge)edge, cPoint);
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
                ((GeneralRelationInstance)myProject.getComponentsStructure().getEntry(myRelation.getId()).getInstance(new OpdKey(opdId, entityInOpdId))).setAutoArranged(false);
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
		jpm = new GeneralRelationPopup(myProject, myProject.getComponentsStructure().getEntry(myRelation.getId()).getInstance(new OpdKey(opdId, entityInOpdId)));
		jpm.show(this, pX, pY);
		return;
	}



}