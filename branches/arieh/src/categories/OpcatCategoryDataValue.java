package categories;

import java.awt.Color;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Aug 10, 2009
 * Time: 12:15:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatCategoryDataValue {

    public OpcatCategoryDataValue(String value, String name, String description, String extID, Color color) {
        this.value = value;
        this.name = name;
        this.description = description;
        this.extID = extID;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtID() {
        return extID;
    }

    public void setExtID(String extID) {
        this.extID = extID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public long getID() {
        //this is uncool but the best I can do now. 
        return extID.hashCode();
    }

    public void setValue(String value) {
        this.value = value;
    }

    int id;
    String value;
    String name;
    String description;
    String extID;
    Color color;
}
