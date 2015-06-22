/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * CMP layer for CollaborativeSession.
 * @lomboz generated
 */
public abstract class CollaborativeSessionCMP
   extends org.objectprocess.cmp.CollaborativeSessionBean
   implements javax.ejb.EntityBean
{

   /**
    * Generated ejbPostCreate for corresponding ejbCreate method.
    *
    * @see #ejbCreate(org.objectprocess.cmp.EditableCollaborativeSessionValue editableCollaborativeSessionValue)
    */
   public void ejbPostCreate(org.objectprocess.cmp.EditableCollaborativeSessionValue editableCollaborativeSessionValue)
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

      EnhancedCollaborativeSessionValue = null;
      CollaborativeSessionValue = null;
      EditableCollaborativeSessionValue = null;
      UpdatableCollaborativeSessionValue = null;
      CollaborativeSessionFileValue = null;
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

   public void addEnhancedCollaborativeSessionPermissionsValue(org.objectprocess.cmp.Enhanced2CollaborativeSessionPermissionsValue added)
   throws javax.ejb.FinderException
   {
	  try
	  {
		  org.objectprocess.cmp.CollaborativeSessionPermissionsPK pk = new org.objectprocess.cmp.CollaborativeSessionPermissionsPK(added.getCollaborativeSessionID(),added.getUserID());

		 org.objectprocess.cmp.CollaborativeSessionPermissionsLocalHome home = org.objectprocess.cmp.CollaborativeSessionPermissionsUtil.getLocalHome();

		 org.objectprocess.cmp.CollaborativeSessionPermissionsLocal relation = home.findByPrimaryKey(pk);
		getCollaborativeSessionPermissions().add(relation);
	  }
	  catch (Exception e){
		 if (e instanceof javax.ejb.FinderException)
			throw (javax.ejb.FinderException)e;
		 else
			throw new javax.ejb.EJBException(e);
	  }
   }

   public void removeEnhancedCollaborativeSessionPermissionsValue(org.objectprocess.cmp.Enhanced2CollaborativeSessionPermissionsValue removed)
   throws javax.ejb.RemoveException
   {
	  try
	  {
		  org.objectprocess.cmp.CollaborativeSessionPermissionsPK pk = new org.objectprocess.cmp.CollaborativeSessionPermissionsPK(removed.getCollaborativeSessionID(),removed.getUserID());

		 org.objectprocess.cmp.CollaborativeSessionPermissionsLocalHome home = org.objectprocess.cmp.CollaborativeSessionPermissionsUtil.getLocalHome();

		org.objectprocess.cmp.CollaborativeSessionPermissionsLocal relation = home.findByPrimaryKey(pk);
		getCollaborativeSessionPermissions().remove(relation);
	  }
	  catch (Exception e){
		 if (e instanceof javax.ejb.RemoveException)
			throw (javax.ejb.RemoveException)e;
		 else
			throw new javax.ejb.EJBException(e);
	  }
   }

   private org.objectprocess.cmp.EnhancedCollaborativeSessionValue EnhancedCollaborativeSessionValue = null;

   public org.objectprocess.cmp.EnhancedCollaborativeSessionValue getEnhancedCollaborativeSessionValue()
   {
      EnhancedCollaborativeSessionValue = new org.objectprocess.cmp.EnhancedCollaborativeSessionValue();
      try
         {
            EnhancedCollaborativeSessionValue.setCollaborativeSessionID( getCollaborativeSessionID() );
            EnhancedCollaborativeSessionValue.setCollaborativeSessionName( getCollaborativeSessionName() );
            EnhancedCollaborativeSessionValue.setEnabled( getEnabled() );
            EnhancedCollaborativeSessionValue.setTerminated( getTerminated() );
            EnhancedCollaborativeSessionValue.setCreationTime( getCreationTime() );
            EnhancedCollaborativeSessionValue.setDescription( getDescription() );
            EnhancedCollaborativeSessionValue.setLastUpdate( getLastUpdate() );
            EnhancedCollaborativeSessionValue.setOpmModelID( getOpmModelID() );
            EnhancedCollaborativeSessionValue.setRevisionID( getRevisionID() );
            EnhancedCollaborativeSessionValue.setTokenTimeout( getTokenTimeout() );
            EnhancedCollaborativeSessionValue.setUserTimeout( getUserTimeout() );
            EnhancedCollaborativeSessionValue.setTokenHolderID( getTokenHolderID() );
            EnhancedCollaborativeSessionValue.clearCollaborativeSessionsPermissions();
            java.util.Iterator iCollaborativeSessionsPermission = getCollaborativeSessionPermissions().iterator();
            while (iCollaborativeSessionsPermission.hasNext()){
                EnhancedCollaborativeSessionValue.addCollaborativeSessionsPermission( ((org.objectprocess.cmp.CollaborativeSessionPermissionsLocal)iCollaborativeSessionsPermission.next()).getEnhanced2CollaborativeSessionPermissionsValue() );
            }
            EnhancedCollaborativeSessionValue.cleanCollaborativeSessionsPermission();

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return EnhancedCollaborativeSessionValue;
   }
   private org.objectprocess.cmp.CollaborativeSessionValue CollaborativeSessionValue = null;

   public org.objectprocess.cmp.CollaborativeSessionValue getCollaborativeSessionValue()
   {
      CollaborativeSessionValue = new org.objectprocess.cmp.CollaborativeSessionValue();
      try
         {
            CollaborativeSessionValue.setCollaborativeSessionID( getCollaborativeSessionID() );
            CollaborativeSessionValue.setCollaborativeSessionName( getCollaborativeSessionName() );
            CollaborativeSessionValue.setEnabled( getEnabled() );
            CollaborativeSessionValue.setTerminated( getTerminated() );
            CollaborativeSessionValue.setCreationTime( getCreationTime() );
            CollaborativeSessionValue.setDescription( getDescription() );
            CollaborativeSessionValue.setLastUpdate( getLastUpdate() );
            CollaborativeSessionValue.setOpmModelID( getOpmModelID() );
            CollaborativeSessionValue.setRevisionID( getRevisionID() );
            CollaborativeSessionValue.setTokenTimeout( getTokenTimeout() );
            CollaborativeSessionValue.setUserTimeout( getUserTimeout() );
            CollaborativeSessionValue.setTokenHolderID( getTokenHolderID() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return CollaborativeSessionValue;
   }
   private org.objectprocess.cmp.EditableCollaborativeSessionValue EditableCollaborativeSessionValue = null;

   public org.objectprocess.cmp.EditableCollaborativeSessionValue getEditableCollaborativeSessionValue()
   {
      EditableCollaborativeSessionValue = new org.objectprocess.cmp.EditableCollaborativeSessionValue();
      try
         {
            EditableCollaborativeSessionValue.setCollaborativeSessionID( getCollaborativeSessionID() );
            EditableCollaborativeSessionValue.setCollaborativeSessionName( getCollaborativeSessionName() );
            EditableCollaborativeSessionValue.setDescription( getDescription() );
            EditableCollaborativeSessionValue.setOpmModelID( getOpmModelID() );
            EditableCollaborativeSessionValue.setRevisionID( getRevisionID() );
            EditableCollaborativeSessionValue.setTokenTimeout( getTokenTimeout() );
            EditableCollaborativeSessionValue.setUserTimeout( getUserTimeout() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return EditableCollaborativeSessionValue;
   }
   private org.objectprocess.cmp.UpdatableCollaborativeSessionValue UpdatableCollaborativeSessionValue = null;

   public org.objectprocess.cmp.UpdatableCollaborativeSessionValue getUpdatableCollaborativeSessionValue()
   {
      UpdatableCollaborativeSessionValue = new org.objectprocess.cmp.UpdatableCollaborativeSessionValue();
      try
         {
            UpdatableCollaborativeSessionValue.setCollaborativeSessionID( getCollaborativeSessionID() );
            UpdatableCollaborativeSessionValue.setCollaborativeSessionName( getCollaborativeSessionName() );
            UpdatableCollaborativeSessionValue.setEnabled( getEnabled() );
            UpdatableCollaborativeSessionValue.setDescription( getDescription() );
            UpdatableCollaborativeSessionValue.setTokenTimeout( getTokenTimeout() );
            UpdatableCollaborativeSessionValue.setUserTimeout( getUserTimeout() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return UpdatableCollaborativeSessionValue;
   }
   private org.objectprocess.cmp.CollaborativeSessionFileValue CollaborativeSessionFileValue = null;

   public org.objectprocess.cmp.CollaborativeSessionFileValue getCollaborativeSessionFileValue()
   {
      CollaborativeSessionFileValue = new org.objectprocess.cmp.CollaborativeSessionFileValue();
      try
         {
            CollaborativeSessionFileValue.setCollaborativeSessionID( getCollaborativeSessionID() );
            CollaborativeSessionFileValue.setOpmModelFile( getOpmModelFile() );

         }
         catch (Exception e)
         {
            throw new javax.ejb.EJBException(e);
         }

	  return CollaborativeSessionFileValue;
   }

   public void setEditableCollaborativeSessionValue( org.objectprocess.cmp.EditableCollaborativeSessionValue valueHolder )
   {

	  try
	  {
		 setCollaborativeSessionName( valueHolder.getCollaborativeSessionName() );
		 setDescription( valueHolder.getDescription() );
		 setOpmModelID( valueHolder.getOpmModelID() );
		 setRevisionID( valueHolder.getRevisionID() );
		 setTokenTimeout( valueHolder.getTokenTimeout() );
		 setUserTimeout( valueHolder.getUserTimeout() );

	  }
	  catch (Exception e)
	  {
		 throw new javax.ejb.EJBException(e);
	  }
   }
   public void setUpdatableCollaborativeSessionValue( org.objectprocess.cmp.UpdatableCollaborativeSessionValue valueHolder )
   {

	  try
	  {
		 setCollaborativeSessionName( valueHolder.getCollaborativeSessionName() );
		 setEnabled( valueHolder.getEnabled() );
		 setDescription( valueHolder.getDescription() );
		 setTokenTimeout( valueHolder.getTokenTimeout() );
		 setUserTimeout( valueHolder.getUserTimeout() );

	  }
	  catch (Exception e)
	  {
		 throw new javax.ejb.EJBException(e);
	  }
   }
   public void setCollaborativeSessionFileValue( org.objectprocess.cmp.CollaborativeSessionFileValue valueHolder )
   {

	  try
	  {
		 setOpmModelFile( valueHolder.getOpmModelFile() );

	  }
	  catch (Exception e)
	  {
		 throw new javax.ejb.EJBException(e);
	  }
   }

/* Value Objects END */

   public abstract java.lang.String getCollaborativeSessionID() ;

   public abstract void setCollaborativeSessionID( java.lang.String collaborativeSessionID ) ;

   public abstract java.lang.String getCollaborativeSessionName() ;

   public abstract void setCollaborativeSessionName( java.lang.String collaborativeSessionName ) ;

   public abstract java.lang.Boolean getEnabled() ;

   public abstract void setEnabled( java.lang.Boolean enabled ) ;

   public abstract java.lang.Boolean getDirty() ;

   public abstract void setDirty( java.lang.Boolean dirty ) ;

   public abstract java.lang.Boolean getTerminated() ;

   public abstract void setTerminated( java.lang.Boolean terminated ) ;

   public abstract java.util.Date getCreationTime() ;

   public abstract void setCreationTime( java.util.Date creationTime ) ;

   public abstract java.lang.String getDescription() ;

   public abstract void setDescription( java.lang.String description ) ;

   public abstract java.util.Date getLastUpdate() ;

   public abstract void setLastUpdate( java.util.Date lastUpdate ) ;

   public abstract java.lang.String getOpmModelID() ;

   public abstract void setOpmModelID( java.lang.String opmModelID ) ;

   public abstract java.lang.String getRevisionID() ;

   public abstract void setRevisionID( java.lang.String revisionID ) ;

   public abstract java.lang.Integer getTokenTimeout() ;

   public abstract void setTokenTimeout( java.lang.Integer tokenTimeout ) ;

   public abstract java.lang.Integer getUserTimeout() ;

   public abstract void setUserTimeout( java.lang.Integer userTimeout ) ;

   public abstract java.lang.String getTokenHolderID() ;

   public abstract void setTokenHolderID( java.lang.String tokenHolderID ) ;

   public abstract java.lang.String getOpmModelFile() ;

   public abstract void setOpmModelFile( java.lang.String opmModelFile ) ;

}
