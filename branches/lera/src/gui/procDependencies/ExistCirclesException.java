package gui.procDependencies;

public class ExistCirclesException extends Exception{
	private String invalidProcsMsg;

	public ExistCirclesException(String invalidProcsMsg) {
		this.invalidProcsMsg = invalidProcsMsg;
	}	
	public String getMsg(){return invalidProcsMsg;}
}