package comp3506.assn2.application;

public class Entry <K,V> {
    final K key;
    V value;
    Entry<K,V> next;

    public Entry(K key, V value, Entry<K,V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }


    public K getKey() {
        return key;
    }

    public V getvalue() {
        return value;
    }

    // public boolean equals()

    public int getHash(Object o) {
        return 1;
    }

}
