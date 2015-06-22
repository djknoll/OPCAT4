/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Value object for Revision.
 *
 * @lomboz generated
 */
public class MetaRevisionValue
   extends java.lang.Object
   implements java.io.Serializable 
{
   private java.lang.String revisionID;
   private boolean revisionIDHasBeenSet = false;
   private java.lang.String opmModelID;
   private boolean opmModelIDHasBeenSet = false;
   private java.lang.Boolean enabled;
   private boolean enabledHasBeenSet = false;
   private java.lang.Integer majorRevision;
   private boolean majorRevisionHasBeenSet = false;
   private java.lang.Integer minorRevision;
   private boolean minorRevisionHasBeenSet = false;
   private java.lang.Integer build;
   private boolean buildHasBeenSet = false;
   private java.util.Date creationTime;
   private boolean creationTimeHasBeenSet = false;
   private java.lang.String comitterID;
   private boolean comitterIDHasBeenSet = false;
   private java.lang.String description;
   private boolean descriptionHasBeenSet = false;

   private java.lang.String pk;

   public MetaRevisionValue()
   {
   }

   public MetaRevisionValue( java.lang.String revisionID,java.lang.String opmModelID,java.lang.Boolean enabled,java.lang.Integer majorRevision,java.lang.Integer minorRevision,java.lang.Integer build,java.util.Date creationTime,java.lang.String comitterID,java.lang.String description )
   {
	  this.revisionID = revisionID;
	  revisionIDHasBeenSet = true;
	  this.opmModelID = opmModelID;
	  opmModelIDHasBeenSet = true;
	  this.enabled = enabled;
	  enabledHasBeenSet = true;
	  this.majorRevision = majorRevision;
	  majorRevisionHasBeenSet = true;
	  this.minorRevision = minorRevision;
	  minorRevisionHasBeenSet = true;
	  this.build = build;
	  buildHasBeenSet = true;
	  this.creationTime = creationTime;
	  creationTimeHasBeenSet = true;
	  this.comitterID = comitterID;
	  comitterIDHasBeenSet = true;
	  this.description = description;
	  descriptionHasBeenSet = true;
	  pk = this.getRevisionID();
   }

   //TODO Cloneable is better than this !
   public MetaRevisionValue( MetaRevisionValue otherValue )
   {
	  this.revisionID = otherValue.revisionID;
	  revisionIDHasBeenSet = true;
	  this.opmModelID = otherValue.opmModelID;
	  opmModelIDHasBeenSet = true;
	  this.enabled = otherValue.enabled;
	  enabledHasBeenSet = true;
	  this.majorRevision = otherValue.majorRevision;
	  majorRevisionHasBeenSet = true;
	  this.minorRevision = otherValue.minorRevision;
	  minorRevisionHasBeenSet = true;
	  this.build = otherValue.build;
	  buildHasBeenSet = true;
	  this.creationTime = otherValue.creationTime;
	  creationTimeHasBeenSet = true;
	  this.comitterID = otherValue.comitterID;
	  comitterIDHasBeenSet = true;
	  this.description = otherValue.description;
	  descriptionHasBeenSet = true;

	  pk = this.getRevisionID();
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
	  setRevisionID( pk );
   }

   public java.lang.String getRevisionID()
   {
	  return this.revisionID;
   }

   public void setRevisionID( java.lang.String revisionID )
   {
	  this.revisionID = revisionID;
	  revisionIDHasBeenSet = true;

		  pk = revisionID;
   }

   public boolean revisionIDHasBeenSet(){
	  return revisionIDHasBeenSet;
   }
   public java.lang.String getOpmModelID()
   {
	  return this.opmModelID;
   }

   public void setOpmModelID( java.lang.String opmModelID )
   {
	  this.opmModelID = opmModelID;
	  opmModelIDHasBeenSet = true;

   }

   public boolean opmModelIDHasBeenSet(){
	  return opmModelIDHasBeenSet;
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
   public java.lang.Integer getMajorRevision()
   {
	  return this.majorRevision;
   }

   public void setMajorRevision( java.lang.Integer majorRevision )
   {
	  this.majorRevision = majorRevision;
	  majorRevisionHasBeenSet = true;

   }

   public boolean majorRevisionHasBeenSet(){
	  return majorRevisionHasBeenSet;
   }
   public java.lang.Integer getMinorRevision()
   {
	  return this.minorRevision;
   }

   public void setMinorRevision( java.lang.Integer minorRevision )
   {
	  this.minorRevision = minorRevision;
	  minorRevisionHasBeenSet = true;

   }

   public boolean minorRevisionHasBeenSet(){
	  return minorRevisionHasBeenSet;
   }
   public java.lang.Integer getBuild()
   {
	  return this.build;
   }

   public void setBuild( java.lang.Integer build )
   {
	  this.build = build;
	  buildHasBeenSet = true;

   }

   public boolean buildHasBeenSet(){
	  return buildHasBeenSet;
   }
   public java.util.Date getCreationTime()
   {
	  return this.creationTime;
   }

   public void setCreationTime( java.util.Date creationTime )
   {
	  this.creationTime = creationTime;
	  creationTimeHasBeenSet = true;

   }

   public boolean creationTimeHasBeenSet(){
	  return creationTimeHasBeenSet;
   }
   public java.lang.String getComitterID()
   {
	  return this.comitterID;
   }

   public void setComitterID( java.lang.String comitterID )
   {
	  this.comitterID = comitterID;
	  comitterIDHasBeenSet = true;

   }

   public boolean comitterIDHasBeenSet(){
	  return comitterIDHasBeenSet;
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

	  str.append("revisionID=" + getRevisionID() + " " + "opmModelID=" + getOpmModelID() + " " + "enabled=" + getEnabled() + " " + "majorRevision=" + getMajorRevision() + " " + "minorRevision=" + getMinorRevision() + " " + "build=" + getBuild() + " " + "creationTime=" + getCreationTime() + " " + "comitterID=" + getComitterID() + " " + "description=" + getDescription());
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
	  return revisionIDHasBeenSet;
   }

   public boolean equals(Object other)
   {
      if (this == other)
         return true;
	  if ( ! hasIdentity() ) return false;
	  if (other instanceof MetaRevisionValue)
	  {
		 MetaRevisionValue that = (MetaRevisionValue) other;
		 if ( ! that.hasIdentity() ) return false;
		 boolean lEquals = true;
		 if( this.revisionID == null )
		 {
			lEquals = lEquals && ( that.revisionID == null );
		 }
		 else
		 {
			lEquals = lEquals && this.revisionID.equals( that.revisionID );
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
	  if (other instanceof MetaRevisionValue)
	  {
		 MetaRevisionValue that = (MetaRevisionValue) other;
		 boolean lEquals = true;
		 if( this.opmModelID == null )
		 {
			lEquals = lEquals && ( that.opmModelID == null );
		 }
		 else
		 {
			lEquals = lEquals && this.opmModelID.equals( that.opmModelID );
		 }
		 if( this.enabled == null )
		 {
			lEquals = lEquals && ( that.enabled == null );
		 }
		 else
		 {
			lEquals = lEquals && this.enabled.equals( that.enabled );
		 }
		 if( this.majorRevision == null )
		 {
			lEquals = lEquals && ( that.majorRevision == null );
		 }
		 else
		 {
			lEquals = lEquals && this.majorRevision.equals( that.majorRevision );
		 }
		 if( this.minorRevision == null )
		 {
			lEquals = lEquals && ( that.minorRevision == null );
		 }
		 else
		 {
			lEquals = lEquals && this.minorRevision.equals( that.minorRevision );
		 }
		 if( this.build == null )
		 {
			lEquals = lEquals && ( that.build == null );
		 }
		 else
		 {
			lEquals = lEquals && this.build.equals( that.build );
		 }
		 if( this.creationTime == null )
		 {
			lEquals = lEquals && ( that.creationTime == null );
		 }
		 else
		 {
			lEquals = lEquals && this.creationTime.equals( that.creationTime );
		 }
		 if( this.comitterID == null )
		 {
			lEquals = lEquals && ( that.comitterID == null );
		 }
		 else
		 {
			lEquals = lEquals && this.comitterID.equals( that.comitterID );
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
      result = 37*result + ((this.revisionID != null) ? this.revisionID.hashCode() : 0);

      result = 37*result + ((this.opmModelID != null) ? this.opmModelID.hashCode() : 0);

      result = 37*result + ((this.enabled != null) ? this.enabled.hashCode() : 0);

      result = 37*result + ((this.majorRevision != null) ? this.majorRevision.hashCode() : 0);

      result = 37*result + ((this.minorRevision != null) ? this.minorRevision.hashCode() : 0);

      result = 37*result + ((this.build != null) ? this.build.hashCode() : 0);

      result = 37*result + ((this.creationTime != null) ? this.creationTime.hashCode() : 0);

      result = 37*result + ((this.comitterID != null) ? this.comitterID.hashCode() : 0);

      result = 37*result + ((this.description != null) ? this.description.hashCode() : 0);

	  return result;
   }

}
