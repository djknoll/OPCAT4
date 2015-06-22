/**
 * UserService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public interface UserService extends javax.xml.rpc.Service {
    public java.lang.String getUserServiceAddress();

    public org.objectprocess.SoapClient.UserAccessLocal getUserService() throws javax.xml.rpc.ServiceException;

    public org.objectprocess.SoapClient.UserAccessLocal getUserService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
