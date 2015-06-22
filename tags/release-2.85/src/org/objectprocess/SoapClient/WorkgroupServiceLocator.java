/**
 * WorkgroupServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class WorkgroupServiceLocator extends org.apache.axis.client.Service implements org.objectprocess.SoapClient.WorkgroupService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	// Use to get a proxy class for WorkgroupService
    private final java.lang.String WorkgroupService_address = "http://127.0.0.1:8080/axis/services/WorkgroupService";

    public java.lang.String getWorkgroupServiceAddress() {
        return this.WorkgroupService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WorkgroupServiceWSDDServiceName = "WorkgroupService";

    public java.lang.String getWorkgroupServiceWSDDServiceName() {
        return this.WorkgroupServiceWSDDServiceName;
    }

    public void setWorkgroupServiceWSDDServiceName(java.lang.String name) {
        this.WorkgroupServiceWSDDServiceName = name;
    }

    public org.objectprocess.SoapClient.WorkgroupAccessLocal getWorkgroupService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(this.WorkgroupService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return this.getWorkgroupService(endpoint);
    }

    public org.objectprocess.SoapClient.WorkgroupAccessLocal getWorkgroupService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.objectprocess.SoapClient.WorkgroupServiceSoapBindingStub _stub = new org.objectprocess.SoapClient.WorkgroupServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(this.getWorkgroupServiceWSDDServiceName());
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
            if (org.objectprocess.SoapClient.WorkgroupAccessLocal.class.isAssignableFrom(serviceEndpointInterface)) {
                org.objectprocess.SoapClient.WorkgroupServiceSoapBindingStub _stub = new org.objectprocess.SoapClient.WorkgroupServiceSoapBindingStub(new java.net.URL(this.WorkgroupService_address), this);
                _stub.setPortName(this.getWorkgroupServiceWSDDServiceName());
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
        if ("WorkgroupService".equals(inputPortName)) {
            return this.getWorkgroupService();
        }
        else  {
            java.rmi.Remote _stub = this.getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8080/", "WorkgroupService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (this.ports == null) {
            this.ports = new java.util.HashSet();
            this.ports.add(new javax.xml.namespace.QName("WorkgroupService"));
        }
        return this.ports.iterator();
    }

}
