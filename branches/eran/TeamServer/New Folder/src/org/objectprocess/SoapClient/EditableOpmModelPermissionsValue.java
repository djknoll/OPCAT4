/**
 * EditableOpmModelPermissionsValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class EditableOpmModelPermissionsValue  implements java.io.Serializable {
    private java.lang.Integer accessControl;
    private java.lang.String opmModelID;
    private org.objectprocess.SoapClient.OpmModelPermissionsPK primaryKey;
    private java.lang.String userID;

    public EditableOpmModelPermissionsValue() {
    }

    public java.lang.Integer getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(java.lang.Integer accessControl) {
        this.accessControl = accessControl;
    }

    public java.lang.String getOpmModelID() {
        return opmModelID;
    }

    public void setOpmModelID(java.lang.String opmModelID) {
        this.opmModelID = opmModelID;
    }

    public org.objectprocess.SoapClient.OpmModelPermissionsPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(org.objectprocess.SoapClient.OpmModelPermissionsPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.String getUserID() {
        return userID;
    }

    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EditableOpmModelPermissionsValue)) return false;
        EditableOpmModelPermissionsValue other = (EditableOpmModelPermissionsValue) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accessControl==null && other.getAccessControl()==null) || 
             (this.accessControl!=null &&
              this.accessControl.equals(other.getAccessControl()))) &&
            ((this.opmModelID==null && other.getOpmModelID()==null) || 
             (this.opmModelID!=null &&
              this.opmModelID.equals(other.getOpmModelID()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            ((this.userID==null && other.getUserID()==null) || 
             (this.userID!=null &&
              this.userID.equals(other.getUserID())));
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
        if (getAccessControl() != null) {
            _hashCode += getAccessControl().hashCode();
        }
        if (getOpmModelID() != null) {
            _hashCode += getOpmModelID().hashCode();
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        if (getUserID() != null) {
            _hashCode += getUserID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EditableOpmModelPermissionsValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "EditableOpmModelPermissionsValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessControl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accessControl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opmModelID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "OpmModelPermissionsPK"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
