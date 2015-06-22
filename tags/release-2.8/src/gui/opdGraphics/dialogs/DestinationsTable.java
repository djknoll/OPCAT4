package gui.opdGraphics.dialogs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *  <p>Class implementing table for view of destinantions of
 *  Fundamental relations in <code>RelationPropertiesDialog</code></p>
 *  @see <a href = "RelationPropertiesDilaog.html"><code>RelationPropertiesDialog</code></a>
 */

public class DestinationsTable extends JPanel {
	private JTable table;
	MyTableModel myModel;
	private JScrollPane scrollPane;
	private int rows;
//	private int colomns;
	private boolean[] isObject;
	private JComboBox comboBox;
    private boolean isCardinalityEditable;


	public DestinationsTable(int myRows, boolean isCardinalityEditable) {
		super();
		rows = myRows;
        this.isCardinalityEditable = isCardinalityEditable;
		isObject = new boolean[myRows];
		myModel = new MyTableModel();
		myModel.initTable();

		table = new JTable(myModel);
		table.setPreferredScrollableViewportSize(new Dimension(350, 80));

		//Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		//Fiddle with the Cardinality column's cell editors/renderers.
		setUpCardinalityColumn(table.getColumnModel().getColumn(1));

		//Fiddle with the Custom column's cell editors/renderers.
		setUpCustomColumn(table.getColumnModel().getColumn(2));

		//Add the scroll pane to this panel.
		add(scrollPane);

		Action emptyAction = new AbstractAction(){
						public void actionPerformed(ActionEvent e){}};
		table.registerKeyboardAction(emptyAction,
			KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);

	}

    public JTable getTable()
    {
        return table;
    }

	public void setIsObject(boolean isObj, int row)
	{
		isObject[row] = isObj;
	}

	public void updateTable()
	{
		if (table.isEditing())
		{
			(table.getCellEditor(table.getEditingRow() , table.getEditingColumn())).stopCellEditing();
		}
	}

	public void setValueAt(Object value, int row, int col)
	{
		table.setValueAt(value, row, col);
	}

	public Object getValueAt(int row, int col)
	{
		return table.getValueAt(row, col);
	}


	public void setUpCardinalityColumn(TableColumn cardinalityColumn) {
		//Set up the editor for the cardinality cells.
		comboBox = new JComboBox();
		comboBox.addActionListener(new ComboBoxAdditionalListener());
		comboBox.addItem("1");
		comboBox.addItem("many");
		comboBox.addItem("custom");

		cardinalityColumn.setCellEditor(new DefaultCellEditor(comboBox));

		//Set up tool tips for the cardinality cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box (if destination is Object)");
		cardinalityColumn.setCellRenderer(renderer);
	}

	public void setUpCustomColumn(TableColumn customColumn)
    {
        //Set up the editor for the custom cells.
        JTextField textField = new JTextField();
        DefaultCellEditor ce = new DefaultCellEditor(textField);
        ce.setClickCountToStart(1);
        customColumn.setCellEditor(ce);

        //Set up tool tips for the cardinality cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click to edit (if cardinality is \"custom\")");
        customColumn.setCellRenderer(renderer);
	 }


	 public boolean hasFocus()
	 {
		return (table.hasFocus() || comboBox.hasFocus());
	 }


	 class ComboBoxAdditionalListener implements ActionListener
	 {
		public void actionPerformed(ActionEvent ae)
		{
			//System.err.println(ae.getSource());
			int si = ((JComboBox)ae.getSource()).getSelectedIndex();
			if(si == 0 || si == 1)
			{
				table.transferFocus();
			}
			else if(si == 2)
			{
                DefaultCellEditor de = (DefaultCellEditor)table.getCellEditor(table.getSelectedRow(), 2);
                table.editCellAt(table.getSelectedRow(), 2);
                JTextField tf = (JTextField)de.getComponent();
                tf.requestFocus();
			}
		}
	 }


	class MyTableModel extends AbstractTableModel {
		final String[] columnNames = {"Name",
									  "Participation Constraint",
									  "Value"
									  };
		Object[][] data = new Object[rows][3];

		public void initTable()
		{
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < 3; j++)
					data[i][j] = "";
			}

		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/*
		 * JTable uses this method to determine the default renderer/
		 * editor for each cell.  If we didn't implement this method,
		 * then the last column would contain text ("true"/"false"),
		 * rather than a check box.
		 */
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			if (!isCardinalityEditable || col < 1) {
				return false;
			}
			else if (col == 1) {
				return isObject[row];
			}
			else if ((col == 2) && ("custom").equals(getValueAt(row , 1))){
				return true;
			}
			else return false;
		}

		public void setValueAt(Object value, int row, int col) {

			if (col == 2) {
				data[row][col] = value.toString();
			}
			else if (col == 1)
			{
				if( ("1").equals(value))
				{
					data[row][col+1] = "1";
				}
				if( ("many").equals(value))
				{
					data[row][col+1] = "m";
				}
				if( ("custom").equals(value))
				{
					data[row][col+1] = "";
				}

				data[row][col] = value;
				fireTableCellUpdated(row, col+1);
			}
			else {
				data[row][col] = value;
			}
			fireTableCellUpdated(row, col);
		}
	}

//	ActionListener cardinalityListener = new AbstractAction("Cardinality Listener"){
//		public void actionPerformed(ActionEvent e){
//			if (((String.valueOf(destinationCardinality.getSelectedItem())).compareTo("custom")) == 0)
//			{
//				customDestinationCardinality.setEditable(true);
//                customDestinationCardinality.requestFocus();
//			}
//			else
//			{
//				customDestinationCardinality.setEditable(false);
//				customDestinationCardinality.setText("");
//			}
//			return;
//		}
//	};


}