package gui.actions.file;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.opdProject.StateMachine;
import gui.util.CustomFileFilter;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JFileChooser;

/**
 * Loads a project from an OPX file, allowing the user to chose the file. Updates 
 * last used files list, and recieves and updates the last directory.
 * 
 * @author Eran Toch
 * @see	gui.controls.FileControl#loadOpxFile()
 */
public class LoadOpxAction extends FileAction {

    public LoadOpxAction(String name, Icon icon) {
        super(name, icon);
    }

    public void actionPerformed(ActionEvent arg0) {
        if (!fileControl.handleOpenedSystem()) {
            return;
        }
        Thread runner = new Thread() {
            public void run() {

                JFileChooser myFileChooser = new JFileChooser();
                myFileChooser.setSelectedFile(new File(""));
                myFileChooser.resetChoosableFileFilters();

                //Setting the last directory
                String ld = fileControl.getLastDirectory();
                if (!ld.equals("")) {
                    myFileChooser.setCurrentDirectory(new File(ld));
                }
                String[] exts = { "opx", "opz" };
                CustomFileFilter myFilter = new CustomFileFilter(exts,
                        "Opcat2 Files");
                myFileChooser.addChoosableFileFilter(myFilter);

                int retVal = myFileChooser
                        .showDialog(Opcat2.getFrame(), "Load System");

                if (retVal != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                ld = myFileChooser.getSelectedFile().getParent();
                fileControl.setLastDirectory(ld);
                StateMachine.setWaiting(true);
                GuiControl.getInstance().setCursor4All(Cursor.WAIT_CURSOR);

                String fileName = myFileChooser.getSelectedFile().getPath();
                fileControl.loadOpxFile(fileName, true);

            }

        };

        runner.start();

    }

}

