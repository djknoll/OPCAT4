package extensionTools.Testing;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TestingLifeSpanCellRenderer extends JButton implements
		TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TableCellRenderer __defaultRenderer;

	public TestingLifeSpanCellRenderer(TableCellRenderer renderer) {
		__defaultRenderer = renderer;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (value instanceof TestingColor) {
			setBackground(((TestingColor) value).getColor());
			setText(value.toString());
			return this;
		} else {
			return __defaultRenderer.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, column);
		}

	}

}
