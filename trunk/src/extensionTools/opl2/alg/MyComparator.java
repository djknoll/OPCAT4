package extensionTools.opl2.alg;

import exportedAPI.opcatAPI.IEntry;

import java.util.Comparator;

/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 *
 * @author OPCAT team
 * @version 1.0
 */

public class MyComparator implements Comparator {
    public MyComparator() {
    }

    public int compare(Object o1, Object o2) {
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        IEntry ent1 = (IEntry) o1;
        IEntry ent2 = (IEntry) o2;
        long diff = ent1.getId() - ent2.getId();
        return (diff > 0 ? 1 : (diff < 0 ? -1 : 0));
    }

    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (this.compare(this, obj) == 0) {
            return true;
        }
        return false;
    }
}