package categories;

import gui.metaLibraries.logic.MetaManager;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Oct 24, 2009
 * Time: 1:09:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatValidatorParser {
    private ArrayList<OpcatCategoryUsage> opcatCategoryUsages = new ArrayList<OpcatCategoryUsage>();
    private boolean using = false;

    public OpcatValidatorParser() {
        super();
    }

    public void addRole(OpcatCategoryUsage opcatCategoryUsage) {
        opcatCategoryUsages.add(opcatCategoryUsage);
    }

    public void process(OpcatValidatorResult res) {
        res.getCategory().load(new MetaManager());
        for (OpcatCategoryUsage opcatCategoryUsage : opcatCategoryUsages) {
            if (res.getCategory().getId() == opcatCategoryUsage.getLibID()) {
                res.setUsing(true);
                res.addUsedItem(opcatCategoryUsage.getId());
            }
        }

    }

    public boolean isUsing() throws Exception {
        return using;
    }
}
