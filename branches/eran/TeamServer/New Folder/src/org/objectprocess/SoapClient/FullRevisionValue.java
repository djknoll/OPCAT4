/**
 * FullRevisionValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class FullRevisionValue  implements java.io.Serializable {
    private java.lang.Integer build;
    private java.lang.String comitterID;
    private java.util.Calendar creationTime;
    private java.lang.String description;
    private java.lang.Boolean enabled;
    private java.lang.Integer majorRevision;
    private java.lang.Integer minorRevision;
    private org.objectprocess.SoapClient.OpmModelValue opmModel;
    private java.lang.String opmModelFile;
    private java.lang.String opmModelID;
    private java.lang.String primaryKey;
    private java.lang.String revisionID;

    public FullRevisionValue() {
    }

    public java.lang.Integer getBuild() {
        return build;
    }

    public void setBuild(java.lang.Integer build) {
        this.build = build;
    }

    public java.lang.String getComitterID() {
        return comitterID;
    }

    public void setComitterID(java.lang.String comitterID) {
        this.comitterID = comitterID;
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

    public org.objectprocess.SoapClient.OpmModelValue getOpmModel() {
        return opmModel;
    }

    public void setOpmModel(org.objectprocess.SoapClient.OpmModelValue opmModel) {
        this.opmModel = opmModel;
    }

    public java.lang.String getOpmModelFile() {
        return opmModelFile;
    }

    public void setOpmModelFile(java.lang.String opmModelFile) {
        this.opmModelFile = opmModelFile;
    }

    public java.lang.String getOpmModelID() {
        return opmModelID;
    }

    public void setOpmModelID(java.lang.String opmModelID) {
        this.opmModelID = opmModelID;
    }

    public java.lang.String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(java.lang.String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.String getRevisionID() {
        return revisionID;
    }

    public void setRevisionID(java.lang.String revisionID) {
        this.revisionID = revisionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FullRevisionValue)) return false;
        FullRevisionValue other = (FullRevisionValue) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.build==null && other.getBuild()==null) || 
             (this.build!=null &&
              this.build.equals(other.getBuild()))) &&
            ((this.comitterID==null && other.getComitterID()==null) || 
             (this.comitterID!=null &&
              this.comitterID.equals(other.getComitterID()))) &&
            ((this.creationTime==null && other.getCreationTime()==null) || 
             (this.creationTime!=null &&
              this.creationTime.equals(other.getCreationTime()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.enabled==null && other.getEnabled()==null) || 
             (this.enabled!=null &&
              this.enabled.equals(other.getEnabled()))) &&
            ((this.majorRevision==null && other.getMajorRevision()==null) || 
             (this.majorRevision!=null &&
              this.majorRevision.equals(other.getMajorRevision()))) &&
            ((this.minorRevision==null && other.getMinorRevision()==null) || 
             (this.minorRevision!=null &&
              this.minorRevision.equals(other.getMinorRevision()))) &&
            ((this.opmModel==null && other.getOpmModel()==null) || 
             (this.opmModel!=null &&
              this.opmModel.equals(other.getOpmModel()))) &&
            ((this.opmModelFile==null && other.getOpmModelFile()==null) || 
             (this.opmModelFile!=null &&
              this.opmModelFile.equals(other.getOpmModelFile()))) &&
            ((this.opmModelID==null && other.getOpmModelID()==null) || 
             (this.opmModelID!=null &&
              this.opmModelID.equals(other.getOpmModelID()))) &&
            ((this.primaryKey==null && other.getPrimaryKey()==null) || 
             (this.primaryKey!=null &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            ((this.revisionID==null && other.getRevisionID()==null) || 
             (this.revisionID!=null &&
              this.revisionID.equals(other.getRevisionID())));
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
        if (getBuild() != null) {
            _hashCode += getBuild().hashCode();
        }
        if (getComitterID() != null) {
            _hashCode += getComitterID().hashCode();
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
        if (getMajorRevision() != null) {
            _hashCode += getMajorRevision().hashCode();
        }
        if (getMinorRevision() != null) {
            _hashCode += getMinorRevision().hashCode();
        }
        if (getOpmModel() != null) {
            _hashCode += getOpmModel().hashCode();
        }
        if (getOpmModelFile() != null) {
            _hashCode += getOpmModelFile().hashCode();
        }
        if (getOpmModelID() != null) {
            _hashCode += getOpmModelID().hashCode();
        }
        if (getPrimaryKey() != null) {
            _hashCode += getPrimaryKey().hashCode();
        }
        if (getRevisionID() != null) {
            _hashCode += getRevisionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FullRevisionValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "FullRevisionValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("build");
        elemField.setXmlName(new javax.xml.namespace.QName("", "build"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comitterID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comitterID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("opmModel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "OpmModelValue"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opmModelFile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opmModelID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revisionID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revisionID"));
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
