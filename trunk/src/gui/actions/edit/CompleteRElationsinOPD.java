package gui.actions.edit;

import gui.Opcat2;
import gui.opdProject.Opd;
import gui.projectStructure.ConnectionEdgeEntry;
import org.w3c.dom.events.EventException;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CompleteRElationsinOPD extends EditAction {

    private static final long serialVersionUID = 1L;

    /**
     * @param name
     * @param icon
     */

    private boolean addMissingThings = false;
    private int relationType;

    public CompleteRElationsinOPD(String name, Icon icon, boolean addMissingThings, int relationType) {
        super(name, icon);
        this.addMissingThings = addMissingThings;
        this.relationType = relationType;
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


            completeLinks(true);
            completeLinks(false);

        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        } finally {
            Opcat2.endWait();
        }
    }

    private void completeLinks(boolean isSource) {

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

        long opdID = edit.getCurrentProject().getCurrentOpd().getOpdId();

        Opd opd = edit.getCurrentProject().getCurrentOpd();

        ArrayList<ConnectionEdgeEntry> entries = edit.getCurrentProject()
                .getSystemStructure().getConnetionEdgeEntriesInOpd(opdID);

        for (ConnectionEdgeEntry entry : entries) {

            entry.completeRealtions(opd, isSource, addMissingThings, null, relationType);

        }
    }
}
