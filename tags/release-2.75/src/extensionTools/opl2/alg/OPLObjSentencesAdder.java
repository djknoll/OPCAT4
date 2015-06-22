package extensionTools.opl2.alg;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.ILinkEntry;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IObjectInstance;
import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.IRelationEntry;
import exportedAPI.opcatAPI.IStateEntry;
import extensionTools.opl2.generated.AgentSentence;
import extensionTools.opl2.generated.GeneralEventSentence;
import extensionTools.opl2.generated.ObjectAggregationSentenceSetType;
import extensionTools.opl2.generated.ObjectExhibitionSentenceSetType;
import extensionTools.opl2.generated.ObjectInZoomingSentenceSet;
import extensionTools.opl2.generated.StateEntranceSentence;
import extensionTools.opl2.generated.StateTimeoutSentence;
import extensionTools.opl2.generated.ThingSentenceSet;

/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author OPCAT team
 * @version 1.0
 */

public class OPLObjSentencesAdder
    extends OPLSentencesAdder {

  private OPLObjectFactor helper;

  public OPLObjSentencesAdder(OPLObjectFactor factor) {
    helper = factor;
    ByProc = false;
  }

/*******************************************************************/
/*               RELATIONS                                         */
/*******************************************************************/
  protected void addAggregRel(ThingSentenceSet thing,
                              IRelationEntry rel, String objName) throws
      Exception {
    if (thing.getObjectAggregationSentenceSet() == null) {
      thing.setObjectAggregationSentenceSet(
          helper.buildObjAggregSentenceSet(objName));
    }
    addAggregObj(thing.getObjectAggregationSentenceSet(), rel);
  }

  protected void addExhibRel(ThingSentenceSet thing,
                             IRelationEntry rel, String objName) throws
      Exception {
    try {
      if (thing.getObjectExhibitionSentenceSet() == null) {
        thing.setObjectExhibitionSentenceSet(
            helper.buildObjExhibSentenceSet(objName));
      }
      addExhibObj(thing.getObjectExhibitionSentenceSet(), rel);
    }
    catch (Exception e) {
      throw e;
    }
  }

  protected void addInhRelSentence(ThingSentenceSet thing,
                                   IRelationEntry rel) throws Exception {
  	if(thing.getObjectInheritanceSentenceSet()==null){
  		thing.setObjectInheritanceSentenceSet(helper.buildObjInhSentenceSet(rel));
  	}else
  		thing.getObjectInheritanceSentenceSet().getObjectInheritanceSentence().add(helper.buildObjInhSentence(rel));
  }

  protected void addInstRelSentence(ThingSentenceSet thing,
                                             IRelationEntry rel)throws
      Exception{
     thing.setObjectInstanceSentence(helper.buildObjInstSentence(rel));
  }


  protected void addUniDirRelSentence(ThingSentenceSet thing,
                                      IRelationEntry rel) throws Exception {
    OPLGeneral.addLast(thing.getObjectUniDirectionalRelationSentence(),
                       helper.buildObjUniDirSentence(rel));
  }

  protected void addBiDirRelSentence(ThingSentenceSet thing,
                                     IRelationEntry rel, int rule) throws
      Exception {
    OPLGeneral.addLast(thing.getObjectBiDirectionalRelationSentence(),
                       helper.buildObjBiDirSentence(rel, rule));
  }

  protected void addExhibObj(ObjectExhibitionSentenceSetType p,
                             IRelationEntry rel) {
    IEntry destination = helper.getEntry(rel);
    try {
      if(destination instanceof IProcessEntry){
        p.getObjectExhibitionSentence().getOperation().add(helper.buildOperation(destination.getName()));
      }else p.getObjectExhibitionSentence().getExhibitedObject().add(helper.buildExhibitedObject(destination, rel));
      helper.builder.addThingSentence(p.getThingSentenceSet(), destination);
    }
    catch (Exception e) {
    }
  }

  protected void addAggregObj(ObjectAggregationSentenceSetType p,
                              IRelationEntry rel) {
    IEntry destination = helper.getEntry(rel);
    try {
      OPLGeneral.addLast(p.getObjectAggregationSentence().getAggregatedObject(),
                         helper.buildAggregatedObject(destination, rel));
      helper.builder.addThingSentence(p.getThingSentenceSet(), destination);
    }
    catch (Exception e) {
    }
  }

/*******************************************************************/
 /*               LINKS                                         */
/*******************************************************************/

  protected boolean specificLink(ThingSentenceSet thing, String objName,
                                 String stateName,
                                 ILinkEntry link) throws
  Exception {
    int type = link.getLinkType();
    String path = link.getPath();
    try {
      if(type == OpcatConstants.AGENT_LINK){
        AgentSentence sentence = getAgentSentenceByPath(thing, link, path);
        addAgentSentenceDestination(sentence, link);
        return true;
      }
      if(type == OpcatConstants.EXCEPTION_LINK ){
        if (!stateName.equals(" ")) {
          StateTimeoutSentence sentence = getStateTimeoutSentenceByPath(thing,
              link, path, stateName);
          addStateTimeoutSentenceDestination(sentence, link);
          return true;
        }
        else {
          GeneralEventSentence sentence = getGeneralEventSentenceByPath(thing,
              link, path);
          addGeneralEventSentenceDestination(sentence, link);
          return true;
        }
      }
      if( type == OpcatConstants.CONSUMPTION_EVENT_LINK ||
          type == OpcatConstants.INSTRUMENT_EVENT_LINK){
        if (!stateName.equals(" ")) {
          StateEntranceSentence sentence = getStateEntranceSentenceByPath(thing,
              link, path, stateName);
          addStateEntranceSentenceDestination(sentence, link);
          return true;
        }
        else {
          GeneralEventSentence sentence = getGeneralEventSentenceByPath(thing,
              link, path);
          addGeneralEventSentenceDestination(sentence, link);
          return true;
        }
      }
      return false;
    }
    catch (Exception e) {
      throw e;
    }
  }


  protected AgentSentence getAgentSentenceByPath(ThingSentenceSet thing,
                                                 ILinkEntry link, String path) throws
      Exception {
    int size = thing.getAgentSentence().size();
    AgentSentence sentence;
    for (int i = 0; i < size; i++) {
      sentence = (AgentSentence) thing.getAgentSentence().get(i);
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
        if(this.helper.setLogicBySource(link).equals(sentence.getLogicalRelation()))
        return sentence;
      }
    }
    sentence = helper.buildAgentSentence(link, path);
    thing.getAgentSentence().add(sentence);
    return sentence;
  }

  protected void addAgentSentenceDestination(AgentSentence sentence,
                                             ILinkEntry link) {
    String procName = helper.builder.myStructure.getIEntry(link.
        getDestinationId()).getName();
    sentence.getTriggeredProcessName().add(procName);
  }

  protected GeneralEventSentence getGeneralEventSentenceByPath(ThingSentenceSet thing,
                                                 ILinkEntry link, String path) throws
      Exception {
    int size = thing.getGeneralEventSentence().size();
    GeneralEventSentence sentence;
    for (int i = 0; i < size; i++) {
      sentence = (GeneralEventSentence) thing.getGeneralEventSentence().get(i);
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
        if(this.helper.setLogicBySource(link).equals(sentence.getLogicalRelation()))
        return sentence;
      }
    }
    sentence = helper.buildGeneralEventSentence(link, path);
    thing.getGeneralEventSentence().add(sentence);
    return sentence;
  }

  protected void addGeneralEventSentenceDestination(GeneralEventSentence sentence,
                                             ILinkEntry link) {
    String procName = helper.builder.myStructure.getIEntry(link.
        getDestinationId()).getName();
    sentence.getTriggeredProcessName().add(procName);
  }

  protected StateTimeoutSentence getStateTimeoutSentenceByPath(ThingSentenceSet thing,
                                               ILinkEntry link, String path,
                                               String stateName) throws
    Exception {
  int size = thing.getStateTimeoutSentence().size();
  StateTimeoutSentence sentence;
  for (int i = 0; i < size; i++) {
    sentence = (StateTimeoutSentence) thing.getStateTimeoutSentence().get(i);
    if(sentence.getStateName().equals(stateName)){
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
        if(this.helper.setLogicBySource(link).equals(sentence.getLogicalRelation()))
        return sentence;
      }
    }
  }
  sentence = helper.buildStateTimeoutSentence(link, path);
  thing.getStateTimeoutSentence().add(sentence);
  return sentence;
  }

  protected void addStateTimeoutSentenceDestination(StateTimeoutSentence sentence,
                                             ILinkEntry link) {
    String procName = helper.builder.myStructure.getIEntry(link.
        getDestinationId()).getName();
    sentence.getTriggeredProcess().add(procName);
  }

  protected StateEntranceSentence getStateEntranceSentenceByPath(ThingSentenceSet thing,
                                               ILinkEntry link, String path,
                                               String stateName) throws
    Exception {
  int size = thing.getStateEntranceSentence().size();
  StateEntranceSentence sentence;
  for (int i = 0; i < size; i++) {
    sentence = (StateEntranceSentence) thing.getStateEntranceSentence().get(i);
    if(sentence.getStateName().equals(stateName)){
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
        if(this.helper.setLogicBySource(link).equals(sentence.getLogicalRelation()))
          return sentence;
      }
    }
  }
  sentence = helper.buildStateEntranceSentence(link, path);
  thing.getStateEntranceSentence().add(sentence);
  return sentence;
  }

  protected void addStateEntranceSentenceDestination(StateEntranceSentence sentence,
                                             ILinkEntry link) {
    String procName = helper.builder.myStructure.getIEntry(link.
        getDestinationId()).getName();
    sentence.getTriggeredProcess().add(procName);
  }


/*******************************************************************/
/*               STATES                                            */
/*******************************************************************/

  /*************************************************/

//Adds states in way sorted!!!
  protected void addStates(IObjectEntry abstractEntry, ThingSentenceSet thing) {
    java.util.List list = OPLGeneral.sort(abstractEntry.getStates(),new MyComparator());
    Iterator e = list.iterator();
    String objName = abstractEntry.getName();
    IStateEntry st = null;
    /*for(int i=0;i<list.size();i++){
      //HERE SHOULD BE DEFAULT!!!!!!!!!!!!!!
      if(((IStateEntry)list.get(i)).isInitial()){
        st = (IStateEntry)list.get(i);
        break;
      }
    }if(st!=null){list.remove(st);list.add(0,st);}*/
    try {
      for (int i=0;i<list.size();i++ ){
        IStateEntry state = (IStateEntry)list.get(i);
        if(helper.builder.testEntity(state)){
          addStateObjRel(thing, objName);
          break;
        }
      }
      while (e.hasNext()) {
        IStateEntry state = (IStateEntry) e.next();
        if(!helper.builder.testEntity(state))
          continue;
        Enumeration lks = state.getLinksBySource();
        String stateName = state.getName();
        java.util.List links = OPLGeneral.sort(lks,new MyLinksComparator());
        for(int i=0;i<links.size();i++) {
          ILinkEntry link = (ILinkEntry) links.get(i);
          if(helper.builder.testEntity(link)){
            specificLink(thing, objName, stateName, link);
          }
        }
        OPLGeneral.addLast(thing.getObjectStateSentence().getStateClause(),
                           helper.buildStateClause(state));
      }
    }
    catch (Exception t) {
      t.printStackTrace();
    }
  }

  private void addStateObjRel(ThingSentenceSet thing,
                              String objName) throws Exception {
    try {
      if (thing.getObjectStateSentence() == null) {
        thing.setObjectStateSentence(
            helper.buildObjStateSentence(objName));
      }
    }
    catch (Exception e) {
      throw e;
    }
  }


/*******************************************************************/
 /*               MAIN ADDING                                     */
/*******************************************************************/

  public boolean testRelation(IRelationEntry rel) {
    return helper.builder.testEntity(rel);
  }

  public boolean fillLinks(Enumeration e, String objName,
                           ThingSentenceSet thing) throws Exception {
    boolean hasLink = false;
    ILinkEntry link;
    java.util.List lst = OPLGeneral.sort(e,new MyLinksComparator());
    for(int i=0;i<lst.size();i++) {
      link = (ILinkEntry) lst.get(i);
      if (helper.builder.testEntity(link) &&
          specificLink(thing, objName, " ", link))
        hasLink = true;
    }
    return hasLink;
  }

  public boolean fillThingSentence(IObjectEntry abstractEntry,
                                   ThingSentenceSet thing) {
    String objName = abstractEntry.getName();
    Enumeration e = abstractEntry.getRelationBySource();
    int type;
    boolean hasRelations = false;
    boolean hasEnvironmentalPhysical = false;
    boolean hasLinks = false;
    boolean hasZooming = false;
    try {
      if(helper.builder.isMainElement(abstractEntry)){
        hasZooming = createObjectInZoomingSentence(thing, abstractEntry);
      }
      for (int rule = 0; rule <= 1; rule++) {
        if (rule == 1) {
          e = abstractEntry.getRelationByDestination();
          if (!hasRelations)
            hasRelations = fillRelations(e, objName, rule, thing,helper.builder);
          else
            fillRelations(e, objName, rule, thing,helper.builder);
        }
        else
          hasRelations = fillRelations(e, objName, rule, thing,helper.builder);
      }
      e = abstractEntry.getLinksBySource();
      if (!hasLinks)
        hasLinks = fillLinks(e, objName, thing);
      else
        fillLinks(e, objName, thing);
      hasEnvironmentalPhysical = addEnvironmentelPhysical(abstractEntry, thing);

      thing.setIsKey(abstractEntry.isKey());
      if(!(hasRelations || hasEnvironmentalPhysical || hasLinks || hasZooming)){
        e = abstractEntry.getStates();
        IStateEntry st;
        if(e.hasMoreElements() && (!OPLGeneral.isBasicType(abstractEntry.getType()))){
          hasLinks =true;
        }
        //while(e.hasMoreElements()){/*
          /*if(hasLinks) break;
          st = (IStateEntry)e.nextElement();
          if(st.getLinksBySource().hasMoreElements()){
            System.err.println(" I am here !!");
            hasLinks = true;
          }
        }*/
      }
      thing.setTransID("0");
      /*Properties aRole = (Properties)allRoles.get(0);<br>
         * 		String roleThingName = aRole.getProperty(RolesManager.THING_NAME);<br>
         * 		String roleLibraryName = aRole.getProperty(RolesManager.LIBRARY_NAME);<br>
*/
     // if(abstractEntry.getRoles()!=null){
          // Inserting role!!!
          
      //}
      if (! (hasRelations || hasEnvironmentalPhysical || hasLinks || hasZooming)) {
        if (abstractEntry.getType().equals("")||abstractEntry.getType().equals("none")) {
          //System.out.println("Object "+abstractEntry.getName()+" ");
          thing.setExistential("exists");
          thing.setTransID("3");
          return true;
        }
        thing.setTypeDeclarationSentence(helper.buildTypeDeclSentence(
            abstractEntry));
		thing.getRole().addAll(helper.buildRoles(abstractEntry));    
        thing.setTransID("1");
      }
      else {
        addStates(abstractEntry, thing);
        setScope(thing, abstractEntry);
		thing.getRole().addAll(helper.buildRoles(abstractEntry));
      }
      addDescription(abstractEntry, thing);
    }
    catch (Exception t) {
      t.printStackTrace();
    }
    return true;
  }

  private void setScope(ThingSentenceSet sentence,IObjectEntry obj){
  if (obj.getScope().equals(OpcatConstants.PRIVATE))
        sentence.setObjectScope("private");
      else {
        if (obj.getScope().equals(OpcatConstants.PROTECTED))
          sentence.setObjectScope("protected");
        else
          sentence.setObjectScope("public");
      }
  }

//  protected boolean createObjectInZoomingSentence(ThingSentenceSet thing,
//      IObjectEntry proc) throws Exception {
//
//    if(!helper.builder.testEntity(proc))
//      return false;
//    Enumeration it = proc.getInstances();
//    Enumeration children;
//    IObjectInstance p;
//    java.util.List tmpObjs = new LinkedList();
//    java.util.List tmpProcs = new LinkedList();
//    while (it.hasMoreElements()) {
//      p = (IObjectInstance) it.nextElement();
//      if (p.isZoomedIn()) {
//        ObjectInZoomingSentenceSet procs =
//            helper.buildObjectInZoomingSentenceSet(proc.getName());
//        ObjectInZoomingSentenceType sentence =
//            procs.getObjectInZoomingSentence();
//        children = p.getChildThings();
//        while (children.hasMoreElements()) {
//          IEntry abstractEntry =
//              ( (IThingInstance) children.nextElement()).getIEntry();
//          if (abstractEntry instanceof IObjectEntry) {
//            tmpObjs.add(abstractEntry);
//            OPLGeneral.addLast(sentence.getInZoomedObject(),abstractEntry.getName());
//          }
//          else if (abstractEntry instanceof IProcessEntry) {
//            tmpProcs.add(abstractEntry);
//            OPLGeneral.addLast(sentence.getInZoomedProcess(),abstractEntry.getName());
//          }
//        }
//        addInZoomedSentences(procs.getThingSentenceSet(), tmpObjs);
//        addInZoomedSentences(procs.getThingSentenceSet(), tmpProcs);
//        thing.setObjectInZoomingSentenceSet(procs);
//        return true;
//      }
//    }
//    return false;
//  }
//
//
//  protected void addInZoomedSentences(java.util.List thing,
//                                      java.util.List entities) {
//    Iterator it = entities.iterator();
//    IEntry abstractEntry;
//    while (it.hasNext()) {
//      abstractEntry = (IEntry) it.next();
//      helper.builder.addThingSentence(thing, abstractEntry);
//    }
//  }

  protected boolean createObjectInZoomingSentence(ThingSentenceSet thing,
      IObjectEntry proc) throws Exception {
    long oldOPD = helper.builder.currOPD;
    if(!helper.builder.isMainElement(proc))
      return false;
    Enumeration it = proc.getInstances();
    IObjectInstance p;
    ObjectInZoomingSentenceSet procs=null;
    java.util.List tmpList = new LinkedList();
    java.util.List tmp = new LinkedList();
    while (it.hasMoreElements()) {
      p = (IObjectInstance) it.nextElement();
      if (p.isZoomedIn()) {
        procs =helper.buildObjectInZoomingSentenceSet(proc.getName());
        helper.builder.currOPD = p.getKey().getOpdId();
        tmpList = OPLGeneral.sort1(((IObjectInstance)p).getChildThings(),
                                new MyThingsComparator(true, this.helper.builder));
        break;
      }
    }
    if(procs!=null){
      addZoomingThings(procs, tmpList);
      thing.setObjectInZoomingSentenceSet(procs);
      helper.builder.currOPD =  oldOPD;
      return true;
    }
    helper.builder.currOPD =  oldOPD;
    return false;
  }

  protected void addZoomingThings(ObjectInZoomingSentenceSet process, java.util.List tmpList)
  throws Exception{
    for(int i=0;i<tmpList.size();i++){
      IEntry ent = (IEntry)tmpList.get(i);
      if(ent instanceof IObjectEntry){
        process.getObjectInZoomingSentence().getInZoomedObject().add(ent.getName());
      }else if(ent instanceof IProcessEntry){
        process.getObjectInZoomingSentence().getInZoomedProcess().add(ent.getName());
      }
    try{
      //ThingSentenceSet s = helper.buildThingSentence(ent);
      helper.builder.addThingSentence(process.getThingSentenceSet(),ent);
      //process.getThingSentenceSet().add(helper.buildThingSentence(ent));
    }catch (Exception e){;}
    }
  }

  protected boolean addEnvironmentelPhysical(IObjectEntry abstractEntry,
                                             ThingSentenceSet thing) throws
      Exception {
    if (abstractEntry.isPhysical() || abstractEntry.isEnvironmental()) {
      thing.setObjectEnvironmentalPhysicalSentence
          (helper.buildObjEnvPhysicalSentence(abstractEntry));
      return true;
    }
    return false;
  }

}
