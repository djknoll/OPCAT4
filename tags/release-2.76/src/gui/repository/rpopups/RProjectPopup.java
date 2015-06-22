package gui.repository.rpopups;

import gui.Opcat2;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;
import gui.repository.BaseView;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;


public class RProjectPopup extends RDefaultPopup
{
	OpdProject myProject;
	public RProjectPopup(BaseView view, OpdProject prj)
	{
		super(view, prj);
		myProject = (OpdProject)userObject;
		add(propertiesAction);
		addCollapseExpand();
	}

	Action propertiesAction = new AbstractAction("Project Properties", StandardImages.PROPERTIES){
		public void actionPerformed(ActionEvent e){
			ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(myProject, Opcat2.getFrame(), "Project Properties");
			ppd.showDialog();
		}
	};

}