package gui.opdProject;
import exportedAPI.opcatAPIx.IXNode;
import extensionTools.opcatLayoutManager.ConstraintBasedForceDirectedLayoutSystem;
import extensionTools.opcatLayoutManager.Constraints.Constraint;
import extensionTools.opcatLayoutManager.Constraints.MultiNodeConstraint;
import gui.Opcat2;
import gui.opdGraphics.opdBaseComponents.OpdFundamentalRelation;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.ThingInstance;
import gui.undo.CompoundUndoAction;
import gui.undo.UndoableMoveResizeComponent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.event.UndoableEditEvent;



public class OpcatLayoutManager{
  OpdProject myProject;
  ConstraintBasedForceDirectedLayoutSystem myLayoutManager;


  public OpcatLayoutManager(OpdProject project){
    myProject = project;
    myLayoutManager = new ConstraintBasedForceDirectedLayoutSystem();
  }

  private void alignDirections(int direction, int origin){
    Opd currentOpd = myProject.getCurrentOpd();
    if (currentOpd == null){
      return;
    }

    ArrayList oldLocations = new ArrayList();
    IXNode mainNode = null;

    for (Enumeration enumSelected = currentOpd.getSelectedItems();
         enumSelected.hasMoreElements(); ) {
      Object currItem = enumSelected.nextElement();
      if (currItem instanceof ThingInstance) {
        ThingInstance currInstance = (ThingInstance) currItem;
        oldLocations.add(new NodeOldLocation(currInstance.getConnectionEdge()));
        mainNode = currInstance;
      }
    }

    //
    // Clear any previously inserted elements from the system.
    //
    myLayoutManager.clear();

    //
    // Create an alignment constraint according to the given direction and origin.
    // This function automatically adds any involved nodes and edges.
    //
    Constraint constraint = myLayoutManager.createAlignmentConstraint( direction,
                                                                       origin,
                                                                       mainNode,
                                                                       currentOpd.getSelectedItems());


    //
    // Add the constraint to the system.
    //
    myLayoutManager.addConstraint(constraint);

    //
    // Initialize an arrangment operation.
    // This function must be called before any arrangment operation.
    //
    myLayoutManager.initArrange(false /* one-phase arrangment */);

    //
    // Start an arangment operation.
    //
    myLayoutManager.arrange(100 /* maximum 100 iterations */);

    CompoundUndoAction undoAction = new CompoundUndoAction();

    for(Iterator oldLocs = oldLocations.iterator(); oldLocs.hasNext();)
    {
      NodeOldLocation currNode = (NodeOldLocation)oldLocs.next();
      JComponent jNode = currNode.node;
      undoAction.addAction(new UndoableMoveResizeComponent(jNode, currNode.location, jNode.getSize(), jNode.getLocation(), jNode.getSize(), myProject));
    }

    Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(this, undoAction));
    Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
    Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

  }

  public void align2Left(){
    alignDirections(Constraint.directionVertical, MultiNodeConstraint.originStart);
  }

  public void align2CenterVertically(){
    alignDirections(Constraint.directionVertical, MultiNodeConstraint.originCenter);
  }

  public void align2Right(){
    alignDirections(Constraint.directionVertical, MultiNodeConstraint.originEnd);
  }

  public void align2Top(){
    alignDirections(Constraint.directionHorizontal, MultiNodeConstraint.originStart);
  }

  public void align2CenterHorizontally(){
    alignDirections(Constraint.directionHorizontal, MultiNodeConstraint.originCenter);
  }

  public void align2Bottom(){
    alignDirections(Constraint.directionHorizontal, MultiNodeConstraint.originEnd);
  }

  public void alignAsTree(){
    Opd currentOpd = myProject.getCurrentOpd();
    if (currentOpd == null){
      return;
    }

    ArrayList oldLocations = new ArrayList();
    IXNode mainNode = null;
    IXNode relationNode = null;

    for (Enumeration enumSelected = currentOpd.getSelectedItems();
         enumSelected.hasMoreElements(); ) {
      Object currItem = enumSelected.nextElement();
      if (currItem instanceof ThingInstance) {
        ThingInstance currInstance = (ThingInstance) currItem;
        oldLocations.add(new NodeOldLocation(currInstance.getConnectionEdge()));
        mainNode = currInstance;
      }

      if (currItem instanceof FundamentalRelationInstance) {
        FundamentalRelationInstance currInstance = (FundamentalRelationInstance) currItem;
        OpdFundamentalRelation triangle = currInstance.getGraphicalRelationRepresentation().getRelation();
        oldLocations.add(new NodeOldLocation(triangle));
        relationNode = triangle;
      }
    }

//    System.err.println("Main node "+((ThingInstance)mainNode).getEntry().getName()+" rel "+relationNode);
    myLayoutManager.clear();

    Constraint constraint = myLayoutManager.createTreeConstraint( mainNode,
                                                                  relationNode,
                                                                  currentOpd.getSelectedItems());
    myLayoutManager.addConstraint(constraint);
    myLayoutManager.initArrange(false);
    myLayoutManager.arrange(100);

    CompoundUndoAction undoAction = new CompoundUndoAction();

    for(Iterator oldLocs = oldLocations.iterator(); oldLocs.hasNext();)
    {
      NodeOldLocation currNode = (NodeOldLocation)oldLocs.next();
      JComponent jNode = currNode.node;
      undoAction.addAction(new UndoableMoveResizeComponent(jNode, currNode.location, jNode.getSize(), jNode.getLocation(), jNode.getSize(), myProject));
    }

    Opcat2.getUndoManager().undoableEditHappened(new UndoableEditEvent(this, undoAction));
    Opcat2.setUndoEnabled(Opcat2.getUndoManager().canUndo());
    Opcat2.setRedoEnabled(Opcat2.getUndoManager().canRedo());

  }




  class NodeOldLocation{
    JComponent node;
    Point location;

    NodeOldLocation(JComponent node){
      this.node = node;
      location = new Point(node.getX(), node.getY());
    }

  }
}