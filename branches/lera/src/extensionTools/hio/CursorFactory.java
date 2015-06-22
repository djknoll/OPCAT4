package extensionTools.hio;

import gui.images.standard.StandardImages;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;

/**
 * A factory class that can return a cursor for the drawing process.
 * @author eran
 *
 */
public class CursorFactory {

	  /**
	   * Returns a cursor object for the drawing process.
	   */
	  public static Cursor getDrawCursor()	{
	  	java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = StandardImages.PENCIL.getImage();
		Cursor drawCursor = toolkit.createCustomCursor(image, new Point(7,
				30), "img");
		return drawCursor;
	  }
}
