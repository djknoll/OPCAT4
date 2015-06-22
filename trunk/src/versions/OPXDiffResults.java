package versions;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by raanan.
 * Date: Apr 12, 2010
 * Time: 11:02:18 AM
 */
public class OPXDiffResults {
    HashMap<OPXID, OPXDiffResult> results = new HashMap<OPXID, OPXDiffResult>();

    public HashMap<OPXID, OPXDiffResult> getResultsMap() {
        return results;
    }

    public Collection<OPXDiffResult> getResults() {
        return results.values();
    }

    public OPXDiffResult getResult(OPXID uuid) {
        return results.get(uuid);
    }

    public void addAtomicResult(OPXID entity, OPXDiffResult result) {
        results.put(entity, result);
    }
}
