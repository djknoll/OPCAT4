/**
 * CollaborativeSessionFileValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class CollaborativeSessionFileValue  implements java.io.Serializable {
    private java.lang.Integer collaborativeSessionID;
    private java.lang.String opmModelFile;
    private java.lang.Integer primaryKey;

    public CollaborativeSessionFileValue() {
    }

    public java.lang.Integer getCollaborativeSessionID() {
        return collaborativeSessionID;
    }

    public void setCollaborativeSessionID(java.lang.Integer collaborativeSessionID) {
        this.collaborativeSessionID = collaborativeSessionID;
    }

    public java.lang.String getOpmModelFile() {
        return opmModelFile;
    }

    public void setOpmModelFile(java.lang.String opmModelFile) {
        this.opmModelFile = opmModelFile;
    }

    public java.lang.Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(java.lang.Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CollaborativeSessionFileValue)) return false;
        CollaborativeSessionFileValue other = (CollaborativeSessionFileValue) obj;
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
            ((this.opmModelFile==null && other.getOpmModelFile()==null) || 
             (this.opmModelFile!=null &&
              this.opmModelFile.equals(other.getOpmModelFile()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey())));
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
        if (getOpmModelFile() != null) {
            _hashCode += getOpmModelFile().hashCode();
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CollaborativeSessionFileValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "CollaborativeSessionFileValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collaborativeSessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "collaborativeSessionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opmModelFile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
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
