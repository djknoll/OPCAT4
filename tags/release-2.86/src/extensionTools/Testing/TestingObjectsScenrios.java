package extensionTools.Testing;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import com.sciapp.table.GroupTableColumn;
import com.sciapp.table.GroupTableHeader;
import gui.Opcat2;
import gui.opdProject.OpdProject;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.StateEntry;
import gui.util.OpcatLogger;
import gui.util.opcatGrid.GridPanel;

public class TestingObjectsScenrios extends GridPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3228741594759395297L;

	OpdProject project;

	int ColumnsNumber;

	Vector ColumnsNames;

	boolean onlyWithStates;

	public TestingObjectsScenrios(int ColumnsNumber, Vector ColumnsNames,
			OpdProject prj, boolean onlyWithStates) {
		super(ColumnsNumber, ColumnsNames);
		project = prj;
		this.ColumnsNames = ColumnsNames;
		this.ColumnsNumber = ColumnsNumber;
		this.onlyWithStates = onlyWithStates;

		this.getGrid().addMouseListener(new MyMouseListner(this));
		this.setEntryTag();
		init();
		this.setTabName("Objects");

	}

	protected void init() {
		// build the Grid
		ClearData();
		Iterator iter = project.getSystemStructure().GetObjectEntries();
		while (iter.hasNext()) {
			ObjectEntry ent = (ObjectEntry) iter.next();

			// if (ent.hasStates() || !onlyWithStates) {
			Object[] row = new Object[ColumnsNames.size()];
			for (int i = 0; i < row.length; i++) {
				row[i] = " ";
			}

			row[0] = ent.getName();

			row[1] = " ";
			HashMap processes = ent.getConnectedThings();
			Iterator connected = processes.values().iterator();
			while (connected.hasNext()) {
				Object[] array = (Object[]) connected.next();
				if (array[0] instanceof ProcessEntry) {
					ProcessEntry process = (ProcessEntry) (array[0]);
					row[1] = process.getName().replace("\n", " ") + ","
							+ row[1];
				}
			}
			row[2] = ent.getDescription();
			row[3] = ent.getUrl();

			Iterator states = Collections.list(ent.getStates()).iterator();
			row[4] = " ";
			row[5] = " ";
			while (states.hasNext()) {
				StateEntry state = (StateEntry) states.next();
				if (state.isInitial()) {
					row[4] = state.getName();
				}
				if (state.isFinal()) {
					row[5] = state.getName();
				}
			}

			Object[] rowTag = { " ", " " };
			rowTag[0] = ent;

			this.getGrid().addRow(row, rowTag);
		}
		// }
	}

	public void rightClickEvent(MouseEvent e) {
		JPopupMenu statesPopupMenu = this.getRMenu();

		if (this.getGrid().getSelectedRow() >= 0) {
			Object[] tag = new Object[2];
			tag = this.getGrid().GetTag(this.getGrid().getSelectedRow());
			ObjectEntry obj = (ObjectEntry) tag[0];
			Iterator iter = Collections.list(obj.getStates()).iterator();
			if (iter.hasNext()) {
				JMenu states = new JMenu("Set Initial");
				JMenu finStates = new JMenu("Set Final");
				while (iter.hasNext()) {
					StateEntry state = (StateEntry) iter.next();
					JCheckBoxMenuItem stateItem = new JCheckBoxMenuItem(state
							.getName());
					JCheckBoxMenuItem finalStateItem = new JCheckBoxMenuItem(
							state.getName());
					if (state.isInitial()) {
						stateItem.setSelected(true);
					} else {
						stateItem.setSelected(false);
					}
					if (state.isFinal()) {
						finalStateItem.setSelected(true);
					} else {
						finalStateItem.setSelected(false);
					}

					stateItem.addActionListener(new StateAction(this, obj,
							state, true));
					finalStateItem.addActionListener(new StateAction(this, obj,
							state, false));
					states.add(stateItem);
					finStates.add(finalStateItem);
				}
				statesPopupMenu.add(states);
				statesPopupMenu.add(finStates);
			}
			statesPopupMenu.add(new JSeparator());
			JMenuItem randomInitial = new JMenuItem("All Initial Off");
			randomInitial.addActionListener(new RandomInitial(this, project,
					true));
			JMenuItem randomFinal = new JMenuItem("All Final off");
			randomFinal.addActionListener(new RandomInitial(this, project,
					false));
			statesPopupMenu.add(randomInitial);
			statesPopupMenu.add(randomFinal);
			statesPopupMenu.add(new JSeparator());
			JMenuItem saveScen = new JMenuItem("Save as Scenario");
			JMenuItem manageScen = new JMenuItem("Manage Scenarios");
			manageScen.addActionListener(new ManageScen(project));
			saveScen.addActionListener(new SaveScen(project, this));
			JMenu loadScen = new JMenu("Load Scenario");

			Iterator sceniter = project.getScen().keySet().iterator();
			while (sceniter.hasNext()) {
				String name = (String) sceniter.next();
				JMenuItem menuitem = new JMenuItem(name);
				menuitem
						.addActionListener(new LoadScen(this, project, name, e));
				loadScen.add(menuitem);
			}

			statesPopupMenu.add(saveScen);
			statesPopupMenu.add(manageScen);
			statesPopupMenu.add(loadScen);

			statesPopupMenu.add(new JSeparator());
			JMenuItem compareScen = new JMenuItem("Compare Scenarios");
			compareScen.addActionListener(new CompareScen(project));
			statesPopupMenu.add(compareScen);

			statesPopupMenu.show(e.getComponent(), e.getX(), e.getY());

		}
	}
}

class CompareScen extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OpdProject project;

	public void actionPerformed(ActionEvent e) {

		Iterator sceniter = project.getScen().keySet().iterator();
		Vector cols = new Vector();
		ArrayList scenNames = new ArrayList();
		cols.add("Object Name");

		while (sceniter.hasNext()) {
			// cols.add((String) sceniter.next());
			cols.add("Initial");
			cols.add("Final");
			String name = (String) sceniter.next();
			scenNames.add(name);
			scenNames.add(name);
		}
		GridPanel scenPanel = new GridPanel(cols.size(), cols);
		scenPanel.setEntryTag();

		for (int i = 1; i < cols.size(); i = i + 2) {
			GroupTableColumn grp = new GroupTableColumn((String) scenNames
					.get(i));
			grp.addColumn(scenPanel.getGrid().getColumnModel().getColumn(i));
			grp
					.addColumn(scenPanel.getGrid().getColumnModel().getColumn(
							i + 1));
			// grp.setShowChildren(false);
			((GroupTableHeader) scenPanel.getGrid().getTableHeader())
					.addGroupColumn(grp);
		}
		scenPanel.setTabName("Compare Scenarios");
		scenPanel.RemoveFromExtensionToolsPanel();
		// now get all objects and put in rows;
		Iterator iter = project.getSystemStructure().GetObjectEntries();
		while (iter.hasNext()) {
			ObjectEntry obj = (ObjectEntry) iter.next();
			Object[] row = new Object[cols.size()];
			row[0] = obj.getName();

			for (int i = 1; i < cols.size(); i = i + 2) {
				HashMap scen = (HashMap) project.getScen().get(
						(String) scenNames.get(i));
				HashMap initialSce = (HashMap) scen.get("initial");
				HashMap finalSce = (HashMap) scen.get("final");

				String initialState = " ";
				Long StateID = (Long) initialSce.get(new Long(obj.getId()));
				if (StateID != null) {
					StateEntry initial = (StateEntry) project
							.getSystemStructure().getEntry(StateID.longValue());
					if (initial != null) {
						initialState = initial.getName();
					}
				}

				String finalState = " ";
				StateID = (Long) finalSce.get(new Long(obj.getId()));
				if (StateID != null) {
					StateEntry finalS = (StateEntry) project
							.getSystemStructure().getEntry(StateID.longValue());
					if (finalS != null) {
						finalState = finalS.getName();
					}
				}

				row[i] = initialState;
				row[i + 1] = finalState;

				// scenPanel.getGrid().getTableHeader()
			}

			Object[] rowTag = new Object[2];
			rowTag[0] = obj;
			rowTag[1] = " ";

			scenPanel.getGrid().addRow(row, rowTag);
		}

		scenPanel.AddToExtensionToolsPanel();
	}

	public CompareScen(OpdProject project) {
		this.project = project;
	}
}

class LoadScen extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	OpdProject project;

	String key;

	TestingObjectsScenrios panel;

	MouseEvent event;

	public LoadScen(TestingObjectsScenrios panel, OpdProject project,
			String key, MouseEvent event) {
		this.key = key;
		this.project = project;
		this.panel = panel;
		this.event = event;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		HashMap scen = (HashMap) project.getScen().get(key);
		HashMap initialSce = (HashMap) scen.get("initial");
		HashMap finalSce = (HashMap) scen.get("final");
		boolean noInitial = false;
		boolean noFinal = false;

		Iterator keys = initialSce.keySet().iterator();

		if (!keys.hasNext())
			noInitial = true;
		while (keys.hasNext()) {
			Long objID = (Long) keys.next();
			ObjectEntry ent = (ObjectEntry) project.getSystemStructure()
					.getEntry(objID.longValue());
			if (ent != null) {
				Iterator iter = Collections.list(ent.getStates()).iterator();
				while (iter.hasNext()) {
					StateEntry state = (StateEntry) iter.next();
					if (state.getId() == ((Long) initialSce.get(objID))
							.longValue()) {
						state.setInitial(true);
					} else {
						state.setInitial(false);
					}
				}
			} else {
				initialSce.remove(objID);
				keys = initialSce.keySet().iterator();
			}
		}

		keys = finalSce.keySet().iterator();
		if (!keys.hasNext())
			noFinal = true;
		while (keys.hasNext()) {
			Long objID = (Long) keys.next();
			ObjectEntry ent = (ObjectEntry) project.getSystemStructure()
					.getEntry(objID.longValue());
			if (ent != null) {
				Iterator iter = Collections.list(ent.getStates()).iterator();
				while (iter.hasNext()) {
					StateEntry state = (StateEntry) iter.next();
					if (state.getId() == ((Long) finalSce.get(objID))
							.longValue()) {
						state.setFinal(true);
					} else {
						state.setFinal(false);
					}
				}
			} else {
				finalSce.remove(objID);
				keys = finalSce.keySet().iterator();
			}
		}

		Iterator objectIter = project.getSystemStructure().GetObjectEntries();
		while (objectIter.hasNext()) {
			ObjectEntry ent = (ObjectEntry) objectIter.next();
			if (ent.hasStates()) {
				Iterator states = Collections.list(ent.getStates()).iterator();
				while (states.hasNext()) {
					StateEntry sta = (StateEntry) states.next();

					if (noInitial) {
						sta.setInitial(false);
					}

					if (noFinal) {
						sta.setFinal(false);
					}
				}
			}
		}
		Opcat2.getFrame().repaint();
		panel.init();
	}

}

class SaveScen extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -861379811237840446L;

	TestingObjectsScenrios panel;

	OpdProject project;

	public SaveScen(OpdProject project, TestingObjectsScenrios panel) {
		this.panel = panel;
		this.project = project;

	}

	public void actionPerformed(ActionEvent e) {

		HashMap initialsce = new HashMap();
		HashMap finalsce = new HashMap();

		HashMap sce = new HashMap();

		Iterator iter = project.getSystemStructure().GetObjectEntries();
		while (iter.hasNext()) {
			ObjectEntry ent = (ObjectEntry) iter.next();
			if (ent.hasStates()) {
				Iterator states = Collections.list(ent.getStates()).iterator();
				while (states.hasNext()) {
					StateEntry state = (StateEntry) states.next();
					if (state.isInitial()) {
						initialsce.put(new Long(ent.getId()), new Long(state
								.getId()));
					}
					if (state.isFinal()) {
						finalsce.put(new Long(ent.getId()), new Long(state
								.getId()));
					}
				}
			}
		}
		sce.put("initial", initialsce);
		sce.put("final", finalsce);

		String name = "Enter Scenario name" ; 
		
		while ( (name != null ) && (name.equalsIgnoreCase("Enter Scenario name") || (project.getScen().get(name) != null))) {
			name = (String) JOptionPane.showInputDialog(panel,
					"Scenario Name", "OPCAT II", JOptionPane.INFORMATION_MESSAGE,
					null, null, name);
			
			if(project.getScen().get(name) != null) {
				JOptionPane.showMessageDialog(panel, "Name already exists","Scenario Name", JOptionPane.ERROR_MESSAGE) ; 
			}
			
		}
		
		if (name != null) {
			project.getScen().put(name, sce);
		} else {
			JOptionPane.showMessageDialog(panel, "Save Canceled","Scenario Name", JOptionPane.ERROR_MESSAGE) ;
		}

		project.setCanClose(false);
	}

}

class StateAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9009284864238922559L;

	private ObjectEntry myObject;

	private StateEntry myState;

	private boolean setInit;

	private TestingObjectsScenrios scen;

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Iterator iter = Collections.list(myObject.getStates()).iterator();
		while (iter.hasNext()) {
			StateEntry state = (StateEntry) iter.next();
			if (setInit) {
				if (state.getId() == myState.getId()) {
					state.setInitial(!state.isInitial());
				} else {
					state.setInitial(false);
				}
			} else {
				if (state.getId() == myState.getId()) {
					state.setFinal(!state.isFinal());
				} else {
					state.setFinal(false);
				}
			}
		}
		Opcat2.getFrame().repaint();
		scen.init();
	}

	public StateAction(TestingObjectsScenrios source, ObjectEntry obj,
			StateEntry state, boolean setInitial) {
		super();
		myObject = obj;
		myState = state;
		setInit = setInitial;
		scen = source;
	}
}

class MyMouseListner extends MouseAdapter {
	private TestingObjectsScenrios panel;

	public MyMouseListner(TestingObjectsScenrios Panel) {
		this.panel = Panel;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			panel.dblClickEvent(e);
		}

		if (e.getButton() == MouseEvent.BUTTON3) {
			if (panel.getGrid().getSelectedRow() != -1)
				panel.rightClickEvent(e);
		}
	}
}

class ManageScen extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	OpdProject project;

	ScenriosDialog diag;

	public ManageScen(OpdProject project) {

		this.project = project;
		try {
			diag = new ScenriosDialog(this.project, Opcat2.getFrame());
		} catch (Exception ex) {
			OpcatLogger.logError(ex);
		}

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		diag.setVisible(true);
	}

}

class RandomInitial extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	OpdProject project;

	TestingObjectsScenrios panel;

	boolean initial = false;

	public RandomInitial(TestingObjectsScenrios panel, OpdProject project,
			boolean setInitial) {
		this.panel = panel;
		this.project = project;
		this.initial = setInitial;
	}

	public void actionPerformed(ActionEvent e) {
		Iterator objectIter = project.getSystemStructure().GetObjectEntries();
		while (objectIter.hasNext()) {
			ObjectEntry ent = (ObjectEntry) objectIter.next();
			if (ent.hasStates()) {
				Iterator states = Collections.list(ent.getStates()).iterator();
				while (states.hasNext()) {
					StateEntry sta = (StateEntry) states.next();

					if (initial) {
						sta.setInitial(false);
					}

					if (!initial) {
						sta.setFinal(false);
					}
				}
			}

		}
		Opcat2.getFrame().repaint();
		panel.init();

	}

}