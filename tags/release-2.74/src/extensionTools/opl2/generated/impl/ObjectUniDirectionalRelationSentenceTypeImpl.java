//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

public class ObjectUniDirectionalRelationSentenceTypeImpl implements extensionTools.opl2.generated.ObjectUniDirectionalRelationSentenceType, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    protected java.lang.String _RelationName;
    protected boolean has_DestinationMaximalCardinality;
    protected int _DestinationMaximalCardinality;
    protected boolean has_SourceMaximalCardinality;
    protected int _SourceMaximalCardinality;
    protected extensionTools.opl2.generated.DestinationRoleType _DestinationRole;
    protected java.lang.String _SourceName;
    protected java.lang.String _DestinationName;
    protected extensionTools.opl2.generated.SourceRoleType _SourceRole;
    protected boolean has_SourceMinimalCardinality;
    protected int _SourceMinimalCardinality;
    protected boolean has_DestinationMinimalCardinality;
    protected int _DestinationMinimalCardinality;
    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/grammar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0002xp\u0011\u009fY\u00b1ppsq\u0000~\u0000\u0000\u000e\u00d9.jppsq\u0000~\u0000\u0000\r\u0017%\u009appsq\u0000~\u0000\u0000\u000bc\u00eblppsq\u0000~\u0000\u0000\t\u00b0\u00b1>ppsq\u0000~\u0000\u0000\u0007\u00ee\u00a8nppsq\u0000~\u0000\u0000\u0005(}\'ppsq\u0000~\u0000\u0000\u0003ftWppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001\u00b3:)pp\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003\u0001\u00b3:\u001eppsr\u0000 com.sun.msv.datatype.xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u001aL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0003intsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$2\u0087z9\u00ee\u00f8,N\u0005\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001aL\u0000\fnamespaceURIq\u0000~\u0000\u001axpq\u0000~\u0000\u001eq\u0000~\u0000\u001dsr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001aL\u0000\fnamespaceURIq\u0000~\u0000\u001axr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0018SourceMinimalCardinalityt\u0000\u0000sq\u0000~\u0000\r\u0001\u00b3:)pp\u0000q\u0000~\u0000\u0014sq\u0000~\u0000&t\u0000\u0018SourceMaximalCardinalityq\u0000~\u0000*sq\u0000~\u0000\r\u0001\u00c2\b\u00cbpp\u0000sq\u0000~\u0000\u0011\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000\u0017q\u0000~\u0000\u001dt\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xq\u0000~\u0000 \u0001q\u0000~\u0000#sq\u0000~\u0000$q\u0000~\u00002q\u0000~\u0000\u001dsq\u0000~\u0000&t\u0000\nSourceNameq\u0000~\u0000*sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001\u0002\u00c6+Bppsq\u0000~\u00008\u0002\u00c6+7sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\r\u0001c\u0015\u00a0q\u0000~\u0000<p\u0000sq\u0000~\u0000\r\u0001c\u0015\u0095pp\u0000sq\u0000~\u00008\u0001c\u0015\u008appsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001c\u0015\u007fq\u0000~\u0000<psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u000exq\u0000~\u0000\u0003\u0001c\u0015|q\u0000~\u0000<psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\bsq\u0000~\u0000;\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\'sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\tq\u0000~\u0000Gpsq\u0000~\u0000&t\u0000,extensionTools.opl2.generated.SourceRoleTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000&t\u0000\nSourceRoleq\u0000~\u0000*sq\u0000~\u0000\r\u0001c\u0015\u0095q\u0000~\u0000<p\u0000sq\u0000~\u00008\u0001c\u0015\u008appsq\u0000~\u0000@\u0001c\u0015\u007fq\u0000~\u0000<psq\u0000~\u0000C\u0001c\u0015|q\u0000~\u0000<pq\u0000~\u0000Fq\u0000~\u0000Iq\u0000~\u0000Ksq\u0000~\u0000&t\u0000(extensionTools.opl2.generated.SourceRoleq\u0000~\u0000Nq\u0000~\u0000Ksq\u0000~\u0000\r\u0001\u00c2\b\u00cbpp\u0000q\u0000~\u0000/sq\u0000~\u0000&t\u0000\fRelationNameq\u0000~\u0000*sq\u0000~\u0000\r\u0001\u00b3:)pp\u0000q\u0000~\u0000\u0014sq\u0000~\u0000&t\u0000\u001dDestinationMinimalCardinalityq\u0000~\u0000*sq\u0000~\u0000\r\u0001\u00b3:)pp\u0000q\u0000~\u0000\u0014sq\u0000~\u0000&t\u0000\u001dDestinationMaximalCardinalityq\u0000~\u0000*sq\u0000~\u0000\r\u0001\u00c2\b\u00cbpp\u0000q\u0000~\u0000/sq\u0000~\u0000&t\u0000\u000fDestinationNameq\u0000~\u0000*sq\u0000~\u00008\u0002\u00c6+Bppsq\u0000~\u00008\u0002\u00c6+7q\u0000~\u0000<psq\u0000~\u0000\r\u0001c\u0015\u00a0q\u0000~\u0000<p\u0000sq\u0000~\u0000\r\u0001c\u0015\u0095pp\u0000sq\u0000~\u00008\u0001c\u0015\u008appsq\u0000~\u0000@\u0001c\u0015\u007fq\u0000~\u0000<psq\u0000~\u0000C\u0001c\u0015|q\u0000~\u0000<pq\u0000~\u0000Fq\u0000~\u0000Iq\u0000~\u0000Ksq\u0000~\u0000&t\u00001extensionTools.opl2.generated.DestinationRoleTypeq\u0000~\u0000Nsq\u0000~\u0000&t\u0000\u000fDestinationRoleq\u0000~\u0000*sq\u0000~\u0000\r\u0001c\u0015\u0095q\u0000~\u0000<p\u0000sq\u0000~\u00008\u0001c\u0015\u008appsq\u0000~\u0000@\u0001c\u0015\u007fq\u0000~\u0000<psq\u0000~\u0000C\u0001c\u0015|q\u0000~\u0000<pq\u0000~\u0000Fq\u0000~\u0000Iq\u0000~\u0000Ksq\u0000~\u0000&t\u0000-extensionTools.opl2.generated.DestinationRoleq\u0000~\u0000Nq\u0000~\u0000Ksr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000u[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\u0014\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\nq\u0000~\u0000\tppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000:q\u0000~\u0000dq\u0000~\u0000\u0005ppppppppq\u0000~\u00009q\u0000~\u0000cppppppppppppppq\u0000~\u0000Bq\u0000~\u0000Sq\u0000~\u0000\bq\u0000~\u0000hq\u0000~\u0000pppppppq\u0000~\u0000?q\u0000~\u0000Rq\u0000~\u0000gq\u0000~\u0000opppppppppppq\u0000~\u0000\fq\u0000~\u0000\u000bpppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\u0007q\u0000~\u0000\u0006ppppppppppppp");

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.ObjectUniDirectionalRelationSentenceType.class;
    }

    public java.lang.String getRelationName() {
        return _RelationName;
    }

    public void setRelationName(java.lang.String value) {
        _RelationName = value;
    }

    public int getDestinationMaximalCardinality() {
        return _DestinationMaximalCardinality;
    }

    public void setDestinationMaximalCardinality(int value) {
        _DestinationMaximalCardinality = value;
        has_DestinationMaximalCardinality = true;
    }

    public int getSourceMaximalCardinality() {
        return _SourceMaximalCardinality;
    }

    public void setSourceMaximalCardinality(int value) {
        _SourceMaximalCardinality = value;
        has_SourceMaximalCardinality = true;
    }

    public extensionTools.opl2.generated.DestinationRoleType getDestinationRole() {
        return _DestinationRole;
    }

    public void setDestinationRole(extensionTools.opl2.generated.DestinationRoleType value) {
        _DestinationRole = value;
    }

    public java.lang.String getSourceName() {
        return _SourceName;
    }

    public void setSourceName(java.lang.String value) {
        _SourceName = value;
    }

    public java.lang.String getDestinationName() {
        return _DestinationName;
    }

    public void setDestinationName(java.lang.String value) {
        _DestinationName = value;
    }

    public extensionTools.opl2.generated.SourceRoleType getSourceRole() {
        return _SourceRole;
    }

    public void setSourceRole(extensionTools.opl2.generated.SourceRoleType value) {
        _SourceRole = value;
    }

    public int getSourceMinimalCardinality() {
        return _SourceMinimalCardinality;
    }

    public void setSourceMinimalCardinality(int value) {
        _SourceMinimalCardinality = value;
        has_SourceMinimalCardinality = true;
    }

    public int getDestinationMinimalCardinality() {
        return _DestinationMinimalCardinality;
    }

    public void setDestinationMinimalCardinality(int value) {
        _DestinationMinimalCardinality = value;
        has_DestinationMinimalCardinality = true;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ObjectUniDirectionalRelationSentenceTypeImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "SourceMinimalCardinality");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInt(((int) _SourceMinimalCardinality)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "SourceMaximalCardinality");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInt(((int) _SourceMaximalCardinality)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "SourceName");
        context.endAttributes();
        try {
            context.text(((java.lang.String) _SourceName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        if (_SourceRole!= null) {
            if (_SourceRole instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _SourceRole));
            } else {
                context.startElement("", "SourceRole");
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _SourceRole));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _SourceRole));
                context.endElement();
            }
        }
        context.startElement("", "RelationName");
        context.endAttributes();
        try {
            context.text(((java.lang.String) _RelationName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "DestinationMinimalCardinality");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInt(((int) _DestinationMinimalCardinality)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "DestinationMaximalCardinality");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printInt(((int) _DestinationMaximalCardinality)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "DestinationName");
        context.endAttributes();
        try {
            context.text(((java.lang.String) _DestinationName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        if (_DestinationRole!= null) {
            if (_DestinationRole instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _DestinationRole));
            } else {
                context.startElement("", "DestinationRole");
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _DestinationRole));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _DestinationRole));
                context.endElement();
            }
        }
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
        return (extensionTools.opl2.generated.ObjectUniDirectionalRelationSentenceType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.xml.bind.unmarshaller.ContentHandlerEx
    {


        public Unmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
            super(context, "--------------------------");
        }

        protected com.sun.xml.bind.unmarshaller.UnmarshallableObject owner() {
            return extensionTools.opl2.generated.impl.ObjectUniDirectionalRelationSentenceTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  20 :
                    if (("" == ___uri)&&("DestinationName" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 21;
                        return ;
                    }
                    break;
                case  9 :
                    if (("" == ___uri)&&("SourceRole" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 10;
                        return ;
                    }
                    if (("" == ___uri)&&("RelationName" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 12;
                        return ;
                    }
                    break;
                case  23 :
                    if (("" == ___uri)&&("DestinationRole" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 24;
                        return ;
                    }
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
                case  3 :
                    if (("" == ___uri)&&("SourceMaximalCardinality" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 4;
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("SourceMinimalCardinality" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 1;
                        return ;
                    }
                    break;
                case  6 :
                    if (("" == ___uri)&&("SourceName" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 7;
                        return ;
                    }
                    break;
                case  14 :
                    if (("" == ___uri)&&("DestinationMinimalCardinality" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 15;
                        return ;
                    }
                    break;
                case  10 :
                    if (("" == ___uri)&&("Role" == ___local)) {
                        _SourceRole = ((extensionTools.opl2.generated.impl.SourceRoleTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.SourceRoleTypeImpl.class), 11, ___uri, ___local, __atts));
                        return ;
                    }
                    break;
                case  17 :
                    if (("" == ___uri)&&("DestinationMaximalCardinality" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 18;
                        return ;
                    }
                    break;
                case  24 :
                    if (("" == ___uri)&&("Role" == ___local)) {
                        _DestinationRole = ((extensionTools.opl2.generated.impl.DestinationRoleTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.DestinationRoleTypeImpl.class), 25, ___uri, ___local, __atts));
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
                case  5 :
                    if (("" == ___uri)&&("SourceMaximalCardinality" == ___local)) {
                        context.popAttributes();
                        state = 6;
                        return ;
                    }
                    break;
                case  23 :
                    revertToParentFromLeaveElement(___uri, ___local);
                    return ;
                case  13 :
                    if (("" == ___uri)&&("RelationName" == ___local)) {
                        context.popAttributes();
                        state = 14;
                        return ;
                    }
                    break;
                case  19 :
                    if (("" == ___uri)&&("DestinationMaximalCardinality" == ___local)) {
                        context.popAttributes();
                        state = 20;
                        return ;
                    }
                    break;
                case  16 :
                    if (("" == ___uri)&&("DestinationMinimalCardinality" == ___local)) {
                        context.popAttributes();
                        state = 17;
                        return ;
                    }
                    break;
                case  8 :
                    if (("" == ___uri)&&("SourceName" == ___local)) {
                        context.popAttributes();
                        state = 9;
                        return ;
                    }
                    break;
                case  2 :
                    if (("" == ___uri)&&("SourceMinimalCardinality" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  11 :
                    if (("" == ___uri)&&("SourceRole" == ___local)) {
                        context.popAttributes();
                        state = 9;
                        return ;
                    }
                    break;
                case  25 :
                    if (("" == ___uri)&&("DestinationRole" == ___local)) {
                        context.popAttributes();
                        state = 23;
                        return ;
                    }
                    break;
                case  22 :
                    if (("" == ___uri)&&("DestinationName" == ___local)) {
                        context.popAttributes();
                        state = 23;
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
                case  23 :
                    revertToParentFromEnterAttribute(___uri, ___local);
                    return ;
            }
            super.enterAttribute(___uri, ___local);
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  23 :
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
                    case  1 :
                        try {
                            _SourceMinimalCardinality = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_SourceMinimalCardinality = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 2;
                        return ;
                    case  18 :
                        try {
                            _DestinationMaximalCardinality = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_DestinationMaximalCardinality = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 19;
                        return ;
                    case  23 :
                        revertToParentFromText(value);
                        return ;
                    case  4 :
                        try {
                            _SourceMaximalCardinality = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_SourceMaximalCardinality = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 5;
                        return ;
                    case  15 :
                        try {
                            _DestinationMinimalCardinality = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_DestinationMinimalCardinality = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 16;
                        return ;
                    case  7 :
                        try {
                            _SourceName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 8;
                        return ;
                    case  21 :
                        try {
                            _DestinationName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 22;
                        return ;
                    case  12 :
                        try {
                            _RelationName = value;
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
                case  25 :
                    state = 25;
                    return ;
            }
            super.leaveChild(nextState);
        }

    }

}
