package gui.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.JOptionPane;
import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXRelationInstance;

import gui.Opcat2;
import gui.controls.FileControl;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.Entry;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkInstance;
import gui.util.OpcatLogger;

public class DataBase {

    private static Connection fileconn = null;

    private static Connection memconn = null;

    //private final static String SCHEME = "PUBLIC";

    public final static String OPDS_TABLE_NAME = "opds";

    public final static String INSTANCES_TABLE_NAME = "instances";

    public final static String ENTRIES_TABLE_NAME = "entries";

    public final static String MODEL_TABLE_NAME = "model";

    public final static String REVISIONS_TABLE_NAME = "version";

    public final static String LINKS_DETAIL_TABLE = "links";

    private HashMap<Long, String> loadedProjects = new HashMap<Long, String>();

    private static DataBase database = new DataBase();

    private DataBase() {

    }

    public static DataBase getInstance() {
	try {
	    if (fileconn != null) {
		fileconn.commit();
		fileconn.close();
	    }
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}
	return database;
    }

    public void commitProjectToDB(OpdProject project) {
	try {
	    if (memconn != null)
		memconn.close();
	    memconn = null;
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

	lazyinit();
	// cretae the tables for this model
	createTables(fileconn, project, true);
	createTables(memconn, project, false);

	// fill data in model tables
	fillData(fileconn, project, Versions.getLastVersionID(project));
	fillData(memconn, project, Versions.getLastVersionID(project));

	// add project to loaded projects in DB
	loadedProjects.put(new Long(Versions.getLastVersionID(project)),
		project.getGlobalID());

    }

    public void updateInMemoryProject(OpdProject project) {
	try {
	    if (memconn != null)
		memconn.close();
	    memconn = null;
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

	lazyinit();

	createTables(memconn, project, false);

	fillData(memconn, project, Versions.getVersionID(project));

	// add project to loaded projects in DB
	loadedProjects.put(new Long(Versions.getLastVersionID(project)),
		project.getGlobalID());

    }

    public void loadProjectVersionToInMemory(long version, String projectID) {

    }

    private static void lazyinit() {
	try {
	    if ((fileconn == null) || (fileconn.isClosed())) {
		try {
		    Class.forName("org.hsqldb.jdbcDriver");
		    fileconn = DriverManager.getConnection("jdbc:hsqldb:file:"
			    + FileControl.getInstance().getOPCATDirectory()
			    + FileControl.fileSeparator
			    + "opcatDB;shutdown=true", "sa", "");
		} catch (Exception ex) {
		    JOptionPane.showMessageDialog(Opcat2.getFrame(), ex
			    .getMessage(), "OPCAT II",
			    JOptionPane.ERROR_MESSAGE);
		    // System.exit(1);
		}
	    }

	    if ((memconn == null) || (memconn.isClosed())) {
		try {
		    Class.forName("org.hsqldb.jdbcDriver");
		    memconn = DriverManager.getConnection(
			    "jdbc:hsqldb:mem:opcat", "sa", "");
		} catch (Exception ex) {
		    JOptionPane.showMessageDialog(Opcat2.getFrame(), ex
			    .getMessage(), "OPCAT II",
			    JOptionPane.ERROR_MESSAGE);
		    // System.exit(1);
		}
	    }
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}
    }

    public String getTableName(OpdProject project, String tablename) {

	// String myPrefix = project.getGlobalID().replace(".", "_");
	// myPrefix = myPrefix.replace(":", "_");
	// myPrefix = myPrefix.replace("/", "_");
	// myPrefix = "p_" + myPrefix;
	//
	// return myPrefix + tablename;

	return tablename;

    }

    private void createTables(Connection conn, OpdProject project,
	    boolean generateVersionID) {

	String modelid = project.getGlobalID();

	try {

	    // revisions table
	    String tablename = getTableName(project, REVISIONS_TABLE_NAME);
	    String createSQL = "CREATE TABLE " + tablename
		    + "( modelid VARCHAR(50) , versionid IDENTITY)";
	    create(conn, tablename, createSQL, null, false);

	    String sql;

	    if (generateVersionID) {
		// get current revision
		sql = "INSERT INTO " + REVISIONS_TABLE_NAME
			+ "(modelid) VALUES ('" + modelid + "')";
		update(conn, sql);

		// sql = "CALL IDENTITY()";
		// Statement sqlStatement = conn.createStatement();
		// ResultSet rsGeneratedID = sqlStatement.executeQuery(sql);
		// rsGeneratedID.next();
		// this.versionid = rsGeneratedID.getInt(1);
	    }

	    // SYSTEM TABLE
	    tablename = getTableName(project, MODEL_TABLE_NAME);
	    createSQL = "CREATE TABLE " + tablename
		    + "( id IDENTITY, extid VARCHAR(50) , name VARCHAR(100))";
	    String deleteSQL = "DELETE FROM " + tablename + " WHERE extid  = '"
		    + project.getGlobalID() + "'";
	    create(conn, tablename, createSQL, deleteSQL, false);

	    // OPDS Table
	    tablename = getTableName(project, OPDS_TABLE_NAME);
	    createSQL = "CREATE TABLE "
		    + tablename
		    + "( id IDENTITY, versionid BIGINT, extid BIGINT, mainentryID BIGINT, maininstanceId VARCHAR(256), name VARCHAR(256))";
	    deleteSQL = "DELETE FROM " + tablename + " WHERE versionid = "
		    + Versions.getLastVersionID(project);
	    create(conn, tablename, createSQL, deleteSQL, false);

	    // ENTRIES Table
	    tablename = getTableName(project, ENTRIES_TABLE_NAME);
	    createSQL = "CREATE TABLE "
		    + tablename
		    + "( id IDENTITY, versionid BIGINT, extid BIGINT, name VARCHAR(256), opmtype VARCHAR(30), type VARCHAR(30), description VARCHAR(256), isEnvironmental BOOLEAN, isPhysical BOOLEAN )";
	    deleteSQL = "DELETE FROM " + tablename + " WHERE versionid = "
		    + Versions.getLastVersionID(project);
	    create(conn, tablename, createSQL, deleteSQL, false);

	    // INSTANCES TABLE
	    tablename = getTableName(project, INSTANCES_TABLE_NAME);
	    createSQL = "CREATE TABLE "
		    + tablename
		    + "( id IDENTITY, versionid BIGINT,key VARCHAR(256) , extid BIGINT, entryid BIGINT, opdid BIGINT, type VARCHAR(30) )";
	    deleteSQL = "DELETE FROM " + tablename + " WHERE versionid = "
		    + Versions.getLastVersionID(project);
	    create(conn, tablename, createSQL, deleteSQL, false);

	    tablename = getTableName(project, "changes");
	    createSQL = "CREATE TABLE "
		    + tablename
		    + "(id IDENTITY, versionid BIGINT,  instancekey VARCHAR(256) , type VARCHAR(30), timestamp TIMESTAMP )";
	    create(conn, tablename, createSQL, null, false);

	    tablename = getTableName(project, LINKS_DETAIL_TABLE);
	    createSQL = "CREATE TABLE "
		    + tablename
		    + "(versionid BIGINT, linkkey VARCHAR(256),  sourcekey VARCHAR(256) , destinationkey VARCHAR(256) )";
	    create(conn, tablename, createSQL, null, false);

	    String ins = getTableName(project, INSTANCES_TABLE_NAME);
	    String opd = getTableName(project, OPDS_TABLE_NAME);
	    String ent = getTableName(project, ENTRIES_TABLE_NAME);

	    sql = "CREATE INDEX " + opd + "_versionid_opd_id_idx ON " + opd
		    + " ( versionid,extid ) ";
	    create(conn, opd + "_versionid_opd_id_idx", sql, null, true);

	    sql = "CREATE INDEX " + ent
		    + "_versionid_opd_id_idx_ent_id_idx  ON " + ent
		    + " ( versionid,extid ) ";
	    create(conn, ent + "_versionid_opd_id_idx_ent_id_idx", sql, null,
		    true);

	    sql = "CREATE INDEX " + ins
		    + "_versionid_instance_opdid_entryid_idx ON " + ins
		    + " (versionid, opdid, entryid) ";
	    create(conn, ins + "_versionid_instance_opdid_entryid_idx", sql,
		    null, true);

	    sql = "CREATE INDEX " + ins + "_versionid_instance_key_idx ON "
		    + ins + " (versionid,key) ";
	    create(conn, ins + "_versionid_instance_key_idx", sql, null, true);

	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

    }

    private void create(Connection conn, String tablename, String createSQL,
	    String updateSQL, boolean index) {

	try {

	    ResultSet tables;
	    if (index) {
		tables = conn.getMetaData().getIndexInfo(null, null, null,
			false, false);
	    } else {
		tables = conn.getMetaData().getTables(null, null, null, null);
	    }

	    tables.last();

	    boolean found = false;
	    tables.beforeFirst();
	    while (tables.next()) {
		String str;
		if (index) {
		    str = tables.getString(6);
		} else {
		    str = tables.getString(3);
		}
		if (str.equalsIgnoreCase(tablename)) {
		    found = true;
		    break;
		}
	    }

	    if (found) {
		if (updateSQL != null) {
		    update(conn, updateSQL);
		}
	    } else {
		if (createSQL != null) {
		    update(conn, createSQL);
		}
	    }
	    tables.close();
	    tables = null;

	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

    }

    // use for SQL commands CREATE, DROP, INSERT and UPDATE
    public synchronized void update(Connection conn, String expression)
	    throws SQLException {
	Statement st = null;
	st = conn.createStatement(); // statements
	int i = st.executeUpdate(expression); // run the query
	if (i == -1) {
	    OpcatLogger.logError("db error : " + expression);
	}
	st.close();
    }

    private void fillData(Connection conn, OpdProject project, long versionid) {

	try {
	    Statement st = null;

	    // opds
	    st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_READ_ONLY);

	    Enumeration<Opd> opds = project.getSystemStructure().getOpds();

	    String sql;

	    sql = "INSERT INTO " + getTableName(project, MODEL_TABLE_NAME)
		    + " (extid, name) VALUES (" + "'" + project.getGlobalID()
		    + "','" + project.getName() + "')";
	    st.executeUpdate(sql);

	    while (opds.hasMoreElements()) {
		Opd opd = opds.nextElement();

		if (opd.getMainInstance() != null) {
		    sql = "INSERT INTO "
			    + getTableName(project, OPDS_TABLE_NAME)
			    + " (versionid,  extid,mainentryID, maininstanceId,name ) VALUES ( "
			    + versionid + "," + opd.getOpdId() + " , "
			    + opd.getMainEntry().getId() + ",'"
			    + opd.getMainInstance().getKey() + "','"
			    + opd.getName() + "')";

		} else {
		    sql = "INSERT INTO "
			    + getTableName(project, OPDS_TABLE_NAME)
			    + " ( versionid, extid,mainentryID, maininstanceId,name ) VALUES ( "
			    + versionid + "," + opd.getOpdId() + " , " + "NULL"
			    + "," + "NULL" + ",'" + opd.getName() + "')";
		}
		st.executeUpdate(sql);

		// "( id VARCHAR(256) , entryid BIGINT, opdid BIGINT, name
		// VARCHAR(256), type VARCHAR(30) )"
		Enumeration<Instance> instances = project.getSystemStructure()
			.getInstancesInOpd(opd.getOpdId());
		while (instances.hasMoreElements()) {
		    Instance instance = instances.nextElement();
		    // id BIGINT , entryid BIGINT, opdid BIGINT, name
		    // VARCHAR(256), type VARCHAR(30) )"

		    sql = "INSERT INTO "
			    + getTableName(project, INSTANCES_TABLE_NAME)
			    + " (versionid, key,extid,entryid, opdid, type ) VALUES ( "
			    + versionid + ",'" + instance.getKey().toString()
			    + "', " + instance.getKey().getEntityInOpdId()
			    + " ," + instance.getEntry().getId() + ","
			    + instance.getKey().getOpdId() + ",'"
			    + instance.getTypeString() + "')";

		    st.executeUpdate(sql);
		    if ((instance instanceof LinkInstance)
			    || (instance instanceof GeneralRelationInstance)
			    || (instance instanceof FundamentalRelationInstance)) {
			OpdKey destinationKey;
			OpdKey sourceKey;

			if (instance instanceof LinkInstance) {
			    destinationKey = ((LinkInstance) instance)
				    .getDestinationInstance().getKey();
			    sourceKey = ((LinkInstance) instance)
				    .getSourceInstance().getKey();
			} else {
			    destinationKey = ((IXRelationInstance) instance)
				    .getDestinationIXInstance().getKey();
			    sourceKey = ((IXRelationInstance) instance)
				    .getSourceIXInstance().getKey();
			}
			sql = "INSERT INTO "
				+ getTableName(project, LINKS_DETAIL_TABLE)
				+ " (versionid , linkkey ,  sourcekey  , destinationkey ) VALUES ( "
				+ versionid + ",'"
				+ instance.getKey().toString() + "','"
				+ sourceKey + "','" + destinationKey + "')";

			st.executeUpdate(sql);
		    }
		}

	    }

	    Enumeration<Entry> entries = project.getSystemStructure()
		    .getElements();
	    while (entries.hasMoreElements()) {
		Entry entry = entries.nextElement();

		// "( id BIGINT, name VARCHAR(256), type VARCHAR(30))";
		sql = "INSERT INTO "
			+ getTableName(project, ENTRIES_TABLE_NAME)
			+ " ( versionid, extid,name, opmtype, type, description, isEnvironmental, isPhysical) VALUES ( "
			+ versionid + "," + entry.getId() + " ,'"
			+ entry.getName() + "','"
			+ entry.getLogicalEntity().getTypeString() + "','"
			+ entry.getTypeString() + "','"
			+ entry.getDescription() + "',"
			+ entry.isEnvironmental() + ","
			+ entry.getLogicalEntity().isPhysical() + ")";
		st.executeUpdate(sql);
	    }

	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}
    }

    public ResultSet testDB_getInstances(OpdProject project, long versionid) {

	try {
	    updateInMemoryProject(project);
	    String ins = getTableName(project, INSTANCES_TABLE_NAME);
	    String opd = getTableName(project, OPDS_TABLE_NAME);
	    String ent = getTableName(project, ENTRIES_TABLE_NAME);

	    Statement st = memconn.createStatement(
		    ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_READ_ONLY);

	    String sql = "SELECT DISTINCT " + opd + ".name as \"OPD Name\", "
		    + ent + ".name," + ins + ".*" + ", " + ent + ".opmtype"
		    + " FROM " + ins + " LEFT JOIN " + opd + " ON " + ins
		    + ".opdid = " + opd + ".extid" + " LEFT JOIN " + ent
		    + " ON " + ins + ".entryid = " + ent + ".extid" + " WHERE "
		    + ent + ".versionid = " + versionid + " AND " + ins
		    + ".versionid = " + versionid + " AND " + opd
		    + ".versionid = " + versionid;

	    // System.out.println(sql);
	    st.execute(sql);
	    // System.out.println("done");

	    return st.getResultSet();
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	    return null;
	}

    }

    public ResultSet testDB_Counting(OpdProject project, long versionid) {

	try {
	    updateInMemoryProject(project);
	    String ins = getTableName(project, INSTANCES_TABLE_NAME);
	    String opd = getTableName(project, OPDS_TABLE_NAME);
	    String ent = getTableName(project, ENTRIES_TABLE_NAME);

	    Statement st = memconn.createStatement(
		    ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_READ_ONLY);
	    String sql = "SELECT " + opd
		    + ".name as \"OPD Name\", COUNT(DISTINCT " + ent
		    + ".type) AS \"Things Type Count\" " + ",COUNT(DISTINCT "
		    + ent + ".extid) AS \"Entries Count\" " + " FROM " + ins
		    + " LEFT JOIN " + opd + " ON " + ins + ".opdid = " + opd
		    + ".extid" + " LEFT JOIN " + ent + " ON " + ins
		    + ".entryid = " + ent + ".extid" + " WHERE " + ent
		    + ".type IN ('Process', 'Object') AND " + ent
		    + ".versionid = " + versionid + " AND " + ins
		    + ".versionid = " + versionid + " AND " + opd
		    + ".versionid = " + versionid + " GROUP BY " + opd
		    + ".name";

	    // System.out.println(sql);
	    st.execute(sql);
	    // System.out.println("done");
	    // HAVING " + ent + ".versionid = " + versionid);

	    return st.getResultSet();
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	    return null;
	}

    }

    public ResultSet executeSQLOnFileDB(String sql, OpdProject project) {
	try {
	    lazyinit();

	    Statement st = fileconn.createStatement(
		    ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_READ_ONLY);

	    // System.out.println(sql);
	    st.execute(sql);
	    // System.out.println("done");
	    return st.getResultSet();
	} catch (Exception ex) {
	    // OpcatLogger.logError(ex);
	    return null;
	}

    }

    public ResultSet executeSQLOnMemory(String sql, OpdProject project) {
	try {
	    lazyinit();
	    updateInMemoryProject(project);

	    Statement st = memconn.createStatement(
		    ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_READ_ONLY);

	    // System.out.println(sql);
	    st.execute(sql);
	    // System.out.println("done");
	    return st.getResultSet();
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	    return null;
	}

    }

    public ResultSet testDB_Select(OpdProject project, long versionid) {

	try {
	    updateInMemoryProject(project);

	    String ins = getTableName(project, INSTANCES_TABLE_NAME);
	    String opd = getTableName(project, OPDS_TABLE_NAME);
	    String ent = getTableName(project, ENTRIES_TABLE_NAME);

	    Statement st = memconn.createStatement(
		    ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_READ_ONLY);

	    String sql = "SELECT DISTINCT " + opd + ".name as \"OPD Name\", "
		    + ent + ".name," + ins + ".*" + ", " + ent + ".opmtype"
		    + " FROM " + ins + " LEFT JOIN " + opd + " ON " + ins
		    + ".opdid = " + opd + ".extid" + " LEFT JOIN " + ent
		    + " ON " + ins + ".entryid = " + ent + ".extid "
		    + " WHERE " + ent + ".opmtype IN ('Agent Link') " + " AND "
		    + ent + ".versionid = " + versionid + " AND " + ins
		    + ".versionid = " + versionid + " AND " + opd
		    + ".versionid = " + versionid;

	    // System.out.println(sql);
	    st.execute(sql);
	    // System.out.println("done");
	    return st.getResultSet();
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	    return null;
	}

    }

    public Connection getMemoryConnection() {
	lazyinit();
	return memconn;
    }

}
