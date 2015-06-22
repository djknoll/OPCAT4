package gui.actions.edit;

import gui.Opcat2;
import gui.opdProject.Opd;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.ConnectionEdgeInstance;
import org.w3c.dom.events.EventException;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CompleteLinksForConnectionEdgeEntry extends EditAction {
    private static final long serialVersionUID = 1L;

    /**
     * @param name
     * @param icon
     */
    ConnectionEdgeEntry myEntry;
    ConnectionEdgeInstance myInstance;
    boolean addMissingThings = false;

    public CompleteLinksForConnectionEdgeEntry(String name, Icon icon,
                                               ConnectionEdgeInstance instance, boolean addMissingThings) {
        super(name, icon);
        myEntry = (ConnectionEdgeEntry) instance.getEntry();
        myInstance = instance;
        this.addMissingThings = addMissingThings;
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
        try {
            Opcat2.startWait();


            completeLinks(true, myInstance);
            completeLinks(false, myInstance);

        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        } finally {
            Opcat2.endWait();
        }
    }

    private void completeLinks(boolean isSource, ConnectionEdgeInstance instance) {

        /**
         * get Things in OPD and connect all missing links.
         *
         */
        /**
         * Algorithem - go over all the things in the Current OPD get all source
         * links and destination links. go over the things connected to those
         * links see if they are in this OPD. if yes then connect with the
         * connection if the link is not in the OPD.
         */

        Opd opd = edit.getCurrentProject().getCurrentOpd();

        myEntry.completeLinks(opd, isSource, addMissingThings, instance);

    }
}
