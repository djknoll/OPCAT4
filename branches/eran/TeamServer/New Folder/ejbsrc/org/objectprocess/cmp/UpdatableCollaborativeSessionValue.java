/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Value object for CollaborativeSession.
 *
 * @lomboz generated
 */
public class UpdatableCollaborativeSessionValue
   extends java.lang.Object
   implements java.io.Serializable 
{
   private java.lang.String collaborativeSessionID;
   private boolean collaborativeSessionIDHasBeenSet = false;
   private java.lang.String collaborativeSessionName;
   private boolean collaborativeSessionNameHasBeenSet = false;
   private java.lang.Boolean enabled;
   private boolean enabledHasBeenSet = false;
   private java.lang.String description;
   private boolean descriptionHasBeenSet = false;
   private java.lang.Integer tokenTimeout;
   private boolean tokenTimeoutHasBeenSet = false;
   private java.lang.Integer userTimeout;
   private boolean userTimeoutHasBeenSet = false;

   private java.lang.String pk;

   public UpdatableCollaborativeSessionValue()
   {
   }

   public UpdatableCollaborativeSessionValue( java.lang.String collaborativeSessionID,java.lang.String collaborativeSessionName,java.lang.Boolean enabled,java.lang.String description,java.lang.Integer tokenTimeout,java.lang.Integer userTimeout )
   {
	  this.collaborativeSessionID = collaborativeSessionID;
	  collaborativeSessionIDHasBeenSet = true;
	  this.collaborativeSessionName = collaborativeSessionName;
	  collaborativeSessionNameHasBeenSet = true;
	  this.enabled = enabled;
	  enabledHasBeenSet = true;
	  this.description = description;
	  descriptionHasBeenSet = true;
	  this.tokenTimeout = tokenTimeout;
	  tokenTimeoutHasBeenSet = true;
	  this.userTimeout = userTimeout;
	  userTimeoutHasBeenSet = true;
	  pk = this.getCollaborativeSessionID();
   }

   //TODO Cloneable is better than this !
   public UpdatableCollaborativeSessionValue( UpdatableCollaborativeSessionValue otherValue )
   {
	  this.collaborativeSessionID = otherValue.collaborativeSessionID;
	  collaborativeSessionIDHasBeenSet = true;
	  this.collaborativeSessionName = otherValue.collaborativeSessionName;
	  collaborativeSessionNameHasBeenSet = true;
	  this.enabled = otherValue.enabled;
	  enabledHasBeenSet = true;
	  this.description = otherValue.description;
	  descriptionHasBeenSet = true;
	  this.tokenTimeout = otherValue.tokenTimeout;
	  tokenTimeoutHasBeenSet = true;
	  this.userTimeout = otherValue.userTimeout;
	  userTimeoutHasBeenSet = true;

	  pk = this.getCollaborativeSessionID();
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
	  setCollaborativeSessionID( pk );
   }

   public java.lang.String getCollaborativeSessionID()
   {
	  return this.collaborativeSessionID;
   }

   public void setCollaborativeSessionID( java.lang.String collaborativeSessionID )
   {
	  this.collaborativeSessionID = collaborativeSessionID;
	  collaborativeSessionIDHasBeenSet = true;

		  pk = collaborativeSessionID;
   }

   public boolean collaborativeSessionIDHasBeenSet(){
	  return collaborativeSessionIDHasBeenSet;
   }
   public java.lang.String getCollaborativeSessionName()
   {
	  return this.collaborativeSessionName;
   }

   public void setCollaborativeSessionName( java.lang.String collaborativeSessionName )
   {
	  this.collaborativeSessionName = collaborativeSessionName;
	  collaborativeSessionNameHasBeenSet = true;

   }

   public boolean collaborativeSessionNameHasBeenSet(){
	  return collaborativeSessionNameHasBeenSet;
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
   public java.lang.Integer getTokenTimeout()
   {
	  return this.tokenTimeout;
   }

   public void setTokenTimeout( java.lang.Integer tokenTimeout )
   {
	  this.tokenTimeout = tokenTimeout;
	  tokenTimeoutHasBeenSet = true;

   }

   public boolean tokenTimeoutHasBeenSet(){
	  return tokenTimeoutHasBeenSet;
   }
   public java.lang.Integer getUserTimeout()
   {
	  return this.userTimeout;
   }

   public void setUserTimeout( java.lang.Integer userTimeout )
   {
	  this.userTimeout = userTimeout;
	  userTimeoutHasBeenSet = true;

   }

   public boolean userTimeoutHasBeenSet(){
	  return userTimeoutHasBeenSet;
   }

   public String toString()
   {
	  StringBuffer str = new StringBuffer("{");

	  str.append("collaborativeSessionID=" + getCollaborativeSessionID() + " " + "collaborativeSessionName=" + getCollaborativeSessionName() + " " + "enabled=" + getEnabled() + " " + "description=" + getDescription() + " " + "tokenTimeout=" + getTokenTimeout() + " " + "userTimeout=" + getUserTimeout());
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
	  return collaborativeSessionIDHasBeenSet;
   }

   public boolean equals(Object other)
   {
      if (this == other)
         return true;
	  if ( ! hasIdentity() ) return false;
	  if (other instanceof UpdatableCollaborativeSessionValue)
	  {
		 UpdatableCollaborativeSessionValue that = (UpdatableCollaborativeSessionValue) other;
		 if ( ! that.hasIdentity() ) return false;
		 boolean lEquals = true;
		 if( this.collaborativeSessionID == null )
		 {
			lEquals = lEquals && ( that.collaborativeSessionID == null );
		 }
		 else
		 {
			lEquals = lEquals && this.collaborativeSessionID.equals( that.collaborativeSessionID );
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
	  if (other instanceof UpdatableCollaborativeSessionValue)
	  {
		 UpdatableCollaborativeSessionValue that = (UpdatableCollaborativeSessionValue) other;
		 boolean lEquals = true;
		 if( this.collaborativeSessionName == null )
		 {
			lEquals = lEquals && ( that.collaborativeSessionName == null );
		 }
		 else
		 {
			lEquals = lEquals && this.collaborativeSessionName.equals( that.collaborativeSessionName );
		 }
		 if( this.enabled == null )
		 {
			lEquals = lEquals && ( that.enabled == null );
		 }
		 else
		 {
			lEquals = lEquals && this.enabled.equals( that.enabled );
		 }
		 if( this.description == null )
		 {
			lEquals = lEquals && ( that.description == null );
		 }
		 else
		 {
			lEquals = lEquals && this.description.equals( that.description );
		 }
		 if( this.tokenTimeout == null )
		 {
			lEquals = lEquals && ( that.tokenTimeout == null );
		 }
		 else
		 {
			lEquals = lEquals && this.tokenTimeout.equals( that.tokenTimeout );
		 }
		 if( this.userTimeout == null )
		 {
			lEquals = lEquals && ( that.userTimeout == null );
		 }
		 else
		 {
			lEquals = lEquals && this.userTimeout.equals( that.userTimeout );
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
      result = 37*result + ((this.collaborativeSessionID != null) ? this.collaborativeSessionID.hashCode() : 0);

      result = 37*result + ((this.collaborativeSessionName != null) ? this.collaborativeSessionName.hashCode() : 0);

      result = 37*result + ((this.enabled != null) ? this.enabled.hashCode() : 0);

      result = 37*result + ((this.description != null) ? this.description.hashCode() : 0);

      result = 37*result + ((this.tokenTimeout != null) ? this.tokenTimeout.hashCode() : 0);

      result = 37*result + ((this.userTimeout != null) ? this.userTimeout.hashCode() : 0);

	  return result;
   }

}