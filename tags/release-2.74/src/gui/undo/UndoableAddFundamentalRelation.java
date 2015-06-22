package gui.undo;
import exportedAPI.OpdKey;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmFundamentalRelation;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.FundamentalRelationEntry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GraphicRepresentationKey;
import gui.projectStructure.GraphicalRelationRepresentation;
import gui.projectStructure.MainStructure;

import java.awt.Container;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableAddFundamentalRelation extends AbstractUndoableEdit
{
  private OpdProject myProject;
  private FundamentalRelationInstance tempRelation;
  private Container myContainer;
  private FundamentalRelationEntry myEntry;
  private GraphicalRelationRepresentation gRepr;

  public UndoableAddFundamentalRelation(OpdProject project, FundamentalRelationInstance pInstance)
  {
	myProject = project;
	tempRelation = pInstance;
	myContainer = tempRelation.getLine(0).getParent();
	myEntry = (FundamentalRelationEntry)pInstance.getEntry();
	gRepr = pInstance.getGraphicalRelationRepresentation();
  }

  public String getPresentationName()
  {
	return "Fundamental Relation Addition";
  }

  public void undo()
  {
	super.undo();
    performUndoJob();
  }

  void performUndoJob()
  {
      myProject.deleteFundamentalRelation(tempRelation);
  }

  public void redo()
  {
	super.redo();
    performRedoJob();
  }

  void performRedoJob()
  {
      MainStructure ms = myProject.getComponentsStructure();

      OpdKey sKey = new OpdKey(gRepr.getSource().getOpdId(), gRepr.getSource().getEntityInOpdId());
      GraphicRepresentationKey myKey = new GraphicRepresentationKey(sKey, gRepr.getType());

      if (ms.getGraphicalRepresentation(myKey) == null)
      {
        ms.put(myKey, gRepr);
        gRepr.add2Container(myContainer);
      }

      if (ms.getEntry(myEntry.getId()) == null)
      {
        ms.put(myEntry.getId(), myEntry);

        OpmFundamentalRelation lRelation = (OpmFundamentalRelation)myEntry.getLogicalEntity();
        ConnectionEdgeEntry sourceEntry	= ms.getSourceEntry(lRelation);
        ConnectionEdgeEntry destinationEntry = ms.getDestinationEntry(lRelation);
        sourceEntry.addRelationSource(lRelation);
        destinationEntry.addRelationDestination(lRelation);
      }

      gRepr.addInstance(tempRelation.getKey(), tempRelation);
      myEntry.addInstance(tempRelation.getKey(), tempRelation);
      tempRelation.add2Container(myContainer);
      tempRelation.update();

  }

}
