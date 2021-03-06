package gui.checkModule;
import exportedAPI.opcatAPIx.IXCheckResult;


public class CheckResult implements IXCheckResult
{
	private int result;
	private String message;

	public CheckResult(){}

	public CheckResult(int result)
	{
		this.result = result;
		this.message = "";
	}

	public CheckResult(int result, String message)
	{
		this.result = result;
		this.message = message;
	}

	public int getResult()
	{
		return this.result;
	}

	public String getMessage()
	{
		return this.message;
	}

	public void setMessage(String newMessage)
	{
		message = newMessage;
	}

	public void setResult(int newResult)
	{
		result = newResult;
	}

	public void appendMessage(String str)
	{
		message = message+str;
	}
}
