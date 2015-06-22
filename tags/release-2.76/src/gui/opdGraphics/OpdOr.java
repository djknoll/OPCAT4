package gui.opdGraphics;

import gui.opdGraphics.draggers.AroundDragger;
import gui.opdGraphics.popups.OrPopup;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.OrInstance;

import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

public class OpdOr
    extends JComponent
    implements MouseListener {
  private static double OR_SIZE = 40.0;
  private static double DIST_SIZE = 6.0;
  private static double SENSITIVITY = 4.0;

  int minAngle, maxAngle, maxDif;
  OpdProject myProject;
  OrInstance myOrInstance;

  public OpdOr(OrInstance ins, OpdProject project) {
    myOrInstance = ins;
    myProject = project;

    GenericTable config = myProject.getConfiguration();
    double currentSize = ( (Integer) config.getProperty("CurrentSize")).
        doubleValue();
    double normalSize = ( (Integer) config.getProperty("NormalSize")).
        doubleValue();
    double factor = currentSize / normalSize;

    this.setSize( (int) Math.round(OR_SIZE * 2 * factor),
                 (int) Math.round(OR_SIZE * 2 * factor));
    this.update();
  }

  public void update() {
    if (myOrInstance.getSize() < 2)
      return;

    Container parent = getParent();

    int size = 0, x = 0, y = 0;
    TreeMap tm = new TreeMap();
    for (Enumeration e = myOrInstance.getInstances(); e.hasMoreElements(); ) {
      LinkInstance li = (LinkInstance) e.nextElement();
      AroundDragger ad;
      if (myOrInstance.isSource()) {
        ad = li.getSourceDragger();
      }
      else {
        ad = li.getDestinationDragger();
      }

      size++;
//      System.out.println("parent: "+ getParent());
	  Point draggerLocation;
	  if (getParent() != null){
	  	draggerLocation = SwingUtilities.convertPoint(ad.getParent(), ad.getLocation(), getParent());
	  }
	  else	{
		draggerLocation = new Point(0, 0);
	  }
      x += draggerLocation.getX() + ad.getWidth() / 2;
      y += draggerLocation.getY() + ad.getHeight() / 2;

//      x += ad.getX() + ad.getWidth() / 2;
//      y += ad.getY() + ad.getHeight() / 2;

      int angle = (int) Math.toDegrees(ad.getRotationAngle());
      angle = (angle + 360 + 270) % 360;
      tm.put(new Integer(angle), new Integer(angle));
    }

    x = x / size;
    y = y / size;

    Integer angle = (Integer) tm.firstKey();
    angle = new Integer(angle.intValue() + 360);
    tm.put(angle, angle);

    Iterator i = tm.values().iterator();
    int prevAngle = ( (Integer) i.next()).intValue();

    maxDif = Integer.MIN_VALUE;

    for (; i.hasNext(); ) {
      int currAngle = ( (Integer) i.next()).intValue();

      if (currAngle - prevAngle > maxDif) {
        maxDif = currAngle - prevAngle;
        minAngle = prevAngle;
        maxAngle = currAngle;
      }

      prevAngle = currAngle;
    }

    maxAngle = maxAngle % 360;

//        System.err.println(-(360-maxDif) +" "+maxAngle+" ");

    GenericTable config = myProject.getConfiguration();
    double currentSize = ( (Integer) config.getProperty("CurrentSize")).
        doubleValue();
    double normalSize = ( (Integer) config.getProperty("NormalSize")).
        doubleValue();
    double factor = currentSize / normalSize;

    setLocation(x - (int) Math.round(OR_SIZE * factor),
                y - (int) Math.round(OR_SIZE * factor));
    repaint();

  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
    GenericTable config = myProject.getConfiguration();
    double currentSize = ( (Integer) config.getProperty("CurrentSize")).
        doubleValue();
    double normalSize = ( (Integer) config.getProperty("NormalSize")).
        doubleValue();
    double factor = currentSize / normalSize;

//        System.err.println(minAngle +" "+maxAngle+" "+instances.size());

    if (!myOrInstance.isOr()) {
      g2.setStroke(new BasicStroke( (float) factor * 2.5f, BasicStroke.CAP_BUTT,
                                   BasicStroke.JOIN_BEVEL, 10.0f,
                                   new float[] {4, 2}
                                   , 0.0f));
      g2.drawArc(2, 2, (int) (OR_SIZE * factor) * 2 - 3,
                 (int) (OR_SIZE * factor) * 2 - 3, -maxAngle, - (360 - maxDif));
//            g2.drawArc(2,2,(int)(OR_SIZE*factor)*2-3,(int)(OR_SIZE*factor)*2-3, 0, 360);
    }
    else {
      int dist = (int) Math.round(factor * DIST_SIZE);
      g2.setStroke(new BasicStroke( (float) factor * 2.0f, BasicStroke.CAP_BUTT,
                                   BasicStroke.JOIN_BEVEL, 10.0f,
                                   new float[] {4, 2}
                                   , 0.0f));
      g2.drawArc(2, 2, (int) (OR_SIZE * factor) * 2 - 3,
                 (int) (OR_SIZE * factor) * 2 - 3, -maxAngle, - (360 - maxDif));
      g2.drawArc(2 + dist, 2 + dist,
                 (int) (OR_SIZE * factor) * 2 - 3 - dist * 2,
                 (int) (OR_SIZE * factor) * 2 - 3 - dist * 2, -maxAngle,
                 - (360 - maxDif));
    }
//        g2.drawArc(0,0,60,60, 90, 45 );

  }

  public boolean contains(int x, int y) {
    GenericTable config = myProject.getConfiguration();
    double currentSize = ( (Integer) config.getProperty("CurrentSize")).
        doubleValue();
    double normalSize = ( (Integer) config.getProperty("NormalSize")).
        doubleValue();
    double sens;
    if (!myOrInstance.isOr()) {
      sens = (currentSize / normalSize) * SENSITIVITY;
    }
    else {
      sens = (currentSize / normalSize) * (SENSITIVITY + DIST_SIZE);
    }

    int tX = x - getWidth() / 2;
    int tY = (y - getHeight() / 2) * ( -1);

    double dist = Math.sqrt(tX * tX + tY * tY);

    if (sens < Math.abs(dist - getWidth() / 2)) {
      return false;
    }

    double k;
    if (tX == 0) {
      k = (double) tY / 0.00001;
    }
    else {
      k = (double) tY / (double) tX;
    }

    int myAngle = (int) Math.toDegrees(Math.atan(k));
    if (myAngle > 0) {
      myAngle = 90 - myAngle;
    }
    else {
      myAngle = -90 - myAngle;
    }

    if (tY < 0) {
      myAngle += 180;
    }

    myAngle = (myAngle + 360 + 270) % 360;

    if ( (minAngle > maxAngle && myAngle < minAngle && myAngle > maxAngle)
        || (minAngle < maxAngle && ! (myAngle > minAngle && myAngle < maxAngle))) {
      return true;
    }
    else {
      return false;
    }
  }

  public void mouseClicked(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {
    if (e.isPopupTrigger()) {
      if (StateMachine.isWaiting() || StateMachine.isAnimated()) {
        return;
      }

      JPopupMenu jpm = new OrPopup(myOrInstance, e.getX(), e.getY());
      Point showPoint = GraphicsUtils.findPlace4Menu(this,
          new Point(e.getX(), e.getY()), jpm.getPreferredSize());
      jpm.show(this, (int) showPoint.getX(), (int) showPoint.getY());
    }

  }
}