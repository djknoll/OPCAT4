package gui;

import gui.images.misc.MiscImages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;


public class AboutDialog extends JDialog {
	private static String htmlText = "<html><br>"
			+ "<font color='navy' face='Tahoma' size=-1><b>Object-Process Methodology:</b><font>"
			+ "<font color='navy' face='Tahoma' size=-1><ul>"
			+ "<li>Dov Dori</li>"
			+ "</ul><font>"
			+

			"<font color='navy' face='Tahoma' size=-1><b>Design:</b><font>"
			+ "<font color='navy' face='Tahoma' size=-1><ul>"
			+ "<li>Dov Dori</li>"
			+ "<li>Iris Berger</li>"
			+ "<li>Arnon Sturm</li>"
			+ "<li>Eran Toch</li>"
			+ "</ul><font>"
			+ "</html>";

	JPanel jPanel1 = new JPanel();

	JTabbedPane jTabbedPane1 = new JTabbedPane();

	JPanel jPanel2 = new JPanel();

	JPanel jPanel3 = new JPanel();

	JButton closeButton = new JButton();

	JLabel jLabel13 = new JLabel(MiscImages.SPLASH);

	JPanel jPanel4 = new JPanel();

	JLabel jLabel16 = new JLabel(MiscImages.LOGO_BIG_ICON);

	JLabel jLabel17 = new JLabel();

	JLabel jLabel18 = new JLabel();
	JLabel licenseLabel = new JLabel();

	JScrollPane jScrollPane1 = new JScrollPane();

	Border border1;

	JEditorPane infoPane = new JEditorPane();

	public AboutDialog() {
		super(Opcat2.getFrame(), "About OPCAT", true);
		try {
			jbInit();
			this.setSize(382, 353);
			int pWidth = Opcat2.getFrame().getWidth();
			int pHeight = Opcat2.getFrame().getHeight();

			setLocation(Math.abs(pWidth / 2 - getWidth() / 2), Math.abs(pHeight
					/ 2 - getHeight() / 2));

			KeyListener kListener = new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						closeButton.doClick();
						return;
					}
				}
			};

			addKeyListener(kListener);

			this.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		border1 = BorderFactory.createCompoundBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(142, 142, 142)),
				BorderFactory.createEmptyBorder(0, 10, 0, 0));
		jPanel1.setLayout(null);
		jTabbedPane1.setBounds(new Rectangle(3, 2, 370, 265));
		closeButton.setText("Close");
		closeButton.setBounds(new Rectangle(270, 274, 102, 28));
		closeButton
				.addActionListener(new AboutDialog_closeButton_actionAdapter(
						this));
		jPanel3.setLayout(null);
		jLabel13.setBounds(new Rectangle(-2, 0, 368, 238));
		jPanel2.setLayout(null);
		this.setResizable(false);
		jPanel4.setLayout(null);
		jLabel16.setBounds(new Rectangle(21, 87, 65, 42));
		jLabel17.setFont(new java.awt.Font("Dialog", 1, 15));
		jLabel17.setForeground(new Color(0, 0, 128));
		jLabel17.setToolTipText("");
		jLabel17.setText("Version:       2.8");
		jLabel17.setBounds(new Rectangle(20, 39, 200, 32));
		jLabel18.setFont(new java.awt.Font("Dialog", 1, 15));
		jLabel18.setForeground(new Color(0, 0, 128));
		jLabel18.setText("Date:  CompileDate")  ;
		jLabel18.setBounds(new Rectangle(20, 70, 200, 32));
		
		licenseLabel.setFont(new java.awt.Font("Dialog", 1, 15));
		licenseLabel.setForeground(new Color(0, 0, 128));
		String licenseTitle = "";
		try	{
			licenseTitle = Opcat2.getLicense().getLicenseDescription();
		}
		catch (NullPointerException npe)	{}
		licenseLabel.setText(licenseTitle);
		licenseLabel.setBounds(new Rectangle(20, 101, 350, 32));
		
		String copyright = "OPCAT, Inc., All rights reserved (c) 2005";
		JLabel copyrightLabel = new JLabel();
		copyrightLabel.setFont(new java.awt.Font("Dialog", 1, 15));
		copyrightLabel.setForeground(new Color(0, 0, 128));
		copyrightLabel.setText(copyright);
		copyrightLabel.setBounds(new Rectangle(20, 130, 350, 32));
		
		
		jScrollPane1.setBorder(border1);
		jScrollPane1.setBounds(new Rectangle(14, 9, 342, 215));
		infoPane.setContentType("text/html");
		infoPane.setText(htmlText);
		infoPane.setCaretPosition(0);
		infoPane.setBackground(jPanel3.getBackground());
		infoPane.setEditable(false);
		this.getContentPane().add(jPanel1, BorderLayout.CENTER);
		jPanel1.add(jTabbedPane1, null);
		jTabbedPane1.add(jPanel2, "About");
		jPanel2.add(jLabel13, null);
		jTabbedPane1.add(jPanel3, "Info");
		jPanel3.add(jScrollPane1, null);
		jScrollPane1.getViewport().add(infoPane, null);
		jTabbedPane1.add(jPanel4, "Version");
		jPanel4.add(jLabel17, null);
		jPanel4.add(jLabel18, null);
		//jPanel4.add(jLabel16, null);
		jPanel4.add(licenseLabel, null);
		jPanel4.add(copyrightLabel, null);
		jPanel1.add(closeButton, null);
	}

	void closeButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}
}

class AboutDialog_closeButton_actionAdapter implements
		java.awt.event.ActionListener {
	AboutDialog adaptee;

	AboutDialog_closeButton_actionAdapter(AboutDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.closeButton_actionPerformed(e);
	}
}