package gui.actions.help;

import java.awt.event.ActionEvent;

import gui.controls.GuiControl;
import gui.util.BrowserLauncher;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Opens the Help contents menu, through an external browser.  
 * @author Eran Toch
 */
public class HelpContentsAction extends AbstractAction {
    
    /**
     * A singleton class handling GUI in Opcat. 
     */
	protected GuiControl myGuiControl = GuiControl.getInstance();
	
    private final static String fileSeparator = System.getProperty("file.separator");
	/**
	 * A general constructor, initiates a name and an icon. 
	 * @param name	The name of the action (e.g. "Save")
	 * @param icon	The icon of the action (presented by menus, toolbars etc)
	 */
	public HelpContentsAction(String name, Icon icon)	{
		super(name, icon);
	}
	
    public void actionPerformed(ActionEvent e) {

        Thread runner = new Thread() {
            public void run() {
                String url = "help" + fileSeparator + "index_frames.html";
                try {
                    BrowserLauncher.openURL(url);
                } catch (Exception e) {
                    try {
                        url = "C:"+ fileSeparator +"Program Files"
                        + fileSeparator +"Opcat" + fileSeparator +
                        "help" + fileSeparator + "index_frames.html";
                        BrowserLauncher.openURL(url);
                    } catch (Exception ex) {
                        JOptionPane
                                .showMessageDialog(myGuiControl.getFrame(), "Help files were not found", "Help error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        runner.start();

    }
	

}
