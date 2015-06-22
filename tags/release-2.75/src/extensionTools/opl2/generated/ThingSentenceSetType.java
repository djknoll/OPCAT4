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
 *           &lt;element name="ObjectDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element name="ObjectScope" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="IsKey" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element ref="{}ObjectIndex" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ObjectEnvironmentalPhysicalSentence" minOccurs="0"/>
 *           &lt;element ref="{}ObjectInheritanceSentenceSet" minOccurs="0"/>
 *           &lt;element ref="{}ObjectInstanceSentence" minOccurs="0"/>
 *           &lt;element ref="{}ObjectStateSentence" minOccurs="0"/>
 *           &lt;element ref="{}ObjectExhibitionSentenceSet" minOccurs="0"/>
 *           &lt;element ref="{}ObjectAggregationSentenceSet" minOccurs="0"/>
 *           &lt;element ref="{}ObjectUniDirectionalRelationSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ObjectBiDirectionalRelationSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}AgentSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}GeneralEventSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}StateEntranceSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}StateTimeoutSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ObjectInZoomingSentenceSet" minOccurs="0"/>
 *           &lt;element ref="{}Role" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{}TypeDeclarationSentence"/>
 *           &lt;element name="ObjectDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element name="IsKey" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element ref="{}ObjectIndex" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}Role" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="ProcessDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element name="ProcessScope" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element ref="{}MinTimeValue" minOccurs="0"/>
 *           &lt;element ref="{}MaxTimeValue" minOccurs="0"/>
 *           &lt;element ref="{}ProcessEnvironmentalPhysicalSentence" minOccurs="0"/>
 *           &lt;element ref="{}ProcessInheritanceSentenceSet" minOccurs="0"/>
 *           &lt;element ref="{}ProcessInstanceSentence" minOccurs="0"/>
 *           &lt;element ref="{}ProcessExhibitionSentenceSet" minOccurs="0"/>
 *           &lt;element ref="{}ProcessAggregationSentenceSet" minOccurs="0"/>
 *           &lt;element ref="{}ProcessUniDirectionalRelationSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ProcessBiDirectionalRelationSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ConditionSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}EnablingSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}EffectSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ChangingSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ConsumptionSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ResultSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="ProcessBody" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element ref="{}ProcessTimeoutSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ProcessInvocationSentence" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}ProcessInZoomingSentenceSet" minOccurs="0"/>
 *           &lt;element ref="{}Role" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="Existential" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *       &lt;attribute name="subjectExhibitionFatherName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="systemName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectAggregationFatherName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectThingName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="transID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ThingSentenceSetType {


    extensionTools.opl2.generated.ObjectInstanceSentenceType getObjectInstanceSentence();

    void setObjectInstanceSentence(extensionTools.opl2.generated.ObjectInstanceSentenceType value);

    java.lang.String getSystemName();

    void setSystemName(java.lang.String value);

    java.util.List getObjectUniDirectionalRelationSentence();

    java.util.List getGeneralEventSentence();

    java.lang.String getProcessDescription();

    void setProcessDescription(java.lang.String value);

    java.lang.String getObjectScope();

    void setObjectScope(java.lang.String value);

    java.util.List getProcessBiDirectionalRelationSentence();

    java.util.List getObjectBiDirectionalRelationSentence();

    extensionTools.opl2.generated.ObjectEnvironmentalPhysicalSentenceType getObjectEnvironmentalPhysicalSentence();

    void setObjectEnvironmentalPhysicalSentence(extensionTools.opl2.generated.ObjectEnvironmentalPhysicalSentenceType value);

    extensionTools.opl2.generated.ObjectStateSentenceType getObjectStateSentence();

    void setObjectStateSentence(extensionTools.opl2.generated.ObjectStateSentenceType value);

    java.lang.String getSubjectThingName();

    void setSubjectThingName(java.lang.String value);

    extensionTools.opl2.generated.MaxTimeValueType getMaxTimeValue();

    void setMaxTimeValue(extensionTools.opl2.generated.MaxTimeValueType value);

    boolean isIsKey();

    void setIsKey(boolean value);

    java.util.List getObjectIndex();

    extensionTools.opl2.generated.ObjectExhibitionSentenceSetType getObjectExhibitionSentenceSet();

    void setObjectExhibitionSentenceSet(extensionTools.opl2.generated.ObjectExhibitionSentenceSetType value);

    java.lang.String getProcessBody();

    void setProcessBody(java.lang.String value);

    java.util.List getProcessInvocationSentence();

    java.util.List getProcessTimeoutSentence();

    java.util.List getProcessUniDirectionalRelationSentence();

    extensionTools.opl2.generated.ProcessAggregationSentenceSetType getProcessAggregationSentenceSet();

    void setProcessAggregationSentenceSet(extensionTools.opl2.generated.ProcessAggregationSentenceSetType value);

    extensionTools.opl2.generated.TypeDeclarationSentenceType getTypeDeclarationSentence();

    void setTypeDeclarationSentence(extensionTools.opl2.generated.TypeDeclarationSentenceType value);

    extensionTools.opl2.generated.ProcessInstanceSentenceType getProcessInstanceSentence();

    void setProcessInstanceSentence(extensionTools.opl2.generated.ProcessInstanceSentenceType value);

    extensionTools.opl2.generated.ObjectAggregationSentenceSetType getObjectAggregationSentenceSet();

    void setObjectAggregationSentenceSet(extensionTools.opl2.generated.ObjectAggregationSentenceSetType value);

    java.lang.String getTransID();

    void setTransID(java.lang.String value);

    java.util.List getConditionSentence();

    extensionTools.opl2.generated.ObjectInZoomingSentenceSetType getObjectInZoomingSentenceSet();

    void setObjectInZoomingSentenceSet(extensionTools.opl2.generated.ObjectInZoomingSentenceSetType value);

    java.util.List getConsumptionSentence();

    extensionTools.opl2.generated.ProcessInheritanceSentenceSetType getProcessInheritanceSentenceSet();

    void setProcessInheritanceSentenceSet(extensionTools.opl2.generated.ProcessInheritanceSentenceSetType value);

    java.util.List getChangingSentence();

    extensionTools.opl2.generated.ProcessEnvironmentalPhysicalSentenceType getProcessEnvironmentalPhysicalSentence();

    void setProcessEnvironmentalPhysicalSentence(extensionTools.opl2.generated.ProcessEnvironmentalPhysicalSentenceType value);

    extensionTools.opl2.generated.ObjectInheritanceSentenceSetType getObjectInheritanceSentenceSet();

    void setObjectInheritanceSentenceSet(extensionTools.opl2.generated.ObjectInheritanceSentenceSetType value);

    java.lang.String getSubjectAggregationFatherName();

    void setSubjectAggregationFatherName(java.lang.String value);

    java.util.List getStateEntranceSentence();

    java.util.List getResultSentence();

    java.util.List getStateTimeoutSentence();

    extensionTools.opl2.generated.ProcessInZoomingSentenceSetType getProcessInZoomingSentenceSet();

    void setProcessInZoomingSentenceSet(extensionTools.opl2.generated.ProcessInZoomingSentenceSetType value);

    java.lang.String getObjectDescription();

    void setObjectDescription(java.lang.String value);

    java.lang.String getExistential();

    void setExistential(java.lang.String value);

    java.util.List getEffectSentence();

    extensionTools.opl2.generated.MinTimeValueType getMinTimeValue();

    void setMinTimeValue(extensionTools.opl2.generated.MinTimeValueType value);

    extensionTools.opl2.generated.ProcessExhibitionSentenceSetType getProcessExhibitionSentenceSet();

    void setProcessExhibitionSentenceSet(extensionTools.opl2.generated.ProcessExhibitionSentenceSetType value);

    java.util.List getEnablingSentence();

    java.lang.String getProcessScope();

    void setProcessScope(java.lang.String value);

    java.util.List getAgentSentence();

    java.util.List getRole();

    java.lang.String getSubjectExhibitionFatherName();

    void setSubjectExhibitionFatherName(java.lang.String value);

}
