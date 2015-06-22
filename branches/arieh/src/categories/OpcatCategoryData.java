package categories;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Aug 10, 2009
 * Time: 12:14:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatCategoryData {

    public static enum OPCAT_DATA_TYPE {
        Integer, Date, String, Boolean
    }

    ;

    private OPCAT_DATA_TYPE type = OPCAT_DATA_TYPE.String;

    public OPCAT_DATA_TYPE getType() {
        return type;
    }

    private ArrayList<OpcatCategoryDataValue> values = new ArrayList<OpcatCategoryDataValue>();

    public OpcatCategoryData(OPCAT_DATA_TYPE type) {
        this.type = type;
    }

    public ArrayList<OpcatCategoryDataValue> getValues() {
        return values;
    }

    public void setValues(ArrayList<OpcatCategoryDataValue> values) {
        this.values = values;
    }

    public String valueIDToExtID(long id) throws Exception {
        for (OpcatCategoryDataValue value : getValues()) {
            if (value.getID() == id) return value.extID;
        }
        throw new Exception("Not a real ID");
    }

}
