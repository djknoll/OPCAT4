package gui.projectStructure;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IInstance;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXRole;
import extensionTools.search.OptionsBase;
import extensionTools.search.OptionsByEntryID;
import extensionTools.search.OptionsExectMatch;
import extensionTools.search.SearchAction;
import gui.Opcat2;
import gui.controls.FileControl;
import gui.dataProject.DataAbstractItem;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.*;
import gui.util.BrowserLauncher2;

import java.awt.Color;

import org.jdom.Element;
import util.Configuration;
import util.OpcatLogger;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.io.File;
import static java.lang.String.format;
import java.net.URL;
import java.util.*;

/**
 * <p/>
 * The base class for all kinds of Entries in MainStructure class. Each Entry
 * represents some entity in user's project. Each Entry holds logical
 * information about this entity (OpmEntity object). Also each Entry has a data
 * structure that holds all graphical instances of this entity in special data
 * structure.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public abstract class Entry implements IXEntry, IEntry {
    protected OpmEntity logicalEntity;

    private Hashtable<OpdKey, Instance> instances;

    protected OpdProject myProject;

    //private String iconName = "none";

    private PlanarImage icon = null;


    private OpcatProperties myProps;

    /**
     * Creates Entry that holds all information about specified pEntity.
     *
     * @param pEntity object of OpmEntity class
     */
    public Entry(OpmEntity pEntity, OpdProject project) {
        this.logicalEntity = pEntity;
        this.instances = new Hashtable<OpdKey, Instance>();
        this.myProject = project;
        this.myProps = new OpcatProperties();
        getMyProps().put(OpcatProperties.entryDescriptionKey, "none");
        getMyProps().put(OpcatProperties.entryURLKey, "none");
        getMyProps().put(OpcatProperties.entryIconNameKey, "none");

    }

    /**
     * Returns OpmEntity that represents logically this Entry's entity. We can
     * retrieve all logical information about this entity from returned
     * OpmEntity.
     */

    public OpmEntity getLogicalEntity() {
        return this.logicalEntity;
    }

    /**
     * tests if the specified pKey is a key in data structure containing
     * graphical instances of this Entry's entity.
     */
    public boolean containsKey(OpdKey pKey) {
        return this.instances.containsKey(pKey);
    }

    /**
     * Adds the specified pInstance with the specified key to the data structure
     * containing graphical instances of this Entry's entity.
     *
     * @param key       OpdKey - key of graphical instance of some entity.
     * @param pInstance graphical Instance of some entity.
     * @return true if specified key is a new key in data structure containing
     *         graphical instances of this Entry's entity. Returns false and
     *         does nothing if specified key already exist in the data
     *         structure.
     */
    public boolean addInstance(OpdKey key, Instance pInstance) {

        if (this.containsKey(key)) {
            return false;
        }
        getMyProject().getSystemStructure().put(pInstance);
        this.instances.put(key, pInstance);
        return true;
    }

    /**
     * Removes from the data structure containing graphical instances of this
     * Entry's entity Instance with the specified pKey
     *
     * @param pKey OpdKey - key of graphical instance of some entity.
     * @return true operation was successful . False if specified key doesn't
     *         exist in the data structure.
     */
    public boolean removeInstance(OpdKey pKey) {
        if (!this.containsKey(pKey)) {
            return false;
        }


        Instance tempInstance = (Instance) this.instances.get(pKey);
        tempInstance.removeFromContainer();
        getMyProject().getSystemStructure().removeInstance(pKey);
        this.instances.remove(pKey);


        return true;
    }

    /**
     * Returns an enumeration of the Instances in this Entry. Use the
     * Enumeration methods on the returned object to fetch the Instances
     * sequentially
     *
     * @return an enumeration of the Instances in this MainStructure
     */

    public Enumeration<Instance> getInstances(boolean includeClipBoard) {
        Vector<Instance> ret = new Vector<Instance>();
        for (Instance ins : instances.values()) {
            if (!includeClipBoard) {
                if (ins.getKey().getOpdId() != OpdProject.CLIPBOARD_ID) {
                    ret.add(ins);
                }
            }
        }
        return ret.elements();
    }


    /**
     * Returns the Instance to which the specified key is mapped in data
     * structure containing graphical instances of this Entry's entity.
     *
     * @param pKey OpdKey - key of graphical instance of some entity.
     * @return the Instance to which the key is mapped in data structure
     *         containing graphical instances of this Entry's entity; null if
     *         the key is not mapped to any Entry in this MainStructure.
     */
    public Instance getInstance(OpdKey pKey) {
        return (Instance) this.instances.get(pKey);
    }

    public IXInstance getIXInstance(OpdKey pKey) {
        return (IXInstance) this.instances.get(pKey);
    }

    public IInstance getIInstance(OpdKey pKey) {
        return (IInstance) this.instances.get(pKey);
    }

    /**
     * Returns the number of graphical instances of this Entry's entity.
     */
    public int getInstancesNumber() {
        return this.instances.size();
    }

    public boolean hasInstanceInOpd(long opdNumber) {
        for (Enumeration e = this.getInstances(false); e.hasMoreElements();) {
            Instance currInstance = (Instance) e.nextElement();
            if (currInstance.getKey().getOpdId() == opdNumber) {
                return true;
            }
        }

        return false;

    }

    public Instance getInstanceInOPD(Opd opd) {
        for (Enumeration e = this.getInstances(false); e.hasMoreElements();) {
            Instance currInstance = (Instance) e.nextElement();
            if (currInstance.getKey().getOpdId() == opd.getOpdId()) {
                return currInstance;
            }
        }
        return null;

    }

    public void addProperty(String key, String data) {
        getMyProps().put(key, data);
    }

    public String getProperty(String key) {
        return getMyProps().getProperty(key);
    }

    public void setProperty(String key, String value) {
        getMyProps().setProperty(key, value);
    }

    public boolean isEmpty() {
        return this.instances.isEmpty();
    }

    public long getId() {
        return this.logicalEntity.getId();
    }

    public String getUserURL() {
        return getMyProps().getProperty(OpcatProperties.entryURLKey);
    }

    public String getURL() {
        return getUserURL();
    }

    public void setURL(String url) {
        setUserURL(url);
    }

    /**
     * Sets the string to be entity url
     *
     * @param url entity url
     */
    public void setUserURL(String url) {
        getMyProps().put(OpcatProperties.entryURLKey, url);
    }

    public String getName() {
        return this.logicalEntity.getName();
        //return myProps.getProperty("entry.general.name");
    }

    /**
     * Sets the string to be entity name.
     *
     * @param name entity name
     */
    public void setName(String name) {
        this.logicalEntity.setName(name);
    }

    /**
     * Returns entity description.
     *
     * @return a String with the entity description
     */
    public String getDescription() {

        //return getPropertiesHTML(null);
        return getMyProps().getProperty(OpcatProperties.entryDescriptionKey);
    }

    public String getAggragatedProperty(ThingInstance root, String aggragatedPropertyName) {
        String ret = null;
        if (root != null) {
            ret = format("%s\n", (root.getEntry().getProperty(aggragatedPropertyName) == null ? "none" : root.getEntry().getProperty(aggragatedPropertyName)));
            Vector<Instance> instances = root.getIncludedProcessInstancesInMyInZoomOPD();
            if ((instances != null) && instances.size() > 0) {
                for (Instance newroot : instances) {
                    ret = format("%s%s", new Object[]{ret, getAggragatedProperty((ThingInstance) newroot, aggragatedPropertyName)});
                }
            }
        }
        return ret;
    }

    public void loadProperties(Element properties) {
        if (properties == null) return;

        getMyProps().loadProperties(properties);

        /**
         * fix URL path
         */
        try {

            String str = getMyProps().getProperty(OpcatProperties.entryURLKey);

            if (hasUserURL()) {
                if (str.length() > 8) {
                    if (str.substring(0, 8).equalsIgnoreCase("file:///")) {
                        str = str.substring(8);

                        if (str.startsWith(".")) {
                            String filename = Opcat2.getLoadedfilename();
                            int last = filename
                                    .lastIndexOf(FileControl.fileSeparator);
                            str = filename.substring(0, last) + str.substring(1);
                            str = "file:///" + str;
                        }
                    }
                    setUserURL(str);
                }
            }
        } catch (Exception e) {

        }

        /**
         * Fix icon path
         */
        String path = getMyProps().getProperty("entry.general.iconnmae");
        if (path != null) {
            if (path.startsWith(".")) {
                String filename = null;
                if (myProject.getPath() != null) {
                    filename = myProject.getPath();
                } else {
                    filename = Opcat2.getLoadedfilename();
                }
                int last = filename.lastIndexOf(FileControl.fileSeparator);
                path = filename.substring(0, last) + path.substring(1);
            }
            setIcon(path);
        }

    }

    /**
     * Sets the string to be entity description.
     *
     * @param description description of the entity
     */
    public void setDescription(String description) {
        getMyProps().put(OpcatProperties.entryDescriptionKey, description);
        //logicalEntity.setDescription(description);
    }

    /**
     * Sets the enviromental/system property of OpmEntity. If value of
     * enviromental is true it's a enviromental thing, otherwise system
     */

    public void setEnvironmental(boolean environmental) {
        this.logicalEntity.setEnviromental(environmental);
    }

    /**
     * Returns true if this OpmEntity is enviromental. If it's system returns
     * false
     */

    public boolean isEnvironmental() {
        return this.logicalEntity.isEnviromental();
    }

    public abstract void updateInstances();

    /**
     * Shows the thing URL using the defualt browser
     */
    public void ShowURL() {


        if (!(this.getUserURL().equalsIgnoreCase("none") || this.getUserURL()
                .equalsIgnoreCase(""))) {

            BrowserLauncher2.openURL(this.getUserURL());

        }

        if (myProps.hasMCURL()) {
            myProps.ShowURL(this.getName());
        }
    }

    public boolean hasUserURL() {
        return (!(this.getUserURL().equalsIgnoreCase("none")) && !(this.getUserURL()
                .equals("")));
    }

    public boolean hasURL() {

        boolean ret = (!(this.getUserURL().equalsIgnoreCase("none")) && !(this.getUserURL()
                .equals("")));

        if (Configuration.getInstance().getProperty("entry.url.name") != null) {
            String[] res = Configuration.getInstance().getProperty("entry.url.name").split(",");
            for (int i = 0; i < res.length; i++) {
                if (res[i].contains("repo")) {
                    if (getProperty(res[i]) != null) {
                        try {
                            return true;
                        } catch (Exception ex) {

                        }
                    }
                }
            }
        }

        return ret;
    }

    /**
     * return all the Thinginstances inside the zoomed in entry. without the
     * links
     *
     * @param opd
     * @return
     */
    public Vector<ThingInstance> GetInZoomedIncludedInstances(Opd opd) {
        Enumeration insEnum = this.myProject.getSystemStructure()
                .getInstancesInOpd(opd.getOpdId());
        Vector<ThingInstance> vec = new Vector<ThingInstance>();

        while (insEnum.hasMoreElements()) {
            Object obj = insEnum.nextElement();
            if (obj instanceof ThingInstance) {
                ThingInstance thingInstance = (ThingInstance) obj;
                if ((thingInstance.getParent() != null)
                        && (thingInstance.getParent().getEntry().getId() == this
                        .getId())) {
                    vec.add(thingInstance);
                }
            }
        }

        return vec;
    }

    /**
     * return all the Thinginstances inside the zoomed in entry. without the
     * links
     *
     * @return
     */
    public Vector<ThingInstance> GetInZoomedIncludedInstances() {
        Enumeration<Opd> insEnum = this.myProject.getSystemStructure()
                .getOpds();
        Vector<ThingInstance> vec = new Vector<ThingInstance>();

        while (insEnum.hasMoreElements()) {
            Opd opd = insEnum.nextElement();
            vec.addAll(GetInZoomedIncludedInstances(opd));
        }

        return vec;
    }

    /**
     * return all the ThingEntries inside the zoomed in entry. without the links
     *
     * @param opd
     * @return
     */
    public Vector<ThingEntry> GetInZoomedIncludedEntries(Opd opd) {
        Enumeration insEnum = this.myProject.getSystemStructure()
                .getInstancesInOpd(opd.getOpdId());

        Vector<ThingEntry> vec = new Vector<ThingEntry>();

        while (insEnum.hasMoreElements()) {
            Object obj = insEnum.nextElement();
            if (obj instanceof ThingInstance) {
                ThingInstance thingInstance = (ThingInstance) obj;
                if ((thingInstance.getParent() != null)
                        && (thingInstance.getParent().getEntry().getId() == this
                        .getId())) {
                    if (!vec.contains((ThingEntry) thingInstance.getEntry())) {
                        vec.add((ThingEntry) thingInstance.getEntry());
                    }
                }
            }
        }

        return vec;
    }

    /**
     * return all the ThingEntries inside the zoomed in entry. without the links
     *
     * @return
     */
    public Vector<ThingEntry> GetInZoomedIncludedEntries() {
        Enumeration<Opd> insEnum = this.myProject.getSystemStructure()
                .getOpds();
        Vector<ThingEntry> vec = new Vector<ThingEntry>();

        while (insEnum.hasMoreElements()) {
            Opd opd = insEnum.nextElement();
            for (ThingEntry entry : GetInZoomedIncludedEntries(opd)) {
                if (!vec.contains(entry)) {
                    vec.add(entry);
                }
            }
        }

        return vec;
    }

    public boolean isIncludedInZoomedIn(ThingEntry isIncluded) {
        boolean found = false;
        Iterator<ThingEntry> included = GetInZoomedIncludedEntries().iterator();

        while ((!found) && (included.hasNext())) {
            ThingEntry entry = included.next();
            if (entry.getId() == isIncluded.getId()) {
                found = true;
            }
        }

        return found;
    }

    /**
     * return all the Thinginstances outside the zoomed in entry. without the
     * links
     *
     * @param opd
     * @return
     */
    public ArrayList GetInZoomedNotIncludedInstances(Opd opd) {
        Enumeration insEnum = this.myProject.getSystemStructure()
                .getInstancesInOpd(opd.getOpdId());
        ArrayList<ThingInstance> list = new ArrayList<ThingInstance>();

        while (insEnum.hasMoreElements()) {
            Object obj = insEnum.nextElement();
            if (obj instanceof ThingInstance) {
                ThingInstance thingInstance = (ThingInstance) obj;
                if ((thingInstance.getEntry().getId() != this.getId())
                        && ((thingInstance.getParent() == null) || (thingInstance
                        .getParent().getEntry().getId() != this.getId()))) {
                    list.add(thingInstance);
                }
            }
        }

        return list;
    }

    public Enumeration<Role> getRoles(int type) {
        Enumeration<Role> roles;
        try {
            roles = ((OpmEntity) this.logicalEntity).getRolesManager()
                    .getRolesVector(type).elements();
        } catch (Exception E) {
            roles = new Vector<Role>().elements();
        }
        return roles;
    }

    public Instance createInstance(Opd targetOpd, boolean insideMainInstance,
                                   int x, int y) {

        return null;
    }

    public String getRole() {
        return ((OpmEntity) this.logicalEntity).getRole();
    }

    public String getFreeTextRole() {
        return ((OpmEntity) this.logicalEntity).getFreeTextRole();
    }

    public void setRole(String role) {
        ((OpmEntity) this.logicalEntity).setRole(role);
    }

    public String getRolesString(int type) {
        String ret = null;
        Iterator<Role> iter = Collections.list(this.getRoles(type)).iterator();
        while (iter.hasNext()) {
            Role role = (Role) iter.next();
            ret = ret + role.getThingName();
            ret = ret + ",";
        }
        if (ret != null) {
            return ret.substring(0, ret.length() - 1);
        } else {
            return ret;
        }

    }

    /**
     * Returns the {@link RoleEntry} objects of the thing - which reflect its
     * notation in an imported ontology.
     *
     * @return Enumeration Contains the roles as <code>RoleEntry</code> objects.
     * @author Eran Toch
     */
    public Enumeration getRolesEntries() {
        Enumeration roles;
        try {
            roles = ((OpmEntity) this.logicalEntity).getRolesManager()
                    .getRoleEntries();
        } catch (Exception E) {
            roles = new Vector().elements();
        }
        return roles;
    }

    public void addRole(IXRole role) {
        ((OpmEntity) this.logicalEntity).getRolesManager().addRole(role);
    }

    public void removeRole(IXRole role) {
        ((OpmEntity) this.logicalEntity).getRolesManager().removeRole(role);
    }

    /**
     * Returns the roles of the thing - which reflect its notation in an
     * imported ontology.
     *
     * @return Enumeration Contains the roles as Role objects.
     * @author Eran Toch
     */
    public Enumeration<Role> getRoles() {
        Enumeration roles;
        try {
            roles = ((OpmEntity) this.logicalEntity).getRolesManager()
                    .getRoles();
        } catch (Exception E) {
            roles = new Vector().elements();
        }
        return roles;
    }

    /**
     * returns the connected things of this Entry all connection ends which are
     * connected to this Entry with Procedural Links
     *
     * @return HashMap<Entry ID, array{entry,link}
     */

    public HashMap getConnectedThings() {

        HashMap<Long, Entry> entries = new HashMap<Long, Entry>();
        HashMap ret = new HashMap();

        Entry connectionEdge = this;
        entries.put(new Long(connectionEdge.getId()), connectionEdge);
        if (connectionEdge instanceof ObjectEntry) {
            ObjectEntry obj = (ObjectEntry) connectionEdge;
            Iterator statesIter = Collections.list(obj.getStates()).iterator();
            while (statesIter.hasNext()) {
                StateEntry state = (StateEntry) statesIter.next();
                entries.put(new Long(state.getId()), state);
            }
        }

        if (connectionEdge instanceof ThingEntry) {
            ThingEntry thingEnt = (ThingEntry) connectionEdge;
            if (thingEnt.getZoomedInOpd() != null) {
                Vector includedIns = GetInZoomedIncludedInstances(thingEnt
                        .getZoomedInOpd());
                Iterator iter = includedIns.iterator();
                while (iter.hasNext()) {
                    Instance ins = (Instance) iter.next();
                    if (!entries.containsKey(new Long(ins.getEntry().getId()))) {
                        entries.put(new Long(ins.getEntry().getId()), ins
                                .getEntry());
                        if (ins instanceof ObjectInstance) {
                            ObjectEntry obj = (ObjectEntry) ins.getEntry();
                            Iterator statesIter = Collections.list(
                                    obj.getStates()).iterator();
                            while (statesIter.hasNext()) {
                                StateEntry state = (StateEntry) statesIter
                                        .next();
                                entries.put(new Long(state.getId()), state);
                            }
                        }
                    }
                }
            }
        }

        Iterator entriesIter = entries.values().iterator();
        while (entriesIter.hasNext()) {

            Entry entry = (Entry) entriesIter.next();
            Iterator destIter = null;
            Iterator srcIter = null;

            if (entry instanceof StateEntry) {
                destIter = Collections.list(
                        ((StateEntry) entry).getDestinationLinks()).iterator();
                srcIter = Collections.list(
                        ((StateEntry) entry).getSourceLinks()).iterator();

            } else if (entry instanceof ThingEntry) {
                destIter = Collections.list(
                        ((ThingEntry) entry).getDestinationLinks()).iterator();
                srcIter = Collections.list(
                        ((ThingEntry) entry).getSourceLinks()).iterator();
            }

            while ((destIter != null) && (destIter.hasNext())) {
                Object obj = destIter.next();
                if (obj instanceof OpmProceduralLink) {
                    OpmProceduralLink link = (OpmProceduralLink) obj;
                    LinkEntry linkEntry = (LinkEntry) Opcat2
                            .getCurrentProject().getSystemStructure().getEntry(
                                    link.getId());

                    Entry ent = (Entry) Opcat2.getCurrentProject()
                            .getSystemStructure().getEntry(
                                    linkEntry.getSourceId());
                    // if (ent instanceof ThingEntry) {
                    // ThingEntry process = (ThingEntry) ent;
                    if (ent == null)
                        continue;
                    if (!ret.containsKey(new Long(ent.getId()))) {
                        Object[] array = new Object[2];
                        array[0] = ent;
                        array[1] = link;
                        ret.put(new Long(ent.getId()), array);
                    }
                }
            }

            while ((srcIter != null) && (srcIter.hasNext())) {
                Object obj = srcIter.next();
                if (obj instanceof OpmProceduralLink) {
                    OpmProceduralLink link = (OpmProceduralLink) obj;

                    LinkEntry linkEntry = (LinkEntry) Opcat2
                            .getCurrentProject().getSystemStructure().getEntry(
                                    link.getId());

                    Entry ent = (Entry) Opcat2.getCurrentProject()
                            .getSystemStructure().getEntry(
                                    linkEntry.getDestinationId());
                    // if (ent instanceof ThingEntry) {
                    // ThingEntry process = (ThingEntry) ent;
                    if (ent == null)
                        continue;
                    if (!ret.containsKey(new Long(ent.getId()))) {
                        Object[] array = new Object[2];
                        array[0] = ent;
                        array[1] = link;
                        ret.put(new Long(ent.getId()), array);
                    }
                }
            }
        }

        return ret;
    }

    /**
     * show instances of all entries in the Vector opmThings
     *
     * @param opmThings a Vector of OpmConnectionEdge objects
     */
    public void ShowInstances(Vector opmThings) {

        OptionsBase[] searchOptions = new OptionsExectMatch[opmThings.size()];
        ConnectionEdgeEntry entries[] = new ConnectionEdgeEntry[opmThings
                .size()];

        for (int i = 0; i < opmThings.size(); i++) {
            OpmEntity opmEnt = (OpmEntity) opmThings.get(i);

            // ConnectionEdgeEntry ent = opmEnt.getConnectionEdgeEntry();

            searchOptions[i] = new OptionsExectMatch();

            if (opmEnt instanceof OpmProcess) {
                searchOptions[i].setForProcess(true);
                searchOptions[i].setForObjects(false);
                searchOptions[i].setForStates(false);
            }
            if (opmEnt instanceof OpmObject) {
                searchOptions[i].setForProcess(false);
                searchOptions[i].setForObjects(true);
                searchOptions[i].setForStates(false);
            }
            if (opmEnt instanceof OpmState) {
                searchOptions[i].setForProcess(false);
                searchOptions[i].setForObjects(false);
                searchOptions[i].setForStates(true);
                searchOptions[i].setRestrictToParent(true);
            }

            if ((opmEnt instanceof OpmStructuralRelation)
                    || (opmEnt instanceof OpmProceduralLink)) {
                searchOptions[i].setForProcess(false);
                searchOptions[i].setForObjects(false);
                searchOptions[i].setForStates(false);
                searchOptions[i].setRestrictToParent(false);
                searchOptions[i].setForLinks(true);
            }

            searchOptions[i].setInDescription(false);
            searchOptions[i].setInName(true);

            searchOptions[i].setSearchText(opmEnt.getName());
            if ((searchOptions[i].isRestrictToParent())
                    && (opmEnt instanceof OpmConnectionEdge)) {

                ConnectionEdgeEntry connectionEdgeEntry = (ConnectionEdgeEntry) myProject
                        .getSystemStructure().getEntry(opmEnt.getId());

                if (connectionEdgeEntry instanceof StateEntry) {
                    entries[i] = (ConnectionEdgeEntry) ((StateEntry) connectionEdgeEntry)
                            .getParentObject();
                }

                if (connectionEdgeEntry instanceof ThingEntry) {
                    ThingEntry ent = (ThingEntry) myProject
                            .getSystemStructure().getEntry(opmEnt.getId()); // ((ThingEntry)

                    if (ent.getZoomedInIOpd() != null) {
                        entries[i] = (ConnectionEdgeEntry) ent
                                .getZoomedInIXOpd().getMainIXEntry();
                    }
                }
            }

        }

        SearchAction search = new SearchAction(entries, searchOptions,
                "Appearances");
        search.actionPerformed(null);

    }

    /**
     * show instances of this entry
     */
    public void ShowInstances() {

        OptionsBase searchOptions = new OptionsByEntryID(this);

        if (this instanceof ProcessEntry) {
            searchOptions.setForProcess(true);
            searchOptions.setForObjects(false);
        }

        if (this instanceof ObjectEntry) {
            searchOptions.setForProcess(false);
            searchOptions.setForObjects(true);
        }
        if (this instanceof StateEntry) {
            searchOptions.setForProcess(false);
            searchOptions.setForObjects(false);
            searchOptions.setForStates(true);
            searchOptions.setRestrictToParent(true);
        }

        if ((this.getLogicalEntity() instanceof OpmStructuralRelation)
                || (this.getLogicalEntity() instanceof OpmProceduralLink)) {
            searchOptions.setForProcess(false);
            searchOptions.setForObjects(false);
            searchOptions.setForStates(false);
            searchOptions.setRestrictToParent(false);
            searchOptions.setForLinks(true);
        }

        searchOptions.setInDescription(false);
        searchOptions.setInName(true);
        searchOptions.setSearchText(this.getLogicalEntity().getName());
        SearchAction search = new SearchAction(this, searchOptions, getName()
                + "@" + myProject.getName(), myProject);
        search.actionPerformed(null);

    }

    public Color getMetaColor(MetaLibrary meta) {

        Color color = Color.WHITE;

        try {
            Iterator roleIter = ((OpmEntity) getLogicalEntity())
                    .getRolesManager().getRolesVector(meta.getType(),
                            meta.getID()).iterator();
            while (roleIter.hasNext()) {
                Role role = (Role) roleIter.next();
                DataAbstractItem metaItem = (DataAbstractItem) role.getThing();
                color = metaItem.getColor(meta);
            }
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
        return color;
    }

    public OpdProject getMyProject() {
        return myProject;
    }

    public boolean canBeGeneric() {
        if (this instanceof ThingEntry) {
            ThingEntry locThing = (ThingEntry) this;
            return (locThing.getZoomedInOpd() == null);
        } else {
            return false;
        }
    }

    public void updateDefaultURL() {

        // if thing is Env AND has Roles AND does not have a URL, then
        // we put the first Role meta path has the URL.
        if (canBeGeneric()
                && this.getLogicalEntity().isEnviromental()
                && this.getLogicalEntity().getRolesManager().getLoadedRoles()
                .hasMoreElements()
                && (this.getUserURL().equalsIgnoreCase("none") || (this.getUserURL() == null))) {
            MetaLibrary myMeta = ((Role) this.getLogicalEntity()
                    .getRolesManager().getLoadedRoles().nextElement())
                    .getOntology();
            try {
                URL url = new URL("file", "", myMeta.getPath());
                this.setUserURL(url.getProtocol() + "://" + url.getPath());
            } catch (Exception ex) {
                OpcatLogger.error(ex);
            }

        }
    }

    public String getTypeString() {
        return "Entry";
    }

    public PlanarImage getIcon() {
        return icon;
    }

    public PlanarImage getRoleIcon() {
        OpmEntity opm = getLogicalEntity();
        Enumeration<Role> roles = opm.getRolesManager().getRoles();
        while (roles.hasMoreElements()) {
            Role role = roles.nextElement();
            if (role.getThing() instanceof OpmEntity) {
                OpmEntity roleOPM = (OpmEntity) role.getThing();
                Entry entry = (Entry) role.getOntology().getStructure()
                        .getIEntry(roleOPM.getId());
                PlanarImage image = entry.getIcon();
                if ((image != null)
                        && (role.getOntology().getType() == MetaLibrary.TYPE_POLICY)) {
                    return image;
                }
            }
        }
        return null;
    }

    public String getIconPath() {
        return getMyProps().getProperty(OpcatProperties.entryIconNameKey);
    }

    public void setIcon(String path) {
        try {
            File iconFile = new File(path);
            if (iconFile.exists()) {

                icon = JAI.create("fileload", path);
                getMyProps().put(OpcatProperties.entryIconNameKey, path);

            } else {
                icon = null;
                getMyProps().put(OpcatProperties.entryIconNameKey, "none");
                // OpcatLogger.logError("Icon - " + path
                // + " - does not exist");
            }
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }

    public boolean isIconSet() {
        return (icon != null);
    }

    public boolean isRoleIconSet() {
        return (getRoleIcon() != null);
    }


    public OpcatProperties getMyProps() {
        return myProps;
    }
}
