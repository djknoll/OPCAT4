//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class ObjectInZoomingSentenceImpl
    extends extensionTools.opl2.generated.impl.ObjectInZoomingSentenceTypeImpl
    implements extensionTools.opl2.generated.ObjectInZoomingSentence, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "ObjectInZoomingSentence";
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ObjectInZoomingSentenceImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "ObjectInZoomingSentence");
        super.serializeURIs(context);
        context.endNamespaceDecls();
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeBody(context);
        context.endElement();
    }

    public void serializeAttributes(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeURIs(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ObjectInZoomingSentence.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001fcom.sun.msv.gra"
+"mmar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007pps"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L"
+"\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000#com.sun.ms"
+"v.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com."
+"sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun"
+".msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.dat"
+"atype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/l"
+"ang/String;L\u0000\btypeNameq\u0000~\u0000\u0017L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/data"
+"type/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XML"
+"Schemat\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProces"
+"sor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpa"
+"ceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$"
+"NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.util.Str"
+"ingPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0017L\u0000\fnamespaceURIq\u0000~\u0000\u0017xpq\u0000~"
+"\u0000\u001bq\u0000~\u0000\u001asr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bpps"
+"r\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\t"
+"nameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005value"
+"xp\u0000psq\u0000~\u0000\u000fppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xq\u0000~\u0000\u0014q\u0000~\u0000\u001at\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceP"
+"rocessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001dq\u0000~\u0000 sq\u0000~\u0000!q\u0000~\u0000,q\u0000~\u0000\u001asr\u0000#c"
+"om.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~"
+"\u0000\u0017L\u0000\fnamespaceURIq\u0000~\u0000\u0017xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instances"
+"r\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\'\u0001psq\u0000~\u00000t\u0000\nObjectNamet\u0000\u0000sq\u0000~\u0000#ppsr\u0000 com.sun.ms"
+"v.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.Una"
+"ryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000(psq\u0000~\u0000#q\u0000~\u0000(psq\u0000~\u0000\u0000q\u0000~"
+"\u0000(p\u0000sq\u0000~\u0000#ppsq\u0000~\u0000<q\u0000~\u0000(psq\u0000~\u0000%q\u0000~\u0000(psr\u00002com.sun.msv.grammar."
+"Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u00007psr\u0000 com"
+".sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00001q\u0000~\u00006sq\u0000~\u00000t\u0000\""
+"extensionTools.opl2.generated.Rolet\u0000+http://java.sun.com/jax"
+"b/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000(p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000#ppsq"
+"\u0000~\u0000<q\u0000~\u0000(psq\u0000~\u0000%q\u0000~\u0000(pq\u0000~\u0000Eq\u0000~\u0000Gq\u0000~\u00006sq\u0000~\u00000t\u0000&extensionTools"
+".opl2.generated.RoleTypeq\u0000~\u0000Jsq\u0000~\u0000#ppsq\u0000~\u0000%q\u0000~\u0000(pq\u0000~\u0000)q\u0000~\u00002q"
+"\u0000~\u00006sq\u0000~\u00000t\u0000\u0004Roleq\u0000~\u0000:q\u0000~\u00006sq\u0000~\u0000#ppsq\u0000~\u0000<q\u0000~\u0000(psq\u0000~\u0000\u0000q\u0000~\u0000(p\u0000"
+"sq\u0000~\u0000\u0007ppq\u0000~\u0000\u0012sq\u0000~\u0000#ppsq\u0000~\u0000%q\u0000~\u0000(pq\u0000~\u0000)q\u0000~\u00002q\u0000~\u00006sq\u0000~\u00000t\u0000\u000eInZ"
+"oomedObjectq\u0000~\u0000:q\u0000~\u00006sq\u0000~\u0000#ppsq\u0000~\u0000<q\u0000~\u0000(psq\u0000~\u0000\u0000q\u0000~\u0000(p\u0000sq\u0000~\u0000\u0007"
+"ppq\u0000~\u0000\u0012sq\u0000~\u0000#ppsq\u0000~\u0000%q\u0000~\u0000(pq\u0000~\u0000)q\u0000~\u00002q\u0000~\u00006sq\u0000~\u00000t\u0000\u000fInZoomedP"
+"rocessq\u0000~\u0000:q\u0000~\u00006sq\u0000~\u0000#ppsq\u0000~\u0000%q\u0000~\u0000(pq\u0000~\u0000)q\u0000~\u00002q\u0000~\u00006sq\u0000~\u00000t\u0000\u0017"
+"ObjectInZoomingSentenceq\u0000~\u0000:sr\u0000\"com.sun.msv.grammar.Expressi"
+"onPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expressi"
+"onPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$C"
+"losedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom"
+"/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0018\u0001pq\u0000~\u0000\tq\u0000~\u0000?q\u0000~\u0000$q\u0000~\u0000S"
+"q\u0000~\u0000[q\u0000~\u0000Xq\u0000~\u0000cq\u0000~\u0000`q\u0000~\u0000gq\u0000~\u0000\u000bq\u0000~\u0000Wq\u0000~\u0000_q\u0000~\u0000Lq\u0000~\u0000;q\u0000~\u0000\u000eq\u0000~\u0000Z"
+"q\u0000~\u0000bq\u0000~\u0000\fq\u0000~\u0000>q\u0000~\u0000Bq\u0000~\u0000Oq\u0000~\u0000Aq\u0000~\u0000Nq\u0000~\u0000\nx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends extensionTools.opl2.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            this.state = startState;
        }

        public java.lang.Object owner() {
            return extensionTools.opl2.generated.impl.ObjectInZoomingSentenceImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            outer:
            while (true) {
                switch (this.state) {
                    case  0 :
                        if (("ObjectInZoomingSentence" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 1;
                            return ;
                        }
                        break;
                    case  3 :
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  1 :
                        if (("ObjectName" == ___local)&&("" == ___uri)) {
                            this.spawnHandlerFromEnterElement((((extensionTools.opl2.generated.impl.ObjectInZoomingSentenceTypeImpl)extensionTools.opl2.generated.impl.ObjectInZoomingSentenceImpl.this).new Unmarshaller(this.context)), 2, ___uri, ___local, ___qname, __atts);
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
            outer:
            while (true) {
                switch (this.state) {
                    case  2 :
                        if (("ObjectInZoomingSentence" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 3;
                            return ;
                        }
                        break;
                    case  3 :
                        this.revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
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
                    case  3 :
                        this.revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            outer:
            while (true) {
                switch (this.state) {
                    case  3 :
                        this.revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            outer:
            while (true) {
                try {
                    switch (this.state) {
                        case  3 :
                            this.revertToParentFromText(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    this.handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
