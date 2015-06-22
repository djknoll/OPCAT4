package gui.projectStructure;

import java.awt.Color;
import java.awt.Container;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IInstance;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;

/**
 * <p>The base class for all kinds of graphical instances of all OPM entities.
     * Instances of some entities are compound from several graphical elements so we
 * have information about all of them.
 * <p>Also this class contains the information to which OPD this Instance belongs.
     * If this Instance graphically belongs to some other object (Zooming in) we hold
 * information about its parent.
 *
 * @version	2.0
 * @author		Stanislav Obydionnov
 * @author		Yevgeny   Yaroker
 *
 */

public abstract class Instance
    implements IXInstance, IInstance {
  protected OpdProject myProject;
  private OpdThing parent;
  protected OpdKey key;
  protected boolean selected;



  /**
   * Creates Instance with specified  pKey.
   *
   * @param key OpdKey - key of graphical instance of some entity.
   */

  public Instance(OpdKey pKey, OpdProject project) {
    this.parent = null;
    this.key = pKey;
    this.myProject = project;
  }

  /**
   * Returns OpdThing which is graphical parent(container) of this Instance(Zooming in).
   * null returned if this Instance has no graphical parent.
   *
   */

  public OpdThing getParent() {
    return this.parent;
  }

  /**
       * Sets pParent to be a graphical parent(container) of this Instance(Zooming in).
   *
   */

  public void setParent(OpdThing pParent) {
    this.parent = pParent;
  }

  /**
   * Retruns OpdKey of this Instance.
   *
   */

  public OpdKey getKey() {
    return this.key;
  }

  public Entry getEntry() {
    return this.myProject.getComponentsStructure().getEntry(this.getLogicalId());
  }

  public IXEntry getIXEntry() {
    return  this.getEntry();
  }

  public IEntry getIEntry() {
    return this.getEntry();
  }

  public Opd getOpd(){
    long opdNum = this.key.getOpdId();
    return this.myProject.getComponentsStructure().getOpd(opdNum);
  }

  protected void copyPropsFrom(Instance origin) {
    this.setBackgroundColor(origin.getBackgroundColor());
    this.setBorderColor(origin.getBorderColor());
    this.setTextColor(origin.getTextColor());
  }

  public boolean isSelected() {
    return this.selected;
  }

  public boolean equals(Object obj) {
    if (! (obj instanceof Instance)) {
      return false;
    }

    Instance tempInstance = (Instance) obj;

    return this.getKey().equals(tempInstance.getKey());
  }

  public abstract long getLogicalId();

  public abstract Color getBackgroundColor();

  public abstract void setBackgroundColor(Color backgroundColor);

  public abstract Color getBorderColor();

  public abstract void setBorderColor(Color borderColor);

  public abstract Color getTextColor();

  public abstract void setTextColor(Color textColor);

  public abstract void setSelected(boolean isSelected);

  public abstract void setVisible(boolean isVisible);

  public abstract boolean isVisible();

  public abstract void removeFromContainer();

  public abstract void add2Container(Container cn);

  public abstract void update();

  //public abstract void autoArrange();

  public abstract BaseGraphicComponent[] getGraphicComponents();


}