/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Value object for User.
 *
 * @lomboz generated
 */
public class EnhancedUserValue
   extends java.lang.Object
   implements java.io.Serializable 
{
   private java.lang.String userID;
   private boolean userIDHasBeenSet = false;
   private java.lang.String loginName;
   private boolean loginNameHasBeenSet = false;
   private java.lang.String firstName;
   private boolean firstNameHasBeenSet = false;
   private java.lang.String lastName;
   private boolean lastNameHasBeenSet = false;
   private java.lang.String password;
   private boolean passwordHasBeenSet = false;
   private java.lang.String email;
   private boolean emailHasBeenSet = false;
   private java.lang.Boolean enabled;
   private boolean enabledHasBeenSet = false;
   private java.lang.Boolean loggedIn;
   private boolean loggedInHasBeenSet = false;
   private java.util.Date lastLoginTime;
   private boolean lastLoginTimeHasBeenSet = false;
   private java.lang.String description;
   private boolean descriptionHasBeenSet = false;
   private java.lang.Boolean administrator;
   private boolean administratorHasBeenSet = false;
   private java.util.Collection EnhancedWorkgroupsPermissions = new java.util.ArrayList();
   private java.util.Collection EnhancedOpmModelsPermissions = new java.util.ArrayList();
   private java.util.Collection EnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();

   private java.lang.String pk;

   public EnhancedUserValue()
   {
   }

   public EnhancedUserValue( java.lang.String userID,java.lang.String loginName,java.lang.String firstName,java.lang.String lastName,java.lang.String password,java.lang.String email,java.lang.Boolean enabled,java.lang.Boolean loggedIn,java.util.Date lastLoginTime,java.lang.String description,java.lang.Boolean administrator )
   {
	  this.userID = userID;
	  userIDHasBeenSet = true;
	  this.loginName = loginName;
	  loginNameHasBeenSet = true;
	  this.firstName = firstName;
	  firstNameHasBeenSet = true;
	  this.lastName = lastName;
	  lastNameHasBeenSet = true;
	  this.password = password;
	  passwordHasBeenSet = true;
	  this.email = email;
	  emailHasBeenSet = true;
	  this.enabled = enabled;
	  enabledHasBeenSet = true;
	  this.loggedIn = loggedIn;
	  loggedInHasBeenSet = true;
	  this.lastLoginTime = lastLoginTime;
	  lastLoginTimeHasBeenSet = true;
	  this.description = description;
	  descriptionHasBeenSet = true;
	  this.administrator = administrator;
	  administratorHasBeenSet = true;
	  pk = this.getUserID();
   }

   //TODO Cloneable is better than this !
   public EnhancedUserValue( EnhancedUserValue otherValue )
   {
	  this.userID = otherValue.userID;
	  userIDHasBeenSet = true;
	  this.loginName = otherValue.loginName;
	  loginNameHasBeenSet = true;
	  this.firstName = otherValue.firstName;
	  firstNameHasBeenSet = true;
	  this.lastName = otherValue.lastName;
	  lastNameHasBeenSet = true;
	  this.password = otherValue.password;
	  passwordHasBeenSet = true;
	  this.email = otherValue.email;
	  emailHasBeenSet = true;
	  this.enabled = otherValue.enabled;
	  enabledHasBeenSet = true;
	  this.loggedIn = otherValue.loggedIn;
	  loggedInHasBeenSet = true;
	  this.lastLoginTime = otherValue.lastLoginTime;
	  lastLoginTimeHasBeenSet = true;
	  this.description = otherValue.description;
	  descriptionHasBeenSet = true;
	  this.administrator = otherValue.administrator;
	  administratorHasBeenSet = true;
	// TODO Clone is better no ?
	  this.EnhancedWorkgroupsPermissions = otherValue.EnhancedWorkgroupsPermissions;
	// TODO Clone is better no ?
	  this.EnhancedOpmModelsPermissions = otherValue.EnhancedOpmModelsPermissions;
	// TODO Clone is better no ?
	  this.EnhancedCollaborativeSessionsPermissions = otherValue.EnhancedCollaborativeSessionsPermissions;

	  pk = this.getUserID();
   }

   public java.lang.String getPrimaryKey()
   {
	  return pk;
   }

   public void setPrimaryKey( java.lang.String pk )
   {
      // it's also nice to update PK object - just in case
      // somebody would ask for it later...
      this.pk = pk;
	  setUserID( pk );
   }

   public java.lang.String getUserID()
   {
	  return this.userID;
   }

   public void setUserID( java.lang.String userID )
   {
	  this.userID = userID;
	  userIDHasBeenSet = true;

		  pk = userID;
   }

   public boolean userIDHasBeenSet(){
	  return userIDHasBeenSet;
   }
   public java.lang.String getLoginName()
   {
	  return this.loginName;
   }

   public void setLoginName( java.lang.String loginName )
   {
	  this.loginName = loginName;
	  loginNameHasBeenSet = true;

   }

   public boolean loginNameHasBeenSet(){
	  return loginNameHasBeenSet;
   }
   public java.lang.String getFirstName()
   {
	  return this.firstName;
   }

   public void setFirstName( java.lang.String firstName )
   {
	  this.firstName = firstName;
	  firstNameHasBeenSet = true;

   }

   public boolean firstNameHasBeenSet(){
	  return firstNameHasBeenSet;
   }
   public java.lang.String getLastName()
   {
	  return this.lastName;
   }

   public void setLastName( java.lang.String lastName )
   {
	  this.lastName = lastName;
	  lastNameHasBeenSet = true;

   }

   public boolean lastNameHasBeenSet(){
	  return lastNameHasBeenSet;
   }
   public java.lang.String getPassword()
   {
	  return this.password;
   }

   public void setPassword( java.lang.String password )
   {
	  this.password = password;
	  passwordHasBeenSet = true;

   }

   public boolean passwordHasBeenSet(){
	  return passwordHasBeenSet;
   }
   public java.lang.String getEmail()
   {
	  return this.email;
   }

   public void setEmail( java.lang.String email )
   {
	  this.email = email;
	  emailHasBeenSet = true;

   }

   public boolean emailHasBeenSet(){
	  return emailHasBeenSet;
   }
   public java.lang.Boolean getEnabled()
   {
	  return this.enabled;
   }

   public void setEnabled( java.lang.Boolean enabled )
   {
	  this.enabled = enabled;
	  enabledHasBeenSet = true;

   }

   public boolean enabledHasBeenSet(){
	  return enabledHasBeenSet;
   }
   public java.lang.Boolean getLoggedIn()
   {
	  return this.loggedIn;
   }

   public void setLoggedIn( java.lang.Boolean loggedIn )
   {
	  this.loggedIn = loggedIn;
	  loggedInHasBeenSet = true;

   }

   public boolean loggedInHasBeenSet(){
	  return loggedInHasBeenSet;
   }
   public java.util.Date getLastLoginTime()
   {
	  return this.lastLoginTime;
   }

   public void setLastLoginTime( java.util.Date lastLoginTime )
   {
	  this.lastLoginTime = lastLoginTime;
	  lastLoginTimeHasBeenSet = true;

   }

   public boolean lastLoginTimeHasBeenSet(){
	  return lastLoginTimeHasBeenSet;
   }
   public java.lang.String getDescription()
   {
	  return this.description;
   }

   public void setDescription( java.lang.String description )
   {
	  this.description = description;
	  descriptionHasBeenSet = true;

   }

   public boolean descriptionHasBeenSet(){
	  return descriptionHasBeenSet;
   }
   public java.lang.Boolean getAdministrator()
   {
	  return this.administrator;
   }

   public void setAdministrator( java.lang.Boolean administrator )
   {
	  this.administrator = administrator;
	  administratorHasBeenSet = true;

   }

   public boolean administratorHasBeenSet(){
	  return administratorHasBeenSet;
   }

   protected java.util.Collection addedEnhancedWorkgroupsPermissions = new java.util.ArrayList();
   protected java.util.Collection onceAddedEnhancedWorkgroupsPermissions = new java.util.ArrayList();
   protected java.util.Collection removedEnhancedWorkgroupsPermissions = new java.util.ArrayList();
   protected java.util.Collection updatedEnhancedWorkgroupsPermissions = new java.util.ArrayList();

   public java.util.Collection getAddedEnhancedWorkgroupsPermissions() { return addedEnhancedWorkgroupsPermissions; }
   public java.util.Collection getOnceAddedEnhancedWorkgroupsPermissions() { return onceAddedEnhancedWorkgroupsPermissions; }
   public java.util.Collection getRemovedEnhancedWorkgroupsPermissions() { return removedEnhancedWorkgroupsPermissions; }
   public java.util.Collection getUpdatedEnhancedWorkgroupsPermissions() { return updatedEnhancedWorkgroupsPermissions; }

   public void setAddedEnhancedWorkgroupsPermissions(java.util.Collection addedEnhancedWorkgroupsPermissions)
   {
      this.addedEnhancedWorkgroupsPermissions.clear();
      this.addedEnhancedWorkgroupsPermissions.addAll(addedEnhancedWorkgroupsPermissions);
   }

   public void setOnceAddedEnhancedWorkgroupsPermissions(java.util.Collection onceAddedEnhancedWorkgroupsPermissions)
   {
      this.onceAddedEnhancedWorkgroupsPermissions.clear();
      this.onceAddedEnhancedWorkgroupsPermissions.addAll(onceAddedEnhancedWorkgroupsPermissions);
   }

   public void setRemovedEnhancedWorkgroupsPermissions(java.util.Collection removedEnhancedWorkgroupsPermissions)
   {
      this.removedEnhancedWorkgroupsPermissions.clear();
      this.removedEnhancedWorkgroupsPermissions.addAll(removedEnhancedWorkgroupsPermissions);
   }

   public void setUpdatedEnhancedWorkgroupsPermissions(java.util.Collection updatedEnhancedWorkgroupsPermissions)
   {
      this.updatedEnhancedWorkgroupsPermissions.clear();
      this.updatedEnhancedWorkgroupsPermissions.addAll(updatedEnhancedWorkgroupsPermissions);
   }

   public org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue[] getEnhancedWorkgroupsPermissions()
   {
	  return (org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue[])this.EnhancedWorkgroupsPermissions.toArray(new org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue[EnhancedWorkgroupsPermissions.size()]);
   }

   public void setEnhancedWorkgroupsPermissions(org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue[] EnhancedWorkgroupsPermissions)
   {
      this.EnhancedWorkgroupsPermissions.clear();
      for (int i=0; i < EnhancedWorkgroupsPermissions.length; i++)
      	this.EnhancedWorkgroupsPermissions.add(EnhancedWorkgroupsPermissions[i]);
   }

   public void clearEnhancedWorkgroupsPermissions()
   {
	  this.EnhancedWorkgroupsPermissions.clear();
   }

   public void addEnhancedWorkgroupsPermission(org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue added)
   {
	  this.EnhancedWorkgroupsPermissions.add(added);

      if (this.removedEnhancedWorkgroupsPermissions.contains(added))
      {
        this.removedEnhancedWorkgroupsPermissions.remove(added);
        if (this.onceAddedEnhancedWorkgroupsPermissions.contains(added))
        {
          if (! this.addedEnhancedWorkgroupsPermissions.contains(added))
            this.addedEnhancedWorkgroupsPermissions.add(added);
        }
        else if (! this.updatedEnhancedWorkgroupsPermissions.contains(added))
        {
            this.updatedEnhancedWorkgroupsPermissions.add(added);
        }
      }
      else
      {
        if (! this.onceAddedEnhancedWorkgroupsPermissions.contains(added))
          this.onceAddedEnhancedWorkgroupsPermissions.add(added);
        if (! this.addedEnhancedWorkgroupsPermissions.contains(added))
          this.addedEnhancedWorkgroupsPermissions.add(added);
      }
   }

   public void removeEnhancedWorkgroupsPermission(org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue removed)
   {
	  this.EnhancedWorkgroupsPermissions.remove(removed);

      if (this.addedEnhancedWorkgroupsPermissions.contains(removed))
        this.addedEnhancedWorkgroupsPermissions.remove(removed);
      else if (! this.removedEnhancedWorkgroupsPermissions.contains(removed))
        this.removedEnhancedWorkgroupsPermissions.add(removed);

	  if (this.updatedEnhancedWorkgroupsPermissions.contains(removed))
		 this.updatedEnhancedWorkgroupsPermissions.remove(removed);
   }

   public void updateEnhancedWorkgroupsPermission(org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue updated)
   {
	  if ( !this.updatedEnhancedWorkgroupsPermissions.contains(updated) && !this.addedEnhancedWorkgroupsPermissions.contains(updated))
		 this.updatedEnhancedWorkgroupsPermissions.add(updated);
      if (this.removedEnhancedWorkgroupsPermissions.contains(updated))
         this.removedEnhancedWorkgroupsPermissions.remove(updated);
   }

   public void cleanEnhancedWorkgroupsPermission(){
	  this.addedEnhancedWorkgroupsPermissions = new java.util.ArrayList();
      this.onceAddedEnhancedWorkgroupsPermissions = new java.util.ArrayList();
	  this.removedEnhancedWorkgroupsPermissions = new java.util.ArrayList();
	  this.updatedEnhancedWorkgroupsPermissions = new java.util.ArrayList();
   }

   public void copyEnhancedWorkgroupsPermissionsFrom(org.objectprocess.cmp.EnhancedUserValue from)
   {
	  // TODO Clone the List ????
	  this.EnhancedWorkgroupsPermissions = from.EnhancedWorkgroupsPermissions;
   }
   protected java.util.Collection addedEnhancedOpmModelsPermissions = new java.util.ArrayList();
   protected java.util.Collection onceAddedEnhancedOpmModelsPermissions = new java.util.ArrayList();
   protected java.util.Collection removedEnhancedOpmModelsPermissions = new java.util.ArrayList();
   protected java.util.Collection updatedEnhancedOpmModelsPermissions = new java.util.ArrayList();

   public java.util.Collection getAddedEnhancedOpmModelsPermissions() { return addedEnhancedOpmModelsPermissions; }
   public java.util.Collection getOnceAddedEnhancedOpmModelsPermissions() { return onceAddedEnhancedOpmModelsPermissions; }
   public java.util.Collection getRemovedEnhancedOpmModelsPermissions() { return removedEnhancedOpmModelsPermissions; }
   public java.util.Collection getUpdatedEnhancedOpmModelsPermissions() { return updatedEnhancedOpmModelsPermissions; }

   public void setAddedEnhancedOpmModelsPermissions(java.util.Collection addedEnhancedOpmModelsPermissions)
   {
      this.addedEnhancedOpmModelsPermissions.clear();
      this.addedEnhancedOpmModelsPermissions.addAll(addedEnhancedOpmModelsPermissions);
   }

   public void setOnceAddedEnhancedOpmModelsPermissions(java.util.Collection onceAddedEnhancedOpmModelsPermissions)
   {
      this.onceAddedEnhancedOpmModelsPermissions.clear();
      this.onceAddedEnhancedOpmModelsPermissions.addAll(onceAddedEnhancedOpmModelsPermissions);
   }

   public void setRemovedEnhancedOpmModelsPermissions(java.util.Collection removedEnhancedOpmModelsPermissions)
   {
      this.removedEnhancedOpmModelsPermissions.clear();
      this.removedEnhancedOpmModelsPermissions.addAll(removedEnhancedOpmModelsPermissions);
   }

   public void setUpdatedEnhancedOpmModelsPermissions(java.util.Collection updatedEnhancedOpmModelsPermissions)
   {
      this.updatedEnhancedOpmModelsPermissions.clear();
      this.updatedEnhancedOpmModelsPermissions.addAll(updatedEnhancedOpmModelsPermissions);
   }

   public org.objectprocess.cmp.EnhancedOpmModelPermissionsValue[] getEnhancedOpmModelsPermissions()
   {
	  return (org.objectprocess.cmp.EnhancedOpmModelPermissionsValue[])this.EnhancedOpmModelsPermissions.toArray(new org.objectprocess.cmp.EnhancedOpmModelPermissionsValue[EnhancedOpmModelsPermissions.size()]);
   }

   public void setEnhancedOpmModelsPermissions(org.objectprocess.cmp.EnhancedOpmModelPermissionsValue[] EnhancedOpmModelsPermissions)
   {
      this.EnhancedOpmModelsPermissions.clear();
      for (int i=0; i < EnhancedOpmModelsPermissions.length; i++)
      	this.EnhancedOpmModelsPermissions.add(EnhancedOpmModelsPermissions[i]);
   }

   public void clearEnhancedOpmModelsPermissions()
   {
	  this.EnhancedOpmModelsPermissions.clear();
   }

   public void addEnhancedOpmModelsPermission(org.objectprocess.cmp.EnhancedOpmModelPermissionsValue added)
   {
	  this.EnhancedOpmModelsPermissions.add(added);

      if (this.removedEnhancedOpmModelsPermissions.contains(added))
      {
        this.removedEnhancedOpmModelsPermissions.remove(added);
        if (this.onceAddedEnhancedOpmModelsPermissions.contains(added))
        {
          if (! this.addedEnhancedOpmModelsPermissions.contains(added))
            this.addedEnhancedOpmModelsPermissions.add(added);
        }
        else if (! this.updatedEnhancedOpmModelsPermissions.contains(added))
        {
            this.updatedEnhancedOpmModelsPermissions.add(added);
        }
      }
      else
      {
        if (! this.onceAddedEnhancedOpmModelsPermissions.contains(added))
          this.onceAddedEnhancedOpmModelsPermissions.add(added);
        if (! this.addedEnhancedOpmModelsPermissions.contains(added))
          this.addedEnhancedOpmModelsPermissions.add(added);
      }
   }

   public void removeEnhancedOpmModelsPermission(org.objectprocess.cmp.EnhancedOpmModelPermissionsValue removed)
   {
	  this.EnhancedOpmModelsPermissions.remove(removed);

      if (this.addedEnhancedOpmModelsPermissions.contains(removed))
        this.addedEnhancedOpmModelsPermissions.remove(removed);
      else if (! this.removedEnhancedOpmModelsPermissions.contains(removed))
        this.removedEnhancedOpmModelsPermissions.add(removed);

	  if (this.updatedEnhancedOpmModelsPermissions.contains(removed))
		 this.updatedEnhancedOpmModelsPermissions.remove(removed);
   }

   public void updateEnhancedOpmModelsPermission(org.objectprocess.cmp.EnhancedOpmModelPermissionsValue updated)
   {
	  if ( !this.updatedEnhancedOpmModelsPermissions.contains(updated) && !this.addedEnhancedOpmModelsPermissions.contains(updated))
		 this.updatedEnhancedOpmModelsPermissions.add(updated);
      if (this.removedEnhancedOpmModelsPermissions.contains(updated))
         this.removedEnhancedOpmModelsPermissions.remove(updated);
   }

   public void cleanEnhancedOpmModelsPermission(){
	  this.addedEnhancedOpmModelsPermissions = new java.util.ArrayList();
      this.onceAddedEnhancedOpmModelsPermissions = new java.util.ArrayList();
	  this.removedEnhancedOpmModelsPermissions = new java.util.ArrayList();
	  this.updatedEnhancedOpmModelsPermissions = new java.util.ArrayList();
   }

   public void copyEnhancedOpmModelsPermissionsFrom(org.objectprocess.cmp.EnhancedUserValue from)
   {
	  // TODO Clone the List ????
	  this.EnhancedOpmModelsPermissions = from.EnhancedOpmModelsPermissions;
   }
   protected java.util.Collection addedEnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();
   protected java.util.Collection onceAddedEnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();
   protected java.util.Collection removedEnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();
   protected java.util.Collection updatedEnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();

   public java.util.Collection getAddedEnhancedCollaborativeSessionsPermissions() { return addedEnhancedCollaborativeSessionsPermissions; }
   public java.util.Collection getOnceAddedEnhancedCollaborativeSessionsPermissions() { return onceAddedEnhancedCollaborativeSessionsPermissions; }
   public java.util.Collection getRemovedEnhancedCollaborativeSessionsPermissions() { return removedEnhancedCollaborativeSessionsPermissions; }
   public java.util.Collection getUpdatedEnhancedCollaborativeSessionsPermissions() { return updatedEnhancedCollaborativeSessionsPermissions; }

   public void setAddedEnhancedCollaborativeSessionsPermissions(java.util.Collection addedEnhancedCollaborativeSessionsPermissions)
   {
      this.addedEnhancedCollaborativeSessionsPermissions.clear();
      this.addedEnhancedCollaborativeSessionsPermissions.addAll(addedEnhancedCollaborativeSessionsPermissions);
   }

   public void setOnceAddedEnhancedCollaborativeSessionsPermissions(java.util.Collection onceAddedEnhancedCollaborativeSessionsPermissions)
   {
      this.onceAddedEnhancedCollaborativeSessionsPermissions.clear();
      this.onceAddedEnhancedCollaborativeSessionsPermissions.addAll(onceAddedEnhancedCollaborativeSessionsPermissions);
   }

   public void setRemovedEnhancedCollaborativeSessionsPermissions(java.util.Collection removedEnhancedCollaborativeSessionsPermissions)
   {
      this.removedEnhancedCollaborativeSessionsPermissions.clear();
      this.removedEnhancedCollaborativeSessionsPermissions.addAll(removedEnhancedCollaborativeSessionsPermissions);
   }

   public void setUpdatedEnhancedCollaborativeSessionsPermissions(java.util.Collection updatedEnhancedCollaborativeSessionsPermissions)
   {
      this.updatedEnhancedCollaborativeSessionsPermissions.clear();
      this.updatedEnhancedCollaborativeSessionsPermissions.addAll(updatedEnhancedCollaborativeSessionsPermissions);
   }

   public org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue[] getEnhancedCollaborativeSessionsPermissions()
   {
	  return (org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue[])this.EnhancedCollaborativeSessionsPermissions.toArray(new org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue[EnhancedCollaborativeSessionsPermissions.size()]);
   }

   public void setEnhancedCollaborativeSessionsPermissions(org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue[] EnhancedCollaborativeSessionsPermissions)
   {
      this.EnhancedCollaborativeSessionsPermissions.clear();
      for (int i=0; i < EnhancedCollaborativeSessionsPermissions.length; i++)
      	this.EnhancedCollaborativeSessionsPermissions.add(EnhancedCollaborativeSessionsPermissions[i]);
   }

   public void clearEnhancedCollaborativeSessionsPermissions()
   {
	  this.EnhancedCollaborativeSessionsPermissions.clear();
   }

   public void addEnhancedCollaborativeSessionsPermission(org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue added)
   {
	  this.EnhancedCollaborativeSessionsPermissions.add(added);

      if (this.removedEnhancedCollaborativeSessionsPermissions.contains(added))
      {
        this.removedEnhancedCollaborativeSessionsPermissions.remove(added);
        if (this.onceAddedEnhancedCollaborativeSessionsPermissions.contains(added))
        {
          if (! this.addedEnhancedCollaborativeSessionsPermissions.contains(added))
            this.addedEnhancedCollaborativeSessionsPermissions.add(added);
        }
        else if (! this.updatedEnhancedCollaborativeSessionsPermissions.contains(added))
        {
            this.updatedEnhancedCollaborativeSessionsPermissions.add(added);
        }
      }
      else
      {
        if (! this.onceAddedEnhancedCollaborativeSessionsPermissions.contains(added))
          this.onceAddedEnhancedCollaborativeSessionsPermissions.add(added);
        if (! this.addedEnhancedCollaborativeSessionsPermissions.contains(added))
          this.addedEnhancedCollaborativeSessionsPermissions.add(added);
      }
   }

   public void removeEnhancedCollaborativeSessionsPermission(org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue removed)
   {
	  this.EnhancedCollaborativeSessionsPermissions.remove(removed);

      if (this.addedEnhancedCollaborativeSessionsPermissions.contains(removed))
        this.addedEnhancedCollaborativeSessionsPermissions.remove(removed);
      else if (! this.removedEnhancedCollaborativeSessionsPermissions.contains(removed))
        this.removedEnhancedCollaborativeSessionsPermissions.add(removed);

	  if (this.updatedEnhancedCollaborativeSessionsPermissions.contains(removed))
		 this.updatedEnhancedCollaborativeSessionsPermissions.remove(removed);
   }

   public void updateEnhancedCollaborativeSessionsPermission(org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue updated)
   {
	  if ( !this.updatedEnhancedCollaborativeSessionsPermissions.contains(updated) && !this.addedEnhancedCollaborativeSessionsPermissions.contains(updated))
		 this.updatedEnhancedCollaborativeSessionsPermissions.add(updated);
      if (this.removedEnhancedCollaborativeSessionsPermissions.contains(updated))
         this.removedEnhancedCollaborativeSessionsPermissions.remove(updated);
   }

   public void cleanEnhancedCollaborativeSessionsPermission(){
	  this.addedEnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();
      this.onceAddedEnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();
	  this.removedEnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();
	  this.updatedEnhancedCollaborativeSessionsPermissions = new java.util.ArrayList();
   }

   public void copyEnhancedCollaborativeSessionsPermissionsFrom(org.objectprocess.cmp.EnhancedUserValue from)
   {
	  // TODO Clone the List ????
	  this.EnhancedCollaborativeSessionsPermissions = from.EnhancedCollaborativeSessionsPermissions;
   }

   public String toString()
   {
	  StringBuffer str = new StringBuffer("{");

	  str.append("userID=" + getUserID() + " " + "loginName=" + getLoginName() + " " + "firstName=" + getFirstName() + " " + "lastName=" + getLastName() + " " + "password=" + getPassword() + " " + "email=" + getEmail() + " " + "enabled=" + getEnabled() + " " + "loggedIn=" + getLoggedIn() + " " + "lastLoginTime=" + getLastLoginTime() + " " + "description=" + getDescription() + " " + "administrator=" + getAdministrator());
	  str.append('}');

	  return(str.toString());
   }

   /**
    * A Value Object has an identity if the attributes making its Primary Key have all been set. An object without identity is never equal to any other object.
    *
    * @return true if this instance has an identity.
    */
   protected boolean hasIdentity()
   {
	  return userIDHasBeenSet;
   }

   public boolean equals(Object other)
   {
      if (this == other)
         return true;
	  if ( ! hasIdentity() ) return false;
	  if (other instanceof EnhancedUserValue)
	  {
		 EnhancedUserValue that = (EnhancedUserValue) other;
		 if ( ! that.hasIdentity() ) return false;
		 boolean lEquals = true;
		 if( this.userID == null )
		 {
			lEquals = lEquals && ( that.userID == null );
		 }
		 else
		 {
			lEquals = lEquals && this.userID.equals( that.userID );
		 }

		 lEquals = lEquals && isIdentical(that);

		 return lEquals;
	  }
	  else
	  {
		 return false;
	  }
   }

   public boolean isIdentical(Object other)
   {
	  if (other instanceof EnhancedUserValue)
	  {
		 EnhancedUserValue that = (EnhancedUserValue) other;
		 boolean lEquals = true;
		 if( this.loginName == null )
		 {
			lEquals = lEquals && ( that.loginName == null );
		 }
		 else
		 {
			lEquals = lEquals && this.loginName.equals( that.loginName );
		 }
		 if( this.firstName == null )
		 {
			lEquals = lEquals && ( that.firstName == null );
		 }
		 else
		 {
			lEquals = lEquals && this.firstName.equals( that.firstName );
		 }
		 if( this.lastName == null )
		 {
			lEquals = lEquals && ( that.lastName == null );
		 }
		 else
		 {
			lEquals = lEquals && this.lastName.equals( that.lastName );
		 }
		 if( this.password == null )
		 {
			lEquals = lEquals && ( that.password == null );
		 }
		 else
		 {
			lEquals = lEquals && this.password.equals( that.password );
		 }
		 if( this.email == null )
		 {
			lEquals = lEquals && ( that.email == null );
		 }
		 else
		 {
			lEquals = lEquals && this.email.equals( that.email );
		 }
		 if( this.enabled == null )
		 {
			lEquals = lEquals && ( that.enabled == null );
		 }
		 else
		 {
			lEquals = lEquals && this.enabled.equals( that.enabled );
		 }
		 if( this.loggedIn == null )
		 {
			lEquals = lEquals && ( that.loggedIn == null );
		 }
		 else
		 {
			lEquals = lEquals && this.loggedIn.equals( that.loggedIn );
		 }
		 if( this.lastLoginTime == null )
		 {
			lEquals = lEquals && ( that.lastLoginTime == null );
		 }
		 else
		 {
			lEquals = lEquals && this.lastLoginTime.equals( that.lastLoginTime );
		 }
		 if( this.description == null )
		 {
			lEquals = lEquals && ( that.description == null );
		 }
		 else
		 {
			lEquals = lEquals && this.description.equals( that.description );
		 }
		 if( this.administrator == null )
		 {
			lEquals = lEquals && ( that.administrator == null );
		 }
		 else
		 {
			lEquals = lEquals && this.administrator.equals( that.administrator );
		 }
		 if( this.getEnhancedWorkgroupsPermissions() == null )
		 {
			lEquals = lEquals && ( that.getEnhancedWorkgroupsPermissions() == null );
		 }
		 else
		 {
			lEquals = lEquals && java.util.Arrays.equals(this.getEnhancedWorkgroupsPermissions() , that.getEnhancedWorkgroupsPermissions()) ;
		 }
		 if( this.getEnhancedOpmModelsPermissions() == null )
		 {
			lEquals = lEquals && ( that.getEnhancedOpmModelsPermissions() == null );
		 }
		 else
		 {
			lEquals = lEquals && java.util.Arrays.equals(this.getEnhancedOpmModelsPermissions() , that.getEnhancedOpmModelsPermissions()) ;
		 }
		 if( this.getEnhancedCollaborativeSessionsPermissions() == null )
		 {
			lEquals = lEquals && ( that.getEnhancedCollaborativeSessionsPermissions() == null );
		 }
		 else
		 {
			lEquals = lEquals && java.util.Arrays.equals(this.getEnhancedCollaborativeSessionsPermissions() , that.getEnhancedCollaborativeSessionsPermissions()) ;
		 }

		 return lEquals;
	  }
	  else
	  {
		 return false;
	  }
   }

   public int hashCode(){
	  int result = 17;
      result = 37*result + ((this.userID != null) ? this.userID.hashCode() : 0);

      result = 37*result + ((this.loginName != null) ? this.loginName.hashCode() : 0);

      result = 37*result + ((this.firstName != null) ? this.firstName.hashCode() : 0);

      result = 37*result + ((this.lastName != null) ? this.lastName.hashCode() : 0);

      result = 37*result + ((this.password != null) ? this.password.hashCode() : 0);

      result = 37*result + ((this.email != null) ? this.email.hashCode() : 0);

      result = 37*result + ((this.enabled != null) ? this.enabled.hashCode() : 0);

      result = 37*result + ((this.loggedIn != null) ? this.loggedIn.hashCode() : 0);

      result = 37*result + ((this.lastLoginTime != null) ? this.lastLoginTime.hashCode() : 0);

      result = 37*result + ((this.description != null) ? this.description.hashCode() : 0);

      result = 37*result + ((this.administrator != null) ? this.administrator.hashCode() : 0);

	  result = 37*result + ((this.getEnhancedWorkgroupsPermissions() != null) ? this.getEnhancedWorkgroupsPermissions().hashCode() : 0);
	  result = 37*result + ((this.getEnhancedOpmModelsPermissions() != null) ? this.getEnhancedOpmModelsPermissions().hashCode() : 0);
	  result = 37*result + ((this.getEnhancedCollaborativeSessionsPermissions() != null) ? this.getEnhancedCollaborativeSessionsPermissions().hashCode() : 0);
	  return result;
   }

}