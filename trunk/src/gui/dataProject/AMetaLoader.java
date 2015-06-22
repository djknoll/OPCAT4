package gui.dataProject;

import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNURL;

import java.io.File;

/**
 * Created by raanan.
 * Date: 04/06/11
 * Time: 12:35
 */
public class AMetaLoader {
    SVNURL repoPath = null;
    String filename = " ";


    public String getID() {

        String id = filename;
        File file = new File(filename);
        if (file.exists()) {
            if (repoPath == null) {
                repoPath = OpcatMCManager.getInstance(true).getFileURL(new File(filename));
                if (repoPath != null) {
                    id = repoPath.getURIEncodedPath();
                }
            }
        }
        return id;
    }


}
