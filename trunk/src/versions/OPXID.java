package versions;

import java.util.UUID;

/**
 * Created by raanan.
 * Date: Jun 17, 2010
 * Time: 8:19:11 AM
 */
public class OPXID implements Comparable {

    public enum OPX_ID_TYPE {
        NAME, UUID
    }

    private OPX_ID_TYPE type;
    private UUID uuid;
    private String uuidString;

    public OPXID(UUID uuid) {
        this.uuid = uuid;
        type = OPX_ID_TYPE.UUID;
    }

    public OPXID(String uuidString) {
        this.uuidString = uuidString;
        type = OPX_ID_TYPE.NAME;
    }

    public static OPXID fromString(String id, OPX_ID_TYPE type) {
        OPXID opx;
        try {
            if (type == OPXID.OPX_ID_TYPE.UUID) {
                opx = new OPXID(UUID.fromString(id));
            } else {
                opx = new OPXID(id);
            }
        } catch (Exception ex) {
            opx = new OPXID(id);
        }
        return opx;
    }


    public int compareTo(Object o) {
        if (o instanceof OPXID) {
            OPXID opx = (OPXID) o;
            switch (type) {
                case UUID:
                    return uuid.compareTo((UUID) opx.getID());
                case NAME:
                    return uuidString.compareTo(opx.getID().toString());
            }
        }
        return -1;
    }

    public boolean equals(Object id) {
        return (this.compareTo(id) == 0);
    }

    public int hashCode() {
        return getID().toString().hashCode();
    }

    public boolean equals(OPXID id) {
        return (this.compareTo(id) == 0);
    }

    public String toString() {
        return getID().toString();
    }

    private Object getID() {
        switch (type) {
            case UUID:
                return uuid;
            case NAME:
                return uuidString;
        }

        return null;
    }

    public OPX_ID_TYPE getIDType() {
        return type;
    }
}
