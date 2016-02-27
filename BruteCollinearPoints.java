import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
/**
 * Algs4 HW03
 * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 */
public class BruteCollinearPoints {

    private final int dim = 4;
    private Point[] points;
    private HashMap<Double, ArrayList<Point>> segs;

    public BruteCollinearPoints(Point[] points) {
        if (points == null || points.length < 1) {
            throw new NullPointerException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new NullPointerException();
            }
        }
        for (int i = 0; i < points.length - 1; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                if (p.compareTo(q) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        this.points = points;
        segs = new HashMap<Double, ArrayList<Point>>();
        combinations();
    }

    /**
     * https://docs.python.org/2/library/itertools.html#itertools.combinations
     */
    private void combinations() {
        int repeat = dim;
        int len = points.length;
        int[] inds = new int[repeat];
        int[] rinds = new int[repeat];
        for (int i = 0; i < repeat; i++) {
            inds[i] = i;
            rinds[i] = repeat - i - 1;
        }
        check4(inds);

        int diff = len - repeat;
        while (true) {
            int i;
            loop: {
                for (int r : rinds) {
                    if (inds[r] != r + diff) {
                        i = r;
                        break loop;
                    }
                }
                return;
            }
            inds[i]++;
            for (int j = i + 1; j < repeat; j++) {
                inds[j] = inds[j - 1] + 1;
            }
            check4(inds);
        }
    }

    /**
     * check whether inds[i] (i = 0,1,2,3) are inline
     * 
     * @param inds
     */
    private void check4(int[] inds) {
        Point[] ps = new Point[dim];
        for (int i = 0; i < dim; i++) {
            ps[i] = points[inds[i]];
        }
        Arrays.sort(ps);
        Point p0 = ps[0];
        Point p3 = ps[3];
        double slp = p0.slopeTo(p3);
        if (slp == p0.slopeTo(ps[1]) && slp == p0.slopeTo(ps[2])) {
            ArrayList<Point> prev = segs.get(slp);
            if (prev == null) {
                segs.put(slp, new ArrayList<Point>(Arrays.asList(p0, p3)));
            } else {
                Point prev_lt = prev.get(0);
                Point prev_rt = prev.get(1);
                Point lt = prev_lt.compareTo(p0) < 0 ? prev_lt : p0;
                Point rt = prev_rt.compareTo(p3) > 0 ? prev_rt : p3;
                segs.put(slp, new ArrayList<Point>(Arrays.asList(lt, rt)));
            }
        }
    }

    public int numberOfSegments() {
        return segs.size();
    }

    public LineSegment[] segments() {
        LineSegment[] lss = new LineSegment[segs.size()];
        int i = 0;
        for (Entry<Double, ArrayList<Point>> ls : segs.entrySet()) {
            ArrayList<Point> pair = ls.getValue();
            lss[i++] = new LineSegment(pair.get(0), pair.get(1));
        }
        return lss;
    }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
