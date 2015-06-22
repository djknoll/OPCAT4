package gui.checkModule;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

class CheckOptionsButton extends JPanel implements ActionListener
{
	JCheckBox allow = new JCheckBox("Allow connection", false);
	JButton advanced = new JButton("Advanced");
	CheckOptionsButton()
	{
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
		this.setMinimumSize(new Dimension(110, 50));
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(110, 50));
		this.setLayout(null);
		advanced.setBounds(new Rectangle(66, 19, 31, 19));
		advanced.setToolTipText("Configure");
		allow.setToolTipText("Allow/Disallow this type of connection");
		allow.setText("Allow");
		allow.setBounds(new Rectangle(6, 17, 57, 22));
		this.add(allow);
		this.add(advanced);
		if(!allow.isSelected())
		{
			advanced.setEnabled(false);
		}
		allow.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(allow))
		{
			if(allow.isSelected())
			{
				advanced.setEnabled(true);
			}
			else
			{
				advanced.setEnabled(false);
			}
		}
	}

//	public static void main(String[] args){
//		JFrame w = new JFrame();
//		w.getContentPane().add(new CheckOptionsButton());
//		w.show();
//	}

}