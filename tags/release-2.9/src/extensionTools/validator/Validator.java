package extensionTools.validator;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import exportedAPI.OpcatExtensionTool;
import exportedAPI.opcatAPI.ISystem;
import extensionTools.validator.algorithm.VAlgorithm;
import extensionTools.validator.gui.ValidatorPanel;
import gui.Opcat2;

public class Validator implements OpcatExtensionTool {

	//	The name of the validation tag
	public static final String VALIDATION_TAG_NAME = "Validation";

	private VAlgorithm algorithm = null;
	
	public Validator() {
	}

	public String getName() {
		return "Ontology Validator";
	}

	public JPanel getAboutBox() {
		return null;
	}

	public String getHelpURL() {
		return null;
	}

	public JPanel execute(ISystem opcatSystem, Opcat2 myOpcat2, JFrame parent) {
		if (this.algorithm == null)	{
			this.algorithm = new VAlgorithm();
		}
		return new ValidatorPanel(opcatSystem, myOpcat2, this.algorithm, parent);
	}

	public JPanel execute(ISystem opcatSystem) {
		if (this.algorithm == null)	{
			this.algorithm = new VAlgorithm();
		}
		return new ValidatorPanel(opcatSystem, null, this.algorithm, null);
	}

	public JDialog validationResults(ISystem opcatSystem, Opcat2 myOpcat2, JFrame parent) {
		//return new ValidatorPanel(opcatSystem);
		JDialog dialog = new JDialog();
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.getContentPane().add(
			new ValidatorPanel(opcatSystem, myOpcat2, this.algorithm, parent),
			BorderLayout.CENTER);
		return dialog;
	}

}
