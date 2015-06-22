/**
 * UserAccessLocal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface UserAccessLocal extends java.rmi.Remote {
    public org.objectprocess.SoapClient.WorkgroupPermissionsValue getWorkgroupPermissionsForUser(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.WorkgroupPermissionsPK in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.OpmModelPermissionsValue getOpmModelPermissionsForUser(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.OpmModelPermissionsPK in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.CollaborativeSessionPermissionsValue getCollaborativeSessionPermissionsForUser(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.CollaborativeSessionPermissionsPK in2) throws java.rmi.RemoteException;
    public java.lang.String loginUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public void logoutUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedUserValue getEnhancedUserByPK(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.UserValue getUserByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.UserValue getUserByLoginName(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void updateUser(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.UpdatableUserValue in2) throws java.rmi.RemoteException;
    public void disableUser(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void enableUser(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public java.lang.String createUser(org.objectprocess.SoapClient.EditableUserValue in0) throws java.rmi.RemoteException;
}
