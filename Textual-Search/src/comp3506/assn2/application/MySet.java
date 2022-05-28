package comp3506.assn2.application;

import java.util.Iterator;


public class MySet implements Iterable<Object>{

    private Node[] buckets;
    private int currentSize;
    private int current;


    /**
     * Constructor for set only used for one wordsOnLine method in Trie class
     * O(n) based on  n length
     * @param length
     */
    public MySet(int length) {
        buckets = new Node[length];
        currentSize = 0;
    }

    /**
     * adding to end of bucket constant O(1)
     * @param o
     * @return true is object added to set
     */
    public boolean add(Object o) {
        int key = getHashCode(o);          //implement
        Node node = buckets[key];
        while (node != null) {
            if(node.data.equals(o)) {
                return false;
            }
            node = node.next;
        }
        if(buckets[current] == null) {
            node = new Node(o);
            current = key;
            buckets[key] = node;
            currentSize++;
        } else {
            node = new Node(o);
            node.next = buckets[current];
            current = key;
            buckets[key] = node;
            currentSize++;
        }
        if(currentSize>(buckets.length*0.75)) {
            rehash();
        }
        return true;
    }

    /**
     * rehash method
     * runTIme : constant O(n)
     */
    public void rehash() {
        Node temp = buckets[current];
        Object s[] = new Object[buckets.length];
        buckets = new Node[2 * buckets.length]; // Double in size
        currentSize = 0;
        int i = 0;
        while (temp != null) {
            s[i] = temp.data;               // Implement Node class
            temp = temp.next;
            i++;
        }
        while (i > 0) {
            add(s[--i]);
        }
        /// ADD MORE HERE LATER

    }

    /**
     * constant O(1)? for object hashCode retrieval
     * @param o
     * @return
     */

    public int getHashCode(Object o) {
        int hash = o.hashCode();
        if(hash < 0)
            hash =- hash;
        return hash%buckets.length;
    }

    public Iterator<Object> iterator() {
        return new HashSetIterator();
    }



    /**
     * INNER NODE CLASS
     */
    private class Node {
        public Object data;
        public Node next;


        /**
         * Constructor for inner node class
         * Constant O(1)
         * @param x
         */
        public Node(Object x) {
            data = x;
        }

        public String toString() {
            return "Data : "  + data;
        }

    }


    /**
     * INNER ITERATOR
     */

    /**
     * Private Iterator for HashSet
     * runTime for hasNext and next() would both be constant 1
     * the method calling and returning Iterator would be O(n).
     *
     */
    private class HashSetIterator implements Iterator<Object> {
        private int bucket = 0;
        private MySet.Node currentNode;

        public HashSetIterator() {
            currentNode = buckets[current];
        }

        public boolean hasNext() {
            if(currentNode.next != null) {
                return true;
            }
            return false;
        }

        public Object next() {
            return currentNode.next;
        }

        public void remove() {
            currentNode.next = currentNode.next.next;
        }

    }
}

