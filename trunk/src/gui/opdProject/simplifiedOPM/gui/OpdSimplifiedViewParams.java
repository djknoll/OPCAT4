package gui.opdProject.simplifiedOPM.gui;

import java.io.File;

/**
 * Created by raanan.
 * Date: 19/07/11
 * Time: 13:49
 */
public class OpdSimplifiedViewParams {

    String name = "";
    boolean showProcesses = true;
    boolean showObject = true;
    boolean showNonLinksThings = false;
    boolean inZoomAsGroup = false;
    boolean showLinks = true;
    boolean showFunRelations = false;
    boolean showContainedFunRelations = false;

    boolean showGenRelations = true;
    boolean stripInZoomLinks = false;
    boolean folder = false;
    long parentID = -1;
    long opdID = -1;
    boolean showInstruments = true;
    boolean showCondirion = true;
    File eastHTM;
    File southHTM;
    File westHTM;
    boolean showConsumptionEvent = true;
    boolean showInstrumentEvent = true;
    boolean objectsAsGroups = false;
    boolean doTimeOrderEdges = true;

    public boolean isDoTimeOrderEdges() {
        return doTimeOrderEdges;
    }

    public void setDoTimeOrderEdges(boolean doTimeOrderEdges) {
        this.doTimeOrderEdges = doTimeOrderEdges;
    }

    public boolean isObjectsAsGroups() {
        return objectsAsGroups;
    }

    public void setObjectsAsGroups(boolean objectsAsGroups) {
        this.objectsAsGroups = objectsAsGroups;
    }

    public boolean isShowContainedFunRelations() {
        return showContainedFunRelations;
    }

    public void setShowContainedFunRelations(boolean showContainedFunRelations) {
        this.showContainedFunRelations = showContainedFunRelations;
    }

    public boolean isShowConsumptionEvent() {
        return showConsumptionEvent;
    }

    public void setShowConsumptionEvent(boolean showConsumptionEvent) {
        this.showConsumptionEvent = showConsumptionEvent;
    }

    public boolean isShowInstrumentEvent() {
        return showInstrumentEvent;
    }

    public void setShowInstrumentEvent(boolean showInstrumentEvent) {
        this.showInstrumentEvent = showInstrumentEvent;
    }

    public File getEastHTM() {
        return eastHTM;
    }

    public void setEastHTM(File eastHTM) {
        this.eastHTM = eastHTM;
    }

    public File getWestHTM() {
        return westHTM;
    }

    public void setWestHTM(File westHTM) {
        this.westHTM = westHTM;
    }

    public File getSouthHTM() {
        return southHTM;
    }

    public void setSouthHTM(File southHTM) {
        this.southHTM = southHTM;
    }

    public boolean isShowCondirion() {
        return showCondirion;
    }

    public void setShowCondirion(boolean showCondirion) {
        this.showCondirion = showCondirion;
    }

    public boolean isShowInstruments() {
        return showInstruments;
    }

    public void setShowInstruments(boolean showInstruments) {
        this.showInstruments = showInstruments;
    }

    public boolean isShowNonLinksThings() {
        return showNonLinksThings;
    }

    public void setShowNonLinksThings(boolean showNonLinksThings) {
        this.showNonLinksThings = showNonLinksThings;
    }

    public boolean isShowObject() {
        return showObject;
    }

    public void setShowObject(boolean showObject) {
        this.showObject = showObject;
    }

    public boolean isShowProcesses() {
        return showProcesses;
    }

    public void setShowProcesses(boolean showProcesses) {
        this.showProcesses = showProcesses;
    }

    public OpdSimplifiedViewParams() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFolder() {
        return folder;
    }

    public long getOpdID() {
        return opdID;
    }

    public long getParentID() {
        return parentID;
    }

    public boolean isInZoomAsGroup() {
        return inZoomAsGroup;
    }

    public boolean isShowFunRelations() {
        return showFunRelations;
    }

    public boolean isShowGenRelations() {
        return showGenRelations;
    }

    public boolean isShowLinks() {
        return showLinks;
    }

    public boolean isStripInZoomLinks() {
        return stripInZoomLinks;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public void setInZoomAsGroup(boolean inZoomAsGroup) {
        this.inZoomAsGroup = inZoomAsGroup;
    }

    public void setOpdID(long opdID) {
        this.opdID = opdID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public void setShowFunRelations(boolean showFunRelations) {
        this.showFunRelations = showFunRelations;
    }

    public void setShowGenRelations(boolean showGenRelations) {
        this.showGenRelations = showGenRelations;
    }

    public void setShowLinks(boolean showLinks) {
        this.showLinks = showLinks;
    }

    public void setStripInZoomLinks(boolean stripInZoomLinks) {
        this.stripInZoomLinks = stripInZoomLinks;
    }
}
