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
 *         &lt;element name="StateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Initial" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Final" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Default" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element ref="{}MinTimeValue" minOccurs="0"/>
 *         &lt;element ref="{}MaxTimeValue" minOccurs="0"/>
 *         &lt;element name="StateDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface StateClauseType {


    java.lang.String getStateName();

    void setStateName(java.lang.String value);

    java.lang.String getStateDescription();

    void setStateDescription(java.lang.String value);

    boolean isFinal();

    void setFinal(boolean value);

    boolean isInitial();

    void setInitial(boolean value);

    extensionTools.opl2.generated.MinTimeValueType getMinTimeValue();

    void setMinTimeValue(extensionTools.opl2.generated.MinTimeValueType value);

    boolean isDefault();

    void setDefault(boolean value);

    extensionTools.opl2.generated.MaxTimeValueType getMaxTimeValue();

    void setMaxTimeValue(extensionTools.opl2.generated.MaxTimeValueType value);

}