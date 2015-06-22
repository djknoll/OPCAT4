package exportedAPI.opcatAPIx;
import javax.swing.*;

public interface IXFileControl{
	public String getSystemFileName();
	public void saveSystem();
	public IXSystem openSystem(String fileName, JProgressBar pBar)
		throws gui.opx.LoadException;
	public boolean closeSystem(boolean redrawOPCATFrame);
	public boolean isProjectOpened();
}
