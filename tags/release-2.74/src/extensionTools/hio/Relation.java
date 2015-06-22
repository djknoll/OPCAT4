package extensionTools.hio;

import java.awt.Point;
import java.util.Vector;


/**
 * <p>Title: class Relation </p>
 * <p>Description: recognizes if the shape is a full(3 kinds) or empty relation extends class connection
 */
public class Relation extends Connection{
  /**
   * static Vector segments the drawn segments vector
   */
  static Vector segments = DrawAppNew.segments;
  /**
   * Vector points the drawn points vector
   */
  Vector points = DrawAppNew.points;


  /**
   * constructor of Relation
   */
  public Relation(){}

  /**
   * the function isRelation
   * @return true if the drawn shape is a kind of relation
   */
  public int isRelation(){
    Vector segments = DrawAppNew.segments;
    Vector points = DrawAppNew.points;

    Vector mySegs = new Vector();
    if (segments.size() < 2)//cann't be a relation
      return -1;

    EdgeValues edgeval = new EdgeValues();
    Segment seg;

    //taking all posible combinations of all the segments minus two in order to find triangle
    for (int i = 0; i < segments.size(); i++)
      for (int j = i + 1; j < segments.size(); j++) {

        edgeval.clear();
        mySegs.clear();

        for (int k = 0; k < segments.size(); k++)
          if (k != i && k != j) {
            seg = ( (Segment) segments.elementAt(k));
            mySegs.add(seg);
            edgeval.setEdgePoints(seg);
          }

        //checking if there is a close shape
        if (!DrawAppNew.separateByLines(edgeval, mySegs)) {
          Segment seg1 = ( (Segment) segments.elementAt(i));
          Segment seg2 = ( (Segment) segments.elementAt(j));

          int midX = (edgeval.getminX() + edgeval.getmaxX()) / 2;
          int midY = (edgeval.getminY() + edgeval.getmaxY()) / 2;
          Point p1start = (Point) points.elementAt(seg1.getStartIndex());
          Point p1end = (Point) points.elementAt(seg1.getStartIndex() +
                                                 seg1.getNumOfPoints() - 1);
          Point p2start = (Point) points.elementAt(seg2.getStartIndex());
          Point p2end = (Point) points.elementAt(seg2.getStartIndex() +
                                                 seg2.getNumOfPoints() - 1);

          Point source = new Point(p1start.distance( (double)//set the source point of the relation
                                              midX, (double) midY) <
                             p1end.distance( (double) midX, (double) midY) ?
                             p1end : p1start);
    setSourceEdgePoint(source);

    Point destination = new Point(p2start.distance( (double)//set the destination point of the relation
              midX, (double) midY) <
                                  p2end.distance( (double) midX, (double) midY) ?
                                  p2end : p2start);
    setDestinationEdgePoint(destination);

          int seg1midx = Math.abs(source.getX() - seg1.getmaxX())  > Math.abs(source.getX() - seg1.getminX())?
              seg1.getmaxX()/4 + seg1.getminX()*3/4 : seg1.getmaxX()*3/4 + seg1.getminX()/4;
          int seg1midy = Math.abs(source.getY() - seg1.getmaxY())  > Math.abs(source.getY() - seg1.getminY())?
              seg1.getmaxY()/4 + seg1.getminY()*3/4 : seg1.getmaxY()*3/4 + seg1.getminY()/4;
          int seg2midx = Math.abs(source.getX() - seg2.getmaxX())  > Math.abs(source.getX() - seg2.getminX())?
              seg2.getmaxX()/4 + seg2.getminX()*3/4 : seg2.getmaxX()*3/4 + seg2.getminX()/4;
          int seg2midy = Math.abs(source.getY() - seg2.getmaxY())  > Math.abs(source.getY() - seg2.getminY())?
              seg2.getmaxY()/4 + seg2.getminY()*3/4 : seg2.getmaxY()*3/4 + seg2.getminY()/4;


          //checking two remaining strokes are on two sides of the shape in order to determine its a relation
          if (seg1midx > edgeval.getmaxX() && seg2midx < edgeval.getminX() ||
              seg1midx < edgeval.getminX() && seg2midx > edgeval.getmaxX() ||
              seg1midy > edgeval.getmaxY() && seg2midy < edgeval.getminY() ||
              seg1midy < edgeval.getminY() && seg2midy > edgeval.getmaxY()) {

            if (isFullRelation(mySegs, edgeval))
              return (DrawAppNew.fullRelation);

            return DrawAppNew.emptyRelation;
          }

        }
      }

    return -1;
  }


    /**
     * the function isFullRelation
     * @param mySegs all the segment of the shape except the two segments of the body
     * @param edgeval the max and min values
     * @return if it's not an empty relation(3 kinds of full relation)
     */
    private boolean isFullRelation(Vector mySegs, EdgeValues edgeval) {

      double midY = (edgeval.getmaxY() + edgeval.getminY()) / 2; //the mid point of triangle of the relation
      double midX = (edgeval.getmaxX() + edgeval.getminX()) / 2;

      Vector tempX = new Vector();
      Vector tempY = new Vector();

      int intersectX = 0;
      int intersectY = 0;

      boolean lastWasRemoved = false;

      for (int k = 0; k < mySegs.size(); k++) {
        int startP = ( (Segment) mySegs.elementAt(k)).getStartIndex();
        int size = ( (Segment) mySegs.elementAt(k)).getNumOfPoints();
        for (int i = startP; i < startP + size; i++) { // for each point checks if it intersects with one of the lines
          Point p = (Point) (points.elementAt(i));

          if (p.getY() <= midY + 1 && p.getY() >= midY - 1) { //if a point in the head has the same y value
            //as the mid point of the head
            tempX.add(p);
            intersectX++; //intersects in the X axis
          }
          if (p.getX() <= midX + 1 && p.getX() >= midX - 1) { //if a point in the head has the same x value
            //as the mid point of the head
            tempY.add(p);
            intersectY++; //intersects in the Y axis
          }
        }
      }

      //remove the duplicate points from x
      for (int k = 0; k < tempX.size() - 1; k++) {
        int x1 = (int) ( (Point) tempX.elementAt(k)).getX();
        int x2 = (int) ( (Point) tempX.elementAt(k + 1)).getX();
        if (Math.abs(x2 - x1) <= 2) {
          lastWasRemoved = true;
          intersectX--;
        }else{
        lastWasRemoved = false;
      }
      }

      lastWasRemoved = false;

      for (int k = 0; k < tempY.size() - 1; k++) {
        int y1 = (int) ( (Point) tempY.elementAt(k)).getY();
        int y2 = (int) ( (Point) tempY.elementAt(k + 1)).getY();
        if (Math.abs(y2 - y1) <= 2) {
          lastWasRemoved = true;
          intersectY--;
        }else{
        lastWasRemoved = false;
      }
      }


      //if find more then 4 intersection in each direction it's a full relation
      if (intersectX >= 4 || intersectY >= 4)
        return true;

      return false;
    };
  }