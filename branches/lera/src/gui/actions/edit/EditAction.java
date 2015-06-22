package gui.actions.edit;

import java.awt.event.ActionEvent;
import gui.controls.EditControl;
import gui.controls.GuiControl;

import javax.swing.Icon;
import javax.swing.AbstractAction;
import org.w3c.dom.events.EventException;

/**
 * A super class for all editing-related actions. Allows access to the 
 * EditControl class. 
 * @author Eran Toch
 */
public abstract class EditAction extends AbstractAction {
	

    /**
     * A singleton class handling edit Actions operation. 
     */
    protected EditControl edit = EditControl.getInstance();
    protected GuiControl gui = GuiControl.getInstance();
    private String locName ; 

    
    /**
	 * A general constructor. 
	 * @param name	The name of the action (e.g. "Save")
	 * @param icon	The icon of the action (presented by menus, toolbars etc)
	 */
	public EditAction(String name, Icon icon)	{
		super(name, icon);
		this.locName = name ; 
	}
	
	public EditAction()	{
	    super();
	}
	
	

    /** 
     * this function is executed every time an Edit action is preforemed. it checks and does general things common to all 
     * Edit actions. (like checkin if aproject is loded before preforming the action 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) throws EventException{
        if (!this.edit.isProjectOpened()) {
    		short code = 1 ; 
    		throw new EventException(code, "No system is opened");         	
        }
        
        if (!this.locName.equalsIgnoreCase("Paste")) {
        	if (this.edit.IsCutPending() ) {
        		short code = 2 ; 
        		throw new EventException(code, EditControl.CUT_PENDING_MSG); 
        	}
        }      
    }             
    

}
