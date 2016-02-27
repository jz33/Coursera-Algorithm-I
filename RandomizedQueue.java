import java.util.Iterator;
import java.util.NoSuchElementException;

//import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int rt; // point to empty position

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        rt = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return rt;
    }

    private void expandIfNeeded() {
        int oldLen = arr.length;
        if (size() >= oldLen) {
            int newLen = oldLen * 2;
            @SuppressWarnings("unchecked")
            Item[] newArr = (Item[]) new Object[newLen];
            System.arraycopy(arr, 0, newArr, 0, size());
            arr = newArr;
        }
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        expandIfNeeded();
        arr[rt++] = item;
    }

    private void shrinkIfPossible() {
        int oldLen = arr.length;
        if (size() < oldLen / 4) {
            int newLen = oldLen / 2;
            @SuppressWarnings("unchecked")
            Item[] newArr = (Item[]) new Object[newLen];
            System.arraycopy(arr, 0, newArr, 0, size());
            arr = newArr;
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int r = StdRandom.uniform(0, rt);
        Item e = arr[r];
        arr[r] = arr[--rt];
        arr[rt] = null;
        shrinkIfPossible();
        return e;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return arr[StdRandom.uniform(0, rt)];
    }

    private int[] knuthArray(int size) {
        int[] arr = new int[size];
        for (int i = 1; i < rt; i++) {
            arr[i] = i;
        }
        StdRandom.shuffle(arr);
        return arr;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>(){
            private int[] _ids = knuthArray(size());
            private int _p = 0;

            @Override
            public boolean hasNext() {
                return _p < _ids.length;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return arr[_ids[_p++]];
            }
        };
    }

    //
    // public void state() {
    // StdOut.println(arr.length + ", " + rt);
    // for (int i = 0; i < arr.length; i++) {
    // StdOut.print(arr[i] + " ");
    // }
    // StdOut.println();
    // }

    public static void main(String[] args) {
        new RandomizedQueue<Integer>();
    }
}
