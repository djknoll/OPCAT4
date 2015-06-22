package categories.tests;

import categories.*;
import modelControl.OpcatMCManager;
import org.apache.commons.io.FileUtils;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNEvent;
import org.tmatesoft.svn.core.wc.SVNEventAction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

//import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Oct 22, 2009
 * Time: 9:33:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class CategoriesTest implements Observer {
    private static ArrayList<OpcatCategory> categories;

    //    @Test
    public void testValidator() {

        //directory where the checked-out files are
        File repoLocation = new File("C:\\Program Files (x86)\\Opcat\\Working Copy\\Simens");

        //repo parh to be checked out. this will be used in the test only if
        //the repoLocation is not exsistent or is not a svn directory
        String repoPath = "/Simens";

        OpcatCategoryValidator ocv = OpcatCategoryValidator.getInstance(repoLocation);
        ocv.addObserver(this);


        OpcatMCManager.doSilentLogin("sl1", "12345");

        if (OpcatMCManager.isConnectedToerver()) {
            OpcatMCManager mc = OpcatMCManager.getInstance();
            mc.addObserver(this);

            SVNURL url = mc.getFileURL(repoLocation);
            //null if directory is not svn directory
            if (url != null) {
                mc.doUpdate(repoLocation);
            } else {
                //directory exists but is not a svn directory so delete it and recreate
                try {
                    FileUtils.deleteDirectory(repoLocation);
                } catch (IOException e) {
                }
                repoLocation.mkdirs();
                mc.doCheckOut(repoPath, repoLocation);
            }


            categories = Categories.getInstance().getCategories();
            for (OpcatCategory categor : categories) {
                String values = "";
                for (OpcatCategoryDataValue value : categor.getData().getValues()) {
                    values = values + "\t" + value.getExtID() + ":" + value.getDescription();
                }
                System.out.println("Starting Category : \"" + categor.getName() + "\"");
                System.out.println("Current Values : " + values);

                ocv.run(categor);
            }

        }

    }

    public void update(Observable o, Object arg) {
        //OpcatValidatorResult res = (OpcatValidatorResult) arg;
        if (arg instanceof OpcatValidatorResult) {
            OpcatValidatorResult res = (OpcatValidatorResult) arg;
            if (res.isInProcessing()) {
                //System.out.println("Started Processing :" + res.getOpcatFile().getPath());
            } else {
                //if(!res.isUsing()) {
                //print something prety to show progress.

                //}  else {
                String usedIDs = "";
                for (String extID : res.getUsedItemsIDs()) {
                    usedIDs = usedIDs + extID + ",";
                }
                if (usedIDs.endsWith(",")) {
                    usedIDs = usedIDs.substring(0, usedIDs.length() - 1);
                }
                usedIDs = "(" + usedIDs + ")";

                System.out.println("Model : \"" +
                        res.getOpcatFile().getPath() + "\"" +
                        " is " + (res.isUsing() ? usedIDs : "not") + " using category \"" +
                        res.getCategory().getName() + "\"");
            }

        } else if (arg instanceof SVNEvent) {
            SVNEvent event = (SVNEvent) arg;
            SVNEventAction action = event.getAction();
            System.out.println("SVN: " +
                    (event.getURL() != null ? event.getURL() + "," : "") +
                    (event.getFile() == null ? "" : event.getFile().getPath() + ",") +
                    (action.toString().contains("_") ? action.toString().substring(0, action.toString().indexOf("_")) : action.toString()).toUpperCase());
        }
    }
}
