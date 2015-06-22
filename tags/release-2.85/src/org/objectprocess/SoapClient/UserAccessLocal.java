/**
 * UserAccessLocal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface UserAccessLocal extends java.rmi.Remote {
    public org.objectprocess.SoapClient.EditableUserValue setUser(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableUserValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.WorkgroupPermissionsValue getWorkgroupPermissionsForUser(int in0, java.lang.String in1, org.objectprocess.SoapClient.WorkgroupPermissionsPK in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.OpmModelPermissionsValue getOpmModelPermissionsForUser(int in0, java.lang.String in1, org.objectprocess.SoapClient.OpmModelPermissionsPK in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.CollaborativeSessionPermissionsValue getCollaborativeSessionPermissionsForUser(int in0, java.lang.String in1, org.objectprocess.SoapClient.CollaborativeSessionPermissionsPK in2) throws java.rmi.RemoteException;
    public int createUser(org.objectprocess.SoapClient.EditableUserValue in0) throws java.rmi.RemoteException;
    public int loginUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public void logoutUser(int in0) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedUserValue getEnhancedUserByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.UserValue getUserByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.UserValue getUserByLoginName(int in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void updateUser(int in0, java.lang.String in1, org.objectprocess.SoapClient.UpdatableUserValue in2) throws java.rmi.RemoteException;
    public void disableUser(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void enableUser(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
}
