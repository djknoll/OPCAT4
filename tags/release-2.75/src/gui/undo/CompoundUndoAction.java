package gui.undo;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.undo.AbstractUndoableEdit;

public class CompoundUndoAction extends AbstractUndoableEdit
{
    private LinkedList actions;

    public CompoundUndoAction()
    {
        actions = new LinkedList();
    }

    public String getPresentationName()
    {
        return "CompoundUndoAction";
    }

    public void addAction(AbstractUndoableEdit nextAction)
    {
        actions.add(nextAction);
    }

    public void undo()
    {
        super.undo();

        ListIterator it = actions.listIterator(actions.size());

        while (it.hasPrevious())
        {
            AbstractUndoableEdit prevAction = (AbstractUndoableEdit)it.previous();
            prevAction.undo();
        }

    }

    public void redo()
    {
        super.redo();

        ListIterator it = actions.listIterator();
        while (it.hasNext())
        {
            AbstractUndoableEdit nextAction = (AbstractUndoableEdit)it.next();
            nextAction.redo();
        }
    }
}
