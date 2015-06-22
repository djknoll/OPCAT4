package gui.dataBase;

import java.sql.ResultSet;
import gui.controls.FileControl;
import gui.opdProject.OpdProject;
import gui.util.OpcatLogger;

public class Versions {

    //private static HashMap<Long, OpdProject> loadedVersions = new HashMap<Long, OpdProject>();

    private Versions() {

    }

    public void loadLatestVersionToMemoryDB(OpdProject project) {

    }

    public static void saveNewVersion(OpdProject project) {
	try {
	    DataBase database = DataBase.getInstance();
	    database.commitProjectToDB(project);

	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}
    }

    public void loadVersionToMemoryDB(long version, OpdProject project) {

    }

    public static long getVersionID(OpdProject project) {
	try {
	    if (FileControl.getInstance().canExitWithoutSave()) {
		DataBase database = DataBase.getInstance();
		ResultSet rs = database.executeSQLOnFileDB(
			"SELECT MAX(versionid) from "
				+ DataBase.REVISIONS_TABLE_NAME
				+ " Where modelid = '" + project.getGlobalID()
				+ "'", project);
		rs.last();
		if (rs.getRow() > 0) {
		    rs.first();
		    if (rs.getObject(1) != null) {
			return rs.getInt(1);
		    } else {
			return -1;
		    }
		} else {
		    return -1;
		}
	    } else {
		return -1;
	    }

	} catch (Exception ex) {
	    return -1;
	}
    }

    public static long getLastVersionID(OpdProject project) {
	try {
	    // if (FileControl.getInstance().canExitWithoutSave()) {
	    DataBase database = DataBase.getInstance();
	    ResultSet rs = database.executeSQLOnFileDB(
		    "SELECT MAX(versionid) from "
			    + DataBase.REVISIONS_TABLE_NAME
			    + " Where modelid = '" + project.getGlobalID()
			    + "'", project);
	    rs.last();
	    if (rs.getRow() > 0) {
		rs.first();
		if (rs.getObject(1) != null) {
		    return rs.getInt(1);
		} else {
		    return -1;
		}
	    } else {
		return -1;
	    }
	    // } else {
	    // return -1;
	    // }

	} catch (Exception ex) {
	    return -1;
	}
    }
}
