package gui.opx.ver3;

import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.MetaManager;
import gui.metaLibraries.logic.Types;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.Constants;
import gui.opmEntities.OpmConnectionEdge;
import gui.opmEntities.OpmFundamentalRelation;
import gui.opmEntities.OpmGeneralRelation;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmState;
import gui.opmEntities.OpmStructuralRelation;
import gui.opx.LoadException;
import gui.opx.ver2.LoaderVer2;
import gui.projectStructure.FundamentalRelationEntry;
import gui.projectStructure.GeneralRelationEntry;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.RelationEntry;
import gui.projectStructure.RoleEntry;
import gui.projectStructure.StateEntry;
import gui.projectStructure.ThingEntry;
import gui.util.OpcatLogger;

import java.util.Iterator;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JProgressBar;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 * @author Eran Toch The 3rd version of the OPX loader. Adds the following
 *         features to the 2nd OPX loader version:
 *         <p>
 *         1. Meta-Libraries loading. <br>
 *         2. Roles loading. <br>
 */
public class LoaderVer3 extends LoaderVer2 {
	public String CURRENT_VERSION;

	public LoaderVer3() {
		this.CURRENT_VERSION = "3";
	}

	public String getVersion() {
		return this.CURRENT_VERSION;
	}

	/**
	 * Overloads the load method of version 2. Currently, does not provide any
	 * functionality.
	 * 
	 */
	public OpdProject load(JDesktopPane parentDesktop, Document document,
			JProgressBar progressBar) throws LoadException {
		try {
			return super.load(parentDesktop, document, progressBar);
		} catch (LoadException lex) {
			throw lex;
		}
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
		} catch (LoadException lex) {
			throw lex;
		}
	}

	/**
	 * Extends the <code>SystemProperties</code> loading section, augmenting
	 * it with <b>MetaLibraries </b> support.
	 */
	protected void _loadSystemProperties(Element props) throws JDOMException {
		try {
			super._loadSystemProperties(props);
			Element metaSection = props.getChild("MetaLibraries");
			if (metaSection != null) {
				List metaLibs = metaSection.getChildren("LibraryReference");
				if (metaLibs != null) {
					this._loadMetaLibraries(metaLibs, this.myProject.getMetaManager());
				}
			}
		} catch (JDOMException ex) {
			throw ex;
		}
	}

	/**
	 * Gets the meta-libraries from the XML file and adds them to the
	 * <code>MetaManager</code>.
	 * 
	 * @param metaLibs
	 * @param man
	 */
	protected void _loadMetaLibraries(List metaLibs, MetaManager man) {
		for (Iterator i = metaLibs.iterator(); i.hasNext();) {
			Element prop = (Element) i.next();
			String libID = prop.getAttributeValue("libID");
			String type = prop.getAttributeValue("libType");
			String dataType = prop.getAttributeValue("libDataType");
			String globalID = "";
			if (prop.getAttribute("libGlobalID") != null) {
				globalID = prop.getAttributeValue("libGlobalID");
			}
			String path = prop.getChildText("LibPath");
			int typeI = Integer.parseInt(type);
			long id = Long.parseLong(libID);
			MetaLibrary lib = new MetaLibrary(id, path, typeI, globalID);
			if (dataType == null ) {
				lib.setType(Types.MetaLibrary) ; //support old versions of the meta library infra
			} else {
				lib.setType(Integer.parseInt(dataType)) ; 
			} 
			
			try {
				man.addMetaLibrary(lib);
			} catch (MetaException ex) {
				OpcatLogger.logError("Error loading meta libraries: \n"
						+ ex.getMessage());
				OpcatLogger.logError(ex);
			}
		}
	}

	/**
	 * Loads the <code>Thing</code> attributes, using the previous OPX
	 * version, plus <code>Role</code> loading.
	 */
	protected void _loadThingAttr(ThingEntry thing, Element xmlThing)
			throws JDOMException {
		super._loadThingAttr(thing, xmlThing);
		Element rolesSection = xmlThing.getChild("Roles");
		if (rolesSection != null) {
			List roles = rolesSection.getChildren("OPMRole");
			if (roles != null) {
				for (Iterator i = roles.iterator(); i.hasNext();) {
					Element xmlRole = (Element) i.next();
					String roleID = xmlRole.getAttributeValue("roleID");
					String libID = xmlRole.getAttributeValue("libID");
					long roleIDNum = Long.parseLong(roleID);
					long libIDNum = Long.parseLong(libID);
					RoleEntry role = new RoleEntry(roleIDNum, libIDNum);
					thing.addRole(role);
				}
			}
		}
	}

	/**
	 * Marks whether the opd is locked, and sets the path to a referenced system
	 * if the opd is "open reused"
	 */
	protected Opd _loadOpd(Element xmlOpd, Opd parentOpd,
			int relationsWithParent) throws JDOMException {
		Opd myOpd = super._loadOpd(xmlOpd, parentOpd, relationsWithParent);

		// reuse comment
		if (xmlOpd.getAttribute("locked") != null) {
			if (xmlOpd.getAttributeValue("locked").equals("true")) {
				myOpd.setLocked(true);
			}
		}

		if (xmlOpd.getAttribute("reusePath") != null) {
			myOpd.setOpenReused(true);
			myOpd.setReusedSystemPath(xmlOpd.getAttributeValue("reusePath"));
			this._loadOpenReuseRelations(xmlOpd, myOpd);
		} else {
			myOpd.setOpenReused(false);
			myOpd.setReusedSystemPath(null);
		}

		// end reuse comment

		return myOpd;
	}

	// LogicalObject
	protected ObjectEntry _loadLogicalObject(Element xmlObject)
			throws JDOMException {
			long objectId = xmlObject.getChild("EntityAttr").getAttribute("id")
					.getLongValue();
			ObjectEntry myObject = new ObjectEntry(new OpmObject(objectId, ""),
					this.myProject);
			this._loadEntityAttr(myObject, xmlObject.getChild("EntityAttr"));
			this._loadThingAttr(myObject, xmlObject.getChild("ThingAttr"));
			myObject.setIndexName(xmlObject.getAttributeValue("indexName"));
			myObject.setIndexOrder(xmlObject.getAttribute("indexOrder")
					.getIntValue());
			myObject.setInitialValue(xmlObject
					.getAttributeValue("initialValue"));
			myObject.setKey(xmlObject.getAttribute("key").getBooleanValue());
			myObject.setPersistent(xmlObject.getAttribute("persistent")
					.getBooleanValue());
			myObject.setType(xmlObject.getAttributeValue("type"));
			if (xmlObject.getAttribute("typeOriginId") != null) {
				myObject.setTypeOriginId(xmlObject.getAttribute("typeOriginId")
						.getLongValue());
			}
			// reuse comment
			if (xmlObject.getAttribute("locked") != null) {
				if (xmlObject.getAttributeValue("locked").equals("true")) {
					myObject.commitLock();
				}
			}
			// end reuse comment
			this.myProject.getComponentsStructure().put(objectId, myObject);
			return myObject;
	}

	// LogicalProcess
	protected void _loadLogicalProcess(Element xmlProcess) throws JDOMException {

			long processId = xmlProcess.getChild("EntityAttr").getAttribute(
					"id").getLongValue();

			ProcessEntry myProcess = new ProcessEntry(new OpmProcess(processId,
					""), this.myProject);

			this._loadEntityAttr(myProcess, xmlProcess.getChild("EntityAttr"));
			this._loadThingAttr(myProcess, xmlProcess.getChild("ThingAttr"));

			myProcess.setMaxTimeActivation(xmlProcess
					.getAttributeValue("maxTimeActivation"));
			myProcess.setMinTimeActivation(xmlProcess
					.getAttributeValue("minTimeActivation"));
			myProcess.setProcessBody(xmlProcess.getChildText("processBody"));
			// reuse comment
			if (xmlProcess.getAttribute("locked") != null) {
				if (xmlProcess.getAttributeValue("locked").equals("true")) {
					myProcess.commitLock();
				}
			}
			// end reuse comment
			this.myProject.getComponentsStructure().put(processId, myProcess);

	}

	// LogicalRelation
	protected void _loadLogicalRelation(Element xmlRelation)

	throws JDOMException {

			long relId = xmlRelation.getChild("EntityAttr").getAttribute("id")
					.getLongValue();
			OpmConnectionEdge source = (OpmConnectionEdge) this.myProject
					.getComponentsStructure()
					.getEntry(
							xmlRelation.getAttribute("sourceId").getLongValue())
					.getLogicalEntity();
			OpmConnectionEdge destination = (OpmConnectionEdge) this.myProject
					.getComponentsStructure().getEntry(
							xmlRelation.getAttribute("destinationId")
									.getLongValue()).getLogicalEntity();

			OpmStructuralRelation opmRel = Constants.createRelation(xmlRelation
					.getAttribute("relationType").getIntValue(), relId, source,
					destination);
			RelationEntry myRelation;

			if (opmRel instanceof OpmFundamentalRelation) {
				myRelation = new FundamentalRelationEntry(
						(OpmFundamentalRelation) opmRel, this.myProject);
			} else {
				myRelation = new GeneralRelationEntry(
						(OpmGeneralRelation) opmRel, this.myProject);
			}

			this._loadEntityAttr(myRelation, xmlRelation.getChild("EntityAttr"));

			myRelation.setSourceCardinality(xmlRelation
					.getAttributeValue("sourceCardinality"));
			myRelation.setDestinationCardinality(xmlRelation
					.getAttributeValue("destinationCardinality"));
			myRelation.setForwardRelationMeaning(xmlRelation
					.getAttributeValue("forwardRelationMeaning"));
			myRelation.setBackwardRelationMeaning(xmlRelation
					.getAttributeValue("backwardRelationMeaning"));
			// reuse comment
			if (xmlRelation.getAttribute("locked") != null) {
				if (xmlRelation.getAttributeValue("locked").equals("true")) {
					myRelation.commitLock();
				}
			}
			// end reuse comment

			this.myProject.getComponentsStructure().put(relId, myRelation);
	}

	// LogicalState
	protected void _loadLogicalStates(Element xmlState, ObjectEntry parentEntry)
			throws JDOMException {
		long stateId = xmlState.getChild("EntityAttr").getAttribute("id")
				.getLongValue();
		StateEntry myState = new StateEntry(new OpmState(stateId, ""),
				(OpmObject) parentEntry.getLogicalEntity(), this.myProject);

		this._loadEntityAttr(myState, xmlState.getChild("EntityAttr"));

		myState.setDefault(xmlState.getAttribute("default").getBooleanValue());
		myState.setFinal(xmlState.getAttribute("final").getBooleanValue());
		myState.setInitial(xmlState.getAttribute("initial").getBooleanValue());
		myState.setMaxTime(xmlState.getAttributeValue("maxTime"));
		myState.setMinTime(xmlState.getAttributeValue("minTime"));
		// reuse comment
		if (xmlState.getAttribute("locked") != null) {
			if (xmlState.getAttributeValue("locked").equals("true")) {
				myState.commitLock();
			}
		}
		// end reuse comment

		this.myProject.getComponentsStructure().put(stateId, myState);
		parentEntry.addState((OpmState) myState.getLogicalEntity());
	}

	protected void _loadLogicalLink(Element xmlLink) throws JDOMException {
		long linkId = xmlLink.getChild("EntityAttr").getAttribute("id")
				.getLongValue();

		OpmProceduralLink opmLink = Constants.createLink(xmlLink.getAttribute(
				"linkType").getIntValue(), linkId, xmlLink.getAttribute(
				"sourceId").getLongValue(), xmlLink.getAttribute(
				"destinationId").getLongValue());

		LinkEntry myLink = new LinkEntry(opmLink, this.myProject);

		this._loadEntityAttr(myLink, xmlLink.getChild("EntityAttr"));

		myLink.setCondition(xmlLink.getAttributeValue("condition"));
		myLink.setPath(xmlLink.getAttributeValue("path"));
		myLink.setMaxReactionTime(xmlLink.getAttributeValue("maxReactionTime"));
		myLink.setMinReactionTime(xmlLink.getAttributeValue("minReactionTime"));
		// reuse comment
		if (xmlLink.getAttribute("locked") != null) {
			if (xmlLink.getAttributeValue("locked").equals("true")) {
				myLink.commitLock();
			}
		}
		// end reuse comment

		this.myProject.getComponentsStructure().put(linkId, myLink);
	}

	// reuse comment
	protected void _loadOpenReuseRelations(Element xmlOpd, Opd myOpd) {

		Element xmlOpenReuseRelationsSection = xmlOpd
				.getChild("openReuseRelations");
		List relations = xmlOpenReuseRelationsSection
				.getChildren("openReuseRelation");
		Iterator it = relations.iterator();
		while (it.hasNext()) {
			Element xmlRel = (Element) it.next();
			myOpd.inhertRelationsNames.put(xmlRel.getAttributeValue("source"),
					xmlRel.getAttributeValue("target"));
		}

	}
	// end reuse comment

}
