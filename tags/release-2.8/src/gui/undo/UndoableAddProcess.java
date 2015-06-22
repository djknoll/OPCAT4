package gui.undo;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableAddProcess extends AbstractUndoableEdit
{
  private OpdProject myProject;
  private ProcessInstance tempProcess;
  private Container myContainer;
  private ProcessEntry myEntry;
  double originalSize;
  private Point location;
  private Dimension size;


  public UndoableAddProcess(OpdProject project, ProcessInstance pInstance)
  {
	myProject = project;
	tempProcess = pInstance;
	myContainer = tempProcess.getThing().getParent();
	myEntry = (ProcessEntry)pInstance.getEntry();
    GenericTable config = myProject.getConfiguration();
    originalSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
    location = tempProcess.getConnectionEdge().getLocation();
    size = tempProcess.getConnectionEdge().getSize();
  }

  public String getPresentationName()
  {
	return "Process Addition";
  }

  public void undo()
  {
	super.undo();
    performUndoJob();
  }

  void performUndoJob()
  {
      myProject.deleteProcess(tempProcess);
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
      }

      myEntry.addInstance(tempProcess.getKey(), tempProcess);
      tempProcess.add2Container(myContainer);

      GenericTable config = myProject.getConfiguration();
      double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
      double factor = currentSize/originalSize;

      tempProcess.getConnectionEdge().setLocation((int)Math.round(location.getX()*factor), (int)Math.round(location.getY()*factor));
      tempProcess.getConnectionEdge().setSize((int)Math.round(size.getWidth()*factor), (int)Math.round(size.getHeight()*factor));
  }
}
