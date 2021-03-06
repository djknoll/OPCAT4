package extensionTools.opltoopd;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JOptionPane;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.IXCheckResult;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXLinkEntry;
import exportedAPI.opcatAPIx.IXLinkInstance;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXObjectInstance;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXProcessInstance;
import exportedAPI.opcatAPIx.IXRelationEntry;
import exportedAPI.opcatAPIx.IXRelationInstance;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXStateInstance;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXSystemStructure;
import exportedAPI.opcatAPIx.IXThingInstance;

/**
 * Represents class which takes care
 * of new sentence adding to the OPD.
 */

public class NewSentenceAdding {

  /**
   * @param system the running IXSystem
   */

  public NewSentenceAdding(IXSystem system) {
    iXSystem = system;
    curOpdId = iXSystem.getCurrentIXOpd().getOpdId();
    systStruct = iXSystem.getIXSystemStructure();
    entryInProjectEnum = systStruct.getElements();
    opdsEnum = systStruct.getOpds();
  }

  /** This function returns an existing Object Instance in the needed OPD
   * if it already was in the system or the new Object Instance
   * (the existence of the Object Instance is checked
   * according to the object name and given OPD)
   * @param newSent an Object for checking
   * @param opdId an OPD ID of the needed OPD
   * @param newOne true if we added this Object in the running session.
   * @return IXObjectInstance
   */
  public IXObjectInstance newObjectAdding(ObjectStruct newSent,long opdId,boolean newOne){
    newOne = false;
    if(opdId==1111){
      return null;
    }
    if(opdId==0){
      opdId = curOpdId;
    }

    if (ifNewThing(newSent.getObjectName())){
      FreeSpaceList list = new FreeSpaceList(iXSystem,opdId);
      list.updateList(spareSpaceForNew);

      IXObjectInstance newObj = iXSystem.addObject(newSent.getObjectName(),50,30,opdId);
      if(!newSent.States.isEmpty()){
        Iterator iter = newSent.States.iterator();
        while(iter.hasNext()){
          State state = (State)iter.next();
          IXStateEntry newStateEntry = iXSystem.addState(state.getStateName(),newObj);
          newStateUpdate(state,newStateEntry);
          newOne = true;
        }
      }
      addThingToList(newObj, list);
      newObjectUpdate(newSent, newObj);
      return newObj;
    }
    Enumeration enum = getThingEntry(newSent.getObjectName()).getInstances();
    IXObjectInstance existingObj = null;
    while(enum.hasMoreElements()){
      existingObj = (IXObjectInstance)enum.nextElement();
      if(existingObj.getKey().getOpdId()==opdId){
        if(!newSent.States.isEmpty()){
          Iterator iter = newSent.States.iterator();
          while(iter.hasNext()){
            State state = (State)iter.next();
            Enumeration objStatesEnum = existingObj.getStateInstances();
            boolean stateFlag=true;
            while(objStatesEnum.hasMoreElements()){
              IXStateInstance newState = (IXStateInstance)objStatesEnum.nextElement();
              if ((newState.getIXEntry().getName()).equals(state.getStateName())){
                newStateUpdate(state,(IXStateEntry)newState.getIXEntry());
                stateFlag=false;
              }
            }
            if (stateFlag){
              IXStateEntry newStateEntry = iXSystem.addState(state.getStateName(),existingObj);
              newStateUpdate(state,newStateEntry);
            }
          }
        }
        newObjectUpdate(newSent, existingObj);
        newOne = false;
        return existingObj;
      }
    }
    FreeSpaceList list = new FreeSpaceList(iXSystem,opdId);
    list.updateList(spareSpaceForNew);
    IXObjectInstance newObj = iXSystem.addObject(newSent.getObjectName(),50,30,opdId);
    if(!newSent.States.isEmpty()){
      Iterator iter = newSent.States.iterator();
      while(iter.hasNext()){
        State state = (State)iter.next();
        IXStateEntry newStateEntry = iXSystem.addState(state.getStateName(),newObj);
        newStateUpdate(state,newStateEntry);
      }
    }
    addThingToList(newObj, list);
    newObjectUpdate(newSent, newObj);
    newOne = true;
    return newObj;
  }

  /**This function returns an existing Process Instance in the needed OPD
   * if it already was in the system or the new Process Instance
   * (the existence of the Process Instance is checked
   * according to the process name and given OPD)
   * @param newSent a Process for checking
   * @param opdId an OPD ID of the needed OPD
   * @param newOne true if we added this Object in the running session.
   * @return IXProcessInstance
   */

  public IXProcessInstance newProcessAdding(ProcessStruct newSent,long opdId, boolean newOne){
    newOne = false;
    if(opdId==1111){
      return null;
    }
    if (opdId==0){
      opdId = curOpdId;
    }
    if (ifNewThing(newSent.getProcessName())){
      FreeSpaceList list = new FreeSpaceList(iXSystem,opdId);
      list.updateList(spareSpaceForNew);
      IXProcessInstance newProc = iXSystem.addProcess(newSent.getProcessName(),50,30,opdId);
      addThingToList(newProc, list);
      newProcessUpdate(newSent, newProc);
      newOne = true;
      return newProc;
    }
    Enumeration enum = getThingEntry(newSent.getProcessName()).getInstances();
    IXProcessInstance existingProc = null;
    /*if process is new in hierarchy then it is one and only instance for entry
    and we will return it.
    if there is no hierarchy, and there are one or few instances then will
    return instance on current opd(if exists) or any other   */
    while(enum.hasMoreElements()){
      existingProc = (IXProcessInstance)enum.nextElement();
      if(existingProc.getKey().getOpdId()==opdId){
        newProcessUpdate(newSent,existingProc);
        return existingProc;
      }
    }
    FreeSpaceList list = new FreeSpaceList(iXSystem,opdId);
    list.updateList(spareSpaceForNew);
    IXProcessInstance newProc = iXSystem.addProcess(newSent.getProcessName(),50,30,opdId);
    addThingToList(newProc, list);
    newProcessUpdate(newSent, newProc);
    newOne = true;
    return newProc;
  }

  /**This function check with user to which OPD he wants to add the new Link sentence
   * and adds it to the needed OPD
   * @param newSent the new sentence
   */
  public void newLinkSentence(LinkStruct newSent){
    boolean objFlag;
    IXObjectInstance sourceObj = null;
    IXProcessInstance sourceProc = null;
    IXObjectInstance destObj;
    IXProcessInstance destProc;
    IXObjectEntry sourceObjEntry, destObjEntry;
    IXProcessEntry sourceProcEntry, destProcEntry;
    boolean newSourse = false;
    boolean newDest = false;
    long neededOpdId;
    long sourceOpdId = curOpdId;
    Iterator iter = newSent.getDestObjects();
    IXLinkInstance newLink;
    boolean sourceHierarchyFlag = false;
    boolean destHierarchyFlag = false;

    if (ifNewSentence(newSent)){
      neededOpdId = curOpdId;
    }
    else{
      /*Here we give the list of the OPDs to User and he choose the only one she wants */
      HashSet proc_screens = new HashSet();
      HashSet obj_screens = new HashSet();
      if (newSent.getSourceProcess()!=null){
        if (!ifNewThing(newSent.getSourceProcess().getProcessName())){
          Enumeration insts  = getThingEntry(newSent.getSourceProcess().getProcessName()).getInstances();
          while(insts.hasMoreElements()){
            long key = (((IXProcessInstance)insts.nextElement()).getKey()).getOpdId();
            proc_screens.add((systStruct.getIXOpd(key)));
      }}}
      if (newSent.getSourceObject()!=null){
        if (!ifNewThing(newSent.getSourceObject().getObjectName())){
          Enumeration insts  = getThingEntry(newSent.getSourceObject().getObjectName()).getInstances();
          while(insts.hasMoreElements()){
            long key = (((IXObjectInstance)insts.nextElement()).getKey()).getOpdId();
            obj_screens.add((systStruct.getIXOpd(key)));
      }}}
      while(iter.hasNext()){
        ObjectStruct curDestObj = (ObjectStruct)iter.next();
        if (!ifNewThing(curDestObj.getObjectName())){
          Enumeration insts  = getThingEntry(curDestObj.getObjectName()).getInstances();
          while(insts.hasMoreElements()){
            long key = (((IXObjectInstance)insts.nextElement()).getKey()).getOpdId();
            obj_screens.add((systStruct.getIXOpd(key)));
      }}}
      iter = newSent.getDestProcesses();
      while(iter.hasNext()){
        ProcessStruct curDestProcess = (ProcessStruct)iter.next();
        if (!ifNewThing(curDestProcess.getProcessName())){
          Enumeration insts  = getThingEntry(curDestProcess.getProcessName()).getInstances();
          while(insts.hasMoreElements()){
            long key = (((IXProcessInstance)insts.nextElement()).getKey()).getOpdId();
            obj_screens.add((systStruct.getIXOpd(key)));
      }}}
      if (!proc_screens.isEmpty()){
        neededOpdId = getNumFromUser(proc_screens);
      }
      else {neededOpdId = getNumFromUser(obj_screens);}
      /****************************  end of OPD choose ********************************* */
    }
    iter = newSent.getDestObjects();
    /********************if the source is Object*************/
    if (newSent.getSourceObject()!=null){
      sourceObj = newObjectAdding(newSent.getSourceObject(),neededOpdId,newSourse);
      if (sourceObj==null){
        return;
      }
      /**********if the destinations are Objects*************/
      while(iter.hasNext()){
        ObjectStruct curDestObj = (ObjectStruct)iter.next();
        destObj = newObjectAdding(curDestObj,neededOpdId, newDest);
        if (destObj==null){
          return;
        }
        addnewLink (sourceObj,destObj,newSent,newSourse,newDest);
      }
      iter = newSent.getDestProcesses();
      while(iter.hasNext()){
        /**********if the destinations are Processes****************/
        ProcessStruct curDestProc = (ProcessStruct)iter.next();
        destProc = newProcessAdding(curDestProc,neededOpdId,newDest);
        if (destProc==null) return;
        /*adding Object or his states*/
        if (newSent.getSourceObject().States.isEmpty()){
          addnewLink (sourceObj,destProc,newSent,newSourse,newDest);
        }
        else{
          Enumeration objStatesEnum = sourceObj.getStateInstances();
          while (objStatesEnum.hasMoreElements()){
            Iterator iterStates = newSent.getSourceObject().States.iterator();
            while (iterStates.hasNext()){
              IXStateInstance sourceState = (IXStateInstance)objStatesEnum.nextElement();
              if(sourceState.getIXEntry().getName().equals(((State)iterStates.next()).getStateName())){
                addnewLink (sourceState,destProc,newSent,newSourse,newDest);
    }}}}}}
    /**************************if the source is Process***********************************************/
    if (newSent.getSourceProcess()!=null){
      sourceProc = newProcessAdding(newSent.getSourceProcess(),neededOpdId,newSourse);
      if(sourceProc==null) return;
      while(iter.hasNext()){
        /*************-------------if the destination are Objects------------*******************/
        ObjectStruct curDestObj = (ObjectStruct)iter.next();
        destObj = newObjectAdding(curDestObj,neededOpdId,newDest);
        if(destObj==null)return;
        if (curDestObj.States.isEmpty()){
          int linkType = getType(newSent.getLinkType());
          /*2 link sentences*/
          Enumeration stateEnum = destObj.getStateInstances();
          while(stateEnum.hasMoreElements()){
            IXStateInstance stateInst = (IXStateInstance)stateEnum.nextElement();
            IXLinkInstance oldLink = iXSystem.getIXLinkBetweenInstances(stateInst ,sourceProc);
            if (oldLink!=null){
              if (((IXLinkEntry)oldLink.getIXEntry()).getLinkType()==consts.INSTRUMENT_EVENT_LINK){
                if(newSent.getLinkType().equals("requires")) return;
                if(newSent.getLinkType().equals("consumes")){
                  newSent.setLinkType("cons_event");
                  iXSystem.delete(oldLink);
            }}}
            addnewLink (stateInst,sourceProc,newSent,newSourse,newDest);
            return;
          }
          IXLinkInstance oldLink = iXSystem.getIXLinkBetweenInstances(destObj,sourceProc);
          if (oldLink!=null){
            if (((IXLinkEntry)oldLink.getIXEntry()).getLinkType()==consts.INSTRUMENT_EVENT_LINK){
              if(newSent.getLinkType().equals("requires")) return;
            if(newSent.getLinkType().equals("consumes")){
              newSent.setLinkType("cons_event");
              iXSystem.delete(oldLink);
          }}}         if ((newSent.getLinkType().equals("cons_event"))||
              (newSent.getLinkType().equals("occurs"))||(linkType==consts.INSTRUMENT_LINK)||
              (newSent.getLinkType().equals("consumes"))) {
            addnewLink (destObj,sourceProc,newSent,newSourse,newDest);
          }
          else{
            addnewLink (sourceProc,destObj,newSent,newSourse,newDest);
        }}
        else{
          if (newSent.getLinkType().equals("changes")){
            changesLink(sourceProc,destObj,curDestObj,newSent,newSourse,newDest);
          }
          else
          {/* state are need to be connected*/
            Enumeration objStatesEnum = destObj.getStateInstances();
            while (objStatesEnum.hasMoreElements()){
              Iterator iterStates = curDestObj.States.iterator();
              while (iterStates.hasNext()){
                IXStateInstance destState = (IXStateInstance)objStatesEnum.nextElement();
                if(destState.getIXEntry().getName().equals(((State)iterStates.next()).getStateName())){
                  int linkType = getType(newSent.getLinkType());
                  IXLinkInstance oldLink = iXSystem.getIXLinkBetweenInstances(destState,sourceProc);
                  if (oldLink!=null){
                    int oldtype = ((IXLinkEntry)oldLink.getIXEntry()).getLinkType();
                    if (oldtype==consts.INSTRUMENT_EVENT_LINK){

                      if(newSent.getLinkType().equals("requires")) return;
                      if(newSent.getLinkType().equals("consumes"))
                        newSent.setLinkType("cons_event");
                  }}
                  if ((newSent.getLinkType().equals("occurs"))||(linkType==consts.INSTRUMENT_LINK)||
                      (newSent.getLinkType().equals("consumes"))){
                    addnewLink (destState,sourceProc,newSent,newSourse,newDest);
                  }
                  else{
                    addnewLink (sourceProc,destState,newSent,newSourse,newDest);
      }}}}}}}
      iter = newSent.getDestProcesses();
      while(iter.hasNext()){
        ProcessStruct curDestProc = (ProcessStruct)iter.next();
        destProc = newProcessAdding(curDestProc,neededOpdId,newDest);
        if (destProc==null) return;
        addnewLink (sourceProc,destProc,newSent,newSourse,newDest);
    }}
    return;
  }

  private void changesLink(IXProcessInstance proc,IXObjectInstance objInst,ObjectStruct objStr,LinkStruct newSent,boolean newProc, boolean newObj){

    Iterator stateIter = objStr.States.iterator();
    while(stateIter.hasNext()){
      State stateStr = (State)stateIter.next();
      Enumeration stateEnum = objInst.getStateInstances();
      while (stateEnum.hasMoreElements()){
        IXStateInstance stateInst = (IXStateInstance)stateEnum.nextElement();
        if (stateStr.getStateName().equals(stateInst .getIXEntry().getName())){
          if (stateStr.getFrom()){
            newLinkUpdate(newSent, iXSystem.addLink(stateInst,proc,consts.CONSUMPTION_LINK),"");
          }
          else{
            newLinkUpdate(newSent, iXSystem.addLink(proc,stateInst,consts.CONSUMPTION_LINK),"");
          }
        }
      }
    }
  }
/**
 *This function checks the legality of the new Link
 * @param source the link source
 * @param dest the link destination
 * @param newSent the new link sentence
 */
  public void addnewLink (IXConnectionEdgeInstance source, IXConnectionEdgeInstance dest,LinkStruct newSent,boolean newSource, boolean newDest){
    IXCheckResult res = iXSystem.checkLinkAddition(source,dest,getType(newSent.getLinkType()));
    if (res.getResult()==res.RIGHT){
      newLinkUpdate(newSent, iXSystem.addLink(source,dest,getType(newSent.getLinkType())),"");
    }
    if (res.getResult()==res.WRONG) {
      if (iXSystem.checkDeletion(source).getResult()==res.RIGHT)
        if (newSource){
          iXSystem.delete(source);
        }
      if (iXSystem.checkDeletion(dest).getResult()==res.RIGHT)
        if(newDest){
          iXSystem.delete(dest);
        }
      JOptionPane.showMessageDialog(iXSystem.getMainFrame(),res.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

    }
    if (res.getResult()==res.WARNING){
      int conf = JOptionPane.showConfirmDialog(iXSystem.getMainFrame(),res.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
      if (conf==JOptionPane.OK_OPTION)
        newLinkUpdate(newSent, iXSystem.addLink(source,dest,getType(newSent.getLinkType())),"");
      else{
        if (iXSystem.checkDeletion(source).getResult()==res.RIGHT)
           if (newSource){
             iXSystem.delete(source);
           }
        if (iXSystem.checkDeletion(dest).getResult()==res.RIGHT)
          if(newDest){
            iXSystem.delete(dest);
          }
      }
    }
  }


  /**This function gets from user the OPD to which he wants to add the new Relation sentence
   * and adds it to the needed OPD
   * @param newSent the new sentence
   */
  public void newRelationSentence(RelationStruct newSent){
    boolean objFlag;
    IXObjectInstance sourceObj = null;
    IXProcessInstance sourceProc = null;
    IXObjectInstance destObj;
    IXProcessInstance destProc;
    IXObjectEntry sourceObjEntry, destObjEntry;
    IXProcessEntry sourceProcEntry, destProcEntry;
    long neededOpdId;
    long sourceOpdId = curOpdId;
    boolean newSourse = false;
    boolean newDest = false;
    Iterator iter = newSent.getDestObjects();
    IXRelationInstance newRelation;
    boolean sourceHierarchyFlag = false;
    boolean destHierarchyFlag = false;


    if (ifNewSentence(newSent)){
      neededOpdId = curOpdId;
    }
    else{
      /*Here we give the list of the OPDs to User and he choose the only one she wants */
      HashSet proc_screens = new HashSet();
      HashSet obj_screens = new HashSet();
      if (newSent.getSourceProcess()!=null){
        if (!ifNewThing(newSent.getSourceProcess().getProcessName())){
          Enumeration insts  = getThingEntry(newSent.getSourceProcess().getProcessName()).getInstances();
          while(insts.hasMoreElements()){
            long key = (((IXProcessInstance)insts.nextElement()).getKey()).getOpdId();
            proc_screens.add((systStruct.getIXOpd(key)));
          }}}
      if (newSent.getSourceObject()!=null){
        if (!ifNewThing(newSent.getSourceObject().getObjectName())){
          Enumeration insts  = getThingEntry(newSent.getSourceObject().getObjectName()).getInstances();
          while(insts.hasMoreElements()){
            long key = (((IXObjectInstance)insts.nextElement()).getKey()).getOpdId();
            obj_screens.add((systStruct.getIXOpd(key)));
          }}  }
      while(iter.hasNext()){
        ObjectStruct curDestObj = (ObjectStruct)iter.next();
        if (!ifNewThing(curDestObj.getObjectName())){
          Enumeration insts  = getThingEntry(curDestObj.getObjectName()).getInstances();
          while(insts.hasMoreElements()){
            long key = (((IXObjectInstance)insts.nextElement()).getKey()).getOpdId();
            obj_screens.add((systStruct.getIXOpd(key)));
          }}}
     iter = newSent.getDestProcesses();
      while(iter.hasNext()){
        ProcessStruct curDestProcess = (ProcessStruct)iter.next();
        if (!ifNewThing(curDestProcess.getProcessName())){
          Enumeration insts  = getThingEntry(curDestProcess.getProcessName()).getInstances();
          while(insts.hasMoreElements()){
            long key = (((IXProcessInstance)insts.nextElement()).getKey()).getOpdId();
            obj_screens.add((systStruct.getIXOpd(key)));
          }
        }
      }
      if (!proc_screens.isEmpty()){
        neededOpdId = getNumFromUser(proc_screens);
      }
      else {neededOpdId = getNumFromUser(obj_screens);}
      /*-------------------  end of OPD choose------------------------------------  */
    }
     iter = newSent.getDestObjects();
     if (newSent.getSourceObject()!=null){/*if the source is Object*/

         sourceObj = newObjectAdding(newSent.getSourceObject(),neededOpdId,newSourse);
         if (sourceObj==null){
           return;
         }
          while(iter.hasNext()){
            ObjectStruct curDestObj = (ObjectStruct)iter.next();
            destObj = newObjectAdding(curDestObj,neededOpdId,newDest);
            if (destObj==null){
              return;
            }
            addnewRelation (sourceObj,destObj,newSent,curDestObj.DestinationCardinality);
          }
          iter = newSent.getDestProcesses();
          while(iter.hasNext()){
            ProcessStruct curDestProc = (ProcessStruct)iter.next();
            destProc = newProcessAdding(curDestProc,neededOpdId,newDest);
            if (destProc==null) return;
            addnewRelation (sourceObj,destProc,newSent, curDestProc.DestinationCardinality);
          }
        }
        if (newSent.getSourceProcess()!=null){/*if the source is Process*/
           sourceProc = newProcessAdding(newSent.getSourceProcess(),neededOpdId,newSourse);
           if(sourceProc==null) return;
            while(iter.hasNext()){
              ObjectStruct curDestObj = (ObjectStruct)iter.next();
              destObj = newObjectAdding(curDestObj,neededOpdId, newDest);
              if(destObj==null)return;
              addnewRelation (sourceProc,destObj,newSent,curDestObj.DestinationCardinality);
            }
            iter = newSent.getDestProcesses();
            while(iter.hasNext()){
              ProcessStruct curDestProc = (ProcessStruct)iter.next();
              destProc = newProcessAdding(curDestProc,neededOpdId,newDest);
              if (destProc==null) return;
              addnewRelation (sourceProc,destProc,newSent,curDestProc.DestinationCardinality);
            }
        }
        return;
  }
  /**
   *This function checks the legality of the new Relation
   * @param source the relation source
   * @param dest the relation destination
   * @param newSent the new relation sentence
 */
  public void addnewRelation (IXConnectionEdgeInstance source, IXConnectionEdgeInstance dest,RelationStruct newSent, String card){
    IXCheckResult res = iXSystem.checkRelationAddition(source,dest,getType(newSent.getRelationType()));
    if (res.getResult()==res.RIGHT){
      newRelationUpdate(newSent, iXSystem.addRelation(source,dest,getType(newSent.getRelationType())),card);
    }
    if (res.getResult()==res.WRONG) {
      if (iXSystem.checkDeletion(source).getResult()==res.RIGHT)
        iXSystem.delete(source);
      if (iXSystem.checkDeletion(dest).getResult()==res.RIGHT)
        iXSystem.delete(dest);
      JOptionPane.showMessageDialog(iXSystem.getMainFrame(),res.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

    }
    if (res.getResult()==res.WARNING){
      int conf = JOptionPane.showConfirmDialog(iXSystem.getMainFrame(),res.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
      if (conf==JOptionPane.OK_OPTION)
        newRelationUpdate(newSent, iXSystem.addRelation(source,dest,getType(newSent.getRelationType())),card);
      else{
        if (iXSystem.checkDeletion(source).getResult()==res.RIGHT)
          iXSystem.delete(source);
        if (iXSystem.checkDeletion(dest).getResult()==res.RIGHT)
          iXSystem.delete(dest);
      }
    }
  }

  /**
   * This function gets from user the OPD to which  he wants to add the new sentence.
   * @param screens the possible OPD screens.
   * @return an OPD ID of the chosen screen.
   */
    public long getNumFromUser(HashSet screens)
     {
       if (screens.size()==1) return ((IXOpd)screens.iterator().next()).getOpdId();
       Iterator iter = screens.iterator();
       Vector possibleValues = new Vector();
       JList list =  new JList(possibleValues );
       int i=0;
       long needOpdId;
        while(iter.hasNext()){
            possibleValues.add(((IXOpd)iter.next()).getName());
        }

       Object selectedValue = JOptionPane.showInputDialog(oPLComboBox,
           " Some of the things in your sentence are already appeared in the project. \n Please choose the OPD in which you want to add your new sentence.", " ",
           JOptionPane.QUESTION_MESSAGE, null,
           possibleValues.toArray(), null/*possibleValues*/);

       if (selectedValue!=null){
       String opdName = selectedValue.toString();

         Enumeration enum = iXSystem.getIXSystemStructure().getOpds();
         while(enum.hasMoreElements()){
           IXOpd opd = (IXOpd)enum.nextElement();
           if(opdName==opd.getName()){
             needOpdId = opd.getOpdId();
             return needOpdId;
           }
         }
       }
       return 1111;
     }


  private IXEntry getThingEntry (String name){
    Enumeration enum = systStruct.getElements();
    IXEntry entry;
    while (enum.hasMoreElements()){
      entry = (IXEntry)enum.nextElement();
      String thingName = entry.getName();
      if(thingName.equals(name)){
        return entry;
      }
    }
   return null;
 }


  private void addThingToList(IXThingInstance thing,FreeSpaceList list)
  {
    int thing_x = thing.getX();
    int thing_y = thing.getY();
    int thing_w = thing.getWidth();
    int thing_h = thing.getHeight();
    Iterator iter = list.getSpace();
    while (iter.hasNext()){
      FreeArea area = (FreeArea)iter.next();
      int area_x = area.getX();
      int area_y = area.getY();
      int area_w = area.getWidth();
      int area_h = area.getHeight();

      if ((spareSpaceForNew + thing_w <= area_w)&&(spareSpaceForNew + thing_h<=area_h)){
        thing.setLocation(area_x + spareSpaceForNew,area_y + spareSpaceForNew);
        return;
      }
    }
    thing.setLocation(900,Y);
    Y+=100;
  }

/*This function returns true if the thing is completely new*/
  private boolean ifNewThing(String newThingName ){

    Enumeration enum = iXSystem.getIXSystemStructure().getElements();
    String existingEntry;
    while(enum.hasMoreElements()){
      existingEntry = ((IXEntry)enum.nextElement()).getName();
      if (newThingName.compareTo(existingEntry)==0){
        return false;
      }
    }
    return true;
  }


/*This function returns true if the sentence is completely new*/
  private boolean ifNewSentence(LinkStruct newSent){
    Iterator iter = newSent.getDestObjects();
    while (iter.hasNext()){
      if (!ifNewThing(((ObjectStruct)iter.next()).getObjectName())) return false;
    }
    iter = newSent.getDestProcesses();
    while (iter.hasNext()){
      if (!ifNewThing(((ProcessStruct)iter.next()).getProcessName())) return false;
    }
    return true;
  }

  /*This function returns true if the sentence is completely new*/
  private boolean ifNewSentence(RelationStruct newSent){
    ObjectStruct obj = newSent.getSourceObject();
    if (obj==null){
      ProcessStruct proc = newSent.getSourceProcess();
      if (!ifNewThing(proc.getProcessName())) return false;
    }
    else{
      if (!ifNewThing(obj.getObjectName())) return false;
    }
    Iterator iter = newSent.getDestObjects();
    while (iter.hasNext()){
      if (!ifNewThing(((ObjectStruct)iter.next()).getObjectName())) return false;
    }
    iter = newSent.getDestProcesses();
    while (iter.hasNext()){
      if (!ifNewThing(((ProcessStruct)iter.next()).getProcessName())) return false;
    }
    return true;
  }


/**
 * This function updates the attributes of the added Object
 * @param obj the new Object
 * @param inst the new Object instance
 */

  public void newObjectUpdate(ObjectStruct obj, IXObjectInstance inst){
    IXObjectEntry entry = (IXObjectEntry)inst.getIXEntry();
    entry.setEnvironmental(obj.getEnvironmental());
    entry.setPersistent(obj.getIsPersistent());
    entry.setPhysical(obj.getIsPhysical());
    entry.setKey(obj.getIsKey());
    if(obj.getInitialValue()!=null){
      entry.setInitialValue(obj.getInitialValue());
    }
    if(obj.getRole()!=null){
      entry.setRole(obj.getRole());
    }
    if(obj.getScope()!=null){
      if (obj.getScope().equals("piblic"))
      entry.setScope(consts.PUBLIC);
      if (obj.getScope().equals("private"))
      entry.setScope(consts.PRIVATE);
      if (obj.getScope().equals("protected"))
      entry.setScope(consts.PROTECTED);
    }
    if(obj.getObjectType()!=null){
      entry.setType(obj.getObjectType());
    }
    entry.updateInstances();
  }

  /**
 * This function updates the attributes of the added State
 * @param state the new State
 * @param stateEntry the new State Entry
 */

  public void newStateUpdate(State state, IXStateEntry stateEntry)
  {

    stateEntry.setInitial(state.getIsInitial());
    stateEntry.setFinal(state.getIsFinal());
    if (state.getMaxTime()!=null){
      stateEntry.setMaxTime(state.getMaxTime());
    }
    if (state.getMinTime()!=null){
      stateEntry.setMinTime(state.getMinTime());
    }
  }

  /**
   * This function updates the attributes of the added Process
   * @param prc the new Process
   * @param inst the new Process instance
   */
  public void newProcessUpdate(ProcessStruct prc, IXProcessInstance inst){
    IXProcessEntry entry = (IXProcessEntry)inst.getIXEntry();
    entry.setEnvironmental(prc.getEnvironmental());
    entry.setPhysical(prc.getIsPhysical());

    if (prc.getMaxTimeActivation() != null) {
        entry.setMaxTimeActivation(prc.getMaxTimeActivation());
    }

    if (prc.getMinTimeActivation() != null){
        entry.setMinTimeActivation(prc.getMinTimeActivation());
    }
    if (prc.getScope() != null){
        entry.setScope(prc.getScope());
    }
    entry.updateInstances();
  }

  /**
   * This function updates the attributes of the added Link
   * @param link the new Link
   * @param inst the new Link instance
   * @param destCard the destination cardinality
   */
  public void newLinkUpdate(LinkStruct link, IXLinkInstance inst, String destCard){

    IXLinkEntry entry = (IXLinkEntry)inst.getIXEntry();
    if (!(link.getLinkCondition()==null)){
      entry.setCondition(link.getLinkCondition());
    }
    if (!(link.getMaxReaction()==null)){
      entry.setMaxReactionTime(link.getMaxReaction());
    }
    if (!(link.getMinReaction()==null)){
      entry.setMinReactionTime(link.getMinReaction());
    }
    if (!(link.getPath()==null)){
      entry.setPath(link.getPath());
    }
    if (!(link.getLinkCondition()==null)){
      entry.setCondition(link.getLinkCondition());
    }

  }

  /**
   * This function updates the attributes of the added Relation
   * @param rel the new Relation
   * @param inst the new Relation instance
   * @param destCard the destination cardinality
   */

  public void newRelationUpdate(RelationStruct rel, IXRelationInstance inst,String destCard){
    IXRelationEntry entry = (IXRelationEntry)inst.getIXEntry();
    if (!(rel.getBackwardMeaning()==null)){
      entry.setBackwardRelationMeaning(rel.getBackwardMeaning());
    }
    if (rel.getDestinationCardinality()!=null){
      entry.setDestinationCardinality(rel.getDestinationCardinality());
    }
    if (rel.getForwardMeaning()!=null){
      entry.setForwardRelationMeaning(rel.getForwardMeaning());
    }
    if (rel.getSourceCardinality()!=null){
      entry.setSourceCardinality(rel.getSourceCardinality());
    }
    if (destCard!=null){
      entry.setDestinationCardinality(destCard);
    }
    entry.updateInstances();
  }


   private  int getType(String type){
     int intType = 0;
     if (type.compareTo("exhibits")==0) return consts.EXHIBITION_RELATION;
     if (type.compareTo("consists of")==0) return consts.AGGREGATION_RELATION;
     if (type.compareTo("is a")==0) return consts.SPECIALIZATION_RELATION;
     if (type.compareTo("is an instance of")==0) return consts.INSTANTINATION_RELATION;
     if (type.compareTo("relates to")==0) return consts.UNI_DIRECTIONAL_RELATION;
     if (type.compareTo("are related")==0) return consts.BI_DIRECTIONAL_RELATION;
     if (type.compareTo("are equivalent")==0) return consts.BI_DIRECTIONAL_RELATION;
     if (type.compareTo("handles")==0) return consts.AGENT_LINK;
     if (type.compareTo("requires")==0) return consts.INSTRUMENT_LINK;
     if (type.compareTo("consumes")==0) return consts.CONSUMPTION_LINK;
     if (type.compareTo("affects")==0) return consts.EFFECT_LINK;
     if (type.compareTo("yields")==0) return consts.RESULT_LINK;
     if (type.compareTo("triggers")==0) return consts.INVOCATION_LINK;
     if (type.compareTo("inst_event")==0) return consts.INSTRUMENT_EVENT_LINK;
     if (type.compareTo("exception")==0) return consts.EXCEPTION_LINK;
     if (type.compareTo("occurs")==0) return consts.CONDITION_LINK;
     if (type.compareTo("cons_event")==0) return consts.CONSUMPTION_EVENT_LINK;

     return intType;
   }

   /**
    * Return the IXLinkInstance of the needed link if its exists or null.
    * @param linkType the type of needed link.
    * @param source the link source.
    * @param dest the link destination.
    * @param opdId the OPD ID of the neede OPD.
    * @return IXLinkInstance.
    */

  public IXLinkInstance getLinkInstance (int linkType, String source, String dest,long opdId){
    Enumeration enum = systStruct.getLinksInOpd (opdId);
    IXLinkInstance linkInst;
    IXLinkEntry linkEntry;
    while (enum.hasMoreElements()){
      linkInst = (IXLinkInstance)enum.nextElement();
      linkEntry = ( IXLinkEntry)linkInst.getIXEntry();
      if (linkEntry.getLinkType()==linkType){
        long sourceId = getThingId(getThingInstance(source,opdId));
        long destId = getThingId(getThingInstance(dest,opdId));
        if (linkEntry.getDestinationId()==destId && linkEntry.getSourceId()==sourceId){
          return linkInst;
        }
      }
    }
    return null;
  }

  /**
   * Return the IXThingInstance of the needed thing if its exists or null.
   * @param name the name of the needed Thing.
   * @param opdId the OPD ID of the needed OPD.
   * @return IXThingInstance.
    */
  public IXThingInstance getThingInstance (String name, long opdId){
       Enumeration enum = systStruct.getThingsInOpd(opdId);
       IXThingInstance thing;
       IXEntry entry;
       while (enum.hasMoreElements()){
         thing = (IXThingInstance)enum.nextElement();
         entry = thing.getIXEntry();
         String thingName = entry.getName();
         if(thingName.equals(name)){
           return thing;
         }
       }
      return null;
   }

   private long getThingId (IXInstance thing){
          IXEntry entry = thing.getIXEntry();
          return entry.getId();
   }
  private IXSystem iXSystem;
  private long curOpdId;
  private IXSystemStructure systStruct;
  private Enumeration entryInProjectEnum;
  private Enumeration opdsEnum;
  private OpcatConstants consts;
  private int spareSpaceForNew = 50;
  private OPLComboBox oPLComboBox;
  private int Y = 60;
}

