//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

public class ObjectStateSentenceTypeImpl implements extensionTools.opl2.generated.ObjectStateSentenceType, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _StateClause = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    protected com.sun.xml.bind.util.ListImpl _Role = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    protected java.lang.String _ObjectName;
    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/grammar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0002xp\u0007N_Tppsq\u0000~\u0000\u0000\u0004\u00884\u0015ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001\u00c2\b\u00cbpp\u0000sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003\u0001\u00c2\b\u00c0ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0013L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u0000.com.sun.msv.datatype.xsd.WhiteSpaceProcessor$1\u0013JMoI\u00db\u00a4G\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\nppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0013L\u0000\fnamespaceURIq\u0000~\u0000\u0013xpq\u0000~\u0000\u0017q\u0000~\u0000\u0016sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0013L\u0000\fnamespaceURIq\u0000~\u0000\u0013xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\nObjectNamet\u0000\u0000sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001\u0002\u00c6+Eppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0002\u00c6+:sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000$\u0002\u00c6+7q\u0000~\u0000*psq\u0000~\u0000\u0007\u0001c\u0015\u00a0q\u0000~\u0000*p\u0000sq\u0000~\u0000\u0007\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000$\u0001c\u0015\u008appsq\u0000~\u0000&\u0001c\u0015\u007fq\u0000~\u0000*psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\bxq\u0000~\u0000\u0003\u0001c\u0015|q\u0000~\u0000*psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\bsq\u0000~\u0000)\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000 sr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\tq\u0000~\u00004psq\u0000~\u0000\u001ft\u0000&extensionTools.opl2.generated.RoleTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u001ft\u0000\u0004Roleq\u0000~\u0000#sq\u0000~\u0000\u0007\u0001c\u0015\u0095q\u0000~\u0000*p\u0000sq\u0000~\u0000$\u0001c\u0015\u008appsq\u0000~\u0000&\u0001c\u0015\u007fq\u0000~\u0000*psq\u0000~\u00000\u0001c\u0015|q\u0000~\u0000*pq\u0000~\u00003q\u0000~\u00006q\u0000~\u00008sq\u0000~\u0000\u001ft\u0000\"extensionTools.opl2.generated.Roleq\u0000~\u0000;q\u0000~\u00008sq\u0000~\u0000&\u0002\u00c6+:ppsq\u0000~\u0000$\u0002\u00c6+7ppsq\u0000~\u0000\u0007\u0001c\u0015\u00a0pp\u0000sq\u0000~\u0000\u0007\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000$\u0001c\u0015\u008appsq\u0000~\u0000&\u0001c\u0015\u007fq\u0000~\u0000*psq\u0000~\u00000\u0001c\u0015|q\u0000~\u0000*pq\u0000~\u00003q\u0000~\u00006q\u0000~\u00008sq\u0000~\u0000\u001ft\u0000-extensionTools.opl2.generated.StateClauseTypeq\u0000~\u0000;sq\u0000~\u0000\u001ft\u0000\u000bStateClauseq\u0000~\u0000#sq\u0000~\u0000\u0007\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000$\u0001c\u0015\u008appsq\u0000~\u0000&\u0001c\u0015\u007fq\u0000~\u0000*psq\u0000~\u00000\u0001c\u0015|q\u0000~\u0000*pq\u0000~\u00003q\u0000~\u00006q\u0000~\u00008sq\u0000~\u0000\u001ft\u0000)extensionTools.opl2.generated.StateClauseq\u0000~\u0000;sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000V[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\u000f\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000+q\u0000~\u0000Epq\u0000~\u0000(q\u0000~\u0000Dpppppppppq\u0000~\u0000%q\u0000~\u0000\u0006pppppppppppq\u0000~\u0000/q\u0000~\u0000@q\u0000~\u0000Iq\u0000~\u0000Qpppppppq\u0000~\u0000.q\u0000~\u0000?q\u0000~\u0000Hq\u0000~\u0000Pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\u0005pppppppp");

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.ObjectStateSentenceType.class;
    }

    public java.util.List getStateClause() {
        return _StateClause;
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

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ObjectStateSentenceTypeImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = _StateClause.size();
        int idx2 = 0;
        final int len2 = _Role.size();
        context.startElement("", "ObjectName");
        context.endAttributes();
        try {
            context.text(((java.lang.String) _ObjectName));
        } catch (java.lang.Exception e) {
            com.sun.xml.bind.marshaller.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx2 != len2) {
            if (_Role.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx2 ++)));
            } else {
                context.startElement("", "Role");
                int idx_1 = idx2;
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx_1 ++)));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _Role.get(idx2 ++)));
                context.endElement();
            }
        }
        while (idx1 != len1) {
            if (_StateClause.get(idx1) instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _StateClause.get(idx1 ++)));
            } else {
                context.startElement("", "StateClause");
                int idx_2 = idx1;
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _StateClause.get(idx_2 ++)));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _StateClause.get(idx1 ++)));
                context.endElement();
            }
        }
    }

    public void serializeAttributes(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = _StateClause.size();
        int idx2 = 0;
        final int len2 = _Role.size();
    }

    public void serializeAttributeBodies(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = _StateClause.size();
        int idx2 = 0;
        final int len2 = _Role.size();
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ObjectStateSentenceType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.xml.bind.unmarshaller.ContentHandlerEx
    {


        public Unmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
            super(context, "---------");
        }

        protected com.sun.xml.bind.unmarshaller.UnmarshallableObject owner() {
            return extensionTools.opl2.generated.impl.ObjectStateSentenceTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  0 :
                    if (("" == ___uri)&&("ObjectName" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 1;
                        return ;
                    }
                    break;
                case  3 :
                    if (("" == ___uri)&&("Role" == ___local)) {
                        context.pushAttributes(__atts);
                        goto4();
                        return ;
                    }
                    if (("" == ___uri)&&("StateClause" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 6;
                        return ;
                    }
                    break;
                case  6 :
                    if (("" == ___uri)&&("StateName" == ___local)) {
                        _StateClause.add(((extensionTools.opl2.generated.impl.StateClauseTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.StateClauseTypeImpl.class), 7, ___uri, ___local, __atts)));
                        return ;
                    }
                    break;
                case  8 :
                    if (("" == ___uri)&&("StateClause" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 6;
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
                case  7 :
                    if (("" == ___uri)&&("StateClause" == ___local)) {
                        context.popAttributes();
                        state = 8;
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
                    if (("" == ___uri)&&("Role" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  8 :
                    revertToParentFromLeaveElement(___uri, ___local);
                    return ;
            }
            super.leaveElement(___uri, ___local);
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  4 :
                    if (("" == ___uri)&&("RoleName" == ___local)) {
                        _Role.add(((extensionTools.opl2.generated.impl.RoleTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 5, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("Library" == ___local)) {
                        _Role.add(((extensionTools.opl2.generated.impl.RoleTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 5, ___uri, ___local)));
                        return ;
                    }
                    break;
                case  8 :
                    revertToParentFromEnterAttribute(___uri, ___local);
                    return ;
            }
            super.enterAttribute(___uri, ___local);
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  8 :
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
                    case  8 :
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
                case  7 :
                    state = 7;
                    return ;
                case  5 :
                    state = 5;
                    return ;
            }
            super.leaveChild(nextState);
        }

        private void goto4()
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            int idx;
            state = 4;
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
