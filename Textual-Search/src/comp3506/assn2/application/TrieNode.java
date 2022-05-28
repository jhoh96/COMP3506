package comp3506.assn2.application;

import comp3506.assn2.utils.Pair;

public class TrieNode {

    protected char c;
    protected MyMap<Character, TrieNode> children;
    protected boolean isLeaf;
    protected int count = 0;
    protected MyArray<Pair<Integer, Integer>> occurrences;
    protected MyArray<Pair<Integer, Integer>> wordOccurrences;


    /**
     * Constructor method for TrieNode
     *
     * The constructor allocates memory depending on length of MyArray. Takes n time to allocate as it will not be
     * a constant 1.
     * Final RunTime = O(n) ?
     *
     * @param c
     * @param lineNumber
     * @param columnNumber
     * @param wordStop
     */
    public TrieNode(char c, int lineNumber, int columnNumber, boolean wordStop) {

        this.c = c;
        this.children = new MyMap<Character, TrieNode>();
        count++;
        occurrences = new MyArray<>();
        wordOccurrences = new MyArray<>();
        addOccurrence(lineNumber, columnNumber);
        if(wordStop) addWordOccurrence(lineNumber, columnNumber);

    }

    private void addOccurrence(int lineNumber, int columnNumber) {
        occurrences.add(new Pair<>(lineNumber, columnNumber));
    }

    private void addWordOccurrence(int lineNumber, int columnNumber) {
        wordOccurrences.add(new Pair<>(lineNumber, columnNumber));
    }

    /**
     * return TrieNode child
     *
     * input c -> search c return TrieNode
     * RunTime : O(n) ?
     *
     * @param c
     * @return
     */
    public TrieNode getChild(char c) {
        return children.get(c);
    }

    /**
     * toString method for TrieNode;
     *
     * RunTime: O(1) constant return value;
     *
     * @return String
     */
    public String toString() {
        return String.format("Letter: %c\nOccurrences: %s\nStrict Word Occurrences: %s", c, occurrences, wordOccurrences);
    }


    /**
     * Sets the child node of the character for word search(Trie Search)
     *
     * O(n) one if statement + calling O(1) addOccurrence.
     * Total runTime: O(n).
     *
     *
     * @param c
     * @param lineNumber
     * @param columnNumber
     * @param wordStop
     */
    public void setChild(char c, int lineNumber, int columnNumber, boolean wordStop) {
        if(children.get(c) != null) {
            children.get(c).addOccurrence(lineNumber, columnNumber);
            if(wordStop) children.get(c).addWordOccurrence(lineNumber, columnNumber);
        }
        else {
            children.put(c, new TrieNode(c, lineNumber, columnNumber, wordStop));
        }
    }

    /**
     * RunTime: O(1) constant return
     * @return boolean
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /**
     * RunTime: O(1);
     * @param isLeaf
     */
    public void setLeafNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * RunTime: O(1);
     *
     */
    public int getCount() {
        return count;
    }

    /**
     * Runtime: O(1);
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

}
