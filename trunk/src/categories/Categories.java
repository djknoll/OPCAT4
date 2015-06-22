package categories;

import gui.metaLibraries.logic.MetaManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelControl.OpcatMCManager;
import util.OpcatLogger;

/**
 * The Categories manager is used to manage the categories files in the repository.
 * The Categories connection to models is done by the DataProject as a classification library.
 *
 * @author raanan
 */
public class Categories {

    private static Categories manager = null;
    private ArrayList<OpcatCategory> myCategories = new ArrayList<OpcatCategory>();

    public File getCategoriesDirectory() {
        return new File(OpcatMCManager.CATEGORIES_DIR);
    }

    private Categories() {
        myCategories = init();
    }

    public boolean save(OpcatCategory category) {
        boolean ret = category.save();
        if (ret) {
            myCategories = init();
        }
        return ret;
    }

    public int saveToVision(OpcatCategory category) {
        int  ret = category.saveToVision();
        return ret;
    }

    public ArrayList<String> getList() {
        ArrayList<String> ret = new ArrayList<String>();
        for (OpcatCategory category : myCategories) {
            ret.add(category.getName());
        }
        return ret;
    }

    public boolean load(MetaManager metas) {
        for (OpcatCategory category : myCategories) {
            //check if category is allready defined
            boolean ret = true;
            if (metas.getMetaLibrary(category.getName()) == null) {
                ret = category.load(metas);
            }
            if (!ret) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<OpcatCategory> init() {
        ArrayList<OpcatCategory> ret = new ArrayList<OpcatCategory>();
        if ((getCategoriesDirectory() != null) && getCategoriesDirectory().exists()) {
            for (File categoryFile : getCategoriesDirectory().listFiles()) {
                if (categoryFile.getPath().endsWith(".opc")) {
                    OpcatCategory category;
                    try {
                        category = new OpcatCategory(categoryFile);
                    } catch (Exception ex) {
                        OpcatLogger.error(ex, false);
                        continue;
                    }
                    ret.add(category);
                }
            }
        }
        return ret;
    }

    public static Categories getInstance() {
        if (manager == null) {
            manager = new Categories();
        }
        return manager;
    }

    public OpcatCategory getCategory(String name) {
        for (OpcatCategory category : myCategories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }

    public OpcatCategory getCategory(int id) {
        for (OpcatCategory category : myCategories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    public boolean deleteCategory(OpcatCategory category) {
        boolean ret = category.delete();
        if (ret) {
            myCategories = init();
        }
        return ret;
    }

    public ArrayList<OpcatCategory> getCategories() {
        return myCategories;
    }

    public void validate(OpcatCategory category) {
    }
}
