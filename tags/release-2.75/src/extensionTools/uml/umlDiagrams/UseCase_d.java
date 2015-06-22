package extensionTools.uml.umlDiagrams;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IInstance;
import exportedAPI.opcatAPI.ILinkEntry;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IObjectInstance;
import exportedAPI.opcatAPI.IOpd;
import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.IProcessInstance;
import exportedAPI.opcatAPI.IRelationEntry;
import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPI.IThingInstance;


/**
 * class UseCase_d creates the USECASE DIAGRAM part in the XML file
 */
class UseCase_d {

  public UseCase_d() {}
  ISystem mySys;


/**
 * Calculates the value of "scope" field
 * @param IProcessEntry proc - the "scope" field is calculated for
 * @return String - the field value
 */
  private String GetScope(IProcessEntry proc){
    String scope = "";
    if (proc.getScope().compareTo("0")== 0)
      scope="Public";
    if(proc.getScope().compareTo("1") == 0)
      scope="Protected";
    if(proc.getScope().compareTo("2") == 0)
      scope="Private";
    return scope;
  }

/**
 * Calculates the value of "scope" field
 * @param IObjectEntry obj - the "scope" field is calculated for
 * @return String - the field value
 */
  private String GetScope(IObjectEntry obj){
    String scope = "";
    if (obj.getScope().compareTo("0")== 0)
      scope="Public";
    if(obj.getScope().compareTo("1") == 0)
      scope="Protected";
    if(obj.getScope().compareTo("2") == 0)
      scope="Private";
    return scope;
  }

/**
 * This function checks if the given process equivalent to a usecase.
 * @param IProcessEntry proc
 * @ISystemStructure MyStructure
 * @return boolean:true-if the process equivalent to a usecase;false-otherwise.
 */
  private boolean isUsecase(IProcessEntry proc,ISystemStructure MyStructure){
    Enumeration list;
    ILinkEntry link;
    IRelationEntry rel;
    IObjectEntry obj;
    IEntry abstrEntry;
    String temp;
    IProcessInstance procInst;
    IThingInstance parent;
    IEntry abstractEntry;

    if((proc.isEnvironmental())||(proc.isPhysical()))
      return false;

    list=proc.getInstances();
    while(list.hasMoreElements()){
      procInst=(IProcessInstance)list.nextElement();
      parent=procInst.getParentIThingInstance();
      if(parent!=null){
        abstractEntry=parent.getIEntry();
        if(abstractEntry instanceof IProcessEntry){
          IProcessEntry father;
          father=(IProcessEntry)abstractEntry;
          if(isUsecase(father,MyStructure))
            return true;
        }
      }
    }

    list=proc.getLinksBySource();
    while(list.hasMoreElements()){
      link=(ILinkEntry)list.nextElement();
      abstrEntry=MyStructure.getIEntry(link.getDestinationId());
      if(abstrEntry instanceof IObjectEntry){
        obj=(IObjectEntry)abstrEntry;
        if(obj.isEnvironmental())
          return true;
      }
    }
    list=proc.getLinksByDestination();
    while(list.hasMoreElements()){
      link=(ILinkEntry)list.nextElement();
      abstrEntry=MyStructure.getIEntry(link.getSourceId());
      if(abstrEntry instanceof IObjectEntry){
        obj=(IObjectEntry)abstrEntry;
        if(obj.isEnvironmental()){
          return true;
        }
      }
    }
    list=proc.getRelationBySource();
    while(list.hasMoreElements()){
      rel=(IRelationEntry)list.nextElement();
      abstrEntry=MyStructure.getIEntry(rel.getDestinationId());
      if(abstrEntry instanceof IObjectEntry){
        obj=(IObjectEntry)abstrEntry;
        if(obj.isEnvironmental())
          return true;
      }
    }
    list=proc.getRelationByDestination();
    while(list.hasMoreElements()){
      rel=(IRelationEntry)list.nextElement();
      abstrEntry=MyStructure.getIEntry(rel.getSourceId());
      if(abstrEntry instanceof IObjectEntry){
        obj=(IObjectEntry)abstrEntry;
        if(obj.isEnvironmental())
          return true;
      }
    }
    return false;
  }

//------------------------------------------- A S S O C - N E E D E D--------FOR RELATION--------------------------
/**
 * This function calculates if there need yes/no to be an association
 *@param IRelationEntry rel
 *@param IProcessEntry proc
 *@param ISystemStructure MyStructure
 *@param Vector OpdVec
 *@param int op
 *@return boolean:true-association needed; false association not needed.
 */
  //  obj(actor) is source & process(usecase) is destination connected by relation
  private boolean assoc_needed(IRelationEntry rel,IProcessEntry proc,ISystemStructure MyStructure,Vector OpdVec,int op){
    Enumeration e,e1,childrenList;
    Vector childrenVec= new Vector(4,2);
    IEntry abstractEntry,abstractEntry1;
    IRelationEntry rel_1;
    IProcessInstance procInst;

    e=MyStructure.getElements();//going through all teh relations in the system
    while(e.hasMoreElements()){
      abstractEntry=(IEntry)e.nextElement();
      if(abstractEntry instanceof IRelationEntry){//if relation
        rel_1=(IRelationEntry)abstractEntry;
        if((rel.getSourceId()==rel_1.getSourceId())&&(rel.getRelationType()==rel_1.getRelationType())&&(rel.getId()!=rel_1.getId())){
        // the same source, the same relation type, but different relation
          e1=proc.getInstances();
          while(e1.hasMoreElements()){
            procInst=(IProcessInstance)e1.nextElement();
            childrenList=procInst.getChildThings();
            while(childrenList.hasMoreElements()){
              abstractEntry1=((IInstance)childrenList.nextElement()).getIEntry();
              if(abstractEntry1 instanceof IProcessEntry){
                IProcessEntry child;
                child=(IProcessEntry)abstractEntry1; //child thing
                if((IsInVec(child,OpdVec))||(op==2)){
                  if(child.getId()==rel_1.getDestinationId())
                    return false;
                }
              }//end of if
            }//end of while
          }//end of while
        }//end of if
      }// end of if
    }//end of while
    return true;
  }//end of func

//------------------------------------------- A S S O C - N E E D E D--------FOR RELATION--------------------------
/**
 * This function calculates if there need yes/no to be an association
 *@param IProcessEntry proc
 *@param IRelationEntry rel
 *@param ISystemStructure MyStructure
 *@param Vector OpdVec
 *@param int op
 *@return boolean:true-association needed; false association not needed.
 */
 //  process(usecase) is source & obj(actor) is destination connected by relation
  private boolean assoc_needed(IProcessEntry proc,IRelationEntry rel,ISystemStructure MyStructure,Vector OpdVec,int op){
    Enumeration e,e1,childrenList;
    Vector childrenVec= new Vector(4,2);
    IEntry abstractEntry,abstractEntry1;
    IRelationEntry rel_1;
    IProcessInstance procInst;

    e=MyStructure.getElements();
    while(e.hasMoreElements()){
      abstractEntry=(IEntry)e.nextElement();
      if(abstractEntry instanceof IRelationEntry){
        rel_1=(IRelationEntry)abstractEntry;
        if((rel.getDestinationId()==rel_1.getDestinationId())&&(rel.getRelationType()==rel_1.getRelationType())&&(rel.getId()!=rel_1.getId())){
          e1=proc.getInstances();
          while(e1.hasMoreElements()){
            procInst=(IProcessInstance)e1.nextElement();
            childrenList=procInst.getChildThings();
            while(childrenList.hasMoreElements()){
              abstractEntry1=((IInstance)childrenList.nextElement()).getIEntry();
              if(abstractEntry1 instanceof IProcessEntry){
                IProcessEntry child;
                child=(IProcessEntry)abstractEntry1; //child thing
                if((IsInVec(child,OpdVec))||(op==2)){
                  if(child.getId()==rel_1.getSourceId())
                    return false;
                }
              }//end of if
            }//end of while
          }//end of while
        }//end of if
      }//end of if
    }//end of while
    return true;
  }//end of func

//------------------------------------------- A S S O C - N E E D E D--------FOR LINK--------------------------
/**
 * This function calculates if there need yes/no to be an association
 *@param ILinkEntry link
 *@param IProcessEntry proc
 *@param ISystemStructure MyStructure
 *@param Vector OpdVec
 *@param int op
 *@return boolean:true-association needed; false association not needed.
 */
   //  obj(actor) is source & process(usecase) is destination connected by link
  private boolean assoc_needed(ILinkEntry link,IProcessEntry proc,ISystemStructure MyStructure,Vector OpdVec,int op){
    Enumeration e,e1,childrenList;
    Vector childrenVec= new Vector(4,2);
    IEntry abstractEntry,abstractEntry1;
    ILinkEntry link_1;
    IProcessInstance procInst;

    e=MyStructure.getElements();
    while(e.hasMoreElements()){
      abstractEntry=(IEntry)e.nextElement();
      if(abstractEntry instanceof ILinkEntry){
        link_1=(ILinkEntry)abstractEntry;
        if((link.getSourceId()==link_1.getSourceId())&&(link.getLinkType()==link_1.getLinkType())&&(link.getId()!=link_1.getId())){
          e1=proc.getInstances();
          while(e1.hasMoreElements()){
            procInst=(IProcessInstance)e1.nextElement();
            childrenList=procInst.getChildThings();
            while(childrenList.hasMoreElements()){
              abstractEntry1=((IInstance)childrenList.nextElement()).getIEntry();
              if(abstractEntry1 instanceof IProcessEntry){
                IProcessEntry child;
                child=(IProcessEntry)abstractEntry1; //child thing
                if((IsInVec(child,OpdVec))||(op==2)){
                  if(child.getId()==link_1.getDestinationId())
                    return false;
                }
              }//end of if
            }//end of while
          }//end of while
        }//end of if
      }//end of if
    }//end of while
    return true;
  }//end of func

//------------------------------------------- A S S O C - N E E D E D--------FOR LINK--------------------------
/**
 * This function calculates if there need yes/no to be an association
 *@param IProcessEntry proc
 *@param ILinkEntry link
 *@param ISystemStructure MyStructure
 *@param Vector OpdVec
 *@param int op
 *@return boolean:true-association needed; false association not needed.
 */
//  process(usecase) is source & obj(actor) is destination connected by link
  private boolean assoc_needed(IProcessEntry proc,ILinkEntry link,ISystemStructure MyStructure,Vector OpdVec,int op){
    Enumeration e,e1,childrenList;
    Vector childrenVec= new Vector(4,2);
    IEntry abstractEntry,abstractEntry1;
    ILinkEntry link_1;
    IProcessInstance procInst;

    e=MyStructure.getElements();
    while(e.hasMoreElements()){
      abstractEntry=(IEntry)e.nextElement();
      if(abstractEntry instanceof ILinkEntry){
        link_1=(ILinkEntry)abstractEntry;
        if((link.getDestinationId()==link_1.getDestinationId())&&(link.getLinkType()==link_1.getLinkType())&&(link.getId()!=link_1.getId())){
          e1=proc.getInstances();
          while(e1.hasMoreElements()){
            procInst=(IProcessInstance)e1.nextElement();
            childrenList=procInst.getChildThings();
            while(childrenList.hasMoreElements()){
              abstractEntry1=((IInstance)childrenList.nextElement()).getIEntry();
              if(abstractEntry1 instanceof IProcessEntry){
                IProcessEntry child;
                child=(IProcessEntry)abstractEntry1; //child thing
                if((IsInVec(child,OpdVec))||(op==2)){
                  if(child.getId()==link_1.getSourceId())
                    return false;
                }
              }//end of if
            }//end of while
          }//end of while
        }//end of if
      }//end of if
    }//end of while
    return true;
  }//end of func


//---------------------------------------- P R I N T - U S E C A S E ----------------------------------------
/**
 * This function responsible for printing the usecase part in the XML file
 * @param ISystemStructure MyStructure
 * @param FileOutputStream file
 * @param IProcessEntry proc - represents a usecase to be printed
 * @param Vector usecaseVec - responsible for outside printing of usecases (Diagram Elements) in XML file
 * @param Vector usecaseExtendVec - responsible of printing an Extend associations in XML file
 * @param Vector usecaseIncludeVec - responsible of printing an Include associations in XML file
 * @param Vector usecaseInzoomIncludeVec - responsible of printing associations created by Inzoom, in XML file
 * @param Vector generalizationVec - responsible of printing associations created by Generalization in XML file
 * @param Vector usecasespecVec - responsible of printing associations created by Specialization in XML file
 * @param Vector OpdVec - includes all the process that should be converted to usecase
 * @param int op - represents the choice of a user: Root Level Only-->op=1; All OPD Levels-->op=2.
 */
  private void PrintUsecase(ISystemStructure MyStructure,FileOutputStream file,IProcessEntry proc,Vector usecaseVec,Vector usecaseExtendVec,Vector usecaseIncludeVec,Vector usecaseInzoomIncludeVec,Vector generalizationVec,Vector usecasespecVec,Vector OpdVec,int op)throws IOException{
  try{
    String temp="";
    Enumeration list,childrenList,g_list;
    int flag;
    ILinkEntry link;
    IEntry abstractEntry;
    IRelationEntry rel,relx;
    IProcessInstance procInst;
    IThingInstance parent;
    Vector childrenVec=new Vector(4,2);


    temp="<!--  ===============[UseCase] ====================  --> ";
    file.write(temp.getBytes());
    temp="<UML:UseCase xmi.id=\"S."+proc.getId()+"\" name=\""+proc.getName()+"\" visibility=\""+GetScope(proc)+"\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" namespace=\"G.0000.1\"  ";
    file.write(temp.getBytes());
    usecaseVec.addElement(""+proc.getId()+"");  //for outside printing of a usecase

//======INVOCATION_LINK AND EXCEPTION_LINK_SUPPLIER==================== EXTEND
    list=proc.getLinksBySource();
    flag=0;
    while(list.hasMoreElements()){
      link=(ILinkEntry)list.nextElement();
      if((link.getLinkType()==OpcatConstants.EXCEPTION_LINK)||(link.getLinkType()==OpcatConstants.INVOCATION_LINK)){
        abstractEntry=MyStructure.getIEntry(link.getDestinationId());
        if(abstractEntry instanceof IProcessEntry){
          IProcessEntry proc1;
          proc1=(IProcessEntry)abstractEntry;
          if(isUsecase(proc1,MyStructure))
            usecaseExtendVec.addElement(link);
        }
      }
    }//end of while

//======AGGREGATION_REL_SUPPLIER==================== INCLUDE
    list=proc.getRelationBySource();
    while(list.hasMoreElements()){
      rel=(IRelationEntry)list.nextElement();
      if(rel.getRelationType()==OpcatConstants.AGGREGATION_RELATION){
        abstractEntry=MyStructure.getIEntry(rel.getDestinationId());
        if(abstractEntry instanceof IProcessEntry){
          IProcessEntry proc1;
          proc1=(IProcessEntry)abstractEntry;
          if(isUsecase(proc1,MyStructure))
            usecaseIncludeVec.addElement(rel);
        }
      }
    }//end of while

//======IN_ZOOMED_CHILDREN_SUPPLIER==================== INCLUDE

    list=proc.getInstances();
    while(list.hasMoreElements()){
      procInst=(IProcessInstance)list.nextElement();
      childrenList=procInst.getChildThings();
      parent=procInst.getParentIThingInstance();
      while(childrenList.hasMoreElements()){
        abstractEntry=((IInstance)childrenList.nextElement()).getIEntry();
        if(abstractEntry instanceof IProcessEntry){
          IProcessEntry proc1;
          proc1=(IProcessEntry)abstractEntry; //child thing
          if(isUsecase(proc1,MyStructure)) {
            if((IsInVec(proc1,OpdVec))||(op==2)){
              childrenVec.addElement(proc1);
              usecaseInzoomIncludeVec.addElement(proc1);
              usecaseInzoomIncludeVec.addElement(proc);
            }
          }
        }
      }

    }
//==========GENERALIZATION - SPECIALIZATION RELATION among usecase`s===

    flag=0;
    list=proc.getRelationBySource();  //all relations in which proc is the source
    while (list.hasMoreElements()) {
      rel=(IRelationEntry)list.nextElement();
      if (rel.getRelationType() == OpcatConstants.SPECIALIZATION_RELATION) {
        abstractEntry=MyStructure.getIEntry(rel.getDestinationId());
        if(abstractEntry instanceof IProcessEntry){
          IProcessEntry proc1;
          proc1=(IProcessEntry)abstractEntry;
          if(isUsecase(proc1,MyStructure)){
            if(flag==0){
              temp="specialization=\" ";
              file.write(temp.getBytes());
              flag=1;
            }
            usecasespecVec.addElement(""+rel.getId()+"");
            temp="G."+rel.getId()+"  ";
            file.write(temp.getBytes());
          }//if usecase
        } //if process
      }
    }
    if(flag==1){
      temp="\" ";
      file.write(temp.getBytes());
    }
    flag=0;
    list=proc.getRelationByDestination();
    while (list.hasMoreElements()) {
      rel=(IRelationEntry)list.nextElement();
      if (rel.getRelationType() == OpcatConstants.SPECIALIZATION_RELATION) {
        abstractEntry=MyStructure.getIEntry(rel.getSourceId());
        if(abstractEntry instanceof IProcessEntry){
          IProcessEntry proc1;
          proc1=(IProcessEntry)abstractEntry;
          if(isUsecase(proc1,MyStructure)){
            if(flag==0){
              temp="generalization=\" ";
              file.write(temp.getBytes());
              flag=1;
            }
            temp="G."+rel.getId()+"  ";
            generalizationVec.addElement(rel); //adding to the vector
            file.write(temp.getBytes());
          }//if usecase
        } //if process
      }
    }
    if (flag==0) {
      temp="/> ";
      file.write(temp.getBytes());
    }
    if (flag==1) {
      temp="\">\n ";
      file.write(temp.getBytes());
      temp="<UML:Namespace.ownedElement> ";
      file.write(temp.getBytes());
      temp="<!--  ===================  [Generalization] =============== --> ";
      file.write(temp.getBytes());
      g_list=generalizationVec.elements(); //list of all generalization relations
      while (g_list.hasMoreElements()) {
        relx=(IRelationEntry)g_list.nextElement();
        if(relx.getDestinationId()==proc.getId()){
          temp="<UML:Generalization xmi.id=\"G."+relx.getId()+"\" name=\"\" visibility=\"public\" isSpecification=\"false\" discriminator=\"\" child=\"S."+relx.getDestinationId()+"\" parent=\"S."+relx.getSourceId()+"\" /> ";
          file.write(temp.getBytes());
        }
      }
      temp="</UML:Namespace.ownedElement>";
      file.write(temp.getBytes());
      temp="</UML:UseCase>";
      file.write(temp.getBytes());
    }
  }//end of try
    catch(IOException e)
    {
      System.out.println("error");
      return;
    }//end of catch
  }

//------------------------------------- C R E A T E - P R O C - A C T - V E C ---------------------------------------------------------
/**
 *Recursive function that creates a Vector which contains all the "UseCase" in the system
 *@param ISystemStructure MyStructure
 *@param IProcessEntry proc - Inzoomed process
 *@param Vector OpdVec - the returned vector
 *@param int level - number of levels to "go down" in the Inzoomed process
 */
  private void CreateProcVec(ISystemStructure MyStructure, IProcessEntry proc,Vector OpdVec, int level){
    int  procF=0, new_level;
    Enumeration e, childList;
    IInstance inst;
    IProcessInstance procInst;
    IObjectInstance objInst;
    IObjectEntry obj;
    IThingInstance thing;

    new_level=level-1;  //new level to pass on
    OpdVec.addElement(proc);  //the process is added to the opdVec->it will be deffered as an usecase
    if (level>0) {    //the current level
      e=proc.getInstances();   //all IProcessInstances of this process;
      while (e.hasMoreElements()){
        procInst=(IProcessInstance)e.nextElement();     //one of the instances of the given process
        childList=procInst.getChildThings();  //contain all children things of the process
        procF=0;
        if  (childList.hasMoreElements() ){   //if the process has children things
          while (childList.hasMoreElements() ){   //going throw all the children
            //means the process is inzoomed
            if (procF==0) {   //the was not declerad as inzoomed
              procF=1;  //the process is inzoomed
            }
            if (new_level>=0) {//number of level to go down (more)
              thing=(IThingInstance)childList.nextElement();  //child
              if (thing instanceof IProcessInstance) {  //if the "child" is a process--> it is an use case (will be added to the vector in the next step)
                procInst=(IProcessInstance)thing;
                CreateProcVec( MyStructure, (IProcessEntry)(procInst.getIEntry()),OpdVec, new_level); //recurtion
              }
            }
          }
        } else return;
      }
    }
    return;
  }

//--------------------------------------C R E A T E - A C T - V E C-------------------------------------------
/**
 * Recursive function that creates a Vector which contains all the "Actors" in the system
 * @param ISystemStructure MyStructure
 * @param FileOutputStream file
 * @param IProcessEntry proc - Inzoomed process
 * @param Vector actVec - the returned vector
 * @param int level - number of levels to "go down" in the Inzoomed process
 */
  private void CreateActVec(ISystemStructure MyStructure,FileOutputStream file, IProcessEntry proc,Vector actVec, int level) throws IOException{
    try{
    int  procF=0, new_level;
    Enumeration e, childList;
    IInstance inst;
    IProcessInstance procInst;
    IObjectInstance objInst;
    IObjectEntry obj;
    IThingInstance thing;

    String procName="-->"+proc.getName()+":";
    new_level=level-1;  //new level to pass on
    if (level>0) {    //the current level
      e=proc.getInstances();   //all IProcessInstances of this process;
      while (e.hasMoreElements()){
        procInst=(IProcessInstance)e.nextElement();     //one of the instances of the given process
        childList=procInst.getChildThings();  //contain all children things of the process
        while (childList.hasMoreElements() ){   //going throw all the children
          thing=(IThingInstance)childList.nextElement();  //child
          if(thing instanceof IObjectInstance){// if the "child" is an object -->adding it to the objVec
            objInst=(IObjectInstance)thing;
            obj=(IObjectEntry)objInst.getIEntry();
            actVec.addElement(obj);
          }
        }
      }
      e=proc.getInstances();   //all IProcessInstances of this process;
      while (e.hasMoreElements()){
        procInst=(IProcessInstance)e.nextElement();     //one of the instances of the given process
        childList=procInst.getChildThings();  //contain all children things of the process
        while (childList.hasMoreElements() ){   //going throw all the children
          thing=(IThingInstance)childList.nextElement();  //child
          if(thing instanceof IProcessInstance){// if the "child" is an process -->again
            procInst=(IProcessInstance)thing;
            CreateActVec( MyStructure, file,(IProcessEntry)(procInst.getIEntry()),actVec,new_level); //recurtion
          }
        }
      }
    } else return;
    return;
        }//end of try
    catch(IOException e)
    {
      System.out.println("error");
      return;
    }//end of catch

  }


//----------------------------------- P R I N T - A C T O R S ----------------------------------------
/**
 * Printing of the "Actor" part in the XML file.
 * @param ISystemStructure MyStructure
 * @param FileOutputStream file
 * @param IObjectEntry obj - an object which represent an "Actor" in USECASE DIAGRAM
 * @param Vector actorTagsVec - used for outside printing of the usecase diagram part in XML file
 * @param Vector generalizationVec - cointains all generalization relations between "actors"
 */
  private void PrintActors(ISystemStructure MyStructure,FileOutputStream file,IObjectEntry obj,Vector actorTagsVec,Vector generalizationVec)throws IOException{
  try{
    String temp="";
    int flag, flag2;
    Enumeration rel_list_s,g_list;
    IRelationEntry rel,relx;
    IEntry abstractEntry;

    temp="<!--  =================  [Actor] ====================  --> ";
    file.write(temp.getBytes());
    temp="<UML:Actor xmi.id=\"S."+obj.getId()+"\" name=\""+obj.getName()+"\" visibility=\""+GetScope(obj)+"\" isSpecification=\"false\" isRoot=\"true\" isLeaf=\"true\" isAbstract=\"false\" namespace=\"G.0000.1\" ";
    file.write(temp.getBytes());
    actorTagsVec.addElement(""+obj.getId()+"");
    rel_list_s=obj.getRelationBySource();
    flag=0; flag2=0;
    while(rel_list_s.hasMoreElements()){
      rel=(IRelationEntry)rel_list_s.nextElement();
      //specialization
      if (rel.getRelationType()==OpcatConstants.SPECIALIZATION_RELATION){
        abstractEntry=MyStructure.getIEntry(rel.getDestinationId());
        if (abstractEntry instanceof IObjectEntry) {
          if (((IObjectEntry)abstractEntry).isEnvironmental()) {//destination is an actor too
            if (flag==0){
              temp="specialization=\" ";
              file.write(temp.getBytes());
              flag=1; flag2=1;
            }
            temp="G."+rel.getId()+"  ";
            file.write(temp.getBytes());
          }//if destination is an actor
        }//if destination is an object
      }//end of if (reltype)
    }//end of while
    if (flag==1){//there were a specialization
      temp="\" "; //closing the list
      file.write(temp.getBytes());
    }//end of if

    rel_list_s=obj.getRelationByDestination();
    flag=0;
    while(rel_list_s.hasMoreElements()){
      rel=(IRelationEntry)rel_list_s.nextElement();
              //generalization
      if (rel.getRelationType()==OpcatConstants.SPECIALIZATION_RELATION) {
        abstractEntry=MyStructure.getIEntry(rel.getSourceId());
          if (abstractEntry instanceof IObjectEntry) {//if object
            if (((IObjectEntry)abstractEntry).isEnvironmental()) {//if actor
              if (flag==0){
                temp="generalization=\" ";
                file.write(temp.getBytes());
                flag=1;
              }
              generalizationVec.addElement(rel); //adding to the vector
              temp="G."+rel.getId()+" ";
              file.write(temp.getBytes());
            }//end if actor
          }//end if source is an object
        }//end of if (reltype)
      }//end of while
      if(flag== 0 && flag2==0 ){   //no generalizationand no specialization
        temp="/>";    //closing the actor
        file.write(temp.getBytes());
      }
      if (flag==0 && flag2==1) {
        temp="/>";    //closing the actor
        file.write(temp.getBytes());
      }
      if (flag==1){//there were a generalization
        temp="\">";//closing the actor
        file.write(temp.getBytes());
        g_list=generalizationVec.elements(); //list of all generalization relations
        while (g_list.hasMoreElements()) {
          relx=(IRelationEntry)g_list.nextElement();
          if(relx.getDestinationId()==obj.getId()){
            temp="<UML:Namespace.ownedElement>";
            file.write(temp.getBytes());
            temp="<UML:Generalization xmi.id=\"G."+relx.getId()+"\" name=\"\" visibility=\"public\" isSpecification=\"false\" discriminator=\"\" child=\"S."+relx.getDestinationId()+"\" parent=\"S."+relx.getSourceId()+"\" /> ";
            file.write(temp.getBytes());
            temp="</UML:Namespace.ownedElement>";
            file.write(temp.getBytes());
          }
        }
        temp="</UML:Actor>";//closing actor
        file.write(temp.getBytes());
      }//end of if
    }//end of try
    catch(IOException e)
    {
      System.out.println("error");
      return;
    }//end of catch

  }

//--------------------------- I S - I N - V E C -----------------------------------------
/**
 * Functions returns true if the given IEntry entry is already inside the given Vector vec.
 * @param IEntry entry
 * @param Vector vec
 * @return true - incase IEntry is in Vector; else return false
 */
  private boolean IsInVec(IEntry entry,Vector vec){
    Enumeration e;
    IEntry AbstractEntry;
    e=vec.elements();
    while(e.hasMoreElements()){
      AbstractEntry=(IEntry)e.nextElement();
      if(AbstractEntry.getId()==entry.getId())
        return true;
    }
    return false;
  }

//-------------------------------- U S E C A S E - D I A G R A M - C R E A T E -----------------------------------
/**
 *The main functions which activeted to generate an USECASE DIAGRAM
 * @param ISystem sys
 * @param FileOutputStream file
 * @param Vector usecaseVec - Vector which contains all the Diagram Elements of usecase for outside printing in XML
 * @param Vector usecaseIncludeVec - Contains all associations need to be printed as an Include
 * @param Vector usecaseInzoomIncludeVec - Contains all Inzoom associations need to be printed as an Include
 * @param Vector usecasespecVec - Contains all Specialization Relations that need to be printed as an associations
 * @param Vector usecaseassVec - Contains all associations to be printed
 * @param Vector usecaseassEndVec - Contains all associations End to be printed
 * @param Vector usecaseSterIncludeVec - Contains the printing of "Stereotype" Include in XML
 * @param Vector actorTagsVec - Contains all the TaggedValue of usecase for outside printing in XML
 * @param Vector usecaseExtendVec - Contains all associations need to be printed as an Extend
 * @param int op - represents the choice of a user: Root Level Only-->op=1; All OPD Levels-->op=2.
 * @param int levels - number of levels that the user have chosen.
 */
  public void  UseCaseDiagramCreate(ISystem sys, FileOutputStream file,Vector usecaseVec,Vector usecaseIncludeVec,Vector usecaseInzoomIncludeVec,Vector usecasespecVec,Vector usecaseassVec,Vector usecaseassEndVec,Vector usecaseSterIncludeVec,Vector actorTagsVec,Vector usecaseExtendVec,int op,int levels) throws IOException{
    try{
      mySys=sys;

      Enumeration link_list_d,link_list_s,rel_list_d,rel_list_s;
      Enumeration list, parentList,e,childrenList,g_list;
      String name="",temp="",temp1="";
      int childrenF,flag2=0,flag=0,new_level;
      int flagPr=0;
      IEntry abstractEntry,abstractEntry2;
      IProcessEntry parentProc,childProc,proc;
      IObjectEntry obj,obj2;
      ILinkEntry link;
      IRelationEntry rel,relx;
      IInstance inst;
      IProcessInstance procInst;
      IObjectInstance objInst;
      ISystemStructure MyStructure;
      IThingInstance parent;
      IOpd root;
      Activity_d act=new Activity_d();
 //-------------------------------------------------------------------
      Vector includeZoomVec=new Vector(4,2);
      Vector parentVec=new Vector(1,0);
      Vector actVec= new Vector(4,2);     //list of "actors"
      Vector distChildrenVec=new Vector(4,2);
      Vector generalizationVec= new Vector(4,2);
      Vector OpdVec= new Vector(4,2);
 //---------------------------------------------------------------------
       //printing of the beggining of the Use Case Diagram
      temp="<!--  ===============[UseCase Model] ====================  --> ";
      file.write(temp.getBytes());
      temp="<UML:Model xmi.id=\"G.0000.1\" name=\"UseCase diagram\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">";
      file.write(temp.getBytes());
      temp="<UML:Namespace.ownedElement>";
      file.write(temp.getBytes());
      temp = "<UML:Stereotype xmi.id=\"S.157.2021.23.6\" name=\"useCaseModel\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" icon=\"\" baseClass=\"Model\" extendedElement=\"G.0000.1\" />";
      file.write(temp.getBytes());

      MyStructure = sys.getISystemStructure();
      if (op==2) {  //all opd levels WERE SELECTED BY THE USER
        e = MyStructure.getElements();
        while (e.hasMoreElements()) {
          abstractEntry = (IEntry)e.nextElement();
          if(abstractEntry instanceof IProcessEntry){  //if process
            proc= (IProcessEntry)abstractEntry;;
            if(isUsecase(proc,MyStructure)){  //if the proc is use case
              PrintUsecase(MyStructure,file,proc,usecaseVec,usecaseExtendVec,usecaseIncludeVec,usecaseInzoomIncludeVec,generalizationVec,usecasespecVec,OpdVec,op);
            }//end of if(IsUsecase)
          }//end of if(proc)
          if(abstractEntry instanceof IObjectEntry){  //if object
            obj= (IObjectEntry)abstractEntry;;
            if(obj.isEnvironmental()){//if actor
              PrintActors(MyStructure,file,obj,actorTagsVec,generalizationVec);
            }//end of if(environmental)
          }//end of if(obj)
        }//end of while
      }

      new_level=levels-1;
      if (op==1) {  //USER SELECTED THE OPD-ROOT OPTION OR UNTILL LEVEL OPTION
        root=act.findRootOpd(MyStructure);
        e=MyStructure.getInstancesInOpd(root.getOpdId());     //all things in root opd
        while (e.hasMoreElements()){
          inst=(IInstance)e.nextElement();  //inst in root opd
          if (inst instanceof IObjectInstance){
            objInst=(IObjectInstance)inst;
            actVec.addElement(objInst.getIEntry());
          }
        }
        e=MyStructure.getInstancesInOpd(root.getOpdId());     //all things in root opd
        while (e.hasMoreElements()){
          inst=(IInstance)e.nextElement();  //inst in root opd
          if (inst instanceof IProcessInstance){    //if it is a process instance
            procInst=(IProcessInstance)inst;
            CreateProcVec(MyStructure, (IProcessEntry)(procInst.getIEntry()),OpdVec,new_level);
            CreateActVec(MyStructure,file, (IProcessEntry)(procInst.getIEntry()),actVec, new_level);
          }
        }

        //USECASE`S
        e=OpdVec.elements();
        while(e.hasMoreElements()){
          proc=(IProcessEntry)e.nextElement();
          if(isUsecase(proc,MyStructure)){
            PrintUsecase(MyStructure,file,proc,usecaseVec,usecaseExtendVec,usecaseIncludeVec,usecaseInzoomIncludeVec,generalizationVec,usecasespecVec,OpdVec,op);
          }//end of if(IsUsecase)
        }

        //ACTORS
        e=MyStructure.getElements();
        while(e.hasMoreElements()){
          abstractEntry=(IEntry)e.nextElement();
          if (abstractEntry instanceof IObjectEntry){
            obj=(IObjectEntry)abstractEntry;
            if (obj.isEnvironmental()){
              flagPr=0;
              list=actVec.elements();
              while ( (list.hasMoreElements()) && (flagPr==0) ){
                obj2=(IObjectEntry)list.nextElement();
                if (obj.getId() == obj2.getId()){
                   PrintActors(MyStructure,file,obj,actorTagsVec,generalizationVec);
                   flagPr=1;
                }
              }
            }
          }
        }
      }

//==================ASSOCIATION=======================
    e = MyStructure.getElements();
    while (e.hasMoreElements()) {
      abstractEntry = (IEntry)e.nextElement();
      if(abstractEntry instanceof IRelationEntry){  //if relation
        rel=(IRelationEntry)abstractEntry;
        abstractEntry=MyStructure.getIEntry(rel.getSourceId()); //souce
        if (abstractEntry instanceof IObjectEntry) {//if source is an object
          obj=(IObjectEntry)abstractEntry;
          if(obj.isEnvironmental()){  //if an actor
            if( ((op==1)&&(IsInVec(obj,actVec))) || (op==2) ){
              abstractEntry=MyStructure.getIEntry(rel.getDestinationId()); //destination
              if (abstractEntry instanceof IProcessEntry) {//if destination is a process
                proc=(IProcessEntry)abstractEntry;
                if(isUsecase(proc, MyStructure)){
                  if(((op==1)&&(IsInVec(proc,OpdVec)))||(op==2)){
                    if (assoc_needed(rel, proc, MyStructure,OpdVec,op)){//*****************************************************************
                      usecaseassVec.addElement(""+rel.getId()+"");
                      usecaseassEndVec.addElement(""+rel.getId()+".1");
                      usecaseassEndVec.addElement(""+rel.getId()+".2");
                      //printing
                      temp="<!--  ==================  [Association] ====================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:Association xmi.id=\"G."+rel.getId()+"\" name=\"{"+rel.getName()+"}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">";
                      file.write(temp.getBytes());
                      temp="<UML:Association.connection>";
                      file.write(temp.getBytes());
                      temp="<!--  ================== [AssociationEnd] =================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd xmi.id=\"G."+rel.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+proc.getId()+"\">";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="<UML:MultiplicityRange lower=\"1\" upper=\""+rel.getDestinationCardinality()+"\" />";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd>";
                      file.write(temp.getBytes());
                      temp="<!--  ================== [AssociationEnd] =================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd xmi.id=\"G."+rel.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj.getId()+"\">";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="<UML:MultiplicityRange lower=\"1\" upper=\""+rel.getSourceCardinality()+"\" />";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd>";
                      file.write(temp.getBytes());
                      temp="</UML:Association.connection>";
                      file.write(temp.getBytes());
                      temp="</UML:Association>";
                      file.write(temp.getBytes());
                    }//**************************************************************
                  }
                }//end of if(destination is a usecase)
              }//end of if(destinatin is a process)
            }
          }//end of if(source is an actor)
        }//end of if(source is an object)
        if (abstractEntry instanceof IProcessEntry) {//if source is a process
          proc=(IProcessEntry)abstractEntry;
          if(isUsecase(proc, MyStructure)){//if source is a  usecase
            if(((op==1)&&(IsInVec(proc,OpdVec)))||(op==2)){
              abstractEntry=MyStructure.getIEntry(rel.getDestinationId()); //destination
              if (abstractEntry instanceof IObjectEntry) {//if destination is an object
                obj=(IObjectEntry)abstractEntry;
                if(obj.isEnvironmental()){ //if an actor
                  if(((op==1)&&(IsInVec(obj,actVec)))||(op==2)){
                    if(assoc_needed(proc,rel,MyStructure,OpdVec,op)){
                      usecaseassVec.addElement(""+rel.getId()+"");
                      usecaseassEndVec.addElement(""+rel.getId()+".1");
                      usecaseassEndVec.addElement(""+rel.getId()+".2");
                      //printing
                      temp="<!--  ==================  [Association] ====================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:Association xmi.id=\"G."+rel.getId()+"\" name=\"{"+rel.getName()+"}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">";
                      file.write(temp.getBytes());
                      temp="<UML:Association.connection>";
                      file.write(temp.getBytes());
                      temp="<!--  ================== [AssociationEnd] =================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd xmi.id=\"G."+rel.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj.getId()+"\">";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="<UML:MultiplicityRange lower=\"1\" upper=\""+rel.getDestinationCardinality()+"\" />";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd>";
                      file.write(temp.getBytes());
                      temp="<!--  ================== [AssociationEnd] =================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd xmi.id=\"G."+rel.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+proc.getId()+"\">";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="<UML:MultiplicityRange lower=\"1\" upper=\""+rel.getSourceCardinality()+"\" />";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd>";
                      file.write(temp.getBytes());
                      temp="</UML:Association.connection>";
                      file.write(temp.getBytes());
                      temp="</UML:Association>";
                      file.write(temp.getBytes());
                    }
                  }
                }//end if(destination is an actor)
              }//end if(destination is an object)
            }
          }//end if(source is a usecase)
        }//end if(source is a process)
      }//end if relation

      if(abstractEntry instanceof ILinkEntry){  //if link
        link=(ILinkEntry)abstractEntry;
        abstractEntry=MyStructure.getIEntry(link.getSourceId()); //source
        if (abstractEntry instanceof IObjectEntry) {//if source is an object
          obj=(IObjectEntry)abstractEntry;
          if(obj.isEnvironmental()){  //if an actor
            if(((op==1)&&(IsInVec(obj,actVec)))||(op==2)){
              abstractEntry=MyStructure.getIEntry(link.getDestinationId()); //destination
              if (abstractEntry instanceof IProcessEntry) {//if destination is a process
                proc=(IProcessEntry)abstractEntry;
                if(isUsecase(proc, MyStructure)){
                  if(((op==1)&&(IsInVec(proc,OpdVec)))||(op==2)){
                    if(assoc_needed(link,proc,MyStructure,OpdVec,op)){
                      usecaseassVec.addElement(""+link.getId()+"");
                      usecaseassEndVec.addElement(""+link.getId()+".1");
                      usecaseassEndVec.addElement(""+link.getId()+".2");
                      //printing
                      temp="<!--  ==================  [Association] ====================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:Association xmi.id=\"G."+link.getId()+"\" name=\"{"+link.getName()+"}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">";
                      file.write(temp.getBytes());
                      temp="<UML:Association.connection>";
                      file.write(temp.getBytes());
                      temp="<!--  ================== [AssociationEnd] =================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd xmi.id=\"G."+link.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+proc.getId()+"\">";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd>";
                      file.write(temp.getBytes());
                      temp="<!--  ================== [AssociationEnd] =================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd xmi.id=\"G."+link.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj.getId()+"\">";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd>";
                      file.write(temp.getBytes());
                      temp="</UML:Association.connection>";
                      file.write(temp.getBytes());
                      temp="</UML:Association>";
                      file.write(temp.getBytes());
                    }
                  }
                }//end of if(destination is a usecase)
              }//end of if(destinatin is a process)
            }
          }//end of if(source is an actor)
        }//end of if(source is an object)
        if (abstractEntry instanceof IProcessEntry) {//if source is a process
          proc=(IProcessEntry)abstractEntry;
          if(isUsecase(proc, MyStructure)){//if source is a  usecase
            if(((op==1)&&(IsInVec(proc,OpdVec)))||(op==2)){
              abstractEntry=MyStructure.getIEntry(link.getDestinationId()); //destination
              if (abstractEntry instanceof IObjectEntry) {//if destination is an object
                obj=(IObjectEntry)abstractEntry;
                if(obj.isEnvironmental()){ //if an actor
                  if(((op==1)&&(IsInVec(obj,actVec)))||(op==2)){
                    if(assoc_needed(proc,link,MyStructure,OpdVec,op)){
                      usecaseassVec.addElement(""+link.getId()+"");
                      usecaseassEndVec.addElement(""+link.getId()+".1");
                      usecaseassEndVec.addElement(""+link.getId()+".2");
                      //printing
                      temp="<!--  ==================  [Association] ====================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:Association xmi.id=\"G."+link.getId()+"\" name=\"{"+link.getName()+"}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">";
                      file.write(temp.getBytes());
                      temp="<UML:Association.connection>";
                      file.write(temp.getBytes());
                      temp="<!--  ================== [AssociationEnd] =================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd xmi.id=\"G."+link.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj.getId()+"\">";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd>";
                      file.write(temp.getBytes());
                      temp="<!--  ================== [AssociationEnd] =================  --> ";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd xmi.id=\"G."+link.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+proc.getId()+"\">";
                      file.write(temp.getBytes());
                      temp="<UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="<UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity.range>";
                      file.write(temp.getBytes());
                      temp="</UML:Multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd.multiplicity>";
                      file.write(temp.getBytes());
                      temp="</UML:AssociationEnd>";
                      file.write(temp.getBytes());
                      temp="</UML:Association.connection>";
                      file.write(temp.getBytes());
                      temp="</UML:Association>";
                      file.write(temp.getBytes());
                    }
                  }
                }//end if(destination is an actor)
              }//end if(destination is an object)
            }
          }//end if(source is a usecase)
        }//end if(source is a process)
      }//end if link
    }//end of while

//-----Include association print------------------------------------------
    e = usecaseIncludeVec.elements();
    IRelationEntry tempRel;
    while (e.hasMoreElements()) {
      tempRel=(IRelationEntry)e.nextElement();
      usecaseSterIncludeVec.addElement(""+tempRel.getId()+"");
      temp="<!--  ==================  [Association] ====================  --> ";
      file.write(temp.getBytes());
      temp="<UML:Association xmi.id=\"G."+tempRel.getId()+"\" name=\"\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">";
      file.write(temp.getBytes());
      temp="<UML:Association.connection>";
      file.write(temp.getBytes());
      temp="<!--  ================== [AssociationEnd] =================  --> ";
      file.write(temp.getBytes());
      temp="<UML:AssociationEnd xmi.id=\"G."+tempRel.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+tempRel.getDestinationId()+"\">";
      file.write(temp.getBytes());
      temp="<UML:AssociationEnd.multiplicity>";
      file.write(temp.getBytes());
      temp="<UML:Multiplicity>";
      file.write(temp.getBytes());
      temp="<UML:Multiplicity.range>";
      file.write(temp.getBytes());
      temp="<UML:MultiplicityRange lower=\"1\" upper=\""+tempRel.getDestinationCardinality()+"\" />";
      file.write(temp.getBytes());
      temp="</UML:Multiplicity.range>";
      file.write(temp.getBytes());
      temp="</UML:Multiplicity>";
      file.write(temp.getBytes());
      temp="</UML:AssociationEnd.multiplicity>";
      file.write(temp.getBytes());
     temp="</UML:AssociationEnd>";
     file.write(temp.getBytes());
     temp="<!--  ================== [AssociationEnd] =================  --> ";
     file.write(temp.getBytes());
     temp="<UML:AssociationEnd xmi.id=\"G."+tempRel.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+tempRel.getSourceId()+"\">";
     file.write(temp.getBytes());
     temp="<UML:AssociationEnd.multiplicity>";
     file.write(temp.getBytes());
     temp="<UML:Multiplicity>";
     file.write(temp.getBytes());
     temp="<UML:Multiplicity.range>";
     file.write(temp.getBytes());
     temp="<UML:MultiplicityRange lower=\"1\" upper=\""+tempRel.getSourceCardinality()+"\" />";
     file.write(temp.getBytes());
     temp="</UML:Multiplicity.range>";
     file.write(temp.getBytes());
     temp="</UML:Multiplicity>";
     file.write(temp.getBytes());
     temp="</UML:AssociationEnd.multiplicity>";
     file.write(temp.getBytes());
     temp="</UML:AssociationEnd>";
     file.write(temp.getBytes());
     temp="</UML:Association.connection>";
     file.write(temp.getBytes());
     temp="</UML:Association>";
     file.write(temp.getBytes());


   }//end of while
   e = usecaseInzoomIncludeVec.elements();
   IProcessEntry tempProc;
   while (e.hasMoreElements()) {
     tempProc=(IProcessEntry)e.nextElement();
     usecaseSterIncludeVec.addElement(""+tempProc.getId()+".3");
     temp="<!--  ==================  [Association] ====================  --> ";
     file.write(temp.getBytes());
     temp="<UML:Association xmi.id=\"G."+tempProc.getId()+".3\" name=\"\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">";
     file.write(temp.getBytes());
     temp="<UML:Association.connection>";
     file.write(temp.getBytes());
     temp="<!--  ================== [AssociationEnd] =================  --> ";
     file.write(temp.getBytes());
     temp="<UML:AssociationEnd xmi.id=\"G."+tempProc.getId()+".3.1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+tempProc.getId()+"\">";
     file.write(temp.getBytes());
     temp="<UML:AssociationEnd.multiplicity>";
     file.write(temp.getBytes());
     temp="<UML:Multiplicity>";
     file.write(temp.getBytes());
     temp="<UML:Multiplicity.range>";
     file.write(temp.getBytes());
     temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";
     file.write(temp.getBytes());
     temp="</UML:Multiplicity.range>";
     file.write(temp.getBytes());
     temp="</UML:Multiplicity>";
     file.write(temp.getBytes());
     temp="</UML:AssociationEnd.multiplicity>";
     file.write(temp.getBytes());
     temp="</UML:AssociationEnd>";
     file.write(temp.getBytes());
     temp="<!--  ================== [AssociationEnd] =================  --> ";
     file.write(temp.getBytes());
     temp="<UML:AssociationEnd xmi.id=\"G."+tempProc.getId()+".3.2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+((IProcessEntry)e.nextElement()).getId()+"\">";
     file.write(temp.getBytes());
     temp="<UML:AssociationEnd.multiplicity>";
     file.write(temp.getBytes());
     temp="<UML:Multiplicity>";
     file.write(temp.getBytes());
     temp="<UML:Multiplicity.range>";
     file.write(temp.getBytes());
     temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";
     file.write(temp.getBytes());
     temp="</UML:Multiplicity.range>";
     file.write(temp.getBytes());
     temp="</UML:Multiplicity>";
     file.write(temp.getBytes());
     temp="</UML:AssociationEnd.multiplicity>";
     file.write(temp.getBytes());
     temp="</UML:AssociationEnd>";
     file.write(temp.getBytes());
     temp="</UML:Association.connection>";
     file.write(temp.getBytes());
     temp="</UML:Association>";
     file.write(temp.getBytes());


   }//end of while
   e = usecaseExtendVec.elements();
   ILinkEntry tempLink;
   String tempName ="";
        while (e.hasMoreElements()) {
          tempLink=(ILinkEntry)e.nextElement();
          if (tempLink.getLinkType()==OpcatConstants.EXCEPTION_LINK)
            tempName = "timeout("+tempLink.getCondition()+")";
          if (tempLink.getLinkType()==OpcatConstants.INVOCATION_LINK)
            tempName = ""+((IProcessEntry)MyStructure.getIEntry(tempLink.getSourceId())).getName()+" stopped";

                temp="<!--  ==================  [Association] ====================  --> ";
                file.write(temp.getBytes());
                temp="<UML:Association xmi.id=\"G."+tempLink.getId()+"\" name=\""+tempName+"\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">";
                file.write(temp.getBytes());
                temp="<UML:Association.connection>";
                file.write(temp.getBytes());
                temp="<!--  ================== [AssociationEnd] =================  --> ";
                file.write(temp.getBytes());
                temp="<UML:AssociationEnd xmi.id=\"G."+tempLink.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+tempLink.getDestinationId()+"\">";
                file.write(temp.getBytes());
                temp="<UML:AssociationEnd.multiplicity>";
                file.write(temp.getBytes());
                temp="<UML:Multiplicity>";
                file.write(temp.getBytes());
                temp="<UML:Multiplicity.range>";
                file.write(temp.getBytes());
                temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";
                file.write(temp.getBytes());
                temp="</UML:Multiplicity.range>";
                file.write(temp.getBytes());
                temp="</UML:Multiplicity>";
                file.write(temp.getBytes());
                temp="</UML:AssociationEnd.multiplicity>";
                file.write(temp.getBytes());
                temp="</UML:AssociationEnd>";
                file.write(temp.getBytes());
                temp="<!--  ================== [AssociationEnd] =================  --> ";
                file.write(temp.getBytes());
                temp="<UML:AssociationEnd xmi.id=\"G."+tempLink.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+tempLink.getSourceId()+"\">";
                file.write(temp.getBytes());
                temp="<UML:AssociationEnd.multiplicity>";
                file.write(temp.getBytes());
                temp="<UML:Multiplicity>";
                file.write(temp.getBytes());
                temp="<UML:Multiplicity.range>";
                file.write(temp.getBytes());
                temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";
                file.write(temp.getBytes());
                temp="</UML:Multiplicity.range>";
                file.write(temp.getBytes());
                temp="</UML:Multiplicity>";
                file.write(temp.getBytes());
                temp="</UML:AssociationEnd.multiplicity>";
                file.write(temp.getBytes());
                temp="</UML:AssociationEnd>";
                file.write(temp.getBytes());
                temp="</UML:Association.connection>";
                file.write(temp.getBytes());
                temp="</UML:Association>";
                file.write(temp.getBytes());
                tempName ="";
       }//end of while

        e=usecaseSterIncludeVec.elements();
        if (e.hasMoreElements()) {
          temp = "<UML:Stereotype xmi.id=\"S.1.1.1.1\" name=\"include\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" icon=\"\" baseClass=\"Association\" extendedElement=\"";
          file.write(temp.getBytes());
        }
        while (e.hasMoreElements()) {
          temp = "G."+e.nextElement()+" ";
          file.write(temp.getBytes());
        }
        e=usecaseSterIncludeVec.elements();
        if (e.hasMoreElements()) {
          temp = "\" />";
          file.write(temp.getBytes());
        }


        e=usecaseExtendVec.elements();
        if (e.hasMoreElements()) {
          temp = "<UML:Stereotype xmi.id=\"S.2.2.2.2\" name=\"extend\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" icon=\"\" baseClass=\"Association\" extendedElement=\"";
          file.write(temp.getBytes());
        }
        while (e.hasMoreElements()) {
          temp = "G."+((ILinkEntry)e.nextElement()).getId()+" ";
          file.write(temp.getBytes());
        }
        e=usecaseExtendVec.elements();
        if (e.hasMoreElements()) {
          temp = "\" />";
          file.write(temp.getBytes());
        }

      temp="</UML:Namespace.ownedElement>";
      file.write(temp.getBytes());
      temp="</UML:Model>";
      file.write(temp.getBytes());


 }//end of try
    catch(IOException e)
    {
      System.out.println("error");
      return;
    }//end of catch

  }//end of func
}//end of class
