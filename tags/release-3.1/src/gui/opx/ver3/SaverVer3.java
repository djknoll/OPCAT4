/*
 * Created on 15/05/2004
 */
package gui.opx.ver3;

import gui.metaDataProject.MetaDataProject;
import gui.metaDataProject.MetaSourceType;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.MetaManager;
import gui.metaLibraries.logic.LibraryTypes;
import gui.opdProject.Opd;
import gui.opmEntities.OpmObject;
import gui.opx.ver2.SaverVer2;
import gui.projectStructure.Entry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GraphicalRelationRepresentation;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.RelationEntry;
import gui.projectStructure.RoleEntry;
import gui.projectStructure.StateEntry;
import gui.scenarios.Scenario;
import gui.scenarios.Scenarios;
import gui.util.OpcatFile;
import gui.util.OpcatLogger;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;

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
	protected void _saveSystemAttributes(Element system) {
		super._saveSystemAttributes(system);
		system.setAttribute("global-id", this.myProject.getGlobalID());
		saveTestingScen(myProject.getScen(), system);
		// saveOpdMeteSection(system);
	}

	protected void saveTestingScen(Scenarios scenrios, Element system) {

		Element scenSection = new Element("Scenarios");

		// a list of all the scenrios
		Iterator keys = scenrios.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			Scenario scen = (Scenario) scenrios.get(key);
			Element scenElem = new Element("Scenario");
			scenElem.setAttribute("ScenName", key);

			try {
				Element meteSection = _saveMetaSection(scen.getSettings());
				if (meteSection != null)
					scenElem.addContent(meteSection);
			} catch (Exception ex) {
				OpcatLogger.logError(ex);
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
			//if (typesInitial != null)
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
				//if (pair != null)
					typesFinal.addContent(pair);
			}
			//if (typesFinal != null)
				scenElem.addContent(typesFinal);

			//if (scenElem != null)
				scenSection.addContent(scenElem);
		}
		system.addContent(scenSection);
	}

	/**
	 * Extends the 2nd version of the <code>_saveSystemProperties</code>
	 * saver, adding meta-librareis saving support.
	 * 
	 * @return The <code>SystemProperties</code> XML element.
	 */
	protected Element _saveSystemProperties() throws JDOMException {
		Element props = super._saveSystemProperties();
		Element meteSection = this._saveMetaSection(this.myProject
				.getMetaManager());

		if (meteSection != null) {
			props.addContent(meteSection);
		}

		return props;
	}

	protected void saveOpdMeteSection(Element system) {

		MetaManager opdMetaManager = new MetaManager();
		Enumeration opdEnum = myProject.getSystemStructure().getOpds();
		while (opdEnum.hasMoreElements()) {
			Opd opd = (Opd) opdEnum.nextElement();
			Object[] source;
			source = new Object[2];
			source[0] = myProject;
			source[1] = new Long(opd.getOpdId());
			MetaDataProject metaData = new MetaDataProject(source,
					new MetaSourceType(MetaSourceType.OPCAT_OPD));
			MetaLibrary meta = new MetaLibrary(metaData);
			try {
				opdMetaManager.addMetaLibrary(meta);
			} catch (Exception ex) {
				OpcatLogger.logError(ex);
			}
		}

		Element opdMeteSection = new Element("OPDMeteSection");

		try {
			Element meteSection = _saveMetaSection(opdMetaManager);
			if (meteSection != null) {
				opdMeteSection.addContent(meteSection);
			}
		} catch (Exception ex) {
			OpcatLogger.logError(ex);
		}
		system.addContent(opdMeteSection);

	}

	/**
	 * Saves the meta-libraries to the XML file.
	 * 
	 * @throws JDOMException
	 */
	protected Element _saveMetaSection(MetaManager man) throws JDOMException {
		if (man.size() == 0) {
			return null;
		}
		Element metaSection = new Element("MetaLibraries");
		MetaLibrary lib;
		Enumeration libs = man.getMetaLibraries();
		while (libs.hasMoreElements()) {
			lib = (MetaLibrary) libs.nextElement();
			Element xmlLib = new Element("LibraryReference");
			xmlLib.setAttribute("libID", Long.toString(lib.getReferenceID()));
			xmlLib.setAttribute("libType", Integer.toString(lib
					.getReferenceType()));
			xmlLib.setAttribute("isPolicy", Boolean.toString(lib
				.isPolicyLibrary()));
			xmlLib.setAttribute("libGlobalID", lib.getGlobalID());
			if (lib.getType() == 0) {
				xmlLib.setAttribute("libDataType", Integer
						.toString(LibraryTypes.MetaLibrary));
			} else {
				xmlLib.setAttribute("libDataType", Integer.toString(lib
						.getType()));
			}
			Element xmlPath = new Element("LibPath");
			// System.out.println(getRealativePath(myProject.getFileName(), lib
			// .getPath()));
			// xmlPath.setText(lib.getPath());
			xmlPath.setText(OpcatFile.getRealativePath(myProject.getFileName(), lib
					.getPath()));
			xmlLib.addContent(xmlPath);

			metaSection.addContent(xmlLib);
		}
		return metaSection;
	}

	/**
	 * Extends the <code>SaverVer2._saveThingAttr</code> with roles and url support.
	 * 
	 * @return the <code>Roles</code> XML element.
	 */

	protected Element _saveEntityAttr(Entry entity) throws JDOMException {

		Element xmlEntity = super._saveEntityAttr(entity);
		Enumeration roles = entity.getRolesEntries();
		if (roles.hasMoreElements()) {
			Element rolesSection = new Element("Roles");
			while (roles.hasMoreElements()) {
				RoleEntry roleEntry = (RoleEntry) roles.nextElement();
				// if ((roleEntry.getLogicalRole().isLoaded()) ||
				// (roleEntry.getLogicalRole().getOntology().getType() ==
				// LibraryTypes.MetaData) ) {
				Element opmRole = new Element("OPMRole");
				if (roleEntry != null) {
					opmRole.setAttribute("roleID", Long.toString(roleEntry
							.getThingId()));
					opmRole.setAttribute("libID", Long.toString(roleEntry
							.getLibraryId()));
					rolesSection.addContent(opmRole);
				}
				// }
			}
			xmlEntity.addContent(rolesSection);
		}
		
		Element url = new Element("url");
		if (entity.hasURL()) {
		    String str = entity.getUrl() ; 
		    if(str.substring(0, 8).equalsIgnoreCase("file:///")) {
			str = OpcatFile.getRealativePath(myProject.getFileName(), str.substring(8)) ; 
			str = "file:///" + str ; 
		    } 
		    url.addContent(str);
		} else {
		    url.addContent("none");
		}

		xmlEntity.addContent(url);		
		return xmlEntity;
	}

	/**
	 * Overloads the <code>_appendOpdPropetries</code> method, aadding support
	 * to reuse properties. The properties include <code>locked</code> and
	 * <code>reusePath</code>.
	 */
	protected Element _appendOpdPropetries(Element opd, Opd myOpd)
			throws JDOMException {
		opd = super._appendOpdPropetries(opd, myOpd);
		// reuse comment
		opd.setAttribute("locked", Boolean.toString(myOpd.isLocked()));
		if (myOpd.getReusedSystemPath() != null) {
			opd.setAttribute("reusePath", myOpd.getReusedSystemPath());
		}
		return opd;
	}

	/**
	 * Overloads the <code>_appendOpdChildren</code>, adding support to reuse
	 * properties, such as <code>penReuseRelations</code>.
	 * 
	 * @see #_saveOpenReuseRelations
	 */
	protected Element _appendOpdChildren(long opdNum,
			Hashtable opdDependencies, Element opd, Opd myOpd)
			throws JDOMException {
		opd = super._appendOpdChildren(opdNum, opdDependencies, opd, myOpd);
		// reuse comment
		opd.addContent(this._saveOpenReuseRelations(myOpd));
		// end reuse comment

		return opd;
	}

	protected void _saveLogicalObject(ObjectEntry objEntry, Element obj)
			throws JDOMException {
		Element xmlObject = new Element("LogicalObject");
		xmlObject.addContent(this._saveEntityAttr(objEntry));
		xmlObject.addContent(this._saveThingAttr(objEntry));

		xmlObject.setAttribute("indexName", objEntry.getIndexName());
		xmlObject.setAttribute("indexOrder", Integer.toString(objEntry
				.getIndexOrder()));
		xmlObject.setAttribute("initialValue", objEntry.getInitialValue());
		xmlObject.setAttribute("key", Boolean.toString(objEntry.isKey()));
		xmlObject.setAttribute("persistent", Boolean.toString(objEntry
				.isPersistent()));
		xmlObject.setAttribute("type", objEntry.getType());
		xmlObject.setAttribute("typeOriginId", Long.toString(objEntry
				.getTypeOriginId()));
		OpmObject opm = (OpmObject) objEntry.getLogicalEntity() ; 
		
		xmlObject.setAttribute("measurmentUnit", opm.getMesurementUnit());
		xmlObject.setAttribute("measurmentUnitInitialValue", Double.toString(opm.getMesurementUnitInitialValue()));
		xmlObject.setAttribute("measurmentUnitMinValue", Double.toString(opm.getMesurementUnitMinValue()));
		xmlObject.setAttribute("measurmentUnitMaxValue", Double.toString(opm.getMesurementUnitMaxValue()));
		
		// reuse comment
		xmlObject.setAttribute("locked", Boolean.toString(objEntry.isLocked()));
		// end reuse comment
		this._saveLogicalStates(xmlObject, objEntry);
		if (obj.getChildren("LogicalObject") != null) {
			obj.getChildren("LogicalObject").add(xmlObject);
		} else {
			obj.addContent(xmlObject);
		}
	}

	protected void _saveLogicalProcess(ProcessEntry procEntry,
			Element processSect) throws JDOMException {
		Element xmlProcess = new Element("LogicalProcess");
		xmlProcess.addContent(this._saveEntityAttr(procEntry));
		xmlProcess.addContent(this._saveThingAttr(procEntry));
		xmlProcess.setAttribute("maxTimeActivation", procEntry
				.getMaxTimeActivation());
		xmlProcess.setAttribute("minTimeActivation", procEntry
				.getMinTimeActivation());
		Element procBody = new Element("processBody");
		procBody.addContent(procEntry.getProcessBody());
		xmlProcess.addContent(procBody);
		// reuse comment
		xmlProcess.setAttribute("locked", Boolean
				.toString(procEntry.isLocked()));
		// end reuse comment

		if (processSect.getChildren("LogicalProcess") != null) {
			processSect.getChildren("LogicalProcess").add(xmlProcess);
		} else {
			processSect.addContent(xmlProcess);
		}
	}

	protected void _saveLogicalRelation(RelationEntry relEntry,
			Element relationSect) throws JDOMException {
		Element xmlRelation = new Element("LogicalRelation");
		xmlRelation.addContent(this._saveEntityAttr(relEntry));
		xmlRelation.setAttribute("relationType", Integer.toString(relEntry
				.getRelationType()));
		xmlRelation.setAttribute("sourceId", Long.toString(relEntry
				.getSourceId()));
		xmlRelation.setAttribute("destinationId", Long.toString(relEntry
				.getDestinationId()));
		xmlRelation.setAttribute("sourceCardinality", relEntry
				.getSourceCardinality());
		xmlRelation.setAttribute("destinationCardinality", relEntry
				.getDestinationCardinality());
		xmlRelation.setAttribute("forwardRelationMeaning", relEntry
				.getForwardRelationMeaning());
		xmlRelation.setAttribute("backwardRelationMeaning", relEntry
				.getBackwardRelationMeaning());
		// reuse comment
		xmlRelation.setAttribute("locked", Boolean
				.toString(relEntry.isLocked()));
		// end reuse comment
		if (relationSect.getChildren("LogicalRelation") != null) {
			relationSect.getChildren("LogicalRelation").add(xmlRelation);
		} else {
			relationSect.addContent(xmlRelation);
		}
	}

	protected void _saveLogicalLink(LinkEntry lEntry, Element link)
			throws JDOMException {
		Element xmlLink = new Element("LogicalLink");

		xmlLink.addContent(this._saveEntityAttr(lEntry));
		xmlLink
				.setAttribute("linkType", Integer
						.toString(lEntry.getLinkType()));
		xmlLink.setAttribute("sourceId", Long.toString(lEntry.getSourceId()));
		xmlLink.setAttribute("destinationId", Long.toString(lEntry
				.getDestinationId()));
		xmlLink.setAttribute("condition", lEntry.getCondition());
		xmlLink.setAttribute("path", lEntry.getPath());
		xmlLink.setAttribute("maxReactionTime", lEntry.getMaxReactionTime());
		xmlLink.setAttribute("minReactionTime", lEntry.getMinReactionTime());
		xmlLink.setAttribute("resourceConsumption", String.valueOf(lEntry.getResourceConsumption()));
		xmlLink.setAttribute("resourceConsumptionAccumolated", String.valueOf(lEntry.isResourceConsumptionAccumolated()));
		// reuse comment
		xmlLink.setAttribute("locked", Boolean.toString(lEntry.isLocked()));
		// end reuse comment

		if (link.getChildren("LogicalLink") != null) {
			link.getChildren("LogicalLink").add(xmlLink);
		} else {
			link.addContent(xmlLink);
		}
	}

	protected void _saveLogicalStates(Element obj, ObjectEntry parentEntry)
			throws JDOMException {
		List statesList = new LinkedList();
		for (Enumeration e = parentEntry.getStates(); e.hasMoreElements();) {
			StateEntry state = (StateEntry) e.nextElement();

			Element xmlState = new Element("LogicalState");
			xmlState.addContent(this._saveEntityAttr(state));
			xmlState.setAttribute("default", Boolean
					.toString(state.isDefault()));
			xmlState.setAttribute("final", Boolean.toString(state.isFinal()));
			// xmlState.setFinal(state.isFinal());
			xmlState.setAttribute("initial", Boolean
					.toString(state.isInitial()));
			// xmlState.setInitial(state.isInitial());
			xmlState.setAttribute("maxTime", state.getMaxTime());
			xmlState.setAttribute("minTime", state.getMinTime());
			// reuse comment
			xmlState.setAttribute("locked", Boolean.toString(state.isLocked()));
			// end reuse comment
			statesList.add(xmlState);
		}
		obj.addContent(statesList);
	}

	// reuse comment
	protected Element _saveOpenReuseRelations(Opd opd) {
		Element xmlOpenReuseRelationsSection = new Element("openReuseRelations");
		Enumeration en = opd.inhertRelationsOverReusedThings.keys();
		while (en.hasMoreElements()) {
			Element xmlOpenReuseRelation = new Element("openReuseRelation");
			Instance source = (Instance) en.nextElement();
			Instance target = (Instance) (opd.inhertRelationsOverReusedThings
					.get(source));
			xmlOpenReuseRelation.setAttribute("source", source.getEntry()
					.getName());
			xmlOpenReuseRelation.setAttribute("target", target.getEntry()
					.getName());
			xmlOpenReuseRelationsSection.addContent(xmlOpenReuseRelation);
		}

		return xmlOpenReuseRelationsSection;
	}

	// end reuse comment

	protected Element _saveFundamentalRelationSection(long opdNum)
			throws JDOMException {
		Element fundamentalSect = new Element("FundamentalRelationSection");

		List relList = new LinkedList();

		for (Enumeration e = this.myProject.getComponentsStructure()
				.getGraphicalRepresentationsInOpd(opdNum); e.hasMoreElements();) {
			GraphicalRelationRepresentation rel = (GraphicalRelationRepresentation) e
					.nextElement();
			Enumeration en = rel.getInstances();
			if (en.hasMoreElements()) {
				if (((FundamentalRelationInstance) en.nextElement()).getEntry()
						.isOpenReused()) {
					continue;
				}
			}
			relList.add(this._saveCommonPart(rel));
		}
		fundamentalSect.addContent(relList);
		return fundamentalSect;
	}

	protected Hashtable _prepareOpdsDependencies() {
		Hashtable OpdsDep = new Hashtable();

		for (Enumeration e = this.myProject.getComponentsStructure().getOpds(); e
				.hasMoreElements();) {
			Opd currOpd = (Opd) e.nextElement();
			Opd parentOpd = currOpd.getParentOpd();
			long parentNum;

			// reuse comment
			//
			if (currOpd.isOpenReused()
					&& (currOpd.getReusedSystemPath() == null)) {
				continue;
			}

			// reuse comment

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
	}

}