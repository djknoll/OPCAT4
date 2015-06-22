package gui.repository.rpopups;

import gui.Opcat2;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;
import gui.repository.BaseView;
import gui.repository.Repository;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class RProjectPopup extends RDefaultPopup {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	OpdProject myProject;

	Repository repo;

	public RProjectPopup(BaseView view, Repository rep, OpdProject prj) {
		super(view, prj);

		this.myProject = (OpdProject) this.userObject;
		this.repo = rep;
		this.add(new JSeparator());
		this.add(this.propertiesAction);
		this.add(new JSeparator());
		JMenu submenu = new JMenu("Views");

		JMenuItem byOPDMenu = new JMenuItem("OPD");
		byOPDMenu.addActionListener(this);
		submenu.add(byOPDMenu);

		JMenuItem byOPDDetailesMenu = new JMenuItem(
				"OPD Things");
		byOPDDetailesMenu.addActionListener(this);
		submenu.add(byOPDDetailesMenu);

		JMenuItem byThing = new JMenuItem("Things");
		byThing.addActionListener(this);
		submenu.add(byThing);
		
		JMenuItem meta = new JMenuItem("Meta Library");
		meta.addActionListener(this);
		submenu.add(meta);						

		this.add(submenu, StandardImages.EMPTY);
		
		JMenuItem refresh = new JMenuItem("Refresh View");
		refresh.addActionListener(this);
		this.add(refresh, StandardImages.EMPTY);
		
		this.addCollapseExpand();
	}

	Action propertiesAction = new AbstractAction("Project Properties",
			StandardImages.PROPERTIES) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

		/**
				 * 
				 */
				 

		public void actionPerformed(ActionEvent e) {
			ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(
					RProjectPopup.this.myProject, Opcat2.getFrame(), "Project Properties");
			ppd.showDialog();
		}
	};

	public void actionPerformed(ActionEvent arg0) {

		// TODO fix the asking on the lable
		JMenuItem menu = (JMenuItem) arg0.getSource();
		String name = menu.getText();

		menu.setSelected(true);
		if (name.equalsIgnoreCase("OPD Things")) {
			this.repo.changeView(Repository.OPDThingsVIEW);
		}

		if (name.equalsIgnoreCase("OPD")) {
			this.repo.changeView(Repository.OPDVIEW);
		}

		if (name.equalsIgnoreCase("Things")) {
			this.repo.changeView(Repository.ThingsVIEW);
		}
		
		if (name.equalsIgnoreCase("Meta Library")) {
			this.repo.changeView(Repository.MetaVIEW);
		}	
		
		if (name.equalsIgnoreCase("Refresh View")) { 
			this.repo.rebuildTrees();
		}		

		menu.setSelected(true);

	}

	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

}