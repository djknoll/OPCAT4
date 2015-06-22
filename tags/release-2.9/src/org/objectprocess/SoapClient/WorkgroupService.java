/**
 * WorkgroupService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface WorkgroupService extends javax.xml.rpc.Service {
    public java.lang.String getWorkgroupServiceAddress();

    public org.objectprocess.SoapClient.WorkgroupAccessLocal getWorkgroupService() throws javax.xml.rpc.ServiceException;

    public org.objectprocess.SoapClient.WorkgroupAccessLocal getWorkgroupService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
