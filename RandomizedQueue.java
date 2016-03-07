import java.util.Iterator;
import java.util.NoSuchElementException;

//import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int rt; // point to empty position

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
        int[] knuthArr = new int[size];
        for (int i = 1; i < rt; i++) {
            knuthArr[i] = i;
        }
        StdRandom.shuffle(knuthArr);
        return knuthArr;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int[] ids = knuthArray(size());
            private int pp = 0;

            @Override
            public boolean hasNext() {
                return pp < ids.length;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return arr[ids[pp++]];
            }
        };
    }
}
