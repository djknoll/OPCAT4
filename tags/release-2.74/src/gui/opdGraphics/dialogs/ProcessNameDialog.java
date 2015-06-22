package gui.opdGraphics.dialogs;

import gui.Opcat2;
import gui.opdGraphics.GraphicsUtils;
import gui.opdProject.OpdProject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is a dialog box for <code>ProcessPropertiesDialog</code>, allowing
 * the user to edit the process name if it does not end with "ing".
 * The dialog box allow the user to skip the message the next time the situation
 * occurs.
 * @author Eran Toch
 */
public class ProcessNameDialog extends JDialog implements ComponentListener
{
	private JPanel mess, p1; // tmp panels
	private JButton okButton, cancelButton;
	private JLabel message;
	private JCheckBox persistant;
	private int returnValue = 0;

	public final int CANCEL = 0;
	public final int OK = 1;
	public final int OK_AND_DONT_ASK_AGAIN = 2;
	/**
	 *  Constructor:
	 *  @param <code>parent</code> -- parent frame, Opcat2 application window or <code>null</code>
	 *  @param <code>pState OpdState</code> -- the OPD State to show properties dialog for.
	 */
	public ProcessNameDialog(OpdProject project, String name)
	{
		super(Opcat2.getFrame(), "Process Name", true);
		this.addComponentListener(this);

		Container baseCont = getContentPane();
		baseCont.setLayout(new BorderLayout(0, 0));

		// Building the message (and checkbox) panel
		mess = new JPanel();
		mess.setLayout(new BoxLayout(mess, BoxLayout.Y_AXIS));
		mess.add(Box.createRigidArea(new Dimension(0,10)));
		String messageText = "If the process name does not end with \"ing\" (such as Manufacturing), \nthe suffix\n";
		JLabel m1 = new JLabel(messageText);
		String messageText2 = "\"Process\" will be added to it in the OPL, so it will be called \""+
	    GraphicsUtils.capitalizeFirstLetters(name.trim())+" Process\"";
		JLabel m2 = new JLabel(messageText2);
		mess.add(m1);
		mess.add(m2);
		mess.add(Box.createRigidArea(new Dimension(0,5)));
		persistant = new JCheckBox("Do not show this message in the future");
		persistant.setSelected(true);
		mess.add(persistant);
		mess.add(Box.createRigidArea(new Dimension(0,5)));

		//Building the buttons panel
		p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(Box.createHorizontalGlue());
		//Building cancel button
		cancelButton = new JButton("Edit Name");
		cancelButton.addActionListener(new CancelListener());
		p1.add(cancelButton);
		p1.add(Box.createRigidArea(new Dimension(10, 0)));

		//Building OK button
		okButton = new JButton("OK");
		okButton.addActionListener(new OkListener());
		getRootPane().setDefaultButton(okButton);
		p1.add(okButton);

		//Building a basic panel that holds the other two
		JPanel space = new JPanel(new BorderLayout(0, 0));
		space.add(mess, BorderLayout.CENTER);
		space.add(p1, BorderLayout.SOUTH);

		//Building a panel for the spaces to the right and to the left
		JPanel xlay = new JPanel();
		xlay.setLayout(new BoxLayout(xlay, BoxLayout.X_AXIS));
		xlay.add(Box.createRigidArea(new Dimension(10,0)));
		xlay.add(space);
		xlay.add(Box.createRigidArea(new Dimension(10,0)));

		//Building a panel for the spaces to the top and to the buttom
		JPanel ylay = new JPanel();
		ylay.setLayout(new BoxLayout(ylay, BoxLayout.Y_AXIS));
		ylay.add(Box.createRigidArea(new Dimension(0,10)));
		ylay.add(xlay);
		ylay.add(Box.createRigidArea(new Dimension(0,10)));

		//Adding all of it to the content pane
		baseCont.add(ylay, BorderLayout.CENTER);
		this.pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		int fX = Opcat2.getFrame().getX();
		int fY = Opcat2.getFrame().getY();
		int pWidth = Opcat2.getFrame().getWidth();
		int pHeight = Opcat2.getFrame().getHeight();

		setLocation(fX + Math.abs(pWidth/2-getWidth()/2), fY + Math.abs(pHeight/2-getHeight()/2));
		this.setResizable(false);
	}

	/**
	 * Showing the dialog, and returning the output
	 * @return <code>ProcessNameDialog.CANCEL</code> if the user choose to edit
	 * the name again, <code>ProcessNameDialog.OK</code> if the user choose to
	 * let the name be and to repeat the message the next time.
	 * <code>ProcessNameDialog.OK_AND_DONT_ASK_AGAIN</code> if the user choose to
	 * let the name be and not to show it the next time. The last one is the
	 * default.
	 */
	public int showDialog()
	{
		this.setVisible(true);
		return returnValue;
	}

	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e){}
	public void componentMoved(ComponentEvent e){}
	public void componentResized(ComponentEvent e){}

	/**
	 * Action Listener for OK. Checks if the user wanted the message to be shown
	 * the next time or not, and return the value accodingly. See <code>showDialog</code>
	 * to see the details.
	 */
	class OkListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (persistant.isSelected()) {
				returnValue = OK_AND_DONT_ASK_AGAIN;
			}
			else {
				returnValue = OK;
			}
			(ProcessNameDialog.this).dispose();
			return;
		}
	}

	/**
	 * Action listener for Cancel.
	 */
	class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			returnValue = CANCEL;
			(ProcessNameDialog.this).dispose();
		}
	}
}



