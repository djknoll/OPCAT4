package gui.metaLibraries.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import gui.metaDataProject.MetaDataProject;
import gui.opdProject.OpdProject;
import gui.projectStructure.Entry;
import gui.projectStructure.MainStructure;

class DataHolder {
	
	private int type ; 
	
	private Object holder ; 
	
	public DataHolder(Object data, int dataType) {
		this.holder = data ; 
		this.type = dataType ; 
	}
	
	public Object getData() {
		if (this.type == LibraryTypes.MetaLibrary) {
			return  this.holder  ;
		} 
		if (this.type == LibraryTypes.MetaData) {
			return this.holder ;
		} 
		return null ; 
	}
	
	public Collection getDataComponents() {
		
		if(this.type == LibraryTypes.MetaLibrary) {
			MainStructure oStructure = ((OpdProject) this.holder).getComponentsStructure();
			ArrayList ret = new ArrayList() ; 
			Iterator iter = Collections.list(oStructure.getElements()).iterator() ;
			while(iter.hasNext()) {
				Entry ent = (Entry) iter.next() ; 
				ret.add(ent.getLogicalEntity()) ; 
			}
			return ret ; 
		}
		
		if(this.type == LibraryTypes.MetaData) {
			MetaDataProject reqs = (MetaDataProject) this.holder;
			return reqs.values();
		}		
		
		return null ; 
	}	
	
	public String getGlobalID() {
		if (this.type == LibraryTypes.MetaLibrary) {
			return ((OpdProject) this.holder ).getGlobalID() ;
		}
		if (this.type == LibraryTypes.MetaData) {
			return ((MetaDataProject) this.holder ).getGlobalID() ;
		}
		return null ; 
	}
	
	public String getName() {
		if (this.type == LibraryTypes.MetaLibrary) {
			return ((OpdProject) this.holder ).getName() ;
		} 
		if (this.type == LibraryTypes.MetaData) {
			return ((MetaDataProject) this.holder ).getType() ;
		}
		return  null ; 
	}

	/**
	 * TODO replace this later with more globel one. 
	 * @return
	 */
	public ISystemStructure getISystemStructure() {
		if (this.type == LibraryTypes.MetaLibrary) {
			return ((OpdProject) this.holder ).getISystemStructure() ;
		} 
		return null ; 
	}
	
	public ISystem getISystem() {
		if (this.type == LibraryTypes.MetaLibrary) {
			return (ISystem) this.holder ;
		} 
		return null ; 
	}
}
