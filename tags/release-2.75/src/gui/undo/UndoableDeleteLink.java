package gui.undo;
import gui.opdProject.OpdProject;
import gui.projectStructure.LinkInstance;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableDeleteLink extends AbstractUndoableEdit
{
  private UndoableAddLink antagonist;

  public UndoableDeleteLink(OpdProject project, LinkInstance pInstance)
  {
      antagonist = new UndoableAddLink(project,pInstance);
  }

  public String getPresentationName()
  {
    return "Link Deletion";
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
