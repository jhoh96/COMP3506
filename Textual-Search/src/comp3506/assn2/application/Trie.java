package comp3506.assn2.application;

import comp3506.assn2.utils.Pair;

public class Trie {

    protected TrieNode root;
    protected MyMap<Character, TrieNode> children;
    protected static int lineNumber = 1;

    /**
     * Constructor class for TrieNode;
     * Assigning root with char, ln, cn, and ws to 0
     * total runTime : O(1)?
     */
    public Trie(){
        root = new TrieNode((char)0, 0, 0, false);
        this.children = children;
        this.lineNumber = lineNumber;
    }


    /**
     * Inserts new word into Trie Node
     *
     * RunTime: 1 for loop -> O(n) + O(n) + O(n)
     * total : O(n);
     *
     * @param word
     * @param lineNumber
     * @param colNumber
     * @return
     */
    // Inserts a word into the trie.
    // Keeps track of word frequency
    public boolean insert(String word, int lineNumber, int colNumber) {
        TrieNode head = root;
        for(int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            head.setChild(ch, lineNumber, colNumber, (i == word.length() -1));
            head = head.getChild(ch);
        }
        if(head.isLeaf()) {
            head.setCount(head.getCount()+1);
            return false;
        }
        head.setLeafNode(true);
        return true;
    }


    /**
     * Returns True if word is found inside the Trie
     * Returns False if word is not found inshde the Trie
     *
     * RunTime: While it's one if statement, it search would be dependent on
     * N size of tree or n location of node.
     *
     * O(n) time.
     *
     * @param word
     * @return True/False
     */
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);
        if(t != null && t.isLeaf)
            return true;
        else
            return false;
    }


    /**
     * Returns node of searched String
     *
     * RunTime: if else statement in a for loop O(n) + O(n). Search would be also
     * O(n) n size dependent.
     * Final RunTime would be O(n).
     *
     *
     * @param str
     * @return TrieNode
     */

    public TrieNode searchNode(String str){
        MyMap<Character, TrieNode> children = root.children;
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            }else{
                return null;
            }
        }

        return t;
    }


    /**
     * RunTime: Initial search for the string prefix in the node would be n dependent.
     * Assigning to new node would just be O(1) constant.
     * Final : O(n) time.
     *
     * @param prefix
     * @return My implementation of Array of Pair (occurrences) for word searched
     */
    public MyArray<Pair<Integer, Integer>> prefixOccurrences(String prefix) {
        TrieNode node = searchNode(prefix);
        return node.occurrences;
    }


    /**
     * Algorithm implementation for AutoTester (up until boolean logic search) actually begins here
     *
     * RunTime : 1 unnested, separate for loops -> O(n) + O(n) * O(n). O(n^2) + O(n) for search
     * and assigning of node and pair for the node (n constant).
     * Total runTime would be O(n^2).
     *
     *
     * @param words
     * @return MyArray<Integer>
     */
    public MyArray<Integer> myWordsOnline(String[] words) {
        MyArray<Integer> temp = new MyArray<>();
        for(int i=0; i<words.length; i++) {
            for(Pair<Integer, Integer> pair : searchNode(words[i]).occurrences) {
                temp.add(pair.getLeftValue());
            }
        }
        MySet set = new MySet(temp.endIndex+1);
        MyArray<Integer> dupes = new MyArray<>();
        for(int num : temp) {
            if(!set.add(num)) {
                dupes.add(num);
            }
        }
        return dupes;
    }


    /**
     * Algorithm for someWordsOnline in AutoTester. More descriptive detail in AutoTester Class
     *
     * RunTIme; Nested For loop -> O(n) * O(n) + search at n constant time.
     * Final runtime would be O(n^2).
     *
     * @param words
     * @return MyArray<Integer>
     */
    public MyArray<Integer> someWordsOnline(String[] words) {
        MyArray<Integer> temp = new MyArray<>();
        for(int i=0; i<words.length; i++) {
            if(!search(words[i])) {
                continue;  // Potato
            }
            for(Pair<Integer, Integer> pair : searchNode(words[i]).occurrences) {
                temp.add(pair.getLeftValue());
            }
        }
        return temp;
    }


    /**
     * Algorithm for wordsNotOnline in autoTester class implemented in Trie class
     * Implemented here for reasons being in AutoTester it would be an O(n^2) call for this method. There is constant
     * access to occurrences in Trie class.
     *
     * RunTime : 3 separate for loops with 1 nested for loop in for loop.
     * O(n) * O(n) + O(n) * O(n) + O(n) * O(n) with each search being n constant based on size of search node
     * Final Runtime : O(n^2).
     *
     * @param wordsRequired
     * @param wordsExcluded
     * @return Array<Integer>
     */

    public MyArray<Integer> wordsNotOnLine(String[] wordsRequired, String[] wordsExcluded) {
        MyArray<Integer> tempLines = new MyArray<>();
        MyArray<Pair<Integer, Integer>> tempReq = new MyArray<>();
        MyArray<Pair<Integer, Integer>> tempExc = new MyArray<>();


        for (String word : wordsRequired) {
            for (int i = 0; i < searchNode(word).occurrences.endIndex; i++) {
                tempReq.add(searchNode(word).occurrences.get(i));
            }

        }
        for (String word : wordsExcluded) {
            for (int i = 0; i < searchNode(word).occurrences.endIndex; i++) {
                tempExc.add(searchNode(word).occurrences.get(i));
            }
        }

        for (int i = 0; i < tempReq.endIndex; i++) {
            if (tempReq.get(i).getLeftValue() != tempExc.get(i).getLeftValue()) {
                tempLines.add(tempReq.get(i).getLeftValue());
            }
        }
        return tempLines;
    }


}
