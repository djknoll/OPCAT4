/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.session;

/**
 * Home interface for WorkgroupAccess.
 * @lomboz generated
 */
public interface WorkgroupAccessHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/WorkgroupAccess";
   public static final String JNDI_NAME="WorkgroupAccessBean";

   public org.objectprocess.session.WorkgroupAccess create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
