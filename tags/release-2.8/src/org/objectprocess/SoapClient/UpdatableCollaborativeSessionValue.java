/**
 * UpdatableCollaborativeSessionValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class UpdatableCollaborativeSessionValue  implements java.io.Serializable {
    private java.lang.Integer collaborativeSessionID;
    private java.lang.String collaborativeSessionName;
    private java.lang.String description;
    private java.lang.Boolean enabled;
    private java.lang.Integer primaryKey;
    private java.lang.Integer tokenTimeout;
    private java.lang.Integer userTimeout;

    public UpdatableCollaborativeSessionValue() {
    }

    public java.lang.Integer getCollaborativeSessionID() {
        return collaborativeSessionID;
    }

    public void setCollaborativeSessionID(java.lang.Integer collaborativeSessionID) {
        this.collaborativeSessionID = collaborativeSessionID;
    }

    public java.lang.String getCollaborativeSessionName() {
        return collaborativeSessionName;
    }

    public void setCollaborativeSessionName(java.lang.String collaborativeSessionName) {
        this.collaborativeSessionName = collaborativeSessionName;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(java.lang.Boolean enabled) {
        this.enabled = enabled;
    }

    public java.lang.Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(java.lang.Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.Integer getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(java.lang.Integer tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    public java.lang.Integer getUserTimeout() {
        return userTimeout;
    }

    public void setUserTimeout(java.lang.Integer userTimeout) {
        this.userTimeout = userTimeout;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdatableCollaborativeSessionValue)) return false;
        UpdatableCollaborativeSessionValue other = (UpdatableCollaborativeSessionValue) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.collaborativeSessionID==null && other.getCollaborativeSessionID()==null) || 
             (this.collaborativeSessionID!=null &&
              this.collaborativeSessionID.equals(other.getCollaborativeSessionID()))) &&
            ((this.collaborativeSessionName==null && other.getCollaborativeSessionName()==null) || 
             (this.collaborativeSessionName!=null &&
              this.collaborativeSessionName.equals(other.getCollaborativeSessionName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.enabled==null && other.getEnabled()==null) || 
             (this.enabled!=null &&
              this.enabled.equals(other.getEnabled()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            ((this.tokenTimeout==null && other.getTokenTimeout()==null) || 
             (this.tokenTimeout!=null &&
              this.tokenTimeout.equals(other.getTokenTimeout()))) &&
            ((this.userTimeout==null && other.getUserTimeout()==null) || 
             (this.userTimeout!=null &&
              this.userTimeout.equals(other.getUserTimeout())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCollaborativeSessionID() != null) {
            _hashCode += getCollaborativeSessionID().hashCode();
        }
        if (getCollaborativeSessionName() != null) {
            _hashCode += getCollaborativeSessionName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getEnabled() != null) {
            _hashCode += getEnabled().hashCode();
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        if (getTokenTimeout() != null) {
            _hashCode += getTokenTimeout().hashCode();
        }
        if (getUserTimeout() != null) {
            _hashCode += getUserTimeout().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdatableCollaborativeSessionValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "UpdatableCollaborativeSessionValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collaborativeSessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "collaborativeSessionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collaborativeSessionName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "collaborativeSessionName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enabled");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enabled"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tokenTimeout");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tokenTimeout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userTimeout");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userTimeout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
