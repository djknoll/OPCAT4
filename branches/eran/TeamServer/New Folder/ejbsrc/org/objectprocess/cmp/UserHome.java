/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Home interface for User.
 * @lomboz generated
 */
public interface UserHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/User";
   public static final String JNDI_NAME="UserBean";

   public org.objectprocess.cmp.User create(org.objectprocess.cmp.EditableUserValue editableUserValue)
      throws javax.ejb.CreateException,java.rmi.RemoteException;

   public java.util.Collection findAll()
      throws javax.ejb.FinderException,java.rmi.RemoteException;

   public java.util.Collection findByLoginName(java.lang.String loginName)
      throws javax.ejb.FinderException,java.rmi.RemoteException;

   public org.objectprocess.cmp.User findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException,java.rmi.RemoteException;

}