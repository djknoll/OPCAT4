package gui.checkModule;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

class ConfigurationPanel extends JPanel {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	private CheckOptionsButton coButton_OO = new CheckOptionsButton();

	private CheckOptionsButton coButton_OP = new CheckOptionsButton();

	private CheckOptionsButton coButton_OS = new CheckOptionsButton();

	private CheckOptionsButton coButton_PO = new CheckOptionsButton();

	private CheckOptionsButton coButton_PP = new CheckOptionsButton();

	private CheckOptionsButton coButton_SP = new CheckOptionsButton();

	private CheckOptionsButton coButton_SO = new CheckOptionsButton();

	private CheckOptionsButton coButton_PS = new CheckOptionsButton();

	private CheckOptionsButton coButton_SS = new CheckOptionsButton();

	private JLabel jLabel4 = new JLabel();

	private JLabel jLabel8 = new JLabel();

	private JLabel jLabel9 = new JLabel();

	private JLabel jLabel11 = new JLabel();

	private JLabel jLabel12 = new JLabel();

	private JLabel jLabel13 = new JLabel();

	private JLabel jLabel16 = new JLabel();

	private JSeparator jSeparator1 = new JSeparator();

	private JSeparator jSeparator2 = new JSeparator();

	private JSeparator jSeparator3 = new JSeparator();

	private JSeparator jSeparator4 = new JSeparator();

	private JSeparator jSeparator5 = new JSeparator();

	private JSeparator jSeparator6 = new JSeparator();

	ConfigurationPanel() {
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		// this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setBorder(BorderFactory.createEtchedBorder());

		this.coButton_OP.setBounds(new Rectangle(101, 117, 101, 58));
		this.coButton_PP.setBounds(new Rectangle(202, 117, 101, 58));
		this.coButton_SP.setBounds(new Rectangle(303, 117, 101, 58));
		this.coButton_OO.setBounds(new Rectangle(101, 58, 101, 58));
		this.coButton_PO.setBounds(new Rectangle(202, 58, 101, 58));
		this.coButton_SO.setBounds(new Rectangle(303, 58, 101, 58));
		this.coButton_OS.setBounds(new Rectangle(101, 174, 101, 58));
		this.coButton_PS.setBounds(new Rectangle(202, 174, 101, 58));
		this.coButton_SS.setBounds(new Rectangle(303, 174, 101, 58));

		this.jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel4.setText("Object");
		this.jLabel4.setBounds(new Rectangle(0, 58, 101, 58));

		this.jLabel8.setBounds(new Rectangle(0, 117, 101, 58));
		this.jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel8.setText("Process");

		this.jLabel9.setBounds(new Rectangle(0, 175, 101, 58));
		this.jLabel9.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel9.setText("State");

		this.jLabel11.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel11.setText("State");
		this.jLabel11.setBounds(new Rectangle(303, 0, 101, 58));

		this.jLabel12.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel12.setText("Process");
		this.jLabel12.setBounds(new Rectangle(202, 0, 101, 58));

		this.jLabel13.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel13.setText("Object");
		this.jLabel13.setBounds(new Rectangle(101, 0, 101, 58));

		this.jLabel16.setBounds(new Rectangle(0, 0, 101, 58));

		this.jSeparator1.setOrientation(SwingConstants.VERTICAL);
		this.jSeparator1.setRequestFocusEnabled(false);
		this.jSeparator1.setBounds(new Rectangle(101, 0, 15, 235));
		this.jSeparator2.setBounds(new Rectangle(202, 0, 15, 235));
		this.jSeparator2.setRequestFocusEnabled(false);
		this.jSeparator2.setOrientation(SwingConstants.VERTICAL);
		this.jSeparator3.setBounds(new Rectangle(303, 0, 15, 235));
		this.jSeparator3.setRequestFocusEnabled(false);
		this.jSeparator3.setOrientation(SwingConstants.VERTICAL);
		this.jSeparator4.setBounds(new Rectangle(0, 58, 404, 15));
		this.jSeparator5.setBounds(new Rectangle(0, 116, 404, 15));
		this.jSeparator6.setBounds(new Rectangle(0, 174, 404, 15));

		p.add(this.jLabel4, null);
		p.add(this.coButton_OO, null);
		p.add(this.coButton_PO, null);
		p.add(this.coButton_SO, null);
		p.add(this.coButton_SP, null);
		p.add(this.coButton_PP, null);
		p.add(this.coButton_SS, null);
		p.add(this.jSeparator2, null);
		p.add(this.jSeparator3, null);
		p.add(this.jSeparator1, null);
		p.add(this.coButton_PS, null);
		p.add(this.jLabel11, null);
		p.add(this.jLabel12, null);
		p.add(this.jLabel13, null);
		p.add(this.jSeparator5, null);
		p.add(this.jLabel16, null);
		p.add(this.jSeparator4, null);
		p.add(this.jLabel8, null);
		p.add(this.coButton_OP, null);
		p.add(this.jSeparator6, null);
		p.add(this.jLabel9, null);
		p.add(this.coButton_OS, null);
		p.setPreferredSize(new Dimension(404, 232));
		p.setMaximumSize(new Dimension(404, 232));
		p.setMinimumSize(new Dimension(404, 232));
		// this.add(Box.createVerticalStrut(110));
		// this.add(p/*, BorderLayout.CENTER*/);
		// this.add(Box.createVerticalStrut(110));
		this.add(p, BorderLayout.CENTER);
		this.add(Box.createVerticalStrut(110), BorderLayout.NORTH);
		this.add(Box.createVerticalStrut(110), BorderLayout.SOUTH);
		this.add(Box.createHorizontalStrut(100), BorderLayout.WEST);
		this.add(Box.createHorizontalStrut(100), BorderLayout.EAST);

	}

	// public static void main(String[] args){
	// JFrame w = new JFrame();
	// ConfigurationPanel cp = new ConfigurationPanel();
	// cp.setPreferredSize(new Dimension(404,232));
	// w.getContentPane().add(cp);
	// w.pack();
	// w.show();
	// }
}