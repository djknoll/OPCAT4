//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class ChangingClauseImpl
    extends extensionTools.opl2.generated.impl.ChangingClauseTypeImpl
    implements extensionTools.opl2.generated.ChangingClause, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (extensionTools.opl2.generated.ChangingClause.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "ChangingClause";
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.ChangingClauseImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "ChangingClause");
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
        return (extensionTools.opl2.generated.ChangingClause.class);
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
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000"
+"\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsr\u0000\u001bcom.sun.msv."
+"grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Data"
+"type;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq"
+"\u0000~\u0000\u0004ppsr\u0000 com.sun.msv.datatype.xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com"
+".sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbaseFa"
+"cetst\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xr\u0000*com.sun."
+"msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv"
+".datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatyp"
+"e.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/"
+"String;L\u0000\btypeNameq\u0000~\u0000#L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype"
+"/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSche"
+"mat\u0000\u0003intsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Col"
+"lapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProce"
+"ssor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u0000*com.sun.msv.datatype.xsd.MaxInclusiveFa"
+"cet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.RangeFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0001L\u0000\nlimitValuet\u0000\u0012Ljava/lang/Object;xr\u00009com.sun.msv.dataty"
+"pe.xsd.DataTypeWithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.su"
+"n.msv.datatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFix"
+"edZ\u0000\u0012needValueCheckFlagL\u0000\bbaseTypeq\u0000~\u0000\u001fL\u0000\fconcreteTypet\u0000\'Lco"
+"m/sun/msv/datatype/xsd/ConcreteType;L\u0000\tfacetNameq\u0000~\u0000#xq\u0000~\u0000\"p"
+"pq\u0000~\u0000*\u0000\u0001sr\u0000*com.sun.msv.datatype.xsd.MinInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000,ppq\u0000~\u0000*\u0000\u0000sr\u0000!com.sun.msv.datatype.xsd.LongType\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001eq\u0000~\u0000&t\u0000\u0004longq\u0000~\u0000*sq\u0000~\u0000+ppq\u0000~\u0000*\u0000\u0001sq\u0000~\u00002ppq\u0000~\u0000*\u0000"
+"\u0000sr\u0000$com.sun.msv.datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001eq\u0000"
+"~\u0000&t\u0000\u0007integerq\u0000~\u0000*sr\u0000,com.sun.msv.datatype.xsd.FractionDigit"
+"sFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.sun.msv.datatype.xsd.DataTy"
+"peWithLexicalConstraintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000xq\u0000~\u0000/ppq\u0000~\u0000*\u0001\u0000sr\u0000#co"
+"m.sun.msv.datatype.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000 q\u0000~\u0000&t\u0000\u0007de"
+"cimalq\u0000~\u0000*q\u0000~\u0000@t\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~\u0000:t\u0000\fminInclusivesr\u0000\u000e"
+"java.lang.Long;\u008b\u00e4\u0090\u00cc\u008f#\u00df\u0002\u0000\u0001J\u0000\u0005valuexr\u0000\u0010java.lang.Number\u0086\u00ac\u0095\u001d\u000b\u0094\u00e0"
+"\u008b\u0002\u0000\u0000xp\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000q\u0000~\u0000:t\u0000\fmaxInclusivesq\u0000~\u0000D\u007f\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffq\u0000~\u00005q\u0000~\u0000Csr"
+"\u0000\u0011java.lang.Integer\u0012\u00e2\u00a0\u00a4\u00f7\u0081\u00878\u0002\u0000\u0001I\u0000\u0005valuexq\u0000~\u0000E\u0080\u0000\u0000\u0000q\u0000~\u00005q\u0000~\u0000Gsq"
+"\u0000~\u0000I\u007f\u00ff\u00ff\u00ffsr\u00000com.sun.msv.grammar.Expression$NullSetExpression"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000"
+"\u0002L\u0000\tlocalNameq\u0000~\u0000#L\u0000\fnamespaceURIq\u0000~\u0000#xpq\u0000~\u0000\'q\u0000~\u0000&sr\u0000\u001dcom.su"
+"n.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun.msv.gr"
+"ammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000"
+"~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0019ppsr\u0000\"c"
+"om.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000 q\u0000~\u0000&t\u0000\u0005QN"
+"ameq\u0000~\u0000*q\u0000~\u0000Msq\u0000~\u0000Nq\u0000~\u0000Yq\u0000~\u0000&sr\u0000#com.sun.msv.grammar.SimpleN"
+"ameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000#L\u0000\fnamespaceURIq\u0000~\u0000#xr\u0000\u001d"
+"com.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://w"
+"ww.w3.org/2001/XMLSchema-instancesr\u00000com.sun.msv.grammar.Exp"
+"ression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000T\u0001psq\u0000~\u0000[t\u0000\u0012M"
+"inimalCardinalityt\u0000\u0000sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000\u001csq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000"
+"Upq\u0000~\u0000Vq\u0000~\u0000]q\u0000~\u0000asq\u0000~\u0000[t\u0000\u0012MaximalCardinalityq\u0000~\u0000esq\u0000~\u0000\u0000pp\u0000sq"
+"\u0000~\u0000\u0007ppsq\u0000~\u0000\u0019ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000 q\u0000~\u0000&t\u0000\u0006stringsr\u00005com.sun.msv.data"
+"type.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000)\u0001q\u0000~\u0000M"
+"sq\u0000~\u0000Nq\u0000~\u0000qq\u0000~\u0000&sq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000Vq\u0000~\u0000]q\u0000~\u0000asq\u0000~\u0000[t\u0000\n"
+"ObjectNameq\u0000~\u0000esq\u0000~\u0000Pppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq"
+"\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000Upsq\u0000~\u0000Pq\u0000~\u0000Upsq\u0000~\u0000\u0000q\u0000~\u0000Up\u0000sq\u0000~\u0000Pppsq\u0000~\u0000zq\u0000~\u0000U"
+"psq\u0000~\u0000Rq\u0000~\u0000Upsr\u00002com.sun.msv.grammar.Expression$AnyStringExp"
+"ression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000bpsr\u0000 com.sun.msv.grammar.AnyNam"
+"eClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\\q\u0000~\u0000asq\u0000~\u0000[t\u0000\"extensionTools.opl2.gen"
+"erated.Rolet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000"
+"~\u0000\u0000q\u0000~\u0000Up\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000Pppsq\u0000~\u0000zq\u0000~\u0000Upsq\u0000~\u0000Rq\u0000~\u0000Upq"
+"\u0000~\u0000\u0083q\u0000~\u0000\u0085q\u0000~\u0000asq\u0000~\u0000[t\u0000&extensionTools.opl2.generated.RoleTyp"
+"eq\u0000~\u0000\u0088sq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000Vq\u0000~\u0000]q\u0000~\u0000asq\u0000~\u0000[t\u0000\u0004Roleq\u0000~\u0000eq"
+"\u0000~\u0000asq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000nsq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000Vq\u0000~\u0000]q\u0000~\u0000"
+"asq\u0000~\u0000[t\u0000\u000fSourceStateNameq\u0000~\u0000esq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000nsq\u0000~\u0000Ppp"
+"sq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000Vq\u0000~\u0000]q\u0000~\u0000asq\u0000~\u0000[t\u0000\u0014DestinationStateNameq\u0000~\u0000"
+"esq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000nsq\u0000~\u0000[t\u0000\u000flogicalRelationq\u0000~\u0000eq\u0000~\u0000a"
+"sq\u0000~\u0000Rppq\u0000~\u0000nsq\u0000~\u0000[t\u0000\nobjectTypeq\u0000~\u0000esq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~"
+"\u0000nsq\u0000~\u0000[t\u0000\u001csubjectAggregationFatherNameq\u0000~\u0000eq\u0000~\u0000asq\u0000~\u0000Pppsq\u0000"
+"~\u0000Rq\u0000~\u0000Upq\u0000~\u0000nsq\u0000~\u0000[t\u0000\u001csubjectAggregationFatherRoleq\u0000~\u0000eq\u0000~\u0000"
+"asq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000nsq\u0000~\u0000[t\u0000\u001bsubjectExhibitionFatherNa"
+"meq\u0000~\u0000eq\u0000~\u0000asq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000nsq\u0000~\u0000[t\u0000\u001bsubjectExhibit"
+"ionFatherRoleq\u0000~\u0000eq\u0000~\u0000asq\u0000~\u0000Rppq\u0000~\u0000nsq\u0000~\u0000[t\u0000\u0010subjectThingNam"
+"eq\u0000~\u0000esq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000nsq\u0000~\u0000[t\u0000\u0010subjectThingRoleq\u0000~\u0000"
+"eq\u0000~\u0000asq\u0000~\u0000Pppsq\u0000~\u0000Rq\u0000~\u0000Upq\u0000~\u0000Vq\u0000~\u0000]q\u0000~\u0000asq\u0000~\u0000[t\u0000\u000eChangingCl"
+"auseq\u0000~\u0000esr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;x"
+"psr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000"
+"\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/Ex"
+"pressionPool;xp\u0000\u0000\u0000(\u0001pq\u0000~\u0000\u000bq\u0000~\u0000\fq\u0000~\u0000}q\u0000~\u0000\u0010q\u0000~\u0000\u000fq\u0000~\u0000Qq\u0000~\u0000hq\u0000~\u0000"
+"uq\u0000~\u0000\u0091q\u0000~\u0000\u0097q\u0000~\u0000\u009dq\u0000~\u0000\u00bfq\u0000~\u0000\u0012q\u0000~\u0000\u00bbq\u0000~\u0000\u008aq\u0000~\u0000\u0013q\u0000~\u0000yq\u0000~\u0000\u0015q\u0000~\u0000mq\u0000~\u0000"
+"\u0014q\u0000~\u0000\u0096q\u0000~\u0000\u009cq\u0000~\u0000\u0011q\u0000~\u0000\tq\u0000~\u0000\u000eq\u0000~\u0000\u0018q\u0000~\u0000gq\u0000~\u0000\u00b0q\u0000~\u0000|q\u0000~\u0000\u00a8q\u0000~\u0000\u0080q\u0000~\u0000"
+"\u008dq\u0000~\u0000\rq\u0000~\u0000\u00a1q\u0000~\u0000\u007fq\u0000~\u0000\u008cq\u0000~\u0000\u0016q\u0000~\u0000\u00b4q\u0000~\u0000\nq\u0000~\u0000\u00acx"));
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
            return extensionTools.opl2.generated.impl.ChangingClauseImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  3 :
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  1 :
                        attIdx = this.context.getAttribute("", "logicalRelation");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "objectType");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  0 :
                        if (("ChangingClause" == ___local)&&("" == ___uri)) {
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
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  3 :
                        this.revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        attIdx = this.context.getAttribute("", "logicalRelation");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "objectType");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  2 :
                        if (("ChangingClause" == ___local)&&("" == ___uri)) {
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
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  3 :
                        this.revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        if (("logicalRelation" == ___local)&&("" == ___uri)) {
                            this.spawnHandlerFromEnterAttribute((((extensionTools.opl2.generated.impl.ChangingClauseTypeImpl)extensionTools.opl2.generated.impl.ChangingClauseImpl.this).new Unmarshaller(this.context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("objectType" == ___local)&&("" == ___uri)) {
                            this.spawnHandlerFromEnterAttribute((((extensionTools.opl2.generated.impl.ChangingClauseTypeImpl)extensionTools.opl2.generated.impl.ChangingClauseImpl.this).new Unmarshaller(this.context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        break;
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
                    case  3 :
                        this.revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        attIdx = this.context.getAttribute("", "logicalRelation");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = this.context.getAttribute("", "objectType");
                        if (attIdx >= 0) {
                            this.context.consumeAttribute(attIdx);
                            this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
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
                        case  3 :
                            this.revertToParentFromText(value);
                            return ;
                        case  1 :
                            attIdx = this.context.getAttribute("", "logicalRelation");
                            if (attIdx >= 0) {
                                this.context.consumeAttribute(attIdx);
                                this.context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = this.context.getAttribute("", "objectType");
                            if (attIdx >= 0) {
                                this.context.consumeAttribute(attIdx);
                                this.context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                    }
                } catch (java.lang.RuntimeException e) {
                    this.handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
