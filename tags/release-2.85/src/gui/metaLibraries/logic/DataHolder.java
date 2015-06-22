package gui.metaLibraries.logic;

import java.util.Collection;
import java.util.Collections;

import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;
import gui.reqProject.ReqProject;

class DataHolder {
	
	private int type ; 
	
	private Object holder ; 
	
	public DataHolder(Object data, int dataType) {
		this.holder = data ; 
		this.type = dataType ; 
	}
	
	public Object getData() {
		if (this.type == Types.MetaLibrary) {
			return  this.holder  ;
		} 
		if (this.type == Types.RequirementsData) {
			return this.holder ;
		} 
		return null ; 
	}
	
	public Collection getDataComponents() {
		
		if(this.type == Types.MetaLibrary) {
			MainStructure oStructure = ((OpdProject) this.holder).getComponentsStructure();
			return Collections.list(oStructure.getElements());
		}
		
		if(this.type == Types.RequirementsData) {
			ReqProject reqs = (ReqProject) this.holder;
			return reqs.values();
		}		
		
		return null ; 
	}	
	
	public String getGlobalID() {
		if (this.type == Types.MetaLibrary) {
			return ((OpdProject) this.holder ).getGlobalID() ;
		}
		if (this.type == Types.RequirementsData) {
			return ((ReqProject) this.holder ).getGlobalID() ;
		}
		return null ; 
	}
	
	public String getName() {
		if (this.type == Types.MetaLibrary) {
			return ((OpdProject) this.holder ).getName() ;
		} 
		if (this.type == Types.RequirementsData) {
			return ((ReqProject) this.holder ).getName() ;
		}
		return  null ; 
	}

	/**
	 * TODO replace this later with more globel one. 
	 * @return
	 */
	public ISystemStructure getISystemStructure() {
		if (this.type == Types.MetaLibrary) {
			return ((OpdProject) this.holder ).getISystemStructure() ;
		} 
		return null ; 
	}
	
	public ISystem getISystem() {
		if (this.type == Types.MetaLibrary) {
			return (ISystem) this.holder ;
		} 
		return null ; 
	}
}
