/**
 * OpmModelRevisionsValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class OpmModelRevisionsValue  implements java.io.Serializable {
    private java.lang.Object[] addedOpmModelRevisions;
    private java.util.Calendar creationTime;
    private java.lang.String description;
    private java.lang.Boolean enabled;
    private java.lang.Object[] onceAddedOpmModelRevisions;
    private java.lang.String opmModelID;
    private java.lang.String opmModelName;
    private org.objectprocess.SoapClient.MetaRevisionValue[] opmModelRevisions;
    private java.lang.String primaryKey;
    private java.lang.Object[] removedOpmModelRevisions;
    private java.lang.Integer totalCollaborativeTime;
    private java.lang.Object[] updatedOpmModelRevisions;
    private java.lang.String workgroupID;

    public OpmModelRevisionsValue() {
    }

    public java.lang.Object[] getAddedOpmModelRevisions() {
        return addedOpmModelRevisions;
    }

    public void setAddedOpmModelRevisions(java.lang.Object[] addedOpmModelRevisions) {
        this.addedOpmModelRevisions = addedOpmModelRevisions;
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

    public java.lang.Object[] getOnceAddedOpmModelRevisions() {
        return onceAddedOpmModelRevisions;
    }

    public void setOnceAddedOpmModelRevisions(java.lang.Object[] onceAddedOpmModelRevisions) {
        this.onceAddedOpmModelRevisions = onceAddedOpmModelRevisions;
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

    public org.objectprocess.SoapClient.MetaRevisionValue[] getOpmModelRevisions() {
        return opmModelRevisions;
    }

    public void setOpmModelRevisions(org.objectprocess.SoapClient.MetaRevisionValue[] opmModelRevisions) {
        this.opmModelRevisions = opmModelRevisions;
    }

    public java.lang.String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(java.lang.String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.Object[] getRemovedOpmModelRevisions() {
        return removedOpmModelRevisions;
    }

    public void setRemovedOpmModelRevisions(java.lang.Object[] removedOpmModelRevisions) {
        this.removedOpmModelRevisions = removedOpmModelRevisions;
    }

    public java.lang.Integer getTotalCollaborativeTime() {
        return totalCollaborativeTime;
    }

    public void setTotalCollaborativeTime(java.lang.Integer totalCollaborativeTime) {
        this.totalCollaborativeTime = totalCollaborativeTime;
    }

    public java.lang.Object[] getUpdatedOpmModelRevisions() {
        return updatedOpmModelRevisions;
    }

    public void setUpdatedOpmModelRevisions(java.lang.Object[] updatedOpmModelRevisions) {
        this.updatedOpmModelRevisions = updatedOpmModelRevisions;
    }

    public java.lang.String getWorkgroupID() {
        return workgroupID;
    }

    public void setWorkgroupID(java.lang.String workgroupID) {
        this.workgroupID = workgroupID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OpmModelRevisionsValue)) return false;
        OpmModelRevisionsValue other = (OpmModelRevisionsValue) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addedOpmModelRevisions==null && other.getAddedOpmModelRevisions()==null) || 
             (this.addedOpmModelRevisions!=null &&
              java.util.Arrays.equals(this.addedOpmModelRevisions, other.getAddedOpmModelRevisions()))) &&
            ((this.creationTime==null && other.getCreationTime()==null) || 
             (this.creationTime!=null &&
              this.creationTime.equals(other.getCreationTime()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.enabled==null && other.getEnabled()==null) || 
             (this.enabled!=null &&
              this.enabled.equals(other.getEnabled()))) &&
            ((this.onceAddedOpmModelRevisions==null && other.getOnceAddedOpmModelRevisions()==null) || 
             (this.onceAddedOpmModelRevisions!=null &&
              java.util.Arrays.equals(this.onceAddedOpmModelRevisions, other.getOnceAddedOpmModelRevisions()))) &&
            ((this.opmModelID==null && other.getOpmModelID()==null) || 
             (this.opmModelID!=null &&
              this.opmModelID.equals(other.getOpmModelID()))) &&
            ((this.opmModelName==null && other.getOpmModelName()==null) || 
             (this.opmModelName!=null &&
              this.opmModelName.equals(other.getOpmModelName()))) &&
            ((this.opmModelRevisions==null && other.getOpmModelRevisions()==null) || 
             (this.opmModelRevisions!=null &&
              java.util.Arrays.equals(this.opmModelRevisions, other.getOpmModelRevisions()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            ((this.removedOpmModelRevisions==null && other.getRemovedOpmModelRevisions()==null) || 
             (this.removedOpmModelRevisions!=null &&
              java.util.Arrays.equals(this.removedOpmModelRevisions, other.getRemovedOpmModelRevisions()))) &&
            ((this.totalCollaborativeTime==null && other.getTotalCollaborativeTime()==null) || 
             (this.totalCollaborativeTime!=null &&
              this.totalCollaborativeTime.equals(other.getTotalCollaborativeTime()))) &&
            ((this.updatedOpmModelRevisions==null && other.getUpdatedOpmModelRevisions()==null) || 
             (this.updatedOpmModelRevisions!=null &&
              java.util.Arrays.equals(this.updatedOpmModelRevisions, other.getUpdatedOpmModelRevisions()))) &&
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
        if (getAddedOpmModelRevisions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAddedOpmModelRevisions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAddedOpmModelRevisions(), i);
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
        if (getOnceAddedOpmModelRevisions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOnceAddedOpmModelRevisions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOnceAddedOpmModelRevisions(), i);
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
        if (getOpmModelRevisions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOpmModelRevisions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOpmModelRevisions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        if (getRemovedOpmModelRevisions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRemovedOpmModelRevisions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRemovedOpmModelRevisions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTotalCollaborativeTime() != null) {
            _hashCode += getTotalCollaborativeTime().hashCode();
        }
        if (getUpdatedOpmModelRevisions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUpdatedOpmModelRevisions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUpdatedOpmModelRevisions(), i);
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
        new org.apache.axis.description.TypeDesc(OpmModelRevisionsValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "OpmModelRevisionsValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addedOpmModelRevisions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addedOpmModelRevisions"));
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
        elemField.setFieldName("onceAddedOpmModelRevisions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "onceAddedOpmModelRevisions"));
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
        elemField.setFieldName("opmModelRevisions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelRevisions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "MetaRevisionValue"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("removedOpmModelRevisions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "removedOpmModelRevisions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCollaborativeTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalCollaborativeTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatedOpmModelRevisions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updatedOpmModelRevisions"));
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
