package expose;

import database.OpcatDatabaseConnection;
import gui.metaLibraries.logic.Role;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;

import java.io.File;

public class OpcatExposeKey implements Comparable<OpcatExposeKey> {

    private String modelDecodedURI;
    private String modelEncodedURI;

    private long opmEntityID;
    private long id = OpcatExposeConstants.EXPOSE_UNDEFINED_ID;
    boolean privateKey = false;

    public void setPrivate(boolean keyIsPrivate) {
        this.privateKey = keyIsPrivate;
    }

    public void refreshID() {
        if (OpcatMCManager.isOnline() && OpcatDatabaseConnection.isOnLine()) {
            id = OpcatExposeManager.getExposeServerID(modelEncodedURI,
                    opmEntityID);
        }
    }

    /**
     * creates an expose key, if this relates to an existing expose it's key is
     * brought from the database else or we are not online id is not set and
     * will be looked up again upon committing if needed.
     *
     * @param exposedModel
     * @param opmEntityID
     * @throws Exception
     */
    public OpcatExposeKey(File exposedModel, long opmEntityID, boolean isPrivate)
            throws Exception {

        modelDecodedURI = null;
        modelEncodedURI = null;
        if (exposedModel.exists()) {
            SVNURL url = OpcatMCManager.getInstance(true).getFileURL(
                    exposedModel);
            if (url != null) {
                this.modelEncodedURI = url.getURIEncodedPath();
                this.modelDecodedURI = url.getPath();
            } else {
                throw new Exception(
                        "Model does not belong to OMC, add this model to OMC and try again");
            }
        }
        this.opmEntityID = opmEntityID;

        this.privateKey = isPrivate;

    }


    public OpcatExposeKey(SVNURL url, long opmEntityID, boolean isPrivate)
            throws Exception {

        this.modelEncodedURI = url.getURIEncodedPath();
        this.modelDecodedURI = url.getPath();
        this.opmEntityID = opmEntityID;
        this.privateKey = isPrivate;

    }

    /**
     * creates an expose key, if this relates to an existing expose it's key is
     * brought from the database else or we are not online id is not set and
     * will be looked up again upon committing if needed.
     *
     * @param modelEncodedURI
     * @param opmEntityID
     * @throws Exception
     */
    public OpcatExposeKey(String modelEncodedURI, long opmEntityID,
                          boolean isPrivate) throws Exception {
        SVNURL url;
        try {
            url = SVNURL.parseURIEncoded(OpcatMCManager.getSvnURL()
                    + modelEncodedURI);
        } catch (SVNException e) {
            throw new Exception("Incorrect encoded url");
        }

        this.modelEncodedURI = url.getURIEncodedPath();
        this.modelDecodedURI = url.getPath();
        this.opmEntityID = opmEntityID;
        this.privateKey = isPrivate;

    }

    public OpcatExposeKey(long exposeID, String modelEncodedURI,
                          long opmEntityID, boolean isPrivate) throws Exception {
        SVNURL url;
        try {
            url = SVNURL.parseURIEncoded(OpcatMCManager.getSvnURL()
                    + modelEncodedURI);
        } catch (SVNException e) {
            throw new Exception("Incorrect encoded url");
        }

        this.modelEncodedURI = url.getURIEncodedPath();
        this.modelDecodedURI = url.getPath();
        this.opmEntityID = opmEntityID;
        this.id = exposeID;
        this.privateKey = isPrivate;
    }

    protected String getKey() {
        return "[" + id + "]" + opmEntityID + "@" + modelEncodedURI;
    }

    @Override
    public int compareTo(OpcatExposeKey o) {
        if (this.getKey().equalsIgnoreCase(o.getKey())) {
            return 1;
        }
        return 0;
    }

    public String getModelDecodedURI() {
        return modelDecodedURI;
    }

    public String getModelEncodedURI() {
        return modelEncodedURI;
    }

    public long getOpmEntityID() {
        return opmEntityID;
    }

    public long getId() {
        if (id == OpcatExposeConstants.EXPOSE_UNDEFINED_ID)
            if (OpcatMCManager.isOnline() && OpcatDatabaseConnection.isOnLine()) {
                id = OpcatExposeManager.getExposeServerID(modelEncodedURI,
                        opmEntityID);
            }
        return id;
    }

    public boolean isPrivate() {
        return privateKey;
    }

    public boolean equals(Object aThat) {
        //if (this == aThat)
        //	return true;
        //if (!(aThat instanceof OpcatExposeKey))
        //	return false;

        OpcatExposeKey that = (OpcatExposeKey) aThat;
        return (getModelEncodedURI().hashCode() == that.getModelEncodedURI().hashCode()) && (getOpmEntityID() == that
                .getOpmEntityID());
    }

    public int hashCode() {
        return getKey().hashCode();
    }

    public Role convertKeyToRole() {
        return new Role(this.getOpmEntityID(), this.modelEncodedURI.hashCode());
    }

}
