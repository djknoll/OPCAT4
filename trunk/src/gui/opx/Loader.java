package gui.opx;

import gui.controls.FileControl;
import gui.opdProject.OpdProject;
import gui.opx.ver2.LoaderVer2;
import gui.opx.ver3.LoaderVer3;
import gui.projectStructure.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import util.OpcatLogger;

import javax.swing.*;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;

public class Loader {

    String path;

    public Loader(String path) {
        this.path = path;
    }

    public OpdProject load(JDesktopPane parentDesktop, InputStream is,
                           JProgressBar progressBar, boolean needGUI) throws LoadException {

        FileControl.getInstance().setDuringFileAction(true);

        try {
            SAXBuilder builder = new SAXBuilder(
                    "org.apache.xerces.parsers.SAXParser");
            Document testDoc = null;
            try {
                testDoc = builder.build(is);
            } catch (Exception ex) {
                OpcatLogger.error(ex);
            }
            Element rootTag = null;
            try {
                rootTag = testDoc.getRootElement();
            } catch (NullPointerException nex) {
                FileControl.getInstance().setDuringFileAction(false);
                throw new LoadException(
                        "Error loading file - OPX file is empty");
            } catch (Exception ex) {
                FileControl.getInstance().setDuringFileAction(false);
                throw new LoadException("Error loading file");
            }
            String version = rootTag.getAttributeValue("version");
            LoaderI myLoader = this.chooseLoader(version);
            if (myLoader == null) {
                FileControl.getInstance().setDuringFileAction(false);
                throw new LoadException("The version: \"" + version
                        + "\" is not supported");
            }

            myLoader.setPath(path);
            OpdProject project = myLoader.load(parentDesktop, testDoc,
                    progressBar);
            project.setPath(path);
            project.setFileName(path);
            FileControl.getInstance().setDuringFileAction(false);
            sentToBack(project);

            /**
             * update the gui
             */
            if (!FileControl.getInstance().isGUIOFF() && needGUI) {
                SwingUtilities.invokeLater(new UpdateGUI(project.getSystemStructure()));
            }
            return project;

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        } finally {
            FileControl.getInstance().setDuringFileAction(false);
        }
        FileControl.getInstance().setDuringFileAction(false);
        return null;
    }

    private void sentToBack(OpdProject project) {
        Enumeration elements = project.getSystemStructure().getElements();
        while (elements.hasMoreElements()) {
            Entry entry = (Entry) elements.nextElement();
            Enumeration insEnum = entry.getInstances(false);
            while (insEnum.hasMoreElements()) {
                Instance ins = (Instance) insEnum.nextElement();
                if (ins.getGraphicalRepresentation() != null) {
                    if (ins instanceof ThingInstance) {
                        if (((ThingInstance) ins).getConnectionEdge().isSentToBAck()) {
                            ((ThingInstance) ins).getConnectionEdge().setSentToBAck(true);
                        }
                    }
                    if (ins instanceof StateInstance) {

                    }
                    //ins.getGraphicalRepresentation().sendToBack();
                }
            }
        }
    }

    private LoaderI chooseLoader(String version) {
        if (version.equals("") || version.equals("1") || version.equals("2")) {
            return new LoaderVer2(path);
        }
        if (version.equals("3")) {
            return new LoaderVer3(path);
        }
        return null;
    }

    class UpdateGUI implements Runnable {
        private MainStructure main;

        UpdateGUI(MainStructure main) {
            this.main = main;
        }

        public void run() {

            Iterator<Instance> elements = main.getInstances();
            while (elements.hasNext()) {
                Instance ins = elements.next();
                ins.update();
            }
        }
    }

    ;
}