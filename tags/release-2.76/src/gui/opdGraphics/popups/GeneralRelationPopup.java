package gui.opdGraphics.popups;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.Instance;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;


public class GeneralRelationPopup extends DefaultPopup
{
	GeneralRelationInstance myInstance;

	public GeneralRelationPopup(OpdProject prj, Instance inst)
	{
		super(prj, inst);
		myInstance = (GeneralRelationInstance)inst;//(GeneralRelationInstance)(myProject.getCurrentOpd().getSelectedItem());

		JMenuItem jmi = new JMenuItem(arrangeAction);
		if(myInstance.isAutoArranged())
		{
			jmi.setIcon(StandardImages.CHECKED);
		}
		else
		{
			jmi.setIcon(StandardImages.UNCHECKED);
		}
		add(jmi);

		if(multiSelected)
		{
			add(copyAction);
			add(cutAction);
		}
		add(deleteAction);
		add(new JSeparator());
		JMenu lineMenu = new JMenu("Line Shape");
		lineMenu.setIcon(StandardImages.EMPTY);
		lineMenu.add(straightAction);
		orthogonalAction.setEnabled(false);
		lineMenu.add(orthogonalAction);
		add(lineMenu);
		add(new JSeparator());
		add(propertiesAction);
	}

	Action propertiesAction = new AbstractAction("Properties", StandardImages.PROPERTIES){
		public void actionPerformed(ActionEvent e){
		   ((GeneralRelationInstance)(myProject.getCurrentOpd().getSelectedItem())).getLine().callPropertiesDialog();
		   myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action arrangeAction = new AbstractAction("Auto Arrange"){
		public void actionPerformed(ActionEvent e){
		   myInstance.setAutoArranged(!myInstance.isAutoArranged());
		}
	};

	Action straightAction = new AbstractAction("Straight"){
		public void actionPerformed(ActionEvent e){
		   myInstance.getLine().makeStraight();
		}
	};

	Action orthogonalAction = new AbstractAction("Orthogonal"){
		public void actionPerformed(ActionEvent e){
		}
	};
}