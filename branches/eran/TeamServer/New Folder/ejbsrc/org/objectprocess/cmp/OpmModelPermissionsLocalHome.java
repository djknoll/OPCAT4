/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Local home interface for OpmModelPermissions.
 * @lomboz generated
 */
public interface OpmModelPermissionsLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/OpmModelPermissionsLocal";
   public static final String JNDI_NAME="OpmModelPermissionsLocal";

   public org.objectprocess.cmp.OpmModelPermissionsLocal create(org.objectprocess.cmp.EditableOpmModelPermissionsValue editableOpmModelPermissionsValue)
      throws javax.ejb.CreateException;

   public java.util.Collection findAll()
      throws javax.ejb.FinderException;

   public org.objectprocess.cmp.OpmModelPermissionsLocal findByPrimaryKey(org.objectprocess.cmp.OpmModelPermissionsPK pk)
      throws javax.ejb.FinderException;

}
