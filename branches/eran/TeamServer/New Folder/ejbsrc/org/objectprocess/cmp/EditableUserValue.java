/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Value object for User.
 *
 * @lomboz generated
 */
public class EditableUserValue
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
   private java.lang.String description;
   private boolean descriptionHasBeenSet = false;
   private java.lang.Boolean administrator;
   private boolean administratorHasBeenSet = false;

   private java.lang.String pk;

   public EditableUserValue()
   {
   }

   public EditableUserValue( java.lang.String userID,java.lang.String loginName,java.lang.String firstName,java.lang.String lastName,java.lang.String password,java.lang.String email,java.lang.String description,java.lang.Boolean administrator )
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
	  this.description = description;
	  descriptionHasBeenSet = true;
	  this.administrator = administrator;
	  administratorHasBeenSet = true;
	  pk = this.getUserID();
   }

   //TODO Cloneable is better than this !
   public EditableUserValue( EditableUserValue otherValue )
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
	  this.description = otherValue.description;
	  descriptionHasBeenSet = true;
	  this.administrator = otherValue.administrator;
	  administratorHasBeenSet = true;

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

   public String toString()
   {
	  StringBuffer str = new StringBuffer("{");

	  str.append("userID=" + getUserID() + " " + "loginName=" + getLoginName() + " " + "firstName=" + getFirstName() + " " + "lastName=" + getLastName() + " " + "password=" + getPassword() + " " + "email=" + getEmail() + " " + "description=" + getDescription() + " " + "administrator=" + getAdministrator());
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
	  if (other instanceof EditableUserValue)
	  {
		 EditableUserValue that = (EditableUserValue) other;
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
	  if (other instanceof EditableUserValue)
	  {
		 EditableUserValue that = (EditableUserValue) other;
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

      result = 37*result + ((this.description != null) ? this.description.hashCode() : 0);

      result = 37*result + ((this.administrator != null) ? this.administrator.hashCode() : 0);

	  return result;
   }

}
