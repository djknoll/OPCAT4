package extensionTools.validator.gui;

import exportedAPI.opcatAPI.ISystem;
import extensionTools.validator.ValidationException;
import extensionTools.validator.algorithm.VAlgorithm;
import gui.Opcat2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class ValidatorPanel extends JPanel {
	
	private Opcat2 o2;
	private JFrame parentFrame = null;
	public ValidatorPanel() {
		super();
	}

	public ValidatorPanel(ISystem opcatSystem, Opcat2 myOpcat2, VAlgorithm algorithm, JFrame parent) {
		o2 = myOpcat2;
		parentFrame = parent;
		Vector warnings = null;
		//Validating the model
		try	{
			warnings = algorithm.validate((ISystem) opcatSystem);
		}
		catch (ValidationException vex)	{
			JOptionPane.showMessageDialog(
					parentFrame,
					"An Error occured while validating the model",
					"Validation Error",
					JOptionPane.ERROR_MESSAGE);
		}
		//String[] columnNames = {"Type", "Thing", "Warning"};
		Vector columnNames = new Vector();
		columnNames.add(" ");
		columnNames.add("Thing");
		columnNames.add("Warning");
		JTable table = new JTable(warnings, columnNames);
		TableColumn columnPicture = table.getColumnModel().getColumn(0);
		columnPicture.setCellRenderer(new PictureRenderer());
		TableColumn columnIcon = table.getColumnModel().getColumn(0);
		columnIcon.setMaxWidth(25);
		TableColumn columnThing = table.getColumnModel().getColumn(1);
		columnThing.setMaxWidth(400);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

		//Button declaretions
		JButton clearButton = new JButton();
		clearButton.setMinimumSize(new Dimension(71, 20));
		clearButton.setText("Close");
		JButton revalidateButton = new JButton();
		revalidateButton.setMinimumSize(new Dimension(71, 20));
		revalidateButton.setText("Revalidate");
		
		JButton propertiesButton = new JButton();
		propertiesButton.setMinimumSize(new Dimension(71, 20));
		propertiesButton.setText("Properties");
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		//buttonPane.add(Box.createVerticalGlue());
		buttonPane.add(clearButton);
		buttonPane.add(Box.createRigidArea(new Dimension(0, 3)));
		buttonPane.add(revalidateButton);
		buttonPane.add(Box.createRigidArea(new Dimension(0, 3)));
		buttonPane.add(propertiesButton);
		
		add(buttonPane, BorderLayout.LINE_END);
		clearButton.addActionListener(new ClearAdapter(this));
		revalidateButton.addActionListener(new RevalidateAdapter(this));
		propertiesButton.addActionListener(new PropertiesAdapter(this));
	}

	/**
	 * Clears the panel.
	 * @param e
	 */
	public void clearPanel(ActionEvent e)	{
		if (o2 != null)	{
			o2.clearValidationWindow();
		}
	}

	/**
	 * Revaldiates the panel
	 * @param e
	 */
	public void revalidate(ActionEvent e)	{
		if (o2 != null)	{
			o2.clearValidationWindow();
			o2.validate();
		}
	}
	
	/**
	 * Opens up properties window.
	 * @param e
	 */
	public void properties(ActionEvent e)	{
		ConfigurationWindow window = new ConfigurationWindow("Validation Properties");
		window.showDialog();
	}
}

class ClearAdapter implements java.awt.event.ActionListener {
	ValidatorPanel adaptee;

	ClearAdapter(ValidatorPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	public void actionPerformed(ActionEvent e) {
		adaptee.clearPanel(e);
	}
}

class RevalidateAdapter implements java.awt.event.ActionListener {
	ValidatorPanel adaptee;

	RevalidateAdapter(ValidatorPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	public void actionPerformed(ActionEvent e) {
		adaptee.revalidate(e);
	}
}

class PropertiesAdapter implements java.awt.event.ActionListener {
	ValidatorPanel adaptee;

	PropertiesAdapter(ValidatorPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	public void actionPerformed(ActionEvent e) {
		adaptee.properties(e);
	}
}
