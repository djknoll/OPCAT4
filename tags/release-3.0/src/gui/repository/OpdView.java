package gui.repository;

import gui.images.standard.StandardImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;
import gui.util.OpcatLogger;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class OpdView extends BaseView {

    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    /**
         * 
         */
    OpdProject myProject = null;

    public OpdView(String invisibleRoot) {
	super(invisibleRoot);
	this.tip = new String("OPD Hierarchy");
	this.viewName = "OPD Hierarchy";
	this.setType(Repository.OPDVIEW);

	// icon = RepositoryImages.OPD;
	this.icon = StandardImages.System_Icon;

    }

    public void rebuildTree(MainStructure s, OpdProject rootComponent) {
	myProject = rootComponent;
	super.rebuildTree(s, rootComponent);

	DefaultTreeModel model = (DefaultTreeModel) this.getModel();
	DefaultMutableTreeNode prjRoot = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) model
		.getRoot()).getChildAt(0);

	this._scanOpdTreeDFS(s, model, -1, prjRoot);

	this.setExpandedDescendants();
    }

    private void _scanOpdTreeDFS(MainStructure s, DefaultTreeModel model,
	    long rootOpdId, DefaultMutableTreeNode parentNode) {
	DefaultMutableTreeNode tmpOpdNode;
	TreeMap opdSorter = new TreeMap();
	Opd tmpParentOpd;
	Object tmpObj;
	long parentOpdId;

	// get all opds and select those that have OPD identified by rootOpdId
	for (Enumeration e = s.getOpds(); e.hasMoreElements();) {
	    Object o = e.nextElement();
	    tmpParentOpd = ((Opd) o).getParentOpd();

	    // handle rootOpd case
	    if (tmpParentOpd == null) {
		parentOpdId = -1;
	    } else {
		parentOpdId = tmpParentOpd.getOpdId();
	    }

	    if (parentOpdId == rootOpdId) {
		// if (o.toString().contains("unfolded")) {
		// opdSorter.put("zzzzzzzzzzzzzzzzzzz" + o.toString(), o);
		// } else {
		opdSorter.put(o.toString(), o);
		// }
	    }
	}

	// for each child: add to node
	// recursivly scan it's subtree
	for (Iterator i = opdSorter.values().iterator(); i.hasNext();) {
	    // the next line is for 'setExpandedDescendants()'
	    // we update parent when we know that is has children
	    // see 'nodesHash' in 'BaseView'
	    this.nodesHash.put(new HashKey(parentNode.getUserObject()),
		    parentNode);

	    tmpObj = i.next();
	    tmpOpdNode = new DefaultMutableTreeNode(tmpObj);
	    model.insertNodeInto(tmpOpdNode, parentNode, parentNode
		    .getChildCount());

	    // Opening all the OPD folders in the tree - Lera
	    TreePath nodes = new TreePath(tmpOpdNode.getPath());
	    this.addSelectionPath(nodes);
	    // END

	    this._scanOpdTreeDFS(s, model, ((Opd) tmpObj).getOpdId(),
		    tmpOpdNode);
	}

	return;
    }

    private String getImage(MouseEvent evt) {
	String file = System.getProperty("java.io.tmpdir")  + "tempOpcatImage" + ".jpg";
	
	try {
	    BufferedOutputStream out = new BufferedOutputStream(
		    new FileOutputStream(file));
	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	    
	    
	    Opd opd = myProject.getCurrentOpd() ; //(Opd) ((DefaultMutableTreeNode) getAnchorSelectionPath().getLastPathComponent())
		    //.getUserObject();

	    
	    
	    BufferedImage bi = myProject.getOpdByID(opd.getOpdId())
		    .getImageRepresentation();
	    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
	    int quality = 100;
	    quality = Math.max(0, Math.min(quality, 100));
	    param.setQuality(quality / 100.0f, false);
	    encoder.setJPEGEncodeParam(param);
	    encoder.encode(bi);
	    out.close();
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

	return file;
    }
}