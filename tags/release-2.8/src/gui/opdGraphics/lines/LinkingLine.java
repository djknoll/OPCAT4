package gui.opdGraphics.lines;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class LinkingLine extends JComponent
{
    private boolean leftDirection;

    public LinkingLine()
    {
      leftDirection = true;
    }

    public void setLeftDirection(boolean lDirection)
    {
      leftDirection = lDirection;
    }

    public void paintComponent(Graphics g)
    {
      Graphics2D g2 = (Graphics2D)g;
      Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);


      g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{6}, 0.0f));

      if (leftDirection)
      {
        g2.drawLine(1, 1, getWidth()-1, getHeight()-1);
      }
      else
      {
        g2.drawLine(1, getHeight()-1, getWidth()-1, 1);
      }
    }
}
