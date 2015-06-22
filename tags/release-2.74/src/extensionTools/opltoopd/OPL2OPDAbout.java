package extensionTools.opltoopd;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Returns the authors of this application
 */
public class OPL2OPDAbout extends JPanel
{
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();

	public OPL2OPDAbout() {
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		jLabel1.setText("<html><font color='navy' face='Tahoma' size='4'><b>Extension Tool Example</b><font></html>");
		jLabel1.setBounds(new Rectangle(92, 12, 217, 27));
		this.setLayout(null);
		jLabel2.setText("<html><font color='navy' face='Tahoma' size='2'><b>Authors:</b><font>" +
						"<font color='navy' face='Tahoma' size='2'><ul>" +
							"<li>Vekselman Ilana</li>" +
							"<li>Yanovsky Maya</li>" +
                                                        "<li>Katz Elena</li>" +
                                                        "<li>Katz Avraham</li>" +
						"</ul><font></html>");
		jLabel2.setBounds(new Rectangle(23, 46, 356, 64));
		jLabel4.setText("<html><font color='navy' face='Tahoma' size='2'><b>Guidance:</b><font>" +
						"<font color='navy' face='Tahoma' size='2'><ul>" +
							"<li>Berger Iris</li>" +
							"<li>Sturm Arnon</li>" +
						"</ul><font></html>");
		jLabel4.setBounds(new Rectangle(23, 122, 352, 61));

		add(jLabel1, null);
		add(jLabel2, null);
		add(jLabel3, null);
		add(jLabel4, null);
		this.setPreferredSize(new Dimension(405, 200));
	}

}
