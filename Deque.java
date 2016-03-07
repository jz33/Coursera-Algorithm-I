import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
/*
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 */
public class Deque<Item> implements Iterable<Item> {

    private boolean debug = false;
    private Item[] arr;
    private int left; // exclusively, i.e, a valid empty space
    private int right;

    public Deque() {
        arr = (Item[]) new Object[2];
        left = 0;
        right = 1;
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public int size() {
        return right - left - 1;
    }

    /**
     * Double arr size, or move content to center
     */
    private void expandIfNeeded() {
        int diff = 0;
        if (size() >= (int) (0.75 * arr.length)) {
            int newSize = arr.length * 2;
            
            Item[] newArr = (Item[]) new Object[newSize];
            diff = ((newSize - 1) >> 1) - ((left + right) >> 1);
            System.arraycopy(arr, left + 1, newArr, left + 1 + diff, size());
            arr = newArr;
            if (debug)
                StdOut.println("expended to new array");
        } else if (left <= -1) {
            diff = ((arr.length - 1) >> 1) - ((left + right) >> 1);
            if (diff != 0) {
                for (int i = right - 1; i > left; i--) {
                    arr[i + diff] = arr[i];
                }
                if (debug)
                    StdOut.println("moved to right in same array");
            }
        } else if (right >= arr.length) {
            diff = ((arr.length - 1) >> 1) - ((left + right) >> 1);
            if (diff != 0) {
                for (int i = left + 1; i < right; i++) {
                    arr[i + diff] = arr[i];
                }
                if (debug)
                    StdOut.println("moved to left in same array");
            }
        }
        left += diff;
        right += diff;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        expandIfNeeded();
        arr[left--] = item;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        expandIfNeeded();
        arr[right++] = item;
    }

    private void shrinkIfPossible() {
        if (size() < (int) (arr.length / 4)) {
            int newSize = arr.length / 2;
            
            Item[] newArr = (Item[]) new Object[newSize];
            int diff = (newSize >> 1) - ((left + right) >> 1);
            for (int i = left + 1; i < right; i++) {
                newArr[i + diff] = arr[i];
            }
            arr = newArr;
            left += diff;
            right += diff;
            if (debug)
                StdOut.println("shrinked");
        }
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item e = arr[++left];
        arr[left] = null;
        shrinkIfPossible();
        return e;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item e = arr[--right];
        arr[right] = null;
        shrinkIfPossible();
        return e;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private int i = left + 1;

            @Override
            public boolean hasNext() {
                return i < right;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (Item) arr[i++];
            }
        };
    }
}
