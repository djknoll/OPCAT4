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
 *         &lt;element name="MinimalCardinality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaximalCardinality" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ObjectName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}Role" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="StateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="subjectThingRole" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="logicalRelation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectExhibitionFatherRole" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectExhibitionFatherName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="objectType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectAggregationFatherName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectThingName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectAggregationFatherRole" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ConditionClauseType {


    java.lang.String getObjectType();

    void setObjectType(java.lang.String value);

    int getMaximalCardinality();

    void setMaximalCardinality(int value);

    java.lang.String getStateName();

    void setStateName(java.lang.String value);

    java.lang.String getSubjectThingRole();

    void setSubjectThingRole(java.lang.String value);

    java.lang.String getSubjectExhibitionFatherRole();

    void setSubjectExhibitionFatherRole(java.lang.String value);

    java.lang.String getSubjectAggregationFatherName();

    void setSubjectAggregationFatherName(java.lang.String value);

    java.lang.String getSubjectAggregationFatherRole();

    void setSubjectAggregationFatherRole(java.lang.String value);

    java.util.List getRole();

    java.lang.String getObjectName();

    void setObjectName(java.lang.String value);

    int getMinimalCardinality();

    void setMinimalCardinality(int value);

    java.lang.String getSubjectThingName();

    void setSubjectThingName(java.lang.String value);

    java.lang.String getSubjectExhibitionFatherName();

    void setSubjectExhibitionFatherName(java.lang.String value);

    java.lang.String getLogicalRelation();

    void setLogicalRelation(java.lang.String value);

}
