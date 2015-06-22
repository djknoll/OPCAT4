package extensionTools.opltoopd;


import javax.swing.JDialog;

import exportedAPI.opcatAPIx.IXSystem;


/**
 * Represent the main class of this application
 */
public class Opl2Opd {

  /**
   * @param system the running IXSystem
   */

  public Opl2Opd(IXSystem system)
  {
      opcatSystem = system;
  }

  /**
   * Get the main dialog of this application
   * @return dialog
   */
  public JDialog getDialog()
  {
      isChanged = false;
      comboControl = new OPLComboBoxController(opcatSystem,isChanged);
      JDialog dialog = comboControl.getComboDialog();
      return dialog;
  }
/**
 * Return true if the systen had been changed
 * @return true if the systen had been changed
 */

  public boolean isChanged()
  {
      return comboControl.getIsChange();
  }
  OPLComboBoxController comboControl;
  IXSystem opcatSystem;
  boolean isChanged;
}
/*OPLComboBoxController comboControl = new OPLComboBoxController(opcatSystem);
          JPanel panel = comboControl.getCombo();
          return panel;*/