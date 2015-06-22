package modelControl;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNConflictHandler;
import org.tmatesoft.svn.core.wc.SVNConflictDescription;
import org.tmatesoft.svn.core.wc.SVNConflictResult;

public class OpcatMCConflictHandler implements ISVNConflictHandler {

	@Override
	public SVNConflictResult handleConflict(
			SVNConflictDescription conflictDescription) throws SVNException {

		return null;
	}
}
