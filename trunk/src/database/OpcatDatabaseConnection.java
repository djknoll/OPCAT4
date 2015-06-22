package database;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import oracle.jdbc.pool.OracleDataSource;
import util.Configuration;
import util.OpcatLogger;

import javax.sql.DataSource;
import java.sql.Connection;

public class OpcatDatabaseConnection {

    private static OpcatDatabaseConnection instance = null;
    // private Connection connection = null;
    private static boolean valid = false;
    private static DataSource ds = null;
    private static Connection conn = null;

    public static boolean isOnLine() {
        return valid;
    }

    private OpcatDatabaseConnection() {
        if (ds == null) {
            try {

                String userName = "opcat";
                String password = "0545224014";

                String dbType = Configuration.getInstance().getProperty(
                        "DBtype");

                String driverMark = "DRIVERMARK_99999";
                String urlSeperatorMark = "URLMARK_99999";

                String database = Configuration.getInstance().getProperty(
                        "DBdatabasename");

                String server = Configuration.getInstance().getProperty(
                        "DBserver");

                String serverPort = Configuration.getInstance().getProperty(
                        "DBPort");

                String DBUrl = "jdbc:" + driverMark + server + ":" + serverPort
                        + urlSeperatorMark + database;

                if (dbType.toLowerCase().equalsIgnoreCase("mysql")) {
                    MysqlDataSource myds = new MysqlDataSource();
                    myds.setURL(DBUrl.replaceAll(driverMark, "mysql://")
                            .replaceAll(urlSeperatorMark, Configuration.getInstance().getProperty(
                                    "DBURLSeperator")));
                    myds.setPassword(password);
                    myds.setUser(userName);
                    OpcatLogger.info(myds.getURL(), false);
                    ds = myds;

                    conn = ds.getConnection();

                }

                if (dbType.toLowerCase().equalsIgnoreCase("oracle")) {
                    OracleDataSource ods = new OracleDataSource();
                    ods.setURL(DBUrl.replaceAll(driverMark, "oracle:thin:@")
                            .replaceAll(urlSeperatorMark, Configuration.getInstance().getProperty(
                                    "DBURLSeperator")));
                    ods.setPassword(password);
                    ods.setUser(userName);
                    OpcatLogger.info(ods.getURL(), false);
                    ds = ods;
                }

                if (dbType.toLowerCase().equalsIgnoreCase("oracle-def")) {
                    OracleDataSource ods = new OracleDataSource();
                    ods.setURL(DBUrl.replaceAll(driverMark, "oracle:@")
                            .replaceAll(urlSeperatorMark, Configuration.getInstance().getProperty(
                                    "DBURLSeperator")));
                    ods.setPassword(password);
                    ods.setUser(userName);
                    OpcatLogger.info(ods.getURL(), false);
                    ds = ods;
                }

                if (dbType.toLowerCase().equalsIgnoreCase("mssql")) {

                }

                if (ds == null)
                    throw new Exception("Unknown database driver");

                valid = true;

            } catch (CommunicationsException ex) {
                valid = false;
                OpcatLogger.warning(ex.getMessage(), false);
            } catch (Exception ex) {
                valid = false;
                OpcatLogger.error(ex);
            }
        }
    }

    public static OpcatDatabaseConnection getInstance() {
        if ((instance == null) || (!isOnLine())) {
            instance = new OpcatDatabaseConnection();
        }
        if (!isOnLine()) {
            //could not connect to database
            OpcatLogger.warning("Could not connect to database ", false);
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            //return ds.getConnection();
            return conn;
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
        return null;

    }


}
