//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//


package extensionTools.opl2.generated.impl;

public class TimeValueTypeImpl implements extensionTools.opl2.generated.TimeValueType, com.sun.xml.bind.JAXBObject, extensionTools.opl2.generated.impl.runtime.UnmarshallableObject, extensionTools.opl2.generated.impl.runtime.XMLSerializable, extensionTools.opl2.generated.impl.runtime.ValidatableObject
{

    protected boolean has_Seconds;
    protected int _Seconds;
    protected boolean has_Minutes;
    protected int _Minutes;
    protected boolean has_Years;
    protected int _Years;
    protected boolean has_Months;
    protected int _Months;
    protected boolean has_Hours;
    protected int _Hours;
    protected boolean has_Days;
    protected int _Days;
    protected boolean has_MilliSeconds;
    protected int _MilliSeconds;
    protected boolean has_Weeks;
    protected int _Weeks;
    public final static java.lang.Class version = (extensionTools.opl2.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

//    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
//        return (extensionTools.opl2.generated.StateTimeoutSentenceType.class);
//    }
//        return (extensionTools.opl2.generated.TimeValueType.class);
//    }

    public int getSeconds() {
        return this._Seconds;
    }

    public void setSeconds(int value) {
        this._Seconds = value;
        this.has_Seconds = true;
    }

    public int getMinutes() {
        return this._Minutes;
    }

    public void setMinutes(int value) {
        this._Minutes = value;
        this.has_Minutes = true;
    }

    public int getYears() {
        return this._Years;
    }

    public void setYears(int value) {
        this._Years = value;
        this.has_Years = true;
    }

    public int getMonths() {
        return this._Months;
    }

    public void setMonths(int value) {
        this._Months = value;
        this.has_Months = true;
    }

    public int getHours() {
        return this._Hours;
    }

    public void setHours(int value) {
        this._Hours = value;
        this.has_Hours = true;
    }

    public int getDays() {
        return this._Days;
    }

    public void setDays(int value) {
        this._Days = value;
        this.has_Days = true;
    }

    public int getMilliSeconds() {
        return this._MilliSeconds;
    }

    public void setMilliSeconds(int value) {
        this._MilliSeconds = value;
        this.has_MilliSeconds = true;
    }

    public int getWeeks() {
        return this._Weeks;
    }

    public void setWeeks(int value) {
        this._Weeks = value;
        this.has_Weeks = true;
    }

    public extensionTools.opl2.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
        return new extensionTools.opl2.generated.impl.TimeValueTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(extensionTools.opl2.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (this.has_Years) {
            context.startElement("", "Years");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(javax.xml.bind.DatatypeConverter.printInt(((int) this._Years)), "Years");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (this.has_Months) {
            context.startElement("", "Months");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(javax.xml.bind.DatatypeConverter.printInt(((int) this._Months)), "Months");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (this.has_Weeks) {
            context.startElement("", "Weeks");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(javax.xml.bind.DatatypeConverter.printInt(((int) this._Weeks)), "Weeks");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (this.has_Days) {
            context.startElement("", "Days");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(javax.xml.bind.DatatypeConverter.printInt(((int) this._Days)), "Days");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (this.has_Hours) {
            context.startElement("", "Hours");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(javax.xml.bind.DatatypeConverter.printInt(((int) this._Hours)), "Hours");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (this.has_Minutes) {
            context.startElement("", "Minutes");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(javax.xml.bind.DatatypeConverter.printInt(((int) this._Minutes)), "Minutes");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (this.has_Seconds) {
            context.startElement("", "Seconds");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(javax.xml.bind.DatatypeConverter.printInt(((int) this._Seconds)), "Seconds");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (this.has_MilliSeconds) {
            context.startElement("", "MilliSeconds");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(javax.xml.bind.DatatypeConverter.printInt(((int) this._MilliSeconds)), "MilliSeconds");
            } catch (java.lang.Exception e) {
                extensionTools.opl2.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
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
        return (extensionTools.opl2.generated.TimeValueType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000pp"
+"sq\u0000~\u0000\u0000ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001pp"
+"sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnam"
+"eClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.gram"
+"mar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcon"
+"tentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005value"
+"xp\u0000p\u0000sq\u0000~\u0000\u0000ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dt"
+"t\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLc"
+"om/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000 com.sun.msv.datatype."
+"xsd.IntType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDe"
+"rivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbaseFacetst\u0000)Lcom/sun/msv/datatype/xs"
+"d/XSDatatypeImpl;xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicT"
+"ype\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003"
+"L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u001fL\u0000\nwhite"
+"Spacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 h"
+"ttp://www.w3.org/2001/XMLSchemat\u0000\u0003intsr\u00005com.sun.msv.datatyp"
+"e.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv"
+".datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u0000*com.sun.ms"
+"v.datatype.xsd.MaxInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.d"
+"atatype.xsd.RangeFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\nlimitValuet\u0000\u0012Ljava/lang/"
+"Object;xr\u00009com.sun.msv.datatype.xsd.DataTypeWithValueConstra"
+"intFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.DataTypeWith"
+"Facet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFlagL\u0000\bbaseT"
+"ypeq\u0000~\u0000\u001bL\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/xsd/Concrete"
+"Type;L\u0000\tfacetNameq\u0000~\u0000\u001fxq\u0000~\u0000\u001eppq\u0000~\u0000&\u0000\u0001sr\u0000*com.sun.msv.datatyp"
+"e.xsd.MinInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000(ppq\u0000~\u0000&\u0000\u0000sr\u0000!com.sun"
+".msv.datatype.xsd.LongType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001aq\u0000~\u0000\"t\u0000\u0004longq\u0000~\u0000&"
+"sq\u0000~\u0000\'ppq\u0000~\u0000&\u0000\u0001sq\u0000~\u0000.ppq\u0000~\u0000&\u0000\u0000sr\u0000$com.sun.msv.datatype.xsd.I"
+"ntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001aq\u0000~\u0000\"t\u0000\u0007integerq\u0000~\u0000&sr\u0000,com.sun.m"
+"sv.datatype.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;co"
+"m.sun.msv.datatype.xsd.DataTypeWithLexicalConstraintFacetT\u0090\u001c"
+">\u001azb\u00ea\u0002\u0000\u0000xq\u0000~\u0000+ppq\u0000~\u0000&\u0001\u0000sr\u0000#com.sun.msv.datatype.xsd.NumberTy"
+"pe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001cq\u0000~\u0000\"t\u0000\u0007decimalq\u0000~\u0000&q\u0000~\u0000<t\u0000\u000efractionDigit"
+"s\u0000\u0000\u0000\u0000q\u0000~\u00006t\u0000\fminInclusivesr\u0000\u000ejava.lang.Long;\u008b\u00e4\u0090\u00cc\u008f#\u00df\u0002\u0000\u0001J\u0000\u0005val"
+"uexr\u0000\u0010java.lang.Number\u0086\u00ac\u0095\u001d\u000b\u0094\u00e0\u008b\u0002\u0000\u0000xp\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000q\u0000~\u00006t\u0000\fmaxInclus"
+"ivesq\u0000~\u0000@\u007f\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffq\u0000~\u00001q\u0000~\u0000?sr\u0000\u0011java.lang.Integer\u0012\u00e2\u00a0\u00a4\u00f7\u0081\u00878\u0002\u0000\u0001I"
+"\u0000\u0005valuexq\u0000~\u0000A\u0080\u0000\u0000\u0000q\u0000~\u00001q\u0000~\u0000Csq\u0000~\u0000E\u007f\u00ff\u00ff\u00ffsr\u00000com.sun.msv.grammar"
+".Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun."
+"msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001fL\u0000\fnamespaceU"
+"RIq\u0000~\u0000\u001fxpq\u0000~\u0000#q\u0000~\u0000\"sq\u0000~\u0000\fppsr\u0000 com.sun.msv.grammar.Attribute"
+"Exp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u000fxq\u0000~\u0000\u0003q\u0000~\u0000\u0013psq\u0000~\u0000\u0015"
+"ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001cq\u0000~"
+"\u0000\"t\u0000\u0005QNameq\u0000~\u0000&q\u0000~\u0000Isq\u0000~\u0000Jq\u0000~\u0000Rq\u0000~\u0000\"sr\u0000#com.sun.msv.grammar."
+"SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001fL\u0000\fnamespaceURIq\u0000"
+"~\u0000\u001fxr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)h"
+"ttp://www.w3.org/2001/XMLSchema-instancesr\u00000com.sun.msv.gram"
+"mar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0012\u0001psq\u0000"
+"~\u0000Tt\u0000\u0005Yearst\u0000\u0000q\u0000~\u0000Zsq\u0000~\u0000\fppsq\u0000~\u0000\u000eq\u0000~\u0000\u0013p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000\u0018sq\u0000~\u0000\fp"
+"psq\u0000~\u0000Mq\u0000~\u0000\u0013pq\u0000~\u0000Oq\u0000~\u0000Vq\u0000~\u0000Zsq\u0000~\u0000Tt\u0000\u0006Monthsq\u0000~\u0000^q\u0000~\u0000Zsq\u0000~\u0000\fp"
+"psq\u0000~\u0000\u000eq\u0000~\u0000\u0013p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000\u0018sq\u0000~\u0000\fppsq\u0000~\u0000Mq\u0000~\u0000\u0013pq\u0000~\u0000Oq\u0000~\u0000Vq\u0000~"
+"\u0000Zsq\u0000~\u0000Tt\u0000\u0005Weeksq\u0000~\u0000^q\u0000~\u0000Zsq\u0000~\u0000\fppsq\u0000~\u0000\u000eq\u0000~\u0000\u0013p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000\u0018"
+"sq\u0000~\u0000\fppsq\u0000~\u0000Mq\u0000~\u0000\u0013pq\u0000~\u0000Oq\u0000~\u0000Vq\u0000~\u0000Zsq\u0000~\u0000Tt\u0000\u0004Daysq\u0000~\u0000^q\u0000~\u0000Zsq"
+"\u0000~\u0000\fppsq\u0000~\u0000\u000eq\u0000~\u0000\u0013p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000\u0018sq\u0000~\u0000\fppsq\u0000~\u0000Mq\u0000~\u0000\u0013pq\u0000~\u0000Oq\u0000~"
+"\u0000Vq\u0000~\u0000Zsq\u0000~\u0000Tt\u0000\u0005Hoursq\u0000~\u0000^q\u0000~\u0000Zsq\u0000~\u0000\fppsq\u0000~\u0000\u000eq\u0000~\u0000\u0013p\u0000sq\u0000~\u0000\u0000pp"
+"q\u0000~\u0000\u0018sq\u0000~\u0000\fppsq\u0000~\u0000Mq\u0000~\u0000\u0013pq\u0000~\u0000Oq\u0000~\u0000Vq\u0000~\u0000Zsq\u0000~\u0000Tt\u0000\u0007Minutesq\u0000~\u0000"
+"^q\u0000~\u0000Zsq\u0000~\u0000\fppsq\u0000~\u0000\u000eq\u0000~\u0000\u0013p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000\u0018sq\u0000~\u0000\fppsq\u0000~\u0000Mq\u0000~\u0000\u0013p"
+"q\u0000~\u0000Oq\u0000~\u0000Vq\u0000~\u0000Zsq\u0000~\u0000Tt\u0000\u0007Secondsq\u0000~\u0000^q\u0000~\u0000Zsq\u0000~\u0000\fppsq\u0000~\u0000\u000eq\u0000~\u0000\u0013"
+"p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000\u0018sq\u0000~\u0000\fppsq\u0000~\u0000Mq\u0000~\u0000\u0013pq\u0000~\u0000Oq\u0000~\u0000Vq\u0000~\u0000Zsq\u0000~\u0000Tt\u0000\fM"
+"illiSecondsq\u0000~\u0000^q\u0000~\u0000Zsr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$"
+"ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHa"
+"sh\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/ms"
+"v/grammar/ExpressionPool;xp\u0000\u0000\u0000\u001f\u0001pq\u0000~\u0000\u0007q\u0000~\u0000\bq\u0000~\u0000\tq\u0000~\u0000Lq\u0000~\u0000bq\u0000"
+"~\u0000iq\u0000~\u0000pq\u0000~\u0000wq\u0000~\u0000~q\u0000~\u0000\u0085q\u0000~\u0000\u008cq\u0000~\u0000\rq\u0000~\u0000_q\u0000~\u0000\u000bq\u0000~\u0000fq\u0000~\u0000mq\u0000~\u0000tq\u0000"
+"~\u0000{q\u0000~\u0000\u0082q\u0000~\u0000\u0089q\u0000~\u0000\u0006q\u0000~\u0000\u0014q\u0000~\u0000aq\u0000~\u0000hq\u0000~\u0000oq\u0000~\u0000vq\u0000~\u0000}q\u0000~\u0000\u0084q\u0000~\u0000\u008bq\u0000"
+"~\u0000\nq\u0000~\u0000\u0005x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends extensionTools.opl2.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "-------------------------");
        }

        protected Unmarshaller(extensionTools.opl2.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            this.state = startState;
        }

        public java.lang.Object owner() {
            return extensionTools.opl2.generated.impl.TimeValueTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (this.state) {
                    case  18 :
                        if (("Seconds" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 19;
                            return ;
                        }
                        this.state = 21;
                        continue outer;
                    case  6 :
                        if (("Weeks" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 7;
                            return ;
                        }
                        this.state = 9;
                        continue outer;
                    case  9 :
                        if (("Days" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 10;
                            return ;
                        }
                        this.state = 12;
                        continue outer;
                    case  21 :
                        if (("MilliSeconds" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 22;
                            return ;
                        }
                        this.state = 24;
                        continue outer;
                    case  24 :
                        this.revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  15 :
                        if (("Minutes" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 16;
                            return ;
                        }
                        this.state = 18;
                        continue outer;
                    case  0 :
                        if (("Years" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 1;
                            return ;
                        }
                        this.state = 3;
                        continue outer;
                    case  12 :
                        if (("Hours" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 13;
                            return ;
                        }
                        this.state = 15;
                        continue outer;
                    case  3 :
                        if (("Months" == ___local)&&("" == ___uri)) {
                            this.context.pushAttributes(__atts, true);
                            this.state = 4;
                            return ;
                        }
                        this.state = 6;
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
                    case  18 :
                        this.state = 21;
                        continue outer;
                    case  23 :
                        if (("MilliSeconds" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 24;
                            return ;
                        }
                        break;
                    case  5 :
                        if (("Months" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 6;
                            return ;
                        }
                        break;
                    case  6 :
                        this.state = 9;
                        continue outer;
                    case  9 :
                        this.state = 12;
                        continue outer;
                    case  2 :
                        if (("Years" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 3;
                            return ;
                        }
                        break;
                    case  14 :
                        if (("Hours" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 15;
                            return ;
                        }
                        break;
                    case  21 :
                        this.state = 24;
                        continue outer;
                    case  11 :
                        if (("Days" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 12;
                            return ;
                        }
                        break;
                    case  8 :
                        if (("Weeks" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 9;
                            return ;
                        }
                        break;
                    case  24 :
                        this.revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  15 :
                        this.state = 18;
                        continue outer;
                    case  0 :
                        this.state = 3;
                        continue outer;
                    case  20 :
                        if (("Seconds" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 21;
                            return ;
                        }
                        break;
                    case  12 :
                        this.state = 15;
                        continue outer;
                    case  3 :
                        this.state = 6;
                        continue outer;
                    case  17 :
                        if (("Minutes" == ___local)&&("" == ___uri)) {
                            this.context.popAttributes();
                            this.state = 18;
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
                    case  18 :
                        this.state = 21;
                        continue outer;
                    case  6 :
                        this.state = 9;
                        continue outer;
                    case  9 :
                        this.state = 12;
                        continue outer;
                    case  21 :
                        this.state = 24;
                        continue outer;
                    case  24 :
                        this.revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  15 :
                        this.state = 18;
                        continue outer;
                    case  0 :
                        this.state = 3;
                        continue outer;
                    case  12 :
                        this.state = 15;
                        continue outer;
                    case  3 :
                        this.state = 6;
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
                    case  18 :
                        this.state = 21;
                        continue outer;
                    case  6 :
                        this.state = 9;
                        continue outer;
                    case  9 :
                        this.state = 12;
                        continue outer;
                    case  21 :
                        this.state = 24;
                        continue outer;
                    case  24 :
                        this.revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  15 :
                        this.state = 18;
                        continue outer;
                    case  0 :
                        this.state = 3;
                        continue outer;
                    case  12 :
                        this.state = 15;
                        continue outer;
                    case  3 :
                        this.state = 6;
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
                        case  18 :
                            this.state = 21;
                            continue outer;
                        case  22 :
                            this.state = 23;
                            this.eatText1(value);
                            return ;
                        case  6 :
                            this.state = 9;
                            continue outer;
                        case  9 :
                            this.state = 12;
                            continue outer;
                        case  21 :
                            this.state = 24;
                            continue outer;
                        case  1 :
                            this.state = 2;
                            this.eatText2(value);
                            return ;
                        case  24 :
                            this.revertToParentFromText(value);
                            return ;
                        case  16 :
                            this.state = 17;
                            this.eatText3(value);
                            return ;
                        case  15 :
                            this.state = 18;
                            continue outer;
                        case  10 :
                            this.state = 11;
                            this.eatText4(value);
                            return ;
                        case  0 :
                            this.state = 3;
                            continue outer;
                        case  4 :
                            this.state = 5;
                            this.eatText5(value);
                            return ;
                        case  7 :
                            this.state = 8;
                            this.eatText6(value);
                            return ;
                        case  12 :
                            this.state = 15;
                            continue outer;
                        case  13 :
                            this.state = 14;
                            this.eatText7(value);
                            return ;
                        case  3 :
                            this.state = 6;
                            continue outer;
                        case  19 :
                            this.state = 20;
                            this.eatText8(value);
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
                TimeValueTypeImpl.this._MilliSeconds = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                TimeValueTypeImpl.this.has_MilliSeconds = true;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                TimeValueTypeImpl.this._Years = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                TimeValueTypeImpl.this.has_Years = true;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                TimeValueTypeImpl.this._Minutes = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                TimeValueTypeImpl.this.has_Minutes = true;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

        private void eatText4(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                TimeValueTypeImpl.this._Days = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                TimeValueTypeImpl.this.has_Days = true;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

        private void eatText5(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                TimeValueTypeImpl.this._Months = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                TimeValueTypeImpl.this.has_Months = true;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

        private void eatText6(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                TimeValueTypeImpl.this._Weeks = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                TimeValueTypeImpl.this.has_Weeks = true;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

        private void eatText7(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                TimeValueTypeImpl.this._Hours = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                TimeValueTypeImpl.this.has_Hours = true;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

        private void eatText8(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                TimeValueTypeImpl.this._Seconds = javax.xml.bind.DatatypeConverter.parseInt(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                TimeValueTypeImpl.this.has_Seconds = true;
            } catch (java.lang.Exception e) {
                this.handleParseConversionException(e);
            }
        }

    }

}