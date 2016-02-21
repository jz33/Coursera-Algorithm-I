import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import static java.lang.System.out;

public class PercolationStats {

    boolean DEBUG = false;
    int N; // matrix dimesion
    int T; // simulation times
    double[] fractions; // 

    public double mean(){
        return StdStats.mean(fractions);
    }
    
    protected double var(){
        return StdStats.var(fractions);
    }
    
    public double stddev(){
        return StdStats.stddev(fractions);
    }
    
    public double confidenceLo(){
        return mean() - 1.96 * var() / Math.sqrt(T);
    }
    
    public double confidenceHi(){
        return mean() + 1.96 * var() / Math.sqrt(T);
    }
    
    protected void print(){
        if(DEBUG){
            for(int i = 0;i<T;i++){
                out.print(fractions[i]+ " ");
            }
            out.println();
        }
        out.format("mean %20s %f\n", "=", mean());
        out.format("stddev %18s %f\n", "=", stddev());
        out.format("%s confidence interval %s %f, %f\n", "95%","=",confidenceLo(),confidenceHi());
        out.println();
    }

    protected void simulate(){
        int bound = N * N;
        Percolation node = new Percolation(N);
        for(int i = 0;i<T;i++){
            while(!node.percolates()){
                int rand = StdRandom.uniform(0,bound);
                int row = (int)(rand / N) + 1;
                int col = (int)(rand % N) + 1;
                node.open(row,col);
            }
            fractions[i] = node.fraction();
            if(DEBUG) node.print();
            node.reset();
        }
    }
    
    public PercolationStats(int N, int T) {
        this.N = N;
        this.T = T;
        fractions = new double[T];
        simulate();
        print();
    }

    // Test
    public PercolationStats(){
        N = 3;
        Percolation node = new Percolation(N);

        node.open(1,2);
        node.open(1,3);
        node.open(3,3);
        node.open(2,2);
        node.open(3,2);
        node.print();
    }
    
    public static void main(String[] args) {
        if(args.length >= 2){
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            new PercolationStats(N,T);
        } else {
            new PercolationStats();
        }   
    }
}
