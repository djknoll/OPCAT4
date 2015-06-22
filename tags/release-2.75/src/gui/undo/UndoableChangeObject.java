package gui.undo;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.projectStructure.Entry;
import gui.projectStructure.ObjectEntry;

import java.util.Enumeration;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableChangeObject extends AbstractUndoableEdit
{
    private OpdProject myProject;
    private ObjectEntry myEntry;
    private OpmObject originalObject;
    private OpmObject changedObject;

    public UndoableChangeObject(OpdProject project, ObjectEntry pEntry,
                                OpmObject originalObject, OpmObject changedObject)
    {
        myProject = project;
        myEntry = pEntry;
        this.originalObject = new OpmObject(-1, "");
        this.changedObject = new OpmObject(-1, "");

        this.originalObject.copyPropsFrom(originalObject);
        this.changedObject.copyPropsFrom(changedObject);
    }

    public String getPresentationName()
    {
        return "Object Change";
    }

    public void undo()
    {
        super.undo();
        ((OpmObject)myEntry.getLogicalEntity()).copyPropsFrom(originalObject);
        myEntry.updateInstances();

        if (!originalObject.getName().equals(changedObject.getName()))
        {
            _updateObjectsTypes(originalObject.getName());
        }
    }

    public void redo()
    {
        super.redo();
        ((OpmObject)myEntry.getLogicalEntity()).copyPropsFrom(changedObject);
        myEntry.updateInstances();

        if (!originalObject.getName().equals(changedObject.getName()))
        {
            _updateObjectsTypes(changedObject.getName());
        }

    }

    private void _updateObjectsTypes(String newName)
    {
        for (Enumeration e = myProject.getComponentsStructure().getElements(); e.hasMoreElements();)
        {
            Entry currEntry = (Entry)e.nextElement();

            if (currEntry instanceof ObjectEntry)
            {
                OpmObject currObject = (OpmObject)currEntry.getLogicalEntity();
                if (currObject.getTypeOriginId() == myEntry.getId())
                {
                    currObject.setType(newName);
                    currEntry.updateInstances();
                }
            }
        }

        return;
    }

}
