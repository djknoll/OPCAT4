//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/raanan/jwsdp-1.6/jaxb/bin/opl.xml line 38)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MinimalCardinality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaximalCardinality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ObjectName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}Role" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SourceStateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DestinationStateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="logicalRelation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="objectType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectAggregationFatherName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectAggregationFatherRole" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectExhibitionFatherName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectExhibitionFatherRole" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectThingName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectThingRole" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ChangingClauseType {


    /**
     * Gets the value of the objectType property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getObjectType();

    /**
     * Sets the value of the objectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setObjectType(java.lang.String value);

    /**
     * Gets the value of the maximalCardinality property.
     * 
     */
    int getMaximalCardinality();

    /**
     * Sets the value of the maximalCardinality property.
     * 
     */
    void setMaximalCardinality(int value);

    /**
     * Gets the value of the sourceStateName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSourceStateName();

    /**
     * Sets the value of the sourceStateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSourceStateName(java.lang.String value);

    /**
     * Gets the value of the subjectAggregationFatherName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSubjectAggregationFatherName();

    /**
     * Sets the value of the subjectAggregationFatherName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSubjectAggregationFatherName(java.lang.String value);

    /**
     * Gets the value of the subjectAggregationFatherRole property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSubjectAggregationFatherRole();

    /**
     * Sets the value of the subjectAggregationFatherRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSubjectAggregationFatherRole(java.lang.String value);

    /**
     * Gets the value of the objectName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getObjectName();

    /**
     * Sets the value of the objectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setObjectName(java.lang.String value);

    /**
     * Gets the value of the subjectThingName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSubjectThingName();

    /**
     * Sets the value of the subjectThingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSubjectThingName(java.lang.String value);

    /**
     * Gets the value of the minimalCardinality property.
     * 
     */
    int getMinimalCardinality();

    /**
     * Sets the value of the minimalCardinality property.
     * 
     */
    void setMinimalCardinality(int value);

    /**
     * Gets the value of the destinationStateName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getDestinationStateName();

    /**
     * Sets the value of the destinationStateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setDestinationStateName(java.lang.String value);

    /**
     * Gets the value of the subjectThingRole property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSubjectThingRole();

    /**
     * Sets the value of the subjectThingRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSubjectThingRole(java.lang.String value);

    /**
     * Gets the value of the subjectExhibitionFatherRole property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSubjectExhibitionFatherRole();

    /**
     * Sets the value of the subjectExhibitionFatherRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSubjectExhibitionFatherRole(java.lang.String value);

    /**
     * Gets the value of the Role property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Role property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link extensionTools.opl2.generated.RoleType}
     * {@link extensionTools.opl2.generated.Role}
     * 
     */
    java.util.List getRole();

    /**
     * Gets the value of the logicalRelation property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getLogicalRelation();

    /**
     * Sets the value of the logicalRelation property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setLogicalRelation(java.lang.String value);

    /**
     * Gets the value of the subjectExhibitionFatherName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSubjectExhibitionFatherName();

    /**
     * Sets the value of the subjectExhibitionFatherName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSubjectExhibitionFatherName(java.lang.String value);

}
