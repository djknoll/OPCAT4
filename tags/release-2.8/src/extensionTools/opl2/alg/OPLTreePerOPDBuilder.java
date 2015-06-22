package extensionTools.opl2.alg;

import java.util.Enumeration;
import java.util.Vector;

import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IInstance;
import exportedAPI.opcatAPI.IStateEntry;
import exportedAPI.opcatAPI.ISystemStructure;
import extensionTools.opl2.generated.ObjectFactory;

/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author OPCAT team
 * @version 1.0
 */

public final class OPLTreePerOPDBuilder
    extends OPLTreeBuilder {
  public OPLTreePerOPDBuilder(ISystemStructure myStructure_,
                              String systemName_,
                              long opd_,ObjectFactory ob) {
    super(myStructure_, systemName_,ob);
    opd = opd_;
    currOPD = opd_;
    if(myStructure == null || myStructure.getIOpd(opd)== null || myStructure.getIOpd(opd).getParentIOpd()==null){
      mainElem = null;
    }else
    mainElem = myStructure.getIOpd(opd).getMainIEntry();
  }

  public boolean isMainElement(IEntry elem){
    if(mainElem==null)
      return false;
    return (mainElem.getId()==elem.getId());

  }

  public IEntry getMainElement(){
    return mainElem;
  }


  protected Enumeration getSystemElements() {
    Enumeration e;
    IEntry entry;
    //java.util.Collection elems = new LinkedList();
    Vector elems = new Vector();
    /*if(mainElem!=null){
      elems.add(myStructure.getIOpd(opd).getMainIEntry());
    }else{
    }*/
    Enumeration el = myStructure.getInstancesInOpd(opd);
      while(el.hasMoreElements()){
        entry = ((IInstance)el.nextElement()).getIEntry();
        if(entry!=mainElem)
          elems.add(entry);
      }
    e = elems.elements();
    return e;
  }

  /**
   * rule = 0 : all relations by source
   * only tests if the relation in the opd
   * rule = 1 : Bidirectional relations && Specialization +
   * test if it is in the opd - relations by destination
   * */
//  protected boolean testRelation(IRelationEntry rel, int rule) {
//    if (!OPLGeneral.isByDestination(rule))
//      return rel.hasInstanceInOpd(opd);
//    int type = rel.getRelationType();
//    return (rel.hasInstanceInOpd(opd) &&
//            ( (type == OpcatConstants.BI_DIRECTIONAL_RELATION &&
//               OPLGeneral.isNotEmpty(rel.getBackwardRelationMeaning())
//               && !rel.getForwardRelationMeaning().equals("")) ||
//             (type == OpcatConstants.SPECIALIZATION_RELATION)));
//  }

//  protected boolean testLink(ILinkEntry link) {
//      return link.hasInstanceInOpd(opd);
//  }
//
//  protected boolean testRelation(IRelationEntry link) {
//      return link.hasInstanceInOpd(opd);
//  }
//
//  protected boolean testState(IStateEntry link) {
//      return link.hasInstanceInOpd(opd);
//  }

  protected boolean testEntity(IEntry entity) {
    if(entity instanceof IStateEntry){
      return ((IStateEntry)entity).hasVisibleInstancesInOpd(opd);
      //System.err.println(" My opd is:" + opd + " my name is " + entity.getName());
    }
   // if(entity.hasInstanceInOpd(opd))
   // System.err.println(" "+entity.getName() + " has inctance in opd "+ opd);
     return entity.hasInstanceInOpd(opd);
 }

  protected long opd;
}
