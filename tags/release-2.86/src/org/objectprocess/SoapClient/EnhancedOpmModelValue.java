/**
 * EnhancedOpmModelValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class EnhancedOpmModelValue implements java.io.Serializable {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	private java.lang.Object[] addedOpmModelsPermissions;

	private java.util.Calendar creationTime;

	private java.lang.String description;

	private java.lang.Boolean enabled;

	private java.lang.Object[] onceAddedOpmModelsPermissions;

	private java.lang.Integer opmModelID;

	private java.lang.String opmModelName;

	private org.objectprocess.SoapClient.Enhanced2OpmModelPermissionsValue[] opmModelsPermissions;

	private java.lang.Integer primaryKey;

	private java.lang.Object[] removedOpmModelsPermissions;

	private java.lang.Integer totalCollaborativeTime;

	private java.lang.Object[] updatedOpmModelsPermissions;

	private java.lang.Integer workgroupID;

	public EnhancedOpmModelValue() {
	}

	public java.lang.Object[] getAddedOpmModelsPermissions() {
		return this.addedOpmModelsPermissions;
	}

	public void setAddedOpmModelsPermissions(
			java.lang.Object[] addedOpmModelsPermissions) {
		this.addedOpmModelsPermissions = addedOpmModelsPermissions;
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

	public java.lang.Object[] getOnceAddedOpmModelsPermissions() {
		return this.onceAddedOpmModelsPermissions;
	}

	public void setOnceAddedOpmModelsPermissions(
			java.lang.Object[] onceAddedOpmModelsPermissions) {
		this.onceAddedOpmModelsPermissions = onceAddedOpmModelsPermissions;
	}

	public java.lang.Integer getOpmModelID() {
		return this.opmModelID;
	}

	public void setOpmModelID(java.lang.Integer opmModelID) {
		this.opmModelID = opmModelID;
	}

	public java.lang.String getOpmModelName() {
		return this.opmModelName;
	}

	public void setOpmModelName(java.lang.String opmModelName) {
		this.opmModelName = opmModelName;
	}

	public org.objectprocess.SoapClient.Enhanced2OpmModelPermissionsValue[] getOpmModelsPermissions() {
		return this.opmModelsPermissions;
	}

	public void setOpmModelsPermissions(
			org.objectprocess.SoapClient.Enhanced2OpmModelPermissionsValue[] opmModelsPermissions) {
		this.opmModelsPermissions = opmModelsPermissions;
	}

	public java.lang.Integer getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(java.lang.Integer primaryKey) {
		this.primaryKey = primaryKey;
	}

	public java.lang.Object[] getRemovedOpmModelsPermissions() {
		return this.removedOpmModelsPermissions;
	}

	public void setRemovedOpmModelsPermissions(
			java.lang.Object[] removedOpmModelsPermissions) {
		this.removedOpmModelsPermissions = removedOpmModelsPermissions;
	}

	public java.lang.Integer getTotalCollaborativeTime() {
		return this.totalCollaborativeTime;
	}

	public void setTotalCollaborativeTime(
			java.lang.Integer totalCollaborativeTime) {
		this.totalCollaborativeTime = totalCollaborativeTime;
	}

	public java.lang.Object[] getUpdatedOpmModelsPermissions() {
		return this.updatedOpmModelsPermissions;
	}

	public void setUpdatedOpmModelsPermissions(
			java.lang.Object[] updatedOpmModelsPermissions) {
		this.updatedOpmModelsPermissions = updatedOpmModelsPermissions;
	}

	public java.lang.Integer getWorkgroupID() {
		return this.workgroupID;
	}

	public void setWorkgroupID(java.lang.Integer workgroupID) {
		this.workgroupID = workgroupID;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof EnhancedOpmModelValue)) {
			return false;
		}
		EnhancedOpmModelValue other = (EnhancedOpmModelValue) obj;
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
		_equals = true
				&& (((this.addedOpmModelsPermissions == null) && (other
						.getAddedOpmModelsPermissions() == null)) || ((this.addedOpmModelsPermissions != null) && java.util.Arrays
						.equals(this.addedOpmModelsPermissions, other
								.getAddedOpmModelsPermissions())))
				&& (((this.creationTime == null) && (other.getCreationTime() == null)) || ((this.creationTime != null) && this.creationTime
						.equals(other.getCreationTime())))
				&& (((this.description == null) && (other.getDescription() == null)) || ((this.description != null) && this.description
						.equals(other.getDescription())))
				&& (((this.enabled == null) && (other.getEnabled() == null)) || ((this.enabled != null) && this.enabled
						.equals(other.getEnabled())))
				&& (((this.onceAddedOpmModelsPermissions == null) && (other
						.getOnceAddedOpmModelsPermissions() == null)) || ((this.onceAddedOpmModelsPermissions != null) && java.util.Arrays
						.equals(this.onceAddedOpmModelsPermissions, other
								.getOnceAddedOpmModelsPermissions())))
				&& (((this.opmModelID == null) && (other.getOpmModelID() == null)) || ((this.opmModelID != null) && this.opmModelID
						.equals(other.getOpmModelID())))
				&& (((this.opmModelName == null) && (other.getOpmModelName() == null)) || ((this.opmModelName != null) && this.opmModelName
						.equals(other.getOpmModelName())))
				&& (((this.opmModelsPermissions == null) && (other
						.getOpmModelsPermissions() == null)) || ((this.opmModelsPermissions != null) && java.util.Arrays
						.equals(this.opmModelsPermissions, other
								.getOpmModelsPermissions())))
				&& (((this.primaryKey == null) && (other.getPrimaryKey() == null)) || ((this.primaryKey != null) && this.primaryKey
						.equals(other.getPrimaryKey())))
				&& (((this.removedOpmModelsPermissions == null) && (other
						.getRemovedOpmModelsPermissions() == null)) || ((this.removedOpmModelsPermissions != null) && java.util.Arrays
						.equals(this.removedOpmModelsPermissions, other
								.getRemovedOpmModelsPermissions())))
				&& (((this.totalCollaborativeTime == null) && (other
						.getTotalCollaborativeTime() == null)) || ((this.totalCollaborativeTime != null) && this.totalCollaborativeTime
						.equals(other.getTotalCollaborativeTime())))
				&& (((this.updatedOpmModelsPermissions == null) && (other
						.getUpdatedOpmModelsPermissions() == null)) || ((this.updatedOpmModelsPermissions != null) && java.util.Arrays
						.equals(this.updatedOpmModelsPermissions, other
								.getUpdatedOpmModelsPermissions())))
				&& (((this.workgroupID == null) && (other.getWorkgroupID() == null)) || ((this.workgroupID != null) && this.workgroupID
						.equals(other.getWorkgroupID())));
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
		if (this.getAddedOpmModelsPermissions() != null) {
			for (int i = 0; i < java.lang.reflect.Array
					.getLength(this.getAddedOpmModelsPermissions()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(
						this.getAddedOpmModelsPermissions(), i);
				if ((obj != null) && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
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
		if (this.getOnceAddedOpmModelsPermissions() != null) {
			for (int i = 0; i < java.lang.reflect.Array
					.getLength(this.getOnceAddedOpmModelsPermissions()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(
						this.getOnceAddedOpmModelsPermissions(), i);
				if ((obj != null) && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (this.getOpmModelID() != null) {
			_hashCode += this.getOpmModelID().hashCode();
		}
		if (this.getOpmModelName() != null) {
			_hashCode += this.getOpmModelName().hashCode();
		}
		if (this.getOpmModelsPermissions() != null) {
			for (int i = 0; i < java.lang.reflect.Array
					.getLength(this.getOpmModelsPermissions()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(
						this.getOpmModelsPermissions(), i);
				if ((obj != null) && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (this.getPrimaryKey() != null) {
			_hashCode += this.getPrimaryKey().hashCode();
		}
		if (this.getRemovedOpmModelsPermissions() != null) {
			for (int i = 0; i < java.lang.reflect.Array
					.getLength(this.getRemovedOpmModelsPermissions()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(
						this.getRemovedOpmModelsPermissions(), i);
				if ((obj != null) && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (this.getTotalCollaborativeTime() != null) {
			_hashCode += this.getTotalCollaborativeTime().hashCode();
		}
		if (this.getUpdatedOpmModelsPermissions() != null) {
			for (int i = 0; i < java.lang.reflect.Array
					.getLength(this.getUpdatedOpmModelsPermissions()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(
						this.getUpdatedOpmModelsPermissions(), i);
				if ((obj != null) && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (this.getWorkgroupID() != null) {
			_hashCode += this.getWorkgroupID().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			EnhancedOpmModelValue.class);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://org/objectprocess/cmp", "EnhancedOpmModelValue"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("addedOpmModelsPermissions");
		elemField.setXmlName(new javax.xml.namespace.QName("",
				"addedOpmModelsPermissions"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "Array"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("creationTime");
		elemField.setXmlName(new javax.xml.namespace.QName("", "creationTime"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "dateTime"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("description");
		elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("enabled");
		elemField.setXmlName(new javax.xml.namespace.QName("", "enabled"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("onceAddedOpmModelsPermissions");
		elemField.setXmlName(new javax.xml.namespace.QName("",
				"onceAddedOpmModelsPermissions"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "Array"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("opmModelID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelID"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "int"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("opmModelName");
		elemField.setXmlName(new javax.xml.namespace.QName("", "opmModelName"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("opmModelsPermissions");
		elemField.setXmlName(new javax.xml.namespace.QName("",
				"opmModelsPermissions"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://org/objectprocess/cmp",
				"Enhanced2OpmModelPermissionsValue"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("primaryKey");
		elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "int"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("removedOpmModelsPermissions");
		elemField.setXmlName(new javax.xml.namespace.QName("",
				"removedOpmModelsPermissions"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "Array"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("totalCollaborativeTime");
		elemField.setXmlName(new javax.xml.namespace.QName("",
				"totalCollaborativeTime"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "int"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("updatedOpmModelsPermissions");
		elemField.setXmlName(new javax.xml.namespace.QName("",
				"updatedOpmModelsPermissions"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "Array"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("workgroupID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "workgroupID"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "int"));
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
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
				_xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
				_xmlType, typeDesc);
	}

}
