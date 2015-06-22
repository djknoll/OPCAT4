package gui.opdProject.simplifiedOPM.comparators;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by raanan.
 * Date: 16/07/11
 * Time: 00:14
 */
public class ValueComparatorDouble implements Comparator {
    Map base;

    public ValueComparatorDouble(Map base) {
        this.base = base;
    }

    public int compare(Object a, Object b) {

        if ((Double) base.get(a) <= (Double) base.get(b)) {
            return 1;
        } else {
            return -1;
        }
    }

}
