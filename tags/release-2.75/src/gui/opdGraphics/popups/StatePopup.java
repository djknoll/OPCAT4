package gui.opdGraphics.popups;
import gui.Opcat2;
import gui.images.standard.StandardImages;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmState;
import gui.projectStructure.Instance;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;


public class StatePopup extends DefaultPopup
{
	OpmState opmSt;
	public StatePopup(OpdProject prj, Instance inst)
	{
		super(prj, inst);
		JMenuItem jmi;
		StateInstance stInstance = (StateInstance)(myProject.getCurrentOpd().getSelectedItem());
		opmSt = ((OpmState)stInstance.getState().getEntity());

		if(((StateEntry)stInstance.getEntry()).isRelated())
		{
			deleteAction.setEnabled(false);
		}
		if(stInstance.isRelated())
		{
			hideAction.setEnabled(false);
		}
		add(deleteAction);
		add(hideAction);
		add(new JSeparator());
		jmi = new JMenuItem(setInitialAction);
		if(opmSt.isInitial())
		{
			jmi.setIcon(StandardImages.CHECKED);
		}
		else
		{
			jmi.setIcon(StandardImages.UNCHECKED);
		}
		add(jmi);
		jmi = new JMenuItem(setFinalAction);
		if(opmSt.isFinal())
		{
			jmi.setIcon(StandardImages.CHECKED);
		}
		else
		{
			jmi.setIcon(StandardImages.UNCHECKED);
		}
		add(jmi);
		add(new JSeparator());
		add(propertiesAction);
	}

	Action propertiesAction = new AbstractAction("Properties", StandardImages.PROPERTIES){
		public void actionPerformed(ActionEvent e){
		   ((StateInstance)(myProject.getCurrentOpd().getSelectedItem())).getState().callPropertiesDialog(
				BaseGraphicComponent.SHOW_ALL_TABS, BaseGraphicComponent.SHOW_ALL_BUTTONS);
		   myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action setInitialAction = new AbstractAction("Initial"){
		public void actionPerformed(ActionEvent e){
		   opmSt.setInitial(!opmSt.isInitial());
		   myProject.getCurrentOpd().getDrawingArea().repaint();
		   Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}
	};

	Action setFinalAction = new AbstractAction("Final"){
		public void actionPerformed(ActionEvent e){
		   opmSt.setFinal(!opmSt.isFinal());
		   myProject.getCurrentOpd().getDrawingArea().repaint();
		   Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}
	};

	Action hideAction = new AbstractAction("Hide state", StandardImages.EMPTY){
		public void actionPerformed(ActionEvent e){
		   ((StateInstance)(myProject.getCurrentOpd().getSelectedItem())).getState().setVisible(false);
		   myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

}