import java.util.ArrayList;

/*
 * http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 */
public class Board {

    private class Point {
        private int x;
        private int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int dim;
    private int[][] blocks;
    private Point empty;

    public Board(int[][] blocks) {
        if (illegalBlocks(blocks)) {
            throw new NullPointerException();
        }
        dim = blocks.length;
        if (dim != blocks[0].length) {
            throw new NullPointerException();
        }
        this.blocks = deepcopy(blocks, dim);

        loop: {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (blocks[i][j] == 0) {
                        empty = new Point(i, j);
                        break loop;
                    }
                }
            }
        }
    }

    private Board(int[][] blocks, Point empty) {
        if (illegalBlocks(blocks)) {
            throw new NullPointerException();
        }
        dim = blocks.length;
        this.blocks = deepcopy(blocks, dim);
        this.empty = empty;
    }

    private boolean illegalBlocks(int[][] anyBlocks) {
        return anyBlocks == null || anyBlocks.length == 0 || anyBlocks[0].length == 0;
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        int ctr = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int v = blocks[i][j];
                if (v != 0) {
                    int s = (v - 1) / dim; // supposed i
                    if (i != s) {
                        ctr++;
                    } else {
                        s = (v - 1) % dim;
                        if (j != s) {
                            ctr++;
                        }
                    }
                }
            }
        }
        return ctr;
    }

    public int manhattan() {
        int ctr = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int v = blocks[i][j];
                if (v != 0) {
                    int si = (v - 1) / dim; // supposed i
                    int sj = (v - 1) % dim; // supposed j
                    ctr += Math.abs(si - i) + Math.abs(sj - j);
                }
            }
        }
        return ctr;
    }

    public boolean isGoal() {
        if (empty.x != dim - 1 || empty.y != dim - 1)
            return false;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int v = blocks[i][j];
                if (v == 0) {
                    return i == dim - 1 && j == dim - 1;
                } else {
                    int s = (v - 1) / dim; // supposed i
                    if (s != i)
                        return false;
                    s = (v - 1) % dim;
                    if (s != j)
                        return false;
                }
            }
        }
        return true;
    }

    private void swap(int[][] mat, int oi, int oj, int ti, int tj) {
        int t = mat[oi][oj];
        mat[oi][oj] = mat[ti][tj];
        mat[ti][tj] = t;
    }

    private int[][] deepcopy(int[][] mat, int size) {
        int[][] newBlocks = new int[size][];
        for (int i = 0; i < size; i++) {
            newBlocks[i] = new int[size];
            System.arraycopy(mat[i], 0, newBlocks[i], 0, size);
        }
        return newBlocks;
    }

    public Board twin() {
        Board nb = null;
        int ori = blocks[0][0];
        if (ori != 0) {
            if (dim > 1) {
                if (blocks[0][1] != 0) {
                    swap(blocks, 0, 0, 0, 1);
                    nb = new Board(blocks, empty);
                    swap(blocks, 0, 0, 0, 1);
                } else if (blocks[1][0] != 0) {
                    swap(blocks, 0, 0, 1, 0);
                    nb = new Board(blocks, empty);
                    swap(blocks, 0, 0, 1, 0);
                }
            }
        } else {
            if (dim > 1) {
                swap(blocks, 0, 1, 1, 1);
                nb = new Board(blocks, empty);
                swap(blocks, 0, 1, 1, 1);
            }
        }
        return nb;
    }

    public boolean equals(Object y) {
        if (y == null || !(y.getClass() != Board.class)) {
            throw new IllegalArgumentException();
        }
        Board that = (Board) y;
        if (that.dimension() != dimension()) {
            return false;
        }
        int[][] b = that.blocks;
        if (illegalBlocks(b)) {
            throw new IllegalArgumentException();
        }
        int[][] m = this.blocks;
        int d = this.dim;
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (m[i][j] != b[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> arr = new ArrayList<Board>();
        int i = empty.x;
        int j = empty.y;
        if (i + 1 < dim) {
            swap(blocks, i, j, i + 1, j);
            arr.add(new Board(blocks, new Point(i + 1, j)));
            swap(blocks, i, j, i + 1, j);
        }
        if (i - 1 > -1) {
            swap(blocks, i, j, i - 1, j);
            arr.add(new Board(blocks, new Point(i - 1, j)));
            swap(blocks, i, j, i - 1, j);
        }
        if (j + 1 < dim) {
            swap(blocks, i, j, i, j + 1);
            arr.add(new Board(blocks, new Point(i, j + 1)));
            swap(blocks, i, j, i, j + 1);
        }
        if (j - 1 > -1) {
            swap(blocks, i, j, i, j - 1);
            arr.add(new Board(blocks, new Point(i, j - 1)));
            swap(blocks, i, j, i, j - 1);
        }
        return arr;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                sb.append(String.format("%2d ", blocks[i][j]));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
