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
import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.IRelationEntry;
import exportedAPI.opcatAPI.IStateEntry;
import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPI.IThingInstance;

/**
 * class Class_d creates the "Class Diagram" from OPM
 */
class Class_d {

  public Class_d() {
  }
  ISystem mySys;
  /**
   * This function returns true if the given IObjectEntry obj is a feature of another IObjectEntry,
   * (that is connected as a destination by EXHIBITION_RELATION to another IObjectEntry) else return false.
   * @param ISystemStructure MyStructure
   * @param IObjectEntry obj
   * @return true-in case the given object is an feature, otherwise false.
   */
  private boolean isFeature(ISystemStructure MyStructure,IObjectEntry obj){
    Enumeration e;
    IEntry abstractEntry;
    IRelationEntry rel;

    e = MyStructure.getElements();
    while(e.hasMoreElements()){
      abstractEntry = (IEntry)e.nextElement();
      if(abstractEntry instanceof IRelationEntry){
        rel= (IRelationEntry)abstractEntry;
        if((rel.getRelationType()==OpcatConstants.EXHIBITION_RELATION)&&(rel.getDestinationId()== obj.getId() )){
          return true;
        }
      }
    }
    return false;
  }

/**
 *This functions calculates which class represents the current IObjectEntry
 * @param ISystemStructure MyStructure
 * @param IEntry ent-it can be an IStateEntry or an IObjectEntry only.
 * @return IObjectEntry - an object which represents the class
 */
  private IObjectEntry ReturnClass(ISystemStructure MyStructure,IEntry ent){
    ISystemStructure ms=MyStructure;
    IEntry absEntry=ent;
    IObjectEntry obj,obj2;
    IStateEntry state;
    Enumeration list;
    IRelationEntry rel;

    if (absEntry  instanceof IStateEntry){//if ent is a stateEntry
      state=(IStateEntry)absEntry;
      absEntry=(IEntry)state.getParentIObjectEntry(); //get the parent-the object which contains the fiven state
    }
    obj2=(IObjectEntry)absEntry;    //absEntry is now an object for sure (and not a state)
    if (absEntry instanceof IObjectEntry){
      obj=(IObjectEntry)absEntry;
      list=obj.getRelationByDestination();//all relations that the object is a destination in them
      while (list.hasMoreElements()) {
        rel=(IRelationEntry)list.nextElement();
        if (rel.getRelationType()==OpcatConstants.EXHIBITION_RELATION){
          obj2=(IObjectEntry)MyStructure.getIEntry((rel.getSourceId()));//source of the relation
        }
      }
    }
    return obj2;
  }

  /**
   * This function finds the "parent" of a given IObjectEntry
   * @param obj-IObjectEntry to which need to be find a parent
   * @return String name of the parent thing o "-1" if there is no parent
   */
  private String getParentThing(IObjectEntry obj){
    Enumeration enum;
    IObjectInstance inst;
    IThingInstance parentThing;
    String result="-1";

    enum = obj.getInstances();
    while (enum.hasMoreElements()){
      inst = (IObjectInstance)enum.nextElement();
      parentThing = inst.getParentIThingInstance();
      if (parentThing!=null)
        result = parentThing.getIEntry().getName();
    }
    return result;
  }

/**
 *This function creates the part of Class Diagram in XML code
 * @param ISystem sys
 * @param FileOutputStream file
 * @param Vector tagsVec - responsible for outside printing of the part TaggedValue in XML
 * @param Vector diagramElementsVec - responsible for outside printing of classes in XML
 * @param Vector specVec - responsible for outside printing in XML
 * @param Vector assEndVec - responsible for outside printing of assosiationEnd in class diagram
 * @param Vector assVec - responsible for outside printing of assosiations in class diagram
 */
  public void  ClassDiagramCreate(ISystem sys, FileOutputStream file,Vector tagsVec, Vector diagramElementsVec,Vector specVec, Vector assEndVec, Vector assVec) throws IOException{
    try{
      mySys=sys;
      IEntry abstractEntry,abstractEntry2;
      IEntry obj11,obj22;
      IObjectEntry obj,dist,obj1,obj2;
      IStateEntry state;
      IProcessEntry proc;
      IRelationEntry rel,spec_rel,gen_rel;
      IRelationEntry relx,agg_rel;
      ISystemStructure MyStructure;
      Enumeration e,en,rel_list;
      Enumeration data,datas;
      Enumeration g_list,agg_list,uni_list,inst_list,childrenList ;
      String temp="1";
      Vector dataType = new Vector(10,5);
      Vector generalizationVec=new Vector(4,2);
      IObjectInstance objInst;
      long objId;
      int flag;
      Enumeration diagramElementsEnum;
      String tempId;


      MyStructure = mySys.getISystemStructure();
      e = MyStructure.getElements();

      while (e.hasMoreElements()) {
        abstractEntry = (IEntry)e.nextElement();
        if(abstractEntry instanceof IObjectEntry){  //if object
          obj= (IObjectEntry)abstractEntry;;
          if ((!obj.isPhysical()) &&(!obj.isEnvironmental()) && (!isFeature(MyStructure,obj))){//a class (by definition)
            String scope = "";
            if (obj.getScope().compareTo("0")== 0)
              scope="Public";
            if(obj.getScope().compareTo("1") == 0)
              scope="Protected";
            if(obj.getScope().compareTo("2") == 0)
              scope="Private";
            //class printout
            temp = "<!--  ================   [Class] ====================   -->\n";
            file.write(temp.getBytes());
//**********************************************************
            String parentName=getParentThing(obj);
            if (parentName=="-1")
              parentName="";
            else
              parentName="_"+parentName+"";
//**********************************************************
            temp="<UML:Class xmi.id=\"S."+obj.getId()+"\" name=\""+obj.getName()+""+parentName+" \" visibility=\""+scope+"\" isSpecification=\"false\" isRoot=\"true\" isLeaf=\"true\" isAbstract=\"false\" isActive=\"false\" namespace=\"G.0000\" ";
            file.write(temp.getBytes());
            diagramElementsVec.addElement(""+obj.getId()+"");
            tagsVec.addElement(""+obj.getId()+"");
            //specialization
            rel_list=obj.getRelationBySource(); //list of relation in which obj is the source
            flag=0;
            while(rel_list.hasMoreElements()){
              spec_rel=(IRelationEntry)rel_list.nextElement();
              if (spec_rel.getRelationType()==OpcatConstants.SPECIALIZATION_RELATION){//if it is specialization
                long dist_id=spec_rel.getDestinationId();
                dist=(IObjectEntry) MyStructure.getIEntry(dist_id);//destination object
                if((!dist.isPhysical()) &&(!dist.isEnvironmental())&& (!isFeature(MyStructure,dist))) {
                  if (flag==0) {
                    temp= "specialization=\" ";
                    file.write(temp.getBytes());
                    flag=1;
                  } //end of if
                  temp="G."+spec_rel.getId()+" ";
                  file.write(temp.getBytes());
                  specVec.addElement(""+spec_rel.getId()+"");
                }//end of if
              }//end of if
            }//end of rel_list while
            if (flag==1) {//means there were an specalization
              temp= "\" ";  //closing the specialization by "
              file.write(temp.getBytes());
            }
            //generalization
            rel_list=obj.getRelationByDestination();  //list of relations in which obj is the destination
            flag=0;
            while(rel_list.hasMoreElements()){
              gen_rel=(IRelationEntry)rel_list.nextElement();
              if (gen_rel.getRelationType()==OpcatConstants.SPECIALIZATION_RELATION){
                long source_id=gen_rel.getSourceId();
                dist=(IObjectEntry) MyStructure.getIEntry(source_id); //source object
                if((!dist.isPhysical()) &&(!dist.isEnvironmental())&& (!isFeature(MyStructure,dist))){
                  if (flag==0) {
                    temp= "generalization=\" ";
                    file.write(temp.getBytes());
                    flag=1;
                  }
                  temp="G."+gen_rel.getId()+" ";
                  file.write(temp.getBytes());
                  relx=(IRelationEntry)MyStructure.getIEntry(gen_rel.getId());
                  generalizationVec.addElement(relx); //adding to the vector
                }//end of if
              }//end of if
            }//end of rel_list while
            if (flag==1) { //mens there were generalization
              temp= "\""; //closing the generalization by "
              file.write(temp.getBytes());
            }
            temp= ">\n"; //closing the row of class
            file.write(temp.getBytes());
            if (flag==1) {//in case of generalization
              g_list=generalizationVec.elements(); //list of all generalization relations
              while (g_list.hasMoreElements()) {
                relx=(IRelationEntry)g_list.nextElement();
                if (relx.getDestinationId() == obj.getId()) {
                temp= "<UML:Namespace.ownedElement>\n";
                file.write(temp.getBytes());
                temp= "<UML:Generalization xmi.id=\"G."+relx.getId()+"\" name=\"\" visibility=\"public\" isSpecification=\"false\" discriminator=\"\" child=\"S."+relx.getDestinationId()+"\" parent=\"S."+relx.getSourceId()+"\"/>\n";
                file.write(temp.getBytes());
                temp= "</UML:Namespace.ownedElement>\n";
                file.write(temp.getBytes());
              }
              }//end of while
            }//end of if
//=======atributes
            temp="<UML:Classifier.feature>\n";
            file.write(temp.getBytes());

            if(obj.getStates().hasMoreElements()){  //if the object has states - add attribute "status"
              dataType.addElement(obj); //add the type to the datatype list
              temp="<!-- === Attribute ====================  -->\n";
              file.write(temp.getBytes());
              if (obj.getScope().compareTo("0")== 0)
                scope="Public";
              if(obj.getScope().compareTo("1") == 0)
                scope="Protected";
              if(obj.getScope().compareTo("2") == 0)
                scope="Private";
              temp="<UML:Attribute xmi.id=\"S."+obj.getId()+".state\" name=\"status\" visibility=\""+scope+"\" isSpecification=\"false\" ownerScope=\"instance\" changeability=\"changeable\" targetScope=\"instance\" type=\"G."+obj.getId()+"\">\n";
              file.write(temp.getBytes());
              temp="<UML:StructuralFeature.multiplicity>\n";
              file.write(temp.getBytes());
              temp="<UML:Multiplicity>\n";
              file.write(temp.getBytes());
              temp="<UML:Multiplicity.range>\n";
              file.write(temp.getBytes());
              temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" /> \n";
              file.write(temp.getBytes());
              temp="</UML:Multiplicity.range>\n";
              file.write(temp.getBytes());
              temp=" </UML:Multiplicity>\n";
              file.write(temp.getBytes());
              temp="</UML:StructuralFeature.multiplicity>\n";
              file.write(temp.getBytes());
              temp="<UML:Attribute.initialValue>\n";
              file.write(temp.getBytes());
              temp=" <UML:Expression language=\"\" body=\""+obj.getInitialValue()+"\" /> \n";
              file.write(temp.getBytes());
              temp="</UML:Attribute.initialValue>\n";
              file.write(temp.getBytes());
              temp="</UML:Attribute>\n";
              file.write(temp.getBytes());
            }//end of if atrib

            en=obj.getRelationBySource(); //list of all that connected to the given object,object is the source
            while (en.hasMoreElements()){
              rel=(IRelationEntry)en.nextElement();

              if(rel.getRelationType()==OpcatConstants.EXHIBITION_RELATION){ //if attribute
                if(MyStructure.getIEntry(rel.getDestinationId()) instanceof IObjectEntry){ //and if object
                  dist=(IObjectEntry)MyStructure.getIEntry(rel.getDestinationId());
                  if((!dist.isEnvironmental()) &&(!dist.isPhysical())){
                    objId=0;  //if datatype is undefined: type=G.0, else type=G.obj_id
                    if(((dist.getStates()).hasMoreElements())||(dist.getType().compareTo("")!=0)){
                      dataType.addElement(dist);
                      objId=dist.getId(); //datatype checking
                    }
                    temp="<!-- === Attribute ====================  -->\n";
                    file.write(temp.getBytes());
                    if (dist.getScope().compareTo("0")== 0)
                      scope="Public";
                    if(dist.getScope().compareTo("1") == 0)
                      scope="Protected";
                    if(dist.getScope().compareTo("2") == 0)
                      scope="Private";
                    temp="<UML:Attribute xmi.id=\"S."+dist.getId()+"\" name=\""+dist.getName()+"\" visibility=\""+scope+"\" isSpecification=\"false\" ownerScope=\"instance\" changeability=\"changeable\" targetScope=\"instance\" type=\"G."+objId+"\">\n";
                    file.write(temp.getBytes());
                    temp="<UML:StructuralFeature.multiplicity>\n";
                    file.write(temp.getBytes());
                    temp="<UML:Multiplicity>\n";
                    file.write(temp.getBytes());
                    temp="<UML:Multiplicity.range>\n";
                    file.write(temp.getBytes());
                    temp="<UML:MultiplicityRange lower=\"1\" upper=\"1\" /> \n";
                    file.write(temp.getBytes());
                    temp="</UML:Multiplicity.range>\n";
                    file.write(temp.getBytes());
                    temp=" </UML:Multiplicity>\n";
                    file.write(temp.getBytes());
                    temp="</UML:StructuralFeature.multiplicity>\n";
                    file.write(temp.getBytes());
                    temp="<UML:Attribute.initialValue>\n";
                    file.write(temp.getBytes());
                    temp=" <UML:Expression language=\"\" body=\""+dist.getInitialValue()+"\" /> \n";
                    file.write(temp.getBytes());
                    temp="</UML:Attribute.initialValue>\n";
                    file.write(temp.getBytes());
                    temp="</UML:Attribute>\n";
                    file.write(temp.getBytes());
                  }//end of if
                }//end of if
              }
            }//end of while

            en=obj.getRelationBySource(); //list of all that connected to the given object,object is the source
            while (en.hasMoreElements()){
              rel=(IRelationEntry)en.nextElement();
              if(rel.getRelationType()==OpcatConstants.EXHIBITION_RELATION){ //if attribute
                if((MyStructure.getIEntry(rel.getDestinationId())) instanceof IProcessEntry){
                  //if process connected to object as attribute==> metoda of a class
                  proc=(IProcessEntry)MyStructure.getIEntry(rel.getDestinationId());
                  if (proc.getScope().compareTo("0")== 0)
                    scope="Public";
                  if(proc.getScope().compareTo("1") == 0)
                    scope="Protected";
                  if(proc.getScope().compareTo("2") == 0)
                    scope="Private";
                  temp="<!--  =============  [Operation] ====================  --> \n";
                  file.write(temp.getBytes());
                  temp="<UML:Operation xmi.id=\"S."+rel.getId()+"\" name=\""+proc.getName()+"\" visibility=\""+scope+"\" isSpecification=\"false\" ownerScope=\"instance\" isQuery=\"false\" concurrency=\"sequential\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" specification=\"\">\n";
                  file.write(temp.getBytes());
                  temp="<UML:BehavioralFeature.parameter>\n";
                  file.write(temp.getBytes());

                  //input parameter of a methoda
                  Enumeration link_list;
                  ILinkEntry link;
                  link_list=proc.getLinksByDestination();
                  while (link_list.hasMoreElements()){
                    link=(ILinkEntry)link_list.nextElement();
                    if (link.getLinkType()==OpcatConstants.INSTRUMENT_LINK){
                      long source_id=link.getSourceId();
                      IObjectEntry param=(IObjectEntry)MyStructure.getIEntry(source_id);
                      if ((!obj.isPhysical()) &&(!obj.isEnvironmental())) {
                        objId=0;
                        if(((param.getStates()).hasMoreElements())||(param.getType().compareTo("")!=0))
                          objId=param.getId();
                        temp="<UML:Parameter xmi.id=\"XX."+param.getId()+"\" name=\""+param.getName()+"\" visibility=\"public\" isSpecification=\"false\" kind=\"inout\" type=\"G."+objId+"\">\n";
                        file.write(temp.getBytes());
                        temp="<UML:Parameter.defaultValue>\n";
                        file.write(temp.getBytes());
                        temp="<UML:Expression language=\"\" body=\"\" /> \n";
                        file.write(temp.getBytes());
                        temp="</UML:Parameter.defaultValue>\n";
                        file.write(temp.getBytes());
                        temp="</UML:Parameter>\n";
                        file.write(temp.getBytes());
                      }//end of if
                    }//end of if
                  }//end of while
                    //output type of a methoda=always void
                  temp="<UML:Parameter xmi.id=\"XX."+proc.getId()+"\" name=\""+proc.getName()+".Return\" visibility=\"public\" isSpecification=\"false\" kind=\"return\" type=\"G.00\" /> \n";
                  file.write(temp.getBytes());
                  temp="</UML:BehavioralFeature.parameter>\n";
                  file.write(temp.getBytes());
                  temp="</UML:Operation>\n";
                  file.write(temp.getBytes());
                }//end of if
              }//end of if
            }//end of while
            //end of a class
            temp="</UML:Classifier.feature>\n";
            file.write(temp.getBytes());
            temp="</UML:Class>\n";
            file.write(temp.getBytes());
          }//end of if obj is physical
        }//end of if instanceof obj
/****************************************************************************************/
      }
/****************************************************************************************/
//=====printouts of a datatype
        //void DataType
        temp="<!--  ============ <void>    [DataType] ============== -->\n";
        file.write(temp.getBytes());
        temp="<UML:DataType xmi.id=\"G.00\" name=\"void\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" />\n";
        file.write(temp.getBytes());
        //undefined DataType
        temp="<!--  ============ <undefined>    [DataType] ============== -->\n";
        file.write(temp.getBytes());
        temp="<UML:DataType xmi.id=\"G.0\" name=\"undefined\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" />\n";
        file.write(temp.getBytes());
        //all other DataTypes from the list created before
        data=dataType.elements();
        while(data.hasMoreElements()){
          obj=(IObjectEntry)data.nextElement();
          datas=obj.getStates(); //if the datatype is a range of states
          flag=0;
          if (datas.hasMoreElements()){ //list of states
            temp="<!--  ============ [DataType] ============== -->\n";
            file.write(temp.getBytes());
            temp="<UML:DataType xmi.id=\"G."+obj.getId()+"\" name=\"{";
            file.write(temp.getBytes());
            while (datas.hasMoreElements()){  //going trought all the states
              if(flag==1){
                temp=",";
                file.write(temp.getBytes());
              }
              flag=1;
              temp=((IStateEntry)datas.nextElement()).getName();
              file.write(temp.getBytes());
            }//end of while
            temp="}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" />\n";
            file.write(temp.getBytes());
          }//end of if list of states
          else{ //not states but defined (boolean, int, string...
            temp="<!--  ============ [DataType] ============== -->\n";
            file.write(temp.getBytes());
            temp="<UML:DataType xmi.id=\"G."+obj.getId()+"\" name=\""+obj.getType()+"\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\" />\n";
            file.write(temp.getBytes());
          }//end of else
        }   //the end of while the data type

//========associations
      //====aggregation
        agg_list = MyStructure.getElements();
        while (agg_list.hasMoreElements()) {
          abstractEntry2 = (IEntry)agg_list.nextElement();
          if(abstractEntry2 instanceof IRelationEntry) { //if relation
            agg_rel= (IRelationEntry)abstractEntry2;
            if (agg_rel.getRelationType() ==OpcatConstants.AGGREGATION_RELATION) {
              long s_id=agg_rel.getSourceId();
              long d_id=agg_rel.getDestinationId();
              if ((MyStructure.getIEntry(s_id) instanceof IObjectEntry) && (MyStructure.getIEntry(d_id) instanceof IObjectEntry)) {
              obj1=(IObjectEntry)MyStructure.getIEntry(s_id);
              obj2=(IObjectEntry)MyStructure.getIEntry(d_id);
              if ((!obj1.isPhysical())&&(!obj2.isPhysical())&&(!obj1.isEnvironmental())&&(!obj2.isEnvironmental())) {
                obj1=ReturnClass(MyStructure,(IEntry)obj1);//=====//
                obj2=ReturnClass(MyStructure,(IEntry)obj2);//=====//
                if ((!obj1.isPhysical())&&(!obj2.isPhysical())&&(!obj1.isEnvironmental())&&(!obj2.isEnvironmental())) {
                  assVec.addElement(""+agg_rel.getId()+"");
                  assEndVec.addElement(""+agg_rel.getId()+".1");
                  assEndVec.addElement(""+agg_rel.getId()+".2");
                  temp = "<!-- =============== [Association] ====================  --> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Association xmi.id=\"G."+agg_rel.getId()+"\" name=\"{"+obj1.getName()+" - "+obj2.getName()+"}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Association.connection> \n";
                  file.write(temp.getBytes());
                  temp = "<!--  ================[AssociationEnd] ===============  -->  \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd xmi.id=\"G."+agg_rel.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj2.getId()+"\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";  //"+agg_rel.getDestinationCardinality()+"
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd> \n";
                  file.write(temp.getBytes());
                  temp = "<!--  ================[AssociationEnd] ===============  -->  \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd xmi.id=\"G."+agg_rel.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"aggregate\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj1.getId()+"\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";  //"+agg_rel.getSourceCardinality()+"
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Association.connection> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Association> \n";
                  file.write(temp.getBytes());
                }
                }
              }//end of if obj is physical
            }//end of if agg_rel
          }//end of if instance of rel
        }//end of while
      //====end of aggregation

      //====uni-directional relation
        uni_list = MyStructure.getElements();
        while (uni_list.hasMoreElements()) {
          abstractEntry2 = (IEntry)uni_list.nextElement();
          if(abstractEntry2 instanceof IRelationEntry) { //if relation
            agg_rel= (IRelationEntry)abstractEntry2;
            if (agg_rel.getRelationType() ==OpcatConstants.UNI_DIRECTIONAL_RELATION) {
              long s_id=agg_rel.getSourceId();
              long d_id=agg_rel.getDestinationId();
              obj1=(IObjectEntry)MyStructure.getIEntry(s_id);
              obj2=(IObjectEntry)MyStructure.getIEntry(d_id);
              if ((!obj1.isPhysical())&&(!obj2.isPhysical())&&(!obj1.isEnvironmental())&&(!obj2.isEnvironmental())) {
                obj1=ReturnClass(MyStructure,(IEntry)obj1);//=====//
                obj2=ReturnClass(MyStructure,(IEntry)obj2);//=====//
                if ((!obj1.isPhysical())&&(!obj2.isPhysical())&&(!obj1.isEnvironmental())&&(!obj2.isEnvironmental())) {
                  assVec.addElement(""+agg_rel.getId()+"");
                  assEndVec.addElement(""+agg_rel.getId()+".1");
                  assEndVec.addElement(""+agg_rel.getId()+".2");
                  temp = "<!-- =============== [Association] ====================  --> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Association xmi.id=\"G."+agg_rel.getId()+"\" name=\"{"+obj1.getName()+" - "+obj2.getName()+"}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Association.connection> \n";
                  file.write(temp.getBytes());
                  temp = "<!--  ================[AssociationEnd] ===============  -->  \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd xmi.id=\"G."+agg_rel.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj2.getId()+"\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";  //"+agg_rel.getDestinationCardinality()+"
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd> \n";
                  file.write(temp.getBytes());
                  temp = "<!--  ================[AssociationEnd] ===============  -->  \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd xmi.id=\"G."+agg_rel.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"false\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj1.getId()+"\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";  //"+agg_rel.getSourceCardinality()+"
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Association.connection> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Association> \n";
                  file.write(temp.getBytes());
                }
              }//end of if
            }//end of if
          }//end of if
        }//end of while
      //====uni-directional relation end
      //====bi-directional relation
        uni_list = MyStructure.getElements();
        while (uni_list.hasMoreElements()) {
          abstractEntry2 = (IEntry)uni_list.nextElement();
          if(abstractEntry2 instanceof IRelationEntry) { //if relation
            agg_rel= (IRelationEntry)abstractEntry2;
            if (agg_rel.getRelationType() ==OpcatConstants.BI_DIRECTIONAL_RELATION) {
              long s_id=agg_rel.getSourceId();
              long d_id=agg_rel.getDestinationId();
              obj1=(IObjectEntry)MyStructure.getIEntry(s_id);
              obj2=(IObjectEntry)MyStructure.getIEntry(d_id);
              if ((!obj1.isPhysical())&&(!obj2.isPhysical())&&(!obj1.isEnvironmental())&&(!obj2.isEnvironmental())) {
                obj1=ReturnClass(MyStructure,(IEntry)obj1);//=====//
                obj2=ReturnClass(MyStructure,(IEntry)obj2);//=====//
                if ((!obj1.isPhysical())&&(!obj2.isPhysical())&&(!obj1.isEnvironmental())&&(!obj2.isEnvironmental())) {
                  assVec.addElement(""+agg_rel.getId()+"");
                  assEndVec.addElement(""+agg_rel.getId()+".1");
                  assEndVec.addElement(""+agg_rel.getId()+".2");
                  temp = "<!-- =============== [Association] ====================  --> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Association xmi.id=\"G."+agg_rel.getId()+"\" name=\"{"+obj1.getName()+" - "+obj2.getName()+"}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Association.connection> \n";
                  file.write(temp.getBytes());
                  temp = "<!--  ================[AssociationEnd] ===============  -->  \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd xmi.id=\"G."+agg_rel.getId()+".1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj2.getId()+"\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";  //"+agg_rel.getDestinationCardinality()+"
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd> \n";
                  file.write(temp.getBytes());
                  temp = "<!--  ================[AssociationEnd] ===============  -->  \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd xmi.id=\"G."+agg_rel.getId()+".2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj1.getId()+"\"> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "<UML:MultiplicityRange lower=\"1\" upper=\"1\" />";  //"+agg_rel.getSourceCardinality()+"
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity.range> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd.multiplicity> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:AssociationEnd> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Association.connection> \n";
                  file.write(temp.getBytes());
                  temp = "</UML:Association> \n";
                  file.write(temp.getBytes());
                }
              }//end of if
            }//end of if
          }//end of if
        }//end of while
              //====bi_directional relation end
//========end of a associations
      //end of a class diagram
        e = MyStructure.getElements();
        while(e.hasMoreElements()){
          abstractEntry=(IEntry)e.nextElement();
          if(abstractEntry instanceof IObjectEntry){
            obj=(IObjectEntry)abstractEntry;
            if((!obj.isPhysical()) &&(!obj.isEnvironmental()) && (!isFeature(MyStructure,obj))){
              inst_list=obj.getInstances();
              while(inst_list.hasMoreElements()){
                objInst=(IObjectInstance)inst_list.nextElement();
                childrenList=objInst.getChildThings();
                while(childrenList.hasMoreElements()){
                  abstractEntry=((IInstance)childrenList.nextElement()).getIEntry();
                  if(abstractEntry instanceof IObjectEntry){
                    obj1=(IObjectEntry)abstractEntry;
                    if((!obj1.isPhysical()) &&(!obj1.isEnvironmental())){
                      obj1=ReturnClass(MyStructure,obj1);
                      if((!obj1.isPhysical()) &&(!obj1.isEnvironmental()) && (!isFeature(MyStructure,obj1))){
                        assVec.addElement(""+obj1.getId()+".3");
                        assEndVec.addElement(""+obj1.getId()+".3.1");
                        assEndVec.addElement(""+obj1.getId()+".3.2");
                        temp = "<!-- =============== [Association] ====================  --> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:Association xmi.id=\"G."+obj1.getId()+".3\" name=\"{"+obj.getName()+" - "+obj1.getName()+"}\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\"> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:Association.connection> \n";
                        file.write(temp.getBytes());
                        temp = "<!--  ================[AssociationEnd] ===============  -->  \n";
                        file.write(temp.getBytes());
                        temp = "<UML:AssociationEnd xmi.id=\"G."+obj1.getId()+".3.1\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"none\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj1.getId()+"\"> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:AssociationEnd.multiplicity> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:Multiplicity> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:Multiplicity.range> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:MultiplicityRange lower=\"1\" upper=\"1\" />  \n";
                        file.write(temp.getBytes());
                        temp = "</UML:Multiplicity.range> \n";
                        file.write(temp.getBytes());
                        temp = "</UML:Multiplicity> \n";
                        file.write(temp.getBytes());
                        temp = "</UML:AssociationEnd.multiplicity> \n";
                        file.write(temp.getBytes());
                        temp = "</UML:AssociationEnd> \n";
                        file.write(temp.getBytes());
                        temp = "<!--  ================[AssociationEnd] ===============  -->  \n";
                        file.write(temp.getBytes());
                        temp = "<UML:AssociationEnd xmi.id=\"G."+obj1.getId()+".3.2\" name=\"\" visibility=\"public\" isSpecification=\"false\" isNavigable=\"true\" ordering=\"unordered\" aggregation=\"aggregate\" targetScope=\"instance\" changeability=\"changeable\" type=\"S."+obj.getId()+"\"> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:AssociationEnd.multiplicity> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:Multiplicity> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:Multiplicity.range> \n";
                        file.write(temp.getBytes());
                        temp = "<UML:MultiplicityRange lower=\"1\" upper=\"1\" />  \n";
                        file.write(temp.getBytes());
                        temp = "</UML:Multiplicity.range> \n";
                        file.write(temp.getBytes());
                        temp = "</UML:Multiplicity> \n";
                        file.write(temp.getBytes());
                        temp = "</UML:AssociationEnd.multiplicity> \n";
                        file.write(temp.getBytes());
                        temp = "</UML:AssociationEnd> \n";
                        file.write(temp.getBytes());
                        temp = "</UML:Association.connection> \n";
                        file.write(temp.getBytes());
                        temp = "</UML:Association> \n";
                        file.write(temp.getBytes());
                      }
                    }
                  }
                }
              }
            }
          }//end of if
        }//end of while



    }//end of try
    catch(IOException e)
    {
      System.out.println("error");
      return;
    }//end of catch

  }//end of func
}//end of class
