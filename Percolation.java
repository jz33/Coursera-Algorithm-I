import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private int mat[][]; // recording index
	private int N;
	private WeightedQuickUnionUF node;

	private void check(int i, int j) {
		if (i > N || j > N || i <= 0 || j <= 0) {
			throw new IndexOutOfBoundsException();
		}
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
		if (isOpen(i, j))
			return;
		int center = linearize(i, j);
		int id = center;

		if (i > 1 && isOpen(i - 1, j)) {
			int nid = mat[i - 2][j - 1];
			if (id != nid) {
				node.union(center, linearize(i - 1, j));
				id = node.find(center);
			}
		}
		if (i < N && isOpen(i + 1, j)) {
			int nid = mat[i][j - 1];
			if (id != nid) {
				node.union(center, linearize(i + 1, j));
				id = node.find(center);
			}
		}
		if (j > 1 && isOpen(i, j - 1)) {
			int nid = mat[i - 1][j - 2];
			if (id != nid) {
				node.union(center, linearize(i, j - 1));
				id = node.find(center);
			}
		}
		if (j < N && isOpen(i, j + 1)) {
			int nid = mat[i - 1][j];
			if (id != nid) {
				node.union(center, linearize(i, j + 1));
				id = node.find(center);
			}
		}

		mat[i - 1][j - 1] = id;
	}

	public boolean isOpen(int i, int j) {
		check(i, j);
		return mat[i - 1][j - 1] != -1;
	}

	public boolean isFull(int i, int j) {
		if (!isOpen(i, j))
			return false;
		if (i == 1)
			return true;

		int id = mat[i - 1][j - 1];
		for (int k = 1; k <= N; k++) {
			if (mat[0][k - 1] == id) {
				return true;
			}
		}
		return false;
	}

	public boolean percolates() {
		for (int i = 1; i <= N; i++) {
			if (isFull(N, i)) {
				return true;
			}
		}
		return false;
	}

	private void print() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.format("%2d ", mat[i][j]);
			}
			System.out.println();
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.format("%2d ", node.find(linearize(i + 1, j + 1)));
			}
			System.out.println();
		}
		// System.out.println();
	}

	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		this.N = N;
		mat = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				mat[i][j] = -1;
			}
		}
		node = new WeightedQuickUnionUF(N * N);
	}

	private static void utest(Percolation pc, int N, int x, int y) {
		pc.open(x, y);
		pc.print();
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				int v = pc.isFull(i, j) ? 1 : 0;
				System.out.format("%2d ", v);
			}
			System.out.println();
		}
		System.out.println(pc.percolates());
	}

	public static void main(String[] args) {
		int N = 3;
		Percolation pc = new Percolation(N);
		utest(pc, N, 1, 1);
		utest(pc, N, 1, 2);
		utest(pc, N, 2, 2);
		utest(pc, N, 3, 2);
	}
}
