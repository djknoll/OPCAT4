//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/raanan/jwsdp-1.6/jaxb/bin/opl.xml line 282)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SourceMinimalCardinality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SourceMaximalCardinality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}DestinationRole" minOccurs="0"/>
 *         &lt;element name="DestinationMinimalCardinality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DestinationMaximalCardinality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DestinationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}SourceRole" minOccurs="0"/>
 *         &lt;element name="RelationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ObjectBiDirectionalRelationSentenceType {


    /**
     * Gets the value of the relationName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getRelationName();

    /**
     * Sets the value of the relationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setRelationName(java.lang.String value);

    /**
     * Gets the value of the destinationMaximalCardinality property.
     * 
     */
    int getDestinationMaximalCardinality();

    /**
     * Sets the value of the destinationMaximalCardinality property.
     * 
     */
    void setDestinationMaximalCardinality(int value);

    /**
     * Gets the value of the sourceMaximalCardinality property.
     * 
     */
    int getSourceMaximalCardinality();

    /**
     * Sets the value of the sourceMaximalCardinality property.
     * 
     */
    void setSourceMaximalCardinality(int value);

    /**
     * Gets the value of the destinationRole property.
     * 
     * @return
     *     possible object is
     *     {@link extensionTools.opl2.generated.DestinationRoleType}
     *     {@link extensionTools.opl2.generated.DestinationRole}
     */
    extensionTools.opl2.generated.DestinationRoleType getDestinationRole();

    /**
     * Sets the value of the destinationRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link extensionTools.opl2.generated.DestinationRoleType}
     *     {@link extensionTools.opl2.generated.DestinationRole}
     */
    void setDestinationRole(extensionTools.opl2.generated.DestinationRoleType value);

    /**
     * Gets the value of the sourceName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSourceName();

    /**
     * Sets the value of the sourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSourceName(java.lang.String value);

    /**
     * Gets the value of the sourceRole property.
     * 
     * @return
     *     possible object is
     *     {@link extensionTools.opl2.generated.SourceRole}
     *     {@link extensionTools.opl2.generated.SourceRoleType}
     */
    extensionTools.opl2.generated.SourceRoleType getSourceRole();

    /**
     * Sets the value of the sourceRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link extensionTools.opl2.generated.SourceRole}
     *     {@link extensionTools.opl2.generated.SourceRoleType}
     */
    void setSourceRole(extensionTools.opl2.generated.SourceRoleType value);

    /**
     * Gets the value of the destinationName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getDestinationName();

    /**
     * Sets the value of the destinationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setDestinationName(java.lang.String value);

    /**
     * Gets the value of the sourceMinimalCardinality property.
     * 
     */
    int getSourceMinimalCardinality();

    /**
     * Sets the value of the sourceMinimalCardinality property.
     * 
     */
    void setSourceMinimalCardinality(int value);

    /**
     * Gets the value of the destinationMinimalCardinality property.
     * 
     */
    int getDestinationMinimalCardinality();

    /**
     * Sets the value of the destinationMinimalCardinality property.
     * 
     */
    void setDestinationMinimalCardinality(int value);

}
