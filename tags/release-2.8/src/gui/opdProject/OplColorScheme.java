package gui.opdProject;

import java.awt.Color;

public class OplColorScheme {

  public static final int DEFAULT_STYLE = 0;
  public static final int OBJECT_STYLE = 1;
  public static final int PROCESS_STYLE = 2;
  public static final int STATE_STYLE = 3;
  public static final int CARDINALITY_STYLE = 4;
  public static final int TAG_STYLE = 5;
  //DIZZA colors
  public static final int NOTE_STYLE = 6;
  public static final int PROMPT_STYLE = 7;
  public static final int MSG_STYLE = 8;
  private int OPLColors = 6;
  private int TeamColors = 3;

//  private TreeMap attributes;
  private ColorAttribute[] attributes = new ColorAttribute[OPLColors+TeamColors];
  private int size;
  private boolean enableColors;

  public OplColorScheme() {
    setDefault();
  }

//  public void getSchemeFrom(OplColorScheme origin) {
//    Object[] keySet = attributes.keySet().toArray();
//
//    for (int i = 0; i < keySet.length; i++) {
//      ColorAttribute att = (ColorAttribute) origin.getAttribute( ( (Integer)
//          keySet[i]).intValue());
//      attributes.put(keySet[i], att.clone());
//    }
//
//    enableColors(origin.isColorsEnabled());
//    setTextSize(origin.getTextSize());
//  }

  public void getSchemeFrom(OplColorScheme origin) {

    for (int i = 0; i < attributes.length; i++) {
      attributes[i] = origin.getAttribute(i);
    }

    enableColors(origin.isColorsEnabled());
    setTextSize(origin.getTextSize());
  }

  public void setDefault() {
    putAttribute(DEFAULT_STYLE,
                 new ColorAttribute("Reserved Words", Color.black, false, false, false));
    putAttribute(OplColorScheme.OBJECT_STYLE,
                 new ColorAttribute("Objects", new Color(0, 109, 0), false, false, false));
    putAttribute(PROCESS_STYLE,
                 new ColorAttribute("Processes", new Color(0, 0, 120), false, false, false));
//        putAttribute(General.STATE_STYLE, new ColorAttribute("States", new Color(125,0,125), false, false, false));
    putAttribute(STATE_STYLE,
                 new ColorAttribute("States", new Color(91, 91, 0), false, false, false));
    putAttribute(CARDINALITY_STYLE,
                 new ColorAttribute("Cardinalities", Color.black, false, false, false));
    putAttribute(TAG_STYLE, new ColorAttribute("Tags", Color.black, false, false, false));
//OPCATeam
    Color greenColor = new Color(0,110,0);
    Color blueColor = new Color(0,102,255);
    putAttribute(NOTE_STYLE, new ColorAttribute("Notes", Color.red, false, false, false));
    putAttribute(PROMPT_STYLE, new ColorAttribute("Prompt", blueColor, false, false, false));
    putAttribute(MSG_STYLE, new ColorAttribute("Message", greenColor, false, false, false));
    enableColors = true;
    size = 2;
  }

  public void setTextSize(int newSize) {
    this.size = newSize;
  }

  public int getTextSize() {
    return size;
  }

  public boolean isColorsEnabled() {
    return enableColors;
  }

  public void enableColors(boolean enabled) {
    enableColors = enabled;
  }

  public void putAttribute(int style, ColorAttribute attribute) {
    attributes[style] = attribute;
  }

  public ColorAttribute getAttribute(int style) {
    return attributes[style];
  }

  public ColorAttribute[] getAttributes() {
    ColorAttribute[] tmpAttributes = new ColorAttribute[OPLColors];
    for(int i=0; i<OPLColors;i++){
      tmpAttributes[i] = attributes[i];
    }
    return tmpAttributes;
  }

}