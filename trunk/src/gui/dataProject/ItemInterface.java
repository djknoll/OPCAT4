package gui.dataProject;

import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdProject.OpdProject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

public interface ItemInterface {
	
	public long getId() ; 
	public String getName();
	public void setAdditionalData(Object entry);
	public Object getAdditionalData() ; 
	public Vector getAllData() ; 
	public void setAllData(Vector data);
	public void setName(String name) ; 
	public String getExtID() ; 
	public Color getColor(MetaLibrary meta) ;
}
