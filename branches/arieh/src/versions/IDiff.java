package versions;

import java.io.File;

/**
 * Created by raanan.
 * Date: Apr 12, 2010
 * Time: 10:56:59 AM
 */
public interface IDiff {
    public OPXDiffResult diff(File opxFile1, File opxFile2);
}
