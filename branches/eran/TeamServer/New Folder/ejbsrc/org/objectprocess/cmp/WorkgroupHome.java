/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Home interface for Workgroup.
 * @lomboz generated
 */
public interface WorkgroupHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/Workgroup";
   public static final String JNDI_NAME="WorkgroupBean";

   public org.objectprocess.cmp.Workgroup create(org.objectprocess.cmp.EditableWorkgroupValue editableWorkgroupValue)
      throws javax.ejb.CreateException,java.rmi.RemoteException;

   public java.util.Collection findAll()
      throws javax.ejb.FinderException,java.rmi.RemoteException;

   public java.util.Collection findByWorkgroupName(java.lang.String workgroupName)
      throws javax.ejb.FinderException,java.rmi.RemoteException;

   public org.objectprocess.cmp.Workgroup findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException,java.rmi.RemoteException;

}
