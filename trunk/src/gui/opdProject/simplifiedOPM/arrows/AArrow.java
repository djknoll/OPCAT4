package gui.opdProject.simplifiedOPM.arrows;

import y.view.Arrow;
import y.view.Drawable;

import java.awt.*;

/**
 * Created by raanan.
 * Date: 05/08/11
 * Time: 11:23
 */
public abstract  class AArrow implements Drawable {

    Arrow arrow = Arrow.STANDARD ;

    @Override
    public void paint(Graphics2D graphics2D) {
        //To change body of implemented methods use File | Settings | File Templates.

    }

    @Override
    public Rectangle getBounds() {
        return arrow.getShape().getBounds() ;
    }
}
