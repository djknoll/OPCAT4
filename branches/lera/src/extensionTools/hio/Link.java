package extensionTools.hio;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * <p>
 * Title: class Link
 * </p>
 * <p>
 * Description: handles the things needed to recognize a link and extends class
 * connection that holds the source and destination points of the link
 * </p>
 */

public class Link extends Connection {
	/**
	 * static Vector segments the drawn segments vector
	 */
	static Vector segments = DrawAppNew.segments;

	/**
	 * Vector points the drawn points vector
	 */
	Vector points = DrawAppNew.points;

	/**
	 * static Vector secondArrowHead contains the index of the segment in
	 * segments
	 */
	static Vector secondArrowHead = new Vector(); // contains the index of the
													// segment in segments

	/**
	 * static Vector firstArrowHead contains the index of the segment in
	 * segments
	 */
	static Vector firstArrowHead = new Vector();

	/**
	 * static Rectangle firstRect contains the closing rectangle of an arrow
	 * head
	 */
	static Rectangle firstRect; // contains the closing rectangle of an arrow
								// head

	/**
	 * static Rectangle secondRect contains the closing rectangle of an arrow
	 * head
	 */
	static Rectangle secondRect; // contains the closing rectangle of an
									// arrow head

	/**
	 * boolean isFirstIsSource flag that indicates
	 */
	boolean isFirstIsSource = false;// flag that indicates

	/**
	 * private int bodySeg the index of the body segment in segments
	 */
	private int bodySeg; // the index of the body segment in segments

	/**
	 * private Point bodySegStart holds the start point of the body
	 */
	private Point bodySegStart;

	/**
	 * private Point bodySegEnd holds the end point of the body
	 */
	private Point bodySegEnd;

	/**
	 * static Graphics2D g2 the current graphics
	 */
	static Graphics2D g2;

	/**
	 * int arrow1 contains the arrow head type int arrow2 contains the arrow
	 * head type
	 */
	int arrow1 = 0, arrow2 = 0;

	/**
	 * ShapeSmoother smooth a varaible of ShapeSmoother that cleans the links
	 * head
	 */
	ShapeSmoother smooth = new ShapeSmoother(segments, this.points);// a varaible of
																// ShapeSmoother
																// that cleans
																// the links
																// head

	/**
	 * constructor of Link
	 * 
	 * @param g
	 *            Graphics
	 */
	public Link(Graphics2D g) {
		g2 = g;
		// clear the links heads
		secondArrowHead.clear();
		firstArrowHead.clear();
		this.arrow1 = 0;
		this.arrow2 = 0;
	}

	/**
	 * the fanction calcRange calculate the range in which we look for a
	 * Thing(object or process)
	 * 
	 * @param rect
	 *            the closing rectangle of the link head
	 * @return half the diagonal of rect
	 */
	double calcRange(Rectangle rect) {
		return Math.sqrt(Math.pow(rect.getHeight(), 2)
				+ Math.pow(rect.getWidth(), 2)) / 2;
	}

	/**
	 * the fanction calcMidPoint
	 * 
	 * @param rect
	 *            the closing rectangle of the link head
	 * @return the middle point of rect
	 */
	Point calcMidPoint(Rectangle rect) {

		int x = (int) (rect.getLocation().getX() + rect.getWidth() / 2);
		int y = (int) (rect.getLocation().getY() + rect.getHeight() / 2);

		Point p = new Point(x, y);
		return p;
	}

	/**
	 * the function drawArrowRect draws a rectangle that enclose the arrow heads
	 * 
	 * @param g
	 *            the Graphics data
	 * @param headArrow
	 *            the arrow head we want to enclose
	 * @return the rectangle that enclose the head
	 */
	static Rectangle drawArrowRect(Graphics g, Vector headArrow) {
		int w = 0, h = 0, x2 = 0, y2 = 0;

		Segment seg;
		if (headArrow.size() == 0) {
			return null;
		}

		Integer index = (Integer) headArrow.elementAt(0);

		seg = (Segment) segments.elementAt(index.intValue());
		x2 = seg.getminX();
		y2 = seg.getminY();
		w = seg.getSegWidth();
		h = seg.getSegHeight();
		Rectangle rect1 = new Rectangle(x2, y2, w, h);
		g.drawRect(x2, y2, w, h);
		return rect1;
	}

	/**
	 * the function separateArrowHeads finds the arrow heads and separate them
	 * to first head and second head(if exist)
	 */
	void separateArrowHeads() {
		if (segments.size() <= 1) {// if there is no heads
			return;
		}

		int index = 0;
		Segment tempseg;
		Segment seg = (Segment) segments.elementAt(0);

		for (int i = 1; i < segments.size(); i++) { // determine the longest
													// segment to be the arrow
													// body
			tempseg = (Segment) segments.elementAt(i);
			if (tempseg.getSegMaxSide() > seg.getSegMaxSide()) {
				seg = tempseg;
				index = i;
			}
		}

		this.bodySeg = index;
		this.bodySegStart = (Point) this.points.elementAt(seg.getStartIndex()); // the
																		// point
																		// where
																		// the
																		// body
																		// seg
																		// starts
		this.bodySegEnd = (Point) this.points.elementAt(seg.getStartIndex()
				+ seg.getNumOfPoints() - 1); // the point where the body seg
												// ends

		firstArrowHead.clear();
		secondArrowHead.clear();

		Segment nextseg;
		Point segMidPoint;
		Integer i;

		for (int j = (this.bodySeg + 1) % segments.size(); j != this.bodySeg;) { // go
																		// through
																		// all
																		// the
																		// segments
																		// except
																		// the
																		// body
			nextseg = (Segment) segments.elementAt(j);
			i = new Integer(j);
			segMidPoint = (Point) this.points.elementAt(nextseg.getStartIndex()
					+ nextseg.getNumOfPoints() / 2);
			// if the middle point of the head segment closer to the start of
			// the body segment enter the segment to
			// the firstArrowHead
			if (segMidPoint.distance(this.bodySegStart) < segMidPoint
					.distance(this.bodySegEnd)) {
				firstArrowHead.add(i);
			} else {
				secondArrowHead.add(i);
			}

			j = (++j) % segments.size();
		}
	}

	/**
	 * the function recognizeHeads recognizes the arrow heads
	 * 
	 * @param head
	 *            the vector of the first or second head that contains the index
	 *            of the segment in segments
	 * @param rect
	 *            the enclosing rectangle of the head
	 * @return the kind of link that was recognized
	 */
	private int recognizeHeads(Vector head, Rectangle rect) {
		if ((head.size() == 0) || (rect == null)) {
			return -1;
		}

		Integer index = new Integer(0);
		Vector tempX = new Vector();
		Vector tempY = new Vector();
		Vector tempDiag = new Vector();
		int intersectX = 0, intersectY = 0;
		index = (Integer) head.firstElement();
		Segment seg = (Segment) segments.elementAt(index.intValue());
		CornerSearcher cs = new CornerSearcher(seg.getStartIndex(), seg
				.getNumOfPoints(), true);
		double midY = (seg.getmaxY() + seg.getminY()) / 2; // the mid point of
															// the arrow head
		double midX = (seg.getmaxX() + seg.getminX()) / 2;
		if (seg.getNumOfPoints() < 8) {// if the segment is smaller then 8
										// pixels we ignore it
			return -1;
		}

		// /checks for open/agent arrow by num of intersection with lines who
		// cross the rectangle
		for (int i = seg.getStartIndex(); i < seg.getNumOfPoints()
				+ seg.getStartIndex(); i++) {
			Point p = (Point) this.points.elementAt(i);
			if ((p.getY() <= midY + 1) && (p.getY() >= midY - 1)) { // if a point in
																// the head has
																// the same y
																// value
				// as the mid point of the head
				tempX.add(p);
				intersectX++; // intersects in the X axis
			}
			if ((p.getX() <= midX + 1) && (p.getX() >= midX - 1)) { // if a point in
																// the head has
																// the same x
																// value
				// as the mid point of the head
				tempY.add(p);
				intersectY++; // intersects in the Y axis
			}
		}

		// remove the duplicate points from x
		for (int k = 0; k < tempX.size() - 1; k++) {
			int x1 = (int) ((Point) tempX.elementAt(k)).getX();
			int x2 = (int) ((Point) tempX.elementAt(k + 1)).getX();
			if (Math.abs(x2 - x1) <= 2) {
				intersectX--;
			}
		}

		// remove the duplicate points y
		for (int k = 0; k < tempY.size() - 1; k++) {
			int y1 = (int) ((Point) tempY.elementAt(k)).getY();
			int y2 = (int) ((Point) tempY.elementAt(k + 1)).getY();
			if (Math.abs(y2 - y1) <= 2) {
				intersectY--;
			}
		}

		// if find more then 4 intersection in each direction
		if ((intersectX >= 4) || (intersectY >= 4)) {
			return DrawAppNew.agentArrow;
		}
		// if there is just one intersect either in x direction or y
		if ((intersectX == 1) || (intersectY == 1)) {
			return DrawAppNew.openArrow;
		}

		// //intersection with diagonal
		double diagDist = Point2D.distance(midX, midY, seg.getminX(), seg
				.getminY()); // the distance between the top left point to
								// center
		LineParam param = new LineParam();
		this.findIntersectsWithDiag(param, seg, tempDiag);
		this.findIntersectsWithDiag(param, seg, tempDiag);

		for (int k = 0; k < tempDiag.size() - 1; k++) {
			Point p1 = (Point) tempDiag.elementAt(k);
			for (int i = k + 1; i < tempDiag.size(); i++) {
				Point p2 = (Point) tempDiag.elementAt(i);
				if ((Math.abs(p2.getX() - p1.getX()) <= 5)
						&& (Math.abs(p2.getY() - p1.getY()) <= 5)) {
					tempDiag.removeElementAt(i--); // remove the duplicate
													// points
				}
			}
		}

		Vector distFromCenter = new Vector();

		for (int i = 0; i < tempDiag.size(); i++) {
			double dist;
			dist = Point2D.distance(midX, midY, ((Point2D) tempDiag
					.elementAt(i)).getX(), ((Point2D) tempDiag.elementAt(i))
					.getY());
			// distance between center point to the intersect point
			java.lang.Double d = new java.lang.Double(dist);
			distFromCenter.add(d);
		}

		int numOfLowProp = 0;
		for (int i = 0; i < tempDiag.size(); i++) {
			double prop = (((java.lang.Double) (distFromCenter.elementAt(i)))
					.doubleValue() / diagDist); // the distance between
												// intersect point to center
												// divided by the
			// distance of half the diagonal
			if (prop < 0.53) {
				numOfLowProp++; // number of times that there is a proportion
								// lower then 0.53
			}
		}

		cs.getCornersSum();

		if ((numOfLowProp > 1)
				|| ((cs.getCorners().size() == 2) && (cs.getCornersSum() < 80 * cs
						.getCorners().size())) || (cs.getCorners().size() > 2)) {
			return DrawAppNew.triangleArrow;
		} else {
			return DrawAppNew.instrumentArrow;
		}
	}

	/**
	 * the function findIntersectsWithDiag
	 * 
	 * @param param
	 *            the line equation of the diagonal
	 * @param seg
	 *            we check intersection with diagonal
	 * @param tempDiag
	 *            the diagonal of the enclosing head rectangle
	 */
	private void findIntersectsWithDiag(LineParam param, Segment seg,
			Vector tempDiag) {
		for (int i = seg.getStartIndex(); i < seg.getNumOfPoints()
				+ seg.getStartIndex(); i++) {
			Point2D p = (Point2D) this.points.elementAt(i);
			if ((p.getY() <= (param.getA() * p.getX() + param.getB()) + 1)
					&& (p.getY() >= (param.getA() * p.getX() + param.getB()) - 1)) { // found
																					// an
																					// intersect
																					// with
																					// the
																					// diagonal
				tempDiag.add(p);
			}
		}

	}

	/**
	 * the function recognizeArrHeads unions each head to one segment, smooth
	 * the heads and recognizes the kind of link that was drawn
	 * 
	 * @param g
	 *            contains the Graphics of the shape
	 */
	int recognizeArrHeads(Graphics2D g) {

		if (segments.size() == 1) {
			if (this.isBiDirectionalRelation()) {
				// BiDirectionalRelation
				return 22;
			}

			return -1;
		}

		if (firstArrowHead.size() > 0) {
			this.smooth.unionTOneSeg(firstArrowHead); // the heads already exists
			this.separateArrowHeads(); // update after union the segments
			Link.firstRect = drawArrowRect(g2, Link.firstArrowHead);
		}
		if (secondArrowHead.size() > 0) {
			this.smooth.unionTOneSeg(secondArrowHead); // union the segments of the
													// head into one segment
			this.separateArrowHeads(); // update after union the segments
			Link.secondRect = drawArrowRect(g2, Link.secondArrowHead);
		}

		int countsides = firstArrowHead.size() + secondArrowHead.size();

		if (firstArrowHead.size() == 1) {
			this.arrow1 = this.recognizeHeads(firstArrowHead, firstRect); // recognize the
																// first head
		}
		if (secondArrowHead.size() == 1) {
			this.arrow2 = this.recognizeHeads(secondArrowHead, secondRect); // recognize
																	// the
																	// second
																	// head

		}
		if (countsides == 0) {// there are no heads
			return 0;
		}

		if ((countsides == 2) && (this.arrow1 != this.arrow2)) { // if both heads are exist
													// and diffrent from one
													// another
			return -1;
		}

		this.findEdgePoints();

		switch (this.arrow1 + this.arrow2 + countsides) {
		case (11): // one of the heads is an open arrow and the other dose'nt
					// exist
			if (this.isBiDirectionalRelation()) {
				return 22;
			} else {
				return 11;
			}
		case (22): // both heads are open arrows
			return 22;
		case (61): // one of the heads is a triangle arrow and the other
					// dose'nt exist
			if (this.isInvocation()) {
				return 65;
			}
			return 61;
		case (122): // both heads are triangle arrows
			return 122;
		case (31): // one of the heads is an agent arrow and the other dose'nt
					// exist
			return 31;
		case (41): // one of the heads is an instrument arrow and the other
					// dose'nt exist
			return 41;
		default:
		}
		return -1;

	}

	/**
	 * the function findEdgePoints finds the source and destination of the link
	 */
	private void findEdgePoints() {

		int whereHeads = 0;

		if (firstArrowHead.size() > 0) {
			whereHeads = 1;// there is arrow head only in the start of body seg
		}
		if (secondArrowHead.size() > 0) {

			if (whereHeads != 1) {
				whereHeads = 2;// there is arrow head only in the end of body
								// seg
			} else {
				whereHeads = 3;// there are arrow heads in both ends of body
								// seg
			}
		}

		switch (whereHeads) {
		case 0: // no heads
			return;
		case 1: // just first head exist and the head in the start of the body
			this.setDestinationEdgePoint(this.calcMidPoint(firstRect));
			this.setDestinationRange((int) this.calcRange(firstRect) + 10);
			this.setSourceEdgePoint(this.bodySegEnd);

			break;
		case 2: // just second head exist and the head in the end of the body
			this.setDestinationEdgePoint(this.calcMidPoint(secondRect));
			this.setDestinationRange((int) this.calcRange(secondRect) + 10);
			this.setSourceEdgePoint(this.bodySegStart);

			break;
		case 3: // both heads exists
			this.setDestinationEdgePoint(this.calcMidPoint(secondRect));
			this.setDestinationRange((int) this.calcRange(secondRect) + 10);
			this.setSourceEdgePoint(this.calcMidPoint(firstRect));
			this.setSourceRange((int) this.calcRange(firstRect) + 10);
		}

	}

	/**
	 * the function isInvocation
	 * 
	 * @return true if the link is an invocation link
	 */
	private boolean isInvocation() {
		Segment seg = (Segment) segments.elementAt(this.bodySeg);
		int startIndex = seg.getStartIndex();
		int size = seg.getNumOfPoints();
		CornerSearcher cs = new CornerSearcher(startIndex, size, false);
		Vector bodyCorners = cs.getCorners();// get the corners of thr
												// segment
		if (bodyCorners.size() > 0) {
			for (int i = 0; i < bodyCorners.size(); i++) {
				Corner corner = (Corner) bodyCorners.elementAt(i);
				// if the angle between 8 and 60 and the angle is in the second
				// or third quarter
				if (// corner.getAngle() > 8 &&
				(corner.getAngle() < 70)
						&& (corner.getIndex() > startIndex + size / 4)
						&& (corner.getIndex() < startIndex + size * 3 / 4)) {
					return true;
				}

			}

		}

		return false;
	}

	/**
	 * the function isBiDirectionalRelation
	 * 
	 * @return true if it's a BiDirectionalRelation
	 */
	private boolean isBiDirectionalRelation() {
		Segment seg = (Segment) segments.elementAt(this.bodySeg);
		int startIndex = seg.getStartIndex();
		int size = seg.getNumOfPoints();

		CornerSearcher cs = new CornerSearcher(startIndex, size, false);
		Vector bodyCorners = cs.getCorners();
		int numCorners = bodyCorners.size();
		if ((numCorners > 0) && (numCorners <= 2)) {// if there 1 or 2 corners
			for (int i = 0; i < numCorners; i++) {
				Corner corner = (Corner) bodyCorners.elementAt(i);
				// return false if the corner bigger then 75 degrees or in the
				// middale of the segment
				if (// corner.getAngle() < 8 ||
				(corner.getAngle() > 75)
						|| ((corner.getIndex() > startIndex + size / 3)
						&& (corner.getIndex() < startIndex + size * 2 / 3))) {
					return false;
				}

				if ((corner.getIndex() < startIndex + size / 3) && (this.arrow1 != 0)
						&& (numCorners == 1)) {
					return false;
				}

				if ((corner.getIndex() > startIndex + size * 2 / 3)
						&& (this.arrow2 != 0) && (numCorners == 1)) {
					return false;
				}

				if ((corner.getIndex() < startIndex + size / 3) && (this.arrow1 == 0)) {
					this.setSourceEdgePoint(corner.getPoint()); // set the
																// source point
																// to the first
																// point where
																// angle is
					this.setSourceRange(10);
				} else if ((corner.getIndex() > startIndex + size * 2 / 3)
						&& (this.arrow2 == 0)) {
					if (this.arrow1 == 0) {
						this.setDestinationEdgePoint(corner.getPoint()); // set
																			// the
																			// destination
																			// point
																			// to
																			// the
																			// second
																			// point
																			// where
																			// angle
																			// is
						this.setDestinationRange(10);
					} else {
						this.setSourceEdgePoint(corner.getPoint()); // set the
																	// source
																	// point to
																	// the first
																	// point
																	// where
																	// angle is
						this.setSourceRange(10);
					}
				}
			}

			return true;
		}
		return false;
	}

}
// end of class link
