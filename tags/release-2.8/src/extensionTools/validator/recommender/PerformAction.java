package extensionTools.validator.recommender;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.event.UndoableEditEvent;

import exportedAPI.opcatAPI.ILinkInstance;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.IRelationInstance;
import exportedAPI.opcatAPI.IThingEntry;
import exportedAPI.opcatAPI.IThingInstance;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXThingEntry;
import exportedAPI.opcatAPIx.IXThingInstance;
import extensionTools.validator.algorithm.Neighbor;
import extensionTools.validator.finder.Finder;
import gui.Opcat2;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdProject.OpdProject;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.ThingInstance;
import gui.undo.CompoundUndoAction;
import gui.undo.UndoableAddFundamentalRelation;
import gui.undo.UndoableAddGeneralRelation;
import gui.undo.UndoableAddLink;
import gui.undo.UndoableAddObject;
import gui.undo.UndoableAddProcess;

/**
 * Performs an advice, giving by an advisor. The advice results in a creation of
 * a new thing (process or object), and connecting it to the current thing. 
 * @author Eran Toch
 * Created: 05/06/2004
 */
public class PerformAction extends AbstractAction {
	private Neighbor neighbor;
	private IThingInstance instance; 
	private IXSystem system;
	private MetaLibrary metaLib;
	
	public PerformAction(Neighbor _neighbor, IThingInstance _instance, IXSystem _system, MetaLibrary _metaLib)	{
		neighbor = _neighbor;
		instance = _instance;
		system = _system;
		metaLib = _metaLib;
	}
	
	/**
	 * Performs the action specified by the advisor. 
	 * The method perfomrs the following actions:<br>
	 * 1. Adds the new logical thing and its instance.<br>
	 * 2. Adds the role to the thing.<br>
	 * 3. Adds a connection (link, relation) to the thing.<br>
	 * 4. Opens up the thing properties window, allowing the user to configure
	 * the new thing.
	 */	
	public void actionPerformed(ActionEvent arg0) {
		try	{
			IThingEntry roleThing = neighbor.getConnectedThing();
			IXThingInstance newInstance = createThing(roleThing);
			if (newInstance == null)	{
				return;
			}
			createRole(newInstance, roleThing);
			IXInstance connection = createConnection(newInstance);
			ThingInstance tInstance = (ThingInstance)newInstance;
			boolean okPressed =
				tInstance.getThing().callPropertiesDialogWithFeedback(
					BaseGraphicComponent.SHOW_ALL_TABS,
					BaseGraphicComponent.SHOW_ALL_BUTTONS);
			if (okPressed)	{
				CompoundUndoAction undoAction = new CompoundUndoAction();
				if (connection instanceof ILinkInstance)	{
					undoAction.addAction(new UndoableAddLink((OpdProject)system, (LinkInstance)connection));
				}
				else if (connection instanceof IRelationInstance)	{
					if (connection instanceof GeneralRelationInstance)	{
						undoAction.addAction(new UndoableAddGeneralRelation((OpdProject)system, (GeneralRelationInstance)connection));
					}
					else if (connection instanceof FundamentalRelationInstance)	{
						undoAction.addAction(new UndoableAddFundamentalRelation((OpdProject)system, (FundamentalRelationInstance)connection));
					}									
				}
				if (roleThing instanceof IObjectEntry)	{
					undoAction.addAction(new UndoableAddObject((OpdProject)system, (ObjectInstance)newInstance));
				}
				else if (roleThing instanceof IProcessEntry)	{
					undoAction.addAction(new UndoableAddProcess((OpdProject)system, (ProcessInstance)newInstance));
				}
				Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent((OpdProject)system, undoAction));

			}
			else	{
				system.delete(connection);
				system.delete(newInstance);
			}
		}
		catch (Exception ex)	{
			ex.printStackTrace();	
		}
	}
	
	/**
	 * Creates the new Thing.
	 * @param roleThing
	 * @return
	 */
	protected IXThingInstance createThing(IThingEntry roleThing)	{
		long opdID = instance.getKey().getOpdId();
		int x = instance.getX() + instance.getWidth() + 30;
		int y = instance.getY() + instance.getHeight() + 30;
		IXThingInstance newInstance = null;
		IXThingInstance parent = (IXThingInstance)instance.getParentIThingInstance();
		if (roleThing instanceof IObjectEntry)	{
			if (parent != null)	{
				newInstance = system.addObject(renderName(roleThing), x, y, parent);
			}
			else	{
				newInstance = system.addObject(renderName(roleThing), x, y, opdID);		   
			}
		}
		else if (roleThing instanceof IProcessEntry){
			if (parent != null)	{
				newInstance = system.addProcess(renderName(roleThing), x, y, parent);
			}
			else	{
				newInstance = system.addProcess(renderName(roleThing), x, y, opdID);
			}
		}
		return newInstance;

	}
	
	/**
	 * Creates the thing name.
	 * @param roleThing
	 * @return
	 */
	protected String renderName(IThingEntry roleThing)	{
		//return "New "+ roleThing.getName();
		return "";
	}
	
	/**
	 * Creates the role of the new thing.
	 * @param newInstance
	 * @param roleThing
	 * @throws Exception
	 */
	protected void createRole(IXThingInstance newInstance, IThingEntry roleThing) throws Exception	{
		IXThingEntry logicalEntry = (IXThingEntry)newInstance.getIXEntry();
		Role role = null;
		try	{
			role = new Role(roleThing.getId(), metaLib);
		}
		catch (Exception ex)	{
			throw ex;
		}
		logicalEntry.addRole(role);		
	}

	/**
	 * Creates the connection to the new thing.
	 * @param newInstance
	 */
	protected IXInstance createConnection(IXThingInstance newInstance)	{
		int type = neighbor.getConnectionType();
		int direction = neighbor.getDirection();
		IXInstance connection = null;
		//Handling relations
		if (type < 300 && type >= 200)	{
			if (direction == Finder.DESTINATION_DIRECTION)	{
				connection = system.addRelation((IXConnectionEdgeInstance)instance, (IXConnectionEdgeInstance)newInstance, neighbor.getConnectionType());
			}
			else if (direction == Finder.SOURCE_DIRECTION)	{
				connection = system.addRelation((IXConnectionEdgeInstance)newInstance, (IXConnectionEdgeInstance)instance, neighbor.getConnectionType());
			}
		}
		//Handling links
		if (type >= 300 && type < 400)	{
			if (direction == Finder.DESTINATION_DIRECTION)	{
				connection = system.addLink((IXConnectionEdgeInstance)instance, (IXConnectionEdgeInstance)newInstance, neighbor.getConnectionType());
			}
			else if (direction == Finder.SOURCE_DIRECTION)	{
				connection = system.addLink((IXConnectionEdgeInstance)newInstance, (IXConnectionEdgeInstance)instance, neighbor.getConnectionType());
			}
		}
		return connection;
	}
}
