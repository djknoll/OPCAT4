package gui.opdGraphics.popups;
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

	Action deleteAction = new AbstractAction("Delete", StandardImages.DELETE){
		public void actionPerformed(ActionEvent e){
		   myProject.delete();
		   myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action cutAction = new AbstractAction("Cut", StandardImages.CUT){
		public void actionPerformed(ActionEvent e){
//			JOptionPane.showMessageDialog(Opcat2.getFrame(), "This option is not implemented yet", "Message" ,JOptionPane.INFORMATION_MESSAGE);
			myProject.cut();
			myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action copyAction = new AbstractAction("Copy", StandardImages.COPY){
		public void actionPerformed(ActionEvent e){
			myProject.copy();
			myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};




//	class MySingleSelectionModel extends DefaultSingleSelectionModel
//	{
//		MySingleSelectionModel()
//		{
//			super();
//		}
//
//		public int getSelectedIndex()
//		{
//			int selectedIndex = super.getSelectedIndex();
//			if(selectedIndex == 1)
//			{
//				return -1;
//			}
//			return selectedIndex;
//		}
//
//		public void setSelectedIndex(int index)
//		{
//			if(index == 0)
//			{
//				return;
//			}
//			super.setSelectedIndex(index);
//		}
//	};
}