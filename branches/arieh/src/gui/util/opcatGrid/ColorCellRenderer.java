package gui.util.opcatGrid;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Oct 26, 2009
 * Time: 11:39:23 AM
 * To change this template use File | Settings | File Templates.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Class to render object of type Color.
 *
 * @author Giovane.Kuhn on 29/05/2005
 */
public final class ColorCellRenderer extends JComponent implements TableCellRenderer {

    private static final long serialVersionUID = 1L;

    /**
     * Maintain current color *
     */
    private Color current;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // just change background color
        if (value instanceof Color) {
            current = (Color) value;
        } else {
            current = table.getBackground();
        }
        return this;
    }

    public void paint(Graphics g) {
        // paint a square
        g.setColor(current);
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}

