package extensionTools.hio;

/**
 * <p>Title: class Segment </p>
 * <p>Description: a class that holds information about a segment
 *  number of points, index of begining in vector points, the min/max points of the segment and a flag that
 * indicates the segments drawing direction.</p>
 */
public class Segment
    extends EdgeValues {
  /**
   * private int numOfPoints holds the number of points of a segment
   */
  private int numOfPoints = 0;//holds the number of points of a segment
  /**
   * private int startIndex indicates the start of the segment in the segment's array
   */
  private int startIndex;//indicates the start of the segment in the segment's array
  /**
   * protected boolean fromStart indicates the segments drawing direction
   */
  protected boolean fromStart = true;//indicates the segments drawing direction

  /**
   * constructor of Segment
   * @param sindex start index at points vector
   * @param sSize the number of points in segment
   * @param maxx the max x coordinate of the segment
   * @param maxy the max y coordinate of the segment
   * @param minx the min x coordinate of the segment
   * @param miny the min y coordinate of the segment
   */
  public Segment(int sindex, int sSize, int maxx, int maxy, int minx, int miny) {
    super();
    startIndex = sindex;
    numOfPoints = sSize;
    setmaxX(maxx);
    setmaxY(maxy);
    setminX(minx);
    setminY(miny);
  }

  /**
   * constructor of Segment
   * @param sindex start index at points vector
   * @param sSize the number of points in segment
   * @param ev the edge values of the segment
   */
 public Segment(int sindex, int sSize, EdgeValues ev) {
    super(ev);
    startIndex = sindex;
    numOfPoints = sSize;
  }

  /**
   * constructor of Segment
   * @param s creats a new Segment with the data of the old one
   */
  public Segment(Segment s) {
    super(s);
    numOfPoints = s.getNumOfPoints();
    startIndex = s.getStartIndex();
    fromStart = s.isFromStart();
  }

  /**
   * the function setNumOfPoints  update the number of points in the segment
   * @param num number of points in segment
   */
  public void setNumOfPoints(int num) {
    numOfPoints = num;
  }

  /**
   * the function getNumOfPoints
   * @return number of points in the segment
   */
  public int getNumOfPoints() {
    return numOfPoints;
  }

  /**
   *  the function getStartIndex
   *  @return the index in the vector that the segment start from
   */
  public int getStartIndex() {
    return startIndex;
  }

  /**
   * the function setStartIndex updates the start index of segment in the vector
   * @param index the start index
   */
  public void setStartIndex(int index) {
    startIndex = index;
  }

  /**
   * the function isFromStart
   *  @return the flag from start that indicates the drawing diraction of the segment
   */
  public boolean isFromStart() {
    return fromStart;
  }

  /**
   * the function setFromStart  update the fromStart flag
   * @param value the new value of the flag
   */
  public void setFromStart(boolean value) {
    fromStart = value;
  }

}
////////////////////////end of class Segment

/**
 * <p>Title: class LineParam </p>
 * <p>Description:contains the a and b parameters of line equation </p>
 */
class LineParam {
  /**
   * private double aLine the a parameter of a line
   */
  private double aLine = 0;//the a parameter of a line
  /**
   * private double bLine the b parameter of a line
   */
  private double bLine = 0; //the b parameter of a line

 public LineParam() {}
  /**
   * constructor of LineParam
   * @param param the a and b parameters of line equation
   */
  public LineParam(LineParam param) {
    aLine = param.getA();
    bLine = param.getB();
  }
  /**
   * the function setA updates the a parameters
   * @param a the a param to be set
   */
  public void setA(double a) {
    aLine = a;
  }
  /**
   * the function returns the a param
   */
  public double getA() {
    return aLine;
  }
  /**
   * the function setB updates the b parameters
   * @param b the b param to be set
   */
  public void setB(double b) {
    bLine = b;
  }
  /**
   * the function returns the b param
   */
  public double getB() {
    return bLine;
  }
}
////////////////////////end of class LineParam
