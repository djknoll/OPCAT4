package gui.license;

import java.io.File;

public class Features {
	
	private Features() {
		
	}
	
	public static boolean hasReq() {
		File reqfile = new File("doreq") ; 
		return reqfile.exists() ; 
	}
	
	public static boolean hasCSV() {
		File reqfile = new File("docsv") ; 
		return reqfile.exists() ;		
	}
	
	public static boolean hasCSVThingsLoading() {
		File reqfile = new File("docsvloading") ; 
		return reqfile.exists() ;		
	}
}
