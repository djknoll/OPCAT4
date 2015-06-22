package versions;

import gui.projectStructure.Instance;

/**
 * Created by raanan.
 * Date: Apr 12, 2010
 * Time: 10:57:49 AM
 */
public interface IDiffResult {

    public boolean isDeleted();

    public boolean isAdded();

    public boolean isChanged();

    public int getID();

    public Instance getNewInstance();

    public Instance getOldInstance();

}
