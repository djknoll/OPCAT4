/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.session;

/**
 * Local home interface for UserAccess.
 * @lomboz generated
 */
public interface UserAccessLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/UserAccessLocal";
   public static final String JNDI_NAME="UserAccessLocal";

   public org.objectprocess.session.UserAccessLocal create()
      throws javax.ejb.CreateException;

}