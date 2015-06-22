package gui.images.animation;
import javax.swing.ImageIcon;

public class AnimationImages
{

    // ********** Animation Images ***************

    public final static ImageIcon PLAY = loadImage("an_play.gif");
    public final static ImageIcon BACKWARD = loadImage("an_backward.gif");
    public final static ImageIcon FORWARD = loadImage("an_forward.gif");
    public final static ImageIcon PAUSE = loadImage("an_pause.gif");
    public final static ImageIcon CLOSE = loadImage("an_close.gif");
    public final static ImageIcon ACTIVATE = loadImage("an_activate.gif");
    public final static ImageIcon DEACTIVATE = loadImage("an_deactivate.gif");
    public final static ImageIcon STOP = loadImage("an_stop.gif");
    public final static ImageIcon SETTINGS = loadImage("an_settings.gif");
    public final static ImageIcon ZOOM_IN = loadImage("an_zoomin.gif");
    public final static ImageIcon ZOOM_OUT = loadImage("an_zoomout.gif");

    private static ImageIcon loadImage(String file)
    {
        try
        {
            return new ImageIcon(AnimationImages.class.getResource(file));
        }
        catch (Exception e)
        {
            System.err.println("cannot load animation image "+file);

            return null;
        }
    }

}
