/**
 * EnhancedCollaborativeSessionValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class EnhancedCollaborativeSessionValue  implements java.io.Serializable {
    private java.lang.Object[] addedCollaborativeSessionsPermissions;
    private java.lang.Integer collaborativeSessionID;
    private java.lang.String collaborativeSessionName;
    private org.objectprocess.SoapClient.Enhanced2CollaborativeSessionPermissionsValue[] collaborativeSessionsPermissions;
    private java.util.Calendar creationTime;
    private java.lang.String description;
    private java.lang.Boolean enabled;
    private java.util.Calendar lastUpdate;
    private java.lang.Integer majorRevision;
    private java.lang.Integer minorRevision;
    private java.lang.Object[] onceAddedCollaborativeSessionsPermissions;
    private java.lang.Integer opmModelID;
    private java.lang.Integer primaryKey;
    private java.lang.Object[] removedCollaborativeSessionsPermissions;
    private java.lang.Integer revisionID;
    private java.lang.Boolean terminated;
    private java.lang.Integer tokenHolderID;
    private java.lang.Integer tokenTimeout;
    private java.lang.Object[] updatedCollaborativeSessionsPermissions;
    private java.lang.Integer userTimeout;

    public EnhancedCollaborativeSessionValue() {
    }

    public java.lang.Object[] getAddedCollaborativeSessionsPermissions() {
        return addedCollaborativeSessionsPermissions;
    }

    public void setAddedCollaborativeSessionsPermissions(java.lang.Object[] addedCollaborativeSessionsPermissions) {
        this.addedCollaborativeSessionsPermissions = addedCollaborativeSessionsPermissions;
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

    public org.objectprocess.SoapClient.Enhanced2CollaborativeSessionPermissionsValue[] getCollaborativeSessionsPermissions() {
        return collaborativeSessionsPermissions;
    }

    public void setCollaborativeSessionsPermissions(org.objectprocess.SoapClient.Enhanced2CollaborativeSessionPermissionsValue[] collaborativeSessionsPermissions) {
        this.collaborativeSessionsPermissions = collaborativeSessionsPermissions;
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

    public java.util.Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(java.util.Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public java.lang.Integer getMajorRevision() {
        return majorRevision;
    }

    public void setMajorRevision(java.lang.Integer majorRevision) {
        this.majorRevision = majorRevision;
    }

    public java.lang.Integer getMinorRevision() {
        return minorRevision;
    }

    public void setMinorRevision(java.lang.Integer minorRevision) {
        this.minorRevision = minorRevision;
    }

    public java.lang.Object[] getOnceAddedCollaborativeSessionsPermissions() {
        return onceAddedCollaborativeSessionsPermissions;
    }

    public void setOnceAddedCollaborativeSessionsPermissions(java.lang.Object[] onceAddedCollaborativeSessionsPermissions) {
        this.onceAddedCollaborativeSessionsPermissions = onceAddedCollaborativeSessionsPermissions;
    }

    public java.lang.Integer getOpmModelID() {
        return opmModelID;
    }

    public void setOpmModelID(java.lang.Integer opmModelID) {
        this.opmModelID = opmModelID;
    }

    public java.lang.Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(java.lang.Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.Object[] getRemovedCollaborativeSessionsPermissions() {
        return removedCollaborativeSessionsPermissions;
    }

    public void setRemovedCollaborativeSessionsPermissions(java.lang.Object[] removedCollaborativeSessionsPermissions) {
        this.removedCollaborativeSessionsPermissions = removedCollaborativeSessionsPermissions;
    }

    public java.lang.Integer getRevisionID() {
        return revisionID;
    }

    public void setRevisionID(java.lang.Integer revisionID) {
        this.revisionID = revisionID;
    }

    public java.lang.Boolean getTerminated() {
        return terminated;
    }

    public void setTerminated(java.lang.Boolean terminated) {
        this.terminated = terminated;
    }

    public java.lang.Integer getTokenHolderID() {
        return tokenHolderID;
    }

    public void setTokenHolderID(java.lang.Integer tokenHolderID) {
        this.tokenHolderID = tokenHolderID;
    }

    public java.lang.Integer getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(java.lang.Integer tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    public java.lang.Object[] getUpdatedCollaborativeSessionsPermissions() {
        return updatedCollaborativeSessionsPermissions;
    }

    public void setUpdatedCollaborativeSessionsPermissions(java.lang.Object[] updatedCollaborativeSessionsPermissions) {
        this.updatedCollaborativeSessionsPermissions = updatedCollaborativeSessionsPermissions;
    }

    public java.lang.Integer getUserTimeout() {
        return userTimeout;
    }

    public void setUserTimeout(java.lang.Integer userTimeout) {
        this.userTimeout = userTimeout;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnhancedCollaborativeSessionValue)) return false;
        EnhancedCollaborativeSessionValue other = (EnhancedCollaborativeSessionValue) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addedCollaborativeSessionsPermissions==null && other.getAddedCollaborativeSessionsPermissions()==null) || 
             (this.addedCollaborativeSessionsPermissions!=null &&
              java.util.Arrays.equals(this.addedCollaborativeSessionsPermissions, other.getAddedCollaborativeSessionsPermissions()))) &&
            ((this.collaborativeSessionID==null && other.getCollaborativeSessionID()==null) || 
             (this.collaborativeSessionID!=null &&
              this.collaborativeSessionID.equals(other.getCollaborativeSessionID()))) &&
            ((this.collaborativeSessionName==null && other.getCollaborativeSessionName()==null) || 
             (this.collaborativeSessionName!=null &&
              this.collaborativeSessionName.equals(other.getCollaborativeSessionName()))) &&
            ((this.collaborativeSessionsPermissions==null && other.getCollaborativeSessionsPermissions()==null) || 
             (this.collaborativeSessionsPermissions!=null &&
              java.util.Arrays.equals(this.collaborativeSessionsPermissions, other.getCollaborativeSessionsPermissions()))) &&
            ((this.creationTime==null && other.getCreationTime()==null) || 
             (this.creationTime!=null &&
              this.creationTime.equals(other.getCreationTime()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.enabled==null && other.getEnabled()==null) || 
             (this.enabled!=null &&
              this.enabled.equals(other.getEnabled()))) &&
            ((this.lastUpdate==null && other.getLastUpdate()==null) || 
             (this.lastUpdate!=null &&
              this.lastUpdate.equals(other.getLastUpdate()))) &&
            ((this.majorRevision==null && other.getMajorRevision()==null) || 
             (this.majorRevision!=null &&
              this.majorRevision.equals(other.getMajorRevision()))) &&
            ((this.minorRevision==null && other.getMinorRevision()==null) || 
             (this.minorRevision!=null &&
              this.minorRevision.equals(other.getMinorRevision()))) &&
            ((this.onceAddedCollaborativeSessionsPermissions==null && other.getOnceAddedCollaborativeSessionsPermissions()==null) || 
             (this.onceAddedCollaborativeSessionsPermissions!=null &&
              java.util.Arrays.equals(this.onceAddedCollaborativeSessionsPermissions, other.getOnceAddedCollaborativeSessionsPermissions()))) &&
            ((this.opmModelID==null && other.getOpmModelID()==null) || 
             (this.opmModelID!=null &&
              this.opmModelID.equals(other.getOpmModelID()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            ((this.removedCollaborativeSessionsPermissions==null && other.getRemovedCollaborativeSessionsPermissions()==null) || 
             (this.removedCollaborativeSessionsPermissions!=null &&
              java.util.Arrays.equals(this.removedCollaborativeSessionsPermissions, other.getRemovedCollaborativeSessionsPermissions()))) &&
            ((this.revisionID==null && other.getRevisionID()==null) || 
             (this.revisionID!=null &&
              this.revisionID.equals(other.getRevisionID()))) &&
            ((this.terminated==null && other.getTerminated()==null) || 
             (this.terminated!=null &&
              this.terminated.equals(other.getTerminated()))) &&
            ((this.tokenHolderID==null && other.getTokenHolderID()==null) || 
             (this.tokenHolderID!=null &&
              this.tokenHolderID.equals(other.getTokenHolderID()))) &&
            ((this.tokenTimeout==null && other.getTokenTimeout()==null) || 
             (this.tokenTimeout!=null &&
              this.tokenTimeout.equals(other.getTokenTimeout()))) &&
            ((this.updatedCollaborativeSessionsPermissions==null && other.getUpdatedCollaborativeSessionsPermissions()==null) || 
             (this.updatedCollaborativeSessionsPermissions!=null &&
              java.util.Arrays.equals(this.updatedCollaborativeSessionsPermissions, other.getUpdatedCollaborativeSessionsPermissions()))) &&
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
        if (getAddedCollaborativeSessionsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAddedCollaborativeSessionsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAddedCollaborativeSessionsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCollaborativeSessionID() != null) {
            _hashCode += getCollaborativeSessionID().hashCode();
        }
        if (getCollaborativeSessionName() != null) {
            _hashCode += getCollaborativeSessionName().hashCode();
        }
        if (getCollaborativeSessionsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCollaborativeSessionsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCollaborativeSessionsPermissions(), i);
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
        if (getLastUpdate() != null) {
            _hashCode += getLastUpdate().hashCode();
        }
        if (getMajorRevision() != null) {
            _hashCode += getMajorRevision().hashCode();
        }
        if (getMinorRevision() != null) {
            _hashCode += getMinorRevision().hashCode();
        }
        if (getOnceAddedCollaborativeSessionsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOnceAddedCollaborativeSessionsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOnceAddedCollaborativeSessionsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOpmModelID() != null) {
            _hashCode += getOpmModelID().hashCode();
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        if (getRemovedCollaborativeSessionsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRemovedCollaborativeSessionsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRemovedCollaborativeSessionsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRevisionID() != null) {
            _hashCode += getRevisionID().hashCode();
        }
        if (getTerminated() != null) {
            _hashCode += getTerminated().hashCode();
        }
        if (getTokenHolderID() != null) {
            _hashCode += getTokenHolderID().hashCode();
        }
        if (getTokenTimeout() != null) {
            _hashCode += getTokenTimeout().hashCode();
        }
        if (getUpdatedCollaborativeSessionsPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUpdatedCollaborativeSessionsPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUpdatedCollaborativeSessionsPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUserTimeout() != null) {
            _hashCode += getUserTimeout().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EnhancedCollaborativeSessionValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "EnhancedCollaborativeSessionValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addedCollaborativeSessionsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addedCollaborativeSessionsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("collaborativeSessionsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "collaborativeSessionsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "Enhanced2CollaborativeSessionPermissionsValue"));
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
        elemField.setFieldName("lastUpdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastUpdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("majorRevision");
        elemField.setXmlName(new javax.xml.namespace.QName("", "majorRevision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minorRevision");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minorRevision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onceAddedCollaborativeSessionsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "onceAddedCollaborativeSessionsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opmModelID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("removedCollaborativeSessionsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "removedCollaborativeSessionsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revisionID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revisionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminated");
        elemField.setXmlName(new javax.xml.namespace.QName("", "terminated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tokenHolderID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tokenHolderID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tokenTimeout");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tokenTimeout"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatedCollaborativeSessionsPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updatedCollaborativeSessionsPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
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
