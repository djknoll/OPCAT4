/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Local home interface for OpmModel.
 * @lomboz generated
 */
public interface OpmModelLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/OpmModelLocal";
   public static final String JNDI_NAME="OpmModelLocal";

   public org.objectprocess.cmp.OpmModelLocal create(org.objectprocess.cmp.EditableOpmModelValue editableOpmModelValue)
      throws javax.ejb.CreateException;

   public java.util.Collection findAll()
      throws javax.ejb.FinderException;

   public java.util.Collection findByOpmModelName(java.lang.String opmModelName)
      throws javax.ejb.FinderException;

   public org.objectprocess.cmp.OpmModelLocal findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException;

}