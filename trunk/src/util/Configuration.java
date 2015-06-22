/*
 * Created on 10/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package util;

import gui.controls.FileControl;
import gui.util.LastFileEntry;
import gui.util.OpcatException;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @author eran
 *         <p/>
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Configuration {
    private static Configuration instance = null;

    public Properties getProperties() {
        return properties;
    }

    private SortedProperties properties;

    private String CONF_FILE_URL;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    protected Configuration() {

        CONF_FILE_URL = FileControl.getInstance()
                .getOPCATDirectory()
                + FileControl.fileSeparator + "opcat.properties";


        this.properties = new SortedProperties();

        try {
            FileControl file = FileControl.getInstance();

            File conf = new File(CONF_FILE_URL);

            if (!conf.exists()) {
                conf.createNewFile();
            }
            FileInputStream in = new FileInputStream(CONF_FILE_URL);

            this.properties.setProperty("directory.opcat.home", file.getOPCATDirectory());
            this.properties.setProperty("directory.opcat.backup", file
                    .getOPCATDirectory()
                    + FileControl.fileSeparator + "backup");
            this.properties.setProperty("directory.opcat.icons", file
                    .getOPCATDirectory()
                    + FileControl.fileSeparator + "icons");
            this.properties.setProperty("file.system.types", file
                    .getOPCATDirectory()
                    + FileControl.fileSeparator + "systems.ops");
            this.properties.setProperty("file.system.colors", file.getOPCATDirectory()
                    + FileControl.fileSeparator + "colors.ops");

            this.properties.setProperty("gui.show", "true");
            this.properties.setProperty("messages.send", "true");
            this.properties.setProperty("show_roles_on_process", "no");
            this.properties.setProperty("show_roles_on_objects", "no");
            this.properties.setProperty("show_state_not_result_rule", "no");
            this.properties.setProperty("show_link_addition_rule", "yes");
            this.properties.setProperty("show_times_on_process", "no");
            this.properties.setProperty("search.width", "1");
            this.properties.setProperty("search.depth", "1");
            this.properties.setProperty("graphics.default.lowerpanelminhighet", "100");
            this.properties.setProperty("req_tool_tip", "true");
            this.properties.setProperty("expose.checks.interfacechange", "true");
            this.properties.setProperty("expose.checks.oncommit", "true");
            this.properties.setProperty("simulation_level_range", "3");
            this.properties.setProperty("show_icons", "true");
            this.properties.setProperty("show_opl", "true");
            this.properties.setProperty("graphics.default.ThingIconAlphaComposite", "0.75");
            this.properties.setProperty("simulation_STOP_AT_AGENT", "false");
            this.properties.setProperty("simulation_SHOW_GRAPHICS", "true");
            this.properties.setProperty("simulation_MIN_TIME__PROCESS_DURATION", "false");
            this.properties.setProperty("simulation_RANDOM_PROCESS_DURATION",
                    "false");
            this.properties.setProperty("simulation_SHOW_RESOURCE", "false");
            this.properties.setProperty("simulation_SHOW_LIFE_SPAN", "false");
            this.properties.setProperty("simulation_RANDOM_STATE_SELECTION",
                    "true");
            this.properties.setProperty("simulation_STEP_BY_STEP_MODE", "true");
            this.properties.setProperty("simulation_MOVE_BETWEEN_OPD", "false");
            this.properties.setProperty("simulation_AUTOMATIC_INITIATION",
                    "true");
            this.properties.setProperty("simulation_ONE_OBJECT_INSTANCE",
                    "true");
            this.properties.setProperty("simulation_REACTION_TIME_RANGE_END",
                    "5");
            this.properties.setProperty("simulation.reaction.time.range.start",
                    "0");
            this.properties.setProperty("simulation.reaction.time", "0");
            this.properties.setProperty("simulation.fixed.reaction.time",
                    "true");
            this.properties.setProperty(
                    "simulation_PROCESS_DURATION_RANGE_END", "15");
            this.properties.setProperty(
                    "simulation_PROCESS_DURATION_RANGE_START", "5");
            this.properties.setProperty("simulation_PROCESS_DURATION", "1");
            this.properties.setProperty("simulation_FIXED_PROCESS_DURATION",
                    "true");
            this.properties.setProperty("simulation_STEP_DURATION", "1000");

            // new settings for opcat enterprise
            this.properties.setProperty("MCaccesstype", "svn");
            this.properties.setProperty("MCserver", "www.opcat.com");
            this.properties.setProperty("MCrepository", "Systems");
            this.properties.setProperty("MCTemplates", "Templates");
            this.properties.setProperty("MCCategories", "Categories");


            this.properties.setProperty("DBtype", "mysql");
            this.properties.setProperty("DBserver", "www.opcat.com");
            this.properties.setProperty("DBdatabasename", "opcat");
            this.properties.setProperty("DBPort", "3306");
            this.properties.setProperty("DBURLSeperator", "/");
            this.properties.setProperty("expose.copy.properties", "true");
            this.properties.setProperty("expose.copy.properties.ignorelist", "entry.rpg.code,object.rpg.Order,process.rpg.Order");

            this.properties.setProperty("process.body.aggragate.key", "entry.rpg.code");
            this.properties.setProperty("process.body.aggragate", "true");

            this.properties.setProperty("models_directory", file
                    .getOPCATDirectory()
                    + FileControl.fileSeparator + "Working Copy");

            this.properties.setProperty("jarname", "Opcat2.jar");

            this.properties.setProperty("show_reguler_links_messages", "false");

            this.properties.setProperty(
                    "expose_treat_relation_delete_as_change", "true");

            this.properties.setProperty("expose_treat_links_delete_as_change",
                    "true");


            this.properties.setProperty("entry.url.name",
                    "entry.rpg.parent.repositorypath");


            this.properties.setProperty(
                    "expose_treat_object_type_change_as_change", "true");

            this.properties.setProperty("logger", "com.opcat.client");

            this.properties.setProperty("entry.opd.inzoom.footer", " in-zoomed");
            this.properties.setProperty("entry.opd.unfold.footer", " unfolded");
            this.properties.setProperty("entry.opd.view.footer", " view");

            this.properties.setProperty("entry.opd.sd.header", "SD ");
            this.properties.setProperty("entry.opd.view.header", "View ");

            this.properties.setProperty("entry.opd.root", "SD");

            this.properties.setProperty("expose.check.files.status", "true");

            this.properties.setProperty("mc.export.overwrite.tmp", "true");
            this.properties.setProperty("project.summary.extra.properties", "entry.rpg.type");

            this.properties.setProperty("search.extra.parameters", "entry.rpg.type;entry.rpg.code");

            this.properties.setProperty("opcat.show.detailederrors", "false");

            this.properties.setProperty("graphics.show.cardinalitylabel", "false");

            this.properties.setProperty("graphics.default.ThingWidth", "120");
            this.properties.setProperty("graphics.default.ThingHeight", "70");
            this.properties.setProperty("graphics.default.MinimalThingWidth", "60");
            this.properties.setProperty("graphics.default.MinimalThingHeight", "35");
            this.properties.setProperty("graphics.default.FundamentalRelationWidth", "36");
            this.properties.setProperty("graphics.default.FundamentalRelationHeight", "31");
            this.properties.setProperty("graphics.default.StateWidth", "60");
            this.properties.setProperty("graphics.default.StateHeight", "35");
            this.properties.setProperty("graphics.default.OPDWidth", "1300");
            this.properties.setProperty("graphics.default.OPDHeight", "860");
            this.properties.setProperty("graphics.default.DraggerSize", "36");
            this.properties.setProperty("graphics.default.NormalSize", "5");
            this.properties.setProperty("graphics.default.CurrentSize", "4");

            this.properties.setProperty("graphics.default.BackgroundColor", "230, 230, 230");
            this.properties.setProperty("graphics.default.OPDBackgroundColor", "white");
            this.properties.setProperty("graphics.default.TextColor", "black");
            this.properties.setProperty("graphics.default.UrlColor", "red");
            this.properties.setProperty("graphics.default.ObjectColor", "0, 110, 0");
            this.properties.setProperty("graphics.default.ProcessColor", "0, 0, 170");
            this.properties.setProperty("graphics.default.LineTextColor", "black");
            this.properties.setProperty("graphics.default.LineColor", "black");
            this.properties.setProperty("graphics.default.BaseBorderColor", "black");
            this.properties.setProperty("graphics.default.StateColor", "91, 91, 0");
            this.properties.setProperty("graphics.default.metacolor", "black");

            this.properties.setProperty("graphics.effect.colorbyresource", "no");
            this.properties.setProperty("graphics.effect.plus", "green");
            this.properties.setProperty("graphics.effect.minus", "red");

            this.properties.setProperty("graphics.default.process.tooltip.property", "entry.general.notes");
            this.properties.setProperty("graphics.default.object.tooltip.property", "entry.general.notes");
            this.properties.setProperty("graphics.default.tooltip.header", "Text");


            this.properties.setProperty("versions.grid.color.change", "green");
            this.properties.setProperty("versions.grid.color.deleted", "red");
            this.properties.setProperty("versions.grid.color.new", "blue");

            this.properties.setProperty("system.lookandfeel.class", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            this.properties.setProperty("versions.diff.properties.ignorelist", "logicalprocess.entityattr.id;dummy");

            this.properties.setProperty("graphics.fonts.ThingFont.style", "0");    //Plain
            this.properties.setProperty("graphics.fonts.ThingFont.size", "16");
            //this.properties.setProperty("graphics.fonts.ThingFont.color", "red");

            this.properties.setProperty("graphics.fonts.URLFont.style", "3");    //Font.BOLD  1 + Font.ITALIC 2
            this.properties.setProperty("graphics.fonts.URLFont.size", "16");
            //this.properties.setProperty("graphics.fonts.URLFont.color", "red");

            this.properties.setProperty("graphics.fonts.StateFont.style", "0");    //Plain
            this.properties.setProperty("graphics.fonts.StateFont.size", "12");
            //this.properties.setProperty("graphics.fonts.StateFont.color", "red");

            this.properties.setProperty("graphics.fonts.LineFont.style", "0");    //Plain
            this.properties.setProperty("graphics.fonts.LineFont.size", "11");
            //this.properties.setProperty("graphics.fonts.LineFont.color", "red");

            this.properties.setProperty("graphics.fonts.LabelFont.style", "0");    //Plain
            this.properties.setProperty("graphics.fonts.LabelFont.size", "11");
            //this.properties.setProperty("graphics.fonts.LabelFont.color", "red");

            this.properties.setProperty("graphics.fonts.LinkFont.style", "0");    //Plain
            this.properties.setProperty("graphics.fonts.LinkFont.size", "12");
            //this.properties.setProperty("graphics.fonts.LinkFont.color", "red");

            this.properties.setProperty("graphics.fonts.SmallFont.style", "0");    //Plain
            this.properties.setProperty("graphics.fonts.SmallFont.size", "11");
            //this.properties.setProperty("graphics.fonts.SmallFont.color", "red");

            this.properties.setProperty("graphics.fonts.TimesFont.style", "0");    //Plain
            this.properties.setProperty("graphics.fonts.TimesFont.size", "16");
            this.properties.setProperty("graphics.fonts.TimesFont.color", "black");

            this.properties.setProperty("graphics.fonts.MeasureFont.style", "0");    //Plain
            this.properties.setProperty("graphics.fonts.MeasureFont.size", "16");
            this.properties.setProperty("graphics.fonts.MeasureFont.color", "black");

            this.properties.setProperty("graphics.show.metadata.colstoshow", "1,3");
            this.properties.setProperty("graphics.show.metadata.headerofcolstoshow", "0,2");
            this.properties.setProperty("graphics.show.metadata.headersep", ":");
            this.properties.setProperty("graphics.show.metadata.showheader", "true");
            this.properties.setProperty("graphics.show.metadata.headfromcolumnhead", "true");
            this.properties.setProperty("graphics.show.metadata.showtimes", "true");
            this.properties.setProperty("graphics.show.metadata.timeslabel", "Times");
            this.properties.setProperty("viewer.more.data", "Utilization:entry.extdata.Utilization %;Completed Jobs:entry.extdata.Number Completed Jobs");

            this.properties.load(in);

        } catch (FileNotFoundException e) {
            saveProperties();
            OpcatLogger.error(e);
        } catch (IOException e) {
            OpcatLogger.error(e);
        }
    }

    public Color getColorFromProperty(String property) {

        String color = getProperty(property);
        Color ret = Color.GRAY;

        try {
            String[] rgbTry = color.split(",");
            if (rgbTry.length == 3) {
                ret = new Color(Integer.valueOf(rgbTry[0].trim()), Integer.valueOf(rgbTry[1].trim()), Integer.valueOf(rgbTry[2].trim()));
            } else {
                if (color.equalsIgnoreCase("black")) {
                    ret = Color.BLACK;
                } else if (color.equalsIgnoreCase("red")) {
                    ret = Color.RED;
                } else if (color.equalsIgnoreCase("green")) {
                    ret = Color.GREEN;
                } else if (color.equalsIgnoreCase("grey")) {
                    ret = Color.GRAY;
                } else if (color.equalsIgnoreCase("blue")) {
                    ret = Color.BLUE;
                } else if (color.equalsIgnoreCase("cyan")) {
                    ret = Color.CYAN;
                } else if (color.equalsIgnoreCase("magenta")) {
                    ret = Color.MAGENTA;
                } else if (color.equalsIgnoreCase("yellow")) {
                    ret = Color.YELLOW;
                } else if (color.equalsIgnoreCase("white")) {
                    ret = Color.WHITE;
                } else if (color.equalsIgnoreCase("light_grey")) {
                    ret = Color.LIGHT_GRAY;
                } else if (color.equalsIgnoreCase("pink")) {
                    ret = Color.PINK;
                } else if (color.equalsIgnoreCase("orange")) {
                    ret = Color.ORANGE;
                } else if (color.equalsIgnoreCase("dark_grey")) {
                    ret = Color.DARK_GRAY;
                }
            }
        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        }


        return (ret != null ? ret : Color.GRAY);
    }

    public String getProperty(String name) {
        return (String) this.properties.get(name);
    }

    public ListIterator<String> getUserDirectories() throws OpcatException {

        ArrayList<String> list = new ArrayList<String>();
        list.add(Configuration.getInstance().getProperty("models_directory"));

        if ((this.getProperty("user_directories") == null)
                || (this.getProperty("user_directories")).trim().equals("")) {
            return list.listIterator();
        }

        StringTokenizer st = new StringTokenizer(this
                .getProperty("user_directories"), ",");
        if (st == null) {
            throw new OpcatException("No used files were found");
        }
        while (st.hasMoreElements()) {
            String entry = st.nextToken();
            list.add(entry);
        }
        return list.listIterator();
    }

    public void removeDirectoryToUserDirectories(String path) {
        StringTokenizer st = null;
        if (this.getProperty("user_directories") != null) {
            st = new StringTokenizer(this.getProperty("user_directories"), ",");
            if (st == null) {
                OpcatLogger
                        .error("[Configuration] No user directories were found");
            }
        }

        int i = 0;
        String newFileList = "";
        if (this.getProperty("user_directories") != null) {
            while (st.hasMoreElements()) {
                String entry = st.nextToken();
                if (!entry.equalsIgnoreCase(path)) {
                    newFileList += "," + entry;
                    i++;
                    OpcatLogger.info(" Removing From list - " + path, false);
                }
            }
        }
        if (newFileList.length() <= 1) {
            this.properties.setProperty("user_directories", "");
        } else {
            this.properties.setProperty("user_directories", newFileList
                    .substring(1, newFileList.length()));
        }

        saveProperties();

    }

    public void addDirectoryToUserDirectories(String path) {
        StringTokenizer st = null;
        if (this.getProperty("user_directories") != null) {
            st = new StringTokenizer(this.getProperty("user_directories"), ",");
            if (st == null) {
                OpcatLogger
                        .error("[Configuration] No used files were found");
            }
        }

        int i = 0;
        String newFileList = path;
        if (this.getProperty("user_directories") != null) {
            while (st.hasMoreElements()) {
                String entry = st.nextToken();
                if (!entry.equalsIgnoreCase(path)) {
                    newFileList += "," + entry;
                    i++;
                }
            }
        }
        this.properties.setProperty("user_directories", newFileList);

        saveProperties();

    }

    public ListIterator<LastFileEntry> getLastUsedFiles() throws OpcatException {
        ArrayList<LastFileEntry> list = new ArrayList<LastFileEntry>();
        if (this.getProperty("last_used_files") == null) {
            return list.listIterator();
        }
        StringTokenizer st = new StringTokenizer(this
                .getProperty("last_used_files"), ",");
        if (st == null) {
            throw new OpcatException("No used files were found");
        }
        while (st.hasMoreElements()) {
            String entry = st.nextToken();
            StringTokenizer et = new StringTokenizer(entry, "@");
            String pName = et.nextToken();
            if (!et.hasMoreTokens()) {
                throw new OpcatException("Bad entry");
            }
            String url = et.nextToken();
            LastFileEntry last = new LastFileEntry(pName, url);
            list.add(last);
        }
        return list.listIterator();
    }

    public void addFileToLastUsed(LastFileEntry last) {
        StringTokenizer st = null;
        if (this.getProperty("last_used_files") != null) {
            st = new StringTokenizer(this.getProperty("last_used_files"), ",");
            if (st == null) {
                OpcatLogger
                        .error("[Configuration] No used files were found");
            }
        }
        String newFile = last.getProjectName() + "@" + last.getFileUrl();
        String newFileList = newFile;
        int i = 0;
        if (this.getProperty("last_used_files") != null) {
            while (st.hasMoreElements() && (i < 4)) {
                String entry = st.nextToken();
                if (entry.compareTo(newFile) != 0) {
                    newFileList += "," + entry;
                    i++;
                }
            }
        }
        this.properties.setProperty("last_used_files", newFileList);

        saveProperties();

    }

    public void saveProperties() {
        try {
            FileOutputStream out = new FileOutputStream(CONF_FILE_URL);
            this.properties.store(out, "Opcat 2 properties file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getSearchWidth() {

        int ret = Integer.parseInt(properties.getProperty("search.width"));
        if (ret <= 0)
            ret = 10;
        return ret;

    }

    public int getSearchDepth() {
        int ret = Integer.parseInt(properties.getProperty("search.depth"));
        if (ret <= 0)
            ret = 10;
        return ret;
    }


    public Properties getPropertiesStartingWith(String firstToken) {
        Properties props = new Properties();
        for (Object prop : properties.keySet()) {
            Object value = properties.get(prop);
            if (value.toString().startsWith(firstToken)) {
                props.put(prop, value);
            }
        }

        return props;
    }

    // public Dimension getPanelDimensions() {
    // return panelDimensions;
    // }
    //
    // public void setPanelDimensions(Dimension d) {
    // panelDimensions = d;
    // }

}