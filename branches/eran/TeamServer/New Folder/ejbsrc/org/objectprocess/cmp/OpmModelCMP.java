/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * CMP layer for OpmModel.
 * @lomboz generated
 */
public abstract class OpmModelCMP
   extends org.objectprocess.cmp.OpmModelBean
   implements javax.ejb.EntityBean
{

   /**
    * Generated ejbPostCreate for corresponding ejbCreate method.
    *
    * @see #ejbCreate(org.objectprocess.cmp.EditableOpmModelValue editableOpmModelValue)
    */
   public void ejbPostCreate(org.objectprocess.cmp.EditableOpmModelValue editableOpmModelValue)
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

      EnhancedOpmModelValue = null;
      OpmModelValue = null;
      EditableOpmModelValue = null;
      UpdatableOpmModelValue = null;
      OpmModelRevisionsValue = null;
   }

   public void setEntityContext(javax.ejb.EntityContext ctx) 
   {
      super.setEntityContext(ctx);
   }

   public void unsetEntityContext() 
   {
      super.unsetEntityContext();
   }

   public void ejbRemove() throws javax.ejb.RemoveException
   {

   }

 /* Value Objects BEGIN */

   public void addMetaRevisionValue(org.objectprocess.cmp.MetaRevisionValue added)
   throws javax.ejb.FinderException
   {
	  try
	  {
		  java.lang.String pk = added.getRevisionID();

		 org.objectprocess.cmp.RevisionLocalHome home = org.objectprocess.cmp.RevisionUtil.getLocalHome();

		 org.objectprocess.cmp.RevisionLocal relation = home.findByPrimaryKey(pk);
		getOpmModelRevisions().add(relation);
	  }
	  catch (Exception e){
		 if (e instanceof javax.ejb.FinderException)
			throw (javax.ejb.FinderException)e;
		 else
			throw new javax.ejb.EJBException(e);
	  }
   }

   public void removeMetaRevisionValue(org.objectprocess.cmp.MetaRevisionValue removed)
   throws javax.ejb.RemoveException
   {
	  try
	  {
		  java.lang.String pk = removed.getRevisionID();

		 org.objectprocess.cmp.RevisionLocalHome home = org.objectprocess.cmp.RevisionUtil.getLocalHome();

		org.objectprocess.cmp.RevisionLocal relation = home.findByPrimaryKey(pk);
		getOpmModelRevisions().remove(relation);
	  }
	  catch (Exception e){
		 if (e instanceof javax.ejb.RemoveException)
			throw (javax.ejb.RemoveException)e;
		 else
			throw new javax.ejb.EJBException(e);
	  }
   }

   public void addEnhancedOpmModelPermissionsValue(org.objectprocess.cmp.Enhanced2OpmModelPermissionsValue added)
   throws javax.ejb.FinderException
   {
	  try
	  {
		  org.objectprocess.cmp.OpmModelPermissionsPK pk = new org.objectprocess.cmp.OpmModelPermissionsPK(added.getOpmModelID(),added.getUserID());

		 org.objectprocess.cmp.OpmModelPermissionsLocalHome home = org.objectprocess.cmp.OpmModelPermissionsUtil.getLocalHome();

		 org.objectprocess.cmp.OpmModelPermissionsLocal relation = home.findByPrimaryKey(pk);
		getOpmModelPermissions().add(relation);
	  }
	  catch (Exception e){
		 if (e instanceof javax.ejb.FinderException)
			throw (javax.ejb.FinderException)e;
		 else
			throw new javax.ejb.EJBException(e);
	  }
   }

   public void removeEnhancedOpmModelPermissionsValue(org.objectprocess.cmp.Enhanced2OpmModelPermissionsValue removed)
   throws javax.ejb.RemoveException
   {
	  try
	  {
		  org.objectprocess.cmp.OpmModelPermissionsPK pk = new org.objectprocess.cmp.OpmModelPermissionsPK(removed.getOpmModelID(),removed.getUserID());

		 org.objectprocess.cmp.OpmModelPermissionsLocalHome home = org.objectprocess.cmp.OpmModelPermissionsUtil.getLocalHome();

		org.objectprocess.cmp.OpmModelPermissionsLocal relation = home.findByPrimaryKey(pk);
		getOpmModelPermissions().remove(relation);
	  }
	  catch (Exception e){
		 if (e instanceof javax.ejb.RemoveException)
			throw (javax.ejb.RemoveException)e;
		 else
			throw new javax.ejb.EJBException(e);
	  }
   }

   public void addCollaborativeSessionValue(org.objectprocess.cmp.CollaborativeSessionValue added)
   throws javax.ejb.FinderException
   {
	  try
	  {
		  java.lang.String pk = added.getCollaborativeSessionID();

		 org.objectprocess.cmp.CollaborativeSessionLocalHome home = org.objectprocess.cmp.CollaborativeSessionUtil.getLocalHome();

		 org.objectprocess.cmp.CollaborativeSessionLocal relation = home.findByPrimaryKey(pk);
		getCollaborativeSessions().add(relation);
	  }
	  catch (Exception e){
		 if (e instanceof javax.ejb.FinderException)
			throw (javax.ejb.FinderException)e;
		 else
			throw new javax.ejb.EJBException(e);
	  }
   }

   public void removeCollaborativeSessionValue(org.objectprocess.cmp.CollaborativeSessionValue removed)
   throws javax.ejb.RemoveException
   {
	  try
	  {
		  java.lang.String pk = removed.getCollaborativeSessionID();

		 org.objectprocess.cmp.CollaborativeSessionLocalHome home = org.objectprocess.cmp.CollaborativeSessionUtil.getLocalHome();

		org.objectprocess.cmp.CollaborativeSessionLocal relation = home.findByPrimaryKey(pk);
		getCollaborativeSessions().remove(relation);
	  }
	  catch (Exception e){
		 if (e instanceof javax.ejb.RemoveException)
			throw (javax.ejb.RemoveException)e;
		 else
			throw new javax.ejb.EJBException(e);
	  }
   }

   private org.objectprocess.cmp.EnhancedOpmModelValue EnhancedOpmModelValue = null;

   public org.objectprocess.cmp.EnhancedOpmModelValue getEnhancedOpmModelValue()
   {
      EnhancedOpmModelValue = new org.objectprocess.cmp.EnhancedOpmModelValue();
      try
         {
            EnhancedOpmModelValue.setOpmModelID( getOpmModelID() );
            EnhancedOpmModelValue.setOpmModelName( getOpmModelName() );
            EnhancedOpmModelValue.setEnabled( getEnabled() );
            EnhancedOpmModelValue.setCreationTime( getCreationTime() );
            EnhancedOpmModelValue.setDescription( getDescription() );
            EnhancedOpmModelValue.setWorkgroupID( getWorkgroupID() );
            EnhancedOpmModelValue.setTotalCollaborativeTime( getTotalCollaborativeTime() );
            EnhancedOpmModelValue.clearOpmModelsPermissions();
            java.util.Iterator iOpmModelsPermission = getOpmModelPermissions().iterator();
            while (iOpmModelsPermission.hasNext()){
                EnhancedOpmModelValue.addOpmModelsPermission( ((org.objectprocess.cmp.OpmModelPermissionsLocal)iOpmModelsPermission.next()).getEnhanced2OpmModelPermissionsValue() );
            }
            EnhancedOpmModelValue.cleanOpmModelsPermission();

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return EnhancedOpmModelValue;
   }
   private org.objectprocess.cmp.OpmModelValue OpmModelValue = null;

   public org.objectprocess.cmp.OpmModelValue getOpmModelValue()
   {
      OpmModelValue = new org.objectprocess.cmp.OpmModelValue();
      try
         {
            OpmModelValue.setOpmModelID( getOpmModelID() );
            OpmModelValue.setOpmModelName( getOpmModelName() );
            OpmModelValue.setEnabled( getEnabled() );
            OpmModelValue.setCreationTime( getCreationTime() );
            OpmModelValue.setDescription( getDescription() );
            OpmModelValue.setWorkgroupID( getWorkgroupID() );
            OpmModelValue.setTotalCollaborativeTime( getTotalCollaborativeTime() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return OpmModelValue;
   }
   private org.objectprocess.cmp.EditableOpmModelValue EditableOpmModelValue = null;

   public org.objectprocess.cmp.EditableOpmModelValue getEditableOpmModelValue()
   {
      EditableOpmModelValue = new org.objectprocess.cmp.EditableOpmModelValue();
      try
         {
            EditableOpmModelValue.setOpmModelID( getOpmModelID() );
            EditableOpmModelValue.setOpmModelName( getOpmModelName() );
            EditableOpmModelValue.setDescription( getDescription() );
            EditableOpmModelValue.setWorkgroupID( getWorkgroupID() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return EditableOpmModelValue;
   }
   private org.objectprocess.cmp.UpdatableOpmModelValue UpdatableOpmModelValue = null;

   public org.objectprocess.cmp.UpdatableOpmModelValue getUpdatableOpmModelValue()
   {
      UpdatableOpmModelValue = new org.objectprocess.cmp.UpdatableOpmModelValue();
      try
         {
            UpdatableOpmModelValue.setOpmModelID( getOpmModelID() );
            UpdatableOpmModelValue.setOpmModelName( getOpmModelName() );
            UpdatableOpmModelValue.setEnabled( getEnabled() );
            UpdatableOpmModelValue.setDescription( getDescription() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return UpdatableOpmModelValue;
   }
   private org.objectprocess.cmp.OpmModelRevisionsValue OpmModelRevisionsValue = null;

   public org.objectprocess.cmp.OpmModelRevisionsValue getOpmModelRevisionsValue()
   {
      OpmModelRevisionsValue = new org.objectprocess.cmp.OpmModelRevisionsValue();
      try
         {
            OpmModelRevisionsValue.setOpmModelID( getOpmModelID() );
            OpmModelRevisionsValue.setOpmModelName( getOpmModelName() );
            OpmModelRevisionsValue.setEnabled( getEnabled() );
            OpmModelRevisionsValue.setCreationTime( getCreationTime() );
            OpmModelRevisionsValue.setDescription( getDescription() );
            OpmModelRevisionsValue.setWorkgroupID( getWorkgroupID() );
            OpmModelRevisionsValue.setTotalCollaborativeTime( getTotalCollaborativeTime() );
            OpmModelRevisionsValue.clearOpmModelRevisions();
            java.util.Iterator iOpmModelRevision = getOpmModelRevisions().iterator();
            while (iOpmModelRevision.hasNext()){
                OpmModelRevisionsValue.addOpmModelRevision( ((org.objectprocess.cmp.RevisionLocal)iOpmModelRevision.next()).getMetaRevisionValue() );
            }
            OpmModelRevisionsValue.cleanOpmModelRevision();

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return OpmModelRevisionsValue;
   }

   public void setEditableOpmModelValue( org.objectprocess.cmp.EditableOpmModelValue valueHolder )
   {

	  try
	  {
		 setOpmModelName( valueHolder.getOpmModelName() );
		 setDescription( valueHolder.getDescription() );
		 setWorkgroupID( valueHolder.getWorkgroupID() );

	  }
	  catch (Exception e)
	  {
		 throw new javax.ejb.EJBException(e);
	  }
   }
   public void setUpdatableOpmModelValue( org.objectprocess.cmp.UpdatableOpmModelValue valueHolder )
   {

	  try
	  {
		 setOpmModelName( valueHolder.getOpmModelName() );
		 setEnabled( valueHolder.getEnabled() );
		 setDescription( valueHolder.getDescription() );

	  }
	  catch (Exception e)
	  {
		 throw new javax.ejb.EJBException(e);
	  }
   }

/* Value Objects END */

   public abstract java.lang.String getOpmModelID() ;

   public abstract void setOpmModelID( java.lang.String opmModelID ) ;

   public abstract java.lang.String getOpmModelName() ;

   public abstract void setOpmModelName( java.lang.String opmModelName ) ;

   public abstract java.lang.Boolean getEnabled() ;

   public abstract void setEnabled( java.lang.Boolean enabled ) ;

   public abstract java.util.Date getCreationTime() ;

   public abstract void setCreationTime( java.util.Date creationTime ) ;

   public abstract java.lang.String getDescription() ;

   public abstract void setDescription( java.lang.String description ) ;

   public abstract java.lang.String getWorkgroupID() ;

   public abstract void setWorkgroupID( java.lang.String workgroupID ) ;

   public abstract java.lang.Integer getTotalCollaborativeTime() ;

   public abstract void setTotalCollaborativeTime( java.lang.Integer totalCollaborativeTime ) ;

}