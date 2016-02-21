import edu.princeton.cs.algs4.QuickFindUF;

import static java.lang.System.out;
import java.util.HashSet;

public class Percolation{

    boolean mat[][];
    int N;
    QuickFindUF node;

    // First row of opened nodes
    HashSet<Integer> firstRow;
    HashSet<Integer> lastRow;
    
    protected boolean check(int i, int j){
        if(mat == null || node == null){
            throw new NullPointerException();
        }
        if(i > N || j > N){
            throw new IndexOutOfBoundsException();
        }
        if(i <= 0 || j <= 0){
            throw new IllegalArgumentException();
        }
        return true;
    }

    /**
    * map (i,j) on linear coordinate
    * @i: [1,N]
    * @return: [0,N*N-1]
    */
    protected int linearize(int i,int j){
        return (i - 1) * N + j - 1;
    }
    
    /**
    *i,j : [1,N]
    */
    public void open(int i, int j){
        check(i,j);
        mat[i-1][j-1] = true;

        int center = linearize(i,j);

        if(i > 1 && isOpen(i-1,j)){
            node.union(center,linearize(i-1,j));
        }
        if(i < N && isOpen(i+1,j)){
            node.union(center,linearize(i+1,j));
        }
        if(j > 1 && isOpen(i,j-1)){
            node.union(center,linearize(i,j-1));
        }
        if(j < N && isOpen(i,j+1)){
            node.union(center,linearize(i,j+1));
        }

        if(i == 1){
            firstRow.add(center);
        }
        if(i == N){
            lastRow.add(center);
        }
    }
    
    public boolean isOpen(int i, int j){
        check(i,j);
        return mat[i-1][j-1] == true;
    }
    
    public boolean isFull(int i, int j){
        check(i,j);
        return mat[i-1][j-1] == false;
    }

    public void print(){
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(mat[i][j])
                    out.format("%1d",1);
                else
                    out.format("%1d",0);
            }
            out.println();
        }
        out.println(firstRow);
        out.println(lastRow);
        out.println(node.count());
        out.println(fraction());
        out.println(percolates());
    }

    public double fraction(){
        int opened = 0;
        for(int i = 1;i<=N;i++){
            for(int j = 1;j<=N;j++){
                if(isOpen(i,j)){
                    opened++;
                }
            }
        }
        return (double)opened / ((double)N*N); 
    }
    
    public boolean percolates(){
        for(Integer i : firstRow){
            for(Integer j : lastRow){
                if(node.connected(i,j)){
                    return true;
                }
            }
        }
        return false;
    }

    public void reset(){
        for(int i = 0;i<N;i++){
            for(int j = 0;j<N;j++){
                mat[i][j] = false;
            }
        }
        node = new QuickFindUF(N*N); 
        firstRow.clear();
        lastRow.clear();
    }

    public Percolation(int N) {
        if(N <=0){
            throw new IllegalArgumentException();
        }
        this.N = N;
        mat = new boolean[N][N];
        node = new QuickFindUF(N*N);
        firstRow = new HashSet<Integer>();
        lastRow = new HashSet<Integer>();
    }
    
    public static void main(String[] args) {
        new Percolation(10);
    }
}
