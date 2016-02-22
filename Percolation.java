import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import edu.princeton.cs.algs4.StdOut;
/*
 http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 */
public class Percolation {

    private boolean mat[][];
    private int N;
    private WeightedQuickUnionUF node;

    private boolean check(int i, int j) {
        if (mat == null || node == null) {
            throw new NullPointerException();
        }
        if (i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }
        if (i <= 0 || j <= 0) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    /**
     * map (i,j) on linear coordinate
     * 
     * @i: [1,N]
     * @return: [0,N*N-1]
     */
    private int linearize(int i, int j) {
        return (i - 1) * N + j - 1;
    }

    /**
     * i,j : [1,N]
     */
    public void open(int i, int j) {
        check(i, j);
        mat[i - 1][j - 1] = true;

        int center = linearize(i, j);

        if (i > 1 && isOpen(i - 1, j)) {
            node.union(center, linearize(i - 1, j));
        }
        if (i < N && isOpen(i + 1, j)) {
            node.union(center, linearize(i + 1, j));
        }
        if (j > 1 && isOpen(i, j - 1)) {
            node.union(center, linearize(i, j - 1));
        }
        if (j < N && isOpen(i, j + 1)) {
            node.union(center, linearize(i, j + 1));
        }
    }

    public boolean isOpen(int i, int j) {
        check(i, j);
        return mat[i - 1][j - 1] == true;
    }

    public boolean isFull(int i, int j) {
        check(i, j);
        int tag = linearize(i, j);
        for (int k = 1; k <= N; k++) {
            if (isOpen(1, k)) {
                if (node.connected(linearize(1, k), tag)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * public void print(){ for(int i=0;i<N;i++){ for(int j=0;j<N;j++){
     * if(mat[i][j]) StdOut.format("%1d",1); else StdOut.format("%1d",0); }
     * StdOut.println(); } StdOut.println(node.count());
     * StdOut.println(fraction()); StdOut.println(percolates()); }
     */

    public boolean percolates() {
        for (int i = 1; i <= N; i++) {
            if (isFull(N, i)) {
                return true;
            }
        }
        return false;
    }

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        mat = new boolean[N][N];
        node = new WeightedQuickUnionUF(N * N);
    }

    public static void main(String[] args) {
        new Percolation(10);
    }
}
