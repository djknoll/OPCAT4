package gui.projectStructure;

import expose.OpcatExposeManager;
import gui.Opcat2;
import gui.controls.FileControl;
import gui.util.BrowserLauncher2;
import gui.util.opcatGrid.GridPanel;
import modelControl.OpcatMCManager;
import org.jdom.CDATA;
import org.jdom.Element;
import util.Configuration;
import util.OpcatLogger;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Jun 14, 2009
 * Time: 3:05:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpcatProperties extends Properties {

    private final String paramStartingString = "paramater.";

    public static enum OpcatPropertiesGroups {
        ALL, GENERAL, RPG, CODE
    }

    //Entry
    public final static String entryDescriptionKey = "entry.general.description";
    public final static String entryIconNameKey = "entry.general.iconname";
    public final static String entryURLKey = "entry.general.url";

    //Instance
    public final static String instanceOrderKey = "instance.opm.ExecutionOrder";

    //OPM
    public final static String opmNameKey = "entity.general.name";
    public final static String opmEnvKey = "entity.opm.enviromental";
    public final static String opmPhyKey = "entity.opm.physical";


    public OpcatProperties() {
        super();
    }

    public Element getPropertiesElement(OpcatProperties.OpcatPropertiesGroups group, String elementName) {
        Element ret = new Element(elementName);
        for (Object key : keySet()) {
            StringTokenizer tok = new StringTokenizer(key.toString(), ".");
            String level = tok.nextToken();
            String type = tok.nextToken();
            if ((group == null) || group.toString().equalsIgnoreCase("all") || group.toString().equalsIgnoreCase(type)) {
                String value = getProperty(key.toString());
                if (value != null) {
                    value = value.trim();
                    if ((value.trim().length() > 1) && !value.equalsIgnoreCase("none")) {
                        Element prop = new Element("Property");
                        prop.setAttribute("key", key.toString());
                        if (key.toString().endsWith(".code") || key.toString().equalsIgnoreCase("entry.general.notes") || key.toString().equalsIgnoreCase(entryDescriptionKey) || key.toString().equalsIgnoreCase(entryURLKey) || key.toString().endsWith(entryIconNameKey)) {
                            CDATA cdata = new CDATA(getProperty(key.toString()));
                            Element val = new Element("value");
                            val.setContent(cdata);
                            prop.addContent(val);
                        } else {
                            prop.setAttribute("value", getProperty(key.toString()));
                        }
                        ret.addContent(prop);
                    }

                }
            }
        }

        return ret;
    }

    public void loadProperties(Element properties) {
        if (properties == null) return;
        List<Element> props = properties.getChildren("Property");
        for (Element prop : props) {
            String key = prop.getAttributeValue("key");
            if (key != null) {
                String value;
                Element valueElement = prop.getChild("value");
                if (valueElement != null) {
                    value = valueElement.getText();
                } else {
                    value = prop.getAttributeValue("value");
                }
                put(key, value);
            }
        }
    }

    public String getPropertiesString(OpcatProperties.OpcatPropertiesGroups group) {

        String ret = "";
        for (Object key : keySet()) {
            StringTokenizer tok = new StringTokenizer(key.toString(), ".");
            String level = tok.nextToken();
            String type = tok.nextToken();
            if ((group == null) || group.toString().equalsIgnoreCase("all") || group.toString().equalsIgnoreCase(type)) {
                String name = tok.nextToken();
                ret = ret + name + " : " + getProperty(key.toString()) + "\n";
            }
        }
        return ret;
    }

    public String getPropertiesHTML(OpcatProperties.OpcatPropertiesGroups group) {
        String ret = "<html><body>";
        ret = ret + "<table border=\"1\"><tr><th>Property Name</th><th>Property Group</th><th>Value</th></tr>";

        for (Object key : keySet()) {
            StringTokenizer tok = new StringTokenizer(key.toString(), ".");
            String level = tok.nextToken();
            String type = tok.nextToken();
            if ((group == null) || group.toString().equalsIgnoreCase("all") || group.toString().equalsIgnoreCase(type)) {
                String name = tok.nextToken();

                ret = ret + "<tr>";
                if (key.toString().equalsIgnoreCase(OpcatProperties.entryURLKey)) {
                    ret = ret + "<td>" + name + "</td> <td>" + type + "</td><td> <a href=\"" + getProperty(key.toString()) + "\">" + getProperty(key.toString()) + "</a></td>";
                } else {
                    //
                    ret = ret + "<td>" + name + "</td><td>" + type + "</td><td>" + getProperty(key.toString()) + "</td>";
                }
                ret = ret + "</tr>";
            }
        }
        return ret + "</table></body></html>";

    }

    public GridPanel getPropertiesGRIDPanel(OpcatProperties.OpcatPropertiesGroups group, String tabName) {
        ArrayList<OpcatProperties> list = new ArrayList<OpcatProperties>();
        list.add(this);
        return getPropertiesGRIDPanel(list, group, tabName);

    }

    public boolean hasMCURL() {


        if (Configuration.getInstance().getProperty("entry.url.name") != null) {
            String[] res = Configuration.getInstance().getProperty("entry.url.name").split(",");
            for (int i = 0; i < res.length; i++) {
                if (res[i].contains("repo")) {
                    if (getProperty(res[i]) != null) {
                        try {
                            if (OpcatMCManager.isConnectedToerver()) {
                                File model = OpcatExposeManager.getModelIntoOpcatTemp(getProperty(res[i]));
                                if (model.getName().endsWith(".opx") || model.getName().endsWith(".opz")) {
                                    return true;
                                }
                            }
                        } catch (Exception ex) {

                        }
                    }
                }
            }
        }
        return false;
    }

    public void ShowURL(String name) {


        if (Configuration.getInstance().getProperty("entry.url.name") != null) {
            String[] res = Configuration.getInstance().getProperty("entry.url.name").split(",");
            for (int i = 0; i < res.length; i++) {
                if (res[i].contains("repo")) {
                    if (getProperty(res[i]) != null) {
                        try {
                            if (OpcatMCManager.isConnectedToerver()) {
                                File model = OpcatExposeManager.getModelIntoOpcatTemp(getProperty(res[i]));
                                if (model.getName().endsWith(".opx") || model.getName().endsWith(".opz")) {
                                    FileControl.getInstance().runNewOPCAT(model, name);
                                } else {
                                    BrowserLauncher2.openURL("file://" + model.getPath());
                                }
                            } else {
                                JOptionPane.showMessageDialog(Opcat2.getFrame(), "Not connected to MC, Error while tring to open - " + res[i], "Warning", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (Exception ex) {

                        }
                    }
                }
            }
        }
    }


    public void ShowURL() {

        ShowURL("1");

    }


    public GridPanel getPropertiesGRIDPanel(ArrayList<OpcatProperties> list, OpcatProperties.OpcatPropertiesGroups group, String tabName) {
        ArrayList<String> cols = new ArrayList<String>();

        int numCols = 0;
        for (OpcatProperties props : list) {
            for (Object key : props.keySet()) {
                StringTokenizer tok = new StringTokenizer(key.toString(), ".");
                if (numCols < tok.countTokens()) numCols = tok.countTokens();
            }
        }
        switch (numCols) {
            case 1:
                cols.add("Level");
                cols.add("Value");
                break;
            case 2:
                cols.add("Level");
                cols.add("Group");
                cols.add("Value");
                break;
            case 3:
                cols.add("Level");
                cols.add("Group");
                cols.add("Name");
                cols.add("Value");
                break;
            case 4:
                cols.add("Level");
                cols.add("Group");
                cols.add("Sub Group");
                cols.add("Name");
                cols.add("Value");
                break;
        }
        GridPanel panel = new GridPanel(cols);

        panel.setTabName(tabName + " : Parameters");

        for (OpcatProperties props : list) {
            for (Object key : props.keySet()) {
                if ((group == null) || group.toString().equalsIgnoreCase("all")) {
                    Object[] row = new Object[cols.size()];
                    Object[] rowTag = new Object[2];
                    int i = 0;
                    StringTokenizer tok = new StringTokenizer(key.toString(), ".");
                    while (tok.hasMoreElements()) {
                        row[i] = tok.nextElement();
                        i++;
                    }
                    row[numCols] = props.getProperty((String) key);
                    rowTag[0] = key;
                    rowTag[1] = props.getProperty((String) key);
                    panel.getGrid().addRow(row, rowTag);
                }
            }
        }
        return panel;

    }

    public void copyFrom(OpcatProperties copy) {
        if (copy != null) {
            for (Object key : copy.keySet()) {
                if (copy.get(key) != null) {
                    put(key, copy.get(key));
                } else {
                    OpcatLogger.error("tring to copy null property of " + key, false);
                }
            }
        } else {
            OpcatLogger.error("tring to copy null properties", false);
        }
    }

    public void copyFrom(OpcatProperties copy, boolean withLimit) {
        if (copy != null) {
            ArrayList<String> ignore = new ArrayList<String>();
            if (Configuration.getInstance().getProperty("expose.copy.properties.ignorelist") != null) {
                StringTokenizer tok = new StringTokenizer(Configuration.getInstance().getProperty("expose.copy.properties.ignorelist"), ",");
                while (tok.hasMoreElements()) {
                    ignore.add(tok.nextToken());
                }
            }

            boolean doCopy;
            for (Object key : copy.keySet()) {
                if (copy.get(key) != null) {
                    if (withLimit) {
                        doCopy = true;
                        for (String ig : ignore) {
                            if (ig.equalsIgnoreCase(String.valueOf(copy.get(key)))) {
                                doCopy = false;
                                break;
                            }
                        }
                        if (doCopy) put(key, copy.get(key));
                    } else {
                        put(key, copy.get(key));
                    }
                } else {
                    OpcatLogger.error("tring to copy null property of " + key, false);
                }
            }
        } else {
            OpcatLogger.error("tring to copy null properties", false);
        }
    }
}
