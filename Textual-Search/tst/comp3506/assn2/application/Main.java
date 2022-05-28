package comp3506.assn2.application;

import java.io.FileNotFoundException;

/**
 * Main method for testing purposes only
 */


public class Main {


    public static void main(String[] args) throws FileNotFoundException {

        MyMap map = new MyMap();

        String firstFile = "files/shakespeare.txt";
        String secondFile = "files/shakespeare-index.txt";
        String thirdFile = "files/stop-words.txt";

        AutoTester tester = new AutoTester(firstFile, secondFile, thirdFile);

        //System.out.println(tester.wordCount("sonnet"));
        //System.out.println(tester.trie.search("from fairest creatures we desire increase,"));
        //System.out.println(tester.lineCount);
        //System.out.println(" ------------------------- " );

        //System.out.println(tester.wordCount("th"));
        //System.out.println(tester.phraseOccurrence("and"));
        //System.out.println(tester.trie.getLineNumber("complete"));



//        System.out.println(tester.wordCount("fairest"));
//        System.out.println(tester.trie.searchNode("sonnet").lineNumber);
//        System.out.println(tester.trie.searchNode("sonnet").columnNumber);
//        System.out.println(tester.trie.searchNode("fairest").lineNumber);
//        System.out.println(tester.trie.searchNode("fairest").columnNumber);


        //System.out.println(tester.phraseOccurrence("maul").toArray());
        //System.out.println(tester.trie.searchNode("fair"));
        //System.out.println(tester.trie.prefixOccurrences(("fair")));






        //String[] temp = {"dicks", "fairest"};
        //System.out.println(tester.trie.searchNode("thousand").occurrences);
        //System.out.println(tester.trie.searchNode("victories").occurrences);
        //System.out.println(tester.trie.someWordsOnline(temp));
//
//
//        String [] requiredWords = {"fairest"};
//        String [] excludedWords = {"lady"};
//        System.out.println(tester.trie.searchNode("fairest").occurrences);
//        System.out.println(tester.trie.searchNode("lady").occurrences);
//
//        System.out.println(tester.trie.wordsNotOnLine(requiredWords, excludedWords));









    }



}
