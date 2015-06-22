package extensionTools.opl2.alg;

import java.util.Enumeration;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IRelationEntry;
import extensionTools.opl2.generated.ThingSentenceSet;

/* <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author OPCAT team
 * @version 1.0
 */

public abstract class OPLSentencesAdder /*extends OPLFactor*/ {

  public OPLSentencesAdder() {
  }

  public abstract boolean testRelation(IRelationEntry rel);

  //here I sort relations
  public boolean fillRelations(Enumeration e, String objName, int rule,
                               ThingSentenceSet thing,
                               OPLTreeBuilder b) throws Exception {
    boolean hasRelation = false;
    java.util.List lst = OPLGeneral.sort(e,new MyRelationsComparator(b,rule,ByProc));
    IRelationEntry rel;
    for (int i = 0;i<lst.size();i++) {
      rel = (IRelationEntry) lst.get(i);
      if (testRelation(rel) && specificRelation(thing, rel, objName, rule))
        hasRelation = true;
    }
    return hasRelation;
  }




  protected boolean specificRelation(ThingSentenceSet thing,
                                     IRelationEntry rel, String objName,
                                     int rule) throws Exception {
    int type = rel.getRelationType();
    try {
      if(OPLGeneral.isByDestination(rule)){return specificRelationByDest(thing,rel,type,rule);}
      switch (type) {
        case OpcatConstants.EXHIBITION_RELATION:
          addExhibRel(thing, rel, objName);
          return true;
        case OpcatConstants.INSTANTINATION_RELATION:
          return false;
        case OpcatConstants.AGGREGATION_RELATION:
          addAggregRel(thing, rel, objName);
          return true;
        case OpcatConstants.UNI_DIRECTIONAL_RELATION:
          addUniDirRelSentence(thing, rel);
          return true;
        case OpcatConstants.BI_DIRECTIONAL_RELATION:
          if(rel.getBackwardRelationMeaning().equals("")||rel.getForwardRelationMeaning().equals(""))
            return false;
          addBiDirRelSentence(thing, rel, rule);
          return true;
        default:
          return false;
      }
    }
    catch (Exception e) {
      throw e;
    }
  }

  protected boolean specificRelationByDest(ThingSentenceSet thing,
                                     IRelationEntry rel, int type, int rule)
  throws Exception{
    switch(type){
        case OpcatConstants.SPECIALIZATION_RELATION:
            addInhRelSentence(thing, rel);
            return true;
        case OpcatConstants.INSTANTINATION_RELATION:
            addInstRelSentence(thing, rel);
            return true;
        case OpcatConstants.BI_DIRECTIONAL_RELATION:
              addBiDirRelSentence(thing, rel, rule);
              return true;
        default: return false;
      }
  }



  protected void addDescription(IEntry abstractEntry, ThingSentenceSet thing) {
    String description = abstractEntry.getDescription();
    if (!description.equals("none")) {
      if (abstractEntry instanceof IObjectEntry)
        thing.setObjectDescription(description);
      else
        thing.setProcessDescription(description);
    }
  }

  protected abstract void addAggregRel(ThingSentenceSet thing,
                                       IRelationEntry rel, String objName) throws
      Exception;

  protected abstract void addExhibRel(ThingSentenceSet thing,
                                      IRelationEntry rel, String objName) throws
      Exception;

  protected abstract void addUniDirRelSentence(ThingSentenceSet thing,
                                               IRelationEntry rel) throws
      Exception;


  protected abstract void addInhRelSentence(ThingSentenceSet thing,
                                            IRelationEntry rel) throws
      Exception;
  protected abstract void addInstRelSentence(ThingSentenceSet thing,
                                             IRelationEntry rel)throws
      Exception;


  protected abstract void addBiDirRelSentence(ThingSentenceSet thing,
                                              IRelationEntry rel, int rule) throws
      Exception;
  public boolean ByProc;

}
