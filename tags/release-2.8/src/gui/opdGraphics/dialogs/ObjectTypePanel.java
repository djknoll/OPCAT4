package gui.opdGraphics.dialogs;

import exportedAPI.OpcatConstants;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmInstantination;
import gui.opmEntities.OpmObject;
import gui.projectStructure.Entry;
import gui.projectStructure.MainStructure;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ThingEntry;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;


public class ObjectTypePanel
    extends JPanel {
  private OpdProject myProject;
  private ThingEntry myEntry;

  private final String basic[] = {
      "Boolean",
      "char",
      "short",
      "integer",
      "unsigned integer",
      "long",
      "float",
      "double"};

  private final String compound[] = {
      "char[]",
      "date",
      "time"};

  private TreeMap sorted = new TreeMap(); //structure to hold the custom types

  private MyComboBox typeCombo = new MyComboBox();
  private JTextField enumText = new JTextField("");
  private JTextField charText = new JTextField("50");
  private Component strut = Box.createHorizontalStrut(200);

  private JRadioButton basicTypes = new JRadioButton("Basic Types", true);
  private JRadioButton compoundTypes = new JRadioButton("Advanced Types");
  private JRadioButton customTypes = new JRadioButton("Custom Types");
  private ButtonGroup types = new ButtonGroup();
  private JLabel label = new JLabel();
  private MyCheckBox definition = new MyCheckBox("Compound Object");

  private final static int MAX_LENGTH = 256;
  private final static int MIN_LENGTH = 0;

  public ObjectTypePanel(ThingEntry pEntry, OpdProject prj) {
    myEntry = pEntry;
    myProject = prj;
    try {
      _jbInit();
      //reuseComment
      if (pEntry.isLocked())
        lockForEdit();
      //endReuseComment
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    setVisible(true);
  }

  private void _jbInit() throws Exception {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.
        createTitledBorder(BorderFactory.createEtchedBorder(), " Object Type "),
        BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    enumText.setPreferredSize(new Dimension(140, 22));
    enumText.setMaximumSize(new Dimension(140, 22));
    enumText.setVisible(false);
    enumText.setToolTipText(
        "Should be a coma separated strings. All white spaces will be ommited");

    charText.setPreferredSize(new Dimension(140, 22));
    charText.setMaximumSize(new Dimension(140, 22));
    charText.setVisible(false);
    charText.setToolTipText("String length 0 or more characters");

    label.setPreferredSize(new Dimension(60, 22));
    label.setMaximumSize(new Dimension(60, 22));
    label.setVisible(false);

    typeCombo.setRenderer(new ComboBoxRenderer());
    basicTypes.setActionCommand("basic");
    basicTypes.addActionListener(typeCombo);
    types.add(basicTypes);
    compoundTypes.setActionCommand("compound");
    compoundTypes.addActionListener(typeCombo);
    types.add(compoundTypes);
    customTypes.setActionCommand("custom");
    customTypes.addActionListener(typeCombo);
    types.add(customTypes);
    _initContol();

    typeCombo.addActionListener(typeCombo);
    definition.addActionListener(definition);

    JPanel p = new JPanel();
    p.setLayout(new FlowLayout(FlowLayout.LEFT));
    p.add(definition);
    this.add(p);
    this.add(Box.createVerticalStrut(5));

    p = new JPanel();
    p.setLayout(new FlowLayout(FlowLayout.LEFT));
    p.add(Box.createHorizontalStrut(10));
    p.add(basicTypes);
    p.add(compoundTypes);
    p.add(customTypes);
    this.add(p);
    this.add(Box.createVerticalStrut(5));

    p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
    p.add(Box.createHorizontalStrut(20));
    p.add(typeCombo);
    p.add(Box.createHorizontalStrut(10));
    p.add(strut);
    p.add(label);
    p.add(enumText);
    p.add(charText);
    this.add(p);

    _setControlData();
  }

  private boolean _checkCharVectorType() {
    int val;
    try {
      val = Integer.parseInt(charText.getText());
    }
    catch (NumberFormatException nfe) {
      return false;
    }
    if (val < 0) {
      return false;
    }
    return true;
  }

  private boolean _checkEnumerationType() {
    StringTokenizer tokens = new StringTokenizer(enumText.getText(), ",");
    String trimmed = new String();
    String tmp;
    for (; tokens.hasMoreTokens(); ) {
      tmp = tokens.nextToken();
      tmp = tmp.trim();
      if (!trimmed.equals("")) {
        trimmed += ",";
      }
      trimmed += tmp;
    }
    enumText.setText(trimmed);
    return true;
  }

  public String getType() {
    if (definition.isSelected()) {
      return "";
    }

    if ( (typeCombo.getSelectedItem().toString()).equals("char[]")) {
      if (!_checkCharVectorType()) {
        return null;
      }
      return "char[" + charText.getText() + "]";
    }

    if ( (typeCombo.getSelectedItem().toString()).equals("enumeration")) {
      if (!_checkEnumerationType()) {
        return null;
      }
      return "enumeration{" + enumText.getText() + "}";
    }

    return typeCombo.getSelectedItem().toString();
  }

  public long getTypeObjectId() {
    if (customTypes.isSelected()) {
      return ( (OpmObject) typeCombo.getSelectedItem()).getId();
    }
    return -1;
  }

  private class ComboBoxRenderer
      extends JLabel
      implements ListCellRenderer {
    public ComboBoxRenderer() {
      setOpaque(true);
      setHorizontalAlignment(LEFT);
      setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
      //setFont(getFont().deriveFont(Font.PLAIN));
      if (value == null) {
        return this;
      }
      String txt = value.toString();

      if (isSelected) {
        setBackground(list.getSelectionBackground());
        setForeground(list.getSelectionForeground());
      }
      else {
        setBackground(list.getBackground());
        setForeground(list.getForeground());
      }

      setText(txt);
      return this;
    }
  };

  private void _initContol() {
    MainStructure ms = myProject.getComponentsStructure();

    for (Enumeration els = ms.getElements(); els.hasMoreElements(); ) {
      Entry ent = (Entry) els.nextElement();
      if (ent instanceof ObjectEntry && ent != myEntry) {
        sorted.put(ent.getLogicalEntity().toString(),
                   ent.getLogicalEntity() /*.toString()*/);
      }
    }

    if (sorted.values().size() <= 0) {
      customTypes.setEnabled(false);
    }
  }

  /**************************************************************************/
  //
  /**************************************************************************/
  private class MyComboBox
      extends JComboBox {
    public void actionPerformed(ActionEvent e) {
      Component eventSource = (Component) e.getSource();

      if (eventSource instanceof JComboBox) { // changing combo selection
        _showHideTextBox();
      }

      if (eventSource instanceof JRadioButton) { // changing type of types selection
        String actCmd = e.getActionCommand();
        _switchComboContence(actCmd);
      }
    }
  };
  /**************************************************************************/

  /**************************************************************************/
  //
  /**************************************************************************/
  private class MyCheckBox
      extends JCheckBox
      implements ActionListener {
    MyCheckBox(String s) {
      super(s);
    }

    public void actionPerformed(ActionEvent e) {
      _enableControl(!isSelected());
      if (typeCombo.getSelectedItem() == null) {
        for (int i = 0; i < basic.length; i++) {
          typeCombo.addItem(basic[i]);
        }
      }
    }
  }

  /**************************************************************************/

  private void _showHideTextBox() {
    Object o = typeCombo.getSelectedItem();
    if (o != null) {
      String selItem = o.toString();
      if (selItem.equals("char[]")) {
        strut.setVisible(false);
        enumText.setVisible(false);
        label.setText("  Length:");
        label.setVisible(true);
        charText.setVisible(true);
      }
      else if (selItem.equals("enumeration")) {
        strut.setVisible(false);
        charText.setVisible(false);
        label.setText("  Values:");
        label.setVisible(true);
        enumText.setVisible(true);
      }
      else {
        charText.setVisible(false);
        enumText.setVisible(false);
        label.setVisible(false);
        strut.setVisible(true);
      }
    }
  }

  private void _enableControl(boolean b) {
    if (b) {
      typeCombo.setEnabled(true);
      charText.setEnabled(true);
      enumText.setEnabled(true);
      basicTypes.setEnabled(true);
      compoundTypes.setEnabled(true);
      if (sorted.size() > 0) {
        customTypes.setEnabled(true);
      }
      label.setEnabled(true);
    }

    else {
      typeCombo.setEnabled(false);
      enumText.setEnabled(false);
      charText.setEnabled(false);
      basicTypes.setEnabled(false);
      compoundTypes.setEnabled(false);
      customTypes.setEnabled(false);
      label.setEnabled(false);
    }
  }

  private void _setControlData() {
    Enumeration locenum = myEntry.getDestinationRelations(OpcatConstants.
        INSTANTINATION_RELATION);
    if (locenum.hasMoreElements()) {
      OpmInstantination opmRel = null;
      try {
        opmRel = (OpmInstantination) locenum.nextElement();
      }
      catch (ClassCastException cce) {
        cce.printStackTrace(System.err);
      }
      //long opmRel.getSourceId();
      ObjectEntry source = (ObjectEntry) myProject.getComponentsStructure().
          getSourceEntry(opmRel);
      customTypes.setSelected(true);
      _switchComboContence("custom");
      typeCombo.setSelectedItem(source.getLogicalEntity());
      definition.setEnabled(false);
      _enableControl(false);
      return;
    }

    String typeString = ( (OpmObject) myEntry.getLogicalEntity()).getType();

    if (typeString == null || typeString.equals("")) {
      definition.setSelected(true);
      _enableControl(false);
      return;
    }
    _enableControl(true);

    // if basic type
    for (int i = 0; i < basic.length; i++) {
      if (typeString.equals(basic[i])) {
        basicTypes.setSelected(true);
        _switchComboContence("basic");
        typeCombo.setSelectedItem(typeString);
        return;
      }
    }

    // compound types expanded
    if (typeString.startsWith("char[")) {
      compoundTypes.setSelected(true);
      _switchComboContence("compound");
      typeCombo.setSelectedItem("char[]");
      charText.setText(typeString.substring(5, typeString.length() - 1));
      return;
    }

    if (typeString.startsWith("enumeration")) {
      compoundTypes.setSelected(true);
      _switchComboContence("compound");
      typeCombo.setSelectedItem("enumeration");
      enumText.setText(typeString.substring(12, typeString.length() - 1));
      return;
    }

    if (typeString.equals("date")) {
      compoundTypes.setSelected(true);
      _switchComboContence("compound");
      typeCombo.setSelectedItem("date");
      return;
    }

    if (typeString.equals("time")) {
      compoundTypes.setSelected(true);
      _switchComboContence("compound");
      typeCombo.setSelectedItem("time");
      return;
    }

    // custom types
    for (Iterator iter = sorted.values().iterator(); iter.hasNext(); ) {
      OpmObject lEntity = (OpmObject) iter.next();
      if (lEntity.getId() ==
          ( (OpmObject) myEntry.getLogicalEntity()).getTypeOriginId()) {
        customTypes.setSelected(true);
        _switchComboContence("custom");
        typeCombo.setSelectedItem(lEntity);
        return;
      }
    }
    JOptionPane.showMessageDialog(this,
        "Internal error in ObjectTypePanel._setControlData()\nPlease contact developers",
                                  "Opcat2 - ERROR", JOptionPane.ERROR_MESSAGE);
    return;
  }

  private void _switchComboContence(String actCmd) {
    if (actCmd.equals("basic")) {
      typeCombo.removeAllItems();
      for (int i = 0; i < basic.length; i++) {
        typeCombo.addItem(basic[i]);
      }
    }

    if (actCmd.equals("compound")) {
      typeCombo.removeAllItems();
      for (int i = 0; i < compound.length; i++) {
        typeCombo.addItem(compound[i]);
      }
    }

    if (actCmd.equals("custom")) {
      typeCombo.removeAllItems();
      for (Iterator iter = sorted.values().iterator(); iter.hasNext(); ) {
        typeCombo.addItem( (OpmObject) iter.next());
      }
    }
    _showHideTextBox();
  }
  //reuseComment
  public void lockForEdit()
  {
    typeCombo.setEditable(false);
    enumText.setEditable(false);
    charText.setEditable(false);
    basicTypes.setEnabled(false);
    compoundTypes.setEnabled(false);
    customTypes.setEnabled(false);
    definition.setEnabled(false);
  }
//endReuseComment

}