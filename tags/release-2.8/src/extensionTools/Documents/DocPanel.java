package extensionTools.Documents;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import exportedAPI.opcatAPI.IInstance;
import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import extensionTools.Documents.UserInterface.DocSel;
import extensionTools.Documents.UserInterface.HandleTemp;


public class DocPanel extends JPanel
{
  ISystem mySys;
  Border border1;
  TitledBorder titledBorder1;
  String[] data = {"Template1", "Template2", "Template3", "Template4","Template5","Template6","Template7","Template8","Template9","Template10"};
  Border border2;
  TitledBorder titledBorder2;
  Border border3;
  TitledBorder titledBorder3;
  Border border4;
  TitledBorder titledBorder4;
  JPanel DocDocPanel = new JPanel();
  JButton DocNew = new JButton();
  JButton TempNew = new JButton();
  GridLayout gridLayout1 = new GridLayout();

	public DocPanel(ISystem sys)
	{
		mySys = sys;
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder1 = new TitledBorder(border1,"First try of UML");

    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder2 = new TitledBorder(border2,"Please select a template:");
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder3 = new TitledBorder(border3,"Handle Document:");
    border4 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    titledBorder4 = new TitledBorder(border4,"Handle Template:");
    this.setLayout(gridLayout1);

    DocDocPanel.setLayout(null);
    DocNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DocNew_actionPerformed(e);
      }
    });
    DocNew.setBounds(new Rectangle(21, 8, 155, 30));
    DocNew.setText("Handle Document      ");
    TempNew.setBounds(new Rectangle(182, 8, 155, 30));
    TempNew.setText("Handle Template");
    TempNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        TempNew_actionPerformed(e);
      }
    });
    this.add(DocDocPanel, null);
    DocDocPanel.add(DocNew, null);
    DocDocPanel.add(TempNew, null);

	}

	class InstanceRepresenter
	{
		private IInstance myInst;
		private ISystemStructure myStruct;
		InstanceRepresenter(IInstance inst, ISystemStructure struct)
		{
			myInst = inst;
			myStruct = struct;
		}
		public String toString()
		{
			return myInst.getIEntry().getName()+ " - " +myStruct.getIOpd(myInst.getKey().getOpdId()).getName();
		}

		public IInstance getIInstance()
		{
			return myInst;
		}
	}


  void DocNew_actionPerformed(ActionEvent e) {
      DocSel new_doc = new DocSel(mySys);
      new_doc.setVisible(true);
  }

  void TempNew_actionPerformed(ActionEvent e) {
      HandleTemp ht=new HandleTemp(mySys);
      ht.setVisible(true);
  }

}