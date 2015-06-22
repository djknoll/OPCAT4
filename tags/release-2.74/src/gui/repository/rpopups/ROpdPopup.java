package gui.repository.rpopups;

import gui.images.repository.RepositoryImages;
import gui.images.standard.StandardImages;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.repository.BaseView;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;


public class ROpdPopup extends RDefaultPopup
{
	Opd myOpd;
	public ROpdPopup(BaseView view, OpdProject prj)
	{
		super(view, prj);
		myOpd = (Opd)userObject;
		add(showAction);
		add(new JSeparator());
		add(deleteAction);
//		add(new JSeparator());
//		add(generateOPLAction);
		addCollapseExpand();
	}

	Action showAction = new AbstractAction("Show OPD", RepositoryImages.OPD){
		public void actionPerformed(ActionEvent e){
			myOpd.setVisible(true);
			try{
				myOpd.getOpdContainer().setSelected(true);
				myOpd.getOpdContainer().setMaximum(true);
			}catch(java.beans.PropertyVetoException pve)
			{
				pve.printStackTrace();
			}
		}
	};

	Action deleteAction = new AbstractAction("Delete OPD", StandardImages.DELETE){
		public void actionPerformed(ActionEvent e){
		   //JOptionPane.showMessageDialog(Opcat2.getFrame(), "This option not impemented yet", "Opcat2 - tmp message", JOptionPane.INFORMATION_MESSAGE);
		   myProject.deleteOpd(myOpd);
		}
	};

//	Action pasteAction = new AbstractAction("Paste", StandardImages.PASTE){
//		public void actionPerformed(ActionEvent e){
//			myProject.paste(x, y,myProject.getCurrentOpd().getDrawingArea());
//			myProject.getCurrentOpd().getDrawingArea().repaint();
//		}
//	};


}