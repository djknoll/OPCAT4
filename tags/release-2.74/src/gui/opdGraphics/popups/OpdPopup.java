package gui.opdGraphics.popups;

import gui.Opcat2;
import gui.images.standard.StandardImages;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;



public class OpdPopup extends DefaultPopup
{
  private int x;
  private int y;
	public OpdPopup(OpdProject prj, int x, int y)
	{
		super(prj, null);
				this.x=x;
				this.y=y;
		add(pasteAction);
		add(deleteAction);
//		add(new JSeparator());
//		add(objectAction);
//		add(processAction);
//		add(new JSeparator());
//		generateOPLAction.setEnabled(false);
//		add(generateOPLAction);
		add(new JSeparator());
		add(propertiesAction);
	}

	Action propertiesAction = new AbstractAction("Properties", StandardImages.PROPERTIES){
		public void actionPerformed(ActionEvent e){
		   ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(myProject, Opcat2.getFrame(), "Project Properties");
		   ppd.showDialog();
		   myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action deleteAction = new AbstractAction("Delete OPD", StandardImages.DELETE){
		public void actionPerformed(ActionEvent e){
		   //JOptionPane.showMessageDialog(Opcat2.getFrame(), "This option not impemented yet", "Opcat2 - tmp message", JOptionPane.INFORMATION_MESSAGE);
		   myProject.deleteOpd(myProject.getCurrentOpd());
		   //myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

	Action pasteAction = new AbstractAction("Paste", StandardImages.PASTE){
		public void actionPerformed(ActionEvent e){
			//JOptionPane.showMessageDialog(null, "This option is not implemented yet", "Message" ,JOptionPane.INFORMATION_MESSAGE);
			myProject.paste(x, y,myProject.getCurrentOpd().getDrawingArea());
			Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
			myProject.getCurrentOpd().getDrawingArea().repaint();
		}
	};

}