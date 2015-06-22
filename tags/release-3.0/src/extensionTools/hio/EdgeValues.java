package extensionTools.hio;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * <p>
 * Title:class EdgeValues
 * </p>
 * <p>
 * Description: contains the max and min edge values of a shape or a segment
 * </p>
 */

public class EdgeValues {

	/**
	 * private int maxX variable that indicates the maximum x coordinate in the
	 * segment
	 */
	private int maxX = 0;// variable that indicates the maximum x coordinate
							// in the segment

	/**
	 * private int minX variable that indicates the minimum x coordinate in the
	 * segment
	 */
	private int minX = Integer.MAX_VALUE;// variable that indicates the
											// minimum x coordinate in the
											// segment

	/**
	 * private int maxY variable that indicates the maximum y coordinate in the
	 * segment
	 */
	private int maxY = 0;// variable that indicates the maximum y coordinate
							// in the segment

	/**
	 * private int minY variable that indicates the minimum y coordinate in the
	 * segment
	 */
	private int minY = Integer.MAX_VALUE;// variable that indicates the
											// minimum y coordinate in the
											// segment

	/**
	 * constructor of EdgeValues
	 */
	public EdgeValues() {
	}

	/**
	 * constructor of EdgeValues
	 * 
	 * @param ev
	 *            another EdgeValues
	 */
	public EdgeValues(EdgeValues ev) {
		this.setEdgePoints(ev);
	}

	/**
	 * updates maxX- variable that indicates the maximum x coordinate in the
	 * segment
	 * 
	 * @param x
	 *            the new maximum
	 */
	public void setmaxX(int x) {
		this.maxX = x;
	}

	/**
	 * updates maxY- variable that indicates the maximum y coordinate in the
	 * segment
	 * 
	 * @param y
	 *            the new maximum
	 */
	public void setmaxY(int y) {
		this.maxY = y;
	}

	/**
	 * updates minX- variable that indicates the minimum x coordinate in the
	 * segment
	 * 
	 * @param x
	 *            the new minimum
	 */
	public void setminX(int x) {
		this.minX = x;
	}

	/**
	 * updates minY- variable that indicates the minimum y coordinate in the
	 * segment
	 * 
	 * @param y
	 *            the new minimum
	 */
	public void setminY(int y) {
		this.minY = y;
	}

	/**
	 * @return the maximum x coordinate of the segment
	 */
	public int getmaxX() {
		return this.maxX;
	}

	/**
	 * @return the maximum y coordinate of the segment
	 */
	public int getmaxY() {
		return this.maxY;
	}

	/**
	 * @return the minimum x coordinate of the segment
	 */
	public int getminX() {
		return this.minX;
	}

	/**
	 * @return the minimum y coordinate of the segment
	 */
	public int getminY() {
		return this.minY;
	}

	/**
	 * getSegMaxSide finds the larger side of the closing rectangle of the
	 * segment
	 * 
	 * @return the larger side of the closing rectangle
	 */
	public int getSegMaxSide() {
		int segw = Math.abs(this.maxX - this.minX);// holds the length width of the
											// segment
		int segh = Math.abs(this.maxY - this.minY);// holds the length hight of the
											// segment
		return segw > segh ? segw : segh;
	}

	/**
	 * @return the height of the closing rectangle of the segment (y direction)
	 */
	public int getSegHeight() {
		return Math.abs(this.maxY - this.minY);
	}

	/**
	 * @return the width of the closing rectangle of the segment (x direction)
	 */
	public int getSegWidth() {
		return Math.abs(this.maxX - this.minX);
	}

	/**
	 * the function setEdgePoints sets the edge points
	 * 
	 * @param x
	 *            the x coordinate to set
	 * @param y
	 *            the y coordinate to set
	 */
	public void setEdgePoints(int x, int y) {
		if (x > this.getmaxX()) {
			this.setmaxX(x);
		}

		if (x < this.getminX()) {
			this.setminX(x);
		}

		if (y > this.getmaxY()) {
			this.setmaxY(y);
		}

		if (y < this.getminY()) {
			this.setminY(y);
		}
	}

	/**
	 * the function setEdgePoints sets the edge points of the segment or a shape
	 * 
	 * @param p
	 *            the point that holds x and y to set
	 */
	public void setEdgePoints(Point p) {
		int x = (int) p.getX();// holds the x value of the checked point
		int y = (int) p.getY();// holds the y value of the checked point
		if (x > this.getmaxX()) {
			this.setmaxX(x);
		}

		if (x < this.getminX()) {
			this.setminX(x);
		}

		if (y > this.getmaxY()) {
			this.setmaxY(y);
		}

		if (y < this.getminY()) {
			this.setminY(y);
		}
	}

	/**
	 * the function setEdgePoints sets the edge points according to another
	 * EdgeValues
	 * 
	 * @param ev
	 *            the another EdgeValues
	 */
	public void setEdgePoints(EdgeValues ev) {

		if (ev.getmaxX() > this.getmaxX()) {
			this.setmaxX(ev.getmaxX());
		}

		if (ev.getminX() < this.getminX()) {
			this.setminX(ev.getminX());
		}

		if (ev.getmaxY() > this.getmaxY()) {
			this.setmaxY(ev.getmaxY());
		}

		if (ev.getminY() < this.getminY()) {
			this.setminY(ev.getminY());
		}
	}

	/**
	 * the function clear clears the edge values
	 */
	public void clear() {
		this.maxX = 0;
		this.minX = Integer.MAX_VALUE;
		this.maxY = 0;
		this.minY = Integer.MAX_VALUE;

	}

	/**
	 * the function topLeft
	 * 
	 * @return the topleft point
	 */
	public Point topLeft() {
		return new Point(this.minX, this.minY);
	}

	/**
	 * the function returnRect
	 * 
	 * @return rectangle that enclose the segment
	 */
	public Rectangle returnRect() {

		Rectangle rect = new Rectangle(this.getminX(), this.getminY(), this
				.getSegWidth(), this.getSegHeight());

		return rect;
	}

}
