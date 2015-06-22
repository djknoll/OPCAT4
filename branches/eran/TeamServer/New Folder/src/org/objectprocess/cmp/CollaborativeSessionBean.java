package org.objectprocess.cmp;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import org.objectprocess.config.PermissionFlags;
import org.objectprocess.config.SystemDefault;

/**
 * Main implementation of the <code>CollaborativeSession</code> entity ejb.
 * xDoclet tags generate all required interfaces and deploymet decriptors.
 * <code>CollaborativeSession</code> is mapped to the <code>CollaborativeSessionSchema</code> 
 * table schema and uses the SESSION SQL table for persistence.
 * The following value objects are defined for the <code>CollaborativeSession</code> ejb: 
 * <ul>
 * 	<li><code>EnhancedCollaborativeSessionValue</code> - contains all attributes but the OPM model file, 
 * 		as well as a <code>Collection</code> of <code>Enhanced2CollaborativeSessionPermissionsValue</code> objects 
 * 		the collaborative session has a coresponding permission for.
 * 	<li><code>CollaborativeSessionValue</code> - contains all attributes but the OPM model file.
 * 	<li><code>EditableCollaborativeSession</code> - contains all attributes that can be initialized when the entity is created,
 * 		should be used as an argument for the <code>ejbCreate</code> method.
 * 	<li><code>UpdatableCollaborativeSession</code> - contains all attributes that can be updated but the OPM model file.
 *  <li><code>CollaborativeSessionFile</code> - contains all attributes as well as the OPM model file.
 * </ul>
 * 
 * @ejb.bean name="CollaborativeSession"
 *	jndi-name="CollaborativeSessionBean"
 *	type="CMP"
 *  primkey-field="collaborativeSessionID"
 *  schema="CollaborativeSessionSchema" 
 *  cmp-version="2.x"
 * @ejb.persistence 
 *	table-name="collaborative_session" 
 *
 * @ejb.value-object
 *	name="EnhancedCollaborativeSession"
 *	match="enhanced"
 * @ejb.value-object
 *	name="CollaborativeSession"
 *	match="flat"
 * @ejb.value-object
 *	name="EditableCollaborativeSession"
 *	match="edit" 
 * @ejb.value-object
 *	name="UpdatableCollaborativeSession"
 *	match="update"
 * @ejb.value-object
 *	name="CollaborativeSessionFile"
 *	match="file"
 * 
 * @ejb.finder 
 *	query="SELECT OBJECT(a) FROM CollaborativeSessionSchema as a"  
 *	signature="java.util.Collection findAll()" 
 *  
 * @ejb.finder 
 * 	query="SELECT OBJECT(a) FROM CollaborativeSessionSchema a where a.collaborativeSessionName = ?1"  
 * 	signature="java.util.Collection findByCollaborativeSessionName(java.lang.String collaborativeSession)" 
 */
public abstract class CollaborativeSessionBean implements EntityBean {

	protected EntityContext eContext;
	public void setEntityContext(EntityContext context){ eContext = context; } 
	public void unsetEntityContext(){ eContext = null; }
	
	/**
	 * CollaborativeSession <code>ejbCreate</code> method.
	 * @param editableCollaborativeSessionValue attributes required to create a new <code>CollaborativeSession</code> ejb.
	 * @throws CreateException if ejb creation failed.
	 * 
	 * The  ejbCreate method.
	 * @ejb.create-method 
	 */
	public String ejbCreate(EditableCollaborativeSessionValue editableCollaborativeSessionValue) 
		throws CreateException 
	{
		setEditableCollaborativeSessionValue(editableCollaborativeSessionValue);
		setTerminated (new Boolean(false));
		setEnabled(new Boolean(true));
		setDirty(new Boolean(false));
		setCreationTime (new Date()); 
		setLastUpdate (new Date());
		setTokenHolderID (PermissionFlags.SERVER_USERID);
		
		//TODO see if this has any meaning?
		if (null == editableCollaborativeSessionValue.getTokenTimeout())
			setTokenTimeout(SystemDefault.TOKEN_TIMEOUT);
		else
			setTokenTimeout (editableCollaborativeSessionValue.getTokenTimeout()); 
		
		if (null == editableCollaborativeSessionValue.getUserTimeout())
			setUserTimeout(SystemDefault.USER_TIMEOUT);
		else
			setUserTimeout (editableCollaborativeSessionValue.getUserTimeout()); 	
		
		return null;
	}
	
	/**
	 * The container invokes this method immediately after it calls <code>ejbCreate</code>.
	 * 
	 * @throws CreateException if ejb creation failed.
	 */
	public void ejbPostCreate() throws CreateException {}
	 
	//TODO add a CMR field for the Revision object the session is based on.
	//	CMR fields	
	/**
	 * Returns a <code>Collection</code> of <code>CollaborativeSessionPermissionsLocal</code> related to the collaborative session.
	 * @return a <code>Collection</code> of related <code>CollaborativeSessionPermissionsLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.Enhanced2CollaborativeSessionPermissionsValue"
	 * 	aggregate-name="CollaborativeSessionsPermission"
	 * 	members="org.objectprocess.cmp.CollaborativeSessionPermissionsLocal"
	 * 	members-name="EnhancedCollaborativeSessionPermissionsValue"
	 * 	relation="external"
	 * 	type="java.util.Collection"
	 *	match="enhanced"
	 * 
	 * @ejb.relation
	 *	name="CollaborativeSessionPermissions-CollaborativeSession"
	 *	role-name="CollaborativeSession-has-CollaborativeSessionPermissions"
	 *	target-ejb="CollaborativeSessionPermissions"
	 *	target-role-name="CollaborativeSessionPermissions-for-CollaborativeSession"
	 *	target-cascade-delete="yes"
	 * 
	 * @jboss.target-relation
	 *	related-pk-field="collaborativeSessionID"
	 *	fk-column="SESSIONID"
	 */
	public abstract Collection getCollaborativeSessionPermissions();	   
	/**
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setCollaborativeSessionPermissions(Collection collaborativeSessionPermissions);

	/**
	 * Returns the <code>OpmModelLocal</code> the collaborative session is based on.
	 * @return the <code>OpmModelLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.relation
	 *	name="OpmModel-CollaborativeSession"
	 *	role-name="CollaborativeSession-in-OpmModel"
	 * 
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.OpmModelValue"
	 * 	aggregate-name="OpmModel"
	 * 	members="org.objectprocess.cmp.OpmModelLocal"
	 * 	members-name="OpmModelValue"
	 * 	relation="external"
	 * 
	 * @jboss.relation
	 *	related-pk-field="opmModelID"
	 *	fk-column="OPMMODELID"
	 */
	public abstract OpmModelLocal getOpmModel();	   
	/**
	 * @ejb.interface-method
	 *    view-type="local"
	 */
	public abstract void setOpmModel(OpmModelLocal OpmModel);

	//CMP fields
	/**
	 * Returns the collaborative session's id.
	 * @return the collaborative session's id.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="SESSIONID"
	 *	sql-type="VARCHAR"
	 * @ejb.pk-field 
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="file"
	 * @ejb.value-object match="update"
	 */
	public abstract String getCollaborativeSessionID();
	/**
	 * Sets the collaborative session's id
	 * @param collaborativeSessionID the new collaborative session's id value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setCollaborativeSessionID(String collaborativeSessionID);
	
	/**
	 * Returns the collaborative session's name.
	 * @return the collaborative session's name.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="SESSIONNAME"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getCollaborativeSessionName();
	/**
	 * Sets the collaborative session's name.
	 * @param collaborativeSessionName the new collaborative session's name value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setCollaborativeSessionName(String collaborativeSessionName);
	
	/**
	 * Returns the collaborative session's <code>enabled</code> flag value.
	 * @return the collaborative session's <code>enabled</code> flag value.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="ENABLED"
	 *	sql-type="TINYINT"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="update"
	 */
	public abstract Boolean getEnabled();
	/**
	 * Sets the the collaborative session's <code>enabled</code> flag value.
	 * @param enabled the new <code>enabled</code> flag value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setEnabled(Boolean enabled);

	/**
	 * Returns the collaborative session's <code>dirty</code> flag value.
	 * @return the collaborative session's <code>dirty</code> flag value.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="DIRTY"
	 *	sql-type="TINYINT"
	 * @ejb.interface-method
	 */
	public abstract Boolean getDirty();	
	/**
	 * Sets the collaborative session's <code>dirty</code> flag value.
	 * @param dirty the new <code>dirty</code> flag value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setDirty(Boolean dirty);
	 	 	    
	/**
	 * Returns the collaborative session's <code>terminated</code> flag value.
	 * The <code>terminated</code> flag is set to <code>true</code> 
	 * once the session is commited into a new revision. 
	 * @return the collaborative session's <code>terminated</code> flag value.
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="DONE"
	 *	sql-type="TINYINT"
	 *  
	 * @ejb.interface-method
	 */
	public abstract Boolean getTerminated();
	/**
	 * Sets the collaborative session's <code>terminated</code> flag value.
	 * @param terminated the new <code>terminated</code> value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setTerminated(Boolean terminated);

	/**
	 * Returns the time the collaborative session was created.
	 * @return the time the collaborative session was created.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="CREATIONTIME"
	 *	sql-type="DATETIME"
	 * @ejb.interface-method
	 *  
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 */
	public abstract Date getCreationTime();
	/**
	 * Sets the time for collaborative session creation, Initialized once, on <code>ejbCreate</code>.
	 * @param CreationTime the new timestamp value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setCreationTime(Date creationTime);

	/**
	 * Returns the collaborative session's description.
	 * @return the collaborative session's description.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="DESCRIPTION"
	 *	sql-type="BLOB"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getDescription();	
	/**
	 * Sets the collaborative session's description.
	 * @param description the new description value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setDescription(String description);
	 
	/**
	 * Returns the last time the OPM model has been saved into the session.
	 * @return the last time the OPM model has been saved into the session.
	 *  
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="LASTUPDATE"
	 *	sql-type="DATETIME" 
	 * @ejb.interface-method
	 *
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 */
	public abstract Date getLastUpdate();
	/**
	 * Sets the last time the OPM model has been saved into the session.
	 * @param lastUpdate the new lastUpdate value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setLastUpdate(Date lastUpdate);
      
	/**
	 * Returns the id for the OPM model the session is based on.
	 * @return the id for the OPM model the session is based on.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="OPMMODELID"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 */
	public abstract String getOpmModelID();
	/**
	 * Sets the id for the OPM model the session is based on.
	 * @param opmModelID the new opmModelID value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setOpmModelID(String opmModelID);

	/**
	 * Returns the id for the OPM model revision the session is based on.
	 * @return the revision id.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *    column-name="REVISIONID"
	 *     sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 */
	public abstract String getRevisionID();
	/**
	 * Sets the id for the OPM model revision the session is based on.
	 * @param revision the new revision id value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setRevisionID(String revisionID);

	/**
	 * Returns the token timeout defined for the session. 
	 * a client holding the session's token has to report to the server 
	 * every <code>tokenTimeout</code> milliseconds. 
	 * Failing to report within this time frame will transfer the token to the server. 
	 * @return the token timeout value.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="TOKENTIMEOUT"
	 *	sql-type="INTEGER"
	 * @ejb.interface-method
	 *  
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract Integer getTokenTimeout();
	/**
	 * Sets the token timeout defined for the session. 
	 * @param tokenTimeout the new tokenTimeout value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setTokenTimeout(Integer tokenTimeout);

	/**
	 * Returns the user timeout defined for the session. 
	 * @return the user timeout value.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="USERTIMEOUT"
	 *	sql-type="INTEGER"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract Integer getUserTimeout();
	/**
	 * Sets the user timeout defined for the session.
	 * @param userTimeout the new user timeout value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setUserTimeout(Integer userTimeout);

	/**
	 * Returns the id for the user currently holding the session token. 
	 * @return the token holder id.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="TOKENHOLDERID"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 */
	public abstract String getTokenHolderID();
	/**
	 * Sets the id for the user currently holding the session token.
	 * @param tokenHolderID the new token holder id value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setTokenHolderID(String tokenHolderID);

	//TODO implement support for holding the OPX file in its comressed form (OPZ).
	/**
	 * Returns the OPX file currently stored in the session.
	 * @return the opmModelFile
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="OPMMODELFILE"
	 *	sql-type="BLOB"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="file"
	 */
	 public abstract String getOpmModelFile();
	 /**
	  * Sets the file currently stored in the session.
	  * @param opmModelFile the new OPX file.
	  * 
	  * @ejb.interface-method
	  */
	 public abstract void setOpmModelFile (String opmModelFile);

	 //	Getter functions for value objects
	 /**
	  * @ejb.interface-method
	  *	view-type="both" 
	  */
	 public CollaborativeSessionValue getCollaborativeSessionValue(){ 
	 	return new CollaborativeSessionValue(
			getCollaborativeSessionID(),
			getCollaborativeSessionName(),
			getEnabled(),
			getTerminated(),
			getCreationTime(),
			getDescription(),
			getLastUpdate(),
			getOpmModelID(),
			getRevisionID(),
			getTokenTimeout(),
			getUserTimeout(),
			getTokenHolderID());
	}
	
	 /**
	  * @ejb.interface-method
	  *	view-type="both" 
	  */
	 public EnhancedCollaborativeSessionValue getEnhancedCollaborativeSessionValue(){ 
	 	return new EnhancedCollaborativeSessionValue(
			getCollaborativeSessionID(),
			getCollaborativeSessionName(),
			getEnabled(),
			getTerminated(),
			getCreationTime(),
			getDescription(),
			getLastUpdate(),
			getOpmModelID(),
			getRevisionID(),
			getTokenTimeout(),
			getUserTimeout(),
			getTokenHolderID());
	 }
	 
	 /**
	  * @ejb.interface-method
	  *	view-type="both" 
	  */
	 public EditableCollaborativeSessionValue getEditableCollaborativeSessionValue(){ 
	 	return new EditableCollaborativeSessionValue(
			getCollaborativeSessionID(),
			getCollaborativeSessionName(),
			getDescription(),
			getOpmModelID(),
			getRevisionID(),
			getTokenTimeout(),
			getUserTimeout());
	 }
	 
	 /**
	  * @ejb.interface-method
	  *	view-type="both" 
	  */
	 public UpdatableCollaborativeSessionValue getUpdatableCollaborativeSessionValue(){ 
	 	return new UpdatableCollaborativeSessionValue(
			getCollaborativeSessionID(),
			getCollaborativeSessionName(),
			getEnabled(),
			getDescription(),
			getTokenTimeout(),
			getUserTimeout());
	 }
	 
	 /**
	  * @ejb.interface-method
	  *	view-type="both" 
	  */
	 public CollaborativeSessionFileValue getCollaborativeSessionFileValue(){ 
	 	return new CollaborativeSessionFileValue(
			getCollaborativeSessionID(),
			getOpmModelFile());
	 }
		
	 //		Setter functions for Editable CollaborativeSession Values
	 
	 /**
	  * @ejb.interface-method
	  *	view-type="both" 
	  */
	 public void setCollaborativeSessionFileValue( CollaborativeSessionFileValue collaborativeSessionFileValue){ 
	 	setOpmModelFile(collaborativeSessionFileValue.getOpmModelFile());
	 }
		
	/**
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public void setUpdatableCollaborativeSessionValue(UpdatableCollaborativeSessionValue updatableCollaborativeSessionValue){
	    if (updatableCollaborativeSessionValue.collaborativeSessionNameHasBeenSet())	
	        setCollaborativeSessionName(updatableCollaborativeSessionValue.getCollaborativeSessionName());
	    if (updatableCollaborativeSessionValue.enabledHasBeenSet())
	        setEnabled(updatableCollaborativeSessionValue.getEnabled());
	    if (updatableCollaborativeSessionValue.descriptionHasBeenSet())
	        setDescription(updatableCollaborativeSessionValue.getDescription());
	    if (updatableCollaborativeSessionValue.tokenTimeoutHasBeenSet())
	        setTokenTimeout(updatableCollaborativeSessionValue.getTokenTimeout());
	    if (updatableCollaborativeSessionValue.userTimeoutHasBeenSet())
	        setUserTimeout(updatableCollaborativeSessionValue.getUserTimeout());
	}
	
	 /**
	  * @ejb.interface-method
	  *	view-type="local"
	  */
	private void setEditableCollaborativeSessionValue(EditableCollaborativeSessionValue editableCollaborativeSessionValue){
	    setCollaborativeSessionID(editableCollaborativeSessionValue.getCollaborativeSessionID());
	    setCollaborativeSessionName(editableCollaborativeSessionValue.getCollaborativeSessionName());
		setDescription(editableCollaborativeSessionValue.getDescription());
		setOpmModelID(editableCollaborativeSessionValue.getOpmModelID());

		setRevisionID(editableCollaborativeSessionValue.getRevisionID());
		setTokenTimeout(editableCollaborativeSessionValue.getTokenTimeout());
		setUserTimeout(editableCollaborativeSessionValue.getUserTimeout());	
	}
}
