package gui.dataProject;

import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmEntity;
import gui.projectStructure.Entry;
import util.OpcatLogger;

import java.util.*;

public abstract class DataAbstractItem implements ItemInterface {

    Object additionalData;

    private Vector data = new Vector();

    private String name;

    private long id;

    private String extID = "";

    public DataAbstractItem(String name, long id) {
        this.extID = Long.toString(id);
        this.id = extID.hashCode();
        this.name = name;
    }

    public DataAbstractItem(String name, String extID) {
        this.id = extID.hashCode();
        this.name = name;
        this.extID = extID;

    }

    public void setAdditionalData(Object entry) {
        additionalData = entry;
    }

    public Object getAdditionalData() {
        return additionalData;
    }

    public Vector getAllData() {
        return data;
    }

    public void setAllData(Vector data) {
        this.data = data;
    }

    public String getName() {
        String str = " ";

        if (name != "") {
            str = name;
        }
        return str;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public String getExtID() {
        return extID;
    }

}
