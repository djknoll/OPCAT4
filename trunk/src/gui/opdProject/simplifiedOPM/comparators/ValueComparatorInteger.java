package gui.opdProject.simplifiedOPM.comparators;

import java.util.Comparator;
import java.util.Map;

public class ValueComparatorInteger implements Comparator {
    Map base;

    public ValueComparatorInteger(Map base) {
        this.base = base;
    }

    public int compare(Object a, Object b) {

        if ((Integer) base.get(a) <= (Integer) base.get(b)) {
            return 1;
        } else {
            return -1;
        }
    }

}