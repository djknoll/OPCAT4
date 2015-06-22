package groups;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: 1/1/11
 * Time: 11:46 AM
 */
public class OpcatGroupExport {
    int exportID;
    int groupID;
    Date dateTime;
    String user;
    boolean active;

    ArrayList<OpcatGroupEntityExport> entities;

    public int getExportID() {
        return exportID;
    }

    public int getGroupID() {
        return groupID;
    }

    public Date getDateTime() {
        return dateTime;
    }


    public String getUser() {
        return user;
    }

    public boolean isActive() {
        return active;
    }

    public OpcatGroupExport(int exportID, int groupID, Date dateTime, String user, boolean active) {
        this.exportID = exportID;
        this.groupID = groupID;
        this.dateTime = dateTime;
        this.user = user;
        this.active = active;
    }

    public ArrayList<OpcatGroupEntityExport> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<OpcatGroupEntityExport> entities) {
        this.entities = entities;
    }
}
