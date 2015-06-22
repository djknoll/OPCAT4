package extensionTools.opltoopd;

import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * Contains the following buttons for viewing, editting and adding
 * infromation and providing methods for controlling their state:
 *  Add - Add a new sentence. Disabled by default.
 *  Update - Update the sentene. Enabled by default.
 *  Clear - Clear the sentence. Disabled by default.
 *  Exit - Exit from the application. Disabled by default.
 */
class UpdateCancelButtonsPanel extends JPanel
{

  static final public int NUM_OF_BUTTONS = 4;
  private JButton addButton = new JButton("Add");
  private JButton updateButton = new JButton("Update");
  private JButton cancelButton = new JButton("Clear");
  private JButton exitButton = new JButton("Exit");

  public UpdateCancelButtonsPanel()
  {
    super();
    add(addButton);
    add(updateButton);
    add(cancelButton);
    add(exitButton);
  }


  /** Returns the add button. */

  public JButton getAddButton()
  {
    return addButton;
  }

  /** Returns the update button. */
  public JButton getUpdateButton()
  {
    return updateButton;
  }


  /** Returns the clear button. */
  public JButton getCancelButton()
  {
    return cancelButton;
  }

  /** Returns the exit button. */
  public JButton getExitButton()
  {
    return exitButton;
  }


    /**
     * Enables/Disables the input buttons.
     * @param add true enables the Add button, false disables it.
     * @param update true enables the Update button, false disables it.
     * @param cancel true enables the Cancel button, false disables it.
     * @param exit true enables the Exit button, false disables it.
     */
  public void enable( boolean add, boolean update, boolean cancel, boolean exit)
  {
      addButton.setEnabled(add);
 //     updateButton.setEnabled(update);
      updateButton.setEnabled(false);
      cancelButton.setEnabled(cancel);
      exitButton.setEnabled(exit);
  }

  private void setMode()
  {
    enable (true,false, true, true);
  }

}

