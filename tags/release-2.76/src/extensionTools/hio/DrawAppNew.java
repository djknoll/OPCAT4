package extensionTools.hio;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import gui.opdProject.OpdProject;

/**
 * <p>Title:class DrawAppNew </p>
 * <p>Description:the class DrawAppNew is the main class of hio project that enables a user
 * to draw with a pen or a mouse and recognizes specific opcat shapes that the user draw </p>
 */
public class DrawAppNew{

 private int x = 0, y = 0; // drawing coordinates

 /**
  * int max_x - global variable that indicates the max x coordinate of a shape
  * int max_y - global variable that indicates the max y coordinate of a shape
  * int min_x - global variable that indicates the min x coordinate of a shape
  * int min_y - global variable that indicates the min y coordinate of a shape
  */
 protected int max_x = 0, max_y = 0, min_x = Integer.MAX_VALUE, min_y = Integer.MAX_VALUE; //global variables that indicates the max/min coordinates of a shape
 /**
  *	EdgeValues shapeEV - global variable that contains the max/min coordinates of the drawn shape
  */
 static protected EdgeValues shapeEV = new EdgeValues();//global variable that contains the max/min coordinates of the drawn shape
 /**
  *	Rectangle wholeShapeRect - the rectancle that enclose the shape
  */
  static protected Rectangle wholeShapeRect;//the rectancle that enclose the shape
  /**
   * int shapeLen - the shape length
   */
  private int shapeLen = 0;
  /**
   * Vector points - vector that holds all the coordinates of a shape
   */
  static protected Vector points = new Vector(); // vector that holds all the coordinates of a shape
  /**
   * Line2D.Double l2D - line between two points
   */
  static protected Line2D.Double l2D;//line between two points
  /**
   * long t1 - time stamp for timer thread
   * long tCurr - time stamp for timer thread
   * long tOld - time stamp for timer thread
   */
  private long t1 = 0, tCurr = 0, tOld = 0; //times stamp for timer thread
  /**
   * Vector segments - vector that keeps information about every segment in the finished shape
   */
  static protected Vector segments = new Vector(); //vector that keeps information about every segment in the finished shape
  /**
   * Segment segment - current segment
   */
  static protected Segment segment;//current segment
  /**
   * int segPointsCount - number of points in segment
   */
  private int segPointsCount; //number of points in segment
  /**
   * long TIMEOUT - the time for drawing a new segment in the same shape
   */
  private long TIMEOUT = 1100; // the time for drawing a new segment in the same shape
  /**
   * int segmentFlag - a flag which indicates if its a new segment in the same shape
   */
  static protected int segmentFlag = 0; //a flag which indicates if its a new segment in the same shape
  /**
   * boolean proccessing - a flag to stop drawing if the software is proccessing a shape
   */
  static protected boolean proccessing = false; // a flag to stop drawing if the software is proccessing a shape
  /**
   * int timerIterator - an iterator for the SegmentTimer array
   */
  private int timerIterator = 0; //an iterator for the SegmentTimer array
  /**
   * SegmentTimer[] segtimer - an array of threads for each segment
   */
  private SegmentTimer[] segtimer = new SegmentTimer[4]; //an array of threads for each segment
  /**
   * OpenDrawInOpcat interactionWithOpcat1 - variable of the class that opens an opcat shape after the recognition
   */
  OpenDrawInOpcat interactionWithOpcat1;//variable of the class that opens an opcat shape after the recognition

 //constants of the opcat shapes recognized
 /**
  * constants of the opcat shapes that the system can recognize
  * int openArrow = 10;
  * int triangleArrow = 60;
  * int agentArrow = 30;
  * int instrumentArrow = 40;
  * int emptyRelation = 50;
  * int fullRelation = 6;
  * int rectangle = 1;
  * int ellipse = 2;
  * int triangleArrowFromBoth = 3;
  * int bidirectionalRelation = 4;
  * int invocationArrow = 5;
  * int mistake = -1;
  */
 static protected final int openArrow = 10;
 static protected final int triangleArrow = 60;
 static protected final int agentArrow = 30;
 static protected final int instrumentArrow = 40;
 static protected final int emptyRelation = 50;
 static protected final int fullRelation = 6;
 static protected final int rectangle = 1;
 static protected final int ellipse = 2;
 static protected final int triangleArrowFromBoth = 3;
 static protected final int bidirectionalRelation = 4;
 static protected final int invocationArrow = 5;
 static protected final int mistake = -1;

 /**
  * MessageScreen msgScreen - popup window in case the system didnt recognized the sketch
  */
 private MessageScreen msgScreen = new MessageScreen();
 /**
  * gui.opdProject.OpdProject myProject - the current project that was opened in opcat
  */
 gui.opdProject.OpdProject myProject;//the current project that was opened in opcat
 /**
  * ShapeSmoother smooth - variable of ShapeSmoother,handle cleaning the shape
  */
 private ShapeSmoother smooth = new ShapeSmoother(segments, points);//variable of ShapeSmoother,handle cleaning the shape
 /**
  * Link link - variable of Link, handle the link recognition
  */
 static protected Link link=null;//variable of Link, handle the link recognition
 /**
  * Relation relation - variable of Relation, handle the relation recognition
  */
 static protected Relation relation=null;//variable of Relation, handle the relation recognition
 /**
  * static protected Graphics currentGraphics
  */
 static protected Graphics currentGraphics;

  /**
   * constructor of DrawAppNew
   * @param opg the graphics
   * @param pProject the current opcat project
   * @throws IOException
   */
  public DrawAppNew(Graphics opg, OpdProject pProject) throws IOException {
    segments.clear();
    currentGraphics = (Graphics2D) opg;
    myProject = pProject;
    interactionWithOpcat1 = new OpenDrawInOpcat(myProject);
  }

 /**
  * <p>Title: class SegmentTimer</p>
  * <p>Description: used in order to limit the time that another segment in the same shape can be drawn </p>
  */
 class SegmentTimer
     extends Thread {
   /**
    *  private int myIterator iterator that goes over the SegmentTimer array
    */
   private int myIterator;
   /**
    * private Timer timer timer that counts the time between mouse release and mouse press
    */
   private Timer timer = new Timer();
   /**
     * <p>Title:class RemindTask </p>
     * <p>Description:check if another segment has been drawn in the same shape
     *  and if not disable the ability to draw and check what is the shape that had been drawn </p>
     */
    class RemindTask
        extends TimerTask {
      public void run() {
        if (segmentFlag == 1) {
          if (t1 + TIMEOUT <= System.currentTimeMillis()) { //start to analyze shape
//analizeShape();
               try
                {
                   analizeShape();
                 }
                 catch(Exception e)
                 {
                  // System.out.println("cannot recognize shape");
                   clear();
                   return;
                 }

          }
        }
        timer.cancel(); //Terminate the timer thread
      }
    } //end of class RemindTask


               SegmentTimer() { //the costructor
                   super();
                 }

                 public void run() {
                   myIterator = timerIterator;
                   timerIterator = (++timerIterator) % 4;
                   timer.schedule(new RemindTask(), TIMEOUT);
                   try {
                     segtimer[myIterator].join();
                   }
                   catch (InterruptedException e) {
                     //System.out.println("segtimer exception");
                   }
                 }
               } //end of class SegmentTimer

 /**
  * the fanction ourMousePressed adds the first point of a segment in the same shape to vector points
  * @param eventPoint the point to add
  */
 public void ourMousePressed(Point eventPoint) {

   if (proccessing == false) { //not in proccess of anlyzing shape
     tCurr = System.currentTimeMillis();//the current time
     x = (int) eventPoint.getX();
     y = (int) eventPoint.getY();
     currentGraphics = myProject.getCurrentOpd().getDrawingArea().getGraphics();//the graphics of the current project

     segment = new Segment(points.size(), 1, x, y, x, y);

     points.add(new Point(x, y)); //adds the press point to points
     shapeEV.setEdgePoints(x, y); //sets the min and max points of the shape
     segment.setEdgePoints(x, y);//sets the min and max points of the segment
     segPointsCount++;
     segmentFlag = 0;
    }

 }

 /**
  * the function ourMouseDragged adds the drawn points to vector points
  * @param eventPoint the point to add
  */
 public void ourMouseDragged(Point eventPoint) {
   if (points.size() == 0) {
     System.out.println("no point in points");
     return;
   }

   l2D = new Line2D.Double( (Point) points.lastElement(),
                           new Point(eventPoint)); //create a new line in order to draw it in Graphics

   Graphics2D g2 = (Graphics2D) currentGraphics;

   g2.setStroke(new BasicStroke());
   g2.setColor(Color.red);

   g2.draw(l2D);
   segmentFlag = 0;

   x = (int) eventPoint.getX();
   y = (int) eventPoint.getY();

   points.add(new Point(x, y)); //adds the points to points vector
   segPointsCount++;
   shapeEV.setEdgePoints(x, y); //sets the min and max points of the shape
   segment.setEdgePoints(x, y);

 }

 /**
  * the function ourMouseReleased handles the mouse release event
  * @param e the event
  */
 public void ourMouseReleased(MouseEvent e) {

   int w = 0, h = 0;
   segment.setNumOfPoints(segPointsCount);//update segment size
   segments.add(new Segment(segment));//adds the current segment to vector segments
   segPointsCount = 0;//clear num of points in current segment
   segmentFlag = 1; //new segment in same shape
   segtimer[timerIterator] = new SegmentTimer();
   t1 = System.currentTimeMillis();
   segtimer[timerIterator].start(); //start the timer

 }


 /**
  * the function analizeShape makes the recognition of the drawn shape
  * @throws java.lang.Exception
  */
 private void analizeShape() throws Exception
  {
    proccessing = true; //start to analize the shape
    relation = new Relation();
    shapeLen = shapeLength(); //calculate the shape length

    wholeShapeRect = drawRect(shapeEV);//finds the enclosed rectangle according to the edge values

    addPoints(); //add to vector points all the points the mouse listener drop

    if (!separateByLines( shapeEV, segments)) { //if its a close shape

     // System.out.println("find close shape");
      shapeLen = shapeLength();

      smooth.cutShape(0); // cut the edges of a close shape if needed
      updateRectBorders();

      CornerSearcher cs = new CornerSearcher(0, points.size(), true);//finds the corners of the shape

      shapeLen = shapeLength();//find the new length of the shape

      int cornersNum = cs.getCorners().size();//number of corners
      int cnt=0;
      //counts the number of corners that their angle ranges between 60-115, possible angle for rectangle
      cnt = cs.getCornersBetweenValues(60,115).size();

      //recognize rectangle if there 3 or more angles in the right range or by perimeter
      if (( cornersNum > 2
         &&  recognizeRectangle(shapeLen, wholeShapeRect.getHeight(),
                             wholeShapeRect.getWidth(), 0.775)) || cnt > 2
         || recognizeRectangle(shapeLen, wholeShapeRect.getHeight(),
                             wholeShapeRect.getWidth(), 0.86))

        //opens object in opcat
        interactionWithOpcat1.openOpcatShape(rectangle,wholeShapeRect);
      //recognize ellipse if there are less then 2 corners
      else if ( cornersNum <= 2 )
        interactionWithOpcat1.openOpcatShape(ellipse,wholeShapeRect);//open process in opcat
        else
          interactionWithOpcat1.openOpcatShape(mistake,wholeShapeRect);//not an opcat shape

    }
    //not close shape
    else if(relation.isRelation() == fullRelation)//open toolbar of 3 kinds of full relation
          interactionWithOpcat1.openOpcatShape(fullRelation,wholeShapeRect);

    else if(relation.isRelation() == emptyRelation)//open an emptyRelation(genralization-specialization)
      interactionWithOpcat1.openOpcatShape(emptyRelation,wholeShapeRect);

    else { //link

      Graphics2D g2 = (Graphics2D) currentGraphics;
      g2.setColor(Color.red);
      link = new Link(g2);

      link.separateArrowHeads();//findes the heads of the link
        switch (link.recognizeArrHeads(g2)) {//recognize the heads of the link

        case (11): //one of the heads is an open arrow and the other dose'nt exist
          interactionWithOpcat1.openOpcatShape(openArrow,wholeShapeRect);
          break;

        case (22): //both heads are open arrows(bidirectionalRelation)
          interactionWithOpcat1.openOpcatShape(bidirectionalRelation,wholeShapeRect);
          break;

        case (61): //one of the heads is a triangle arrow and the other dose'nt exist(result/consumption)
          interactionWithOpcat1.openOpcatShape(triangleArrow,wholeShapeRect);
          break;

        case (65): //invocation
          interactionWithOpcat1.openOpcatShape(invocationArrow,wholeShapeRect);
          break;

        case (122): //both heads are triangle arrows(effect link)
          interactionWithOpcat1.openOpcatShape(triangleArrowFromBoth,wholeShapeRect);
          break;

        case (31): //one of the heads is an agent arrow and the other dose'nt exist
          interactionWithOpcat1.openOpcatShape(agentArrow,wholeShapeRect);
          break;

        case (41): //one of the heads is an instrument arrow and the other dose'nt exist
          interactionWithOpcat1.openOpcatShape(instrumentArrow,wholeShapeRect);
          break;

        default:
          //interactionWithOpcat1.openOpcatShape(mistake,wholeShapeRect);//not an opcat shape
          msgScreen.showMessage(myProject);
          myProject.refresh();
      }
     }
     // clear all the vectors after recognizing the shape
     shapeEV.clear();
     points.clear();
     segments.clear();
     proccessing = false;
    }

  /**
    * the function paint go through all points vector and paints the lines between the points
    * @param g the shape to paint
    */
   public void paint(Graphics g) {
     Graphics2D g2 = (Graphics2D) g;
     for (int i = 0; i < points.size() - 1; i++) {
       l2D = new Line2D.Double( (Point) points.elementAt(i),
                               (Point) points.elementAt(i + 1));
       g2.draw( (Shape) l2D);
   }

   }


   /**
    * the function lineEq finds the line equation between two points
    * @param p1 the first point
    * @param p2 the second point
    * @param params contains parameter a and b of the line equation
    * @return the line between p1 and p2
    */

 static  public Line2D lineEq(Point p1, Point p2, LineParam params) {
   double denum = p1.getX() - p2.getX();
   double numerator = p1.getY() - p2.getY();
   if (denum == 0) {
     return null;
   }

   if (numerator == 0) {
     params.setA(0);
     params.setB(p1.getY());
   }
   else {
     params.setA(numerator / denum);
     params.setB(p1.getY() - params.getA() * p1.getX());
   }
   Point p12D = p1;
   Point p22D = p2;
   Line2D.Double line = new Line2D.Double(p1, p2);

   return line;
   }

   /**
   * the function addLinePoints adds points according to the flag
   * @param x1 the x coordinate of the first point
   * @param x2 the x coordinate of the second point
   * @param y1 the y coordinate of the first point
   * @param y2 the y coordinate of the second point
   * @param index index to insert the point in points
   * @param flag indicates the direction to insert
   * @return the number of points that were added to points
   */
  static protected int addLinePoints(int x1, int x2, int y1, int y2, int index, int flag) {
    int distX = x2 - x1;
    int distY = y2 - y1;
    int y, currindex = index, numadd = 0;
    if (distX == 0) {
      return 0;
    }
    for (int x = x1 + 1; x < x2; x++) {
      y = (x - x1) * distY / distX + y1; //y value according to the formula (y-y1)/(x-x1)=(y2-y1)/(x2-x1)(the same slope)
      if (flag > 1) { //we add points by "y" values
        points.add(currindex, new Point(y, x));
      }
      else { //we add points by "x" values
        points.add(currindex, new Point(x, y));
      }
      if ( (flag == 0) || (flag == 3)) { //direction of points added is from first to second point
        currindex++;
      }
      numadd++;
    }
    return numadd;
  }


  /**
   * the function addPoints adds points that the mouse listener didn't received to vector points
   */
  static private void addPoints() {
    int distX = 0, distY = 0, flag = 0;
    Point p1, p2;
    //go trough each segment and adds the missing points
    for (int seg = 0; seg < segments.size(); seg++) {
      Segment segment = (Segment) segments.elementAt(seg);
      int index = segment.getStartIndex(), numadded = 0, totaladded = 0;
      int endseg = segment.getStartIndex() + segment.getNumOfPoints() - 1;
      while (index < endseg) {
        flag = 0;//flag that indicates the progress direction
        p1 = new Point( (Point) points.elementAt(index++));//the next point
        p2 = new Point( (Point) points.elementAt(index));//the current point
        distX = Math.abs( (int) p1.getX() - (int) p2.getX()); //distance between sequential x coordinates
        distY = Math.abs( (int) p1.getY() - (int) p2.getY());//distance between sequential y coordinates

        if (distX > distY) {
          if (p1.getX() > p2.getX()) { // progress is in x direction and the value of x decreasing
            flag = 1;
          }
        }
        else {
          if (p1.getY() > p2.getY()) {
            flag = 2; //progress is in y direction  and the value of y decreasing
          }
          else {
            flag = 3; //progress is in y direction  and the value of y raising
          }
        }

        switch (flag) {
          case 0: //  progress is in x direction  and the value of x raising
            numadded = addLinePoints( (int) p1.getX(), (int) p2.getX(),
                                     (int) p1.getY(), (int) p2.getY(), index,
                                     flag);
            break;

          case 1:// progress is in x direction and the value of x decreasing
            numadded = addLinePoints( (int) p2.getX(), (int) p1.getX(),
                                     (int) p2.getY(), (int) p1.getY(), index,
                                     flag);
            break;

          case 2://progress is in y direction  and the value of y decreasing
            numadded = addLinePoints( (int) p2.getY(), (int) p1.getY(),
                                     (int) p2.getX(), (int) p1.getX(), index,
                                     flag);
            break;

          case 3: //progress is in y direction  and the value of y raising
            numadded = addLinePoints( (int) p1.getY(), (int) p2.getY(),
                                     (int) p1.getX(), (int) p2.getX(), index,
                                     flag);
            break;
        }

        totaladded += numadded; //number of points that were added
        index += numadded; //the start index of the segment
        endseg += numadded;
      }
      updateSegments(seg, -totaladded); //the minos sign is because the function updateSegments get num of points to reduce but here needs to add
      segment.setNumOfPoints(segment.getNumOfPoints() + totaladded);//updates the number of points in segment
      segments.set(seg, segment);//update the segment in segments vector

    }
  }


  /**
   * function shapeLength
   *  @return the length of a finished shape
   */
  private int shapeLength() {
    int sum = 0;
    //go through all the segments and sums thier length
    for (Iterator i = segments.listIterator(); i.hasNext(); ) {
      Segment seg = new Segment( (Segment) i.next());
      sum +=
          findLength(seg.getStartIndex(),
                     seg.getStartIndex() + seg.getNumOfPoints() - 1);//find a segment length
    }
    return sum;
  }

   /**
   * function findLength
   * @param start start index of a segment
   * @param end end index of a segment
   * @return the length of a segment
   */
 static protected int findLength(int start, int end) {
    int sum = 0;
    //go trough all points of segment and calculate the distance between them
    for (int i = start; i < end - 1; i++) {
      sum +=( (Point) points.elementAt(i)).distance((Point) points.elementAt(i + 1));
    }
    return sum;
  }

  /**
   * the function drawRect draws a rectangle that enclose the shape
   * @param shapeEV
   * @return the rectangle that enclosing the shape
   */
  static protected Rectangle drawRect(EdgeValues shapeEV) {
   return shapeEV.returnRect();
 }

 /**
  * the function separateByLines finds if the shape is a close shape by entering vertical and
  * horizontal lines and check the number of cuttings with the shape
  * @param shapev edge values of the shape
  * @param mySegs a vector that is a collection of the segments that combines the close shape
  * @return true if it's a close shape
  */
  static protected boolean separateByLines( EdgeValues shapev, Vector mySegs) {

 // if(shapeEV==null)return false;
  int widthLen = 0, widthStart = 0, heightLen = 0, heightStart = 0; // length and start value of bigger side
  int[] widthlines = {
      0, 0, 0, 0, 0}; //the width lines b parameter of equation value
  int[] heightlines = {
      0, 0, 0, 0, 0}; //the height lines b parameter of equation value
  int[] widthIntersectsCounter = {
      0, 0, 0, 0, 0}; //counter for how many points intersects with each width line
  int[] heightIntersectsCounter = {
      0, 0, 0, 0, 0}; //counter for how many points intersects with each height line
  int i;
  int jumpflag = 0;
  int numLines = 5;

  widthStart = shapev.getminX();
  widthLen = shapev.getmaxX() - shapev.getminX();//the width of the closing rectangle
//the width of the closing rectangle
  heightStart = shapev.getminY();
  heightLen = shapev.getmaxY() - shapev.getminY();//the height of the closing rectangle

  widthlines[0] = (int) (widthLen * 0.18 + widthStart); //the first line, after 18% of the start of the width
  for (i = 1; i < numLines; i++) {
    widthlines[i] += (int) (widthlines[i - 1] + 0.16 * widthLen); //each line in distance of 36% of the width from the rest of he lines

  }
  heightlines[0] = (int) (heightLen * 0.18 + heightStart); //the first line, after 18% of the start of the height
  for (i = 1; i < numLines; i++) {
    heightlines[i] += (int) (heightlines[i - 1] + 0.16 * heightLen); //each line in distance of 36% of the height from the rest of he lines

  }
  int[] lastWidthP = {
      Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
  int[] lastHeightP = {
      Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
  for(int k = 0; k < mySegs.size(); k++){
    int startP = ((Segment)mySegs.elementAt(k)).getStartIndex();
    int size = ((Segment)mySegs.elementAt(k)).getNumOfPoints();
  for (i = startP; i < startP + size; i++) { // for each point checks if it intersects with one of the lines
    Point p = (Point) (points.elementAt(i));
    for (int j = 0; j < numLines; j++) {
      jumpflag = 0;
      if (p.getX() <= widthlines[j] + 1 && p.getX() >= widthlines[j] - 1) {
        if ( (p.getY() < lastWidthP[j] - heightLen / 8 ||
              p.getY() > lastWidthP[j] + heightLen / 8)
            ) {
          widthIntersectsCounter[j]++; //found intersection
          lastWidthP[j] = (int) p.getY();
        }
      }
      if (p.getY() <= heightlines[j] + 1 && p.getY() >= heightlines[j] - 1) {
        if ( (p.getX() < lastHeightP[j] - widthLen / 8 ||
              p.getX() > lastHeightP[j] + widthLen / 8)
            ) {
          heightIntersectsCounter[j]++;
          lastHeightP[j] = (int) p.getX();
        }
      }
    }
  }
}

  int width1IntersectsCounter = 0; //counts how many lines cuts the shape only once
  int height1IntersectsCounter = 0;

  for (int j = 0; j < numLines; j++) {
    if (widthIntersectsCounter[j] <= 1) {
      width1IntersectsCounter++;
    }
    if (heightIntersectsCounter[j] <= 1) {
      height1IntersectsCounter++;
    }
  }

  if (width1IntersectsCounter > 1 || height1IntersectsCounter > 1) {
    return true; //link
  }
  return false; //close shape
}

  /**
   * the function updateRectBorders updates the borders of the closing rectangle after the cutting
   */
static protected void updateRectBorders() {

  shapeEV.clear();
  //go through vector points and sets the edge points of the shape
  for (int i = 0; i < points.size(); i++) {
    Point p1 = new Point(0, 0);
    p1 = (Point) points.elementAt(i);
    shapeEV.setEdgePoints( (int) p1.getX(), (int) p1.getY());
  }

  wholeShapeRect = drawRect(shapeEV);
}


/**
 * the function updateSegRectBorders updates the borders of the closing rectangle on each segment
 * after the cutting of an arrow head
 * @param segIndex the index of the segment in segments
 */

static protected void updateSegRectBorders(int segIndex) {
  Segment seg = (Segment) segments.elementAt(segIndex);//find the relevant segment
  Point p;
  int maxX = 0, maxY = 0, minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
//go through a segment and update is edge values
  for (int i = seg.getStartIndex();
       i < seg.getStartIndex() + seg.getNumOfPoints(); i++) {
    p = (Point) points.elementAt(i);

    if (p.getX() > maxX) {
      maxX = (int) p.getX();

    }
    else if (p.getX() < minX) {
      minX = (int) p.getX();

    }

    if (p.getY() > maxY) {
      maxY = (int) p.getY();

    }
    else if (p.getY() < minY) {
      minY = (int) p.getY();

    }
  }
  //update the right fields
  seg.setmaxX(maxX);
  seg.setmaxY(maxY);
  seg.setminX(minX);
  seg.setminY(minY);
  segments.set(segIndex, seg); // update the new borders in segments
}


   /**
    * the function recognizeEllipse recognizes if a shape is an ellipse by comparing
    *  the shape length with the shape perimeter according to the enclosing rectangle
    * @param length the length of the drawn shape
    * @param a  half the height of the enclosing rectangle
    * @param b  half the width of the enclosing rectangle
    * @return true if the shape is an ellipse
    */
   private boolean recognizeEllipse(int length, double a, double b) {
     double ellipsePeri = 0;
     ellipsePeri = Math.PI *
         Math.sqrt(2 * (a * a + b * b) - 0.5 * (a - b) * (a - b));// the ellipse perimeter according to a formula that is almost accurate
     if (length < ellipsePeri * 1.07 && length > ellipsePeri * 0.9) {
       return true;
     }

     return false;
   }


  /**
   * the function recognizeRectangle recognizes if the shape is a rectangle by comparing
   * the shape length with the enclosing rectangle perimeter
   * @param length the length of the drawn shape
   * @param a the height of the enclosing rectangle
   * @param b the width of the enclosing rectangle
   * @param minPercentage the variation that is still acceptable
   * @return true if the shape is a rectangle
   */
  private boolean recognizeRectangle(int length, double a, double b, double minPercentage) {
    double rectPeri = 2 * a + 2 * b;//thr rectangle perimeter
    if (length < rectPeri * 1.1 && length > rectPeri * minPercentage) {
      return true;
    }
    return false;
  }



  /**
    * the function updateSegments updates the sgments vector after cutting segments
    * @param index the index of the segment in vector segments
    * @param numRemoved the number of points that was removed at the cut
    */
   static protected void updateSegments(int index, int numRemoved) {
     int startIndex = 0;
     int myStart = ( (Segment) segments.elementAt(index)).getStartIndex();//the start index of the relevant segment
//go through the segments and updates the start index if needed
     for (int i = 0; i < segments.size(); i++) {
       startIndex = ( (Segment) segments.elementAt(i)).getStartIndex();
       if (startIndex > myStart) {
         ( (Segment) segments.elementAt(i)).setStartIndex(startIndex -
             numRemoved);
       }
     }
   }



  /**
   * the function drawArrowRect draws a rectangle that enclose the arrow heads
   * @param headArrow the arrow head we want to enclose
   * @return the rectangle that enclose the head
   */
  static protected Rectangle drawArrowRect(Vector headArrow) {
    int segX = 0, segY = 0, w = 0, h = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0, i = 0;
    int arrMaxX = 0, arrMaxY = 0, arrMinX = Integer.MAX_VALUE, arrMinY = Integer.MAX_VALUE;
    Segment seg;
    if (headArrow.size() == 0) {
         return null;
   }

    Integer index = (Integer) (headArrow.elementAt(i));
   // System.out.println("drawing rect for seg " + index.intValue());
    seg = (Segment) segments.elementAt(index.intValue());
//find the points of the rectangle
    x2 = seg.getminX();
    y2 = seg.getminY();
    w = seg.getSegWidth();
    h = seg.getSegHeight();
    Rectangle rect1 = new Rectangle(x2, y2, w, h);
    return rect1;
  }

  /**
   *the function clear clears all the vectors and flags
   */
  public void clear(){
    points.clear();//clear points vector
    segments.clear();//clear segments vector
    proccessing = false;//not analyzing shape
    shapeEV.clear();//clesr the edge values

  }


}

// end of class drawappNew


