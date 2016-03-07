import java.util.ArrayList;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/*
 * http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
 */
public class PointSET {

	private TreeSet<Point2D> ts;

	public PointSET() {
		ts = new TreeSet<Point2D>();
	}

	public boolean isEmpty() {
		return ts.size() == 0;
	}

	public int size() {
		return ts.size();
	}

	private void checkNull(Object p) {
		if (p == null)
			throw new NullPointerException();
	}

	public void insert(Point2D p) {
		checkNull(p);
		ts.add(p);
	}

	public boolean contains(Point2D p) {
		checkNull(p);
		return ts.contains(p);
	}

	public void draw() {
		for (Point2D s : ts)
			s.draw();
	}

	public Iterable<Point2D> range(RectHV rect) {
		checkNull(rect);
		ArrayList<Point2D> arr = new ArrayList<Point2D>();
		for (Point2D s : ts) {
			if (rect.contains(s)) {
				arr.add(s);
			}
		}
		return arr;
	}

	public Point2D nearest(Point2D p) {
		checkNull(p);
		Point2D minPoint = null;
		double minDist = Double.MAX_VALUE;
		for (Point2D s : ts) {
			double dist = s.distanceTo(p);
			if (dist < minDist) {
				minDist = dist;
				minPoint = s;
			}
		}
		return minPoint;
	}
}
