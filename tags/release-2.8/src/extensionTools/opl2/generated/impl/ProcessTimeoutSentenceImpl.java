//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

import extensionTools.opl2.generated.impl.ProcessTimeoutSentenceTypeImpl.Unmarshaller;

public class ProcessTimeoutSentenceImpl
    extends extensionTools.opl2.generated.impl.ProcessTimeoutSentenceTypeImpl
    implements extensionTools.opl2.generated.ProcessTimeoutSentence, com.sun.xml.bind.RIElement, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xp\u0012 \u00d0kpp\u0000sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004\u0012 \u00d0`ppsq\u0000~\u0000\u0007\u000fZ\u00a5$ppsq\u0000~\u0000\u0007\f\u0094y\u00ddppsq\u0000~\u0000\u0007\t\u00ceN\u0096ppsq\u0000~\u0000\u0007\b\fE\u00b8ppsq\u0000~\u0000\u0007\u0006J<\u00e5ppsq\u0000~\u0000\u0007\u0004\u00884\u0015ppsq\u0000~\u0000\u0000\u0001\u00c2\b\u00cbpp\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0019L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0019L\u0000\fnamespaceURIq\u0000~\u0000\u0019xpq\u0000~\u0000\u001dq\u0000~\u0000\u001csr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0019L\u0000\fnamespaceURIq\u0000~\u0000\u0019xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u000bProcessNamet\u0000\u0000sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\b\u0002\u00c6+Eppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004\u0002\u00c6+:sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000*\u0002\u00c6+7q\u0000~\u00000psq\u0000~\u0000\u0000\u0001c\u0015\u00a0q\u0000~\u00000p\u0000sq\u0000~\u0000\u0000\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000*\u0001c\u0015\u008appsq\u0000~\u0000,\u0001c\u0015\u007fq\u0000~\u00000psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004\u0001c\u0015|q\u0000~\u00000psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004\u0000\u0000\u0000\bsq\u0000~\u0000/\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000&sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004\u0000\u0000\u0000\tq\u0000~\u0000:psq\u0000~\u0000%t\u0000&extensionTools.opl2.generated.RoleTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000%t\u0000\u0004Roleq\u0000~\u0000)sq\u0000~\u0000\u0000\u0001c\u0015\u0095q\u0000~\u00000p\u0000sq\u0000~\u0000*\u0001c\u0015\u008appsq\u0000~\u0000,\u0001c\u0015\u007fq\u0000~\u00000psq\u0000~\u00006\u0001c\u0015|q\u0000~\u00000pq\u0000~\u00009q\u0000~\u0000<q\u0000~\u0000>sq\u0000~\u0000%t\u0000\"extensionTools.opl2.generated.Roleq\u0000~\u0000Aq\u0000~\u0000>sq\u0000~\u0000\u0000\u0001\u00c2\b\u00cbpp\u0000q\u0000~\u0000\u0014sq\u0000~\u0000%t\u0000\u000fLogicalRelationq\u0000~\u0000)sq\u0000~\u0000,\u0001\u00c2\b\u00ceppsq\u0000~\u0000\u0000\u0001\u00c2\b\u00cbpp\u0000q\u0000~\u0000\u0014sq\u0000~\u0000%t\u0000\u0010TriggeredProcessq\u0000~\u0000)sq\u0000~\u0000*\u0001\u00c2\b\u00d9ppsq\u0000~\u0000,\u0001\u00c2\b\u00ceq\u0000~\u00000psq\u0000~\u0000\u0000\u0001\u00c2\b\u00cbq\u0000~\u00000p\u0000q\u0000~\u0000\u0014sq\u0000~\u0000%t\u0000\tPathLabelq\u0000~\u0000)q\u0000~\u0000>sq\u0000~\u0000*\u0002\u00c6+Bppsq\u0000~\u0000*\u0002\u00c6+7q\u0000~\u00000psq\u0000~\u0000\u0000\u0001c\u0015\u00a0q\u0000~\u00000p\u0000sq\u0000~\u0000\u0000\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000*\u0001c\u0015\u008appsq\u0000~\u0000,\u0001c\u0015\u007fq\u0000~\u00000psq\u0000~\u00006\u0001c\u0015|q\u0000~\u00000pq\u0000~\u00009q\u0000~\u0000<q\u0000~\u0000>sq\u0000~\u0000%t\u00001extensionTools.opl2.generated.MinReactionTimeTypeq\u0000~\u0000Asq\u0000~\u0000%t\u0000\u000fMinReactionTimeq\u0000~\u0000)sq\u0000~\u0000\u0000\u0001c\u0015\u0095q\u0000~\u00000p\u0000sq\u0000~\u0000*\u0001c\u0015\u008appsq\u0000~\u0000,\u0001c\u0015\u007fq\u0000~\u00000psq\u0000~\u00006\u0001c\u0015|q\u0000~\u00000pq\u0000~\u00009q\u0000~\u0000<q\u0000~\u0000>sq\u0000~\u0000%t\u0000-extensionTools.opl2.generated.MinReactionTimeq\u0000~\u0000Aq\u0000~\u0000>sq\u0000~\u0000*\u0002\u00c6+Bppsq\u0000~\u0000*\u0002\u00c6+7q\u0000~\u00000psq\u0000~\u0000\u0000\u0001c\u0015\u00a0q\u0000~\u00000p\u0000sq\u0000~\u0000\u0000\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000*\u0001c\u0015\u008appsq\u0000~\u0000,\u0001c\u0015\u007fq\u0000~\u00000psq\u0000~\u00006\u0001c\u0015|q\u0000~\u00000pq\u0000~\u00009q\u0000~\u0000<q\u0000~\u0000>sq\u0000~\u0000%t\u00001extensionTools.opl2.generated.MaxReactionTimeTypeq\u0000~\u0000Asq\u0000~\u0000%t\u0000\u000fMaxReactionTimeq\u0000~\u0000)sq\u0000~\u0000\u0000\u0001c\u0015\u0095q\u0000~\u00000p\u0000sq\u0000~\u0000*\u0001c\u0015\u008appsq\u0000~\u0000,\u0001c\u0015\u007fq\u0000~\u00000psq\u0000~\u00006\u0001c\u0015|q\u0000~\u00000pq\u0000~\u00009q\u0000~\u0000<q\u0000~\u0000>sq\u0000~\u0000%t\u0000-extensionTools.opl2.generated.MaxReactionTimeq\u0000~\u0000Aq\u0000~\u0000>sq\u0000~\u0000*\u0002\u00c6+7ppsq\u0000~\u0000\u0000\u0001c\u0015\u00a0pp\u0000sq\u0000~\u0000\u0000\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000*\u0001c\u0015\u008appsq\u0000~\u0000,\u0001c\u0015\u007fq\u0000~\u00000psq\u0000~\u00006\u0001c\u0015|q\u0000~\u00000pq\u0000~\u00009q\u0000~\u0000<q\u0000~\u0000>sq\u0000~\u0000%t\u00001extensionTools.opl2.generated.MaxTimeoutValueTypeq\u0000~\u0000Asq\u0000~\u0000%t\u0000\u000fMaxTimeoutValueq\u0000~\u0000)sq\u0000~\u0000\u0000\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000*\u0001c\u0015\u008appsq\u0000~\u0000,\u0001c\u0015\u007fq\u0000~\u00000psq\u0000~\u00006\u0001c\u0015|q\u0000~\u00000pq\u0000~\u00009q\u0000~\u0000<q\u0000~\u0000>sq\u0000~\u0000%t\u0000-extensionTools.opl2.generated.MaxTimeoutValueq\u0000~\u0000Asq\u0000~\u0000%t\u0000\u0016ProcessTimeoutSentenceq\u0000~\u0000)sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000\u008b[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\"\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfppppppq\u0000~\u0000\tppq\u0000~\u0000Qppppppq\u0000~\u0000\u000bpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u00001q\u0000~\u0000Wq\u0000~\u0000hq\u0000~\u0000.q\u0000~\u0000xppppppq\u0000~\u0000Vq\u0000~\u0000gpq\u0000~\u0000+q\u0000~\u0000\u000fq\u0000~\u0000\u000eq\u0000~\u0000\rpppppppppq\u0000~\u00005q\u0000~\u0000Fq\u0000~\u0000[q\u0000~\u0000cq\u0000~\u0000\fq\u0000~\u0000lq\u0000~\u0000tq\u0000~\u0000\nq\u0000~\u0000|q\u0000~\u0000\u0084pq\u0000~\u00004q\u0000~\u0000Eq\u0000~\u0000Zq\u0000~\u0000bq\u0000~\u0000kq\u0000~\u0000sq\u0000~\u0000{q\u0000~\u0000\u0083pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000Mq\u0000~\u0000R");

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "ProcessTimeoutSentence";
    }

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.ProcessTimeoutSentence.class;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ProcessTimeoutSentenceImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "ProcessTimeoutSentence");
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
        return (extensionTools.opl2.generated.ProcessTimeoutSentence.class);
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
            return extensionTools.opl2.generated.impl.ProcessTimeoutSentenceImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  3 :
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
                case  0 :
                    if (("" == ___uri)&&("ProcessTimeoutSentence" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 1;
                        return ;
                    }
                    break;
                case  1 :
                    if (("" == ___uri)&&("ProcessName" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.ProcessTimeoutSentenceTypeImpl)extensionTools.opl2.generated.impl.ProcessTimeoutSentenceImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
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
                case  3 :
                    revertToParentFromLeaveElement(___uri, ___local);
                    return ;
                case  2 :
                    if (("" == ___uri)&&("ProcessTimeoutSentence" == ___local)) {
                        context.popAttributes();
                        state = 3;
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
                case  3 :
                    revertToParentFromEnterAttribute(___uri, ___local);
                    return ;
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

    }

}
