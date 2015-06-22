package gui.opx.ver2;

import exportedAPI.OpdKey;
import expose.OpcatExposeKey;
import gui.actions.expose.OpcatExposeChange;
import gui.opdGraphics.lines.StyledLine;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdFundamentalRelation;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.GenericTable;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.*;
import gui.opx.SaverI;
import gui.projectStructure.*;
import gui.util.OpcatFile;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import util.Configuration;
import util.OpcatLogger;

import javax.swing.*;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lera t To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class SaverVer2 implements SaverI {
    public String CURRENT_VERSION;

    protected final static int ROOT_OPD = 1;

    protected final static int OR_TYPE = 0;

    protected final static int XOR_TYPE = 1;

    protected XMLOutputter outp = new XMLOutputter();

    protected Document doc;

    protected OpdProject myProject;

    public SaverVer2() {
        this.CURRENT_VERSION = "2";
    }

    public String getVersion() {
        return this.CURRENT_VERSION;
    }

    public void save(OpdProject project, OutputStream os,
                     JProgressBar progressBar) throws Exception {
        try {
            this.myProject = project;
            this.doc = new Document();
            Element mainTag = new Element("OPX");
            this.doc.setRootElement(mainTag);
            mainTag.setAttribute("version", this.getVersion());
            Element system = new Element("OPMSystem");
            mainTag.addContent(system);
            // creationDate="2003-06-24T16:44:29.000+02:00"
            // lastUpdate="2003-07-18T13:17:05.218+02:00"
            system
                    .setAttribute("creationDate",
                            "2003-06-24T16:44:29.000+02:00");
            system.setAttribute("lastUpdate", "2003-06-24T16:44:29.000+02:00");
            this._saveSystemAttributes(system);
            system.addContent(this._saveSystemProperties());
            system.addContent(this._saveLogicalStructure());
            system.addContent(this._saveVisualPart());


            //Format format = this.outp.getFormat();
            //format.setIndent("      ");
            //format.setTextMode(TextMode.TRIM);
            //format.setLineSeparator(System.getProperty("line.separator"));
            // outp.setTextTrim(true);
            // outp.setNewlines(true);
            outp.setFormat(Format.getPrettyFormat());
            this.outp.output(this.doc, os);
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // Gets OPMSystem

    protected void _saveSystemAttributes(Element system) throws Exception {
        try {
            system.setAttribute("author", this.myProject.getCreator());
            system.setAttribute("modeltype", myProject.getCurrentModelType());
            system.setAttribute("name", this.myProject.getName());
            system.setAttribute("uuid", this.myProject.getUuid().toString());


            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            system.setAttribute("creationDate", df.format(this.myProject
                    .getCreationDate()));
            GregorianCalendar cal = new GregorianCalendar();
            system.setAttribute("lastUpdate", df.format(cal.getTime()));
            Attribute attr = new Attribute("maxEntityEntry", Long
                    .toString(this.myProject.getEntityMaxEntry()));
            system.setAttribute(attr);
            attr = new Attribute("maxOpdEntry", Long.toString(this.myProject
                    .getOpdMaxEntry()));
            system.setAttribute(attr);
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // SystemProperties

    protected Element _saveSystemProperties() throws Exception {
        try {
            Element props = new Element("SystemProperties");
            Element cnfg = new Element("SystemConfiguration");
            this._saveGenericTable(cnfg, this.myProject.getConfiguration());
            props.addContent(cnfg);
            Element genInfo = new Element("GeneralInfo");
            this._saveGenericTable(genInfo, this.myProject.getGeneralInfo());
            props.addContent(genInfo);
            return props;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // Gets cnfg - SystemConfiguration and adds to it list of "Property"
    // elements

    protected void _saveGenericTable(Element cnfg, GenericTable table) throws Exception {
        try {
            List propsList = new LinkedList();
            for (Enumeration e = table.propertyNames(); e.hasMoreElements(); ) {
                String propName = (String) e.nextElement();
                String propValue = table.getDBString(propName);
                if (propValue != null) {
                    Element prop = new Element("Property");
                    prop.setAttribute("key", propName);
                    prop.setAttribute("value", propValue);
                    //Element val = new Element("value");
                    //val.setText(propValue);
                    //prop.addContent(val);
                    propsList.add(prop);
                }
            }
            cnfg.addContent(propsList);
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // LogicalStructure

    protected Element _saveLogicalStructure() throws Exception {

        try {
            Element logicalStruct = new Element("LogicalStructure");
            logicalStruct.addContent(this._saveObjectSection());
            logicalStruct.addContent(this._saveProcessSection());
            logicalStruct.addContent(this._saveRelationSection());
            logicalStruct.addContent(this._saveLinkSection());
            return logicalStruct;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected Element _saveObjectSection() throws Exception {


        try {
            Element objectSect = new Element("ObjectSection");
            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getElements(); e.hasMoreElements(); ) {
                Entry currentEntry = (Entry) (e.nextElement());
                if (currentEntry instanceof ObjectEntry) {
                    this._saveLogicalObject((ObjectEntry) currentEntry, objectSect);
                }
            }
            return objectSect;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    protected void _saveLogicalObject(ObjectEntry objEntry, Element obj) throws Exception {

        try {
            Element xmlObject = new Element("LogicalObject");

            xmlObject.addContent(this._saveEntityAttr(objEntry));
            xmlObject.addContent(this._saveThingAttr(objEntry));

            if (!objEntry.getIndexName().equalsIgnoreCase(OpmObject.DEFAULT_INDEX_NAME))
                xmlObject.setAttribute("indexName", objEntry.getIndexName());

            if (objEntry.getIndexOrder() != OpmObject.DEFAULT_INDEX_ORDER)
                xmlObject.setAttribute("indexOrder", Integer.toString(objEntry
                        .getIndexOrder()));

            if (!objEntry.getInitialValue().equalsIgnoreCase(OpmObject.DEFAULT_INITIAL_VALUE))
                xmlObject.setAttribute("initialValue", objEntry.getInitialValue());

            if (objEntry.isPersistent() != OpmObject.DEFAULT_PERSISTENT)
                xmlObject.setAttribute("persistent", Boolean.toString(objEntry
                        .isPersistent()));

            if (objEntry.isKey() != OpmObject.DEFAULT_IS_KEY)
                xmlObject.setAttribute("key", Boolean.toString(objEntry.isKey()));

            if (!objEntry.getType().equalsIgnoreCase(OpmObject.DEFAULT_TYPE))
                xmlObject.setAttribute("type", objEntry.getType());

            if (objEntry.getTypeOriginId() != OpmObject.DEFAULT_TYPE_ORIGIN_ID)
                xmlObject.setAttribute("typeOriginId", Long.toString(objEntry
                        .getTypeOriginId()));

            OpmObject opm = (OpmObject) objEntry.getLogicalEntity();
            if (opm.isMesurementUnitExists()) {
                xmlObject.setAttribute("measurmentUnit", opm.getMesurementUnit());
                xmlObject.setAttribute("measurmentUnitInitialValue", Double
                        .toString(opm.getMesurementUnitInitialValue()));
                xmlObject.setAttribute("measurmentUnitMinValue", Double.toString(opm
                        .getMesurementUnitMinValue()));
                xmlObject.setAttribute("measurmentUnitMaxValue", Double.toString(opm
                        .getMesurementUnitMaxValue()));
            }

            this._saveLogicalStates(xmlObject, objEntry);
            if (obj.getChildren("LogicalObject") != null) {
                obj.getChildren("LogicalObject").add(xmlObject);
            } else {
                obj.addContent(xmlObject);
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected void _saveLogicalStates(Element obj, ObjectEntry parentEntry) throws Exception {
        try {
            List statesList = new LinkedList();
            for (Enumeration e = parentEntry.getStates(); e.hasMoreElements(); ) {
                StateEntry state = (StateEntry) e.nextElement();

                Element xmlState = new Element("LogicalState");
                xmlState.addContent(this._saveEntityAttr(state));

                if (state.isDefault() != OpmState.DEFAULT_DEFAULT_STATE)
                    xmlState.setAttribute("default", Boolean
                            .toString(state.isDefault()));

                if (state.isFinal() != OpmState.DEFAULT_FINAL_STATE)
                    xmlState.setAttribute("final", Boolean.toString(state.isFinal()));

                if (state.isInitial() != OpmState.DEFAULT_INITIAL_STATE)
                    xmlState.setAttribute("initial", Boolean
                            .toString(state.isInitial()));

                if (!state.getMaxTime().equalsIgnoreCase(OpmState.DEFAULT_MAX_TIME)) {
                    xmlState.setAttribute("maxTime", state.getMaxTime());
                }

                if (!state.getMinTime().equalsIgnoreCase(OpmState.DEFAULT_MIN_TIME)) {
                    xmlState.setAttribute("minTime", state.getMinTime());
                }

                statesList.add(xmlState);
            }
            obj.addContent(statesList);
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;

        }
    }


    private Element _saveIncludedStates(Entry entry) throws Exception {


        try {
            Element xmlInstances = new Element("EntityStateEntries");
            if (entry instanceof ThingEntry) {
                ThingEntry thing = (ThingEntry) entry;
                Collection<OpmState> states = thing.getStateHash().values();
                for (OpmState opmState : states) {
                    StateEntry stateEntry = (StateEntry) myProject.getSystemStructure().getEntry(opmState.getId());
                    Element stateXML = new Element("state");
                    stateXML.setAttribute("uuid", stateEntry.getUUID().toString());
                    xmlInstances.addContent(stateXML);
                }
            }
            return xmlInstances;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    private Element _saveIncludedInstances(Entry entry) throws Exception {
        try {
            Element xmlInstances = new Element("EntityIncludedInstances");

            if (entry instanceof ThingEntry) {
                ThingEntry thing = (ThingEntry) entry;
                if (thing.getZoomedInOpd() != null) {
                    Opd opd = thing.getZoomedInOpd();
                    Vector<Instance> processes = ((ThingInstance) opd.getMainIXInstance()).getIncludedProcessInstancesInMyInZoomOPD();
                    for (Instance ins : processes) {
                        xmlInstances.addContent(_saveInstance(ins));
                    }
                    Vector<Instance> objects = ((ThingInstance) opd.getMainIXInstance()).getIncludedObjectInstancesInMyInZoomOPD();
                    for (Instance ins : objects) {
                        xmlInstances.addContent(_saveInstance(ins));
                    }
                }
            }

            return xmlInstances;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    private Element _saveInstance(Instance instance) throws Exception {

        try {

            Element xmlInstance = new Element("instance");
            xmlInstance.setAttribute("uuid", instance.getUUID().toString());
            xmlInstance.setAttribute("name", instance.getEntry().getName() + " ( OPD : " + instance.getOpd().getName() + ")");

            //xmlInstance.setAttribute("opd", NAME.valueOf(instance.getOpd().getOpdId()));
            //xmlInstance.setAttribute("in.opd.id", instance.getKey().toString().split(" ")[1]);
            //if (instance.isMainInstance())
            //    xmlInstance.setAttribute("main.instance", NAME.valueOf(instance.isMainInstance()));
            if (instance instanceof ProcessInstance) {
                ProcessInstance tInstance = (ProcessInstance) instance;
                if (tInstance.getOrder() > 0)
                    xmlInstance.setAttribute("realetive.order", String.valueOf(tInstance.getOrder()));
                //calculate global order
            }
            //xmlInstance.setAttribute("type", instance.getTypeString());
            //xmlInstance.setAttribute("opd-id", NAME.valueOf(instance.getKey().getOpdId()));
            return xmlInstance;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    private Element _saveInstances(Entry entry) throws Exception {

        try {

            Element xmlInstances = new Element("EntityInstances");
            Enumeration<Instance> instances = entry.getInstances(false);

            int i = 0;
            while (instances.hasMoreElements()) {
                Instance ins = instances.nextElement();
                xmlInstances.addContent(_saveInstance(ins));
                i++;
            }

            xmlInstances.setAttribute("count", String.valueOf(i));

            return xmlInstances;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    protected Element _saveEntityAttr(Entry entity) throws Exception {
        try {
            Element xmlEntity = new Element("EntityAttr");// factory.createEntityAttrType();
            xmlEntity.setAttribute("id", Long.toString(entity.getId()));
            xmlEntity.setAttribute("uuid", entity.getUUID().toString());

            /**
             * take care of the url path
             */
            String old_url = entity.getUserURL();
            if (entity.hasUserURL()) {
                String str = entity.getUserURL();
                if (str.length() > 8) {
                    if (str.substring(0, 8).equalsIgnoreCase("file:///")) {
                        str = OpcatFile.getRealativePath(myProject.getFileName(), str
                                .substring(8));
                        str = "file:///" + str;
                    }
                    entity.setUserURL(str);
                }
            } else {
                entity.setUserURL("none");
            }
            /**
             * save properties
             */
            xmlEntity.addContent(entity.getMyProps().getPropertiesElement(OpcatProperties.OpcatPropertiesGroups.ALL, "EntryProperties"));
            xmlEntity.addContent(entity.getLogicalEntity().getMyProps().getPropertiesElement(OpcatProperties.OpcatPropertiesGroups.ALL, "OPMProperties"));

            /**
             * return url to old url
             */
            entity.setUserURL(old_url);

            //xmlEntity.setAttribute("environmental", Boolean.toString(entity
            //        .isEnvironmental()));

            xmlEntity.setAttribute("exposed", Boolean.toString(entity
                    .getLogicalEntity().isPublicExposed()));

            xmlEntity.setAttribute("privateexposed", Boolean.toString(entity
                    .getLogicalEntity().isPrivateExposed()));

            // if (entity.getLogicalEntity().isExposed()) {

            // }

            try {
                Element exposeElement = _saveExposePrivateChange(myProject
                        .getExposeManager().getExposeLastChangeOp(
                                entity.getLogicalEntity().getId(), true));
                if (exposeElement != null)
                    xmlEntity.addContent(exposeElement);

                exposeElement = _saveExposePublicChange(myProject
                        .getExposeManager().getExposeLastChangeOp(
                                entity.getLogicalEntity().getId(), false));
                if (exposeElement != null)
                    xmlEntity.addContent(exposeElement);

            } catch (Exception ex) {
                OpcatLogger.error(new Exception("Error saving expose history",
                        ex));
            }

            //name = new Element("iconName");
            //name.addContent(entity.getIconPath());
            //xmlEntity.addContent(name);

            xmlEntity.addContent(_saveInstances(entity));

            return xmlEntity;

        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    protected Element _saveThingAttr(ThingEntry thing) throws Exception {
        try {
            Element xmlThing = new Element("ThingAttr");// factory.createThingAttrType();

            if (thing.getNumberOfMASInstances() != OpmThing.DEFAULT_NUMBER_OF_INSTANCES)
                xmlThing.setAttribute("numberOfInstances", Integer.toString(thing
                        .getNumberOfMASInstances()));
            //xmlThing.setAttribute("physical", Boolean.toString(thing.isPhysical()));
            if (!(thing.getFreeTextRole().equalsIgnoreCase(OpmThing.DEFAULT_ROLE)))
                xmlThing.setAttribute("role", thing.getFreeTextRole());

            if ((thing.getScope() != null) && !(thing.getScope().equalsIgnoreCase(OpmThing.DEFAULT_SCOPE)))
                xmlThing.setAttribute("scope", thing.getScope());

            xmlThing.addContent(_saveIncludedInstances(thing));

            //xmlThing.addContent(_saveIncludedStates(thing));

            return xmlThing;

        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // ProcessSection

    protected Element _saveProcessSection() throws Exception {
        try {
            Element processSect = new Element("ProcessSection");

            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getElements(); e.hasMoreElements(); ) {
                Entry currentEntry = (Entry) (e.nextElement());
                if (currentEntry instanceof ProcessEntry) {

                    this._saveLogicalProcess((ProcessEntry) currentEntry,
                            processSect);
                }

            }

            return processSect;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;

        }

    }

    protected void _saveLogicalProcess(ProcessEntry procEntry,
                                       Element processSect) throws Exception {

        try {
            Element xmlProcess = new Element("LogicalProcess");

            xmlProcess.addContent(this._saveEntityAttr(procEntry));
            xmlProcess.addContent(this._saveThingAttr(procEntry));


            if (!procEntry
                    .getMaxTimeActivation().equalsIgnoreCase(OpmProcess.DEFAULT_MAX_TIME_ACTIBVATION))
                xmlProcess.setAttribute("maxTimeActivation", procEntry
                        .getMaxTimeActivation());

            if (!procEntry
                    .getMinTimeActivation().equalsIgnoreCase(OpmProcess.DEFAULT_MIN_TIME_ACTIBVATION))
                xmlProcess.setAttribute("minTimeActivation", procEntry
                        .getMinTimeActivation());


            if (!procEntry.getProcessBody().equalsIgnoreCase(OpmProcess.DEFAULT_PROCESS_BODY)) {
                Element procBody = new Element("processBody");
                procBody.addContent(procEntry.getProcessBody());
                xmlProcess.addContent(procBody);
            }

            if (processSect.getChildren("LogicalProcess") != null) {
                processSect.getChildren("LogicalProcess").add(xmlProcess);
            } else {
                processSect.addContent(xmlProcess);
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // RelationSection

    protected Element _saveRelationSection() throws Exception {

        try {
            Element relationSect = new Element("RelationSection");

            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getElements(); e.hasMoreElements(); ) {
                Entry currentEntry = (Entry) (e.nextElement());
                if (currentEntry instanceof RelationEntry) {
                    this._saveLogicalRelation((RelationEntry) currentEntry,
                            relationSect);
                }

            }

            return relationSect;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;

        }

    }

    protected void _saveLogicalRelation(RelationEntry relEntry,
                                        Element relationSect) throws Exception {

        try {
            Element xmlRelation = new Element("LogicalRelation");
            xmlRelation.addContent(this._saveEntityAttr(relEntry));

            xmlRelation.setAttribute("relationType", Integer.toString(relEntry
                    .getRelationType()));

            xmlRelation.setAttribute("sourceId", Long.toString(relEntry
                    .getSourceId()));

            xmlRelation.setAttribute("destinationId", Long.toString(relEntry
                    .getDestinationId()));

            xmlRelation.setAttribute("source-uuid", relEntry.getSourceUUID().toString());

            xmlRelation.setAttribute("destination-uuid", relEntry.getDestinationUUID().toString());

            if (!relEntry.getSourceCardinality().equalsIgnoreCase(OpmFundamentalRelation.DEFAULT_SOURCE_CARDINALITY))
                xmlRelation.setAttribute("sourceCardinality", relEntry
                        .getSourceCardinality());

            if (!relEntry.getDestinationCardinality().equalsIgnoreCase(OpmFundamentalRelation.DEFAULT_DESTINATION_CARDINALITY))
                xmlRelation.setAttribute("destinationCardinality", relEntry
                        .getDestinationCardinality());

            if (!relEntry.getForwardRelationMeaning().equalsIgnoreCase(OpmFundamentalRelation.DEFAULT_FORWARD_RELATION_MEANING))
                xmlRelation.setAttribute("forwardRelationMeaning", relEntry
                        .getForwardRelationMeaning());

            if (!relEntry.getBackwardRelationMeaning().equalsIgnoreCase(OpmFundamentalRelation.DEFAULT_BACKWARD_RELATION_MEANING))
                xmlRelation.setAttribute("backwardRelationMeaning", relEntry
                        .getBackwardRelationMeaning());

            if (relationSect.getChildren("LogicalRelation") != null) {
                relationSect.getChildren("LogicalRelation").add(xmlRelation);
            } else {
                relationSect.addContent(xmlRelation);
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // LinkSection

    protected Element _saveLinkSection() throws Exception {
        try {
            Element linkSect = new Element("LinkSection");

            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getElements(); e.hasMoreElements(); ) {
                Entry currentEntry = (Entry) (e.nextElement());
                if (currentEntry instanceof LinkEntry) {
                    this._saveLogicalLink((LinkEntry) currentEntry, linkSect);
                }

            }

            return linkSect;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected void _saveLogicalLink(LinkEntry lEntry, Element link) throws Exception {
        try {
            Element xmlLink = new Element("LogicalLink");

            xmlLink.addContent(this._saveEntityAttr(lEntry));

            xmlLink
                    .setAttribute("linkType", Integer
                            .toString(lEntry.getLinkType()));

            xmlLink.setAttribute("sourceId", Long.toString(lEntry.getSourceId()));

            xmlLink.setAttribute("destinationId", Long.toString(lEntry
                    .getDestinationId()));

            xmlLink.setAttribute("source-uuid", lEntry.getSourceUUID().toString());

            xmlLink.setAttribute("destination-uuid", lEntry.getDestinationUUID().toString());

            if (!lEntry.getCondition().equalsIgnoreCase(OpmProceduralLink.DEFAULT_CONDITION))
                xmlLink.setAttribute("condition", lEntry.getCondition());

            if (!lEntry.getPath().equalsIgnoreCase(OpmProceduralLink.DEFAULT_PATH))
                xmlLink.setAttribute("path", lEntry.getPath());

            if (!lEntry.getMaxReactionTime().equalsIgnoreCase(OpmProceduralLink.DEFAULT_MAX_TIME_REACTION))
                xmlLink.setAttribute("maxReactionTime", lEntry.getMaxReactionTime());

            if (!lEntry.getMinReactionTime().equalsIgnoreCase(OpmProceduralLink.DEFAULT_MIN_TIME_REACTION))
                xmlLink.setAttribute("minReactionTime", lEntry.getMinReactionTime());
            if (lEntry
                    .getResourceConsumption() != LinkEntry.DEFAULT_RESOURCE_CONSUMPTION)
                xmlLink.setAttribute("resourceConsumption", String.valueOf(lEntry
                        .getResourceConsumption()));

            if (lEntry.isResourceConsumptionAccumolated() != LinkEntry.DEFAULT_RESOURCE_CONSUMPTION_ACCUMOLATED)
                xmlLink.setAttribute("resourceConsumptionAccumolated", String
                        .valueOf(lEntry.isResourceConsumptionAccumolated()));

            if (link.getChildren("LogicalLink") != null) {
                link.getChildren("LogicalLink").add(xmlLink);
            } else {
                link.addContent(xmlLink);
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // VisualPart

    protected Element _saveVisualPart() throws Exception {
        try {
            Element vpt = new Element("VisualPart");
            Hashtable opdDependencies = this._prepareOpdsDependencies();
            vpt.addContent(this._saveOpd(ROOT_OPD, opdDependencies));
            return vpt;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    /**
     * Creates an OPX representation of an OPD. Uses
     * <code>_appendOpdPropetries</code> in order to render the properties of
     * the actual OPD, and <code>_appendOpdPropetries</code> in order to render
     * the subelements of the OPD.
     *
     * @param opdNum The number of OPD
     */
    protected Element _saveOpd(long opdNum, Hashtable opdDependencies) throws Exception {
        try {
            Element opd = new Element("OPD");
            Opd myOpd = this.myProject.getComponentsStructure().getOpd(opdNum);
            myOpd.setViewZoomIn(false);
            opd = this._appendOpdPropetries(opd, myOpd);
            opd = this._appendOpdChildren(opdNum, opdDependencies, opd, myOpd);
            return opd;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    /**
     * Saves the properties of the OPD element.
     *
     * @param opd   The XML Element rendering of the OPD
     * @param myOpd The original OPD object
     * @return A modified opd XML element
     * @
     */
    protected Element _appendOpdPropetries(Element opd, Opd myOpd) throws Exception {
        try {
            opd.setAttribute("id", Long.toString(myOpd.getOpdId()));
            opd.setAttribute("name", myOpd.getName());
            opd.setAttribute("maxEntityEntry", Long.toString(myOpd
                    .getEntityInOpdMaxEntry()));
            opd.setAttribute("view", Boolean.toString(myOpd
                    .isView()));

            opd.setAttribute("indexinlevel", Integer.toString(myOpd
                    .getIndexInLevel()));
            if (myOpd.getDrawingArea().getBackground().getRGB() != Configuration.getInstance().getColorFromProperty("graphics.default.OPDBackgroundColor").getRGB())
                opd.setAttribute("background", String.valueOf(myOpd.getDrawingArea().getBackground().getRGB()));

            //if (myOpd.isShowExtData()) {
            //    opd.setAttribute("opd.show.ext.data" , String.valueOf(myOpd.isShowExtData())) ;
            //}
            return opd;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    /**
     * Renders the OPD subelements, such as tjomgs. fundumental relations etc.
     *
     * @
     */
    protected Element _appendOpdChildren(long opdNum,
                                         Hashtable opdDependencies, Element opd, Opd myOpd) throws Exception {

        try {
            Hashtable parentChildren = this
                    ._prepareParentChildrenDependencies(opdNum);

            if (opdNum != ROOT_OPD) {
                opd.addContent(this._saveMainEntity(myOpd.getMainInstance(),
                        opdNum, parentChildren));
            }

            opd.addContent(this._saveThingSection(opdNum, parentChildren));
            opd.addContent(this._saveFundamentalRelationSection(opdNum));
            opd.addContent(this._saveGeneralRelationSection(opdNum));
            opd.addContent(this._saveVisualLinkSection(opdNum));
            opd.addContent(this._saveUnfolded(opdNum, opdDependencies));
            opd.addContent(this._saveView(opdNum, opdDependencies));
            opd.addContent(this._saveInZoomed(opdNum, opdDependencies));

            return opd;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // MainEntity

    protected Element _saveMainEntity(ThingInstance mainInstance, long opdNum,
                                      Hashtable parentChildren) throws Exception {
        try {
            Element mainEntity = new Element("MainEntity");
            LinkedList rootList = (LinkedList) parentChildren.get(new Long(0));
            rootList.remove(mainInstance);
            mainEntity.addContent(this._saveVisualThing(mainInstance,
                    parentChildren));

            return mainEntity;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // ThingSection

    protected Element _saveThingSection(long opdNum, Hashtable parentChildren) throws Exception {
        try {
            Element thingSect = new Element("ThingSection");
            List thingList = new LinkedList();// thingSect.getVisualThing();

            LinkedList rootList = (LinkedList) parentChildren.get(new Long(0));

            for (Iterator it = rootList.iterator(); it.hasNext(); ) {
                ThingInstance instance = (ThingInstance) it.next();
                thingList.add(this._saveVisualThing(instance, parentChildren));
            }
            thingSect.addContent(thingList);
            return thingSect;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected Hashtable _prepareParentChildrenDependencies(long opdNum) throws Exception {
        try {
            Hashtable parentChildren = new Hashtable();
            LinkedList rootList = new LinkedList();
            parentChildren.put(new Long(0), rootList);

            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getThingsInOpd(opdNum); e.hasMoreElements(); ) {
                ThingInstance currIns = (ThingInstance) e.nextElement();
                java.awt.Container parent = currIns.getConnectionEdge().getParent();

                if (!(parent instanceof OpdThing)) {
                    rootList.add(currIns);
                } else {
                    OpdThing parentThing = (OpdThing) parent;
                    LinkedList tempList = (LinkedList) parentChildren.get(new Long(
                            parentThing.getEntityInOpdId()));
                    if (tempList == null) {
                        tempList = new LinkedList();
                        parentChildren.put(
                                new Long(parentThing.getEntityInOpdId()), tempList);
                    }
                    tempList.add(currIns);
                }
            }

            return parentChildren;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected Element _saveExportKey(OpcatExposeKey key) throws Exception {
        try {
            Element tData = new Element("ExportKey");
            if (key != null) {
                tData.setAttribute("modeluri", key.getModelEncodedURI());
                tData.setAttribute("opmentityid", Long.toString(key
                        .getOpmEntityID()));
                tData.setAttribute("exposeid", Long.toString(key.getId()));
                tData.setAttribute("private", Boolean.toString(key.isPrivate()));
            }

            return tData;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected Element _saveExposePublicChange(OpcatExposeChange change) throws Exception {
        return _saveExposeChange(change, "ExposePublicChange");
    }

    protected Element _saveExposePrivateChange(OpcatExposeChange change) throws Exception {
        return _saveExposeChange(change, "ExposePrivateChange");
    }

    private Element _saveExposeChange(OpcatExposeChange change, String name) throws Exception {
        try {
            if (change != null) {
                Element tData = new Element(name);

                if (change != null) {
                    tData.setAttribute("private", Boolean.toString(change
                            .isPrivateAction()));
                    tData.setAttribute("op", change.getOp().toString());
                }

                return tData;
            }

            return null;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // VisualThing

    protected Element _saveVisualThing(ThingInstance tInstance,
                                       Hashtable parentChildren) throws Exception {

        try {
            Element vThing = new Element("VisualThing");
            Element tData = new Element("ThingData");

            if (tInstance instanceof ProcessInstance) {
                tData.addContent(this
                        ._saveVisualProcess((ProcessInstance) tInstance));
            } else {
                tData
                        .addContent(this
                                ._saveVisualObject((ObjectInstance) tInstance));
            }

            vThing.addContent(tData);
            Element childrenCont = new Element("ChildrenContainer");
            vThing.addContent(childrenCont);

            LinkedList childrenList = (LinkedList) parentChildren.get(new Long(
                    tInstance.getKey().getEntityInOpdId()));

            if (childrenList != null) {
                List xmlChildrenList = new LinkedList();// childrenCont.getVisualThing();

                for (Iterator it = childrenList.iterator(); it.hasNext(); ) {
                    xmlChildrenList.add(this._saveVisualThing((ThingInstance) it
                            .next(), parentChildren));
                }
                childrenCont.addContent(xmlChildrenList);
            }

            return vThing;

        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // VisualProcess

    protected Element _saveVisualProcess(ProcessInstance proc) throws Exception {
        try {
            Element xmlProcess = new Element("VisualProcess");

            xmlProcess.addContent(this._saveInstanceAttr(proc));
            xmlProcess.addContent(this._saveConnectionEdgeAttr(proc));
            xmlProcess.addContent(this._saveThingInstanceAttr(proc));
            xmlProcess.addContent(_saveExportKey(proc.getPointer()));

            if (proc.isTemplateInstance())
                xmlProcess.setAttribute("istemplate", Boolean.toString(proc
                        .isTemplateInstance()));
            if (proc.isExposeOriginal())
                xmlProcess.setAttribute("originalexpose", Boolean.toString(proc
                        .isExposeOriginal()));
            if (proc.isAdvisor())
                xmlProcess.setAttribute("advisorinstance", Boolean.toString(proc
                        .isAdvisor()));

            return xmlProcess;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // VisualObject

    protected Element _saveVisualObject(ObjectInstance obj) throws Exception {
        try {
            Element xmlObject = new Element("VisualObject");

            xmlObject.addContent(this._saveInstanceAttr(obj));
            xmlObject.addContent(this._saveConnectionEdgeAttr(obj));
            xmlObject.addContent(this._saveThingInstanceAttr(obj));

            if (!obj.isStatesAutoArranged())
                xmlObject.setAttribute("statesAutoArranged", Boolean.toString(obj
                        .isStatesAutoArranged()));

            xmlObject.addContent(_saveExportKey(obj.getPointer()));

            if (obj.isExposeOriginal())
                xmlObject.setAttribute("originalexpose", Boolean.toString(obj
                        .isExposeOriginal()));

            if (obj.isTemplateInstance())
                xmlObject.setAttribute("istemplate", Boolean.toString(obj
                        .isTemplateInstance()));

            List statesList = new LinkedList();// xmlObject.getVisualState();
            for (Enumeration e = obj.getStateInstances(); e.hasMoreElements(); ) {
                statesList.add(this._saveVisualState((StateInstance) e
                        .nextElement()));
            }
            xmlObject.addContent(statesList);
            return xmlObject;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // VisualState

    protected Element _saveVisualState(StateInstance state) throws Exception {
        try {
            Element xmlState = new Element("VisualState");

            xmlState.addContent(this._saveInstanceAttr(state));
            xmlState.addContent(this._saveConnectionEdgeAttr(state));
            xmlState.setAttribute("visible", Boolean.toString(state.isVisible()));
            xmlState.addContent(_saveExportKey(state.getPointer()));
            xmlState.setAttribute("istemplate", Boolean.toString(state
                    .isTemplateInstance()));

            return xmlState;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // InstanceAttr

    protected Element _saveInstanceAttr(Instance ins) throws Exception {
        try {
            Element xmlInstance = new Element("InstanceAttr");
            xmlInstance.setAttribute("entityId", Long.toString(ins.getLogicalId()));
            xmlInstance.setAttribute("entityInOpdId", Long.toString(ins.getKey()
                    .getEntityInOpdId()));


            int backgroundColor = Configuration.getInstance().getColorFromProperty("graphics.default.BackgroundColor").getRGB();
            int defBorderColor = Configuration.getInstance().getColorFromProperty("graphics.default.BaseBorderColor").getRGB();
            int textColor = Configuration.getInstance().getColorFromProperty("graphics.default.TextColor").getRGB();

            if (ins instanceof ProcessInstance)
                defBorderColor = Configuration.getInstance().getColorFromProperty("graphics.default.ProcessColor").getRGB();

            if (ins instanceof ObjectInstance)
                defBorderColor = Configuration.getInstance().getColorFromProperty("graphics.default.ObjectColor").getRGB();

            if (ins instanceof StateInstance)
                defBorderColor = Configuration.getInstance().getColorFromProperty("graphics.default.StateColor").getRGB();

            if (ins instanceof LinkInstance) {
                defBorderColor = Configuration.getInstance().getColorFromProperty("graphics.default.LineColor").getRGB();
                textColor = Configuration.getInstance().getColorFromProperty("graphics.default.LineTextColor").getRGB();
            }


            if (backgroundColor != ins.getBackgroundColor().getRGB())
                xmlInstance.setAttribute("backgroundColor", Integer.toString(ins
                        .getBackgroundColor().getRGB()));

            if (defBorderColor != ins.getBorderColor().getRGB())
                xmlInstance.setAttribute("borderColor", Integer.toString(ins
                        .getBorderColor().getRGB()));

            if (textColor != ins
                    .getTextColor().getRGB())
                xmlInstance.setAttribute("textColor", Integer.toString(ins
                        .getTextColor().getRGB()));

            if (!ins.isVisible())
                xmlInstance
                        .setAttribute("visible", Boolean.toString(ins.isVisible()));


            xmlInstance
                    .setAttribute("uuid", ins.getUUID().toString());

            return xmlInstance;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // ConnectionEdgeAttr
    protected Element _saveConnectionEdgeAttr(ConnectionEdgeInstance cEdge) throws Exception {
        try {
            Element xmlConEdge = new Element("ConnectionEdgeAttr");

            xmlConEdge.setAttribute("x", Integer.toString(cEdge.getX()));

            xmlConEdge.setAttribute("y", Integer.toString(cEdge.getY()));

            xmlConEdge.setAttribute("width", Integer.toString(cEdge.getWidth()));

            xmlConEdge.setAttribute("height", Integer.toString(cEdge.getHeight()));

            if (cEdge.getGraphicalRepresentation().isSentToBAck())
                xmlConEdge.setAttribute("sentToBack", Boolean.toString(cEdge
                        .getGraphicalRepresentation().isSentToBAck()));

            return xmlConEdge;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // ThingInstanceAttr

    protected Element _saveThingInstanceAttr(ThingInstance tInstance) throws Exception {
        try {
            Element xmlThingIns = new Element("ThingInstanceAttr");

            //xmlThingIns.setAttribute("layer", Integer
            //        .toString(tInstance.getLayer()));
            xmlThingIns.setAttribute("textPosition", tInstance.getTextPosition());

            OpdThing opdthing = (OpdThing) tInstance.getGraphicalRepresentation();

            if (!opdthing.isIconVisible())
                xmlThingIns.setAttribute("showIcon", Boolean.toString(opdthing
                        .isIconVisible()));


            return xmlThingIns;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // FundamentalRelationSection

    protected Element _saveFundamentalRelationSection(long opdNum) throws Exception {
        try {
            Element fundamentalSect = new Element("FundamentalRelationSection");

            List relList = new LinkedList();

            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getGraphicalRepresentationsInOpd(opdNum); e.hasMoreElements(); ) {
                relList.add(this
                        ._saveCommonPart((GraphicalRelationRepresentation) e
                                .nextElement()));
            }
            fundamentalSect.addContent(relList);
            return fundamentalSect;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected Element _saveCommonPart(GraphicalRelationRepresentation rel) throws Exception {
        try {
            Element xmlRelation = new Element("CommonPart");
            OpdFundamentalRelation commonPart = rel.getRelation();

            xmlRelation.setAttribute("sourceId", Long.toString(rel.getSource()
                    .getEntity().getId()));
            xmlRelation.setAttribute("sourceInOpdId", Long.toString(rel.getSource()
                    .getEntityInOpdId()));
            xmlRelation.setAttribute("sourceConnectionSide", Integer.toString(rel
                    .getSourceDragger().getSide()));
            xmlRelation.setAttribute("sourceConnectionParameter", Float
                    .toString((float) rel.getSourceDragger().getParam()));
            xmlRelation.setAttribute("x", Integer.toString(commonPart.getX()));
            xmlRelation.setAttribute("y", Integer.toString(commonPart.getY()));
            xmlRelation.setAttribute("width", Integer.toString(commonPart
                    .getWidth()));
            xmlRelation.setAttribute("height", Integer.toString(commonPart
                    .getHeight()));


            int defBorderColor = Configuration.getInstance().getColorFromProperty("graphics.default.BaseBorderColor").getRGB();


            if (Configuration.getInstance().getColorFromProperty("graphics.default.BackgroundColor").getRGB() != commonPart
                    .getBackgroundColor().getRGB())
                xmlRelation.setAttribute("backgroundColor", Integer.toString(commonPart
                        .getBackgroundColor().getRGB()));

            if (defBorderColor != commonPart
                    .getBorderColor().getRGB())
                xmlRelation.setAttribute("borderColor", Integer.toString(commonPart
                        .getBorderColor().getRGB()));

            List insList = new LinkedList();

            for (Enumeration e = rel.getInstances(); e.hasMoreElements(); ) {
                insList
                        .add(this
                                ._saveVisualFundamentalRelation((FundamentalRelationInstance) e
                                        .nextElement()));
            }
            xmlRelation.addContent(insList);
            return xmlRelation;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // VisualFundamentalRelation

    protected Element _saveVisualFundamentalRelation(
            FundamentalRelationInstance rel) throws Exception {
        try {
            Element xmlRelation = new Element("VisualFundamentalRelation");

            xmlRelation.addContent(this._saveInstanceAttr(rel));

            xmlRelation.setAttribute("destinationId", Long.toString(rel
                    .getDestination().getEntity().getId()));
            xmlRelation.setAttribute("destinationInOpdId", Long.toString(rel
                    .getDestination().getEntityInOpdId()));
            xmlRelation.setAttribute("destinationSide", Integer.toString(rel
                    .getDestinationDragger().getSide()));
            xmlRelation.setAttribute("destinationParameter", Float
                    .toString((float) rel.getDestinationDragger().getParam()));

            xmlRelation.setAttribute("labelX", Integer.toString(rel
                    .getDestinationDragger().getOpdCardinalityLabel().getX()));

            xmlRelation.setAttribute("labelY", Integer.toString(rel
                    .getDestinationDragger().getOpdCardinalityLabel().getY()));

            xmlRelation
                    .setAttribute("visible", Boolean.toString(rel.isVisible()));

            return xmlRelation;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    // GeneralRelationSection

    protected Element _saveGeneralRelationSection(long opdNum) throws Exception {
        try {
            Element genRelSect = new Element("GeneralRelationSection");
            List relList = new LinkedList();

            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getGeneralRelationsInOpd(opdNum); e.hasMoreElements(); ) {
                relList.add(this
                        ._saveVisualGeneralRelation((GeneralRelationInstance) e
                                .nextElement()));
            }
            genRelSect.addContent(relList);
            return genRelSect;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // VisualGeneralRelation

    protected Element _saveVisualGeneralRelation(GeneralRelationInstance rel) throws Exception {
        try {
            Element xmlRelation = new Element("VisualGeneralRelation");
            xmlRelation.addContent(this._saveInstanceAttr(rel));
            xmlRelation.addContent(this._saveLineAttr(rel.getLine()));
            xmlRelation.setAttribute("sourceLabelX", Integer.toString(rel
                    .getSourceDragger().getOpdCardinalityLabel().getX()));
            xmlRelation.setAttribute("sourceLabelY", Integer.toString(rel
                    .getSourceDragger().getOpdCardinalityLabel().getY()));
            xmlRelation.setAttribute("destinationLabelX", Integer.toString(rel
                    .getDestinationDragger().getOpdCardinalityLabel().getX()));
            xmlRelation.setAttribute("destinationLabelY", Integer.toString(rel
                    .getDestinationDragger().getOpdCardinalityLabel().getY()));

            if (!rel.getLine().isStraight()) {
                xmlRelation.addContent(this._saveBreakPoints(rel.getLine()));
            }
            return xmlRelation;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected Element _saveLineAttr(StyledLine line) throws Exception {
        try {
            Element xmlLine = new Element("LineAttr");

            xmlLine.setAttribute("autoArranged", Boolean.toString(line
                    .isAutoArranged()));
            xmlLine.setAttribute("sourceId", Long.toString(line.getSource()
                    .getEntity().getId()));
            xmlLine.setAttribute("sourceInOpdId", Long.toString(line.getSource()
                    .getEntityInOpdId()));
            xmlLine.setAttribute("sourceConnectionSide", Integer.toString(line
                    .getSourceDragger().getSide()));
            xmlLine.setAttribute("sourceConnectionParameter", Float
                    .toString((float) line.getSourceDragger().getParam()));
            xmlLine.setAttribute("destinationId", Long.toString(line
                    .getDestination().getEntity().getId()));
            xmlLine.setAttribute("destinationInOpdId", Long.toString(line
                    .getDestination().getEntityInOpdId()));
            xmlLine.setAttribute("destinationConnectionSide", Integer.toString(line
                    .getDestinationDragger().getSide()));
            xmlLine.setAttribute("destinationConnectionParameter", Float
                    .toString((float) line.getDestinationDragger().getParam()));
            return xmlLine;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // BreakPoints

    protected Element _saveBreakPoints(StyledLine line) throws Exception {
        try {
            Element points = new Element("BreakPoints");

            List pointsList = new LinkedList();

            BaseGraphicComponent pointsArray[] = line.getPointsArray();

            for (int i = 0; i < pointsArray.length; i++) {
                Element xmlPoint = new Element("Point");
                xmlPoint.setAttribute("x", Integer.toString(pointsArray[i].getX()));
                xmlPoint.setAttribute("y", Integer.toString(pointsArray[i].getY()));
                pointsList.add(xmlPoint);
            }
            points.addContent(pointsList);
            return points;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // VisualLinkSection

    protected Element _saveVisualLinkSection(long opdNum) throws Exception {
        try {
            Element linkSect = new Element("VisualLinkSection");

            List linkList = new LinkedList();// linkSect.getVisualLink();

            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getLinksInOpd(opdNum); e.hasMoreElements(); ) {
                linkList.add(this._saveVisualLink((LinkInstance) e.nextElement()));
            }
            linkSect.addContent(linkList);
            this._saveOrXor(linkSect, opdNum);

            return linkSect;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // Gets VisualLinkSection

    protected void _saveOrXor(Element linkSect, long opdNum) throws Exception {
        try {
            Hashtable<OpdKey, LinkInstance> sourceGroups = new Hashtable<OpdKey, LinkInstance>();
            Hashtable<OpdKey, LinkInstance> destinationGroups = new Hashtable<OpdKey, LinkInstance>();

            for (Enumeration e = this.myProject.getComponentsStructure()
                    .getLinksInOpd(opdNum); e.hasMoreElements(); ) {
                LinkInstance lInstance = (LinkInstance) e.nextElement();
                if (lInstance.getSourceOr() != null) {
                    sourceGroups.put(lInstance.getKey(), lInstance);
                }

                if (lInstance.getDestOr() != null) {
                    destinationGroups.put(lInstance.getKey(), lInstance);
                }
            }

            List orList = new LinkedList();// linkSect.getOrXorGroup();

            while (!sourceGroups.isEmpty()) {
                Enumeration e = sourceGroups.elements();
                LinkInstance lInstance = (LinkInstance) e.nextElement();
                sourceGroups.remove(lInstance.getKey());
                OrInstance orIns = lInstance.getSourceOr();

                Element xmlOr = new Element("OrXorGroup");
                xmlOr.setAttribute("isSourceGroup", "true");
                List memberList = new LinkedList();// xmlOr.getMember();

                if (orIns.isOr()) {
                    xmlOr.setAttribute("type", Integer.toString(OR_TYPE));
                } else {
                    xmlOr.setAttribute("type", Integer.toString(XOR_TYPE));
                }

                for (Enumeration e2 = orIns.getInstances(); e2.hasMoreElements(); ) {
                    LinkInstance cLink = (LinkInstance) e2.nextElement();
                    Element xmlMember = new Element("Member");
                    xmlMember.setAttribute("memberId", Long.toString(cLink
                            .getEntry().getId()));
                    xmlMember.setAttribute("memberInOpdId", Long.toString(cLink
                            .getKey().getEntityInOpdId()));
                    memberList.add(xmlMember);
                    sourceGroups.remove(cLink.getKey());
                }
                xmlOr.addContent(memberList);
                orList.add(xmlOr);
            }

            while (!destinationGroups.isEmpty()) {
                Enumeration e = destinationGroups.elements();
                LinkInstance lInstance = (LinkInstance) e.nextElement();
                OrInstance orIns = lInstance.getDestOr();

                Element xmlOr = new Element("OrXorGroup");
                xmlOr.setAttribute("isSourceGroup", "false");
                List memberList = new LinkedList();// xmlOr.getMember();

                if (orIns.isOr()) {
                    xmlOr.setAttribute("type", Integer.toString(OR_TYPE));
                } else {
                    xmlOr.setAttribute("type", Integer.toString(XOR_TYPE));
                }

                for (Enumeration e2 = orIns.getInstances(); e2.hasMoreElements(); ) {
                    LinkInstance cLink = (LinkInstance) e2.nextElement();
                    Element xmlMember = new Element("Member");
                    xmlMember.setAttribute("memberId", Long.toString(cLink
                            .getEntry().getId()));
                    xmlMember.setAttribute("memberInOpdId", Long.toString(cLink
                            .getKey().getEntityInOpdId()));
                    memberList.add(xmlMember);
                    destinationGroups.remove(cLink.getKey());
                }
                xmlOr.addContent(memberList);
                orList.add(xmlOr);
            }
            linkSect.addContent(orList);
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }


    }

    // VisualLink

    protected Element _saveVisualLink(LinkInstance link) throws Exception {

        try {
            Element xmlLink = new Element("VisualLink");

            xmlLink.addContent(this._saveInstanceAttr(link));
            xmlLink.addContent(this._saveLineAttr(link.getLine()));

            if (!link.getLine().isStraight()) {
                xmlLink.addContent(this._saveBreakPoints(link.getLine()));
            }
            return xmlLink;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // Unfolded

    protected Element _saveUnfolded(long opdNum, Hashtable opdDependencies) throws Exception {
        try {
            Element unfolded = new Element("Unfolded");

            List xmlChildrenList = new LinkedList();// unfolded.getUnfoldingProperties();

            LinkedList children = (LinkedList) opdDependencies
                    .get(new Long(opdNum));

            if (children != null) {
                for (Iterator it = children.iterator(); it.hasNext(); ) {
                    Opd currOpd = (Opd) it.next();
                    if (currOpd.getParentOpd().getOpdId() == opdNum) {
                        ThingInstance parentInstance = this
                                ._getParentInstance(currOpd);
                        if (parentInstance != null) {
                            Element xmlOpd = new Element("UnfoldingProperties");
                            xmlOpd.setAttribute("entityId", Long
                                    .toString(parentInstance.getLogicalId()));
                            xmlOpd.setAttribute("entityInOpdId", Long
                                    .toString(parentInstance.getKey()
                                            .getEntityInOpdId()));
                            xmlOpd.addContent(this._saveOpd(currOpd.getOpdId(),
                                    opdDependencies));
                            xmlChildrenList.add(xmlOpd);
                        }

                    }
                }
            }

            unfolded.addContent(xmlChildrenList);
            return unfolded;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    protected Element _saveView(long opdNum, Hashtable opdDependencies) throws Exception {
        try {
            Element unfolded = new Element("View");

            List xmlChildrenList = new LinkedList();// unfolded.getUnfoldingProperties();

            LinkedList children = (LinkedList) opdDependencies
                    .get(new Long(opdNum));

            if (children != null) {
                for (Iterator it = children.iterator(); it.hasNext(); ) {
                    Opd currOpd = (Opd) it.next();
                    if (currOpd.getParentOpd().getOpdId() == opdNum) {
                        ThingInstance parentInstance = _getParentInstance(currOpd);
                        if (parentInstance != null) {
                            // is unfolding opd
                            continue;
                        }
                        if (currOpd.getMainEntry().getZoomedInOpd() == currOpd) {
                            // is in-zoom opd
                            continue;
                        }
                        parentInstance = currOpd.getMainInstance();

                        if (parentInstance != null) {
                            Element xmlOpd = new Element("ViewProperties");
                            xmlOpd.setAttribute("entityId", Long
                                    .toString(parentInstance.getLogicalId()));
                            xmlOpd.setAttribute("entityInOpdId", Long
                                    .toString(parentInstance.getKey()
                                            .getEntityInOpdId()));
                            xmlOpd.addContent(this._saveOpd(currOpd.getOpdId(),
                                    opdDependencies));
                            xmlChildrenList.add(xmlOpd);
                        }

                    }
                }
            }

            unfolded.addContent(xmlChildrenList);
            return unfolded;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    protected ThingInstance _getParentInstance(Opd child) throws Exception {
        try {
            ThingEntry mainEntry = child.getMainEntry();
            for (Enumeration e = mainEntry.getInstances(false); e.hasMoreElements(); ) {

                ThingInstance parentInstance = (ThingInstance) e.nextElement();
                Opd unfoldingOpd = parentInstance.getUnfoldingOpd();
                if ((unfoldingOpd != null)
                        && (unfoldingOpd.getOpdId() == child.getOpdId())) {
                    return parentInstance;
                }
            }

            return null;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

    // InZoomed

    protected Element _saveInZoomed(long opdNum, Hashtable opdDependencies) throws Exception {
        try {
            Element inZoomed = new Element("InZoomed");
            List xmlChildrenList = new LinkedList();// inZoomed.getOPD();

            LinkedList children = (LinkedList) opdDependencies
                    .get(new Long(opdNum));

            if (children != null) {
                for (Iterator it = children.iterator(); it.hasNext(); ) {
                    Opd currOpd = (Opd) it.next();
                    if (currOpd.getMainEntry().getZoomedInOpd() == currOpd) {
                        xmlChildrenList.add(this._saveOpd(currOpd.getOpdId(),
                                opdDependencies));
                    }
                }
            }
            inZoomed.addContent(xmlChildrenList);
            return inZoomed;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }

    }

    protected Hashtable _prepareOpdsDependencies() throws Exception {
        try {
            Hashtable OpdsDep = new Hashtable();

            for (Enumeration e = this.myProject.getComponentsStructure().getOpds(); e
                    .hasMoreElements(); ) {
                Opd currOpd = (Opd) e.nextElement();
                Opd parentOpd = currOpd.getParentOpd();
                long parentNum;

                if (parentOpd == null) {
                    parentNum = 0;
                } else {
                    parentNum = parentOpd.getOpdId();
                }

                LinkedList tempList = (LinkedList) OpdsDep.get(new Long(parentNum));
                if (tempList == null) {
                    tempList = new LinkedList();
                    OpdsDep.put(new Long(parentNum), tempList);
                }
                tempList.add(currOpd);

            }

            return OpdsDep;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
            throw ex;
        }
    }

}