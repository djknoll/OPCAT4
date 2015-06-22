package gui.undo;
import gui.opdProject.OpdProject;
import gui.projectStructure.ObjectInstance;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableDeleteObject extends AbstractUndoableEdit
{
  private UndoableAddObject antagonist;

  public UndoableDeleteObject(OpdProject project, ObjectInstance pInstance)
  {
      antagonist = new UndoableAddObject(project, pInstance);
  }

  public String getPresentationName()
  {
    return "Object Deletion";
  }

  public void redo()
  {
    super.redo();
    antagonist.performUndoJob();
  }

  public void undo()
  {
    super.undo();
    antagonist.performRedoJob();
  }

}

