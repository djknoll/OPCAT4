/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.session;

/**
 * Local interface for WorkgroupAccess.
 * @lomboz generated
 */
public interface WorkgroupAccessLocal
   extends javax.ejb.EJBLocalObject
{
   /**
    * returns a workgroup value object for the given workgroup id.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @param workgroupID primary key for the requested workgroup.
    * @return the workgroup value object.
    * @throws AuthenticationFailedFault if user authentication failed.
    * @throws WorkgroupLookupFault if the requested workgroup could not be found.
    */
   public org.objectprocess.cmp.WorkgroupValue getWorkgroupByPK( java.lang.String sUserID,java.lang.String sPassword,java.lang.String workgroupID ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.lookup.LookupFault;

   /**
    * returns an enhanced workgroup value object for the given workgroup id.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @param workgroupID primary key for the requested workgroup.
    * @return the enhanced workgroup value object.
    * @throws AuthenticationFailedFault if user authentication failed.
    * @throws WorkgroupLookupFault if the requested workgroup could not be found.
    */
   public org.objectprocess.cmp.EnhancedWorkgroupValue getEnhancedWorkgroupByPK( java.lang.String sUserID,java.lang.String sPassword,java.lang.String workgroupID ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.lookup.LookupFault;

   /**
    * returns an enhanced workgroup permissions value object for the given workgroup permissions primary key. An enhanced workgroup permissions value object include a corresponding workgroup value object.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @param workgroupPermissionsPK primary key for the requested workgroup permissions.
    * @return the enhanced workgroup permissions value object.
    * @throws AuthenticationFailedFault if user authentication failed.
    * @throws PermissionLookupFault if the requested workgroup permissions could not be found.
    */
   public org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue getEnhancedWorkgroupPermissionsByPK( java.lang.String sUserID,java.lang.String sPassword,org.objectprocess.cmp.WorkgroupPermissionsPK workgroupPermissionsPK ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.lookup.LookupFault;

   /**
    * returns an array list of workgroup value objects for all workgroups in the system.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @return the list of all workgroups.
    * @throws AuthenticationFailedFault if user authentication failed.
    * @throws NoWorkgroupsFoundFault if no workgroups were found.
    */
   public java.util.ArrayList getAllWorkgroups( java.lang.String sUserID,java.lang.String sPassword ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.lookup.LookupFault;

   /**
    * returns an array list of OPM model value objects for all OPM models of the given workgroup.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @return the list of all OPM models.
    * @throws AuthenticationFailedFault if user authentication failed.
    * @throws NoOpmModelsFoundFault if no OPM models were found.
    */
   public java.util.ArrayList getOpmModelsForWorkgroup( java.lang.String sUserID,java.lang.String sPassword,java.lang.String workgroupID ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.lookup.LookupFault;

   /**
    * sets the workgroup permissions according to the editable workgroup permissions value object. if the permissions already exist they will be updated, otherwise new permissions will be created.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @param editableWorkgroupPermissionsValue an editable workgroup permissions value object containing the new permissions.
    * @throws AuthenticationFailedFault if user authentication failed.
    */
   public void setWorkgroupPermissions( java.lang.String sUserID,java.lang.String sPassword,org.objectprocess.cmp.EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault;

   /**
    * updates the workgroup entry with the given updatable workgroup value object.
    * @param sUserID workgroup id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @param updatableWorkgroupValue updatable workgroup value encapsulating the information to be updated.
    * @throws AuthenticationFailedFault if workgroup authentication failed.
    * @throws NoSuchWorkgroupFault if the requested workgroup could not be found.
    */
   public void updateWorkgroup( java.lang.String sUserID,java.lang.String sPassword,org.objectprocess.cmp.UpdatableWorkgroupValue updatableWorkgroupValue ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.lookup.LookupFault;

   /**
    * marks the requested workgroup as disabled. If <code>recursive</code> is set to <code>true</code> all OPM models in the workgroup as well as all collaborative sessions for those OPM models will recursively be disabled. If set to <code>false</code> the procedure will check that all OPM models in the workgroup are disabled before disabling the workgroup. if enabled OPM models are found the workgroup will not be disabled and a <code>RecursiveDisableFault</code> will be raised.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @param workgroupID primary key for workgroup to be disabled.
    * @param recursive a boolean denoting if simple or a recursive disable should be preformed.
    * @throws AuthenticationFailedFault if user authentication failed.
    * @throws NoSuchWorkgroupFault if the requested workgroup could not be found.
    * @throws RecursiveDisableFault if <code>recursive</code> is set to true and enabled OPM models are found in the workgroup.
    */
   public void disableWorkgroup( java.lang.String sUserID,java.lang.String sPassword,java.lang.String workgroupID,boolean recursive ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.lookup.LookupFault, org.objectprocess.fault.recursiveDisable.RecursiveDisableFault;

   /**
    * marks the requested workgroup as enabled. If <code>recursive</code> is set to <code>true</code> all OPM models in the workgroup as well as all collaborative sessions for those OPM models will recursively be enabled. If set to <code>false</code> only the requested workgroup will be enabled.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @param workgroupID primary key for workgroup to be disabled.
    * @param recursive a boolean denoting if simple or a recursive enable should be preformed.
    * @throws AuthenticationFailedFault if user authentication failed.
    * @throws NoSuchWorkgroupFault if the requested workgroup could not be found.
    */
   public void enableWorkgroup( java.lang.String sUserID,java.lang.String sPassword,java.lang.String workgroupID,boolean recursive ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.lookup.LookupFault;

   /**
    * creates a new workgroup entry based on the information in the editable workgroup value object. an editable workgroup value object contains all workgroup attributes that can be initialized during creation.
    * @param sUserID user id for the user performing the action.
    * @param sPassword password for the user performing the action.
    * @param editableWorkgroupValue the editable workgroup value object containing the editable workgroup attributes.
    * @return GUID primary key for the new workgroup.
    * @throws WorkgroupAlreadyExistFault if a workgroup with the requested name already exists.
    */
   public java.lang.String createWorkgroup( java.lang.String sUserID,java.lang.String sPassword,org.objectprocess.cmp.EditableWorkgroupValue editableWorkgroupValue ) throws org.objectprocess.fault.authenticate.AuthenticationFailedFault, org.objectprocess.fault.create.CreationFault;

}
