package extensionTools.uml.umlDiagrams;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.ILinkEntry;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPI.IRelationEntry;
import exportedAPI.opcatAPI.ISystem;

/**
 * Creates all UML diagrams choosed by the user.
 */
public class Diagrams_Creator {

  public Diagrams_Creator() {
  }
  ISystem mySys;
  int dest1;
  int dest2;
  int dest3;
  int dest4;
  int destSeq;

/**
 * Increces the 4 geometric parameters, used in external printing.
 */
  private void dest(){
    this.dest1=this.dest1+200;
    this.dest2=this.dest2+200;
    this.dest3=this.dest3+200;
    this.dest4=this.dest4+200;
    this.destSeq=this.destSeq+350;
    if((this.dest1>8000)||(this.dest2>8000)||(this.dest3>8000)||(this.dest4>8000)||(this.destSeq>8000)) {
		this.initialize_dest();
	}
    return;
  }

  /**
   * Initializes the 4 geometric parameters, used in external printing.
   */
  private void initialize_dest(){
    this.dest1=48;
    this.dest2=34;
    this.dest3=24;
    this.dest4=18;
    this.destSeq=20;
  }

  /**
   *Creates the xml code of the xml file according the users choice.
   * @param ISystem sys
   * @param FileOutputStream file - output file
   * @param int opC - indicates the chooser choice for class diagram.
   * @param int opU - indicates the chooser choice for usecase diagram.
   * @param int levU - level chosen by the user for usecase diagram.
   * @param int opSq - indicates the chooser choice for sequence diagram.
   * @param Vector vecSq - processes chosen by the user for sequence diagram.
   * @param int opSt - indicates the chooser choice for statechart diagram.
   * @param Vector vecSt - objects chosen by the user for statechart diagram.
   * @param int opA - indicates the chooser choice for activity diagram.
   * @param int levA - level chosen by the user for activity diagram.
   * @param Vector vecA - processes chosen by the user for activity diagram.
   * @param int opD - indicates the chooser choice for deployment diagram.
   */
  public void Creator(ISystem sys,FileOutputStream file,int opC,int opU,int levU,int opSq, Vector vecSq,int opSt,Vector vecSt,int opA,int levA,Vector vecA,int opD)throws IOException{
  try{
    this.mySys=sys;
    String temp="",tempId="";
    int flag=0;
    Enumeration e, e2,e3;
    Enumeration diagramElementsEnum;
    //----------------------------------------------------
    Class_d clas = new Class_d();
    UseCase_d usecase= new UseCase_d();
    Statechart_d statechart=new Statechart_d();
    sequence_d sequence=new sequence_d();
    Deployment_d deployment=new Deployment_d();
    Activity_d activity=new Activity_d();
    //---------------------------------------------------
    Vector usecaseVec = new Vector(15,2);
    Vector usecaseIncludeVec = new Vector(10,2);
    Vector usecaseInzoomIncludeVec = new Vector(10,2);
    Vector usecaseSterIncludeVec = new Vector(10,2);
    Vector usecaseExtendVec = new Vector(10,2);
    Vector actorTagsVec = new Vector(15,2);
    Vector usecaseassVec = new Vector(15,2);
    Vector usecaseassEndVec = new Vector(15,2);
    Vector usecasespecVec = new Vector(10,2);
    Vector tagsVec = new Vector(15,2);
    Vector diagramElementsVec = new Vector(20,2);
    Vector assVec = new Vector(10,2);
    Vector assEndVec = new Vector(10,2);
    Vector specVec = new Vector(10,2);
    //--------------------------for deployment
    Vector deplPrVec= new Vector(4,2);
    Vector deplRelVec= new Vector(4,2);
    //----------------------------------------------------------
    //for activity
    Vector actDiagramsVec = new  Vector(2,2);
    Vector ActivityPrint = new  Vector(2,2);
    Vector TransitionPrint = new  Vector(2,2);
    Vector BranchPrint = new  Vector(2,2);
    Vector ForkPrint = new  Vector(2,2);
    Vector JoinPrint = new  Vector(2,2);
    Vector FinalPrint = new  Vector(2,2);
    Vector InitialPrint = new  Vector(2,2);
    //-----------------------------------------------------------
    //for statechart
    Vector StateSimplePrint=new Vector(1,1);
    Vector TransitionStatePrint=new Vector(1,1);
    Vector stateDiagramsOut=new Vector(1,1);
    Vector finalStatePrint=new Vector(1,1);
    Vector initialStatePrint=new Vector(1,1);
    //-----------------------------------------------------------
    //for sequence
    Vector ClassifierRolePrint=new Vector(4,2);
    Vector AssociationPrint=new Vector(4,2);
    Vector seqEntryVec = new Vector(4,2);
    //-----------------------------------------------------------
    temp = "<!--  ==============   [Model] ====================   -->\n";
    file.write(temp.getBytes());
    String str = this.mySys.getName();
    temp = "<UML:Model xmi.id=\"G.0000\" name=\""+str+"\" visibility=\"public\" isSpecification=\"false\" isRoot=\"false\" isLeaf=\"false\" isAbstract=\"false\">\n";
    file.write(temp.getBytes());
    temp = "<UML:Namespace.ownedElement>\n";
    file.write(temp.getBytes());


    //********CALLING OF ALL DIAGRAMS*************************
    if (opC!= -1) {
		clas.ClassDiagramCreate(sys,file,tagsVec,diagramElementsVec,specVec,assEndVec,assVec);
	}

    if(opU!=-1) {
		usecase.UseCaseDiagramCreate(sys, file,usecaseVec,usecaseIncludeVec,usecaseInzoomIncludeVec,usecasespecVec,usecaseassVec,usecaseassEndVec,usecaseSterIncludeVec,actorTagsVec,usecaseExtendVec,opU,levU);
	}

    if ((opSt!=-1)&&(!vecSt.isEmpty())) {
		statechart.StateChartDiagramCreate(sys,file,vecSt,stateDiagramsOut, StateSimplePrint,TransitionStatePrint,finalStatePrint,initialStatePrint);
	}

    if ((opSq!=-1)&&(!vecSq.isEmpty())) {
		sequence.SequenceDiagramCreate(sys,file,vecSq,seqEntryVec/*,restObjVec*/,ClassifierRolePrint,AssociationPrint) ;
	}

    if (opD==1){
      deployment.DeploymentDiagramCreate(sys, file,deplPrVec,deplRelVec);
    }

    if ((opA==1)||( (opA==2)&&(!vecA.isEmpty()) ) ) {
		activity.ActivityDiagramCreate(sys,file,opA,vecA,levA,actDiagramsVec,ActivityPrint,ForkPrint,JoinPrint,InitialPrint,FinalPrint,BranchPrint,TransitionPrint);
	}
    //########################################################################################################
    temp = "</UML:Namespace.ownedElement>\n";
    file.write(temp.getBytes());
    temp = "</UML:Model>\n";
    file.write(temp.getBytes());


    diagramElementsEnum = tagsVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      tempId = (String)diagramElementsEnum.nextElement();
      temp = "<UML:TaggedValue xmi.id=\"XX."+tempId+"."+tempId+"\" tag=\"persistence\" value=\"transient\" modelElement=\"S."+tempId+"\" /> \n";
      file.write(temp.getBytes());
    }

    //---------------------Tags for actors--------------------------------------------------------------
    diagramElementsEnum = actorTagsVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      tempId = (String)diagramElementsEnum.nextElement();
      temp = "<UML:TaggedValue xmi.id=\"XX."+tempId+"."+tempId+"\" tag=\"persistence\" value=\"transient\" modelElement=\"S."+tempId+"\" /> \n";
      file.write(temp.getBytes());
    }


    //---------------------end of Tags for actors--------------------------------------------------------------

    temp = "<UML:Diagram xmi.id=\"S.0.0.0\" name=\"Main\" toolName=\"Rational Rose 98\" diagramType=\"ClassDiagram\" style=\"\" owner=\"G.0000\">\n";
    file.write(temp.getBytes());
    temp = "<UML:Diagram.element>\n";
    file.write(temp.getBytes());

    this.initialize_dest();
    diagramElementsEnum = diagramElementsVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=1,AutomaticResize=1,ShowAllAttributes=1,ShowAllOperations=1,ShowOperationSignature=1,SuppressAttributes=0,SuppressOperations=0,\" subject=\"S."+tempId+"\" /> \n";
      file.write(temp.getBytes());
    }
    diagramElementsEnum = assVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id =\"XX."+tempId+"\" geometry =\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style = \"Association:Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153, \" subject = \"G."+tempId+"\" /> \n";
      file.write(temp.getBytes());
    }
    diagramElementsEnum = assEndVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id = \"XX."+tempId+"\" geometry =\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style = \"Role\" subject = \"G."+tempId+"\" />\n";
      file.write(temp.getBytes());
    }
    diagramElementsEnum = specVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id = \"XX."+tempId+"\" geometry = \""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style = \"Inheritance\" subject = \"G."+tempId+"\" />\n";
      file.write(temp.getBytes());
    }


    temp = " </UML:Diagram.element>\n";
    file.write(temp.getBytes());
    temp = "</UML:Diagram>\n";
    file.write(temp.getBytes());


    //---------------------------------------UseCase Digram----------------------------------------

    temp="<UML:Diagram xmi.id=\"S.0.0.0.1\" name=\"Main\" toolName=\"Rational Rose 98\" diagramType=\"UseCaseDiagram\" style=\"\" owner=\"G.0000.1\">\n";
    file.write(temp.getBytes());
    temp = "<UML:Diagram.element>\n";
    file.write(temp.getBytes());

    this.initialize_dest();
    diagramElementsEnum = usecaseVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=1,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"S."+tempId+"\" />\n";
      file.write(temp.getBytes());
    }

    diagramElementsEnum = actorTagsVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,FillColor.Blue= 255,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=0,AutomaticResize=1,ShowAllAttributes=1,ShowAllOperations=1,ShowOperationSignature=0,SuppressAttributes=0,SuppressOperations=0,\" subject=\"S."+tempId+"\" />\n";
      file.write(temp.getBytes());
    }

    diagramElementsEnum = usecaseassVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Association:Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempId+"\" /> \n";
      file.write(temp.getBytes());
    }

    diagramElementsEnum = usecaseassEndVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Role\" subject=\"G."+tempId+"\" />  \n";
      file.write(temp.getBytes());
    }

    diagramElementsEnum = usecasespecVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Inheritance\" subject=\"G."+tempId+"\" /> \n";
      file.write(temp.getBytes());
    }

    diagramElementsEnum = usecaseSterIncludeVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=(String)diagramElementsEnum.nextElement();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Association:Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempId+"\" /> \n";
      file.write(temp.getBytes());
      this.dest();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+".1\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Role\" subject=\"G."+tempId+".1\" />\n";
      file.write(temp.getBytes());
      this.dest();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+".2\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Role\" subject=\"G."+tempId+".2\" />\n";
      file.write(temp.getBytes());
    }

    diagramElementsEnum = usecaseExtendVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      this.dest();
      tempId=""+((ILinkEntry)diagramElementsEnum.nextElement()).getId()+"";
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Association:Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempId+"\" /> \n";
      file.write(temp.getBytes());
      this.dest();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+".1\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Role\" subject=\"G."+tempId+".1\" />\n";
      file.write(temp.getBytes());
      this.dest();
      temp = "<UML:DiagramElement xmi.id=\"XX."+tempId+".2\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"Role\" subject=\"G."+tempId+".2\" />\n";
      file.write(temp.getBytes());

    }




    temp = " </UML:Diagram.element>\n";
    file.write(temp.getBytes());
    temp = "</UML:Diagram>\n";
    file.write(temp.getBytes());

    //---------------------------------------end of UseCase Digram-----------------------------------
    //---------------------------------PRINTOUTS OF DEPLOYMENT DIAGRAM----------------------------------
    long id;
    temp="<UML:Diagram xmi.id=\"G.0000.6\" name=\"Deployment View\" toolName=\"Rational Rose 98\" diagramType=\"DeploymentDiagram\" style=\"\" owner=\"G.0000\">";
    file.write(temp.getBytes());
    temp="<UML:Diagram.element>";
    file.write(temp.getBytes());

    this.initialize_dest();
    diagramElementsEnum = deplPrVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      id=((IObjectEntry)diagramElementsEnum.nextElement()).getId();
      this.dest();
      temp="<UML:DiagramElement xmi.id=\"XX."+id+"\" geometry=\""+this.dest1+", "+this.dest2+", "+this.dest3+", "+this.dest4+",\" style=\"FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=1,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+id+"\" /> ";
      file.write(temp.getBytes());
     }
    diagramElementsEnum = deplRelVec.elements();
    while (diagramElementsEnum.hasMoreElements()) {
      id=((IRelationEntry)diagramElementsEnum.nextElement()).getId();
      temp="<UML:DiagramElement xmi.id=\"XX."+id+"\" geometry=\"472, 575, 102, 417,\" style=\"FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=0,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" /> ";
      file.write(temp.getBytes());
    }

    temp="</UML:Diagram.element>";
    file.write(temp.getBytes());
    temp="</UML:Diagram>";
    file.write(temp.getBytes());

//---------------------------------PRINTOUTS OF DEPLOYMENT DIAGRAM end----------------------------------
//---------------------------------PRINTOUTS OF ACTIVITY DIAGRAM ----------------------------------

    Enumeration actTransitions,actJoins,actForks,actBranchs,actFinals,actInitials,actActivities;
    Enumeration actDiagrams=actDiagramsVec.elements();//all activity diagrams that should be created
    String diagramId="",diagramName,ownerId;
    Activity_d.PrintStructure tempPrintStructure;


    while (actDiagrams.hasMoreElements()){
      this.initialize_dest();
      diagramId=(String)actDiagrams.nextElement();
      diagramName=(String)actDiagrams.nextElement();
      ownerId=this.isActSubDiagram(diagramId,vecA);
      temp="<UML:Diagram xmi.id=\"S.458"+diagramId+"\" name=\""+diagramName+"\" toolName=\"Rational Rose 98\" diagramType=\"ActivityDiagram\" style=\"\" owner=\"";
      file.write(temp.getBytes());
      if (ownerId.compareTo("G")==0){
        temp="G."+diagramId+".5";
        file.write(temp.getBytes());
      }
      else{
        temp=""+ownerId+"";
        file.write(temp.getBytes());
      }
      temp="\">";
      file.write(temp.getBytes());
      temp="<UML:Diagram.element>";
      file.write(temp.getBytes());

      //initials
      actInitials=InitialPrint.elements();
      while (actInitials.hasMoreElements()){
        tempPrintStructure=(Activity_d.PrintStructure)actInitials.nextElement();
        if (tempPrintStructure.getDiagramId().compareTo(diagramId)==0){
          temp="<UML:DiagramElement xmi.id=\"XX.a"+tempPrintStructure.getElementId()+"\" geometry=\"816,"+this.dest2+", 60, 60,\" style=\"FillColor.Blue= 255,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=0,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempPrintStructure.getElementId()+"\" />";
          file.write(temp.getBytes());                                                                // 188
        }
      }

      //forks
      actForks=ForkPrint.elements();
      while (actForks.hasMoreElements()){
        tempPrintStructure=(Activity_d.PrintStructure)actForks.nextElement();
        if (tempPrintStructure.getDiagramId().compareTo(diagramId)==0){
          this.dest();
          temp="<UML:DiagramElement xmi.id=\"XX.a"+tempPrintStructure.getElementId()+"\" geometry=\" 864,"+this.dest2+", 992, 18,\" style=\"SyncItem, Horizontal=1,\" subject=\"G."+tempPrintStructure.getElementId()+"\" />";
          file.write(temp.getBytes());                                                            //864, 956  "+dest1+"
        }
      }
      //branches
      actBranchs=BranchPrint.elements();
      while (actBranchs.hasMoreElements()){
        tempPrintStructure=(Activity_d.PrintStructure)actBranchs.nextElement();
        if (tempPrintStructure.getDiagramId().compareTo(diagramId)==0){
          this.dest();
          temp="<UML:DiagramElement xmi.id=\"XX.a"+tempPrintStructure.getElementId()+"\" geometry=\"800,"+this.dest2+", 150, 74,\" style=\"FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=1,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempPrintStructure.getElementId()+"\" />";
          file.write(temp.getBytes());                                                                    //1420
        }
      }

      //activities
      actActivities=ActivityPrint.elements();
      while (actActivities.hasMoreElements()){
        tempPrintStructure=(Activity_d.PrintStructure)actActivities.nextElement();
        if (tempPrintStructure.getDiagramId().compareTo(diagramId)==0){
          this.dest();
          temp="<UML:DiagramElement xmi.id=\"XX.a"+tempPrintStructure.getElementId()+"\" geometry=\" 736,"+this.dest2+", 411, 124,\" style=\"FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=1,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempPrintStructure.getElementId()+".5\" />";
          file.write(temp.getBytes());                                                            //736, 476  "+dest2+"
        }
      }
      //transitons
      actTransitions=TransitionPrint.elements();
      while (actTransitions.hasMoreElements()){
        tempPrintStructure=(Activity_d.PrintStructure)actTransitions.nextElement();
        if (tempPrintStructure.getDiagramId().compareTo(diagramId)==0){
          temp="<UML:DiagramElement xmi.id=\"XX.a"+tempPrintStructure.getElementId()+"\" geometry=\"736, 339, 162, 150,\" style=\"Transition\" subject=\"G."+tempPrintStructure.getElementId()+"\" />";
          file.write(temp.getBytes());
        }
      }
     //joins
      actJoins=JoinPrint.elements();
      while (actJoins.hasMoreElements()){
        tempPrintStructure=(Activity_d.PrintStructure)actJoins.nextElement();
        if (tempPrintStructure.getDiagramId().compareTo(diagramId)==0){
          this.dest();
          temp="<UML:DiagramElement xmi.id=\"XX.a"+tempPrintStructure.getElementId()+"\" geometry=\" 864,"+this.dest2+", 992, 18,\" style=\"SyncItem, Horizontal=1,\" subject=\"G."+tempPrintStructure.getElementId()+"\" />";
          file.write(temp.getBytes());                                                            //832, 1564 "+dest1+"
        }
      }
      //finals
      actFinals=FinalPrint.elements();
      while (actFinals.hasMoreElements()){
        tempPrintStructure=(Activity_d.PrintStructure)actFinals.nextElement();
        if (tempPrintStructure.getDiagramId().compareTo(diagramId)==0){
          this.dest();
          temp="<UML:DiagramElement xmi.id=\"XX.a"+tempPrintStructure.getElementId()+"\" geometry=\"800,"+this.dest2+", 84, 84,\" style=\"FillColor.Blue= 255,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=0,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempPrintStructure.getElementId()+"\" />";
          file.write(temp.getBytes());                                                                 // 2396
        }
      }

      temp="</UML:Diagram.element>";
      file.write(temp.getBytes());
      temp="</UML:Diagram>";
      file.write(temp.getBytes());
    }

//---------------------------------PRINTOUTS OF ACTIVITY DIAGRAM end----------------------------------
//---------------------------------PRINTOUTS OF STATECHART DIAGRAM ----------------------------------
    if (opSt==1){
      IEntry ent,father_ent;
      e=stateDiagramsOut.elements();
      Enumeration e1=StateSimplePrint.elements();
      e3=TransitionStatePrint.elements();
      e2=finalStatePrint.elements();
      Enumeration e4=initialStatePrint.elements();

      flag=0;
      while (e.hasMoreElements()){
        this.initialize_dest();
        ent=(IEntry)e.nextElement();
        father_ent=(IEntry)e.nextElement();
        temp="<UML:Diagram xmi.id=\"S."+ent.getId()+".4\" name=\""+ent.getName()+"\" toolName=\"Rational Rose 98\" diagramType=\"StateDiagram\" style=\"\" owner=\"S."+father_ent.getId()+".44\">";
        file.write(temp.getBytes());
        temp="<UML:Diagram.element>";
        file.write(temp.getBytes());
        flag=0;
        while ( (e1.hasMoreElements()) && (flag==0) ){
          tempId=(String)e1.nextElement();
          if (tempId!="-1"){
            this.dest();
            temp="<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.dest1+", "+this.dest2+", 300, 134,\" style=\"FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=1,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempId+"\" /> ";
            file.write(temp.getBytes());
          } else {
			flag=1;
		}
        }
        flag=0;
        while ( (e3.hasMoreElements()) && (flag==0) ){
          tempId=(String)e3.nextElement();
          if (tempId!="-1"){
            temp="<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\"528, 194, 162, 150,\" style=\"Transition\" subject=\"G."+tempId+"\" /> ";
            file.write(temp.getBytes());
          } else {
			flag=1;
		}
        }
        //finalStatePrint
        flag=0;
        while ((e2.hasMoreElements())&& (flag==0)){
           tempId=(String)e2.nextElement();
           if (tempId!="-1"){
            temp="<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\"1024, 848, 84, 84,\" style=\"FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=1,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempId+"\" /> ";
            file.write(temp.getBytes());
          } else {
			flag=1;
		}
        }
        //initialStatePrint
        flag=0;
        while ((e4.hasMoreElements())&& (flag==0)){
           tempId=(String)e4.nextElement();
           if (tempId!="-1"){
            temp="<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\"1536, 432, 60, 60,\" style=\"FillColor.Blue= 255,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=0,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=0,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempId+"\" /> ";
            file.write(temp.getBytes());
          } else {
			flag=1;
		}
        }


        temp="</UML:Diagram.element>";
        file.write(temp.getBytes());
        temp="</UML:Diagram>";
        file.write(temp.getBytes());
      }
    }
//---------------------------------PRINTOUTS OF STATECHART DIAGRAM end----------------------------------
//---------------------------------PRINTOUTS OF Sequence DIAGRAM -------------------------------------------

    Enumeration clsRoleEnum;
    Enumeration assEnum;
    String diagIdC="",diagIdA="",nameC="";
    int sqn;
    boolean M1,M2;

    clsRoleEnum = ClassifierRolePrint.elements();
    while(clsRoleEnum.hasMoreElements()){
        if (clsRoleEnum.hasMoreElements()) {
			diagIdC = (String)clsRoleEnum.nextElement();
		}
        if (clsRoleEnum.hasMoreElements()) {
			nameC = (String)clsRoleEnum.nextElement();
		}


        temp="<UML:Diagram xmi.id=\"S."+diagIdC+"\" name=\""+nameC+"\" toolName=\"Rational Rose 98\" diagramType=\"SequenceDiagram\" style=\"\" owner=\"G."+diagIdC+"\">";
        file.write(temp.getBytes());
        temp="<UML:Diagram.element>";
        file.write(temp.getBytes());

        this.initialize_dest();

        M1=false;
        while ((!M1)&&(clsRoleEnum.hasMoreElements())){
          tempId = (String)clsRoleEnum.nextElement();
          if (tempId=="-1") {
			M1=true;
		} else{
            this.dest();
            temp="<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.destSeq+", 224, 300, 118,\" style=\"FillColor.Blue= 204,FillColor.Green= 255,FillColor.Red= 255,FillColor.Transparent=1,Font.Blue= 0,Font.Green= 0,Font.Red= 0,Font.FaceName=Arial,Font.Size= 10,Font.Bold=0,Font.Italic=0,Font.Strikethrough=0,Font.Underline=1,LineColor.Blue= 51,LineColor.Green= 0,LineColor.Red= 153,\" subject=\"G."+tempId+"\" /> ";
            file.write(temp.getBytes());
          }
        }

        M1=false;
        M2=false;
        sqn=0;
        assEnum = AssociationPrint.elements();
        while ((assEnum.hasMoreElements())&&(!M1)){
          M2=false;
          diagIdA = (String)assEnum.nextElement();//diagramID
          if (diagIdA.compareTo(diagIdC)==0){
            while(!M1){
              tempId = (String)assEnum.nextElement();
              if (tempId=="-1") {
				M1=true;
			} else{
                sqn++;
                this.dest();
                temp="<UML:DiagramElement xmi.id=\"XX."+tempId+"\" geometry=\""+this.destSeq+", 324, 12, 52,\" style=\"Message, SQN= "+sqn+",\" subject=\"G."+tempId+"\" /> ";
                file.write(temp.getBytes());
              }//end else
            }
          }
          else{//not relavent diagram-->out until -1 (M1)
            while (!M2){
              if (assEnum.hasMoreElements()){
                tempId = (String) assEnum.nextElement();
              }
                if (tempId == "-1") {
					M2 = true;
				}

            }
          }
        }

        temp="</UML:Diagram.element>";
        file.write(temp.getBytes());
        temp="</UML:Diagram>";
        file.write(temp.getBytes());
    }



//---------------------------------PRINTOUTS OF Sequence DIAGRAM end------------------------------------
  }//end of try
  catch(IOException e)
    {
      System.out.println("error");
      return;
    }//end of catch
  }



  /**
   * Calculates if the given diagram id is an id of subdiagram in activity diagram or it is a main diagram
   * @param String diagramId
   * @param Vector vecA - contains process that need to be diagrams - processes that the chooser selected.
   * @retuns "S" if main diagram
   * @retuns "G" if subDiagram diagram
   */
  private String isActSubDiagram(String diagramId,Vector vecA){
    Enumeration locenum = vecA.elements();
    IProcessEntry proc;
    String temp="";

    if (diagramId.compareTo("00.555")==0) {
		return "S.0000.5";
	}
    while(locenum.hasMoreElements()){
      proc = (IProcessEntry)locenum.nextElement();
      temp = ""+proc.getId()+".5";
      if (diagramId.compareTo(temp)==0) {
		return "S.0000.5";
	}
    }
    return "G";
  }

}