package gui.undo;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmState;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;


public class UndoableAddState extends AbstractUndoableEdit
{
  private OpdProject myProject;
  private StateEntry myEntry;
  private Vector instances;
  private Vector statesSizes;
  private Vector statesLocations;
  double originalSize;

  public UndoableAddState(OpdProject project, StateEntry entry)
  {
	myProject = project;
	myEntry = entry;

    GenericTable config = myProject.getConfiguration();
    originalSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();

	instances = new Vector();
    statesSizes = new Vector();
    statesLocations = new Vector();

	for (Enumeration e = myEntry.getInstances(); e.hasMoreElements();)
	{
        StateInstance si = (StateInstance)e.nextElement();
        instances.add(si);
        statesSizes.add(si.getState().getSize());
        statesLocations.add(si.getState().getLocation());
	}

  }

  public String getPresentationName()
  {
	return "State Addition";
  }

  public void undo()
  {
    super.undo();
    performUndoJob();
  }

  void performUndoJob()
  {
      myProject.deleteState((StateInstance)instances.get(0));
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

      ObjectEntry parent = (ObjectEntry)ms.getEntry(myEntry.getParentObjectId());
      parent.addState((OpmState)myEntry.getLogicalEntity());

      GenericTable config = myProject.getConfiguration();
      double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
      double factor = currentSize/originalSize;

      int counter = 0;

      for (Enumeration e = instances.elements(); e.hasMoreElements();)
      {

        StateInstance currInstance = (StateInstance)e.nextElement();
        myEntry.addInstance(currInstance.getKey(), currInstance);
        currInstance.getParent().add(currInstance.getConnectionEdge());

        Point stLocation = (Point)statesLocations.get(counter);
        Dimension stSize = (Dimension)statesSizes.get(counter);
        currInstance.getState().setLocation((int)Math.round(stLocation.getX()*factor), (int)Math.round(stLocation.getY()*factor));
        currInstance.getState().setSize((int)Math.round(stSize.getWidth()*factor), (int)Math.round(stSize.getHeight()*factor));
        counter++;

        currInstance.getParent().repaint();
      }

  }

}
