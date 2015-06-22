package gui.opdProject;

import gui.Opcat2;
import gui.opdGraphics.OpdContainer;

import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;

/**
 *  This class is desktop manager for MDI application Opcat2.
 */

class OpcatDesktopManager extends DefaultDesktopManager
{
	OpdProject myProject;
	public OpcatDesktopManager(OpdProject pProject)
	{
		super();
		myProject = pProject;
	}

	public void activateFrame(JInternalFrame f)
	{
		super.activateFrame(f);
		if (f instanceof OpdContainer)
		{
			myProject.setCurrentOpd(((OpdContainer)f).getOpd());
			Opcat2.updateStructureChange(Opcat2.OPD_CHANGE);
		}
	}

	public void deactivateFrame(JInternalFrame f)
	{
		super.deactivateFrame(f);
		if (f instanceof OpdContainer)
		{
//			myProject.getCurrentOpd().removeSelection();
			myProject.setCurrentOpd(null);
			Opcat2.updateStructureChange(Opcat2.OPD_CHANGE);
		}
	}

}
