package gui.metaDataProject;

public class MetaSourceType {
	
	/**
	 * load meta data from opz file
	 */
	public static int OPCAT_FILE_OPZ = 1;
	
	/**
	 * load from csv files 
	 */
	public static int TEXT_FILE_CSV = 2 ; 
	
	/**
	 * load from a Project data structure
	 */
	public static int OPCAT_PROJECT = 5 ;
	
	public static int UNDEFINED = 6 ;
	
	public static int OPCAT_OPD = 7 ;	
	
	int type = UNDEFINED ; 
	
	public MetaSourceType (int type) {
		this.type = type ; 
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
