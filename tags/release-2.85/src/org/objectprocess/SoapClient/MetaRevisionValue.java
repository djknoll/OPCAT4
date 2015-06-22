/**
 * MetaRevisionValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class MetaRevisionValue  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	private java.lang.Integer comitterID;
    private java.util.Calendar creationTime;
    private java.lang.String description;
    private java.lang.Boolean enabled;
    private java.lang.Integer majorRevision;
    private java.lang.Integer minorRevision;
    private java.lang.Integer opmModelID;
    private java.lang.Integer primaryKey;
    private java.lang.Integer revisionID;

    public MetaRevisionValue() {
    }

    public java.lang.Integer getComitterID() {
        return this.comitterID;
    }

    public void setComitterID(java.lang.Integer comitterID) {
        this.comitterID = comitterID;
    }

    public java.util.Calendar getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(java.util.Calendar creationTime) {
        this.creationTime = creationTime;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(java.lang.Boolean enabled) {
        this.enabled = enabled;
    }

    public java.lang.Integer getMajorRevision() {
        return this.majorRevision;
    }

    public void setMajorRevision(java.lang.Integer majorRevision) {
        this.majorRevision = majorRevision;
    }

    public java.lang.Integer getMinorRevision() {
        return this.minorRevision;
    }

    public void setMinorRevision(java.lang.Integer minorRevision) {
        this.minorRevision = minorRevision;
    }

    public java.lang.Integer getOpmModelID() {
        return this.opmModelID;
    }

    public void setOpmModelID(java.lang.Integer opmModelID) {
        this.opmModelID = opmModelID;
    }

    public java.lang.Integer getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(java.lang.Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.Integer getRevisionID() {
        return this.revisionID;
    }

    public void setRevisionID(java.lang.Integer revisionID) {
        this.revisionID = revisionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MetaRevisionValue)) {
			return false;
		}
        MetaRevisionValue other = (MetaRevisionValue) obj;
        if (obj == null) {
			return false;
		}
        if (this == obj) {
			return true;
		}
        if (this.__equalsCalc != null) {
            return (this.__equalsCalc == obj);
        }
        this.__equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            (((this.comitterID==null) && (other.getComitterID()==null)) || 
             ((this.comitterID!=null) &&
              this.comitterID.equals(other.getComitterID()))) &&
            (((this.creationTime==null) && (other.getCreationTime()==null)) || 
             ((this.creationTime!=null) &&
              this.creationTime.equals(other.getCreationTime()))) &&
            (((this.description==null) && (other.getDescription()==null)) || 
             ((this.description!=null) &&
              this.description.equals(other.getDescription()))) &&
            (((this.enabled==null) && (other.getEnabled()==null)) || 
             ((this.enabled!=null) &&
              this.enabled.equals(other.getEnabled()))) &&
            (((this.majorRevision==null) && (other.getMajorRevision()==null)) || 
             ((this.majorRevision!=null) &&
              this.majorRevision.equals(other.getMajorRevision()))) &&
            (((this.minorRevision==null) && (other.getMinorRevision()==null)) || 
             ((this.minorRevision!=null) &&
              this.minorRevision.equals(other.getMinorRevision()))) &&
            (((this.opmModelID==null) && (other.getOpmModelID()==null)) || 
             ((this.opmModelID!=null) &&
              this.opmModelID.equals(other.getOpmModelID()))) &&
            (((this.primaryKey==null) && (other.getPrimaryKey()==null)) || 
             ((this.primaryKey!=null) &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            (((this.revisionID==null) && (other.getRevisionID()==null)) || 
             ((this.revisionID!=null) &&
              this.revisionID.equals(other.getRevisionID())));
        this.__equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (this.getComitterID() != null) {
            _hashCode += this.getComitterID().hashCode();
        }
        if (this.getCreationTime() != null) {
            _hashCode += this.getCreationTime().hashCode();
        }
        if (this.getDescription() != null) {
            _hashCode += this.getDescription().hashCode();
        }
        if (this.getEnabled() != null) {
            _hashCode += this.getEnabled().hashCode();
        }
        if (this.getMajorRevision() != null) {
            _hashCode += this.getMajorRevision().hashCode();
        }
        if (this.getMinorRevision() != null) {
            _hashCode += this.getMinorRevision().hashCode();
        }
        if (this.getOpmModelID() != null) {
            _hashCode += this.getOpmModelID().hashCode();
        }
        if (this.getPrimaryKey() != null) {
            _hashCode += this.getPrimaryKey().hashCode();
        }
        if (this.getRevisionID() != null) {
            _hashCode += this.getRevisionID().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MetaRevisionValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "MetaRevisionValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comitterID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comitterID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
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
        elemField.setFieldName("revisionID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revisionID"));
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
