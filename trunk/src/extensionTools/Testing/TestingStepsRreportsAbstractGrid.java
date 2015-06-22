package extensionTools.Testing;

import com.sciapp.table.AdvancedJTable;
import com.sciapp.table.styles.Style;
import gui.controls.GuiControl;
import gui.util.opcatGrid.GridPanel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.util.ArrayList;
import java.util.TreeMap;

public abstract class TestingStepsRreportsAbstractGrid extends GridPanel {

    private static final long serialVersionUID = 1L;

    protected ArrayList<TestingEntry> entries = new ArrayList<TestingEntry>();

    protected ArrayList<String> cols = new ArrayList<String>();

    protected String tabName = "No name set";

    public TestingStepsRreportsAbstractGrid(TestingSystem testingSys, String tabName) {
        entries = new ArrayList<TestingEntry>();
        entries.addAll(testingSys.getTestingElements());

        this.tabName = tabName;
        JTabbedPane tabs = GuiControl.getInstance().getExtensionToolsPane();
        if (tabs.indexOfTab(tabName) > 0) {
            tabs.removeTabAt(tabs.indexOfTab(tabName));
        }
    }

    protected void init(long stepsNumber, ArrayList<String> firstCols) {

        this.cols = firstCols;

        for (int i = 1; i <= stepsNumber; i++) {
            cols.add(String.valueOf(i));
        }

        setupGridPanel(cols);

        getGrid().setAutoResizeMode(AdvancedJTable.AUTO_RESIZE_OFF);

        setTabName(tabName);
        //if (isOnExtensionToolsPane()) {
        //    RemoveFromExtensionToolsPanel();
        //}
        setEntryTag();
        // panel.AddToExtensionToolsPanel();

        TableCellRenderer defRend = getGrid().getDefaultRenderer(
                Object.class);
        TableCellRenderer rend = new TestingLifeSpanCellRenderer(defRend);

        getGrid().setDefaultRenderer(Object.class, rend);

        Style[] style = getGrid().getStyleModel().getStyles();
        for (int i = 0; i < style.length; i++) {
            getGrid().getStyleModel().removeStyle(style[i]);
        }

        getGrid().getGridData().fireTableStructureChanged();
        getGrid().getGridData().fireTableDataChanged();

    }

    protected void init(long stepsNumber) {

        this.cols = new ArrayList<String>();
        cols.add("Name");
        cols.add("Type");

        init(stepsNumber, cols);

//        for (int i = 1; i <= stepsNumber; i++) {
//            cols.add(String.valueOf(i));
//        }
//
//        setupGridPanel(cols);
//
//        getGrid().setAutoResizeMode(AdvancedJTable.AUTO_RESIZE_OFF);
//
//        setTabName(tabName);
//        if (isOnExtensionToolsPane()) {
//            RemoveFromExtensionToolsPanel();
//        }
//        setEntryTag();
//        // panel.AddToExtensionToolsPanel();
//
//        TableCellRenderer defRend = getGrid().getDefaultRenderer(
//                Object.class);
//        TableCellRenderer rend = new TestingLifeSpanCellRenderer(defRend);
//
//        getGrid().setDefaultRenderer(Object.class, rend);
//
//        Style[] style = getGrid().getStyleModel().getStyles();
//        for (int i = 0; i < style.length; i++) {
//            getGrid().getStyleModel().removeStyle(style[i]);
//        }
//
//        getGrid().getGridData().fireTableStructureChanged();
//        getGrid().getGridData().fireTableDataChanged();

    }

    protected String getStateString(TestingStepEntryData entryData) {

        String value = entryData.getStateName();

        if (entryData.isObject()) {

            if (value.equals("")) {
                if (entryData.isAnimated()) {
                    value = "exists";
                } else {
                    value = "not exists";
                }
            }
        } else {
            if (entryData.isAnimated()) {
                value = "active";
            } else {
                value = "not active";
            }
        }

        return value;
    }


    abstract public void showGrid(TreeMap<Long, TestingStepData> data);

}
