/**
 * CollaborativeSessionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class CollaborativeSessionServiceLocator extends org.apache.axis.client.Service implements org.objectprocess.SoapClient.CollaborativeSessionService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	// Use to get a proxy class for CollaborativeSessionService
    private final java.lang.String CollaborativeSessionService_address = "http://127.0.0.1:8080/axis/services/CollaborativeSessionService";

    public java.lang.String getCollaborativeSessionServiceAddress() {
        return this.CollaborativeSessionService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CollaborativeSessionServiceWSDDServiceName = "CollaborativeSessionService";

    public java.lang.String getCollaborativeSessionServiceWSDDServiceName() {
        return this.CollaborativeSessionServiceWSDDServiceName;
    }

    public void setCollaborativeSessionServiceWSDDServiceName(java.lang.String name) {
        this.CollaborativeSessionServiceWSDDServiceName = name;
    }

    public org.objectprocess.SoapClient.CollaborativeSessionAccessLocal getCollaborativeSessionService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(this.CollaborativeSessionService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return this.getCollaborativeSessionService(endpoint);
    }

    public org.objectprocess.SoapClient.CollaborativeSessionAccessLocal getCollaborativeSessionService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.objectprocess.SoapClient.CollaborativeSessionServiceSoapBindingStub _stub = new org.objectprocess.SoapClient.CollaborativeSessionServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(this.getCollaborativeSessionServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.objectprocess.SoapClient.CollaborativeSessionAccessLocal.class.isAssignableFrom(serviceEndpointInterface)) {
                org.objectprocess.SoapClient.CollaborativeSessionServiceSoapBindingStub _stub = new org.objectprocess.SoapClient.CollaborativeSessionServiceSoapBindingStub(new java.net.URL(this.CollaborativeSessionService_address), this);
                _stub.setPortName(this.getCollaborativeSessionServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return this.getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("CollaborativeSessionService".equals(inputPortName)) {
            return this.getCollaborativeSessionService();
        }
        else  {
            java.rmi.Remote _stub = this.getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8080/", "CollaborativeSessionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (this.ports == null) {
            this.ports = new java.util.HashSet();
            this.ports.add(new javax.xml.namespace.QName("CollaborativeSessionService"));
        }
        return this.ports.iterator();
    }

}
