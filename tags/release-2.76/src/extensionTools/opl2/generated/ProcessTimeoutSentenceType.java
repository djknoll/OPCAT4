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


    extensionTools.opl2.generated.MaxTimeoutValueType getMaxTimeoutValue();

    void setMaxTimeoutValue(extensionTools.opl2.generated.MaxTimeoutValueType value);

    extensionTools.opl2.generated.MaxReactionTimeType getMaxReactionTime();

    void setMaxReactionTime(extensionTools.opl2.generated.MaxReactionTimeType value);

    java.lang.String getProcessName();

    void setProcessName(java.lang.String value);

    extensionTools.opl2.generated.MinReactionTimeType getMinReactionTime();

    void setMinReactionTime(extensionTools.opl2.generated.MinReactionTimeType value);

    java.util.List getRole();

    java.lang.String getLogicalRelation();

    void setLogicalRelation(java.lang.String value);

    java.util.List getPathLabel();

    java.util.List getTriggeredProcess();

}
