/*
 *  opcat2
 *  package: opdGraphics
 *  file:    AroundDragger.java
 */

package gui.opdGraphics.draggers;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.dialogs.LinkPropertiesDialog;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmProceduralLink;

import java.awt.Graphics;

/**
 *  <p>Abstract class AroundDragger is a superclass for all classes which represents
 *  items that can be dragged around Objects, Processes or States in OPD.
 *  Actually this class provides dragging functionality for edges of all kinds
 *  of OPD links and relations.</p>
 */

public class TransparentLinkDragger extends OpdLink
{
	public TransparentLinkDragger(Connectable pEdge, RelativeConnectionPoint pPoint, OpmProceduralLink pLink,
							long pOpdId, long pEntityInOpdId, OpdProject pProject)
	{
		  super(pEdge, pPoint, pLink, pOpdId, pEntityInOpdId, pProject);
		  updateDragger();
	}

	public void paintComponent(Graphics g)
	{
		  if(isSelected())
		  {
			drawSelection(g);
		  }
		}

	public void callPropertiesDialog()
	{
		LinkPropertiesDialog ld = new LinkPropertiesDialog(myLink, new OpdKey(getOpdId(), getEntityInOpdId()), myProject, "");
		ld.show();
	}

}
