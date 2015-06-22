package gui.opdProject.simplifiedOPM.arrows;

import y.view.Arrow;
import y.view.Drawable;
import y.view.ShapeDrawable;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by raanan.
 * Date: 19/07/11
 * Time: 18:18
 */
public class ConditionArrow implements Drawable {

    Arrow delegateArrow = Arrow.TRANSPARENT_CIRCLE;


    public void paint(Graphics2D g) {
        delegateArrow.paint(g, 0, 0, 1, 0);
        g.drawString("c", Math.round(-delegateArrow.getShape().getBounds().getWidth() / 2), Math.round(delegateArrow.getShape().getBounds().getHeight() / 2));
        g.rotate(0.4);
    }

    public Rectangle getBounds() {
        Rectangle unionRect = delegateArrow.getShape().getBounds();
        return unionRect;
    }


}
