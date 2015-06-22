package gui.actions.plugins;


import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import gui.controls.FileControl;
import gui.controls.GuiControl;
import extensionTools.visWeb.*;

	;
	/**
	 * 
	 * @author Perelman Valeria
	 */
	public class DependenciesAction extends AbstractAction {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		 

		protected FileControl fileControl = FileControl.getInstance();

		protected GuiControl myGuiControl = GuiControl.getInstance();

		/**
		 * 
		 */
		public DependenciesAction() {
			super();
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param arg0
		 */
		public DependenciesAction(String arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		public DependenciesAction(String arg0, Icon arg1) {
			super(arg0, arg1);
			// TODO Auto-generated constructor stub
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (!this.fileControl.isProjectOpened()) {
				JOptionPane
						.showMessageDialog(this.myGuiControl.getFrame(),
								"No system is opened", "Message",
								JOptionPane.ERROR_MESSAGE);
				return;
			}
			int numOfColumns = 3;
			java.util.Vector columnNames = new java.util.Vector();
			columnNames.add("Process");
			columnNames.add("Process Predecessor");
			columnNames.add("Type");
			new gui.procDependencies.gui.DependenciesSummaryPanel(numOfColumns, columnNames,this.fileControl.getCurrentProject()).setVisible(true);
		}

	}


