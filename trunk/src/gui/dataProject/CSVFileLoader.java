package gui.dataProject;

import categories.OpcatCategory;
import com.csvreader.CsvReader;
import gui.opdProject.OpdProject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

public class CSVFileLoader extends AMetaLoader implements MetaLoader {

    private String name = " ";
    private Vector rows = new Vector();
    private Vector headers = new Vector();
    private int nameIndex = -1;
    private int coloringIndex = -1;
    private int idIndex = -1;
    private MetaStatus status = new MetaStatus();

    public CSVFileLoader(String filename) {
        this.filename = filename;
    }

    public void load(OpdProject project) {
        load();
    }

    public void load() {
        File file = new File(filename);

        if (!file.exists()) {
            status.setLoadFail(true);
            status.setFailReason("Failed to load " + file.getPath() + ",\nfile does not exists");
            return;
        }

        try {

            // file = new File("req.csv");

            BufferedReader bufRdr = new BufferedReader(new FileReader(file));
            String line = null;
            boolean skip = false;

            // First line is Type and Tab name
            if (file.getName().endsWith(".req")) {
                line = "Requirements";
            } else {
                line = bufRdr.readLine();
                skip = true;
                line = line.replace(",", " ");
            }

            name = (new StringTokenizer(line, "#")).nextToken();
            bufRdr.close();

            CsvReader reader = new CsvReader(file.getPath(), ',', Charset.defaultCharset());
            reader.setDelimiter(',');
            reader.setTextQualifier('"');
            reader.setUseTextQualifier(true);

            if (skip) {
                reader.skipLine(); // skip the name of tab line
            }

            reader.readHeaders();
            String[] hed = reader.getHeaders();

            for (int i = 0; i < hed.length; i++) {
                headers.add(hed[i]);
            }

            idIndex = -1;
            for (int i = 0; i < headers.size(); i++) {
                String locHed = (String) headers.get(i);
                if (locHed.equalsIgnoreCase("id") || locHed.equalsIgnoreCase("Object Identifier") || locHed.equalsIgnoreCase("Absolute Number") || locHed.contains("\"ID\"") || locHed.contains("\"id\"")) {
                    idIndex = i;
                    break;
                }
            }

            nameIndex = -1;
            for (int i = 0; i < headers.size(); i++) {
                if (((String) headers.get(i)).equalsIgnoreCase("name")) {
                    nameIndex = i;
                    break;
                }
            }
            coloringIndex = -1;
            for (int i = 0; i < headers.size(); i++) {
                if ((((String) headers.get(i)).toLowerCase().contains("level")) || (((String) headers.get(i)).toLowerCase().contains("color"))) {
                    coloringIndex = i;
                    break;
                }
            }

            while (reader.readRecord()) {
                Vector row = new Vector();

                boolean cancelRow = false;
                for (int i = 0; i < hed.length; i++) {
                    Object obj = reader.get(hed[i]);
                    // extID = <name of metadata>+realid
                    // this will keep the distinction
                    // between the diffrent metadata libs.
                    if (i == idIndex) {
                        if ((obj == null) || (obj.toString().equalsIgnoreCase(""))) {
                            cancelRow = true;
                            break;
                        }
                        //obj = name + " : " + obj.toString();
                        obj = obj.toString();

                    }

                    if (i == coloringIndex) {
                        if ((obj == null) || (obj.toString().equalsIgnoreCase(""))) {
                            cancelRow = true;
                            break;
                        }

                        obj = OpcatCategory.parseColorString(obj.toString());
                    }

                    if (i == nameIndex) {
                        if ((obj == null) || (obj.toString().equalsIgnoreCase(""))) {
                            cancelRow = true;
                            break;
                        }
                    }

                    if ((obj == null) || (obj.toString().equalsIgnoreCase(""))) {
                        obj = " ";
                    }

                    row.add(obj);
                }

                if (!cancelRow) {
                    this.rows.add(row);
                }

            }

            reader.close();
        } catch (Exception e) {
            status.setFailReason(e.getMessage());
        }

        if (idIndex == -1) {
            status.setLoadFail(true);
            status.setFailReason("No ID in file");
            return;
        }

        status.setLoadFail(false);

    }

    public Iterator getRowsIterator() {
        return Collections.list(this.rows.elements()).iterator();
    }

    public Vector getRowAt(int i) {
        return (Vector) this.rows.elementAt(i);
    }

    public Vector getHeaders() {
        return headers;
    }

    public int getSize() {
        return rows.size();
    }

    public int getColoringIndex() {
        return coloringIndex;
    }

    public String getName() {
        return name;
    }

    public boolean hasColoringData() {
        return coloringIndex > -1;
    }

    public boolean hasIDData() {
        return idIndex > -1;
    }

    public boolean hasNameData() {
        return nameIndex > -1;
    }

    public Color getColoringData(Vector row) {
        try {
            if (hasColoringData()) {
                return (Color) row.elementAt(coloringIndex);
            } else {
                return Color.WHITE;
            }
        } catch (Exception ex) {
            return Color.WHITE;
        }
    }

    public String getExtID(Vector row) {
        if (hasIDData()) {
            return (String) row.elementAt(idIndex);
            //return name + " " + (NAME) row.elementAt(idIndex);
        } else {
            return "1";
        }
    }

    public String getName(Vector row) {
        if (hasNameData()) {
            return (String) row.elementAt(nameIndex);
        } else {
            return " ";
        }
    }

    public MetaStatus getStatus() {
        return status;
    }

    public String getPath() {
        return filename;
    }

    public boolean isShowColorValueAsPrograssBar() {
        return false;
    }
}
