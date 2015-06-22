/**
 * EnhancedWorkgroupValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class EnhancedWorkgroupValue  implements java.io.Serializable {
    private java.lang.Object[] addedWorkgroupsPermissions;
    private java.util.Calendar creationTime;
    private java.lang.String description;
    private java.lang.Boolean enabled;
    private java.lang.Object[] onceAddedWorkgroupsPermissions;
    private java.lang.String primaryKey;
    private java.lang.Object[] removedWorkgroupsPermissions;
    private java.lang.Object[] updatedWorkgroupsPermissions;
    private java.lang.String workgroupID;
    private java.lang.String workgroupName;
    private org.objectprocess.SoapClient.Enhanced2WorkgroupPermissionsValue[] workgroupsPermissions;

    public EnhancedWorkgroupValue() {
    }

    public java.lang.Object[] getAddedWorkgroupsPermissions() {
        return addedWorkgroupsPermissions;
    }

    public void setAddedWorkgroupsPermissions(java.lang.Object[] addedWorkgroupsPermissions) {
        this.addedWorkgroupsPermissions = addedWorkgroupsPermissions;
    }

    public java.util.Calendar getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(java.util.Calendar creationTime) {
        this.creationTime = creationTime;
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

    public java.lang.Object[] getOnceAddedWorkgroupsPermissions() {
        return onceAddedWorkgroupsPermissions;
    }

    public void setOnceAddedWorkgroupsPermissions(java.lang.Object[] onceAddedWorkgroupsPermissions) {
        this.onceAddedWorkgroupsPermissions = onceAddedWorkgroupsPermissions;
    }

    public java.lang.String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(java.lang.String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.Object[] getRemovedWorkgroupsPermissions() {
        return removedWorkgroupsPermissions;
    }

    public void setRemovedWorkgroupsPermissions(java.lang.Object[] removedWorkgroupsPermissions) {
        this.removedWorkgroupsPermissions = removedWorkgroupsPermissions;
    }

    public java.lang.Object[] getUpdatedWorkgroupsPermissions() {
        return updatedWorkgroupsPermissions;
    }

    public void setUpdatedWorkgroupsPermissions(java.lang.Object[] updatedWorkgroupsPermissions) {
        this.updatedWorkgroupsPermissions = updatedWorkgroupsPermissions;
    }

    public java.lang.String getWorkgroupID() {
        return workgroupID;
    }

    public void setWorkgroupID(java.lang.String workgroupID) {
        this.workgroupID = workgroupID;
    }

    public java.lang.String getWorkgroupName() {
        return workgroupName;
    }

    public void setWorkgroupName(java.lang.String workgroupName) {
        this.workgroupName = workgroupName;
    }

    public org.objectprocess.SoapClient.Enhanced2WorkgroupPermissionsValue[] getWorkgroupsPermissions() {
        return workgroupsPermissions;
    }

    public void setWorkgroupsPermissions(org.objectprocess.SoapClient.Enhanced2WorkgroupPermissionsValue[] workgroupsPermissions) {
        this.workgroupsPermissions = workgroupsPermissions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnhancedWorkgroupValue)) return false;
        EnhancedWorkgroupValue other = (EnhancedWorkgroupValue) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addedWorkgroupsPermissions==null && other.getAddedWorkgroupsPermissions()==null) || 
             (this.addedWorkgroupsPermissions!=null &&
              java.util.Arrays.equals(this.addedWorkgroupsPermissions, other.getAddedWorkgroupsPermissions()))) &&
            ((this.creationTime==null && other.getCreationTime()==null) || 
             (this.creationTime!=null &&
              this.creationTime.equals(other.getCreationTime()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.enabled==null && other.getEnabled()==null) || 
             (this.enabled!=null &&
              this.enabled.equals(other.getEnabled()))) &&
            ((this.onceAddedWorkgroupsPermissions==null && other.getOnceAddedWorkgroupsPermissions()==null) || 
             (this.onceAddedWorkgroupsPermissions!=null &&
              java.util.Arrays.equals(this.onceAddedWorkgroupsPermissions, other.getOnceAddedWorkgroupsPermissions()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            ((this.removedWorkgroupsPermissions==null && other.getRemovedWorkgroupsPermissions()==null) || 
             (this.removedWorkgroupsPermissions!=null &&
              java.util.Arrays.equals(this.removedWorkgroupsPermissions, other.getRemovedWorkgroupsPermissions()))) &&
            ((this.updatedWorkgroupsPermissions==null && other.getUpdatedWorkgroupsPermissions()==null) || 
             (this.updatedWorkgroupsPermissions!=null &&
              java.util.Arrays.equals(this.updatedWorkgroupsPermissions, other.getUpdatedWorkgroupsPermissions()))) &&
            ((this.workgroupID==null && other.getWorkgroupID()==null) || 
             (this.workgroupID!=null &&
              this.workgroupID.equals(other.getWorkgroupID()))) &&
            ((this.workgroupName==null && other.getWorkgroupName()==null) || 
             (this.workgroupName!=null &&
              this.workgroupName.equals(other.getWorkgroupName()))) &&
            ((this.workgroupsPermissions==null && other.getWorkgroupsPermissions()==null) || 
             (this.workgroupsPermissions!=null &&
              java.util.Arrays.equals(this.workgroupsPermissions, other.getWorkgroupsPermissions())));
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
        if (getAddedWorkgroupsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAddedWorkgroupsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAddedWorkgroupsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCreationTime() != null) {
            _hashCode += getCreationTime().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getEnabled() != null) {
            _hashCode += getEnabled().hashCode();
        }
        if (getOnceAddedWorkgroupsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOnceAddedWorkgroupsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOnceAddedWorkgroupsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        if (getRemovedWorkgroupsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRemovedWorkgroupsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRemovedWorkgroupsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUpdatedWorkgroupsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUpdatedWorkgroupsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUpdatedWorkgroupsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getWorkgroupID() != null) {
            _hashCode += getWorkgroupID().hashCode();
        }
        if (getWorkgroupName() != null) {
            _hashCode += getWorkgroupName().hashCode();
        }
        if (getWorkgroupsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getWorkgroupsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getWorkgroupsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EnhancedWorkgroupValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "EnhancedWorkgroupValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addedWorkgroupsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addedWorkgroupsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creationTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("onceAddedWorkgroupsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "onceAddedWorkgroupsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("removedWorkgroupsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "removedWorkgroupsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatedWorkgroupsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updatedWorkgroupsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workgroupID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workgroupID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workgroupName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workgroupName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workgroupsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workgroupsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "Enhanced2WorkgroupPermissionsValue"));
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
