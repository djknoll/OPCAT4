//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class ObjectUniDirectionalRelationSentenceImpl
    extends extensionTools.opl2.generated.impl.ObjectUniDirectionalRelationSentenceTypeImpl
    implements extensionTools.opl2.generated.ObjectUniDirectionalRelationSentence, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "ObjectUniDirectionalRelationSentence";
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ObjectUniDirectionalRelationSentenceImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "ObjectUniDirectionalRelationSentence");
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
        return (extensionTools.opl2.generated.ObjectUniDirectionalRelationSentence.class);
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
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~"
+"\u0000\u0007ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/r"
+"elaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/ms"
+"v/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000 com.sun.msv.datatype.xsd.IntTy"
+"pe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType"
+"\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbaseFacetst\u0000)Lcom/sun/msv/datatype/xsd/XSDatat"
+"ypeImpl;xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000"
+"\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamesp"
+"aceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u001eL\u0000\nwhiteSpacet\u0000.L"
+"com/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www"
+".w3.org/2001/XMLSchemat\u0000\u0003intsr\u00005com.sun.msv.datatype.xsd.Whi"
+"teSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype"
+".xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u0000*com.sun.msv.datatyp"
+"e.xsd.MaxInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.x"
+"sd.RangeFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\nlimitValuet\u0000\u0012Ljava/lang/Object;xr"
+"\u00009com.sun.msv.datatype.xsd.DataTypeWithValueConstraintFacet\""
+"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFlagL\u0000\bbaseTypeq\u0000~\u0000\u001aL"
+"\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/xsd/ConcreteType;L\u0000\tf"
+"acetNameq\u0000~\u0000\u001exq\u0000~\u0000\u001dppq\u0000~\u0000%\u0000\u0001sr\u0000*com.sun.msv.datatype.xsd.Min"
+"InclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\'ppq\u0000~\u0000%\u0000\u0000sr\u0000!com.sun.msv.data"
+"type.xsd.LongType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0019q\u0000~\u0000!t\u0000\u0004longq\u0000~\u0000%sq\u0000~\u0000&ppq"
+"\u0000~\u0000%\u0000\u0001sq\u0000~\u0000-ppq\u0000~\u0000%\u0000\u0000sr\u0000$com.sun.msv.datatype.xsd.IntegerTyp"
+"e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0019q\u0000~\u0000!t\u0000\u0007integerq\u0000~\u0000%sr\u0000,com.sun.msv.dataty"
+"pe.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.sun.msv"
+".datatype.xsd.DataTypeWithLexicalConstraintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000x"
+"q\u0000~\u0000*ppq\u0000~\u0000%\u0001\u0000sr\u0000#com.sun.msv.datatype.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001bq\u0000~\u0000!t\u0000\u0007decimalq\u0000~\u0000%q\u0000~\u0000;t\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~\u0000"
+"5t\u0000\fminInclusivesr\u0000\u000ejava.lang.Long;\u008b\u00e4\u0090\u00cc\u008f#\u00df\u0002\u0000\u0001J\u0000\u0005valuexr\u0000\u0010jav"
+"a.lang.Number\u0086\u00ac\u0095\u001d\u000b\u0094\u00e0\u008b\u0002\u0000\u0000xp\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000q\u0000~\u00005t\u0000\fmaxInclusivesq\u0000~\u0000?"
+"\u007f\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffq\u0000~\u00000q\u0000~\u0000>sr\u0000\u0011java.lang.Integer\u0012\u00e2\u00a0\u00a4\u00f7\u0081\u00878\u0002\u0000\u0001I\u0000\u0005valuexq"
+"\u0000~\u0000@\u0080\u0000\u0000\u0000q\u0000~\u00000q\u0000~\u0000Bsq\u0000~\u0000D\u007f\u00ff\u00ff\u00ffsr\u00000com.sun.msv.grammar.Expressi"
+"on$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.util."
+"StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001eL\u0000\fnamespaceURIq\u0000~\u0000\u001exp"
+"q\u0000~\u0000\"q\u0000~\u0000!sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\b"
+"ppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003"
+"L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005va"
+"luexp\u0000psq\u0000~\u0000\u0014ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001bq\u0000~\u0000!t\u0000\u0005QNameq\u0000~\u0000%q\u0000~\u0000Hsq\u0000~\u0000Iq\u0000~\u0000Tq\u0000~\u0000!sr\u0000#com.sun"
+".msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001eL\u0000\fn"
+"amespaceURIq\u0000~\u0000\u001exr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instancesr\u00000com"
+".sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~"
+"\u0000\u0004sq\u0000~\u0000O\u0001psq\u0000~\u0000Vt\u0000\u0018SourceMinimalCardinalityt\u0000\u0000sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000"
+"\u0007ppq\u0000~\u0000\u0017sq\u0000~\u0000Kppsq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000~\u0000Qq\u0000~\u0000Xq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000\u0018SourceMa"
+"ximalCardinalityq\u0000~\u0000`sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0014ppsr\u0000#com.sun.ms"
+"v.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000\u001bq\u0000"
+"~\u0000!t\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor"
+"$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000$\u0001q\u0000~\u0000Hsq\u0000~\u0000Iq\u0000~\u0000lq\u0000~\u0000!sq\u0000~\u0000Kppsq\u0000~"
+"\u0000Mq\u0000~\u0000Ppq\u0000~\u0000Qq\u0000~\u0000Xq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000\nSourceNameq\u0000~\u0000`sq\u0000~\u0000Kppsq\u0000~\u0000"
+"Kq\u0000~\u0000Ppsq\u0000~\u0000\u0000q\u0000~\u0000Pp\u0000sq\u0000~\u0000Kppsr\u0000 com.sun.msv.grammar.OneOrMor"
+"eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000Ppsq\u0000~\u0000Mq\u0000~\u0000Ppsr\u00002com.sun.msv.grammar.Exp"
+"ression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000]psr\u0000 com.su"
+"n.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Wq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000(ext"
+"ensionTools.opl2.generated.SourceRolet\u0000+http://java.sun.com/"
+"jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000Pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000Kp"
+"psq\u0000~\u0000xq\u0000~\u0000Ppsq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000~\u0000}q\u0000~\u0000\u007fq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000,extensionTo"
+"ols.opl2.generated.SourceRoleTypeq\u0000~\u0000\u0082sq\u0000~\u0000Kppsq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000"
+"~\u0000Qq\u0000~\u0000Xq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000\nSourceRoleq\u0000~\u0000`q\u0000~\u0000\\sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq"
+"\u0000~\u0000isq\u0000~\u0000Kppsq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000~\u0000Qq\u0000~\u0000Xq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000\fRelationName"
+"q\u0000~\u0000`sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000\u0017sq\u0000~\u0000Kppsq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000~\u0000Qq\u0000~\u0000Xq\u0000~"
+"\u0000\\sq\u0000~\u0000Vt\u0000\u001dDestinationMinimalCardinalityq\u0000~\u0000`sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007"
+"ppq\u0000~\u0000\u0017sq\u0000~\u0000Kppsq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000~\u0000Qq\u0000~\u0000Xq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000\u001dDestinati"
+"onMaximalCardinalityq\u0000~\u0000`sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000isq\u0000~\u0000Kppsq\u0000~\u0000"
+"Mq\u0000~\u0000Ppq\u0000~\u0000Qq\u0000~\u0000Xq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000\u000fDestinationNameq\u0000~\u0000`sq\u0000~\u0000Kpps"
+"q\u0000~\u0000Kq\u0000~\u0000Ppsq\u0000~\u0000\u0000q\u0000~\u0000Pp\u0000sq\u0000~\u0000Kppsq\u0000~\u0000xq\u0000~\u0000Ppsq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000~\u0000"
+"}q\u0000~\u0000\u007fq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000-extensionTools.opl2.generated.Destinatio"
+"nRoleq\u0000~\u0000\u0082sq\u0000~\u0000\u0000q\u0000~\u0000Pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000Kppsq\u0000~\u0000xq\u0000~\u0000Pp"
+"sq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000~\u0000}q\u0000~\u0000\u007fq\u0000~\u0000\\sq\u0000~\u0000Vt\u00001extensionTools.opl2.gene"
+"rated.DestinationRoleTypeq\u0000~\u0000\u0082sq\u0000~\u0000Kppsq\u0000~\u0000Mq\u0000~\u0000Ppq\u0000~\u0000Qq\u0000~\u0000X"
+"q\u0000~\u0000\\sq\u0000~\u0000Vt\u0000\u000fDestinationRoleq\u0000~\u0000`q\u0000~\u0000\\sq\u0000~\u0000Kppsq\u0000~\u0000Mq\u0000~\u0000Ppq"
+"\u0000~\u0000Qq\u0000~\u0000Xq\u0000~\u0000\\sq\u0000~\u0000Vt\u0000$ObjectUniDirectionalRelationSentenceq"
+"\u0000~\u0000`sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpT"
+"ablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-"
+"com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005c"
+"ountB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/Express"
+"ionPool;xp\u0000\u0000\u0000(\u0001pq\u0000~\u0000\fq\u0000~\u0000\u000bq\u0000~\u0000uq\u0000~\u0000\u00a8q\u0000~\u0000Lq\u0000~\u0000cq\u0000~\u0000pq\u0000~\u0000\u008bq\u0000~\u0000"
+"\u0091q\u0000~\u0000\u0097q\u0000~\u0000\rq\u0000~\u0000\u009dq\u0000~\u0000\u00a3q\u0000~\u0000\u00b7q\u0000~\u0000\u00bbq\u0000~\u0000\u000fq\u0000~\u0000\u0084q\u0000~\u0000\u00b0q\u0000~\u0000\u0010q\u0000~\u0000hq\u0000~\u0000"
+"\u0090q\u0000~\u0000\u00a2q\u0000~\u0000\tq\u0000~\u0000\u0013q\u0000~\u0000bq\u0000~\u0000\u0096q\u0000~\u0000\u009cq\u0000~\u0000zq\u0000~\u0000\u0087q\u0000~\u0000\u00abq\u0000~\u0000\u00b3q\u0000~\u0000\nq\u0000~\u0000"
+"wq\u0000~\u0000\u0086q\u0000~\u0000\u00aaq\u0000~\u0000\u00b2q\u0000~\u0000\u0011q\u0000~\u0000tq\u0000~\u0000\u00a7q\u0000~\u0000\u000ex"));
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
            return extensionTools.opl2.generated.impl.ObjectUniDirectionalRelationSentenceImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            outer:
            while (true) {
                switch (this.state) {
                    case  1 :
                        if (("SourceMinimalCardinality" == ___local)&&("" == ___uri)) {
                            this.spawnHandlerFromEnterElement((((extensionTools.opl2.generated.impl.ObjectUniDirectionalRelationSentenceTypeImpl)extensionTools.opl2.generated.impl.ObjectUniDirectionalRelationSentenceImpl.this).new Unmarshaller(this.context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  3 :
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("ObjectUniDirectionalRelationSentence" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, false);
                            this.state = 1;
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
                    case  3 :
                        this.revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("ObjectUniDirectionalRelationSentence" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 3;
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
