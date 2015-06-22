package extensionTools.hio;


import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXThingInstance;
import gui.images.opm.OPMImages;
import gui.opdGraphics.dialogs.ObjectPropertiesDialog;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;

/**
 * <p>Title: class hioPopUps</p>
 * <p>Description: opens pop up toolbars for object or state in zoomIn or fullRelation popup</p>
 */

public class HioPopUps extends JPopupMenu{
  /**
   * private OpdProject myProject holds the current project
   */
  private OpdProject myProject = extensionTools.hio.OpenDrawInOpcat.myProject;//holds the current project
  /**
   * private IXConnectionEdgeInstance openIn indecates where to open the draw
   */
  private IXConnectionEdgeInstance openIn = OpenDrawInOpcat.openIn;//indecates where to open the draw
  /**
   * private Point openAt indecates where to open the draw
   */
  private Point openAt = OpenDrawInOpcat.openAt;//indecates where to open the draw
  /**
   * private int relationType indecates what kind of relation was recognized
   */
  private int relationType;//indecates what kind of relation was recognized

  /**
   * constructor of hioPopUps
   * @param menuKind if 0 fullRelation popup, else state/object popup
   */
  public HioPopUps(int menuKind) {
    //popup for relation
    if( menuKind == 0){
      //add buttons for the following relations
      add(apAction);
      add(fcAction);
      add(ciAction);
    }

    //popup for state/object
      if( menuKind == 1){
        //add buttons for object and state
        add(objAction);
        add(stateAction);
      }


  }

  /**
   * the function openFundamentalRelation opens a chosen relation and enables undo/redo and delete
   */
  private void openFundamentalRelation(){
    FundamentalRelationInstance relation = (FundamentalRelationInstance)//the type of relation is FundamentalRelation
    OpenDrawInOpcat.openRelation(relationType);
    OpenDrawInOpcat.enableFundamentalRelation(relation);
  }

//the action performed when pressig on the popup buttons
  Action apAction = new AbstractAction("Agregation-Particulation",
                                       OPMImages.AGGREGATION) {
    public void actionPerformed(ActionEvent e) {
		relationType = 204;
		openFundamentalRelation();
    }

  };
  Action fcAction = new AbstractAction("Featuring-Characterization",
                                       OPMImages.EXHIBITION) {
    public void actionPerformed(ActionEvent e) {
      relationType = 202;
	  openFundamentalRelation();
    }
  };

  Action ciAction = new AbstractAction("Classification-Instantiation",
                                       OPMImages.INSTANTIATION) {
    public void actionPerformed(ActionEvent e) {
      relationType = 203;
	  openFundamentalRelation();
    }
  };


//action performed for an object
  Action objAction = new AbstractAction("Object",
                                        OPMImages.OBJECT) {
    public void actionPerformed(ActionEvent e) {
      OpmObject sampleObjOpm = new OpmObject(0, "");
      ObjectEntry sampleObjEntry = new ObjectEntry(sampleObjOpm, myProject);
      ObjectInstance sampleObjInstance = new ObjectInstance(sampleObjEntry,
          new OpdKey(0, 0), null, myProject, false);
      OpdObject sampleObjOpd = (OpdObject) sampleObjInstance.getConnectionEdge();
      ObjectPropertiesDialog od = new ObjectPropertiesDialog(sampleObjOpd,
          sampleObjEntry, myProject, true); //creates properties dialog

      if (od.showDialog()) {
        ObjectInstance currObject = (ObjectInstance) myProject.addObject(
            od.getName(), (int) openAt.getX(),
            (int) openAt.getY(),
            (IXThingInstance)openIn);
        currObject.setLocation( (int) openAt.getX(),
                               (int) openAt.getY());
        OpenDrawInOpcat.enableObject(currObject, sampleObjOpm, sampleObjInstance);
      }

    }
  };

//action performed for a state
  Action stateAction = new AbstractAction("State",
                                          OPMImages.STATE) {
    public void actionPerformed(ActionEvent e) {
      OpenDrawInOpcat.openState();
    }
  };


}