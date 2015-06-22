package gui.opdProject;

import gui.Opcat2;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;


public class OplColorsDialog extends JDialog
{
    private final String sizes[] = {"Smallest", "Smaller", "Medium", "Larger", "Largest"};
    private OplColorScheme colorScheme;
    private OplColorScheme originalScheme;

    JPanel topPanel = new JPanel();
    JPanel colorsPanel = new JPanel();
    TitledBorder titledBorder1;
    JCheckBox disableColors = new JCheckBox();
    JPanel miscPanel = new JPanel();
    Border border1;
    JComboBox sizesBox = new JComboBox();
    JLabel textSizeLabel = new JLabel();
    JButton applyButton = new JButton();
    JButton cancelButton = new JButton();
    JButton okButton = new JButton();
    JLabel oplElementsLabel = new JLabel();
    JScrollPane listScrollPane = new JScrollPane();
    JList elementsList = new JList();
    JLabel attributesLabel = new JLabel();
    JCheckBox boldBox = new JCheckBox();
    JCheckBox italicBox = new JCheckBox();
    JLabel textColorLabel = new JLabel();
    JButton colorButton = new JButton();
    JCheckBox underlinedBox = new JCheckBox();
    JButton defaultButton = new JButton();

    public OplColorsDialog(JFrame owner, String title, OplColorScheme cScheme)
    {
        super(owner, title, true);
        originalScheme = cScheme;
        colorScheme = new OplColorScheme();

        try
        {
            jbInit();
            myInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception
    {
        titledBorder1 = new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153),2),"");
        border1 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
        this.getContentPane().setLayout(null);
        topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        topPanel.setBounds(new Rectangle(8, 8, 370, 231));
        topPanel.setLayout(null);
        colorsPanel.setBorder(titledBorder1);
        colorsPanel.setBounds(new Rectangle(15, 74, 345, 145));
        colorsPanel.setLayout(null);
        titledBorder1.setTitle("Colors");
        titledBorder1.setBorder(BorderFactory.createEtchedBorder());
        disableColors.setText("Disable Colors   ");
        disableColors.setHorizontalTextPosition(SwingConstants.LEADING);
        disableColors.setActionCommand("disableColors");
        disableColors.setBounds(new Rectangle(200, 15, 130, 20));
        disableColors.addActionListener(new OplColorsDialog_disableColors_actionAdapter(this));
        miscPanel.setBorder(border1);
        miscPanel.setBounds(new Rectangle(15, 16, 341, 49));
        miscPanel.setLayout(null);
        sizesBox.setBounds(new Rectangle(102, 14, 79, 22));
        sizesBox.addActionListener(new OplColorsDialog_sizesBox_actionAdapter(this));
        textSizeLabel.setText("Text Size");
        textSizeLabel.setBounds(new Rectangle(37, 15, 60, 20));
        applyButton.setText("Apply");
        applyButton.setBounds(new Rectangle(219, 254, 70, 27));
        applyButton.addActionListener(new OplColorsDialog_applyButton_actionAdapter(this));
        cancelButton.setText("Cancel");
        cancelButton.setBounds(new Rectangle(296, 254, 80, 27));
        cancelButton.addActionListener(new OplColorsDialog_cancelButton_actionAdapter(this));
        okButton.setText("OK");
        okButton.setBounds(new Rectangle(145, 254, 66, 27));
        okButton.addActionListener(new OplColorsDialog_okButton_actionAdapter(this));
        oplElementsLabel.setText("OPL Element :");
        oplElementsLabel.setBounds(new Rectangle(15, 25, 110, 21));
        listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    listScrollPane.setBounds(new Rectangle(16, 48, 115, 80));
        attributesLabel.setText("Attributes :");
        attributesLabel.setBounds(new Rectangle(151, 26, 72, 22));
        boldBox.setText("Bold");
        boldBox.setBounds(new Rectangle(232, 25, 97, 18));
        boldBox.addActionListener(new OplColorsDialog_boldBox_actionAdapter(this));
        italicBox.setText("Italic");
        italicBox.setBounds(new Rectangle(232, 48, 97, 18));
        italicBox.addActionListener(new OplColorsDialog_italicBox_actionAdapter(this));
        textColorLabel.setText("Text Color :");
        textColorLabel.setBounds(new Rectangle(151, 103, 81, 22));
        colorButton.setText("    ");
        colorButton.setBounds(new Rectangle(232, 105, 97, 22));
        colorButton.addActionListener(new OplColorsDialog_colorButton_actionAdapter(this));
        this.setResizable(false);
        elementsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        elementsList.addListSelectionListener(new OplColorsDialog_elementsList_listSelectionAdapter(this));
        underlinedBox.setText("Underlined");
        underlinedBox.setBounds(new Rectangle(232, 71, 97, 18));
        underlinedBox.addActionListener(new OplColorsDialog_underlinedBox_actionAdapter(this));
        defaultButton.setText("Defaults");
        defaultButton.setBounds(new Rectangle(21, 254, 90, 27));
        defaultButton.addActionListener(new OplColorsDialog_defaultButton_actionAdapter(this));
        this.getContentPane().add(topPanel, null);
        topPanel.add(miscPanel, null);
        miscPanel.add(disableColors, null);
        miscPanel.add(textSizeLabel, null);
        miscPanel.add(sizesBox, null);
        topPanel.add(colorsPanel, null);
        colorsPanel.add(listScrollPane, null);
    colorsPanel.add(oplElementsLabel, null);
    colorsPanel.add(underlinedBox, null);
    colorsPanel.add(textColorLabel, null);
    colorsPanel.add(attributesLabel, null);
    colorsPanel.add(boldBox, null);
    colorsPanel.add(italicBox, null);
    colorsPanel.add(colorButton, null);
    listScrollPane.getViewport().add(elementsList, null);
        this.getContentPane().add(defaultButton, null);
    this.getContentPane().add(okButton, null);
    this.getContentPane().add(applyButton, null);
    this.getContentPane().add(cancelButton, null);

        this.setBounds(0, 0, 393, 330);
		this.setLocationRelativeTo(this.getParent());
//		this.setVisible(true);
    }

    private void myInit()
    {
        for(int i = 0; i < sizes.length; i++)
        {
            sizesBox.addItem(sizes[i]);
        }

        colorScheme.getSchemeFrom(originalScheme);

        sizesBox.setSelectedIndex(colorScheme.getTextSize());
        elementsList.setListData(colorScheme.getAttributes());
        elementsList.setSelectedIndex(0);
        disableColors.setSelected(!colorScheme.isColorsEnabled());
        enableColors(colorScheme.isColorsEnabled());

    }

    private void enableColors(boolean enabled)
    {
        colorsPanel.setEnabled(enabled);

        JScrollBar jsb = listScrollPane.getVerticalScrollBar();
        if (jsb!=null)
        {
            jsb.setEnabled(enabled);
        }
        attributesLabel.setEnabled(enabled);
        textColorLabel.setEnabled(enabled);
        oplElementsLabel.setEnabled(enabled);
        elementsList.setEnabled(enabled);
        colorButton.setEnabled(enabled);
        boldBox.setEnabled(enabled);
        italicBox.setEnabled(enabled);
        underlinedBox.setEnabled(enabled);
    }

    void cancelButton_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }

    void disableColors_actionPerformed(ActionEvent e)
    {
        enableColors(!disableColors.isSelected());
        colorScheme.enableColors(!disableColors.isSelected());
    }

    void elementsList_valueChanged(ListSelectionEvent e)
    {
        ColorAttribute att = (ColorAttribute)elementsList.getSelectedValue();
        if (att == null) return;

        colorButton.setBackground(att.getColor());
        boldBox.setSelected(att.isBold());
        italicBox.setSelected(att.isItalic());
        underlinedBox.setSelected(att.isUnderlined());
    }

    void boldBox_actionPerformed(ActionEvent e)
    {
        ColorAttribute att = (ColorAttribute)elementsList.getSelectedValue();
        if (att == null) return;

        att.setBold(boldBox.isSelected());
    }

    void italicBox_actionPerformed(ActionEvent e)
    {
        ColorAttribute att = (ColorAttribute)elementsList.getSelectedValue();
        if (att == null) return;

        att.setItalic(italicBox.isSelected());
    }

    void colorButton_actionPerformed(ActionEvent e)
    {
        ColorAttribute att = (ColorAttribute)elementsList.getSelectedValue();
        if (att == null) return;

        Color newColor = JColorChooser.showDialog(OplColorsDialog.this,
					                            "Choose "+att.toString()+" Color",
					                            colorButton.getBackground());
        if(newColor != null)
        {
            att.setColor(newColor);
            colorButton.setBackground(newColor);
        }

    }

    void okButton_actionPerformed(ActionEvent e)
    {
        originalScheme.getSchemeFrom(colorScheme);
        Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
        this.dispose();
    }

    void applyButton_actionPerformed(ActionEvent e)
    {
        originalScheme.getSchemeFrom(colorScheme);
        Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
    }

    void sizesBox_actionPerformed(ActionEvent e)
    {
        colorScheme.setTextSize(sizesBox.getSelectedIndex());
    }

    void underlinedBox_actionPerformed(ActionEvent e)
    {
        ColorAttribute att = (ColorAttribute)elementsList.getSelectedValue();
        if (att == null) return;

        att.setUnderlined(underlinedBox.isSelected());

    }

    void defaultButton_actionPerformed(ActionEvent e)
    {
        int selIndex = elementsList.getSelectedIndex();

        colorScheme.setDefault();
        sizesBox.setSelectedIndex(colorScheme.getTextSize());
        elementsList.setListData(colorScheme.getAttributes());

        if (selIndex == -1)
        {
            elementsList.setSelectedIndex(0);
        }
        else
        {
            elementsList.setSelectedIndex(selIndex);
        }

        ColorAttribute att = (ColorAttribute)elementsList.getSelectedValue();
        if (att == null) return;

        colorButton.setBackground(att.getColor());
        boldBox.setSelected(att.isBold());
        italicBox.setSelected(att.isItalic());
        underlinedBox.setSelected(att.isUnderlined());

        disableColors.setSelected(false);
        enableColors(true);

    }

}

class OplColorsDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_cancelButton_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelButton_actionPerformed(e);
    }
}

class OplColorsDialog_disableColors_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_disableColors_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.disableColors_actionPerformed(e);
    }
}

class OplColorsDialog_elementsList_listSelectionAdapter implements javax.swing.event.ListSelectionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_elementsList_listSelectionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void valueChanged(ListSelectionEvent e)
    {
        adaptee.elementsList_valueChanged(e);
    }
}

class OplColorsDialog_boldBox_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_boldBox_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.boldBox_actionPerformed(e);
    }
}

class OplColorsDialog_italicBox_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_italicBox_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.italicBox_actionPerformed(e);
    }
}

class OplColorsDialog_colorButton_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_colorButton_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.colorButton_actionPerformed(e);
    }
}

class OplColorsDialog_okButton_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_okButton_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.okButton_actionPerformed(e);
    }
}

class OplColorsDialog_applyButton_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_applyButton_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.applyButton_actionPerformed(e);
    }
}

class OplColorsDialog_sizesBox_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_sizesBox_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.sizesBox_actionPerformed(e);
    }
}

class OplColorsDialog_underlinedBox_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_underlinedBox_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.underlinedBox_actionPerformed(e);
    }
}

class OplColorsDialog_defaultButton_actionAdapter implements java.awt.event.ActionListener
{
    OplColorsDialog adaptee;

    OplColorsDialog_defaultButton_actionAdapter(OplColorsDialog adaptee)
    {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e)
    {
        adaptee.defaultButton_actionPerformed(e);
    }
}
