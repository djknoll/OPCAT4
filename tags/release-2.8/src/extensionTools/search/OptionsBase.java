package extensionTools.search;

/**
 * the base class of all options for any search algorithems. 
 * Any search implemantation must implement it's own options 
 * which extends the OptionBase class. 
 * 
 * @see OptionsInString 
 * @author raanan
 *
 */
public abstract class OptionsBase {
	boolean inDescription = false ; 
	boolean inName = false ; 
	boolean forStates = false ; 
	boolean forObjects = false ; 
	boolean forProcess = false ; 
	String searchText = "" ;
	
	public OptionsBase () {
		inDescription = false ; 
		inName = false ; 
		forStates = false ; 
		forObjects = false ; 
		forProcess = false ; 
		searchText = "" ;
	}
	
	public boolean isForProcess() {
		return forProcess;
	}
	public boolean isForStates() {
		return forStates;
	}
	public boolean isInDescription() {
		return inDescription;
	}
	public boolean isInName() {
		return inName;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setForObjects(boolean forObjects) {
		this.forObjects = forObjects;
	}
	public boolean isForObjects() {
		return forObjects;
	}
	public void setForProcess(boolean forProcess) {
		this.forProcess = forProcess;
	}
	public void setForStates(boolean forStates) {
		this.forStates = forStates;
	}
	public void setInDescription(boolean inDescription) {
		this.inDescription = inDescription;
	}
	public void setInName(boolean inName) {
		this.inName = inName;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	} 
	
	
	
	
}
