/**
package comp3506.assn2.application;

public class MyMap<K,V> {

    private Entry<K, V>[] buckets;
    private static final int INITIAL_CAPACITY = 16;
    private int size = 0;


    public MyMap(int initialCapacity) {
        this(INITIAL_CAPACITY);
    }

    public MyMap(int capacity) {
        this.buckets = new Entry[capacity];
    }


    public void put(K key, V value) {
        Entry<K,V> entry = new Entry<>(key, value, null);
        int bucket = getHash(key);

    }

}
**/

package comp3506.assn2.application;

import java.util.Iterator;

public class MyMap<K, V> implements Iterable<Object>{

    protected Entry<K, V>[] entryTable;
    protected int initialCapacity = 26;
    protected K Key;
    protected V Value;
    @SuppressWarnings("unchecked")
    protected MyMap() {
        entryTable = new Entry[initialCapacity];
    }


    /**
     * Constructor Class for MyMap
     * RunTime: initial capacity n dependent - > Final runtime O(n).
     * @param capacity
     */
    @SuppressWarnings("unchecked")
    public MyMap(int capacity) {
        initialCapacity = capacity;
        entryTable = new Entry[initialCapacity];
    }


    /**
     * getters and setters for Key and Value of Map
     * RunTime : O(1) constant time for hashmap
     *
     * @return K
     */
    public K getKey() {
        return Key;
    }

    /**
     * getter for value
     * RunTime : O(1) constant time.
     *
     * @return V
     */
    public V getValue() {
        return Value;
    }


    /**
     * Put method for map. Inserts element assigned to key.
     *
     * If hashbased Map -> Adding/putting value to a designated key would be constant O(1) operation.
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new RuntimeException("null key is not allowed");
        }
        // hash value of the key
        int hashValue = hashValue(key);
        // create the entry
        Entry<K, V> entry = new Entry<K, V>(key, value, null);

        // if no entry found at the hash value index of the entry table then put
        // the value
        if (entryTable[hashValue] == null) {
            entryTable[hashValue] = entry;
        } else {// if found then put the value in a linked list
            Entry<K, V> previous = null;
            Entry<K, V> current = entryTable[hashValue];
            while (current != null) {
                if (current.k.equals(key)) {
                    if (previous == null) {
                        entry.next = current.next;
                        entryTable[hashValue] = entry;
                    } else {
                        entry.next = current.next;
                        previous.next = entry;
                    }
                }
                previous = current;
                current = current.next;
            }
            previous.next = entry;
        }

    }

    /**
     * Returns value based on param Key
     *
     * RunTime : HashMap retrieval of Value per Key would be a constant O(1). Size would not matter as
     * long as key is designated.
     *
     * @param key
     * @return V
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }
        // hash value of the key
        int hashValue = hashValue(key);
        if (entryTable[hashValue] == null) {
            return null;
        } else {
            Entry<K, V> temp = entryTable[hashValue];
            while (temp != null) {
                if (temp.k.equals(key)) {
                    return temp.v;
                }
                temp = temp.next;
            }
        }
        return null;
    }

    /**
     * Remove element of Key K
     *
     * Run Time : Particular map does not allow duplicate or multiple entries per key. As long as key
     * is assigned to a value, it would be O(1) constant operation.
     *
     * @param key
     * @return false if empty, True if removed
     */
    public boolean remove(K key) {
        if (key == null) {
            return false;
        }
        // hash value of the key
        int hashValue = hashValue(key);
        if (entryTable[hashValue] == null) {
            return false;
        } else {
            Entry<K, V> previous = null;
            Entry<K, V> current = entryTable[hashValue];
            while (current != null) {
                if (current.k.equals(key)) {
                    if (previous == null) {
                        entryTable[hashValue] = entryTable[hashValue].next;
                        return true;
                    } else {
                        previous.next = current.next;
                        return true;
                    }
                }
                previous = current;
                current = current.next;
            }
            return false;
        }
    }


    /**
     * Checks if entryTable contains a Key. If Value assigned to Key.
     *
     * RunTime: checking key per value in entrYTable would be constant O(1) time as it will not be
     * size dependent.
     *
     * @param key
     * @return true if containsKey false otherwise
     */
    public boolean containsKey(K key) {
        int hashValue = hashValue(key);
        if (entryTable[hashValue] == null) {
            return false;
        } else {
            Entry<K, V> current = entryTable[hashValue];
            while (current != null) {
                if (current.k.equals(key)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    /**
     * returns size of the map
     *
     * RunTime : inside a for loop O(n) + O(n) for if statement + O(n). search would be n constant
     * as it will be size dependent. Based on the number of keys/value.
     * Total would be O(n).
     *
     * @return int size
     */
    public int size() {
        int count = 0;
        for (int i = 0; i < entryTable.length; i++) {
            if (entryTable[i] != null) {
                int nodeCount = 0;
                for (Entry<K, V> e = entryTable[i]; e.next != null; e = e.next) {
                    nodeCount++;
                }
                count += nodeCount;
                count++;// consider also vertical count
            }
        }
        return count;
    }


    /**
     * returns hashValue based on Key
     *
     * runTime : O(1). Not size dependent. hashValue assigned on key input 1 constant time
     *
     * @param key
     * @return int hashValue
     */
    public int hashValue(K key) {
        return Math.abs(key.hashCode()) % initialCapacity;
    }

    /**
     * Iterator method for MyMap implementation
     * runTIme : hasNext and next would be constant but calling would be size dependent. All operations would be at 1 constant time
     * but Method that calls the ITerator would be based on n time -> O(n).
     *
     * @return Iterator<Object>
     */
    @Override
    public Iterator<Object> iterator() {
        Iterator<Object> iterator = new Iterator<Object>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < initialCapacity && entryTable[currentIndex] != null;
            }
            @Override
            public Object next() {
                return entryTable[currentIndex++];
            }

        };
        return iterator;
    }

    private static class Entry<K, V> {
        private K k;
        private V v;
        private Entry<K, V> next;

        public Entry(K k, V v, Entry<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

    }




}