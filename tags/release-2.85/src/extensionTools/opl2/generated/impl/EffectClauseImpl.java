//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class EffectClauseImpl
    extends extensionTools.opl2.generated.impl.EffectClauseTypeImpl
    implements extensionTools.opl2.generated.EffectClause, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (extensionTools.opl2.generated.EffectClause.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "EffectClause";
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.EffectClauseImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "EffectClause");
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
        return (extensionTools.opl2.generated.EffectClause.class);
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
+"\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000"
+"~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000 com.su"
+"n.msv.datatype.xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatyp"
+"e.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbaseFacetst\u0000)Lcom/sun/"
+"msv/datatype/xsd/XSDatatypeImpl;xr\u0000*com.sun.msv.datatype.xsd"
+".BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.Co"
+"ncreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatype"
+"Impl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNa"
+"meq\u0000~\u0000!L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceP"
+"rocessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0003intsr\u00005com."
+"sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"psr\u0000*com.sun.msv.datatype.xsd.MaxInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr"
+"\u0000#com.sun.msv.datatype.xsd.RangeFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\nlimitValu"
+"et\u0000\u0012Ljava/lang/Object;xr\u00009com.sun.msv.datatype.xsd.DataTypeW"
+"ithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.x"
+"sd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCh"
+"eckFlagL\u0000\bbaseTypeq\u0000~\u0000\u001dL\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/dataty"
+"pe/xsd/ConcreteType;L\u0000\tfacetNameq\u0000~\u0000!xq\u0000~\u0000 ppq\u0000~\u0000(\u0000\u0001sr\u0000*com."
+"sun.msv.datatype.xsd.MinInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000*ppq\u0000~"
+"\u0000(\u0000\u0000sr\u0000!com.sun.msv.datatype.xsd.LongType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001cq\u0000"
+"~\u0000$t\u0000\u0004longq\u0000~\u0000(sq\u0000~\u0000)ppq\u0000~\u0000(\u0000\u0001sq\u0000~\u00000ppq\u0000~\u0000(\u0000\u0000sr\u0000$com.sun.msv"
+".datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001cq\u0000~\u0000$t\u0000\u0007integerq\u0000~"
+"\u0000(sr\u0000,com.sun.msv.datatype.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0001I\u0000\u0005scalexr\u0000;com.sun.msv.datatype.xsd.DataTypeWithLexicalCon"
+"straintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000xq\u0000~\u0000-ppq\u0000~\u0000(\u0001\u0000sr\u0000#com.sun.msv.dataty"
+"pe.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001eq\u0000~\u0000$t\u0000\u0007decimalq\u0000~\u0000(q\u0000~\u0000>t"
+"\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~\u00008t\u0000\fminInclusivesr\u0000\u000ejava.lang.Long;\u008b"
+"\u00e4\u0090\u00cc\u008f#\u00df\u0002\u0000\u0001J\u0000\u0005valuexr\u0000\u0010java.lang.Number\u0086\u00ac\u0095\u001d\u000b\u0094\u00e0\u008b\u0002\u0000\u0000xp\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000q\u0000"
+"~\u00008t\u0000\fmaxInclusivesq\u0000~\u0000B\u007f\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffq\u0000~\u00003q\u0000~\u0000Asr\u0000\u0011java.lang.Inte"
+"ger\u0012\u00e2\u00a0\u00a4\u00f7\u0081\u00878\u0002\u0000\u0001I\u0000\u0005valuexq\u0000~\u0000C\u0080\u0000\u0000\u0000q\u0000~\u00003q\u0000~\u0000Esq\u0000~\u0000G\u007f\u00ff\u00ff\u00ffsr\u00000com."
+"sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000"
+"\u0004ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~"
+"\u0000!L\u0000\fnamespaceURIq\u0000~\u0000!xpq\u0000~\u0000%q\u0000~\u0000$sr\u0000\u001dcom.sun.msv.grammar.Ch"
+"oiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun.msv.grammar.AttributeE"
+"xp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004sr\u0000\u0011java.lang"
+".Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0017ppsr\u0000\"com.sun.msv.datat"
+"ype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001eq\u0000~\u0000$t\u0000\u0005QNameq\u0000~\u0000(q\u0000~\u0000Ksq\u0000"
+"~\u0000Lq\u0000~\u0000Wq\u0000~\u0000$sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000!L\u0000\fnamespaceURIq\u0000~\u0000!xr\u0000\u001dcom.sun.msv.gram"
+"mar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/X"
+"MLSchema-instancesr\u00000com.sun.msv.grammar.Expression$EpsilonE"
+"xpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000R\u0001psq\u0000~\u0000Yt\u0000\u0012MinimalCardinalit"
+"yt\u0000\u0000sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000\u001asq\u0000~\u0000Nppsq\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000Tq\u0000~\u0000[q\u0000~\u0000"
+"_sq\u0000~\u0000Yt\u0000\u0012MaximalCardinalityq\u0000~\u0000csq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0017ppsr"
+"\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysV"
+"alidxq\u0000~\u0000\u001eq\u0000~\u0000$t\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteSp"
+"aceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\'\u0001q\u0000~\u0000Ksq\u0000~\u0000Lq\u0000~\u0000oq\u0000~\u0000$"
+"sq\u0000~\u0000Nppsq\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000Tq\u0000~\u0000[q\u0000~\u0000_sq\u0000~\u0000Yt\u0000\nObjectNameq\u0000~\u0000cs"
+"q\u0000~\u0000Nppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001cco"
+"m.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000Sp"
+"sq\u0000~\u0000Nq\u0000~\u0000Spsq\u0000~\u0000\u0000q\u0000~\u0000Sp\u0000sq\u0000~\u0000Nppsq\u0000~\u0000xq\u0000~\u0000Spsq\u0000~\u0000Pq\u0000~\u0000Spsr\u0000"
+"2com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000`psr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xq\u0000~\u0000Zq\u0000~\u0000_sq\u0000~\u0000Yt\u0000\"extensionTools.opl2.generated.Rolet\u0000+ht"
+"tp://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000Sp\u0000sq\u0000~\u0000\u0007"
+"ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000Nppsq\u0000~\u0000xq\u0000~\u0000Spsq\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000\u0081q\u0000~\u0000\u0083q\u0000~\u0000_sq"
+"\u0000~\u0000Yt\u0000&extensionTools.opl2.generated.RoleTypeq\u0000~\u0000\u0086sq\u0000~\u0000Nppsq"
+"\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000Tq\u0000~\u0000[q\u0000~\u0000_sq\u0000~\u0000Yt\u0000\u0004Roleq\u0000~\u0000cq\u0000~\u0000_sq\u0000~\u0000Nppsq\u0000~"
+"\u0000Pq\u0000~\u0000Spq\u0000~\u0000lsq\u0000~\u0000Yt\u0000\u000flogicalRelationq\u0000~\u0000cq\u0000~\u0000_sq\u0000~\u0000Pppq\u0000~\u0000l"
+"sq\u0000~\u0000Yt\u0000\nobjectTypeq\u0000~\u0000csq\u0000~\u0000Nppsq\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000lsq\u0000~\u0000Yt\u0000\u001csu"
+"bjectAggregationFatherNameq\u0000~\u0000cq\u0000~\u0000_sq\u0000~\u0000Nppsq\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000"
+"lsq\u0000~\u0000Yt\u0000\u001csubjectAggregationFatherRoleq\u0000~\u0000cq\u0000~\u0000_sq\u0000~\u0000Nppsq\u0000~"
+"\u0000Pq\u0000~\u0000Spq\u0000~\u0000lsq\u0000~\u0000Yt\u0000\u001bsubjectExhibitionFatherNameq\u0000~\u0000cq\u0000~\u0000_s"
+"q\u0000~\u0000Nppsq\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000lsq\u0000~\u0000Yt\u0000\u001bsubjectExhibitionFatherRole"
+"q\u0000~\u0000cq\u0000~\u0000_sq\u0000~\u0000Pppq\u0000~\u0000lsq\u0000~\u0000Yt\u0000\u0010subjectThingNameq\u0000~\u0000csq\u0000~\u0000Np"
+"psq\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000lsq\u0000~\u0000Yt\u0000\u0010subjectThingRoleq\u0000~\u0000cq\u0000~\u0000_sq\u0000~\u0000Np"
+"psq\u0000~\u0000Pq\u0000~\u0000Spq\u0000~\u0000Tq\u0000~\u0000[q\u0000~\u0000_sq\u0000~\u0000Yt\u0000\fEffectClauseq\u0000~\u0000csr\u0000\"co"
+"m.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lco"
+"m/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.ms"
+"v.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstr"
+"eamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp"
+"\u0000\u0000\u0000\"\u0001pq\u0000~\u0000\tq\u0000~\u0000\u0011q\u0000~\u0000\u00adq\u0000~\u0000{q\u0000~\u0000Oq\u0000~\u0000fq\u0000~\u0000sq\u0000~\u0000\u008fq\u0000~\u0000\rq\u0000~\u0000\u00b1q\u0000~\u0000"
+"\u000bq\u0000~\u0000\u0088q\u0000~\u0000wq\u0000~\u0000\u0013q\u0000~\u0000kq\u0000~\u0000\u0012q\u0000~\u0000\u0016q\u0000~\u0000eq\u0000~\u0000\u009aq\u0000~\u0000\u00a2q\u0000~\u0000\u0010q\u0000~\u0000\fq\u0000~\u0000"
+"zq\u0000~\u0000~q\u0000~\u0000\u008bq\u0000~\u0000\nq\u0000~\u0000}q\u0000~\u0000\u008aq\u0000~\u0000\u0014q\u0000~\u0000\u000fq\u0000~\u0000\u000eq\u0000~\u0000\u009eq\u0000~\u0000\u0093q\u0000~\u0000\u00a6x"));
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
            return extensionTools.opl2.generated.impl.EffectClauseImpl.this;
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
                        if (("EffectClause" == ___local)&&("" == ___uri)) {
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
                        if (("EffectClause" == ___local)&&("" == ___uri)) {
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
                            this.spawnHandlerFromEnterAttribute((((extensionTools.opl2.generated.impl.EffectClauseTypeImpl)extensionTools.opl2.generated.impl.EffectClauseImpl.this).new Unmarshaller(this.context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("objectType" == ___local)&&("" == ___uri)) {
                            this.spawnHandlerFromEnterAttribute((((extensionTools.opl2.generated.impl.EffectClauseTypeImpl)extensionTools.opl2.generated.impl.EffectClauseImpl.this).new Unmarshaller(this.context)), 2, ___uri, ___local, ___qname);
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
