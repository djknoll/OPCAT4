/**
 * EditableUserValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.objectprocess.SoapClient;

public class EditableUserValue  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	private java.lang.Boolean administrator;
    private java.lang.String description;
    private java.lang.String email;
    private java.lang.String firstName;
    private java.lang.String lastName;
    private java.lang.String loginName;
    private java.lang.String password;
    private java.lang.Integer primaryKey;
    private java.lang.Integer userID;

    public EditableUserValue() {
    }

    public java.lang.Boolean getAdministrator() {
        return this.administrator;
    }

    public void setAdministrator(java.lang.Boolean administrator) {
        this.administrator = administrator;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getEmail() {
        return this.email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public java.lang.String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    public java.lang.String getLastName() {
        return this.lastName;
    }

    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

    public java.lang.String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(java.lang.String loginName) {
        this.loginName = loginName;
    }

    public java.lang.String getPassword() {
        return this.password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public java.lang.Integer getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(java.lang.Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public java.lang.Integer getUserID() {
        return this.userID;
    }

    public void setUserID(java.lang.Integer userID) {
        this.userID = userID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EditableUserValue)) {
			return false;
		}
        EditableUserValue other = (EditableUserValue) obj;
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
            (((this.administrator==null) && (other.getAdministrator()==null)) || 
             ((this.administrator!=null) &&
              this.administrator.equals(other.getAdministrator()))) &&
            (((this.description==null) && (other.getDescription()==null)) || 
             ((this.description!=null) &&
              this.description.equals(other.getDescription()))) &&
            (((this.email==null) && (other.getEmail()==null)) || 
             ((this.email!=null) &&
              this.email.equals(other.getEmail()))) &&
            (((this.firstName==null) && (other.getFirstName()==null)) || 
             ((this.firstName!=null) &&
              this.firstName.equals(other.getFirstName()))) &&
            (((this.lastName==null) && (other.getLastName()==null)) || 
             ((this.lastName!=null) &&
              this.lastName.equals(other.getLastName()))) &&
            (((this.loginName==null) && (other.getLoginName()==null)) || 
             ((this.loginName!=null) &&
              this.loginName.equals(other.getLoginName()))) &&
            (((this.password==null) && (other.getPassword()==null)) || 
             ((this.password!=null) &&
              this.password.equals(other.getPassword()))) &&
            (((this.primaryKey==null) && (other.getPrimaryKey()==null)) || 
             ((this.primaryKey!=null) &&
              this.primaryKey.equals(other.getPrimaryKey()))) &&
            (((this.userID==null) && (other.getUserID()==null)) || 
             ((this.userID!=null) &&
              this.userID.equals(other.getUserID())));
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
        if (this.getAdministrator() != null) {
            _hashCode += this.getAdministrator().hashCode();
        }
        if (this.getDescription() != null) {
            _hashCode += this.getDescription().hashCode();
        }
        if (this.getEmail() != null) {
            _hashCode += this.getEmail().hashCode();
        }
        if (this.getFirstName() != null) {
            _hashCode += this.getFirstName().hashCode();
        }
        if (this.getLastName() != null) {
            _hashCode += this.getLastName().hashCode();
        }
        if (this.getLoginName() != null) {
            _hashCode += this.getLoginName().hashCode();
        }
        if (this.getPassword() != null) {
            _hashCode += this.getPassword().hashCode();
        }
        if (this.getPrimaryKey() != null) {
            _hashCode += this.getPrimaryKey().hashCode();
        }
        if (this.getUserID() != null) {
            _hashCode += this.getUserID().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EditableUserValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://org/objectprocess/cmp", "EditableUserValue"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("administrator");
        elemField.setXmlName(new javax.xml.namespace.QName("", "administrator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userID"));
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
