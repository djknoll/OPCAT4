//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class ProcessExhibitionSentenceTypeImpl implements extensionTools.opl2.generated.ProcessExhibitionSentenceType, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Operation;
    protected java.lang.String _ProcessName;
    protected com.sun.xml.bind.util.ListImpl _ExhibitedObject;
    protected com.sun.xml.bind.util.ListImpl _Role;
    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

//    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
//        return (extensionTools.opl2.generated.ProcessExhibitionSentenceType.class);
//    }

    protected com.sun.xml.bind.util.ListImpl _getOperation() {
        if (this._Operation == null) {
            this._Operation = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return this._Operation;
    }

    public java.util.List getOperation() {
        return this._getOperation();
    }

    public java.lang.String getProcessName() {
        return this._ProcessName;
    }

    public void setProcessName(java.lang.String value) {
        this._ProcessName = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getExhibitedObject() {
        if (this._ExhibitedObject == null) {
            this._ExhibitedObject = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return this._ExhibitedObject;
    }

    public java.util.List getExhibitedObject() {
        return this._getExhibitedObject();
    }

    protected com.sun.xml.bind.util.ListImpl _getRole() {
        if (this._Role == null) {
            this._Role = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return this._Role;
    }

    public java.util.List getRole() {
        return this._getRole();
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ProcessExhibitionSentenceTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((this._Operation == null)? 0 :this._Operation.size());
        int idx3 = 0;
        final int len3 = ((this._ExhibitedObject == null)? 0 :this._ExhibitedObject.size());
        int idx4 = 0;
        final int len4 = ((this._Role == null)? 0 :this._Role.size());
        context.startElement("", "ProcessName");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) this._ProcessName), "ProcessName");
        } catch (java.lang.Exception e) {
            extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx4 != len4) {
            if (this._Role.get(idx4) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._Role.get(idx4 ++)), "Role");
            } else {
                context.startElement("", "Role");
                int idx_2 = idx4;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._Role.get(idx_2 ++)), "Role");
                context.endNamespaceDecls();
                int idx_3 = idx4;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._Role.get(idx_3 ++)), "Role");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._Role.get(idx4 ++)), "Role");
                context.endElement();
            }
        }
        while (idx1 != len1) {
            if (this._Operation.get(idx1) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._Operation.get(idx1 ++)), "Operation");
            } else {
                context.startElement("", "Operation");
                int idx_4 = idx1;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._Operation.get(idx_4 ++)), "Operation");
                context.endNamespaceDecls();
                int idx_5 = idx1;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._Operation.get(idx_5 ++)), "Operation");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._Operation.get(idx1 ++)), "Operation");
                context.endElement();
            }
        }
        while (idx3 != len3) {
            if (this._ExhibitedObject.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ExhibitedObject.get(idx3 ++)), "ExhibitedObject");
            } else {
                context.startElement("", "ExhibitedObject");
                int idx_6 = idx3;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ExhibitedObject.get(idx_6 ++)), "ExhibitedObject");
                context.endNamespaceDecls();
                int idx_7 = idx3;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ExhibitedObject.get(idx_7 ++)), "ExhibitedObject");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ExhibitedObject.get(idx3 ++)), "ExhibitedObject");
                context.endElement();
            }
        }
    }

    public void serializeAttributes(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((this._Operation == null)? 0 :this._Operation.size());
        int idx3 = 0;
        final int len3 = ((this._ExhibitedObject == null)? 0 :this._ExhibitedObject.size());
        int idx4 = 0;
        final int len4 = ((this._Role == null)? 0 :this._Role.size());
        while (idx4 != len4) {
            if (this._Role.get(idx4) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._Role.get(idx4 ++)), "Role");
            } else {
                idx4 += 1;
            }
        }
        while (idx1 != len1) {
            if (this._Operation.get(idx1) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._Operation.get(idx1 ++)), "Operation");
            } else {
                idx1 += 1;
            }
        }
        while (idx3 != len3) {
            if (this._ExhibitedObject.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ExhibitedObject.get(idx3 ++)), "ExhibitedObject");
            } else {
                idx3 += 1;
            }
        }
    }

    public void serializeURIs(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((this._Operation == null)? 0 :this._Operation.size());
        int idx3 = 0;
        final int len3 = ((this._ExhibitedObject == null)? 0 :this._ExhibitedObject.size());
        int idx4 = 0;
        final int len4 = ((this._Role == null)? 0 :this._Role.size());
        while (idx4 != len4) {
            if (this._Role.get(idx4) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._Role.get(idx4 ++)), "Role");
            } else {
                idx4 += 1;
            }
        }
        while (idx1 != len1) {
            if (this._Operation.get(idx1) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._Operation.get(idx1 ++)), "Operation");
            } else {
                idx1 += 1;
            }
        }
        while (idx3 != len3) {
            if (this._ExhibitedObject.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ExhibitedObject.get(idx3 ++)), "ExhibitedObject");
            } else {
                idx3 += 1;
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ProcessExhibitionSentenceType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\'com.sun.msv.grammar."
+"trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/gr"
+"ammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000s"
+"q\u0000~\u0000\u0000ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLor"
+"g/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun"
+"/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000#com.sun.msv.datatype.xsd.St"
+"ringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype."
+"xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd"
+".ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatat"
+"ypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btyp"
+"eNameq\u0000~\u0000\u0015L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpa"
+"ceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr"
+"\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetExpression"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000"
+"\u0002L\u0000\tlocalNameq\u0000~\u0000\u0015L\u0000\fnamespaceURIq\u0000~\u0000\u0015xpq\u0000~\u0000\u0019q\u0000~\u0000\u0018sr\u0000\u001dcom.su"
+"n.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.gr"
+"ammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\txq\u0000"
+"~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\rppsr\u0000\"c"
+"om.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0012q\u0000~\u0000\u0018t\u0000\u0005QN"
+"amesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001bq\u0000~\u0000\u001esq\u0000~\u0000\u001fq\u0000~\u0000*q\u0000~\u0000\u0018sr\u0000#com.sun.msv.gramma"
+"r.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0015L\u0000\fnamespaceURI"
+"q\u0000~\u0000\u0015xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000"
+")http://www.w3.org/2001/XMLSchema-instancesr\u00000com.sun.msv.gr"
+"ammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000%\u0001ps"
+"q\u0000~\u0000.t\u0000\u000bProcessNamet\u0000\u0000sq\u0000~\u0000!ppsr\u0000 com.sun.msv.grammar.OneOrM"
+"oreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001"
+"L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000&psq\u0000~\u0000!q\u0000~\u0000&psq\u0000~\u0000\bq\u0000~\u0000&p\u0000sq\u0000~\u0000!ppsq\u0000~"
+"\u0000:q\u0000~\u0000&psq\u0000~\u0000#q\u0000~\u0000&psr\u00002com.sun.msv.grammar.Expression$AnySt"
+"ringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u00005psr\u0000 com.sun.msv.grammar"
+".AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000/q\u0000~\u00004sq\u0000~\u0000.t\u0000\"extensionTools.o"
+"pl2.generated.Rolet\u0000+http://java.sun.com/jaxb/xjc/dummy-elem"
+"entssq\u0000~\u0000\bq\u0000~\u0000&p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\bpp\u0000sq\u0000~\u0000!ppsq\u0000~\u0000:q\u0000~\u0000&psq\u0000~\u0000#"
+"q\u0000~\u0000&pq\u0000~\u0000Cq\u0000~\u0000Eq\u0000~\u00004sq\u0000~\u0000.t\u0000&extensionTools.opl2.generated."
+"RoleTypeq\u0000~\u0000Hsq\u0000~\u0000!ppsq\u0000~\u0000#q\u0000~\u0000&pq\u0000~\u0000\'q\u0000~\u00000q\u0000~\u00004sq\u0000~\u0000.t\u0000\u0004Rol"
+"eq\u0000~\u00008q\u0000~\u00004sq\u0000~\u0000!ppsq\u0000~\u0000:q\u0000~\u0000&psq\u0000~\u0000!q\u0000~\u0000&psq\u0000~\u0000\bq\u0000~\u0000&p\u0000sq\u0000~"
+"\u0000!ppsq\u0000~\u0000:q\u0000~\u0000&psq\u0000~\u0000#q\u0000~\u0000&pq\u0000~\u0000Cq\u0000~\u0000Eq\u0000~\u00004sq\u0000~\u0000.t\u0000\'extensio"
+"nTools.opl2.generated.Operationq\u0000~\u0000Hsq\u0000~\u0000\bq\u0000~\u0000&p\u0000sq\u0000~\u0000\u0000ppsq\u0000"
+"~\u0000\bpp\u0000sq\u0000~\u0000!ppsq\u0000~\u0000:q\u0000~\u0000&psq\u0000~\u0000#q\u0000~\u0000&pq\u0000~\u0000Cq\u0000~\u0000Eq\u0000~\u00004sq\u0000~\u0000.t"
+"\u0000+extensionTools.opl2.generated.OperationTypeq\u0000~\u0000Hsq\u0000~\u0000!ppsq"
+"\u0000~\u0000#q\u0000~\u0000&pq\u0000~\u0000\'q\u0000~\u00000q\u0000~\u00004sq\u0000~\u0000.t\u0000\tOperationq\u0000~\u00008q\u0000~\u00004sq\u0000~\u0000!p"
+"psq\u0000~\u0000:q\u0000~\u0000&psq\u0000~\u0000!q\u0000~\u0000&psq\u0000~\u0000\bq\u0000~\u0000&p\u0000sq\u0000~\u0000!ppsq\u0000~\u0000:q\u0000~\u0000&psq"
+"\u0000~\u0000#q\u0000~\u0000&pq\u0000~\u0000Cq\u0000~\u0000Eq\u0000~\u00004sq\u0000~\u0000.t\u0000-extensionTools.opl2.genera"
+"ted.ExhibitedObjectq\u0000~\u0000Hsq\u0000~\u0000\bq\u0000~\u0000&p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\bpp\u0000sq\u0000~\u0000!"
+"ppsq\u0000~\u0000:q\u0000~\u0000&psq\u0000~\u0000#q\u0000~\u0000&pq\u0000~\u0000Cq\u0000~\u0000Eq\u0000~\u00004sq\u0000~\u0000.t\u00001extensionT"
+"ools.opl2.generated.ExhibitedObjectTypeq\u0000~\u0000Hsq\u0000~\u0000!ppsq\u0000~\u0000#q\u0000"
+"~\u0000&pq\u0000~\u0000\'q\u0000~\u00000q\u0000~\u00004sq\u0000~\u0000.t\u0000\u000fExhibitedObjectq\u0000~\u00008q\u0000~\u00004sr\u0000\"com"
+".sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom"
+"/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv"
+".grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstre"
+"amVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000"
+"\u0000\u0000 \u0001pq\u0000~\u0000=q\u0000~\u0000Wq\u0000~\u0000lq\u0000~\u0000\"q\u0000~\u0000Qq\u0000~\u0000fq\u0000~\u0000{q\u0000~\u0000\u0005q\u0000~\u0000Jq\u0000~\u0000_q\u0000~\u0000t"
+"q\u0000~\u00009q\u0000~\u0000Uq\u0000~\u0000jq\u0000~\u0000\fq\u0000~\u0000\u0007q\u0000~\u0000<q\u0000~\u0000Vq\u0000~\u0000kq\u0000~\u0000@q\u0000~\u0000Mq\u0000~\u0000Zq\u0000~\u0000b"
+"q\u0000~\u0000oq\u0000~\u0000wq\u0000~\u0000?q\u0000~\u0000Lq\u0000~\u0000Yq\u0000~\u0000aq\u0000~\u0000nq\u0000~\u0000vq\u0000~\u0000\u0006x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends extensionTools.opl2.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "-------------");
        }

        protected Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            this.state = startState;
        }

        public java.lang.Object owner() {
            return extensionTools.opl2.generated.impl.ProcessExhibitionSentenceTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  6 :
                        if (("ExhibitedObject" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getExhibitedObject().add(((extensionTools.opl2.generated.impl.ExhibitedObjectImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ExhibitedObjectImpl.class), 6, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ExhibitedObject" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 7;
                            return ;
                        }
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  5 :
                        if (("Operation" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getOperation().add(((extensionTools.opl2.generated.impl.OperationImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.OperationImpl.class), 5, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Operation" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 9;
                            return ;
                        }
                        if (("ExhibitedObject" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getExhibitedObject().add(((extensionTools.opl2.generated.impl.ExhibitedObjectImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ExhibitedObjectImpl.class), 6, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ExhibitedObject" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 7;
                            return ;
                        }
                        this.state = 6;
                        continue outer;
                    case  9 :
                        if (("OperationName" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getOperation().add(((extensionTools.opl2.generated.impl.OperationTypeImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.OperationTypeImpl.class), 10, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  7 :
                        if (("MinimalCardinality" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getExhibitedObject().add(((extensionTools.opl2.generated.impl.ExhibitedObjectTypeImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ExhibitedObjectTypeImpl.class), 8, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  0 :
                        if (("ProcessName" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 1;
                            return ;
                        }
                        break;
                    case  3 :
                        if (("Role" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getRole().add(((extensionTools.opl2.generated.impl.RoleImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.RoleImpl.class), 4, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Role" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 11;
                            return ;
                        }
                        this.state = 4;
                        continue outer;
                    case  11 :
                        attIdx = this.context.getAttribute("", "Library");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "RoleName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  4 :
                        if (("Role" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getRole().add(((extensionTools.opl2.generated.impl.RoleImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.RoleImpl.class), 4, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Role" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 11;
                            return ;
                        }
                        if (("Operation" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getOperation().add(((extensionTools.opl2.generated.impl.OperationImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.OperationImpl.class), 5, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Operation" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 9;
                            return ;
                        }
                        this.state = 5;
                        continue outer;
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
                    case  8 :
                        if (("ExhibitedObject" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 6;
                            return ;
                        }
                        break;
                    case  6 :
                        this.revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        this.state = 6;
                        continue outer;
                    case  2 :
                        if (("ProcessName" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 3;
                            return ;
                        }
                        break;
                    case  12 :
                        if (("Role" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 4;
                            return ;
                        }
                        break;
                    case  3 :
                        this.state = 4;
                        continue outer;
                    case  11 :
                        attIdx = this.context.getAttribute("", "Library");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "RoleName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  4 :
                        this.state = 5;
                        continue outer;
                    case  10 :
                        if (("Operation" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 5;
                            return ;
                        }
                        break;
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
                    case  6 :
                        this.revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        this.state = 6;
                        continue outer;
                    case  3 :
                        this.state = 4;
                        continue outer;
                    case  11 :
                        if (("Library" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getRole().add(((extensionTools.opl2.generated.impl.RoleTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 12, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("RoleName" == ___local)&&("" == ___uri)) {
                            ProcessExhibitionSentenceTypeImpl.this._getRole().add(((extensionTools.opl2.generated.impl.RoleTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 12, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  4 :
                        this.state = 5;
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
                    case  6 :
                        this.revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        this.state = 6;
                        continue outer;
                    case  3 :
                        this.state = 4;
                        continue outer;
                    case  11 :
                        attIdx = this.context.getAttribute("", "Library");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "RoleName");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  4 :
                        this.state = 5;
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
                        case  6 :
                            this.revertToParentFromText(value);
                            return ;
                        case  5 :
                            this.state = 6;
                            continue outer;
                        case  3 :
                            this.state = 4;
                            continue outer;
                        case  11 :
                            attIdx = this.context.getAttribute("", "Library");
                            if (attIdx >= 0) {
                                this.context.consumeAttribute(attIdx);
                                this.context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = this.context.getAttribute("", "RoleName");
                            if (attIdx >= 0) {
                                this.context.consumeAttribute(attIdx);
                                this.context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  4 :
                            this.state = 5;
                            continue outer;
                        case  1 :
                            this.state = 2;
                            this.eatText1(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    this.handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                ProcessExhibitionSentenceTypeImpl.this._ProcessName = value;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

    }

}
