/**
 * OpmModelAccessLocal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface OpmModelAccessLocal extends java.rmi.Remote {
    public void setOpmModelPermissions(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.EditableOpmModelPermissionsValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.OpmModelValue getOpmModelByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedOpmModelValue getEnhancedOpmModelByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.OpmModelRevisionsValue getOpmModelRevisionsByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public java.lang.Object getAllRevisions(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.MetaRevisionValue getMetaRevisionByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.FullRevisionValue getRevisionByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public java.lang.Object getPreCommit(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue getEnhancedOpmModelPermissionsByPK(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.OpmModelPermissionsPK in2) throws java.rmi.RemoteException;
    public void updateOpmModel(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.UpdatableOpmModelValue in2) throws java.rmi.RemoteException;
    public void disableOpmModel(java.lang.String in0, java.lang.String in1, java.lang.String in2, boolean in3) throws java.rmi.RemoteException;
    public void enableOpmModel(java.lang.String in0, java.lang.String in1, java.lang.String in2, boolean in3) throws java.rmi.RemoteException;
    public java.lang.String createOpmModel(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.EditableOpmModelValue in2) throws java.rmi.RemoteException;
}
