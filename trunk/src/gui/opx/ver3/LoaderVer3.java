package gui.opx.ver3;

import categories.Categories;
import com.csvreader.CsvReader;
import gui.controls.FileControl;
import gui.dataProject.DataCreatorType;
import gui.dataProject.DataProject;
import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.MetaManager;
import gui.opdProject.OpdProject;
import gui.opx.LoadException;
import gui.opx.ver2.LoaderVer2;
import gui.projectStructure.Entry;
import gui.projectStructure.RoleEntry;
import gui.scenarios.Scenario;
import gui.scenarios.Scenarios;
import modelControl.OpcatMCManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import util.OpcatLogger;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Eran Toch The 3rd version of the OPX loader. Adds the following
 *         features to the 2nd OPX loader version:
 *         <p/>
 *         1. Meta-Libraries loading. <br>
 *         2. Roles loading. <br>
 */
public class LoaderVer3 extends LoaderVer2 {

    public String CURRENT_VERSION;

    public LoaderVer3(String path) {
        super(path);
        this.CURRENT_VERSION = "3";
    }

    public String getVersion() {
        return this.CURRENT_VERSION;
    }

    public static void loadExtraData(OpdProject prj) throws IOException {
        String dir = FileControl.getInstance().getExtraDataDirectory();
        if (dir != null) {
            File directory = new File(dir);
            if (directory.exists() && directory.isDirectory()) {
                File propertiesFile = new File(directory.getPath() + File.separator + "properties.opd");
                if (!(propertiesFile.exists())) {
                    propertiesFile = new File(directory.getPath() + File.separator + "properties.csv");
                }
                if (propertiesFile.exists() && propertiesFile.isFile()) {
                    //file ok loading file
                    CsvReader reader = new CsvReader(new FileReader(propertiesFile));
                    reader.setDelimiter(',');
                    while (reader.readRecord()) {
                        String entryName = reader.getValues()[0];
                        String propertyName = reader.getValues()[1];
                        String propertyValue = reader.getValues()[2];
                        ArrayList<Entry> entries = prj.getSystemStructure().getEntryByFixedName(entryName);
                        if (entries == null) entries = prj.getSystemStructure().getEntry(entryName);
                        if ((entries != null) && (entries.size() > 0)) {
                            for (Entry entry : entries) {
                                if(propertyValue.indexOf(".") >= 0 ) {
                                    propertyValue = propertyValue.substring(0, propertyValue.indexOf(".") + 2) ;
                                }
                                entry.setProperty("entry.extdata." + propertyName, propertyValue);
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * Overloads the load method of version 2. Currently, does not provide any
     * functionality.
     */
    public OpdProject load(JDesktopPane parentDesktop, Document document,
                           JProgressBar progressBar) throws LoadException {
        try {
            // URI docURI = new URI(document.getBaseURI());
            // path = docURI.getPath();

            OpdProject prj = super.load(parentDesktop, document, progressBar);

            //load extraattributes ;
            if (prj != null)
                loadExtraData(prj);


            //now refrash roles on entries
//            Enumeration<Entry> entries = prj.getSystemStructure().getElements();
//            while (entries.hasMoreElements()) {
//                Entry entey = entries.nextElement();
//                RolesManager rolesManager = entey.getLogicalEntity().getRolesManager();
//                Enumeration<MetaLibrary> metas = prj.getMetaLibraries();
//                while (metas.hasMoreElements()) {
//                    MetaLibrary meta = metas.nextElement();
//                    rolesManager.loadOntRoles(meta);
//                }
//
//            }

            //open the tree

            return prj;
        } catch (LoadException lex) {
            throw lex;
        } // catch (URISyntaxException syn) {
        catch (FileNotFoundException e) {
            OpcatLogger.error(e, false);
        } catch (IOException e) {
            OpcatLogger.error(e, false);
        }

        return null;
        // throw new LoadException(syn.getMessage());
        // }
    }

    /**
     * Overloads the system attributes loading, setting the version number of
     * the OPX to <code>myProject</code>.
     *
     * @see OpdProject#setVersionString
     */
    protected void _loadSystemAttributes(Element system) throws LoadException {
        this.myProject.setVersionString(this.getVersion());

        try {
            super._loadSystemAttributes(system);
            if (system.getAttribute("global-id") != null) {
                this.myProject.setGlobalID(system.getAttributeValue("global-id"));
            }
            Element scenSection = system.getChild("Scenarios");
            if (scenSection != null) {
                List scens = scenSection.getChildren("Scenario");
                if (scens != null) {
                    this.myProject.setScen(this._loadScenarios(scens));
                }
            }


        } catch (LoadException lex) {
            throw lex;
        }
    }

    /**
     * Extends the <code>SystemProperties</code> loading section, augmenting it
     * with <b>MetaLibraries </b> support.
     */
    protected void _loadSystemProperties(Element props) throws JDOMException {
        try {
            super._loadSystemProperties(props);

            String loadCategories = System.getProperty("load.libs");
            boolean loadCategoriesBool = true;
            if (loadCategories != null) {
                try {
                    loadCategoriesBool = Boolean.valueOf(loadCategories);
                } catch (Exception ex) {
                    loadCategoriesBool = true;
                }
            }

            if (loadCategoriesBool) {
                Element metaSection = props.getChild("MetaLibraries");
                if (metaSection != null) {
                    List metaLibs = metaSection.getChildren("LibraryReference");
                    if (metaLibs != null) {
                        this._loadMetaLibraries(metaLibs, this.myProject.getMetaManager());
                    }
                }

                Categories.getInstance().load(this.myProject.getMetaManager());
            }

        } catch (JDOMException ex) {
            throw ex;
        }
    }

    protected Scenarios _loadScenarios(List scenarios) {
        Scenarios prjScenarios = new Scenarios();
        for (Iterator i = scenarios.iterator(); i.hasNext(); ) {
            Element scen = (Element) i.next();
            String scenName = scen.getAttributeValue("ScenName");
            Scenario scenrio = new Scenario(scenName);
            List types = scen.getChildren("types");
            for (Iterator j = types.iterator(); j.hasNext(); ) {
                Element typeElem = (Element) j.next();
                String type = typeElem.getAttributeValue("type");
                HashMap<Long, Long> typeHash = new HashMap<Long, Long>();
                List pairs = typeElem.getChildren("pairs");
                for (Iterator k = pairs.iterator(); k.hasNext(); ) {
                    Element pairElement = (Element) k.next();
                    Long ObjID = new Long(pairElement.getAttributeValue("ObjectID"));
                    Long StateID = new Long(pairElement.getAttributeValue("StateID"));
                    typeHash.put(ObjID, StateID);
                }
                if (type.equalsIgnoreCase("initial")) {
                    scenrio.setInitialObjectsHash(typeHash);
                } else {
                    scenrio.setFinalObjectsHash(typeHash);
                }
            }

            Element metaSection = scen.getChild("MetaLibraries");
            if (metaSection != null) {
                List metaLibs = metaSection.getChildren("LibraryReference");
                if (metaLibs != null) {
                    this._loadMetaLibraries(metaLibs, scenrio.getSettings());
                }
            }

            prjScenarios.add(scenrio);
        }
        return prjScenarios;
    }

    private String fixPrivatePath(String path, String loadingPath) {
        // loading Private
        return fixRelativePath(loadingPath);
    }

    private String fixTemplatesPath(String path, String templateOriginalPath) {

        // loading templates so we have to make sure to load from models
        // dir. this should realy be implemented as DataCreatorType

        String originalPath = templateOriginalPath;

        String workingCopyPath = OpcatMCManager.WORKING_COPY;

        int temp = workingCopyPath.lastIndexOf(FileControl.fileSeparator);

        String modelsDir = workingCopyPath.substring(temp + 1);

        String localPathToModelsDir = workingCopyPath.substring(0, temp);

        temp = originalPath.lastIndexOf(modelsDir);

        if (temp > 0) {
            originalPath = localPathToModelsDir + originalPath.substring(temp - 1);
        }

        return fixRelativePath(originalPath);
    }

    private String fixCategoriesPath(String path, String templateOriginalPath) {

        String categoriesDir = OpcatMCManager.CATEGORIES_DIR;
        int temp = path.lastIndexOf(FileControl.fileSeparator);
        String cat = path.substring(temp + 1);
        return categoriesDir + FileControl.fileSeparator + cat;
    }


    public String fixRelativePath(String relative, String originalPath) {

        return fixRelativePath(originalPath.substring(0, originalPath.lastIndexOf(FileControl.fileSeparator) + 1) + relative);


    }

    /**
     * referencePath is assumed to be a path to a file i.e. it ends with a file
     * name. this file path is the reference for the path element
     *
     * @param relativePath
     * @return
     */
    private String fixRelativePath(String relativePath) {

        String path = relativePath;

        path = path.replace("\\", FileControl.fileSeparator);

        relativePath = relativePath.replace("\\", FileControl.fileSeparator);

        path = path.replace("/", FileControl.fileSeparator);

        relativePath = relativePath.replace("/", FileControl.fileSeparator);

        if (path.startsWith(".")) {
            String filename = relativePath;
            int last = filename.lastIndexOf(FileControl.fileSeparator);
            path = filename.substring(0, last) + path.substring(1);
        }

        path = path.replace(FileControl.fileSeparator + "." + FileControl.fileSeparator, FileControl.fileSeparator);

        File myFile = new File(path);

        try {
            return myFile.getCanonicalPath();
        } catch (Exception ex) {

            String myDirectorySymbol = "@";

            path = path.replace(FileControl.fileSeparator, myDirectorySymbol);


            if (path.contains(myDirectorySymbol)) {
                StringTokenizer tok = new StringTokenizer(path, myDirectorySymbol);
                String prev = "";
                while (tok.hasMoreElements()) {
                    String current = tok.nextToken();
                    if (current.equalsIgnoreCase("..")) {
                        path = path.replaceFirst(myDirectorySymbol + prev + myDirectorySymbol + "..", "");
                    }
                    prev = current;
                }

            }

            path = path.replace(myDirectorySymbol, FileControl.fileSeparator);

            return path;
        }
    }

    /**
     * Gets the meta-libraries from the XML file and adds them to the
     * <code>MetaManager</code>.
     *
     * @param metaLibs
     * @param man
     */
    protected void _loadMetaLibraries(List<Element> metaLibs, MetaManager man) {

        Iterator<Element> i = metaLibs.iterator();

        while (i.hasNext()) {

            Element prop = i.next();

            int dataReferenceType = (prop.getAttributeValue("creatorReferenceType") != null ? Integer.parseInt(prop.getAttributeValue("creatorReferenceType"))
                    : DataCreatorType.REFERENCE_TYPE_TEMPLATE_POLICY_FILE);

            int libraryType = (prop.getAttributeValue("libDataType") != null ? Integer.parseInt(prop.getAttributeValue("libDataType"))
                    : MetaLibrary.TYPE_POLICY);

            boolean hidden = (prop.getAttributeValue("hidden") == null ? false
                    : Boolean.parseBoolean(prop.getAttributeValue("hidden")));

            int dataType = (prop.getAttributeValue("creatorDataType") != null ? Integer.parseInt(prop.getAttributeValue("creatorDataType"))
                    : DataCreatorType.DATA_TYPE_OPCAT_LIBRARAY);

            String libDataID = (prop.getAttributeValue("libDataID") != null ? prop.getAttributeValue("libDataID")
                    : "-1");

            String path = prop.getChildText("LibPath");

            DataProject data;

            if (dataReferenceType == DataCreatorType.REFERENCE_TYPE_TEMPLATE_POLICY_FILE) {
                String originalPath = (prop.getChildText("OriginalPath") != null ? prop.getChildText("OriginalPath")
                        : loadingPath);
                path = originalPath.substring(0, originalPath.lastIndexOf(FileControl.fileSeparator) + 1) + path;
                //path = fixTemplatesPath(path, originalPath);
            } else if (dataReferenceType == DataCreatorType.REFERENCE_TYPE_PRIVATE_POLICY_FILE) {
                path = fixPrivatePath(path, loadingPath);
            } else if (dataReferenceType == DataCreatorType.REFERENCE_TYPE_CATEGORY_CLASSIFICATION_FILE) {
                String originalPath = (prop.getChildText("OriginalPath") != null ? prop.getChildText("OriginalPath")
                        : loadingPath);
                path = fixCategoriesPath(path, originalPath);
            } else if (dataReferenceType == DataCreatorType.REFERENCE_TYPE_PRIVATE_CLASSIFICATION_FILE) {
                String originalPath = (prop.getChildText("OriginalPath") != null ? prop.getChildText("OriginalPath")
                        : loadingPath);
                path = fixRelativePath(path, originalPath);
            }

//            if ((libraryType == MetaLibrary.TYPE_CLASSIFICATION) && (dataType == DataCreatorType.DATA_TYPE_TEXT_FILE_CSV)) {
//                NAME originalPath = (prop.getChildText("OriginalPath") != null ? prop.getChildText("OriginalPath")
//                        : loadingPath);
//                path = fixRelativePath(prop.getChildText("LibPath"), originalPath);
//            }

            MetaLibrary lib = null;
            Object param;

            if (dataReferenceType == DataCreatorType.REFERENCE_TYPE_PRIVATE_POLICY_FILE) {
                param = new Object[]{path, myProject};
                // data = new DataProject(param, new DataCreatorType(dataType,
                // dataReferenceType));
            } else {
                param = path;
                // data = new DataProject(path, new DataCreatorType(dataType,
                // dataReferenceType));
            }

            lib = new MetaLibrary(libraryType, param, dataType,
                    dataReferenceType);


            lib.setHidden(hidden);

            try {
                if (libraryType == MetaLibrary.TYPE_CLASSIFICATION) {
                    lib.load();
                }
            } catch (Exception ex) {
                OpcatLogger.error(ex, false);
            }

            try {
                man.addMetaLibrary(lib);

            } catch (MetaException ex) {
                OpcatLogger.error("Error loading libraries: \n" + ex.getMessage());
                OpcatLogger.error(ex);
            }

        }

    }

    /**
     * Loads the <code>Entry</code> attributes, using the previous OPX version,
     * plus <code>Role</code> and URL loading.
     */
    protected void _loadEntityAttr(Entry entity, Element xmlEntity)
            throws JDOMException {
        // super._loadThingAttr(thing, xmlThing);
        super._loadEntityAttr(entity, xmlEntity);
        Element rolesSection = xmlEntity.getChild("Roles");
        if (rolesSection != null) {
            List roles = rolesSection.getChildren("OPMRole");
            if (roles != null) {
                for (Iterator i = roles.iterator(); i.hasNext(); ) {
                    Element xmlRole = (Element) i.next();
                    String roleID = xmlRole.getAttributeValue("roleID");
                    String libID = xmlRole.getAttributeValue("libID");
                    long roleIDNum = Long.parseLong(roleID);
                    long libIDNum = Long.parseLong(libID);
                    RoleEntry role = new RoleEntry(roleIDNum, libIDNum);
                    entity.addRole(role);
                }
            }
        }

    }


}
