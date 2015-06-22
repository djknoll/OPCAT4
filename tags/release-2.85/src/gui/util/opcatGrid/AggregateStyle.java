package gui.util.opcatGrid;

import java.awt.*;

import com.sciapp.table.styles.Style;
import com.sciapp.tree.*;

/**
 * 
 */
public class AggregateStyle implements Style {
	public TreeTable table;

	public TreeTableModel model;

	/**
	 * 
	 */
	public AggregateStyle(Grid table) {
		this.table = table;

		this.model = table.getTreeModel();
	}

	/**
	 * 
	 */
	public void apply(Component c, javax.swing.JTable t, int row, int column) {
		if (this.model.isFooter(row)) {

		} else if (this.model.isHeader(row)) {
			Font f = c.getFont().deriveFont(Font.BOLD);
			c.setFont(f);
		}
	}
}
