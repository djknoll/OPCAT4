package gui.actions.edit;

import gui.Opcat2;
import gui.controls.EditControl;
import gui.opdProject.IllegalPassException;
import gui.opdProject.StateMachine;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.w3c.dom.events.EventException;

/**
 * Carries out a copy operation.
 * 
 * @author Eran Toch
 */
public class CutAction extends EditAction {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	public CutAction(String name, Icon icon) {
		super(name, icon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		try {
			super.actionPerformed(arg0);
		} catch (EventException e) {
			JOptionPane.showMessageDialog(this.gui.getFrame(), e.getMessage()
					.toString(), "Message", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String msg = this.edit.getCurrentProject().cut(
				this.edit.getCurrentProject().getCurrentOpd(), EditControl.CUT);
		if (msg != null) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(), msg,
					"Opcat2 - Message", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			try {
				StateMachine.go(StateMachine.EDIT_CUT, 1);
			} catch (IllegalPassException e) {
				// can not enter into CUT mode have to cancel the cut and inform
				// user
				this.edit.undo();
				msg = "Can not CUT when "
						+ StateMachine.getStringEntepretation(StateMachine
								.getCurrentState()) + " is in progress";
				JOptionPane.showMessageDialog(this.gui.getFrame(), msg, "Message",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
