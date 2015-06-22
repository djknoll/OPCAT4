package gui.util;

import java.io.File;


/**
 * Created by raanan.
 * Date: Jun 20, 2010
 * Time: 6:30:04 PM
 */
public class OpcatFileFilter extends javax.swing.filechooser.FileFilter {


    String[] extensions;
    String description;
    boolean filesOnly = false;
    boolean directoriesOnly = false;

    public OpcatFileFilter(String ext) {
        this(new String[]{ext}, null, false, false);
    }

    public OpcatFileFilter(String[] exts, String descr, boolean filesOnly, boolean directoriesOnly) {
        // Clone and lowercase the extensions
        extensions = new String[exts.length];
        for (int i = exts.length - 1; i >= 0; i--) {
            extensions[i] = exts[i].toLowerCase();
        }
        // Make sure we have a valid (if simplistic) description
        description = (descr == null ? exts[0] + " files" : descr);
    }

    public boolean accept(File f) {
        // We always allow directories, regardless of their extension
        if (f.isDirectory() && !filesOnly) {
            return true;
        }

        // Ok, it a regular file, so check the extension
        if (!directoriesOnly) {
            String name = f.getName().toLowerCase();
            for (int i = extensions.length - 1; i >= 0; i--) {
                if (name.endsWith(extensions[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getDescription() {
        return description;
    }
}




