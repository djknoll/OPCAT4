/**
 * EnhancedWorkgroupPermissionsValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class EnhancedWorkgroupPermissionsValue implements java.io.Serializable {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	private java.lang.Integer accessControl;

	private java.util.Calendar joinTime;

	private org.objectprocess.SoapClient.WorkgroupPermissionsPK primaryKey;

	private java.lang.Integer userID;

	private org.objectprocess.SoapClient.WorkgroupValue workgroup;

	private java.lang.Integer workgroupID;

	public EnhancedWorkgroupPermissionsValue() {
	}

	public java.lang.Integer getAccessControl() {
		return this.accessControl;
	}

	public void setAccessControl(java.lang.Integer accessControl) {
		this.accessControl = accessControl;
	}

	public java.util.Calendar getJoinTime() {
		return this.joinTime;
	}

	public void setJoinTime(java.util.Calendar joinTime) {
		this.joinTime = joinTime;
	}

	public org.objectprocess.SoapClient.WorkgroupPermissionsPK getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(
			org.objectprocess.SoapClient.WorkgroupPermissionsPK primaryKey) {
		this.primaryKey = primaryKey;
	}

	public java.lang.Integer getUserID() {
		return this.userID;
	}

	public void setUserID(java.lang.Integer userID) {
		this.userID = userID;
	}

	public org.objectprocess.SoapClient.WorkgroupValue getWorkgroup() {
		return this.workgroup;
	}

	public void setWorkgroup(
			org.objectprocess.SoapClient.WorkgroupValue workgroup) {
		this.workgroup = workgroup;
	}

	public java.lang.Integer getWorkgroupID() {
		return this.workgroupID;
	}

	public void setWorkgroupID(java.lang.Integer workgroupID) {
		this.workgroupID = workgroupID;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof EnhancedWorkgroupPermissionsValue)) {
			return false;
		}
		EnhancedWorkgroupPermissionsValue other = (EnhancedWorkgroupPermissionsValue) obj;
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
				&& (((this.accessControl == null) && (other.getAccessControl() == null)) || ((this.accessControl != null) && this.accessControl
						.equals(other.getAccessControl())))
				&& (((this.joinTime == null) && (other.getJoinTime() == null)) || ((this.joinTime != null) && this.joinTime
						.equals(other.getJoinTime())))
				&& (((this.primaryKey == null) && (other.getPrimaryKey() == null)) || ((this.primaryKey != null) && this.primaryKey
						.equals(other.getPrimaryKey())))
				&& (((this.userID == null) && (other.getUserID() == null)) || ((this.userID != null) && this.userID
						.equals(other.getUserID())))
				&& (((this.workgroup == null) && (other.getWorkgroup() == null)) || ((this.workgroup != null) && this.workgroup
						.equals(other.getWorkgroup())))
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
		if (this.getAccessControl() != null) {
			_hashCode += this.getAccessControl().hashCode();
		}
		if (this.getJoinTime() != null) {
			_hashCode += this.getJoinTime().hashCode();
		}
		if (this.getPrimaryKey() != null) {
			_hashCode += this.getPrimaryKey().hashCode();
		}
		if (this.getUserID() != null) {
			_hashCode += this.getUserID().hashCode();
		}
		if (this.getWorkgroup() != null) {
			_hashCode += this.getWorkgroup().hashCode();
		}
		if (this.getWorkgroupID() != null) {
			_hashCode += this.getWorkgroupID().hashCode();
		}
		this.__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			EnhancedWorkgroupPermissionsValue.class);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://org/objectprocess/cmp",
				"EnhancedWorkgroupPermissionsValue"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("accessControl");
		elemField
				.setXmlName(new javax.xml.namespace.QName("", "accessControl"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "int"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("joinTime");
		elemField.setXmlName(new javax.xml.namespace.QName("", "joinTime"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "dateTime"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("primaryKey");
		elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://org/objectprocess/cmp", "WorkgroupPermissionsPK"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("userID");
		elemField.setXmlName(new javax.xml.namespace.QName("", "userID"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "int"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("workgroup");
		elemField.setXmlName(new javax.xml.namespace.QName("", "workgroup"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://org/objectprocess/cmp", "WorkgroupValue"));
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
