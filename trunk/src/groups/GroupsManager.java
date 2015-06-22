package groups;

import database.Lookupable;
import database.OpcatDatabaseConnection;
import database.OpcatDatabaseLookupDAO;
import gui.opdProject.OpdProject;
import gui.opx.Loader;
import gui.projectStructure.Entry;
import modelControl.OpcatMCManager;
import user.OpcatUser;
import util.OpcatLogger;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Dec 23, 2010
 * Time: 12:37:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroupsManager {

    private static final String TABLE_NAME = "groups";
    private static final String REMOVE_FILE = "delete * from groups_data where GROUP_ID = ? AND FILE_PATH = ?";
    private static final String ADD_FILE = "insert into groups_data (GROUP_ID, FILE_PATH) values(? , ?) ";

    static OpcatDatabaseLookupDAO lookup = new OpcatDatabaseLookupDAO(TABLE_NAME);


    public static OpcatGroup getGroup(String groupName) {
        try {
            Lookupable dao = lookup.getByName(groupName);
            if (dao != null)
                return new OpcatGroup(dao.getName(), dao.getDescription(), dao.getColor(), dao.getId());
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }
        return null;
    }

    public static OpcatGroup getGroup(int ID) {

        OpcatDatabaseLookupDAO lookup = new OpcatDatabaseLookupDAO("groups");
        try {
            Lookupable dao = lookup.getByID(ID);
            if (dao != null)
                return new OpcatGroup(dao.getName(), dao.getDescription(), dao.getColor(), dao.getId());
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }
        return null;
    }

    public static ArrayList<OpcatGroup> getAllGroups() {
        ArrayList<OpcatGroup> ret = new ArrayList<OpcatGroup>();
        try {
            List<Lookupable> look = (List<Lookupable>) lookup.getAll();
            for (Lookupable daoLook : look) {
                OpcatGroup group = new OpcatGroup(daoLook.getName(), daoLook.getDescription(), daoLook.getColor(), daoLook.getId());
                ret.add(group);
            }
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }
        return ret;
    }

    public static OpcatGroup updateGroup(OpcatGroup group) {

        try {
            return getGroup(lookup.update(group.getId(), group.getName(), group.getDescription(), group.getColor()).getId());
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }
        return null;
    }

    public static OpcatGroup createGroup(String groupName, String groupDescription, int color) {
        try {
            return getGroup(lookup.create(groupName, groupDescription, color).getId());
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }
        return null;
    }

    public static OpcatGroup createGroup(String groupName, String groupDescription) {
        try {
            return getGroup(lookup.create(groupName, groupDescription, 0).getId());
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }
        return null;
    }

    public static void connectToGroup(OpcatGroup group, ArrayList<String> files) {
        try {
            OpcatDatabaseConnection.getInstance().getConnection().setAutoCommit(false);
            //insert into data base.
            for (String opx : files) {
                //if (opx.isFile()) {
                removeFromGroup(group, opx);
                //add to group
                PreparedStatement ps = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                        "INSERT INTO  groups_data (GROUP_ID, FILE_PATH) VALUES( " + group.getId() + ",'" + opx + "')");
                ps.executeUpdate();
                OpcatDatabaseConnection.getInstance().getConnection().commit();
                //}
            }
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        } finally {
            try {
                OpcatDatabaseConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException e1) {
                OpcatLogger.error(e1, true);
            }
        }


    }

    public static void removeFromGroup(OpcatGroup group, String file) {
        ArrayList<String> files = new ArrayList<String>();
        files.add(file);
        removeFromGroup(group, files);
    }


    public static void removeFromGroup(OpcatGroup group, ArrayList<String> files) {

        for (String file : files) {
            //remove from group.
            try {
                PreparedStatement ps = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                        "DELETE FROM groups_data where GROUP_ID = " + group.getId() + " AND FILE_PATH = '" + file + "'");
                ps.executeUpdate();
            } catch (SQLException e) {
                OpcatLogger.error(e, false);
            }
        }


    }

    public static ArrayList<String> getConnectedFilesByID(int groupID) {
        ArrayList<String> ret = new ArrayList<String>();

        try {
            PreparedStatement ps = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement("Select * from groups_data where GROUP_ID = " + groupID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("FILE_PATH"));
            }
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }

        return ret;
    }

    public static void removeGroup(OpcatGroup group) {
        try {
            PreparedStatement ps = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement("delete from groups where ID = " + group.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }
    }


    public static ArrayList<OpcatGroupExport> getAllExports(OpcatGroup group, boolean includeEntities) {
        ArrayList<OpcatGroupExport> ret = new ArrayList<OpcatGroupExport>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            PreparedStatement psExports = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                    "select * " +
                            "from groups_exports where GROUP_ID = " + group.getId() + " order by EXPORT_TIME desc ");
            ResultSet exports = psExports.executeQuery();
            while (exports.next()) {
                OpcatGroupExport groupExport = new OpcatGroupExport(exports.getInt("EXPORT_ID"), exports.getInt("GROUP_ID"), exports.getTimestamp("EXPORT_TIME"), exports.getString("USER_NAME"), exports.getBoolean("IS_CURRENT"));
                if (includeEntities) {
                    PreparedStatement ps = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                            "select * " +
                                    "from groups_export_data " +
                                    "where EXPORT_ID =  " + exports.getInt("EXPORT_ID"));
                    ResultSet rs = ps.executeQuery();
                    ArrayList<OpcatGroupEntityExport> entities = new ArrayList<OpcatGroupEntityExport>();
                    while (rs.next()) {
                        entities.add(new OpcatGroupEntityExport(rs.getString("ENTITY_NAME"), rs.getString("ENTITY_TEXT"), rs.getString("ENTITY_ID"), rs.getString("FILE_PATH")));
                    }
                    groupExport.setEntities(entities);
                }
                ret.add(groupExport);
            }
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }

        return ret;
    }

    public static OpcatGroupExport getExport(int exportID, boolean includeEntities) {
        try {
            PreparedStatement psExports = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                    "select * " +
                            "from groups_exports where EXPORT_ID = " + exportID + " order by EXPORT_TIME desc ");
            ResultSet exports = psExports.executeQuery();
            while (exports.next()) {
                OpcatGroupExport groupExport = new OpcatGroupExport(exports.getInt("EXPORT_ID"), exports.getInt("GROUP_ID"), exports.getTimestamp("EXPORT_TIME"), exports.getString("USER_NAME"), exports.getBoolean("IS_CURRENT"));
                if (includeEntities) {
                    PreparedStatement ps = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                            "select * " +
                                    "from groups_export_data " +
                                    "where EXPORT_ID =  " + exports.getInt("EXPORT_ID"));
                    ResultSet rs = ps.executeQuery();
                    ArrayList<OpcatGroupEntityExport> entities = new ArrayList<OpcatGroupEntityExport>();
                    while (rs.next()) {
                        entities.add(new OpcatGroupEntityExport(rs.getString("ENTITY_NAME"), rs.getString("ENTITY_TEXT"), rs.getString("ENTITY_ID"), rs.getString("FILE_PATH")));
                    }
                    groupExport.setEntities(entities);
                }
                return groupExport;
            }
        } catch (SQLException e) {
            OpcatLogger.error(e, false);
        }

        return null;
    }


    public static int exportGroup(OpcatGroup group) {
        int exportID = -1;
        //get file lists
        try {
            //get files in group
            PreparedStatement psExports = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                    "select * " +
                            "from groups_data where GROUP_ID = " + group.getId());

            ResultSet files = psExports.executeQuery();

            //for each file in group
            while (files.next()) {
                String filePath = files.getString("FILE_PATH");
                //export the file
                File model = OpcatMCManager.getInstance().doExportToTempDir(filePath);

                if ((model != null) && model.isFile()) {
                    //load the file into a new project or process with xml
                    Loader ld = new gui.opx.Loader(model.getPath());
                    InputStream is = null;
                    if (model.getName().endsWith(".opz")) {
                        is = new GZIPInputStream(new FileInputStream(model), 4096);
                    } else {
                        is = new BufferedInputStream(new FileInputStream(model), 4096);
                    }
                    if (is != null) {
                        OpdProject project = ld.load(new JDesktopPane(), is, null, false);
                        if (project != null) {

                            //got the project
                            project.setPath(model.getPath());
                            project.close();
                            is.close();

                            //create export line
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                            //create new export in database
                            PreparedStatement temp0 = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                                    "insert into groups_exports (GROUP_ID, USER_NAME, EXPORT_TIME, IS_CURRENT) VALUES ("
                                            + group.getId()
                                            + ",'" + OpcatUser.getCurrentUser().getName()
                                            + "','" + sdf.format(cal.getTime())
                                            + "',1" + ")");
                            temp0.executeUpdate();

                            //get the new export ID
                            PreparedStatement temp = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                                    "select MAX(EXPORT_ID) as MAX_ID from groups_exports");
                            ResultSet rs = temp.executeQuery();

                            if (rs.next()) {
                                exportID = rs.getInt("MAX_ID");

                                //loop on entities
                                Enumeration<Entry> entries = project.getSystemStructure().getAllElements();
                                while (entries.hasMoreElements()) {
                                    Entry entry = entries.nextElement();

                                    String text = entry.getProperty("entry.general.notes");

                                    if ((text != null) && !(text.trim().equalsIgnoreCase("")) && !(text.trim().equalsIgnoreCase("none"))) {
                                        //save export line into databse
                                        PreparedStatement temp1 = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                                                "insert into groups_export_data (EXPORT_ID, FILE_PATH, ENTITY_NAME, ENTITY_TEXT,ENTITY_ID) VALUES ("
                                                        + exportID
                                                        + ",'" + filePath
                                                        + "','" + entry.getName()
                                                        + "','" + text
                                                        + "','" + entry.getUUID()
                                                        + "')");
                                        temp1.executeUpdate();
                                    }
                                }


                                //set the export line as active
                                PreparedStatement temp2 = OpcatDatabaseConnection.getInstance().getConnection().prepareStatement(
                                        "update groups_exports SET IS_CURRENT = 0 WHERE EXPORT_ID <> " + exportID + " AND GROUP_ID = " + group.getId());
                                temp2.executeUpdate();

                                //return export id
                            } else {
                                //problam creating export line
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            OpcatLogger.error(e, false);
        }

        //create export line

        //return export id
        return exportID;
    }


}
