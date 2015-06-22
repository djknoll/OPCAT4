/**
 * OpmModelAccessLocal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface OpmModelAccessLocal extends java.rmi.Remote {
    public void setOpmModelPermissions(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableOpmModelPermissionsValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.OpmModelValue setOpmModel(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableOpmModelValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.OpmModelValue getOpmModelByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedOpmModelValue getEnhancedOpmModelByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue createOpmModel(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableOpmModelValue in2) throws java.rmi.RemoteException;
    public java.lang.Object getAllRevisions(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public java.lang.Object getPreCommit(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.MetaRevisionValue getMetaRevisionByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.FullRevisionValue getRevisionByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void updateOpmModel(int in0, java.lang.String in1, org.objectprocess.SoapClient.UpdatableOpmModelValue in2) throws java.rmi.RemoteException;
    public void disableOpmModel(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void enableOpmModel(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
}
