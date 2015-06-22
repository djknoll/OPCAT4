package extensionTools.opltoopd;

/**
 * Represent Free Area on the current OPD screen.
 */
public class FreeArea {
  /**
   * The left upper corner X coordinate.
   */
  private int X;
  /**
   * The left upper corner Y coordinate.
   */
  private int Y;
  /**
   * The Width of the current area.
   */
  private int Width;
  /**
   * The Height of the current area.
   */
  private int Height;

  /**
   * Creates a new free area in the left upper corner of the screen
   * @param width the needed wight
   * @param height the needed height
   */
  public FreeArea(int width, int height) {
    X=0;
    Y=0;
    Width = width;
    Height = height;
  }


  /**
   * Creates a new free area
   * @param x X coordinate
   * @param y Y coordinate
   * @param width the needed wight
   * @param height the needed height
   */
  public FreeArea(int x, int y, int width, int height) {
   X=x;
   Y=y;
   Width = width;
   Height = height;
  }

  /**
   * Returns X coordinate of the Free Area
   * @return X
   */
  public int getX (){
      return X;
  }
  /**
   * Returns Y coordinate of the Free Area
   * @return Y
   */
  public int getY (){
     return Y;
 }

 /**
  * Returns the Width of the Free Area
  * @return Width
   */
 public int getWidth (){
     return Width;
 }

 /**
  * Returns the Height of the Free Area
  * @return Height
   */
 public int getHeight (){
     return Height;
 }

 /**
  * Sets X coordinate of the Free Area
  * @param x X coordinate of the Free Area
  */
 public void setX (int x){
      X = x;
 }

 /**
  * Sets Y coordinate of the Free Area
  * @param y Y coordinate of the Free Area
  */
 public void setY (int y){
       Y = y;
 }

 /**
  * Sets the Width of the Free Area
  * @param width Width coordinate of the Free Area
  */
 public void setWidth (int width){
     Width = width;
 }

 /**
  * Sets the Height of the Free Area
  * @param height Height coordinate of the Free Area
  */
 public void setHeight (int height){
      Height = height;
 }

 /**
  * Overrides method in Object class
  *
  */
 public boolean equals(Object other){
   if (other!=null && other.getClass().equals(this.getClass())){
     return ((X==((FreeArea)other).X)&&(Y==((FreeArea)other).Y));
   }
   return false;
 }
}