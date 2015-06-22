/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Value object for OpmModelPermissions.
 *
 * @lomboz generated
 */
public class Enhanced2OpmModelPermissionsValue
   extends java.lang.Object
   implements java.io.Serializable 
{
   private java.lang.String opmModelID;
   private boolean opmModelIDHasBeenSet = false;
   private java.lang.String userID;
   private boolean userIDHasBeenSet = false;
   private java.util.Date joinTime;
   private boolean joinTimeHasBeenSet = false;
   private java.lang.Integer accessControl;
   private boolean accessControlHasBeenSet = false;
   private org.objectprocess.cmp.UserValue User;
   private boolean UserHasBeenSet = false;

   private org.objectprocess.cmp.OpmModelPermissionsPK pk;

   public Enhanced2OpmModelPermissionsValue()
   {
	  pk = new org.objectprocess.cmp.OpmModelPermissionsPK();
   }

   public Enhanced2OpmModelPermissionsValue( java.lang.String opmModelID,java.lang.String userID,java.util.Date joinTime,java.lang.Integer accessControl )
   {
	  this.opmModelID = opmModelID;
	  opmModelIDHasBeenSet = true;
	  this.userID = userID;
	  userIDHasBeenSet = true;
	  this.joinTime = joinTime;
	  joinTimeHasBeenSet = true;
	  this.accessControl = accessControl;
	  accessControlHasBeenSet = true;
	  pk = new org.objectprocess.cmp.OpmModelPermissionsPK(this.getOpmModelID(),this.getUserID());
   }

   //TODO Cloneable is better than this !
   public Enhanced2OpmModelPermissionsValue( Enhanced2OpmModelPermissionsValue otherValue )
   {
	  this.opmModelID = otherValue.opmModelID;
	  opmModelIDHasBeenSet = true;
	  this.userID = otherValue.userID;
	  userIDHasBeenSet = true;
	  this.joinTime = otherValue.joinTime;
	  joinTimeHasBeenSet = true;
	  this.accessControl = otherValue.accessControl;
	  accessControlHasBeenSet = true;
	// TODO Clone is better no ?
	  this.User = otherValue.User;
	  UserHasBeenSet = true;

	  pk = new org.objectprocess.cmp.OpmModelPermissionsPK(this.getOpmModelID(),this.getUserID());
   }

   public org.objectprocess.cmp.OpmModelPermissionsPK getPrimaryKey()
   {
	  return pk;
   }

   public void setPrimaryKey( org.objectprocess.cmp.OpmModelPermissionsPK pk )
   {
      // it's also nice to update PK object - just in case
      // somebody would ask for it later...
      this.pk = pk;
	  setOpmModelID( pk.opmModelID );
	  setUserID( pk.userID );
   }

   public java.lang.String getOpmModelID()
   {
	  return this.opmModelID;
   }

   public void setOpmModelID( java.lang.String opmModelID )
   {
	  this.opmModelID = opmModelID;
	  opmModelIDHasBeenSet = true;

		 pk.setOpmModelID(opmModelID);
   }

   public boolean opmModelIDHasBeenSet(){
	  return opmModelIDHasBeenSet;
   }
   public java.lang.String getUserID()
   {
	  return this.userID;
   }

   public void setUserID( java.lang.String userID )
   {
	  this.userID = userID;
	  userIDHasBeenSet = true;

		 pk.setUserID(userID);
   }

   public boolean userIDHasBeenSet(){
	  return userIDHasBeenSet;
   }
   public java.util.Date getJoinTime()
   {
	  return this.joinTime;
   }

   public void setJoinTime( java.util.Date joinTime )
   {
	  this.joinTime = joinTime;
	  joinTimeHasBeenSet = true;

   }

   public boolean joinTimeHasBeenSet(){
	  return joinTimeHasBeenSet;
   }
   public java.lang.Integer getAccessControl()
   {
	  return this.accessControl;
   }

   public void setAccessControl( java.lang.Integer accessControl )
   {
	  this.accessControl = accessControl;
	  accessControlHasBeenSet = true;

   }

   public boolean accessControlHasBeenSet(){
	  return accessControlHasBeenSet;
   }

   public org.objectprocess.cmp.UserValue getUser()
   {
	  return this.User;
   }
   public void setUser( org.objectprocess.cmp.UserValue User )
   {
	  this.User = User;
	  UserHasBeenSet = true;
   }

   public String toString()
   {
	  StringBuffer str = new StringBuffer("{");

	  str.append("opmModelID=" + getOpmModelID() + " " + "userID=" + getUserID() + " " + "joinTime=" + getJoinTime() + " " + "accessControl=" + getAccessControl());
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
	  boolean ret = true;
	  ret = ret && opmModelIDHasBeenSet;
	  ret = ret && userIDHasBeenSet;
	  return ret;
   }

   public boolean equals(Object other)
   {
      if (this == other)
         return true;
	  if ( ! hasIdentity() ) return false;
	  if (other instanceof Enhanced2OpmModelPermissionsValue)
	  {
		 Enhanced2OpmModelPermissionsValue that = (Enhanced2OpmModelPermissionsValue) other;
		 if ( ! that.hasIdentity() ) return false;
		 boolean lEquals = true;
		 if( this.opmModelID == null )
		 {
			lEquals = lEquals && ( that.opmModelID == null );
		 }
		 else
		 {
			lEquals = lEquals && this.opmModelID.equals( that.opmModelID );
		 }
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
	  if (other instanceof Enhanced2OpmModelPermissionsValue)
	  {
		 Enhanced2OpmModelPermissionsValue that = (Enhanced2OpmModelPermissionsValue) other;
		 boolean lEquals = true;
		 if( this.joinTime == null )
		 {
			lEquals = lEquals && ( that.joinTime == null );
		 }
		 else
		 {
			lEquals = lEquals && this.joinTime.equals( that.joinTime );
		 }
		 if( this.accessControl == null )
		 {
			lEquals = lEquals && ( that.accessControl == null );
		 }
		 else
		 {
			lEquals = lEquals && this.accessControl.equals( that.accessControl );
		 }
		 if( this.User == null )
		 {
			lEquals = lEquals && ( that.User == null );
		 }
		 else
		 {
			lEquals = lEquals && this.User.equals( that.User );
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
      result = 37*result + ((this.opmModelID != null) ? this.opmModelID.hashCode() : 0);

      result = 37*result + ((this.userID != null) ? this.userID.hashCode() : 0);

      result = 37*result + ((this.joinTime != null) ? this.joinTime.hashCode() : 0);

      result = 37*result + ((this.accessControl != null) ? this.accessControl.hashCode() : 0);

	  result = 37*result + ((this.User != null) ? this.User.hashCode() : 0);
	  return result;
   }

}