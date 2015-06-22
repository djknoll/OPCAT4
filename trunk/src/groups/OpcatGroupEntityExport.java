package groups;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: 1/1/11
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatGroupEntityExport {

    String name;
    String text;
    String id;
    String modelPath;


    public OpcatGroupEntityExport(String name, String text, String id, String modelPath) {
        this.name = name;
        this.text = text;
        this.id = id;
        this.modelPath = modelPath;
    }

    public String getModelPath() {
        return modelPath;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

}
