//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.12.01 at 04:25:51 GMT+02:00 
//


package extensionTools.opl2.generated.impl;

public class ObjectExhibitionSentenceSetTypeImpl implements extensionTools.opl2.generated.ObjectExhibitionSentenceSetType, com.sun.xml.bind.unmarshaller.UnmarshallableObject, com.sun.xml.bind.serializer.XMLSerializable, com.sun.xml.bind.validator.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _ThingSentenceSet = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
    protected extensionTools.opl2.generated.ObjectExhibitionSentenceType _ObjectExhibitionSentence;
    private final static com.sun.msv.grammar.Grammar schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/grammar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0003I\u0000\u000ecachedHashCodeL\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0002xp\u0005\u008cV\u0081ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001\u0002\u00c6+7ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001c\u0015\u00a0pp\u0000sq\u0000~\u0000\b\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000\u0006\u0001c\u0015\u008appsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003\u0001c\u0015\u007fsr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\txq\u0000~\u0000\u0003\u0001c\u0015|q\u0000~\u0000\u0012psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\bsq\u0000~\u0000\u0011\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003\u0000\u0000\u0000\tq\u0000~\u0000\u0017psr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001exq\u0000~\u0000\u0019t\u0000:extensionTools.opl2.generated.ObjectExhibitionSentenceTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u001dt\u0000\u0018ObjectExhibitionSentencet\u0000\u0000sq\u0000~\u0000\b\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000\u0006\u0001c\u0015\u008appsq\u0000~\u0000\u000e\u0001c\u0015\u007fq\u0000~\u0000\u0012psq\u0000~\u0000\u0013\u0001c\u0015|q\u0000~\u0000\u0012pq\u0000~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u00006extensionTools.opl2.generated.ObjectExhibitionSentenceq\u0000~\u0000!sq\u0000~\u0000\u0006\u0002\u00c6+Eppsq\u0000~\u0000\u000e\u0002\u00c6+:q\u0000~\u0000\u0012psq\u0000~\u0000\u0006\u0002\u00c6+7q\u0000~\u0000\u0012psq\u0000~\u0000\b\u0001c\u0015\u00a0q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\b\u0001c\u0015\u0095pp\u0000sq\u0000~\u0000\u0006\u0001c\u0015\u008appsq\u0000~\u0000\u000e\u0001c\u0015\u007fq\u0000~\u0000\u0012psq\u0000~\u0000\u0013\u0001c\u0015|q\u0000~\u0000\u0012pq\u0000~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u00002extensionTools.opl2.generated.ThingSentenceSetTypeq\u0000~\u0000!sq\u0000~\u0000\u001dt\u0000\u0010ThingSentenceSetq\u0000~\u0000$sq\u0000~\u0000\b\u0001c\u0015\u0095q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u0006\u0001c\u0015\u008appsq\u0000~\u0000\u000e\u0001c\u0015\u007fq\u0000~\u0000\u0012psq\u0000~\u0000\u0013\u0001c\u0015|q\u0000~\u0000\u0012pq\u0000~\u0000\u0016q\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000.extensionTools.opl2.generated.ThingSentenceSetq\u0000~\u0000!q\u0000~\u0000\u001csr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0002\u0000\u0004I\u0000\u0005countI\u0000\tthresholdL\u0000\u0006parentq\u0000~\u0000>[\u0000\u0005tablet\u0000![Lcom/sun/msv/grammar/Expression;xp\u0000\u0000\u0000\r\u0000\u0000\u00009pur\u0000![Lcom.sun.msv.grammar.Expression;\u00d68D\u00c3]\u00ad\u00a7\n\u0002\u0000\u0000xp\u0000\u0000\u0000\u00bfppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\u0007q\u0000~\u0000-pq\u0000~\u0000,ppppppppppq\u0000~\u0000+ppppppppppppq\u0000~\u0000\u0010q\u0000~\u0000\'q\u0000~\u00001q\u0000~\u00009pppppppq\u0000~\u0000\rq\u0000~\u0000&q\u0000~\u00000q\u0000~\u00008pppppppppppppppppppppppppppppppppppppppppppppppppppppppppq\u0000~\u0000\u0005ppppppppppp");

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return extensionTools.opl2.generated.ObjectExhibitionSentenceSetType.class;
    }

    public java.util.List getThingSentenceSet() {
        return _ThingSentenceSet;
    }

    public extensionTools.opl2.generated.ObjectExhibitionSentenceType getObjectExhibitionSentence() {
        return _ObjectExhibitionSentence;
    }

    public void setObjectExhibitionSentence(extensionTools.opl2.generated.ObjectExhibitionSentenceType value) {
        _ObjectExhibitionSentence = value;
    }

    public com.sun.xml.bind.unmarshaller.ContentHandlerEx getUnmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ObjectExhibitionSentenceSetTypeImpl.Unmarshaller(context);
    }

    public java.lang.Class getPrimaryInterfaceClass() {
        return PRIMARY_INTERFACE_CLASS();
    }

    public void serializeElements(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = _ThingSentenceSet.size();
        if (_ObjectExhibitionSentence instanceof javax.xml.bind.Element) {
            context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _ObjectExhibitionSentence));
        } else {
            context.startElement("", "ObjectExhibitionSentence");
            context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _ObjectExhibitionSentence));
            context.endAttributes();
            context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _ObjectExhibitionSentence));
            context.endElement();
        }
        while (idx1 != len1) {
            if (_ThingSentenceSet.get(idx1) instanceof javax.xml.bind.Element) {
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _ThingSentenceSet.get(idx1 ++)));
            } else {
                context.startElement("", "ThingSentenceSet");
                int idx_1 = idx1;
                context.childAsAttributes(((com.sun.xml.bind.serializer.XMLSerializable) _ThingSentenceSet.get(idx_1 ++)));
                context.endAttributes();
                context.childAsElements(((com.sun.xml.bind.serializer.XMLSerializable) _ThingSentenceSet.get(idx1 ++)));
                context.endElement();
            }
        }
    }

    public void serializeAttributes(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = _ThingSentenceSet.size();
    }

    public void serializeAttributeBodies(com.sun.xml.bind.serializer.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = _ThingSentenceSet.size();
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ObjectExhibitionSentenceSetType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.xml.bind.unmarshaller.ContentHandlerEx
    {


        public Unmarshaller(com.sun.xml.bind.unmarshaller.UnmarshallingContext context) {
            super(context, "------");
        }

        protected com.sun.xml.bind.unmarshaller.UnmarshallableObject owner() {
            return extensionTools.opl2.generated.impl.ObjectExhibitionSentenceSetTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, org.xml.sax.Attributes __atts)
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            switch (state) {
                case  1 :
                    if (("" == ___uri)&&("ObjectName" == ___local)) {
                        _ObjectExhibitionSentence = ((extensionTools.opl2.generated.impl.ObjectExhibitionSentenceTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ObjectExhibitionSentenceTypeImpl.class), 2, ___uri, ___local, __atts));
                        return ;
                    }
                    break;
                case  0 :
                    if (("" == ___uri)&&("ObjectExhibitionSentence" == ___local)) {
                        context.pushAttributes(__atts);
                        state = 1;
                        return ;
                    }
                    break;
                case  3 :
                    if (("" == ___uri)&&("ThingSentenceSet" == ___local)) {
                        context.pushAttributes(__atts);
                        goto4();
                        return ;
                    }
                    revertToParentFromEnterElement(___uri, ___local, __atts);
                    return ;
                case  4 :
                    if (("" == ___uri)&&("Existential" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("ProcessScope" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("TypeDeclarationSentence" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("ObjectDescription" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("ObjectScope" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local, __atts)));
                        return ;
                    }
                    if (("" == ___uri)&&("ProcessDescription" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local, __atts)));
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
                    if (("" == ___uri)&&("ThingSentenceSet" == ___local)) {
                        context.popAttributes();
                        state = 3;
                        return ;
                    }
                    break;
                case  2 :
                    if (("" == ___uri)&&("ObjectExhibitionSentence" == ___local)) {
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
                case  4 :
                    if (("" == ___uri)&&("subjectExhibitionFatherName" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("systemName" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("transID" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectAggregationFatherName" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local)));
                        return ;
                    }
                    if (("" == ___uri)&&("subjectThingName" == ___local)) {
                        _ThingSentenceSet.add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 5, ___uri, ___local)));
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
                case  5 :
                    state = 5;
                    return ;
                case  2 :
                    state = 2;
                    return ;
            }
            super.leaveChild(nextState);
        }

        private void goto4()
            throws com.sun.xml.bind.unmarshaller.UnreportedException
        {
            int idx;
            state = 4;
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

    }

}
