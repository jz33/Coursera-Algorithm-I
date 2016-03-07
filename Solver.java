import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/*
 * http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/MinPQ.html
 */
public class Solver {

    private ArrayList<Board> steps;

    public Solver(Board initial) {
        steps = new ArrayList<Board>();
        if (!compute(initial)) {
            Board newBoard = initial.twin();
            if (!compute(newBoard)) {
                steps.clear();
            }
        }
    }

    private class ComparatorManhanttan implements Comparator<Board> {
        public int compare(Board o1, Board o2) {
            return o1.manhattan() - o2.manhattan();
        }
    }

    private boolean compute(Board initial) {
        steps.clear();

        MinPQ<Board> pq = new MinPQ<Board>(new ComparatorManhanttan());
        pq.insert(initial);
        TreeSet<Board> visited = new TreeSet<Board>(new ComparatorManhanttan());

        while (pq.size() > 0) {
            Board curr = pq.delMin();
            steps.add(curr);
            if (curr.isGoal()) {
                return true;
            }
            visited.add(curr);
            for (Board n : curr.neighbors()) {
                if (!visited.contains(n))
                    pq.insert(n);
            }
        }
        return false;
    }

    public boolean isSolvable() {
        return steps.size() > 0;
    }

    public int moves() {
        if (steps.size() == 0) {
            return -1;
        } else {
            return steps.size() - 1;
        }
    }

    public Iterable<Board> solution() {
        return steps;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
