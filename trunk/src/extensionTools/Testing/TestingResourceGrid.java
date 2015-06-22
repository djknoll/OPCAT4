package extensionTools.Testing;

import exportedAPI.opcatAPIx.IXObjectEntry;
import gui.Opcat2;
import gui.controls.FileControl;
import gui.opmEntities.OpmObject;
import gui.projectStructure.ObjectEntry;
import gui.util.CustomFileFilter;
import gui.util.opcatGrid.Grid;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import util.OpcatLogger;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class TestingResourceGrid extends TestingStepsRreportsAbstractGrid {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TestingResourceGrid(TestingSystem testingSys) {
        super(testingSys, "Resource Graph");
    }

    public void showGrid(TreeMap<Long, TestingStepData> data) {

        //need to remove the pannel from the tab as there could be left one in there.
        //if ((isOnExtensionToolsPane())) {
        //    ClearData();
        //    RemoveFromExtensionToolsPanel();
        //}


        ArrayList<String> cols = new ArrayList<String>();
        cols.add("Name");
        cols.add("Limits");

        init(data.keySet().size(), cols);


        for (TestingEntry entry : entries) {

            if (!(entry.getIXEntry() instanceof IXObjectEntry)) {
                continue;
            }

            Object[] row = new Object[cols.size()];
            row[0] = entry.getIXEntry().getName();
            OpmObject opm = (OpmObject) ((ObjectEntry) entry.getIXEntry())
                    .getLogicalEntity();

            if (!opm.isMesurementUnitExists()) {
                continue;
            }

            row[1] = opm.getMesurementUnitInitialValue() + " ["
                    + opm.getMesurementUnitMinValue() + ","
                    + opm.getMesurementUnitMaxValue() + "] "
                    + opm.getMesurementUnit();

            for (Long step : data.keySet()) {

                TestingStepData stepData = data.get(step);
                TestingStepEntryData entryData = stepData.getEntryData(entry);

                row[step.intValue() + 1] = getStateString(entryData) + " ("
                        + entryData.getMeasurementUnitAcommulatedValue() + ")";
            }

            Object[] rowTag = new Object[2];
            rowTag[0] = entry.getIXEntry();
            rowTag[1] = " ";
            getGrid().addRow(row, rowTag);


        }

        getGrid().getGridData().fireTableStructureChanged();
        getGrid().getGridData().fireTableDataChanged();
        AddToExtensionToolsPanel(true);

    }

    @Override
    public void rightClickEvent(MouseEvent e) {
        super.rightClickEvent(e);
        JPopupMenu exportMenu = getRMenu();

        JMenuItem fixWarnings = new JMenuItem("Export Resource  XML");
        fixWarnings.addActionListener(new actionExportToVisionDataXML(getGrid()));
        exportMenu.add(fixWarnings);

        exportMenu.show(e.getComponent(), e.getX(), e.getY());

    }

}

class actionExportToVisionDataXML implements ActionListener {

    private Grid grid = null;

    actionExportToVisionDataXML(Grid grid) {
        this.grid = grid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {


            TableModel model = grid.getModel();

            int cols = model.getColumnCount();
            int rows = model.getRowCount();


            /*
            <Row>
                <EntityID>
                Used Journal Bearings
                </EntityID>
                <SeriesName>
                Modelname
                </SeriesName>
//		<data xvalue="2009-03-24-10.01.25.49700018" yvalue="22"/>
//		<data xvalue="2009-03-24-10.01.25.49700018" yvalue="22"/>
//		<data xvalue="2009-03-24-10.01.25.49700018" yvalue="22"/>
//		<data xvalue="2009-03-24-10.01.25.49700018" yvalue="22"/>

            </Row>
            */

            //names is on first col.

            //seconed col should be ignored.

            //third col is start of data of steps and should be point 1

            TreeMap<String, TreeMap<Integer, String>> data = new TreeMap<String, TreeMap<Integer, String>>();
            TreeMap<String, TreeMap<Integer, String>> exceptionData = new TreeMap<String, TreeMap<Integer, String>>();

            TreeMap<String, String> initValues = new TreeMap<String, String>();

            for (int i = 0; i < rows; i++) {
                TreeMap<Integer, String> row = new TreeMap<Integer, String>();
                TreeMap<Integer, String> exceptionRow = new TreeMap<Integer, String>();

                String key = "xxxx";
                String initValue = "xxxxxx";

                for (int j = 0; j < cols; j++) {
                    if (j == 0)
                        key = model.getValueAt(i, j).toString().trim().replace("\n", " ");

                    if (j == 1)
                        initValue = model.getValueAt(i, j).toString();

                    if (j > 1) {
                        String val = model.getValueAt(i, j).toString();

                        if (val.trim().startsWith("min exception state")) {
                            String excValue;

                            int start = initValue.indexOf("[") + 1;
                            int end = initValue.indexOf(",", start);
                            excValue = initValue.substring(start, end);

                            //calc it from initValue
                            exceptionRow.put(j - 1, excValue);
                        }

                        int lastSpace = val.lastIndexOf(" ");
                        val = val.substring(lastSpace + 2, val.length() - 1);
                        row.put(j - 1, val);


                    }

                }

                initValues.put(key, initValue);
                data.put(key, row);
                if (exceptionRow.keySet().size() > 0)
                    exceptionData.put(key, exceptionRow);
            }

            Document docData = new Document();
            //Document docStates = new Document();

            //doc.
            Element root = new Element("ROOT");

            docData.setRootElement(root);


            for (String key : data.keySet()) {
                //create new row data for each row

                Element rowData = new Element("Row");
                Element rowExeptions = new Element("Row");

                Element id = new Element("EntityID");
                id.setText(key);
                rowData.addContent(id);

                Element seriesName = new Element("SeriesName");
                seriesName.setText(Opcat2.getCurrentProject().getName());
                rowData.addContent(seriesName);


                if (exceptionData.get(key) != null) {
                    Element id2 = new Element("EntityID");
                    id2.setText(key + " Exceptions");
                    rowExeptions.addContent(id2);

                    Element seriesName2 = new Element("SeriesName");
                    seriesName2.setText(Opcat2.getCurrentProject().getName());
                    rowExeptions.addContent(seriesName2);
                }

                for (Integer value : data.get(key).keySet()) {


                    Element xValue = new Element("data");
                    xValue.setAttribute("xvalue", String.valueOf(value));
                    xValue.setAttribute("yvalue", data.get(key).get(value));
                    rowData.addContent(xValue);

                    if ((exceptionData.get(key) != null) && (exceptionData.get(key).get(value) != null)) {
                        Element xValue2 = new Element("data");
                        xValue2.setAttribute("xvalue", String.valueOf(value));
                        xValue2.setAttribute("yvalue", exceptionData.get(key).get(value));
                        rowExeptions.addContent(xValue2);
                    }

                }
                root.addContent(rowData);
                if (rowExeptions.getChildren().size() > 0)
                    root.addContent(rowExeptions);
            }


            File outFile = new File("ResourcesData.xml");

            JFileChooser myFileChooser = new JFileChooser();
            myFileChooser.setSelectedFile(outFile);
            myFileChooser.resetChoosableFileFilters();
            CustomFileFilter opxChooser = new CustomFileFilter("xml",
                    "XML Files");
            if (!FileControl.getInstance().getLastDirectory().equals("")) {
                myFileChooser.setCurrentDirectory(new File(FileControl.getInstance().getLastDirectory()));
            }
            myFileChooser.addChoosableFileFilter(opxChooser);


            int retVal = myFileChooser.showDialog(Opcat2.getFrame(),
                    "Save System");

            if (retVal != JFileChooser.APPROVE_OPTION) {
                return;
            }

            outFile = myFileChooser.getSelectedFile();

            if ((outFile == null) || ((outFile.exists()) && (!(outFile.isFile())))) {
                JOptionPane.showMessageDialog(Opcat2.getFrame(), outFile.getPath() + "\n  not a File");
                return;
            }

            //save xml doc
            XMLOutputter outp = new XMLOutputter();
            outp.setFormat(Format.getPrettyFormat());
            outp.output(docData, new FileOutputStream(outFile));


        } catch (Exception e1) {
            OpcatLogger.error(e1, true);
        }

    }
}

