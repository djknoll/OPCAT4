package extensionTools.hio;

import java.awt.Point;
import java.util.Vector;

/**
 * <p>
 * Title: class ShapeSmoother
 * </p>
 * <p>
 * Description: handles the cleaning (at the beginning or at the end) of the
 * drawn shape
 * </p>
 */
public class ShapeSmoother {
	/**
	 * private Vector segments will contain the segments we want to clean
	 */
	private Vector segments; // will contain the segments we want to clean

	/**
	 * private Vector points will contain the points of the segments we want to
	 * clean
	 */
	private Vector points; // will contain the points of the segments we want

	// to clean

	/**
	 * constructor of ShapeSmoother
	 * 
	 * @param segs
	 *            the collection of segments to clean
	 * @param ps
	 *            points vector
	 */
	public ShapeSmoother(Vector segs, Vector ps) {
		this.segments = segs;
		this.points = ps;
	}

	/**
	 * the function closeShape completes the missing line between the closest
	 * points in the edges of a one segment shape and cut the remains from that
	 * point
	 * 
	 * @param minLen
	 *            the distance between the nearest points at start and end area
	 * @param segIndex
	 *            the index of the segment we close in segments
	 * @param cutIndex1
	 *            the index in points from which we start the closing of the
	 *            segment
	 * @param cutIndex2
	 *            the index in points from which we end the closing of the
	 *            segment
	 */
	private void closeShape(double minLen, int segIndex, int cutIndex1,
			int cutIndex2) {
		Point p1 = new Point(0, 0);
		Point p2 = new Point(0, 0);
		Segment mySeg = (Segment) this.segments.elementAt(segIndex);
		int seglen = mySeg.getNumOfPoints(); // the number of points in
		// segment
		int start = mySeg.getStartIndex(); // the start index of the segments
		// in points
		int end = start + seglen, i = 0, numRemoved = 0;

		if (minLen <= DrawAppNew.findLength(start, end) / 10) { // the distance
			// between the
			// points to
			// connect lower
			// then 1/8 of
			// the shape

			for (i = start; i < cutIndex1; i++) {
				this.points.removeElementAt(start);

			}
			// update the variables after removing the points
			numRemoved = cutIndex1 - start;
			cutIndex2 -= numRemoved;
			end -= numRemoved;

			// remove the points beyond the cut point at the second edge
			for (i = cutIndex2; i < end; i++) {
				this.points.removeElementAt(cutIndex2);
			}
			numRemoved += end - cutIndex2; // update the variables after
			// removing the points
			end = cutIndex2;

			// add the missing points if didnt find intersection
			int flag = 0, numAdded = 0; // the flag indicates the progress
			// direction
			p1.setLocation((Point) this.points.elementAt(end - 1));
			p2.setLocation((Point) this.points.elementAt(start));
			int distX = Math.abs((int) p1.getX() - (int) p2.getX());// the
			// distance
			// between
			// p1 and p2
			// in the x
			// axis
			int distY = Math.abs((int) p1.getY() - (int) p2.getY());// the
			// distance
			// between
			// p1 and p2
			// in the y
			// axis
			if (distX > distY) {// if the distance in x axis bigger
				if (p1.getX() > p2.getX()) { // progress is in x direction
					// and the value of x decreasing
					flag = 1;
				}
			} else {// if the distance in y axis bigger
				if (p1.getY() > p2.getY()) {
					flag = 2; // progress is in y direction and the value of y
					// decreasing
				} else {
					flag = 3; // progress is in y direction and the value of y
					// raising
				}
			}

			switch (flag) {
			case 0: // progress is in x direction and the value of x raising
				// adds the missing points
				numAdded = DrawAppNew.addLinePoints((int) p1.getX(), (int) p2
						.getX(), (int) p1.getY(), (int) p2.getY(), start, flag);
				break;

			case 1: // progress is in x direction and the value of x decreasing
				// adds the missing points
				numAdded = DrawAppNew.addLinePoints((int) p2.getX(), (int) p1
						.getX(), (int) p2.getY(), (int) p1.getY(), start, flag);
				break;

			case 2: // progress is in y direction and the value of y decreasing
				// adds the missing points
				numAdded = DrawAppNew.addLinePoints((int) p2.getY(), (int) p1
						.getY(), (int) p2.getX(), (int) p1.getX(), start, flag);
				break;

			case 3: // progress is in y direction and the value of y raising
				// adds the missing points
				numAdded = DrawAppNew.addLinePoints((int) p1.getY(), (int) p2
						.getY(), (int) p1.getX(), (int) p2.getX(), start, flag);
				break;
			}

			mySeg.setNumOfPoints(seglen - numRemoved + numAdded); // update
			// the
			// segment
			// after the
			// cutting
			DrawAppNew.updateSegments(segIndex, numRemoved - numAdded);
			DrawAppNew.updateSegRectBorders(segIndex);// update the borders of
			// the closing rectangle
			// of the segment
		}
	}

	/**
	 * the function connectSegs connects 2 segments in the shape according to
	 * the appropriate order to one segment
	 * 
	 * @param fSeg
	 *            the index of the segment we want to connect to the follow up
	 *            segment
	 * @param sSeg
	 *            the index of the second segment in the shape
	 * @param numofseg
	 *            number of segments in the shape
	 */
	private void connectSegs(int fSeg, int sSeg, int numofseg) {
		Segment seg1 = (Segment) this.segments.elementAt(fSeg);// the first segment
		// to connect
		Segment seg2 = (Segment) this.segments.elementAt(sSeg);// the second
		// segment to
		// connect
		int seg1Iterator = 0, seg2Iterator = 0;
		int seg1Len = seg1.getNumOfPoints(), seg2Len = seg2.getNumOfPoints();// the
		// number
		// of
		// points
		// of
		// the
		// segments
		int seg1Start = seg1.getStartIndex(), seg2Start = seg2.getStartIndex();
		int distFromStart = Integer.MAX_VALUE, len = Integer.MAX_VALUE, distFromEnd = Integer.MAX_VALUE;
		int cutIndex1 = 0, cutIndex2 = 0;
		int whichEnds = 0; // flag that indicates the direction of the segment

		// check what ends they connect
		if (!seg1.isFromStart()) {// if the direction of the segment is not
			// the same as the first segment in the
			// global shape
			whichEnds++;
		}
		if (!seg2.isFromStart()) {// if the direction of the segment is not
			// the same as the first segment in the
			// global shape
			whichEnds += 10;
		}
		Point p1 = new Point(0, 0);
		Point p2 = new Point(0, 0);

		switch (whichEnds) {
		case 0: // if both segments are connected from start to end
		case 1: // if only the first segment is connected from end to start
			p2.setLocation((Point) this.points.elementAt(seg2Start));
			break;
		case 10: // if only the second segment is connected from end to start
		case 11: // if both segments are connected from end to start
			p2.setLocation((Point) this.points.elementAt(seg2Start + seg2Len - 1));
		}

		// check where is the connect points
		for (seg1Iterator = seg1Start + seg1Len - 1; /*
														 * go through all the
														 * points of seg1
														 * starting from the end
														 * of the first segment
														 * and look for the
														 * closest point to
														 * p2-which in this case
														 * is the nearest point
														 * to seg1
														 */

		seg1Iterator > seg1Start; seg1Iterator--) {
			p1.setLocation((Point) this.points.elementAt(seg1Iterator));
			len = (int) p2.distance(p1);
			if (len < distFromStart) {
				distFromStart = len; // updates distFromStart to be the
				// smallest distance between p1 and p2
				cutIndex1 = seg1Iterator; // the index of the closest point to
				// the second segment
			}
		}

		switch (whichEnds) {
		case 0: // if both segments are connected from start to end
			p1.setLocation((Point) this.points.elementAt(seg1Start + seg1Len - 1));// p1
			// is
			// the
			// end
			// point
			break;
		case 1:// if only the first segment is connected from end to start
			p1.setLocation((Point) this.points.elementAt(seg1Start));
			break;
		case 10: // if only the second segment is connected from end to start
			p1.setLocation((Point) this.points.elementAt(seg1Start + seg1Len - 1));
			break;
		case 11:// if both segments are connected from end to start
			p1.setLocation((Point) this.points.elementAt(seg1Start));
		}
		len = Integer.MAX_VALUE;

		for (seg2Iterator = seg2Start; seg2Iterator < seg2Start + seg2Len; /*
																			 * go
																			 * through
																			 * all
																			 * the
																			 * points
																			 * of
																			 * seg2
																			 * starting
																			 * from
																			 * the
																			 * start
																			 * of
																			 * the
																			 * second
																			 * segment
																			 * and
																			 * look
																			 * for
																			 * the
																			 * closest
																			 * point
																			 * to
																			 * p1-which
																			 * in
																			 * this
																			 * case
																			 * is
																			 * the
																			 * nearest
																			 * point
																			 * to
																			 * seg2
																			 */
		seg2Iterator++) {
			p2.setLocation((Point) this.points.elementAt(seg2Iterator));
			len = (int) p1.distance(p2);
			if (len < distFromEnd) {

				distFromEnd = len; // updates distFromEnd to be the smallest
				// distance between p1 and p2
				cutIndex2 = seg2Iterator; // the index of the closest point to
				// the first segment
			}
		}

		// cut extra points where needed
		if (distFromEnd > distFromStart) { // need to cut all the remain points
			// from cutIndex1
			int numRemoved = this.cutSeg(fSeg, cutIndex1);
			DrawAppNew.updateSegments(fSeg, numRemoved); // updates segments
			// from seg index
		} else { // need to cut all the remain points from cutIndex2
			int numRemoved = this.cutSeg(sSeg, cutIndex2);
			DrawAppNew.updateSegments(sSeg, numRemoved); // updates segments
			// from secondSeg
			// index
		}
		// get the new data
		seg1Len = seg1.getNumOfPoints();
		seg2Len = seg2.getNumOfPoints();
		seg1Start = seg1.getStartIndex();
		seg2Start = seg2.getStartIndex();

		// connect the segments
		switch (whichEnds) { // updates p1 and p2 to be the points that needs
		// to be connected
		case 0:
			p1.setLocation((Point) this.points.elementAt(seg1Start + seg1Len - 1));
			p2.setLocation((Point) this.points.elementAt(seg2Start));
			break;
		case 1:
			p1.setLocation((Point) this.points.elementAt(seg1Start));
			p2.setLocation((Point) this.points.elementAt(seg2Start));
			break;
		case 10:
			p1.setLocation((Point) this.points.elementAt(seg1Start + seg1Len - 1));
			p2.setLocation((Point) this.points.elementAt(seg2Start + seg2Len - 1));
			break;
		case 11:
			p1.setLocation((Point) this.points.elementAt(seg1Start));
			p2.setLocation((Point) this.points.elementAt(seg2Start + seg2Len - 1));
		}

		int seg1end = seg1Start;
		switch (whichEnds) { // seg1end is index from which we start to
		// connect the sequential segments
		case 0:
			seg1end = seg1Start + seg1Len;
			break;
		case 1:
			seg1end = seg1Start;
			break;
		case 10:
			seg1end = seg1Start + seg1Len;
			break;
		case 11:
			seg1end = seg1Start;
		}

		int flag = 0, numadded = 0;// flag that indicates the progress
		// direction

		int distX = Math.abs((int) p1.getX() - (int) p2.getX()); // the
		// distance
		// between
		// p1 and p2
		int distY = Math.abs((int) p1.getY() - (int) p2.getY());
		if ((distX > seg1Len / 8) || (distY > seg1Len / 8)) {
			return; // if its an open arrow dont need to connect
		}
		if (distX > distY) {
			if (p1.getX() > p2.getX()) { // progress is in x direction and
				// the value of x decreasing
				flag = 1;
			}
		} else {
			if (p1.getY() > p2.getY()) {
				flag = 2; // progress is in y direction and the value of y
				// decreasing
			} else {
				flag = 3; // progress is in y direction and the value of y
				// raising
			}
		}

		switch (flag) { // adding the missing points
		case 0: // progress is in x direction and the value of x raising
			numadded = DrawAppNew.addLinePoints((int) p1.getX(), (int) p2
					.getX(), (int) p1.getY(), (int) p2.getY(), seg1end, flag);
			break;

		case 1:// progress is in x direction and the value of x decreasing
			numadded = DrawAppNew.addLinePoints((int) p2.getX(), (int) p1
					.getX(), (int) p2.getY(), (int) p1.getY(), seg1end, flag);
			break;

		case 2: // progress is in y direction and the value of y decreasing
			numadded = DrawAppNew.addLinePoints((int) p2.getY(), (int) p1
					.getY(), (int) p2.getX(), (int) p1.getX(), seg1end, flag);
			break;

		case 3:// progress is in y direction and the value of y raising
			numadded = DrawAppNew.addLinePoints((int) p1.getY(), (int) p2
					.getY(), (int) p1.getX(), (int) p2.getX(), seg1end, flag);
			break;
		}
		// update the segments data
		DrawAppNew.updateSegments(fSeg, -numadded);
		seg1.setNumOfPoints(seg1.getNumOfPoints() + numadded);
		this.segments.set(fSeg, seg1);
	}

	/**
	 * the function cutOneSegShape cut the edges of a shape that is drawn by one
	 * segment only
	 * 
	 * @param segIndex
	 *            the index of the segment to cut
	 */
	private void cutOneSegShape(int segIndex) {
		Segment mySeg = (Segment) this.segments.elementAt(segIndex);// the segment
		// to cut
		int seglen = mySeg.getNumOfPoints() - 1;// the number of points in the
		// segment
		int start = mySeg.getStartIndex();
		int end = start + seglen;
		double len = Integer.MAX_VALUE, minLen = Integer.MAX_VALUE;
		int cutIndex1 = 0, cutIndex2 = 0, numRemoved = 0;
		Point p1 = new Point(0, 0);// p1 and p2 are the closest points in the
		// segment(one from start and other from
		// end)
		Point p2 = new Point(0, 0);
		int endIterator = 0, startIterator = 0;
		int i = 0;
		// look for cut point at the start of the segment
		for (startIterator = start; startIterator < start + seglen / 8; startIterator++) {

			p1.setLocation((Point) this.points.elementAt(startIterator));
			// look for cut point at the end of the segment
			for (endIterator = end; endIterator > start + seglen * 7 / 8; endIterator--) {
				p2.setLocation((Point) this.points.elementAt(endIterator));

				len = p1.distance(p2);// distance between p1 and p2
				if (len < minLen) {
					minLen = len;// min len is the minimum length between p1
					// and p2
					cutIndex1 = startIterator;
					cutIndex2 = endIterator;
				}
				if (len <= Math.sqrt(2)) { // found cut point
					// remove the points beyond the cut point at one edge
					for (i = start; i <= startIterator; i++) {
						this.points.removeElementAt(start);
					}
					numRemoved = startIterator - start + 1;

					endIterator -= numRemoved;// update the endIterator
					end -= numRemoved;
					// remove the points beyond the cut point at the second edge
					for (i = endIterator; i <= end; i++) {
						this.points.removeElementAt(endIterator);
					}
					numRemoved += end - endIterator + 1;

					mySeg.setNumOfPoints(seglen + 1 - numRemoved); // update
					// the
					// segment
					// after the
					// cutting
					this.segments.set(segIndex, mySeg);
					DrawAppNew.updateSegments(segIndex, numRemoved);

					DrawAppNew.updateSegRectBorders(segIndex);

					return;
				}
			}
		}
		// if shape doesnt close then close it
		if (minLen / seglen < 0.1) {
		}
		this.closeShape(minLen, segIndex, cutIndex1, cutIndex2); // dont need to
		// update rect
		// borders
	}

	/**
	 * the function cutSeg cut the edge of a segment and update the vector
	 * segments
	 * 
	 * @param segindex
	 *            the segment index in the vector
	 * @param segCutPoint
	 *            the index of the cut point in points
	 * @return the number of points that was removed from the segment
	 */
	private int cutSeg(int segindex, int segCutPoint) {
		Segment seg = (Segment) this.segments.elementAt(segindex);// the segment
		// to cut
		int segLen = seg.getNumOfPoints();// segment number of points
		int segStart = seg.getStartIndex();
		int firstPartLen = DrawAppNew.findLength(segStart, segCutPoint);// the
		// length
		// before
		// cut
		// point
		int secondPartLen = DrawAppNew.findLength(segCutPoint, segStart
				+ segLen);// the length after cut point

		if (firstPartLen <= secondPartLen) { // remove points from start
			for (int i = segStart; i < segCutPoint; i++) {
				this.points.removeElementAt(segStart);
			}
			seg.setNumOfPoints(segStart + segLen - segCutPoint);// update the
			// segment data
			this.segments.set(segindex, seg);
		} else { // remove points from cut point
			for (int i = segCutPoint; i < segStart + segLen; i++) {
				this.points.removeElementAt(segCutPoint);
			}
			seg.setNumOfPoints(segCutPoint - segStart);// update the segment
			// data
			this.segments.set(segindex, seg);
		}
		return segLen - seg.getNumOfPoints();
	}

	/**
	 * the function cutShape smooth the shape according to the number of
	 * segments the shape has
	 * 
	 * @param headNum
	 *            flag that can get the values 0 if its close shape, 1 if its
	 *            the first arrow head and 2 if its the second arrow head
	 */
	void cutShape(int headNum) {
		Vector relevantSegs = this.initRelevantSegs(headNum);// vector of the
		// segments we want to
		// cut their edges

		int start = ((Integer) relevantSegs.elementAt(0)).intValue();// the
		// index
		// of
		// the
		// first
		// relevant
		// segment
		// at
		// segments
		int numofseg = relevantSegs.size();// number of segments in the shape
		// we want to smooth

		if (numofseg == 1) { // if its only one segment to cut we call
			// cutOneSegShape
			this.cutOneSegShape(start);
			return;
		} else { // more then one segment
			int minDist = Integer.MAX_VALUE;
			if (numofseg == 2) {
				this.cutTwoSeg(start, ((Integer) relevantSegs.elementAt(1))
						.intValue(), minDist); // cuts only one side of the
				// shape

				this.cutTwoSegShape(start, ((Integer) relevantSegs.elementAt(1))
						.intValue()); // cuts the other side
				if ((headNum == 1) || (headNum == 2)) {// if the shape is an arrow
					// head we need to separate
					// again to heads after the
					// cutting
					DrawAppNew.link.separateArrowHeads();
				}
			} else { // more then two segments
				int iSeg = start, jSeg = start;
				for (int i = 0; i < numofseg; i++) { // go throw all pairs of
					// segments
					minDist = Integer.MAX_VALUE;
					iSeg = ((Integer) relevantSegs.elementAt(i)).intValue();
					for (int j = i + 1; j < numofseg; j++) {
						jSeg = ((Integer) relevantSegs.elementAt(j)).intValue();
						minDist = this.cutTwoSeg(iSeg, jSeg, minDist); // cuts the
						// current
						// pair
					}
					if ((headNum == 1) || (headNum == 2)) {// if we changed the
						// arrow heads we need
						// to separate again for
						// two heads
						DrawAppNew.link.separateArrowHeads();
					}
				}
			}

			relevantSegs = this.initRelevantSegs(headNum);// after the cutting we
			// need to find again
			// the relevant segments
			// because we separated again the arrow heads to different index
			// union 2 following segments
			for (int i = 0; i < numofseg - 2; i++) {
				int firstSeg = ((Integer) relevantSegs.elementAt(i)).intValue();
				int secondSeg = ((Integer) relevantSegs.elementAt(i + 1))
						.intValue(); // this isnt the updated seg
				this.connectSegs(firstSeg, secondSeg, numofseg); // connects the
				// segments
				// according to the
				// right order

			}
			// union the last segment with the first
			this.connectSegs(((Integer) relevantSegs.elementAt(numofseg - 1))
					.intValue(), ((Integer) relevantSegs.elementAt(0))
					.intValue(), numofseg);

			EdgeValues ev = new EdgeValues();
			Vector newPoints = this.updatePointsByOrder(relevantSegs, ev); // creats
			// a new
			// ordered
			// points
			// vector
			// from
			// "start"
			// remove the old points segment by segment
			for (int j = 0; j < relevantSegs.size(); j++) {
				int relevantSegIndex = ((Integer) relevantSegs.elementAt(j))
						.intValue();
				Segment relevantSeg = (Segment) this.segments
						.elementAt(relevantSegIndex);
				for (int i = 0; i < relevantSeg.getNumOfPoints(); i++) {
					this.points.removeElementAt(relevantSeg.getStartIndex()); // remove
					// the
					// points
					// from
					// "start"
					// from
					// vector
					// points
				}
				DrawAppNew.updateSegments(relevantSegIndex, relevantSeg
						.getNumOfPoints());
			}

			// create new segment for head
			Segment newSeg = new Segment(this.points.size(), newPoints.size(), ev);

			// remove the head segments from vector segments
			for (int i = 0; i < relevantSegs.size(); i++) {
				int nextSegIndex = ((Integer) relevantSegs.elementAt(i))
						.intValue();
				this.segments.removeElementAt(nextSegIndex); // remove the next
				// segment after start

				for (int j = i; j < relevantSegs.size(); j++) {
					int segValue = ((Integer) relevantSegs.elementAt(j))
							.intValue();
					if (segValue > nextSegIndex) {
						Integer newSegValue = new Integer(segValue - 1);
						relevantSegs.setElementAt(newSegValue, j);
					}
				}
			}

			this.segments.add(newSeg);
			start = this.segments.size() - 1;
			for (int i = 0; i < newPoints.size(); i++) {
				this.points.add(this.points.size(), newPoints.elementAt(i)); // adds the
				// new
				// ordered
				// points to
				// vector
				// points
			}
		}
	}

	/**
	 * the function initRelevantSegs
	 * 
	 * @param headNum
	 *            flag that can get the values 0 if its close shape, 1 if its
	 *            the first arrow head
	 * @return the relevant segments of the head
	 */
	private Vector initRelevantSegs(int headNum) {
		Vector relevantSegs = Link.secondArrowHead;
		switch (headNum) {
		case 0:// close shape, relevantSegs contains all the segments of the
			// shape
			relevantSegs = new Vector();
			// update relevantSegs
			for (int i = 0; i < this.segments.size(); i++) {
				Integer segI = new Integer(i);
				relevantSegs.addElement(segI);
			}
			break;
		case 1:// update relevantSegs to be the first arrow head
			relevantSegs = Link.firstArrowHead;
			break;
		case 2:// relevantSegs are allready the second arrow head
		}

		return relevantSegs;

	}

	/**
	 * the function cutTwoSeg cut the edges beyond the cross point of two
	 * segments in a close shape
	 * 
	 * @param firstSeg
	 *            the first segment to cut
	 * @param secondSeg
	 *            the second segment to cut
	 * @param minDist
	 *            the min distance between all the segments of the shape
	 * @return the min distance between the segments
	 */
	private int cutTwoSeg(int firstSeg, int secondSeg, int minDist) {
		Segment seg1 = (Segment) this.segments.elementAt(firstSeg);// the first
		// segment to
		// cut
		Segment seg2 = (Segment) this.segments.elementAt(secondSeg);// the second
		// segment to
		// cut
		int seg1Iterator = 0, seg2Iterator = 0;// iterator that contains index
		// of optional cut point
		int seg1Len = seg1.getNumOfPoints(), seg2Len = seg2.getNumOfPoints();
		int seg1Start = seg1.getStartIndex(), seg2Start = seg2.getStartIndex();

		Point p1 = new Point(0, 0);// will hold the cut point
		Point p2 = new Point(0, 0);

		Point endP = new Point(0, 0); // will be the end point of the first
		// seg according to the right direction
		int nextSeg = secondSeg, len = minDist;
		// find the end point of seg1
		for (seg1Iterator = seg1Start; seg1Iterator < seg1Start + seg1Len; seg1Iterator++) {
			if (seg1.fromStart == true) { // if the first segment from start
				// to end
				endP = (Point) this.points.elementAt(seg1Start + seg1Len - 1);
			} else { // if the first segment from end to start
				endP = (Point) this.points.elementAt(seg1Start);

			}
			p1.setLocation((Point) this.points.elementAt(seg1Iterator));
			// look for cut point
			for (seg2Iterator = seg2Start; seg2Iterator < seg2Start + seg2Len; seg2Iterator++) {
				p2.setLocation((Point) this.points.elementAt(seg2Iterator));

				len = (int) endP.distance(p2); // distance between the last
				// point in the first segment to
				// current point in second
				// segment
				if (len < minDist) { // if the distance smaller then the
					// smallest distance
					minDist = len;
					if (seg2Iterator - seg2Start > seg2Start + seg2Len
							- seg2Iterator) { // set the second segment in the
						// right direction
						((Segment) this.segments.elementAt(secondSeg))
								.setFromStart(false);
					} else {
						((Segment) this.segments.elementAt(secondSeg))
								.setFromStart(true);
					}
					if (nextSeg > firstSeg + 1) {
						nextSeg = firstSeg + 1;
					}
				}
				double dist = p1.distance(p2);
				if (dist < Math.sqrt(2)) { // found cut point

					this.updateSegmentsForCut(firstSeg, secondSeg, seg1Iterator,
							seg2Iterator);
					// cut the segments and updates the segments after cutting
					// both of them
					if (nextSeg != secondSeg) {
						seg2 = (Segment) this.segments.elementAt(secondSeg);
						this.segments.removeElementAt(secondSeg); // put the next
						// segment after
						// the first one
						this.segments.add(nextSeg, seg2);
					}
					return minDist;
				}
			}
		}
		if (nextSeg != secondSeg) {
			this.segments.removeElementAt(secondSeg); // put the next segment
			// after the first one
			this.segments.add(nextSeg, seg2);
		}
		return minDist;
	}

	/**
	 * the function cutTwoSegShape cut the edges beyond the cross point of two
	 * segments shape in a close shape
	 * 
	 * @param firstSeg
	 *            the first segment of the shape
	 * @param secondSeg
	 *            the second segment of the shape
	 */
	private void cutTwoSegShape(int firstSeg, int secondSeg) {
		Segment seg1 = (Segment) this.segments.elementAt(firstSeg);// the first
		// segment to
		// cut
		Segment seg2 = (Segment) this.segments.elementAt(secondSeg);// the second
		// segment to
		// cut
		int seg1Iterator = 0, seg2Iterator = 0;// iterator that contains index
		// of optional cut point
		int seg1Len = seg1.getNumOfPoints(), seg2Len = seg2.getNumOfPoints();// the
		// first
		// segment
		// length
		int seg1Start = seg1.getStartIndex(), seg2Start = seg2.getStartIndex();// the
		// second
		// segment
		// length

		Point p1 = new Point(0, 0);// will hold the cut point
		Point p2 = new Point(0, 0);
		// go through half the shape and look for cut point
		for (seg1Iterator = seg1Start + seg1Len - 1; seg1Iterator > seg1Start
				+ seg1Len / 2; seg1Iterator--) { // go through half the shape
			p1.setLocation((Point) this.points.elementAt(seg1Iterator));
			if (seg2.fromStart == false) { // if the segment drawing diraction
				// is not from start direction
				for (seg2Iterator = seg2Start + seg2Len - 1; seg2Iterator >= seg2Start; seg2Iterator--) {
					p2.setLocation((Point) this.points.elementAt(seg2Iterator));
					double dist = p1.distance(p2);
					if (dist < Math.sqrt(2)) { // found cut point
						this.updateSegmentsForCut(firstSeg, secondSeg, seg1Iterator,
								seg2Iterator);// cut the segments
						return;
					}
				}
			} else { // if the segment drawing direction is from start
				// direction
				for (seg2Iterator = seg2Start; seg2Iterator < seg2Start
						+ seg2Len; seg2Iterator++) {
					p2.setLocation((Point) this.points.elementAt(seg2Iterator));
					double dist = p1.distance(p2);
					if (dist < Math.sqrt(2)) { // found cut point
						this.updateSegmentsForCut(firstSeg, secondSeg, seg1Iterator,
								seg2Iterator);// cut the segments
						return;
					}
				}
			}
		}
	}

	/**
	 * the function unionTOneSeg union and smooth an arrow head
	 * 
	 * @param head
	 *            the arrow head to union
	 */
	protected void unionTOneSeg(Vector head) {

		if (head.size() > 0) {

			int headNum = 1; // flag that indicates if union arrow head or
			// close shape gets 1 if its the first arrow
			// head
			if (head.equals(Link.secondArrowHead)) {
				headNum = 2; // get 2 if we union the second head
			}
			this.cutShape(headNum);
		}
	}

	/**
	 * the function updatePointsByOrder creates a new ordered points vector
	 * according to the fromStart flag
	 * 
	 * @param relevantSegs
	 *            a vector which holds the relevant segments for uniting and
	 *            orgenazing by order
	 * @param ev
	 *            the EdgeValues variable for the relevant points
	 * @return the new organized points vector
	 */
	private Vector updatePointsByOrder(Vector relevantSegs, EdgeValues ev) {

		Segment tempSeg;
		int tempSegIndex = 0;
		Vector newPoints = new Vector(); // will contain the new points
		// vector
		// go trough all relevant segments and create new ordered points vector
		for (int i = 0; i < relevantSegs.size(); i++) {
			tempSegIndex = ((Integer) relevantSegs.elementAt(i)).intValue();
			tempSeg = (Segment) this.segments.elementAt(tempSegIndex);

			int startindex = tempSeg.getStartIndex();
			int endindex = tempSeg.getStartIndex() + tempSeg.getNumOfPoints()
					- 1;
			if (tempSeg.fromStart == false) { // the segment in the opposite
				// direction
				for (int j = endindex; j >= startindex; j--) {
					ev.setEdgePoints((Point) this.points.elementAt(j));// update
					// the edge
					// values of
					// the shape
					newPoints.add(this.points.elementAt(j)); // add the points to the
					// new vector from end
					// to start
				}
			} else {// the segment in the same direction as the first drawn
				// segment
				for (int j = startindex; j <= endindex; j++) {
					ev.setEdgePoints((Point) this.points.elementAt(j));
					newPoints.add(this.points.elementAt(j)); // add the points to the
					// new vector from start
					// to end
				}
			}
		}
		return newPoints;// return the new ordered points vector
	}

	/**
	 * the function updateSegmentsForCut cut the segments and updates segments
	 * after cutting both segments
	 * 
	 * @param firstSeg
	 *            the first segment to cut
	 * @param secondSeg
	 *            the second segment to cut
	 * @param seg1Iterator
	 *            the index of the cutting point in the vector of the first
	 *            segment
	 * @param seg2Iterator
	 *            the index of the cutting point in the vector of the second
	 *            segment
	 */
	private void updateSegmentsForCut(int firstSeg, int secondSeg,
			int seg1Iterator, int seg2Iterator) {
		int segNewLen = this.cutSeg(firstSeg, seg1Iterator);// the segment length
		DrawAppNew.updateSegments(firstSeg, segNewLen);// update vector
		// segments
		if (seg2Iterator > seg1Iterator) {
			seg2Iterator -= segNewLen;
		}
		DrawAppNew.updateSegments(secondSeg, this.cutSeg(secondSeg, seg2Iterator));// update
		// vector
		// segments
	}
}
// ///////////////end of class
