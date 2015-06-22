package gui.undo;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableAddObject extends AbstractUndoableEdit
{
  private OpdProject myProject;
  private ObjectInstance tempObject;
  private Container myContainer;
  private ObjectEntry myEntry;
  private Vector stateVector;

  private Vector statesSizes;
  private Vector statesLocations;
  double originalSize;
  private Point location;
  private Dimension size;

  public UndoableAddObject(OpdProject project, ObjectInstance pInstance)
  {
	myProject = project;
	tempObject = pInstance;
	myContainer = tempObject.getThing().getParent();
	myEntry = (ObjectEntry)pInstance.getEntry();

    GenericTable config = myProject.getConfiguration();
    originalSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
    location = tempObject.getConnectionEdge().getLocation();
    size = tempObject.getConnectionEdge().getSize();

	stateVector = new Vector();
    statesSizes = new Vector();
    statesLocations = new Vector();

	for (Enumeration e = tempObject.getStateInstances();e.hasMoreElements();)
	{
        StateInstance si = (StateInstance)e.nextElement();
	  stateVector.add(si);
      statesSizes.add(si.getState().getSize());
      statesLocations.add(si.getState().getLocation());
	}
  }

  public String getPresentationName()
  {
	return "Object Addition";
  }

  public void undo()
  {
	super.undo();
    performUndoJob();
  }

  void performUndoJob()
  {
      myProject.deleteObject(tempObject);
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

      myEntry.addInstance(tempObject.getKey(), tempObject);
      tempObject.add2Container(myContainer);

      GenericTable config = myProject.getConfiguration();
      double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
      double factor = currentSize/originalSize;

      tempObject.getConnectionEdge().setLocation((int)Math.round(location.getX()*factor), (int)Math.round(location.getY()*factor));
      tempObject.getConnectionEdge().setSize((int)Math.round(size.getWidth()*factor), (int)Math.round(size.getHeight()*factor));

      int counter = 0;
      for (Enumeration e = stateVector.elements(); e.hasMoreElements();)
      {

          StateInstance currInstance = (StateInstance)e.nextElement();
          StateEntry currEntry = (StateEntry)currInstance.getEntry();

          Point stLocation = (Point)statesLocations.get(counter);
          Dimension stSize = (Dimension)statesSizes.get(counter);

          if (ms.getEntry(currEntry.getId()) == null)
          {
            ms.put(currEntry.getId(), currEntry);
          }

          currEntry.addInstance(currInstance.getKey(), currInstance);
          tempObject.getThing().add(currInstance.getConnectionEdge());

          currInstance.getState().setLocation((int)Math.round(stLocation.getX()*factor), (int)Math.round(stLocation.getY()*factor));
          currInstance.getState().setSize((int)Math.round(stSize.getWidth()*factor), (int)Math.round(stSize.getHeight()*factor));
          counter++;
      }
  }

}

