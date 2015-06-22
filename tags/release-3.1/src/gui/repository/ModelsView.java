package gui.repository;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;
import gui.util.Configuration;
import gui.util.OpcatFile;
import gui.util.OpcatLogger;

public class ModelsView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2888245865859717591L;
	private boolean refrash = true;

	public ModelsView(String invisibleRoot) {
		super(invisibleRoot);

		setRootVisible(true);
		this.tip = new String("Manage models");
		this.viewName = "Models";
		this.icon = StandardImages.System_Icon;
		this.setType(Repository.ModelsVIEW);
	}

	public void rebuildTree(MainStructure s, OpdProject opdProject) {

		if (!refrash) {
			return;
		}

		refrash = false;

		clearTree();

		DefaultTreeModel model = (DefaultTreeModel) this.getModel();

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Models");
		model.setRoot(root);

		try {
			ListIterator<String> iter = Configuration.getInstance()
					.getUserDirectories();

			while (iter.hasNext()) {
				String path = iter.next();
				File file = new File(path);
				if (file.exists() && file.isDirectory()) {
					addNodes(root, file);
				} else {
					Configuration.getInstance()
							.removeDirectoryToUserDirectories(path);
				}
			}
		} catch (Exception ex) {
			OpcatLogger.logError(ex);
		}
		expandRoot();

	}

	/** Add nodes from under "dir" into curTop. Highly recursive. */
	DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
		String curPath = dir.getPath();
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(
				new OpcatFile(curPath));
		if (curTop != null) { // should only be null at root
			curTop.add(curDir);
		}
		Vector ol = new Vector();
		// It is also possible to filter the list of returned files.
		// This example does not return any files that start with `.'.
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				File file = new File(dir + File.separator + name);
				return (name.toLowerCase().endsWith("opz") || (file
						.isDirectory() && (file.list(this).length > 0)));
			}
		};

		String[] tmp = dir.list(filter);

		for (int i = 0; i < tmp.length; i++)
			ol.addElement(tmp[i]);
		Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
		File f;
		Vector files = new Vector();
		// Make two passes, one for Dirs and one for Files. This is #1.
		for (int i = 0; i < ol.size(); i++) {
			String thisObject = (String) ol.elementAt(i);
			String newPath;
			if (curPath.equals("."))
				newPath = thisObject;
			else
				newPath = curPath + File.separator + thisObject;
			if ((f = new File(newPath)).isDirectory())
				addNodes(curDir, f);
			else {
				OpcatFile file = new OpcatFile(curPath + File.separator
						+ thisObject);
				files.addElement(file);
			}
		}
		// Pass two: for files.
		for (int fnum = 0; fnum < files.size(); fnum++)
			curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
		return curDir;
	}

	public void setRefrash(boolean refrash) {
		this.refrash = refrash;
	}

	// public Dimension getMinimumSize() {
	// return new Dimension(200, 400);
	// }
	//
	// public Dimension getPreferredSize() {
	// return new Dimension(200, 400);
	// }
}
