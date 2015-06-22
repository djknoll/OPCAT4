package gui.undo;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.undo.AbstractUndoableEdit;

public class UndoableMoveResizeComponent extends AbstractUndoableEdit
{
  private JComponent myComponent;
  private Point initialLocation, finalLocation;
  private Dimension initialSize, finalSize;
  double originalSize;
  OpdProject myProject;

  public UndoableMoveResizeComponent(JComponent component, Point iLocation, Dimension iSize,
									 Point fLocation, Dimension fSize, OpdProject myProject)
  {
	this.myProject = myProject;
	myComponent = component;
	initialLocation = iLocation;
	initialSize = iSize;
	finalLocation = fLocation;
	finalSize = fSize;
	GenericTable config = myProject.getConfiguration();
	originalSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();

  }

  public String getPresentationName()
  {
	return "Move/Resize Component";
  }

  public void undo()
  {
	super.undo();

	GenericTable config = myProject.getConfiguration();
	double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
	double factor = currentSize/originalSize;

	myComponent.setLocation((int)Math.round(initialLocation.getX()*factor), (int)Math.round(initialLocation.getY()*factor));
	myComponent.setSize((int)Math.round(initialSize.getWidth()*factor), (int)Math.round(initialSize.getHeight()*factor));
  }

  public void redo()
  {
	super.redo();

	GenericTable config = myProject.getConfiguration();
	double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
	double factor = currentSize/originalSize;

	myComponent.setLocation((int)Math.round(finalLocation.getX()*factor), (int)Math.round(finalLocation.getY()*factor));
	myComponent.setSize((int)Math.round(finalSize.getWidth()*factor), (int)Math.round(finalSize.getHeight()*factor));
  }
}
