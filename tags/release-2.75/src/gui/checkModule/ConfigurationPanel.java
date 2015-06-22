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

class ConfigurationPanel extends JPanel
{
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

	ConfigurationPanel()
	{
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception
	{
//		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setBorder(BorderFactory.createEtchedBorder());

		coButton_OP.setBounds(new Rectangle(101, 117, 101, 58));
		coButton_PP.setBounds(new Rectangle(202, 117, 101, 58));
		coButton_SP.setBounds(new Rectangle(303, 117, 101, 58));
		coButton_OO.setBounds(new Rectangle(101, 58, 101, 58));
		coButton_PO.setBounds(new Rectangle(202, 58, 101, 58));
		coButton_SO.setBounds(new Rectangle(303, 58, 101, 58));
		coButton_OS.setBounds(new Rectangle(101, 174, 101, 58));
		coButton_PS.setBounds(new Rectangle(202, 174, 101, 58));
		coButton_SS.setBounds(new Rectangle(303, 174, 101, 58));

		jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel4.setText("Object");
		jLabel4.setBounds(new Rectangle(0, 58, 101, 58));

		jLabel8.setBounds(new Rectangle(0, 117, 101, 58));
		jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel8.setText("Process");

		jLabel9.setBounds(new Rectangle(0, 175, 101, 58));
		jLabel9.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel9.setText("State");

		jLabel11.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel11.setText("State");
		jLabel11.setBounds(new Rectangle(303, 0, 101, 58));

		jLabel12.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel12.setText("Process");
		jLabel12.setBounds(new Rectangle(202, 0, 101, 58));

		jLabel13.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel13.setText("Object");
		jLabel13.setBounds(new Rectangle(101, 0, 101, 58));

		jLabel16.setBounds(new Rectangle(0, 0, 101, 58));

		jSeparator1.setOrientation(JSeparator.VERTICAL);
		jSeparator1.setRequestFocusEnabled(false);
		jSeparator1.setBounds(new Rectangle(101, 0, 15, 235));
		jSeparator2.setBounds(new Rectangle(202, 0, 15, 235));
		jSeparator2.setRequestFocusEnabled(false);
		jSeparator2.setOrientation(JSeparator.VERTICAL);
		jSeparator3.setBounds(new Rectangle(303, 0, 15, 235));
		jSeparator3.setRequestFocusEnabled(false);
		jSeparator3.setOrientation(JSeparator.VERTICAL);
		jSeparator4.setBounds(new Rectangle(0, 58, 404, 15));
		jSeparator5.setBounds(new Rectangle(0, 116, 404, 15));
		jSeparator6.setBounds(new Rectangle(0, 174, 404, 15));

		p.add(jLabel4, null);
		p.add(coButton_OO, null);
		p.add(coButton_PO, null);
		p.add(coButton_SO, null);
		p.add(coButton_SP, null);
		p.add(coButton_PP, null);
		p.add(coButton_SS, null);
		p.add(jSeparator2, null);
		p.add(jSeparator3, null);
		p.add(jSeparator1, null);
		p.add(coButton_PS, null);
		p.add(jLabel11, null);
		p.add(jLabel12, null);
		p.add(jLabel13, null);
		p.add(jSeparator5, null);
		p.add(jLabel16, null);
		p.add(jSeparator4, null);
		p.add(jLabel8, null);
		p.add(coButton_OP, null);
		p.add(jSeparator6, null);
		p.add(jLabel9, null);
		p.add(coButton_OS, null);
		p.setPreferredSize(new Dimension(404,232));
		p.setMaximumSize(new Dimension(404,232));
		p.setMinimumSize(new Dimension(404,232));
//		this.add(Box.createVerticalStrut(110));
//		this.add(p/*, BorderLayout.CENTER*/);
//		this.add(Box.createVerticalStrut(110));
		this.add(p, BorderLayout.CENTER);
		this.add(Box.createVerticalStrut(110), BorderLayout.NORTH);
		this.add(Box.createVerticalStrut(110), BorderLayout.SOUTH);
		this.add(Box.createHorizontalStrut(100), BorderLayout.WEST);
		this.add(Box.createHorizontalStrut(100), BorderLayout.EAST);

	}

//	public static void main(String[] args){
//		JFrame w = new JFrame();
//		ConfigurationPanel cp = new ConfigurationPanel();
//		cp.setPreferredSize(new Dimension(404,232));
//		w.getContentPane().add(cp);
//		w.pack();
//		w.show();
//	}
}