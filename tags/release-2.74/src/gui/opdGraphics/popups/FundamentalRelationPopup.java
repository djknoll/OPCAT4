package gui.opdGraphics.popups;

import gui.images.standard.StandardImages;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdProject.OpdProject;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.Instance;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;


public class FundamentalRelationPopup extends DefaultPopup
{
	public FundamentalRelationPopup(OpdProject prj, Instance inst)
	{
		super(prj, inst);
		copyAction.setEnabled(false);
		add(copyAction);
//		cutAction.setEnabled(false);
//		add(cutAction);
		add(deleteAction);
		add(new JSeparator());
		add(propertiesAction);
	}

	Action propertiesAction = new AbstractAction("Properties", StandardImages.PROPERTIES){
		public void actionPerformed(ActionEvent e)
		{
			FundamentalRelationInstance inst =
				   (FundamentalRelationInstance)(myProject.getCurrentOpd().getSelectedItem());
			inst.getGraphicalRelationRepresentation().getRelation().callPropertiesDialog(
				BaseGraphicComponent.SHOW_ALL_TABS, BaseGraphicComponent.SHOW_ALL_BUTTONS);
			myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};
}