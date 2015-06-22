/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Value object for Workgroup.
 *
 * @lomboz generated
 */
public class UpdatableWorkgroupValue
   extends java.lang.Object
   implements java.io.Serializable 
{
   private java.lang.String workgroupID;
   private boolean workgroupIDHasBeenSet = false;
   private java.lang.String workgroupName;
   private boolean workgroupNameHasBeenSet = false;
   private java.lang.Boolean enabled;
   private boolean enabledHasBeenSet = false;
   private java.lang.String description;
   private boolean descriptionHasBeenSet = false;

   private java.lang.String pk;

   public UpdatableWorkgroupValue()
   {
   }

   public UpdatableWorkgroupValue( java.lang.String workgroupID,java.lang.String workgroupName,java.lang.Boolean enabled,java.lang.String description )
   {
	  this.workgroupID = workgroupID;
	  workgroupIDHasBeenSet = true;
	  this.workgroupName = workgroupName;
	  workgroupNameHasBeenSet = true;
	  this.enabled = enabled;
	  enabledHasBeenSet = true;
	  this.description = description;
	  descriptionHasBeenSet = true;
	  pk = this.getWorkgroupID();
   }

   //TODO Cloneable is better than this !
   public UpdatableWorkgroupValue( UpdatableWorkgroupValue otherValue )
   {
	  this.workgroupID = otherValue.workgroupID;
	  workgroupIDHasBeenSet = true;
	  this.workgroupName = otherValue.workgroupName;
	  workgroupNameHasBeenSet = true;
	  this.enabled = otherValue.enabled;
	  enabledHasBeenSet = true;
	  this.description = otherValue.description;
	  descriptionHasBeenSet = true;

	  pk = this.getWorkgroupID();
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
	  setWorkgroupID( pk );
   }

   public java.lang.String getWorkgroupID()
   {
	  return this.workgroupID;
   }

   public void setWorkgroupID( java.lang.String workgroupID )
   {
	  this.workgroupID = workgroupID;
	  workgroupIDHasBeenSet = true;

		  pk = workgroupID;
   }

   public boolean workgroupIDHasBeenSet(){
	  return workgroupIDHasBeenSet;
   }
   public java.lang.String getWorkgroupName()
   {
	  return this.workgroupName;
   }

   public void setWorkgroupName( java.lang.String workgroupName )
   {
	  this.workgroupName = workgroupName;
	  workgroupNameHasBeenSet = true;

   }

   public boolean workgroupNameHasBeenSet(){
	  return workgroupNameHasBeenSet;
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

	  str.append("workgroupID=" + getWorkgroupID() + " " + "workgroupName=" + getWorkgroupName() + " " + "enabled=" + getEnabled() + " " + "description=" + getDescription());
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
	  return workgroupIDHasBeenSet;
   }

   public boolean equals(Object other)
   {
      if (this == other)
         return true;
	  if ( ! hasIdentity() ) return false;
	  if (other instanceof UpdatableWorkgroupValue)
	  {
		 UpdatableWorkgroupValue that = (UpdatableWorkgroupValue) other;
		 if ( ! that.hasIdentity() ) return false;
		 boolean lEquals = true;
		 if( this.workgroupID == null )
		 {
			lEquals = lEquals && ( that.workgroupID == null );
		 }
		 else
		 {
			lEquals = lEquals && this.workgroupID.equals( that.workgroupID );
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
	  if (other instanceof UpdatableWorkgroupValue)
	  {
		 UpdatableWorkgroupValue that = (UpdatableWorkgroupValue) other;
		 boolean lEquals = true;
		 if( this.workgroupName == null )
		 {
			lEquals = lEquals && ( that.workgroupName == null );
		 }
		 else
		 {
			lEquals = lEquals && this.workgroupName.equals( that.workgroupName );
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
      result = 37*result + ((this.workgroupID != null) ? this.workgroupID.hashCode() : 0);

      result = 37*result + ((this.workgroupName != null) ? this.workgroupName.hashCode() : 0);

      result = 37*result + ((this.enabled != null) ? this.enabled.hashCode() : 0);

      result = 37*result + ((this.description != null) ? this.description.hashCode() : 0);

	  return result;
   }

}
