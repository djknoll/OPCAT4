package gui.opdGraphics.popups;

import gui.projectStructure.OrInstance;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;



public class OrPopup  extends JPopupMenu
{
  private int x;
  private int y;
  private OrInstance  myOr;

	public OrPopup(OrInstance myOr, int x, int y)
	{
		super();
		this.x=x;
		this.y=y;
		this.myOr = myOr;

		ButtonGroup myGroup = new ButtonGroup();
		JMenuItem temp;

		if (myOr.isOr())
		{
			temp = new JRadioButtonMenuItem("XOR", false);
		}
		else
		{
			temp = new JRadioButtonMenuItem("XOR", true);
		}

		temp.addActionListener(ChangeAction);
		myGroup.add(temp);
		add(temp);
		if (myOr.isOr())
		{
			temp = new JRadioButtonMenuItem("OR", true);
		}
		else
		{
			temp = new JRadioButtonMenuItem("OR", false);
		}

		temp.addActionListener(ChangeAction);
		myGroup.add(temp);
		add(temp);

	}

	ActionListener ChangeAction = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			String command = e.getActionCommand();

			if (command.equals("XOR"))
			{
				myOr.setOr(false);
			}
			else
			{
				myOr.setOr(true);
			}

            myOr.update();
		}
	};

}