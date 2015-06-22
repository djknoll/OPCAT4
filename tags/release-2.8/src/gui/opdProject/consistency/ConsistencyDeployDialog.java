package gui.opdProject.consistency;


import gui.images.standard.StandardImages;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConsistencyDeployDialog extends JDialog{
	
	
	private JButton jCancelButton = null;
	private JButton jOKButton = null;
	private JButton jNextButton = null;
	private boolean cancelPressed  = false ; 
	private boolean nextPresed = false ; 
	private boolean okPressed = false ;
	private boolean autoPressed = false ;
	private boolean windowClosing = false; 
	private JPanel jPanel1 = null;
	private JButton jAllButton = null;
	private JLabel jLabel = null;
	private JPanel jPanel2 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public ConsistencyDeployDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(321,116));
        this.setContentPane(getJPanel2());
        this.setTitle("Consistency Deployment");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        	public void windowClosing(java.awt.event.WindowEvent e) {
        		setWindowClosing(true); // TODO Auto-generated Event stub windowClosing()
        	}
        });
			
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelJButton() {
		if (jCancelButton == null) {
			jCancelButton = new JButton();
			jCancelButton.setText("Cancel");			
			jCancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setCancelPressed(true); // TODO Auto-generated Event stub actionPerformed()
					setVisible(false) ; 
				}
			});
		}
		return jCancelButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOKJButton() {
		if (jOKButton == null) {
			jOKButton = new JButton();
			jOKButton.setText("OK");
			jOKButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOkPressed(true); // TODO Auto-generated Event stub actionPerformed()
					setVisible(false) ; 
				}
			});
		}
		return jOKButton;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNextButton() {
		if (jNextButton == null) {
			jNextButton = new JButton();
			jNextButton.setText("Next");
			jNextButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setNextPresed(true); // TODO Auto-generated Event stub actionPerformed()
					setVisible(false) ; 
				}
			});
		}
		return jNextButton;
	}

	public boolean isCancelPressed() {
		return cancelPressed;
	}

	public void setCancelPressed(boolean cancelPressed) {
		this.cancelPressed = cancelPressed;
	}

	public boolean isNextPresed() {
		return nextPresed;
	}

	public void setNextPresed(boolean nextPresed) {
		this.nextPresed = nextPresed;
	}

	public boolean isOkPressed() {
		return okPressed;
	}

	public void setOkPressed(boolean okPressed) {
		this.okPressed = okPressed;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.add(getOKJButton(), null);
			jPanel1.add(getNextButton(), null);
			jPanel1.add(getJAllButton(), null);
			jPanel1.add(getCancelJButton(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jAllButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJAllButton() {
		if (jAllButton == null) {
			jAllButton = new JButton();
			jAllButton.setText("Auto");
			jAllButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setAutoPressed(true); // TODO Auto-generated Event stub actionPerformed()
					setVisible(false) ; 
				}
			});
		}
		return jAllButton;
	}
	
	public void setTextIcon(Icon icon) {
		jLabel.setIcon(icon); 
	}
	
	public void setText(String text) {
		this.jLabel.setText(text) ; 
	}

	public boolean isAutoPressed() {
		return autoPressed;
	}

	public void setAutoPressed(boolean autoPressed) {
		this.autoPressed = autoPressed;
	}

	/**
	 * This method initializes jLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText("Place here checker label");
			jLabel.setIcon(StandardImages.DELETE);
		}
		return jLabel;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.add(getJLabel(), null);
			jPanel2.add(getJPanel1(), null);
		}
		return jPanel2;
	}

	public boolean isWindowClosing() {
		return windowClosing;
	}

	public void setWindowClosing(boolean windowClosing) {
		this.windowClosing = windowClosing;
	}

}  //  @jve:decl-index=0:visual-constraint="312,59"
