package gui.projectStructure;

import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IStateEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmState;

import java.util.Enumeration;


public class StateEntry
    extends ConnectionEdgeEntry
    implements IXStateEntry, IStateEntry {

  private OpmObject parentObject;

  public StateEntry(OpmState pState, OpmObject pObject, OpdProject project) {
    super(pState, project);
    parentObject = pObject;
  }

  public ObjectEntry getParentObject() {
    return (ObjectEntry) myProject.getComponentsStructure().getEntry(
        parentObject.getId());
  }

  public IObjectEntry getParentIObjectEntry() {
    return (IObjectEntry) getParentObject();
  }

  public IXObjectEntry getParentIXObjectEntry() {
    return (IXObjectEntry) getParentObject();
  }

  public long getParentObjectId() {
    return parentObject.getId();
  }

  public boolean isRelated() {
    for (Enumeration e = getInstances(); e.hasMoreElements(); ) {
      StateInstance si = (StateInstance) e.nextElement();
      if (si.isRelated()) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns String representing minimum activation time
   * of this OpmState. This String contains non-negative integer X 7
   * (msec, sec, min, hours, days, months, years) with semi-colons
   * separation.
   *
   */
  public String getMinTime() {
    return ( (OpmState) logicalEntity).getMinTime();
  }

  /**
   * Sets  minimum activation time
   * of this OpmState. This field contains non-negative integer X 7
   * (msec, sec, min, hours, days, months, years) with semi-colons
   * separation.
   *
   */
  public void setMinTime(String minTime) {
    ( (OpmState) logicalEntity).setMinTime(minTime);
  }

  /**
   * Returns String representing maximum activation time
   * of this OpmState. This String contains non-negative integer X 7
   * (msec, sec, min, hours, days, months, years) with semi-colons
   * separation.
   *
   */
  public String getMaxTime() {
    return ( (OpmState) logicalEntity).getMaxTime();
  }

  /**
   * Sets  maximum activation time
   * of this OpmState. This field contains non-negative integer X 7
   * (msec, sec, min, hours, days, months, years) with semi-colons
   * separation.
   *
   */
  public void setMaxTime(String maxTime) {
    ( (OpmState) logicalEntity).setMaxTime(maxTime);
  }

  /**
   * Sets the initialState property of OpmState.
   *
   */
  public void setInitial(boolean initialState) {
    ( (OpmState) logicalEntity).setInitial(initialState);
  }

  /**
   * Returns true if this State is initial.
   *
   */
  public boolean isInitial() {
    return ( (OpmState) logicalEntity).isInitial();
  }

  /**
   * Sets the finalState property of OpmState.
   *
   */
  public void setFinal(boolean finalState) {
    ( (OpmState) logicalEntity).setFinal(finalState);
  }

  /**
   * Returns true if this State is final.
   *
   */
  public boolean isFinal() {
    return ( (OpmState) logicalEntity).isFinal();
  }

  public boolean isDefault() {
    return ( (OpmState) logicalEntity).isDefault();
  }

  public void setDefault(boolean defaultState) {
    ( (OpmState) logicalEntity).setDefault(defaultState);
  }

  public boolean hasVisibleInstancesInOpd(long opdNumber){
    for (Enumeration e = getInstances(); e.hasMoreElements();)
            {
        Instance currInstance = (Instance)e.nextElement();
        if ((currInstance.getKey().getOpdId() == opdNumber)&&
            (currInstance.isVisible()))
        {
            return true;
        }
    }

    return false;


  }

}