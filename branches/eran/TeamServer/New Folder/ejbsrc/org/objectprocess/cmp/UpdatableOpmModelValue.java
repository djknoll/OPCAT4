/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Value object for OpmModel.
 *
 * @lomboz generated
 */
public class UpdatableOpmModelValue
   extends java.lang.Object
   implements java.io.Serializable 
{
   private java.lang.String opmModelID;
   private boolean opmModelIDHasBeenSet = false;
   private java.lang.String opmModelName;
   private boolean opmModelNameHasBeenSet = false;
   private java.lang.Boolean enabled;
   private boolean enabledHasBeenSet = false;
   private java.lang.String description;
   private boolean descriptionHasBeenSet = false;

   private java.lang.String pk;

   public UpdatableOpmModelValue()
   {
   }

   public UpdatableOpmModelValue( java.lang.String opmModelID,java.lang.String opmModelName,java.lang.Boolean enabled,java.lang.String description )
   {
	  this.opmModelID = opmModelID;
	  opmModelIDHasBeenSet = true;
	  this.opmModelName = opmModelName;
	  opmModelNameHasBeenSet = true;
	  this.enabled = enabled;
	  enabledHasBeenSet = true;
	  this.description = description;
	  descriptionHasBeenSet = true;
	  pk = this.getOpmModelID();
   }

   //TODO Cloneable is better than this !
   public UpdatableOpmModelValue( UpdatableOpmModelValue otherValue )
   {
	  this.opmModelID = otherValue.opmModelID;
	  opmModelIDHasBeenSet = true;
	  this.opmModelName = otherValue.opmModelName;
	  opmModelNameHasBeenSet = true;
	  this.enabled = otherValue.enabled;
	  enabledHasBeenSet = true;
	  this.description = otherValue.description;
	  descriptionHasBeenSet = true;

	  pk = this.getOpmModelID();
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
	  setOpmModelID( pk );
   }

   public java.lang.String getOpmModelID()
   {
	  return this.opmModelID;
   }

   public void setOpmModelID( java.lang.String opmModelID )
   {
	  this.opmModelID = opmModelID;
	  opmModelIDHasBeenSet = true;

		  pk = opmModelID;
   }

   public boolean opmModelIDHasBeenSet(){
	  return opmModelIDHasBeenSet;
   }
   public java.lang.String getOpmModelName()
   {
	  return this.opmModelName;
   }

   public void setOpmModelName( java.lang.String opmModelName )
   {
	  this.opmModelName = opmModelName;
	  opmModelNameHasBeenSet = true;

   }

   public boolean opmModelNameHasBeenSet(){
	  return opmModelNameHasBeenSet;
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

   public String toString()
   {
	  StringBuffer str = new StringBuffer("{");

	  str.append("opmModelID=" + getOpmModelID() + " " + "opmModelName=" + getOpmModelName() + " " + "enabled=" + getEnabled() + " " + "description=" + getDescription());
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
	  return opmModelIDHasBeenSet;
   }

   public boolean equals(Object other)
   {
      if (this == other)
         return true;
	  if ( ! hasIdentity() ) return false;
	  if (other instanceof UpdatableOpmModelValue)
	  {
		 UpdatableOpmModelValue that = (UpdatableOpmModelValue) other;
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
	  if (other instanceof UpdatableOpmModelValue)
	  {
		 UpdatableOpmModelValue that = (UpdatableOpmModelValue) other;
		 boolean lEquals = true;
		 if( this.opmModelName == null )
		 {
			lEquals = lEquals && ( that.opmModelName == null );
		 }
		 else
		 {
			lEquals = lEquals && this.opmModelName.equals( that.opmModelName );
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

      result = 37*result + ((this.opmModelName != null) ? this.opmModelName.hashCode() : 0);

      result = 37*result + ((this.enabled != null) ? this.enabled.hashCode() : 0);

      result = 37*result + ((this.description != null) ? this.description.hashCode() : 0);

	  return result;
   }

}
