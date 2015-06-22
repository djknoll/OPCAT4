package extensionTools.search;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JLabel;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.opdProject.OpdProject;
import gui.projectStructure.Entry;
import gui.util.OpcatLogger;
import gui.util.opcatGrid.GridPanel;

/**
 * This class is the Action of the search Button in the {@link  SearchDialog }
 * It gets an OptionBase from the dialog and creates a Search algorithem by
 * using the AlgorithemFactory. this search is then used to search the project.
 * 
 * The search output is then added into the grid. a row for each instance. The
 * Instance Key is added as a tag to the grid. this tag is latter used in
 * {@link SearchAction} to move the focus to the selected line.
 * 
 * singleton
 * 
 * @author raanan
 * 
 */
public class SearchAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private String tabname = "None";

	GridPanel searchPanel = null;

	IXSystem opcat = null;

	Entry parents[];

	OptionsBase[] myOptions;

	public SearchAction(String tabname) {
		this.myOptions = new OptionsBase[1];
		this.tabname = tabname;
		this.searchPanel = this.InitSearchGridPanel();
	}

	public SearchAction(Entry parent, OptionsBase options, String tabname) {
		this.myOptions = new OptionsBase[1];
		this.myOptions[0] = options;
		this.tabname = tabname;

		this.parents = new Entry[1];
		this.parents[0] = parent;
		this.searchPanel = this.InitSearchGridPanel();
	}

	public SearchAction(Entry[] parents, OptionsBase[] options, String tabname) {
		this.myOptions = options;
		this.tabname = tabname;
		this.parents = parents;
		this.searchPanel = this.InitSearchGridPanel();
	}

	public void actionPerformed(ActionEvent e) {
	    
		GuiControl.getInstance().getGlassPane().setVisible(true);
		GuiControl.getInstance().getGlassPane().start();	    

		// if(this.opcat == null) {
		opcat = (IXSystem) FileControl.getInstance().getCurrentISystem();
		// }
		Vector output = new Vector();
		for (int i = 0; i < this.myOptions.length; i++) {
			// Search here
			/**
			 * TODO: Add a options factory so i could use the algo factory
			 * without the dialog, it's input should be OptionBase class
			 */
			AlgoFactory factory;
			if (this.myOptions[i] != null) {
				factory = new AlgoFactory(this.myOptions[i]);
			} else {
				factory = new AlgoFactory(SearchDialog.OptionsFactory());
			}

			AlgoInterface search = (AlgoInterface) factory.create();
			if (parents == null) {
				output.add(search.PreformSearch(null, this.opcat));
			} else {
				output.add(search.PreformSearch(parents[i], this.opcat));
			}
		}

		searchPanel.RemoveFromExtensionToolsPanel();
		searchPanel = null;
		searchPanel = InitSearchGridPanel();
		searchPanel.AddToExtensionToolsPanel();

		Iterator outIter = output.iterator();
		while (outIter.hasNext()) {
			Vector entries = (Vector) outIter.next();
			Iterator entIter = entries.iterator();
			while (entIter.hasNext()) {
				IXEntry ent = (IXEntry) entIter.next();
				this.SearchGridAddRow(ent);
			}
		}
		
		GuiControl.getInstance().getGlassPane().stop();

	}

	private GridPanel InitSearchGridPanel() {
		ArrayList cols = new ArrayList();
		cols.add("Thing Type");
		cols.add("OPD");
		cols.add("Thing Name");
		// cols.add("Roles") ;
		GridPanel locPanel;
		locPanel = new GridPanel(cols);
		locPanel.setInstanceTag(opcat);
		locPanel.getGrid().setDuplicateRows(false);
		locPanel.getButtonPane().add(new JLabel(""));
		locPanel.getButtonPane().add(new JLabel(""));
		locPanel.getButtonPane().add(new JLabel(""));
		locPanel.setTabName(tabname);
		locPanel.doLayout();
		return locPanel;
	}

	private void SearchGridAddRow(IXEntry entry) {

		// Entry entry = (Entry) ent ;

		Enumeration insEnum = entry.getInstances();
		for (; insEnum.hasMoreElements();) {
			try {
				IXInstance ins = null;
				OpdKey key = null;
				Object[] tag = new Object[2];
				Object[] row = new Object[this.searchPanel.getGrid()
						.getColumnCount()];

				ins = (IXInstance) insEnum.nextElement();
				if (ins.getKey().getOpdId() != OpdProject.CLIPBOARD_ID) {
					key = ins.getKey();
					String type = "None";
					if (entry instanceof IXProcessEntry) {
						type = "Process";
					} else if (entry instanceof IXObjectEntry) {
						type = "Object";
					} else if (entry instanceof IXStateEntry) {
						type = "State";
					} else {
						type = "Link";
					}
					row[0] = type;
					row[1] = this.opcat.getIXSystemStructure().getIXOpd(
							key.getOpdId()).getName().replaceAll("\n", " ");
					row[2] = entry.getName().replaceAll("\n", " ");

					tag[0] = key;
					tag[1] = new Long(entry.getId());
					searchPanel.getGrid().addRow(row, tag);
				}
			} catch (Exception e) {
				OpcatLogger.logError(e);
			}
		}
	}

	public GridPanel getSearchPanel() {
		return searchPanel;
	}

}
