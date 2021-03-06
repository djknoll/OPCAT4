/*
 * Generated by XDoclet - Do not edit!
 */
package org.objectprocess.cmp;

/**
 * Remote interface for WorkgroupPermissions.
 * @lomboz generated
 */
public interface WorkgroupPermissions
   extends javax.ejb.EJBObject
{
   /**
    * Returns the workgroupID
    * @return the workgroupID
    */
   public java.lang.String getWorkgroupID(  )
      throws java.rmi.RemoteException;

   /**
    * Sets the workgroupID
    * @param java.lang.Integer the new workgroupID value
    */
   public void setWorkgroupID( java.lang.String workgroupID )
      throws java.rmi.RemoteException;

   /**
    * Returns the userID
    * @return the userID
    */
   public java.lang.String getUserID(  )
      throws java.rmi.RemoteException;

   /**
    * Sets the userID
    * @param java.lang.Integer the new userID value
    */
   public void setUserID( java.lang.String userID )
      throws java.rmi.RemoteException;

   /**
    * Returns the joinTime
    * @return the joinTime
    */
   public java.util.Date getJoinTime(  )
      throws java.rmi.RemoteException;

   /**
    * Sets the joinTime
    * @param java.util.Date the new joinTime value
    */
   public void setJoinTime( java.util.Date joinTime )
      throws java.rmi.RemoteException;

   /**
    * Returns the accessControl
    * @return the accessControl
    */
   public java.lang.Integer getAccessControl(  )
      throws java.rmi.RemoteException;

   /**
    * Sets the accessControl
    * @param java.lang.Integer the new accessControl value
    */
   public void setAccessControl( java.lang.Integer accessControl )
      throws java.rmi.RemoteException;

}
