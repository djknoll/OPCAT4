/*
 * Created on 15/05/2004
 */
package gui.opx.ver3;

import gui.dataProject.DataCreatorType;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.MetaManager;
import gui.metaLibraries.logic.Role;
import gui.opx.ver2.SaverVer2;
import gui.projectStructure.Entry;
import gui.scenarios.Scenario;
import gui.scenarios.Scenarios;
import gui.util.OpcatFile;
import org.jdom.Element;
import org.jdom.JDOMException;
import util.OpcatLogger;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The 3rd version of the OPX saver. Includes support for meta-libraries and
 * meta-libraries based roles.
 *
 * @author Eran Toch Created: 15/05/2004
 */
public class SaverVer3 extends SaverVer2 {

    public SaverVer3() {
        this.CURRENT_VERSION = "3";
    }

    /**
     * Overslods the system attributes saver, adding the <code>global-id</code>
     * attribute to the system element.
     */
    protected void _saveSystemAttributes(Element system) throws Exception {
        try {
            super._saveSystemAttributes(system);
            system.setAttribute("global-id", this.myProject.getGlobalID());
            saveTestingScen(myProject.getScen(), system);
            // saveOpdMeteSection(system);
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    protected void saveTestingScen(Scenarios scenrios, Element system) throws Exception {

        try {

            Element scenSection = new Element("Scenarios");

            // a list of all the scenrios
            for (String key : scenrios.keySet()) {
                Scenario scen = (Scenario) scenrios.get(key);
                Element scenElem = new Element("Scenario");
                scenElem.setAttribute("ScenName", key);

                try {
                    Element meteSection = _saveMetaSection(scen.getSettings());
                    if (meteSection != null)
                        scenElem.addContent(meteSection);
                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                }

                Element typesInitial = new Element("types");
                typesInitial.setAttribute("type", "initial");
                HashMap states = (HashMap) scen.getInitialObjectsHash();
                // here we have an hash of ObjectID, StateID
                Iterator data = states.keySet().iterator();
                while (data.hasNext()) {
                    Long objID = (Long) data.next();
                    Long stateID = (Long) states.get(objID);
                    Element pair = new Element("pairs");
                    pair.setAttribute("ObjectID", objID.toString());
                    pair.setAttribute("StateID", stateID.toString());
                    typesInitial.addContent(pair);
                }
                // if (typesInitial != null)
                scenElem.addContent(typesInitial);

                Element typesFinal = new Element("types");
                typesFinal.setAttribute("type", "final");
                states = (HashMap) scen.getFinalObjectsHash();
                // here we have an hash of ObjectID, StateID
                data = states.keySet().iterator();
                while (data.hasNext()) {
                    Long objID = (Long) data.next();
                    Long stateID = (Long) states.get(objID);
                    Element pair = new Element("pairs");
                    pair.setAttribute("ObjectID", objID.toString());
                    pair.setAttribute("StateID", stateID.toString());
                    // if (pair != null)
                    typesFinal.addContent(pair);
                }
                // if (typesFinal != null)
                scenElem.addContent(typesFinal);

                // if (scenElem != null)
                scenSection.addContent(scenElem);
            }
            system.addContent(scenSection);
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    /**
     * Extends the 2nd version of the <code>_saveSystemProperties</code> saver,
     * adding meta-librareis saving support.
     *
     * @return The <code>SystemProperties</code> XML element.
     */
    protected Element _saveSystemProperties() throws Exception {
        try {
            Element props = super._saveSystemProperties();
            Element meteSection = this._saveMetaSection(this.myProject
                    .getMetaManager());

            if (meteSection != null) {
                props.addContent(meteSection);
            }

            return props;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    /**
     * Saves the meta-libraries to the XML file.
     *
     * @throws JDOMException
     */
    protected Element _saveMetaSection(MetaManager man) throws Exception {

        try {
            if (man.size() == 0) {
                return null;
            }
            Element metaSection = new Element("MetaLibraries");
            MetaLibrary lib;
            Enumeration libs = man.getMetaLibraries();
            while (libs.hasMoreElements()) {
                lib = (MetaLibrary) libs.nextElement();

                // if (lib.getState() != MetaLibrary.STATE_LOADED)
                // lib.load();

                Element xmlLib = new Element("LibraryReference");
                xmlLib.setAttribute("creatorReferenceType", Integer.toString(lib
                        .getReferenceType()));

                xmlLib.setAttribute("creatorDataType", Integer.toString(lib
                        .getDataProject().getSourceType().getType()));

                xmlLib.setAttribute("hidden", Boolean.toString(lib.isHidden()));

                if (lib.getType() == 0) {
                    xmlLib.setAttribute("libDataType", Integer
                            .toString(MetaLibrary.TYPE_POLICY));
                } else {
                    xmlLib.setAttribute("libDataType", Integer.toString(lib
                            .getType()));
                }

                Element xmlPath = new Element("LibPath");
                if ((lib.getReferenceType() == DataCreatorType.REFERENCE_TYPE_REPOSITORY_POLICY_FILE)
                        || (lib.getReferenceType() == DataCreatorType.REFERENCE_TYPE_URL)) {
                    xmlPath.setText(lib.getPath());
                } else {
                    xmlPath.setText(OpcatFile.getRealativePath(myProject
                            .getFileName(), lib.getPath()));
                    // xmlPath.setText(lib.getPath());
                }

                Element xmlOriginalPath = new Element("OriginalPath");
                xmlOriginalPath.setText(myProject.getPath());

                Element xmlLibID = new Element("libDataID");
                xmlLibID.setText(lib.getDataID());

                xmlLib.addContent(xmlOriginalPath);

                xmlLib.addContent(xmlPath);

                metaSection.addContent(xmlLib);
            }
            return metaSection;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    /**
     * Extends the <code>SaverVer2._saveThingAttr</code> with roles and url
     * support.
     *
     * @return the <code>Roles</code> XML element.
     */

    protected Element _saveEntityAttr(Entry entity) throws Exception {
        try {

            Element xmlEntity = super._saveEntityAttr(entity);
            Enumeration roles = entity.getRoles(); //.getRolesEntries();
            if (roles.hasMoreElements()) {
                Element rolesSection = new Element("Roles");
                while (roles.hasMoreElements()) {
                    Role roleEntry = (Role) roles.nextElement();
                    // if ((roleEntry.getLogicalRole().isLoaded()) ||
                    // (roleEntry.getLogicalRole().getOntology().getIDType() ==
                    // MetaLibrary.DATA_TYPE_DATA) ) {
                    Element opmRole = new Element("OPMRole");
                    if (roleEntry != null) {
                        opmRole.setAttribute("roleID", Long.toString(roleEntry.getThingId()));
                        opmRole.setAttribute("libID", Long.toString(roleEntry.getLibraryId()));
                        rolesSection.addContent(opmRole);
                    }
                    // }
                }
                xmlEntity.addContent(rolesSection);
            }
            return xmlEntity;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }


}