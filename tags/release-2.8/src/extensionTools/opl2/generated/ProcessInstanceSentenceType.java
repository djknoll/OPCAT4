//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated;


/**
 * Java content class for annonymous complex type.
 *  <p>The following schema fragment specifies the expected content contained within this java content object.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="ProcessName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element ref="{}Role" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="InstanceFatherName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="InstanceFatherRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ProcessInstanceSentenceType {


    java.lang.String getProcessName();

    void setProcessName(java.lang.String value);

    java.lang.String getInstanceFatherName();

    void setInstanceFatherName(java.lang.String value);

    java.lang.String getInstanceFatherRole();

    void setInstanceFatherRole(java.lang.String value);

    java.util.List getRole();

}
