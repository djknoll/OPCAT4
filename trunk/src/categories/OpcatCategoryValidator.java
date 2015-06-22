package categories;

import gui.metaLibraries.logic.MetaManager;
import org.apache.commons.digester.Digester;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Oct 21, 2009
 * Time: 1:02:55 PM
 * To change this template use File | Settings | File Templates.
 */

public class OpcatCategoryValidator extends Observable {

    private static OpcatCategoryValidator instance = null;
    private File repoLocation;
    private OpcatCategory category = null;


    public synchronized void notifyObservers(Object args) {
        super.setChanged();
        super.notifyObservers(args);
    }


    private OpcatCategoryValidator(File repoLocation) {
        this.repoLocation = repoLocation;
    }

    public static OpcatCategoryValidator getInstance(File repoLocation) {
        if (instance == null) {
            instance = new OpcatCategoryValidator(repoLocation);
        }
        return instance;
    }


    public void run(OpcatCategory category) {
        try {

            this.category = category;
            MetaManager mm = new MetaManager();
            this.category.load(mm);
            processFiles(repoLocation);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void processFiles(File file)
            throws IOException {
        // do not try to index files that cannot be read
        if (file.canRead()) {
            if (file.isDirectory()) {
                String[] files = file.list();
                // an IO error could occur
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        processFiles(new File(file, files[i]));
                    }
                }
            } else {
                try {

                    if (file.getName().toLowerCase().endsWith("opx")) {

                        Categories cat = Categories.getInstance();

                        OpcatValidatorResult res = new OpcatValidatorResult(false, file, category);
                        res.setInProcessing(true);
                        notifyObservers(res);

                        Digester digester = new Digester();
                        digester.setValidating(false);
                        digester.setNamespaceAware(false);

                        //System start
                        digester.addObjectCreate("OPX/OPMSystem", OpcatValidatorParser.class);

                        //Object Roles
                        digester.addObjectCreate("OPX/OPMSystem/LogicalStructure/ObjectSection/LogicalObject/EntityAttr/Roles/OPMRole", OpcatCategoryUsage.class);
                        digester.addSetProperties("OPX/OPMSystem/LogicalStructure/ObjectSection/LogicalObject/EntityAttr/Roles/OPMRole", "roleID", "id");
                        digester.addSetProperties("OPX/OPMSystem/LogicalStructure/ObjectSection/LogicalObject/EntityAttr/Roles/OPMRole", "libID", "libID");

                        digester.addObjectCreate("OPX/OPMSystem/LogicalStructure/ProcessSection/LogicalProcess/EntityAttr/Roles/OPMRole", OpcatCategoryUsage.class);
                        digester.addSetProperties("OPX/OPMSystem/LogicalStructure/ProcessSection/LogicalProcess/EntityAttr/Roles/OPMRole", "roleID", "id");
                        digester.addSetProperties("OPX/OPMSystem/LogicalStructure/ProcessSection/LogicalProcess/EntityAttr/Roles/OPMRole", "libID", "libID");

                        digester.addSetNext("OPX/OPMSystem/LogicalStructure/ObjectSection/LogicalObject/EntityAttr/Roles/OPMRole", "addRole");
                        digester.addSetNext("OPX/OPMSystem/LogicalStructure/ProcessSection/LogicalProcess/EntityAttr/Roles/OPMRole", "addRole");

                        OpcatValidatorParser ocv = (OpcatValidatorParser) digester.parse(file);


                        ocv.process(res);
                        res.setInProcessing(false);
                        notifyObservers(res);
                    }

                }
                // at least on windows, some temporary files raise this exception with an "access denied" message
                // checking if the file can be read doesn't help
                catch (Exception fnfe) {
                    notifyObservers("Error : " + fnfe.getMessage());
                }
            }
        }
    }

}


