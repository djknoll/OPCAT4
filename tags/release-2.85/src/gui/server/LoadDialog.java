package gui.server;

import gui.Opcat2;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

/**
 * This Class is Load Project Dialog.
 */

public class LoadDialog extends JDialog {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	private LoadTable loadTable;

	private JButton loadButton, cancelButton;

	private LoadParameters param;

	/**
	 * Constructs a LoadDialog with specified parameters
	 */

	public LoadDialog(LoadParameters parameters) {
		super(Opcat2.getFrame(), "Load System", true);

		this.param = parameters;
		this.loadTable = new LoadTable(this.param.length());

		for (int i = 0; i < this.param.length(); i++) {
			this.loadTable.setValueAt(new ProjectNameId(this.param.getProjectName(i), i),
					i, 0);
			this.loadTable.setValueAt(this.param.getProjectCreator(i), i, 1);
			this.loadTable.setValueAt(this.param.getCreationDate(i), i, 2);
		}
		this.loadButton = new JButton(" Load ");
		this.cancelButton = new JButton("Cancel");
		this.loadButton.addActionListener(new loadListener());
		this.cancelButton.addActionListener(new cancelListener());

		Container cont = this.getContentPane();
		cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));

		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		// table
		JPanel tb = new JPanel();
		tb.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 10), BorderFactory
				.createEtchedBorder()));
		// tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
		tb.add(this.loadTable);
		jp.add(tb);

		// buttons
		JPanel buttons = new JPanel();
		// buttons.setLayout(new GridLayout(1, 4));
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(Box.createHorizontalStrut(300));
		buttons.add(this.loadButton);
		buttons.add(Box.createHorizontalStrut(20));
		buttons.add(this.cancelButton);
		// buttons.add(Box.createHorizontalStrut(20));
		jp.add(buttons);
		jp.add(Box.createVerticalStrut(10));

		cont.add(jp);

		// Escape & Enter listener
		KeyListener escapeAndEnterListener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					(LoadDialog.this).dispose();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						LoadDialog.this.param.setReturnedPId(LoadDialog.this.loadTable.getSelectedRow());
						(LoadDialog.this).dispose();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(LoadDialog.this,
								"Choose the project to load");
						return;
					}
				}
			}
		};

		this.addKeyListener(escapeAndEnterListener);

		this.pack();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		int pWidth = Opcat2.getFrame().getWidth();
		int pHeight = Opcat2.getFrame().getHeight();

		this.setLocation(Math.abs(pWidth / 2 - this.getWidth() / 2), Math.abs(pHeight / 2
				- this.getHeight() / 2));

		this.setResizable(false);
		this.setVisible(true);
	}

	class loadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				ProjectNameId pnid;
				pnid = (ProjectNameId) LoadDialog.this.loadTable.getValueAt(LoadDialog.this.loadTable
						.getSelectedRow(), 0);
				LoadDialog.this.param.setReturnedPId(pnid.getId());
				(LoadDialog.this).dispose();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(LoadDialog.this,
						"Choose the project to load");
				return;
			}
		}
	}

	class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			(LoadDialog.this).dispose();
		}
	}

	// inner class LoadTable for table of projects
	public class LoadTable extends JPanel {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		 

		private JTable table;

		LoadTableModel loadModel;

		private JScrollPane scrollPane;

		private int rows;

		// private int colomns;

		public LoadTable(int myRows) {
			super();
			this.rows = myRows;
			this.loadModel = new LoadTableModel();
			this.loadModel.initTable();

			TableSorter sorter = new TableSorter();
			sorter.setModel(this.loadModel);

			this.table = new JTable(sorter);
			// Use a scrollbar, in case there are many columns.
			// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			// Install a mouse listener in the TableHeader as the sorter UI.
			sorter.addMouseListenerToHeaderInTable(this.table);

			// JScrollPane scrollpane = new JScrollPane(table);

			// table = new JTable(loadModel);

			Action emptyAction = new AbstractAction() {
				 

				/**
				 * 
				 */
				private static final long serialVersionUID = -4745845283156885288L;

				public void actionPerformed(ActionEvent e) {
				}
			};
			this.table.registerKeyboardAction(emptyAction, KeyStroke.getKeyStroke(
					KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
			this.table.setPreferredScrollableViewportSize(new Dimension(450, 150));
			this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			// Create the scroll pane and add the table to it.
			this.scrollPane = new JScrollPane(this.table);

			// Set up the editor for the cells.
			JTextField textField = new JTextField();
			this.table.setCellEditor(new DefaultCellEditor(textField));
			// //Set up the renderer for the cells.
			// DefaultTableCellRenderer renderer = new
			// DefaultTableCellRenderer();
			// table.setCellRenderer(renderer);

			// Add the scroll pane to this panel.
			this.add(this.scrollPane);
		}

		public void setValueAt(Object value, int row, int col) {
			this.table.setValueAt(value, row, col);
		}

		public Object getValueAt(int row, int col) {
			return this.table.getValueAt(row, col);
		}

		public int getSelectedRow() {
			return this.table.getSelectedRow();
		}

		class LoadTableModel extends AbstractTableModel {
			 

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * 
			 */
			 

			final String[] columnNames = { "System Name", "Author", "Created" };

			Object[][] data = new Object[LoadTable.this.rows][3];

			public void initTable() {
				for (int i = 0; i < LoadTable.this.rows; i++) {
					for (int j = 0; j < 3; j++) {
						this.data[i][j] = "";
					}
				}
			}

			public int getColumnCount() {
				return this.columnNames.length;
			}

			public int getRowCount() {
				return this.data.length;
			}

			public String getColumnName(int col) {
				return this.columnNames[col];
			}

			public Object getValueAt(int row, int col) {
				return this.data[row][col];
			}

			/*
			 * JTable uses this method to determine the default renderer/ editor
			 * for each cell. If we didn't implement this method, then the last
			 * column would contain text ("true"/"false"), rather than a check
			 * box.
			 */
			public Class getColumnClass(int c) {
				return this.getValueAt(0, c).getClass();
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public void setValueAt(Object value, int row, int col) {
				this.data[row][col] = value;
				this.fireTableCellUpdated(row, col);

			}
		}
	}

}

class ProjectNameId {
	private String name;

	private int id;

	public ProjectNameId(String projectName, int projectId) {
		this.name = projectName;
		this.id = projectId;
	}

	public String toString() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}
}
