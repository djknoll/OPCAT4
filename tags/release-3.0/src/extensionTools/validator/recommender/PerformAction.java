package extensionTools.validator.recommender;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.event.UndoableEditEvent;

import exportedAPI.opcatAPI.IConnectionEdgeEntry;
import exportedAPI.opcatAPI.ILinkInstance;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.IRelationInstance;
import exportedAPI.opcatAPI.IThingInstance;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXSystem;
import extensionTools.validator.algorithm.Neighbor;
import extensionTools.validator.finder.Finder;
import gui.Opcat2;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdProject.OpdProject;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessInstance;
import gui.undo.CompoundUndoAction;
import gui.undo.UndoableAddFundamentalRelation;
import gui.undo.UndoableAddGeneralRelation;
import gui.undo.UndoableAddLink;
import gui.undo.UndoableAddObject;
import gui.undo.UndoableAddProcess;

/**
 * Performs an advice, giving by an advisor. The advice results in a creation of
 * a new thing (process or object), and connecting it to the current thing.
 * 
 * @author Eran Toch Created: 05/06/2004
 */
public class PerformAction extends AbstractAction {
    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    /**
         * 
         */

    private Neighbor neighbor;

    private IThingInstance instance;

    private IXSystem system;

    private MetaLibrary metaLib;

    public PerformAction(Neighbor _neighbor, IThingInstance _instance,
	    IXSystem _system, MetaLibrary _metaLib) {
	this.neighbor = _neighbor;
	this.instance = _instance;
	this.system = _system;
	this.metaLib = _metaLib;
    }

    /**
         * Performs the action specified by the advisor. The method perfomrs the
         * following actions:<br>
         * 1. Adds the new logical thing and its instance.<br>
         * 2. Adds the role to the thing.<br>
         * 3. Adds a connection (link, relation) to the thing.<br>
         * 4. Opens up the thing properties window, allowing the user to
         * configure the new thing.
         */
    public void actionPerformed(ActionEvent arg0) {
	try {
	    IConnectionEdgeEntry roleThing = (IConnectionEdgeEntry) this.neighbor
		    .getConnectedThing();
	    IXConnectionEdgeInstance newInstance = null;

	    // if ((roleThing instanceof ObjectEntry)
	    // || (roleThing instanceof StateEntry)) {

	    Role role = new Role(neighbor.getConnectedThing().getId(),
		    this.metaLib);

	    
	    newInstance = MetaLibrary.insertConnectionEdge(role, Opcat2
		    .getCurrentProject().getCurrentOpd(), true,
		    (ConnectionEdgeInstance) instance, (ConnectionEdgeInstance) instance.getParentIThingInstance());

	    // } else {
	    // ((ThingInstance) newInstance).getEntry().setName(
	    // roleThing.getName());
	    // newInstance = this.createThing((IThingEntry) roleThing);
	    // }

	    if (newInstance == null) {
		return;
	    }
//	    if (roleThing instanceof IThingEntry) {
//		this.createRole(newInstance, (IThingEntry) roleThing);
//	    }

	    IXInstance connection = this.createConnection(newInstance);
	    // ThingInstance tInstance = (ThingInstance) newInstance;
	    // boolean okPressed = tInstance.getThing()
	    // .callPropertiesDialogWithFeedback(
	    // BaseGraphicComponent.SHOW_ALL_TABS,
	    // BaseGraphicComponent.SHOW_ALL_BUTTONS);
	    // if (okPressed) {
	    CompoundUndoAction undoAction = new CompoundUndoAction();
	    if (connection instanceof ILinkInstance) {
		undoAction.addAction(new UndoableAddLink(
			(OpdProject) this.system, (LinkInstance) connection));
	    } else if (connection instanceof IRelationInstance) {
		if (connection instanceof GeneralRelationInstance) {
		    undoAction.addAction(new UndoableAddGeneralRelation(
			    (OpdProject) this.system,
			    (GeneralRelationInstance) connection));
		} else if (connection instanceof FundamentalRelationInstance) {
		    undoAction.addAction(new UndoableAddFundamentalRelation(
			    (OpdProject) this.system,
			    (FundamentalRelationInstance) connection));
		}
	    }
	    if (roleThing instanceof IObjectEntry) {
		undoAction
			.addAction(new UndoableAddObject(
				(OpdProject) this.system,
				(ObjectInstance) newInstance));
	    } else if (roleThing instanceof IProcessEntry) {
		undoAction
			.addAction(new UndoableAddProcess(
				(OpdProject) this.system,
				(ProcessInstance) newInstance));
	    }
	    // else if(roleThing instanceof IStateEntry) {
	    // undoAction.addAction(new UndoableAddObject(
	    // (OpdProject) this.system,
	    // (ObjectInstance) newInstance));
	    // }
	    Opcat2.getUndoManager().undoableEditHappened(
		    new UndoableEditEvent(this.system, undoAction));

	    // } else {
	    // this.system.delete(connection);
	    // this.system.delete(newInstance);
	    // }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    // /**
    // * Creates the new Thing.
    // *
    // * @param roleThing
    // * @return
    // */
    // protected IXConnectionEdgeInstance createThing(IThingEntry roleThing)
        // {
    // long opdID = this.instance.getKey().getOpdId();
    // int x = this.instance.getX() + this.instance.getWidth() + 30;
    // int y = this.instance.getY() + this.instance.getHeight() + 30;
    // IXConnectionEdgeInstance newInstance = null;
    // IXThingInstance parent = (IXThingInstance) this.instance
    // .getParentIThingInstance();
    // if (roleThing instanceof IObjectEntry) {
    // if (parent != null) {
    // newInstance = this.system.addObject(this.renderName(roleThing),
    // x, y, parent);
    // } else {
    // newInstance = this.system.addObject(this.renderName(roleThing),
    // x, y, opdID);
    // }
    // } else if (roleThing instanceof IProcessEntry) {
    // if (parent != null) {
    // newInstance = this.system.addProcess(
    // this.renderName(roleThing), x, y, parent);
    // } else {
    // newInstance = this.system.addProcess(
    // this.renderName(roleThing), x, y, opdID);
    // }
    // }
    // return newInstance;
    //
    // }

//    /**
//         * Creates the thing name.
//         * 
//         * @param roleThing
//         * @return
//         */
//    protected String renderName(IThingEntry roleThing) {
//	// return "New "+ roleThing.getName();
//	return "";
//    }

//    /**
//         * Creates the role of the new thing.
//         * 
//         * @param newInstance
//         * @param roleThing
//         * @throws Exception
//         */
//    protected void createRole(IXConnectionEdgeInstance newInstance,
//	    IThingEntry roleThing) throws Exception {
//	IXThingEntry logicalEntry = (IXThingEntry) newInstance.getIXEntry();
//	Role role = null;
//	try {
//	    role = new Role(roleThing.getId(), this.metaLib);
//	} catch (Exception ex) {
//	    throw ex;
//	}
//	logicalEntry.addRole(role);
//    }

    /**
         * Creates the connection to the new thing.
         * 
         * @param newInstance
         */
    protected IXInstance createConnection(IXConnectionEdgeInstance newInstance) {
	int type = this.neighbor.getConnectionType();
	int direction = this.neighbor.getDirection();
	IXInstance connection = null;
	// Handling relations
	if ((type < 300) && (type >= 200)) {
	    if (direction == Finder.DESTINATION_DIRECTION) {
		connection = this.system.addRelation(
			(IXConnectionEdgeInstance) this.instance,
			(IXConnectionEdgeInstance) newInstance, this.neighbor
				.getConnectionType());
	    } else if (direction == Finder.SOURCE_DIRECTION) {
		connection = this.system.addRelation(
			(IXConnectionEdgeInstance) newInstance,
			(IXConnectionEdgeInstance) this.instance, this.neighbor
				.getConnectionType());
	    }
	}
	// Handling links
	if ((type >= 300) && (type < 400)) {
	    if (direction == Finder.DESTINATION_DIRECTION) {
		connection = this.system.addLink(
			(IXConnectionEdgeInstance) this.instance,
			(IXConnectionEdgeInstance) newInstance, this.neighbor
				.getConnectionType());
	    } else if (direction == Finder.SOURCE_DIRECTION) {
		connection = this.system.addLink(
			(IXConnectionEdgeInstance) newInstance,
			(IXConnectionEdgeInstance) this.instance, this.neighbor
				.getConnectionType());
	    }
	}
	return connection;
    }
}
