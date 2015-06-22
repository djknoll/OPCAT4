package extensionTools.etAnimation;

import javax.swing.JPanel;

import exportedAPI.OpcatExtensionToolX;
import exportedAPI.opcatAPIx.IXSystem;

public class AnimationToolExample implements OpcatExtensionToolX
{
	public AnimationToolExample()
	{
	}
	public String getName()
	{
		return "ET Animation Example";
	}
	public JPanel getAboutBox()
	{
		return new AnimationToolExampleAbout();
	}
	public String getHelpURL()
	{
		return null;
	}
	public JPanel execute(IXSystem opcatSystem)
	{
		return new AnimationGUIPanel(opcatSystem);
	}

}

