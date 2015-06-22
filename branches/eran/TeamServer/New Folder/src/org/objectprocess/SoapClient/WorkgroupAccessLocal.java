/**
 * WorkgroupAccessLocal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface WorkgroupAccessLocal extends java.rmi.Remote {
    public void setWorkgroupPermissions(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.EditableWorkgroupPermissionsValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.WorkgroupValue getWorkgroupByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedWorkgroupValue getEnhancedWorkgroupByPK(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue getEnhancedWorkgroupPermissionsByPK(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.WorkgroupPermissionsPK in2) throws java.rmi.RemoteException;
    public void updateWorkgroup(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.UpdatableWorkgroupValue in2) throws java.rmi.RemoteException;
    public void disableWorkgroup(java.lang.String in0, java.lang.String in1, java.lang.String in2, boolean in3) throws java.rmi.RemoteException;
    public void enableWorkgroup(java.lang.String in0, java.lang.String in1, java.lang.String in2, boolean in3) throws java.rmi.RemoteException;
    public java.lang.String createWorkgroup(java.lang.String in0, java.lang.String in1, org.objectprocess.SoapClient.EditableWorkgroupValue in2) throws java.rmi.RemoteException;
}
