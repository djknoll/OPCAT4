package extensionTools.hio;

import java.awt.Point;


/**
 * <p>Title:class Connection </p>
 * <p>Description:This class contains the edge points of a connection -
 * can be a relation edge points or a link edge points and also contains the range of the link head
 * that help us later finding the closest Thing </p>
 */
public class Connection {
  /**
   * static public Point destinationEdgePoint - the edge point in the side of the head or the second segment in case of relation
   */
  static public Point destinationEdgePoint;//the edge point in the side of the head
                                           //or the second segment in case of relation
 /**
 *  static public Point sourceEdgePoint - the edge point in the side of the body
 */
 static public Point sourceEdgePoint;//the edge point in the side of the body

 /**
  * private int destinationRange - half of the diagonal of the rectangle that enclose the head
  */
  private int destinationRange = 10;//half of the diagonal of the rectangle that enclose the head
  /**
  * private int sourceRange - half of the diagonal of the rectangle that enclose the head if there is no head we set to 10
  */
  private int sourceRange = 10;//half of the diagonal of the rectangle that enclose the head
                      //if there is no head we set to 10

    /**
     * consructor of Connection
     */
    public Connection() {}

  /**
  * the fanction getDestinationRange
  * @return half of the diagonal of the rectangle that close the head if there is no head we set to 10
  */
 public int getDestinationRange(){ return this.destinationRange;}

 /**
  * the fanction setDestinationRange
  * @param range the range to set
  */
  public void setDestinationRange(int range){ this.destinationRange = range;}

  /**
   * the fanction getSourceRange
   * @return the range
   */
  public int getSourceRange(){ return this.sourceRange;}

  /**
   * the fanction setSourceRange sets the range
   * @param range the range to set
   */
  public void setSourceRange(int range){ this.sourceRange = range;}

  /**
   * the fanction getDestinationEdgePoint
   * @return the edge point of the link head or relation body
   */
  public Point getDestinationEdgePoint(){ return destinationEdgePoint;}

  /**
   * the fanction setDestinationEdgePoint sets the edge point of the link head or relation body
   * @param point the destination edge point
   */
  public void setDestinationEdgePoint(Point point){ destinationEdgePoint = point;}

  /**
   * the fanction getSourceEdgePoint
   * @return the edge point of the link/relation body (or second head if exist)
   */
  public Point getSourceEdgePoint(){ return sourceEdgePoint;}

  /**
   * the fanction setSourceEdgePoint sets the edge point of the link/relation body (or second head if exist)
   * @param point the source edge point
   */
  public void setSourceEdgePoint(Point point){ sourceEdgePoint = point;}


}
