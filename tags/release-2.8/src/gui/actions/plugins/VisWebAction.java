package gui.actions.plugins;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;


import gui.controls.FileControl;
import gui.controls.GuiControl;
import extensionTools.visWeb.*;
;/**
 * 
 * @author Eran Toch
 */
public class VisWebAction extends AbstractAction {
    protected FileControl fileControl = FileControl.getInstance();
    protected GuiControl myGuiControl = GuiControl.getInstance();
	
    /**
     * 
     */
    public VisWebAction() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public VisWebAction(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     */
    public VisWebAction(String arg0, Icon arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        if (!fileControl.isProjectOpened()) {
			JOptionPane.showMessageDialog(
			        myGuiControl.getFrame(),
				"No system is opened",
				"Message",
				JOptionPane.ERROR_MESSAGE);
			return;
		}

		//            UMLmain tmp = new UMLmain(currentProject);
		//            tmp.getDialog().show();
		 (new OwlFrame(fileControl.getCurrentProject())).setVisible(true);

    }

}
