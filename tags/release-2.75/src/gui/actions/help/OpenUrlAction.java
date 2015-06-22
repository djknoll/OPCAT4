package gui.actions.help;

import java.awt.event.ActionEvent;

import gui.controls.GuiControl;
import gui.util.BrowserLauncher;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Opens the Help contents menu, through an external browser.
 * 
 * @author Eran Toch
 */
public class OpenUrlAction extends AbstractAction {

    /**
     * A singleton class handling GUI in Opcat.
     */
    protected GuiControl myGuiControl = GuiControl.getInstance();

    protected String url = "";

    private final static String fileSeparator = System
            .getProperty("file.separator");

    /**
     * A general constructor, initiates a name and an icon.
     * 
     * @param name
     *            The name of the action (e.g. "Save")
     * @param icon
     *            The icon of the action (presented by menus, toolbars etc)
     */
    public OpenUrlAction(String name, Icon icon, String url) {
        super(name, icon);
        this.url = url;
    }

    public void actionPerformed(ActionEvent e) {

        Thread runner = new Thread() {
            public void run() {
                try {
                    BrowserLauncher.openURL(url);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(myGuiControl.getFrame(), "Could not find the URL", "Browser error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        runner.start();

    }

}