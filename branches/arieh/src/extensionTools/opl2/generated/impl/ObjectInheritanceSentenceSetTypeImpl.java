//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class ObjectInheritanceSentenceSetTypeImpl implements extensionTools.opl2.generated.ObjectInheritanceSentenceSetType, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Role;
    protected java.lang.String _ObjectName;
    protected com.sun.xml.bind.util.ListImpl _ObjectInheritanceSentence;
    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    protected com.sun.xml.bind.util.ListImpl _getRole() {
        if (this._Role == null) {
            this._Role = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return this._Role;
    }

    public java.util.List getRole() {
        return this._getRole();
    }

    public java.lang.String getObjectName() {
        return this._ObjectName;
    }

    public void setObjectName(java.lang.String value) {
        this._ObjectName = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getObjectInheritanceSentence() {
        if (this._ObjectInheritanceSentence == null) {
            this._ObjectInheritanceSentence = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return this._ObjectInheritanceSentence;
    }

    public java.util.List getObjectInheritanceSentence() {
        return this._getObjectInheritanceSentence();
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ObjectInheritanceSentenceSetTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((this._Role == null)? 0 :this._Role.size());
        int idx3 = 0;
        final int len3 = ((this._ObjectInheritanceSentence == null)? 0 :this._ObjectInheritanceSentence.size());
        context.startElement("", "ObjectName");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) this._ObjectName), "ObjectName");
        } catch (java.lang.Exception e) {
            extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx1 != len1) {
            if (this._Role.get(idx1) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._Role.get(idx1 ++)), "Role");
            } else {
                context.startElement("", "Role");
                int idx_2 = idx1;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._Role.get(idx_2 ++)), "Role");
                context.endNamespaceDecls();
                int idx_3 = idx1;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._Role.get(idx_3 ++)), "Role");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._Role.get(idx1 ++)), "Role");
                context.endElement();
            }
        }
        while (idx3 != len3) {
            if (this._ObjectInheritanceSentence.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ObjectInheritanceSentence.get(idx3 ++)), "ObjectInheritanceSentence");
            } else {
                context.startElement("", "ObjectInheritanceSentence");
                int idx_4 = idx3;
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ObjectInheritanceSentence.get(idx_4 ++)), "ObjectInheritanceSentence");
                context.endNamespaceDecls();
                int idx_5 = idx3;
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ObjectInheritanceSentence.get(idx_5 ++)), "ObjectInheritanceSentence");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) this._ObjectInheritanceSentence.get(idx3 ++)), "ObjectInheritanceSentence");
                context.endElement();
            }
        }
    }

    public void serializeAttributes(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((this._Role == null)? 0 :this._Role.size());
        int idx3 = 0;
        final int len3 = ((this._ObjectInheritanceSentence == null)? 0 :this._ObjectInheritanceSentence.size());
        while (idx1 != len1) {
            if (this._Role.get(idx1) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._Role.get(idx1 ++)), "Role");
            } else {
                idx1 += 1;
            }
        }
        while (idx3 != len3) {
            if (this._ObjectInheritanceSentence.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) this._ObjectInheritanceSentence.get(idx3 ++)), "ObjectInheritanceSentence");
            } else {
                idx3 += 1;
            }
        }
    }

    public void serializeURIs(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((this._Role == null)? 0 :this._Role.size());
        int idx3 = 0;
        final int len3 = ((this._ObjectInheritanceSentence == null)? 0 :this._ObjectInheritanceSentence.size());
        while (idx1 != len1) {
            if (this._Role.get(idx1) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._Role.get(idx1 ++)), "Role");
            } else {
                idx1 += 1;
            }
        }
        while (idx3 != len3) {
            if (this._ObjectInheritanceSentence.get(idx3) instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) this._ObjectInheritanceSentence.get(idx3 ++)), "ObjectInheritanceSentence");
            } else {
                idx3 += 1;
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (extensionTools.opl2.generated.ObjectInheritanceSentenceSetType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000\'com.sun.msv.grammar.trex.Ele"
+"mentPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/Na"
+"meClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aigno"
+"reUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000pps"
+"r\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxn"
+"g/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/uti"
+"l/StringPair;xq\u0000~\u0000\u0003ppsr\u0000#com.sun.msv.datatype.xsd.StringType"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.Buil"
+"tinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.Concret"
+"eType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~"
+"\u0000\u0014L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProces"
+"sor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u00005com.su"
+"n.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr"
+"\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001"
+"sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tloca"
+"lNameq\u0000~\u0000\u0014L\u0000\fnamespaceURIq\u0000~\u0000\u0014xpq\u0000~\u0000\u0018q\u0000~\u0000\u0017sr\u0000\u001dcom.sun.msv.gr"
+"ammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.At"
+"tributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\bxq\u0000~\u0000\u0003sr\u0000\u0011j"
+"ava.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\fppsr\u0000\"com.sun.m"
+"sv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0011q\u0000~\u0000\u0017t\u0000\u0005QNamesr\u00005c"
+"om.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xq\u0000~\u0000\u001aq\u0000~\u0000\u001dsq\u0000~\u0000\u001eq\u0000~\u0000)q\u0000~\u0000\u0017sr\u0000#com.sun.msv.grammar.Simple"
+"NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0014L\u0000\fnamespaceURIq\u0000~\u0000\u0014xr\u0000"
+"\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://"
+"www.w3.org/2001/XMLSchema-instancesr\u00000com.sun.msv.grammar.Ex"
+"pression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000$\u0001psq\u0000~\u0000-t\u0000\n"
+"ObjectNamet\u0000\u0000sq\u0000~\u0000 ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~"
+"\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000%psq\u0000~\u0000 q\u0000~\u0000%psq\u0000~\u0000\u0007q\u0000~\u0000%p\u0000sq\u0000~\u0000 ppsq\u0000~\u00009q\u0000~\u0000%ps"
+"q\u0000~\u0000\"q\u0000~\u0000%psr\u00002com.sun.msv.grammar.Expression$AnyStringExpre"
+"ssion\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u00004psr\u0000 com.sun.msv.grammar.AnyNameC"
+"lass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000.q\u0000~\u00003sq\u0000~\u0000-t\u0000\"extensionTools.opl2.gener"
+"ated.Rolet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000"
+"\u0007q\u0000~\u0000%p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000 ppsq\u0000~\u00009q\u0000~\u0000%psq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~"
+"\u0000Bq\u0000~\u0000Dq\u0000~\u00003sq\u0000~\u0000-t\u0000&extensionTools.opl2.generated.RoleTypeq"
+"\u0000~\u0000Gsq\u0000~\u0000 ppsq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~\u0000&q\u0000~\u0000/q\u0000~\u00003sq\u0000~\u0000-t\u0000\u0004Roleq\u0000~\u00007q\u0000~"
+"\u00003sq\u0000~\u0000 ppsq\u0000~\u00009q\u0000~\u0000%psq\u0000~\u0000 q\u0000~\u0000%psq\u0000~\u0000\u0007q\u0000~\u0000%p\u0000sq\u0000~\u0000 ppsq\u0000~\u0000"
+"9q\u0000~\u0000%psq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~\u0000Bq\u0000~\u0000Dq\u0000~\u00003sq\u0000~\u0000-t\u00007extensionTools.op"
+"l2.generated.ObjectInheritanceSentenceq\u0000~\u0000Gsq\u0000~\u0000\u0007q\u0000~\u0000%p\u0000sq\u0000~"
+"\u0000\u0000ppsq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000 ppsq\u0000~\u00009q\u0000~\u0000%psq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~\u0000Bq\u0000~\u0000Dq\u0000~\u00003"
+"sq\u0000~\u0000-t\u0000;extensionTools.opl2.generated.ObjectInheritanceSent"
+"enceTypeq\u0000~\u0000Gsq\u0000~\u0000 ppsq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~\u0000&q\u0000~\u0000/q\u0000~\u00003sq\u0000~\u0000-t\u0000\u0019Obj"
+"ectInheritanceSentenceq\u0000~\u00007q\u0000~\u00003sr\u0000\"com.sun.msv.grammar.Expr"
+"essionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expr"
+"essionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPo"
+"ol$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$"
+"Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0016\u0001pq\u0000~\u0000<q\u0000~\u0000Vq\u0000~\u0000!q"
+"\u0000~\u0000Pq\u0000~\u0000eq\u0000~\u0000Iq\u0000~\u0000^q\u0000~\u00008q\u0000~\u0000Tq\u0000~\u0000\u000bq\u0000~\u0000\u0006q\u0000~\u0000;q\u0000~\u0000Uq\u0000~\u0000?q\u0000~\u0000Lq"
+"\u0000~\u0000Yq\u0000~\u0000aq\u0000~\u0000>q\u0000~\u0000Kq\u0000~\u0000Xq\u0000~\u0000`q\u0000~\u0000\u0005x"));
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
            return extensionTools.opl2.generated.impl.ObjectInheritanceSentenceSetTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  5 :
                        if (("InheritanceFatherName" == ___local)&&("" == ___uri)) {
                            ObjectInheritanceSentenceSetTypeImpl.this._getObjectInheritanceSentence().add(((extensionTools.opl2.generated.impl.ObjectInheritanceSentenceTypeImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ObjectInheritanceSentenceTypeImpl.class), 6, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  7 :
                        if (("ObjectInheritanceSentence" == ___local)&&("" == ___uri)) {
                            ObjectInheritanceSentenceSetTypeImpl.this._getObjectInheritanceSentence().add(((extensionTools.opl2.generated.impl.ObjectInheritanceSentenceImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ObjectInheritanceSentenceImpl.class), 7, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ObjectInheritanceSentence" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 5;
                            return ;
                        }
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  3 :
                        if (("Role" == ___local)&&("" == ___uri)) {
                            ObjectInheritanceSentenceSetTypeImpl.this._getRole().add(((extensionTools.opl2.generated.impl.RoleImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.RoleImpl.class), 4, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Role" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 8;
                            return ;
                        }
                        this.state = 4;
                        continue outer;
                    case  8 :
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
                    case  0 :
                        if (("ObjectName" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 1;
                            return ;
                        }
                        break;
                    case  4 :
                        if (("Role" == ___local)&&("" == ___uri)) {
                            ObjectInheritanceSentenceSetTypeImpl.this._getRole().add(((extensionTools.opl2.generated.impl.RoleImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.RoleImpl.class), 4, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("Role" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 8;
                            return ;
                        }
                        if (("ObjectInheritanceSentence" == ___local)&&("" == ___uri)) {
                            ObjectInheritanceSentenceSetTypeImpl.this._getObjectInheritanceSentence().add(((extensionTools.opl2.generated.impl.ObjectInheritanceSentenceImpl) this.spawnChildFromEnterElement((extensionTools.opl2.generated.impl.ObjectInheritanceSentenceImpl.class), 7, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("ObjectInheritanceSentence" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 5;
                            return ;
                        }
                        this.state = 7;
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
                    case  9 :
                        if (("Role" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 4;
                            return ;
                        }
                        break;
                    case  7 :
                        this.revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        this.state = 4;
                        continue outer;
                    case  2 :
                        if (("ObjectName" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 3;
                            return ;
                        }
                        break;
                    case  8 :
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
                    case  6 :
                        if (("ObjectInheritanceSentence" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 7;
                            return ;
                        }
                        break;
                    case  4 :
                        this.state = 7;
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
                    case  7 :
                        this.revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        this.state = 4;
                        continue outer;
                    case  8 :
                        if (("Library" == ___local)&&("" == ___uri)) {
                            ObjectInheritanceSentenceSetTypeImpl.this._getRole().add(((extensionTools.opl2.generated.impl.RoleTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 9, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("RoleName" == ___local)&&("" == ___uri)) {
                            ObjectInheritanceSentenceSetTypeImpl.this._getRole().add(((extensionTools.opl2.generated.impl.RoleTypeImpl) this.spawnChildFromEnterAttribute((extensionTools.opl2.generated.impl.RoleTypeImpl.class), 9, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  4 :
                        this.state = 7;
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
                    case  7 :
                        this.revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        this.state = 4;
                        continue outer;
                    case  8 :
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
                        this.state = 7;
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
                        case  1 :
                            this.state = 2;
                            this.eatText1(value);
                            return ;
                        case  7 :
                            this.revertToParentFromText(value);
                            return ;
                        case  3 :
                            this.state = 4;
                            continue outer;
                        case  8 :
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
                            this.state = 7;
                            continue outer;
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
                ObjectInheritanceSentenceSetTypeImpl.this._ObjectName = value;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

    }

}
