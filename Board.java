import java.util.ArrayList;
import java.util.Iterator;

public class Board {

    private int dim;
    private int[][] blocks;

    public Board(int[][] blocks) {
        if (illegalBlocks(blocks)) {
            throw new NullPointerException();
        }
        dim = blocks.length;
        if (dim != blocks[0].length) {
            throw new NullPointerException();
        }
        this.blocks = deepcopy(blocks, dim);
    }

    private boolean illegalBlocks(int[][] blocks) {
        return blocks == null || blocks.length == 0 || blocks[0].length == 0;
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
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int v = blocks[i][j] - 1;
                if (v == -1) {
                    return i == dim - 1 && j == dim - 1;
                } else {
                    int s = v / dim; // supposed i
                    if (s != i) {
                        return false;
                    }
                    s = v % dim;
                    if (s != j) {
                        return false;
                    }
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

    private int[][] deepcopy(int[][] blocks, int dim) {
        int[][] newBlocks = new int[dim][];
        for (int i = 0; i < dim; i++) {
            newBlocks[i] = new int[dim];
            System.arraycopy(blocks[i], 0, newBlocks[i], 0, dim);
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
                    nb = new Board(blocks);
                    swap(blocks, 0, 0, 0, 1);
                } else if (blocks[1][0] != 0) {
                    swap(blocks, 0, 0, 1, 0);
                    nb = new Board(blocks);
                    swap(blocks, 0, 0, 1, 0);
                }
            }
        } else {
            if (dim > 1) {
                swap(blocks, 0, 1, 1, 1);
                nb = new Board(blocks);
                swap(blocks, 0, 1, 1, 1);
            }
        }
        return nb;
    }

    public boolean equals(Object y) {
        if (y == null || !(y instanceof Board)) {
            throw new IllegalArgumentException();
        }
        Board that = (Board) y;
        if(that.dimension() != dimension()){
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

    private ArrayList<Board> getNeighbors() {
        ArrayList<Board> al = new ArrayList<Board>();
        loop: {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    int v = blocks[i][j];
                    if (v == 0) {
                        if (i + 1 < dim) {
                            swap(blocks, i, j, i + 1, j);
                            al.add(new Board(blocks));
                            swap(blocks, i, j, i + 1, j);
                        }
                        if (i - 1 > -1) {
                            swap(blocks, i, j, i - 1, j);
                            al.add(new Board(blocks));
                            swap(blocks, i, j, i - 1, j);
                        }
                        if (j + 1 < dim) {
                            swap(blocks, i, j, i, j + 1);
                            al.add(new Board(blocks));
                            swap(blocks, i, j, i, j + 1);
                        }
                        if (j - 1 > -1) {
                            swap(blocks, i, j, i, j - 1);
                            al.add(new Board(blocks));
                            swap(blocks, i, j, i, j - 1);
                        }
                        break loop;
                    }
                }
            }
        }
        return al;
    }

    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {

                    private ArrayList<Board> al = getNeighbors();
                    private int i = 0;

                    @Override
                    public boolean hasNext() {
                        return i < al.size();
                    }

                    @Override
                    public Board next() {
                        return al.get(i++);
                    }
                };
            }
        };
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                sb.append(String.format("%3d", blocks[i][j]));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
