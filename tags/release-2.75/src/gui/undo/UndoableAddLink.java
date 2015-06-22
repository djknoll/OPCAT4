package gui.undo;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmProceduralLink;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.MainStructure;

import java.awt.Container;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableAddLink extends AbstractUndoableEdit
{
  private OpdProject myProject;
  private LinkInstance tempLink;
  private Container myContainer;
  private LinkEntry myEntry;

  public UndoableAddLink(OpdProject project, LinkInstance pInstance)
  {
	myProject = project;
	tempLink = pInstance;
	myContainer = tempLink.getSourceDragger().getParent();
	myEntry = (LinkEntry)pInstance.getEntry();
  }

  public String getPresentationName()
  {
	return "Link Addition";
  }

  public void undo()
  {
	super.undo();
    performUndoJob();
  }

  void performUndoJob()
  {
      myProject.deleteLink(tempLink);
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

          OpmProceduralLink lLink = (OpmProceduralLink)myEntry.getLogicalEntity();
          ConnectionEdgeEntry sourceEntry	= ms.getSourceEntry(lLink);
          ConnectionEdgeEntry destinationEntry = ms.getDestinationEntry(lLink);
          sourceEntry.addLinkSource(lLink);
          destinationEntry.addLinkDestination(lLink);
      }

      myEntry.addInstance(tempLink.getKey(), tempLink);
      tempLink.add2Container(myContainer);
      tempLink.update();
  }

}
