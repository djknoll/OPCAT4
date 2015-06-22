package extensionTools.search;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;
import gui.opdProject.OpdProject;
import gui.util.debug.Debug;

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
	 

	SearchGridPanel searchPanel = null;

	IXSystem opcat = null;

	OptionsBase[] myOptions;

	String searchPanelName = "Search";
	
	public SearchAction(IXSystem system) {
		this.opcat = system;
		this.myOptions = new OptionsBase[1];
		this.searchPanel = this.InitSearchGridPanel();
	}	

	public SearchAction(IXSystem system, OptionsBase options) {
		this.opcat = system;
		this.myOptions = new OptionsBase[1];
		this.myOptions[0] = options;
		this.searchPanel = this.InitSearchGridPanel();
	}

	public SearchAction(IXSystem system, OptionsBase[] options) {
		this.opcat = system;
		this.myOptions = options;
		this.searchPanel = this.InitSearchGridPanel();
	}

	public void actionPerformed(ActionEvent e) {

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
			output.add(search.PreformSearch(this.opcat));
		}

		this.searchPanel.AddToExtensionToolsPanel(this.searchPanelName);

		Iterator outIter = output.iterator();
		while (outIter.hasNext()) {
			Vector entries = (Vector) outIter.next();
			Iterator entIter = entries.iterator();
			while (entIter.hasNext()) {
				IXEntry ent = (IXEntry) entIter.next();
				this.SearchGridAddRow(ent);
			}
		}

	}

	private SearchGridPanel InitSearchGridPanel() {
		Vector cols = new Vector();
		cols.add("Thing Type");
		cols.add("OPD");
		cols.add("Thing Name");
		SearchGridPanel locPanel;
		locPanel = new SearchGridPanel(cols.size(), cols, this.opcat);
		locPanel.doLayout();
		return locPanel;
	}

	private void SearchGridAddRow(IXEntry entry) {

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
					}
					if (entry instanceof IXObjectEntry) {
						type = "Object";
					}
					if (entry instanceof IXStateEntry) {
						type = "State";
					}
					row[0] = type;
					row[1] = this.opcat.getIXSystemStructure().getIXOpd(
							key.getOpdId()).getName().replaceAll("\n", " ");
					row[2] = entry.getName().replaceAll("\n", " ");
					// System.out.println(key.toShtring()) ;
					tag[0] = key;
					tag[1] = new Long(entry.getId());
					int numOfInsertedRow = this.searchPanel.getGrid().addRow(row,
							tag);
					this.searchPanel.getGrid().Update(numOfInsertedRow,
							numOfInsertedRow);
				}
			} catch (Exception e) {
				Debug debug = Debug.getInstance();
				debug.Print(this, e.getMessage(), "1");
			}
		}
	}

	public void RemoveSearch() {
		this.searchPanel.RemoveFromExtensionToolsPanel(this.searchPanelName);
	}

	public String getSearchPanelName() {
		return this.searchPanelName;
	}

	public void setSearchPanelName(String searchPanelName) {
		this.searchPanelName = searchPanelName;
	}
}
