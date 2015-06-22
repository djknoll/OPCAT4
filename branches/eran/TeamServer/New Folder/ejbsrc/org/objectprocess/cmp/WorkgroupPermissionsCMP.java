/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * CMP layer for WorkgroupPermissions.
 * @lomboz generated
 */
public abstract class WorkgroupPermissionsCMP
   extends org.objectprocess.cmp.WorkgroupPermissionsBean
   implements javax.ejb.EntityBean
{

   /**
    * Generated ejbPostCreate for corresponding ejbCreate method.
    *
    * @see #ejbCreate(org.objectprocess.cmp.EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue)
    */
   public void ejbPostCreate(org.objectprocess.cmp.EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue)
   {
   }

   public void ejbLoad() 
   {
   }

   public void ejbStore() 
   {
   }

   public void ejbActivate() 
   {
   }

   public void ejbPassivate() 
   {

      EnhancedWorkgroupPermissionsValue = null;
      Enhanced2WorkgroupPermissionsValue = null;
      WorkgroupPermissionsValue = null;
      EditableWorkgroupPermissionsValue = null;
   }

   public void setEntityContext(javax.ejb.EntityContext ctx) 
   {
   }

   public void unsetEntityContext() 
   {
   }

   public void ejbRemove() throws javax.ejb.RemoveException
   {

   }

 /* Value Objects BEGIN */

   private org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue EnhancedWorkgroupPermissionsValue = null;

   public org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue getEnhancedWorkgroupPermissionsValue()
   {
      EnhancedWorkgroupPermissionsValue = new org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue();
      try
         {
            EnhancedWorkgroupPermissionsValue.setWorkgroupID( getWorkgroupID() );
            EnhancedWorkgroupPermissionsValue.setUserID( getUserID() );
            EnhancedWorkgroupPermissionsValue.setJoinTime( getJoinTime() );
            EnhancedWorkgroupPermissionsValue.setAccessControl( getAccessControl() );
            if ( getWorkgroup() != null )
               EnhancedWorkgroupPermissionsValue.setWorkgroup( getWorkgroup().getWorkgroupValue() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return EnhancedWorkgroupPermissionsValue;
   }
   private org.objectprocess.cmp.Enhanced2WorkgroupPermissionsValue Enhanced2WorkgroupPermissionsValue = null;

   public org.objectprocess.cmp.Enhanced2WorkgroupPermissionsValue getEnhanced2WorkgroupPermissionsValue()
   {
      Enhanced2WorkgroupPermissionsValue = new org.objectprocess.cmp.Enhanced2WorkgroupPermissionsValue();
      try
         {
            Enhanced2WorkgroupPermissionsValue.setWorkgroupID( getWorkgroupID() );
            Enhanced2WorkgroupPermissionsValue.setUserID( getUserID() );
            Enhanced2WorkgroupPermissionsValue.setJoinTime( getJoinTime() );
            Enhanced2WorkgroupPermissionsValue.setAccessControl( getAccessControl() );
            if ( getUser() != null )
               Enhanced2WorkgroupPermissionsValue.setUser( getUser().getUserValue() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return Enhanced2WorkgroupPermissionsValue;
   }
   private org.objectprocess.cmp.WorkgroupPermissionsValue WorkgroupPermissionsValue = null;

   public org.objectprocess.cmp.WorkgroupPermissionsValue getWorkgroupPermissionsValue()
   {
      WorkgroupPermissionsValue = new org.objectprocess.cmp.WorkgroupPermissionsValue();
      try
         {
            WorkgroupPermissionsValue.setWorkgroupID( getWorkgroupID() );
            WorkgroupPermissionsValue.setUserID( getUserID() );
            WorkgroupPermissionsValue.setJoinTime( getJoinTime() );
            WorkgroupPermissionsValue.setAccessControl( getAccessControl() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return WorkgroupPermissionsValue;
   }
   private org.objectprocess.cmp.EditableWorkgroupPermissionsValue EditableWorkgroupPermissionsValue = null;

   public org.objectprocess.cmp.EditableWorkgroupPermissionsValue getEditableWorkgroupPermissionsValue()
   {
      EditableWorkgroupPermissionsValue = new org.objectprocess.cmp.EditableWorkgroupPermissionsValue();
      try
         {
            EditableWorkgroupPermissionsValue.setWorkgroupID( getWorkgroupID() );
            EditableWorkgroupPermissionsValue.setUserID( getUserID() );
            EditableWorkgroupPermissionsValue.setAccessControl( getAccessControl() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return EditableWorkgroupPermissionsValue;
   }

/* Value Objects END */

   public abstract java.lang.String getWorkgroupID() ;

   public abstract void setWorkgroupID( java.lang.String workgroupID ) ;

   public abstract java.lang.String getUserID() ;

   public abstract void setUserID( java.lang.String userID ) ;

   public abstract java.util.Date getJoinTime() ;

   public abstract void setJoinTime( java.util.Date joinTime ) ;

   public abstract java.lang.Integer getAccessControl() ;

   public abstract void setAccessControl( java.lang.Integer accessControl ) ;

}