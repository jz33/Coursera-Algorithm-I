import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset {

    public Subset(int k) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString().trim());
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        new Subset(k);
    }
}
