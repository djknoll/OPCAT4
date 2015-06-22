package extensionTools.Testing;

import gui.opdProject.OpdProject;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


/**
 * A dialog for library reference import selection. The dialog enable users to manage their referene library.
 * @author Eran Toch
 * @version 1.0
 */

public class ScenriosDialog extends JDialog {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	JList importedList = new JList();
	JLabel jLabel1 = new JLabel();
	JButton edit = new JButton();
	JButton remove = new JButton();
	JButton ok = new JButton() ; 
	JButton cancel = new JButton() ; 
	JScrollPane importedScroll = new JScrollPane();
	protected OpdProject currentProject = null; 
	protected JFrame parentFrame = null;
	protected HashMap locScen  = new HashMap() ; 

	public ScenriosDialog(OpdProject _project, JFrame parent) throws Exception {
		super(parent) ;  
		try {
			parentFrame = parent;
			currentProject = _project ;
			locScen = (HashMap) _project.getScen().clone() ;			
			this.jbInit();
			InitList() ; 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	


	private void jbInit() throws Exception {		
		//this.setIconImage(MiscImages.LOGO_SMALL_ICON.getImage()) ;
		this.setTitle("OPCAT II - Manage Scenarios") ; 
		this.setSize(450, 300) ;
		this.setLocation( (parentFrame.getWidth() / 2) - 225, (parentFrame.getHeight() / 2 ) - 150 ) ; 
		this.setLayout(null);
		Rectangle firstButtonPosition = new Rectangle(313, 54, 100, 29);
		Rectangle secondButtonPosition = new Rectangle(313, 97, 100, 29);
		Rectangle thirdButtonPosition = new Rectangle(313, 140, 100, 28);
		this.importedList.setBorder(BorderFactory.createEtchedBorder());
		this.jLabel1.setPreferredSize(new Dimension(124, 15));
		this.jLabel1.setText("Manage Scenarios");
		this.jLabel1.setBounds(new Rectangle(24, 26, 350, 19));
		this.edit.setBounds(secondButtonPosition);
		this.edit.setPreferredSize(new Dimension(60, 23));
		this.edit.setText("Rename...");
		this.edit.addActionListener(new renameAction(this)) ; 
		//this.edit.setEnabled(false) ; 
		
		//this.edit.addActionListener();
		
		this.ok.setPreferredSize(new Dimension(60, 23));
		this.cancel.setPreferredSize(new Dimension(60, 23));
		this.ok.setText("OK");	
		this.cancel.setText("Cancel");
		this.cancel.setBounds(313, 220, 100, 29) ;
		this.ok.setBounds(200, 220, 100, 29) ; 
		this.cancel.addActionListener(new CancelAction(this) ) ;  
		
		this.remove.setBounds(thirdButtonPosition);
		this.remove.setMaximumSize(new Dimension(60, 23));
		this.remove.setMinimumSize(new Dimension(60, 23));
		this.remove.setPreferredSize(new Dimension(60, 23));
		this.remove.setMnemonic('0');
		this.remove.setText("Remove");
		this.remove.addActionListener(new RemoveAction( locScen , this)) ; 
		this.ok.addActionListener(new okAction(currentProject, this)) ; 
		//this.remove.addActionListener();
		//this.setOpaque(true);
		this.importedScroll.setBounds(new Rectangle(24, 54, 276, 157));
		this.add(this.jLabel1, null);
		this.edit.setBounds(firstButtonPosition);
		this.remove.setBounds(secondButtonPosition);
		this.add(this.edit, null);
		this.add(this.remove, null);
		this.add(this.ok, null) ;
		this.add(this.cancel, null) ; 
		this.add(this.importedScroll, null);
		this.importedScroll.getViewport().add(this.importedList, null);
	}
	
	protected void InitList() { 
		
		//importedList.remove(importedList.getSelectedIndex()) ;
		importedList.removeAll(); 
		importedList.setListData(locScen.keySet().toArray()) ; 
	}
	

}	//End of class
class CancelAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ScenriosDialog diag ; 
	
	public CancelAction(ScenriosDialog diag) { 
		this.diag = diag ; 
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		diag.setVisible(false); 
	}
}

class RemoveAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ScenriosDialog diag ; 
	HashMap scen = new HashMap() ; 
	
	public RemoveAction(HashMap scerios , ScenriosDialog diag) { 
		this.diag = diag ; 
		this.scen = scerios ; 
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (diag.importedList.getSelectedIndex() < 0 ) return ;
		diag.locScen.remove( (String) diag.importedList.getSelectedValue()) ;
		diag.InitList() ; 
		//diag.setVisible(false); 
	}
}

class okAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ScenriosDialog diag ;
	OpdProject project ; 
	
	public okAction(OpdProject project , ScenriosDialog diag) { 
		this.diag = diag ; 
		this.project = project ; 
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		project.setScen(diag.locScen) ; 
		diag.setVisible(false); 
	}
}

class renameAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ScenriosDialog diag ;
	
	public renameAction(ScenriosDialog diag) { 
		this.diag = diag ; 
	}

	public void actionPerformed(ActionEvent e) {
		HashMap renamed = (HashMap) diag.locScen.get((String) diag.importedList.getSelectedValue()) ;
		
		if (diag.importedList.getSelectedIndex() < 0 ) return ; 
		String name = (String) diag.importedList.getSelectedValue() ; 
		
		name = (String) JOptionPane.showInputDialog(diag,
				"Scenario Name", "OPCAT II", JOptionPane.INFORMATION_MESSAGE,
				null, null, name);

		if (name != null) {
			diag.locScen.remove((String) diag.importedList.getSelectedValue()) ; 
			diag.locScen.put(name, renamed) ; 
			diag.InitList(); 
		}
		

	}
}