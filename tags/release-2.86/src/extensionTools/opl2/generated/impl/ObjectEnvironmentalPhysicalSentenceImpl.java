//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class ObjectEnvironmentalPhysicalSentenceImpl
    extends extensionTools.opl2.generated.impl.ObjectEnvironmentalPhysicalSentenceTypeImpl
    implements extensionTools.opl2.generated.ObjectEnvironmentalPhysicalSentence, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "ObjectEnvironmentalPhysicalSentence";
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ObjectEnvironmentalPhysicalSentenceImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "ObjectEnvironmentalPhysicalSentence");
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
        return (extensionTools.opl2.generated.ObjectEnvironmentalPhysicalSentence.class);
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
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsr\u0000\u001bcom.sun.msv.grammar.Data"
+"Exp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exce"
+"ptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000#co"
+"m.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValid"
+"xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000"
+"%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun"
+".msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000"
+"\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0018L\u0000\nwhiteSpacet\u0000.Lcom/sun/"
+"msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/"
+"2001/XMLSchemat\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteSpa"
+"ceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd."
+"WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Exp"
+"ression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv."
+"util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0018L\u0000\fnamespaceURIq\u0000"
+"~\u0000\u0018xpq\u0000~\u0000\u001cq\u0000~\u0000\u001bsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"q\u0000~\u0000\bppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003exp"
+"q\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001"
+"Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0010ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0015q\u0000~\u0000\u001bt\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.Whi"
+"teSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001eq\u0000~\u0000!sq\u0000~\u0000\"q\u0000~\u0000-q\u0000"
+"~\u0000\u001bsr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tloca"
+"lNameq\u0000~\u0000\u0018L\u0000\fnamespaceURIq\u0000~\u0000\u0018xr\u0000\u001dcom.sun.msv.grammar.NameCl"
+"ass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-i"
+"nstancesr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000(\u0001psq\u0000~\u00001t\u0000\nObjectNamet\u0000\u0000sq\u0000~\u0000$ppsr\u0000 co"
+"m.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.gra"
+"mmar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000)psq\u0000~\u0000$q\u0000~\u0000)ps"
+"q\u0000~\u0000\u0000q\u0000~\u0000)p\u0000sq\u0000~\u0000$ppsq\u0000~\u0000=q\u0000~\u0000)psq\u0000~\u0000&q\u0000~\u0000)psr\u00002com.sun.msv."
+"grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u00008"
+"psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00002q\u0000~\u00007s"
+"q\u0000~\u00001t\u0000\"extensionTools.opl2.generated.Rolet\u0000+http://java.sun"
+".com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000)p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq"
+"\u0000~\u0000$ppsq\u0000~\u0000=q\u0000~\u0000)psq\u0000~\u0000&q\u0000~\u0000)pq\u0000~\u0000Fq\u0000~\u0000Hq\u0000~\u00007sq\u0000~\u00001t\u0000&extens"
+"ionTools.opl2.generated.RoleTypeq\u0000~\u0000Ksq\u0000~\u0000$ppsq\u0000~\u0000&q\u0000~\u0000)pq\u0000~"
+"\u0000*q\u0000~\u00003q\u0000~\u00007sq\u0000~\u00001t\u0000\u0004Roleq\u0000~\u0000;q\u0000~\u00007sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0010pp"
+"sr\u0000$com.sun.msv.datatype.xsd.BooleanType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0015q\u0000~"
+"\u0000\u001bt\u0000\u0007booleanq\u0000~\u0000/q\u0000~\u0000!sq\u0000~\u0000\"q\u0000~\u0000]q\u0000~\u0000\u001bsq\u0000~\u0000$ppsq\u0000~\u0000&q\u0000~\u0000)pq\u0000"
+"~\u0000*q\u0000~\u00003q\u0000~\u00007sq\u0000~\u00001t\u0000\rEnvironmentalq\u0000~\u0000;sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~"
+"\u0000Zsq\u0000~\u0000$ppsq\u0000~\u0000&q\u0000~\u0000)pq\u0000~\u0000*q\u0000~\u00003q\u0000~\u00007sq\u0000~\u00001t\u0000\bPhysicalq\u0000~\u0000;s"
+"q\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000Zsq\u0000~\u0000$ppsq\u0000~\u0000&q\u0000~\u0000)pq\u0000~\u0000*q\u0000~\u00003q\u0000~\u00007sq\u0000~"
+"\u00001t\u0000\tTransientq\u0000~\u0000;sq\u0000~\u0000$ppsq\u0000~\u0000&q\u0000~\u0000)pq\u0000~\u0000*q\u0000~\u00003q\u0000~\u00007sq\u0000~\u00001"
+"t\u0000#ObjectEnvironmentalPhysicalSentenceq\u0000~\u0000;sr\u0000\"com.sun.msv.g"
+"rammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/g"
+"rammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.E"
+"xpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL"
+"\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0017\u0001pq\u0000~\u0000@"
+"q\u0000~\u0000%q\u0000~\u0000Tq\u0000~\u0000_q\u0000~\u0000eq\u0000~\u0000kq\u0000~\u0000oq\u0000~\u0000Yq\u0000~\u0000dq\u0000~\u0000jq\u0000~\u0000\nq\u0000~\u0000Mq\u0000~\u0000<"
+"q\u0000~\u0000\u000fq\u0000~\u0000\rq\u0000~\u0000\u000bq\u0000~\u0000?q\u0000~\u0000Cq\u0000~\u0000Pq\u0000~\u0000\fq\u0000~\u0000\tq\u0000~\u0000Bq\u0000~\u0000Ox"));
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
            return extensionTools.opl2.generated.impl.ObjectEnvironmentalPhysicalSentenceImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            outer:
            while (true) {
                switch (this.state) {
                    case  1 :
                        if (("ObjectName" == ___local)&&("" == ___uri)) {
                            this.spawnHandlerFromEnterElement((((extensionTools.opl2.generated.impl.ObjectEnvironmentalPhysicalSentenceTypeImpl)extensionTools.opl2.generated.impl.ObjectEnvironmentalPhysicalSentenceImpl.this).new Unmarshaller(this.context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  0 :
                        if (("ObjectEnvironmentalPhysicalSentence" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 1;
                            return ;
                        }
                        break;
                    case  3 :
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
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
                        if (("ObjectEnvironmentalPhysicalSentence" == ___local)&&("" == ___uri)) {
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
