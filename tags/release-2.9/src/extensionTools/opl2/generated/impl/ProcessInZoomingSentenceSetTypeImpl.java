//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class ProcessInZoomingSentenceSetTypeImpl implements extensionTools.opl2.generated.ProcessInZoomingSentenceSetType, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    protected extensionTools.opl2.generated.ProcessInZoomingSentenceType _ProcessInZoomingSentence;
    protected com.sun.xml.bind.util.ListImpl _ThingSentenceSet;
    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

//    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
//        return (extensionTools.opl2.generated.ProcessInZoomingSentenceSetType.class);
//    }

    public extensionTools.opl2.generated.ProcessInZoomingSentenceType getProcessInZoomingSentence() {
        return this._ProcessInZoomingSentence;
    }

    public void setProcessInZoomingSentence(extensionTools.opl2.generated.ProcessInZoomingSentenceType value) {
        this._ProcessInZoomingSentence = value;
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
        return new extensionTools.opl2.generated.impl.ProcessInZoomingSentenceSetTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((this._ThingSentenceSet == null)? 0 :this._ThingSentenceSet.size());
        if (this._ProcessInZoomingSentence instanceof javax.xml.bind.Element) {
            context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ProcessInZoomingSentence), "ProcessInZoomingSentence");
        } else {
            context.startElement("", "ProcessInZoomingSentence");
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ProcessInZoomingSentence), "ProcessInZoomingSentence");
            context.endNamespaceDecls();
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ProcessInZoomingSentence), "ProcessInZoomingSentence");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ProcessInZoomingSentence), "ProcessInZoomingSentence");
            context.endElement();
        }
        while (idx2 != len2) {
            if (this._ThingSentenceSet.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx2 ++)), "ThingSentenceSet");
            } else {
                context.startElement("", "ThingSentenceSet");
                int idx_2 = idx2;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx_2 ++)), "ThingSentenceSet");
                context.endNamespaceDecls();
                int idx_3 = idx2;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx_3 ++)), "ThingSentenceSet");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx2 ++)), "ThingSentenceSet");
                context.endElement();
            }
        }
    }

    public void serializeAttributes(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((this._ThingSentenceSet == null)? 0 :this._ThingSentenceSet.size());
        if (this._ProcessInZoomingSentence instanceof javax.xml.bind.Element) {
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ProcessInZoomingSentence), "ProcessInZoomingSentence");
        }
        while (idx2 != len2) {
            if (this._ThingSentenceSet.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx2 ++)), "ThingSentenceSet");
            } else {
                idx2 += 1;
            }
        }
    }

    public void serializeURIs(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((this._ThingSentenceSet == null)? 0 :this._ThingSentenceSet.size());
        if (this._ProcessInZoomingSentence instanceof javax.xml.bind.Element) {
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ProcessInZoomingSentence), "ProcessInZoomingSentence");
        }
        while (idx2 != len2) {
            if (this._ThingSentenceSet.get(idx2) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ThingSentenceSet.get(idx2 ++)), "ThingSentenceSet");
            } else {
                idx2 += 1;
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ProcessInZoomingSentenceSetType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom."
+"sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttr"
+"ibutesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0006ppsr\u0000 com.sun.msv.g"
+"rammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryE"
+"xp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002"
+"\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\txq\u0000~\u0000\u0003q\u0000~\u0000\u0011psr\u00002com.sun.msv.gra"
+"mmar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0010\u0001p"
+"sr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.m"
+"sv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Exp"
+"ression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0016psr\u0000#com.sun."
+"msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/l"
+"ang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001dxq\u0000~\u0000\u0018t\u00006extensionTools.opl2."
+"generated.ProcessInZoomingSentencet\u0000+http://java.sun.com/jax"
+"b/xjc/dummy-elementssq\u0000~\u0000\bpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\bpp\u0000sq\u0000~\u0000\u0006ppsq\u0000~\u0000\r"
+"q\u0000~\u0000\u0011psq\u0000~\u0000\u0012q\u0000~\u0000\u0011pq\u0000~\u0000\u0015q\u0000~\u0000\u0019q\u0000~\u0000\u001bsq\u0000~\u0000\u001ct\u0000:extensionTools.opl"
+"2.generated.ProcessInZoomingSentenceTypeq\u0000~\u0000 sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0012q"
+"\u0000~\u0000\u0011psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/"
+"relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/m"
+"sv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.Qnam"
+"eType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicTy"
+"pe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L"
+"\u0000\fnamespaceUriq\u0000~\u0000\u001dL\u0000\btypeNameq\u0000~\u0000\u001dL\u0000\nwhiteSpacet\u0000.Lcom/sun/"
+"msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/"
+"2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpac"
+"eProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.W"
+"hiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expre"
+"ssion$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.ut"
+"il.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001dL\u0000\fnamespaceURIq\u0000~\u0000"
+"\u001dxpq\u0000~\u00006q\u0000~\u00005sq\u0000~\u0000\u001ct\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchem"
+"a-instanceq\u0000~\u0000\u001bsq\u0000~\u0000\u001ct\u0000\u0018ProcessInZoomingSentencet\u0000\u0000sq\u0000~\u0000\u0006pps"
+"q\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u0006q\u0000~\u0000\u0011psq\u0000~\u0000\bq\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0006ppsq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~"
+"\u0000\u0012q\u0000~\u0000\u0011pq\u0000~\u0000\u0015q\u0000~\u0000\u0019q\u0000~\u0000\u001bsq\u0000~\u0000\u001ct\u0000.extensionTools.opl2.generate"
+"d.ThingSentenceSetq\u0000~\u0000 sq\u0000~\u0000\bq\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\bpp\u0000sq\u0000~\u0000\u0006p"
+"psq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u0012q\u0000~\u0000\u0011pq\u0000~\u0000\u0015q\u0000~\u0000\u0019q\u0000~\u0000\u001bsq\u0000~\u0000\u001ct\u00002extensionTo"
+"ols.opl2.generated.ThingSentenceSetTypeq\u0000~\u0000 sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0012q\u0000"
+"~\u0000\u0011pq\u0000~\u0000.q\u0000~\u0000>q\u0000~\u0000\u001bsq\u0000~\u0000\u001ct\u0000\u0010ThingSentenceSetq\u0000~\u0000Cq\u0000~\u0000\u001bsr\u0000\"co"
+"m.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lco"
+"m/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.ms"
+"v.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstr"
+"eamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp"
+"\u0000\u0000\u0000\u0011\u0001pq\u0000~\u0000\u0007q\u0000~\u0000Fq\u0000~\u0000\u0005q\u0000~\u0000)q\u0000~\u0000Uq\u0000~\u0000\"q\u0000~\u0000Nq\u0000~\u0000Dq\u0000~\u0000Eq\u0000~\u0000\u000fq\u0000~\u0000"
+"%q\u0000~\u0000Iq\u0000~\u0000Qq\u0000~\u0000\fq\u0000~\u0000$q\u0000~\u0000Hq\u0000~\u0000Px"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends extensionTools.opl2.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "-------");
        }

        protected Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            this.state = startState;
        }

        public java.lang.Object owner() {
            return extensionTools.opl2.generated.impl.ProcessInZoomingSentenceSetTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  2 :
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
                            ProcessInZoomingSentenceSetTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetImpl.class), 4, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 2;
                            return ;
                        }
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  1 :
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            ProcessInZoomingSentenceSetTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ThingSentenceSetImpl.class), 4, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 2;
                            return ;
                        }
                        this.state = 4;
                        continue outer;
                    case  0 :
                        if (("ProcessInZoomingSentence" == ___local)&&("" == ___uri)) {
                            ProcessInZoomingSentenceSetTypeImpl.this._ProcessInZoomingSentence = ((extensionTools.opl2.generated.impl.ProcessInZoomingSentenceImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ProcessInZoomingSentenceImpl.class), 1, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("ProcessInZoomingSentence" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 5;
                            return ;
                        }
                        break;
                    case  5 :
                        if (("ProcessName" == ___local)&&("" == ___uri)) {
                            ProcessInZoomingSentenceSetTypeImpl.this._ProcessInZoomingSentence = ((extensionTools.opl2.generated.impl.ProcessInZoomingSentenceTypeImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ProcessInZoomingSentenceTypeImpl.class), 6, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        break;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  3 :
                        if (("ThingSentenceSet" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 4;
                            return ;
                        }
                        break;
                    case  2 :
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
                    case  6 :
                        if (("ProcessInZoomingSentence" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 1;
                            return ;
                        }
                        break;
                    case  4 :
                        this.revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        this.state = 4;
                        continue outer;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            outer:
            while (true) {
                switch (this.state) {
                    case  2 :
                        if (("subjectAggregationFatherName" == ___local)&&("" == ___uri)) {
                            ProcessInZoomingSentenceSetTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 3, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("subjectExhibitionFatherName" == ___local)&&("" == ___uri)) {
                            ProcessInZoomingSentenceSetTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 3, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("subjectThingName" == ___local)&&("" == ___uri)) {
                            ProcessInZoomingSentenceSetTypeImpl.this._getThingSentenceSet().add(((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.ThingSentenceSetTypeImpl.class), 3, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  4 :
                        this.revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        this.state = 4;
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
                    case  2 :
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
                        this.revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        this.state = 4;
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
                        case  2 :
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
                            this.revertToParentFromText(value);
                            return ;
                        case  1 :
                            this.state = 4;
                            continue outer;
                    }
                } catch (java.lang.RuntimeException e) {
                    this.handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
