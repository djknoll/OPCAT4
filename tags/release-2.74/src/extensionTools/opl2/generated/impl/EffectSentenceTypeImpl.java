//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

public class EffectSentenceTypeImpl implements extensionTools.opl2.generated.EffectSentenceType, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    protected java.lang.String _ProcessName;
    protected com.sun.xml.bind.util.ListImpl _EffectClause = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    protected com.sun.xml.bind.util.ListImpl _Role = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    protected java.lang.String _LogicalRelation;
    protected com.sun.xml.bind.util.ListImpl _PathLabel = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/grammar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0002xp\n\u00d2q\rppsq\u0000~\u0000\u0000\t\u0010h/ppsq\u0000~\u0000\u0000\u0006J<\u00f0ppsq\u0000~\u0000\u0000\u0004\u00884\u0015ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001\u00c2\b\u00cbpp\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0015L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0015L\u0000\fnamespaceURIq\u0000~\u0000\u0015xpq\u0000~\u0000\u0019q\u0000~\u0000\u0018sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0015L\u0000\fnamespaceURIq\u0000~\u0000\u0015xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u000bProcessNamet\u0000\u0000sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001\u0002\u00c6+Eppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0002\u00c6+:sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000&\u0002\u00c6+7q\u0000~\u0000,psq\u0000~\u0000\t\u0001c\u0015\u00a0q\u0000~\u0000,p\u0000sq\u0000~\u0000\t\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000&\u0001c\u0015\u008appsq\u0000~\u0000(\u0001c\u0015\u007fq\u0000~\u0000,psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\nxq\u0000~\u0000\u0003\u0001c\u0015|q\u0000~\u0000,psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\bsq\u0000~\u0000+\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\"sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\tq\u0000~\u00006psq\u0000~\u0000!t\u0000&extensionTools.opl2.generated.RoleTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000!t\u0000\u0004Roleq\u0000~\u0000%sq\u0000~\u0000\t\u0001c\u0015\u0095q\u0000~\u0000,p\u0000sq\u0000~\u0000&\u0001c\u0015\u008appsq\u0000~\u0000(\u0001c\u0015\u007fq\u0000~\u0000,psq\u0000~\u00002\u0001c\u0015|q\u0000~\u0000,pq\u0000~\u00005q\u0000~\u00008q\u0000~\u0000:sq\u0000~\u0000!t\u0000\"extensionTools.opl2.generated.Roleq\u0000~\u0000=q\u0000~\u0000:sq\u0000~\u0000&\u0001\u00c2\b\u00d6ppsq\u0000~\u0000\t\u0001\u00c2\b\u00cbq\u0000~\u0000,p\u0000q\u0000~\u0000\u0010sq\u0000~\u0000!t\u0000\u000fLogicalRelationq\u0000~\u0000%q\u0000~\u0000:sq\u0000~\u0000(\u0002\u00c6+:ppsq\u0000~\u0000&\u0002\u00c6+7ppsq\u0000~\u0000\t\u0001c\u0015\u00a0pp\u0000sq\u0000~\u0000\t\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000&\u0001c\u0015\u008appsq\u0000~\u0000(\u0001c\u0015\u007fq\u0000~\u0000,psq\u0000~\u00002\u0001c\u0015|q\u0000~\u0000,pq\u0000~\u00005q\u0000~\u00008q\u0000~\u0000:sq\u0000~\u0000!t\u0000.extensionTools.opl2.generated.EffectClauseTypeq\u0000~\u0000=sq\u0000~\u0000!t\u0000\fEffectClauseq\u0000~\u0000%sq\u0000~\u0000\t\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000&\u0001c\u0015\u008appsq\u0000~\u0000(\u0001c\u0015\u007fq\u0000~\u0000,psq\u0000~\u00002\u0001c\u0015|q\u0000~\u0000,pq\u0000~\u00005q\u0000~\u00008q\u0000~\u0000:sq\u0000~\u0000!t\u0000*extensionTools.opl2.generated.EffectClauseq\u0000~\u0000=sq\u0000~\u0000&\u0001\u00c2\b\u00d9ppsq\u0000~\u0000(\u0001\u00c2\b\u00ceq\u0000~\u0000,psq\u0000~\u0000\t\u0001\u00c2\b\u00cbq\u0000~\u0000,p\u0000q\u0000~\u0000\u0010sq\u0000~\u0000!t\u0000\tPathLabelq\u0000~\u0000%q\u0000~\u0000:sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000a[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\u0014\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfppq\u0000~\u0000\u0006pppq\u0000~\u0000Fppq\u0000~\u0000[ppppppq\u0000~\u0000\u0005pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000-q\u0000~\u0000Kpq\u0000~\u0000*q\u0000~\u0000Jpppppppppq\u0000~\u0000\'q\u0000~\u0000\bpppppppppq\u0000~\u0000\u0007pq\u0000~\u00001q\u0000~\u0000Bq\u0000~\u0000Oq\u0000~\u0000Wpppppppq\u0000~\u00000q\u0000~\u0000Aq\u0000~\u0000Nq\u0000~\u0000Vpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\\p");

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.EffectSentenceType.class;
    }

    public java.lang.String getProcessName() {
        return _ProcessName;
    }

    public void setProcessName(java.lang.String value) {
        _ProcessName = value;
    }

    public java.util.List getEffectClause() {
        return _EffectClause;
    }

    public java.util.List getRole() {
        return _Role;
    }

    public java.lang.String getLogicalRelation() {
        return _LogicalRelation;
    }

    public void setLogicalRelation(java.lang.String value) {
        _LogicalRelation = value;
    }

    public java.util.List getPathLabel() {
        return _PathLabel;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.EffectSentenceTypeImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = _EffectClause.size();
        int idx3 = 0;
        final int len3 = _Role.size();
        int idx5 = 0;
        final int len5 = _PathLabel.size();
        context.startElement("", "ProcessName");
        context.endAttributes();
        try {
            context.text(((java.lang.String) _ProcessName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx3 != len3) {
            if (_Role.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx3 ++)));
            } else {
                context.startElement("", "Role");
                int idx_1 = idx3;
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx_1 ++)));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx3 ++)));
                context.endElement();
            }
        }
        if (_LogicalRelation!= null) {
            context.startElement("", "LogicalRelation");
            context.endAttributes();
            try {
                context.text(((java.lang.String) _LogicalRelation));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        while (idx2 != len2) {
            if (_EffectClause.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _EffectClause.get(idx2 ++)));
            } else {
                context.startElement("", "EffectClause");
                int idx_3 = idx2;
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _EffectClause.get(idx_3 ++)));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _EffectClause.get(idx2 ++)));
                context.endElement();
            }
        }
        while (idx5 != len5) {
            context.startElement("", "PathLabel");
            int idx_4 = idx5;
            try {
                idx_4 += 1;
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttributes();
            try {
                context.text(((java.lang.String) _PathLabel.get(idx5 ++)));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
    }

    public void serializeAttributes(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = _EffectClause.size();
        int idx3 = 0;
        final int len3 = _Role.size();
        int idx5 = 0;
        final int len5 = _PathLabel.size();
    }

    public void serializeAttributeBodies(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = _EffectClause.size();
        int idx3 = 0;
        final int len3 = _Role.size();
        int idx5 = 0;
        final int len5 = _PathLabel.size();
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.EffectSentenceType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.xml.bind.unmarshaller.ContentHandlerEx
    {


        public Unmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
            super(context, "-------------");
        }

        protected com.sun.xml.bind.unmarshaller.UnmarshallableObject owner() {
            return extensionTools.opl2.generated.impl.EffectSentenceTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  10 :
                    if (("" == ___uri)&&("EffectClause" == ___local)) {
                        context.pushAttributes(__atts);
                        goto8();
                        return ;
                    }
                    if (("" == ___uri)&&("PathLabel" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 11;
                        return ;
                    }
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
                case  8 :
                    if (("" == ___uri)&&("MinimalCardinality" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local, __atts)));
                        return ;
                    }
                    break;
                case  3 :
                    if (("" == ___uri)&&("Role" == ___local)) {
                        context.pushAttributes(__atts);
                        goto6();
                        return ;
                    }
                    if (("" == ___uri)&&("EffectClause" == ___local)) {
                        context.pushAttributes(__atts);
                        goto8();
                        return ;
                    }
                    if (("" == ___uri)&&("LogicalRelation" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 4;
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("ProcessName" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 1;
                        return ;
                    }
                    break;
            }
            super.enterElement(___uri, ___local, __atts);
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  7 :
                    if (("" == ___uri)&&("Role" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  9 :
                    if (("" == ___uri)&&("EffectClause" == ___local)) {
                        context.popAttributes();
                        state = 10;
                        return ;
                    }
                    break;
                case  10 :
                    revertToParentFromLeaveElement(___uri, ___local);
                    return ;
                case  5 :
                    if (("" == ___uri)&&("LogicalRelation" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  2 :
                    if (("" == ___uri)&&("ProcessName" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  12 :
                    if (("" == ___uri)&&("PathLabel" == ___local)) {
                        context.popAttributes();
                        state = 10;
                        return ;
                    }
                    break;
            }
            super.leaveElement(___uri, ___local);
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  10 :
                    revertToParentFromEnterAttribute(___uri, ___local);
                    return ;
                case  8 :
                    if (("" == ___uri)&&("subjectThingName" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectAggregationFatherRole" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectThingRole" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectAggregationFatherName" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectExhibitionFatherName" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("objectType" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("logicalRelation" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectExhibitionFatherRole" == ___local)) {
                        _EffectClause.add(((extensionTools.opl2.generated.impl.EffectClauseTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.EffectClauseTypeImpl.class), 9, ___uri, ___local)));
                        return ;
                    }
                    break;
                case  6 :
                    if (("" == ___uri)&&("RoleName" == ___local)) {
                        _Role.add(((extensionTools.opl2.generated.impl.RoleTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 7, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("Library" == ___local)) {
                        _Role.add(((extensionTools.opl2.generated.impl.RoleTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 7, ___uri, ___local)));
                        return ;
                    }
                    break;
            }
            super.enterAttribute(___uri, ___local);
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  10 :
                    revertToParentFromLeaveAttribute(___uri, ___local);
                    return ;
            }
            super.leaveAttribute(___uri, ___local);
        }

        public void text(java.lang.String value)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            try {
                switch (state) {
                    case  10 :
                        revertToParentFromText(value);
                        return ;
                    case  1 :
                        try {
                            _ProcessName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 2;
                        return ;
                    case  4 :
                        try {
                            _LogicalRelation = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 5;
                        return ;
                    case  11 :
                        try {
                            _PathLabel.add(value);
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 12;
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
                case  7 :
                    state = 7;
                    return ;
                case  9 :
                    state = 9;
                    return ;
            }
            super.leaveChild(nextState);
        }

        private void goto8()
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            int idx;
            state = 8;
            idx = context.getAttribute("", "subjectAggregationFatherRole");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectAggregationFatherName");
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
            idx = context.getAttribute("", "subjectExhibitionFatherName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectThingName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "objectType");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectThingRole");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
        }

        private void goto6()
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            int idx;
            state = 6;
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

    }

}
