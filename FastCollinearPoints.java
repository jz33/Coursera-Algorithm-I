import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
/**
 * Algs4 HW03
 * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 */
public class FastCollinearPoints {

    private final int dim = 4;
    private Point[] points;
    private ArrayList<LineSegment> segs;

    public FastCollinearPoints(Point[] points) {
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
        segs = new ArrayList<LineSegment>();
        faster();
    }

    private void faster() {
        int size = points.length;
        Point[] newPoints = new Point[size];
        System.arraycopy(points, 0, newPoints, 0, size);
        for (int i = 0; i < size; i++) {
            Point ori = points[i];
            Arrays.sort(newPoints, ori.slopeOrder());

            int ctr = 2;
            Point lt = null;
            Point rt = null;
            double slp = Double.NEGATIVE_INFINITY;
            for (int j = 1; j < size; j++) {
                Point p = newPoints[j];
                double slp2 = p.slopeTo(ori);
                if (slp == slp2) {
                    ctr++;
                    rt = p;
                } else {
                    if (ctr >= dim) {
                        segs.add(new LineSegment(lt, rt));
                    }
                    lt = p;
                    rt = p;
                    ctr = 2;
                    slp = slp2;
                }
            }
            if (ctr >= dim) {
                segs.add(new LineSegment(lt, rt));
            }
        }
    }

    public int numberOfSegments() {
        return segs.size();
    }

    public LineSegment[] segments() {
        LineSegment[] lss = new LineSegment[segs.size()];
        int i = 0;
        for (LineSegment ls : segs) {
            lss[i++] = ls;
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
