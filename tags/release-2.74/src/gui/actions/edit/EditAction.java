package gui.actions.edit;

import java.awt.event.ActionEvent;

import exportedAPI.OpcatConstants;
import gui.actions.controls.EditControl;
import gui.actions.controls.GuiControl;
import gui.opdProject.StateMachine;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import javax.swing.AbstractAction;

/**
 * A super class for all editing-related actions. Allows access to the 
 * EditControl class. 
 * @author Eran Toch
 */
public abstract class EditAction extends AbstractAction {
    /**
     * A singleton class handling file operation. 
     */
    protected EditControl edit = EditControl.getInstance();

    protected GuiControl gui = GuiControl.getInstance();
    
    /**
	 * A general constructor. 
	 * @param name	The name of the action (e.g. "Save")
	 * @param icon	The icon of the action (presented by menus, toolbars etc)
	 */
	public EditAction(String name, Icon icon)	{
		super(name, icon);
	}
	
	public EditAction()	{
	    super();
	}
	
	

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        if (!edit.isProjectOpened()) {
            JOptionPane
                    .showMessageDialog(gui.getFrame(), "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }  
    
}
