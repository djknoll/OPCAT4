package categories;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Oct 22, 2009
 * Time: 5:51:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatCategoryUsage {
    private long id;
    private long libID;

    public OpcatCategoryUsage() {
        super();
    }

    public long getId() {
        return id;
    }

    public long getLibID() {
        return libID;
    }

    public void setId(long id) {
        this.id = Long.valueOf(id);
    }

    public void setLibID(long libID) {
        this.libID = Long.valueOf(libID);
    }
}
