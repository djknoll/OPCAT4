/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Home interface for OpmModel.
 * @lomboz generated
 */
public interface OpmModelHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/OpmModel";
   public static final String JNDI_NAME="OpmModelBean";

   public org.objectprocess.cmp.OpmModel create(org.objectprocess.cmp.EditableOpmModelValue editableOpmModelValue)
      throws javax.ejb.CreateException,java.rmi.RemoteException;

   public java.util.Collection findAll()
      throws javax.ejb.FinderException,java.rmi.RemoteException;

   public java.util.Collection findByOpmModelName(java.lang.String opmModelName)
      throws javax.ejb.FinderException,java.rmi.RemoteException;

   public org.objectprocess.cmp.OpmModel findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException,java.rmi.RemoteException;

}
