package extensionTools.validator.gui;

import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.ISystem;
import extensionTools.validator.ValidationException;
import extensionTools.validator.Validator;
import extensionTools.validator.algorithm.Offence;
import extensionTools.validator.algorithm.VAlgorithm;
import gui.Opcat2;
import gui.util.opcatGrid.GridPanel;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class ValidatorPanel extends GridPanel {

    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    /**
         * 
         */

    private Opcat2 o2;

    private JFrame parentFrame = null;

    public ValidatorPanel(ISystem opcatSystem, Opcat2 myOpcat2,
	    VAlgorithm algorithm, ArrayList<String> columnNames, JFrame parent) {
	super(columnNames);

	this.o2 = myOpcat2;
	this.parentFrame = parent;
	Vector warnings = null;
	// Validating the model
	try {
	    warnings = algorithm.validate(opcatSystem);
	} catch (ValidationException vex) {
	    JOptionPane.showMessageDialog(this.parentFrame,
		    "An Error occured while validating the model",
		    "Validation Error", JOptionPane.ERROR_MESSAGE);
	}
	/**
         * add the data from warnings
         */
	this.setEntryTag();
	this.setTabName(Validator.VALIDATION_TAG_NAME);
	for (int i = 0; i < warnings.size(); i++) {
	    Object[] row = new Object[4];
	    Offence offence = (Offence) ((Vector) warnings.get(i)).toArray()[1];
	    row[0] = Opcat2.getCurrentProject().getMetaManager()
		    .getMetaLibrary(offence.getRole().getMetaLibID()).getName();
	    if (offence.getOriginalThing() instanceof IProcessEntry) {
		row[1] = "Process";
	    } else {
		row[1] = "Object";
	    }
	    row[2] = offence.getOriginalThingName();
	    row[3] = ((Vector) warnings.get(i)).toArray()[2];

	    Object[] rowTag = new Object[2];
	    rowTag[0] = offence.getOriginalThing();
	    rowTag[1] = " ";

	    getGrid().addRow(row, rowTag);
	}

	JTable table = this.getGrid();
	TableColumn columnIcon = table.getColumnModel().getColumn(0);
	columnIcon.setMaxWidth(300);

	JButton revalidateButton = new JButton();
	revalidateButton.setText("Revalidate");
	getButtonPane().add(revalidateButton);

	JButton propertiesButton = new JButton();
	propertiesButton.setText("Properties");
	getButtonPane().add(propertiesButton);

	revalidateButton.addActionListener(new RevalidateAdapter(this));
	propertiesButton.addActionListener(new PropertiesAdapter(this));
    }

    /**
         * Revaldiates the panel
         * 
         * @param e
         */
    public void revalidate(ActionEvent e) {
	if (this.o2 != null) {
	    this.o2.clearValidationWindow();
	    this.o2.validate();
	}
    }

    /**
         * Opens up properties window.
         * 
         * @param e
         */
    public void properties(ActionEvent e) {
	ConfigurationWindow window = new ConfigurationWindow(
		"Validation Properties");
	window.showDialog();
    }
}

class RevalidateAdapter implements java.awt.event.ActionListener {
    ValidatorPanel adaptee;

    RevalidateAdapter(ValidatorPanel adaptee) {
	this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
	this.adaptee.revalidate(e);
    }
}

class PropertiesAdapter implements java.awt.event.ActionListener {
    ValidatorPanel adaptee;

    PropertiesAdapter(ValidatorPanel adaptee) {
	this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
	this.adaptee.properties(e);
    }
}
