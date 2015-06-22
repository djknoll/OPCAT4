package gui;

import exportedAPI.OpcatConstants;
import exportedAPI.OpcatExtensionTool;
import exportedAPI.OpcatExtensionToolX;
import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPIx.IXSystem;
import extensionTools.Documents.UserInterface.DocSel;
import extensionTools.Documents.UserInterface.HandleTemp;
import extensionTools.etAnimation.AnimationSettingsWindow;
import extensionTools.etAnimation.AnimationStatusBar;
import extensionTools.etAnimation.AnimationSystem;
import extensionTools.hio.DrawAppNew;
import extensionTools.opltoopd.Opl2Opd;
import extensionTools.search.SearchDialog;
import extensionTools.search.SearchExtensionTool;
import extensionTools.uml.userInterface.UMLChooser;
import extensionTools.validator.Validator;
import gui.actions.edit.AddElementAction;
import gui.actions.edit.CopyAction;
import gui.actions.edit.CutAction;
import gui.actions.edit.DeleteAction;
import gui.actions.edit.MergeDraggingAction;
import gui.actions.edit.PasteAction;
import gui.actions.edit.RedoAction;
import gui.actions.edit.SelectAllAction;
import gui.actions.edit.UndoAction;
import gui.actions.file.CloseAction;
import gui.actions.file.ExitAction;
import gui.actions.file.LoadExampleAction;
import gui.actions.file.LoadOpxAction;
import gui.actions.file.LoadPrevious;
import gui.actions.file.NewProjectAction;
import gui.actions.file.ReuseImportAction;
import gui.actions.file.SaveAsImageAction;
import gui.actions.file.SaveAsOpxAction;
import gui.actions.file.SaveOpxAction;
import gui.actions.file.SelectDataBaseAction;
import gui.actions.help.HelpAboutAction;
import gui.actions.help.HelpContentsAction;
import gui.actions.help.OpenUrlAction;
import gui.actions.plugins.VisWebAction;
import gui.controls.EditControl;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.images.animation.AnimationImages;
import gui.images.misc.MiscImages;
import gui.images.opcaTeam.OPCATeamImages;
import gui.images.opm.OPMImages;
import gui.images.standard.StandardImages;
import gui.license.ILicense;
import gui.license.LicenseFactory;
import gui.metaLibraries.dialogs.LibrariesLoadingWindow;
import gui.opdGraphics.SplashLabel;
import gui.opdGraphics.dialogs.GeneralBiDirRelationPropertiesDialog;
import gui.opdGraphics.dialogs.GeneralUniDirRelationPropertiesDialog;
import gui.opdGraphics.dialogs.LinkPropertiesDialog;
import gui.opdGraphics.dialogs.ObjectPropertiesDialog;
import gui.opdGraphics.dialogs.ProcessPropertiesDialog;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdProject.GenericTable;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.OplColorsDialog;
import gui.opdProject.ProjectPropertiesDialog;
import gui.opdProject.StateMachine;
import gui.opmEntities.OpmBiDirectionalRelation;
import gui.opmEntities.OpmUniDirectionalRelation;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.repository.Repository;
import gui.server.OpcatDB;
import gui.undo.OpcatUndoManager;
import gui.util.Configuration;
import gui.util.CustomFileFilter;
import gui.util.ExtensionToolInfo;
import gui.util.ExtensionToolsInstaller;
import gui.util.FileCopy;
import gui.util.JToolBarButton;
import gui.util.JToolBarToggleButton;
import gui.util.LastFileEntry;
import gui.util.OpcatException;
import gui.util.OpcatLogger;
import gui.util.debug.Debug;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.FontRenderContext;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.StyledEditorKit.BoldAction;
import javax.swing.undo.UndoManager;


import org.objectprocess.Client.ChatMessageHandler;
import org.objectprocess.Client.ChatRoom;
import org.objectprocess.Client.CollaborativeSessionMessageHandler;
import org.objectprocess.Client.TeamMember;
import org.objectprocess.team.ActiveCollaborativeSession;
import org.objectprocess.team.PropertiesDialog;

public class Opcat2 {
	

    private static final String EMPTY_HTML = "<html><head></head><body></body></html>";

    private Hashtable extensionsTable;

    private static JFrame myFrame;

    private static OpdProject currentProject;

    /** *******************HIOTeam*************** */
    public static DrawAppNew drawing = null;

    private JMenuBar mainMenuBar;

    private JToolBar componentsToolBar;

    private JToolBar standardToolBar;

    private JToolBar animationToolBar;

    private JPanel diagramPanel;

    private AnimationSystem anSystem;
    

    /**
     * A Validator object - enables validaton of the model.
     * 
     * @author Eran Toch
     */
    private Validator validator = null;

    /**
     * Holds the license object for the Opcat instance that determines the
     * license policy.
     */
    private static ILicense license = null;

    private boolean projectWasOpened;

    private JDesktopPane desktop;

    private JTabbedPane extensionToolsPane;

    private static JTextPane oplPane;

    protected static OpcatUndoManager myUndoManager = new OpcatUndoManager();

    private Hashtable m_lfs; // look & feel hashtable

    private final static String fileSeparator = System
            .getProperty("file.separator");

    private final static String winImagesPath = "images" + fileSeparator
            + "winimages" + fileSeparator;

    public final static String javaImagesPath = "images" + fileSeparator
            + "javaimages" + fileSeparator;

    private final static String exapmplesDB = "examples" + fileSeparator
            + "Examples.mdb";

    private final static String opmTypes = "codeGenerator" + fileSeparator
            + "opl2Java" + fileSeparator + "opmTypes";

    public final static String OPCAT_SITE_URL = "http://www.opcat.com";
    
    
    private ImageIcon emptyIcon = StandardImages.EMPTY;

    private String currentImagesPath;

    private OpcatDB dataBase;

    private static Repository repository;

    private ClassLoader loader;

    private JToolBarButton newButton;

    private JToolBarButton openButton;

    private JToolBarButton closeButton;

    private JToolBarButton saveButton;

    private JToolBarButton printButton;
    
    private JToolBarButton searchButton; 

    private JToolBarButton cutButton;

    private JToolBarButton copyButton;

    private JToolBarButton pasteButton;

    private JToolBarButton anZoomInButton;

    private JToolBarButton anZoomOutButton;

    private JToolBarButton zoomInButton;

    private JToolBarButton zoomOutButton;

    private JToolBarButton importButton;

    private JToolBarButton metaLibsButton;

    //	Valdidation button decleration - by Eran Toch
    private JToolBarButton validateButton;

    private JToolBarToggleButton anPauseButton;

    private JLabel anStatusLabel;

    private JTextField anForwardField;

    private JTextField anBackwardField;

    private static JToolBarToggleButton showHideExtensionTools;

    private static JToolBarButton undoButton;

    private static JToolBarButton redoButton;

    private JToolBarButton deleteButton;

    private static JToolBarToggleButton moveOnDragButton, selectOnDragButton;

    private ButtonGroup moveSelectGroup = new ButtonGroup();

    /** *****HIOTeam**** */
    private static JToolBarToggleButton drawOnDragButton;

    private JMenuItem newItem;

    private JMenuItem openItem;

    private JMenuItem closeItem;

    private JMenuItem saveItem;

    private JMenuItem saveAsItem;

    private JMenuItem printItem;

    private JMenuItem printPreview;

    private JMenuItem printQuick;

    private JMenuItem cutItem;

    private JMenuItem copyItem;

    private JMenuItem pasteItem;

    private static JMenuItem undoItem;

    private static JMenuItem redoItem;

    private JMenuItem grZoomInItem;

    private JMenuItem grZoomOutItem;

    private JMenuItem deleteItem;

    private JMenuItem refreshItem;

    private JMenuItem selectAllItem;

    private JMenuItem searchItem ; 
    //OPCATeam: Add to the menu item server, its posibilities: connect,
    // disconnect and properties.
    private JMenuItem connectItem;

    private JMenuItem disconnectItem;

    private JMenuItem propertiesItem;

    private static ChatRoom chatRoom;

    private TeamMember teamMember = null;

    private CollaborativeSessionMessageHandler csMessageHandler = null;

    private ChatMessageHandler chatMessageHandler = null;

    private ActiveCollaborativeSession activeCollaborativeSession = null;

    private Opcat2 myOpcat2 = this;

    //End OPCATeam

    private JWindow splashScreen = null;

    private JWindow waitScreen = null;

    //By Eran Toch
    private JWindow metaWaitScreen = null;

    private JLabel splashLabel = null;

    private JLabel splashText = null;

    private JProgressBar jpb;

    private static boolean isWholeProjectCompiled;

    //	private static JCheckBoxMenuItem isOPLShown;
    private JSplitPane desktopTools;

    private final ImageIcon logoIcon = MiscImages.LOGO_SMALL_ICON;

    private JDialog aboutDlg;

    /**
     * If set to <code>true</code>, then OPCAT2 would present the process
     * name message (the one with the "ing").
     * 
     * @author Eran Toch
     */
    private static boolean showProcessNameMessage = true;

    public static final int GRAPHICAL_CHANGE = 1;

    public static final int LOGICAL_CHANGE = 2;

    public static final int OPD_CHANGE = 3;

    private int progressCounter = 0;

    private JToolBar[] toolbars;

    private JFileChooser myFileChooser = new JFileChooser();

    private static String drawingAreaOnMouseDragAction = "select";

    // can be 'select' or 'move'

    //ReuseComment
    private JMenuItem reuseImportItem;

    private JMenuItem draggingButton;

    //EndReuseComment

    /** ******************** File Actions ************************** */
    private LoadOpxAction loadOpxActionA = null;

    private SaveOpxAction saveOpxActionA = null;

    private SaveAsOpxAction saveAsOpxActionA = null;

    private CloseAction closeActionA = null;

    private ExitAction exitActionA = null;

    private NewProjectAction newProjectActionA = null;

    private LoadExampleAction loadExampleActionA = null;

    private SelectDataBaseAction selectDataBaseActionA = null;

    private SaveAsImageAction saveAsImageActionA = null;

    private ReuseImportAction reuseImportActionA = null;

    /** ***************** Edit Actions *************************** */
    private MergeDraggingAction mergeDraggingActionA = null;

    private CopyAction copyActionA = null;

    private CutAction cutActionA = null;

    private DeleteAction deleteActionA = null;

    private PasteAction pasteActionA = null;

    private RedoAction redoActionA = null;

    private UndoAction undoActionA = null;

    private SelectAllAction selectAllActionA = null;

    /** ***************** Add Element Actions *************************** */
    private AddElementAction objectAction = null;

    private AddElementAction apAction = null;

    private AddElementAction fcAction = null;

    private AddElementAction gsAction = null;

    private AddElementAction ciAction = null;

    private AddElementAction unidirAction = null;

    private AddElementAction bidirAction = null;

    private AddElementAction stateAction = null;

    private AddElementAction processAction = null;

    private AddElementAction inAction = null;

    private AddElementAction agAction = null;

    private AddElementAction reAction = null;

    private AddElementAction effectAction = null;

    private AddElementAction exceptionAction = null;

    private AddElementAction invocationAction = null;

    private AddElementAction consumptionEventLinkAction = null;

    private AddElementAction instrumentEventLinkAction = null;

    private AddElementAction conditionAction = null;

    /** ***************** Plugins Actions *************************** */
    private VisWebAction visWebActionA = null;
    
    
    /********************* Help Actions	*******************************/
    private HelpContentsAction helpContentsActionA = null;
    private HelpAboutAction helpAboutActionA = null;
    private OpenUrlAction checkForUpdatesActionA = null;
    
    /** ***************** Singletons Initating *************************** */
    private Configuration configuration = null;

    private FileControl fileControl = null;

    private GuiControl guiControl = null;

    private EditControl editControl = null;

    public Opcat2() {
    	
    	//debug in dev versions
    	Debug.setDebugLevelToCore() ; 
    	
    	
        // set locale to en_US
        Locale.setDefault(Locale.ENGLISH);

        myFrame = new JFrame("Opcat II");
        //		javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new
        // OpcatTheme());
        SwingUtilities.updateComponentTreeUI(myFrame);

        myFrame.setIconImage(logoIcon.getImage());
        Container cp = myFrame.getContentPane();
        cp.setLayout(new BorderLayout());

        //Load License
        license = loadLicense();

        //		Load configuration from the properties file
        configuration = Configuration.getInstance();

        /** ************************* Initiating Controls **************** */
        fileControl = FileControl.getInstance();
        fileControl.setOpcat(this);
        guiControl = GuiControl.getInstance();
        guiControl.setOpcat(this);
        editControl = EditControl.getInstance();
        editControl.setOpcat(this);

        /** ************************* Initiating File Actions **************** */
        loadOpxActionA = new LoadOpxAction("Open", StandardImages.OPEN);
        saveOpxActionA = new SaveOpxAction("Save", StandardImages.SAVE);
        saveAsOpxActionA = new SaveAsOpxAction("Save As", StandardImages.SAVE);
        closeActionA = new CloseAction("Close", StandardImages.CLOSE);
        exitActionA = new ExitAction("Exit", emptyIcon);
        newProjectActionA = new NewProjectAction("New...", StandardImages.NEW);
        loadExampleActionA = new LoadExampleAction();
        selectDataBaseActionA = new SelectDataBaseAction("Database",
                StandardImages.EMPTY);
        saveAsImageActionA = new SaveAsImageAction("Save As Image", emptyIcon);
        reuseImportActionA = new ReuseImportAction("Import Model",
                StandardImages.IMPORT);

        /** ************************* Initiating Edit Actions **************** */
        copyActionA = new CopyAction("Copy", StandardImages.COPY);
        cutActionA = new CutAction("Cut", StandardImages.CUT);
        deleteActionA = new DeleteAction("Delete", StandardImages.DELETE);
        pasteActionA = new PasteAction("Paste", StandardImages.PASTE);
        redoActionA = new RedoAction("Redo", StandardImages.REDO);
        undoActionA = new UndoAction("Undo", StandardImages.UNDO);
        selectAllActionA = new SelectAllAction("Select All", emptyIcon);

        mergeDraggingActionA = new MergeDraggingAction("Merge Entities",
                StandardImages.EMPTY);

        /**
         * ************************* Initiating Add Element Actions ***********/
        objectAction = new AddElementAction("Object", OPMImages.OBJECT,
                StateMachine.ADD_OBJECT, OpcatConstants.OBJECT);
        apAction = new AddElementAction("Agregation-Particulation",
                OPMImages.AGGREGATION, StateMachine.ADD_RELATION,
                OpcatConstants.AGGREGATION_RELATION);
        fcAction = new AddElementAction("Exhibition-Characterization",
                OPMImages.EXHIBITION, StateMachine.ADD_RELATION,
                OpcatConstants.EXHIBITION_RELATION);
        gsAction = new AddElementAction("Generalization-Specialization",
                OPMImages.GENERALIZATION, StateMachine.ADD_RELATION,
                OpcatConstants.SPECIALIZATION_RELATION);
        ciAction = new AddElementAction("Classification-Instantiation",
                OPMImages.INSTANTIATION, StateMachine.ADD_RELATION,
                OpcatConstants.INSTANTINATION_RELATION);
        unidirAction = new AddElementAction("Unidirectional Relation",
                OPMImages.UNI_DIRECTIONAL, StateMachine.ADD_GENERAL_RELATION,
                OpcatConstants.UNI_DIRECTIONAL_RELATION);
        bidirAction = new AddElementAction("Bidirectional Relation",
                OPMImages.BI_DIRECTIONAL, StateMachine.ADD_GENERAL_RELATION,
                OpcatConstants.BI_DIRECTIONAL_RELATION);
        stateAction = new AddElementAction("State", OPMImages.STATE,
                StateMachine.ADD_STATE, OpcatConstants.STATE);
        processAction = new AddElementAction("Process", OPMImages.PROCESS,
                StateMachine.ADD_PROCESS, OpcatConstants.PROCESS);
        inAction = new AddElementAction("Instrument Link",
                OPMImages.INSTRUMENT_LINK, StateMachine.ADD_LINK,
                OpcatConstants.INSTRUMENT_LINK);
        agAction = new AddElementAction("Agent Link", OPMImages.AGENT_LINK,
                StateMachine.ADD_LINK, OpcatConstants.AGENT_LINK);
        reAction = new AddElementAction("Result Link", OPMImages.RESULT_LINK,
                StateMachine.ADD_LINK, OpcatConstants.CONSUMPTION_LINK);
        effectAction = new AddElementAction("Effect Link",
                OPMImages.EFFECT_LINK, StateMachine.ADD_LINK,
                OpcatConstants.EFFECT_LINK);
        exceptionAction = new AddElementAction("Exception Link",
                OPMImages.EXCEPTION_LINK, StateMachine.ADD_LINK,
                OpcatConstants.EXCEPTION_LINK);
        invocationAction = new AddElementAction("Invocation Link",
                OPMImages.INVOCATION_LINK, StateMachine.ADD_LINK,
                OpcatConstants.INVOCATION_LINK);
        consumptionEventLinkAction = new AddElementAction(
                "Consumption Event Link", OPMImages.CONSUMPTION_EVENT_LINK,
                StateMachine.ADD_LINK, OpcatConstants.CONSUMPTION_EVENT_LINK);
        instrumentEventLinkAction = new AddElementAction(
                "Instrument Event Link", OPMImages.INSTRUMENT_EVENT_LINK,
                StateMachine.ADD_LINK, OpcatConstants.INSTRUMENT_EVENT_LINK);
        conditionAction = new AddElementAction("Condition Link",
                OPMImages.CONDITION_LINK, StateMachine.ADD_LINK,
                OpcatConstants.CONDITION_LINK);

        /** ************************* Initiating Plugin Actions **************** */
        visWebActionA = new VisWebAction("OWL Generation");
        
        /** ************************* Initiating Help Actions **************** */
        helpContentsActionA = new HelpContentsAction("Contents", StandardImages.HELPBOOK);
        helpAboutActionA = new HelpAboutAction("About", StandardImages.EMPTY);
        checkForUpdatesActionA = new OpenUrlAction("Check for Updates", StandardImages.EMPTY, OPCAT_SITE_URL);
        
        
        // Create and throw the splash screen up. Since this will
        // physically throw bits on the screen, we need to do this
        // on the GUI thread using invokeLater.
        _createSplashScreen();

        // do the following on the gui thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                splashScreen.setVisible(true);
            }
        });

        myFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        currentImagesPath = javaImagesPath;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        myFrame
                .setSize((int) (screenSize.getWidth() * 7 / 8), (int) (screenSize
                        .getHeight() * 7 / 8));
        myFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

        desktop = new JDesktopPane();
        //desktop.setPreferredSize(new Dimension(1200, 800));
        desktop.setBackground(Color.lightGray);

        oplPane = new JTextPane();
        oplPane.setContentType("text/html");
        oplPane.setEditable(false);
        oplPane.setPreferredSize(new Dimension(300, 450));

        oplPane.setBackground(new Color(230, 230, 230));

        jpb.setValue(++progressCounter);

        repository = new Repository(currentProject, this);
        repository.setMinimumSize(repository.getPreferredSize());

        jpb.setValue(++progressCounter);

        cp.add(_constructStandardToolBar(), BorderLayout.NORTH);

        jpb.setValue(++progressCounter);

        diagramPanel = new JPanel();
        diagramPanel.setLayout(new BorderLayout());
        diagramPanel.add(_constructComponentToolBar(), BorderLayout.SOUTH);
 
        _constructAnimationToolBar();

        jpb.setValue(++progressCounter);
        extensionToolsPane = new JTabbedPane(SwingConstants.BOTTOM);
        // add OPL Pane
        extensionToolsPane.add("OPL Generator", new JScrollPane(oplPane));

        //----------------------------------------------------------------
        // init all Extension Tools here and add them to extensionToolsPane
        //----------------------------------------------------------------

        desktopTools = _constructdesktopToolsPane(desktop, extensionToolsPane);
        diagramPanel.add(desktopTools, BorderLayout.CENTER); 

        cp.add(_constructSplitPane(/* opcatTree */
        repository, diagramPanel), BorderLayout.CENTER);
        //cp.add(ConstructSplitPane(opcatTree, desktop), BorderLayout.CENTER);
        projectWasOpened = false;

        toolbars = new JToolBar[2];
        toolbars[0] = standardToolBar;
        toolbars[1] = componentsToolBar;
        //setProjectActionEnable(false);

        myFrame.setJMenuBar(_constructMenuBar());

        jpb.setValue(++progressCounter);

        // Escape listener
        KeyListener escapeListener = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE
                        && currentProject != null
                        && currentProject.getCurrentOpd() != null) {
                    if (!StateMachine.isWaiting()) {
                        StateMachine.reset();
                        currentProject
                                .getCurrentOpd()
                                .getOpdContainer()
                                .getDrawingArea()
                                .setCursor(Cursor
                                        .getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        currentProject.removeSelection();
                    }
                }
            }
        };

        myFrame.addKeyListener(escapeListener);

        // Window closer
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fileControl._exit();
            }
        };
        myFrame.addWindowListener(l);
        //end Window closer

        dataBase = new OpcatDB();
        jpb.setValue(++progressCounter);

        _loadClasses();
        // Show the main screen and take down the splash screen. Note that
        // we again must do this on the GUI thread using invokeLater.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                myFrame.setVisible(true);
                splashScreen.setVisible(false);
                splashScreen = null;
                splashLabel = null;
            }
        });

    }

    //==========================================================================================
    // create menu bar with all its handling
    private JMenuBar _constructMenuBar() {
        mainMenuBar = new JMenuBar();
        JMenuItem mi;

        openItem = new JMenuItem(loadOpxActionA);
        openItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        closeItem = new JMenuItem(closeActionA);
        saveItem = new JMenuItem(saveOpxActionA);
        saveItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsItem = new JMenuItem(saveAsOpxActionA);
        printItem = new JMenuItem(printAction);
        printItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        printPreview = new JMenuItem(printPreviewAction);
        cutItem = new JMenuItem(cutActionA);
        cutItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copyItem = new JMenuItem(copyActionA);
        copyItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        pasteItem = new JMenuItem(pasteActionA);
        pasteItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        undoItem = new JMenuItem(undoActionA);
        undoItem.setEnabled(false);
        undoItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

        redoItem = new JMenuItem(redoActionA);
        redoItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK
                        + ActionEvent.SHIFT_MASK));
        redoItem.setEnabled(false);

        deleteItem = new JMenuItem(deleteActionA);
        deleteItem
                .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

        selectAllItem = new JMenuItem(selectAllActionA);
        selectAllItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        
        /**
         * TODO: search feature
         */
        searchItem = new JMenuItem(SearchActionA);
        searchItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        
        reuseImportItem = new JMenuItem(reuseImportActionA);

        // File menu
        JMenu fileMenu;
        fileMenu = new JMenu("System");
        newItem = new JMenuItem(newProjectActionA);
        newItem.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        fileMenu.add(newItem);

        fileMenu.add(openItem);

        fileMenu.add(closeItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);

        JMenuItem importItem = new JMenuItem(selectDataBaseActionA);
        importItem.setText("Open Legacy Model");
        fileMenu.add(importItem);

        File[] examples = (new File("examples" + fileSeparator))
                .listFiles(new CustomFileFilter("opz"));

        JMenu examplesMenu = new JMenu("Examples");
        examplesMenu.setIcon(StandardImages.EMPTY);
        if (examples != null) {
            for (int i = 0; i < examples.length; i++) {
                JMenuItem tempItem = new JMenuItem(loadExampleActionA);
                tempItem.setText(examples[i].getName().replaceAll(".opz", ""));
                examplesMenu.add(tempItem);
            }
        }
        fileMenu.add(examplesMenu);

        fileMenu.add(new JSeparator());

        fileMenu.add(projectPropertiesAction);
        fileMenu.add(projectMetaLibrariesAction);
        fileMenu.add(reuseImportItem);

        fileMenu.add(new JSeparator());
        fileMenu.add(saveAsImageActionA);
        fileMenu.add(new JSeparator());

        fileMenu.add(new JMenuItem(pageSetupAction));
        fileMenu.add(printItem);
        fileMenu.add(printPreview);
        fileMenu.add(new JSeparator());

        //		OPCATeam:Add to menu the item "Team Support"
        JMenu teamMenu = new JMenu("Team Support");
        teamMenu.setIcon(OPCATeamImages.PEOPLEsmall);

        connectItem = new JMenuItem(connectAction);
        disconnectItem = new JMenuItem(disconnectAction);
        propertiesItem = new JMenuItem(propertiesAction);

        teamMenu.add(connectItem);
        teamMenu.add(disconnectItem);
        teamMenu.add(propertiesItem);

        disconnectItem.setEnabled(false);

        fileMenu.add(teamMenu);

        fileMenu.add(new JSeparator());
        //End OPCATeam

        //Adding previous files used
        if (configuration != null) {
            try {
                ListIterator lastFiles = configuration.getLastUsedFiles();
                LastFileEntry last = null;
                int i = 0;
                while (lastFiles.hasNext() && i < 4) {
                    last = (LastFileEntry) lastFiles.next();
                    if (last != null) {
                        i++;
                        LoadPrevious loadPrevious = new LoadPrevious(i + "  "
                                + last.getFileNameWithoutPath(), emptyIcon,
                                last.getFileUrl());
                        JMenuItem loadItem = new JMenuItem(loadPrevious);
                        fileMenu.add(loadItem);
                    }
                }
            } catch (OpcatException e) {
            }
        }
        fileMenu.add(new JSeparator());
        fileMenu.add(exitActionA);

        //edit menu
        JMenu editMenu;
        editMenu = new JMenu("Edit");

        editMenu.add(undoItem);
        editMenu.add(redoItem);

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(deleteItem);
        editMenu.add(selectAllItem);
        editMenu.add(new JSeparator());
        editMenu.add(searchItem);
        

        //Merge is Remarked by Eran Toch
        //editMenu.add(new JSeparator());
        //draggingButton = new JMenuItem(mergeDraggingActionA);
        //editMenu.add(draggingButton);

        // view menu
        JMenu viewMenu;
        JMenuItem tmpItem;
        viewMenu = new JMenu("View");

        grZoomInItem = new JMenuItem(viewZoomInAction);
        grZoomOutItem = new JMenuItem(viewZoomOutAction);

        viewMenu.add(grZoomInItem);
        viewMenu.add(grZoomOutItem);

        JMenu toolbarsMenu = new JMenu("Toolbars");
        toolbarsMenu.setIcon(StandardImages.EMPTY);

        for (int i = 0; i < toolbars.length; i++) {
            tmpItem = new JCheckBoxMenuItem(toolbars[i].getName(), true);
            tmpItem.addActionListener(tBarslst);
            toolbarsMenu.add(tmpItem);
        }

        viewMenu.add(toolbarsMenu);

        JMenu mLF = new JMenu("Look&Feel");
        m_lfs = new Hashtable();
        UIManager.LookAndFeelInfo lfs[] = UIManager.getInstalledLookAndFeels();
        ButtonGroup uiButtonGroup = new ButtonGroup();
        for (int k = 0; k < lfs.length; k++) {
            String name = lfs[k].getName();
            JMenuItem lf = new JRadioButtonMenuItem(name);
            if (name.equals("Metal")) {
                lf.setSelected(true);
            }
            m_lfs.put(name, lfs[k].getClassName());
            lf.addActionListener(lst);
            uiButtonGroup.add(lf);
            mLF.add(lf);
        }

        mLF.setIcon(StandardImages.EMPTY);
        viewMenu.add(mLF);

        refreshItem = new JMenuItem(refreshAction);
        refreshItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        viewMenu.add(refreshItem);

        // OPM Notation
        JMenu opmMenu;
        opmMenu = new JMenu("Notation");

        JMenu thingMenu = new JMenu("Thing");
        thingMenu.add(objectAction);
        thingMenu.add(stateAction);
        thingMenu.add(processAction);

        opmMenu.add(thingMenu);

        JMenu linkMenu = new JMenu("Link");
        linkMenu.add(inAction);
        linkMenu.add(agAction);
        linkMenu.add(reAction);
        linkMenu.add(effectAction);
        linkMenu.add(instrumentEventLinkAction);
        linkMenu.add(consumptionEventLinkAction);
        linkMenu.add(conditionAction);
        linkMenu.add(exceptionAction);
        linkMenu.add(invocationAction);

        opmMenu.add(linkMenu);

        JMenu relationMenu = new JMenu("Relation ");
        relationMenu.add(apAction);
        relationMenu.add(fcAction);
        relationMenu.add(gsAction);
        relationMenu.add(ciAction);
        relationMenu.add(unidirAction);
        relationMenu.add(bidirAction);
        opmMenu.add(relationMenu);

        opmMenu.add(new JSeparator());

        JMenu graphicObject = new JMenu("Graphic Object");

        tmpItem = new JMenuItem("Rectangle");
        tmpItem.setEnabled(false);
        graphicObject.add(tmpItem);

        tmpItem = new JMenuItem("Oval");
        tmpItem.setEnabled(false);
        graphicObject.add(tmpItem);

        tmpItem = new JMenuItem("Bubble");
        tmpItem.setEnabled(false);
        graphicObject.add(tmpItem);
        opmMenu.add(graphicObject);

        // scaling menu
        JMenu operationsMenu = new JMenu("Operation");

        operationsMenu.add(unfoldingAction);
        operationsMenu.add(zoomInAction);
        operationsMenu.add(new JSeparator());
        operationsMenu.add(go2Animation);

        //Ontology validation menu item
        operationsMenu.add(new JSeparator());
        operationsMenu.add(new JMenuItem(validationAction));

        // generation menu
        JMenu generationMenu;
        generationMenu = new JMenu("Generation");

        JMenu oplMenu = new JMenu("OPL");

        oplMenu.add(saveOPLAction);
        oplMenu.add(oplColorsAction);
        oplMenu.add(new JSeparator());
        ButtonGroup oplGroup = new ButtonGroup();
        isWholeProjectCompiled = false;
        JMenuItem oplTemp = new JRadioButtonMenuItem("OPL for current OPD",
                true);
        oplTemp.addActionListener(OPLCompileAction);
        oplGroup.add(oplTemp);
        oplMenu.add(oplTemp);
        oplTemp = new JRadioButtonMenuItem("OPL for whole System", false);
        oplTemp.addActionListener(OPLCompileAction);
        oplGroup.add(oplTemp);
        oplMenu.add(oplTemp);

        generationMenu.add(oplMenu);
        generationMenu.add(new JMenuItem(opl2opd));
        generationMenu.add(new JSeparator());

        JMenu docMenu = new JMenu("Documents");
        docMenu.add(new JMenuItem(docAction));
        docMenu.add(new JMenuItem(templateAction));
        generationMenu.add(docMenu);

        generationMenu.add(new JMenuItem(umlAction));

        generationMenu.add(new JSeparator());
        generationMenu.add(new JMenuItem(generateOplXml));
        generationMenu.add(new JMenuItem(generateCode));

        //vis web
        generationMenu.add(new JMenuItem(visWebActionA));
        //		************************** OWL-S Start
        // **********************************
        /*
         * generationMenu.add(new JSeparator()); JMenu semWebMenu = new
         * JMenu("SemanticWeb"); semWebMenu.add(new JMenuItem(semweb2opm));
         * semWebMenu.add(new JMenuItem(opm2semweb));
         * generationMenu.add(semWebMenu);
         */
        //		************************** OWL-S End
        // **********************************
        /****************************help menu*****************************/
        JMenu helpMenu;
        helpMenu = new JMenu("Help");
        helpMenu.add(helpContentsActionA);
        helpMenu.add(checkForUpdatesActionA);

        helpMenu.add(new JSeparator());

        //		helpMenu.add(_constructExtensionToolsAboutMenu());

        helpMenu.add(helpAboutActionA);

        // add menus to menubar
        mainMenuBar.add(fileMenu);
        mainMenuBar.add(editMenu);
        mainMenuBar.add(viewMenu);
        mainMenuBar.add(opmMenu);
        mainMenuBar.add(operationsMenu);
        mainMenuBar.add(generationMenu);
        //LERA EXTENSION TOOL
    	//debug = Debug.getInstance() ;
        //if (debug.IsDebug()) {
        //	_loadExtensionTools();
        //	mainMenuBar.add(_constractExtensionToolsMenu());
        //}

        mainMenuBar.add(helpMenu);
        return mainMenuBar;
    }

    //==========================================================================================
    // create general toolbar
    private JToolBar _constructStandardToolBar() {
        standardToolBar = new JToolBar();
        standardToolBar.setName("Standard");
        standardToolBar.setFloatable(false);

        newButton = new JToolBarButton(newProjectActionA, "Create New Model");
        openButton = new JToolBarButton(loadOpxActionA, "Open Model");
        closeButton = new JToolBarButton(closeActionA, "Close Model");
        saveButton = new JToolBarButton(saveOpxActionA, "Save Model");
        printButton = new JToolBarButton(printQuickAction, "Print current OPD");
        searchButton = new JToolBarButton(SearchActionA, "Search");
        cutButton = new JToolBarButton(cutActionA, "Cut Object to Clipboard");
        copyButton = new JToolBarButton(copyActionA, "Copy Object to Clipboard");
        pasteButton = new JToolBarButton(pasteActionA,
                "Paste Object from Clipboard");
        undoButton = new JToolBarButton(undoActionA, "Undo Last Action");
        zoomInButton = new JToolBarButton(viewZoomInAction, "Zoom In");
        zoomOutButton = new JToolBarButton(viewZoomOutAction, "Zoom Out");

        importButton = new JToolBarButton(reuseImportActionA, "Import Model");
        metaLibsButton = new JToolBarButton(projectMetaLibrariesAction,
                "Open Meta-Libraries");

        validateButton = new JToolBarButton(validationAction, "Validate Model");

        undoButton.setEnabled(false);
        redoButton = new JToolBarButton(redoActionA, "Redo Last Action");
        redoButton.setEnabled(false);
        deleteButton = new JToolBarButton(deleteActionA, "Delete");
        showHideExtensionTools = new JToolBarToggleButton(
                showExtensionToolsAction, "Hide Extension Tools",
                "Show Extension Tools");
        showHideExtensionTools.setSelected(true);

        moveOnDragButton = new JToolBarToggleButton(
                drawingAreaMouseDragActionMoves,
                "Move OPD Contents by Mouse Dragging",
                "Drag Drawing Area by Mouse dragging");
        selectOnDragButton = new JToolBarToggleButton(
                drawingAreaMouseDragActionSelects, "Select by Mouse Dragging",
                "Select by Mouse Dragging");
        selectOnDragButton.setSelected(true);

        /** ***********HIOTeam************* */
        drawOnDragButton = new JToolBarToggleButton(
                drawingAreaMouseDragActionDraw, "Draw by Mouse Dragging",
                "Draw by Mouse Dragging");

        moveSelectGroup.add(moveOnDragButton);
        moveSelectGroup.add(selectOnDragButton);
        moveSelectGroup.add(drawOnDragButton);
        /** *********HIOTeam***************************** */

        standardToolBar.add(newButton);
        standardToolBar.add(openButton);
        standardToolBar.add(closeButton);
        standardToolBar.add(saveButton);
        
        standardToolBar.add(new JToolBar.Separator());
        standardToolBar.add(searchButton);

        standardToolBar.add(new JToolBar.Separator());
        standardToolBar.add(printButton);

        standardToolBar.add(new JToolBar.Separator());
        standardToolBar.add(importButton);
        standardToolBar.add(metaLibsButton);

        standardToolBar.add(new JToolBar.Separator());
        standardToolBar.add(cutButton);
        standardToolBar.add(copyButton);
        standardToolBar.add(pasteButton);
        standardToolBar.add(undoButton);
        standardToolBar.add(redoButton);
        standardToolBar.add(deleteButton);

        standardToolBar.add(new JToolBar.Separator());
        standardToolBar.add(showHideExtensionTools);
        standardToolBar.add(zoomInButton);
        standardToolBar.add(zoomOutButton);
        standardToolBar.add(new JToolBar.Separator());

        standardToolBar.add(selectOnDragButton);
        standardToolBar.add(moveOnDragButton);
        standardToolBar.add(drawOnDragButton);
        /** ***************HIOTeam************************** */

        standardToolBar.add(new JToolBar.Separator());
        standardToolBar.add(new JToolBarButton(go2Animation, "Animate System"));

        _setBorders4ToolBar(standardToolBar);

        return standardToolBar;

    }

    //create structual relation toolbar
    private JToolBar _constructComponentToolBar() {
        componentsToolBar = new JToolBar();

        componentsToolBar.setName("Components");
        componentsToolBar.setFloatable(false);

        componentsToolBar.add(new JToolBarButton(objectAction, "Object"));
        componentsToolBar.add(new JToolBarButton(stateAction, "State"));
        componentsToolBar.add(new JToolBarButton(processAction, "Process"));
        componentsToolBar.add(new JToolBar.Separator());
        componentsToolBar.add(new JToolBar.Separator());
        componentsToolBar.add(new JToolBarButton(apAction,
                "Aggregation-Particulation"));
        componentsToolBar.add(new JToolBarButton(fcAction,
                "Exhibition-Characterization"));
        componentsToolBar.add(new JToolBarButton(gsAction,
                "Generalization-Specialization"));
        componentsToolBar.add(new JToolBarButton(ciAction,
                "Classification-Instantiation"));
        componentsToolBar.add(new JToolBarButton(unidirAction,
                "Unidirectional Relation"));
        componentsToolBar.add(new JToolBarButton(bidirAction,
                "Bidirectional Relation"));
        componentsToolBar.add(new JToolBar.Separator());
        componentsToolBar.add(new JToolBar.Separator());
        componentsToolBar.add(new JToolBarButton(agAction, "Agent Link"));
        componentsToolBar.add(new JToolBarButton(inAction, "Instrument Link"));
        componentsToolBar.add(new JToolBarButton(reAction,
                "Result/Consumption Link"));
        componentsToolBar.add(new JToolBarButton(effectAction, "Effect Link"));
        componentsToolBar.add(new JToolBarButton(instrumentEventLinkAction,
                "Instrument Event Link"));
        componentsToolBar.add(new JToolBarButton(consumptionEventLinkAction,
                "Consumption Event Link"));
        componentsToolBar.add(new JToolBarButton(conditionAction,
                "Condition Link"));
        componentsToolBar.add(new JToolBarButton(exceptionAction,
                "Exception Link"));
        componentsToolBar.add(new JToolBarButton(invocationAction,
                "Invocation Link"));
        _setBorders4ToolBar(componentsToolBar);

        return componentsToolBar;
    }

    private void _setBorders4ToolBar(JToolBar bar) {
        for (int i = 0; i < bar.getComponentCount(); i++) {
            if (bar.getComponentAtIndex(i) instanceof JToolBarButton) {
                ((JToolBarButton) bar.getComponentAtIndex(i))
                        .setInitialBorder();
            } else if (bar.getComponentAtIndex(i) instanceof JToolBarToggleButton) {
                JToolBarToggleButton tButton = (JToolBarToggleButton) bar
                        .getComponentAtIndex(i);
                tButton.setSelected(tButton.isSelected());
            }

        }

    }

    private JToolBar _constructAnimationToolBar() {
        animationToolBar = new JToolBar();
        anPauseButton = new JToolBarToggleButton(anPause, "Continue", "Pause");

        JLabel l = new JLabel("Backward");
        Font f = l.getFont();
        int maxPanelsSize = 14 + (int) Math.max(f
                .getStringBounds("Forward", new FontRenderContext(null, true,
                        false)).getWidth(), f
                .getStringBounds("Backward", new FontRenderContext(null, true,
                        false)).getWidth());

        JPanel anForwardPanel = new JPanel(new GridLayout(2, 1));
        anForwardPanel
                .setBorder(BorderFactory.createCompoundBorder(BorderFactory
                        .createEtchedBorder(), BorderFactory
                        .createEmptyBorder(2, 5, 2, 5)));
        anForwardField = new JTextField("1", 3);
        anForwardField.setMaximumSize(anForwardField.getPreferredSize());
        anForwardField.setBorder(null);
        anForwardPanel.add(new JLabel("Forward"));
        anForwardPanel.add(anForwardField);
        anForwardPanel.setMaximumSize(new Dimension(maxPanelsSize, 1000));

        JPanel anBackwardPanel = new JPanel(new GridLayout(2, 1));
        anBackwardPanel
                .setBorder(BorderFactory.createCompoundBorder(BorderFactory
                        .createEtchedBorder(), BorderFactory
                        .createEmptyBorder(2, 5, 2, 5)));
        anBackwardField = new JTextField("1", 3);
        anBackwardField.setMaximumSize(anBackwardField.getPreferredSize());
        anBackwardField.setBorder(null);
        anBackwardPanel.add(new JLabel("Backward"));
        anBackwardPanel.add(anBackwardField);
        anBackwardPanel.setMaximumSize(new Dimension(maxPanelsSize, 1000));

        animationToolBar.add(new JToolBarButton(anPlay, "Play/Continue"));
        animationToolBar.add(new JToolBarButton(anBackward, "Backward"));
        animationToolBar.add(anBackwardPanel);
        animationToolBar.add(new JToolBarButton(anForward, "Forward"));
        animationToolBar.add(anForwardPanel);
        animationToolBar.add(anPauseButton);
        animationToolBar.add(new JToolBarButton(anStop, "Stop"));
        animationToolBar.add(new JToolBar.Separator());
        animationToolBar.add(new JToolBarButton(anActivate, "Activate"));
        animationToolBar.add(new JToolBarButton(anDeactivate, "Deactivate"));
        animationToolBar.add(new JToolBarButton(anSettings,
                "Animation Settings"));
        animationToolBar.add(new JToolBar.Separator());

        anZoomInButton = new JToolBarButton(viewZoomInAction, "Zoom In");
        anZoomInButton.setIcon(AnimationImages.ZOOM_IN);
        anZoomOutButton = new JToolBarButton(viewZoomOutAction, "Zoom Out");
        anZoomOutButton.setIcon(AnimationImages.ZOOM_OUT);
        animationToolBar.add(anZoomInButton);
        animationToolBar.add(anZoomOutButton);

        animationToolBar.add(new JToolBar.Separator());
        animationToolBar.add(new JToolBarButton(closeAnimation,
                "Exit Animation Mode"));

        _setBorders4ToolBar(animationToolBar);
        return animationToolBar;
    }

    //==========================================================================================
    // create Split pane
    private JSplitPane _constructSplitPane(Component left, Component right) {
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);

        //		sp.setDividerLocation((int)(myFrame.getHeight()*0.29));
        sp.setResizeWeight(0.0);

        sp.setOneTouchExpandable(true);
        return sp;
    }

    private JSplitPane _constructdesktopToolsPane(Component top, Component bottom) {
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top, bottom);

        sp.setDividerLocation((int) (myFrame.getHeight() * 0.65));
        sp.setResizeWeight(1.0);
        sp.setOneTouchExpandable(true);
        return sp;
    }

    //==========================================================================================
    // unirversal actions for all controls
    //==========================================================================================

    /**
     * **** actions added by the reuse team
     * *************************************************************
     */

    // end of all the reuse actions
    //endReuseComment
    //OPCATeam: Add the action to the menu items under "team support" : Connect
    // action
    Action connectAction = new AbstractAction("Connect", StandardImages.EMPTY) {
        public void actionPerformed(ActionEvent e) {

            //second check if a local project is open - if yes ->
            //dont let him do this action again..
            if (projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "You should close the current Model, then try to connect again.", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //It is OK to present the connect dialog box- > do it.
            teamMember = new TeamMember();
            csMessageHandler = new CollaborativeSessionMessageHandler(myOpcat2);
            chatMessageHandler = new ChatMessageHandler(myOpcat2);
            org.objectprocess.team.Connect connect = new org.objectprocess.team.Connect(
                    teamMember);
            if (teamMember.isConnected()) {
                connectItem.setEnabled(false);
                disconnectItem.setEnabled(true);
                getRepository().addOPCATeamTab();
                getRepository().initiateOPCATeamTab();
            } else {
                teamMember = null;
                csMessageHandler = null;
                chatMessageHandler = null;
            }
        }
    };

    // OPCATeam: Add the action to the menu items under "Team Support" :
    // Disconnect action
    Action disconnectAction = new AbstractAction("Disconnect",
            StandardImages.EMPTY) {
        public void actionPerformed(ActionEvent e) {

            int retValue;
            retValue = JOptionPane
                    .showConfirmDialog(Opcat2.getFrame(), "Are you sure you want to disconnect from server?", "Disconnect", JOptionPane.YES_NO_OPTION);

            switch (retValue) {
            case JOptionPane.YES_OPTION: {
                try {
                    // if a session was opened -> close the file and logout from
                    // the session
                    if (getActiveCollaborativeSession() != null) {
                        getActiveCollaborativeSession().logoutFromSession();
                        setActiveCollaborativeSession(null);
                        closeFileForOPCATeam();
                        teamMember.logoutUser();
                    }
                } catch (Exception eLogout) {
                }

                repository.removeOPCATeamTab();
                removeChatRoom();
                connectItem.setEnabled(true);
                disconnectItem.setEnabled(false);
                if (csMessageHandler != null)
                    csMessageHandler.closeConnection();
                if (chatMessageHandler != null) {
                    chatMessageHandler.closeConnection();
                    removeChatRoom();
                }
                teamMember = null;
                csMessageHandler = null;
                chatMessageHandler = null;
                break;
            }
            case JOptionPane.NO_OPTION: {
                return;
            }
            }
        }
    };

    //OPCATeam: Add the action to the menu items under "Team Support" :
    // Properties action
    Action propertiesAction = new AbstractAction("Properties",
            StandardImages.PROPERTIES) {
        public void actionPerformed(ActionEvent e) {
            Thread runner = new Thread() {
                public void run() {
                    PropertiesDialog propertiesDialog = new org.objectprocess.team.PropertiesDialog(
                            myFrame);
                }
            };
            runner.start();
        }
    };

    //End OPCATeam

    /**
     * Validates the current model, according to the attached ontology
     * libraries.
     * 
     * @author Eran Toch
     */
    Action validationAction = new AbstractAction("Validation",
            StandardImages.EMPTY) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            validate();

        }
    };

    Action generateOplXml = new AbstractAction("OPL-XML script") {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            myFileChooser.setSelectedFile(new File(""));
            myFileChooser.resetChoosableFileFilters();
            String[] exts = { "xml" };
            CustomFileFilter myFilter = new CustomFileFilter(exts, "XML Files");
            myFileChooser.addChoosableFileFilter(myFilter);

            int retVal = myFileChooser
                    .showDialog(Opcat2.getFrame(), "Extract OPL-XML");

            if (retVal != JFileChooser.APPROVE_OPTION) {
                return;
            }

            Thread runner = new Thread() {
                public void run() {
                    try {

                        JProgressBar pBar = new JProgressBar();
                        pBar.setIndeterminate(true);
                        showWaitMessage("Extracting OPL-XML script...", pBar);
                        StateMachine.setWaiting(true);
                        setCursor4All(Cursor.WAIT_CURSOR);

                        String fileName = myFileChooser.getSelectedFile()
                                .getPath();
                        if (!fileName.endsWith(".xml")) {
                            fileName = fileName + ".xml";
                        }

                        File f = new File(fileName);
                        FileOutputStream os = new FileOutputStream(f);
                        StringBuffer oplXmlString = currentProject
                                .getOplXmlScript();
                        os.write(currentProject.getOplXmlScript().toString()
                                .getBytes());
                        os.close();

                    } catch (IOException ioe) {
                    } finally {
                        hideWaitMessage();
                        StateMachine.reset();
                        StateMachine.setWaiting(false);
                        setCursor4All(Cursor.DEFAULT_CURSOR);

                    }
                }
            };

            runner.start();

        }

    };

    Action printQuickAction = new AbstractAction("Print", StandardImages.PRINT) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Thread runner = new Thread() {
                public void run() {
                    currentProject.print(false, false);
                }
            };
            runner.start();
        }
    };

    Action printAction = new AbstractAction("Print", StandardImages.PRINT) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Thread runner = new Thread() {
                public void run() {
                    currentProject.print(true, false);
                }
            };
            runner.start();
        }
    };

    Action pageSetupAction = new AbstractAction("Page Setup",
            StandardImages.PAGE_SETUP) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Thread runner = new Thread() {
                public void run() {
                    currentProject.pageSetup();
                }
            };
            runner.start();
        }
    };

    Action printPreviewAction = new AbstractAction("Print Preview",
            StandardImages.PRINT_PREVIEW) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Thread runner = new Thread() {
                public void run() {
                    currentProject.print(true, true);
                    Opcat2.myFrame.repaint();
                }
            };
            runner.start();
        }
    };

    Action projectPropertiesAction = new AbstractAction("Properties",
            StandardImages.PROPERTIES) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(
                    currentProject, myFrame, "System Properties");
            boolean okPressed = ppd.showDialog();

            // Handling Meta-librares (ontologies) import
            // by Eran Toch
            if (okPressed) {
                refreshMetaLibraries(ppd.getMetaLibraries());
            }
        }
    };

    Action projectMetaLibrariesAction = new AbstractAction("Meta-Libraries",
            StandardImages.METALIBS) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(
                    currentProject, myFrame, "System Properties");
            boolean okPressed = ppd.showDialogAtLibraries();

            // Handling Meta-librares (ontologies) import
            // by Eran Toch
            if (okPressed) {
                refreshMetaLibraries(ppd.getMetaLibraries());
            }
        }
    };

    Action viewZoomInAction = new AbstractAction("Zoom in",
            StandardImages.ZOOM_IN) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            GenericTable config = currentProject.getConfiguration();
            int previousSize = ((Integer) config.getProperty("CurrentSize"))
                    .intValue();
            int normalSize = ((Integer) config.getProperty("NormalSize"))
                    .intValue();

            if (previousSize < normalSize + 2) {
                int currentSize = previousSize + 1;
                double factor = (double) currentSize / (double) previousSize;
                config.setProperty("CurrentSize", new Integer(currentSize));
                currentProject.viewZoomIn(factor);
                _enableZoomOut(true);

                if (currentSize == normalSize + 2) {
                    _enableZoomIn(false);
                }
            }
        }
    };

    Action viewZoomOutAction = new AbstractAction("Zoom out",
            StandardImages.ZOOM_OUT) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            GenericTable config = currentProject.getConfiguration();
            int previousSize = ((Integer) config.getProperty("CurrentSize"))
                    .intValue();
            int normalSize = ((Integer) config.getProperty("NormalSize"))
                    .intValue();

            if (previousSize > normalSize - 2) {
                int currentSize = previousSize - 1;
                double factor = (double) currentSize / (double) previousSize;
                config.setProperty("CurrentSize", new Integer(currentSize));
                currentProject.viewZoomIn(factor);

                _enableZoomIn(true);
                if (currentSize == normalSize - 2) {
                    _enableZoomOut(false);
                }

            }

        }
    };

    Action unfoldingAction = new AbstractAction("Unfolding",
            StandardImages.EMPTY) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {

                currentProject.unfolding();
            } catch (Exception e1) {
                System.out.println(e1);
            }
        }
    };

    Action zoomInAction = new AbstractAction("In-Zooming", StandardImages.EMPTY) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {

                currentProject.zoomIn();
            } catch (Exception e1) {
                System.out.println(e1);
            }
        }
    };

    /** *start*****HIOTeam*** */
    Action drawAction = new AbstractAction("drawing", StandardImages.PEN) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened || currentProject == null) {

                moveOnDragButton.setSelected(false);
                drawOnDragButton.setSelected(false);
                selectOnDragButton.setSelected(true);

                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);

                return;
            }
            drawingAreaOnMouseDragAction = "draw";
            moveOnDragButton.setSelected(false);
            selectOnDragButton.setSelected(false);
            drawOnDragButton.setSelected(true);
            StateMachine.reset();
            if (currentProject.getCurrentOpd() == null)
                return;
            Graphics g = currentProject.getCurrentOpd().getDrawingArea()
                    .getGraphics();
            Graphics2D g2 = (Graphics2D) g;
            if (drawing == null) {
                try {
                    drawing = new DrawAppNew(g2, currentProject);
                } catch (Exception e1) {
                    //System.out.println("can't create drawing");
                }
            }

        }
    };

    /** **start******HIOTeam******** */
    Action drawingAreaMouseDragActionDraw = new AbstractAction("draw",
            StandardImages.PEN) {
        public void actionPerformed(ActionEvent e) {

            if (!projectWasOpened || currentProject == null) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                moveOnDragButton.setSelected(false);
                selectOnDragButton.setSelected(true);
                drawOnDragButton.setSelected(false);

                return;
            }
            drawingAreaOnMouseDragAction = "draw";
            moveOnDragButton.setSelected(false);
            selectOnDragButton.setSelected(false);
            drawOnDragButton.setSelected(true);
            StateMachine.reset();
            if (currentProject.getCurrentOpd() == null)
                return;
            Graphics g = currentProject.getCurrentOpd().getDrawingArea()
                    .getGraphics();
            Graphics2D g2 = (Graphics2D) g;
            if (drawing == null) {
                try {
                    drawing = new DrawAppNew(g2, currentProject);
                } catch (Exception e1) {
                    JOptionPane
                            .showMessageDialog(myFrame, "The Drawing Application could not be initialized", "Message", JOptionPane.ERROR_MESSAGE);
                }
            }

            return;

        }
    };

    /** **end*****HIOTeam******** */
    Action SearchActionA = new AbstractAction("Search", StandardImages.SEARCH) {
        public void actionPerformed(ActionEvent e) {
        	
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }        	
            SearchExtensionTool searchTool = new SearchExtensionTool () ; 
            searchTool.execute((IXSystem) currentProject) ; 
        }
    };
    
    
    Action showExtensionToolsAction = new AbstractAction("Show OPL",
            StandardImages.HIDE_TOOLS) {
        public void actionPerformed(ActionEvent e) {
            if (!showHideExtensionTools.isSelected()) {
                desktopTools.setBottomComponent(null);
                desktopTools.setDividerSize(0);
            } else {
                desktopTools.setBottomComponent(extensionToolsPane);
                desktopTools.setDividerSize(10);
                desktopTools
                        .setDividerLocation((int) (myFrame.getHeight() * 0.63));
                Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
            }

        }
    };

    Action go2Animation = new AbstractAction("Animate System",
            StandardImages.ANIMATE) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            StateMachine.setAnimated(true);
	            if (showHideExtensionTools.isSelected()) {
	                //desktopTools.setBottomComponent(null);
	                //desktopTools.setDividerSize(0);
	            }

            for (int i = 0; i < mainMenuBar.getMenuCount(); i++) {
                mainMenuBar.getMenu(i).setVisible(false);
            }
            //viewZoomInAction.set
            anPauseButton.setSelected(false);
            mainMenuBar.setEnabled(false);
            myFrame.getContentPane().remove(standardToolBar);
            myFrame.getContentPane().add(animationToolBar, BorderLayout.NORTH);
            anBackwardField.setText("1");
            anForwardField.setText("1");
            anStatusLabel = new AnimationStatusBar();
            anStatusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
            anStatusLabel.setFont(new Font("mine", Font.PLAIN, 16));
            diagramPanel.remove(componentsToolBar);
            diagramPanel.add(anStatusLabel, BorderLayout.SOUTH);
            anSystem = new AnimationSystem(currentProject,
                    (AnimationStatusBar) anStatusLabel);
            myFrame.validate();
            myFrame.repaint();
        }
    };

    public Action closeAnimation = new AbstractAction("Close",
            AnimationImages.CLOSE) {
        public void actionPerformed(ActionEvent e) {
            anSystem.animationStop();
            
            //Remove the gridpanel
            
            anSystem.getAnimationPanel().RemoveFromExtensionToolsPanel(anSystem.getAnimationPanel().getPanelName()) ; 
            StateMachine.setAnimated(false);
            //			componentsToolBar.setVisible(true);

            for (int i = 0; i < mainMenuBar.getMenuCount(); i++) {
                mainMenuBar.getMenu(i).setVisible(true);
            }

            mainMenuBar.setEnabled(true);

            myFrame.getContentPane().remove(animationToolBar);
            myFrame.getContentPane().add(standardToolBar, BorderLayout.NORTH);
            //            myFrame.getContentPane().remove(anStatusLabel);
            diagramPanel.remove(anStatusLabel);
            diagramPanel.add(componentsToolBar, BorderLayout.SOUTH);

	            if (showHideExtensionTools.isSelected()) {
	                //desktopTools.setBottomComponent(extensionToolsPane);
	                //desktopTools.setDividerSize(10);
	                //desktopTools
	                //        .setDividerLocation((int) (myFrame.getHeight() * 0.63));
	                Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
	            }
            myFrame.validate();
            myFrame.repaint();
        }
    };

    Action anPlay = new AbstractAction("Play", AnimationImages.PLAY) {
        public void actionPerformed(ActionEvent e) {
            if (!anSystem.isAnimationPaused()) {
                anSystem.animationStart();
            } else {
                anSystem.animationContinue();
                anPauseButton.setSelected(false);
            }

        }
    };

    Action anStop = new AbstractAction("Stop animation", AnimationImages.STOP) {
        public void actionPerformed(ActionEvent e) {
            anSystem.animationStop();
            anPauseButton.setSelected(false);
        }
    };

    Action anForward = new AbstractAction("Forward", AnimationImages.FORWARD) {
        public void actionPerformed(ActionEvent e) {
            try {
                anSystem.animationForward(Integer.decode(anForwardField
                        .getText()).intValue());
            } catch (NumberFormatException nfe) {
                JOptionPane
                        .showMessageDialog(myFrame, "Number of forward steps must be integer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    Action anBackward = new AbstractAction("Backward", AnimationImages.BACKWARD) {
        public void actionPerformed(ActionEvent e) {
            try {
                anSystem.animationBackward(Integer.decode(anBackwardField
                        .getText()).intValue());
            } catch (NumberFormatException nfe) {
                JOptionPane
                        .showMessageDialog(myFrame, "Number of backward steps must be integer", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    };

    Action anPause = new AbstractAction("Pause", AnimationImages.PAUSE) {
        public void actionPerformed(ActionEvent e) {
            if (!anSystem.isAnimationPaused()) {
                anSystem.animationPause();
            } else {
                anSystem.animationContinue();
            }

        }
    };

    Action anActivate = new AbstractAction("Activate", AnimationImages.ACTIVATE) {
        public void actionPerformed(ActionEvent e) {
            anSystem.animationActivate();
        }
    };

    Action anDeactivate = new AbstractAction("Deactivate",
            AnimationImages.DEACTIVATE) {
        public void actionPerformed(ActionEvent e) {
            anSystem.animationDeactivate();
        }
    };

    Action anSettings = new AbstractAction("Animation Settings",
            AnimationImages.SETTINGS) {
        public void actionPerformed(ActionEvent e) {
            (new AnimationSettingsWindow()).setVisible(true);
        }
    };

    Action oplColorsAction = new AbstractAction("OPL Style") {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    OplColorsDialog ocd = new OplColorsDialog(Opcat2.myFrame,
                            "OPL Style", currentProject.getOplColorScheme());
                    ocd.setVisible(true);
                }
            });

        }
    };

    Action opl2opd = new AbstractAction("OPD") {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Opl2Opd tDialog = new Opl2Opd(currentProject);
            tDialog.getDialog().setVisible(true);

            if (tDialog.isChanged()) {
                Opcat2.getUndoManager().discardAllEdits();
                Opcat2.setRedoEnabled(false);
                Opcat2.setUndoEnabled(false);
            }

        }
    };

    Action umlAction = new AbstractAction("UML") {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //            UMLmain tmp = new UMLmain(currentProject);
            //            tmp.getDialog().show();
            (new UMLChooser(currentProject)).setVisible(true);
        }
    };

    Action docAction = new AbstractAction("Handle Document") {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //            DocumentMain tmp = new DocumentMain(currentProject);
            //            tmp.getDocumentDialog().show();
            (new DocSel(currentProject)).setVisible(true);

        }
    };

    Action templateAction = new AbstractAction("Handle Template") {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //            DocumentMain tmp = new DocumentMain(currentProject);
            //            tmp.getTemplateDialog().show();
            (new HandleTemp(currentProject)).setVisible(true);
        }
    };

    Action drawingAreaMouseDragActionSelects = new AbstractAction(
            "Select by Dragging", StandardImages.POINTER) {
        public void actionPerformed(ActionEvent e) {
            drawingAreaOnMouseDragAction = "select";
            moveOnDragButton.setSelected(false);
            drawOnDragButton.setSelected(false);
            selectOnDragButton.setSelected(true);
            StateMachine.reset();
        }
    };

    Action drawingAreaMouseDragActionMoves = new AbstractAction(
            "Move by Dragging", StandardImages.HAND) {
        public void actionPerformed(ActionEvent e) {
            drawingAreaOnMouseDragAction = "move";
            selectOnDragButton.setSelected(false);
            drawOnDragButton.setSelected(false);
            moveOnDragButton.setSelected(true);
            StateMachine.reset();
            //moveSelectGroup.setSelected(moveOnDragButton.getModel(), true);
        }
    };

    Action saveOPLAction = new AbstractAction("Save OPL As...") {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            myFileChooser.setSelectedFile(new File(""));
            myFileChooser.resetChoosableFileFilters();
            myFileChooser.setFileFilter(new CustomFileFilter("html",
                    "HTML Files"));
            int returnVal = myFileChooser.showSaveDialog(myFrame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {

                    String s;

                    if (isWholeProjectCompiled) {
                        s = currentProject.getOPL(true);
                    } else {
                        Opd currentOpd = currentProject.getCurrentOpd();
                        if (currentOpd == null) {
                            s = EMPTY_HTML;
                        } else {
                            s = currentProject.getOPL(true, currentOpd
                                    .getOpdId());
                        }
                    }

                    FileOutputStream file = new FileOutputStream(myFileChooser
                            .getSelectedFile().getAbsolutePath());
                    //				 file.write(oplPane.getText().getBytes());
                    file.write(s.getBytes());
                    file.flush();
                    file.close();
                } catch (java.io.IOException IOE) {
                    System.out.println("IOException " + IOE);
                }
            }
        }
    };

    ActionListener OPLCompileAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            if (command.equals("OPL for current OPD")) {
                isWholeProjectCompiled = false;
            } else {
                isWholeProjectCompiled = true;
            }
            Opcat2.oplPane.setCaretPosition(0);

            Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);

        }
    };

    Action refreshAction = new AbstractAction("Refresh", StandardImages.EMPTY) {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentProject.refresh();
            Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
            Opcat2.myFrame.repaint();
        }
    };

    Action generateCode = new AbstractAction("Java Code") {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            myFileChooser.setSelectedFile(new File(""));
            myFileChooser.resetChoosableFileFilters();
            myFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int retVal = myFileChooser
                    .showDialog(Opcat2.getFrame(), "Generate Java Code");
            myFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (retVal != JFileChooser.APPROVE_OPTION) {
                return;
            }
            Thread runner = new Thread() {
                public void run() {
                    StateMachine.setWaiting(true);
                    setCursor4All(Cursor.WAIT_CURSOR);

                    JProgressBar pBar = new JProgressBar();
                    pBar.setIndeterminate(true);
                    showWaitMessage("Generating Code...", pBar);

                    File selectedDirectory = myFileChooser.getSelectedFile();
                    /* START Coping opmTypes directory */
                    /* LERA */
                    try {
                        File opmDir = new File(selectedDirectory, "opmTypes");
                        if (!opmDir.exists()) {
                            opmDir.mkdir();
                            File opmType_ = new File(opmTypes);
                            File[] files = opmType_.listFiles();
                            for (int i = 0; i < files.length; i++) {
                                FileCopy
                                        .copy(files[i].getAbsolutePath(), opmDir
                                                .getAbsolutePath());
                            }
                        } else {
                            JOptionPane
                                    .showMessageDialog(myFrame, "Code Generator Message: Using existing opmTypes directory", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException e) {
                        JOptionPane
                                .showMessageDialog(myFrame, "Code Generator Error: I/O error in types copying\\"
                                        + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane
                                .showMessageDialog(myFrame, "Code Generator Error: opm types copying failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    translator.ITranslator engine = new translator.Engine(
                            new String[] {});

                    try {
                        engine.loadLanguage(new FileInputStream(
                                "codeGenerator\\opl2Java.xml"));
                        engine.setOutputPREProcessor(new FileInputStream(
                                "codeGenerator\\transform.java.xsl"));
                        engine
                                .translateOPDS(new ByteArrayInputStream(
                                        currentProject.getOplXmlScript()
                                                .toString().getBytes()), selectedDirectory, true, System.out);

                    } catch (translator.TranslationException te) {
                        hideWaitMessage();
                        StateMachine.setWaiting(false);
                        setCursor4All(Cursor.DEFAULT_CURSOR);
                        JOptionPane
                                .showMessageDialog(myFrame, te.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                    } catch (FileNotFoundException fnfe) {
                        hideWaitMessage();
                        StateMachine.setWaiting(false);
                        setCursor4All(Cursor.DEFAULT_CURSOR);
                        JOptionPane
                                .showMessageDialog(myFrame, fnfe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        hideWaitMessage();
                        StateMachine.setWaiting(false);
                        setCursor4All(Cursor.DEFAULT_CURSOR);
                    }

                }
            };

            runner.start();

        }
    };

    ActionListener extensionToolsListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (!projectWasOpened) {
                JOptionPane
                        .showMessageDialog(myFrame, "No system is opened", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JPanel panel = null;
            String str = e.getActionCommand();

            ExtensionToolInfo eti = (ExtensionToolInfo) extensionsTable
                    .get(str);

            if (eti != null) {
                for (int i = 1; i < extensionToolsPane.getTabCount(); i++) {

                    if (extensionToolsPane.getTitleAt(i).equals(eti.getName())) {
                        extensionToolsPane.setSelectedIndex(i);
                        return;
                    }
                }

                try {
                    if (eti.getExtension() instanceof OpcatExtensionToolX) {
                        panel = ((OpcatExtensionToolX) eti.getExtension())
                                .execute((IXSystem) currentProject);
                    } else {
                        panel = ((OpcatExtensionTool) eti.getExtension())
                                .execute((ISystem) currentProject);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (panel != null) {
                    extensionToolsPane.add(eti.getName(), panel);
                    extensionToolsPane.setSelectedComponent(panel);
                }
            } else {
                JOptionPane
                        .showMessageDialog(myFrame, "No '" + str
                                + "' Extension Tool could be found.", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    ActionListener extensionToolsListenerAbout = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            ExtensionToolInfo eti = (ExtensionToolInfo) extensionsTable
                    .get(str);
            JPanel aboutPanel = null;
            if (eti != null) {
                aboutPanel = eti.getExtension().getAboutBox();

                if (aboutPanel != null) {
                    aboutDlg = new JDialog(Opcat2.getFrame(), true);
                    aboutDlg.setTitle("About " + eti.getName());
                    aboutDlg.getContentPane().setLayout(new BorderLayout());
                    aboutDlg.getContentPane().add(aboutPanel);
                    JButton tButton = new JButton("Close");
                    JPanel tp = new JPanel(new GridLayout(1, 5));
                    tp
                            .setBorder(BorderFactory
                                    .createEmptyBorder(0, 10, 10, 10));
                    tp.add(Box.createHorizontalGlue());
                    tp.add(Box.createHorizontalGlue());
                    tp.add(tButton);
                    tp.add(Box.createHorizontalGlue());
                    tp.add(Box.createHorizontalGlue());
                    tButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            aboutDlg.dispose();
                        }
                    });

                    aboutDlg.getContentPane().add(tp, BorderLayout.SOUTH);
                    aboutDlg
                            .setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                    aboutDlg.pack();

                    int fX = Opcat2.getFrame().getX();
                    int fY = Opcat2.getFrame().getY();
                    int pWidth = Opcat2.getFrame().getWidth();
                    int pHeight = Opcat2.getFrame().getHeight();
                    aboutDlg
                            .setLocation(fX
                                    + Math.abs(pWidth / 2 - aboutDlg.getWidth()
                                            / 2), fY
                                    + Math.abs(pHeight / 2
                                            - aboutDlg.getHeight() / 2));

                    aboutDlg.setVisible(true);
                }
            }
        }
    };

    ActionListener tBarslst = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JToolBar tmpBar = standardToolBar;

            for (int i = 0; i < toolbars.length; i++) {
                if (toolbars[i].getName().equals(e.getActionCommand()))
                    tmpBar = toolbars[i];
            }

            tmpBar.setVisible(!tmpBar.isVisible());
        }
    };

    ActionListener lst = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            Object obj = m_lfs.get(str);
            if (obj != null)
                try {
                    String className = (String) obj;
                    Class lnfClass = Class.forName(className);

                    UIManager.setLookAndFeel((LookAndFeel) (lnfClass
                            .newInstance()));

                    SwingUtilities.updateComponentTreeUI(myFrame);
                    _setBorders4ToolBar(animationToolBar);
                    _setBorders4ToolBar(standardToolBar);
                    _setBorders4ToolBar(componentsToolBar);

                } catch (Exception ex) {
                    OpcatLogger.logError(ex);
                }
        }
    };

    //	************************** OWL-S Start **********************************
    /*
     * Action semweb2opm = new AbstractAction("SemanticWeb to OPM") { public
     * void actionPerformed(ActionEvent e) { if (!projectWasOpened) {
     * JOptionPane.showMessageDialog(myFrame, "No system is opened", "Message",
     * JOptionPane.ERROR_MESSAGE); return; }
     * 
     * (new SemWebToOpm(currentProject, currentProject)).show(); } };
     * 
     * Action opm2semweb = new AbstractAction("OPM to OWL-S") { public void
     * actionPerformed(ActionEvent e) { if (!projectWasOpened) {
     * JOptionPane.showMessageDialog(myFrame, "No system is opened", "Message",
     * JOptionPane.ERROR_MESSAGE); return; }
     * 
     * (new OpmToSemWeb(currentProject, currentProject)).show(); } };
     */
    //	************************** OWL-S End **********************************
    //==========================================================================================
    // end unirversal actions for all controls
    //==========================================================================================
    private void _createSplashScreen() {
        Calendar rightNow = new GregorianCalendar();
        int year = rightNow.get(Calendar.YEAR);
        if (year < 2004) {
            year = 2004;
        }
        String copyrightString = " OPCAT Inc, All rights reserved (c) 2005 \n";
        splashLabel = new SplashLabel(copyrightString, MiscImages.SPLASH
                .getImage());
        splashLabel.setPreferredSize(new Dimension(370, 253));
        splashLabel.setForeground(new Color(44, 71, 148));
        splashLabel.setHorizontalAlignment(SwingConstants.CENTER);
        splashLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        splashScreen = new JWindow();
        splashScreen.getContentPane().setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        splashScreen.getContentPane().add(splashLabel, BorderLayout.CENTER);
        jpb = new JProgressBar();
        jpb.setForeground(new Color(37, 119, 197));
        jpb.setString("Loading... Please wait");
        jpb.setStringPainted(true);
        jpb.setMinimum(0);
        jpb.setMaximum(18);
        jpb.setValue(0);
        bottomPanel.add(jpb, BorderLayout.CENTER);
        JLabel licenseLabel = new JLabel(Opcat2.getLicense()
                .getLicenseCaption());
        licenseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(licenseLabel, BorderLayout.SOUTH);
        splashScreen.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        splashScreen.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splashScreen.setLocation(screenSize.width / 2
                - splashScreen.getSize().width / 2, screenSize.height / 2
                - splashScreen.getSize().height / 2);
    }

    private void _loadClasses() {
        OpdProject tProject = new OpdProject(desktop, 1);
        jpb.setValue(++progressCounter);
        ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(tProject,
                myFrame, "New Project Properties");
        Opd tOpd = new Opd(tProject, "tempName", 1, null);
        tProject.getComponentsStructure().put(1, tOpd);
        tProject.setCurrentOpd(tOpd);

        jpb.setValue(++progressCounter);

        JLayeredPane tContainer = tOpd.getDrawingArea();

        ProcessInstance pi = tProject.addProcess(5, 5, tContainer, -1, -1);

        jpb.setValue(++progressCounter);

        ProcessPropertiesDialog pd = new ProcessPropertiesDialog(
                (OpdProcess) pi.getThing(), (ProcessEntry) pi.getEntry(),
                tProject, false);
        jpb.setValue(++progressCounter);
        ObjectInstance oi1 = tProject
                .addObject(5, 5, tContainer, -1, -1, false);
        ObjectInstance oi2 = tProject
                .addObject(5, 5, tContainer, -1, -1, false);
        jpb.setValue(++progressCounter);
        ObjectPropertiesDialog od = new ObjectPropertiesDialog((OpdObject) oi1
                .getThing(), (ObjectEntry) oi1.getEntry(), tProject, false);

        jpb.setValue(++progressCounter);
        RelativeConnectionPoint rPoint = new RelativeConnectionPoint(
                OpcatConstants.N_BORDER, 0.5);
        LinkInstance li = tProject
                .addLink(oi1.getConnectionEdge(), rPoint, pi
                        .getConnectionEdge(), rPoint, tContainer, OpcatConstants.RESULT_LINK, -1, -1);
        jpb.setValue(++progressCounter);
        LinkPropertiesDialog ld = new LinkPropertiesDialog(li.getLink(), li
                .getKey(), tProject, "");
        jpb.setValue(++progressCounter);
        GeneralRelationInstance gri1 = tProject
                .addGeneralRelation(oi1.getConnectionEdge(), rPoint, oi2
                        .getConnectionEdge(), rPoint, tContainer, OpcatConstants.BI_DIRECTIONAL_RELATION, -1, -1);
        jpb.setValue(++progressCounter);
        GeneralBiDirRelationPropertiesDialog gd1 = new GeneralBiDirRelationPropertiesDialog(
                (OpmBiDirectionalRelation) gri1.getEntry().getLogicalEntity(),
                gri1.getKey(), tProject);

        GeneralRelationInstance gri2 = tProject
                .addGeneralRelation(oi1.getConnectionEdge(), rPoint, oi2
                        .getConnectionEdge(), rPoint, tContainer, OpcatConstants.UNI_DIRECTIONAL_RELATION, -1, -1);
        jpb.setValue(++progressCounter);
        GeneralUniDirRelationPropertiesDialog gd2 = new GeneralUniDirRelationPropertiesDialog(
                (OpmUniDirectionalRelation) gri2.getEntry().getLogicalEntity(),
                gri2.getKey(), tProject);
        jpb.setValue(++progressCounter);

        FundamentalRelationInstance fri = tProject
                .addRelation(oi1.getConnectionEdge(), rPoint, oi2
                        .getConnectionEdge(), rPoint, OpcatConstants.SPECIALIZATION_RELATION, tContainer, -1, -1);
        jpb.setValue(++progressCounter);
    }

    public void setProjectActionEnable(boolean isEnable) {
        newProjectActionA.setEnabled(!isEnable);

        closeActionA.setEnabled(isEnable);
        saveOpxActionA.setEnabled(isEnable);

        projectPropertiesAction.setEnabled(isEnable);
        printAction.setEnabled(isEnable);
        cutActionA.setEnabled(isEnable);
        copyActionA.setEnabled(isEnable);
        pasteActionA.setEnabled(isEnable);
        undoActionA.setEnabled(myUndoManager.canUndo());
        redoActionA.setEnabled(myUndoManager.canRedo());
        deleteActionA.setEnabled(isEnable);
        viewZoomInAction.setEnabled(isEnable);
        viewZoomOutAction.setEnabled(isEnable);
        objectAction.setEnabled(isEnable);

        processAction.setEnabled(isEnable);
        inAction.setEnabled(isEnable);
        agAction.setEnabled(isEnable);
        reAction.setEnabled(isEnable);
        effectAction.setEnabled(isEnable);
        instrumentEventLinkAction.setEnabled(isEnable);
        conditionAction.setEnabled(isEnable);
        exceptionAction.setEnabled(isEnable);
        invocationAction.setEnabled(isEnable);
        apAction.setEnabled(isEnable);
        fcAction.setEnabled(isEnable);
        gsAction.setEnabled(isEnable);
        ciAction.setEnabled(isEnable);
        unidirAction.setEnabled(isEnable);
        bidirAction.setEnabled(isEnable);
        unfoldingAction.setEnabled(isEnable);
        zoomInAction.setEnabled(isEnable);
        viewZoomOutAction.setEnabled(isEnable);

        saveOPLAction.setEnabled(isEnable);
        newButton.setEnabled(!isEnable);
        openButton.setEnabled(!isEnable);
        closeButton.setEnabled(isEnable);
        saveButton.setEnabled(isEnable);
        printButton.setEnabled(isEnable);
        searchButton.setEnabled( isEnable); 
        cutButton.setEnabled(isEnable);
        copyButton.setEnabled(isEnable);
        pasteButton.setEnabled(isEnable);
        undoButton.setEnabled(myUndoManager.canUndo());
        redoButton.setEnabled(myUndoManager.canRedo());
        deleteButton.setEnabled(isEnable);
    }

    public static JFrame getFrame() {
        return myFrame;
    }

    public static UndoManager getUndoManager() {
        return myUndoManager;
    }

    public static void setUndoEnabled(boolean flag) {
        undoButton.setEnabled(flag);
        undoItem.setEnabled(flag);
    }

    public static void setRedoEnabled(boolean flag) {
        redoButton.setEnabled(flag);
        redoItem.setEnabled(flag);
    }

    public static boolean isUndoEnabled() {
        return undoButton.isEnabled();
    }

    public static boolean isRedoEnabled() {
        return undoButton.isEnabled();
    }

   
    /**
     * function to updateStructurechange with an option not to update the OPL. 
     * this is usful when the object is not presened on the screen 
     * but generated automaticly. 
     * @param kindOfChange
     * @param withOPL - is OPL to be generated ?
     */
    public static void updateStructureChange(int kindOfChange) {
    	

        if (currentProject == null || kindOfChange == Opcat2.GRAPHICAL_CHANGE
                || StateMachine.isAnimated()) {
            return;
        }
	        
	    if (currentProject.isPresented()) {	        
	
	        if (kindOfChange == Opcat2.OPD_CHANGE) {
	            if (showHideExtensionTools.isSelected() && !isWholeProjectCompiled) {
	                Opd currentOpd = currentProject.getCurrentOpd();
	                if (currentOpd == null) {
	                    oplPane.setText(EMPTY_HTML);
	                } else {
	                    oplPane.setText(currentProject.getOPL(true, currentOpd
	                            .getOpdId()));
	                }
	                oplPane.setCaretPosition(0);
	            }
	
	            return;
	        }
	
	        // Logical change
	        repository.rebuildTrees();
	        if (!showHideExtensionTools.isSelected()) {
	            return;
	        }
	
	        int currentPosition = oplPane.getCaretPosition();
	
	        if (isWholeProjectCompiled) {
	            oplPane.setText(currentProject.getOPL(true));
	        } else {
	            Opd currentOpd = currentProject.getCurrentOpd();
	            if (currentOpd == null) {
	                oplPane.setText(EMPTY_HTML);
	            } else {
	                oplPane.setText(currentProject.getOPL(true, currentOpd
	                        .getOpdId()));
	            }
	        }
	        try {
	            oplPane.setCaretPosition(Math.min(currentPosition, oplPane
	                    .getText().length() - 1));
	        } catch (java.lang.IllegalArgumentException e) {
	            OpcatLogger.logError(e);
	        }
    	}
    }

    public void _enableZoomOut(boolean yn) {
        zoomOutButton.setEnabled(yn);
        anZoomOutButton.setEnabled(yn);
        viewZoomOutAction.setEnabled(yn);
    }

    public void _enableZoomIn(boolean yn) {
        zoomInButton.setEnabled(yn);
        anZoomInButton.setEnabled(yn);
        viewZoomInAction.setEnabled(yn);
    }

    private JMenu _constractExtensionToolsMenu() {
        JMenu extensionsMenu = new JMenu("Extension Tools");
        JMenuItem menuItem;
        Enumeration enum = extensionsTable.elements();
        ExtensionToolInfo eti = null;
        extensionsMenu.setEnabled(true);

        for (; enum.hasMoreElements();) {
            extensionsMenu.setEnabled(true);
            eti = (ExtensionToolInfo) enum.nextElement();
            menuItem = new JMenuItem(eti.getName());
            menuItem.addActionListener(extensionToolsListener);
            extensionsMenu.add(menuItem);
        }
        return extensionsMenu;
    }

    private JMenu _constructExtensionToolsAboutMenu() {
        JMenu extensionsMenu = new JMenu("About Extension Tools");
        extensionsMenu.setIcon(emptyIcon);
        JMenuItem menuItem;
        Enumeration enum = extensionsTable.elements();
        ExtensionToolInfo eti = null;
        extensionsMenu.setEnabled(true);

        for (; enum.hasMoreElements();) {
            eti = (ExtensionToolInfo) enum.nextElement();
            if (eti.getExtension().getAboutBox() != null) {
                extensionsMenu.setEnabled(true);
                menuItem = new JMenuItem(eti.getName());
                menuItem.addActionListener(extensionToolsListenerAbout);
                extensionsMenu.add(menuItem);
            }
        }
        return extensionsMenu;
    }

    private void _loadExtensionTools() {
        Enumeration locEnum = ExtensionToolsInstaller.getExtensionTools();
        ExtensionToolInfo eti = null;
        extensionsTable = new Hashtable();

        for (; locEnum.hasMoreElements();) {
            eti = (ExtensionToolInfo) locEnum.nextElement();
            extensionsTable.put(eti.getName(), eti);
        }
    }

    public static String getDrawingAreaOnMouseDragAction() {
        return drawingAreaOnMouseDragAction;
    }

    private void setCursor4All(final int cursorType) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                myFrame.setCursor(Cursor.getPredefinedCursor(cursorType));
                oplPane.setCursor(Cursor.getPredefinedCursor(cursorType));

                if (currentProject != null) {
                    for (Enumeration e = currentProject
                            .getComponentsStructure().getOpds(); e
                            .hasMoreElements();) {
                        Opd tempOpd = (Opd) e.nextElement();
                        tempOpd.getDrawingArea().setCursor(Cursor
                                .getPredefinedCursor(cursorType));
                    }
                }
            }
        });

    }

    public void showWaitMessage(String message, JProgressBar pBar) {
        waitScreen = new JWindow(myFrame);
        handleWaitScreen(waitScreen, message, pBar);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                waitScreen.setVisible(true);
            }
        });

    }

    private void hideWaitMessage() {
        if (waitScreen == null) {
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                waitScreen.dispose();
            }
        });

    }

    public void handleWaitScreen(JWindow theScreen, String message, JProgressBar pBar) {
        JLabel msgLabel = new JLabel(message);
        msgLabel.setBorder(new EmptyBorder(25, 30, 25, 30));
        msgLabel.setForeground(new Color(0, 51, 102));
        msgLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(msgLabel, BorderLayout.CENTER);
        panel.add(pBar, BorderLayout.SOUTH);

        panel.setBorder(new LineBorder(new Color(0, 51, 102), 1, true));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        theScreen.getContentPane().setLayout(new BorderLayout());
        theScreen.getContentPane().add(panel, BorderLayout.CENTER);
        pBar.setForeground(new Color(7, 65, 122));
        pBar.setBorder(new LineBorder(new Color(0, 51, 102)));
        pBar.setFont(new Font("Arial", Font.BOLD, 14));

        theScreen.pack();
        theScreen.setLocation(myFrame.getWidth() / 2
                - theScreen.getSize().width / 2, myFrame.getHeight() / 2
                - theScreen.getSize().height / 2);
    }

    private void showMetaWaitMessage(String message, JProgressBar pBar) {
        metaWaitScreen = new JWindow(myFrame);
        handleWaitScreen(metaWaitScreen, message, pBar);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                metaWaitScreen.setVisible(true);
            }
        });
    }

    private void hideMetaWaitMessage() {
        if (metaWaitScreen == null) {
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                metaWaitScreen.dispose();
            }
        });

    }

    /**
     * Handles situations when the system was already opened, and a new system
     * should be opened. The method closes the old system, if a system is
     * opened.
     * 
     * @return <code>false</code> if the user cancelled the operation.
     *         <code>true</code> otherwise.
     */
    public static void main(String[] args) {
        Opcat2 op = null;
        try {
            op = new Opcat2();
        } catch (Exception exc) {
            OpcatLogger.logError(exc);
            JOptionPane
                    .showMessageDialog(null, "Opcat2 Fatal Error: \"error.log\" file was created in Opcat2 directory.\nError is: "
                            + exc.getMessage(), "Opcat2 Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
        //If Opcat recieves a file name as input, it tries to load it
        if (op != null && args.length == 1) {
            op.getFileControl().loadOpxFile(args[0].toString(), true);
        }

    }

    /**
     * Checks wheather there is a need to save the system before closing it. The
     * method cheks if the undo manager has any changes or the
     * <code>systemChangeProbability</code> variable is set to
     * <code>true</code>.
     * 
     * @return <code>true</code> if it's possible to exit without changes.
     *         <code>false</code> otherwise.
     * @see UndoManager
     * @see #getSystemChangeProbability
     */
    public boolean canExitWithoutSave() {
        return myUndoManager.canCloseProject();
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
    public static void setShowProcessNameMessage(boolean flag) {
        showProcessNameMessage = flag;
        currentProject.setShowProcessNameMessage(flag);
    }

    /**
     * Returns the <code>showProcessNameMessage</code> flag. <code>true</code>
     * means that the user is interested in displaying annoying messages
     * regarding the "ing" ending in the process name.
     */
    public static boolean getShowProcessNameMessage() {
        return showProcessNameMessage;
    }

    //	OPCATeam: functions to support OPCATeam utilities
    private void setAdmin(org.objectprocess.team.Admin newAdmin) {
        repository.setAdmin(newAdmin);
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public Repository getRepository() {
        return repository;
    }

    public CollaborativeSessionMessageHandler getCollaborativeSessionMessageHandler() {
        return csMessageHandler;
    }

    public ChatMessageHandler getChatMessageHandler() {
        return chatMessageHandler;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom cr) {
        chatRoom = cr;
    }

    public void addChatRoom() {
        //OPCATeam : add tab for chat room.
        chatRoom = new ChatRoom(this);
        extensionToolsPane.add("Chat Room", chatRoom);
    }

    public void removeChatRoom() {
        if (chatRoom != null) {
            extensionToolsPane.remove(chatRoom);
            setChatRoom(null);
        }
    }

    public ActiveCollaborativeSession getActiveCollaborativeSession() {
        return activeCollaborativeSession;
    }

    public void setActiveCollaborativeSession(ActiveCollaborativeSession acs) {
        activeCollaborativeSession = acs;
    }

    private OpdProject _loadProjectWithSyncMessage(InputStream is)
            throws gui.opx.LoadException {
        gui.opx.Loader ld = new gui.opx.Loader();
        JProgressBar pBar = new JProgressBar();
        showWaitMessage(" New file arrived from Token Holder. Please Wait ..", pBar);
        OpdProject tProject = ld.load(desktop, is, pBar);
        hideWaitMessage();
        return tProject;
    }

    public void saveFileForOPCATeam() {
        fileControl._save(true);
    }

    public void loadFileForOPCATeam(String fileName, String sessionName, boolean onMessageRequest)
            throws Exception {
        try {

            StateMachine.setWaiting(true);
            setCursor4All(Cursor.WAIT_CURSOR);

            InputStream is;
            File f = new File(fileName);

            if (fileName.endsWith(".opz"))
                is = new GZIPInputStream(new FileInputStream(f), 4096);
            else
                is = new BufferedInputStream(new FileInputStream(f), 4096);

            if (onMessageRequest == true)
                currentProject = _loadProjectWithSyncMessage(is);
            else
                currentProject = fileControl
                        ._loadProject(is, new JProgressBar());
            is.close();
            currentProject.setFileName(fileName);
            if (sessionName != null)
                currentProject.setName(sessionName);

            projectWasOpened = true;
            StateMachine.reset();

            Opcat2.getFrame().setTitle("Opcat II - " + currentProject.getName()
                    + " : ");

            //setProjectActionEnable(true);
            GenericTable config = currentProject.getConfiguration();
            int currentSize = ((Integer) config.getProperty("CurrentSize"))
                    .intValue();
            int normalSize = ((Integer) config.getProperty("NormalSize"))
                    .intValue();

            _enableZoomIn(true);
            _enableZoomOut(true);

            if (currentSize == normalSize + 2)
                _enableZoomIn(false);

            if (currentSize == normalSize - 2)
                _enableZoomOut(false);

            repository.setProject(currentProject);
            Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);

        } catch (gui.opx.LoadException le) {
            throw le;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            StateMachine.reset();
            StateMachine.setWaiting(false);
            setCursor4All(Cursor.DEFAULT_CURSOR);
        }
    };

    public void closeFileForOPCATeam() {
        closeCleanUp();
    }

    /* added by YG */
    public JDesktopPane getDesktop() {
        return desktop;
    }

    /* added by YG */
    public OpdProject getCurrentProject() {
        return currentProject;
    }

    /* added by YG */
    public void setCurrentProject(IXSystem newCurrentProject) {
        currentProject = (OpdProject) newCurrentProject;
    }

    /* added by YG */

    public void setProjectOpened(boolean rc) {
        projectWasOpened = rc;
    }

    //	OPCATeam - end

    public boolean isProjectOpened() {
        return projectWasOpened;
    }

    /**
     * Handles the meta-libraries assigned to this project. The method laods the
     * libraries and handles the changes done to the list in the
     * <code>ProjectPropertiesDialog</code>.
     * 
     * @param metaLibraries
     *            A Vector containing <code>MetaLibrary</code> objects. If
     *            null, the method will return without carrying out any
     *            operation.
     */
    public void refreshMetaLibraries(Vector metaLibraries) {
        if (metaLibraries == null) {
            return;
        }
        currentProject.getMetaManager().setLibrariesVector(metaLibraries);
        JProgressBar pBar = new JProgressBar();
        showMetaWaitMessage("Loading meta-libraries...", pBar);
        try {
            currentProject.getMetaManager().refresh(currentProject, pBar);
        } finally {
            hideMetaWaitMessage();
        }
        if (currentProject.getMetaManager().containFailed()) {
            LibrariesLoadingWindow libsLoad = new LibrariesLoadingWindow(
                    currentProject.getMetaManager(), currentProject, myFrame);
            if (libsLoad.showDialog()) {
                refreshMetaLibraries(libsLoad.getUpdatedLibs());
            }
            /**
             * JOptionPane.showMessageDialog( Opcat2.getFrame(),
             * currentProject.getMetaManager().getFailedMessage(), "Opcat2 -
             * Error", JOptionPane.INFORMATION_MESSAGE);
             */
        }
    }

    /**
     * Removes the validation window.
     * 
     * @author Eran Toch BUG: the index might not always be 1
     */
    public void clearValidationWindow() {
        int indexOfValidation = extensionToolsPane
                .indexOfTab(Validator.VALIDATION_TAG_NAME);
        if (indexOfValidation > 0) {
            try {
                extensionToolsPane.remove(indexOfValidation);
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Validates the model against its meta-libraries.
     * 
     * @author Eran Toch
     */
    public void validate() {
        if (validator == null) {
            validator = new Validator();
        }
        JPanel validPanel = validator
                .execute(currentProject, Opcat2.this, myFrame);
        int indexOfValidation = extensionToolsPane
                .indexOfTab(Validator.VALIDATION_TAG_NAME);
        if (indexOfValidation > 0) {
            try {
                extensionToolsPane.remove(indexOfValidation);
            } catch (Exception ex) {
            }
        }
        extensionToolsPane.add(Validator.VALIDATION_TAG_NAME, validPanel);
        extensionToolsPane
                .setIconAt(extensionToolsPane
                        .indexOfTab(Validator.VALIDATION_TAG_NAME), StandardImages.VALIDATION);
        extensionToolsPane.setSelectedComponent(validPanel);
    }

    /**
     * loadNProject this function was written to support open of a new system
     * for open reuse and loading an open reused system
     * 
     * @param path
     * @return
     * @throws HeadlessException
     */
    public OpdProject loadNewProject(String path) throws HeadlessException {
        OpdProject newProject;
        try {
            File f = new File(path);
            gui.opx.Loader ld = new gui.opx.Loader();
            InputStream is;
            if (path.endsWith(".opz")) {
                is = new GZIPInputStream(new FileInputStream(f), 4096);
            } else {
                is = new BufferedInputStream(new FileInputStream(f), 4096);
            }
            JProgressBar pBar = new JProgressBar();
            newProject = ld.load(desktop, is, pBar);
            is.close();
            return newProject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the CHECKED image on the <code>setEnableDraggingButton</code> menu
     * item.
     * 
     * @param doEnable
     *            If <code>true</code>, the option would be displayed as
     *            <code>CHECKED</code>. Otherwise, it would be displayed as
     *            <code>EMPTY</code>.
     */
    //    public void enableDragging(boolean doEnable) {
    //        if (doEnable) {
    //            draggingButton.setIcon(StandardImages.CHECKED);
    //        } else {
    //            draggingButton.setIcon(StandardImages.EMPTY);
    //        }
    //    }
    //endReuseComment
    /**
     * Performs clean-up operations when a project is closed down.
     */
    public void closeCleanUp() {
        currentProject.close();
        //Merge remarked
        // enableDragging(false); //reuse
        Opcat2.getFrame().setTitle("Opcat II");
        repository.clearHistory();
        repository.setProject(null);
        currentProject = null;
        oplPane.setText(EMPTY_HTML);
        System.gc();
        projectWasOpened = false;
    }

    /**
     * Displays a message to the user.
     * 
     * @param title
     *            The title of the windows ("Error")
     * @param text
     *            The text of the message ("Code generator cannot work, becuase
     *            there is not enough memory")
     * @param type
     *            The type of the message, according to JOptionPane vocabulary
     *            (JOptionPane.ERROR_MESSAGE)
     */
    public static void showMessage(String title, String text, int type) {
        JOptionPane.showMessageDialog(myFrame, text, title, type);
    }

    /**
     * Returns the license class.
     */
    public static ILicense getLicense() {
        return license;
    }

    /**
     * Loads the license code from the license file.
     * 
     * @return
     */
    public ILicense loadLicense() {
        ILicense lic = null;
        Properties props = new Properties();
        boolean isError = false;
        try {
            FileInputStream fis = new FileInputStream("license.lic");
            props.load(fis);
            String code = props.getProperty("code");
            lic = LicenseFactory.createLicense(code);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            isError = true;
        } catch (IOException e1) {
            e1.printStackTrace();
            isError = true;
        }
        if (isError) {
            lic = LicenseFactory.createDefaultLicense();
        }
        return lic;
    }

    /**
     * @return the opl pane.
     */
    public static JTextPane getOplPane() {
        return oplPane;
    }

    /**
     * @return Returns the waitScreen.
     */
    public JWindow getWaitScreen() {
        return waitScreen;
    }

    /**
     * @param waitScreen
     *            The waitScreen to set.
     */
    public void setWaitScreen(JWindow waitScreen) {
        this.waitScreen = waitScreen;
    }

    /**
     * @return Returns the projectWasOpened.
     */
    public boolean isProjectWasOpened() {
        return projectWasOpened;
    }

    /**
     * @param projectWasOpened
     *            The projectWasOpened to set.
     */
    public void setProjectWasOpened(boolean projectWasOpened) {
        this.projectWasOpened = projectWasOpened;
    }

    /**
     * @return Returns the metaWaitScreen.
     */
    public JWindow getMetaWaitScreen() {
        return metaWaitScreen;
    }

    /**
     * @param metaWaitScreen
     *            The metaWaitScreen to set.
     */
    public void setMetaWaitScreen(JWindow metaWaitScreen) {
        this.metaWaitScreen = metaWaitScreen;
    }

    /**
     * Returns the Opcat undo manager.
     */
    public OpcatUndoManager getOpcatUndoManager() {
        return myUndoManager;
    }

    /**
     * @param myFileChooser
     *            The myFileChooser to set.
     */
    public void setMyFileChooser(JFileChooser myFileChooser) {
        this.myFileChooser = myFileChooser;
    }

    /**
     * @return Returns the extensionToolsPane.
     */
    public JTabbedPane getExtensionToolsPane() {
        return extensionToolsPane;
    }

    public void setCurrentProject(OpdProject newCurrentProject) {
        currentProject = newCurrentProject;
    }

    public OpcatDB getDataBase() {
        return dataBase;
    }

    public FileControl getFileControl() {
        return fileControl;
    }

    /**
     * @return Returns the isWholeProjectCompiled.
     */
    public boolean isWholeProjectCompiled() {
        return isWholeProjectCompiled;
    }

    /**
     * @param isWholeProjectCompiled
     *            The isWholeProjectCompiled to set.
     */
    public void setWholeProjectCompiled(boolean isWholeProjectCompiled) {
        Opcat2.isWholeProjectCompiled = isWholeProjectCompiled;
    }

    public void refreshMenuBar() {
        myFrame.setJMenuBar(_constructMenuBar());
        myFrame.validate();
        myFrame.repaint();
    }

    /**
     * Handles the redo/undo buttons appearence, after they were change by the
     * user.
     * 
     * @param isUndo
     *            If the parameter is <code>true</code>, then the operation
     *            will set the buttons to their state after an undo. Otherwise,
     *            it would be set to the state after redo.
     */
    public void toggleUndoButtons(boolean isUndo) {
        if (isUndo) {
            undoButton.setEnabled(myUndoManager.canUndo());
            undoButton.setToolTipText(myUndoManager.getUndoPresentationName());
            redoButton.setEnabled(myUndoManager.canRedo());
            redoButton.setToolTipText(myUndoManager.getRedoPresentationName());
            undoItem.setEnabled(myUndoManager.canUndo());
            redoItem.setEnabled(myUndoManager.canRedo());
        } else {
            undoButton.setEnabled(myUndoManager.canUndo());
            redoButton.setEnabled(myUndoManager.canRedo());
            undoItem.setEnabled(myUndoManager.canUndo());
            redoItem.setEnabled(myUndoManager.canRedo());
        }
    }
}