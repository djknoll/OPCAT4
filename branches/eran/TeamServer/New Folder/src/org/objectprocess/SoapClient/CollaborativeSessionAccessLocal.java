/**
 * CollaborativeSessionAccessLocal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface CollaborativeSessionAccessLocal extends java.rmi.Remote {
    public void setCollaborativeSessionPermissions(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.EditableCollaborativeSessionPermissionsValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.CollaborativeSessionValue getCollaborativeSessionByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedCollaborativeSessionValue getEnhancedCollaborativeSessionByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.CollaborativeSessionFileValue getCollaborativeSessionFileByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue getEnhancedCollaborativeSessionPermissionsByPK(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.CollaborativeSessionPermissionsPK in2) throws java.rmi.RemoteException;
    public void updateCollaborativeSession(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.UpdatableCollaborativeSessionValue in2) throws java.rmi.RemoteException;
    public void requestToken(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void returnToken(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EditableRevisionValue commitCollaborativeSession(java.lang.String in0, java.lang.String in1, java.lang.String in2, org.objectprocess.SoapClient.EditableRevisionValue in3, boolean in4) throws java.rmi.RemoteException;
    public void disableCollaborativeSession(java.lang.String in0, java.lang.String in1, java.lang.String in2, boolean in3) throws java.rmi.RemoteException;
    public void enableCollaborativeSession(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void sessionLoginUser(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void sessionLogoutUser(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public java.lang.String createCollaborativeSession(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.EditableCollaborativeSessionValue in2) throws java.rmi.RemoteException;
    public void updateCollaborativeSessionFile(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.CollaborativeSessionFileValue in2) throws java.rmi.RemoteException;
}
