/**
 * CollaborativeSessionAccessLocal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface CollaborativeSessionAccessLocal extends java.rmi.Remote {
    public void setCollaborativeSessionPermissions(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableCollaborativeSessionPermissionsValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.CollaborativeSessionValue setCollaborativeSession(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableCollaborativeSessionValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.CollaborativeSessionValue getCollaborativeSessionByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedCollaborativeSessionValue getEnhancedCollaborativeSessionByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue createCollaborativeSession(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableCollaborativeSessionValue in2) throws java.rmi.RemoteException;
    public void updateCollaborativeSession(int in0, java.lang.String in1, org.objectprocess.SoapClient.UpdatableCollaborativeSessionValue in2) throws java.rmi.RemoteException;
    public boolean requestToken(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void returnToken(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EditableRevisionValue commitCollaborativeSession(int in0, java.lang.String in1, int in2, org.objectprocess.SoapClient.EditableRevisionValue in3, boolean in4) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.CollaborativeSessionFileValue getCollaborativeSessionFileByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void setCollaborativeSessionFileByPK(int in0, java.lang.String in1, org.objectprocess.SoapClient.CollaborativeSessionFileValue in2) throws java.rmi.RemoteException;
    public void disableCollaborativeSession(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void enableCollaborativeSession(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void sessionLoginUser(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void sessionLogoutUser(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
}
