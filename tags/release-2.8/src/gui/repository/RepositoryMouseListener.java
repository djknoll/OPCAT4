package gui.repository;

import gui.opdGraphics.DrawingArea;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.projectStructure.ThingInstance;
import gui.repository.rpopups.ROpdPopup;
import gui.repository.rpopups.RProjectPopup;
import gui.repository.rpopups.RThingPopup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


class RepositoryMouseListener extends MouseAdapter
{
	BaseView eventSender;
	Repository repository;
	public RepositoryMouseListener(BaseView tree, Repository rep)
	{
		repository = rep;
		eventSender = tree;
	}

	public void mouseReleased(MouseEvent e)
	{
        if (StateMachine.isAnimated() || StateMachine.isAnimated())
        {
            return;
        }

		if (e.isPopupTrigger())
		{
			TreePath selPath = eventSender.getPathForLocation(e.getX(), e.getY());
			if (selPath == null)
			{
				return;
			}
			eventSender.setSelectionPath(selPath);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)(selPath.getLastPathComponent());
			if (node != null)
			{
				Object obj = node.getUserObject();
				if (obj instanceof Opd)
				{
					JPopupMenu jpm = new ROpdPopup(eventSender, repository.getProject());
					jpm.show(eventSender, e.getX(), e.getY());
				}
				if (obj instanceof ThingInstance)
				{
					JPopupMenu jpm = new RThingPopup(eventSender, repository.getProject());
					jpm.show(eventSender, e.getX(), e.getY());
				}
				if(obj instanceof OpdProject)
				{
					JPopupMenu jpm = new RProjectPopup(eventSender, repository.getProject());
					jpm.show(eventSender, e.getX(), e.getY());
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		
		TreePath selPath = eventSender.getPathForLocation(e.getX(), e.getY());
		if (selPath == null)
		{
			return;
		}
		eventSender.setSelectionPath(selPath);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)(selPath.getLastPathComponent());
		
		

		if(e.getClickCount() == 2)
		{
			if (node != null)
			{
				Object obj = node.getUserObject();
				if (obj instanceof Opd)
				{
					showOpd((Opd)obj); 
				}

				if (obj instanceof ThingInstance)
				{
					ThingInstance inst = ((ThingInstance)obj);
					long opdId = inst.getThing().getOpdId();
					Opd myOpd = repository.getProject().getComponentsStructure().getOpd(opdId);
					OpdProject myProject = repository.getProject();
					showOpd(myOpd);
					myProject.removeSelection();
					myProject.addSelection(inst, true);
					inst.getThing().setSelected(true);

					//center view to Thing
					centerView(myOpd, inst);

				}
			}
		}
		//here we need to color the selected cell background.
	}
	
	


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

