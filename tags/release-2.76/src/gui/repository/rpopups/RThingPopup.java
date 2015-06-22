package gui.repository.rpopups;
import gui.Opcat2;
import gui.checkModule.CheckModule;
import gui.checkModule.CheckResult;
import gui.images.standard.StandardImages;
import gui.opdGraphics.DrawingArea;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.Instance;
import gui.projectStructure.ThingInstance;
import gui.repository.BaseView;
import gui.undo.CompoundUndoAction;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;



public class RThingPopup extends RDefaultPopup
{
	ThingInstance myInstance;
	OpdThing myThing;
	public RThingPopup(BaseView view, OpdProject prj)
	{
		super(view, prj);
		myInstance = (ThingInstance)userObject;
		myThing = myInstance.getThing();
		add(showAction);
		add(new JSeparator());
		add(deleteAction);
		add(new JSeparator());
		add(zoomInAction);
		add(unfoldingAction);
		add(new JSeparator());
		add(propertiesAction);
		addCollapseExpand();
	}




	Action showAction = new AbstractAction("Focus On Thing", StandardImages.EMPTY){
		public void actionPerformed(ActionEvent e){
					long opdId = myThing.getOpdId();
					Opd myOpd = myProject.getComponentsStructure().getOpd(opdId);
					showOpd(myOpd);
					myProject.removeSelection();
					myProject.addSelection(myInstance, true);
					myThing.setSelected(true);

					//center view to Thing
					centerView(myOpd, myInstance);

		}
	};

//	Action copyAction = new AbstractAction("Copy", StandardImages.COPY){
//		public void actionPerformed(ActionEvent e){
//			myProject.copy();
//			myProject.getCurrentOpd().getDrawingArea().repaint();
//		}
//	};


	Action deleteAction = new AbstractAction("Delete", StandardImages.DELETE){
		public void actionPerformed(ActionEvent e){

			CheckResult cr = CheckModule.checkDeletion(myInstance, myProject);
			if (cr.getResult() == CheckResult.WRONG)
			{
				JOptionPane.showMessageDialog(Opcat2.getFrame(), cr.getMessage(), "Opcat2 - Error" ,JOptionPane.ERROR_MESSAGE);
				return;
			}


			if (cr.getResult() == CheckResult.WARNING)
			{
				int retVal = JOptionPane.showConfirmDialog(Opcat2.getFrame(), cr.getMessage(),
				"Opcat2 - Warning" ,JOptionPane.YES_NO_OPTION);

				if (retVal != JOptionPane.YES_OPTION)
				{
					return;
				}
			}

			CompoundUndoAction undoAction = new CompoundUndoAction();

			for (Enumeration e1 = myInstance.getRelatedInstances(); e1.hasMoreElements();)
			{
				Instance currInstance = (Instance)e1.nextElement();
				undoAction.addAction(myProject.deleteInstance(currInstance));
			}

			undoAction.addAction(myProject.deleteInstance(myInstance));



			Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(this, undoAction));
			Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
			Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action zoomInAction = new AbstractAction("In-Zooming", StandardImages.EMPTY){
		public void actionPerformed(ActionEvent e){
			try{
				Opd myOpd = myProject.getComponentsStructure().getOpd(myThing.getOpdId());
				myProject.removeSelection();
				myProject.addSelection(myInstance, true);
				myThing.setSelected(true);
				centerView(myOpd, myInstance);

				myProject.zoomIn();
			}
			catch (Exception e1)	{System.out.println(e1);}
		}
	};

	Action unfoldingAction = new AbstractAction("Unfolding", StandardImages.EMPTY){
		public void actionPerformed(ActionEvent e){
			try{
				Opd myOpd = myProject.getComponentsStructure().getOpd(myThing.getOpdId());
				myProject.removeSelection();
				myProject.addSelection(myInstance, true);
				myThing.setSelected(true);
				centerView(myOpd, myInstance);

				myProject.unfolding();
			}
			catch (Exception e1)	{System.out.println(e1);}
		}
	};

	Action propertiesAction = new AbstractAction("Properties", StandardImages.PROPERTIES){
		public void actionPerformed(ActionEvent e){
		   myThing.callPropertiesDialog(BaseGraphicComponent.SHOW_ALL_TABS, BaseGraphicComponent.SHOW_ALL_BUTTONS);
		   myProject.getComponentsStructure().getOpd(myThing.getOpdId()).getDrawingArea().repaint();
		}
	};

	private void centerView(Opd myOpd, ThingInstance inst)
	{
		DrawingArea da = (DrawingArea)myOpd.getDrawingArea();
		JViewport jv = (JViewport)da.getParent();
		OpdThing thing = inst.getThing();
		Dimension visiblePort = jv.getExtentSize();

		int newX = (int)(thing.getX() + thing.getWidth()/2);
		int newY = (int)(thing.getY() + thing.getHeight()/2);

		Point thingCenter = SwingUtilities.convertPoint(thing.getParent(), newX, newY, da);

		newX = (int)(thingCenter.getX() - visiblePort.getWidth()/2);
		newY = (int)(thingCenter.getY() - visiblePort.getHeight()/2);

		int maxX = da.getWidth() - jv.getWidth();
		int maxY = da.getHeight() - jv.getHeight();

		if (newX < 0) newX = 0;
		if (newX > maxX) newX = maxX;
		if (newY < 0) newY = 0;
		if (newY > maxY) newY = maxY;

		jv.setViewPosition(new Point(newX, newY));
	}


	private void showOpd(Opd myOpd)
	{
		myOpd.setVisible(true);
		try{
			myOpd.getOpdContainer().setSelected(true);
			myOpd.getOpdContainer().setMaximum(true);
		}catch(java.beans.PropertyVetoException pve)
		{
			pve.printStackTrace();
		}
	}

}