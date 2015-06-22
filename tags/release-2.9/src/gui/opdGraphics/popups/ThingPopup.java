package gui.opdGraphics.popups;

import exportedAPI.opcatAPI.ISystem;
import extensionTools.uml.umlDiagrams.DiagramsCreator;
import extensionTools.uml.userInterface.UMLSaver;
import extensionTools.uml.userInterface.UserDesicion;
import extensionTools.validator.recommender.Recommender;
import gui.Opcat2;
import gui.actions.edit.InsertPropertyAction;
import gui.images.standard.StandardImages;
import gui.metaLibraries.logic.LibraryTypes;
import gui.metaLibraries.logic.Role;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdProject.OpcatLayoutManager;
import gui.opdProject.OpdProject;
import gui.opdProject.consistency.ConsistencyAbstractChecker;
import gui.opdProject.consistency.ConsistencyAction;
import gui.opdProject.consistency.ConsistencyFactory;
import gui.opdProject.consistency.ConsistencyResult;
import gui.opmEntities.OpmThing;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.FundamentalRelationEntry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.testingScenarios.Scenario;
import gui.testingScenarios.ScenarioUtils;
import gui.testingScenarios.TestingScenariosPanel;
import gui.util.CustomFileFilter;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class ThingPopup extends DefaultPopup {

    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    /**
         * 
         */

    private ThingInstance ti;

    public ThingPopup(OpdProject prj, Instance inst, int x, int y) {
	super(prj, inst);
	this.ti = (ThingInstance) inst;// (ThingInstance)myProject.getCurrentOpd().getSelectedItem();
	this.add(this.showAction);

	// properties menu
	JMenu sonsMenu = new JMenu("Insert property");
	sonsMenu.setIcon(StandardImages.EMPTY);

	// add direct sons
	JMenu direct = new JMenu("Direct");
	sonsMenu.add(direct);
	direct.addMenuListener(this.directAction);

	// add inharated ons
	JMenu inharated = new JMenu("Inherited");
	sonsMenu.add(inharated);
	inharated.addMenuListener(this.inharatedAction);

	JMenu meta = new JMenu("From library");
	sonsMenu.add(meta);
	// meta direct
	JMenu metadirect = new JMenu("Direct");
	metadirect.addMenuListener(this.metadirectAction);
	meta.add(metadirect);

	// meta inherited
	JMenu metainharated = new JMenu("Inherited");
	metainharated.addMenuListener(this.metainharatedAction);
	meta.add(metainharated);

	JMenu rolesinherited = new JMenu("From inherited roles");
	rolesinherited.addMenuListener(this.rolesinharatedAction);
	meta.add(rolesinherited);

	this.add(sonsMenu);
	this.add(new JSeparator());

	ThingEntry myEntry = (ThingEntry) ti.getEntry();

	if (myEntry.getUnfoldingOpd() == null) {
	    this.add(this.unfoldingAction);
	} else {
	    this.add(this.gotoUnfoldAction);
	}
	if (myEntry.getZoomedInOpd() == null) {
	    this.add(this.zoomInAction);
	} else {
	    this.add(this.gotoZoominAction);
	}
	this.add(new JSeparator());

	this.add(this.copyAction);

	this.add(this.cutAction);
	// System.err.println(ti);

	if (!(this.ti.getThing().isZoomedIn())) {
	    this.pasteAction.setEnabled(false);
	}
	// add(pasteAction);
	this.add(this.pasteAction);

	this.add(this.deleteAction);
	this.add(new JSeparator());

	this.add(this.bringToFrontAction);
	this.add(this.sendToBackAction);
	this.add(this.fitToContent);

	JMenu alingMenu = new JMenu("Align");
	alingMenu.setIcon(StandardImages.EMPTY);
	alingMenu.add(this.alignLeftAction);
	alingMenu.add(this.alignRightAction);
	alingMenu.add(this.alignTopAction);
	alingMenu.add(this.alignBottomAction);
	alingMenu.add(this.alignCenterVerticallyAction);
	alingMenu.add(this.alignCenterHorizontallyAction);
	alingMenu.add(new JSeparator());
	alingMenu.add(this.alignAsTreeAction);

	this.add(alingMenu);
	if ((this.myProject.getCurrentOpd() == null)
		|| (this.myProject.getCurrentOpd().getSelectedItemsHash()
			.size() < 2)) {
	    alingMenu.setEnabled(false);
	}

	this.add(new JSeparator());
	this.add(this.ConsistencyHelper);
	this.add(this.TestingCenter);
	this.add(new JSeparator());
	this.add(this.propertiesAction);

	this.add(new JSeparator());
	this.add(this.GenerateClassDiagram);
	if (!(this.myInstance instanceof ObjectInstance)
		|| (((ObjectInstance) this.myInstance).getUnfoldingOpd() == null)) {
	    this.GenerateClassDiagram.setEnabled(false);
	}

	// Meta-Libraries based advisor - by Eran Toch
	this.add(new JSeparator());
	Recommender rec = new Recommender(this.ti, this.myProject);
	this.add(rec.getAdvices());
    }

    Action showAction = new AbstractAction("Show all appearances",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
         * 
         */

	public void actionPerformed(ActionEvent e) {

	    ThingPopup.this.ti.getEntry().ShowInstances();
	}
    };

    private JMenu fillPropertiesMenu(HashMap hmap, JMenu menu, boolean metalib,
	    Role role) {
	Iterator sonsIter = hmap.keySet().iterator();

	while (sonsIter.hasNext()) {
	    ThingEntry thing = (ThingEntry) sonsIter.next();
	    FundamentalRelationEntry relation = (FundamentalRelationEntry) hmap
		    .get(thing);
	    // only show Thing if it is not connected in this OPD
	    boolean isThingInOPD = (myProject.getSystemStructure()
		    .getInstanceInOpd(ti.getOpd(), thing.getId()) != null);
	    boolean showSon = true;

	    // now check if son is alrady connected so we don't have to show
	    // it
	    if (isThingInOPD && !metalib) {
		// see if ti is connected to Son.
		Iterator iter = Collections.list(
			ti.getRelatedSourceFundamentalRelation()).iterator();
		while (iter.hasNext()) {
		    FundamentalRelationInstance relIns = (FundamentalRelationInstance) iter
			    .next();
		    if (relIns.getDestinationInstance().getEntry().getId() == thing
			    .getId()) {
			showSon = false;
			break;
		    }
		}

	    }
	    // boolean isRelationINOpd = (myProject.getSystemStructure()
	    // .getInstanceInOpd(ti.getOpd(), relation.getId()) != null);

	    if (showSon && !metalib) {
		JMenuItem son = new JMenuItem(thing.getName());
		ActionListener sonsActionListner = new InsertPropertyAction(ti,
			thing, relation, false, null);
		son.addActionListener(sonsActionListner);
		menu.add(son);
	    }
	    if (metalib) {
		JMenuItem son = new JMenuItem(thing.getName());
		ActionListener sonsActionListner = new InsertPropertyAction(ti,
			thing, relation, true, role);
		son.addActionListener(sonsActionListner);
		menu.add(son);
	    }
	}

	return menu;
    }

    MenuListener directAction = new MenuListener() {

	private static final long serialVersionUID = 1L;

	public void menuCanceled(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuDeselected(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuSelected(MenuEvent e) {
	    // TODO Auto-generated method stub
	    JMenu direct = (JMenu) e.getSource(); // getValue("Menu") ;
	    direct.removeAll();
	    HashMap sons = ((ThingEntry) ti.getEntry())
		    .getAllFundemantulRelationSons();
	    fillPropertiesMenu(sons, direct, false, null);

	}
    };

    MenuListener inharatedAction = new MenuListener() {

	private static final long serialVersionUID = 1L;

	public void menuCanceled(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuDeselected(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuSelected(MenuEvent e) {
	    // TODO Auto-generated method stub
	    ((JMenu) e.getSource()).removeAll();
	    HashMap sons = ((ThingEntry) ti.getEntry())
		    .getAllInharatedProperties();
	    fillPropertiesMenu(sons, (JMenu) e.getSource(), false, null);

	}
    };

    MenuListener metainharatedAction = new MenuListener() {

	private static final long serialVersionUID = 1L;

	public void menuCanceled(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuDeselected(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuSelected(MenuEvent e) {
	    ((JMenu) e.getSource()).removeAll();
	    Iterator rolesIter = ti.getEntry().getLogicalEntity()
		    .getRolesManager().getLoadedRolesVector(
			    LibraryTypes.MetaLibrary).iterator();
	    while (rolesIter.hasNext()) {
		Role role = (Role) rolesIter.next();
		OpdProject rolesProject = (OpdProject) role.getOntology()
			.getProjectHolder();
		ThingEntry entry = (ThingEntry) rolesProject
			.getSystemStructure().getEntry(
				((OpmThing) role.getThing()).getId());
		if (entry != null) {
		    HashMap sons = entry.getAllInharatedProperties();
		    fillPropertiesMenu(sons, (JMenu) e.getSource(), true, role);
		}
	    }
	}
    };

    MenuListener rolesinharatedAction = new MenuListener() {

	private static final long serialVersionUID = 1L;

	public void menuCanceled(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuDeselected(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuSelected(MenuEvent e) {
	    ((JMenu) e.getSource()).removeAll();
	    HashMap rolesMap = ((ThingEntry) ti.getEntry())
		    .getAllIneratenceRoles();
	    Iterator rolesIter = rolesMap.keySet().iterator();
	    while (rolesIter.hasNext()) {
		Role role = (Role) rolesIter.next();
		OpdProject rolesProject = (OpdProject) role.getOntology()
			.getProjectHolder();
		ThingEntry entry = (ThingEntry) rolesProject
			.getSystemStructure().getEntry(
				((OpmThing) role.getThing()).getId());
		if (entry != null) {
		    JMenu roleMenu = new JMenu(entry.getName());
		    JMenu direct = new JMenu("Direct");
		    JMenu inherited = new JMenu("Inherited");
		    roleMenu.add(direct);
		    roleMenu.add(inherited);
		    HashMap sons = entry.getAllInharatedProperties();
		    fillPropertiesMenu(sons, inherited, true, role);
		    sons = entry.getAllFundemantulRelationSons();
		    fillPropertiesMenu(sons, direct, true, role);
		    ((JMenu) e.getSource()).add(roleMenu);
		}
	    }
	}
    };

    MenuListener metadirectAction = new MenuListener() {

	private static final long serialVersionUID = 1L;

	public void menuCanceled(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuDeselected(MenuEvent e) {
	    // TODO Auto-generated method stub

	}

	public void menuSelected(MenuEvent e) {
	    ((JMenu) e.getSource()).removeAll();
	    Iterator rolesIter = ti.getEntry().getLogicalEntity()
		    .getRolesManager().getLoadedRolesVector(
			    LibraryTypes.MetaLibrary).iterator();
	    while (rolesIter.hasNext()) {
		Role role = (Role) rolesIter.next();
		OpdProject rolesProject = (OpdProject) role.getOntology()
			.getProjectHolder();
		ThingEntry entry = (ThingEntry) rolesProject
			.getSystemStructure().getEntry(
				((OpmThing) role.getThing()).getId());
		if (entry != null) {
		    HashMap sons = entry.getAllFundemantulRelationSons();
		    fillPropertiesMenu(sons, (JMenu) e.getSource(), true, role);
		}
	    }
	    // HashMap sons = ((ThingEntry) ti.getEntry())
	    // .getAllInharatedProperties();
	    // fillPropertiesMenu(sons, (JMenu) e.getSource());

	}
    };

    Action zoomInAction = new AbstractAction("In-Zoom", StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = 7921410206025464287L;

	public void actionPerformed(ActionEvent e) {
	    try {

		ThingPopup.this.myProject.zoomIn();
	    } catch (Exception e1) {
		System.out.println(e1);
	    }
	}
    };

    Action unfoldingAction = new AbstractAction("Unfold", StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = -667563739626134447L;

	public void actionPerformed(ActionEvent e) {
	    try {
		ThingPopup.this.myProject.unfolding();
	    } catch (Exception e1) {
		OpcatLogger.logError(e1);
	    }
	}
    };

    Action gotoUnfoldAction = new AbstractAction("Show Unfolded OPD",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = -667563739626134447L;

	public void actionPerformed(ActionEvent e) {
	    try {
		Opcat2.getCurrentProject().showOPD(
			((ThingEntry) ti.getEntry()).getUnfoldingOpd()
				.getOpdId());
	    } catch (Exception e1) {
		OpcatLogger.logError(e1);
	    }
	}
    };

    Action gotoZoominAction = new AbstractAction("Show In-zoomed OPD",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = -667563739626134447L;

	public void actionPerformed(ActionEvent e) {
	    try {
		Opcat2.getCurrentProject().showOPD(
			((ThingEntry) ti.getEntry()).getZoomedInOpd()
				.getOpdId());
	    } catch (Exception e1) {
		OpcatLogger.logError(e1);
	    }
	}
    };

    Action alignLeftAction = new AbstractAction("Left") {

	/**
         * 
         */
	private static final long serialVersionUID = -5483421693041278633L;

	public void actionPerformed(ActionEvent e) {
	    ThingPopup.this.myProject.getLayoutManager().align2Left();
	}
    };

    Action alignRightAction = new AbstractAction("Right") {

	/**
         * 
         */
	private static final long serialVersionUID = 239503285663982807L;

	public void actionPerformed(ActionEvent e) {
	    ThingPopup.this.myProject.getLayoutManager().align2Right();
	}
    };

    Action alignTopAction = new AbstractAction("Top") {

	/**
         * 
         */
	private static final long serialVersionUID = 2305020403623966419L;

	public void actionPerformed(ActionEvent e) {
	    ThingPopup.this.myProject.getLayoutManager().align2Top();
	}
    };

    Action alignBottomAction = new AbstractAction("Bottom") {

	/**
         * 
         */
	private static final long serialVersionUID = 7814303177070480931L;

	public void actionPerformed(ActionEvent e) {
	    ThingPopup.this.myProject.getLayoutManager().align2Bottom();
	}
    };

    Action alignCenterVerticallyAction = new AbstractAction("Center Vertically") {

	/**
         * 
         */
	private static final long serialVersionUID = 1316613334680687140L;

	public void actionPerformed(ActionEvent e) {
	    ThingPopup.this.myProject.getLayoutManager()
		    .align2CenterVertically();
	}
    };

    Action alignCenterHorizontallyAction = new AbstractAction(
	    "Center Horizontally") {

	/**
         * 
         */
	private static final long serialVersionUID = 6662070932673168893L;

	public void actionPerformed(ActionEvent e) {
	    ThingPopup.this.myProject.getLayoutManager()
		    .align2CenterHorizontally();
	}
    };

    Action alignAsTreeAction = new AbstractAction("As Tree") {

	/**
         * 
         */
	private static final long serialVersionUID = -5193633177133700618L;

	public void actionPerformed(ActionEvent e) {
	    ThingPopup.this.myProject.getLayoutManager().alignAsTree();
	}
    };

    Action alignVertically = new AbstractAction("Vertically") {

	/**
         * 
         */
	private static final long serialVersionUID = -5193633177133700618L;

	public void actionPerformed(ActionEvent e) {

	    ((ObjectInstance) ti).setStatesAutoArranged(false);

	    myProject.removeSelection();
	    ti.getOpd().getSelection().resetSelection();
	    ti.getOpd().getSelection().removeSelection();

	    ti.getOpd().addSelection(ti, false);

	    OpcatLayoutManager myLayoutManager = myProject.getLayoutManager();
	    for (Enumeration enumSelected = ((ObjectInstance) ti)
		    .getStateInstances(); enumSelected.hasMoreElements();) {
		Object currItem = enumSelected.nextElement();
		ConnectionEdgeInstance currInstance = (ConnectionEdgeInstance) currItem;
		currInstance.getOpd().addSelection(currInstance, false);
	    }

	    int x = ti.getX();
	    int y = ti.getY();

	    myLayoutManager.evenSpaceVertically();

	    ti.setLocation(x, y);

	    myProject.removeSelection();
	    ti.getOpd().getSelection().resetSelection();
	    ti.getOpd().getSelection().removeSelection();

	}
    };

    Action propertiesAction = new AbstractAction("Properties",
	    StandardImages.PROPERTIES) {

	/**
         * 
         */
	private static final long serialVersionUID = 1764163162957061291L;

	public void actionPerformed(ActionEvent e) {
	    (ti).getThing().callPropertiesDialog(
		    BaseGraphicComponent.SHOW_ALL_TABS,
		    BaseGraphicComponent.SHOW_ALL_BUTTONS);
	    ThingPopup.this.myProject.getCurrentOpd().getDrawingArea()
		    .repaint();

	}
    };

    Action ConsistencyHelper = new AbstractAction("Consistency Helper",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = 1764163162957061291L;

	public void actionPerformed(ActionEvent e) {

	    ConsistencyResult checkResult = new ConsistencyResult();
	    ConsistencyAbstractChecker checker = (ConsistencyAbstractChecker) (new ConsistencyFactory(
		    ti, myProject, ConsistencyAction.GLOBAL, checkResult))
		    .create();
	    checker.check();
	    if (!checkResult.isConsistent()) {
		checker.deploy(checkResult);
	    }
	}
    };

    Action TestingCenter = new AbstractAction("Test Scenario",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = 1764163162957061291L;

	public void actionPerformed(ActionEvent e) {

	    TestingScenariosPanel panel = ScenarioUtils
		    .getScenaioPanel(new Scenario(ti.getEntry().getName()), (ConnectionEdgeEntry) ti.getEntry());

	    panel.AddToExtensionToolsPanel();
	}
    };

    Action GenerateClassDiagram = new AbstractAction("Generate UML",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
         * performs the XMI generation task. The action will be performed only
         * to objects that have an unfolding OPD.
         * 
         * @author Raanan (modified by Eran)
         */
	public void actionPerformed(ActionEvent e) {
	    // if ((myInstance instanceof ObjectInstance)
	    // && (((ObjectInstance) myInstance).getUnfoldingOpd() != null))
	    // {

	    ObjectInstance objIns = (ObjectInstance) myInstance;
	    DiagramsCreator xmiCreator = new DiagramsCreator();

	    UMLSaver uml_save = new UMLSaver();
	    CustomFileFilter filter = new CustomFileFilter("xmi",
		    "UML XMI Files");
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.addChoosableFileFilter(filter);
	    int ret = fileChooser.showSaveDialog(Opcat2.getFrame());

	    if (ret == JFileChooser.APPROVE_OPTION) {
		String filename = fileChooser.getSelectedFile().getPath();
		StringTokenizer st = new StringTokenizer(filename, ".", false);
		filename = st.nextToken() + ".xmi";

		UserDesicion desicion = new UserDesicion();
		desicion.UClassYes = true;

		try {
		    Vector vec = new Vector();
		    myProject.removeSelection();
		    objIns.getUnfoldingOpd().selectAll();
		    Enumeration myEnum = objIns.getUnfoldingOpd()
			    .getSelectedItems();
		    while (myEnum.hasMoreElements()) {
			Object locObj = myEnum.nextElement();
			if (locObj instanceof Instance) {
			    vec.add(((Instance) locObj).getEntry());
			}
		    }
		    xmiCreator.setBaseEnum(vec.elements());
		    xmiCreator.generateXmiFile((ISystem) Opcat2
			    .getCurrentProject(), filename, desicion);

		} catch (Exception ex) {
		    OpcatLogger.logError(ex);
		}
	    }
	}

	// }
    };

    Action bringToFrontAction = new AbstractAction("Bring To Front",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = -7919648834938175001L;

	public void actionPerformed(ActionEvent e) {
	    OpdBaseComponent obc = ThingPopup.this.ti.getConnectionEdge();
	    obc.bringToFront();
	}
    };

    Action sendToBackAction = new AbstractAction("Send To Back",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = -381895123736075568L;

	public void actionPerformed(ActionEvent e) {
	    OpdBaseComponent obc = ThingPopup.this.ti.getConnectionEdge();
	    obc.sendToBack();
	}
    };

    Action fitToContent = new AbstractAction("Fit To Content",
	    StandardImages.EMPTY) {

	/**
         * 
         */
	private static final long serialVersionUID = 879338001685397770L;

	public void actionPerformed(ActionEvent e) {
	    OpdConnectionEdge oce = ThingPopup.this.ti.getConnectionEdge();
	    oce.fitToContent();

	}
    };

}