import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/*
 * http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
 */
public class KdTree {

	private class Node {
		private Point2D val;
		private Node left;
		private Node right;
		private boolean vertical;

		private Node(Point2D val, boolean vertical) {
			this.val = val;
			this.vertical = vertical;
			this.left = null;
			this.right = null;
		}

		private void insert(Point2D p) {
			if ((vertical && p.x() <= val.x())
					|| (!vertical && p.y() <= val.y())) {
				if (left != null) {
					left.insert(p);
				} else {
					left = new Node(p, !vertical);
				}
			} else {
				if (right != null) {
					right.insert(p);
				} else {
					right = new Node(p, !vertical);
				}
			}
		}

		private boolean contains(Point2D p) {
			if (val.equals(p)) {
				return true;
			}
			if ((vertical && p.x() <= val.x())
					|| (!vertical && p.y() <= val.y())) {
				return left != null && left.contains(p);
			} else {
				return right != null && right.contains(p);
			}
		}

		private void draw(RectHV rect) {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.point(val.x(), val.y());
			if (vertical) {
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(val.x(), val.y(), val.x(), rect.ymin());
				StdDraw.line(val.x(), val.y(), val.x(), rect.ymax());
				if (left != null) {
					left.draw(new RectHV(rect.xmin(), rect.ymin(), val.x(),
							rect.ymax()));
				}
				if (right != null) {
					right.draw(new RectHV(val.x(), rect.ymin(), rect.xmax(),
							rect.ymax()));
				}
			} else {
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.line(val.x(), val.y(), rect.xmin(), val.y());
				StdDraw.line(val.x(), val.y(), rect.xmax(), val.y());
				if (left != null) {
					left.draw(new RectHV(rect.xmin(), rect.ymin(), rect.xmax(),
							val.y()));
				}
				if (right != null) {
					right.draw(new RectHV(rect.xmin(), val.y(), rect.xmax(),
							rect.ymax()));
				}
			}
		}

		private void range(RectHV rect, ArrayList<Point2D> arr) {
			if (rect.contains(val)) {
				arr.add(val);
			}
			if ((vertical && rect.xmax() <= val.x())
					|| (!vertical && rect.ymax() <= val.y())) {
				if (left != null)
					left.range(rect, arr);
			} else if ((vertical && rect.xmin() >= val.x())
					|| (!vertical && rect.ymin() >= val.y())) {
				if (right != null)
					right.range(rect, arr);
			} else {
				if (left != null)
					left.range(rect, arr);
				if (right != null)
					right.range(rect, arr);
			}
		}

		private void nearest(Point2D target, ArrayList<Point2D> curr,
				ArrayList<Double> dist) {
			double d = val.distanceTo(target);
			if (d < dist.get(0)) {
				curr.set(0, val);
				dist.set(0, d);
			}
			if (vertical) {
				d = target.x() - val.x();
			} else {
				d = target.y() - val.y();
			}
			if (d <= 0) {
				if (left != null)
					left.nearest(target, curr, dist);
				// could there be a point in right that is nearer?
				if (right != null && -d < dist.get(0)) {
					right.nearest(target, curr, dist);
				}
			} else {
				if (right != null)
					right.nearest(target, curr, dist);
				if (left != null && d < dist.get(0)) {
					left.nearest(target, curr, dist);
				}
			}
		}
	}

	private Node root;
	private int size;

	public KdTree() {
		root = null;
		size = 0;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return size;
	}

	private void checkNull(Object p) {
		if (p == null)
			throw new NullPointerException();
	}

	public void insert(Point2D p) {
		checkNull(p);
		if (root == null)
			root = new Node(p, false);
		else
			root.insert(p);
		size++;
	}

	public boolean contains(Point2D p) {
		checkNull(p);
		return root != null && root.contains(p);
	}

	public void draw() {
		RectHV rec = new RectHV(0, 0, 1, 1);
		if (root != null) {
			root.draw(rec);
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		checkNull(rect);
		ArrayList<Point2D> arr = new ArrayList<Point2D>();
		if (root != null)
			root.range(rect, arr);
		return arr;
	}

	public Point2D nearest(Point2D p) {
		checkNull(p);
		if (root == null)
			return null;
		ArrayList<Point2D> curr = new ArrayList<Point2D>();
		curr.add(p);
		ArrayList<Double> dist = new ArrayList<Double>();
		dist.add(Double.MAX_VALUE);
		root.nearest(p, curr, dist);
		return curr.get(0);
	}
}
