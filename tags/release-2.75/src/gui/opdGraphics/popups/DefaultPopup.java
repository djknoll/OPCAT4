package gui.opdGraphics.popups;
import gui.actions.edit.CopyAction;
import gui.actions.edit.CutAction;
import gui.actions.edit.DeleteAction;
import gui.actions.edit.PasteAction;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.projectStructure.Instance;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;


public class DefaultPopup extends JPopupMenu
{
	OpdProject myProject;
	Instance myInstance;
	protected boolean multiSelected = false;

	public DefaultPopup(OpdProject prj, Instance inst)
	{
		//super(((ThingInstance)(prj.getCurrentOpd().getSelectedItem())).getThing().getEntity().getEntityName());
//		setSelectionModel(new MySingleSelectionModel());
		myProject = prj;
		myInstance = inst;
		if(inst != null && myProject.getComponentsStructure().getOpd(inst.getKey().getOpdId()).getSelectedItemsHash().size() > 1)
		{
			multiSelected = true;
		}

	}

	PasteAction pasteAction = new PasteAction("Paste", StandardImages.PASTE);
	CutAction cutAction = new CutAction("Cut", StandardImages.CUT ) ;   
	CopyAction copyAction = new CopyAction("Copy", StandardImages.COPY) ;
	DeleteAction deleteAction = new DeleteAction("Delete", StandardImages.DELETE);

}