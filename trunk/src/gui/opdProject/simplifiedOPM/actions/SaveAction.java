package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import y.io.graphml.graph2d.Graph2DGraphMLHandler;
import y.io.graphml.output.GraphMLWriteException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by raanan.
 * Date: 12/09/11
 * Time: 18:12
 */
public class SaveAction extends AbstractAction {

    OpdSimplifiedView view;


    public SaveAction(OpdSimplifiedView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //show file dialog
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(view);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            Graph2DGraphMLHandler writer = new Graph2DGraphMLHandler();
            try {
                writer.write(view.getGraph2D(), new FileWriter(file));
            } catch (GraphMLWriteException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}
