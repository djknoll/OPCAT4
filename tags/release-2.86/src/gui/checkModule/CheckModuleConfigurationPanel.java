package gui.checkModule;

import gui.images.opm.OPMImages;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class CheckModuleConfigurationPanel extends JFrame {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	JTabbedPane mainTabbedPane = new JTabbedPane(SwingConstants.LEFT);

	// JTabbedPane linksTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
	// JTabbedPane relationsTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
	public CheckModuleConfigurationPanel() {
		this.mainTabbedPane.addTab("Agent Link", OPMImages.AGENT_LINK,
				new ConfigurationPanel());
		// mainTabbedPane.addTab("Exeption Link", new
		// ImageIcon(Opcat2.javaImagesPath + "exceptionLink.gif"),new
		// ConfigurationPanel());
		this.mainTabbedPane.addTab("Exeption Link", OPMImages.EXCEPTION_LINK,
				new ConfigurationPanel());
		this.mainTabbedPane.addTab("Instrument Link", OPMImages.INSTRUMENT_LINK,
				new ConfigurationPanel());
		this.mainTabbedPane.addTab("Result/Consumption Link", OPMImages.RESULT_LINK,
				new ConfigurationPanel());
		this.mainTabbedPane.addTab("Effect Link", OPMImages.EFFECT_LINK,
				new ConfigurationPanel());
		this.mainTabbedPane.addTab("Instrument Event Link",
				OPMImages.INSTRUMENT_EVENT_LINK, new ConfigurationPanel());
		this.mainTabbedPane.addTab("Consumption Event Link",
				OPMImages.CONSUMPTION_EVENT_LINK, new ConfigurationPanel());
		this.mainTabbedPane.addTab("Condition Link", OPMImages.CONDITION_LINK,
				new ConfigurationPanel());
		this.mainTabbedPane.addTab("Exception Link", OPMImages.EXCEPTION_LINK,
				new ConfigurationPanel());
		this.mainTabbedPane.addTab("Invocation Link", OPMImages.INVOCATION_LINK,
				new ConfigurationPanel());

		this.mainTabbedPane.addTab("Aggregation-Particulation",
				OPMImages.AGGREGATION, new ConfigurationPanel());
		this.mainTabbedPane.addTab("Featuring-Characterization",
				OPMImages.EXHIBITION, new ConfigurationPanel());
		this.mainTabbedPane.addTab("Generalization-Specialization",
				OPMImages.GENERALIZATION, new ConfigurationPanel());
		this.mainTabbedPane.addTab("Classification-Instantiation",
				OPMImages.INSTANTIATION, new ConfigurationPanel());
		this.mainTabbedPane.addTab("Unidirectional Relation",
				OPMImages.UNI_DIRECTIONAL, new ConfigurationPanel());
		this.mainTabbedPane.addTab("Bidirectional Relation",
				OPMImages.BI_DIRECTIONAL, new ConfigurationPanel());

		// mainTabbedPane.add("Links", linksTabbedPane);
		// mainTabbedPane.add("Relations", relationsTabbedPane);
		this.getContentPane().add(this.mainTabbedPane);

	}

	// public static void main(String[] args){
	// // JFrame w = new JFrame();
	// // ConfigurationPanel cp = new ConfigurationPanel();
	// // cp.setPreferredSize(new Dimension(404,232));
	// // w.getContentPane().add(cp);
	// CheckModuleConfigurationPanel w = new CheckModuleConfigurationPanel();
	// w.pack();
	// w.show();
	// }
}
