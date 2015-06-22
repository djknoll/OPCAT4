package gui.repository.rpopups;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;

import gui.Opcat2;
import gui.controls.FileControl;
import gui.images.standard.StandardImages;
import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.opdProject.OpdProject;
import gui.repository.BaseView;
import gui.repository.ModelsView;
import gui.util.BrowserLauncher2;
import gui.util.Configuration;
import gui.util.OpcatFile;
import gui.util.OpcatLogger;
import gui.util.opcatGrid.GridPanel;

public class RDirPopup extends RDefaultPopup {

	OpcatFile file;

	public RDirPopup(BaseView view, OpdProject prj) {
		super(view, prj);
		// TODO Auto-generated constructor stub

		if (userObject instanceof OpcatFile) {
			this.file = (OpcatFile) this.userObject;

			if (!file.isDirectory()) {
				add(OpeninNewWindow);
				add(OpeninCurrentWindow);
				add(new JSeparator());
				JMenuItem item = add(AddasPolicy);
				if (myProject == null)
					item.setEnabled(false);
				item = add(AddasClassification);
				if (myProject == null)
					item.setEnabled(false);
			}

			if (file.isDirectory()) {
				JMenuItem item = add(RemoveDirectory);
				int i = 0;
				if (view.getModel() != null) {
					i = ((DefaultMutableTreeNode) view.getModel().getRoot())
							.getIndex(((DefaultMutableTreeNode) this.selectedPath
									.getPathComponent(1)));
				}

				if ((i == 0) || (selectedPath.getPathCount() > 2))
					item.setEnabled(false);

			}
		}

		if (userObject instanceof String) {
			add(RefrashAction);
			add(new JSeparator());
			add(AddDirectory);
		}
	}

	Action RemoveDirectory = new AbstractAction("Remove Directory",
			StandardImages.DELETE) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {

			Configuration.getInstance().removeDirectoryToUserDirectories(
					file.getAbsolutePath());
			((ModelsView) view).setRefrash(true);
			view.rebuildTree(null, null);
		}
	};

	Action AddDirectory = new AbstractAction("Add Directory",
			StandardImages.SAVE_AS) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			JFileChooser myFileChooser = new JFileChooser();
			myFileChooser.setSelectedFile(new File(""));
			myFileChooser.resetChoosableFileFilters();
			myFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			int retVal = myFileChooser.showOpenDialog(Opcat2.getFrame());
			if (retVal == JFileChooser.APPROVE_OPTION) {
				Configuration.getInstance().addDirectoryToUserDirectories(
						myFileChooser.getSelectedFile().getAbsolutePath());
				((ModelsView) view).setRefrash(true);
				view.rebuildTree(null, null);
			}
		}
	};

	Action AddasClassification = new AbstractAction("Add as Classification",
			StandardImages.Classification) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		public void actionPerformed(ActionEvent e) {

			String path = file.getAbsolutePath();
			int type = MetaLibrary.TYPE_FILE;
			boolean isPolicy = false;

			try {
				if (myProject != null) {
					MetaLibrary newLib = myProject.getMetaManager()
							.createNewMetaLibraryReference(path, type);
					newLib.setPolicyLibrary(isPolicy);
					myProject.getMetaManager().addMetaLibrary(newLib);
					myProject.setCanClose(false);
					myProject.getMetaManager().refresh(myProject,
							new JProgressBar());
				}
			} catch (MetaException E) {
				OpcatLogger.logError(E);
			}
		}
	};

	Action AddasPolicy = new AbstractAction("Add as Policy",
			StandardImages.Policies) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		public void actionPerformed(ActionEvent e) {

			String path = file.getAbsolutePath();
			int type = MetaLibrary.TYPE_FILE;
			boolean isPolicy = true;

			try {
				if (myProject != null) {
					MetaLibrary newLib = myProject.getMetaManager()
							.createNewMetaLibraryReference(path, type);
					newLib.setPolicyLibrary(isPolicy);
					myProject.getMetaManager().addMetaLibrary(newLib);
					myProject.setCanClose(false);
					myProject.getMetaManager().refresh(myProject,
							new JProgressBar());
				}
			} catch (MetaException E) {
				OpcatLogger.logError(E);
			}
		}
	};

	Action OpeninCurrentWindow = new AbstractAction("Open in Current Window",
			StandardImages.REFRESH) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		public void actionPerformed(ActionEvent e) {
			try {
				GridPanel.RemoveALLPanels() ; 
								

				FileControl.getInstance().loadFileWithOutFilesPrompt(
						file.getPath());			
	
				if (myProject != null)
					myProject.setCanClose(false);
			} catch (Exception ex) {
				OpcatLogger.logError(ex);
			}
		}
	};

	Action OpeninNewWindow = new AbstractAction("Open in new Window",
			StandardImages.OPEN) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		public void actionPerformed(ActionEvent e) {
			try {
				URL url = new URL("file", "", file.getAbsolutePath());
				BrowserLauncher2.openURL(url.toExternalForm());
			} catch (Exception ex) {
				OpcatLogger.logError(ex);
			}
		}
	};

	Action RefrashAction = new AbstractAction("Refresh View",
			StandardImages.REFRESH) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		public void actionPerformed(ActionEvent e) {
			((ModelsView) view).setRefrash(true);
			view.rebuildTree(null, null);
		}
	};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
