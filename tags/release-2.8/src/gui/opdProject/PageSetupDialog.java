package gui.opdProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.print.Paper;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



class PageSetupDialog extends JDialog
{
	private JCheckBox projectName = new JCheckBox();
	private JCheckBox printTime = new JCheckBox();
	private JCheckBox authorName = new JCheckBox();
	private JCheckBox pageNum = new JCheckBox();
	private JCheckBox center = new JCheckBox();
	private JCheckBox fitToPage1 = new JCheckBox();
	protected JSlider marginsSlider = new JSlider();
	protected JLabel inchText = new JLabel();
	private JPanel topPanel = new JPanel();
	private JButton okButton = new JButton();
	private JButton cancelButton = new JButton();
	private JPanel jPanel1 = new JPanel();
	private TitledBorder titledBorder1;
	private JPanel jPanel2 = new JPanel();
	private JPanel jPanel3 = new JPanel();
	private PageSetupData printProp;
	private JCheckBox opdName = new JCheckBox();
	private JRadioButton centerView = new JRadioButton();
	private JRadioButton fitToPage = new JRadioButton();
	private ButtonGroup fitOrCenter = new ButtonGroup();
	private JRadioButton noAlignment = new JRadioButton();

	TitledBorder titledBorder2;
	TitledBorder titledBorder3;
	Border border2;
	TitledBorder titledBorder4;
	Border border3;
	TitledBorder titledBorder5;

	public PageSetupDialog(JFrame owner, String title, PageSetupData pp)
	{
		super(owner, title, true);
		printProp = pp;
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception
	{
		titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Margin Width");
		titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Margin Width");
		titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Margin Width");
		border2 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
		titledBorder4 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"System Info");
		border3 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
		titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Printing Alignment");
		projectName.setToolTipText("Print Project Name");
		projectName.setSelected(printProp.isPrjName());
		projectName.setText("System Name");
		projectName.setBounds(new Rectangle(17, 28, 117, 25));
		this.getContentPane().setLayout(null);
		printTime.setBounds(new Rectangle(17, 109, 113, 25));
		printTime.setSelected(printProp.isPrintTime());
		printTime.setText("Printing Time");
		authorName.setBounds(new Rectangle(17, 82, 117, 25));
		authorName.setText("Author Name");
		authorName.setSelected(printProp.isPrjCreator());
		pageNum.setBounds(new Rectangle(17, 136, 115, 25));
		pageNum.setText("Page Number");
		pageNum.setSelected(printProp.isPageNumber());
		center.setSelected(printProp.isCenterView());
		center.setText("Center View");
		center.setBounds(new Rectangle(19, 103, 87, 25));
		center.setVisible(false);
		fitToPage1.setSelected(printProp.isFitToPage());
		fitToPage1.setText("Fit To Page");
		fitToPage1.setBounds(new Rectangle(19, 133, 87, 25));
		fitToPage1.setVisible(false);
//		fitToPage.setEnabled(false);
		marginsSlider.setMajorTickSpacing(8);
		marginsSlider.setMaximum(72);
		marginsSlider.setMinimum(16);
		marginsSlider.setMinorTickSpacing(4);
		marginsSlider.setPaintLabels(true);
		marginsSlider.setPaintTicks(true);
		marginsSlider.createStandardLabels(8);
		marginsSlider.setForeground(Color.black);
		marginsSlider.setToolTipText("Adjust Margins Size");
		marginsSlider.setVerifyInputWhenFocusTarget(false);
		marginsSlider.setBounds(new Rectangle(6, 16, 242, 45));
//		marginsSlider.addMouseMotionListener(new PrintDialog_marginsSlider_mouseMotionAdapter(this));
		marginsSlider.addChangeListener(new SliderChanged());
		marginsSlider.setLayout(null);
		marginsSlider.setValue(printProp.getMarginWidth());
		inchText.setForeground(Color.black);
		inchText.setText(marginsSlider.getValue()+"/72 inch");
		inchText.setBounds(new Rectangle(255, 28, 71, 20));
		topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		topPanel.setMinimumSize(new Dimension(1, 1));
		topPanel.setPreferredSize(new Dimension(1, 1));
		topPanel.setVerifyInputWhenFocusTarget(false);
		topPanel.setBounds(new Rectangle(9, 14, 380, 276));
		topPanel.setLayout(null);
		okButton.setText("OK");
		okButton.setBounds(new Rectangle(209, 303, 84, 26));
		okButton.addActionListener(new PageSetupDialog_okButton_actionAdapter(this));
		cancelButton.setBounds(new Rectangle(301, 303, 84, 26));
		cancelButton.addActionListener(new PageSetupDialog_cancelButton_actionAdapter(this));
		cancelButton.setText("Cancel");
		jPanel1.setBorder(titledBorder4);
		jPanel1.setBounds(new Rectangle(20, 20, 159, 167));
		jPanel1.setLayout(null);
		jPanel2.setBorder(titledBorder5);
		jPanel2.setBounds(new Rectangle(203, 20, 159, 165));
		jPanel2.setLayout(null);
		jPanel3.setBorder(titledBorder1);
		jPanel3.setBounds(new Rectangle(20, 195, 343, 66));
		jPanel3.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
        this.setTitle("");
		this.setResizable(false);
		//this.setTitle("Printing Properties");
		opdName.setText("OPD name");
		opdName.setBounds(new Rectangle(17, 55, 112, 24));
		opdName.setSelected(printProp.isOpdName());
		opdName.addActionListener(new PageSetupDialog_opdName_actionAdapter(this));
		centerView.setSelected(printProp.isCenterView());
		centerView.setText("Center View");
		centerView.setBounds(new Rectangle(18, 63, 107, 24));
		fitToPage.setText("Fit to Page");
		fitToPage.setBounds(new Rectangle(18, 96, 100, 24));
		fitToPage.setSelected(printProp.isFitToPage());
		noAlignment.setText("Do not Align");
		noAlignment.setBounds(new Rectangle(18, 31, 105, 24));
		noAlignment.setSelected(printProp.isDoNotAlign());
		fitOrCenter.add(fitToPage);
		fitOrCenter.add(centerView);
		fitOrCenter.add(noAlignment);
		this.getContentPane().add(topPanel, null);
		topPanel.add(jPanel1, null);
		jPanel1.add(authorName, null);
		jPanel1.add(projectName, null);
		jPanel1.add(opdName, null);
		jPanel1.add(printTime, null);
		jPanel1.add(pageNum, null);
		topPanel.add(jPanel2, null);
		jPanel2.add(center, null);
		jPanel2.add(fitToPage1, null);
		jPanel2.add(centerView, null);
		jPanel2.add(fitToPage, null);
		jPanel2.add(noAlignment, null);
		topPanel.add(jPanel3, null);
		jPanel3.add(marginsSlider, null);
		jPanel3.add(inchText, null);
		this.getContentPane().add(cancelButton, null);
		this.getContentPane().add(okButton, null);

		//this.pack();
		this.setBounds(0, 0, 402, 370);
		this.setLocationRelativeTo(this.getParent());
		this.setVisible(true);
	}


	void marginsSlider_mouseDragged(MouseEvent e)
	{
		inchText.setText(marginsSlider.getValue()+"/72 inch");
	}

	private void _updatePageFormat()
	{
		int marginWidth = marginsSlider.getValue();
		double h = printProp.getWidth();
		double w = printProp.getHeight();
		Paper p = printProp.getPaper();
		p.setImageableArea(marginWidth, marginWidth,
										w-marginWidth*2, h-marginWidth*2);
		printProp.setPaper(p);
		printProp.setMarginWidth(marginWidth);
		//printProp.setCenterView(center.isSelected());
		printProp.setCenterView(centerView.isSelected());
		//printProp.setFitToPage(fitToPage1.isSelected());
		printProp.setFitToPage(fitToPage.isSelected());
		printProp.setDoNotAlign(noAlignment.isSelected());
		printProp.setPageNumber(pageNum.isSelected());
		printProp.setPrintTime(printTime.isSelected());
		printProp.setPrjCreator(authorName.isSelected());
		printProp.setPrjName(projectName.isSelected());
		printProp.setOpdName(opdName.isSelected());
		printProp.setApplyed(true);
		this.dispose();
	}

		Action sliderDraggedAction = new AbstractAction(){
		public void actionPerformed(ActionEvent e){
			(PageSetupDialog.this).inchText.setText(marginsSlider.getValue()+"/72 inch");
		}
	};

	class SliderChanged implements ChangeListener
	{
		public SliderChanged()
		{
			//Empty stub
		}

		public void stateChanged(ChangeEvent e)
		{
			(PageSetupDialog.this).inchText.setText(marginsSlider.getValue()+"/72 inch");
		}

	}

	void opdName_actionPerformed(ActionEvent e) {
		//empty stub
	} 

	void okButton_actionPerformed(ActionEvent e)
	{
		_updatePageFormat();
		this.dispose();
	}

	void cancelButton_actionPerformed(ActionEvent e)
	{
		printProp.setApplyed(false);
		this.dispose();
	}

}

class PrintDialog_marginsSlider_mouseMotionAdapter extends java.awt.event.MouseMotionAdapter
{
	PageSetupDialog adaptee;

	PrintDialog_marginsSlider_mouseMotionAdapter(PageSetupDialog adaptee)
	{
		this.adaptee = adaptee;
	}
	public void mouseDragged(MouseEvent e)
	{
		adaptee.marginsSlider_mouseDragged(e);
	}
}

class PageSetupDialog_opdName_actionAdapter implements java.awt.event.ActionListener
{
	PageSetupDialog adaptee;

	PageSetupDialog_opdName_actionAdapter(PageSetupDialog adaptee)
	{
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e)
	{
		adaptee.opdName_actionPerformed(e);
	}
}

class PageSetupDialog_okButton_actionAdapter implements java.awt.event.ActionListener
{
	PageSetupDialog adaptee;

	PageSetupDialog_okButton_actionAdapter(PageSetupDialog adaptee)
	{
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e)
	{
		adaptee.okButton_actionPerformed(e);
	}
}

class PageSetupDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener
{
	PageSetupDialog adaptee;

	PageSetupDialog_cancelButton_actionAdapter(PageSetupDialog adaptee)
	{
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e)
	{
		adaptee.cancelButton_actionPerformed(e);
	}
}