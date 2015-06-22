/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Local home interface for Workgroup.
 * @lomboz generated
 */
public interface WorkgroupLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/WorkgroupLocal";
   public static final String JNDI_NAME="WorkgroupLocal";

   public org.objectprocess.cmp.WorkgroupLocal create(org.objectprocess.cmp.EditableWorkgroupValue editableWorkgroupValue)
      throws javax.ejb.CreateException;

   public java.util.Collection findAll()
      throws javax.ejb.FinderException;

   public java.util.Collection findByWorkgroupName(java.lang.String workgroupName)
      throws javax.ejb.FinderException;

   public org.objectprocess.cmp.WorkgroupLocal findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException;

}