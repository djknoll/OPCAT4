package extensionTools.hio;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Vector;


/**
 * <p>Title: class CornerSearcher </p>
 * <p>Description: finds the corners in a line made of a sequence of points.</p>
 */
public class CornerSearcher {

  /**
   * Vector corners - keeps corners index
   */
  Vector corners; //keeps corners index
  /**
   * Vector points - a pointer to the points vector
   */
  Vector points;//a pointer to the points vector
  /**
   * int jumpSize - number of points to jump
   */
  int jumpSize = 16;//number of points to jump
  /**
   *  int mindis - minimum length from which we suspect a corner area
   */
  int mindis = 10;//minimum length from which we suspect a corner area
  /**
   * int maxSize - maximum size of jump
   */
  int maxSize = 60;//maximum size of jump
  /**
   * int maxAngleSize - maximum angle size for a valid corner point
   */
  int maxAngleSize = 115;// maximum angle size for a valid corner point

  /**
   * consructor of CornerSearcher
   * @param start the starting point in vector points
   * @param size the shape length
   * @param closeShape true if it's close shape
   */
  public CornerSearcher( int start, int size, boolean closeShape){

    if( size < 60 ) jumpSize = 8;
    corners = new Vector();

    points = DrawAppNew.points;	//initiate the pointer
    FindShapeCorners(start, size);//initiate search for corners

//if it is a close shape there may be corner between the end of the line and its begining
if(closeShape){
  if( size >= jumpSize){
    int end = start + size - 1;//end of vector points

    //calculate the point at the meeting of the start and the end
     int angle = calcAngle((Point)points.elementAt(end - jumpSize/2),
(Point)points.elementAt(end),
                 (Point)points.elementAt(start + jumpSize/2));

    //if found an angle
   if(angle < maxAngleSize){
     Corner cornerObj = new Corner(end, angle);
    corners.add(cornerObj);
    }}
}
}


/**
 * the function FindShapeCorners is a recursive function to find the shapes corners
 * @param start where in vector points the search begins
 * @param size on how many points the search should be preformed
 * @return the index of the last corner point that was found
 */
  public int FindShapeCorners(int start, int size) {
    int jump = jumpSize; //a variable which holds the current jump size
    int len = 0;//minimum distance for suspecting corner area
    int res;
    double dist = 0;
    double minDist = jumpSize * Math.sqrt(2) / 2;//the distance of the mid point from the line

    if (size <= jumpSize * 3 / 2)
      return FindCorner(start, size);//look for the exact corner point

    dist = calcDist( (Point) points.elementAt(start),
                    (Point) points.elementAt(start + jump / 2),
                    (Point) points.elementAt(start + jump));
    len = calcLen( (Point) points.elementAt(start),
                  (Point) points.elementAt(start + jump)) / 6;

//while the distance is under len and under the minimum required which is 10 continue searching
//for corner area
    while ( (dist <= len) && (dist < mindis) && (jump < maxSize)) {
      if (jump == size - 1)//we finished going over the points
        return 0;
      jump *= 2;//enlarge the jump
      jump = jump < size ? jump : size - 1;//if jump is over vectorsize jump to end of vector

      //calculate new distance
      dist = calcDist( (Point) points.elementAt(start),
                      (Point) points.elementAt(start + jump / 2),
                      (Point) points.elementAt(start + jump));

     // calculate new len
      len = calcLen( (Point) points.elementAt(start),
                    (Point) points.elementAt(start + jump)) / 6;
    }
    //end of while loop

    if (jump == jumpSize)
      res = FindCorner(start, jump);
    else
      res = FindCornerArea(start + jump / 4, 3 * jump / 4);

    //no point was found continue search from a bit before where we are shore there is no point
    if (res == 0)
      return FindShapeCorners(start + jump / 2, size - jump / 2);

    //point was found at index rea, continue search from there
    return FindShapeCorners(res, size + start - res);
  }


  /**
   * the function FindCornerArea is a recursive function which reduces an area which it suspects
   * there might be a corner
   * @param start where in vector points the search begins
   * @param size on how many points the search should preform
   * @return corner index in points Vector if finds a corner, '0' otherwise.
   */
  public int FindCornerArea(int start, int size) {
    int dist = 0, jump = jumpSize, len = 0;

    if (size <= jumpSize * 3 / 2)
      return FindCorner(start, size);

    dist = calcDist( (Point) points.elementAt(start),
                    (Point) points.elementAt(start + jump / 2),
                    (Point) points.elementAt(start + jump));
    len = calcLen( (Point) points.elementAt(start),
                  (Point) points.elementAt(start + jump)) / 6;

//search for a corner area
    while (dist <= len && (dist < mindis) && (jump < maxSize)) {

      if (jump == size - 1)
        return 0;
      jump *= 2;
      jump = jump < size ? jump : size - 1;
      dist = calcDist( (Point) points.elementAt(start),
                      (Point) points.elementAt(start + jump / 2),
                      (Point) points.elementAt(start + jump));
      len = calcLen( (Point) points.elementAt(start),
                    (Point) points.elementAt(start + jump)) / 6;
    }

    //smallest area possible, search for an exact corner point
    if (jump == jumpSize)
      return
          FindCorner(start, jump);

    //recursive call for a smaller area
    return FindCornerArea(start + jump / 4, 3 * jump / 4);
  }


  /**
   * the function FindCorner goes on an array of points and calculate the angle on each one
   * of them by stretching a line from that points to appoint after it and to a point before it.
   * If the smallest corner in the array is smaller then 110 degrees then that is where we have
   * a corner
   * @param start the index of the point where the search begins
   * @param size on how many points the search should be preformed
   * @return the index of the point of the corner if found one, '0' otherwise
   */
  public int FindCorner(int start, int size) {
    double angle = maxAngleSize;//holds the smallest angle found so far if its less then maxAngleSize
    double newAngle =  360; //temporary variable for angles
    int corner = 0;//index of the corner on points vector

   //go on the points from jumpsize/2 to size - jumpsize/2
    for (int i = start; i <= start + size - jumpSize; i++) {
      //calac the angle
      newAngle = calcAngle( (Point) points.elementAt(i),
                           (Point) points.elementAt(i + jumpSize / 2),
                           (Point) points.elementAt(i + jumpSize - 1));

     //if found a smaller angle replace it
        if (newAngle < angle) {
        angle = newAngle;
        corner = i + jumpSize / 2;
      }
    }

    //if found a corner create a Corner variable
    if (corner > 0) {
      Corner cornerObj = new Corner(corner, (int) angle);
      corners.add(cornerObj);
    }
    return corner;
  }//end of loop


  /**
   * the function calcLen calculate the distance between two points
   * @param p1 first point
   * @param p2 second point
   * @return the distance
   */
    public int calcLen(Point p1, Point p2) { return (int)p1.distance(p2);}// return distance between 2 points

    /**
     * the function calcDist calculate the distance of point p from the line that streches
     * from point 'startLine' to point 'endLine'.
     * @param startLine point where the line begins
     * @param p the point from which we calculate the distance
     * @param endLine point where the line ends
     * @return the distance of p from the line 	startLine---endLine
     */
    public int calcDist(Point startLine, Point p, Point endLine)
    {
      Line2D.Double line = new Line2D.Double(startLine, endLine);
      return (int) line.ptLineDist(p);
    }

    /**
     * the function calcAngle calculate the angle between line p1---p2 and line p2---p3
     * @param p1
     * @param p2
     * @param p3
     * @return angle between line p1---p2 and line p2---p3
     */
    public int calcAngle(Point p1, Point p2, Point p3)
    {
      double num, denum;
      Point[] vec = new Point[2];

      vec[0] = new Point( (int) (p1.getX() - p2.getX()),
                         (int) (p1.getY() - p2.getY()));
      vec[1] = new Point( (int) (p3.getX() - p2.getX()),
                         (int) (p3.getY() - p2.getY()));
      num = vec[0].x * vec[1].x + vec[0].y * vec[1].y;
      denum = Math.sqrt(Math.pow(vec[0].x, 2) +
                        Math.pow(vec[0].y, 2)) *
          Math.sqrt(Math.pow(vec[1].x, 2) + Math.pow(vec[1].y, 2));
      int angle = (int) (Math.acos(num / denum) * 180 / Math.PI);
      angle = (angle == 0) ? 180 : angle;
      return angle;
    }

    /**
     * the function getCorners
     * @return a vector of the corners found by the class for a given shape
     */
  Vector getCorners(){ return corners;}

  /**
   * the function getCornersSum
   * @return the sum of angle values of all the angles in vector corners
   */
  int getCornersSum(){
    int CornersSum = 0;
    for ( int i = 0; i < corners.size(); i++)
      CornersSum += ((Corner) corners.elementAt(i)).getAngle();

    return CornersSum;
  }

  /**
   * the function getCornersBetweenValues makes a vector of all the corners in Vector corners that their
   * angle value is between the given range
   * @param min minimum value for a valid angle
   * @param max maximum value for a valid angle
   * @return a vector of the valid corners
   */
  Vector getCornersBetweenValues(int min, int max){
    Vector ValidCorners = new Vector();

    for ( int i = 0; i < corners.size(); i++)
      if ( ((Corner) corners.elementAt(i)).getAngle() > min &&
          ((Corner) corners.elementAt(i)).getAngle() < max)
        ValidCorners.add(corners.elementAt(i));


   return ValidCorners;
  }
}
//end of class CornerSearcher

/**
 * <p>Title: class Corner</p>
 * <p>Description: holds corner parameters - index in vector and angle value</p>
 */
 class Corner{
   /**
    * int index the index of the corner in vector points
    */
  public int index = -1;
  /**
   * public int angle the angle of the corner
   */
  public int angle = 361;
 /**
  * costructor of class Corner
  * @param i the index of the corner in a vector/array
  * @param ang
  */
 public Corner( int i, int ang){
   index = i;
   angle = ang;
 }

 /**
  * the function getIndex
  * @return the index of the corner
  */
 int getIndex(){return index;}

 /**
  * the function getPoint
  * @return the point of the corner from vector points
  */
 Point getPoint(){return(Point)DrawAppNew.points.elementAt(index);}

 /**
  * the function getAngle
  * @return the angle of the corner
  */
 int getAngle(){return angle;}
}

///end of class Corner