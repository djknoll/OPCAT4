package categories;

import util.OpcatLogger;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

public class OpcatValidatorResult {

    private HashMap<Long, String> idToExtIDMap = new HashMap<Long, String>();

    public void addUsedItem(long itemID) {
        try {
            String extID = "Not defined";
            if (category.getMetaItem(itemID) != null) {
                extID = category.getMetaItem(itemID).getExtID();
            }
            idToExtIDMap.put(itemID, extID);
        } catch (Exception e) {
            OpcatLogger.error(e, false);
        }
    }

    public Collection<String> getUsedItemsIDs() {
        return idToExtIDMap.values();
    }

    public boolean isUsing() {
        return using;
    }

    public File getOpcatFile() {
        return opcatFile;
    }

    public void setUsing(boolean using) {
        this.using = using;
    }

    OpcatValidatorResult(boolean using, File opcatFile, OpcatCategory category) {
        this.using = using;
        this.opcatFile = opcatFile;
        this.category = category;
    }

    boolean inProcessing = true;

    public OpcatCategory getCategory() {
        return category;
    }

    public boolean isInProcessing() {
        return inProcessing;
    }

    public void setInProcessing(boolean inProcessing) {
        this.inProcessing = inProcessing;
    }

    public String toString() {
        String status = (isInProcessing() ? "Processing :" : "Processed :");
        if (isInProcessing()) {
            return "model :\t" + opcatFile.getPath() + " is being processed ";
        } else {
            return "model :\t" + opcatFile.getPath() + (isUsing() ? ", is using category \"" + category.getName() + "\"" : ", has no usage of \"" + category.getName() + "\"");
        }
    }

    boolean using = false;
    File opcatFile;
    OpcatCategory category;

}
