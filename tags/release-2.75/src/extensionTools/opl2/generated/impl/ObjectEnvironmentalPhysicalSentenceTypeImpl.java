//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

public class ObjectEnvironmentalPhysicalSentenceTypeImpl implements extensionTools.opl2.generated.ObjectEnvironmentalPhysicalSentenceType, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    protected boolean has_Transient;
    protected boolean _Transient;
    protected boolean has_Environmental;
    protected boolean _Environmental;
    protected com.sun.xml.bind.util.ListImpl _Role = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    protected java.lang.String _ObjectName;
    protected boolean has_Physical;
    protected boolean _Physical;
    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/grammar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0002xp\u0006\u0004Y\u001bppsq\u0000~\u0000\u0000\u0005\u0085\u00a2\u0019ppsq\u0000~\u0000\u0000\u0005\u0006\u00eb\u0017ppsq\u0000~\u0000\u0000\u0004\u00884\u0015ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001\u00c2\b\u00cbpp\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0015L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0015L\u0000\fnamespaceURIq\u0000~\u0000\u0015xpq\u0000~\u0000\u0019q\u0000~\u0000\u0018sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0015L\u0000\fnamespaceURIq\u0000~\u0000\u0015xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\nObjectNamet\u0000\u0000sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001\u0002\u00c6+Eppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0002\u00c6+:sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000&\u0002\u00c6+7q\u0000~\u0000,psq\u0000~\u0000\t\u0001c\u0015\u00a0q\u0000~\u0000,p\u0000sq\u0000~\u0000\t\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000&\u0001c\u0015\u008appsq\u0000~\u0000(\u0001c\u0015\u007fq\u0000~\u0000,psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\nxq\u0000~\u0000\u0003\u0001c\u0015|q\u0000~\u0000,psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\bsq\u0000~\u0000+\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\"sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\tq\u0000~\u00006psq\u0000~\u0000!t\u0000&extensionTools.opl2.generated.RoleTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000!t\u0000\u0004Roleq\u0000~\u0000%sq\u0000~\u0000\t\u0001c\u0015\u0095q\u0000~\u0000,p\u0000sq\u0000~\u0000&\u0001c\u0015\u008appsq\u0000~\u0000(\u0001c\u0015\u007fq\u0000~\u0000,psq\u0000~\u00002\u0001c\u0015|q\u0000~\u0000,pq\u0000~\u00005q\u0000~\u00008q\u0000~\u0000:sq\u0000~\u0000!t\u0000\"extensionTools.opl2.generated.Roleq\u0000~\u0000=q\u0000~\u0000:sq\u0000~\u0000\t\u0000~\u00b6\u00fdpp\u0000sq\u0000~\u0000\r\u0000~\u00b6\u00f2ppsr\u0000$com.sun.msv.datatype.xsd.BooleanType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0012q\u0000~\u0000\u0018t\u0000\u0007booleansr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$2\u0087z9\u00ee\u00f8,N\u0005\u0002\u0000\u0000xq\u0000~\u0000\u001bq\u0000~\u0000\u001esq\u0000~\u0000\u001fq\u0000~\u0000Jq\u0000~\u0000\u0018sq\u0000~\u0000!t\u0000\rEnvironmentalq\u0000~\u0000%sq\u0000~\u0000\t\u0000~\u00b6\u00fdpp\u0000q\u0000~\u0000Gsq\u0000~\u0000!t\u0000\bPhysicalq\u0000~\u0000%sq\u0000~\u0000\t\u0000~\u00b6\u00fdpp\u0000q\u0000~\u0000Gsq\u0000~\u0000!t\u0000\tTransientq\u0000~\u0000%sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000W[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\u000b\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\u0006q\u0000~\u0000-ppq\u0000~\u0000*ppppppppppq\u0000~\u0000\'q\u0000~\u0000\bpppppppppppq\u0000~\u00001q\u0000~\u0000Bpppppppppq\u0000~\u00000q\u0000~\u0000Apppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\u0005ppppppppppppppq\u0000~\u0000\u0007pppppppp");

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.ObjectEnvironmentalPhysicalSentenceType.class;
    }

    public boolean isTransient() {
        return _Transient;
    }

    public void setTransient(boolean value) {
        _Transient = value;
        has_Transient = true;
    }

    public boolean isEnvironmental() {
        return _Environmental;
    }

    public void setEnvironmental(boolean value) {
        _Environmental = value;
        has_Environmental = true;
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

    public boolean isPhysical() {
        return _Physical;
    }

    public void setPhysical(boolean value) {
        _Physical = value;
        has_Physical = true;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ObjectEnvironmentalPhysicalSentenceTypeImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = _Role.size();
        context.startElement("", "ObjectName");
        context.endAttributes();
        try {
            context.text(((java.lang.String) _ObjectName));
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
        context.startElement("", "Environmental");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printBoolean(((boolean) _Environmental)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "Physical");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printBoolean(((boolean) _Physical)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "Transient");
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printBoolean(((boolean) _Transient)));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
    }

    public void serializeAttributes(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = _Role.size();
    }

    public void serializeAttributeBodies(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = _Role.size();
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ObjectEnvironmentalPhysicalSentenceType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.xml.bind.unmarshaller.ContentHandlerEx
    {


        public Unmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
            super(context, "---------------");
        }

        protected com.sun.xml.bind.unmarshaller.UnmarshallableObject owner() {
            return extensionTools.opl2.generated.impl.ObjectEnvironmentalPhysicalSentenceTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  9 :
                    if (("" == ___uri)&&("Transient" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 10;
                        return ;
                    }
                    break;
                case  12 :
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
                case  6 :
                    if (("" == ___uri)&&("Physical" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 7;
                        return ;
                    }
                    break;
                case  3 :
                    if (("" == ___uri)&&("Role" == ___local)) {
                        context.pushAttributes(__atts);
                        goto13();
                        return ;
                    }
                    if (("" == ___uri)&&("Environmental" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 4;
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("ObjectName" == ___local)) {
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
                case  11 :
                    if (("" == ___uri)&&("Transient" == ___local)) {
                        context.popAttributes();
                        state = 12;
                        return ;
                    }
                    break;
                case  2 :
                    if (("" == ___uri)&&("ObjectName" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  5 :
                    if (("" == ___uri)&&("Environmental" == ___local)) {
                        context.popAttributes();
                        state = 6;
                        return ;
                    }
                    break;
                case  12 :
                    revertToParentFromLeaveElement(___uri, ___local);
                    return ;
                case  8 :
                    if (("" == ___uri)&&("Physical" == ___local)) {
                        context.popAttributes();
                        state = 9;
                        return ;
                    }
                    break;
                case  14 :
                    if (("" == ___uri)&&("Role" == ___local)) {
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
                case  12 :
                    revertToParentFromEnterAttribute(___uri, ___local);
                    return ;
                case  13 :
                    if (("" == ___uri)&&("RoleName" == ___local)) {
                        _Role.add(((extensionTools.opl2.generated.impl.RoleTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 14, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("Library" == ___local)) {
                        _Role.add(((extensionTools.opl2.generated.impl.RoleTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 14, ___uri, ___local)));
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
                case  12 :
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
                            _ObjectName = value;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 2;
                        return ;
                    case  12 :
                        revertToParentFromText(value);
                        return ;
                    case  4 :
                        try {
                            _Environmental = javax.xml.bind.DatatypeConverter.parseBoolean(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_Environmental = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 5;
                        return ;
                    case  10 :
                        try {
                            _Transient = javax.xml.bind.DatatypeConverter.parseBoolean(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_Transient = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 11;
                        return ;
                    case  7 :
                        try {
                            _Physical = javax.xml.bind.DatatypeConverter.parseBoolean(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                            has_Physical = true;
                        } catch (java.lang.Exception e) {
                            handleParseConversionException(e);
                        }
                        state = 8;
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
                case  14 :
                    state = 14;
                    return ;
            }
            super.leaveChild(nextState);
        }

        private void goto13()
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            int idx;
            state = 13;
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