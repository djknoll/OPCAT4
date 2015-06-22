package gui.images.misc;
import javax.swing.ImageIcon;

public class MiscImages
{


    // ********** Misc Images ***************

    public final static ImageIcon SPLASH = loadImage("Splash.gif");
    public final static ImageIcon LOGO_SMALL_ICON = loadImage("Opcat2_16x16.gif");
    public final static ImageIcon LOGO_BIG_ICON = loadImage("opcat2_32x32.gif");

    protected static ImageIcon loadImage(String file)
    {
        try
        {
            return new ImageIcon(MiscImages.class.getResource(file));
        }
        catch (Exception e)
        {
            System.err.println("cannot load misc image "+file);

            return null;
        }
    }

}
