//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

public class ConsumptionClauseTypeImpl implements extensionTools.opl2.generated.ConsumptionClauseType, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    protected java.lang.String _ObjectType;
    protected boolean has_MaximalCardinality;
    protected int _MaximalCardinality;
    protected java.lang.String _StateName;
    protected java.lang.String _SubjectThingRole;
    protected java.lang.String _SubjectExhibitionFatherRole;
    protected java.lang.String _SubjectAggregationFatherName;
    protected java.lang.String _SubjectAggregationFatherRole;
    protected com.sun.xml.bind.util.ListImpl _Role = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    protected java.lang.String _ObjectName;
    protected boolean has_MinimalCardinality;
    protected int _MinimalCardinality;
    protected java.lang.String _SubjectExhibitionFatherName;
    protected java.lang.String _SubjectThingName;
    protected java.lang.String _LogicalRelation;
    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/grammar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0002xp\u001f\u00c09\u0092ppsq\u0000~\u0000\u0000\u001d\u001d@\u001fppsq\u0000~\u0000\u0000\u001bM0\u0080ppsq\u0000~\u0000\u0000\u0018i\u001cyppsq\u0000~\u0000\u0000\u0015\u009d\u00c0\u00cbppsq\u0000~\u0000\u0000\u0013@(\u0001ppsq\u0000~\u0000\u0000\u000f\u008d\n)ppsq\u0000~\u0000\u0000\f\u000fF\\ppsq\u0000~\u0000\u0000\t\u00b0\u00b1Lppsq\u0000~\u0000\u0000\u0006\u00ea\u0086\u0002ppsq\u0000~\u0000\u0000\u0005(}2ppsq\u0000~\u0000\u0000\u0003ftWppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001\u00b3:)pp\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003\u0001\u00b3:\u001eppsr\u0000 com.sun.msv.datatype.xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u001eL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0003intsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$2\u0087z9\u00ee\u00f8,N\u0005\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001eL\u0000\fnamespaceURIq\u0000~\u0000\u001expq\u0000~\u0000\"q\u0000~\u0000!sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001eL\u0000\fnamespaceURIq\u0000~\u0000\u001exr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0012MinimalCardinalityt\u0000\u0000sq\u0000~\u0000\u0011\u0001\u00b3:)pp\u0000q\u0000~\u0000\u0018sq\u0000~\u0000*t\u0000\u0012MaximalCardinalityq\u0000~\u0000.sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001\u0001\u00c2\b\u00d6ppsq\u0000~\u0000\u0011\u0001\u00c2\b\u00cbsr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000p\u0000sq\u0000~\u0000\u0015\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000\u001bq\u0000~\u0000!t\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xq\u0000~\u0000$\u0001q\u0000~\u0000\'sq\u0000~\u0000(q\u0000~\u0000:q\u0000~\u0000!sq\u0000~\u0000*t\u0000\tStateNameq\u0000~\u0000.sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\tsq\u0000~\u00005\u0001psq\u0000~\u0000\u0011\u0001\u00c2\b\u00cbpp\u0000q\u0000~\u00007sq\u0000~\u0000*t\u0000\nObjectNameq\u0000~\u0000.sq\u0000~\u00002\u0002\u00c6+Eppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0002\u00c6+:q\u0000~\u00006psq\u0000~\u00002\u0002\u00c6+7q\u0000~\u00006psq\u0000~\u0000\u0011\u0001c\u0015\u00a0q\u0000~\u00006p\u0000sq\u0000~\u0000\u0011\u0001c\u0015\u0095pp\u0000sq\u0000~\u00002\u0001c\u0015\u008appsq\u0000~\u0000G\u0001c\u0015\u007fq\u0000~\u00006psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u0012xq\u0000~\u0000\u0003\u0001c\u0015|q\u0000~\u00006psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\bq\u0000~\u0000Bpsr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000+q\u0000~\u0000Asq\u0000~\u0000*t\u0000&extensionTools.opl2.generated.RoleTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000*t\u0000\u0004Roleq\u0000~\u0000.sq\u0000~\u0000\u0011\u0001c\u0015\u0095q\u0000~\u00006p\u0000sq\u0000~\u00002\u0001c\u0015\u008appsq\u0000~\u0000G\u0001c\u0015\u007fq\u0000~\u00006psq\u0000~\u0000O\u0001c\u0015|q\u0000~\u00006pq\u0000~\u0000Rq\u0000~\u0000Tq\u0000~\u0000Asq\u0000~\u0000*t\u0000\"extensionTools.opl2.generated.Roleq\u0000~\u0000Wq\u0000~\u0000Asq\u0000~\u00002\u0002^\u0095\u000bppsq\u0000~\u0000O\u0002^\u0095\u0000q\u0000~\u00006pq\u0000~\u00007sq\u0000~\u0000*t\u0000\u001csubjectAggregationFatherNameq\u0000~\u0000.q\u0000~\u0000Asq\u0000~\u00002\u0003}\u00c3\u00c8ppsq\u0000~\u0000O\u0003}\u00c3\u00bdq\u0000~\u00006pq\u0000~\u00007sq\u0000~\u0000*t\u0000\u001csubjectAggregationFatherRoleq\u0000~\u0000.q\u0000~\u0000Asq\u0000~\u00002\u0003\u00b3\u001d\u00d3ppsq\u0000~\u0000O\u0003\u00b3\u001d\u00c8q\u0000~\u00006pq\u0000~\u00007sq\u0000~\u0000*t\u0000\u000flogicalRelationq\u0000~\u0000.q\u0000~\u0000Asq\u0000~\u00002\u0002]\u0098\u00c5ppsq\u0000~\u0000O\u0002]\u0098\u00baq\u0000~\u00006pq\u0000~\u00007sq\u0000~\u0000*t\u0000\u001bsubjectExhibitionFatherRoleq\u0000~\u0000.q\u0000~\u0000Asq\u0000~\u0000O\u0002\u00cb[\u00a9ppq\u0000~\u00007sq\u0000~\u0000*t\u0000\u0010subjectThingNameq\u0000~\u0000.sq\u0000~\u00002\u0002\u00e4\u0014\u0002ppsq\u0000~\u0000O\u0002\u00e4\u0013\u00f7q\u0000~\u00006pq\u0000~\u00007sq\u0000~\u0000*t\u0000\u001bsubjectExhibitionFatherNameq\u0000~\u0000.q\u0000~\u0000Asq\u0000~\u00002\u0001\u00d0\u000f\u009appsq\u0000~\u0000O\u0001\u00d0\u000f\u008fq\u0000~\u00006pq\u0000~\u00007sq\u0000~\u0000*t\u0000\u0010subjectThingRoleq\u0000~\u0000.q\u0000~\u0000Asq\u0000~\u0000O\u0002\u00a2\u00f9nppq\u0000~\u00007sq\u0000~\u0000*t\u0000\nobjectTypeq\u0000~\u0000.sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000\u007f[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\u001a\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfpq\u0000~\u0000\u000bq\u0000~\u0000hpppq\u0000~\u00003q\u0000~\u0000\fq\u0000~\u0000\npppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\rpppppppppppppppppppppppppppq\u0000~\u0000Jq\u0000~\u0000\u0006pq\u0000~\u0000Ippppppppppq\u0000~\u0000Fppppppppppppq\u0000~\u0000Nq\u0000~\u0000\\ppppppppq\u0000~\u0000lq\u0000~\u0000Mq\u0000~\u0000[q\u0000~\u0000\u0007pppppq\u0000~\u0000\bpppq\u0000~\u0000\tppq\u0000~\u0000\u0010pppppppq\u0000~\u0000`ppq\u0000~\u0000\u000fq\u0000~\u0000\u000eppppq\u0000~\u0000wppppppppppppppppppppppppppq\u0000~\u0000sq\u0000~\u0000\u0005pq\u0000~\u0000dpppppppppp");

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.ConsumptionClauseType.class;
    }

    public java.lang.String getObjectType() {
        return _ObjectType;
    }

    public void setObjectType(java.lang.String value) {
        _ObjectType = value;
    }

    public int getMaximalCardinality() {
        return _MaximalCardinality;
    }

    public void setMaximalCardinality(int value) {
        _MaximalCardinality = value;
        has_MaximalCardinality = true;
    }

    public java.lang.String getStateName() {
        return _StateName;
    }

    public void setStateName(java.lang.String value) {
        _StateName = value;
    }

    public java.lang.String getSubjectThingRole() {
        return _SubjectThingRole;
    }

    public void setSubjectThingRole(java.lang.String value) {
        _SubjectThingRole = value;
    }

    public java.lang.String getSubjectExhibitionFatherRole() {
        return _SubjectExhibitionFatherRole;
    }

    public void setSubjectExhibitionFatherRole(java.lang.String value) {
        _SubjectExhibitionFatherRole = value;
    }

    public java.lang.String getSubjectAggregationFatherName() {
        return _SubjectAggregationFatherName;
    }

    public void setSubjectAggregationFatherName(java.lang.String value) {
        _SubjectAggregationFatherName = value;
    }

    public java.lang.String getSubjectAggregationFatherRole() {
        return _SubjectAggregationFatherRole;
    }

    public void setSubjectAggregationFatherRole(java.lang.String value) {
        _SubjectAggregationFatherRole = value;
    }

    public java.util.List getRole() {
        return _Role;
    }

    public java.lang.String getObjectName() {
        return _ObjectName;
    }

    public void setObjectName(java.lang.String value) {
        _ObjectName = value;
    }

    public int getMinimalCardinality() {
        return _MinimalCardinality;
    }

    public void setMinimalCardinality(int value) {
        _MinimalCardinality = value;
        has_MinimalCardinality = true;
    }

    public java.lang.String getSubjectExhibitionFatherName() {
        return _SubjectExhibitionFatherName;
    }

    public void setSubjectExhibitionFatherName(java.lang.String value) {
        _SubjectExhibitionFatherName = value;
    }

    public java.lang.String getSubjectThingName() {
        return _SubjectThingName;
    }

    public void setSubjectThingName(java.lang.String value) {
        _SubjectThingName = value;
    }

    public java.lang.String getLogicalRelation() {
        return _LogicalRelation;
    }

    public void setLogicalRelation(java.lang.String value) {
        _LogicalRelation = value;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ConsumptionClauseTypeImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx8 = 0;
        final int len8 = _Role.size();
        context.startElement("", "MinimalCardinality");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInt(((int) _MinimalCardinality)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "MaximalCardinality");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInt(((int) _MaximalCardinality)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        if (_StateName!= null) {
            context.startElement("", "StateName");
            context.endAttributes();
            try {
                context.text(((java.lang.String) _StateName));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        context.startElement("", "ObjectName");
        context.endAttributes();
        try {
            context.text(((java.lang.String) _ObjectName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx8 != len8) {
            if (_Role.get(idx8) instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx8 ++)));
            } else {
                context.startElement("", "Role");
                int idx_4 = idx8;
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx_4 ++)));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx8 ++)));
                context.endElement();
            }
        }
    }

    public void serializeAttributes(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx8 = 0;
        final int len8 = _Role.size();
        if (_SubjectAggregationFatherName!= null) {
            context.startAttribute("", "subjectAggregationFatherName");
            try {
                context.text(((java.lang.String) _SubjectAggregationFatherName));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        if (_SubjectAggregationFatherRole!= null) {
            context.startAttribute("", "subjectAggregationFatherRole");
            try {
                context.text(((java.lang.String) _SubjectAggregationFatherRole));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        if (_LogicalRelation!= null) {
            context.startAttribute("", "logicalRelation");
            try {
                context.text(((java.lang.String) _LogicalRelation));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        if (_SubjectExhibitionFatherRole!= null) {
            context.startAttribute("", "subjectExhibitionFatherRole");
            try {
                context.text(((java.lang.String) _SubjectExhibitionFatherRole));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        context.startAttribute("", "subjectThingName");
        try {
            context.text(((java.lang.String) _SubjectThingName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        if (_SubjectExhibitionFatherName!= null) {
            context.startAttribute("", "subjectExhibitionFatherName");
            try {
                context.text(((java.lang.String) _SubjectExhibitionFatherName));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        if (_SubjectThingRole!= null) {
            context.startAttribute("", "subjectThingRole");
            try {
                context.text(((java.lang.String) _SubjectThingRole));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        context.startAttribute("", "objectType");
        try {
            context.text(((java.lang.String) _ObjectType));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
    }

    public void serializeAttributeBodies(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx8 = 0;
        final int len8 = _Role.size();
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ConsumptionClauseType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.xml.bind.unmarshaller.ContentHandlerEx
    {


        public Unmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
            super(context, "------------------------------");
        }

        protected com.sun.xml.bind.unmarshaller.UnmarshallableObject owner() {
            return extensionTools.opl2.generated.impl.ConsumptionClauseTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  3 :
                    if (("" == ___uri)&&("MaximalCardinality" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 4;
                        return ;
                    }
                    break;
                case  6 :
                    if (("" == ___uri)&&("ObjectName" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 7;
                        return ;
                    }
                    if (("" == ___uri)&&("StateName" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 12;
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("MinimalCardinality" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 1;
                        return ;
                    }
                    break;
                case  9 :
                    if (("" == ___uri)&&("Role" == ___local)) {
                        context.pushAttributes(__atts);
                        goto10();
                        return ;
                    }
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
            }
            super.enterElement(___uri, ___local, __atts);
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  13 :
                    if (("" == ___uri)&&("StateName" == ___local)) {
                        context.popAttributes();
                        state = 6;
                        return ;
                    }
                    break;
                case  8 :
                    if (("" == ___uri)&&("ObjectName" == ___local)) {
                        context.popAttributes();
                        state = 9;
                        return ;
                    }
                    break;
                case  2 :
                    if (("" == ___uri)&&("MinimalCardinality" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  11 :
                    if (("" == ___uri)&&("Role" == ___local)) {
                        context.popAttributes();
                        state = 9;
                        return ;
                    }
                    break;
                case  5 :
                    if (("" == ___uri)&&("MaximalCardinality" == ___local)) {
                        context.popAttributes();
                        state = 6;
                        return ;
                    }
                    break;
                case  9 :
                    revertToParentFromLeaveElement(___uri, ___local);
                    return ;
            }
            super.leaveElement(___uri, ___local);
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  10 :
                    if (("" == ___uri)&&("RoleName" == ___local)) {
                        _Role.add(((extensionTools.opl2.generated.impl.RoleTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 11, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("Library" == ___local)) {
                        _Role.add(((extensionTools.opl2.generated.impl.RoleTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 11, ___uri, ___local)));
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("subjectExhibitionFatherRole" == ___local)) {
                        state = 18;
                        return ;
                    }
                    if (("" == ___uri)&&("subjectThingName" == ___local)) {
                        state = 14;
                        return ;
                    }
                    if (("" == ___uri)&&("logicalRelation" == ___local)) {
                        state = 20;
                        return ;
                    }
                    if (("" == ___uri)&&("subjectThingRole" == ___local)) {
                        state = 24;
                        return ;
                    }
                    if (("" == ___uri)&&("subjectAggregationFatherRole" == ___local)) {
                        state = 16;
                        return ;
                    }
                    if (("" == ___uri)&&("objectType" == ___local)) {
                        state = 28;
                        return ;
                    }
                    if (("" == ___uri)&&("subjectExhibitionFatherName" == ___local)) {
                        state = 26;
                        return ;
                    }
                    if (("" == ___uri)&&("subjectAggregationFatherName" == ___local)) {
                        state = 22;
                        return ;
                    }
                    break;
                case  9 :
                    revertToParentFromEnterAttribute(___uri, ___local);
                    return ;
            }
            super.enterAttribute(___uri, ___local);
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  15 :
                    if (("" == ___uri)&&("subjectThingName" == ___local)) {
                        goto0();
                        return ;
                    }
                    break;
                case  21 :
                    if (("" == ___uri)&&("logicalRelation" == ___local)) {
                        goto0();
                        return ;
                    }
                    break;
                case  29 :
                    if (("" == ___uri)&&("objectType" == ___local)) {
                        goto0();
                        return ;
                    }
                    break;
                case  23 :
                    if (("" == ___uri)&&("subjectAggregationFatherName" == ___local)) {
                        goto0();
                        return ;
                    }
                    break;
                case  27 :
                    if (("" == ___uri)&&("subjectExhibitionFatherName" == ___local)) {
                        goto0();
                        return ;
                    }
                    break;
                case  9 :
                    revertToParentFromLeaveAttribute(___uri, ___local);
                    return ;
                case  19 :
                    if (("" == ___uri)&&("subjectExhibitionFatherRole" == ___local)) {
                        goto0();
                        return ;
                    }
                    break;
                case  25 :
                    if (("" == ___uri)&&("subjectThingRole" == ___local)) {
                        goto0();
                        return ;
                    }
                    break;
                case  17 :
                    if (("" == ___uri)&&("subjectAggregationFatherRole" == ___local)) {
                        goto0();
                        return ;
                    }
                    break;
            }
            super.leaveAttribute(___uri, ___local);
        }

        public void text(java.lang.String value)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            try {
                switch (state) {
                    case  22 :
                        try {
                            _SubjectAggregationFatherName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 23;
                        return ;
                    case  24 :
                        try {
                            _SubjectThingRole = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 25;
                        return ;
                    case  14 :
                        try {
                            _SubjectThingName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 15;
                        return ;
                    case  16 :
                        try {
                            _SubjectAggregationFatherRole = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 17;
                        return ;
                    case  20 :
                        try {
                            _LogicalRelation = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 21;
                        return ;
                    case  4 :
                        try {
                            _MaximalCardinality = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_MaximalCardinality = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 5;
                        return ;
                    case  28 :
                        try {
                            _ObjectType = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 29;
                        return ;
                    case  9 :
                        revertToParentFromText(value);
                        return ;
                    case  18 :
                        try {
                            _SubjectExhibitionFatherRole = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 19;
                        return ;
                    case  7 :
                        try {
                            _ObjectName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 8;
                        return ;
                    case  1 :
                        try {
                            _MinimalCardinality = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_MinimalCardinality = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 2;
                        return ;
                    case  26 :
                        try {
                            _SubjectExhibitionFatherName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 27;
                        return ;
                    case  12 :
                        try {
                            _StateName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 13;
                        return ;
                }
            } catch (java.lang.RuntimeException e) {
                handleUnexpectedTextException(value, e);
            }
        }

        public void leaveChild(int nextState)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (nextState) {
                case  11 :
                    state = 11;
                    return ;
            }
            super.leaveChild(nextState);
        }

        private void goto10()
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            int idx;
            state = 10;
            idx = context.getAttribute("", "RoleName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "Library");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
        }

        private void goto0()
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            int idx;
            state = 0;
            idx = context.getAttribute("", "subjectAggregationFatherName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectAggregationFatherRole");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "logicalRelation");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectExhibitionFatherRole");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectThingName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectExhibitionFatherName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectThingRole");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "objectType");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
        }

    }

}
