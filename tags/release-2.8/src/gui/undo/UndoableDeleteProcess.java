package gui.undo;
import gui.opdProject.OpdProject;
import gui.projectStructure.ProcessInstance;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableDeleteProcess extends AbstractUndoableEdit
{
  private UndoableAddProcess antagonist;

  public UndoableDeleteProcess(OpdProject project, ProcessInstance pInstance)
  {
      antagonist = new UndoableAddProcess(project, pInstance);
  }

  public String getPresentationName()
  {
    return "Process Deletion";
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
