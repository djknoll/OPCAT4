package gui.util;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import gui.Opcat2;
import gui.metaDataProject.CSVLoad;
import gui.opmEntities.OpmThing;
import gui.projectStructure.ThingInstance;
import gui.util.opcatGrid.GridPanel;

/**
 * This class will load a CSV in the form - ThingName,Thing Type(Process,
 * Object),Phys?,Env? into the current OPD.
 * 
 * @author raanan
 * 
 */
public class LoadThingsCSV {

	File file;

	public LoadThingsCSV(File file) {
		this.file = file;
	}

	public void load() {
		Vector colNames = new Vector();
		colNames.add("Line");
		colNames.add("Name");
		colNames.add("Type");
		colNames.add("Description");		
		colNames.add("Phys ?");
		colNames.add("Env ?");
		colNames.add("Error String");
		GridPanel gridPanel = new GridPanel(colNames.size(), colNames);
		gridPanel.setTabName("Loading Errors");
		gridPanel.RemoveFromExtensionToolsPanel();

		CSVLoad loader = new CSVLoad(file);

		Iterator iter = loader.getRowsIterator();

		int i = 50;
		int line = 0;
		while (iter.hasNext()) {
			Vector row = (Vector) iter.next();
			line++;
			try {
				String name = (String) row.elementAt(0);
				String type = (String) row.elementAt(1);
				String description = (String) row.elementAt(2) ; 
				String phys = (String) row.elementAt(3);
				String env = (String) row.elementAt(4);

				ThingInstance thing = null;
				if (type.equalsIgnoreCase("object")) {
					thing = Opcat2.getCurrentProject().addObject(
							i,
							i,
							Opcat2.getCurrentProject().getCurrentOpd()
									.getDrawingArea(), -1, -1, false);

				}

				if (type.equalsIgnoreCase("process")) {
					thing = Opcat2.getCurrentProject().addProcess(
							i,
							i,
							Opcat2.getCurrentProject().getCurrentOpd()
									.getDrawingArea(), -1, -1);
				}

				if (thing != null) {

					thing.getEntry().setName(name);

					if (env.equalsIgnoreCase("yes")) {
						thing.getEntry().setEnvironmental(true);
					}

					if (phys.equalsIgnoreCase("yes")) {
						((OpmThing) thing.getEntry().getLogicalEntity())
								.setPhysical(true);
					}
					
					thing.getEntry().setDescription(description) ; 

					thing.getConnectionEdge().fitToContent();
					i += 5;
				}
			} catch (Exception ex) {
				// report fail on object.
				Object[] errorRow = new Object[colNames.size()];
				errorRow[0] = new Long(line + 2 );

				for (int count = 0; count < colNames.size() - 2  ; count++) {
					try {
						errorRow[count + 1] = row.elementAt(count);
					} catch (Exception errorEx) {
						errorRow[count + 1] = "Problem !!!";
						if(errorRow[colNames.size() -1 ] == null ) {
							errorRow[colNames.size() -1 ] = "Index " + (count +1)  + ", "  ;
						} else {
							errorRow[colNames.size() -1 ] = errorRow[colNames.size() -1 ] + "Index " + (count +1)  + ", "  ; 
						}
					}
				}
				gridPanel.getGrid().addRow(errorRow, null);
			}
		}
		if (gridPanel.getGrid().getRowCount() > 0)
			gridPanel.AddToExtensionToolsPanel();
		Opcat2.getCurrentProject().setCanClose(false);
		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		Opcat2.getCurrentProject().getCurrentOpd().refit();
		Opcat2.getCurrentProject().getCurrentOpd().getDrawingArea().repaint();
	}
}
