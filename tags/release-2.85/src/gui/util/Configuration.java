/*
 * Created on 10/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @author eran
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Configuration {
    private static Configuration instance = null;

    private Properties properties;

    public final static String CONF_FILE_URL = "conf.txt";

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    protected Configuration() {
        this.properties = new Properties();

        try {
            FileInputStream in = new FileInputStream(CONF_FILE_URL);
            this.properties.load(in);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }
    }

    private String getProperty(String name) {
        return (String) this.properties.get(name);
    }

    public ListIterator getLastUsedFiles() throws OpcatException {
        ArrayList list = new ArrayList();
        if (this.getProperty("last_used_files") == null) {
            return list.listIterator();
        }
        StringTokenizer st = new StringTokenizer(
                this.getProperty("last_used_files"), ",");
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
                        .logError("[Configuration] No used files were found");
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

        try {
            FileOutputStream out = new FileOutputStream(CONF_FILE_URL);
            this.properties.store(out, "updated");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}