//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

public class ProcessInZoomingSentenceTypeImpl implements extensionTools.opl2.generated.ProcessInZoomingSentenceType, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    protected java.lang.String _ProcessName;
    protected com.sun.xml.bind.util.ListImpl _InZoomedProcess = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    protected com.sun.xml.bind.util.ListImpl _InZoomedObject = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/grammar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0002xp\u0005F\u001a\u0087ppsq\u0000~\u0000\u0000\u0003\u0084\u0011\u00a9ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001\u00c2\b\u00cbpp\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0013L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0013L\u0000\fnamespaceURIq\u0000~\u0000\u0013xpq\u0000~\u0000\u0017q\u0000~\u0000\u0016sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0013L\u0000\fnamespaceURIq\u0000~\u0000\u0013xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u000bProcessNamet\u0000\u0000sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001\u0001\u00c2\b\u00d9ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001\u00c2\b\u00cesr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0007\u0001\u00c2\b\u00cbq\u0000~\u0000*p\u0000q\u0000~\u0000\u000esq\u0000~\u0000\u001ft\u0000\u000fInZoomedProcessq\u0000~\u0000#sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\tsq\u0000~\u0000)\u0001psq\u0000~\u0000$\u0001\u00c2\b\u00d9ppsq\u0000~\u0000&\u0001\u00c2\b\u00ceq\u0000~\u0000*psq\u0000~\u0000\u0007\u0001\u00c2\b\u00cbq\u0000~\u0000*p\u0000q\u0000~\u0000\u000esq\u0000~\u0000\u001ft\u0000\u000eInZoomedObjectq\u0000~\u0000#q\u0000~\u0000/sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u00007[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\u0006\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfpppppppppq\u0000~\u0000%q\u0000~\u0000\u0006q\u0000~\u00001pppppppppppq\u0000~\u0000\u0005pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000(q\u0000~\u00002");

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.ProcessInZoomingSentenceType.class;
    }

    public java.lang.String getProcessName() {
        return _ProcessName;
    }

    public void setProcessName(java.lang.String value) {
        _ProcessName = value;
    }

    public java.util.List getInZoomedProcess() {
        return _InZoomedProcess;
    }

    public java.util.List getInZoomedObject() {
        return _InZoomedObject;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ProcessInZoomingSentenceTypeImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = _InZoomedProcess.size();
        int idx3 = 0;
        final int len3 = _InZoomedObject.size();
        context.startElement("", "ProcessName");
        context.endAttributes();
        try {
            context.text(((java.lang.String) _ProcessName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx2 != len2) {
            context.startElement("", "InZoomedProcess");
            int idx_1 = idx2;
            try {
                idx_1 += 1;
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttributes();
            try {
                context.text(((java.lang.String) _InZoomedProcess.get(idx2 ++)));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        while (idx3 != len3) {
            context.startElement("", "InZoomedObject");
            int idx_2 = idx3;
            try {
                idx_2 += 1;
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttributes();
            try {
                context.text(((java.lang.String) _InZoomedObject.get(idx3 ++)));
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
        final int len2 = _InZoomedProcess.size();
        int idx3 = 0;
        final int len3 = _InZoomedObject.size();
    }

    public void serializeAttributeBodies(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = _InZoomedProcess.size();
        int idx3 = 0;
        final int len3 = _InZoomedObject.size();
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ProcessInZoomingSentenceType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.xml.bind.unmarshaller.ContentHandlerEx
    {


        public Unmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
            super(context, "--------");
        }

        protected com.sun.xml.bind.unmarshaller.UnmarshallableObject owner() {
            return extensionTools.opl2.generated.impl.ProcessInZoomingSentenceTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  0 :
                    if (("" == ___uri)&&("ProcessName" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 1;
                        return ;
                    }
                    break;
                case  3 :
                    if (("" == ___uri)&&("InZoomedObject" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 6;
                        return ;
                    }
                    if (("" == ___uri)&&("InZoomedProcess" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 4;
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
                case  5 :
                    if (("" == ___uri)&&("InZoomedProcess" == ___local)) {
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
                case  7 :
                    if (("" == ___uri)&&("InZoomedObject" == ___local)) {
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
                            _InZoomedProcess.add(value);
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 5;
                        return ;
                    case  6 :
                        try {
                            _InZoomedObject.add(value);
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 7;
                        return ;
                    case  3 :
                        revertToParentFromText(value);
                        return ;
                }
            } catch (java.lang.RuntimeException e) {
                handleUnexpectedTextException(value, e);
            }
        }

    }

}
