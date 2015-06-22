/**
 * WorkgroupAccessLocal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface WorkgroupAccessLocal extends java.rmi.Remote {
    public void setWorkgroupPermissions(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableWorkgroupPermissionsValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.WorkgroupValue setWorkgroup(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableWorkgroupValue in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.WorkgroupValue getWorkgroupByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedWorkgroupValue getEnhancedWorkgroupByPK(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue createWorkgroup(int in0, java.lang.String in1, org.objectprocess.SoapClient.EditableWorkgroupValue in2) throws java.rmi.RemoteException;
    public void disableWorkgroup(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
    public void enableWorkgroup(int in0, java.lang.String in1, int in2) throws java.rmi.RemoteException;
}
