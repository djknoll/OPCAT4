//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated;


/**
 * Java content class for OPLscript element declaration.
 *  <p>The following schema fragment specifies the expected content contained within this java content object.
 * <p>
 * <pre>
 * &lt;element name="OPLscript">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{}ThingSentenceSet" maxOccurs="unbounded"/>
 *           &lt;element name="SystemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;attribute name="systemName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface OPLscript
    extends javax.xml.bind.Element, extensionTools.opl2.generated.OPLscriptType
{


}