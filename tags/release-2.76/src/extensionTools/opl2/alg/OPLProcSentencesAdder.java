package extensionTools.opl2.alg;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.ILinkEntry;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.IProcessInstance;
import exportedAPI.opcatAPI.IRelationEntry;
import extensionTools.opl2.generated.ChangingClause;
import extensionTools.opl2.generated.ChangingSentence;
import extensionTools.opl2.generated.ConditionClause;
import extensionTools.opl2.generated.ConditionSentence;
import extensionTools.opl2.generated.ConditionSentenceType;
import extensionTools.opl2.generated.ConsumptionClause;
import extensionTools.opl2.generated.ConsumptionSentence;
import extensionTools.opl2.generated.ConsumptionSentenceType;
import extensionTools.opl2.generated.EffectClause;
import extensionTools.opl2.generated.EffectSentence;
import extensionTools.opl2.generated.EffectSentenceType;
import extensionTools.opl2.generated.EnablingClause;
import extensionTools.opl2.generated.EnablingSentence;
import extensionTools.opl2.generated.EnablingSentenceType;
import extensionTools.opl2.generated.ExhibitedObject;
import extensionTools.opl2.generated.Operation;
import extensionTools.opl2.generated.ProcessAggregationSentenceSetType;
import extensionTools.opl2.generated.ProcessExhibitionSentenceSetType;
import extensionTools.opl2.generated.ProcessInZoomingSentenceSet;
import extensionTools.opl2.generated.ProcessInvocationSentence;
import extensionTools.opl2.generated.ProcessTimeoutSentence;
import extensionTools.opl2.generated.ResultClause;
import extensionTools.opl2.generated.ResultSentence;
import extensionTools.opl2.generated.ResultSentenceType;
import extensionTools.opl2.generated.ThingSentenceSet;

/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author OPCAT team
 * @version 1.0
 */

public class OPLProcSentencesAdder
    extends OPLSentencesAdder {

  private OPLProcessFactor helper;

  public OPLProcSentencesAdder(OPLProcessFactor factor) {
    helper = factor;
    ByProc = true;
  }

  protected void addAggregRel(ThingSentenceSet thing,
                              IRelationEntry rel, String objName) throws
  Exception {
    if (thing.getProcessAggregationSentenceSet() == null) {
      thing.setProcessAggregationSentenceSet(
          helper.buildProcAggregSentenceSet(objName));
    }
    addAggregProc(thing.getProcessAggregationSentenceSet(), rel);
  }

  protected void addExhibRel(ThingSentenceSet thing,
                             IRelationEntry rel, String objName) throws
  Exception {
    try {
      if (thing.getProcessExhibitionSentenceSet() == null) {
        thing.setProcessExhibitionSentenceSet(
            helper.buildProcExhibSentenceSet(objName));
      }
      addExhibProc(thing.getProcessExhibitionSentenceSet(), rel);
    }
    catch (Exception e) {
      throw e;
    }
  }
 
  protected void addInhRelSentence(ThingSentenceSet thing,
        IRelationEntry rel) throws Exception {
if(thing.getProcessInheritanceSentenceSet()==null){
thing.setProcessInheritanceSentenceSet(helper.buildProcInhSentenceSet(rel));
}else
thing.getProcessInheritanceSentenceSet().getProcessInheritanceSentence().add(helper.buildProcInhSentence(rel));
}

  protected void addInstRelSentence(ThingSentenceSet thing,
                                             IRelationEntry rel)throws
      Exception{
     thing.setProcessInstanceSentence(helper.buildProcInstSentence(rel));
  }


  protected void addUniDirRelSentence(ThingSentenceSet thing,
                                      IRelationEntry rel) throws Exception {
    OPLGeneral.addLast(thing.getProcessUniDirectionalRelationSentence(),
                       helper.buildProcUniDirSentence(rel));
  }

  protected void addBiDirRelSentence(ThingSentenceSet thing,
                                     IRelationEntry rel, int rule) throws
  Exception {
    OPLGeneral.addLast(thing.getProcessBiDirectionalRelationSentence(),
                       helper.buildProcBiDirSentence(rel, rule));
  }

  protected void addExhibProc(ProcessExhibitionSentenceSetType p,
                              IRelationEntry rel) {
    IEntry destination = helper.getEntry(rel);
    try {
      if (destination instanceof IObjectEntry) {
        ExhibitedObject obj = helper.buildExhibitedObject(destination, rel);
        OPLGeneral.addLast(p.getProcessExhibitionSentence().getExhibitedObject(),
                           obj);
      }
      else {
        Operation op = helper.buildOperation(destination.getName());
        OPLGeneral.addLast(p.getProcessExhibitionSentence().getOperation(), op);
      }
      //System.err.println("Before adding sentence about: "+ destination.getName());
      helper.builder.addThingSentence(p.getThingSentenceSet(), destination);

    }
    catch (Exception e) {
      ;
    }
  }

  protected void addAggregProc(ProcessAggregationSentenceSetType p,
                               IRelationEntry rel) {
    IEntry destination = helper.getEntry(rel);
    try {
      String aggregatedProcess = destination.getName();
      OPLGeneral.addLast(p.getProcessAggregationSentence().getAggregatedProcess(),
                         aggregatedProcess);
      helper.builder.addThingSentence(p.getThingSentenceSet(), destination);
    }
    catch (Exception e) {
      ;
    }
  }

  protected boolean specificLink(ThingSentenceSet thing,
                                 ILinkEntry link, String objName, int rule) throws
  Exception {
    int type = link.getLinkType();
    String path = link.getPath();
    try {
      switch (type) {
        case OpcatConstants.CONDITION_LINK:
          if (OPLGeneral.isByDestination(rule)) {
            addConditionLink(thing, link, objName, path);
            return true;
          }return false;
        case OpcatConstants.CONSUMPTION_LINK:
          if (OPLGeneral.isByDestination(rule)) {
            addConsumptionLink(thing, link, objName, path);
            return true;
          }return false;
        case OpcatConstants.CONSUMPTION_EVENT_LINK:
          if (OPLGeneral.isByDestination(rule)) {
             addConsumptionLink(thing, link, objName, path);
             return true;
           }return false;
        case OpcatConstants.INSTRUMENT_EVENT_LINK:
          if (OPLGeneral.isByDestination(rule)) {
            addEnablingLink(thing, link, objName, path);
            return true;
          }return false;
        case OpcatConstants.EFFECT_LINK:
          if (OPLGeneral.isByDestination(rule)) {
            addEffectLink(thing, link, objName, path);
            return true;
          }return false;
        case OpcatConstants.EXCEPTION_LINK:
          if (!OPLGeneral.isByDestination(rule)) {
            ProcessTimeoutSentence sentence = getProcessTimeoutSentenceByPath(thing, link, path);
            addProcessTimeoutSentenceDestination(sentence, link);
            return true;
          }return false;
        case OpcatConstants.INSTRUMENT_LINK:
          if (OPLGeneral.isByDestination(rule)) {
            addEnablingLink(thing, link, objName, path);
            return true;
          }return false;
        case OpcatConstants.INVOCATION_LINK:
          if (!OPLGeneral.isByDestination(rule)) {
            ProcessInvocationSentence sentence = getProcessInvocationSentenceByPath(thing, link, path);
            addProcessInvocationSentenceDestination(sentence, link);
            return true;
          }return false;
        case OpcatConstants.RESULT_LINK:
          if (!OPLGeneral.isByDestination(rule)) {
            addResultLink(thing, link, objName, path);
            return true;
          }
        default:
          return false;
      }
    }
    catch (Exception e) {
      throw e;
    }
  }

  protected ProcessInvocationSentence getProcessInvocationSentenceByPath(ThingSentenceSet thing,
      ILinkEntry link, String path) throws
  Exception {
    int size = thing.getProcessInvocationSentence().size();
    ProcessInvocationSentence sentence;
    for (int i = 0; i < size; i++) {
      sentence = (ProcessInvocationSentence) thing.getProcessInvocationSentence().get(i);
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
        if(this.helper.setLogicBySource(link).equals(sentence.getLogicalRelation()))
          return sentence;
      }
    }
    sentence = helper.buildProcessInvocationSentence(link, path);
    thing.getProcessInvocationSentence().add(sentence);
    return sentence;
  }

  protected void addProcessInvocationSentenceDestination(ProcessInvocationSentence sentence,
      ILinkEntry link) {
    String procName = helper.builder.myStructure.getIEntry(link.
        getDestinationId()).getName();
    sentence.getTriggeredProcess().add(procName);
  }

  protected ProcessTimeoutSentence getProcessTimeoutSentenceByPath(ThingSentenceSet thing,
      ILinkEntry link, String path) throws
  Exception {
    int size = thing.getProcessTimeoutSentence().size();
    ProcessTimeoutSentence sentence;
    for (int i = 0; i < size; i++) {
      sentence = (ProcessTimeoutSentence) thing.getProcessTimeoutSentence().get(i);
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
      if(this.helper.setLogicBySource(link).equals(sentence.getLogicalRelation()))
        return sentence;
      }
    }
    sentence = helper.buildProcessTimeoutSentence(link, path);
    thing.getProcessTimeoutSentence().add(sentence);
    return sentence;
  }

  protected void addProcessTimeoutSentenceDestination(ProcessTimeoutSentence sentence,
      ILinkEntry link) {
    String procName = helper.builder.myStructure.getIEntry(link.
        getDestinationId()).getName();
    sentence.getTriggeredProcess().add(procName);
  }

  private void addConditionLink(ThingSentenceSet thing,
                                ILinkEntry rel, String objName, String path) throws
  Exception {

    int size = thing.getConditionSentence().size();
    ConditionSentence sentence=null;
    boolean found = false;
    for (int i = 0; i < size; i++) {
      sentence = (ConditionSentence) thing.getConditionSentence().get(i);
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
        if(this.helper.setLogicByDestination(rel).equals(sentence.getLogicalRelation())){
        found=true;
        break;
       }
      }
    }
    if(!found){
      sentence = helper.buildConditionSentence(objName, this.helper.setLogicByDestination(rel), path);
      thing.getConditionSentence().add(sentence);
    }
    addConditionClause(sentence, rel, thing);
  }

  private void addConditionClause(ConditionSentenceType sentence,
                                  ILinkEntry link, ThingSentenceSet thing) throws
  Exception {
    ConditionClause clause = helper.buildConditionClause(link);
    clause.setSubjectAggregationFatherName(thing.
        getSubjectAggregationFatherName());
    clause.setSubjectExhibitionFatherName(thing.getSubjectExhibitionFatherName());
    clause.setSubjectThingName(thing.getSubjectThingName());
    OPLGeneral.addLast(sentence.getConditionClause(), clause);
  }

  protected ResultSentence getResultsSentenceByPath(ThingSentenceSet thing,
      java.util.List path) throws Exception {
    int size = thing.getResultSentence().size();
    ResultSentence sentence;
    for (int i = 0; i < size; i++) {
      sentence = (ResultSentence) thing.getResultSentence().get(i);
      if(OPLGeneral.equalPaths(sentence.getPathLabel(),path))
        if(sentence.getLogicalRelation().equals("and"))
        return sentence;
    }
    return null;
  }

  /***************************************************************************/

  private void addEffectLink(ThingSentenceSet thing,
                             ILinkEntry rel, String objName, String path) throws Exception {
    int size = thing.getEffectSentence().size();
    EffectSentence sentence=null;
    boolean found = false;
    for (int i = 0; i < size; i++) {
      sentence = (EffectSentence) thing.getEffectSentence().get(i);
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
        if(this.helper.setLogicByDestination(rel).equals(sentence.getLogicalRelation())){
          found = true;
          break;
        }
      }
    }
    if(!found){
      sentence = helper.buildEffectSentence(objName, this.helper.setLogicByDestination(rel), path);
      thing.getEffectSentence().add(sentence);
    }
    addEffectClause(sentence, rel, thing);
  }

  private void addEffectClause(EffectSentenceType sentence,
                               ILinkEntry link, ThingSentenceSet thing) throws
  Exception {
    EffectClause clause = helper.buildEffectClause(link);
    clause.setSubjectAggregationFatherName(thing.
        getSubjectAggregationFatherName());
    clause.setSubjectExhibitionFatherName(thing.getSubjectExhibitionFatherName());
    clause.setSubjectThingName(thing.getSubjectThingName());
    OPLGeneral.addLast(sentence.getEffectClause(), clause);
  }

  private void addConsumptionLink(ThingSentenceSet thing,
                                  ILinkEntry rel, String objName, String path) throws
  Exception {
    int size = thing.getConsumptionSentence().size();
    ConsumptionSentence sentence=null;
    boolean found = false;
    for (int i = 0; i < size; i++) {
      sentence = (ConsumptionSentence) thing.getConsumptionSentence().get(i);
      if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
        if(this.helper.setLogicByDestination(rel).equals(sentence.getLogicalRelation())){
          found = true;
          break;
        }
      }
    }
    if(!found){
      sentence = helper.buildConsumptionSentence(objName, this.helper.setLogicByDestination(rel), path);
      thing.getConsumptionSentence().add(sentence);
    }
    addConsumptionClause(sentence, rel, thing);
  }

  private void addConsumptionClause(ConsumptionSentenceType sentence,
                                    ILinkEntry link, ThingSentenceSet thing) throws
  Exception {
    ConsumptionClause clause = helper.buildConsumptionClause(link);
    clause.setSubjectAggregationFatherName(thing.
        getSubjectAggregationFatherName());
    clause.setSubjectExhibitionFatherName(thing.getSubjectExhibitionFatherName());
    clause.setSubjectThingName(thing.getSubjectThingName());
    OPLGeneral.addLast(sentence.getConsumptionClause(), clause);
  }

  private void addEnablingLink(ThingSentenceSet thing,
                               ILinkEntry rel, String objName, String path) throws Exception {
   int size = thing.getEnablingSentence().size();
   EnablingSentence sentence=null;
   boolean found = false;
   for (int i = 0; i < size; i++) {
     sentence = (EnablingSentence) thing.getEnablingSentence().get(i);
     if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
       if(this.helper.setLogicByDestination(rel).equals(sentence.getLogicalRelation())){
         found = true;
         break;
       }
     }
   }
   if(!found){
     sentence = helper.buildEnablingSentence(objName, this.helper.setLogicByDestination(rel), path);
     thing.getEnablingSentence().add(sentence);
   }
    addEnablingClause(sentence, rel, thing);
  }

  private void addEnablingClause(EnablingSentenceType sentence,
                                 ILinkEntry link, ThingSentenceSet thing) throws
  Exception {
    EnablingClause clause = helper.buildEnablingClause(link);
    clause.setSubjectAggregationFatherName(thing.
        getSubjectAggregationFatherName());
    clause.setSubjectExhibitionFatherName(thing.getSubjectExhibitionFatherName());
    clause.setSubjectThingName(thing.getSubjectThingName());
    OPLGeneral.addLast(sentence.getEnablingClause(), clause);
  }

  private void addResultLink(ThingSentenceSet thing,
                             ILinkEntry rel, String objName, String path) throws Exception {
   int size = thing.getResultSentence().size();
   ResultSentence sentence=null;
   boolean found = false;
   for (int i = 0; i < size; i++) {
     sentence = (ResultSentence) thing.getResultSentence().get(i);
     if (OPLGeneral.hasEqualPath(sentence.getPathLabel(), path)) {
       if(this.helper.setLogicBySource(rel).equals(sentence.getLogicalRelation())){
       found=true;
       break;}
     }
   }
   if(!found){
     sentence = helper.buildResultSentence(objName, this.helper.setLogicBySource(rel), path);
     thing.getResultSentence().add(sentence);
   }
    addResultClause(sentence, rel, thing);
  }

  private void addResultClause(ResultSentenceType sentence,
                               ILinkEntry link, ThingSentenceSet thing) throws
  Exception {
    ResultClause clause = helper.buildResultClause(link);
    clause.setSubjectAggregationFatherName(thing.
        getSubjectAggregationFatherName());
    clause.setSubjectExhibitionFatherName(thing.getSubjectExhibitionFatherName());
    clause.setSubjectThingName(thing.getSubjectThingName());
    sentence.getResultClause().add(clause);
  }

  /***********************************************************************/

  public boolean fillLinks(Enumeration e, String objName,
                           ThingSentenceSet thing, int rule) throws Exception {
    boolean hasLink = false;
    ILinkEntry link;
    java.util.List lst = OPLGeneral.sort(e,new MyLinksComparator());
    //while (e.hasMoreElements()) {
    for(int i=0;i<lst.size();i++) {
      link = (ILinkEntry) lst.get(i);

//    while (e.hasMoreElements()) {
  //    link = (ILinkEntry) e.nextElement();
      if (helper.builder.testEntity(link) &&
          specificLink(thing, link, objName, rule))
        hasLink = true;
    }
    return hasLink;
  }

  public boolean testRelation(IRelationEntry rel) {
    return helper.builder.testEntity(rel);
  }

  public boolean fillThingSentence(IProcessEntry abstractEntry,
                                   ThingSentenceSet thing) {
    String objName = abstractEntry.getName();
    Enumeration e = abstractEntry.getRelationBySource();
    boolean hasZooming = false;
    boolean hasLinks = false, hasScope=false;
    boolean hasRelations = false;
    boolean hasEnvironmentalPhysical = false;
    int type;
    try {
     // if(abstractEntry.getRoles()!=null){
          // Inserting role!!!
          
      //}

     if(helper.builder.isMainElement(abstractEntry)){
       hasZooming = createProcessInZoomingSentence(thing, abstractEntry);
     }

      for (int rule = 0; rule <= 1; rule++) {
        if (rule == 1) {
          e = abstractEntry.getRelationByDestination();
        }
          if (!hasRelations)
            hasRelations = fillRelations(e, objName, rule, thing, helper.builder);
          else
            fillRelations(e, objName, rule, thing,helper.builder);
      }
      hasEnvironmentalPhysical = addEnvironmentelPhysical(abstractEntry, thing);
      e = abstractEntry.getLinksBySource();
      for (int rule = 0; rule <= 1; rule++) {
        if (rule == 1) {
          e = abstractEntry.getLinksByDestination();
        }
      if (!hasLinks)
        hasLinks = fillLinks(e, objName, thing,rule);
      else
        fillLinks(e, objName, thing,rule);
      }
      if (hasLinks){
        createChangingSentence(thing);
        createChangingSentence(thing);
        createChangingSentence(thing);
      }
      if(hasZooming || hasLinks || hasRelations || hasEnvironmentalPhysical || hasZooming){
        addDescription(abstractEntry, thing);
        setScope(thing, abstractEntry);
        if(abstractEntry.getRole()!=null && !abstractEntry.getRole().equals("")){
          // Inserting role!!!
          thing.getRole().addAll(helper.buildRoles(abstractEntry));
          //thing.setSubjectRole(abstractEntry.getRole());
        }
        thing.setTransID("2");
		thing.getRole().addAll(helper.buildRoles(abstractEntry));
        return true;
      }
	  thing.setExistential("operates");
	  thing.setTransID("3");
	  return true;
    }
    catch (Exception t) {
      t.printStackTrace();
    }
    return false;
  }


//Changing Clause is OK
  protected void createChangingSentence(ThingSentenceSet thing) throws
  Exception {
    ConsumptionSentenceType cons = null;
    ResultSentenceType resultByPath = null;
    Set tmpResults = new HashSet(), tmpConsumps = new HashSet();
    for(int i=0; i<thing.getConsumptionSentence().size();i++){
      cons = (ConsumptionSentence)thing.getConsumptionSentence().get(i);
      if(!cons.getLogicalRelation().equals("and"))
        continue;
      Iterator clauses = cons.getConsumptionClause().iterator();
      resultByPath = getResultsSentenceByPath(thing,cons.getPathLabel());
      if(resultByPath==null)continue;
      java.util.List results = resultByPath.getResultClause();
      while (clauses.hasNext()) {
        ConsumptionClause cl = (ConsumptionClause) clauses.next();
        if (cl.getStateName() != null) {
          java.util.List res = getResultWithObj(results, cl.getObjectName());
          if(!res.isEmpty()){
            tmpConsumps.add(cl);
            for(Iterator it=res.iterator();it.hasNext();){
              ResultClause ress = (ResultClause)it.next();
              if (ress != null) {
                addChangingClause(thing, ress, cl,cons.getPathLabel());
                tmpResults.add(ress);
              }
            }

          }
        }
      }
      cleanResults(resultByPath, tmpResults);
      cleanConsumps(cons, tmpConsumps);
      tmpResults.clear();
      tmpConsumps.clear();
      cleanThing(thing, resultByPath, cons);
    }
  }

  protected void cleanThing(ThingSentenceSet thing, ResultSentenceType tmpResults,
                            ConsumptionSentenceType tmpConsumps) {
    if(tmpResults.getResultClause().isEmpty()){
      thing.getResultSentence().remove(tmpResults);
    }
    if(tmpConsumps.getConsumptionClause().isEmpty()){
      thing.getConsumptionSentence().remove(tmpConsumps);
    }
  }

  protected void cleanResults(ResultSentenceType tmpResults,
                            java.util.Set closes) {
    tmpResults.getResultClause().removeAll(closes);
  }

  protected void cleanConsumps(ConsumptionSentenceType tmpResults,
                            java.util.Set closes) {
    tmpResults.getConsumptionClause().removeAll(closes);
  }


  protected java.util.List getResultWithObj(java.util.List results,
      String objName) {
    java.util.List ress = new LinkedList();
    ResultClause res;
    if (results == null)
      return null;
    Iterator it = results.iterator();
    while (it.hasNext()) {
      res = (ResultClause) it.next();
      if (res.getStateName() != null)
        if (res.getObjectName() == objName)
          ress.add(res);
    }
    return ress;
  }

//!!!!!!!!!!!!!!00000000000000000000000000000
  protected void addChangingClause(ThingSentenceSet thing,
                                   ResultClause res, ConsumptionClause cl,java.util.List path) throws
  Exception {
    ChangingSentence sentence=null;
    boolean found = false;
    //System.out.println("Creating new changing "+ res.getStateName()+" "+cl.getStateName());
    for (int i = 0; i < thing.getChangingSentence().size(); i++) {
      sentence = (ChangingSentence) thing.getChangingSentence().get(i);
      if (OPLGeneral.equalPaths(sentence.getPathLabel(), path)) {
        found=true;
        break;
      }
    }
    if(!found){
      sentence = helper.buildChangingSentence(res.getSubjectThingName(), res.getLogicalRelation(), path);
      //Here to add sentence according to its path!!!
      addChan(thing.getChangingSentence(),sentence);
      //thing.getChangingSentence().add(sentence);
    }
    ChangingClause change = helper.buildChangingClause(cl, res);
    sentence.getChangingClause().add(change);
  }

  private void addChan(java.util.List chans, ChangingSentence sentence){
    int index=-1;
    for(int i=0;i<chans.size() && index==-1;i++){
       java.util.List pathSource = ((ChangingSentence)chans.get(i)).getPathLabel();
       if(pathSource.size()>sentence.getPathLabel().size())
         index=i;
       else for(int j=0;j<pathSource.size();j++){
           if(pathSource.get(j).toString().compareTo(sentence.getPathLabel().get(j).toString())>-1)
             index = i;
             break;
         }
     }
     if(index==-1)
       chans.add(sentence);
     else
       chans.add(index,sentence);
  }


  private boolean setScope(ThingSentenceSet sentence,IProcessEntry obj){
  if (obj.getScope().equals(OpcatConstants.PRIVATE))
        sentence.setProcessScope("private");
      else {
        if (obj.getScope().equals(OpcatConstants.PROTECTED))
          sentence.setProcessScope("protected");
        else{
          sentence.setProcessScope("public");
          return false;
        }
      }
  return true;
  }


  protected boolean createProcessInZoomingSentence(ThingSentenceSet thing,
      IProcessEntry proc) throws Exception {
    long oldOPD = helper.builder.currOPD;
    if(!helper.builder.testEntity(proc))
      return false;
    Enumeration it = proc.getInstances();
  //  thing.setTransID("2");
    IProcessInstance p;
    ProcessInZoomingSentenceSet procs=null;
    java.util.List tmpList = new LinkedList();
    java.util.List tmp = new LinkedList();
    while (it.hasMoreElements()) {
      p = (IProcessInstance) it.nextElement();
      if (p.isZoomedIn()) {
        procs =helper.buildProcessInZoomingSentenceSet(proc.getName());

        helper.builder.currOPD = p.getKey().getOpdId();
        tmpList = OPLGeneral.sort1(((IProcessInstance)p).getChildThings(),
                                new MyThingsComparator(true, this.helper.builder));


        break;
      }
    }
    if(procs!=null){
      addZoomingThings(procs, tmpList);
      thing.setProcessInZoomingSentenceSet(procs);
      helper.builder.currOPD = oldOPD;
      return true;
    }
    helper.builder.currOPD = oldOPD;
    return false;
  }

  protected void addZoomingThings(ProcessInZoomingSentenceSet process, java.util.List tmpList)
  throws Exception{
    for(int i=0;i<tmpList.size();i++){
      IEntry ent = (IEntry)tmpList.get(i);
      if(ent instanceof IObjectEntry){
        process.getProcessInZoomingSentence().getInZoomedObject().add(ent.getName());
      }else if(ent instanceof IProcessEntry){
        process.getProcessInZoomingSentence().getInZoomedProcess().add(ent.getName());
      }
    try{
      //ThingSentenceSet s = helper.buildThingSentence(ent);
      helper.builder.addThingSentence(process.getThingSentenceSet(),ent);
      //process.getThingSentenceSet().add(helper.buildThingSentence(ent));
    }catch (Exception e){;}
    }
  }


  /********************************************************************/
  /***************************************************************88****8*/
//  protected boolean createProcessInZoomingSentence(ThingSentenceSet thing, IProcessEntry proc)
//throws Exception{
//
//  Enumeration it = proc.getInstances();
//  Enumeration children;
//  IProcessInstance p;
//  java.util.List tmpObjs = new LinkedList();
//  java.util.List tmpProcs = new LinkedList();
//  while(it.hasMoreElements()){
//      p = (IProcessInstance)it.nextElement();
//      if(p.isZoomedIn()){
//        ProcessInZoomingSentenceSet procs =
//            helper.buildProcessInZoomingSentenceSet(proc.getName());
//        ProcessInZoomingSentenceType sentence =
//            procs.getProcessInZoomingSentence();
//        children = p.getChildThings();
//        while(children.hasMoreElements()){
//          IEntry abstractEntry =
//              ((IThingInstance)children.nextElement()).getIEntry();
//          if(abstractEntry instanceof IObjectEntry){
//            tmpObjs.add(abstractEntry);
//            sentence.getInZoomedObject().add(abstractEntry.getName());
//          }
//          else if(abstractEntry instanceof IProcessEntry){
//            tmpProcs.add(abstractEntry);
//            sentence.getInZoomedProcess().add(abstractEntry.getName());
//          }
//        }
//        addInZoomedSentences(procs.getThingSentenceSet(),tmpObjs);
//        addInZoomedSentences(procs.getThingSentenceSet(),tmpProcs);
//        thing.setProcessInZoomingSentenceSet(procs);
//        return true;
//      }
//  }
//return false;
//}
//
//protected void addInZoomedSentences(java.util.List thing, java.util.List entities){
//  Iterator it = entities.iterator();
//  IEntry abstractEntry;
//  while(it.hasNext()){
//    abstractEntry = (IEntry)it.next();
//    helper.builder.addThingSentence(thing,abstractEntry);
//  }
//}


//  protected void addInZoomedSentences(java.util.List thing,
//                                      java.util.List entities) {
//    Iterator it = entities.iterator();
//    IEntry abstractEntry;
//    while (it.hasNext()) {
//      abstractEntry = (IEntry) it.next();
//      helper.builder.addThingSentence(thing, abstractEntry);
//    }
//  }

  protected boolean addEnvironmentelPhysical(IProcessEntry abstractEntry,
      ThingSentenceSet thing) throws
  Exception {
    if (abstractEntry.isPhysical() || abstractEntry.isEnvironmental()) {
      thing.setProcessEnvironmentalPhysicalSentence
      (helper.buildProcEnvPhysicalSentence(abstractEntry));
      return true;
    }
    return false;
  }
  public boolean ByProc;
}