package gui.metaLibraries.dialogs;

import gui.metaLibraries.logic.Role;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractListModel;

/**
 * The class represent a ListModel for OPM Roles, used in JList objects.
 */
public class RolesListModel extends AbstractListModel {

	/**
	 * Data structure for the roles.
	 */
	private Vector roles;

	/**
	 * Creating the roles structure.
	 */
	public RolesListModel() {
		roles = new Vector();
	}

	/**
	 * Building a RolesListModel using an existing collection.
	 */
	public RolesListModel(Vector roles) {
		if (roles == null) {
			roles = new Vector();
		}
		else {
			this.roles = roles;
			fireContentsChanged(this, 0, roles.size());
		}
	}

	/**
	 * Returns a Role element according to an index.
	 */
	public Object getElementAt(int index){
		Iterator it = roles.iterator();
		int i = 0;
		while(it.hasNext()){
			if(i == index){
				return it.next();
			}
			it.next();
			i++;
		}
		return null;
	}

	public int getSize(){
		if (roles == null) {
			return 0;
		}
		return roles.size();
	}

	/**
	 * Return the roles as an ArrayList model.
	 */
	public Vector getRoles(){
		return roles;
	}


	/**
	 * Sets the roles.
	 */
	public void setRoles(Vector roles){
		this.roles = roles;
		fireContentsChanged(this, 0, roles.size());
	}

	public boolean addRole(Role role){

		if (!isRoleInList(role)){
			this.roles.add(role);
			fireContentsChanged(this, 0, roles.size());
			return true;
		}
		return false;
	}

	public boolean removeRole(Role role){
		Iterator it = roles.iterator();
		Role temp;
		while(it.hasNext()){
			temp = (Role)(it.next());
			if(temp.equals(role)){
				roles.remove(temp);
				fireContentsChanged(this,0,roles.size());
				return true;
			}
		}
		return false;
	}

	private boolean isRoleInList(Role role){
		if (roles == null) {
			return false;
		}
		Iterator it = roles.iterator();
		while(it.hasNext()){
			if(((Role)(it.next())).equals(role)){
				return true;
			}
		}
		return false;
	}
}