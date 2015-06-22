package versions;

import gui.projectStructure.Instance;

/**
 * Created by raanan.
 * Date: Apr 12, 2010
 * Time: 11:05:13 AM
 */
public abstract class AOPXDiffResult implements IDiffResult {
    private boolean deleted, changed, added;
    private Instance oldInstance, newInstance;

    private int id;


    public boolean isDeleted() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isAdded() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isChanged() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getID() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Instance getNewInstance() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
