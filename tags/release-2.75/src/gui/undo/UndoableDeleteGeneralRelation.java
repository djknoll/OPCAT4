package gui.undo;
import gui.opdProject.OpdProject;
import gui.projectStructure.GeneralRelationInstance;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableDeleteGeneralRelation extends AbstractUndoableEdit
{
  private UndoableAddGeneralRelation antagonist;

  public UndoableDeleteGeneralRelation(OpdProject project, GeneralRelationInstance pInstance)
  {
      antagonist = new UndoableAddGeneralRelation(project, pInstance);
  }

  public String getPresentationName()
  {
    return "General Relation Deletion";
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
