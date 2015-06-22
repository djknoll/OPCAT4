/**
 * 
 */
package gui.opmEntities;

public class Predecessor{
	public static final int FF = 0;
	public static final int FS = 1;
	public static final int SS = 2;
	public static final int SF = 3;
	private long processID;
	private static final String[] DEPENDENCY_NAMES = {"FF","FS", "SS", "SF"};
	private int type;
	
	public long getProcessID() {
		return processID;
	}
	public void setProcessID(long id) {
		processID = id;
	}
	public int getType() {
		return type;
	}
	
	public String getType2String(){
		return this.DEPENDENCY_NAMES[type];
	}
	public static String[] getTypes(){
		return DEPENDENCY_NAMES;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public void setType(String type){
		for(int i=0; i<SF+1; i++){
			if(type.equals(this.DEPENDENCY_NAMES[i])){
				this.type = i;
				return;
			}	
		}
	}
	public Predecessor(long ID, int type){
		this.processID = ID;
		this.type = type;
	}
	public Predecessor(){
		processID = -1;
		type = 1;
	}
}