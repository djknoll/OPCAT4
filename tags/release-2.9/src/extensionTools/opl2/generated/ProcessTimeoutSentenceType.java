//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/raanan/jwsdp-1.6/jaxb/bin/opl.xml line 604)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProcessName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}Role" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LogicalRelation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TriggeredProcess" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="PathLabel" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}MinReactionTime" minOccurs="0"/>
 *         &lt;element ref="{}MaxReactionTime" minOccurs="0"/>
 *         &lt;element ref="{}MaxTimeoutValue"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ProcessTimeoutSentenceType {


    /**
     * Gets the value of the maxTimeoutValue property.
     * 
     * @return
     *     possible object is
     *     {@link extensionTools.opl2.generated.MaxTimeoutValueType}
     *     {@link extensionTools.opl2.generated.MaxTimeoutValue}
     */
    extensionTools.opl2.generated.MaxTimeoutValueType getMaxTimeoutValue();

    /**
     * Sets the value of the maxTimeoutValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link extensionTools.opl2.generated.MaxTimeoutValueType}
     *     {@link extensionTools.opl2.generated.MaxTimeoutValue}
     */
    void setMaxTimeoutValue(extensionTools.opl2.generated.MaxTimeoutValueType value);

    /**
     * Gets the value of the maxReactionTime property.
     * 
     * @return
     *     possible object is
     *     {@link extensionTools.opl2.generated.MaxReactionTime}
     *     {@link extensionTools.opl2.generated.MaxReactionTimeType}
     */
    extensionTools.opl2.generated.MaxReactionTimeType getMaxReactionTime();

    /**
     * Sets the value of the maxReactionTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link extensionTools.opl2.generated.MaxReactionTime}
     *     {@link extensionTools.opl2.generated.MaxReactionTimeType}
     */
    void setMaxReactionTime(extensionTools.opl2.generated.MaxReactionTimeType value);

    /**
     * Gets the value of the processName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getProcessName();

    /**
     * Sets the value of the processName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setProcessName(java.lang.String value);

    /**
     * Gets the value of the minReactionTime property.
     * 
     * @return
     *     possible object is
     *     {@link extensionTools.opl2.generated.MinReactionTimeType}
     *     {@link extensionTools.opl2.generated.MinReactionTime}
     */
    extensionTools.opl2.generated.MinReactionTimeType getMinReactionTime();

    /**
     * Sets the value of the minReactionTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link extensionTools.opl2.generated.MinReactionTimeType}
     *     {@link extensionTools.opl2.generated.MinReactionTime}
     */
    void setMinReactionTime(extensionTools.opl2.generated.MinReactionTimeType value);

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
     * Gets the value of the PathLabel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the PathLabel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPathLabel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getPathLabel();

    /**
     * Gets the value of the TriggeredProcess property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the TriggeredProcess property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTriggeredProcess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getTriggeredProcess();

}
