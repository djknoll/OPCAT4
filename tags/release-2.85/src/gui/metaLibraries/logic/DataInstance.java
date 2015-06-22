package gui.metaLibraries.logic;

import gui.opmEntities.OpmEntity;
import gui.opmEntities.OpmThing;
import gui.projectStructure.ThingInstance;
import gui.reqProject.Req;

public class DataInstance {
	
	private Object mainData ; 
	private Object additionalData ; 
	//private Color backgroundColor;
	//private Color textColor;	
	
	public DataInstance(Object inData) {
		this.mainData = inData ; 
	}
	
	public Object getDataInstance() {
		return this.mainData ; 
	}
	
	public long getId() {
		if(this.isOPMEntity() ) {
			return ((OpmThing) this.mainData).getId() ;
		} 
		if(this.mainData instanceof Req) {
			return ((Req) this.mainData).getId() ;
		}
		return 0 ; 
	}
	
	public String getName() {
		if(this.isOPMEntity()) {
			return ((OpmThing) this.mainData).getName() ;
		} 
		if(this.mainData instanceof Req) {
			return ((Req) this.mainData).getName();
		}
		return this.mainData.getClass().getCanonicalName() ; 
	}

	public boolean isOPMEntity() {
		return (this.mainData instanceof OpmEntity) || (this.mainData instanceof OpmThing) || (this.mainData instanceof ThingInstance) ; 
	}
	
	public String ToString() {
		return this.getId() + " " + this.getName() ; 
	}

	public Object getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(Object additionalData) {
		this.additionalData = additionalData;
	}

//	public Color getBackgroundColor() {
//		return backgroundColor;
//	}
//
//	public void setBackgroundColor(Color backgroundColor) {
//		this.backgroundColor = backgroundColor;
//	}
//
//	public Color getTextColor() {
//		return textColor;
//	}
//
//	public void setTextColor(Color textColor) {
//		this.textColor = textColor;
//	}
}
