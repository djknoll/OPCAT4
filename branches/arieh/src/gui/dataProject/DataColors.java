package gui.dataProject;

import java.awt.Color;
import java.util.ArrayList;

public class DataColors {

	//private static ArrayList colors = null ;

	boolean used = false;
	
	boolean lazyInit = true ; 

	public DataColors() {
		used = false;
	}


	public Color getColor(Color color) {

	    if(lazyInit) {
		//colors = MetaLoadColors.getInstance().load();
		lazyInit = !lazyInit ; 
	    }
		return color;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
