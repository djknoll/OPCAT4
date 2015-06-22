/**
 * WorkgroupValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class WorkgroupValue  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	private java.util.Calendar creationTime;
    private java.lang.String description;
    private java.lang.Boolean enabled;
    private java.lang.Integer primaryKey;
    private java.lang.Integer workgroupID;
    private java.lang.String workgroupName;

    public WorkgroupValue() {
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

    public java.lang.Integer getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(java.lang.Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.Integer getWorkgroupID() {
        return this.workgroupID;
    }

    public void setWorkgroupID(java.lang.Integer workgroupID) {
        this.workgroupID = workgroupID;
    }

    public java.lang.String getWorkgroupName() {
        return this.workgroupName;
    }

    public void setWorkgroupName(java.lang.String workgroupName) {
        this.workgroupName = workgroupName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WorkgroupValue)) {
			return false;
		}
        WorkgroupValue other = (WorkgroupValue) obj;
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
            (((this.creationTime==null) && (other.getCreationTime()==null)) || 
             ((this.creationTime!=null) &&
              this.creationTime.equals(other.getCreationTime()))) &&
            (((this.description==null) && (other.getDescription()==null)) || 
             ((this.description!=null) &&
              this.description.equals(other.getDescription()))) &&
            (((this.enabled==null) && (other.getEnabled()==null)) || 
             ((this.enabled!=null) &&
              this.enabled.equals(other.getEnabled()))) &&
            (((this.primaryKey==null) && (other.getPrimaryKey()==null)) || 
             ((this.primaryKey!=null) &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            (((this.workgroupID==null) && (other.getWorkgroupID()==null)) || 
             ((this.workgroupID!=null) &&
              this.workgroupID.equals(other.getWorkgroupID()))) &&
            (((this.workgroupName==null) && (other.getWorkgroupName()==null)) || 
             ((this.workgroupName!=null) &&
              this.workgroupName.equals(other.getWorkgroupName())));
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
        if (this.getCreationTime() != null) {
            _hashCode += this.getCreationTime().hashCode();
        }
        if (this.getDescription() != null) {
            _hashCode += this.getDescription().hashCode();
        }
        if (this.getEnabled() != null) {
            _hashCode += this.getEnabled().hashCode();
        }
        if (this.getPrimaryKey() != null) {
            _hashCode += this.getPrimaryKey().hashCode();
        }
        if (this.getWorkgroupID() != null) {
            _hashCode += this.getWorkgroupID().hashCode();
        }
        if (this.getWorkgroupName() != null) {
            _hashCode += this.getWorkgroupName().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WorkgroupValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "WorkgroupValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workgroupID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workgroupID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workgroupName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workgroupName"));
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
