//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/raanan/jwsdp-1.6/jaxb/bin/opl.xml line 413)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ObjectInZoomingSentence"/>
 *         &lt;element ref="{}ThingSentenceSet" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ObjectInZoomingSentenceSetType {


    /**
     * Gets the value of the objectInZoomingSentence property.
     * 
     * @return
     *     possible object is
     *     {@link extensionTools.opl2.generated.ObjectInZoomingSentenceType}
     *     {@link extensionTools.opl2.generated.ObjectInZoomingSentence}
     */
    extensionTools.opl2.generated.ObjectInZoomingSentenceType getObjectInZoomingSentence();

    /**
     * Sets the value of the objectInZoomingSentence property.
     * 
     * @param value
     *     allowed object is
     *     {@link extensionTools.opl2.generated.ObjectInZoomingSentenceType}
     *     {@link extensionTools.opl2.generated.ObjectInZoomingSentence}
     */
    void setObjectInZoomingSentence(extensionTools.opl2.generated.ObjectInZoomingSentenceType value);

    /**
     * Gets the value of the ThingSentenceSet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ThingSentenceSet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getThingSentenceSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link extensionTools.opl2.generated.ThingSentenceSetType}
     * {@link extensionTools.opl2.generated.ThingSentenceSet}
     * 
     */
    java.util.List getThingSentenceSet();

}
