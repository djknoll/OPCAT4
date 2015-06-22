package extensionTools.hio;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXCheckResult;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXLinkInstance;
import exportedAPI.opcatAPIx.IXRelationInstance;
import exportedAPI.opcatAPIx.IXStateInstance;
import exportedAPI.opcatAPIx.IXSystemStructure;
import exportedAPI.opcatAPIx.IXThingInstance;
import gui.Opcat2;
import gui.opdGraphics.dialogs.ObjectPropertiesDialog;
import gui.opdGraphics.dialogs.ProcessPropertiesDialog;
import gui.opdGraphics.dialogs.StatePropertiesDialog;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmState;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;
import gui.projectStructure.ThingInstance;
import gui.undo.UndoableAddFundamentalRelation;
import gui.undo.UndoableAddGeneralRelation;
import gui.undo.UndoableAddLink;
import gui.undo.UndoableAddObject;
import gui.undo.UndoableAddProcess;
import gui.undo.UndoableAddState;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;

/**
 * <p>Title:class InteractionWithOpcat </p>
 * <p>Description: This class contains all the interaction with opcat after recognizing the shape that was drawn</p>
 */

public class OpenDrawInOpcat{
  /**
   * private int diagnose the recognition of the draw
   */
  private int diagnose = 0; //the recognition of the draw
  /**
   * static protected Rectangle shapeRectangle the enclosing rectangle of the draw
   */
  static protected Rectangle shapeRectangle;//the enclosing rectangle of the draw
  /**
   * static protected OpdProject myProject the current project
   */
  static protected OpdProject myProject;//the current project
  /**
   * static protected Opd myOpd the current opd
   */
  static protected Opd myOpd = null;//the current opd
  /**
   * static protected IXConnectionEdgeInstance openIn the Instance in which the draw should be open
   */
  static protected IXConnectionEdgeInstance openIn = null;//the Instance in which the draw should be open
  /**
   * static protected Point openAt the location in which the draw should be open
   */
  static protected Point openAt = new Point(10, 10);//the location in which the draw should be open
  /**
   * private OpdConnectionEdge my_OpdConnectionEdge the current OpdConnectionEdge in opcat
   */
  private OpdConnectionEdge my_OpdConnectionEdge = null;
  /**
   * private Graphics g the current graphics
   */
  private Graphics g;//the current graphics
  /**
   * private int linkType holds the diagnose in case of a link or a general relation
   */
  private int linkType = -1;//holds the diagnose in case of a link or a general relation
  /**
   * static protected int addToX an extension to a search point in case of a state Instance in  x direction
   */
  static protected int addToX = 0;//an extension to a search point in case of a state Instance in  x direction
  /**
   * static protected int addToY an extension to a search point in case of a state Instance in y direction
   */
  static protected int addToY = 0;//an extension to a search point in case of a state Instance in y direction
  /**
   * private IXConnectionEdgeInstance sourceThingInstance the source Instance for a link or a relation
   */
  private IXConnectionEdgeInstance sourceThingInstance = null;//the source Instance for a link or a relation
  /**
   * private IXConnectionEdgeInstance destinationThingInstance the destination Instance for a link or a relation
   */
  private IXConnectionEdgeInstance destinationThingInstance = null;//the destination Instance for a link or a relation

  /**
   * constructor of OpenDrawInOpcat
   * @param myProject_ the current project
   */
  public OpenDrawInOpcat(
      OpdProject myProject_) {

    myProject = myProject_;
    myOpd = myProject.getCurrentOpd();

  }

  /**
   * the function openOpcatShape opens the draw in opcat according to the diagnose
   * @param shapeDiagnose the diagnose
   * @param wholeShapeRect the enclosing rectangle of the draw
   */
  public void openOpcatShape(final int shapeDiagnose, Rectangle wholeShapeRect) {
    LinkInstance link = null;//a new link
    diagnose = shapeDiagnose;
    shapeRectangle = wholeShapeRect;
    Point sourcePoint;//holds the sourcePoint incaes the diagnose is link/relation
    Point destinationPoint;//holds the destinationPoint incaes the diagnose is link/relation
    int sourceRange = 0;//an extension to a search point
    int destinationRange = 0;
    MessageScreen msgScreen = new MessageScreen();
	//clears the variabels
    addToX = 0;
    addToY = 0;

    if( diagnose == -1){
      myProject.refresh();
      linkType = -1;
    }

    if( diagnose > 2 && diagnose != 6 && diagnose != 50)
    if (DrawAppNew.link != null) {//if diagnose is a link

      sourcePoint = new Point(DrawAppNew.link.getSourceEdgePoint());//the point around it we look for a source Instance
      destinationPoint = new Point(DrawAppNew.link.getDestinationEdgePoint());//the point around it we look for a destination Instance
      //the range around a point we look for an Instance
      sourceRange = DrawAppNew.link.getSourceRange();
      destinationRange = DrawAppNew.link.getDestinationRange();
      //an extension to a search point in case of a state Instance
      addToX = addToX(sourcePoint, destinationPoint, sourceRange);
      addToY = addToY(sourcePoint, destinationPoint, sourceRange);


      Point[] source = {sourcePoint};//an array of points
      sourceThingInstance = nearestThingInstance(source,sourceRange);//find the valid source Instance
      //an extension to a search point in case of a state Instance
      addToX = addToX( destinationPoint, sourcePoint, destinationRange);
      addToY = addToY( destinationPoint, sourcePoint, destinationRange);

      Point[] destination = {destinationPoint};//an array of points
      destinationThingInstance = nearestThingInstance(destination,destinationRange);//find the valid destination Instance
      //in case didn't found sourceThingInstance or destinationThingInstance open message window
      if(sourceThingInstance == null || destinationThingInstance == null){
        msgScreen.showMessage(myProject);
        myProject.refresh();
      }
    }
	//according to the diagnose opens the opcat shape
    switch (diagnose) {

      case DrawAppNew.rectangle:
        openThing(DrawAppNew.rectangle);
        break;

      case DrawAppNew.ellipse:
        openThing(DrawAppNew.ellipse);
        break;

      case DrawAppNew.fullRelation:
        showPopUp(DrawAppNew.relation.getDestinationEdgePoint(), 0);
        break;

      case DrawAppNew.emptyRelation:
        enableFundamentalRelation((FundamentalRelationInstance)openRelation(201));
        break;

      case DrawAppNew.bidirectionalRelation:
        if (linkType == -1) {//if link type dosen't hold the diagnose yet
          linkType = 206;

        }
      case DrawAppNew.openArrow:
        if (linkType == -1) {//if link type dosen't hold the diagnose yet
          linkType = 205;

        }
        GeneralRelationInstance relation = (GeneralRelationInstance)openGeneralRelation(linkType, sourceThingInstance, destinationThingInstance);
        enableGeneralRelation(relation);
        break;

      case DrawAppNew.triangleArrowFromBoth:
        linkType = 302;

      case DrawAppNew.invocationArrow:
        if (linkType == -1) { //if link type dosen't hold the diagnose yet
          linkType = 307;

        }
      case DrawAppNew.triangleArrow:
        if (linkType == -1) {//if link type dosen't hold the diagnose yet
          if ( ( (ConnectionEdgeInstance) sourceThingInstance)instanceof ProcessInstance) {
            linkType = 306;
          }
          else {
            linkType = 301; //source is state or object
          }
        }

      case DrawAppNew.agentArrow:
        if (linkType == -1) {
          linkType = 305;

        }

      case DrawAppNew.instrumentArrow:
        if (linkType == -1) {
          linkType = 303;

        }

        link = openLink(linkType, sourceThingInstance, destinationThingInstance);
        if (link == null) { //in case open link fails: source or destination are null
          break;
        }
        enableLink(link);
        break;

      default:
//JOptionPane.showMessageDialog(myProject.getMainFrame(), "Cannot Recognize The Shape, Please Try Again", "Message" ,JOptionPane.ERROR_MESSAGE);//roni
 //  System.out.println("System didn't recognized the draw");

    myProject.refresh(); //clean the drawing area screen from the draw

    }
    myProject.refresh();
    linkType = -1;
  }

  /**
   * the function nearestThingInstance find the valid Instance. if draw was process/object/state,
   * finds the Instance inside it most of the draw was drawn. if draw was a link or relation find the
   * Instance of the source or destination according to the given points
   * @param inPoints array of points where to look for an Instance: 1 if its a link/relation,
   * 3 if its process/object/state
   * @param radiusAroundPoint the range from the point where an Instance is considered valid
   * @return the valid Instance
   */
  static protected IXConnectionEdgeInstance nearestThingInstance(Point[] inPoints,
      int radiusAroundPoint) {

    boolean isObject = false;//boolean which tells if it's an object
    IXConnectionEdgeInstance nearest = null;//holds the nearest instance to the source/dest point
    IXStateInstance nearestState = null;
    myOpd = myProject.getCurrentOpd();
    IXSystemStructure mySystemStructure = myProject.getIXSystemStructure();//holds the current system Structure
    Enumeration enumOutsideZoom = mySystemStructure.getThingsInOpd(myOpd.
        getOpdId());//enumaration that go over all the Instances in the current opd
    RectangularShape nearestShape;//holds the nearest shape
    Point thingEdgePoint = new Point( -1, -1);//holds the x,y param of a checked thing
    int counter = 0;//counts the number of points found in the checked instance
    //how many points are needed to be in an Instance for it to be valid
    int threshold = inPoints.length == 1 ? 1 : (int) (inPoints.length * 0.5 + 1);

    if (inPoints == null) {
      return null;
    }

    //loop to find in which Instance we have at least as many points as defined in threshold
    while (enumOutsideZoom.hasMoreElements()) {
      nearest = (IXThingInstance) enumOutsideZoom.nextElement();
      counter = 0;
      isObject = false;

      thingEdgePoint.setLocation(nearest.getX(), nearest.getY());

      //if the thing is inside zoomin
      if ( ( (IXThingInstance) nearest).getParentIXThingInstance() != null) {
        if ( ( (IXThingInstance) nearest).getParentIXThingInstance().isZoomedIn()) {
          thingEdgePoint = SwingUtilities.convertPoint( ( (ThingInstance)
              ( (IXThingInstance) nearest).getParentIXThingInstance()).
              getConnectionEdge(),
              thingEdgePoint,
              myProject.getCurrentOpd().getDrawingArea());
             }
      }

      if (nearest instanceof ObjectInstance) {

        isObject = true;
        //create an extended rectangle around the object Instance to check if the point is inside it
        nearestShape = new Rectangle( (int) thingEdgePoint.getX() -
                                     radiusAroundPoint,
                                     (int) thingEdgePoint.getY() -
                                     radiusAroundPoint,
                                     nearest.getWidth() + 2 * radiusAroundPoint,
                                     nearest.getHeight() +
                                     2 * radiusAroundPoint);
      }
      else { //nearest is instanceof process
        //create an extended ellipse around the process Instance to check if the point is inside it
        nearestShape = new Ellipse2D.Double( (int) thingEdgePoint.getX() -
                                            radiusAroundPoint,
                                            (int) thingEdgePoint.getY() -
                                            radiusAroundPoint,
                                            nearest.getWidth() +
                                            2 * radiusAroundPoint,
                                            nearest.getHeight() +
                                            2 * radiusAroundPoint);
      }

      //check how many points from the given array are inside the Instance
      for (int i = 0; i < inPoints.length; i++) {
        if (nearestShape.contains(inPoints[i])) {
          counter++;

        }
      }
      //if found at least as many points as defined in threshold this is the valid Instance
      if (counter >= threshold){
        if(inPoints.length == 1 &&  isObject == true)//if we have link/relation check if it relates to a state
        nearestState = nearestStateInstance(inPoints[0]);
        if (nearestState != null) {//found a state it relates to
          return nearestState;
        }
        return nearest;
      }
      }

    return null;
  }

  /**
   * the function nearestStateInstance find the valid state Instance
   * @param searchPt the point where to look for a state Instance
   * @return state Instance if found a point inside one, otherwise return null
   */
  static protected IXStateInstance nearestStateInstance(Point searchPt) {
    Component inComponent = myProject.getCurrentOpd().getDrawingArea().findComponentAt(
        (int) searchPt.getX() + addToX/2 , (int) searchPt.getY() + addToY/2);
    if (inComponent instanceof OpdState)
     return (IXStateInstance) ( (OpdConnectionEdge) inComponent).getInstance();
    return null;
  }

  /**
   * the function openLink opens the right link in opcat
   * @param linkType the link type
   * @param sourceThingInstance the source Instance of the link
   * @param destinationThingInstance the destination Instance of the link
   * @return the link Instance that was opened, null if it failed
   */
  private LinkInstance openLink(int linkType,
                                IXConnectionEdgeInstance sourceThingInstance,
                                IXConnectionEdgeInstance
                                destinationThingInstance) {
    IXCheckResult res;//checks if the link was opened or not
    IXLinkInstance link = null;//holds the link instance that was opend
    if (sourceThingInstance == null || destinationThingInstance == null) {
      return null;
    }
    res = myProject.checkLinkAddition(sourceThingInstance,
                                      destinationThingInstance, linkType);
    if (res.getResult() == IXCheckResult.RIGHT) {
      link = myProject.addLink(sourceThingInstance, destinationThingInstance,
                               linkType);
      return (LinkInstance) link;
    }
    else if (res.getResult() == IXCheckResult.WRONG) {
      JOptionPane.showMessageDialog(myProject.getMainFrame(), res.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
    }
    else if (res.getResult() == IXCheckResult.WARNING) {
      JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                    res.getMessage() + "\n Do you want to continue?",
                                    "Opcat2 - Warning",
                                    JOptionPane.YES_NO_OPTION);
    }
    return null;
  }

  /**
   * the function openRelation opens the right relation in opcat
   * @param linkType the relation type
   * @return the relation Instance that was opened, null if it failed
   */
  static protected IXRelationInstance openRelation(int linkType) {
    Point[] source = {
       DrawAppNew.relation.getSourceEdgePoint()};//gets the edge points
     Point[] destination = {
       DrawAppNew.relation.getDestinationEdgePoint()};//gets the Destination points
    IXConnectionEdgeInstance sourceThingInstance;//holds the nearest thing instance
    IXConnectionEdgeInstance destinationThingInstance;
    IXCheckResult res;//checks if the relation was opened or not
    IXRelationInstance relation = null;//holds the relation instance that was opend

      sourceThingInstance = nearestThingInstance(
         source,
         DrawAppNew.relation.getSourceRange());

      destinationThingInstance = nearestThingInstance(
         destination, DrawAppNew.relation.getDestinationRange());
    relation = null;
    if (sourceThingInstance == null || destinationThingInstance == null) {
      return null;
    }
    res = myProject.checkRelationAddition(sourceThingInstance,
                                          destinationThingInstance, linkType);
    if (res.getResult() == IXCheckResult.RIGHT) {
      relation = myProject.addRelation(sourceThingInstance,
                                       destinationThingInstance,
                                       linkType);
      return relation;
    }
    else if (res.getResult() == IXCheckResult.WRONG) {
      JOptionPane.showMessageDialog(myProject.getMainFrame(), res.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
    }
    else if (res.getResult() == IXCheckResult.WARNING) {
      int retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                    res.getMessage() + "\n Do you want to continue?",
                                    "Opcat2 - Warning",
                                    JOptionPane.YES_NO_OPTION);

      if (retValue == JOptionPane.OK_OPTION)
      {
        relation = myProject.addRelation(sourceThingInstance,
                                   destinationThingInstance,
                                   linkType);
        return relation;

       }

    }
    return null;
  }

  /**
   * the function openGeneralRelation opens a bidirectional or unidirectional relation in opcat
   * @param linkType the relation type
   * @return the relation Instance that was opened, null if it failed
   */

  static protected IXRelationInstance openGeneralRelation(int linkType, IXConnectionEdgeInstance sourceThingInstance,
                                IXConnectionEdgeInstance
                                destinationThingInstance) {


    IXCheckResult res;//checks if the GeneralRelation was opened or not
    IXRelationInstance relation = null;//holds the GeneralRelation instance that was opend

     if (sourceThingInstance == null || destinationThingInstance == null)
        return null;
      res = myProject.checkRelationAddition(sourceThingInstance,
                                            destinationThingInstance, linkType);
      if (res.getResult() == IXCheckResult.RIGHT) {
        relation = myProject.addRelation(sourceThingInstance,
                                         destinationThingInstance,
                                         linkType);
        return relation;
      }

      if (res.getResult() == IXCheckResult.WRONG) {
        JOptionPane.showMessageDialog(myProject.getMainFrame(), res.getMessage(),
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }
      else if(res.getResult() == IXCheckResult.WARNING) {
        JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                      res.getMessage() + "\n Do you want to continue?",
                                      "Opcat2 - Warning",
                                      JOptionPane.YES_NO_OPTION);
      }
      return null;
  }


  /**
   * the function openObject opens an object in opcat
   */
  private void openObject() {
    OpmObject sampleObjOpm = new OpmObject(0, "");
    ObjectEntry sampleObjEntry = new ObjectEntry(sampleObjOpm, myProject);
    ObjectInstance sampleObjInstance = new ObjectInstance(sampleObjEntry,
        new OpdKey(0, 0), null, myProject, false);
    OpdObject sampleObjOpd = (OpdObject) sampleObjInstance.getConnectionEdge();
    ObjectPropertiesDialog od = new ObjectPropertiesDialog(sampleObjOpd,
        sampleObjEntry, myProject, true); //creates properties dialog

    if (openIn != null) {//if object is opened in an Instance
      if ( ( (IXThingInstance) openIn).isZoomedIn() &&
          (openIn instanceof ObjectInstance)) { //open object or state in object zoomed in

        showPopUp(openAt, 1);
        return;

      }
      else if ( ( (IXThingInstance) openIn).isZoomedIn() &&
               (openIn instanceof ProcessInstance)) { //open object in process zoomed in
        if (od.showDialog()) {
          ObjectInstance currObject = (ObjectInstance) myProject.addObject(
              od.getName(), (int) openAt.getX(),
              (int) openAt.getY(),
              (IXThingInstance) openIn);
          currObject.setLocation( (int) openAt.getX(),
                                 (int) openAt.getY());
          enableObject(currObject, sampleObjOpm, sampleObjInstance);

          return;
        }
      }
      else {//in object Instance not zoomedIn then open a state
        my_OpdConnectionEdge = ( (ThingInstance) openIn).getConnectionEdge();
        if ( (my_OpdConnectionEdge.getInstance()instanceof ObjectInstance)) {
          openState();
          return;
        }
      }
    }

    // if not zoomed and drawing object in opd
    else {
      if (od.showDialog()) {
        ObjectInstance currObject = (ObjectInstance) myProject.addObject(od.
            getName(), (int) openAt.getX(),
            (int) openAt.getY(), myOpd.getOpdId());

        enableObject(currObject, sampleObjOpm, sampleObjInstance);
      }
    }
  }

  /**
   * the function openProcess open a process in opcat
   */
  private void openProcess() {
    OpmProcess sampleProcOpm = new OpmProcess(0, "");
    ProcessEntry sampleProcEntry = new ProcessEntry(sampleProcOpm, myProject);
    ProcessInstance sampleProcInstance = new ProcessInstance(sampleProcEntry,
        new OpdKey(0, 0), null, myProject);
    OpdProcess sampleProcOpd = (OpdProcess) sampleProcInstance.
        getConnectionEdge();
    ProcessPropertiesDialog pd1 = new ProcessPropertiesDialog(sampleProcOpd,
        sampleProcEntry, myProject, true);

    if (openIn != null) {//if process is opened in an Instance
      if ( ( (IXThingInstance) openIn).isZoomedIn()) { //if zoomed in
        if (pd1.showDialog()) {
          ProcessInstance currProcess = (ProcessInstance) myProject.
              addProcess(pd1.getName(), (int) openAt.getX(),
                         (int) openAt.getY(), (IXThingInstance) openIn);

          currProcess.setLocation( (int) openAt.getX(),
                                  (int) openAt.getY());
          enableProcess(currProcess, sampleProcOpm, sampleProcInstance);
        }
      }
    }
    else { //not zoomed in
      if (pd1.showDialog()) {
        ProcessInstance currProcess = (ProcessInstance) myProject.
            addProcess(pd1.getName(), (int) openAt.getX(),
                       (int) openAt.getY(), myOpd.getOpdId());
        enableProcess(currProcess, sampleProcOpm, sampleProcInstance);

      }
    }
  }

  /**
   * the function openState open a state in an object in opcat
   */
  static protected void openState() {
    ObjectInstance parentInstance;
    OpmObject sampleObj = new OpmObject(0, "");

    OpmState sampleOpmState = new OpmState(0, "");
    StateEntry sampleStateEntry = new StateEntry(sampleOpmState,
                                                 sampleObj, myProject);
    OpmObject sampleObjOpm = new OpmObject(0, "");
    ObjectEntry sampleObjEntry = new ObjectEntry(sampleObjOpm, myProject);
    ObjectInstance sampleObjInstance = new ObjectInstance(sampleObjEntry,
        new OpdKey(0, 0), null, myProject, false);

    OpdObject sampleObjOpd = (OpdObject) sampleObjInstance.getConnectionEdge();
    StateInstance sampleSI = new StateInstance(sampleStateEntry,
                                               new OpdKey(0, 0),
                                               sampleObjOpd, myProject);

    parentInstance = (ObjectInstance) openIn;//the object in which we open the state
    StatePropertiesDialog sd = new StatePropertiesDialog(sampleSI,
        myProject, false,
        BaseGraphicComponent.SHOW_OK | BaseGraphicComponent.SHOW_CANCEL);
    if (sd.showDialog()) {
      StateEntry myEntry = (StateEntry) myProject.addState(sd.getName(),
          parentInstance);

      OpmState nState = (OpmState) myEntry.getLogicalEntity();
      nState.copyPropsFrom(sampleOpmState);
	//goes over all the states in an instance
      for (Enumeration en = parentInstance.getEntry().getInstances();
           en.hasMoreElements(); ) {
        parentInstance = (ObjectInstance) en.nextElement();
        OpdObject pObj = (OpdObject) parentInstance.getThing();

        if (pObj.isStatesAutoarrange()) {
          pObj.fitToContent();
        }
      }

      Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
      Opcat2.getUndoManager().undoableEditHappened(new
          UndoableEditEvent(myProject,
                            new UndoableAddState(myProject, myEntry)));
      Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
      Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
    }
  }

  /**
   * the function enableFundamentalRelation enables undo/redo/delete after opening a relation
   * @param relation the relevant relation
   */
  static protected void enableFundamentalRelation(FundamentalRelationInstance relation) {
    if (relation == null) {
      return;
    }
    Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(
        myProject, new UndoableAddFundamentalRelation(myProject, relation)));
    Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
    Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

    myProject.removeSelection();
    myProject.addSelection(relation, true);
    StateMachine.reset();
    Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);

  }

  /**
   * the function enableGeneralRelation enables undo/redo/delete after opening a general relation
   * @param relation the relevant relation
   */
  private void enableGeneralRelation(GeneralRelationInstance relation) {
    if (relation == null) {
      return;
    }
    Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(
        myProject, new UndoableAddGeneralRelation(myProject, relation)));
    Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
    Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

    myProject.removeSelection();
    myProject.addSelection(relation, true);
    StateMachine.reset();
    Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
  }

  /**
   * the function enableThing enables undo/redo/delete after opening a Thing
   * @param thing the relevant Thing
   * @param thingFlag 0 if object, 1 if process
   */
  static protected void enableThing(ThingInstance thing, int thingFlag) {
    if (thingFlag == 0) { //object
      Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(
          myProject,
          new UndoableAddObject(myProject, (ObjectInstance) thing)));
    }
    else {
      Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(
          myProject,
          new UndoableAddProcess(myProject, (ProcessInstance) thing)));

    }
    Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
    Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());
    myProject.removeSelection();
    myProject.addSelection(thing, true);
    StateMachine.reset();
    Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
  }

  /**
   * the function enableLink enables undo/redo/delete after opening a Link
   * @param link the relevant Link
   */
  private void enableLink(LinkInstance link) {
    Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(
        myProject, new UndoableAddLink(myProject, link)));
    Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
    Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

    myProject.removeSelection();
    myProject.addSelection(link, true);
    StateMachine.reset();
    Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
  }

  /**
   * the function enableObject enables undo/redo/delete after opening an Object
   * @param currObject the relevant object
   * @param sampleObjOpm the relevant sampleObjOpm
   * @param sampleObjInstance the relevant sampleObjInstance
   */
   static protected void enableObject(ObjectInstance currObject, OpmObject sampleObjOpm,
                           ObjectInstance sampleObjInstance) {
    OpmObject newObject = (OpmObject) currObject.getEntry().
        getLogicalEntity();
    newObject.copyPropsFrom(sampleObjOpm);
    currObject.copyPropsFrom(sampleObjInstance);
    currObject.update();
    currObject.getConnectionEdge().fitToContent();

    currObject.setSize( (int) shapeRectangle.getWidth(),
                       (int) shapeRectangle.getHeight());
    currObject.getConnectionEdge().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    enableThing(currObject, 0);

  }

  /**
   * the function enableProcess enables undo/redo/delete after opening an Process
   * @param currProcess the relevant process
   * @param sampleProcOpm the relevant sampleProcOpm
   * @param sampleProcInstance the relevant sampleProcInstance
   */
  private void enableProcess(ProcessInstance currProcess,
                             OpmProcess sampleProcOpm,
                             ProcessInstance sampleProcInstance) {
    OpmProcess newProcess = (OpmProcess) currProcess.getEntry().
        getLogicalEntity();
    newProcess.copyPropsFrom(sampleProcOpm);
    currProcess.copyPropsFrom(sampleProcInstance);
    currProcess.update();
    currProcess.getConnectionEdge().fitToContent();
    currProcess.setSize( (int) shapeRectangle.getWidth(),
                        (int) shapeRectangle.getHeight());
    currProcess.getConnectionEdge().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    enableThing(currProcess, 1);
  }

  /**
   * the function openThing open a Process/Object in opcat
   * @param diagnose Process/Object
   */
  private void openThing(int diagnose) {

    openAt = shapeRectangle.getLocation();
    IXConnectionEdgeInstance nearestThing = openThingIn();//where to open the shape at

    openIn = nearestThing;

    if (nearestThing != null) {
      openAt = SwingUtilities.convertPoint(
          myProject.getCurrentOpd().getDrawingArea(),
          openAt,
          ( (ThingInstance) nearestThing).getConnectionEdge());
    }
    if (diagnose == DrawAppNew.rectangle) {
      openObject();
    }
    else {
      openProcess();

    }

  }

  /**
   * the function openThingIn find 3 points of shape in order to decide where to open the
   * Thing that was drawn
   * @return the Instance in which the Thing should be opened
   */
  private IXConnectionEdgeInstance openThingIn() {
     Point[] originalPoints = new Point[3];//an array which holds 3 points
     Vector tempCompVector = new Vector();
     IXThingInstance nearestThing;//where to open the thing
     int componentCnt = 0, index = 0;

    //find 3 points of shape in order to decide where to open
     int pointsSize = DrawAppNew.points.size() - 1;//the number of points the draw has
    for (int i = 0; i < 3; i++) {
      originalPoints[i] = (Point) DrawAppNew.points.
          elementAt(i * pointsSize / 3);
    }
    myOpd = myProject.getCurrentOpd();

    return nearestThingInstance(originalPoints, 0);

  }

  /**
   * the function showPopUp opens the relevant popup menu
   * @param showPoint where to open the popup menu
   * @param menuKind 0 if its a relation menu, 1 if its an object/state menu
   */
  private void showPopUp(Point showPoint, int menuKind) {

     JPopupMenu jpm = new HioPopUps(menuKind);//new popupmenu
    if (menuKind == 0) {
      jpm.show(myProject.getCurrentOpd().getDrawingArea(), (int) showPoint.getX(),
               (int) showPoint.getY());

    }
    if (menuKind == 1) {
      jpm.show( ( (ThingInstance) openIn).getConnectionEdge(),
               (int) showPoint.getX(), (int) showPoint.getY());

    }

  }

  /**
   * the function addToX determine the value to add/remove to the x parameter of a point
   * in order to find the closest state Instance more accurately
   * @param addTo the point to change the value
   * @param otherEdge the point in the other side of the link in order to calculate the direction
   * and the proportion of the change
   * @param range a parameter for calculating the value of the change
   * @return the value of the change, minos if its for remove
   */
  private int addToX(Point addTo, Point otherEdge, int range) {
     double xSide = Math.pow(addTo.getX() - otherEdge.getX(), 2);//who much to add in x side
     double ySide = Math.pow(addTo.getY() - otherEdge.getY(), 2);//who much to add in y side
     double hypotenuse = Math.sqrt(xSide + ySide);
     double addValue = range * Math.sqrt(xSide) / hypotenuse;//the proportion to be added
    if (addTo.getX() - otherEdge.getX() < 0) {
      addValue *= -1;
    }
    return (int) addValue;
  }

  /**
   * the function addToY determine the value to add/remove to the y parameter of a point
   * in order to find the closest state Instance more accurately
   * @param addTo the point to change the value
   * @param otherEdge the point in the other side of the link in order to calculate the direction
   * and the proportion of the change
   * @param range a parameter for calculating the value of the change
   * @return the value of the change, minos if its for remove
   */
  private int addToY(Point addTo, Point otherEdge, int range) {
     double xSide = Math.pow(addTo.getX() - otherEdge.getX(), 2);//who much to add in x side
     double ySide = Math.pow(addTo.getY() - otherEdge.getY(), 2);//who much to add in y side
     double hypotenuse = Math.sqrt(xSide + ySide);
     double addValue = range * Math.sqrt(ySide) / hypotenuse;//the proportion to be added
    if (addTo.getY() - otherEdge.getY() < 0) {
      addValue *= -1;
    }
    return (int) addValue;
  }
  
}
//end of OpenDrawInOpcat
