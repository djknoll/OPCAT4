package database;

import java.sql.SQLException;
import java.util.List;

public interface LookupDAO {
    public abstract List<? extends Lookupable> getAll() throws SQLException;

    public abstract Lookupable getByID(int ID) throws SQLException;

    public abstract Lookupable getByName(String name) throws SQLException;

    public abstract Lookupable create(String name, String description, int color) throws SQLException;

    public abstract Lookupable update(int id, String name, String description, int color) throws SQLException;

    public abstract Lookupable updateByName(String name, String description, int color) throws SQLException;

}
