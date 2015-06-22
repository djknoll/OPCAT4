package groups.GUI;

import com.sciapp.renderers.BooleanRenderer;
import com.sciapp.renderers.DateRenderer;
import groups.OpcatGroup;
import groups.OpcatGroupEntityExport;
import groups.OpcatGroupExport;
import gui.util.opcatGrid.Grid;
import gui.util.opcatGrid.GridPanel;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: 1/1/11
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExportPanel extends GridPanel {

    public void fillData(ArrayList<OpcatGroupExport> exports, OpcatGroup group) {
        Grid exportGrid = this.getGrid();

        exportGrid.getGridData().clear();

        for (OpcatGroupExport export : exports) {
            for (OpcatGroupEntityExport entity : export.getEntities()) {
                Object[] row = new Object[9];
                Object[] rowTag = new Object[2];
                row[0] = export.getExportID();
                row[1] = export.isActive();
                row[2] = export.getUser();
                row[3] = export.getDateTime();
                row[4] = entity.getModelPath();
                row[5] = entity.getName();
                row[6] = entity.getText();
                row[7] = group.getName();
                row[8] = entity.getId();

                rowTag[0] = " ";
                rowTag[1] = " ";
                exportGrid.addRow(row, rowTag);
            }
        }
        exportGrid.getGridData().fireTableDataChanged();
        exportGrid.repaint();
    }

    public ExportPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);    //To change body of overridden methods use File | Settings | File Templates.
        setupGridPanel(preperaCols());
    }

    public ExportPanel(LayoutManager layout) {
        super(layout);    //To change body of overridden methods use File | Settings | File Templates.
        setupGridPanel(preperaCols());
    }

    public ExportPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);    //To change body of overridden methods use File | Settings | File Templates.
        setupGridPanel(preperaCols());
    }

    public ExportPanel() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
        setupGridPanel(preperaCols());
    }

    private Collection<String> preperaCols() {
        Collection<String> cols = new ArrayList<String>();
        cols.add("Export ID");
        cols.add("Active");
        cols.add("User");
        cols.add("Date");
        cols.add("File");
        cols.add("Entity");
        cols.add("Text");
        cols.add("Group");
        cols.add("Entity UUID");

        return cols;

    }


    @Override
    public void setupGridPanel(Collection<String> cols) {
        super.setupGridPanel(cols);
        Grid exportGrid = this.getGrid();
        this.getButtonPane().setVisible(false);
//        JPanel groupingPanel = new GroupingPanel(exportGrid.getTreeModel());
//        AdvancedJScrollPane scrollPane = new AdvancedJScrollPane(
//                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//
//        // scrollPane.add(myGrid) ;
//        scrollPane.setViewportView(exportGrid);
//        this.setLayout(new BorderLayout());
//        this.add(scrollPane, BorderLayout.CENTER);
//        this.add(groupingPanel, BorderLayout.SOUTH);
//        SearchPanel sp = new SearchPanel();
//        sp.getCloseButton().getParent().remove(sp.getCloseButton());
//        sp.setSearchOnKeyInput(true);
//        com.sciapp.table.search.TableStyleSelector selector = new com.sciapp.table.search.TableStyleSelector(
//                exportGrid);
//        ((AdvancedJTable) exportGrid).getStyleModel().addStyle(
//                selector.getStyle());
//        exportGrid.setCellSelectionEnabled(true);
//        sp.getSearchModel().addSearchModelListener(selector);
//        this.add(sp, BorderLayout.NORTH);

        getGrid().setCellSelectionEnabled(false);

        exportGrid.getColumnModel().getColumn(1).setCellRenderer(
                new BooleanRenderer());

        exportGrid.getColumnModel().getColumn(3).setCellRenderer(
                new DateRenderer());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ((DateRenderer) exportGrid.getColumnModel().getColumn(3).getCellRenderer()).setDateFormat(sdf);

        this.repaint();

    }
}
