//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

public class OPLscriptTypeImpl implements extensionTools.opl2.generated.OPLscriptType, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    protected java.lang.String _SystemName;
    protected java.lang.String _SystemDescription;
    protected com.sun.xml.bind.util.ListImpl _ThingSentenceSet = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/grammar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0002xp\b.\u00cd5ppsq\u0000~\u0000\u0000\u0004\u00884\u0015ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0002\u00c6+:ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001\u0002\u00c6+7ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001c\u0015\u00a0pp\u0000sq\u0000~\u0000\f\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000\n\u0001c\u0015\u008appsq\u0000~\u0000\u0007\u0001c\u0015\u007fsr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\rxq\u0000~\u0000\u0003\u0001c\u0015|q\u0000~\u0000\u0014psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\bsq\u0000~\u0000\u0013\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\tq\u0000~\u0000\u0019psr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000 xq\u0000~\u0000\u001bt\u00002extensionTools.opl2.generated.ThingSentenceSetTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u001ft\u0000\u0010ThingSentenceSett\u0000\u0000sq\u0000~\u0000\f\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000\n\u0001c\u0015\u008appsq\u0000~\u0000\u0007\u0001c\u0015\u007fq\u0000~\u0000\u0014psq\u0000~\u0000\u0015\u0001c\u0015|q\u0000~\u0000\u0014pq\u0000~\u0000\u0018q\u0000~\u0000\u001cq\u0000~\u0000\u001esq\u0000~\u0000\u001ft\u0000.extensionTools.opl2.generated.ThingSentenceSetq\u0000~\u0000#sq\u0000~\u0000\n\u0001\u00c2\b\u00d6ppsq\u0000~\u0000\f\u0001\u00c2\b\u00cbq\u0000~\u0000\u0014p\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000 L\u0000\btypeNameq\u0000~\u0000 L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000 L\u0000\fnamespaceURIq\u0000~\u0000 xpq\u0000~\u0000:q\u0000~\u00009sq\u0000~\u0000\u001ft\u0000\u0011SystemDescriptionq\u0000~\u0000&q\u0000~\u0000\u001esq\u0000~\u0000\u0015\u0003\u00a6\u0099\u001bppq\u0000~\u00002sq\u0000~\u0000\u001ft\u0000\nsystemNameq\u0000~\u0000&sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000H[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\t\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfppppppq\u0000~\u0000-pppppppppppppppppppppppppppppppq\u0000~\u0000\u0005pppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\u000bppq\u0000~\u0000\tppppppppppq\u0000~\u0000\u0006ppppppppppppq\u0000~\u0000\u0012q\u0000~\u0000)pppppppppq\u0000~\u0000\u0011q\u0000~\u0000(ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.OPLscriptType.class;
    }

    public java.lang.String getSystemName() {
        return _SystemName;
    }

    public void setSystemName(java.lang.String value) {
        _SystemName = value;
    }

    public java.lang.String getSystemDescription() {
        return _SystemDescription;
    }

    public void setSystemDescription(java.lang.String value) {
        _SystemDescription = value;
    }

    public java.util.List getThingSentenceSet() {
        return _ThingSentenceSet;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.OPLscriptTypeImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = _ThingSentenceSet.size();
        while (idx3 != len3) {
            if (_ThingSentenceSet.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _ThingSentenceSet.get(idx3 ++)));
            } else {
                context.startElement("", "ThingSentenceSet");
                int idx_0 = idx3;
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _ThingSentenceSet.get(idx_0 ++)));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _ThingSentenceSet.get(idx3 ++)));
                context.endElement();
            }
        }
        if (_SystemDescription!= null) {
            context.startElement("", "SystemDescription");
            context.endAttributes();
            try {
                context.text(((java.lang.String) _SystemDescription));
            } catch (java.lang.Exception e) {
                com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
    }

    public void serializeAttributes(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = _ThingSentenceSet.size();
        context.startAttribute("", "systemName");
        try {
            context.text(((java.lang.String) _SystemName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
    }

    public void serializeAttributeBodies(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = _ThingSentenceSet.size();
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.OPLscriptType.class);
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
            return extensionTools.opl2.generated.impl.OPLscriptTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  3 :
                    if (("" == ___uri)&&("ThingSentenceSet" == ___local)) {
                        context.pushAttributes(__atts);
                        goto1();
                        return ;
                    }
                    if (("" == ___uri)&&("SystemDescription" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 4;
                        return ;
                    }
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
                case  1 :
                    if (("" == ___uri)&&("Existential" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("ProcessScope" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("TypeDeclarationSentence" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("ObjectDescription" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("ObjectScope" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("ProcessDescription" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local, __atts)));
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("ThingSentenceSet" == ___local)) {
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
                case  3 :
                    revertToParentFromLeaveElement(___uri, ___local);
                    return ;
                case  2 :
                    if (("" == ___uri)&&("ThingSentenceSet" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  5 :
                    if (("" == ___uri)&&("SystemDescription" == ___local)) {
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
                case  1 :
                    if (("" == ___uri)&&("subjectExhibitionFatherName" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("systemName" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("transID" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectAggregationFatherName" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectThingName" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 2, ___uri, ___local)));
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("systemName" == ___local)) {
                        state = 6;
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
                case  7 :
                    if (("" == ___uri)&&("systemName" == ___local)) {
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
                    case  3 :
                        revertToParentFromText(value);
                        return ;
                    case  6 :
                        try {
                            _SystemName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 7;
                        return ;
                    case  4 :
                        try {
                            _SystemDescription = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 5;
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
            idx = context.getAttribute("", "subjectExhibitionFatherName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "systemName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectAggregationFatherName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "subjectThingName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
            idx = context.getAttribute("", "transID");
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
            idx = context.getAttribute("", "systemName");
            if (idx >= 0) {
                context.consumeAttribute(idx);
                return ;
            }
        }

    }

}