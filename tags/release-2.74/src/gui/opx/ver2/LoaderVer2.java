package gui.opx.ver2;

import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.draggers.OpdRelationDragger;
import gui.opdGraphics.lines.StyledLine;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdFundamentalRelation;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdProject.GenericTable;
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
import gui.opx.LoaderI;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.Entry;
import gui.projectStructure.FundamentalRelationEntry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationEntry;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.GraphicalRelationRepresentation;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.OrInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.RelationEntry;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.util.OpcatLogger;

import java.awt.Color;
import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

//import javax.xdml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;

public class LoaderVer2 implements LoaderI
{
public String CURRENT_VERSION;
	protected final static int ROOT_OPD = 1;
  protected final static int OR_TYPE = 0;
  protected final static int XOR_TYPE = 1;

  protected final static int NO_PARENT = 0;
  protected final static int UNFOLDED = 1;
  protected final static int INZOOMED = 2;
  
  protected int numOfOpds = 0;
  protected OpdProject myProject;
  protected JProgressBar pBar;

  public LoaderVer2()
  {
	CURRENT_VERSION = "2";
  }
  
  public String getVersion()
  {
	return CURRENT_VERSION;
  }
/*
 * TODO: there is no need to pass the graphic objects like the desktop to the load function. 
 * the file funcctions shoud fucos on file ops only and not set any graphics objects. 
 * raanan
 */
  public OpdProject load(JDesktopPane parentDesktop, Document document,JProgressBar progressBar) throws LoadException
  {
	try
	{
	  pBar = progressBar;
	  if (pBar != null)	{
	  	  pBar.setStringPainted(true);
	  }
	  Element rootTag = document.getRootElement();
	  _prepareProgressBar(rootTag);

	  myProject = new OpdProject(parentDesktop, 1);
	  Element system = rootTag.getChild("OPMSystem");
	  _loadSystemAttributes(system);
	  Element systemProperties = system.getChild("SystemProperties");
	  _loadSystemProperties(systemProperties);
	  
      //reuse comment
      myProject.setDuringLoad(true);
      //end reuse comment

	  Element logicalStructure = system.getChild("LogicalStructure");	
	  _loadLogicalStructure(logicalStructure);
	  Element visualPart = system.getChild("VisualPart");	
	  _loadVisualPart(visualPart);
 
	  //reuse comment
      myProject.setDuringLoad(false);
      //end reuse comment
      
	  myProject.setCurrentOpd(myProject.getComponentsStructure().getOpd(ROOT_OPD));
	  
	  //Added by Eran Toch (2/12/04). The root OPD is shown only after all 
	  //the elements were displayed
	  myProject.showRootOpd();
	  return myProject;
	}
	catch(Exception e)
	{
		OpcatLogger.logError(e);
		throw new LoadException("This is not valid Opcat2 file");
	}
  }

//OPMSystem
  protected void _loadSystemAttributes(Element system)throws LoadException
  {
	myProject.setCreator(system.getAttributeValue("author"));
	myProject.setName(system.getAttributeValue("name"));
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	try{
	
		Date d = df.parse(system.getAttributeValue("creationDate"));
		myProject.setCreationDate(d);
	}catch(Exception t){t.printStackTrace();}
   
	try{
		myProject.setEntityMaxEntry(system.getAttribute("maxEntityEntry").getLongValue());
		myProject.setOpdMaxEntry(system.getAttribute("maxOpdEntry").getLongValue());
	}catch(DataConversionException e){
		throw new LoadException("This is not valid Opcat2 file");
	}
  }

//SystemProperties
  protected  void _loadSystemProperties(Element props) throws JDOMException
  {
	List propList = props.getChild("SystemConfiguration").getChildren("Property");
	_loadGenericTable(propList, myProject.getConfiguration());
	List propGenList = props.getChild("GeneralInfo").getChildren("Property");
	_loadGenericTable(propGenList, myProject.getGeneralInfo());

  }

//Property list
  protected void _loadGenericTable(List props, GenericTable table) throws JDOMException
  {
	for (Iterator i = props.iterator();i.hasNext();)
	{
	  Element prop = (Element)i.next();
	  table.setDBString(prop.getAttributeValue("key"),prop.getChildText("value"));
	}
  }

//LogicalStructure
  protected  void _loadLogicalStructure(Element logicalStruct) throws JDOMException
  {
  	
	_loadObjectSection(logicalStruct.getChild("ObjectSection"));
	incrementProgressBar();
	_loadProcessSection(logicalStruct.getChild("ProcessSection"));
	incrementProgressBar();
	_loadRelationSection(logicalStruct.getChild("RelationSection"));
	incrementProgressBar();
	_loadLinkSection(logicalStruct.getChild("LinkSection"));
	incrementProgressBar();
  }

//ObjectSection
  protected void _loadObjectSection(Element objectSect) throws JDOMException
  {
	List objectsList = objectSect.getChildren("LogicalObject");
	for (Iterator it = objectsList.iterator();it.hasNext();)
	{
		//LogicalObject
	  Element xmlObject = (Element)it.next();
	  ObjectEntry oe = _loadLogicalObject(xmlObject);

	  List statesList = xmlObject.getChildren("LogicalState");
	  for (Iterator stit = statesList.iterator();stit.hasNext();)
	  {
		_loadLogicalStates((Element) stit.next(), oe);
	  }
	}

  }

//LogicalObject
  protected ObjectEntry _loadLogicalObject(Element xmlObject) throws JDOMException
  {
	long objectId = xmlObject.getChild("EntityAttr").getAttribute("id").getLongValue();
	ObjectEntry myObject = new ObjectEntry(new OpmObject(objectId, ""), myProject);
	_loadEntityAttr(myObject,xmlObject.getChild("EntityAttr"));
	_loadThingAttr(myObject, xmlObject.getChild("ThingAttr"));
	myObject.setIndexName(xmlObject.getAttributeValue("indexName"));
	myObject.setIndexOrder(xmlObject.getAttribute("indexOrder").getIntValue());
	myObject.setInitialValue(xmlObject.getAttributeValue("initialValue"));
	myObject.setKey(xmlObject.getAttribute("key").getBooleanValue());
	myObject.setPersistent(xmlObject.getAttribute("persistent").getBooleanValue());
	myObject.setType(xmlObject.getAttributeValue("type"));
	if(xmlObject.getAttribute("typeOriginId")!=null){
		myObject.setTypeOriginId(xmlObject.getAttribute("typeOriginId").getLongValue());
	}
	myProject.getComponentsStructure().put(objectId, myObject);
	return myObject;
  }

//LogicalState
  protected void _loadLogicalStates(Element xmlState, ObjectEntry parentEntry) throws JDOMException
  {
	long stateId = xmlState.getChild("EntityAttr").getAttribute("id").getLongValue();
	StateEntry myState = new StateEntry(new OpmState(stateId,""), (OpmObject)parentEntry.getLogicalEntity(),myProject);

	_loadEntityAttr(myState, xmlState.getChild("EntityAttr"));

	myState.setDefault(xmlState.getAttribute("default").getBooleanValue());
	myState.setFinal(xmlState.getAttribute("final").getBooleanValue());
	myState.setInitial(xmlState.getAttribute("initial").getBooleanValue());
	myState.setMaxTime(xmlState.getAttributeValue("maxTime"));
	myState.setMinTime(xmlState.getAttributeValue("minTime"));

	myProject.getComponentsStructure().put(stateId, myState);
	parentEntry.addState((OpmState)myState.getLogicalEntity());
  }

//EntityAttr
  protected void _loadEntityAttr(Entry entity, Element xmlEntity) throws JDOMException
  {
	entity.setName(xmlEntity.getChildText("name"));
	entity.setEnvironmental(xmlEntity.getAttribute("environmental").getBooleanValue());
	entity.setDescription(xmlEntity.getChildText("description"));
	/*
	 * TODO: this is the place to load the url from the file.
	 * this is done here to save the need to create a new file loader 
	 * and saver.
	 */
	try {
		if (xmlEntity.getChildText("url").equalsIgnoreCase("")) {
			entity.setUrl ( "none") ; 
		} 
		else {
		entity.setUrl(xmlEntity.getChildText("url"));
		}
	} catch (Exception e) {
		
	}

  }

//ThingAttr
  protected void _loadThingAttr(ThingEntry thing, Element xmlThing) throws JDOMException
  {
	thing.setNumberOfMASInstances(xmlThing.getAttribute("numberOfInstances").getIntValue());
	thing.setPhysical(xmlThing.getAttribute("physical").getBooleanValue());
	thing.setRole(xmlThing.getAttributeValue("role"));
	thing.setScope(xmlThing.getAttributeValue("scope"));

  }

//ProcessSection
  protected void _loadProcessSection(Element processSect) throws JDOMException
  {

	List processesList = processSect.getChildren("LogicalProcess");

	for (Iterator it = processesList.iterator(); it.hasNext();)
	{
	  Element xmlProcess = (Element)it.next();
	  _loadLogicalProcess(xmlProcess);
	}
  }

//LogicalProcess
  protected void _loadLogicalProcess(Element xmlProcess) throws JDOMException
  {
	long processId = xmlProcess.getChild("EntityAttr").getAttribute("id").getLongValue();

	ProcessEntry myProcess = new ProcessEntry(new OpmProcess(processId, ""), myProject);

	_loadEntityAttr(myProcess, xmlProcess.getChild("EntityAttr"));
	_loadThingAttr(myProcess, xmlProcess.getChild("ThingAttr"));

	myProcess.setMaxTimeActivation(xmlProcess.getAttributeValue("maxTimeActivation"));
	myProcess.setMinTimeActivation(xmlProcess.getAttributeValue("minTimeActivation"));
	myProcess.setProcessBody(xmlProcess.getChildText("processBody"));
	//myProcess.se
	myProject.getComponentsStructure().put(processId, myProcess);
  }

//RelationSection
  protected void _loadRelationSection(Element relationSect) throws JDOMException
  {
	List relationsList = relationSect.getChildren("LogicalRelation");

	for (Iterator it = relationsList.iterator(); it.hasNext();)
	{
	  Element xmlRelation = (Element)it.next();
	  _loadLogicalRelation(xmlRelation);
	}

  }

//LogicalRelation
  protected void _loadLogicalRelation(Element xmlRelation) throws JDOMException
  {

	long relId = xmlRelation.getChild("EntityAttr").getAttribute("id").getLongValue();
	OpmConnectionEdge source = (OpmConnectionEdge)myProject.getComponentsStructure().getEntry(xmlRelation.getAttribute("sourceId").getLongValue()).getLogicalEntity();
	OpmConnectionEdge destination = (OpmConnectionEdge)myProject.getComponentsStructure().getEntry(xmlRelation.getAttribute("destinationId").getLongValue()).getLogicalEntity();

	OpmStructuralRelation opmRel = Constants.createRelation(xmlRelation.getAttribute("relationType").getIntValue(), relId, source, destination);
	RelationEntry myRelation;

	if (opmRel instanceof OpmFundamentalRelation)
	{
	  myRelation = new FundamentalRelationEntry((OpmFundamentalRelation)opmRel, myProject);
	}
	else
	{
	  myRelation = new GeneralRelationEntry((OpmGeneralRelation)opmRel, myProject);
	}

	_loadEntityAttr(myRelation, xmlRelation.getChild("EntityAttr"));

	myRelation.setSourceCardinality(xmlRelation.getAttributeValue("sourceCardinality"));
	myRelation.setDestinationCardinality(xmlRelation.getAttributeValue("destinationCardinality"));
	myRelation.setForwardRelationMeaning(xmlRelation.getAttributeValue("forwardRelationMeaning"));
	myRelation.setBackwardRelationMeaning(xmlRelation.getAttributeValue("backwardRelationMeaning"));

	myProject.getComponentsStructure().put(relId, myRelation);
  }

//LinkSection
  protected void _loadLinkSection(Element linkSect) throws JDOMException
  {
	List linksList = linkSect.getChildren("LogicalLink");

	for (Iterator it = linksList.iterator(); it.hasNext();)
	{
	  Element xmlLink = (Element)it.next();
	  _loadLogicalLink(xmlLink);
	}

  }

  //LogicalLink
  protected void _loadLogicalLink(Element xmlLink) throws JDOMException
  {
	long linkId = xmlLink.getChild("EntityAttr").getAttribute("id").getLongValue();

	OpmProceduralLink opmLink = Constants.createLink(xmlLink.getAttribute("linkType").getIntValue(), linkId, xmlLink.getAttribute("sourceId").getLongValue(), xmlLink.getAttribute("destinationId").getLongValue());

	LinkEntry myLink = new LinkEntry(opmLink, myProject);

	_loadEntityAttr(myLink, xmlLink.getChild("EntityAttr"));

	myLink.setCondition(xmlLink.getAttributeValue("condition"));
	myLink.setPath(xmlLink.getAttributeValue("path"));
	myLink.setMaxReactionTime(xmlLink.getAttributeValue("maxReactionTime"));
	myLink.setMinReactionTime(xmlLink.getAttributeValue("minReactionTime"));

	myProject.getComponentsStructure().put(linkId, myLink);
  }

//VisualPart
  protected void _loadVisualPart(Element vpt) throws JDOMException
  {
	_loadOpd(vpt.getChild("OPD"), null, LoaderVer2.NO_PARENT); 
  }

//OPD
  protected Opd _loadOpd(Element xmlOpd, Opd parentOpd, int relationsWithParent) throws JDOMException
  {
	long opdId = xmlOpd.getAttribute("id").getLongValue();
	
	//Loading also the OPD name attribute 
	Opd myOpd = new Opd(myProject , xmlOpd.getAttributeValue("name") , opdId, parentOpd);
	myProject.getComponentsStructure().put(opdId, myOpd);
	myOpd.getDrawingArea().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	myOpd.setEntityInOpdMaxEntry(xmlOpd.getAttribute("maxEntityEntry").getLongValue());
	myProject.setCurrentOpd(myOpd);

	if (opdId != ROOT_OPD)
	{
	  _loadMainEntity(xmlOpd.getChild("MainEntity"), myOpd, relationsWithParent);
	}

	_loadThingSection(xmlOpd.getChild("ThingSection"), myOpd);
	_loadFundamentalRelationSection(xmlOpd.getChild("FundamentalRelationSection"), myOpd);
	_loadGeneralRelationSection(xmlOpd.getChild("GeneralRelationSection"), myOpd);
	_loadVisualLinkSection(xmlOpd.getChild("VisualLinkSection"), myOpd);
	incrementProgressBar();

	_loadUnfolded(xmlOpd.getChild("Unfolded"), myOpd);
	_loadInzoomed(xmlOpd.getChild("InZoomed"), myOpd);

	return myOpd;
  }

//MainEntity
  protected void _loadMainEntity(Element mainEntity, Opd myOpd, int mainEntityOrigin) throws JDOMException
  {
	Element xmlThing = mainEntity.getChild("VisualThing");

	ThingInstance tInstance = _loadVisualThing(xmlThing, myOpd, null, mainEntityOrigin);
	
	myOpd.setMainInstance(tInstance);
	myOpd.setMainEntry((ThingEntry)tInstance.getEntry());
  }

//ThingSection
  protected void _loadThingSection(Element thingSect, Opd currentOpd) throws JDOMException
  {
	List thingList = thingSect.getChildren("VisualThing");

	for (Iterator it = thingList.iterator(); it.hasNext();)
	{
	  Element vThing = (Element)it.next();
	  _loadVisualThing(vThing, currentOpd, null, LoaderVer2.NO_PARENT);
	}

  }

//VisualThing
  protected ThingInstance _loadVisualThing(Element xmlThing, Opd currentOpd, ThingInstance parent, int relationWithParent) throws JDOMException
  {
	ThingInstance myThing;

	Element tData = xmlThing.getChild("ThingData");

	if (tData.getChild("VisualProcess") != null)
	{
	  myThing = _loadVisualProcess(tData.getChild("VisualProcess"), currentOpd, parent);
	}
	else
	{
	  myThing = _loadVisualObject(tData.getChild("VisualObject"), currentOpd, parent);
	}

	Element childrenContainer = xmlThing.getChild("ChildrenContainer");
	List childrenList = childrenContainer.getChildren("VisualThing");

	if (relationWithParent == LoaderVer2.INZOOMED)	{
	    myThing.setZoomedIn(true);
	}
	else	{
	    myThing.setZoomedIn(false);
	}
	
	//myThing.setZoomedIn(!childrenList.isEmpty());

	for (Iterator it = childrenList.iterator(); it.hasNext();)
	{
	  Element vThing = (Element)it.next();
	  _loadVisualThing(vThing, currentOpd, myThing, LoaderVer2.NO_PARENT);
	}

	return myThing;
  }

//VisualProcess
  protected ProcessInstance _loadVisualProcess(Element xmlProcess, Opd currentOpd, ThingInstance parent) throws JDOMException
  {
	Element insAttr = xmlProcess.getChild("InstanceAttr");
	ProcessInstance myProcess;
	if (parent == null)
	{
	  myProcess = myProject.addProcess(0, 0, currentOpd.getDrawingArea(),
									   insAttr.getAttribute("entityId").getLongValue(),
									   insAttr.getAttribute("entityInOpdId").getLongValue());
	}
	else
	{
	  myProcess = myProject.addProcess(0, 0, parent.getConnectionEdge(),
						insAttr.getAttribute("entityId").getLongValue(),
						insAttr.getAttribute("entityInOpdId").getLongValue());

	}
	_loadInstanceAttr(myProcess, insAttr);
	_loadConnectionEdgeAttr(myProcess, xmlProcess.getChild("ConnectionEdgeAttr"));
	_loadThingInstanceAttr(myProcess, xmlProcess.getChild("ThingInstanceAttr"));

	return myProcess;
  }

//VisualObject
  protected ObjectInstance _loadVisualObject(Element xmlObject, Opd currentOpd, ThingInstance parent) throws JDOMException
  {
	Element insAttr = xmlObject.getChild("InstanceAttr");
	ObjectInstance myObject;

	if (parent == null)
	{
	  myObject = myProject.addObject(0, 0, currentOpd.getDrawingArea(),
									 insAttr.getAttribute("entityId").getLongValue(),
		insAttr.getAttribute("entityInOpdId").getLongValue(), false);
	}
	else
	{
	  myObject = myProject.addObject(0, 0, parent.getConnectionEdge(),
							 insAttr.getAttribute("entityId").getLongValue(),
insAttr.getAttribute("entityInOpdId").getLongValue(), false);
	}


	_loadInstanceAttr(myObject, insAttr);
	_loadConnectionEdgeAttr(myObject, xmlObject.getChild("ConnectionEdgeAttr"));
	_loadThingInstanceAttr(myObject, xmlObject.getChild("ThingInstanceAttr"));

	myObject.setStatesAutoArranged(xmlObject.getAttribute("statesAutoArranged").getBooleanValue());

	for (Iterator i = xmlObject.getChildren("VisualState").iterator(); i.hasNext();)
	{
	  _loadVisualState((Element)i.next(), myObject, currentOpd);
	}

	return myObject;
  }

//VisualState
  protected void _loadVisualState(Element xmlState, ObjectInstance parentInstance, Opd currentOpd) throws JDOMException
  {
	Element insAttr = xmlState.getChild("InstanceAttr");
	OpdKey myKey = new OpdKey(currentOpd.getOpdId(), insAttr.getAttribute("entityInOpdId").getLongValue());

	StateEntry myEntry = (StateEntry)myProject.getComponentsStructure().getEntry(insAttr.getAttribute("entityId").getLongValue());
	StateInstance myInstance = new StateInstance(myEntry, myKey,
												 (OpdObject)parentInstance.getConnectionEdge(), myProject);
	myEntry.addInstance(myKey, myInstance);

	_loadInstanceAttr(myInstance, insAttr);
	_loadConnectionEdgeAttr(myInstance, xmlState.getChild("ConnectionEdgeAttr"));
	myInstance.setVisible(xmlState.getAttribute("visible").getBooleanValue());

  }

//InstanceAttr
  protected void _loadInstanceAttr(Instance ins, Element xmlInstance) throws JDOMException
  {
	ins.setBackgroundColor(new Color(xmlInstance.getAttribute("backgroundColor").getIntValue()));
	ins.setBorderColor(new Color(xmlInstance.getAttribute("borderColor").getIntValue()));
	ins.setTextColor(new Color(xmlInstance.getAttribute("textColor").getIntValue()));
  }

//ConnectionEdgeAttr
  protected void _loadConnectionEdgeAttr(ConnectionEdgeInstance cEdge, Element xmlConEdge) throws JDOMException
  {
	cEdge.setLocation(xmlConEdge.getAttribute("x").getIntValue(), xmlConEdge.getAttribute("y").getIntValue());
	cEdge.setSize(xmlConEdge.getAttribute("width").getIntValue(), xmlConEdge.getAttribute("height").getIntValue());

  }

//ThingInstanceAttr
  protected void _loadThingInstanceAttr(ThingInstance tInstance, Element xmlThingIns) throws JDOMException
  {
	tInstance.setLayer(xmlThingIns.getAttribute("layer").getIntValue());
	tInstance.setTextPosition(xmlThingIns.getAttributeValue("textPosition"));
  }

//FundamentalRelationSection
  protected void _loadFundamentalRelationSection(Element relationSect, Opd currentOpd) throws JDOMException
  {
	List relationList = relationSect.getChildren("CommonPart");

	for (Iterator it = relationList.iterator(); it.hasNext();)
	{
	  Element cPart = (Element)it.next();
	  _loadCommonPart(cPart, currentOpd);
	}
  }

//CommonPart
  protected void _loadCommonPart(Element cPart, Opd currentOpd) throws JDOMException
  {
	List relationList = cPart.getChildren("VisualFundamentalRelation");
	FundamentalRelationInstance fInstance = null;

	for (Iterator it = relationList.iterator(); it.hasNext();)
	{
	  fInstance = _loadVisualFundamentalRelation(cPart,
												 (Element)it.next(), currentOpd);
	}

	GraphicalRelationRepresentation repr = fInstance.getGraphicalRelationRepresentation();
	RelativeConnectionPoint sourcePoint = new RelativeConnectionPoint(cPart.getAttribute("sourceConnectionSide").getIntValue(), (double)cPart.getAttribute("sourceConnectionParameter").getFloatValue());

	repr.getSourceDragger().setRelativeConnectionPoint(sourcePoint);
	repr.getSourceDragger().updateDragger();

	OpdFundamentalRelation triangle = repr.getRelation();
	triangle.setLocation(cPart.getAttribute("x").getIntValue(), cPart.getAttribute("y").getIntValue());
	triangle.setSize(cPart.getAttribute("width").getIntValue(), cPart.getAttribute("height").getIntValue());
	repr.setBackgroundColor(new Color(cPart.getAttribute("backgroundColor").getIntValue()));
	repr.setBorderColor(new Color(cPart.getAttribute("borderColor").getIntValue()));
	repr.arrangeLines();
  }

//CommonPart VisualFundamentalRelation
  protected FundamentalRelationInstance _loadVisualFundamentalRelation(Element cPart,Element xmlRelation, Opd currentOpd) throws JDOMException
  {
	Element insAttr = xmlRelation.getChild("InstanceAttr");

	FundamentalRelationEntry myEntry = (FundamentalRelationEntry)myProject.getComponentsStructure().getEntry(insAttr.getAttribute("entityId").getLongValue());

	ConnectionEdgeEntry sourceEntry = (ConnectionEdgeEntry)myProject.getComponentsStructure().getEntry(cPart.getAttribute("sourceId").getLongValue());
	ConnectionEdgeEntry destinationEntry = (ConnectionEdgeEntry)myProject.getComponentsStructure().getEntry(xmlRelation.getAttribute("destinationId").getLongValue());
	ConnectionEdgeInstance sourceInstance = (ConnectionEdgeInstance)(sourceEntry.getInstance(new OpdKey(currentOpd.getOpdId(), cPart.getAttribute("sourceInOpdId").getLongValue())));
	OpdConnectionEdge source = sourceInstance.getConnectionEdge();
	ConnectionEdgeInstance destinationInstance = (ConnectionEdgeInstance)destinationEntry.getInstance(new OpdKey(currentOpd.getOpdId(), xmlRelation.getAttribute("destinationInOpdId").getLongValue()));
	OpdConnectionEdge destination = destinationInstance.getConnectionEdge();

	JLayeredPane tempContainer;
	if (source.getParent() == destination.getParent())
	{
	  tempContainer = (JLayeredPane)source.getParent();
	}
	else
	{
	  tempContainer = (JLayeredPane)currentOpd.getDrawingArea();
	}

	RelativeConnectionPoint sourcePoint = new RelativeConnectionPoint(cPart.getAttribute("sourceConnectionSide").getIntValue(), (double)cPart.getAttribute("sourceConnectionParameter").getFloatValue());
	RelativeConnectionPoint destinationPoint = new RelativeConnectionPoint(xmlRelation.getAttribute("destinationSide").getIntValue(), (double)xmlRelation.getAttribute("destinationParameter").getFloatValue());

	FundamentalRelationInstance myRelation = myProject.addRelation(source, sourcePoint,
											destination, destinationPoint,
											myEntry.getRelationType(), tempContainer,  myEntry.getId(), insAttr.getAttribute("entityInOpdId").getLongValue());


	_loadInstanceAttr(myRelation, insAttr);


	myRelation.update();
	myRelation.setAutoArranged(true);
	myRelation.arrangeLines();

	myRelation.getDestinationDragger().getOpdCardinalityLabel().setLocation(xmlRelation.getAttribute("labelX").getIntValue(), xmlRelation.getAttribute("labelY").getIntValue());
	return myRelation;
  }



//GeneralRelationSection
  protected void _loadGeneralRelationSection(Element relationSect, Opd currentOpd) throws JDOMException
  {
	List relationList = relationSect.getChildren("VisualGeneralRelation");

	for (Iterator it = relationList.iterator(); it.hasNext();)
	{
	  Element vRelation = (Element)it.next();
	  _loadVisualGeneralRelation(vRelation, currentOpd);
	}
  }

//VisualGeneralRelation
  protected void _loadVisualGeneralRelation(Element xmlRelation, Opd currentOpd) throws JDOMException
  {
	Element insAttr = xmlRelation.getChild("InstanceAttr");
	Element lineAttr = xmlRelation.getChild("LineAttr");

	GeneralRelationEntry myEntry = (GeneralRelationEntry)myProject.getComponentsStructure().getEntry(insAttr.getAttribute("entityId").getLongValue());

	ConnectionEdgeEntry sourceEntry = (ConnectionEdgeEntry)myProject.getComponentsStructure().getEntry(lineAttr.getAttribute("sourceId").getLongValue());
	ConnectionEdgeEntry destinationEntry = (ConnectionEdgeEntry)myProject.getComponentsStructure().getEntry(lineAttr.getAttribute("destinationId").getLongValue());
	ConnectionEdgeInstance sourceInstance = (ConnectionEdgeInstance)(sourceEntry.getInstance(new OpdKey(currentOpd.getOpdId(), lineAttr.getAttribute("sourceInOpdId").getLongValue())));
	OpdConnectionEdge source = sourceInstance.getConnectionEdge();
	ConnectionEdgeInstance destinationInstance = (ConnectionEdgeInstance)destinationEntry.getInstance(new OpdKey(currentOpd.getOpdId(), lineAttr.getAttribute("destinationInOpdId").getLongValue()));
	OpdConnectionEdge destination = destinationInstance.getConnectionEdge();

	JLayeredPane tempContainer;
	if (source.getParent() == destination.getParent())
	{
	  tempContainer = (JLayeredPane)source.getParent();
	}
	else
	{
	  tempContainer = (JLayeredPane)currentOpd.getDrawingArea();
	}

	RelativeConnectionPoint sourcePoint = new RelativeConnectionPoint(lineAttr.getAttribute("sourceConnectionSide").getIntValue(), (double)lineAttr.getAttribute("sourceConnectionParameter").getFloatValue());
	RelativeConnectionPoint destinationPoint = new RelativeConnectionPoint(lineAttr.getAttribute("destinationConnectionSide").getIntValue(), (double)lineAttr.getAttribute("destinationConnectionParameter").getFloatValue());

	GeneralRelationInstance myRelation = myProject.addGeneralRelation(source, sourcePoint,
											destination, destinationPoint,
											tempContainer, myEntry.getRelationType(), myEntry.getId(), insAttr.getAttribute("entityInOpdId").getLongValue());

	_loadInstanceAttr(myRelation, insAttr);

	Element points = xmlRelation.getChild("BreakPoints");
	if (points !=null)
	{
	  _loadBreakPoints(points, myRelation.getLine());
	}

	if (lineAttr.getAttribute("autoArranged").getBooleanValue())
	{
	  myRelation.update();
	  myRelation.setAutoArranged(true);
	  myRelation.getLine().arrange();
	}
	else
	{
	  myRelation.setAutoArranged(false);
	  myRelation.getSourceDragger().setRelativeConnectionPoint(sourcePoint);
	  myRelation.getDestinationDragger().setRelativeConnectionPoint(destinationPoint);
	  myRelation.update();
	}

	((OpdRelationDragger)(myRelation.getSourceDragger())).getOpdCardinalityLabel().setLocation(xmlRelation.getAttribute("sourceLabelX").getIntValue(),xmlRelation.getAttribute("sourceLabelY").getIntValue());
	((OpdRelationDragger)(myRelation.getDestinationDragger())).getOpdCardinalityLabel().setLocation(xmlRelation.getAttribute("destinationLabelX").getIntValue(), xmlRelation.getAttribute("destinationLabelY").getIntValue());

  }


//BreakPoints
  protected void _loadBreakPoints(Element points, StyledLine line)throws JDOMException
  {
	List pointsList = points.getChildren("Point");

	for (Iterator i = pointsList.iterator(); i.hasNext();)
	{
	  Element xmlPoint = (Element)i.next();
	  line.addPoint(new java.awt.Point(xmlPoint.getAttribute("x").getIntValue(), xmlPoint.getAttribute("y").getIntValue()));
	}
  }

//VisualLinkSection
  protected void _loadVisualLinkSection(Element linkSect,
									  Opd currentOpd) throws JDOMException
  {
	List linkList = linkSect.getChildren("VisualLink");

	for (Iterator it = linkList.iterator(); it.hasNext(); )
	{
	  Element vLink = (Element) it.next();
	  _loadVisualLink(vLink, currentOpd);
	}

	List orList = linkSect.getChildren("OrXorGroup");

	for (Iterator it = orList.iterator(); it.hasNext(); )
	{
	 Element xmlOr = (Element) it.next();
	  _loadOrXor(xmlOr, currentOpd);
	}

  }

//OrXorGroup
  protected void _loadOrXor(Element xmlOr, Opd currentOpd) throws JDOMException
  {
	List memberList = xmlOr.getChildren("Member");
	Element xmlMember = (Element)memberList.get(0);

	LinkEntry lEntry = (LinkEntry)myProject.getComponentsStructure().getEntry(xmlMember.getAttribute("memberId").getLongValue());
	LinkInstance lInstance = (LinkInstance)lEntry.getInstance(new OpdKey(currentOpd.getOpdId(), xmlMember.getAttribute("memberInOpdId").getLongValue()));
	
	if(lInstance==null){
		System.err.println("Null Pointer in lInstance!" +xmlMember.getAttributeValue("memberInOpdId"));
		return;
	}
	OrInstance myOr;
	
	if (xmlOr.getAttribute("isSourceGroup")!=null && xmlOr.getAttribute("isSourceGroup").getBooleanValue())
	{
	  myOr = lInstance.getSourceOr();
	}
	else
	{
	  myOr = lInstance.getDestOr();
	}

	if (myOr == null)
	{
	  return;
	}

	if (xmlOr.getAttribute("type")!=null && xmlOr.getAttribute("type").getIntValue() == OR_TYPE)
	{
	  myOr.setOr(true);
	}
	else
	{
	  myOr.setOr(false);
	}

  }

//VisualLink
  protected void _loadVisualLink(Element xmlLink, Opd currentOpd) throws JDOMException
  {
	Element insAttr = xmlLink.getChild("InstanceAttr");
	Element lineAttr = xmlLink.getChild("LineAttr");

	LinkEntry myEntry = (LinkEntry)myProject.getComponentsStructure().getEntry(insAttr.getAttribute("entityId").getLongValue());

	ConnectionEdgeEntry sourceEntry = (ConnectionEdgeEntry)myProject.getComponentsStructure().getEntry(lineAttr.getAttribute("sourceId").getLongValue());
	ConnectionEdgeEntry destinationEntry = (ConnectionEdgeEntry)myProject.getComponentsStructure().getEntry(lineAttr.getAttribute("destinationId").getLongValue());
	ConnectionEdgeInstance sourceInstance = (ConnectionEdgeInstance)(sourceEntry.getInstance(new OpdKey(currentOpd.getOpdId(), lineAttr.getAttribute("sourceInOpdId").getLongValue())));
	OpdConnectionEdge source = sourceInstance.getConnectionEdge();
	ConnectionEdgeInstance destinationInstance = (ConnectionEdgeInstance)destinationEntry.getInstance(new OpdKey(currentOpd.getOpdId(), lineAttr.getAttribute("destinationInOpdId").getLongValue()));
	OpdConnectionEdge destination = destinationInstance.getConnectionEdge();

	JLayeredPane tempContainer;
	if (source.getParent() == destination.getParent())
	{
	  tempContainer = (JLayeredPane)source.getParent();
	}
	else
	{
	  tempContainer = (JLayeredPane)currentOpd.getDrawingArea();
	}

	RelativeConnectionPoint sourcePoint = new RelativeConnectionPoint(lineAttr.getAttribute("sourceConnectionSide").getIntValue(), (double)lineAttr.getAttribute("sourceConnectionParameter").getFloatValue());
	RelativeConnectionPoint destinationPoint = new RelativeConnectionPoint(lineAttr.getAttribute("destinationConnectionSide").getIntValue(), (double)lineAttr.getAttribute("destinationConnectionParameter").getFloatValue());

	LinkInstance myLink = myProject.addLink(source, sourcePoint,
											destination, destinationPoint,
											tempContainer, myEntry.getLinkType(), myEntry.getId(), insAttr.getAttribute("entityInOpdId").getLongValue());



	_loadInstanceAttr(myLink, insAttr);

	Element points = xmlLink.getChild("BreakPoints");
	if (points !=null)
	{
	  _loadBreakPoints(points, myLink.getLine());
	}

	if (lineAttr.getAttribute("autoArranged").getBooleanValue())
	{
	  myLink.update();
	  myLink.setAutoArranged(true);
	  myLink.getLine().arrange();
	}
	else
	{
	  myLink.setAutoArranged(false);
	  myLink.getSourceDragger().setRelativeConnectionPoint(sourcePoint);
	  myLink.getDestinationDragger().setRelativeConnectionPoint(destinationPoint);
	  myLink.update();
	  myLink.updateOrConnections();
	}

  }



//InZoomed
  protected void _loadInzoomed(Element inzoomed, Opd parentOpd) throws JDOMException
  {
	for (Iterator it = inzoomed.getChildren("OPD").iterator(); it.hasNext();)
	{
	  Opd newOpd = _loadOpd((Element)it.next(), parentOpd, LoaderVer2.INZOOMED);
	  newOpd.getMainEntry().setZoomedInOpd(newOpd);
	  newOpd.getMainEntry().updateInstances();
	}
  }

//Unfolded ,OPD
  protected void _loadUnfolded(Element unfolded, Opd parentOpd) throws JDOMException
 {
   for (Iterator it = unfolded.getChildren("UnfoldingProperties").iterator(); it.hasNext();)
   {
	 Element xmlOpd = (Element)it.next();
	 //ThingEntry
	 ThingEntry unfoldedEntry = (ThingEntry)myProject.getComponentsStructure().getEntry(xmlOpd.getAttribute("entityId").getLongValue());
	 ThingInstance unfoldedInstance = (ThingInstance)unfoldedEntry.getInstance(new OpdKey(parentOpd.getOpdId(),xmlOpd.getAttribute("entityInOpdId").getLongValue()));
	 unfoldedInstance.setUnfoldingOpd(_loadOpd(xmlOpd.getChild("OPD"), parentOpd, LoaderVer2.UNFOLDED));
	 unfoldedInstance.update();
   }

 }


//Accepts OPX
  protected void _prepareProgressBar(Element rootTag)
 {
   Element rootOPD = rootTag.getChild("OPMSystem").getChild("VisualPart").getChild("OPD");	
   numOfOpds = 0;
   _countSubTree(rootOPD);
   if (pBar != null)	{
      	pBar.setMinimum(0);
   		pBar.setMaximum(numOfOpds+4);
   		pBar.setValue(0);
   }
 }

//Element OPD
  protected void _countSubTree(Element opd)
 {
   numOfOpds++;
   List inZoomed = opd.getChild("InZoomed").getChildren("OPD");
   for (Iterator i1 = inZoomed.iterator(); i1.hasNext();)
   {
	//OPDType
	 _countSubTree((Element)i1.next());
   }
   List unfolded = opd.getChild("Unfolded").getChildren("UnfoldingProperties");	
   for (Iterator i2 = unfolded.iterator(); i2.hasNext();)
   {
	 Element upt = (Element)i2.next();
	 _countSubTree(upt.getChild("OPD"));
   }

 }

protected void incrementProgressBar() {
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			if (pBar != null) {
				synchronized (pBar) {
					pBar.setValue(pBar.getValue() + 1);
				}
			}
		}
	});

 }
}
