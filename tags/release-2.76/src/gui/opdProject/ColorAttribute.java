package gui.opdProject;
import java.awt.Color;
import java.util.StringTokenizer;

public class ColorAttribute implements Cloneable
{
    private String name;
    private Color myColor;
    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private String openingFontTag;
    private String closingFontTag;

    public ColorAttribute(String name,Color myColor, boolean bold, boolean italic, boolean underlined)
    {
        this.name = name;
        this.myColor = myColor;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        calculateFontTags();
    }

    protected Object clone()
    {
        return new ColorAttribute(name, myColor, bold, italic, underlined);
    }

    public String getName()
    {
        return name;
    }

    public String getStyleName()
    {
        StringTokenizer st = new StringTokenizer(name);
        return st.nextToken();
    }

    public void setColor(Color myColor)
    {
        this.myColor = myColor;
        calculateFontTags();
    }

    public Color getColor()
    {
        return myColor;
    }

    public void setBold(boolean bold)
    {
        this.bold = bold;
        calculateFontTags();
    }

    public boolean isBold()
    {
        return bold;
    }

    public void setItalic(boolean italic)
    {
        this.italic = italic;
        calculateFontTags();
    }

    public boolean isItalic()
    {
        return italic;
    }

    public void setUnderlined(boolean underlined)
    {
        this.underlined = underlined;
        calculateFontTags();
    }

    public boolean isUnderlined()
    {
        return underlined;
    }


    private String getRGB()
    {
        return toHex(myColor.getRed())+toHex(myColor.getGreen())+toHex(myColor.getBlue());
    }

    public String getStyleTag()
    {
        String tag = " ."+getStyleName()+" {\n";

        if (isBold())
        {
            tag += "font-weight : bold;\n";
        }
        else
        {
            tag += "font-weight : normal;\n";
        }

        if (isItalic())
        {
            tag += "font-style : italic;\n";
        }
        else
        {
            tag += "font-style : normal;\n";
        }

        if (isUnderlined())
        {
            tag += "text-decoration : underline;\n";
        }
        else
        {
            tag += "text-decoration : normal;\n";
        }


        tag += "color : #"+getRGB()+";\n";
        tag += "}\n";

        return tag;
    }



    public String openingHTMLStyleTag()
    {
        return "<font class=\""+getStyleName()+"\">";
    }

    public String closingHTMLStyleTag()
    {
        return "</font>";
    }

    public String openingHTMLFontTag()
    {
        return openingFontTag;
    }

    private void calculateFontTags()
    {
      openingFontTag = "<font color=\"#"+ getRGB()+"\">";
      closingFontTag = "</font>";
      if (isBold())
      {
        openingFontTag+="<B>";
        closingFontTag="</B>"+closingFontTag;
      }
      if (isItalic())
      {
        openingFontTag+="<I>";
        closingFontTag="</I>"+closingFontTag;
      }
      if (isUnderlined())
      {
        openingFontTag+="<U>";
        closingFontTag="</U>"+closingFontTag;
      }
    }

    public String closingHTMLFontTag()
    {
        return closingFontTag;
    }


    private String toHex(int num)
    {
        String s = Integer.toHexString(num);

        if (s.length() == 1)
        {
            return "0"+s;
        }
        else
        {
            return s;
        }
    }

    public String toString()
    {
        return name;
    }
}