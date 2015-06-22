package gui.undo;
import gui.opdProject.OpdProject;
import gui.projectStructure.FundamentalRelationEntry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GraphicalRelationRepresentation;

import java.awt.Container;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableDeleteFundamentalRelation extends AbstractUndoableEdit
{
  private UndoableAddFundamentalRelation antagonist;
  private FundamentalRelationInstance tempRelation;
  private Container myContainer;
  private FundamentalRelationEntry myEntry;
  private GraphicalRelationRepresentation gRepr;

  public UndoableDeleteFundamentalRelation(OpdProject project, FundamentalRelationInstance pInstance)
  {
      antagonist = new UndoableAddFundamentalRelation(project, pInstance);
  }

  public String getPresentationName()
  {
    return "Fundamental Relation Deletion";
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
