package gui.projectStructure;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IObjectInstance;
import exportedAPI.opcatAPIx.IXObjectInstance;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmState;

import java.awt.Component;
import java.awt.Container;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * <p>
 * This class represents instance of OPM object. In this class we additionally
 * hold information about all states instance belonging to this object's
 * instance .
 * 
 * @version 2.0
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * 
 */

public class ObjectInstance extends ThingInstance implements IObjectInstance,
		IXObjectInstance {
	Hashtable stateInstances;

	/**
	 * Creates ThingInstance with specified myKey, which holds pThing that
	 * represents this ObjectInstance graphically.
	 * 
	 * @param key
	 *            OpdKey - key of graphical instance of this entity.
	 * @param pThing
	 *            OpdThing that represents this ObjectInstance graphically.
	 */
	public ObjectInstance(ObjectEntry myEntry, OpdKey myKey,
			Container container, OpdProject project, boolean bringStates) {
		super(myKey, project);
		this.myEntry = myEntry;
		this.stateInstances = new Hashtable();

		if (myEntry.getLogicalEntity() instanceof OpmObject) {
			this.myConnectionEdge = new OpdObject(myEntry, this.myProject,
					myKey.getOpdId(), myKey.getEntityInOpdId());
		}

		if (container instanceof OpdThing) {
			this.setParent((OpdThing) container);
		}

		this.myConnectionEdge.addMouseListener(this.myConnectionEdge);
		this.myConnectionEdge.addMouseMotionListener(this.myConnectionEdge);
		// container.add(myConnectionEdge);
		// container.repaint();

		if (bringStates) {

			for (Enumeration e = myEntry.getStateEnum(); e.hasMoreElements();) {
				OpmState lState = (OpmState) e.nextElement();
				Opd tmpOpd = null;
				if (this.myConnectionEdge.getOpdId() == OpdProject.CLIPBOARD_ID) {
					tmpOpd = this.myProject.getClipBoard();
				} else {
					tmpOpd = this.myProject.getComponentsStructure().getOpd(
							this.myConnectionEdge.getOpdId());
				}
				long stateInOpdId = tmpOpd._getFreeEntityInOpdEntry();
				OpdKey stateKey = new OpdKey(tmpOpd.getOpdId(), stateInOpdId);
				StateEntry sEntry = (StateEntry) (this.myProject
						.getComponentsStructure().getEntry(lState.getId()));
				StateInstance tmpStateInstance = new StateInstance(sEntry,
						stateKey, (OpdObject) this.myConnectionEdge,
						this.myProject);
				sEntry.addInstance(stateKey, tmpStateInstance);
			}

		}

		this.graphicalRepresentation = myConnectionEdge;

	}

	public boolean isRelated() {
		if (this.myConnectionEdge.isRelated()) {
			return true;
		}

		Component components[] = this.myConnectionEdge.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof OpdBaseComponent) {
				if (((OpdBaseComponent) components[i]).isRelated()) {
					return true;
				}
			}
		}

		return false;

	}

	public Enumeration getStateInstances() {
		Vector instances = new Vector();
		MainStructure ms = this.myProject.getComponentsStructure();

		Component components[] = this.myConnectionEdge.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof OpdState) {
				OpdState tempState = (OpdState) components[i];
				Entry stEntry = ms.getEntry(tempState.getEntity().getId());
				Instance stInstance = stEntry.getInstance(new OpdKey(tempState
						.getOpdId(), tempState.getEntityInOpdId()));
				instances.add(stInstance);
			}
		}

		return instances.elements();

	}

	public void copyPropsFrom(ObjectInstance origin) {
		super.copyPropsFrom(origin);
		this.setStatesAutoArranged(origin.isStatesAutoArranged());

	}

	public void update() {
		super.update();
	}

	public boolean isStatesAutoArranged() {
		return ((OpdObject) this.myConnectionEdge).isStatesAutoarrange();
	}

	public void setStatesAutoArranged(boolean isArranged) {
		((OpdObject) this.myConnectionEdge).setStatesAutoarrange(isArranged);
	}

	public boolean isStateInstaceExists(String stateName) {

		Iterator iter = Collections.list(this.getStateInstances()).iterator();
		while (iter.hasNext()) {
			StateInstance state = (StateInstance) iter.next();
			if (state.getEntry().getName().equalsIgnoreCase(stateName)) {
				return true;
			}
		}

		return false;
	}

	public StateInstance getState(String stateName) {

		Iterator iter = Collections.list(this.getStateInstances()).iterator();
		while (iter.hasNext()) {
			StateInstance state = (StateInstance) iter.next();
			if (state.getEntry().getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}

		return null;
	}
}