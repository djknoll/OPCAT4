//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class OPLscriptTypeImpl implements extensionTools.opl2.generated.OPLscriptType, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    protected java.lang.String _SystemName;
    protected java.lang.String _SystemDescription;
    protected com.sun.xml.bind.util.ListImpl _ThingSentenceSet;
    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (extensionTools.opl2.generated.OPLscriptType.class);
    }

    public java.lang.String getSystemName() {
        return this._SystemName;
    }

    public void setSystemName(java.lang.String value) {
        this._SystemName = value;
    }

    public java.lang.String getSystemDescription() {
        return this._SystemDescription;
    }

    public void setSystemDescription(java.lang.String value) {
        this._SystemDescription = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getThingSentenceSet() {
        if (this._ThingSentenceSet == null) {
            this._ThingSentenceSet = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return this._ThingSentenceSet;
    }

    public java.util.List getThingSentenceSet() {
        return this._getThingSentenceSet();
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.OPLscriptTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((this._ThingSentenceSet == null)? 0 :this._ThingSentenceSet.size());
        while (idx3 != len3) {
            if (this._ThingSentenceSet.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx3 ++)), "ThingSentenceSet");
            } else {
                context.startElement("", "ThingSentenceSet");
                int idx_0 = idx3;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx_0 ++)), "ThingSentenceSet");
                context.endNamespaceDecls();
                int idx_1 = idx3;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx_1 ++)), "ThingSentenceSet");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx3 ++)), "ThingSentenceSet");
                context.endElement();
            }
        }
        if (this._SystemDescription!= null) {
            context.startElement("", "SystemDescription");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(((java.lang.String) this._SystemDescription), "SystemDescription");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
    }

    public void serializeAttributes(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((this._ThingSentenceSet == null)? 0 :this._ThingSentenceSet.size());
        context.startAttribute("", "systemName");
        try {
            context.text(((java.lang.String) this._SystemName), "SystemName");
        } catch (java.lang.Exception e) {
            extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        while (idx3 != len3) {
            if (this._ThingSentenceSet.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx3 ++)), "ThingSentenceSet");
            } else {
                idx3 += 1;
            }
        }
    }

    public void serializeURIs(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx3 = 0;
        final int len3 = ((this._ThingSentenceSet == null)? 0 :this._ThingSentenceSet.size());
        while (idx3 != len3) {
            if (this._ThingSentenceSet.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx3 ++)), "ThingSentenceSet");
            } else {
                idx3 += 1;
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.OPLscriptType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000 com.sun.msv.grammar.OneOrMor"
+"eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xq\u0000~\u0000\u0001ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun"
+".msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttribu"
+"tesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0007sr\u0000\u0011java.lang."
+"Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.Attrib"
+"uteExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\rxq\u0000~\u0000\u0003q\u0000~\u0000\u0013psr\u0000"
+"2com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0012\u0001psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun"
+".msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000"
+"~\u0000\u0018psr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tloc"
+"alNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001fxq\u0000~\u0000\u001at\u0000.exte"
+"nsionTools.opl2.generated.ThingSentenceSett\u0000+http://java.sun"
+".com/jaxb/xjc/dummy-elementssq\u0000~\u0000\fpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\fpp\u0000sq\u0000~\u0000\n"
+"ppsq\u0000~\u0000\u0007q\u0000~\u0000\u0013psq\u0000~\u0000\u0014q\u0000~\u0000\u0013pq\u0000~\u0000\u0017q\u0000~\u0000\u001bq\u0000~\u0000\u001dsq\u0000~\u0000\u001et\u00002extensionT"
+"ools.opl2.generated.ThingSentenceSetTypeq\u0000~\u0000\"sq\u0000~\u0000\nppsq\u0000~\u0000\u0014q"
+"\u0000~\u0000\u0013psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/"
+"relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/m"
+"sv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.Qnam"
+"eType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicTy"
+"pe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L"
+"\u0000\fnamespaceUriq\u0000~\u0000\u001fL\u0000\btypeNameq\u0000~\u0000\u001fL\u0000\nwhiteSpacet\u0000.Lcom/sun/"
+"msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/"
+"2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpac"
+"eProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.W"
+"hiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expre"
+"ssion$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.ut"
+"il.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001fL\u0000\fnamespaceURIq\u0000~\u0000"
+"\u001fxpq\u0000~\u00008q\u0000~\u00007sq\u0000~\u0000\u001et\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchem"
+"a-instanceq\u0000~\u0000\u001dsq\u0000~\u0000\u001et\u0000\u0010ThingSentenceSett\u0000\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\fq\u0000~"
+"\u0000\u0013p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000-ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u00002q\u0000~\u00007t\u0000\u0006stringsr\u00005com.sun.ms"
+"v.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000:"
+"\u0001q\u0000~\u0000=sq\u0000~\u0000>q\u0000~\u0000Lq\u0000~\u00007sq\u0000~\u0000\nppsq\u0000~\u0000\u0014q\u0000~\u0000\u0013pq\u0000~\u00000q\u0000~\u0000@q\u0000~\u0000\u001dsq\u0000"
+"~\u0000\u001et\u0000\u0011SystemDescriptionq\u0000~\u0000Eq\u0000~\u0000\u001dsq\u0000~\u0000\u0014ppq\u0000~\u0000Isq\u0000~\u0000\u001et\u0000\nsyste"
+"mNameq\u0000~\u0000Esr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L"
+"\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;"
+"xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003"
+"\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/E"
+"xpressionPool;xp\u0000\u0000\u0000\r\u0001pq\u0000~\u0000Fq\u0000~\u0000\u000bq\u0000~\u0000+q\u0000~\u0000Pq\u0000~\u0000$q\u0000~\u0000Hq\u0000~\u0000\tq\u0000~"
+"\u0000\u0011q\u0000~\u0000\'q\u0000~\u0000\u0005q\u0000~\u0000\u0010q\u0000~\u0000&q\u0000~\u0000\u0006x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends extensionTools.opl2.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----------");
        }

        protected Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            this.state = startState;
        }

        public java.lang.Object owner() {
            return extensionTools.opl2.generated.impl.OPLscriptTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  3 :
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            OPLscriptTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetImpl.class), 4, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 5;
                            return ;
                        }
                        break;
                    case  9 :
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        attIdx = this.context.getAttribute("", "systemName");
                        if (attIdx >= 0) {
                            final java.lang.String v = this.context.eatAttribute(attIdx);
                            this.state = 3;
                            this.eatText1(v);
                            continue outer;
                        }
                        break;
                    case  5 :
                        attIdx = this.context.getAttribute("", "subjectAggregationFatherName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "subjectExhibitionFatherName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "subjectThingName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  4 :
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            OPLscriptTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetImpl.class), 4, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 5;
                            return ;
                        }
                        if (("SystemDescription" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 7;
                            return ;
                        }
                        this.state = 9;
                        continue outer;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                OPLscriptTypeImpl.this._SystemName = value;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  8 :
                        if (("SystemDescription" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 9;
                            return ;
                        }
                        break;
                    case  6 :
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 4;
                            return ;
                        }
                        break;
                    case  9 :
                        this.revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        attIdx = this.context.getAttribute("", "systemName");
                        if (attIdx >= 0) {
                            final java.lang.String v = this.context.eatAttribute(attIdx);
                            this.state = 3;
                            this.eatText1(v);
                            continue outer;
                        }
                        break;
                    case  5 :
                        attIdx = this.context.getAttribute("", "subjectAggregationFatherName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "subjectExhibitionFatherName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "subjectThingName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  4 :
                        this.state = 9;
                        continue outer;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  9 :
                        this.revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        if (("systemName" == ___local)&&("" == ___uri)) {
                            this.state = 1;
                            return ;
                        }
                        break;
                    case  5 :
                        if (("subjectAggregationFatherName" == ___local)&&("" == ___uri)) {
                            OPLscriptTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 6, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("subjectExhibitionFatherName" == ___local)&&("" == ___uri)) {
                            OPLscriptTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 6, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("subjectThingName" == ___local)&&("" == ___uri)) {
                            OPLscriptTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 6, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  4 :
                        this.state = 9;
                        continue outer;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  9 :
                        this.revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("systemName" == ___local)&&("" == ___uri)) {
                            this.state = 3;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = this.context.getAttribute("", "systemName");
                        if (attIdx >= 0) {
                            final java.lang.String v = this.context.eatAttribute(attIdx);
                            this.state = 3;
                            this.eatText1(v);
                            continue outer;
                        }
                        break;
                    case  5 :
                        attIdx = this.context.getAttribute("", "subjectAggregationFatherName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "subjectExhibitionFatherName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "subjectThingName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  4 :
                        this.state = 9;
                        continue outer;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                try {
                    switch (this.state) {
                        case  7 :
                            this.state = 8;
                            this.eatText2(value);
                            return ;
                        case  9 :
                            this.revertToParentFromText(value);
                            return ;
                        case  1 :
                            this.state = 2;
                            this.eatText1(value);
                            return ;
                        case  0 :
                            attIdx = this.context.getAttribute("", "systemName");
                            if (attIdx >= 0) {
                                final java.lang.String v = this.context.eatAttribute(attIdx);
                                this.state = 3;
                                this.eatText1(v);
                                continue outer;
                            }
                            break;
                        case  5 :
                            attIdx = this.context.getAttribute("", "subjectAggregationFatherName");
                            if (attIdx >= 0) {
                                this.context.consumeAttribute(attIdx);
                                this.context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = this.context.getAttribute("", "subjectExhibitionFatherName");
                            if (attIdx >= 0) {
                                this.context.consumeAttribute(attIdx);
                                this.context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = this.context.getAttribute("", "subjectThingName");
                            if (attIdx >= 0) {
                                this.context.consumeAttribute(attIdx);
                                this.context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  4 :
                            this.state = 9;
                            continue outer;
                    }
                } catch (java.lang.RuntimeException e) {
                    this.handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                OPLscriptTypeImpl.this._SystemDescription = value;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

    }

}
