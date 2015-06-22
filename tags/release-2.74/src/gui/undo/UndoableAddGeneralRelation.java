package gui.undo;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmGeneralRelation;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.GeneralRelationEntry;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.MainStructure;

import java.awt.Container;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableAddGeneralRelation extends AbstractUndoableEdit
{
  private OpdProject myProject;
  private GeneralRelationInstance tempRelation;
  private Container myContainer;
  private GeneralRelationEntry myEntry;

  public UndoableAddGeneralRelation(OpdProject project, GeneralRelationInstance pInstance)
  {
	myProject = project;
	tempRelation = pInstance;
	myContainer = tempRelation.getSourceDragger().getParent();
	myEntry = (GeneralRelationEntry)pInstance.getEntry();
  }

  public String getPresentationName()
  {
	return "General Relation Addition";
  }

  public void undo()
  {
	super.undo();
    performUndoJob();
  }

  void performUndoJob()
  {
	myProject.deleteGeneralRelation(tempRelation);
  }

  public void redo()
  {
	super.redo();
    performRedoJob();
  }

  void performRedoJob()
  {
      MainStructure ms = myProject.getComponentsStructure();

      if (ms.getEntry(myEntry.getId()) == null)
      {
          ms.put(myEntry.getId(), myEntry);

          OpmGeneralRelation lRelation = (OpmGeneralRelation)myEntry.getLogicalEntity();
          ConnectionEdgeEntry sourceEntry	= ms.getSourceEntry(lRelation);
          ConnectionEdgeEntry destinationEntry = ms.getDestinationEntry(lRelation);
          sourceEntry.addGeneralRelationSource(lRelation);
          destinationEntry.addGeneralRelationDestination(lRelation);
      }

      myEntry.addInstance(tempRelation.getKey(), tempRelation);
      tempRelation.add2Container(myContainer);
      tempRelation.update();

  }

}
