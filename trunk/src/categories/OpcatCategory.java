package categories;

import com.csvreader.CsvReader;
import database.OpcatDatabaseConnection;
import gui.controls.FileControl;
import gui.dataProject.DataAbstractItem;
import gui.dataProject.DataCreatorType;
import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.MetaManager;
import gui.util.OpcatFile;
import modelControl.OpcatMCManager;
import util.OpcatLogger;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Aug 10, 2009
 * Time: 12:13:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatCategory {

    private String name;
    private String description;
    private int id = -1;
    private OpcatCategoryData data = null;
    private File mySource = null;
    private MetaLibrary meta = null;

    public OpcatCategory(String name, String description, OpcatCategoryData data) {
        this.name = name;
        this.description = description;
        this.data = data;
    }

    public DataAbstractItem getMetaItem(long id) {
        if (meta == null) return null;
        try {
            return meta.getMEtaDataItem((int) id);
        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        }
        return null;
    }

    protected boolean load(MetaManager man) {

        try {
            meta = new MetaLibrary(MetaLibrary.TYPE_CLASSIFICATION, mySource.getPath(), DataCreatorType.DATA_TYPE_TEXT_FILE_CSV, DataCreatorType.REFERENCE_TYPE_CATEGORY_CLASSIFICATION_FILE);
            //meta.getDataProject().isShowColorValueAsPrograssBar();
            meta.setHidden(true);
            //load the csv or xml as dataproject
            man.addMetaLibrary(meta);
            //load the library.
            meta.load();
            if (meta.getState() != MetaLibrary.STATE_LOADED) {
                return false;
            }
        } catch (MetaException ex) {
            OpcatLogger.error(ex);
            return false;
        }
        return true;
    }

    protected OpcatCategory(File categoryFile) throws FileNotFoundException, IOException {
        mySource = categoryFile;

        BufferedReader bufRdr = new BufferedReader(new FileReader(categoryFile));
        String hed = bufRdr.readLine();

        String[] hedear = hed.split("#");
        //StringTokenizer hedToken = new StringTokenizer(hed, "#");
        //name = hedToken.nextToken();
        //description = hedToken.nextToken();
        //NAME type = hedToken.nextToken();
        String type = "String";
        name = hedear[0];
        if (hedear.length == 3) {
            description = hedear[1];
            description = hedear[2];
        } else {
            type = hedear[1];
        }

        bufRdr.close();

        data = new OpcatCategoryData(OpcatCategoryData.OPCAT_DATA_TYPE.valueOf(type));

        CsvReader reader = new CsvReader(categoryFile.getPath(), ',', Charset.defaultCharset());
        reader.setDelimiter(',');
        reader.setTextQualifier('"');
        reader.setUseTextQualifier(true);
        reader.skipLine(); // skip the name of tab line
        reader.readHeaders();

        ArrayList<OpcatCategoryDataValue> values = new ArrayList<OpcatCategoryDataValue>();
        while (reader.readRecord()) {
            Color color = parseColorString(reader.get(4));
            OpcatCategoryDataValue value = new OpcatCategoryDataValue(reader.get(3), reader.get(1), reader.get(2), reader.get(0), color);
            values.add(value);
        }
        data.setValues(values);

    }

    public static Color parseColorString(String colorString) {
        StringTokenizer colorToken = new StringTokenizer(colorString, "#");
        String RED = colorToken.nextToken();
        String GREEN = colorToken.nextToken();
        String BLUE = colorToken.nextToken();
        String ALFE = colorToken.nextToken();
        int red = Integer.valueOf(RED);
        int green = Integer.valueOf(GREEN);
        int blue = Integer.valueOf(BLUE);
        int alfe = Integer.valueOf(ALFE);
        Color color = new Color(red, green, blue, alfe);
        return color;

    }

    public OpcatCategoryData getData() {
        return data;
    }

    public void setData(OpcatCategoryData data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected long getId() {
        if (meta != null) {
            return meta.getID();
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * create the category in Vision
     *
     * @return visionID if sucssesfull or -1 if failed
     */
    protected int saveToVision() {
        //get DB connection
        Connection connection = OpcatDatabaseConnection.getInstance().getConnection();
        int id = -1;
        try {
            //create vision category

            //check if category exists
            String query = "select ID  from dw_categories where NAME = '" + getName() + "'";
            ResultSet rs = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(query);

            if (rs.next()) {
                id = rs.getInt("ID");
                query = "UPDATE dw_categories SET NAME = '" + getName() + "',DESCRIPTION = '" + getDescription() + "', IS_AUTO = 0 WHERE ID = " + id;
            } else {
                query = "select max(ID) as MAX_ID  from dw_categories ";
                ResultSet number = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
                if (number.next()) {
                    id = (id < (number.getInt("MAX_ID") + 1) ? number.getInt("MAX_ID") + 1 : id);
                } else {
                    id = 5000;
                }
                query = "insert into dw_categories (ID, NAME , DESCRIPTION, IS_AUTO) values (" + id + ",'" + getName() + "','" + getDescription() + "'," + "0)";
            }
            int ret = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeUpdate(query);
            if (ret == 1) {
                //create vision values
                int valueID = 5000;

                for (OpcatCategoryDataValue value : getData().getValues()) {
                    query = "SELECT * FROM dw_categories_data where NAME  = '" + value.getName() + "'";
                    rs = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
                    if (rs.next()) {
                        //update
                        valueID = rs.getInt("ID");
                        query = "UPDATE dw_categories_data SET NAME = '" + value.getName() + "',DESCRIPTION = '" + value.getDescription() + "' WHERE ID = " + valueID;
                    } else {
                        //insert
                        query = " SELECT MAX(ID) as MAX_ID from dw_categories_data ";
                        rs = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
                        if (rs.next()) {
                            valueID = rs.getInt("MAX_ID") + 1;
                        }
                        query = "insert into dw_categories_data (ID, NAME , DESCRIPTION) values (" + valueID + ",'" + value.getName() + "','" + value.getDescription() + "')";
                    }
                    int valueRet = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY).executeUpdate(query);
                    if (valueRet != 1) {
                        OpcatLogger.error("Failed to insert value " + value.getName() + " of category " + getName());
                    }
                }
            } else {
                OpcatLogger.error("Could not insert category " + getName() + " Into vision DW");
            }
        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        }
        return id;
    }

    protected boolean save() {

        File file = Categories.getInstance().getCategoriesDirectory();
        if ((file == null) || !(file.exists())) {
            OpcatLogger.error("No categories directory " + (file != null ? file.getPath() : ""), false);
            return false;
        }

        if (mySource == null) {
            mySource = new File(file.getPath() + FileControl.fileSeparator + getName().replace("#", "_").replace("$", "_").replace("^", "_").replace("&", "_").replace("*", "_").replace("%", "_").replace("@", "_").replace("'", "_").replace("\"", "_").replace(" ", "_").replace("\\", "_") + ".opc");
        }

        if ((file == null) || !file.exists()) {
            OpcatLogger.error("No categories directory " + (mySource != null ? mySource.getPath() : ""), false);
            return false;
        }

        if ((mySource != null) && mySource.isDirectory()) {
            OpcatLogger.error("Source category file is a Directory and not a file " + (mySource != null ? mySource.getPath() : ""), false);
            return false;
        }


        try {
            File tempCategory = File.createTempFile("opcat", null);
            FileWriter writer = new FileWriter(tempCategory);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(getName() + "#" + getDescription() + "#" + getData().getType() + "\n");

            ArrayList<OpcatCategoryDataValue> values = data.getValues();
            out.write("\"ID\", \"Name\", \"Description\" , \"" + getName() + "\" , \"Color\"" + "\n");
            for (OpcatCategoryDataValue value : values) {
                out.write("\"" + value.getExtID() + "\",");
                out.write("\"" + value.getName() + "\",");
                out.write("\"" + value.getDescription() + "\",");
                out.write("\"" + value.getValue() + "\",");
                out.write("\"" + value.getColor().getRed() + "#" + value.getColor().getGreen() + "#" + value.getColor().getBlue() + "#" + value.getColor().getAlpha() + "\"" + "\n");
            }
            out.close();

            if ((mySource != null) && mySource.exists()) {
                mySource.delete();
            }

            copyFile(tempCategory, mySource);

            //commit to SVN
            OpcatFile templates = new OpcatFile(OpcatMCManager.CATEGORIES_DIR, true);
            if (OpcatMCManager.getInstance().getFileURL(mySource) == null) {
                OpcatMCManager.getInstance().doAdd(mySource);
            }
            OpcatMCManager.getInstance().doCommit(templates, "Category gui commit", false, false);

            return true;

        } catch (IOException ex) {
            OpcatLogger.error(ex, false);
            return false;
        }
    }

    public static void copyFile(File fromFile, File toFile) throws IOException {
        Scanner freader = new Scanner(fromFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));

        //... Loop as long as there are input lines.
        String line = null;
        while (freader.hasNextLine()) {
            line = freader.nextLine();
            writer.write(line);
            writer.newLine();   // Write system dependent end of line.
        }

        freader.close();  // Close to unlock.
        writer.close();  // Close to unlock and flush to disk.
    }

    protected boolean delete() {
        if (mySource == null) {
            OpcatLogger.error("No category file to delete", false);
        } else {
        }

        return false;
    }
}
