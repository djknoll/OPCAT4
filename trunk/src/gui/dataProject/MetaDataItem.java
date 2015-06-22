package gui.dataProject;

import gui.metaLibraries.logic.MetaLibrary;
import java.awt.Color;

public class MetaDataItem extends  DataAbstractItem implements ItemInterface{


	private Color color;


	public MetaDataItem(String extId, String projectGlobalID) {
		super("", extId) ; 
	}



	public Color getColor() {
		return color;
	}
	
	public Color getColor(MetaLibrary meta) {
		return getColor();
	}	

	public void setColor(Color color) {
		this.color = color;
	}

	
}
