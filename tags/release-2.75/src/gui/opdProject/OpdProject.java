package gui.opdProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.print.Book;
import java.awt.print.PrinterJob;
import java.beans.PropertyVetoException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;

import exportedAPI.OpcatConstants;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPI.IConnectionEdgeInstance;
import exportedAPI.opcatAPI.ILinkInstance;
import exportedAPI.opcatAPI.IOpd;
import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPIx.IXCheckResult;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXLinkInstance;
import exportedAPI.opcatAPIx.IXObjectInstance;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXProcessInstance;
import exportedAPI.opcatAPIx.IXRelationInstance;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXSystemStructure;
import exportedAPI.opcatAPIx.IXThingInstance;
import extensionTools.opl2.OPLBuilder;
import extensionTools.reuse.ReuseCommand;
import gui.Opcat2;
import gui.actions.edit.EditAction;
import gui.actions.edit.RedoAction;
import gui.checkModule.CheckModule;
import gui.checkModule.CheckResult;
import gui.checkModule.CommandWrapper;
import gui.controls.EditControl;
import gui.metaLibraries.logic.MetaManager;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opmEntities.Constants;
import gui.opmEntities.OpmAgent;
import gui.opmEntities.OpmAggregation;
import gui.opmEntities.OpmBiDirectionalRelation;
import gui.opmEntities.OpmConditionLink;
import gui.opmEntities.OpmConnectionEdge;
import gui.opmEntities.OpmConsumptionEventLink;
import gui.opmEntities.OpmConsumptionLink;
import gui.opmEntities.OpmEffectLink;
import gui.opmEntities.OpmExceptionLink;
import gui.opmEntities.OpmExhibition;
import gui.opmEntities.OpmFundamentalRelation;
import gui.opmEntities.OpmGeneralRelation;
import gui.opmEntities.OpmInstantination;
import gui.opmEntities.OpmInstrument;
import gui.opmEntities.OpmInstrumentEventLink;
import gui.opmEntities.OpmInvocationLink;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmResultLink;
import gui.opmEntities.OpmSpecialization;
import gui.opmEntities.OpmState;
import gui.opmEntities.OpmStructuralRelation;
import gui.opmEntities.OpmUniDirectionalRelation;
import gui.opx.Saver;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.Entry;
import gui.projectStructure.FundamentalRelationEntry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationEntry;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.GraphicRepresentationKey;
import gui.projectStructure.GraphicalRelationRepresentation;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.LinkPrecedence;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.undo.CompoundUndoAction;
import gui.undo.UndoableAddFundamentalRelation;
import gui.undo.UndoableAddGeneralRelation;
import gui.undo.UndoableAddLink;
import gui.undo.UndoableAddObject;
import gui.undo.UndoableAddProcess;
import gui.undo.UndoableDeleteFundamentalRelation;
import gui.undo.UndoableDeleteGeneralRelation;
import gui.undo.UndoableDeleteLink;
import gui.undo.UndoableDeleteObject;
import gui.undo.UndoableDeleteProcess;
import gui.undo.UndoableDeleteState;
import gui.util.OpcatException;


/**
 * This class represents user's project.
 * <p>
 * This class manages all needed for the project: graphics, internal data
 * structures, adding and deleting components, common OPM operations such as
 * Unfolding and Zooming in, repository manager ,printing.
 * </p>
 */

public class OpdProject implements ISystem, IXSystem {
	
	private String projectName;

	private String projectCreator;

	private Date createDate;

	private long projectId;

	private MainStructure mainComponentsStructure;

	private OpcatLayoutManager layoutManager;

	private long entityMaxEntry;

	private long opdMaxEntry;

	private Opd currentOpd;

	private JDesktopPane parentDesktop;

	private long tempNum;

	private GenericTable config;

	private GenericTable generalInfo;

	private ThingInstance thingToCopy = null;

	private Hashtable thingsToCopy = null;

	private PrinterJob printJob;

	private PageSetupData pageSetupData;

	private OplColorScheme colorScheme;

	private String fileName = null;
	
	public static int CLIPBOARD_ID = -1;
	
	private boolean isPresented = true ; 

	// cut'n'paste:
	// on cut we are doing setVisible(false) for all selected components
	// on paste we paste all components and delete unvisible components
	// cutted variable reseted on copy() or at the end of paste()
	private boolean cutted = false;

	private Opd clipBoard = null;

	/**
	 * If set to <code>true</code>, then OPCAT2 would present the process
	 * name message (the one with the "ing").
	 */
	private boolean showProcessNameMessage = true;

	private final static int DELETE = 100;

	private final static int COPY = 200;

	private final static int CUT = 300;

	private final static int PASTE = 400;

	/**
	 * A list of meta-libraries that are imported, or need to be imported, into
	 * the project.
	 * 
	 * @author Eran Toch
	 */
	private MetaManager metaManager = null;

	/**
	 * Holds the OPX version number, represented by a String. The value is
	 * updated by the loader.
	 */
	private String versionString = "";

	/**
	 * Holds a unique identifier for each of the opcat projects.
	 */
	private String opcatGlobalID = "";
	
	//reuseCommnet
	//-----

	// Hashtables:
	// we have three hash tables , their ohb is to remember which Instance and
	// Entries were
	// entered during the reuse
	// openReused is mentioned so we can delete these instances when we close or
	// save the system.

	// the istances list which are open reused is saved in order to be deleted
	// at save
	// and will be refreshed at each open reuse ( when loading the system )
	private LinkedList openReusedInstanceList = new LinkedList();

	// here we will sa ve the locations of systems matches fofr reuse for these
	// OPD's
	private Hashtable openReusedOpdsTable = new Hashtable();

	public OpdProject reusedProject = null;

	/**
	 * A flag indicating whether merge dragging is on or off. 
	 */
	private boolean enableDragging = false;

	public LinkedList reusedInstancesGlobalList = new LinkedList();

	//the nextfield indicates weather this project is an open-reused
	//project or it is a regular project.
	private int projectType;

	public final static int REGULAR_SYSTEM = 0;

	public final static int OPEN_REUSED_SYSTEM = 1;

	private boolean duringLoad = false;

	//endReuseCommnet

	/**
	 * Creates OpdProject with specified parameters
	 * 
	 * @param parent
	 *            JDesktopPane which contains all OPD belonging to this project
	 * @param opcatTree
	 *            ReporistoryManager of the user's project
	 * @param pId
	 *            id of the user's project. This id have to be unique in
	 *            database.
	 */
	public OpdProject(JDesktopPane parent, long pId) {
		entityMaxEntry = 0;
		opdMaxEntry = 0;
		projectName = new String("NoName");
		projectCreator = new String("NoCreator");
		createDate = (new GregorianCalendar()).getTime();

		mainComponentsStructure = new MainStructure();
		config = new GenericTable("CONFIGURATION");
		initConfig();

		pageSetupData = new PageSetupData();
		colorScheme = new OplColorScheme();
		projectId = pId;

		parentDesktop = parent;
		parentDesktop.setDesktopManager(new OpcatDesktopManager(this));
		clipBoard = new Opd(this, "ClipBoard", -1, null, false);
		generalInfo = new GenericTable("CONFIGURATION");
		layoutManager = new OpcatLayoutManager(this);

		//Creating a MetaManger
		//By Eran Toch
		metaManager = new MetaManager();

		//ReuseCommnet
		// when creating a new project through the c'tor we create it with
		// in a regular mode
		projectType = REGULAR_SYSTEM;
		//endReuseCommnet

		//Setting global unique ID
		opcatGlobalID = generateGlobalID();
		
		//Setting the version number
		setVersionString(Saver.currentVersion);

	}

	private void initConfig() {
		config.setProperty("ThingWidth", new Integer(120));
		config.setProperty("ThingHeight", new Integer(70));
		config.setProperty("MinimalThingWidth", new Integer(60));
		config.setProperty("MinimalThingHeight", new Integer(35));

		config.setProperty("FundamentalRelationWidth", new Integer(36));
		config.setProperty("FundamentalRelationHeight", new Integer(31));

		config.setProperty("StateWidth", new Integer(60));
		config.setProperty("StateHeight", new Integer(35));

		config.setProperty("OPDWidth", new Integer(1300));
		config.setProperty("OPDHeight", new Integer(860));
		config.setProperty("DraggerSize", new Integer(36));

		config.setProperty("ThingFont", new Font("OurFont", Font.PLAIN, 16));
		config.setProperty("URLFont", new Font("OurFont", Font.BOLD + Font.ITALIC, 16));
		config.setProperty("StateFont", new Font("OurFont", Font.PLAIN, 12));
		config.setProperty("LineFont", new Font("OurFont", Font.PLAIN, 11));
		config.setProperty("LabelFont", new Font("OurFont", Font.PLAIN, 11));
		config.setProperty("LinkFont", new Font("OurFont", Font.PLAIN, 12));

		config.setProperty("SmallFont", new Font("OurFont", Font.PLAIN, 11));

		config.setProperty("BackgroundColor", new Color(230, 230, 230));
		// kind of gray
		config.setProperty("TextColor", Color.black);
		config.setProperty("UrlColor", Color.RED);
		config.setProperty("ObjectColor", new Color(0, 110, 0));
		// kind of green
		config.setProperty("ProcessColor", new Color(0, 0, 170));
		// kind of blue

		config.setProperty("StateColor", new Color(91, 91, 0));
		// kind of brown

		config.setProperty("NormalSize", new Integer(5));
		config.setProperty("CurrentSize", new Integer(4));

	}

	/**
	 * Creates a global ID, which is generated using a random number generator.
	 * The method takes the time in millisecods, use it as a random seed, and
	 * creates a string which is a concatination of the first number and the
	 * second, seperated by a dot (".").
	 * 
	 * @return
	 */
	protected String generateGlobalID() {
		Calendar rightNow = Calendar.getInstance();
		long nowMillis = rightNow.getTimeInMillis();
		Random generator = new Random(nowMillis);
		long randomLong = generator.nextLong();
		if (randomLong < 0)	{
			randomLong *= -1;
		}
		String globalID = String.valueOf(randomLong) +"."
				+ String.valueOf(nowMillis);
		return globalID;
	}

	/**
	 * Returns the main OPCAT frame.
	 * 
	 * @return JFrame main OPCAT frame.
	 */
	public JFrame getMainFrame() {
		return Opcat2.getFrame();
	}

	public Dimension getUserAreaSize() {
		//        GenericTable config = myProject.getConfiguration();
		double normalSize = ((Integer) config.getProperty("NormalSize"))
				.doubleValue();
		double currentSize = ((Integer) config.getProperty("CurrentSize"))
				.doubleValue();
		double factor = currentSize / normalSize;
		double width = ((Integer) config.getProperty("OPDWidth")).intValue()
				* factor;
		double height = ((Integer) config.getProperty("OPDHeight")).intValue()
				* factor;
		return new Dimension((int) width, (int) height);
	}

	public void showRootOpd() {
		//tempNum = _getFreeOpdEntry();
		currentOpd = mainComponentsStructure.getOpd(1);

		if (currentOpd == null) {
			tempNum = _getFreeOpdEntry();
			currentOpd = new Opd(this, "SD", tempNum, null);
			mainComponentsStructure.put(tempNum, currentOpd);
		}

		currentOpd.show();
	}

	public void showOPD(long opdNum) {
		Opd tempOPD = mainComponentsStructure.getOpd(opdNum);

		if (tempOPD == null)
			return;

		tempOPD.setVisible(true);
		try {
			tempOPD.getOpdContainer().setSelected(true);
			tempOPD.getOpdContainer().setMaximum(true);
		} catch (java.beans.PropertyVetoException pve) {
			pve.printStackTrace();
		}

	}

	private long _getFreeEntityEntry() {
		entityMaxEntry++;
		return entityMaxEntry;
	}

	/**
	 * Returns an OPD according to its ID, or <code>null</code> if no OPD by this id 
	 * was found.
	 */
	public Opd getOpdByID(long opdId)	{
	    return mainComponentsStructure.getOpd(opdId);
	}
	
	/**
	 * Returns current OPD (user is currently working with this OPD)
	 */
	public Opd getCurrentOpd() {
		return currentOpd;
	}

	public IOpd getCurrentIOpd() {
		return (IOpd) currentOpd;
	}

	public IXOpd getCurrentIXOpd() {
		return (IXOpd) currentOpd;
	}

	public void updateChanges() {
		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
	}

	/**
	 * Sets current OPD
	 */
	public void setCurrentOpd(Opd pOpd) {
		currentOpd = pOpd;
	}

	/**
	 * Returns MainStructure - data structure which holds all inforation about
	 * this project
	 */
	public MainStructure getComponentsStructure() {
		return mainComponentsStructure;
	}

	public ISystemStructure getISystemStructure() {
		return (ISystemStructure) mainComponentsStructure;
	}

	public IXSystemStructure getIXSystemStructure() {
		return (IXSystemStructure) mainComponentsStructure;
	}

	private long _getFreeOpdEntry() {
		opdMaxEntry++;
		return opdMaxEntry;
	}

	/**
	 * Returns project's name
	 */
	public String getName() {
		return projectName;
	}

	/**
	 * Returns String representing this project
	 */
	public String toString() {
		return projectName;
	}

	/**
	 * Returns name of project's creator
	 */

	public String getCreator() {
		return projectCreator;
	}

	/**
	 * Returns project's creaion date
	 */

	public Date getCreationDate() {
		return createDate;
	}

	public String getInfoValue(String key) {
		return (String) generalInfo.getProperty(key);
	}

	public void setInfoValue(String key, String value) {
		generalInfo.setProperty(key, value);
	}

	/**
	 * Returns unique project's id (for save purposes)
	 */
	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long prId) {
		projectId = prId;
	}

	/**
	 * Sets project name
	 */
	public void setName(String name) {
		projectName = name;
	}

	public OplColorScheme getOplColorScheme() {
		return colorScheme;
	}

	public GenericTable getConfiguration() {
		return config;
	}

	public GenericTable getGeneralInfo() {
		return generalInfo;
	}

	/**
	 * Sets project creator name
	 */
	public void setCreator(String creator) {
		projectCreator = creator;
	}

	/**
	 * Sets project creation date
	 */
	public void setCreationDate(Date cDate) {
		createDate = cDate;
	}

	public LinkInstance getLinkBetweenInstances(ConnectionEdgeInstance source,
			ConnectionEdgeInstance destination) {
		for (Enumeration e = source.getRelatedInstances(); e.hasMoreElements();) {
			Object o = (Object) e.nextElement();
			if (o instanceof LinkInstance) {
				LinkInstance tmp = (LinkInstance) o;
				if (tmp.getSource().getOpdKey().equals(
						source.getConnectionEdge().getOpdKey())
						&& tmp.getDestination().getOpdKey().equals(
								destination.getConnectionEdge().getOpdKey())) {
					return tmp;
				}
			}
		}

		return null;
	}

	public IXLinkInstance getIXLinkBetweenInstances(
			IXConnectionEdgeInstance source,
			IXConnectionEdgeInstance destination) {
		return (IXLinkInstance) getLinkBetweenInstances(
				(ConnectionEdgeInstance) source,
				(ConnectionEdgeInstance) destination);
	}

	public ILinkInstance getILinkBetweenInstances(
			IConnectionEdgeInstance source, IConnectionEdgeInstance destination) {
		return (ILinkInstance) getLinkBetweenInstances(
				(ConnectionEdgeInstance) source,
				(ConnectionEdgeInstance) destination);
	}

	public Enumeration getRelationBetweenInstances(
			ConnectionEdgeInstance source, ConnectionEdgeInstance destination) {
		Vector tempVector = new Vector();

		for (Enumeration e = source.getRelatedInstances(); e.hasMoreElements();) {
			Object o = (Object) e.nextElement();
			if (o instanceof GeneralRelationInstance) {
				GeneralRelationInstance tmp = (GeneralRelationInstance) o;
				if (tmp.getSource().getOpdKey().equals(
						source.getConnectionEdge().getOpdKey())
						&& tmp.getDestination().getOpdKey().equals(
								destination.getConnectionEdge().getOpdKey())) {
					tempVector.add(tmp);
				}
			}
		}

		return tempVector.elements();
	}

	public Enumeration getIXRelationsBetweenInstances(
			IXConnectionEdgeInstance source,
			IXConnectionEdgeInstance destination) {
		return getRelationBetweenInstances((ConnectionEdgeInstance) source,
				(ConnectionEdgeInstance) destination);
	}

	public Enumeration getIRelationsBetweenInstances(
			IConnectionEdgeInstance source, IConnectionEdgeInstance destination) {
		return getRelationBetweenInstances((ConnectionEdgeInstance) source,
				(ConnectionEdgeInstance) destination);
	}

	//  public String getOPL(boolean isHTML, long opdNum) {
	//    return generateOPL(isHTML, opdNum);
	//  }

	public String getOPL(boolean isHTML, long opdNum) {
			OPLBuilder myOplBuilder = new OPLBuilder(this, colorScheme);
	
			if (isHTML) {
				return myOplBuilder.getOplHTML(opdNum).toString();
			} else {
				return myOplBuilder.getOplText(opdNum).toString();
			}
	}

	public String getOPL(boolean isHTML) {
		
			OPLBuilder myOplBuilder = new OPLBuilder(this, colorScheme);
	
			if (isHTML) {
	//			long startTime = System.currentTimeMillis();
				String opl = myOplBuilder.getOplHTML().toString();
	//			System.err.println("OPL in "
	//					+ (System.currentTimeMillis() - startTime));
				return opl;
			} else {
				return myOplBuilder.getOplText().toString();
			}
	}

	public StringBuffer getOplXmlScript() {
		OPLBuilder myOplBuilder = new OPLBuilder(this, colorScheme);
		return myOplBuilder.getOplXmlScript();
	}

	public void applyNewBackground(Color newBackgroundColor) {
		Color oldColor = (Color) config.getProperty("BackgroundColor");
		config.setProperty("BackgroundColor", newBackgroundColor);
		for (Enumeration e1 = mainComponentsStructure.getElements(); e1
				.hasMoreElements();) {
			Entry tempEntry = (Entry) e1.nextElement();
			for (Enumeration e2 = tempEntry.getInstances(); e2
					.hasMoreElements();) {
				Instance tempInstance = (Instance) e2.nextElement();
				if (tempInstance.getBackgroundColor().equals(oldColor)) {
					tempInstance.setBackgroundColor(newBackgroundColor);
				}
			}
		}

		for (Enumeration e1 = mainComponentsStructure
				.getGraphicalRepresentations(); e1.hasMoreElements();) {
			GraphicalRelationRepresentation tempRepr = (GraphicalRelationRepresentation) e1
					.nextElement();
			if (tempRepr.getBackgroundColor().equals(oldColor)) {
				tempRepr.setBackgroundColor(newBackgroundColor);
			}
		}

	}

	public void print(boolean showDialog, boolean preview) {

		Book nBook = new Book();
		//      PrinterJob printJob = PrinterJob.getPrinterJob();
		//		PageFormat pf;
		//		pageSetupData = getPrinterJob().defaultPage();
		//		pf.setOrientation(PageFormat.REVERSE_LANDSCAPE);
		//		double h = pf.getWidth();
		//		double w = pf.getHeight();
		//		PrintProperties pp = new PrintProperties();
		//		Paper p = pf.getPaper();
		//		int marginWidth = pp.getMarginWidth();
		//		p.setImageableArea(marginWidth, marginWidth,
		//		w-marginWidth*2, h-marginWidth*2);
		//		pf.setPaper(p);

		TreeMap sortedOpds = new TreeMap();
		LinkedList tempOpds = new LinkedList();
		Hashtable allOpds = new Hashtable();

		boolean change = true;
		long pageNum = 1;

		sortedOpds.put(new Long(pageNum), mainComponentsStructure.getOpd(1));

		while (change) {
			tempOpds.clear();
			change = false;

			for (Enumeration e = mainComponentsStructure.getOpds(); e
					.hasMoreElements();) {
				Opd tOpd = (Opd) e.nextElement();
				if (sortedOpds.containsValue(tOpd.getParentOpd())
						&& !sortedOpds.containsValue(tOpd)) {
					tempOpds.add(tOpd);
					change = true;
				}
			}

			for (Iterator i = tempOpds.listIterator(); i.hasNext();) {
				pageNum++;
				sortedOpds.put(new Long(pageNum), i.next());
			}
		}

		for (Iterator i = sortedOpds.values().iterator(); i.hasNext();) {
			nBook.append((Opd) i.next(), pageSetupData);
		}

		//PrinterJob printJob = PrinterJob.getPrinterJob();
		getPrinterJob().setPageable(nBook);

		if (preview) {
			new PrintPreview(printJob, nBook, pageSetupData);
			return;
		}

		if (showDialog) {
			//			new PrintDialog(Opcat2.getFrame(), "Printing Properties",
			// getPrinterJob(), pf, pp);
			if (!getPrinterJob().printDialog()) {
				//applyNewBackground(oldColor);
				return;
			}
		}

		// else direct print

		try {
			getPrinterJob().print();
		} catch (Exception ex) {
			System.err.println("Exception in printing");
			ex.printStackTrace();
		}
	}

	/** *********************** OWL-S Start *********************** */
	public void zoomOut() {
		currentOpd = getCurrentOpd().getParentOpd();
		currentOpd.setVisible(true);
		try {
			currentOpd.getOpdContainer().setSelected(true);
		} catch (PropertyVetoException e) {
		}
	}

	/** *********************** OWL-S End *********************** */

	public Opd zoomIn(ThingInstance parentInstance) {
		ThingEntry myEntry;

		ThingInstance newInstance;
		Opd oldCurrentOpd = currentOpd;
		Opd parent = parentInstance.getOpd();

		myEntry = (ThingEntry) parentInstance.getEntry();

		currentOpd = myEntry.getZoomedInOpd();

		if (currentOpd == null) {

			Opcat2.getUndoManager().discardAllEdits();
			Opcat2.setRedoEnabled(false);
			Opcat2.setUndoEnabled(false);

			tempNum = _getFreeOpdEntry();
			currentOpd = new Opd(this, myEntry.getName() + " - In-Zooming OPD",
					tempNum, parent);
			//      currentOpd.show();
			mainComponentsStructure.put(tempNum, currentOpd);
			myEntry.setZoomedInOpd(currentOpd);

			if (myEntry instanceof ProcessEntry) {
				newInstance = addProcess(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), myEntry.getId(), -1);
			} else {
				newInstance = addObject(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), myEntry.getId(), -1, true);
			}

			newInstance.setZoomedIn(true);
			((OpdThing) (newInstance.getConnectionEdge())).setTextPosition("N");

			Integer nSize = (Integer) config.getProperty("NormalSize");
			Integer cSize = (Integer) config.getProperty("CurrentSize");

			double factor = cSize.doubleValue() / nSize.doubleValue();

			newInstance.setLocation((int) (225.0 * factor),
					(int) (170.0 * factor));
			newInstance.setSize((int) (570.0 * factor), (int) (475.0 * factor));
			if (newInstance instanceof ObjectInstance) {
				((OpdObject) newInstance.getThing()).drawStates();
			}
			drawLinkedThings(newInstance, parentInstance);
			myEntry.updateInstances();

			currentOpd.setMainEntry(myEntry);
			currentOpd.setMainInstance(newInstance);
			setNames4OPDs();
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}

		Opd retOpd = currentOpd;
		currentOpd = oldCurrentOpd;
		return retOpd;
	}


	public void unfolding() {
		ThingEntry myEntry;
		ThingInstance myInstance;
		ThingInstance newInstance;
		long thingId;
		Opd parent = currentOpd;
		Hashtable addedInstances = new Hashtable();

		Integer nSize = (Integer) config.getProperty("NormalSize");
		Integer cSize = (Integer) config.getProperty("CurrentSize");

		double factor = cSize.doubleValue() / nSize.doubleValue();

		Instance tempComp = currentOpd.getSelectedItem();
		if (tempComp == null || !(tempComp instanceof ThingInstance)) {
			return;
		}

		myInstance = (ThingInstance) tempComp;

		if (myInstance.getUnfoldingOpd() == null) {
			int retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
					"A new OPD will be created\n" + "Do you want to continue?",
					"Unfolding", JOptionPane.YES_NO_OPTION);

			if (retValue != JOptionPane.YES_OPTION)
				return;
		}

		int retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
				"Do you want to bring related things?", "Unfolding",
				JOptionPane.YES_NO_OPTION);

		currentOpd.removeSelection();
		currentOpd = unfolding(myInstance, (retValue == JOptionPane.YES_OPTION));

		currentOpd.setVisible(true);
		parentDesktop.getDesktopManager().deiconifyFrame(
				currentOpd.getOpdContainer());

		try {
			currentOpd.getOpdContainer().setSelected(true);
		} catch (PropertyVetoException e) {
		}
	}

	public Opd unfolding(ThingInstance parentInstance,
			boolean bringRelatedThings) {
		ThingEntry myEntry;
		ThingInstance newInstance;
		long thingId;
		Opd parent = currentOpd;
		Hashtable addedInstances = new Hashtable();

		Integer nSize = (Integer) config.getProperty("NormalSize");
		Integer cSize = (Integer) config.getProperty("CurrentSize");

		double factor = cSize.doubleValue() / nSize.doubleValue();

		OpdThing unfoldedThing = parentInstance.getThing();

		thingId = unfoldedThing.getEntity().getId();
		myEntry = (ThingEntry) parentInstance.getEntry();

		Opd oldCurrentOpd = currentOpd;
		currentOpd = parentInstance.getUnfoldingOpd();

		if (currentOpd == null) {
			Opcat2.getUndoManager().discardAllEdits();
			Opcat2.setRedoEnabled(false);
			Opcat2.setUndoEnabled(false);

			tempNum = _getFreeOpdEntry();
			currentOpd = new Opd(this, unfoldedThing + "  -  Unfolding OPD",
					tempNum, parent);
			//      currentOpd.show();
			mainComponentsStructure.put(tempNum, currentOpd);
			parentInstance.setUnfoldingOpd(currentOpd);

			if (myEntry instanceof ProcessEntry) {
				newInstance = addProcess(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), thingId, -1);
			} else {
				newInstance = addObject(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), thingId, -1, true);
			}

			newInstance.setLocation((int) (350 * factor), (int) (20 * factor));
			newInstance.getConnectionEdge().fitToContent();

			currentOpd.setMainEntry(myEntry);
			currentOpd.setMainInstance(newInstance);

			if (bringRelatedThings) {
				drawRelatedThings(newInstance, addedInstances, true, 1,
						new Hashtable());
			}

			setNames4OPDs();
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			myEntry.updateInstances();
		}

		Opd retOpd = currentOpd;
		currentOpd = oldCurrentOpd;
		return retOpd;
	}

	/**
	 * Closes this project. This operation frees all resources used by this
	 * project.
	 */
	public void close() {
		for (Enumeration e = mainComponentsStructure.getOpds(); e
				.hasMoreElements();) {
			((Opd) e.nextElement()).dispose();
		}
		Opcat2.getUndoManager().discardAllEdits();
		Opcat2.setRedoEnabled(false);
		Opcat2.setUndoEnabled(false);
	}

	private void drawRelatedThings(ThingInstance root,
			Hashtable addedInstances, boolean bringGenRel, int level,
			Hashtable layouts) {
		//		OpmThing lRoot = (OpmThing)rootThing.getEntity();
		OpdThing rootThing = (OpdThing) root.getConnectionEdge();
		OpdThing gRelatedThing;

		OpmStructuralRelation lRelation;
		ThingEntry rootEntry;
		ThingEntry newEntry;
		ThingInstance newInstance;

		rootEntry = (ThingEntry) root.getEntry();
		double x, y;

		Integer nSize = (Integer) config.getProperty("NormalSize");
		Integer cSize = (Integer) config.getProperty("CurrentSize");

		double factor = cSize.doubleValue() / nSize.doubleValue();

		y = level * 250 * factor;

		Integer tempX = (Integer) layouts.get(new Integer(level));
		if (tempX == null) {
			x = 100 * factor;
		} else {
			x = tempX.intValue();
		}
		
		for (Enumeration e = rootEntry.getSourceRelations(); e
				.hasMoreElements();) {
			RelativeConnectionPoint sourcePoint = new RelativeConnectionPoint(
					OpcatConstants.S_BORDER, 0.5);
			RelativeConnectionPoint targetPoint = new RelativeConnectionPoint(
					OpcatConstants.N_BORDER, 0.5);

			lRelation = (OpmFundamentalRelation) (e.nextElement());

			newEntry = (ThingEntry) (mainComponentsStructure.getEntry(lRelation
					.getDestinationId()));

			long thingId = newEntry.getId();

			newInstance = (ThingInstance) addedInstances.get(new Long(thingId));

			if (newInstance == null) {
				if (newEntry instanceof ProcessEntry) {
					newInstance = addProcess(0, 0, currentOpd.getOpdContainer()
							.getDrawingArea(), thingId, -1);
				}
				//NOTE
				else if (newEntry instanceof ObjectEntry) {
					newInstance = addObject(0, 0, currentOpd.getOpdContainer()
							.getDrawingArea(), thingId, -1, true);
				} 
				addedInstances.put(new Long(thingId), newInstance);
				newInstance.setLocation((int) x, (int) y);
				newInstance.getConnectionEdge().fitToContent();
				x += 160 * factor;
				drawRelatedThings(newInstance, addedInstances, false,
						level + 1, layouts);
			}

			gRelatedThing = newInstance.getThing();
			addRelation(rootThing, sourcePoint, gRelatedThing, targetPoint,
					Constants.getType4Relation(lRelation), rootThing
							.getParent(), -1, -1);
		}

		if (bringGenRel) {
			for (Iterator i = rootEntry.getSourceGeneralRelations(); i
					.hasNext();) {

				lRelation = (OpmGeneralRelation) (i.next());
				newEntry = (ThingEntry) (mainComponentsStructure
						.getEntry(lRelation.getDestinationId()));

				long thingId = newEntry.getId();

				newInstance = (ThingInstance) addedInstances.get(new Long(
						thingId));

				if (newInstance == null) {

					if (newEntry instanceof ProcessEntry) {
						newInstance = addProcess(0, 0, currentOpd
								.getOpdContainer().getDrawingArea(), thingId,
								-1);
					} else if (newEntry instanceof ObjectEntry) {
						newInstance = addObject(0, 0, currentOpd
								.getOpdContainer().getDrawingArea(), thingId,
								-1, true);
					} 
					addedInstances.put(new Long(thingId), newInstance);
					newInstance.setLocation((int) x, (int) y);
					newInstance.getConnectionEdge().fitToContent();
					x += 160 * factor;
				}

				gRelatedThing = newInstance.getThing();

				RelativeConnectionPoint sourcePoint = GraphicsUtils
						.calculateConnectionPoint(rootThing, gRelatedThing);
				RelativeConnectionPoint targetPoint = GraphicsUtils
						.calculateConnectionPoint(gRelatedThing, rootThing);

				GeneralRelationInstance gi = addGeneralRelation(rootThing,
						sourcePoint, gRelatedThing, targetPoint, rootThing
								.getParent(), Constants
								.getType4Relation(lRelation),
						lRelation.getId(), -1);

				gi.update();
			}
		}

		layouts.put(new Integer(level), new Integer((int) x));
		return;

	}

	private boolean _isSource4link(ThingInstance ti, LinkInstance li) {
		return (ti.getEntry().getId() == ((LinkEntry) (li.getEntry()))
				.getSourceId());
	}

	private void drawLinkedThings(ThingInstance rootInstance,
			ThingInstance originInstance) {
		Vector sourceLinks = new Vector();
		Vector destinationLinks = new Vector();
		Hashtable addedInstances = new Hashtable();

		for (Enumeration e = originInstance.getRelatedInstances(); e
				.hasMoreElements();) {
			Object currInstance = e.nextElement();

			if (currInstance instanceof LinkInstance) {
				if (_isSource4link(originInstance, (LinkInstance) currInstance)) {
					sourceLinks.add(currInstance);
				} else {
					destinationLinks.add(currInstance);
				}
			}
		}

		ThingInstance originParent = (ThingInstance) originInstance
				.getParentIXThingInstance();

		if (originParent != null) {
			for (Enumeration e2 = originParent.getRelatedInstances(); e2
					.hasMoreElements();) {

				Instance currInstance = (Instance) e2.nextElement();
				if (currInstance instanceof LinkInstance) {
					OpmProceduralLink currLink = (OpmProceduralLink) currInstance
							.getEntry().getLogicalEntity();

					CommandWrapper cw;
					if (_isSource4link(originParent,
							(LinkInstance) currInstance)) {
						cw = new CommandWrapper(
								rootInstance.getEntry().getId(), currLink
										.getDestinationId(), Constants
										.getType4Link(currLink), this);
					} else {
						cw = new CommandWrapper(currLink.getSourceId(),
								rootInstance.getEntry().getId(), Constants
										.getType4Link(currLink), this);
					}

					CheckResult cr = CheckModule.checkCommand(cw);
					if (cr.getResult() == IXCheckResult.RIGHT) {
						if (_isSource4link(originParent,
								(LinkInstance) currInstance)) {
							sourceLinks.add(currInstance);
						} else {
							destinationLinks.add(currInstance);
						}
					}

				}
			}
		}

		for (int i = 0; i < sourceLinks.size(); i++) {
			_drawElement(rootInstance, (LinkInstance) sourceLinks.get(i), true,
					i, sourceLinks.size() + destinationLinks.size(),
					addedInstances);
		}

		try {
			for (int i = 0; i < destinationLinks.size(); i++) {
				_drawElement(rootInstance, (LinkInstance) destinationLinks
						.get(i), false, i + sourceLinks.size(), sourceLinks
						.size()
						+ destinationLinks.size(), addedInstances);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//		_drawElements(sourceLinks, addedInstances, rootThing, 0,
		// sourceLinks.size()+destinationLinks.size(), true);
		//		_drawElements(destinationLinks, addedInstances, rootThing,
		// sourceLinks.size(), sourceLinks.size()+destinationLinks.size(),
		// false);

	}

	private void _drawElement(ThingInstance rootInstance,
			LinkInstance addedLink, boolean isSource, int numInCircle,
			int numOfLinks, Hashtable addedInstances) {
		//        OpmProceduralLink lLink;
		ThingInstance newInstance = null;
		OpdConnectionEdge gRelatedThing = null;
		ConnectionEdgeEntry newEntry = null;

		Integer nSize = (Integer) config.getProperty("NormalSize");
		Integer cSize = (Integer) config.getProperty("CurrentSize");
		double factor = cSize.doubleValue() / nSize.doubleValue();

		LinkEntry addedLinkEntry = (LinkEntry) addedLink.getEntry();
		int linkType = addedLinkEntry.getLinkType();

		if (isSource) {
			newEntry = (ConnectionEdgeEntry) mainComponentsStructure
					.getEntry(addedLinkEntry.getDestinationId());
		} else {
			newEntry = (ConnectionEdgeEntry) (mainComponentsStructure
					.getEntry(addedLinkEntry.getSourceId()));
		}

		long thingId = newEntry.getId();
		newInstance = (ThingInstance) addedInstances.get(new Long(thingId));

		if (newEntry instanceof ProcessEntry) {
			if (newInstance == null) {
				newInstance = addProcess(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), thingId, -1);
				addedInstances.put(new Long(thingId), newInstance);
			}
			gRelatedThing = newInstance.getThing();
		}

		if (newEntry instanceof ObjectEntry) {
			if (newInstance == null) {
				newInstance = addObject(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), thingId, -1, true);
				addedInstances.put(new Long(thingId), newInstance);
			}
			gRelatedThing = newInstance.getThing();
		} 

		if (newEntry instanceof StateEntry) {
			StateEntry se = (StateEntry) newEntry;
			thingId = se.getParentObjectId();

			newInstance = (ThingInstance) addedInstances.get(new Long(thingId));
			if (newInstance == null) {
				newInstance = addObject(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), thingId, -1, true);
				addedInstances.put(new Long(thingId), newInstance);
			}

			for (Enumeration e = ((ObjectInstance) newInstance)
					.getStateInstances(); e.hasMoreElements();) {
				StateInstance currState = (StateInstance) e.nextElement();
				if (currState.getEntry().getId() == se.getId()) {
					gRelatedThing = currState.getConnectionEdge();
					break;
				}
			}

		}

		int size = (numOfLinks + 1) / 2;
		int currAngle = numInCircle / 2 + 1;
		double k = Math.tan(((Math.PI - 0.0001) * currAngle) / size + Math.PI
				/ 4);
		double a2 = rootInstance.getWidth() / 2 + 140 * factor;
		a2 = a2 * a2;
		double b2 = rootInstance.getHeight() / 2 + 140 * factor;
		b2 = b2 * b2;
		double x = (Math.sqrt((a2 * b2) / (b2 + a2 * k * k)));

		if (numInCircle % 2 == 0) {
			x = -1 * x;
		}

		double y = k * x;

		newInstance.getConnectionEdge().fitToContent();

		x = x + rootInstance.getX() + rootInstance.getWidth() / 2
				- newInstance.getWidth() / 2;
		y = y + rootInstance.getY() + rootInstance.getHeight() / 2
				- newInstance.getHeight() / 2;
		newInstance.setLocation((int) x, (int) y);

		// Extra check for link legality is for avoiding of links duplications
		if (isSource) {

			CommandWrapper cw = new CommandWrapper(rootInstance.getEntry()
					.getId(), rootInstance.getKey(), gRelatedThing.getEntity()
					.getId(), new OpdKey(gRelatedThing.getOpdId(),
					gRelatedThing.getEntityInOpdId()), linkType, this);

			CheckResult cr = CheckModule.checkCommand(cw);
			if (cr.getResult() == IXCheckResult.RIGHT) {
				RelativeConnectionPoint sourcePoint = GraphicsUtils
						.calculateConnectionPoint(rootInstance
								.getConnectionEdge(), gRelatedThing);
				RelativeConnectionPoint targetPoint = GraphicsUtils
						.calculateConnectionPoint(gRelatedThing, rootInstance
								.getConnectionEdge());

				addLink(rootInstance.getConnectionEdge(), sourcePoint,
						gRelatedThing, targetPoint, rootInstance
								.getConnectionEdge().getParent(), linkType, -1,
						-1);
			}
		} else {

			CommandWrapper cw = new CommandWrapper(gRelatedThing.getEntity()
					.getId(), new OpdKey(gRelatedThing.getOpdId(),
					gRelatedThing.getEntityInOpdId()), rootInstance.getEntry()
					.getId(), rootInstance.getKey(), linkType, this);

			CheckResult cr = CheckModule.checkCommand(cw);
			if (cr.getResult() == IXCheckResult.RIGHT) {
				RelativeConnectionPoint sourcePoint = GraphicsUtils
						.calculateConnectionPoint(gRelatedThing, rootInstance
								.getConnectionEdge());
				RelativeConnectionPoint targetPoint = GraphicsUtils
						.calculateConnectionPoint(rootInstance
								.getConnectionEdge(), gRelatedThing);

				addLink(gRelatedThing, sourcePoint, rootInstance
						.getConnectionEdge(), targetPoint, rootInstance
						.getConnectionEdge().getParent(), linkType, -1, -1);
			}
		}

	}

	/**
	 * Zooms in (graphically - not OPM scaling operation) current OPD
	 */
	public void viewZoomIn(double zoomFactor) {
		for (Enumeration e = mainComponentsStructure.getOpds(); e
				.hasMoreElements();) {
			((Opd) e.nextElement()).viewZoomIn(zoomFactor);
		}
	}

	/**
	 * Zooms out (graphically - not OPM scaling operation) current OPD
	 */

	public void viewZoomOut() {
		if (currentOpd == null)
			return;
		currentOpd.viewZoomOut(1.2);
	}

	/**
	 * Adds selected item to the Hashtable of selected object. (for multiple
	 * selection)
	 */
	public void addSelection(Instance sItem, boolean removePrevious) {
		if (currentOpd == null)
			return;
		currentOpd.addSelection(sItem, removePrevious);
	}

	public void addSelection(BaseGraphicComponent sItem, boolean removePrevious) {
		if (currentOpd == null)
			return;
		currentOpd.addSelection(sItem, removePrevious);
	}

	/**
	 * Clears Hashtable of selected object
	 */

	public void removeSelection() {
		if (currentOpd == null)
			return;
		currentOpd.removeSelection();
	}

	public void removeSelection(Instance inst) {
		currentOpd.removeSelection(inst);
	}

	public IXObjectInstance addObject(String name, int x, int y, long opdId) {
		
		Opd tempOpd = mainComponentsStructure.getOpd(opdId);

		if (tempOpd == null)
			return null;
		if (currentOpd == null || currentOpd.getOpdId() != opdId) {
			showOPD(opdId);
		}

		IXObjectInstance newIns = (IXObjectInstance) addObject(x, y, tempOpd
				.getDrawingArea(), -1, -1, false);
		newIns.getIXEntry().setName(name);

		((ObjectInstance) newIns).getConnectionEdge().fitToContent();
		newIns.setLocation(x, y);
		newIns.getIXEntry().updateInstances();
		return newIns;

	}

	public IXObjectInstance addObject(String name, int x, int y,
			IXThingInstance parentInstance) {
		if (!parentInstance.isZoomedIn())
			return null;

		Opd tempOpd = mainComponentsStructure.getOpd(parentInstance.getKey()
				.getOpdId());

		if (tempOpd == null)
			return null;
		if (currentOpd == null || currentOpd.getOpdId() != tempOpd.getOpdId()) {
			showOPD(tempOpd.getOpdId());
		}

		Entry tempEntry = mainComponentsStructure.getEntry(parentInstance
				.getLogicalId());
		ThingInstance tempInstance = (ThingInstance) tempEntry
				.getInstance(parentInstance.getKey());
		IXObjectInstance newIns = (IXObjectInstance) addObject(x, y,
				tempInstance.getThing(), -1, -1, false);
		newIns.getIXEntry().setName(name);
		newIns.getIXEntry().updateInstances();
		return newIns;

	}

	public UndoableDeleteObject deleteObject(ObjectInstance delInstance) {
		UndoableDeleteObject ud = new UndoableDeleteObject(this, delInstance);
		ThingEntry selectedEntry = (ThingEntry) delInstance.getEntry();

		for (Enumeration e = delInstance.getStateInstances(); e
				.hasMoreElements();) {
			StateInstance si = (StateInstance) e.nextElement();
			StateEntry se = (StateEntry) si.getEntry();

			se.removeInstance(si.getKey());

			if (se.isEmpty()) {
				mainComponentsStructure.removeEntry(se.getId());
			}
		}

		selectedEntry.removeInstance(delInstance.getKey());

		if (selectedEntry.isEmpty()) {
			mainComponentsStructure.removeEntry(selectedEntry.getId());
		}
		return ud;
	}


	public IXProcessInstance addProcess(String name, int x, int y, long opdId) {
		Opd tempOpd = mainComponentsStructure.getOpd(opdId);

		if (tempOpd == null)
			return null;

		if (currentOpd == null || currentOpd.getOpdId() != opdId) {
			showOPD(opdId);
		}

		IXProcessInstance newIns = (IXProcessInstance) addProcess(x, y, tempOpd
				.getDrawingArea(), -1, -1);
		newIns.getIXEntry().setName(name);
		((ProcessInstance) (newIns)).getConnectionEdge().fitToContent();
		newIns.setLocation(x, y);
		newIns.getIXEntry().updateInstances();
		return newIns;
	}

	public IXProcessInstance addProcess(String name, int x, int y,
			IXThingInstance parentInstance) {
		if (!parentInstance.isZoomedIn())
			return null;

		Opd tempOpd = mainComponentsStructure.getOpd(parentInstance.getKey()
				.getOpdId());

		if (tempOpd == null)
			return null;
		if (currentOpd == null || currentOpd.getOpdId() != tempOpd.getOpdId()) {
			showOPD(tempOpd.getOpdId());
		}

		Entry tempEntry = mainComponentsStructure.getEntry(parentInstance
				.getLogicalId());
		ThingInstance tempInstance = (ThingInstance) tempEntry
				.getInstance(parentInstance.getKey());
		IXProcessInstance newIns = (IXProcessInstance) addProcess(x, y,
				tempInstance.getThing(), -1, -1);
		newIns.getIXEntry().setName(name);
		newIns.getIXEntry().updateInstances();
		return newIns;
	}

	public UndoableDeleteProcess deleteProcess(ProcessInstance delInstance) {

		UndoableDeleteProcess ud = new UndoableDeleteProcess(this, delInstance);
		ThingEntry selectedEntry = (ThingEntry) delInstance.getEntry();

		selectedEntry.removeInstance(delInstance.getKey());

		if (selectedEntry.isEmpty()) {
			mainComponentsStructure.removeEntry(selectedEntry.getId());
		}

		return ud;
	}

	private Point computeRelationPoint(OpdBaseComponent source,
			OpdBaseComponent destination) {

		int tempX, tempY;
		int relWidth = ((Integer) config
				.getProperty("FundamentalRelationWidth")).intValue();
		int relHeight = ((Integer) config
				.getProperty("FundamentalRelationHeight")).intValue();

		tempX = (source.getX() + source.getWidth() / 2 + destination.getX() + destination
				.getWidth() / 2)
				/ 2 - relWidth / 2;
		tempY = (source.getY() + source.getHeight() / 2 + destination.getY() + destination
				.getHeight() / 2)
				/ 2 - relHeight / 2;
		//        if (source.getX()+source.getWidth() < destination.getX())
		//        {
		//            tempX = ((source.getX()+source.getWidth()+destination.getX())/2);
		//        }
		//        else
		//        {
		//            tempX =
		// ((source.getX()+destination.getWidth()+destination.getX())/2);
		//        }
		//
		//        if (source.getY()+source.getHeight() < destination.getY())
		//        {
		//            tempY = ((source.getY()+source.getHeight()+destination.getY())/2);
		//        }
		//        else
		//        {
		//            tempY =
		// ((source.getY()+destination.getHeight()+destination.getY())/2);
		//        }

		return new Point(tempX, tempY);
	}

	private OpmFundamentalRelation getRelation4type(int type, long id,
			OpmConnectionEdge source, OpmConnectionEdge destination) {
		switch (type) {
		case OpcatConstants.AGGREGATION_RELATION:
			return new OpmAggregation(id, "Aggregation " + id, source,
					destination);
		case OpcatConstants.EXHIBITION_RELATION:
			return new OpmExhibition(id, "Featuring " + id, source, destination);
		case OpcatConstants.INSTANTINATION_RELATION:
			return new OpmInstantination(id, "Instantination " + id, source,
					destination);
		case OpcatConstants.SPECIALIZATION_RELATION:
			return new OpmSpecialization(id, "Generalization " + id, source,
					destination);

		default: {
			JOptionPane
					.showMessageDialog(
							Opcat2.getFrame(),
							" Serious internal bug occured in AddRelation function \n Please contact software developers.",
							"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
			return (OpmFundamentalRelation) null;
		}
		}
	}

	public IXRelationInstance addRelation(IXConnectionEdgeInstance source,
			IXConnectionEdgeInstance destination, int relationType) {
		Opd tOpd = mainComponentsStructure.getOpd(source.getKey().getOpdId());

		if (tOpd == null)
			return null;
		if ((!OpcatConstants.isRelation(relationType))
				|| (tOpd.getOpdId() != destination.getKey().getOpdId()))
			return null;

		if (currentOpd == null || currentOpd.getOpdId() != tOpd.getOpdId()) {
			showOPD(tOpd.getOpdId());
		}

		Entry sourceEntry = mainComponentsStructure.getEntry(source
				.getLogicalId());
		ConnectionEdgeInstance sourceInstance = (ConnectionEdgeInstance) sourceEntry
				.getInstance(source.getKey());

		Entry destinationEntry = mainComponentsStructure.getEntry(destination
				.getLogicalId());
		ConnectionEdgeInstance destinationInstance = (ConnectionEdgeInstance) destinationEntry
				.getInstance(destination.getKey());

		RelativeConnectionPoint sourcePoint = GraphicsUtils
				.calculateConnectionPoint(sourceInstance.getConnectionEdge(),
						destinationInstance.getConnectionEdge());
		RelativeConnectionPoint destinationPoint = GraphicsUtils
				.calculateConnectionPoint(destinationInstance
						.getConnectionEdge(), sourceInstance
						.getConnectionEdge());

		JLayeredPane tempContainer = (JLayeredPane) tOpd.getDrawingArea();

		if (sourceInstance.getConnectionEdge().getParent() == destinationInstance
				.getConnectionEdge().getParent()) {
			tempContainer = (JLayeredPane) sourceInstance.getConnectionEdge()
					.getParent();
		}

		if (relationType == OpcatConstants.BI_DIRECTIONAL_RELATION
				|| relationType == OpcatConstants.UNI_DIRECTIONAL_RELATION) {
			return (IXRelationInstance) addGeneralRelation(sourceInstance
					.getConnectionEdge(), sourcePoint, destinationInstance
					.getConnectionEdge(), destinationPoint, tempContainer,
					relationType, -1, -1);
		} else {
			return (IXRelationInstance) addRelation(sourceInstance
					.getConnectionEdge(), sourcePoint, destinationInstance
					.getConnectionEdge(), destinationPoint, relationType,
					tempContainer, -1, -1);
		}

	}

	public IXLinkInstance addLink(IXConnectionEdgeInstance source,
			IXConnectionEdgeInstance destination, int linkType) {
		Opd tOpd = mainComponentsStructure.getOpd(source.getKey().getOpdId());

		if (tOpd == null)
			return null;
		if ((!OpcatConstants.isLink(linkType))
				|| (tOpd.getOpdId() != destination.getKey().getOpdId()))
			return null;

		if (currentOpd == null || currentOpd.getOpdId() != tOpd.getOpdId()) {
			showOPD(tOpd.getOpdId());
		}

		Entry sourceEntry = mainComponentsStructure.getEntry(source
				.getLogicalId());
		ConnectionEdgeInstance sourceInstance = (ConnectionEdgeInstance) sourceEntry
				.getInstance(source.getKey());

		Entry destinationEntry = mainComponentsStructure.getEntry(destination
				.getLogicalId());
		ConnectionEdgeInstance destinationInstance = (ConnectionEdgeInstance) destinationEntry
				.getInstance(destination.getKey());

		RelativeConnectionPoint sourcePoint = GraphicsUtils
				.calculateConnectionPoint(sourceInstance.getConnectionEdge(),
						destinationInstance.getConnectionEdge());
		RelativeConnectionPoint destinationPoint = GraphicsUtils
				.calculateConnectionPoint(destinationInstance
						.getConnectionEdge(), sourceInstance
						.getConnectionEdge());

		JLayeredPane tempContainer = (JLayeredPane) tOpd.getDrawingArea();

		if (sourceInstance.getConnectionEdge().getParent() == destinationInstance
				.getConnectionEdge().getParent()) {
			tempContainer = (JLayeredPane) sourceInstance.getConnectionEdge()
					.getParent();
		}

		return (IXLinkInstance) addLink(sourceInstance.getConnectionEdge(),
				sourcePoint, destinationInstance.getConnectionEdge(),
				destinationPoint, tempContainer, linkType, -1, -1);
	}

	/**
	 * Adds procedural link between source and destination to the specified
	 * container. linkType specifies which type of procedural link we add.
	 * 
	 * @param source
	 *            source thing for this procedural link.
	 * @param side1 -
	 *            the side of source component. Possible values are defined as
	 *            constants in file - OpdBaseComponent.
	 * @param param1
	 *            Relative coordinate of the point to which we connect this link
	 *            on the source component. Float number between 0 and 1 that
	 *            means which point (relatively) is connection point. In order
	 *            to get absolute point of connection(in coordinates of
	 *            connected object) just multiply the length of connection side
	 *            by this number.
	 * @param destination
	 *            destination thing for this procedural link.
	 * @param side2 -
	 *            the side of destination component. Possible values are defined
	 *            as constants in file - OpdBaseComponent.
	 * @param param2
	 *            Relative coordinate of the point to which we connect this link
	 *            on the destination component. Float number between 0 and 1
	 *            that means which point (relatively) is connection point. In
	 *            order to get absolute point of connection(in coordinates of
	 *            connected object) just multiply the length of connection side
	 *            by this number.
	 * @param linkType
	 *            one of constants defined in Interface OpmEntities.Constants
	 *            specifying which kind of procedural link will be added.
	 * @param container
	 *            Container to which we add this procedural link
	 */
	private LinkInstance _addLink(OpdConnectionEdge source,
			RelativeConnectionPoint pPoint1, OpdConnectionEdge destination,
			RelativeConnectionPoint pPoint2, Container container, int linkType,
			long entityId, long entityInOpdId) {
		if (linkType == OpcatConstants.EFFECT_LINK
				&& source instanceof OpdProcess) {
			return _addLink(destination, pPoint2, source, pPoint1, container,
					linkType, entityId, entityInOpdId);
		}

		OpmProceduralLink lLink = null;
		LinkEntry myEntry;
		LinkInstance myInstance;
		ConnectionEdgeEntry sourceEntry, destinationEntry;

		long linkId;
		long linkInOpdId;
		OpdKey instanceId;

		long sourceId = source.getEntity().getId();
		long destinationId = destination.getEntity().getId();

		sourceEntry = (ConnectionEdgeEntry) (mainComponentsStructure
				.getEntry(sourceId));
		destinationEntry = (ConnectionEdgeEntry) (mainComponentsStructure
				.getEntry(destinationId));

		if (entityId != -1) {
			myEntry = (LinkEntry) mainComponentsStructure.getEntry(entityId);
			lLink = (OpmProceduralLink) myEntry.getLogicalEntity();
		} else {
			lLink = sourceEntry.getLinkByDestination(destinationId, linkType);

			if (lLink == null) {
				linkId = _getFreeEntityEntry();
				lLink = createLink4Type(linkId, linkType, sourceId,
						destinationId);
				myEntry = new LinkEntry(lLink, this);
				mainComponentsStructure.put(lLink.getId(), myEntry);
			} else {
				myEntry = (LinkEntry) mainComponentsStructure.getEntry(lLink
						.getId());
				lLink = (OpmProceduralLink) myEntry.getLogicalEntity();
			}

		}

		sourceEntry.addLinkSource(lLink);
		destinationEntry.addLinkDestination(lLink);

		if (entityInOpdId == -1) {
			linkInOpdId = currentOpd._getFreeEntityInOpdEntry();
		} else {
			linkInOpdId = entityInOpdId;
		}

		instanceId = new OpdKey(currentOpd.getOpdId(), linkInOpdId);
		myInstance = new LinkInstance(source, pPoint1, destination, pPoint2,
				lLink, instanceId, this, container);

		//currentOpd.addLinkInstance(myInstance, container);

		myEntry.addInstance(instanceId, myInstance);
		return myInstance;
	}

	/*
	 * ATTENTION!! this method adds undo redo actions, you shouldnt add it
	 * yourself
	 */
	public LinkInstance addLinkConsistent(OpdConnectionEdge source,
			RelativeConnectionPoint pPoint1, OpdConnectionEdge destination,
			RelativeConnectionPoint pPoint2, Container container, int linkType,
			long entityId, long entityInOpdId) {
		// handle the simplest case nothing Zoomed - In.
		// This case is checked separated from others cos it's fast to check
		// and I suppose these are most cases
		// the first case according to design doc
		if (!LinkPrecedence.isEdgesZoomedIn(source, destination, this)
				&& !LinkPrecedence.hasDifferentContainers(source, destination,
						this)) {
			LinkInstance li = _addLink(source, pPoint1, destination, pPoint2,
					container, linkType, entityId, entityInOpdId);
			Opcat2.getUndoManager().undoableEditHappened(
					new UndoableEditEvent(this, new UndoableAddLink(this, li)));
			Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
			Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
			return li;
		}

		// Now we going to detect which one of other cases we are dealing with
		// Numbers are according to Design doc
		//
		// 2a. Parent OPD link addition, second thing does not appear in child
		// OPD
		// 2b. Parent OPD link addition, second thing appears in child OPD -
		// this case is impossible,
		//     but we'll detect it to show error message or so on.
		// 4a. Child OPD link addition, second thing appears in parent OPD.
		// 4b. Child OPD link addition, second thing doesn’t appear in parent
		// OPD

		// there are 6 possibilities
		// 1. Parent OPD link addition - source has In-Zooming OPD.
		// 2. Parent OPD link addition - destination has In-Zooming OPD.
		// 3. Child OPD link addition - source is In-Zoomed
		// 4. Child OPD link addition - destination is In-Zoomed
		// 5. Child OPD link addition - source is _INSIDE_ In-Zoomed thing
		// 6. Child OPD link addition - destination is _INSIDE_ In-Zoomed thing

		// src will always hold In-Zoomed thing or one that has In-Zooming OPD
		// e. g. problematic thing
		switch (LinkPrecedence.detectLinkActionCase(source, destination, this)) {
		case 1:
			return _addLinkToParentOpd(source, pPoint1, destination, pPoint2,
					container, linkType, entityId, entityInOpdId, false);

		case 2:
			return _addLinkToParentOpd(destination, pPoint2, source, pPoint1,
					container, linkType, entityId, entityInOpdId, true);

		case 3:
			return _addLinkToChildOpd(null, source, pPoint1, destination,
					pPoint2, container, linkType, entityId, entityInOpdId,
					false);

		case 4:
			return _addLinkToChildOpd(null, destination, pPoint2, source,
					pPoint1, container, linkType, entityId, entityInOpdId, true);

		case 5:
			return _addLinkToChildOpd((OpdThing) source.getParent(), source,
					pPoint1, destination, pPoint2, container, linkType,
					entityId, entityInOpdId, false);

		case 6:
			return _addLinkToChildOpd((OpdThing) destination.getParent(),
					destination, pPoint2, source, pPoint1, container, linkType,
					entityId, entityInOpdId, true);

		default:
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Failed to add link.", "Opcat II - Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}

	private OpmProceduralLink createLink4Type(long linkId, int linkType,
			long sourceId, long destinationId) {
		switch (linkType) {
		case OpcatConstants.AGENT_LINK:
			return new OpmAgent(linkId, "Agent " + linkId, sourceId,
					destinationId);
		case OpcatConstants.INSTRUMENT_LINK:
			return new OpmInstrument(linkId, "Instrument " + linkId, sourceId,
					destinationId);
		case OpcatConstants.CONDITION_LINK:
			return new OpmConditionLink(linkId, "Condition " + linkId,
					sourceId, destinationId);
		case OpcatConstants.INSTRUMENT_EVENT_LINK:
			return new OpmInstrumentEventLink(linkId, "Event " + linkId,
					sourceId, destinationId);
		case OpcatConstants.RESULT_LINK:
			return new OpmResultLink(linkId, "Result " + linkId, sourceId,
					destinationId);
		case OpcatConstants.CONSUMPTION_LINK:
			return new OpmConsumptionLink(linkId, "Consumption " + linkId,
					sourceId, destinationId);
		case OpcatConstants.EFFECT_LINK:
			return new OpmEffectLink(linkId, "Effect " + linkId, sourceId,
					destinationId);
		case OpcatConstants.EXCEPTION_LINK:
			return new OpmExceptionLink(linkId, "Exception " + linkId,
					sourceId, destinationId);
		case OpcatConstants.INVOCATION_LINK:
			return new OpmInvocationLink(linkId, "Invocation " + linkId,
					sourceId, destinationId);
		case OpcatConstants.CONSUMPTION_EVENT_LINK:
			return new OpmConsumptionEventLink(linkId, "Instrument Event "
					+ linkId, sourceId, destinationId);

		default: {
			JOptionPane
					.showMessageDialog(
							Opcat2.getFrame(),
							" Serious internal bug occured in AddLink function \n Please contact software developers.",
							"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
			return null;
		}

		}

	}

	public UndoableDeleteLink deleteLink(LinkInstance delInstance) {
		return _deleteLink(delInstance);
	}

	public UndoableDeleteLink deleteLinkConsistent(LinkInstance delInstance) {
		// handle the simplest case nothing Zoomed - In.
		// This case is checked separated from others cos it's fast to check
		OpdConnectionEdge source = delInstance.getSource();
		OpdConnectionEdge destination = delInstance.getDestination();
		if (!LinkPrecedence.isEdgesZoomedIn(source, destination, this)
				&& !LinkPrecedence.hasDifferentContainers(source, destination,
						this)) {
			UndoableDeleteLink li = _deleteLink(delInstance);
			Opcat2.getUndoManager().undoableEditHappened(
					new UndoableEditEvent(this, li));
			Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
			Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
			return li;
		}
		// src will always hold In-Zoomed thing or one that has In-Zooming OPD
		// e. g. problematic thing
		switch (LinkPrecedence.detectLinkActionCase(source, destination, this)) {
		case 1:
			return _deleteLinkFromParentOpd(delInstance);

		case 2:
			return _deleteLinkFromParentOpd(delInstance);

		case 3:
			return _deleteLinkFromChildOpd(delInstance);

		case 4:
			return _deleteLinkFromChildOpd(delInstance);

		case 5:
			return _deleteLinkFromChildOpd(delInstance);

		case 6:
			return _deleteLinkFromChildOpd(delInstance);

		default:
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Failed to delete link.", "Opcat II - Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}

	private UndoableDeleteLink _deleteLink(LinkInstance delInstance) {
		UndoableDeleteLink udl = new UndoableDeleteLink(this, delInstance);

		Entry selectedEntry = delInstance.getEntry();
		selectedEntry.removeInstance(delInstance.getKey());
		
		if (selectedEntry.isEmpty()) {

			ConnectionEdgeEntry sourceEntry, destinationEntry;
			OpmProceduralLink lLink = (OpmProceduralLink) selectedEntry
					.getLogicalEntity();

			long sourceId = delInstance.getSource().getEntity().getId();
			long destinationId = delInstance.getDestination().getEntity()
					.getId();

			sourceEntry = (ConnectionEdgeEntry) (mainComponentsStructure
					.getEntry(sourceId));
			destinationEntry = (ConnectionEdgeEntry) (mainComponentsStructure
					.getEntry(destinationId));

			sourceEntry.removeLinkSource(lLink);
			destinationEntry.removeLinkDestination(lLink);

			mainComponentsStructure.removeEntry(selectedEntry.getId());
		}
		return udl;
	}

	public CheckResult switchLinkEdge(LinkInstance instance,
			OpdConnectionEdge oldEdge, OpdConnectionEdge newEdge,
			RelativeConnectionPoint newPoint) {
		OpdConnectionEdge source;
		OpdConnectionEdge destination;
		RelativeConnectionPoint sourcePoint;
		RelativeConnectionPoint destinationPoint;

		if (instance.getDestination() == oldEdge) {
			source = instance.getSource();
			sourcePoint = new RelativeConnectionPoint(instance
					.getSourceDragger().getSide(), instance.getSourceDragger()
					.getParam());
			destination = newEdge;
			destinationPoint = new RelativeConnectionPoint(newPoint.getSide(),
					newPoint.getParam());
		} else {
			source = newEdge;
			sourcePoint = new RelativeConnectionPoint(newPoint.getSide(),
					newPoint.getParam());
			destination = instance.getDestination();
			destinationPoint = new RelativeConnectionPoint(instance
					.getDestinationDragger().getSide(), instance
					.getDestinationDragger().getParam());
		}

		OpdKey sourceKey = new OpdKey(source.getOpdId(), source
				.getEntityInOpdId());
		OpdKey targetKey = new OpdKey(destination.getOpdId(), destination
				.getEntityInOpdId());

		CommandWrapper cw = new CommandWrapper(source.getEntity().getId(),
				sourceKey, destination.getEntity().getId(), targetKey,
				Constants.getType4Link(instance.getLink()), this);

		CheckResult cr = CheckModule.checkCommand(cw);

		if (cr.getResult() == IXCheckResult.WRONG) {
			return cr;
		}

		CompoundUndoAction undoAction = new CompoundUndoAction();

		Container cn;
		if (source.getParent() == destination.getParent()) {
			cn = source.getParent();
		} else {
			cn = getCurrentOpd().getDrawingArea();
		}

		LinkInstance newInstance = addLink(source, sourcePoint, destination,
				destinationPoint, cn, Constants
						.getType4Link(instance.getLink()), -1, -1);

		undoAction.addAction(new UndoableAddLink(this, newInstance));

		newInstance.getLink().copyPropsFrom(instance.getLink());
		newInstance.copyPropsFrom(instance);
		newInstance.update();

		undoAction.addAction(new UndoableDeleteLink(this, instance));

		Opcat2.getUndoManager().undoableEditHappened(
				new UndoableEditEvent(this, undoAction));
		Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
		Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

		deleteLink(instance);
		removeSelection();
		addSelection(newInstance, true);
		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		return cr;
	}

	public CheckResult switchGeneralRelationEdge(
			GeneralRelationInstance instance, OpdConnectionEdge oldEdge,
			OpdConnectionEdge newEdge, RelativeConnectionPoint newPoint) {
		OpdConnectionEdge source;
		OpdConnectionEdge destination;
		RelativeConnectionPoint sourcePoint;
		RelativeConnectionPoint destinationPoint;

		if (instance.getDestination() == oldEdge) {
			source = instance.getSource();
			sourcePoint = new RelativeConnectionPoint(instance
					.getSourceDragger().getSide(), instance.getSourceDragger()
					.getParam());
			destination = newEdge;
			destinationPoint = new RelativeConnectionPoint(newPoint.getSide(),
					newPoint.getParam());
		} else {
			source = newEdge;
			sourcePoint = new RelativeConnectionPoint(newPoint.getSide(),
					newPoint.getParam());
			destination = instance.getDestination();
			destinationPoint = new RelativeConnectionPoint(instance
					.getDestinationDragger().getSide(), instance
					.getDestinationDragger().getParam());
		}

		OpdKey sourceKey = new OpdKey(source.getOpdId(), source
				.getEntityInOpdId());
		OpdKey targetKey = new OpdKey(destination.getOpdId(), destination
				.getEntityInOpdId());

		CommandWrapper cw = new CommandWrapper(source.getEntity().getId(),
				sourceKey, destination.getEntity().getId(), targetKey,
				Constants.getType4Relation(instance.getGeneralRelation()), this);

		CheckResult cr = CheckModule.checkCommand(cw);

		if (cr.getResult() == IXCheckResult.WRONG) {
			return cr;
		}

		CompoundUndoAction undoAction = new CompoundUndoAction();

		Container cn;
		if (source.getParent() == destination.getParent()) {
			cn = source.getParent();
		} else {
			cn = getCurrentOpd().getDrawingArea();
		}

		GeneralRelationInstance newInstance = addGeneralRelation(source,
				sourcePoint, destination, destinationPoint, cn, Constants
						.getType4Relation(instance.getGeneralRelation()), -1,
				-1);

		newInstance.getGeneralRelation().copyPropsFrom(
				instance.getGeneralRelation());
		newInstance.copyPropsFrom(instance);
		newInstance.update();

		undoAction.addAction(new UndoableAddGeneralRelation(this, newInstance));
		undoAction.addAction(new UndoableDeleteGeneralRelation(this, instance));

		Opcat2.getUndoManager().undoableEditHappened(
				new UndoableEditEvent(this, undoAction));
		Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
		Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

		deleteGeneralRelation(instance);
		removeSelection();
		addSelection(newInstance, true);
		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		return cr;
	}

	public UndoableDeleteFundamentalRelation deleteFundamentalRelation(
			FundamentalRelationInstance delInstance) {
		UndoableDeleteFundamentalRelation udf = new UndoableDeleteFundamentalRelation(
				this, delInstance);

		Entry selectedEntry = delInstance.getEntry();
		selectedEntry.removeInstance(delInstance.getKey());
		GraphicalRelationRepresentation gRelR = delInstance
				.getGraphicalRelationRepresentation();
		gRelR.removeInstance(delInstance.getKey());

		if (selectedEntry.isEmpty()) {
			ConnectionEdgeEntry sourceEntry, destinationEntry;
			OpmFundamentalRelation lRel = (OpmFundamentalRelation) selectedEntry
					.getLogicalEntity();

			long sourceId = gRelR.getSource().getEntity().getId();
			long destinationId = delInstance.getDestination().getEntity()
					.getId();

			sourceEntry = (ConnectionEdgeEntry) (mainComponentsStructure
					.getEntry(sourceId));
			destinationEntry = (ConnectionEdgeEntry) (mainComponentsStructure
					.getEntry(destinationId));

			sourceEntry.removeRelationSource(lRel);
			destinationEntry.removeRelationDestination(lRel);

			mainComponentsStructure.removeEntry(selectedEntry.getId());
		}

		if (gRelR.isEmpty()) {
			OpdKey sourceKey = new OpdKey(gRelR.getSource().getOpdId(), gRelR
					.getSource().getEntityInOpdId());
			GraphicRepresentationKey key = new GraphicRepresentationKey(
					sourceKey, gRelR.getType());
			mainComponentsStructure.removeGraphicalRelationRepresentation(key);
		}

		return udf;
	}

	public UndoableDeleteGeneralRelation deleteGeneralRelation(
			GeneralRelationInstance delInstance) {
		UndoableDeleteGeneralRelation dgr = new UndoableDeleteGeneralRelation(
				this, delInstance);
		Entry selectedEntry = delInstance.getEntry();
		selectedEntry.removeInstance(delInstance.getKey());

		if (selectedEntry.isEmpty()) {
			ConnectionEdgeEntry sourceEntry, destinationEntry;
			OpmGeneralRelation lRelation = (OpmGeneralRelation) selectedEntry
					.getLogicalEntity();

			long sourceId = delInstance.getSource().getEntity().getId();
			long destinationId = delInstance.getDestination().getEntity()
					.getId();

			sourceEntry = (ConnectionEdgeEntry) (mainComponentsStructure
					.getEntry(sourceId));
			destinationEntry = (ConnectionEdgeEntry) (mainComponentsStructure
					.getEntry(destinationId));

			sourceEntry.removeGeneralRelationSource(lRelation);
			destinationEntry.removeGeneralRelationDestination(lRelation);

			mainComponentsStructure.removeEntry(selectedEntry.getId());
		}
		return dgr;
	}

	public IXStateEntry addState(String name, IXObjectInstance parentObject) {
		long opdId = parentObject.getKey().getOpdId();

		if (currentOpd == null || currentOpd.getOpdId() != opdId) {
			showOPD(opdId);
		}

		ObjectEntry parentEntry = (ObjectEntry) mainComponentsStructure
				.getEntry(parentObject.getLogicalId());
		ObjectInstance parentInstance = (ObjectInstance) parentEntry
				.getInstance(parentObject.getKey());

		IXStateEntry newEntry = (IXStateEntry) addState((OpdObject) parentInstance
				.getThing());
		newEntry.setName(name);
		newEntry.updateInstances();

		for (Enumeration e = parentObject.getIXEntry().getInstances(); e
				.hasMoreElements();) {
			ObjectInstance tempObject = (ObjectInstance) e.nextElement();
			if (tempObject.isStatesAutoArranged()) {
				tempObject.getConnectionEdge().fitToContent();
			}
		}

		return newEntry;
	}

	public UndoableDeleteState deleteState(StateInstance delInstance) {
		StateEntry myEntry = (StateEntry) delInstance.getEntry();

		UndoableDeleteState ud = new UndoableDeleteState(this, myEntry);

		ObjectEntry parentEntry = myEntry.getParentObject();

		for (Enumeration e = myEntry.getInstances(); e.hasMoreElements();) {
			StateInstance si = (StateInstance) e.nextElement();
			myEntry.removeInstance(si.getKey());
		}

		parentEntry.removeState(myEntry.getId());
		mainComponentsStructure.removeEntry(myEntry.getId());

		return ud;
	}

	/**
	 * Returns maximal Entity Entry (for save purposes)
	 */
	public long getEntityMaxEntry() {
		return this.entityMaxEntry;
	}

	/**
	 * Sets maximal Entity Entry (for load purposes)
	 */

	public void setEntityMaxEntry(long pEntityMaxEntry) {
		entityMaxEntry = pEntityMaxEntry;
	}

	/**
	 * Returns maximal OPD Entry (for save purposes)
	 */

	public long getOpdMaxEntry() {
		return opdMaxEntry;
	}

	/**
	 * Returns maximal OPD Entry (for save purposes)
	 */

	public void setOpdMaxEntry(long pOpdMaxEntry) {
		opdMaxEntry = pOpdMaxEntry;
	}

	/**
	 * Returns JDesktopPane which is parent (contains) for this project
	 */
	public JDesktopPane getParentDesktop() {
		return parentDesktop;
	}

	public void copy() {
		
		
		clipBoard.selectAll();
		_delete(clipBoard, DELETE);
		String message = _copy(getCurrentOpd(), clipBoard, 0, 0, clipBoard.getDrawingArea(),COPY);
		if (message != null) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(), message,
					"Opcat2 - Message", JOptionPane.ERROR_MESSAGE);
		}				
	}

	public void paste(int x, int y, Container parent) {
		EditControl edit = EditControl.getInstance() ; 
		
		clipBoard.selectAll(); 
		if (!edit.IsCutPending()) {
			try {
				_copy(clipBoard, getCurrentOpd(), x, y, parent, PASTE);
			} catch (Exception e ) {
				
			}
		} else { 
			/**
			 * TODO: check if the target OPD is one of the sources sons.
			 * locking the source OPD's will do the same 
			 */
			boolean doPaste = false ; 
			
			doPaste = true ; 
			if (doPaste) {
		    	ReuseCommand reuseCommand = new ReuseCommand(this,this,clipBoard, ReuseCommand.MOVE); 
		        try {
		        	/**
		        	 * TODO: need to work on the speed of the Import class. it's very slow. 
		        	 */
		        	reuseCommand.commitReuse() ; 
		        	//empty clipBoard from left overs. 
		        	_delete(clipBoard, DELETE);
		        } catch (OpcatException e) {
		        	
		        }		
		        
			}
	         
		}
	}

	public AbstractUndoableEdit delete(IXInstance delInstance) {
		if (checkDeletion(delInstance).getResult() == IXCheckResult.WRONG) {
			return null;
		} else {
			return deleteInstance((Instance) delInstance);
		}
	}

	public IXCheckResult checkDeletion(IXInstance checkedInstance) {
		return CheckModule.checkDeletion((Instance) checkedInstance, this);
	}

	public IXCheckResult checkLinkAddition(IXConnectionEdgeInstance source,
			IXConnectionEdgeInstance destination, int linkType) {
		CommandWrapper cw = new CommandWrapper(source.getLogicalId(), source
				.getKey(), destination.getLogicalId(), destination.getKey(),
				linkType, this);

		return CheckModule.checkCommand(cw);

	}

	public IXCheckResult checkRelationAddition(IXConnectionEdgeInstance source,
			IXConnectionEdgeInstance destination, int relationType) {
		CommandWrapper cw = new CommandWrapper(source.getLogicalId(), source
				.getKey(), destination.getLogicalId(), destination.getKey(),
				relationType, this);

		return CheckModule.checkCommand(cw);

	}

	
	public void delete() {
		String message = _delete(getCurrentOpd(), DELETE);
		if (message != null) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(), message,
					"Opcat2 - Message", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void refresh() {
		if (currentOpd != null) {
			currentOpd.getDrawingArea().repaint();
		}
	}

	public PrinterJob getPrinterJob() {
		if (printJob == null) {
			printJob = PrinterJob.getPrinterJob();
		}
		return printJob;
	}

	public void pageSetup() {
		//JOptionPane.showMessageDialog(Opcat2.getFrame(), "This is page
		// Setup", "Message" ,JOptionPane.ERROR_MESSAGE);
		new PageSetupDialog(Opcat2.getFrame(), "Page Setup", pageSetupData);
	}

	public void setNames4OPDs() {
		//        String currentLevel = "";
		Hashtable currentLevel = new Hashtable();
		Hashtable previousLevel = new Hashtable();
		Hashtable notScanned = new Hashtable();
		Opd currOPD;

		for (Enumeration e = mainComponentsStructure.getOpds(); e
				.hasMoreElements();) {
			currOPD = (Opd) e.nextElement();
			notScanned.put(new Long(currOPD.getOpdId()), currOPD);
		}

		currOPD = mainComponentsStructure.getOpd(1);
		currOPD.setName("SD");
		notScanned.remove(new Long(currOPD.getOpdId()));

		_setName4ChildOPD(currOPD, notScanned);

		previousLevel.put(new Long(currOPD.getOpdId()), currOPD);

		while (!notScanned.isEmpty()) {
			for (Enumeration e = notScanned.elements(); e.hasMoreElements();) {
				currOPD = (Opd) e.nextElement();
				if (previousLevel.containsKey(new Long(currOPD.getParentOpd()
						.getOpdId()))) {
					//                    System.err.println(currOPD.getOpdId());
					currentLevel.put(new Long(currOPD.getOpdId()), currOPD);
					notScanned.remove(new Long(currOPD.getOpdId()));
					_setName4ChildOPD(currOPD, notScanned);
				}
			}

			previousLevel = new Hashtable(currentLevel);
			currentLevel.clear();
		}
	}

	private void _setName4ChildOPD(Opd myOpd, Hashtable notScanned) {
		TreeMap currentLevel = new TreeMap();
		String header;

		if (myOpd.getOpdId() == 1) {
			header = "SD";
		} else {
			StringTokenizer st = new StringTokenizer(myOpd.getName(), " ");
			header = st.nextToken() + ".";
		}

		for (Enumeration e = notScanned.elements(); e.hasMoreElements();) {
			Opd currOPD = (Opd) e.nextElement();

			if (currOPD.getParentOpd() != null
					&& myOpd.getOpdId() == currOPD.getParentOpd().getOpdId()) {
				currentLevel.put(currOPD.getMainEntry().getLogicalEntity()
						.getName()
						+ " " + currOPD.getOpdId(), currOPD);
			}
		}

		int counter = 1;

		for (Iterator i = currentLevel.values().iterator(); i.hasNext();) {
			Opd currOPD = (Opd) i.next();
			String footer = currOPD.getMainEntry().getLogicalEntity().getName()
					.replace('\n', ' ');

			if (currOPD.getMainEntry().getZoomedInOpd() != null
					&& currOPD.getMainEntry().getZoomedInOpd().getOpdId() == currOPD
							.getOpdId()) {
				footer += " in-zoomed";
			} else {
				footer += " unfolded";
			}
			currOPD.setName(header + counter + " - " + footer);
			counter++;
		}

	}

	public String cut(Opd cuttingFrom, int action) {
		
		String message ; 
		 
		clipBoard.selectAll();
		_delete(clipBoard, DELETE);
		_copy(getCurrentOpd(), clipBoard, 0, 0, clipBoard.getDrawingArea(), action);
		message = _delete(cuttingFrom, action); 
		return  message ; 
		
	}

	public void selectAll() {
		this.getCurrentOpd().selectAll();
	}


	/**
	 * there are 2 methods generally: 1. _copy(Opd copyFrom, opd copyTo) that
	 * copies selection from one opd to another 2. _delete(Opd deleteFrom) that
	 * deletes selection from given Opd
	 * 
	 * all copy/cut/paste/delete are combinations of these 2 methods
	 */

	///////////////////////////
	// IMPORTENT: handle any2state and state2any links and relations -- DONE!!
	/////////////////////////////
	private String _copy(Opd copyFrom, Opd copyTo, int x, int y,
			Container parent, int action) {
		
		Hashtable t = new Hashtable(); // things
		Hashtable l = new Hashtable(); // links
		Hashtable fr = new Hashtable(); // fundamental relations
		Hashtable gr = new Hashtable(); // general relations
		Hashtable selectedItems = copyFrom.getSelectedItemsHash();
		// all selected Instances

		boolean wasNewOpd = false;

		Instance inst = null;
		Enumeration e;
		ThingInstance ti = null;
		ThingInstance newTi = null;
		LinkInstance li = null;
		GeneralRelationInstance gri = null;
		FundamentalRelationInstance fri = null;

		// undo only when action == PASTE
		CompoundUndoAction undoAction = new CompoundUndoAction();

		Opd oldOpd = currentOpd;

		currentOpd = copyFrom;

		// sort all selected Instances according to the type
		for (e = selectedItems.elements(); e.hasMoreElements();) {
			inst = (Instance) e.nextElement();
			if (inst instanceof ThingInstance) {
				t.put(new Long(inst.getKey().getEntityInOpdId()), inst);
			} else if (inst instanceof LinkInstance) {
				l.put(new Long(inst.getKey().getEntityInOpdId()), inst);
			} else if (inst instanceof FundamentalRelationInstance) {
				fr.put(new Long(inst.getKey().getEntityInOpdId()), inst);
			} else if (inst instanceof GeneralRelationInstance) {
				gr.put(new Long(inst.getKey().getEntityInOpdId()), inst);
			}
		}

		// now calculate the most topleft point
		// we calculate it from _graphical_ selection so if
		// one of DraggedPoints or OpdFundamentalRelations outstends the
		// OpdThings
		// bounds it would taken in count
		Component[] bgcs = copyFrom.getSelection()
				.graphicalSelectionComponents();
		int topleftX = Integer.MAX_VALUE;
		int topleftY = Integer.MAX_VALUE;

		for (int i = 0; i < bgcs.length; i++) {
			if (bgcs[i].getX() < topleftX) {
				topleftX = bgcs[i].getX();
			}
			if (bgcs[i].getY() < topleftY) {
				topleftY = bgcs[i].getY();
			}
		}

		int theX, theY; // really x,y to paste
		currentOpd = copyTo;
		//////////////////////////////////////////////////////////
		// this hash holds oldInstance/newInstance pairs
		// so we can add all links/relations properly
		Hashtable orig2new = new Hashtable();
		//////////////////////////////////////////////////////////

		for (Enumeration e1 = t.elements(); e1.hasMoreElements();) {
			ti = (ThingInstance) e1.nextElement();

			// calc real location
			theX = x + ti.getX() - topleftX;
			theY = y + ti.getY() - topleftY;

			if (ti instanceof ProcessInstance) {
				ProcessInstance pi;
				pi = addProcess(theX, theY, parent, ti.getLogicalId(), -1);
				pi.setLocation(theX, theY);
				pi.getThing().fitToContent();
				pi.copyPropsFrom((ProcessInstance) ti);
				pi.update();

				if (action == PASTE) {
					undoAction.addAction(new UndoableAddProcess(this, pi));
				}

				orig2new.put(ti, pi);
				newTi = pi;
			}

			if (ti instanceof ObjectInstance) {
				ObjectInstance oi;
				oi = this.addObject(theX, theY, parent, ti.getLogicalId(), -1,
						true);
				oi.setLocation(theX, theY);
				oi.getThing().fitToContent();
				oi.copyPropsFrom((ObjectInstance) ti);
				oi.update();

				if (action == PASTE) {
					undoAction.addAction(new UndoableAddObject(this, oi));
				}
				orig2new.put(ti, oi);
				newTi = oi;

				ObjectInstance oldOi = (ObjectInstance) ti;
				StateInstance newSi, oldSi;
				for (Enumeration enum = oldOi.getStateInstances(); enum
						.hasMoreElements();) {
					oldSi = (StateInstance) enum.nextElement();
					for (Enumeration enum1 = oi.getStateInstances(); enum1
							.hasMoreElements();) {
						newSi = (StateInstance) enum1.nextElement();
						if (oldSi.getLogicalId() == newSi.getLogicalId()) {
							orig2new.put(oldSi, newSi);
						}
					}
				}
			}

			// add new OPD if thing is unfolded
			if (ti.getUnfoldingOpd() != null) {
			}
		}

		ConnectionEdgeInstance origSrc, origDst, newSrc, newDst;
		// it's time to paste links
		LinkInstance origLi, newLi;
		for (Enumeration e2 = l.elements(); e2.hasMoreElements();) {
			origLi = (LinkInstance) e2.nextElement();
			origSrc = (ConnectionEdgeInstance) origLi.getSourceInstance();
			origDst = (ConnectionEdgeInstance) origLi.getDestinationInstance();
			newSrc = (ConnectionEdgeInstance) orig2new.get(origSrc);
			newDst = (ConnectionEdgeInstance) orig2new.get(origDst);
			newLi = this
					.addLink(newSrc.getConnectionEdge(), origLi
							.getSourceDragger().getRelativeConnectionPoint(),
							newDst.getConnectionEdge(), origLi
									.getDestinationDragger()
									.getRelativeConnectionPoint(), newSrc
									.getConnectionEdge().getParent(), Constants
									.getType4Link((OpmProceduralLink) origLi
											.getEntry().getLogicalEntity()),
							origLi.getEntry().getId(), -1);
			newLi.copyPropsFrom(origLi);
			newLi.update();

			if (action == PASTE) {
				undoAction.addAction(new UndoableAddLink(this, newLi));
			}

		}

		// paste fundamental relations
		FundamentalRelationInstance origFri, newFri;
		for (Enumeration e2 = fr.elements(); e2.hasMoreElements();) {
			origFri = (FundamentalRelationInstance) e2.nextElement();
			origSrc = (ConnectionEdgeInstance) origFri
					.getGraphicalRelationRepresentation().getSourceInstance();
			origDst = (ConnectionEdgeInstance) origFri.getDestinationInstance();
			newSrc = (ConnectionEdgeInstance) orig2new.get(origSrc);
			newDst = (ConnectionEdgeInstance) orig2new.get(origDst);
			newFri = this.addRelation(newSrc.getConnectionEdge(), origFri
					.getDestinationDragger().getRelativeConnectionPoint(),
					newDst.getConnectionEdge(), origFri.getDestinationDragger()
							.getRelativeConnectionPoint(), Constants
							.getType4Relation((OpmStructuralRelation) origFri
									.getEntry().getLogicalEntity()), newSrc
							.getConnectionEdge().getParent(), origFri
							.getEntry().getId(), -1);
			//          newFri.copyPropsFrom(origFri);
			newFri.update();
			if (action == PASTE) {
				undoAction.addAction(new UndoableAddFundamentalRelation(this,
						newFri));
			}

		}

		// paste general relations
		GeneralRelationInstance origGri, newGri;
		for (Enumeration e2 = gr.elements(); e2.hasMoreElements();) {
			origGri = (GeneralRelationInstance) e2.nextElement();
			origSrc = (ConnectionEdgeInstance) origGri.getSourceInstance();
			origDst = (ConnectionEdgeInstance) origGri.getDestinationInstance();
			newSrc = (ConnectionEdgeInstance) orig2new.get(origSrc);
			newDst = (ConnectionEdgeInstance) orig2new.get(origDst);
			newGri = this.addGeneralRelation(newSrc.getConnectionEdge(),
					origGri.getDestinationDragger()
							.getRelativeConnectionPoint(), newDst
							.getConnectionEdge(), origGri
							.getDestinationDragger()
							.getRelativeConnectionPoint(), newSrc
							.getConnectionEdge().getParent(), Constants
							.getType4Relation((OpmStructuralRelation) origGri
									.getEntry().getLogicalEntity()), origGri
							.getEntry().getId(), -1);
			//          newGri.copyPropsFrom(origFri);
			newGri.update();
			if (action == PASTE) {
				undoAction.addAction(new UndoableAddGeneralRelation(this,
						newGri));
			}
		}

		currentOpd = oldOpd;

		if (wasNewOpd) {
			Opcat2.getUndoManager().discardAllEdits();
			Opcat2.setRedoEnabled(false);
			Opcat2.setUndoEnabled(false);
		}
		if (action == PASTE) {
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			Opcat2.getUndoManager().undoableEditHappened(
					new UndoableEditEvent(this, undoAction));
			Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
			Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
		}
		return null;
	}

	private String _delete(Opd deletingFrom, int action) {
		
		if (currentOpd == null)
			return null;

		Enumeration e = deletingFrom.getSelectedItems();
		Hashtable selConnectionEdgesInst = new Hashtable();
		Hashtable selLinksRelationsInst = new Hashtable();
		Instance tmpInst = null;
		for (; e.hasMoreElements();) {
			tmpInst = (Instance) e.nextElement();
			if (tmpInst instanceof ConnectionEdgeInstance) {
				selConnectionEdgesInst.put(tmpInst.getKey(), tmpInst);
			} else {
				selLinksRelationsInst.put(tmpInst.getKey(), tmpInst);
			}
		}

		if (selConnectionEdgesInst.size() == 0
				&& selLinksRelationsInst.size() == 0) {
			//			JOptionPane.showMessageDialog(Opcat2.getFrame(), "Nothing was
			// selected", "Opcat2 - Message", JOptionPane.ERROR_MESSAGE);
			return "Nothing was selected";
		}

		if (selConnectionEdgesInst.size() != 0) {
			CheckResult cr = null;
			Vector warnings = new Vector();

			// run over all ConnectionEdges and checkTheir possibility for
			// deletion
			// if one of them is illegal to delete, operation is canceled
			if (deletingFrom.getOpdId() != -1) {
				for (Enumeration e1 = selConnectionEdgesInst.elements(); e1
						.hasMoreElements();) {
					if (action == DELETE) { 
						cr = CheckModule.checkDeletion((Instance) e1.nextElement(),	this);
					}
					if (action == CUT ) {
						cr = CheckModule.checkCut((Instance) e1.nextElement(),	this);	
					}
					if (cr.getResult() == IXCheckResult.WRONG) {
						//					JOptionPane.showMessageDialog(Opcat2.getFrame(),
						// cr.getMessage(), "Opcat2 - Error"
						// ,JOptionPane.ERROR_MESSAGE);
						return cr.getMessage();
					}
				}
			}

			CompoundUndoAction undoAction = new CompoundUndoAction();

			for (Enumeration e2 = selConnectionEdgesInst.elements(); e2
					.hasMoreElements();) {
				tmpInst = (Instance) e2.nextElement();
				//				System.err.println("tmpInst = "+ tmpInst);
				for (Enumeration e3 = ((ConnectionEdgeInstance) tmpInst)
						.getRelatedInstances(); e3.hasMoreElements();) {
					Instance currInstance = (Instance) e3.nextElement();
					//System.err.println("currInst = "+currInstance);
					undoAction.addAction(deleteInstance(currInstance));
				}
				undoAction.addAction(deleteInstance(tmpInst));
			}

			if (action == CUT || action == DELETE) {
				Opcat2.getUndoManager().undoableEditHappened(
						new UndoableEditEvent(this, undoAction));
				Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
				Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
			}

			deletingFrom.removeSelection();
			
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			return null;
		} else {
			// Deletion of links and relations
			CompoundUndoAction undoAction = new CompoundUndoAction();
			for (Enumeration e4 = selLinksRelationsInst.elements(); e4
					.hasMoreElements();) {
				undoAction
						.addAction(deleteInstance((Instance) e4.nextElement()));
			}

			if (action == CUT || action == DELETE) {
				Opcat2.getUndoManager().undoableEditHappened(
						new UndoableEditEvent(this, undoAction));
				Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
				Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
			}

			//currentOpd.removeSelection();
			deletingFrom.removeSelection(); 
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			return null;
		}
	}

	public void clearClipBoard() {
		clipBoard.getSelection().selectAll();
		_delete(clipBoard, DELETE);
	}

	public Opd getClipBoard() {
		return clipBoard;
	}

	/**
	 * Method checks legality of the link
	 * 
	 * @returns true if leagal, false otherwise
	 */
	private boolean _checkLinkLegality(OpdConnectionEdge source,
			OpdConnectionEdge destination, int linkType) {
		CommandWrapper cw = new CommandWrapper(source.getEntity().getId(),
				source.getOpdKey(), destination.getEntity().getId(),
				destination.getOpdKey(), linkType, this);

		CheckResult cr = CheckModule.checkCommand(cw);

		if (cr.getResult() == IXCheckResult.WRONG) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(), cr.getMessage(),
					"Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
			StateMachine.reset();
			return false;
		}

		if (cr.getResult() == IXCheckResult.WARNING) {
			int retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(), cr
					.getMessage()
					+ "\n Do you want to continue?", "Opcat2 - Warning",
					JOptionPane.YES_NO_OPTION);

			if (retValue == JOptionPane.NO_OPTION) {
				StateMachine.reset();
				return false;
			}
		}

		return true;
	}

	private LinkInstance _addLinkToParentOpd(OpdConnectionEdge source,
			RelativeConnectionPoint pPoint1, OpdConnectionEdge destination,
			RelativeConnectionPoint pPoint2, Container container, int linkType,
			long entityId, long entityInOpdId, boolean swapped) {
		CompoundUndoAction undoAction = new CompoundUndoAction();
		// check if destination exist in child OPD
		boolean isState = false;
		if (destination instanceof OpdState) {
			isState = true;
		}

		ConnectionEdgeInstance currTi = null;
		ConnectionEdgeInstance currSi = null;
		StateEntry se = null;
		boolean destExist = false;
		Opd childOpd = ((ThingEntry) source.getEntry()).getZoomedInOpd();
		for (Enumeration e = getComponentsStructure().getThingsInOpd(childOpd); e
				.hasMoreElements();) {
			currTi = (ThingInstance) e.nextElement();
			if (!isState) {
				if (currTi.getEntry().getId() == destination.getEntity()
						.getId()) {
					destExist = true;
					break;
				}
			} else if (currTi instanceof ObjectInstance) {
				ObjectInstance oi = (ObjectInstance) currTi;
				for (Enumeration e1 = oi.getStateInstances(); e1
						.hasMoreElements();) {
					currSi = (StateInstance) e1.nextElement();
					if (currSi.getEntry().getId() == destination.getEntity()
							.getId()) {
						se = (StateEntry) currSi.getEntry();
						currTi = currSi;
						destExist = true;
						break;
					}
				}
			}
		}

		// destination doesn't exist in child OPD, so we add it
		if (!destExist) {
			currTi = _addNeededThing(childOpd, destination, undoAction);
			if (currTi == null) {
				return null;
			}
		}
		// now currTi holds needed thing in child OPD

		// child OPD vars
		OpdThing srcInChildOpd = childOpd.getMainInstance().getThing();
		RelativeConnectionPoint srcP = GraphicsUtils.calculateConnectionPoint(
				srcInChildOpd, currTi.getConnectionEdge());
		RelativeConnectionPoint dstP = GraphicsUtils.calculateConnectionPoint(
				currTi.getConnectionEdge(), srcInChildOpd);

		Opd oldOpd = this.getCurrentOpd();
		currentOpd = childOpd;
		LinkInstance tmpLi = null;
		if (swapped) {
			tmpLi = _addLink(currTi.getConnectionEdge(), dstP, srcInChildOpd,
					srcP, childOpd.getDrawingArea(), linkType, entityId, -1);
		} else {
			tmpLi = _addLink(srcInChildOpd, srcP, currTi.getConnectionEdge(),
					dstP, childOpd.getDrawingArea(), linkType, entityId, -1);
		}
		undoAction.addAction(new UndoableAddLink(this, tmpLi));
		currentOpd = oldOpd;
		LinkInstance li = null;
		if (swapped) {
			li = _addLink(destination, pPoint2, source, pPoint1, container,
					linkType, tmpLi.getEntry().getId(), -1);
		} else {
			li = _addLink(source, pPoint1, destination, pPoint2, container,
					linkType, tmpLi.getEntry().getId(), -1);
		}
		undoAction.addAction(new UndoableAddLink(this, li));
		Opcat2.getUndoManager().undoableEditHappened(
				new UndoableEditEvent(this, undoAction));
		Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
		Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

		return li;
	}

	private LinkInstance _addLinkToChildOpd(OpdThing parentThing,
			OpdConnectionEdge source, RelativeConnectionPoint pPoint1,
			OpdConnectionEdge destination, RelativeConnectionPoint pPoint2,
			Container container, int linkType, long entityId,
			long entityInOpdId, boolean swapped) {
		// ATTANTION: source is always the problematic case

		CompoundUndoAction undoAction = new CompoundUndoAction();
		LinkInstance newLink = null;
		if (swapped) {
			newLink = _addLink(destination, pPoint2, source, pPoint1,
					container, linkType, entityId, -1);
		} else {
			newLink = _addLink(source, pPoint1, destination, pPoint2,
					container, linkType, entityId, -1);
		}
		undoAction.addAction(new UndoableAddLink(this, newLink));

		//now pThing contains the thing that is In-Zoomed
		OpdThing pThing = (parentThing != null ? parentThing
				: (OpdThing) source);

		/////////////////////////////////////////////////////////////////////////////////
		// detect if destination appears in parent OPDs
		// if not add it
		// if number of instances present in parent OPD we add link to each one
		// of them
		//		Vector destInParent = new Vector();
		Vector parentOpds = new Vector();
		ThingInstance parentThingInstance = null;
		Opd tmpOpd = null;
		// get all parent OPDs
		for (Enumeration e = pThing.getEntry().getInstances(); e
				.hasMoreElements();) {
			parentThingInstance = (ThingInstance) e.nextElement();
			if (!parentThingInstance.isZoomedIn()) {
				tmpOpd = mainComponentsStructure.getOpd(parentThingInstance
						.getKey().getOpdId());
				if (tmpOpd == null) {
					continue;
				}

				parentOpds.add(tmpOpd);

				//add destination thing if it doesn't exist
				if (mainComponentsStructure.getInstanceInOpd(tmpOpd,
						destination.getEntity().getId()) == null) {
					_addNeededThing(tmpOpd, destination, undoAction);
				}
			}
		}

		// we have all parent OPDs in parentOpds vector
		// and each one of them contains destination

		// go through parentOpds and add link between each one of sources
		// to each one of destinations

		LinkInstance tmpLi = null;
		ConnectionEdgeInstance tmpSrcInst = null;
		ConnectionEdgeInstance tmpDstInst = null;
		RelativeConnectionPoint srcP = null;
		RelativeConnectionPoint dstP = null;

		int resultLink = -1;

		for (Iterator iter1 = parentOpds.iterator(); iter1.hasNext();) {
			tmpOpd = (Opd) iter1.next();
			for (Enumeration srcs = mainComponentsStructure.getInstanceInOpd(
					tmpOpd, pThing.getEntity().getId()); srcs.hasMoreElements();) {
				tmpSrcInst = (ConnectionEdgeInstance) srcs.nextElement();
				for (Enumeration dsts = mainComponentsStructure
						.getInstanceInOpd(tmpOpd, destination.getEntity()
								.getId()); dsts.hasMoreElements();) {
					tmpDstInst = (ConnectionEdgeInstance) dsts.nextElement();
					srcP = GraphicsUtils.calculateConnectionPoint(tmpSrcInst
							.getConnectionEdge(), tmpDstInst
							.getConnectionEdge());
					dstP = GraphicsUtils.calculateConnectionPoint(tmpDstInst
							.getConnectionEdge(), tmpSrcInst
							.getConnectionEdge());

					if (resultLink == -1) {
						Vector allLinks = null;
						Vector combinedLink = null;
						if (parentThing == null) {
							combinedLink = LinkPrecedence
									.combineLinks(_getAllLinkBetweenThings(
											(ConnectionEdgeInstance) source
													.getInstance(),
											(ConnectionEdgeInstance) destination
													.getInstance(), 0));
						} else {
							combinedLink = LinkPrecedence
									.combineLinks(_getAllLinkBetweenThings(
											(ConnectionEdgeInstance) parentThing
													.getInstance(),
											(ConnectionEdgeInstance) destination
													.getInstance(), 0));
						}
						if (combinedLink.size() == 1) {
							resultLink = ((Integer) combinedLink.elementAt(0))
									.intValue();
						} else {
							String[] resLinks = new String[combinedLink.size()];
							for (int r = 0; r < combinedLink.size(); r++) {
								resLinks[r] = Constants
										.type2String(((Integer) combinedLink
												.elementAt(r)).intValue());
							}
							Object res = JOptionPane
									.showInputDialog(
											Opcat2.getFrame(),
											"There are several possibilities to display link in parent OPD.\nPlease select one of the followings.",
											"Opcat2 - Question",
											JOptionPane.QUESTION_MESSAGE, null,
											resLinks, resLinks[0]);

							resultLink = Constants.string2type(res.toString());
						}
					}

					// remove link in parent OPD is exists
					for (Enumeration delEnum = _getAllLinkBetweenThings(
							(ConnectionEdgeInstance) tmpSrcInst,
							(ConnectionEdgeInstance) tmpDstInst, 1); delEnum
							.hasMoreElements();) {
						undoAction.addAction(deleteLink((LinkInstance) delEnum
								.nextElement()));
					}

					// add combined link
					if (swapped) {
						tmpLi = _addLink(tmpDstInst.getConnectionEdge(), dstP,
								tmpSrcInst.getConnectionEdge(), srcP, tmpOpd
										.getDrawingArea(), resultLink,
								entityId, -1);
					} else {
						tmpLi = _addLink(tmpSrcInst.getConnectionEdge(), srcP,
								tmpDstInst.getConnectionEdge(), dstP, tmpOpd
										.getDrawingArea(), resultLink,
								entityId, -1);
					}
					undoAction.addAction(new UndoableAddLink(this, tmpLi));
				}
			}
		}

		Opcat2.getUndoManager().undoableEditHappened(
				new UndoableEditEvent(this, undoAction));
		Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
		Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

		return tmpLi;
	}

	// used in addLinkConsistent
	private ConnectionEdgeInstance _addNeededThing(Opd opdToAddTo,
			OpdConnectionEdge destination, CompoundUndoAction undoAction) {
		Opd oldOpd = currentOpd;
		currentOpd = opdToAddTo;
		ConnectionEdgeInstance currTi = null;
		StateEntry se = null;

		if (destination instanceof OpdObject) {
			currTi = addObject(50, 50, currentOpd.getDrawingArea(), destination
					.getEntity().getId(), -1, true);
			undoAction.addAction(new UndoableAddObject(this,
					(ObjectInstance) currTi));
			((ObjectInstance) currTi).getThing().fitToContent();
		} else if (destination instanceof OpdProcess) {
			currTi = addProcess(50, 50, currentOpd.getDrawingArea(),
					destination.getEntity().getId(), -1);
			undoAction.addAction(new UndoableAddProcess(this,
					(ProcessInstance) currTi));
		} else if (destination instanceof OpdState) {
			long parEntryId = ((StateInstance) ((OpdState) destination)
					.getInstance()).getParent().getEntry().getId();
			ObjectInstance obji = addObject(50, 50,
					currentOpd.getDrawingArea(), parEntryId, -1, true);
			//			ObjectInstance obji = addObject(50, 50,
			// currentOpd.getDrawingArea(), parEntryId, -1, false);
			undoAction.addAction(new UndoableAddObject(this, obji));
			//			currTi = obji;
			obji.getThing().fitToContent();
			StateInstance sti = null;
			se = (StateEntry) ((OpdState) destination).getEntry();
			for (Enumeration e2 = se.getInstances(); e2.hasMoreElements();) {
				sti = (StateInstance) e2.nextElement();
				if (sti.getKey().getOpdId() == currentOpd.getOpdId()) {
					currTi = sti;
					break;
				}
			}
		} else {
			currTi = null;
		}

		currentOpd = oldOpd;
		return currTi;
	}

	/**
	 * mode : 0 - OpmProceduralLink 1 - LinkInstance
	 */
	private Enumeration _getAllLinkBetweenThings(ConnectionEdgeInstance first,
			ConnectionEdgeInstance second, int mode) {
		Vector retVector = new Vector();

		// first get all links between things themselfs
		LinkInstance tmpLi = null;
		Object obj;
		for (Enumeration enum1 = first.getRelatedInstances(); enum1
				.hasMoreElements();) {
			obj = enum1.nextElement();
			if (obj instanceof LinkInstance) {
				tmpLi = (LinkInstance) obj;
			}
			if ((tmpLi.getSource().equals(first.getConnectionEdge()) && tmpLi
					.getDestination().equals(second.getConnectionEdge()))
					|| (tmpLi.getSource().equals(second.getConnectionEdge()) && tmpLi
							.getDestination().equals(first.getConnectionEdge()))) {
				if (mode == 0) {
					retVector.add(tmpLi.getLink());
				} else {
					retVector.add(tmpLi);
				}
			}
		}

		// then if one of things is Zoomed-In get links between inside things
		// and second thing
		Enumeration includedThings;
		if (first instanceof ThingInstance
				&& ((ThingInstance) first).isZoomedIn()) {
			includedThings = ((ThingInstance) first).getChildThings();
			ThingInstance tmpInst = null;
			for (; includedThings.hasMoreElements();) {
				tmpInst = (ThingInstance) includedThings.nextElement();
				for (Enumeration enum2 = tmpInst.getRelatedInstances(); enum2
						.hasMoreElements();) {

					obj = enum2.nextElement();
					if (obj instanceof LinkInstance) {
						tmpLi = (LinkInstance) obj;
					}
					if ((tmpLi.getSource().equals(tmpInst.getConnectionEdge()) && tmpLi
							.getDestination()
							.equals(second.getConnectionEdge()))
							|| (tmpLi.getSource().equals(
									second.getConnectionEdge()) && tmpLi
									.getDestination().equals(
											tmpInst.getConnectionEdge()))) {
						if (mode == 0) {
							retVector.add(tmpLi.getLink());
						} else {
							retVector.add(tmpLi);
						}
					}
				}
			}
		} else if (second instanceof ThingInstance
				&& ((ThingInstance) second).isZoomedIn()) {
			includedThings = ((ThingInstance) second).getChildThings();
			ThingInstance tmpInst = null;
			for (; includedThings.hasMoreElements();) {
				tmpInst = (ThingInstance) includedThings.nextElement();
				for (Enumeration enum3 = tmpInst.getRelatedInstances(); enum3
						.hasMoreElements();) {

					obj = enum3.nextElement();
					if (obj instanceof LinkInstance) {
						tmpLi = (LinkInstance) obj;
					}
					if ((tmpLi.getSource().equals(first.getConnectionEdge()) && tmpLi
							.getDestination().equals(
									tmpInst.getConnectionEdge()))
							|| (tmpLi.getSource().equals(
									tmpInst.getConnectionEdge()) && tmpLi
									.getDestination().equals(
											first.getConnectionEdge()))) {
						if (mode == 0) {
							retVector.add(tmpLi.getLink());
						} else {
							retVector.add(tmpLi);
						}
					}
				}
			}
		}

		// third case link between state to the thing
		Enumeration stateList;
		StateInstance stInst = null;
		if (first instanceof ObjectInstance) {
			stateList = ((ObjectInstance) first).getStateInstances();
			for (; stateList.hasMoreElements();) {
				stInst = (StateInstance) stateList.nextElement();
				for (Enumeration enum1 = stInst.getRelatedInstances(); enum1
						.hasMoreElements();) {
					obj = enum1.nextElement();
					if (obj instanceof LinkInstance) {
						tmpLi = (LinkInstance) obj;
					}
					if ((tmpLi.getSource().equals(stInst.getConnectionEdge()) && tmpLi
							.getDestination()
							.equals(second.getConnectionEdge()))
							|| (tmpLi.getSource().equals(
									second.getConnectionEdge()) && tmpLi
									.getDestination().equals(
											stInst.getConnectionEdge()))) {
						if (mode == 0) {
							retVector.add(tmpLi.getLink());
						} else {
							retVector.add(tmpLi);
						}
					}
				}
			}
		}

		if (second instanceof ObjectInstance) {
			stateList = ((ObjectInstance) second).getStateInstances();
			for (; stateList.hasMoreElements();) {
				stInst = (StateInstance) stateList.nextElement();
				for (Enumeration enum1 = stInst.getRelatedInstances(); enum1
						.hasMoreElements();) {
					obj = enum1.nextElement();
					if (obj instanceof LinkInstance) {
						tmpLi = (LinkInstance) obj;
					}
					if ((tmpLi.getSource().equals(stInst.getConnectionEdge()) && tmpLi
							.getDestination().equals(first.getConnectionEdge()))
							|| (tmpLi.getSource().equals(
									first.getConnectionEdge()) && tmpLi
									.getDestination().equals(
											stInst.getConnectionEdge()))) {
						if (mode == 0) {
							retVector.add(tmpLi.getLink());
						} else {
							retVector.add(tmpLi);
						}
					}
				}
			}
		}

		// forth case link between state to internal thing
		return retVector.elements();
	}

	// delete all links
	private UndoableDeleteLink _deleteLinkFromParentOpd(LinkInstance delInstance) {
		CompoundUndoAction undoAction = new CompoundUndoAction();
		ConnectionEdgeInstance src = (ConnectionEdgeInstance) delInstance
				.getSource().getInstance();
		ConnectionEdgeInstance dst = (ConnectionEdgeInstance) delInstance
				.getDestination().getInstance();

		undoAction.addAction(_deleteLink(delInstance));

		return null;
	}

	private UndoableDeleteLink _deleteLinkFromChildOpd(LinkInstance delInstance) {
		CompoundUndoAction undoAction = new CompoundUndoAction();
		return null;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public OpcatLayoutManager getLayoutManager() {
		return layoutManager;
	}

	/**
	 * Sets the <code>showProcessNameMessage</code> flag, indicating if the
	 * user is interested in displaying annoying messages regarding the "ing"
	 * ending in the process name.
	 * 
	 * @param flag
	 *            <code>false</code> for not showing the message.
	 *            <code>true</code> to show it.
	 */
	public void setShowProcessNameMessage(boolean flag) {
		showProcessNameMessage = flag;
	}

	/**
	 * Returns the <code>showProcessNameMessage</code> flag. <code>true</code>
	 * means that the user is interested in displaying annoying messages
	 * regarding the "ing" ending in the process name.
	 */
	public boolean getShowProcessNameMessage() {
		return showProcessNameMessage;
	}

	public MetaManager getMetaManager() {
		return metaManager;
	}

	/**
	 * Returns an Enumeration of all the <code>MetaLibrary</code> models
	 * imported by this system.
	 * 
	 * @return Enumeration containing <code>MetaLibrary</code> objects.
	 */
	public Enumeration getMetaLibraries() {
		return metaManager.getMetaLibraries();
	}

	/**
	 * Gets the version string representation of the OPX file.
	 * 
	 * @return String representation of the version.
	 */
	public String getVersionString() {
		return versionString;
	}

	/**
	 * Sets the version string representation of the OPX file.
	 * 
	 * @param string
	 *            The version representation.
	 */
	public void setVersionString(String string) {
		versionString = string;
	}

	//reuseComment
	//////////////////////////////////////////
	// from this stage are the defitions of functions created/updated by the
	// reuse team

	public void setDuringLoad(boolean dLoad) {
		duringLoad = dLoad;
	}

	public ThingInstance getThingInstanceByName(String name) {
		ThingInstance returnThing = null;
		Enumeration e = this.getSystemStructure().getInstancesInOpd(
				this.getCurrentOpd());
		while (e.hasMoreElements()) {
			Instance ins = (Instance) e.nextElement();
			if (ins instanceof ThingInstance) {
				returnThing = (ThingInstance) ins;
				if (returnThing.getEntry().getName().equals(name))
					return returnThing;
				else
					returnThing = null;
			}
		}
		return returnThing;
	}

	//--------------
	// locking section
	/** ********************************************************************* */
	/* function: isBothInstancesLocked */
	/* purpose: */
	/* check weather two entries are locked */
	/** ********************************************************************* */
	public boolean isBothInstancesLocked(Instance aInstance, Instance bInstance) {
		return (aInstance.getEntry().isLocked() && bInstance.getEntry()
				.isLocked());
	}

	/** ********************************************************************* */
	/* function: isBothEntriesLocked */
	/* purpose: */
	/* check weather two entries are locked */
	/** ********************************************************************* */
	public boolean isBothEntriesLocked(Entry aEntry, Entry bEntry) {
		return (aEntry.isLocked() && bEntry.isLocked());
	}

	// open reuse section
	//-------------------
	/** ******************************************************************** */
	/* function: addopenResuedOpdToList */
	/* purpose: */
	/* check weather two entries are locked */
	/** ******************************************************************** */
	public void addopenResuedOpdToList(Opd reusedOpd, String systemPath) {
		openReusedOpdsTable.put(reusedOpd, systemPath);
	}

	public void addInstanceToOpenReuseList(Instance aInstance) {
		openReusedInstanceList.add(aInstance);
	}

	public boolean isEntryOpenReused(Entry aEntry) {
		return aEntry.isOpenReused();
	}

	public boolean isInstanceOpenReused(Instance aInstance) {
		return aInstance.getEntry().isOpenReused();
	}

	public void removeInstanceFromOpenReuseList(Instance aInstance) {
		openReusedInstanceList.remove(aInstance);
	}

	public void copyReusedProcessEntryProperties(ProcessInstance reused,
			ProcessInstance target) {

	}

	/**
	 * @return whether reuse merge dragging is enables (<code>true</code>) or not.
	 */
	public boolean getEnableDragging() {
		return enableDragging;
	}
	
	/**
	 * Sets the reuse merge dragging option. 
	 */
	public void setEnableDragging(boolean _enable) {
		enableDragging = _enable;
	}

	// name : returnThingIntersection
	// this function recieves a ThingInstance and checks weather there are
	// ThingInstances
	// that has intersection with it
	// returns the first instance of intersection
	public ThingInstance returnThingIntersection(ThingInstance dragged) {
		Instance abstractInstance;
		ThingInstance targetInstance, returnInstance = null;
		// is it an object or process
		boolean isObject = false;

		if (!getEnableDragging())
			return null;
		if (dragged instanceof ObjectInstance)
			isObject = true;
		int draggedX, draggedWidth, draggedHeight, draggedY, targetX, targetWidth, targetY, targetHeight;
		draggedX = dragged.getX();
		draggedY = dragged.getY();
		draggedWidth = dragged.getWidth();
		draggedHeight = dragged.getHeight();
		Enumeration e = this.getSystemStructure().getInstancesInOpd(
				this.getCurrentOpd());
		while (e.hasMoreElements()) {
			abstractInstance = (Instance) e.nextElement();
			if (((abstractInstance instanceof ObjectInstance) && isObject)
					|| ((abstractInstance instanceof ProcessInstance) && (!isObject))) {
				if (abstractInstance.getLogicalId() != dragged.getLogicalId()) {
					targetInstance = (ThingInstance) abstractInstance;
					targetX = targetInstance.getX();
					targetY = targetInstance.getY();
					targetWidth = targetInstance.getWidth();
					targetHeight = targetInstance.getHeight();

					if ((((targetX < draggedX) && (draggedX < (targetX + targetWidth))) || ((draggedX < targetX) && (targetX < (draggedX + draggedWidth))))
							&& (((targetY < draggedY) && (draggedY < (targetY + targetHeight))) || ((draggedY < targetY) && (targetY < (draggedY + draggedHeight)))))
						returnInstance = targetInstance;
				}
			}
		}
		return returnInstance;
	}

	// additional reuse functions
	//---------------------------
	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int _projectType) {
		projectType = _projectType;
	}

	public void setReusedProject(OpdProject _project) {
		reusedProject = _project;
	}

	// functions that we ( reuse team ) changes/updated
	// in each method you'll find the change rapped with reuse comments
	//--------------------------------------------------
	/**
	 * 
	 * @param source
	 * @param destination
	 * @param linkType
	 * @param target
	 * @return
	 */
	public LinkInstance addLink(Instance source, Instance destination,
			int linkType, Opd target) {

		//reuseComment
		if (!duringLoad) {
			if (reusedProject == null) {
				if (source.getEntry().isLocked()
						&& destination.getEntry().isLocked()) {
					JOptionPane.showMessageDialog(this.getMainFrame(),
							"you can't link two locked items", "Message",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				if (source.getEntry().isOpenReused()
						|| destination.getEntry().isOpenReused()) {
					JOptionPane.showMessageDialog(this.getMainFrame(),
							"source or destination in link is open reused",
							"Message", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		//endReuseComment

		Entry sourceEntry = mainComponentsStructure.getEntry(source
				.getLogicalId());
		ConnectionEdgeInstance sourceInstance = (ConnectionEdgeInstance) sourceEntry
				.getInstance(source.getKey());

		Entry destinationEntry = mainComponentsStructure.getEntry(destination
				.getLogicalId());
		ConnectionEdgeInstance destinationInstance = (ConnectionEdgeInstance) destinationEntry
				.getInstance(destination.getKey());

		RelativeConnectionPoint sourcePoint = GraphicsUtils
				.calculateConnectionPoint(sourceInstance.getConnectionEdge(),
						destinationInstance.getConnectionEdge());
		RelativeConnectionPoint destinationPoint = GraphicsUtils
				.calculateConnectionPoint(destinationInstance
						.getConnectionEdge(), sourceInstance
						.getConnectionEdge());

		JLayeredPane tempContainer = (JLayeredPane) target.getDrawingArea();

		if (sourceInstance.getConnectionEdge().getParent() == destinationInstance
				.getConnectionEdge().getParent()) {
			tempContainer = (JLayeredPane) sourceInstance.getConnectionEdge()
					.getParent();
		}

		return addLink(sourceInstance.getConnectionEdge(), sourcePoint,
				destinationInstance.getConnectionEdge(), destinationPoint,
				tempContainer, linkType, -1, -1);
	}

	/**
	 * @name addObjectInstance
	 * @param name
	 * @param x
	 * @param y
	 * @param parentInstance
	 * @return ObjbjectInstnace this functions add a new Object to the current
	 *         Opd
	 *  
	 */
	public ObjectInstance addObjectInstance(String name, int x, int y,
			ThingEntry myEntry) {
		return addObjectInstanceToContainer(name, x, y, myEntry, currentOpd.getOpdContainer()
				.getDrawingArea());
	}
	
	/**
	 * Adds a new object instance to a given container, which can be a thing
	 * or just an OPD. 
	 * @param name	The name of the instance
	 * @param x		X coordinate
	 * @param y		Y coordinate
	 * @param myEntry	The logical entry of the instance
	 * @param container	The container that the thing is placed within. Can be 
	 * an inzoomed thing, or just the OPD. In the latter case, 
	 *  then <code>currentOpd.getOpdContainer().getDrawingArea()</code>
	 * should be passed to the method
	 * @return	The instance that was created. 
	 */
	public ObjectInstance addObjectInstanceToContainer(String name, int x, int y,
			ThingEntry myEntry, Container container) {
		IXObjectInstance newIns = addObject(x, y, container, myEntry.getId(), -1, true);
		newIns.getIXEntry().setName(name);
		newIns.getIXEntry().updateInstances();
		return (ObjectInstance) newIns;
	}

	/**
	 * @name createNewOpd
	 * 
	 * @param zoomed -
	 *            boolean value that indicates that this creation is a result of
	 *            zooming
	 * @param fInstance -
	 *            IXEntry of the things that is ths father of the new Opd
	 * @param fatherOpd
	 *            in and not unfoloing
	 * @return Opd
	 *  
	 */
	public Opd createNewOpd(boolean zoomed, Instance fInstance, Opd fatherOpd) {
		ThingInstance fThingInstance;
		long tempNum = _getFreeOpdEntry();
		String str;
		if (zoomed)
			str = " - In-Zooming OPD";
		else
			str = " -  Unfolding OPD";
		Opd newOpd = new Opd(this, fInstance.getEntry().getName() + str,
				tempNum, fatherOpd);
		mainComponentsStructure.put(tempNum, newOpd);
		ThingEntry nFather = (ThingEntry) fInstance.getEntry();
		if (zoomed)
			nFather.setZoomedInOpd(newOpd);
		else {
			fThingInstance = (ThingInstance) fInstance;
			fThingInstance.setUnfoldingOpd(newOpd);
		}
		newOpd.setMainEntry((ThingEntry) fInstance.getEntry());
		setNames4OPDs();
		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		return newOpd;
	}

	/**
	 * @name deleteRootOpd this function delete an Opd and all it's sub OPD's
	 * @param opdToDelete
	 *            The function goes over all the elements in the Opd check
	 *            weather they gave lower levels if so it calls for ther
	 *            deletion as well
	 */
	public void deleteRootOpd(Opd opdToDelete) {
		Instance abstractInstance;
		ThingInstance thInstance;
		ThingEntry ThEntry;
		Enumeration e = this.getSystemStructure().getInstancesInOpd(
				opdToDelete.getOpdId());
		while (e.hasMoreElements()) {
			abstractInstance = (Instance) e.nextElement();
			if (abstractInstance instanceof ThingInstance) {
				thInstance = (ThingInstance) abstractInstance;
				ThEntry = (ThingEntry) thInstance.getEntry();
				if (ThEntry.getZoomedInOpd() != null)
					if (ThEntry.getId() != opdToDelete.getMainEntry().getId())
						deleteRootOpd(ThEntry.getZoomedInOpd());
			}
		}
		deleteOpd(opdToDelete, true);
	}

	public StateInstance addStateInstance(Opd targetOpd,
			ObjectInstance parentInstance, StateEntry myEntry) {
		long stateInOpdId = targetOpd._getFreeEntityInOpdEntry();
		OpdObject tmpObject = (OpdObject) (parentInstance.getThing());
		OpdKey stateKey = new OpdKey(targetOpd.getOpdId(), stateInOpdId);
		StateInstance tmpStateInstance = new StateInstance(myEntry, stateKey,
				tmpObject, this);
		myEntry.addInstance(stateKey, tmpStateInstance);
		return tmpStateInstance;
	}

	/**
	 * Adds a new process instance to the current OPD. 
	 * @param name
	 * @param x
	 * @param y
	 * @param parentInstance
	 * @return
	 */
	public ProcessInstance addProcessInstance(String name, int x, int y,
			ThingEntry myEntry) {
		return addProcessInstanceToContainer(name, x, y, myEntry, currentOpd
				.getOpdContainer().getDrawingArea());
	}
	
	/**
	 * Adds a new process instance to a given container, which can be a thing
	 * or just an OPD. 
	 * @param name	The name of the instance
	 * @param x		X coordinate
	 * @param y		Y coordinate
	 * @param myEntry	The logical entry of the instance
	 * @param container	The container that the thing is placed within. Can be 
	 * an inzoomed thing, or just the OPD. In the latter case, 
	 *  then <code>currentOpd.getOpdContainer().getDrawingArea()</code>
	 * should be passed to the method
	 * @return	The instance that was created. 
	 */
	public ProcessInstance addProcessInstanceToContainer(String name, int x, int y,
			ThingEntry myEntry, Container container) {

		IXProcessInstance newIns = addProcess(x, y, container, myEntry.getId(), -1);

		newIns.getIXEntry().setName(name);
		newIns.getIXEntry().updateInstances();
		return (ProcessInstance) newIns;
	}
	

	public MainStructure getSystemStructure() {
		return this.mainComponentsStructure;
	}

	/**
	 * name : getEntryInstanceInOpd this method was created in order to find all
	 * Instances of a given Entry in a specific OPD
	 * 
	 * @param aOpd
	 * @param aEntry
	 * @return
	 */
	public Instance getEntryInstanceInOpd(Opd aOpd, Entry aEntry) {
		Enumeration e = this.getSystemStructure().getInstancesInOpd(aOpd);
		while (e.hasMoreElements()) {
			Instance aInstance = (Instance) e.nextElement();
			if (aInstance.getEntry() == aEntry)
				return aInstance;
		}
		return null;
	}

	public AbstractUndoableEdit deleteInstance(Instance delInstance) {
		//reuseComment
		if (reusedProject == null)
			if (delInstance.getEntry().isLocked()) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						" Istance is locked!!!!", "Opcat2 - Message",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}

		if (delInstance instanceof ObjectInstance) {
			return deleteObject((ObjectInstance) delInstance);
		}

		if (delInstance instanceof ProcessInstance) {
			return deleteProcess((ProcessInstance) delInstance);
		}

		if (delInstance instanceof FundamentalRelationInstance) {
			return deleteFundamentalRelation((FundamentalRelationInstance) delInstance);
		}

		if (delInstance instanceof GeneralRelationInstance) {
			return deleteGeneralRelation((GeneralRelationInstance) delInstance);
		}

		if (delInstance instanceof LinkInstance) {
			return deleteLink((LinkInstance) delInstance);
		}

		if (delInstance instanceof StateInstance) {
			return deleteState((StateInstance) delInstance);
		}

		System.err.println("Error " + delInstance.getClass().toString() + " "
				+ delInstance.getEntry().getLogicalEntity().getName());
		return null;
	}

	// delete Opd
	// created in order to support automatic deletion of an Opd
	public boolean deleteOpd(Opd opdToDelete) {
		return deleteOpd(opdToDelete, false);
	}

	// to the next function we added boolean field that indicates
	// weather this deletion is automatic - does ot need the user approval

	//endReuseComment
	public boolean deleteOpd(Opd opdToDelete, boolean automaticDelete) {
		if (!automaticDelete) {
			int retVal = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
					"Are you sure you want to delete '" + opdToDelete.getName()
							+ "'?", "Opd Deletion confirmation",
					JOptionPane.YES_NO_OPTION);
			if (retVal != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		// -1 test if the OPD is not root OPD
		if (opdToDelete.getOpdId() == 1) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Cannot delete root OPD", "Opcat2 - Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//reuseCommnet
		if (reusedProject == null)
			if (opdToDelete.isLocked()) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						"Cannot delete  OPD - it is locked", "Opcat2 - Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		//endReuseCommet
		Enumeration enum;
		// 0 check if this opd is leaf
		// run on all opds and compare to parent opd to opdToDelete
		Opd tempOpd;
		for (enum = mainComponentsStructure.getOpds(); enum.hasMoreElements();) {
			tempOpd = (Opd) enum.nextElement();
			if ((tempOpd.getParentOpd() != null)
					&& (tempOpd.getParentOpd().getOpdId() == opdToDelete
							.getOpdId())) {
				JOptionPane
						.showMessageDialog(
								Opcat2.getFrame(),
								"Unable to delete OPD.\nThis OPD has things that are Unfolded or In-zoomed. Delete child OPDs first.",
								"Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		// 1 delete all links
		for (enum = mainComponentsStructure.getLinksInOpd(opdToDelete); enum
				.hasMoreElements();) {
			deleteLink((LinkInstance) enum.nextElement());
		}
		// 2 delete all general relations
		for (enum = mainComponentsStructure
				.getGeneralRelationsInOpd(opdToDelete); enum.hasMoreElements();) {
			deleteGeneralRelation((GeneralRelationInstance) enum.nextElement());
		}
		// 3 delete all fundamental relations
		for (enum = mainComponentsStructure
				.getFundamentalRelationsInOpd(opdToDelete); enum
				.hasMoreElements();) {
			deleteFundamentalRelation((FundamentalRelationInstance) enum
					.nextElement());
		}
		// 4 delete all things except main instance
		ThingInstance ti;
		for (enum = mainComponentsStructure.getThingsInOpd(opdToDelete); enum
				.hasMoreElements();) {
			ti = (ThingInstance) enum.nextElement();
			if (!ti.getKey().equals(opdToDelete.getMainInstance().getKey())) {
				deleteInstance(ti);
			}
		}
		//finally delete main instance
		ThingInstance thInstance;
		ThingEntry thEntry = (ThingEntry) opdToDelete.getMainInstance()
				.getEntry();
		if ((thEntry.getZoomedInOpd() != null)
				&& (thEntry.getZoomedInOpd().getOpdId() == opdToDelete
						.getOpdId())) { // In-Zoomed Opd
			thEntry.setZoomedInOpd(null);
		} else {
			for (Enumeration e = thEntry.getInstances(); e.hasMoreElements();) {
				thInstance = (ThingInstance) e.nextElement();
				if ((thInstance.getUnfoldingOpd() != null)
						&& (thInstance.getUnfoldingOpd().getOpdId() == opdToDelete
								.getOpdId())) {
					thInstance.setUnfoldingOpd(null);
					thEntry.updateInstances();
				}
			}
		}
		deleteInstance(opdToDelete.getMainInstance());

		mainComponentsStructure.deleteOpd(opdToDelete.getOpdId());
		opdToDelete.dispose();
		setNames4OPDs();

		Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		// clear Undo/Redo
		Opcat2.getUndoManager().discardAllEdits();
		Opcat2.setRedoEnabled(false);
		Opcat2.setUndoEnabled(false);

		return true;
	}

	/**
	 * Zooms in selected item (some OPM thing). This operation opens a new OPD
	 * and performs zooming in operation in this window
	 */
	public void zoomIn() {
		ThingEntry myEntry;
		ThingInstance newInstance;
		ThingInstance parentInstance;
		Opd parent = currentOpd;

		Instance myInstance = currentOpd.getSelectedItem();
		if (myInstance == null || !(myInstance instanceof ThingInstance)) {
			return;
		}

		myEntry = (ThingEntry) myInstance.getEntry();

		if (myEntry.getZoomedInOpd() == null) {
			//reuseComment
			if (myEntry.isLocked()) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						"thing is locked!!!!", "Opcat2 - Message",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			//endReuseComment
			int retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
					"A new OPD will be created\n" + "Do you want to continue?",
					"In-Zooming", JOptionPane.YES_NO_OPTION);

			if (retValue != JOptionPane.YES_OPTION)
				return;
		}

		currentOpd.removeSelection();
		currentOpd = myEntry.getZoomedInOpd();

		if (currentOpd == null) {
			Opcat2.getUndoManager().discardAllEdits();
			Opcat2.setRedoEnabled(false);
			Opcat2.setUndoEnabled(false);

			tempNum = _getFreeOpdEntry();
			currentOpd = new Opd(this, myEntry.getName() + " - In-Zooming OPD",
					tempNum, parent);

			currentOpd.show();

			mainComponentsStructure.put(tempNum, currentOpd);
			myEntry.setZoomedInOpd(currentOpd);

			if (myEntry instanceof ProcessEntry) {
				newInstance = addProcess(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), myEntry.getId(), -1);
			} else {
				newInstance = addObject(0, 0, currentOpd.getOpdContainer()
						.getDrawingArea(), myEntry.getId(), -1, true);
			}

			newInstance.setZoomedIn(true);
			((OpdThing) (newInstance.getConnectionEdge())).setTextPosition("N");

			Integer nSize = (Integer) config.getProperty("NormalSize");
			Integer cSize = (Integer) config.getProperty("CurrentSize");

			double factor = cSize.doubleValue() / nSize.doubleValue();

			newInstance.setLocation((int) (225.0 * factor),
					(int) (170.0 * factor));
			newInstance.setSize((int) (570.0 * factor), (int) (475.0 * factor));
			if (newInstance instanceof ObjectInstance) {
				((OpdObject) newInstance.getThing()).drawStates();
			}
			drawLinkedThings(newInstance, (ThingInstance) myInstance);
			myEntry.updateInstances();

			currentOpd.setMainEntry(myEntry);
			currentOpd.setMainInstance(newInstance);
			setNames4OPDs();
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
		}
		currentOpd.setVisible(true);
		parentDesktop.getDesktopManager().deiconifyFrame(
				currentOpd.getOpdContainer());

		try {
			currentOpd.getOpdContainer().setSelected(true);
		} catch (PropertyVetoException e) {
		}
	}

	/**
	 * This method adds Link of linkType while first checks legality of this
	 * link
	 */
	public LinkInstance addLinkChecked(OpdConnectionEdge source,
			RelativeConnectionPoint pPoint1, OpdConnectionEdge destination,
			RelativeConnectionPoint pPoint2, Container container, int linkType,
			long entityId, long entityInOpdId) {
		if (_checkLinkLegality(source, destination, linkType) == false) {
			return null;
		}
		//reuseComment
		if (reusedProject == null)
			if (source.getEntry().isLocked()
					&& destination.getEntry().isLocked()) {
				JOptionPane.showMessageDialog(this.getMainFrame(),
						"you can't relate two locked items", "Message",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		return _addLink(source, pPoint1, destination, pPoint2, container,
				linkType, entityId, entityInOpdId);
	}

	/**
	 * Adds general structural relation between source and destination to the
	 * specified container. relationType specifies which type of general
	 * structural relation we add.
	 * 
	 * @param source
	 *            source thing for this general structural relation.
	 * @param side1 -
	 *            the side of source component. Possible values are defined as
	 *            constants in file - OpdBaseComponent.
	 * @param param1
	 *            Relative coordinate of the point to which we connect this
	 *            general structural relation on the source component. Float
	 *            number between 0 and 1 that means which point (relatively) is
	 *            connection point. In order to get absolute point of
	 *            connection(in coordinates of connected object) just multiply
	 *            the length of connection side by this number.
	 * @param destination
	 *            destination thing for this pgeneral structural relation.
	 * @param side2 -
	 *            the side of destination component. Possible values are defined
	 *            as constants in file - OpdBaseComponent.
	 * @param param2
	 *            Relative coordinate of the point to which we connect this
	 *            general structural relation on the destination component.
	 *            Float number between 0 and 1 that means which point
	 *            (relatively) is connection point. In order to get absolute
	 *            point of connection(in coordinates of connected object) just
	 *            multiply the length of connection side by this number.
	 * @param relationType
	 *            one of constants defined in Interface OpmEntities.Constants
	 *            specifying which kind of general structural relation will be
	 *            added.
	 * @param container
	 *            Container to which we add this general structural relation.
	 */
	public GeneralRelationInstance addGeneralRelation(OpdConnectionEdge source,
			RelativeConnectionPoint pPoint1, OpdConnectionEdge destination,
			RelativeConnectionPoint pPoint2, Container container,
			int relationType, long entityId, long entityInOpdId) {
		OpmGeneralRelation lRelation = null;
		GeneralRelationEntry myEntry;
		GeneralRelationInstance myInstance;
		ConnectionEdgeEntry sourceEntry, destinationEntry;

		long relationId;
		long relationInOpdId;
		OpdKey instanceId;
		//reuseComment
		if (reusedProject == null) {
			if (source.getEntry().isLocked()
					&& destination.getEntry().isLocked()) {
				JOptionPane.showMessageDialog(this.getMainFrame(),
						"you can't add relation to two locked items",
						"Message", JOptionPane.ERROR_MESSAGE);
				return null;
			}

			if (source.getEntry().isOpenReused()
					|| destination.getEntry().isOpenReused()) {
				JOptionPane.showMessageDialog(this.getMainFrame(),
						"source or destination in relation is open reused",
						"Message", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		//endReuseComment

		long sourceId = source.getEntity().getId();
		long destinationId = destination.getEntity().getId();

		sourceEntry = (ConnectionEdgeEntry) (mainComponentsStructure
				.getEntry(sourceId));
		destinationEntry = (ConnectionEdgeEntry) (mainComponentsStructure
				.getEntry(destinationId));

		if (entityId != -1) {
			myEntry = (GeneralRelationEntry) mainComponentsStructure
					.getEntry(entityId);
			lRelation = (OpmGeneralRelation) myEntry.getLogicalEntity();
		} else {
			relationId = _getFreeEntityEntry();

			switch (relationType) {
			case OpcatConstants.UNI_DIRECTIONAL_RELATION: {
				lRelation = new OpmUniDirectionalRelation(relationId,
						"UniDirectionalRelation" + relationId, source
								.getEntity(), destination.getEntity());
				break;
			}
			case OpcatConstants.BI_DIRECTIONAL_RELATION: {
				lRelation = new OpmBiDirectionalRelation(relationId,
						"BiDirectionalRelation" + relationId, source
								.getEntity(), destination.getEntity());
				break;
			}

			default: {
				JOptionPane
						.showMessageDialog(
								Opcat2.getFrame(),
								" Serious internal bug occured in AddLink function \n Please contact software developers.",
								"Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			}

			myEntry = new GeneralRelationEntry(lRelation, this);
			mainComponentsStructure.put(lRelation.getId(), myEntry);
		}

		sourceEntry.addGeneralRelationSource(lRelation);
		destinationEntry.addGeneralRelationDestination(lRelation);

		if (entityInOpdId == -1) {
			relationInOpdId = currentOpd._getFreeEntityInOpdEntry();
		} else {
			relationInOpdId = entityInOpdId;
		}

		instanceId = new OpdKey(currentOpd.getOpdId(), relationInOpdId);
		myInstance = new GeneralRelationInstance(source, pPoint1, destination,
				pPoint2, lRelation, instanceId, this, container);
		myEntry.addInstance(instanceId, myInstance);
		return myInstance;
	}

	public ProcessInstance addProcess(int pX, int pY, Container container,
			long entityId, long entityInOpdId) {
		ProcessEntry myEntry = null;
		ProcessInstance myInstance;
		OpmProcess lProcess = null;

		long processId;
		long processInOpdId;

		/*
		 * Check for name existance if exists lPro is reference to exist, if
		 * asked and myEntry too
		 */

		//resueComment
		if (reusedProject == null) {
			if (getCurrentOpd().getReusedSystemPath() != null) {
				double loc = (this.getCurrentOpd().getDrawingArea()
						.getLocationOnScreen()).getY();
				double height;
				if (getCurrentOpd().getOpdId() == 1)
					height = this.getCurrentOpd().getDrawingArea().getHeight()
							/ 2 + loc;
				else
					height = this.getCurrentOpd().getDrawingArea().getHeight()
							/ 10 + loc;
				if (pY < height) {
					JOptionPane.showMessageDialog(this.getMainFrame(),
							"cant add process to the open section", "Message",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}

			if (this.getCurrentOpd().isLocked()) {
				JOptionPane.showMessageDialog(this.getMainFrame(),
						"can't add process OPD is locked", "Message",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		//endReuseComment

		if (entityId == -1) {
			processId = _getFreeEntityEntry();
			lProcess = new OpmProcess(processId, "Process " + processId);
			if (container instanceof OpdThing) {
				ThingEntry parentEntry = (ThingEntry) ((OpdThing) container)
						.getEntry();
				myEntry = new ProcessEntry(lProcess, parentEntry, this);
			} else {
				myEntry = new ProcessEntry(lProcess, this);
			}
			mainComponentsStructure.put(processId, myEntry);
		} else {
			processId = entityId;
			myEntry = (ProcessEntry) mainComponentsStructure.getEntry(entityId);
		}

		if (entityInOpdId == -1) {
			processInOpdId = currentOpd._getFreeEntityInOpdEntry();
		} else {
			processInOpdId = entityInOpdId;
		}

		OpdKey key = new OpdKey(currentOpd.getOpdId(), processInOpdId);
		myInstance = new ProcessInstance(myEntry, key, container, this);
		myInstance.setLocation(pX - myInstance.getWidth() / 2, pY
				- myInstance.getHeight() / 2);
		myEntry.addInstance(key, myInstance);
		myInstance.add2Container(container);

		return myInstance;
	}

	/**
	 * Adds fundamental relation between source and destination to the specified
	 * container. relationType specifies which type of relation we add.
	 * 
	 * @param source
	 *            source thing for this fundamental relation.
	 * @param side1 -
	 *            the side of source component. Possible values are defined as
	 *            constants in file - OpdBaseComponent.
	 * @param param1
	 *            Relative coordinate of the point to which we connect the
	 *            relation on the source component. Float number between 0 and 1
	 *            that means which point (relatively) is connection point. In
	 *            order to get absolute point of connection(in coordinates of
	 *            connected object) just multiply the length of connection side
	 *            by this number.
	 * @param destination
	 *            destination thing for this fundamental relation.
	 * @param side2 -
	 *            the side of destination component. Possible values are defined
	 *            as constants in file - OpdBaseComponent.
	 * @param param2
	 *            Relative coordinate of the point to which we connect the
	 *            relation on the destination component. Float number between 0
	 *            and 1 that means which point (relatively) is connection point.
	 *            In order to get absolute point of connection(in coordinates of
	 *            connected object) just multiply the length of connection side
	 *            by this number.
	 * @param relationType
	 *            one of constants defined in Interface OpmEntities.Constants
	 *            specifying which kind of fundamental relation will be added.
	 * @param container
	 *            Container to which we add this fundamental relation.
	 * @return OpdFundamentalRelation created OpdFundamentalRelation.
	 */

	public FundamentalRelationInstance addRelation(OpdConnectionEdge source,
			RelativeConnectionPoint pPoint1, OpdConnectionEdge destination,
			RelativeConnectionPoint pPoint2, int relationType,
			Container container, long entityId, long entityInOpdId) {

		Point tempPoint;
		FundamentalRelationEntry myLogicalEntry;
		GraphicalRelationRepresentation myGraphicEntry;
		FundamentalRelationInstance myInstance;
		ConnectionEdgeEntry sourceEntry, destinationEntry;

		OpmFundamentalRelation lRelation = null;

		long relationId;
		long relationInOpdId;
		OpdKey instanceId;
		GraphicRepresentationKey graphicEntryKey;

		//reuseComment

		boolean openReuseFlag = false;
		if (!duringLoad) {

			if (reusedProject == null) {
				if (source.getEntry().isLocked()
						&& destination.getEntry().isLocked()) {
					JOptionPane.showMessageDialog(this.getMainFrame(),
							"you can't link two locked items", "Message",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				// if it is inheritence

				if (relationType == OpcatConstants.SPECIALIZATION_RELATION)
					if (destination.getEntry().isOpenReused()) {
						JOptionPane.showMessageDialog(this.getMainFrame(),
								"source in relation is open reused", "Message",
								JOptionPane.ERROR_MESSAGE);
						return null;
					}
				if (reusedProject == null)
					if (relationType != OpcatConstants.SPECIALIZATION_RELATION)
						if (source.getEntry().isOpenReused()
								|| destination.getEntry().isOpenReused()) {
							JOptionPane.showMessageDialog(this.getMainFrame(),
									"source in relation is open reused",
									"Message", JOptionPane.ERROR_MESSAGE);
							return null;
						}

				if (relationType == OpcatConstants.SPECIALIZATION_RELATION
						&& source.getEntry().isOpenReused()) {
					ThingInstance sourceInstance = (ThingInstance) getEntryInstanceInOpd(
							this.getCurrentOpd(), source.getEntry());
					ThingInstance destinationInstance = (ThingInstance) getEntryInstanceInOpd(
							this.getCurrentOpd(), destination.getEntry());
					openReuseFlag = true;
					getCurrentOpd().addOpenReusedInheritRelationToTable(
							sourceInstance, destinationInstance);
					//endReuseComment
				}
			}
		}

		tempPoint = computeRelationPoint(source, destination);

		sourceEntry = (ConnectionEdgeEntry) (mainComponentsStructure
				.getEntry(((OpmConnectionEdge) source.getEntity()).getId()));
		destinationEntry = (ConnectionEdgeEntry) (mainComponentsStructure
				.getEntry(((OpmConnectionEdge) destination.getEntity()).getId()));

		if (entityId != -1) {
			myLogicalEntry = (FundamentalRelationEntry) mainComponentsStructure
					.getEntry(entityId);
			lRelation = (OpmFundamentalRelation) myLogicalEntry
					.getLogicalEntity();
		} else {
			lRelation = (OpmFundamentalRelation) (sourceEntry
					.getRelationByDestination(destination.getEntity().getId(),
							relationType));

			if (lRelation == null) {
				relationId = _getFreeEntityEntry();
				lRelation = getRelation4type(relationType, relationId, source
						.getEntity(), destination.getEntity());
				myLogicalEntry = new FundamentalRelationEntry(lRelation, this);
				mainComponentsStructure.put(relationId, myLogicalEntry);
			} else {
				myLogicalEntry = (FundamentalRelationEntry) mainComponentsStructure
						.getEntry(lRelation.getId());
				lRelation = (OpmFundamentalRelation) myLogicalEntry
						.getLogicalEntity();
			}
		}

		sourceEntry.addRelationSource(lRelation);
		destinationEntry.addRelationDestination(lRelation);

		graphicEntryKey = new GraphicRepresentationKey(new OpdKey(source
				.getOpdId(), source.getEntityInOpdId()), relationType);

		myGraphicEntry = mainComponentsStructure
				.getGraphicalRepresentation(graphicEntryKey);

		if (myGraphicEntry == null) {
			myGraphicEntry = new GraphicalRelationRepresentation(source,
					new RelativeConnectionPoint(OpcatConstants.S_BORDER, 0.5),
					relationType, this, tempPoint, container);
			mainComponentsStructure.put(graphicEntryKey, myGraphicEntry);
		}

		if (entityInOpdId == -1) {
			relationInOpdId = currentOpd._getFreeEntityInOpdEntry();
		} else {
			relationInOpdId = entityInOpdId;
		}

		instanceId = new OpdKey(currentOpd.getOpdId(), relationInOpdId);

		myInstance = new FundamentalRelationInstance(lRelation, destination,
				pPoint2, myGraphicEntry, instanceId, this, container);

		if (container instanceof OpdThing) {
			myInstance.setParent((OpdThing) container);
		}
		myLogicalEntry.addInstance(instanceId, myInstance);
		myGraphicEntry.addInstance(instanceId, myInstance);
		if (openReuseFlag)
			myInstance.getEntry().setOpenReused(true);
		return myInstance;
	}

	/**
	 * Adds thing to the specified container. It is generic function for adding
	 * objects to the project. If entityId equals to -1 allocates new entityId,
	 * otherwise uses received entityId
	 * 
	 * @param pX
	 *            X coordinate to where we place added component.
	 * @param pY
	 *            Y coordinate to where we place added component.
	 * @param container
	 *            Container to which we add the thing. Can be DrawingArea of
	 *            some Opd (if we add object directly to Opd) or OpdThing (if we
	 *            add object to some inzoomed thing).
	 * @param entityId -
	 *            entity id of added thing. If entityId equals to -1 allocates
	 *            new entityId, otherwise uses received entityId.
	 * @param entityInOpdId -
	 *            entity in OPD id of added thing.If entityId equals to -1
	 *            allocates new entityId, otherwise uses received entityInOpdId.
	 *            return ObjectInstance ObjectInstance of created object.
	 */
	public ObjectInstance addObject(int pX, int pY, Container container,
			long entityId, long entityInOpdId, boolean bringStates) {
		ObjectEntry myEntry = null;
		ObjectInstance myInstance;
		OpmObject lObject = null;

		long objectId;
		long objectInOpdId;
		//reuseComment
		if (reusedProject == null) {
			if (getCurrentOpd().getReusedSystemPath() != null) {
				double loc = (this.getCurrentOpd().getDrawingArea()
						.getLocationOnScreen()).getY();
				double height;
				if (getCurrentOpd().getOpdId() == 1)
					height = this.getCurrentOpd().getDrawingArea().getHeight()
							/ 2 + loc;
				else
					height = this.getCurrentOpd().getDrawingArea().getHeight()
							/ 10 + loc;

				if (pY < height) {
					JOptionPane.showMessageDialog(this.getMainFrame(),
							"cant add object to the open section", "Message",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
			if (this.getCurrentOpd().isLocked()) {
				JOptionPane.showMessageDialog(this.getMainFrame(),
						"cant add object to a locked OPD", "Message",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		//endReuseComment

		if (entityId == -1) {
			// create
			objectId = _getFreeEntityEntry();
			lObject = new OpmObject(objectId, "Object " + objectId);

			if (container instanceof OpdThing) {
				ThingEntry parentEntry = (ThingEntry) ((OpdThing) container)
						.getEntry();
				myEntry = new ObjectEntry(lObject, parentEntry, this);
			} else {
				myEntry = new ObjectEntry(lObject, this);
			}
			mainComponentsStructure.put(objectId, myEntry);

		} else {
			objectId = entityId;
			myEntry = (ObjectEntry) mainComponentsStructure.getEntry(objectId);
		}

		if (entityInOpdId == -1) {
			objectInOpdId = currentOpd._getFreeEntityInOpdEntry();
		} else {
			objectInOpdId = entityInOpdId;
		}

		OpdKey key = new OpdKey(currentOpd.getOpdId(), objectInOpdId);
		myInstance = new ObjectInstance(myEntry, key, container, this,
				bringStates);
		myInstance.setLocation(pX - myInstance.getWidth() / 2, pY
				- myInstance.getHeight() / 2);
		myEntry.addInstance(key, myInstance);
		myInstance.add2Container(container);

		return myInstance;
	}

	public LinkInstance addLink(OpdConnectionEdge source,
			RelativeConnectionPoint pPoint1, OpdConnectionEdge destination,
			RelativeConnectionPoint pPoint2, Container container, int linkType,
			long entityId, long entityInOpdId) {
		//reuseComment
		if (!duringLoad) {
			if (reusedProject == null)
				if (isBothEntriesLocked(source.getEntry(), destination
						.getEntry())) {
					JOptionPane.showMessageDialog(this.getMainFrame(),
							"you can't link two locked items", "Message",
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
		}
		if (this.getCurrentOpd().getReusedSystemPath() != null) {
			if (destination.getEntry().isOpenReused()
					|| source.getEntry().isOpenReused()) {
				JOptionPane.showMessageDialog(this.getMainFrame(),
						"you can't link two locked items", "Message",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		//endReuseComment
		return _addLink(source, pPoint1, destination, pPoint2, container,
				linkType, entityId, entityInOpdId);

	}

	/**
	 * Adds state to specified parentObject. if entityId equals to -1 allocates
	 * new entityId, otherwise uses received entityId.
	 * 
	 * @param parentObject
	 *            paren object for this state.
	 * @param entityId -
	 *            entity id of added thing. If entityId equals to -1 allocates
	 *            new entityId, otherwise uses received entityId.
	 * @param entityInOpdId -
	 *            entity i OPD id of added thing. return OpdState created
	 *            OpdState.
	 */
	public StateEntry addState(OpdObject parentObject) {
		OpmState lState;

		OpmObject lParentObject = (OpmObject) (parentObject.getEntity());
		ObjectEntry objectEntry = (ObjectEntry) (mainComponentsStructure
				.getEntry(lParentObject.getId()));
		long stateId;
		//reuseCommnet
		if (objectEntry.isLocked()) {
			JOptionPane.showMessageDialog(this.getMainFrame(),
					"Can't add state - Object is locked", "Message",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		//endReuseCommnet
		stateId = _getFreeEntityEntry();
		lState = new OpmState(stateId, "State " + stateId);

		StateEntry myEntry = null;

		objectEntry.addState(lState);
		myEntry = new StateEntry(lState, lParentObject, this);
		mainComponentsStructure.put(stateId, myEntry);

		for (Enumeration e = objectEntry.getInstances(); e.hasMoreElements();) {
			ObjectInstance tmpObjectInstance = (ObjectInstance) (e
					.nextElement());
			OpdObject tmpObject = (OpdObject) (tmpObjectInstance.getThing());
			Opd tmpOpd = mainComponentsStructure.getOpd(tmpObject.getOpdId());
			if(tmpOpd == null)
              	tmpOpd = clipBoard;
			long stateInOpdId = tmpOpd._getFreeEntityInOpdEntry();
			OpdKey stateKey = new OpdKey(tmpOpd.getOpdId(), stateInOpdId);
			StateInstance tmpStateInstance = new StateInstance(myEntry,
					stateKey, tmpObject, this);
			myEntry.addInstance(stateKey, tmpStateInstance);
		}

		return myEntry;

	}

	//end reuse project functions
	
	/**
	 * Returns the global unique ID of the project. The ID is unique for each opcat
	 * projects that exists. 
	 */
	public String getGlobalID()	{
		return opcatGlobalID;
	}
	
	/**
	 * Sets the global ID.
	 * @param globalID	The string containing the global ID of the project. 
	 */
	public void setGlobalID(String globalID)	{
		opcatGlobalID = globalID;
	}
	
	
	/**
	 * @return The number of entries in the main structure (objects, processes and states). 
	 */
	public int getNumOfEntries()	{
		return mainComponentsStructure.getNumOfEntries();
	}

	/**
	 * @return Returns the isPresented.
	 */
	public boolean isPresented() {
		return isPresented;
	}

	/**
	 * @param isPresented The isPresented to set.
	 */
	public void setPresented(boolean isPresented) {
		this.isPresented = isPresented;
	}
}