//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

import extensionTools.opl2.generated.impl.EnablingClauseTypeImpl.Unmarshaller;

public class EnablingClauseImpl
    extends extensionTools.opl2.generated.impl.EnablingClauseTypeImpl
    implements extensionTools.opl2.generated.EnablingClause, com.sun.xml.bind.RIElement, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xp\u001f\u00cf\u00f5\u00fepp\u0000sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004\u001f\u00cf\u00f5\u00f3ppsq\u0000~\u0000\u0007\u001dY.\u00e5ppsq\u0000~\u0000\u0007\u0019\u00a6nLppsq\u0000~\u0000\u0007\u0017a\u00ec\u0013ppsq\u0000~\u0000\u0007\u0014\u00fa\u00a5ippsq\u0000~\u0000\u0007\u0011\u00dbN\u00b2ppsq\u0000~\u0000\u0007\u000f\u00deF]ppsq\u0000~\u0000\u0007\rb\u00e5\u00a2ppsq\u0000~\u0000\u0007\t\u00b0\u00b1Lppsq\u0000~\u0000\u0007\u0006\u00ea\u0086\u0002ppsq\u0000~\u0000\u0007\u0005(}2ppsq\u0000~\u0000\u0007\u0003ftWppsq\u0000~\u0000\u0000\u0001\u00b3:)pp\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004\u0001\u00b3:\u001eppsr\u0000 com.sun.msv.datatype.xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u001fL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0003intsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$2\u0087z9\u00ee\u00f8,N\u0005\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001fL\u0000\fnamespaceURIq\u0000~\u0000\u001fxpq\u0000~\u0000#q\u0000~\u0000\"sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001fL\u0000\fnamespaceURIq\u0000~\u0000\u001fxr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0012MinimalCardinalityt\u0000\u0000sq\u0000~\u0000\u0000\u0001\u00b3:)pp\u0000q\u0000~\u0000\u0019sq\u0000~\u0000+t\u0000\u0012MaximalCardinalityq\u0000~\u0000/sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\b\u0001\u00c2\b\u00d6ppsq\u0000~\u0000\u0000\u0001\u00c2\b\u00cbsr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000p\u0000sq\u0000~\u0000\u0016\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000\u001cq\u0000~\u0000\"t\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xq\u0000~\u0000%\u0001q\u0000~\u0000(sq\u0000~\u0000)q\u0000~\u0000;q\u0000~\u0000\"sq\u0000~\u0000+t\u0000\tStateNameq\u0000~\u0000/sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004\u0000\u0000\u0000\tsq\u0000~\u00006\u0001psq\u0000~\u0000\u0000\u0001\u00c2\b\u00cbpp\u0000q\u0000~\u00008sq\u0000~\u0000+t\u0000\nObjectNameq\u0000~\u0000/sq\u0000~\u00003\u0002\u00c6+Eppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004\u0002\u00c6+:q\u0000~\u00007psq\u0000~\u00003\u0002\u00c6+7q\u0000~\u00007psq\u0000~\u0000\u0000\u0001c\u0015\u00a0q\u0000~\u00007p\u0000sq\u0000~\u0000\u0000\u0001c\u0015\u0095pp\u0000sq\u0000~\u00003\u0001c\u0015\u008appsq\u0000~\u0000H\u0001c\u0015\u007fq\u0000~\u00007psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004\u0001c\u0015|q\u0000~\u00007psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004\u0000\u0000\u0000\bq\u0000~\u0000Cpsr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000,q\u0000~\u0000Bsq\u0000~\u0000+t\u0000&extensionTools.opl2.generated.RoleTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000+t\u0000\u0004Roleq\u0000~\u0000/sq\u0000~\u0000\u0000\u0001c\u0015\u0095q\u0000~\u00007p\u0000sq\u0000~\u00003\u0001c\u0015\u008appsq\u0000~\u0000H\u0001c\u0015\u007fq\u0000~\u00007psq\u0000~\u0000P\u0001c\u0015|q\u0000~\u00007pq\u0000~\u0000Sq\u0000~\u0000Uq\u0000~\u0000Bsq\u0000~\u0000+t\u0000\"extensionTools.opl2.generated.Roleq\u0000~\u0000Xq\u0000~\u0000Bsq\u0000~\u00003\u0003\u00b24Qppsq\u0000~\u0000P\u0003\u00b24Fq\u0000~\u00007pq\u0000~\u00008sq\u0000~\u0000+t\u0000\u000flogicalRelationq\u0000~\u0000/q\u0000~\u0000Bsq\u0000~\u00003\u0002{`\u00b6ppsq\u0000~\u0000P\u0002{`\u00abq\u0000~\u00007pq\u0000~\u00008sq\u0000~\u0000+t\u0000\u001csubjectAggregationFatherRoleq\u0000~\u0000/q\u0000~\u0000Bsq\u0000~\u00003\u0001\u00fd\bPppsq\u0000~\u0000P\u0001\u00fd\bEq\u0000~\u00007pq\u0000~\u00008sq\u0000~\u0000+t\u0000\u001csubjectAggregationFatherNameq\u0000~\u0000/q\u0000~\u0000Bsq\u0000~\u00003\u0003\u001fV\u00b2ppsq\u0000~\u0000P\u0003\u001fV\u00a7q\u0000~\u00007pq\u0000~\u00008sq\u0000~\u0000+t\u0000\u001bsubjectExhibitionFatherRoleq\u0000~\u0000/q\u0000~\u0000Bsq\u0000~\u0000P\u0002gF\u00a5ppq\u0000~\u00008sq\u0000~\u0000+t\u0000\nobjectTypeq\u0000~\u0000/sq\u0000~\u00003\u0002D\u00824ppsq\u0000~\u0000P\u0002D\u0082)q\u0000~\u00007pq\u0000~\u00008sq\u0000~\u0000+t\u0000\u001bsubjectExhibitionFatherNameq\u0000~\u0000/q\u0000~\u0000Bsq\u0000~\u0000P\u0003\u00b2\u00c0\u0094ppq\u0000~\u00008sq\u0000~\u0000+t\u0000\u0010subjectThingNameq\u0000~\u0000/sq\u0000~\u00003\u0002v\u00c7\tppsq\u0000~\u0000P\u0002v\u00c6\u00feq\u0000~\u00007pq\u0000~\u00008sq\u0000~\u0000+t\u0000\u0010subjectThingRoleq\u0000~\u0000/q\u0000~\u0000Bsq\u0000~\u0000+t\u0000\u000eEnablingClauseq\u0000~\u0000/sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000\u0082[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\u001a\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfppppppq\u0000~\u00004q\u0000~\u0000apppppppppq\u0000~\u0000epppppq\u0000~\u0000\fppppppppppppppppppq\u0000~\u0000\tpppppppppq\u0000~\u0000\u0011pppppppppppq\u0000~\u0000\u0010ppq\u0000~\u0000\rq\u0000~\u0000tppppppppppq\u0000~\u0000mq\u0000~\u0000Kppq\u0000~\u0000Jq\u0000~\u0000ipq\u0000~\u0000\u000fpppppppq\u0000~\u0000Gpq\u0000~\u0000\u000bppppppppppq\u0000~\u0000Oq\u0000~\u0000]q\u0000~\u0000\nppppppppq\u0000~\u0000Nq\u0000~\u0000\\pq\u0000~\u0000{pppppppppppq\u0000~\u0000\u0014ppppppppppq\u0000~\u0000\u0013q\u0000~\u0000\u0012ppppppppppppppppppppppppppppq\u0000~\u0000\u000epppppppppppppppp");

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "EnablingClause";
    }

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.EnablingClause.class;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.EnablingClauseImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "EnablingClause");
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeElements(context);
        context.endElement();
    }

    public void serializeAttributes(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeAttributeBodies(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.EnablingClause.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.xml.bind.unmarshaller.ContentHandlerEx
    {


        public Unmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
            super(context, "----");
        }

        protected com.sun.xml.bind.unmarshaller.UnmarshallableObject owner() {
            return extensionTools.opl2.generated.impl.EnablingClauseImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  3 :
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
                case  1 :
                    if (("" == ___uri)&&("MinimalCardinality" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("EnablingClause" == ___local)) {
                        context.pushAttributes(__atts);
                        goto1();
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
                case  2 :
                    if (("" == ___uri)&&("EnablingClause" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  3 :
                    revertToParentFromLeaveElement(___uri, ___local);
                    return ;
            }
            super.leaveElement(___uri, ___local);
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  3 :
                    revertToParentFromEnterAttribute(___uri, ___local);
                    return ;
                case  1 :
                    if (("" == ___uri)&&("subjectExhibitionFatherRole" == ___local)) {
                        spawnSuperClassFromEnterAttribute((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
                        return ;
                    }
                    if (("" == ___uri)&&("subjectThingRole" == ___local)) {
                        spawnSuperClassFromEnterAttribute((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
                        return ;
                    }
                    if (("" == ___uri)&&("subjectExhibitionFatherName" == ___local)) {
                        spawnSuperClassFromEnterAttribute((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
                        return ;
                    }
                    if (("" == ___uri)&&("objectType" == ___local)) {
                        spawnSuperClassFromEnterAttribute((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
                        return ;
                    }
                    if (("" == ___uri)&&("subjectAggregationFatherRole" == ___local)) {
                        spawnSuperClassFromEnterAttribute((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
                        return ;
                    }
                    if (("" == ___uri)&&("subjectThingName" == ___local)) {
                        spawnSuperClassFromEnterAttribute((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
                        return ;
                    }
                    if (("" == ___uri)&&("logicalRelation" == ___local)) {
                        spawnSuperClassFromEnterAttribute((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
                        return ;
                    }
                    if (("" == ___uri)&&("subjectAggregationFatherName" == ___local)) {
                        spawnSuperClassFromEnterAttribute((((extensionTools.opl2.generated.impl.EnablingClauseTypeImpl)extensionTools.opl2.generated.impl.EnablingClauseImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
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
                case  3 :
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
                    case  3 :
                        revertToParentFromText(value);
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
                case  2 :
                    state = 2;
                    return ;
            }
            super.leaveChild(nextState);
        }

        private void goto1()
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            int idx;
            state = 1;
            idx = context.getAttribute("", "logicalRelation");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
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
            idx = context.getAttribute("", "subjectExhibitionFatherRole");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "objectType");
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
            idx = context.getAttribute("", "subjectThingRole");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
        }

    }

}