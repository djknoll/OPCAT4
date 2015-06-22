package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;
import gui.images.standard.StandardImages;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.objectprocess.Client.TeamMember;

//Class for User Data Popup Menu
public class UserDataPopupMenu extends JPopupMenu {
      protected TeamMember myTeamMember;
      protected Opcat2 myOpcat2;

      public UserDataPopupMenu(TeamMember teamMember,
                               Opcat2 opcat2) {
        super();
        myTeamMember=teamMember;
        myOpcat2 = opcat2;

        this.add(addWorkgroupAction);
        this.add(  new JSeparator());
        this.add(userDetailsAction);
        this.add(  new JSeparator());
        this.add(refreshAction);

        }
    Action addWorkgroupAction = new AbstractAction("New Workgroup", StandardImages.NEW){
      public void actionPerformed(ActionEvent e){
        AddWorkgroupDialog addWorkgroupDialog = new AddWorkgroupDialog(myTeamMember,myOpcat2);
     }
    };


    Action userDetailsAction = new AbstractAction("Show/Edit Details", OPCATeamImages.DOCUMENT){
      public void actionPerformed(ActionEvent e){
        UserValueDialog userDataDialog = new UserValueDialog(myTeamMember);
      }
    };

    Action refreshAction = new AbstractAction("Refresh Control Panel", OPCATeamImages.CONTROL_PANEL){
      public void actionPerformed(ActionEvent e){
        myOpcat2.getRepository().refreshOPCATeamControlPanel();
      }
    };

} //end of class UserDataPopUpMenu
