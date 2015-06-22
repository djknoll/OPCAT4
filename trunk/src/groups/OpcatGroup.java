package groups;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Dec 23, 2010
 * Time: 12:39:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatGroup {
    private String name;
    private String description;
    private Integer id;
    private int color;


    protected OpcatGroup(String name, String description, int color, Integer id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public int getColor() {
        return color;
    }
}
