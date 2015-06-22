/**
 * EnhancedOpmModelValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class EnhancedOpmModelValue  implements java.io.Serializable {
    private java.lang.Object[] addedOpmModelsPermissions;
    private java.util.Calendar creationTime;
    private java.lang.String description;
    private java.lang.Boolean enabled;
    private java.lang.Object[] onceAddedOpmModelsPermissions;
    private java.lang.String opmModelID;
    private java.lang.String opmModelName;
    private org.objectprocess.SoapClient.Enhanced2OpmModelPermissionsValue[] opmModelsPermissions;
    private java.lang.String primaryKey;
    private java.lang.Object[] removedOpmModelsPermissions;
    private java.lang.Integer totalCollaborativeTime;
    private java.lang.Object[] updatedOpmModelsPermissions;
    private java.lang.String workgroupID;

    public EnhancedOpmModelValue() {
    }

    public java.lang.Object[] getAddedOpmModelsPermissions() {
        return addedOpmModelsPermissions;
    }

    public void setAddedOpmModelsPermissions(java.lang.Object[] addedOpmModelsPermissions) {
        this.addedOpmModelsPermissions = addedOpmModelsPermissions;
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

    public java.lang.Object[] getOnceAddedOpmModelsPermissions() {
        return onceAddedOpmModelsPermissions;
    }

    public void setOnceAddedOpmModelsPermissions(java.lang.Object[] onceAddedOpmModelsPermissions) {
        this.onceAddedOpmModelsPermissions = onceAddedOpmModelsPermissions;
    }

    public java.lang.String getOpmModelID() {
        return opmModelID;
    }

    public void setOpmModelID(java.lang.String opmModelID) {
        this.opmModelID = opmModelID;
    }

    public java.lang.String getOpmModelName() {
        return opmModelName;
    }

    public void setOpmModelName(java.lang.String opmModelName) {
        this.opmModelName = opmModelName;
    }

    public org.objectprocess.SoapClient.Enhanced2OpmModelPermissionsValue[] getOpmModelsPermissions() {
        return opmModelsPermissions;
    }

    public void setOpmModelsPermissions(org.objectprocess.SoapClient.Enhanced2OpmModelPermissionsValue[] opmModelsPermissions) {
        this.opmModelsPermissions = opmModelsPermissions;
    }

    public java.lang.String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(java.lang.String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.Object[] getRemovedOpmModelsPermissions() {
        return removedOpmModelsPermissions;
    }

    public void setRemovedOpmModelsPermissions(java.lang.Object[] removedOpmModelsPermissions) {
        this.removedOpmModelsPermissions = removedOpmModelsPermissions;
    }

    public java.lang.Integer getTotalCollaborativeTime() {
        return totalCollaborativeTime;
    }

    public void setTotalCollaborativeTime(java.lang.Integer totalCollaborativeTime) {
        this.totalCollaborativeTime = totalCollaborativeTime;
    }

    public java.lang.Object[] getUpdatedOpmModelsPermissions() {
        return updatedOpmModelsPermissions;
    }

    public void setUpdatedOpmModelsPermissions(java.lang.Object[] updatedOpmModelsPermissions) {
        this.updatedOpmModelsPermissions = updatedOpmModelsPermissions;
    }

    public java.lang.String getWorkgroupID() {
        return workgroupID;
    }

    public void setWorkgroupID(java.lang.String workgroupID) {
        this.workgroupID = workgroupID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnhancedOpmModelValue)) return false;
        EnhancedOpmModelValue other = (EnhancedOpmModelValue) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addedOpmModelsPermissions==null && other.getAddedOpmModelsPermissions()==null) || 
             (this.addedOpmModelsPermissions!=null &&
              java.util.Arrays.equals(this.addedOpmModelsPermissions, other.getAddedOpmModelsPermissions()))) &&
            ((this.creationTime==null && other.getCreationTime()==null) || 
             (this.creationTime!=null &&
              this.creationTime.equals(other.getCreationTime()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.enabled==null && other.getEnabled()==null) || 
             (this.enabled!=null &&
              this.enabled.equals(other.getEnabled()))) &&
            ((this.onceAddedOpmModelsPermissions==null && other.getOnceAddedOpmModelsPermissions()==null) || 
             (this.onceAddedOpmModelsPermissions!=null &&
              java.util.Arrays.equals(this.onceAddedOpmModelsPermissions, other.getOnceAddedOpmModelsPermissions()))) &&
            ((this.opmModelID==null && other.getOpmModelID()==null) || 
             (this.opmModelID!=null &&
              this.opmModelID.equals(other.getOpmModelID()))) &&
            ((this.opmModelName==null && other.getOpmModelName()==null) || 
             (this.opmModelName!=null &&
              this.opmModelName.equals(other.getOpmModelName()))) &&
            ((this.opmModelsPermissions==null && other.getOpmModelsPermissions()==null) || 
             (this.opmModelsPermissions!=null &&
              java.util.Arrays.equals(this.opmModelsPermissions, other.getOpmModelsPermissions()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            ((this.removedOpmModelsPermissions==null && other.getRemovedOpmModelsPermissions()==null) || 
             (this.removedOpmModelsPermissions!=null &&
              java.util.Arrays.equals(this.removedOpmModelsPermissions, other.getRemovedOpmModelsPermissions()))) &&
            ((this.totalCollaborativeTime==null && other.getTotalCollaborativeTime()==null) || 
             (this.totalCollaborativeTime!=null &&
              this.totalCollaborativeTime.equals(other.getTotalCollaborativeTime()))) &&
            ((this.updatedOpmModelsPermissions==null && other.getUpdatedOpmModelsPermissions()==null) || 
             (this.updatedOpmModelsPermissions!=null &&
              java.util.Arrays.equals(this.updatedOpmModelsPermissions, other.getUpdatedOpmModelsPermissions()))) &&
            ((this.workgroupID==null && other.getWorkgroupID()==null) || 
             (this.workgroupID!=null &&
              this.workgroupID.equals(other.getWorkgroupID())));
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
        if (getAddedOpmModelsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAddedOpmModelsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAddedOpmModelsPermissions(), i);
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
        if (getOnceAddedOpmModelsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOnceAddedOpmModelsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOnceAddedOpmModelsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOpmModelID() != null) {
            _hashCode += getOpmModelID().hashCode();
        }
        if (getOpmModelName() != null) {
            _hashCode += getOpmModelName().hashCode();
        }
        if (getOpmModelsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOpmModelsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOpmModelsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        if (getRemovedOpmModelsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRemovedOpmModelsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRemovedOpmModelsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTotalCollaborativeTime() != null) {
            _hashCode += getTotalCollaborativeTime().hashCode();
        }
        if (getUpdatedOpmModelsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUpdatedOpmModelsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUpdatedOpmModelsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getWorkgroupID() != null) {
            _hashCode += getWorkgroupID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EnhancedOpmModelValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "EnhancedOpmModelValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addedOpmModelsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addedOpmModelsPermissions"));
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
        elemField.setFieldName("onceAddedOpmModelsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "onceAddedOpmModelsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opmModelID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opmModelName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opmModelsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "Enhanced2OpmModelPermissionsValue"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("removedOpmModelsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "removedOpmModelsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCollaborativeTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalCollaborativeTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatedOpmModelsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updatedOpmModelsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workgroupID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workgroupID"));
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
