//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

import extensionTools.opl2.generated.impl.TimeValueTypeImpl.Unmarshaller;

public class TimeValueImpl
    extends extensionTools.opl2.generated.impl.TimeValueTypeImpl
    implements extensionTools.opl2.generated.TimeValue, com.sun.xml.bind.RIElement, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xp\r\u0099\u00d1\u00cepp\u0000sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004\r\u0099\u00d1\u00c3ppsq\u0000~\u0000\u0007\u000b\u00e6\u0097\u008appsq\u0000~\u0000\u0007\n3]Qppsq\u0000~\u0000\u0007\b\u0080#\u0018ppsq\u0000~\u0000\u0007\u0006\u00cc\u00e8\u00dfppsq\u0000~\u0000\u0007\u0005\u0019\u00ae\u00a6ppsq\u0000~\u0000\u0007\u0003ftmppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\b\u0001\u00b3:4ppsq\u0000~\u0000\u0000\u0001\u00b3:)sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000p\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004\u0001\u00b3:\u001eppsr\u0000 com.sun.msv.datatype.xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u001eL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0003intsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$2\u0087z9\u00ee\u00f8,N\u0005\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001eL\u0000\fnamespaceURIq\u0000~\u0000\u001expq\u0000~\u0000\"q\u0000~\u0000!sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001eL\u0000\fnamespaceURIq\u0000~\u0000\u001exr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0005Yearst\u0000\u0000sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004\u0000\u0000\u0000\tsq\u0000~\u0000\u0013\u0001psq\u0000~\u0000\u0010\u0001\u00b3:4ppsq\u0000~\u0000\u0000\u0001\u00b3:)q\u0000~\u0000\u0014p\u0000q\u0000~\u0000\u0018sq\u0000~\u0000*t\u0000\u0006Monthsq\u0000~\u0000.q\u0000~\u00000sq\u0000~\u0000\u0010\u0001\u00b3:4ppsq\u0000~\u0000\u0000\u0001\u00b3:)q\u0000~\u0000\u0014p\u0000q\u0000~\u0000\u0018sq\u0000~\u0000*t\u0000\u0005Weeksq\u0000~\u0000.q\u0000~\u00000sq\u0000~\u0000\u0010\u0001\u00b3:4ppsq\u0000~\u0000\u0000\u0001\u00b3:)q\u0000~\u0000\u0014p\u0000q\u0000~\u0000\u0018sq\u0000~\u0000*t\u0000\u0004Daysq\u0000~\u0000.q\u0000~\u00000sq\u0000~\u0000\u0010\u0001\u00b3:4ppsq\u0000~\u0000\u0000\u0001\u00b3:)q\u0000~\u0000\u0014p\u0000q\u0000~\u0000\u0018sq\u0000~\u0000*t\u0000\u0005Hoursq\u0000~\u0000.q\u0000~\u00000sq\u0000~\u0000\u0010\u0001\u00b3:4ppsq\u0000~\u0000\u0000\u0001\u00b3:)q\u0000~\u0000\u0014p\u0000q\u0000~\u0000\u0018sq\u0000~\u0000*t\u0000\u0007Minutesq\u0000~\u0000.q\u0000~\u00000sq\u0000~\u0000\u0010\u0001\u00b3:4ppsq\u0000~\u0000\u0000\u0001\u00b3:)q\u0000~\u0000\u0014p\u0000q\u0000~\u0000\u0018sq\u0000~\u0000*t\u0000\u0007Secondsq\u0000~\u0000.q\u0000~\u00000sq\u0000~\u0000\u0010\u0001\u00b3:4ppsq\u0000~\u0000\u0000\u0001\u00b3:)q\u0000~\u0000\u0014p\u0000q\u0000~\u0000\u0018sq\u0000~\u0000*t\u0000\fMilliSecondsq\u0000~\u0000.q\u0000~\u00000sq\u0000~\u0000*t\u0000\tTimeValueq\u0000~\u0000.sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000Q[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\u000f\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfpppppppppppppq\u0000~\u0000\fppppppppppppppppppppppppppppppq\u0000~\u0000\u000epppppppppppppppppq\u0000~\u0000\tppppppppppppq\u0000~\u0000\u0011q\u0000~\u00002q\u0000~\u00006q\u0000~\u0000:q\u0000~\u0000>q\u0000~\u0000Bq\u0000~\u0000Fq\u0000~\u0000Jppppppppppq\u0000~\u0000\u000bppppppppppppppppppppppppppppppq\u0000~\u0000\rppppppppppppppppppppppppppppppq\u0000~\u0000\u000fpppppppppppppppppq\u0000~\u0000\nppppppppppppppppp");

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "TimeValue";
    }

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.TimeValue.class;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.TimeValueImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "TimeValue");
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
        return (extensionTools.opl2.generated.TimeValue.class);
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
            return extensionTools.opl2.generated.impl.TimeValueImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  0 :
                    if (("" == ___uri)&&("TimeValue" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 1;
                        return ;
                    }
                    break;
                case  1 :
                    if (("" == ___uri)&&("Hours" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    if (("" == ___uri)&&("Weeks" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    if (("" == ___uri)&&("Months" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    if (("" == ___uri)&&("Minutes" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    if (("" == ___uri)&&("Seconds" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    if (("" == ___uri)&&("Days" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    if (("" == ___uri)&&("Years" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    if (("" == ___uri)&&("MilliSeconds" == ___local)) {
                        spawnSuperClassFromEnterElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, __atts);
                        return ;
                    }
                    break;
                case  3 :
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
            }
            super.enterElement(___uri, ___local, __atts);
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  2 :
                    if (("" == ___uri)&&("TimeValue" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  1 :
                    if (("" == ___uri)&&("TimeValue" == ___local)) {
                        spawnSuperClassFromLeaveElement((((extensionTools.opl2.generated.impl.TimeValueTypeImpl)extensionTools.opl2.generated.impl.TimeValueImpl.this).new Unmarshaller(context)), 2, ___uri, ___local);
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