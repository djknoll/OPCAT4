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
 *       &lt;sequence>
 *         &lt;element name="SourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}SourceRole" minOccurs="0"/>
 *         &lt;element name="RelationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DestinationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}DestinationRole" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ProcessUniDirectionalRelationSentenceType {


    java.lang.String getRelationName();

    void setRelationName(java.lang.String value);

    extensionTools.opl2.generated.DestinationRoleType getDestinationRole();

    void setDestinationRole(extensionTools.opl2.generated.DestinationRoleType value);

    java.lang.String getSourceName();

    void setSourceName(java.lang.String value);

    java.lang.String getDestinationName();

    void setDestinationName(java.lang.String value);

    extensionTools.opl2.generated.SourceRoleType getSourceRole();

    void setSourceRole(extensionTools.opl2.generated.SourceRoleType value);

}
