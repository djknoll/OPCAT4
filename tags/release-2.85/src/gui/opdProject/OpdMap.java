package gui.opdProject;

import gui.Opcat2;

import java.util.ArrayList;

public class OpdMap {
	
	private static ArrayList opds = new ArrayList() ;
	private static int currentPlace = -1 ; 
	
	public OpdMap () {
		
	}
	
	public static Opd getUpOpd () {
		
		return null ; 		
	}
	
	public static Opd getDownOpd() {
		
		return null ; 
	}
	
	public static Opd getBackOpd () {		
		if (currentPlace > 0 ) currentPlace-- ; 
		
		if (currentPlace >= 0 ) {
			return Opcat2.getCurrentProject().getOpdByID( ((Long) opds.get(currentPlace)).longValue()) ;
		} else {
				return null ; 
		}		  
	}
	
	public static Opd getForwordOpd() {		
		if(currentPlace <  opds.size() -1 ) currentPlace++ ; 
		if (currentPlace > opds.size() -1 ) {
			return Opcat2.getCurrentProject().getOpdByID( ((Long) opds.get(currentPlace)).longValue()) ;
		} else { 
			return Opcat2.getCurrentProject().getCurrentOpd() ; 
		}		
	}
	
	public static void UpdateOpdMap(Opd opd) {		
		opds.add(new Long(opd.getOpdId())) ;
		currentPlace++ ; 		
	}
	
	

}
