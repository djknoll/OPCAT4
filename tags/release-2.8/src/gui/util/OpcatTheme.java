
package gui.util;
import java.awt.Font;

import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * This class describes an OPCAT standard theme.
 *
 */
public class OpcatTheme extends DefaultMetalTheme {

    public String getName() { return "OpcatTheme"; }

//    private final ColorUIResource primary1 = new ColorUIResource(51, 142, 71);
//    private final ColorUIResource primary2 = new ColorUIResource(102, 193, 122);
//    private final ColorUIResource primary3 = new ColorUIResource(153, 244, 173);

    private final FontUIResource arialFont = new FontUIResource("Arial", Font.PLAIN, 12);


//    protected ColorUIResource getPrimary1() { return primary1; }
//    protected ColorUIResource getPrimary2() { return primary2; }
//    protected ColorUIResource getPrimary3() { return primary3; }
    public FontUIResource getControlTextFont() { return arialFont; }
    public FontUIResource getSystemTextFont() { return arialFont; }
    public FontUIResource getUserTextFont() { return arialFont; }
    public FontUIResource getMenuTextFont() { return arialFont; }
    public FontUIResource getWindowTitleFont() { return arialFont; }
    public FontUIResource getSubTextFont() { return arialFont; }
}
