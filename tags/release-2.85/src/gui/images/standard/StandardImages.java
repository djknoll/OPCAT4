package gui.images.standard;
import javax.swing.ImageIcon;

public class StandardImages
{


    private final static String basePath = "/gui/images/standard/";//Images.opmPath;
    // ********** Standard Images ***************

    public final static ImageIcon NEW = loadImage("new.gif");
    public final static ImageIcon EMPTY = loadImage("empty.gif");
    public final static ImageIcon OPEN = loadImage("open.gif");
    public final static ImageIcon CLOSE = loadImage("close.gif");
    public final static ImageIcon SAVE = loadImage("save.gif");
    public final static ImageIcon CUT = loadImage("cut.gif");
    public final static ImageIcon COPY = loadImage("copy.gif");
    public final static ImageIcon CLONE = loadImage("clone.gif");
    public final static ImageIcon COPYFORMAT = loadImage("copyformat.gif");
    public final static ImageIcon PASTE = loadImage("paste.gif");
    public final static ImageIcon UNDO = loadImage("undo.gif");
    public final static ImageIcon REDO = loadImage("redo.gif");
    public final static ImageIcon DELETE = loadImage("delete.gif");
    public final static ImageIcon REQ = loadImage("req.gif");

    public final static ImageIcon HIDE_TOOLS = loadImage("showHideTools.gif");
    public final static ImageIcon ZOOM_IN = loadImage("zoomin.gif");
    public final static ImageIcon ZOOM_OUT = loadImage("zoomout.gif");

    public final static ImageIcon POINTER = loadImage("pointer.gif");
    public final static ImageIcon PEN = loadImage("pen2.gif");         /*****************************HIOTeam****************/
    public final static ImageIcon PENCIL = loadImage("pencil.gif");         /*****************************HIOTeam****************/

    public final static ImageIcon HAND = loadImage("hand.gif");
    public final static ImageIcon ANIMATE = loadImage("animate.gif");

    public final static ImageIcon CHECKED = loadImage("checked.gif");
    public final static ImageIcon UNCHECKED = loadImage("unchecked.gif");

    public final static ImageIcon PRINT = loadImage("print.gif");
    public final static ImageIcon PREVIEW_CLOSE = loadImage("previewClose.gif");
    public final static ImageIcon PAGE_SETUP = loadImage("pageSetup.gif");
    public final static ImageIcon PRINTER_SETUP = loadImage("printerSetup.gif");
    public final static ImageIcon PRINT_PREVIEW = loadImage("preview.gif");

    public final static ImageIcon PROPERTIES = loadImage("properties.gif");
    public final static ImageIcon HELPBOOK = loadImage("helpbook.gif");
    
    public final static ImageIcon SEARCH = loadImage("search.gif");    
    
    
	/**
	 * An icon for representing validation.
	 * @author Eran Toch
	 */
	  public final static ImageIcon VALIDATION = loadImage("validate.gif");
	  
	  /**
	   * An icon for representing meta-libraries
	   */
	  public final static ImageIcon METALIBS = loadImage("metaLibs.gif");
	  
	  /**
	   * An icon for representing import
	   */
	  public final static ImageIcon IMPORT = loadImage("import.gif");

    protected static ImageIcon loadImage(String file)
    {
        try
        {
            return new ImageIcon(StandardImages.class.getResource(basePath+file), "test");
        }
        catch (Exception e)
        {
            System.err.println("cannot load standard image "+file);

            return null;
        }
    }

}
