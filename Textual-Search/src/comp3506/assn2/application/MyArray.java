package comp3506.assn2.application;

import java.util.Iterator;

public class MyArray<T> implements Iterable<T>{
    protected T[] a;
    protected int endIndex = 0;
    protected final int INIT_LENGTH = 1;

    /**
     * Constructor for MyArray -> implementation of an ArrayList for CDT storage
     * runTime : new construction would be n dependent on array size.
     * O(n) time final
     *
     */
    public MyArray() {
        a = (T[])new Object[INIT_LENGTH];
    }


    /**
     * get Method for array
     *
     * runTime : size of Index would be n. Searching array of n size would take n time
     * O(n) time - final.
     *
     * @param i
     * @return T
     */
    public T get(int i) {
        if(i < endIndex) {
            return a[i];
        } else {
            throw new IndexOutOfBoundsException();
        }

    }

    /**
     * adding to Array
     * O(1) because adding to an Array, regardless of current size would just be constant 1 time.
     *
     * @param element
     */
    public void add (T element) {
        if(endIndex < a.length) {
            a[endIndex] = element;
            this.endIndex++;
        } else {
            // array too small; double size & copy over old elements
            T[] newA = (T[]) new Object[a.length * 2];
            for (int i = 0; i < a.length; i++) {
                newA[i] = a[i];
            }
            a = newA;
            a[endIndex] = element;
            this.endIndex++;

        }
    }


    /**
     * Iterator method for Array
     * RunTime: hasNext()  based on index size to check next element/object O(1).
     *
     * RunTime : next()  based on index size to check next and return -> constant O(1).
     *
     * Method call that returns the iterator would be O(n).
     * @return Iterator
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < endIndex && a[currentIndex] != null;
            }
            @Override
            public T next() {
                return a[currentIndex++];
            }

        };
        return iterator;
    }


}

