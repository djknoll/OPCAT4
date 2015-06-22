package gui.checkModule;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

class CheckOptionsButton extends JPanel implements ActionListener {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	JCheckBox allow = new JCheckBox("Allow connection", false);

	JButton advanced = new JButton("Advanced");

	CheckOptionsButton() {
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setMinimumSize(new Dimension(110, 50));
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(110, 50));
		this.setLayout(null);
		this.advanced.setBounds(new Rectangle(66, 19, 31, 19));
		this.advanced.setToolTipText("Configure");
		this.allow.setToolTipText("Allow/Disallow this type of connection");
		this.allow.setText("Allow");
		this.allow.setBounds(new Rectangle(6, 17, 57, 22));
		this.add(this.allow);
		this.add(this.advanced);
		if (!this.allow.isSelected()) {
			this.advanced.setEnabled(false);
		}
		this.allow.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.allow)) {
			if (this.allow.isSelected()) {
				this.advanced.setEnabled(true);
			} else {
				this.advanced.setEnabled(false);
			}
		}
	}

	// public static void main(String[] args){
	// JFrame w = new JFrame();
	// w.getContentPane().add(new CheckOptionsButton());
	// w.show();
	// }

}