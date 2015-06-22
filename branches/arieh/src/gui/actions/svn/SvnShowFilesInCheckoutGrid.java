package gui.actions.svn;

import gui.Opcat2;
import modelControl.gui.OpcatCheckoutGrid;
import org.tmatesoft.svn.core.SVNURL;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SvnShowFilesInCheckoutGrid extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private SVNURL url;

    public SvnShowFilesInCheckoutGrid(SVNURL url, String name, Icon icon) {
        super(name, icon);
        this.url = url;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        Opcat2.getGlassPane().setVisible(true);
        Opcat2.getGlassPane().start();

        try {
            OpcatCheckoutGrid grid = new OpcatCheckoutGrid(url);
            grid.show();

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }

        Opcat2.getGlassPane().setVisible(false);
        Opcat2.getGlassPane().stop();

    }

}
