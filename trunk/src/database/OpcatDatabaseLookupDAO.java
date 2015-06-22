package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpcatDatabaseLookupDAO implements LookupDAO {

    String tableName;

    public OpcatDatabaseLookupDAO(String tableName) {
        super();
        this.tableName = tableName;
    }

    @Override
    public List<? extends Lookupable> getAll() throws SQLException {
        List<Lookupable> list = new ArrayList<Lookupable>();
        ResultSet rs;
        PreparedStatement ps = OpcatDatabaseConnection.getInstance()
                .getConnection().prepareStatement("select * from " + tableName + " order by name asc");
        rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new LookupImpl(rs
                    .getInt(OpcatDatabaseConstants.FIELD_NAME_ID), rs
                    .getString(OpcatDatabaseConstants.FIELD_NAME_NAME), rs
                    .getString(OpcatDatabaseConstants.FIELD_NAME_DESCRIPTION),
                    rs.getInt(OpcatDatabaseConstants.FIELD_NAME_COLOR)));
        }
        rs.close();
        return list;

    }

    @Override
    public Lookupable getByID(int ID) throws SQLException {

        ResultSet rs;
        PreparedStatement ps = OpcatDatabaseConnection.getInstance()
                .getConnection().prepareStatement("select * from " + tableName + " where ID = " + ID);
        rs = ps.executeQuery();
        while (rs.next()) {
            return new LookupImpl(rs
                    .getInt(OpcatDatabaseConstants.FIELD_NAME_ID), rs
                    .getString(OpcatDatabaseConstants.FIELD_NAME_NAME), rs
                    .getString(OpcatDatabaseConstants.FIELD_NAME_DESCRIPTION),
                    rs.getInt(OpcatDatabaseConstants.FIELD_NAME_COLOR));
        }

        return null;

    }

    @Override
    public Lookupable getByName(String name) throws SQLException {
        ResultSet rs;
        PreparedStatement ps = OpcatDatabaseConnection.getInstance()
                .getConnection().prepareStatement("select * from " + tableName + " where NAME = '" + name + "'");
        rs = ps.executeQuery();
        while (rs.next()) {
            return new LookupImpl(rs
                    .getInt(OpcatDatabaseConstants.FIELD_NAME_ID), rs
                    .getString(OpcatDatabaseConstants.FIELD_NAME_NAME), rs
                    .getString(OpcatDatabaseConstants.FIELD_NAME_DESCRIPTION),
                    rs.getInt(OpcatDatabaseConstants.FIELD_NAME_COLOR));
        }

        return null;
    }

    @Override
    public Lookupable create(String name, String description, int color) throws SQLException {
        PreparedStatement ps = OpcatDatabaseConnection.getInstance()
                .getConnection().prepareStatement(
                        "insert into " + tableName + " (name, description, color) values('"
                                + name
                                + "','" + description
                                + "'," + color + ")"
                                + "; \n ;");


        ps.executeUpdate();

        ps = OpcatDatabaseConnection.getInstance()
                .getConnection().prepareStatement("SELECT MAX(ID) as ID from " + tableName);
        ResultSet rs = ps.executeQuery();

        rs.next();
        return getByID(rs.getInt("ID"));
    }

    @Override
    public Lookupable update(int id, String name, String description, int color) throws SQLException {
        PreparedStatement ps = OpcatDatabaseConnection.getInstance()
                .getConnection().prepareStatement("update " + tableName
                        + " set NAME = '" + name + "', DESCRIPTION = '"
                        + description + "', COLOR = " + color
                        + " Where ID = " + id);
        ps.executeUpdate();
        return getByID(id);
    }

    @Override
    public Lookupable updateByName(String name, String description, int color) throws SQLException {
        PreparedStatement ps = OpcatDatabaseConnection.getInstance()
                .getConnection().prepareStatement("update " + tableName
                        + " set DESCRIPTION = '" + description
                        + "', COLOR = " + color
                        + " Where NAME  = " + name);

        ps.executeUpdate();
        return getByName(name);
    }
}
