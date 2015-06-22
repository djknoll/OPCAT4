package gui.repository.rpopups;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;
import gui.actions.file.ReuseImportAction;
import gui.images.repository.RepositoryImages;
import gui.images.standard.StandardImages;
import gui.metaLibraries.logic.MetaLibrary;
import gui.opdProject.OpdProject;
import gui.repository.BaseView;
import gui.util.BrowserLauncher2;
import gui.util.OpcatException;
import gui.util.debug.Debug;

public class RMetaPopup extends RDefaultPopup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	MetaLibrary myMeta;

	public RMetaPopup(BaseView view, OpdProject prj) {
		super(view, prj);
		this.myMeta = (MetaLibrary) this.userObject;
		this.add(this.importAction);
		this.add(new JSeparator());
		this.add(this.openAction);
		this.addCollapseExpand();
	}

	Action importAction = new AbstractAction("Import Library",
			RepositoryImages.TREE) {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			ReuseImportAction imp = new ReuseImportAction("Import Model",
					StandardImages.IMPORT);
			try {
				imp.doImport(myMeta.getPath());
			} catch (OpcatException ex) {
				Debug debug = Debug.getInstance();
				debug.Print(myMeta, "Import Failed :"
						+ ex.getLocalizedMessage(), "1");
			}
			myProject.setCanClose(false) ; 
		}
	};

	Action openAction = new AbstractAction("Open Library", StandardImages.OPEN) {
		private static final long serialVersionUID = 8446841991771412186L;

		public void actionPerformed(ActionEvent e) {
			try {
				URL url = new URL("file", "" , myMeta.getPath() ) ;
				BrowserLauncher2.openURL(url.toExternalForm()) ; 
			} catch (Exception ex) {
				Debug debug = Debug.getInstance() ; 
				debug.Print(myMeta, ex.getLocalizedMessage(), "1") ; 
			}
		}
	};

}