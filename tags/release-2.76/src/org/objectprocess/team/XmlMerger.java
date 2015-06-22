package org.objectprocess.team;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
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
import exportedAPI.opcatAPIx.IXThingEntry;
import exportedAPI.opcatAPIx.IXThingInstance;
import gui.Opcat2;
import gui.opdProject.OpdProject;
import gui.opx.Loader;
import gui.projectStructure.Instance;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import javax.swing.JFileChooser;
import javax.swing.JProgressBar;

/**
 * <p>Title: XmlMerger </p>
 * <p>Description: Merge two opcat structures </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * @author not attributable
 * @version 1.0
 */

public class XmlMerger {
  protected Opcat2 myOpcat2;
  private Enumeration Merged;
  IXSystem clientSystem;
  IXSystem serverSystem;

  public XmlMerger(Opcat2 opcat2) {
    myOpcat2 = opcat2;
  }

   /**
     * Recursive function, go throw tree of Opds and merge between client && server
     *
     * @param parentOpd - parent of opds we are going to merge (null for the root)
     * @return merged system
     */

 public IXSystem mergeOpdTree(IXOpd parentOpd){
   Vector curNodeChildrenOpds = new Vector();
   Enumeration serverOpds = serverSystem.getIXSystemStructure().getOpds();
    while(serverOpds.hasMoreElements()){
     /* find children of parent OPD */
     IXOpd tmpServerOpd = (IXOpd) serverOpds.nextElement();

     if (parentOpd == null) {
       /* case of root OPD*/
       if (tmpServerOpd.getParentIXOpd() == null)
         curNodeChildrenOpds.addElement(tmpServerOpd);
     }
     else if (tmpServerOpd.getParentIXOpd() == null) continue;
     else if (getOpdName(tmpServerOpd.getParentIXOpd()).compareTo(getOpdName(parentOpd))
         == 0) {
       curNodeChildrenOpds.addElement(tmpServerOpd);
     }

   }
   if(curNodeChildrenOpds.isEmpty()) return clientSystem;
   /* merge children */
   merge( curNodeChildrenOpds.elements(),parentOpd);
   for (int i = 0; i< curNodeChildrenOpds.size() ; i++) {
     /* Recursive call for every children (already merged)*/
     mergeOpdTree((IXOpd)curNodeChildrenOpds.elementAt(i));
   }
   return clientSystem;
 }

   /** Merge enumeration of server's opds (having common parent)  into client's system
     * @serverOpds - enumeration of server's opds
     * @param parentOpd - parent of opds we are going to merge
     * @return merged system
     */
  public IXSystem merge( Enumeration serverOpds, IXOpd parentOpd){

    IXSystemStructure clientStruct = (IXSystemStructure) clientSystem.
        getIXSystemStructure();
    IXSystemStructure serverStruct = (IXSystemStructure) serverSystem.
        getIXSystemStructure();
    /**
     * we will go through the servers version and insert every object that
     * dosen't exist in the client version
     * */

    while(serverOpds.hasMoreElements()){
      long clientOpdKey;
      boolean  opd_exist_in_client=false;

      IXOpd tmpServerOpd = (IXOpd)  serverOpds.nextElement();

      Enumeration clientOpds = clientStruct.getOpds();
      while(clientOpds.hasMoreElements()){
	/* check if client has this opd*/
        IXOpd tmpClientOpd = (IXOpd) clientOpds.nextElement();

        if (getOpdName(tmpClientOpd).compareTo(getOpdName(tmpServerOpd)) == 0) {

            /* Client has this OPD, just merge inner structures of OPD*/
            boolean rc = copyOpd(tmpServerOpd,tmpClientOpd) ;
            if(rc == false)
              return null;

            /* Opd was found - no need to look more or to insert this opd*/
            opd_exist_in_client = true;
            break;
        }
      }
      if (!opd_exist_in_client) {
        /* Create new opd in client*/
        createOpd(tmpServerOpd,parentOpd);
      }

    }
    clientSystem.updateChanges();
    return clientSystem;
  }


   /** Create new opd in client and copy internal stucture
    * from server
    * @param serverOpd - opd
    * @param parentOpd - parent of opd we are going to insert
    * @return true if opd was created
    */
  public boolean createOpd(IXOpd serverOpd, IXOpd parentOpd)
  {


   IXSystemStructure serverStruct = serverSystem.getIXSystemStructure();
   // Add opd to client
   IXOpd tmpClientOpd = addOpd(clientSystem,serverSystem,serverOpd);
   if(tmpClientOpd == null)
         return false;
    // copy internal structure
    copyOpd(serverOpd,tmpClientOpd);
    return true;

   }

 /** Merge internal stucture of server into client server
    * @param serverProject - the system of server
    * @param serverOpd - opd to copy from
    * @param clientOpd - target opd
    * @return true if opd was copied
    */
  public boolean copyOpd( IXOpd serverOpd, IXOpd clientOpd) {
     // newadd

    if (clientOpd.getMainIXInstance() != null) {

      clientOpd.getMainIXInstance().setSize(serverOpd.getMainIXInstance().getWidth(),
                                             serverOpd.getMainIXInstance().getHeight());
      clientOpd.getMainIXInstance().setLocation(serverOpd.getMainIXInstance().getX(),
                                             serverOpd.getMainIXInstance().getY());
    }  // --newadd
    IXSystemStructure serverStruct = serverSystem.getIXSystemStructure();
    Enumeration tenum = serverStruct.getInstancesInOpd(serverOpd.getOpdId());
    /* Used to save data about merged entries */
    Vector objectList = new Vector();
    while(tenum.hasMoreElements() == true){
      /* Check instances in server's opd*/
      Instance tObj = (Instance) tenum.nextElement();
      if (tObj instanceof IXThingInstance) {

        NewConnectionEdgeEntry newEntry = new NewConnectionEdgeEntry();
        newEntry.serverEntry = (IXThingEntry)tObj.getIXEntry();
        newEntry.serverKey = tObj.getKey();
        if (checkThingExisting(clientOpd,
                               (IXThingInstance)tObj, newEntry)) {
        /* thing exists in client - no need to add
	   remember the new entry*/
          objectList.addElement(newEntry);


          continue;

        }
       /* insert thing from server*/
        if (tObj instanceof IXObjectInstance) {
          insertObj(clientOpd,(IXObjectInstance)tObj, newEntry);

        } else {
          insertPrc(clientOpd, tObj, newEntry);

        }
         objectList.addElement(newEntry);
      }
    }

      //Done processes now Objects
      Vector statesList = new Vector();
      for (int i=0; i<objectList.size(); i++) {
	  // Merge states in inserted objects
         NewConnectionEdgeEntry entry = (NewConnectionEdgeEntry)objectList.elementAt(i);
         if(entry.serverEntry instanceof IXObjectEntry) {
            Vector newStates  =
             insertStatesOfObject((IXObjectInstance)entry.serverEntry.getIXInstance(entry.serverKey),
                              clientSystem,(IXObjectInstance)entry.clientEntry.getIXInstance(entry.clientKey));
             if (newStates != null) statesList.addAll(newStates);
          }

      }
      objectList.addAll(statesList);
      for (int i=0; i<objectList.size(); i++) {
         NewConnectionEdgeEntry entry = (NewConnectionEdgeEntry)objectList.elementAt(i);
         // Merge links and relations in opd

          insertOpdLnks( (IXConnectionEdgeEntry) entry.serverEntry,
                        clientOpd, serverOpd,
                        entry.clientEntry);

          // copy internal state of entry

          if (!entry.existsInClient) {
            copyEntry(entry.serverEntry,entry.clientEntry);
           // clientSystem.updateChanges();
           }


      }


      return true;
    }


  /** Check the existance of thing in client
    * from server
    * @param clientOpd - target opd
    * @param serverEntry - thing in server
    * @param newEntry - old + new data about the entry
    * @return true if thing exists in client
    */
    // newadd
    private boolean checkThingExisting(
                                      IXOpd clientOpd, IXThingInstance serverIns,
                                      NewConnectionEdgeEntry newEntry) {



      IXEntry serverEntry = serverIns.getIXEntry();
      IXSystemStructure clientStruct = clientSystem.getIXSystemStructure();
      String name = serverEntry.getName();
      Enumeration enum = clientStruct.getThingsInOpd(clientOpd.getOpdId());
      while(enum.hasMoreElements()==true){
        IXThingInstance ins = (IXThingInstance)enum.nextElement();
        if(ins.getIXEntry().getName().compareTo(name) == 0) {
          newEntry.clientEntry = (IXThingEntry)ins.getIXEntry();
          newEntry.clientKey = ins.getKey();
          newEntry.existsInClient = true;

          ins.setSize(serverIns.getWidth(),serverIns.getHeight());
          ins.setLocation(serverIns.getX(),serverIns.getY());


          return true;
        }
      }
      return false;







    }
     // --newadd
  /** Add process to client
    * @param clientOpd - target opd
    * @param obj - new process
    * @param newEntry - old + new data about the entry
    * @return true if opd was created
    */
    private boolean insertPrc(IXOpd clientOpd,IXInstance obj,
                                     NewConnectionEdgeEntry newEntry){
      IXProcessInstance tmp = (IXProcessInstance)obj;
      IXProcessInstance newProc = null;
      if (tmp.getParentIXThingInstance() == null) {
	/* process is done on opd */
        newProc = clientSystem.addProcess(tmp.getIXEntry().getName(),
                                      tmp.getX(),
                                      tmp.getY(),
                                      clientOpd.getOpdId());
     } else {
        /* Add process to existing parent*/
       newProc = clientSystem.addProcess(tmp.getIXEntry().getName(),
                                      tmp.getX(),
                                      tmp.getY(),
                                      clientSystem.getIXSystemStructure().getIXOpd(clientOpd.getOpdId()).getMainIXInstance());


      }
      if(newProc != null) {
		newEntry.clientEntry = (IXProcessEntry) newProc.getIXEntry();
		newEntry.clientKey = newProc.getKey();
                 // newadd
                newProc.setSize(tmp.getWidth(),tmp.getHeight());
                newProc.setLocation(tmp.getX(),tmp.getY());
                 // --newadd
		return true;
      }
      newEntry.clientEntry = null;
      return false;


    }

 /** Add object to client
    * @param clientOpd - target opd
    * @param obj - new process
    * @param newEntry - old + new data about the entry
    * @return true if opd was created
    */
  private boolean insertObj(IXOpd clientOpd,
                                        IXObjectInstance obj,NewConnectionEdgeEntry newEntry){
    IXObjectEntry objEntry = (IXObjectEntry)obj.getIXEntry();
    /* create instance of object in client's opd */
    IXThingInstance parentInstance = obj.getParentIXThingInstance();
    IXObjectInstance newObjectInstance = null;
    if (parentInstance == null ) {
	//object is done on opd
      newObjectInstance = clientSystem.addObject(objEntry.getName(),
                                             obj.getX(),
                                             obj.getY(),
                                             clientOpd.getOpdId());
    } else {
      newObjectInstance =
          clientSystem.addObject(objEntry.getName(),
                             obj.getX(),
                             obj.getY(),
                             clientSystem.getIXSystemStructure().getIXOpd(clientOpd.getOpdId()).getMainIXInstance());

    }

    if(newObjectInstance != null) {
      /* save data of merge */
      newEntry.clientEntry = (IXObjectEntry) newObjectInstance.getIXEntry();
      newEntry.clientKey = newObjectInstance.getKey();
       // newadd
      newObjectInstance.setSize(obj.getWidth(),obj.getHeight());
      newObjectInstance.setLocation(obj.getX(),obj.getY());
       // --newadd
      return true;
    }
    newEntry.clientEntry = null;
    return false;



  }

   /** Merge links and relations of server opd into client for server entry
    * @param serverEntry - thing or state in server
    * @param clientOpd - target opd
    * @param serverOpd - server opd
    * @param newObject - new thing or state (belongs to client)
    * @return true if links && relations were inserted
    */
  private boolean insertOpdLnks (IXConnectionEdgeEntry serverEntry,
                                  IXOpd clientOpd,IXOpd serverOpd,
                                        IXConnectionEdgeEntry newObject)

   {
     boolean rcLinks=false, rcRels=false;
    /*insert the object's links*/
    /*get all links having this entry as source in server*/
    Enumeration serverLinks = serverEntry.getLinksBySource();
    if(serverLinks == null)
      return true;
    /* we failed to make object instance, fix_me later*/
    if (newObject == null ) {
      return false;
    }

    rcLinks = insertLnksOfThing(serverLinks,newObject, clientOpd,serverOpd);

    /* insert the object's Relations too;*/
    Enumeration serverRelations = serverEntry.getRelationBySource();
    if(serverRelations == null)
      return true;
    rcRels = insertRelationsOfThing(serverRelations,clientSystem,serverSystem,newObject,clientOpd,serverOpd);

    return rcRels&&rcLinks;
   }


   /** Merge links of server opd into client, going one
    * by one over enumerations of links, having source entry as source
    * @param thingLinks - enumeration of links having sourceEntry as source
    * @param sourceEntry - thing or state in server
    * @param clientOpd - client opd
    * @param serverOpd - server opd
    * @return true if links && relations were inserted
    */

   private boolean insertLnksOfThing(Enumeration thingLinks,

                                       IXConnectionEdgeEntry sourceEntry,
                                       IXOpd clientOpd,IXOpd serverOpd){



      while(thingLinks.hasMoreElements()){
        // link in server
        IXLinkEntry lnk = (IXLinkEntry)thingLinks.nextElement();
        // link is not exists for given opd
        if (!lnk.hasInstanceInOpd(serverOpd.getOpdId())) continue;
        long destId = lnk.getDestinationId();
	// destination entry in server
        IXEntry destinationEntry = getEntryById(destId,clientOpd,serverOpd);
        boolean rc = insertLnk(sourceEntry,destinationEntry,lnk, clientOpd);
        if(rc == false)
          return false;

      }
      return true;
    }

     /** Insert one link into client

    * @param xSrc - source entry
    * @param xDst - destination entry
    * @param lnk - link
    * @param clientOpd - client opd
    * @return true if links && relations were inserted
    */
     private boolean insertLnk(IXEntry xSrc,IXEntry xDst,IXLinkEntry lnk,
                               IXOpd clientOpd){
       if(xSrc == null || xDst == null) {
         return false;
       }

       int type = lnk.getLinkType();
       boolean rc=true;

       Enumeration snum = xSrc.getInstances();
       while (snum.hasMoreElements() == true) {
       // find instances of source and destination entries in current opd
         IXConnectionEdgeInstance xSrcIns = (IXConnectionEdgeInstance)snum.nextElement();
         Enumeration tnum = xDst.getInstances();
         while (tnum.hasMoreElements() == true) {
           IXConnectionEdgeInstance xDstIns = (IXConnectionEdgeInstance) tnum.
               nextElement();
           long sid = xSrcIns.getKey().getOpdId();
           long cid = xDstIns.getKey().getOpdId();
           if(sid==cid && cid == clientOpd.getOpdId()) { // link belongs to opd
             boolean toAddLink = true;;
             for(Enumeration e =((IXConnectionEdgeInstance)xSrcIns).getRelatedInstances();
                 e.hasMoreElements(); ) {
               /* Check if link with same type and direction already exists in client for
                this source instance*/
               IXInstance myLnk = (IXInstance)e.nextElement();
               if(myLnk instanceof IXLinkInstance) {
                 if(((IXLinkEntry)myLnk.getIXEntry()).getLinkType()==type &&
                    ( ((IXLinkEntry) myLnk.getIXEntry()).getSourceId() == xSrc.getId()&&
                    ( (IXLinkEntry) myLnk.getIXEntry()).getDestinationId() == xDst.getId())) {
                   /* The link exists - no need to add*/
                   toAddLink = false;
                   break;
                 }
               }

             }
             if(!toAddLink) continue;
             IXLinkInstance newLinkIns = clientSystem.addLink(xSrcIns, xDstIns, type);
             if (newLinkIns != null) {
               /* copy properties */
               copyEntry (lnk,newLinkIns.getIXEntry());
               rc = true;
             }


             else rc = false;

           }
         }
       }
       return rc;
     }

 /** Merge relations of server opd into client, going one
    * by one over enumerations of links, having sourceEntry as source
    * @param thingLinks - enumeration of links having sourceEntry as source
    * @param mySystem - the system of client
    * @param serverSystem - the system of server
    * @param sourceEntry - thing or state in server
    * @param clientOpd - client opd
    * @param serverOpd - server opd
    * @return true if relations were inserted
    */
    private boolean insertRelationsOfThing(Enumeration thingRelations,
                                   IXSystem mySystem,
                                   IXSystem serverProject,
                                   IXConnectionEdgeEntry sourceEntry,IXOpd clientOpd,IXOpd serverOpd){


     while (thingRelations.hasMoreElements()) {
       IXRelationEntry rel = (IXRelationEntry) thingRelations.nextElement();
       /* This relation isn't in OPD*/
       if (!rel.hasInstanceInOpd(serverOpd.getOpdId())) continue;
       long destId = rel.getDestinationId();
       IXEntry destinationEntry = getEntryById(destId,clientOpd,serverOpd);

       boolean rc = insertRel(mySystem, sourceEntry, destinationEntry,
                              rel.getRelationType(),clientOpd, rel);
      if (rc == false)
        return false;
    }
    return true;
  }
    /** Insert one relation into client
    * @param mySystem - the system of client
    * @param xSrc - source entry
    * @param xDst - destination entry
    * @param type - type of relation
    * @param clientOpd - client opd
    * @return true if links && relations were inserted
    */
   private boolean insertRel(IXSystem mySystem,IXEntry xSrc,IXEntry xDst,int type, IXOpd clientOpd, IXRelationEntry rel){
     if(xSrc == null || xDst == null)
       return false;
     Enumeration snum = xSrc.getInstances();
     boolean rc = true;
     while (snum.hasMoreElements() == true) {
       IXConnectionEdgeInstance xSrcIns = (IXConnectionEdgeInstance)snum.nextElement();
       Enumeration tnum = xDst.getInstances();
       while (tnum.hasMoreElements() == true) {
         IXConnectionEdgeInstance xDstIns = (IXConnectionEdgeInstance) tnum.nextElement();
         long sid = xSrcIns.getKey().getOpdId();
         long cid = xDstIns.getKey().getOpdId();
         if(sid==cid && cid == clientOpd.getOpdId()) {
           boolean toAddLink = true;;
           for(Enumeration e =((IXConnectionEdgeInstance)xSrcIns).getRelatedInstances();
                 e.hasMoreElements(); ) {
               /* check the relation with given type and direction */
               IXInstance myLnk = (IXInstance) e.nextElement();
               if (myLnk instanceof IXRelationInstance) {
                 if ( ( (IXRelationEntry) myLnk.getIXEntry()).getRelationType() ==
                     type && ( (IXRelationEntry) myLnk.getIXEntry()).getSourceId() == xSrc.getId() &&
                     ( (IXRelationEntry) myLnk.getIXEntry()).getDestinationId() == xDst.getId() )
                    {
                   toAddLink = false;
                   break;

                 }
               }
           }
           if(!toAddLink) continue;
           IXRelationInstance newRelIns = mySystem.addRelation(xSrcIns, xDstIns, type);
           if (newRelIns != null) {
             copyEntry (rel,newRelIns.getIXEntry());
             rc = true;
           }
           else  {rc = false; }
           }

       }
     }
     return rc;
   }

    /** Insert states from server's object into client's object
      * @param serverInstance - instance of object in server
      * @param mySystem - the system of client
      * @param newObject - instance of object in client
      * @return vector of data about the added states
      *
      */

   private Vector insertStatesOfObject(IXObjectInstance serverInstance,
                           IXSystem mySystem,
                           IXObjectInstance newObject){


    Enumeration serverStates = serverInstance.getStateInstances();
    Vector newObjectStates = new Vector();
    if( serverStates == null)  return null;
    while(serverStates.hasMoreElements()){
      // check states in server's object
      boolean addState = true;
      NewConnectionEdgeEntry newState = new NewConnectionEdgeEntry();
      IXStateInstance stateEntry = (IXStateInstance)serverStates.nextElement();
      Enumeration clientStates = newObject.getStateInstances();
      newState.serverEntry = (IXStateEntry)stateEntry.getIXEntry();
      while(clientStates.hasMoreElements()){
        /* Check if client has this state*/
        IXStateInstance clStatesIns = (IXStateInstance) clientStates.
            nextElement();
        if (clStatesIns.getIXEntry().getName().compareTo(stateEntry.getIXEntry().getName())==0) {
           /* Client has this state already- no need to add it*/
           // newadd
            clStatesIns.setSize(stateEntry.getWidth(),stateEntry.getHeight());
            clStatesIns.setLocation(stateEntry.getX(),stateEntry.getY());
            // --newadd
            addState = false;
            newState.clientEntry = (IXStateEntry)clStatesIns.getIXEntry();
            newState.existsInClient = true;
            newObjectStates.addElement(newState);
            break;
        }
      }
      if (addState) {
        /* add state */
        IXStateEntry res = mySystem.addState(stateEntry.getIXEntry().getName(),
                                             newObject);
        if (res == null) {
          continue;
        }
        IXStateEntry serverEntry = (IXStateEntry) stateEntry.getIXEntry();
        newState.clientEntry = res;
        newObjectStates.addElement(newState);

      }
    }

  return newObjectStates;
}


    /** Find in client server's entry having given id (destId)
      * @param mySystem - the system of client
      * @param serverProject - the system of server
      * @param destId - id in server of thing we looking for
      * @param clientOpd - the opd client
      * @param serverOpd - the opd of server
      * @param newObject - instance of object in client
      * @return entry in client
      *
      */
 private IXEntry getEntryById(long destId, IXOpd clientOpd,IXOpd serverOpd){
   Enumeration lenum = serverSystem.getIXSystemStructure().getInstancesInOpd(serverOpd.getOpdId());

   while(lenum.hasMoreElements()){
     IXInstance myObj = (IXInstance) lenum.nextElement();
     /* Find instances connected to the entry in server */
     if (myObj.getIXEntry().getId() == destId){

       IXEntry myObjEntry = findInClient(clientSystem, myObj.getIXEntry(),clientOpd);
       return myObjEntry;

     }
   }
   return null;
 }

  /** Find entry  toFind in client for given opd
  	* @param mySystem - the system of client
    * @param toFind - server's entry we are looking for
	* @param clientOpd - the opd client
    * @return entry in client
	*/

IXEntry findInClient(IXSystem mySystem, IXEntry toFind,IXOpd clientOpd){

   Enumeration lenum = mySystem.getIXSystemStructure().
                          getInstancesInOpd(clientOpd.getOpdId());
   while(lenum.hasMoreElements()){
     IXInstance myObj = (IXInstance) lenum.nextElement();
     if (myObj.getIXEntry().getName().equals(toFind.getName()) == true)
         return myObj.getIXEntry();

   }

   return null;
 }

   /** Add OPD into client system
  	* @param clientSystem - the system of client
  	* @param serverSystem - the system of server
    * @param serverOpd - the opd of server
    * @return entry in client
	*/
  public IXOpd addOpd(IXSystem clientSystem, IXSystem serverSystem, IXOpd serverOpd) {


   IXThingInstance mainInstance   = serverOpd.getMainIXInstance();

   Enumeration opds = clientSystem.getIXSystemStructure().getOpds();
   IXOpd curClientOpd = null;
   /* Search for parent of new opd in client */
    while(opds.hasMoreElements()){
     curClientOpd = (IXOpd) opds.nextElement();
     if (getOpdName(curClientOpd).compareTo(getOpdName(serverOpd.getParentIXOpd()))==0) {
       break;
    }

   }



    if (curClientOpd == null ) {
      return null;
    }
    long clientParentOpdId = curClientOpd.getOpdId();



  Enumeration tenum = clientSystem.getIXSystemStructure().getThingsInOpd(
                                                clientParentOpdId);

   IXThingInstance ins;
   IXOpd clientOpd = null;
   /*Search for main instance in client */
   while(tenum.hasMoreElements()){
     ins = (IXThingInstance) tenum.nextElement();
     if (ins.getIXEntry().getName().compareTo(mainInstance.getIXEntry().getName())==0)  {
       long parentOpdId = serverOpd.getParentIXOpd().getOpdId();
       Enumeration instances = serverSystem.getIXSystemStructure().getInstancesInOpd(parentOpdId);
       boolean unfold = false;
       while(instances.hasMoreElements()) {
         /* check if the current Opd is the result of unfolding or inzooming*/
         IXInstance parentIns = (IXInstance)instances.nextElement();
         if (!(parentIns instanceof IXThingInstance)) continue;
         if (parentIns.getIXEntry().getName().compareTo(ins.getIXEntry().getName())==0) {
           if(((IXThingInstance)parentIns).getUnfoldingIXOpd() == null) continue;
           if(((IXThingInstance)parentIns).getUnfoldingIXOpd().getOpdId() == serverOpd.getOpdId()) {
             unfold = true;
             break;
           }
         }
       }
       if (unfold) {
         clientOpd =  ins.createUnfoldedOpd(true);
         break;

       } else {
         clientOpd = ins.createInzoomedOpd();
         break;
       }

     }

    }
    if (clientOpd == null) return null;

    Enumeration instances = clientSystem.getIXSystemStructure().getInstancesInOpd(clientOpd.getOpdId());
    if(instances ==null) return clientOpd;
    while(instances.hasMoreElements()){
      IXInstance instance = (IXInstance) instances.nextElement();

      if (instance.getIXEntry().getId() == clientOpd.getMainIXEntry().getId() ||
          instance instanceof IXStateInstance)
       continue;
      if (instance instanceof IXLinkInstance || instance instanceof IXRelationInstance) {
        clientSystem.delete(instance);
        continue;
      }

      Enumeration serverInstances = serverSystem.getIXSystemStructure().getInstancesInOpd(serverOpd.getOpdId());
      boolean existInServer = false;
      while(serverInstances.hasMoreElements()) {
         IXInstance serverIns = (IXInstance) serverInstances.nextElement();
         if (serverIns.getIXEntry().getName().compareTo(instance.getIXEntry().
             getName()) == 0) {
           existInServer = true;
           break;
         }
      }
       // The opd is new for client, so it have to be exact copy of opd in server,
       // for this reason we delete all things (except things existing in server),
       // that client inserted to opd as default !
      if(!existInServer) {
         clientSystem.delete(instance);
       }


    }


    return clientOpd;

 }


   /** Copy internal values of IXEntry
  	* @param entryFrom - source (server) entry
  	* @param entryTo - target (client) entry
  	*/
 void copyEntry (IXEntry entryFrom, IXEntry entryTo ) {
   int x =1;
   if (x==0)
     return;
   entryTo.setEnvironmental(entryFrom.isEnvironmental());
   entryTo.setDescription(entryFrom.getDescription());
   if (entryTo instanceof IXThingEntry)
   {
     ((IXThingEntry) entryFrom).setNumberOfMASInstances(
          ((IXThingEntry) entryFrom).getNumberOfMASInstances());

     ((IXThingEntry) entryTo).setRole(((IXThingEntry) entryFrom).getRole());
     ((IXThingEntry) entryTo).setScope(((IXThingEntry) entryFrom).getScope());
     ((IXThingEntry) entryTo).setPhysical(((IXThingEntry) entryFrom).isPhysical());
     if(entryTo instanceof IXObjectEntry) {
       ((IXObjectEntry)entryTo).setInitialValue( ((IXObjectEntry)entryFrom).getInitialValue());
       ((IXObjectEntry)entryTo).setIndexName( ((IXObjectEntry)entryFrom).getIndexName());
       ((IXObjectEntry)entryTo).setIndexOrder( ((IXObjectEntry)entryFrom).getIndexOrder());
       ((IXObjectEntry)entryTo).setKey( ((IXObjectEntry)entryFrom).isKey());
       ((IXObjectEntry)entryTo).setPersistent( ((IXObjectEntry)entryFrom).isPersistent());
       ((IXObjectEntry)entryTo).setType( ((IXObjectEntry)entryFrom).getType());
       ((IXObjectEntry)entryTo).setTypeOriginId( ((IXObjectEntry)entryFrom).getTypeOriginId());
     } else {
       ((IXProcessEntry)entryTo).setMaxTimeActivation(((IXProcessEntry)entryFrom).getMaxTimeActivation());
       ((IXProcessEntry)entryTo).setMinTimeActivation( ((IXProcessEntry)entryFrom).getMinTimeActivation());
       ((IXProcessEntry)entryTo).setProcessBody( ((IXProcessEntry)entryFrom).getProcessBody());

     }
   }
   else if (entryTo instanceof IXStateEntry) {
     ( (IXStateEntry) entryTo).setDefault( ( (IXStateEntry) entryFrom).
                                          isDefault());
     ( (IXStateEntry) entryTo).setFinal( ( (IXStateEntry) entryFrom).isFinal());
     ( (IXStateEntry) entryTo).setInitial( ( (IXStateEntry) entryFrom).
                                          isInitial());
     ( (IXStateEntry) entryTo).setMaxTime( ( (IXStateEntry) entryFrom).
                                          getMaxTime());
     ( (IXStateEntry) entryTo).setMinTime( ( (IXStateEntry) entryFrom).
                                          getMinTime());

   }
   else if (entryTo instanceof IXLinkEntry) {
     ( (IXLinkEntry) entryTo).setCondition( ( (IXLinkEntry) entryFrom).getCondition());
     ( (IXLinkEntry) entryTo).setMaxReactionTime( ( (IXLinkEntry) entryFrom).getMaxReactionTime());
     ( (IXLinkEntry) entryTo).setMinReactionTime( ( (IXLinkEntry) entryFrom).getMinReactionTime());
     ( (IXLinkEntry) entryTo).setPath( ( (IXLinkEntry) entryFrom).getPath());

   }
   else if (entryTo instanceof IXRelationEntry) {
     ( (IXRelationEntry) entryTo).setBackwardRelationMeaning( ( (IXRelationEntry) entryFrom).getBackwardRelationMeaning());
     ( (IXRelationEntry) entryTo).setDestinationCardinality( ( (IXRelationEntry) entryFrom).getDestinationCardinality());
     ( (IXRelationEntry) entryTo).setForwardRelationMeaning( ( (IXRelationEntry) entryFrom).getForwardRelationMeaning());
     ( (IXRelationEntry) entryTo).setSourceCardinality( ( (IXRelationEntry) entryFrom).getSourceCardinality());

   }
   Enumeration instances = entryTo.getInstances();
   while (instances.hasMoreElements()) {
     IXInstance inst = (IXInstance)instances.nextElement();
     inst.update();
   }




 }
 String getOpdName(IXOpd opd) {
   if (opd == null) return "";
   String fullName = opd.getName();
   int index = fullName.indexOf(" - ");
   return fullName.substring(index+3);

 }
 void getName(IXOpd opd) {
  if (opd == null) return ;
  String fullName = opd.getName();
  int index = fullName.lastIndexOf(" - ");

}

  /** Merge server system into client's one.
  	* @param sClientFileName - client file name
    * @param sServerFileName - server file name
	* @return merged system
	*/
  public IXSystem mergeProject(String sClientFileName,String sServerFileName) throws gui.opx.LoadException,IOException,Exception
  {
    InputStream isC, isS;
    File fC = new File(sClientFileName);
    File fS = new File(sServerFileName);
    IXSystem mergedProject = null;
	// fix_me - open if  want to get the names in the running time
    //isC = openFile("client");
    //isS = openFile("server");
    try{
      if (sClientFileName.endsWith(".opz")) {
        isC = new GZIPInputStream(new FileInputStream(fC), 4096);
      }
      else {
        isC = new BufferedInputStream(new FileInputStream(fC), 4096);
      }
      if (sServerFileName.endsWith(".opz")) {
        isS = new GZIPInputStream(new FileInputStream(fS), 4096);
      }
      else {
        isS = new BufferedInputStream(new FileInputStream(fS), 4096);
      }
      // fix_me - message
      if (isC == null || isS == null) return null;

      Loader ldC = new gui.opx.Loader();
      Loader ldS = new gui.opx.Loader();
      JProgressBar pBar = new JProgressBar();
      serverSystem = ldS.load(myOpcat2.getDesktop(), isS, pBar);
      clientSystem = ldC.load(myOpcat2.getDesktop(), isC, pBar);

      isS.close();;
      isC.close();
      myOpcat2.setCurrentProject(serverSystem);
      XmlMerger Merger = new XmlMerger(myOpcat2);
      //test(serverSystem);
      mergedProject = mergeOpdTree ( null);
      //test( mergedProject);
      if(mergedProject == null){
        myOpcat2.getCurrentProject().refresh();
        /*JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                      "Fatale Error in merging",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);*/
        throw new gui.opx.LoadException();
      }
      // close server project
      myOpcat2.getCurrentProject().close();
      myOpcat2.setCurrentProject(mergedProject);
      myOpcat2.getCurrentProject().refresh();
      myOpcat2.setProjectOpened(true);
      myOpcat2.getCurrentProject().showRootOpd();

      /*Opcat2.getFrame().setTitle("Opcat II - Server " + serverSystem.getName()+
                                " and client " + mergedProject.getName()+ " - merged");
      myOpcat2.getCurrentProject().setName("MergeResult");*/
      (myOpcat2.getRepository()).setProject((OpdProject)mergedProject);
      myOpcat2.getRepository().rebuildTrees();
      myOpcat2.getCurrentProject().updateChanges();
      myOpcat2.getCurrentProject().refresh();
      /*JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    "Merge is done",
                                    "Message",
                                    JOptionPane.INFORMATION_MESSAGE);*/
      return null;
    }
    catch (IOException ioe) {throw ioe;}
    catch (Exception load) { throw load;}
  }
  /** Merge server system into client's one.
          * @param sClientFileName - client file name
    * @param sServerFileName - server file name
        * @return merged system
        */
  public IXSystem mergeProject(IXSystem clientSys,String sServerFileName) throws gui.opx.LoadException,IOException,Exception
  {
    InputStream isC, isS;
    File fS = new File(sServerFileName);
    IXSystem mergedProject = null;
        // fix_me - open if  want to get the names in the running time
    //isC = openFile("client");
    //isS = openFile("server");
    try{
      if (sServerFileName.endsWith(".opz")) {
        isS = new GZIPInputStream(new FileInputStream(fS), 4096);
      }
      else {
        isS = new BufferedInputStream(new FileInputStream(fS), 4096);
      }
      // fix_me - message
      if (clientSys == null || isS == null) return null;

      Loader ldC = new gui.opx.Loader();
      Loader ldS = new gui.opx.Loader();
      JProgressBar pBar = new JProgressBar();
      serverSystem = ldS.load(myOpcat2.getDesktop(), isS, pBar);
      clientSystem = clientSys;

      isS.close();
      myOpcat2.setCurrentProject(serverSystem);
      XmlMerger Merger = new XmlMerger(myOpcat2);
      //test(serverSystem);
      mergedProject = mergeOpdTree ( null);
      //test( mergedProject);
      if(mergedProject == null){
        myOpcat2.getCurrentProject().refresh();
        /*JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                      "Fatale Error in merging",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);*/
        throw new gui.opx.LoadException();
      }
      // close server project
      myOpcat2.getCurrentProject().close();
      myOpcat2.setCurrentProject(mergedProject);
      myOpcat2.getCurrentProject().refresh();
      myOpcat2.setProjectOpened(true);
      myOpcat2.getCurrentProject().showRootOpd();

      /*Opcat2.getFrame().setTitle("Opcat II - Server " + serverSystem.getName()+
                                " and client " + mergedProject.getName()+ " - merged");
      myOpcat2.getCurrentProject().setName("MergeResult");*/
      (myOpcat2.getRepository()).setProject((OpdProject)mergedProject);
      myOpcat2.getRepository().rebuildTrees();
      myOpcat2.getCurrentProject().updateChanges();
      myOpcat2.getCurrentProject().refresh();
     /* JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    "Merge is done",
                                    "Message",
                                    JOptionPane.INFORMATION_MESSAGE);*/
      return null;
    }
    catch (IOException ioe) {throw ioe;}
    catch (Exception load) { throw load;}
  }


  /* Making browser for opening input files for merger*/
  private InputStream openFile (String filePurpose) {
    JFileChooser myFileChooser = new JFileChooser();
    myFileChooser.setSelectedFile(new File(""));
    //myFileChooser.resetChoosableFileFilters();

    int retVal = myFileChooser.showDialog(Opcat2.getFrame(), "Load " + filePurpose+" System");
    if (retVal != JFileChooser.APPROVE_OPTION) {
      return null;
    }

    String fileName = myFileChooser.getSelectedFile().getPath();
    InputStream is = null;
    File f = new File(fileName);
    try {
      if (fileName.endsWith(".opz")) {
        is = new GZIPInputStream(new FileInputStream(f), 4096);
      }
      else {
        is = new BufferedInputStream(new FileInputStream(f), 4096);
      }
    } catch (Exception e ) {}
    return is;


  }



   /** Inner class for merger, is used to contain data about merged things and states,
      * this data is required for merging links, relations and states (we are going through
      * list contains NewConnectionEdgeEntry objects)
      */


      private class NewConnectionEdgeEntry {
        /* entry for entity in server */
        IXConnectionEdgeEntry serverEntry;
        /* entry for same entity in client */
        IXConnectionEdgeEntry clientEntry;
        /* OpdKey for instance of server's entity*/
        OpdKey serverKey;
         /* OpdKey for instance of client's entity*/
        OpdKey clientKey;
        /* true if client had the entry b4 merge*/
        boolean existsInClient;
        NewConnectionEdgeEntry() {
          serverEntry = null;
          clientEntry = null;
          existsInClient = false;
        }
      }
}

