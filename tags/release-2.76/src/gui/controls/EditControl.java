package gui.controls;

import javax.swing.undo.CannotRedoException;

import gui.Opcat2;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.util.OpcatLogger;

/**
 * Provides methods for controling editing actions.
 * 
 * @author Eran Toch
 */
public class EditControl {
	
	public final static int CUT = 300;
	public final static String CUT_PENDING_MSG = "Finish the Pending Cut operation first" ; 
		

    /**
     * The single instance of this class.
     */
    private static EditControl instance = null;

    private Opcat2 myOpcat = null;

    private GuiControl myGuiControl = GuiControl.getInstance();


    /**
     * A private constructor, exists only to defeat instantiation.
     *
     */
    private EditControl() {
    }

    /**
     * Returns the singleton instance of the class. Initiaits it if it does not
     * exist already, or creates a new one if it does.
     * @return
     */
    public static EditControl getInstance() {
        if (instance == null) {
            instance = new EditControl();
        }
        return instance;
    }

    /**
     * Sets a reference to Opcat. This method must be called before other methods
     * could be called. 
     * @param opcat
     */
    public void setOpcat(Opcat2 opcat) {
        this.myOpcat = opcat;
    }

    public OpdProject getCurrentProject() {
        if (myOpcat == null) {
            OpcatLogger.logError("EditControl was not initiated by Opcat2");
        }
        return myOpcat.getCurrentProject();
    }

    /**
     * Sets the CHECKED image on the <code>setEnableDraggingButton</code> menu
     * item.
     * 
     * @param doEnable
     *            If <code>true</code>, the option would be displayed as
     *            <code>CHECKED</code>. Otherwise, it would be displayed as
     *            <code>EMPTY</code>.
     */
    public void enableDragging(boolean doEnable) {
        //Merge Remarked by Eran Toch
        //myOpcat.enableDragging(doEnable);
    }

    /**
     * 
     * @return Whether a project is currently open. Uses
     *         {@link	Opcat2#isProjectWasOpened()}in order to retrieve an
     *         answer.
     */
    public boolean isProjectOpened() {
    	FileControl file = FileControl.getInstance() ; 
        return file.isProjectOpened();
    }
    
    /**
     * Updates a logical change in the diagram refreshes the OPl and the 
     * repository. 
     */
    public void updateLogicalStructureChange()	{
        Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
    }
    
    /**
     * Performs an undo operation - calls <code>Opcat2.getUndoManager().undo()</code>,
     * handle errors, toggles the undo/redo buttons and updates the structural 
     * change. 
     */
    public void undo()	{
        try {
            Opcat2.getUndoManager().undo();
        } catch (CannotRedoException cre) {
            OpcatLogger.logError(cre);
        }
        myOpcat.toggleUndoButtons(true);
        updateLogicalStructureChange();
    }
    
    /**
     * Performs a redo operation. Similarly to the undo operation, it calls
     * <code>Opcat2.getUndoManager().redo()</code>, toggles the undo/redo buttons and updates the structural 
     * change. 
     *
     */
    public void redo()	{
        try {
            Opcat2.getUndoManager().redo();
        } catch (CannotRedoException cre) {
            OpcatLogger.logError(cre);
        }
        myOpcat.toggleUndoButtons(false);
        updateLogicalStructureChange();
    }
    
    /**
     * a Function to warn the user about a Pending CUT OP. 
     * @return
     */
    public boolean IsCutPending() {
    	return  (StateMachine.getCurrentState() == StateMachine.EDIT_CUT ) ; 
    }
    /**
     * a Function to warn about a pending edit OP. to warn the user before doing 
     * something which will kill the Edit OP
     * @return
     */
    public boolean IsEditPending() {
    	return  (StateMachine.getCurrentState() == StateMachine.EDIT_CUT ) ; 
    }    

}