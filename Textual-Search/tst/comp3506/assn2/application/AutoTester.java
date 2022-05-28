package comp3506.assn2.application;

import comp3506.assn2.utils.Pair;
import comp3506.assn2.utils.Triple;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * AutoTester class that implements Search interface
 * Contains all the methods for search, word count, boolean logic search
 *
 * Separate Analysis on readme.pdf in the assignment2 folder.
 * AutoTester boolean logic search methods do not implement stopper words with the current implementation.
 * All tests given in the original ProvidedTests class passes but will probably change once 'harder' tests are
 * implemented and with stopper words.
 *
 * Main data structure utilised would be Trie, MyMap, and MyArray with supporting classes for the CDTs.
 *
 *
 * @author JI HOON OH 43789991
 */
public class AutoTester implements Search {


	protected Trie trie;
	protected Trie indexTrie;
	protected MyMap map;
	protected MyArray<String> stoppers;
	private static File docFile;
	private static File indexFile;
	private static File stopWordsFile;
	private BufferedReader doc;
	private BufferedReader index;
	private BufferedReader stopWords;
	public static int lineCount;
	protected MyArray<Pair<String, Integer>> indexes;

	/**
	 * Constructor for AutoTester that only takes in document File
	 * RunTime : constant reads + 2 for loops reading dependent on line numbers
	 *  total Time : O(n^2)
	 * @param documentFileName
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */

	public AutoTester(String documentFileName) throws FileNotFoundException, IllegalArgumentException, IOException {
		this.docFile = new File(documentFileName);
		doc = new BufferedReader(new FileReader(docFile));
		int lineCount = 1;
		int columnNumber = 1;
		for (String line = null; (line = doc.readLine()) != null; ) {
			String[] words = line.split("[^\\w']");
			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].replaceAll("", "").toLowerCase();
				words[i] = words[i].toLowerCase();
				trie.insert(words[i], lineCount, columnNumber);
				columnNumber += words[i].length() + 1;
			}
			lineCount++;
			columnNumber = 1;
		}
	}

	/**
	 * Constructor for AutoTester that takes in document file and indexFile
	 * Same runTime as above.
	 *
	 * @param documentFileName
	 * @param indexFileName
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public AutoTester(String documentFileName, String indexFileName) throws FileNotFoundException,
			IllegalArgumentException, IOException {
		doc = new BufferedReader(new FileReader(docFile));
		index = new BufferedReader(new FileReader(indexFile));
		int lineCount = 1;
		int columnNumber = 1;
		for (String line = null; (line = doc.readLine()) != null; ) {
			String[] words = line.split("[^\\w']");
			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].replaceAll("", "").toLowerCase();
				words[i] = words[i].toLowerCase();
				trie.insert(words[i], lineCount, columnNumber);
				columnNumber += words[i].length() + 1; // increment this by 1 instead of words[i] for column in relation to the sentence
			}
			lineCount++; // Just to keep track of the lines
			columnNumber = 1;
		}
		for(String line = null; (line = index.readLine()) != null;) {
			String[] words = line.split("");
			String newLine = line.replaceAll("[,0-9]", "");
			int indexNumber = Integer.parseInt(line.replaceAll("\\D+", ""));
			indexes.add(new Pair(newLine, indexNumber));
		}
	}



	/**
	 * Create an object that performs search operations on a document.
	 * If indexFileName or stopWordsFileName are null or an empty string the document should be loaded
	 * and all searches will be across the entire document with no stop words.
	 * All files are expected to be in the files sub-directory and 
	 * file names are to include the relative path to the files (e.g. "files\\shakespeare.txt").
	 *
	 * RunTime : same run time as above with + O(n) for extra n constant reads
	 *
	 *
	 * @param documentFileName  Name of the file containing the text of the document to be searched.
	 * @param indexFileName     Name of the file containing the index of sections in the document.
	 * @param stopWordsFileName Name of the file containing the stop words ignored by most searches.
	 * @throws FileNotFoundException if any of the files cannot be loaded.
	 *                               The name of the file(s) that could not be loaded should be passed 
	 *                               to the FileNotFoundException's constructor.
	 * @throws IllegalArgumentException if documentFileName is null or an empty string.
	 */
	public AutoTester(String documentFileName, String indexFileName, String stopWordsFileName)
			throws FileNotFoundException, IllegalArgumentException {
		// TODO Implement constructor to load the data from these files and
		// TODO setup your data structures for the application.

		this.docFile = new File(documentFileName);
		this.indexFile = new File(indexFileName);
		this.stopWordsFile = new File(stopWordsFileName);
		this.trie = new Trie();
		this.indexTrie = new Trie();
		this.map = new MyMap<Integer, TrieNode>();
		this.indexes = new MyArray<>();

		try {
			doc = new BufferedReader(new FileReader(docFile));
			index = new BufferedReader(new FileReader(indexFile));
			stopWords = new BufferedReader(new FileReader(stopWordsFile));
			int lineCount = 1;
			int columnNumber = 1;
			for(String line = null; (line = doc.readLine()) != null;) {
				String[] words = line.split("[^\\w']");
				for(int i=0; i<words.length; i++) {
					words[i] = words[i].replaceAll("", "").toLowerCase();
					words[i] = words[i].toLowerCase();
					trie.insert(words[i], lineCount, columnNumber);
					columnNumber += words[i].length() + 1; // increment this by 1 instead of words[i] for column in relation to the sentence
				}
				lineCount++; // Just to keep track of the lines
				columnNumber = 1;

			}

			for(String line = null; (line = index.readLine()) != null;) {
				String[] words = line.split("");
				String newLine = line.replaceAll("[,0-9]", "");
				int indexNumber = Integer.parseInt(line.replaceAll("\\D+",""));
				indexes.add(new Pair(newLine, indexNumber));
			}

			this.stoppers = new MyArray<>();
			for(String line = null; (line = stopWords.readLine()) != null;) {
				this.stoppers.add(line);
			}


		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException");
		} finally {
			try {
				doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Determines the number of times the word appears in the document.
	 *
	 * RunTime: The word count method itself is a direct call to the searchNode method
	 * in the Trie class. Inside the Trie class the method has a run time efficiency
	 * of O(n) where n is the length of the string. Final runtime would be O(n)
	 *
	 *
	 * @param word The word to be counted in the document.
	 * @return The number of occurrences of the word in the document.
	 * @throws IllegalArgumentException if word is null or an empty String.
	 */

	// Simply calls the getCount method implemented in TrieNode
	// Convert all word to lowercase
	@Override
	public int wordCount(String word) {
		try {
			return trie.searchNode(word.toLowerCase()).wordOccurrences.endIndex;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	/**
	 * Finds all occurrences of the phrase in the document.
	 * A phrase may be a single word or a sequence of words.
	 * WARNING : LOTS OF FOR LOOPS
	 *
	 * RunTime: 1 for loop outside with 3 nested for loops. Each for loop nested with increase index constant +1.
	 * Also calls method wordsToOccurrences (private helper method) inside all the for loops.
	 * The private helper method has the runtime of O(n^2). For loops have runtime n value assignments
	 * (searching array/map with constant index and assigning to a temp pair. Final answer would be : O(n^2) runtime.
	 *
	 * @param phrase The phrase to be found in the document.
	 * @return List of pairs, where each pair indicates the line and column number of each occurrence of the phrase.
	 *         Returns an empty list if the phrase is not found in the document.
	 * @throws IllegalArgumentException if phrase is null or an empty String.
	 */

	// Phrase Occurrence test took about ~851ms when I tested it, barely going over the timeout. It passes if we
	// increase the timer to ~1s, but fails otherwise.
	public List<Pair<Integer,Integer>> phraseOccurrence(String phrase) throws IllegalArgumentException {
		String[] words = phrase.split("[^\\w']");
		List<Pair<Integer, Integer>> phrases = new ArrayList<>();
		MyMap<String, MyArray<Pair<Integer, Integer>>> wordsToOccurrences = new MyMap<>();
		for(String word : words) {
			wordsToOccurrences.put(word, trie.searchNode(word).wordOccurrences);
		}
		zeroOccurrencesLoop:
		for(int i = 0; i < wordsToOccurrences.get(words[0]).endIndex; i++) {
			String prevWord = words[0];
			Pair<Integer, Integer> prevOccurrence = wordsToOccurrences.get(words[0]).get(i);
			wordloop:
			for(int j = 1; j < words.length; j++) {
				occurrenceloop:
				for(int k = 0; k < wordsToOccurrences.get(words[j]).endIndex; k++) {
					Pair<Integer, Integer> occurrence = wordsToOccurrences.get(words[j]).get(k);
					if(occurrence.getLeftValue() < prevOccurrence.getLeftValue()) {
						continue;
					}
					else if(occurrence.getLeftValue() > prevOccurrence.getLeftValue()) {
						// not on same line
						continue zeroOccurrencesLoop;
					} else if(occurrence.getRightValue() - prevOccurrence.getRightValue() != prevWord.length()+1) {
						// on same line but not contiguous
						continue;
					} else {
						// phrase matches so far
						prevWord = words[j];
						prevOccurrence = occurrence;
						break;
					}
				}
			}
			if(prevWord.equals(words[words.length-1])) {
				// matched!
				phrases.add(wordsToOccurrences.get(words[0]).get(i));
			}
		}
		return phrases;
	}

	/**
	 * Finds all occurrences of the prefix in the document.
	 * A prefix is the start of a word. It can also be the complete word.
	 * For example, "obscure" would be a prefix for "obscure", "obscured", "obscures" and "obscurely".
	 *
	 * RunTime: O(n) search and assign on new Pair and index/pair occurrence search. One for loop of O(n).
	 * O(n) + O(n) -> O(n) runtime.
	 *
	 * @param prefix The prefix of a word that is to be found in the document.
	 * @return List of pairs, where each pair indicates the line and column number of each occurrence of the prefix.
	 *         Returns an empty list if the prefix is not found in the document.
	 * @throws IllegalArgumentException if prefix is null or an empty String.
	 */
	public List<Pair<Integer,Integer>> prefixOccurrence(String prefix) throws IllegalArgumentException {
		List<Pair<Integer, Integer>> temp = new ArrayList<>();
		int count = 0;
		for(Pair<Integer, Integer> pair : trie.prefixOccurrences(prefix)) {
			temp.add(count, new Pair(pair.getLeftValue(), pair.getRightValue()));
			count++;
		}
		return temp;
	}

	/**
	 * Searches the document for lines that contain all the words in the 'words' parameter.
	 * Implements simple "and" logic when searching for the words.
	 * The words do not need to be contiguous on the line.
	 *
	 * RunTime: O(n) search and assign on List. Calls myWordsOnline from TrieNode class O(n) runtime.
	 * Final runtime would be O(n) + O(n) * O(n) = O(n^2);
	 *
	 *
	 * @param words Array of words to find on a single line in the document.
	 * @return List of line numbers on which all the words appear in the document.
	 *         Returns an empty list if the words do not appear in any line in the document.
	 * @throws IllegalArgumentException if words is null or an empty array
	 *                                  or any of the Strings in the array are null or empty.
	 */
	public List<Integer> wordsOnLine(String[] words) throws IllegalArgumentException {
		// FIX THIS
		List<Integer> temp = new ArrayList<>();
		int counter = 0;
		for(int numbers : trie.myWordsOnline(words)) {
			temp.add(counter, numbers);
			counter++;
		}
		return temp;
	}


	/**
	 * Searches the document for lines that contain any of the words in the 'words' parameter.
	 * Implements simple "or" logic when searching for the words.
	 * The words do not need to be contiguous on the line.
	 *
	 * RunTime : O(n) search and assign on List. calls someWOrdsOnline from TrieNode class O(n) with 2 for loops.
	 *  For loop inside this method O(n). Total = O(n^2);
	 *
	 *
	 * @param words Array of words to find on a single line in the document.
	 * @return List of line numbers on which any of the words appear in the document.
	 *         Returns an empty list if none of the words appear in any line in the document.
	 * @throws IllegalArgumentException if words is null or an empty array
	 *                                  or any of the Strings in the array are null or empty.
	 */
	public List<Integer> someWordsOnLine(String[] words) throws IllegalArgumentException {
		List<Integer> temp = new ArrayList<>();
		int counter = 0;
		for(int numbers : trie.someWordsOnline(words)) {
			temp.add(counter, numbers);
			counter++;
		}
		return temp;
	}

	/**
	 * Searches the document for lines that contain all the words in the 'wordsRequired' parameter
	 * and none of the words in the 'wordsExcluded' parameter.
	 * Implements simple "not" logic when searching for the words.
	 * The words do not need to be contiguous on the line.
	 *
	 * RunTime : O(n) search and assign on List. calls someWOrdsOnline from TrieNode class O(n) with 3 for loops which
	 * would be O(n) + ... = O(n).
	 * 	 For loop inside this method O(n). Total = O(n^2);
	 *
	 *
	 * @param wordsRequired Array of words to find on a single line in the document.
	 * @param wordsExcluded Array of words that must not be on the same line as 'wordsRequired'.
	 * @return List of line numbers on which all the wordsRequired appear
	 *         and none of the wordsExcluded appear in the document.
	 *         Returns an empty list if no lines meet the search criteria.
	 * @throws IllegalArgumentException if either of wordsRequired or wordsExcluded are null or an empty array
	 *                                  or any of the Strings in either of the arrays are null or empty.
	 */
	public List<Integer> wordsNotOnLine(String[] wordsRequired, String[] wordsExcluded) {
		List<Integer> temp = new ArrayList<>();
		int counter = 0;
		for(int numbers : trie.wordsNotOnLine(wordsRequired, wordsExcluded)) {
			temp.add(counter, numbers);
			counter++;
		}
		return temp;
	}

	/**
	 * Searches the document for sections that contain all the words in the 'words' parameter.
	 * Implements simple "and" logic when searching for the words.
	 * The words do not need to be on the same lines.
	 *
	 * RunTime: 3 nested for loops -> O(n^3) + O(n) search/assign on MyArray + 1 put into List
	 * Total runtime for this method = O(n^3);
	 *
	 *
	 * @param titles Array of titles of the sections to search within,
	 *               the entire document is searched if titles is null or an empty array.
	 * @param words Array of words to find within a defined section in the document.
	 * @return List of triples, where each triple indicates the line and column number and word found,
	 *         for each occurrence of one of the words.
	 *         Returns an empty list if the words are not found in the indicated sections of the document,
	 *         or all the indicated sections are not part of the document.
	 * @throws IllegalArgumentException if words is null or an empty array
	 *                                  or any of the Strings in either of the arrays are null or empty.
	 */
	public List<Triple<Integer,Integer,String>> simpleAndSearch(String[] titles, String[] words){
		List<Triple<Integer, Integer, String>> triple = new ArrayList<>();
		MyArray<Triple<String, Integer, Integer>> tt = matchIndex(titles);

		for(Triple<String, Integer, Integer> section : tt) {
			MyArray<Triple<Integer, Integer, String>> occurrencesOfWordsInThisSection = new MyArray<>();
			int upperBoundary = section.getRightValue();
			int lowerBoundary = section.getCentreValue();
			boolean hasAllWords = true;
			for(String word : words) {
				boolean hasThisWord = false;
				TrieNode node = trie.searchNode(word);
				MyArray<Triple<Integer, Integer, String>> occurrencesOfThisWordInThisSection = new MyArray<>();
				for(Pair<Integer, Integer> o: node.wordOccurrences) {
					if (o.getLeftValue() > upperBoundary && o.getLeftValue() < lowerBoundary) {
						hasThisWord = true;
						occurrencesOfWordsInThisSection.add(new Triple<>(o.getLeftValue(), o.getRightValue(), word));
					}
				}
				if(hasThisWord == false) {
					hasAllWords = false;
					break;
				}
			}
			if(hasAllWords) {
				for(Triple<Integer, Integer, String> t : occurrencesOfWordsInThisSection) {
					triple.add(t);
				}
			}
		}

		return triple;
	}

	/**
	 * Searches the document for sections that contain any of the words in the 'words' parameter.
	 * Implements simple "or" logic when searching for the words.
	 * The words do not need to be on the same lines.
	 *
	 * Runtime : 3 nested for loops inside with each for loop incrementing the index by a constant 1
	 * therefore, it would be O(n^3).
	 *
	 *
	 *
	 * @param titles Array of titles of the sections to search within,
	 *               the entire document is searched if titles is null or an empty array.
	 * @param words Array of words to find within a defined section in the document.
	 * @return List of triples, where each triple indicates the line and column number and word found,
	 *         for each occurrence of one of the words.
	 *         Returns an empty list if the words are not found in the indicated sections of the document,
	 *         or all the indicated sections are not part of the document.
	 * @throws IllegalArgumentException if words is null or an empty array
	 *                                  or any of the Strings in either of the arrays are null or empty.
	 */
	public List<Triple<Integer,Integer,String>> simpleOrSearch(String[] titles, String[] words){
		List<Triple<Integer, Integer, String>> triple = new ArrayList<>();
		MyArray<Triple<String, Integer, Integer>> tt = matchIndex(titles);

		for(int i=0; i<words.length; i++) {
			String word = words[i];
			TrieNode node = trie.searchNode(word);
			for(int j=0; j<tt.endIndex; j++) {
				int upperBoundary = tt.get(j).getRightValue();
				int lowerBoundary = tt.get(j).getCentreValue();

				for(int k=0; k<node.wordOccurrences.endIndex; k++) {
					if(node.wordOccurrences.get(k).getLeftValue() > upperBoundary &&
							node.wordOccurrences.get(k).getLeftValue() < lowerBoundary) {
						triple.add(new Triple(node.wordOccurrences.get(k).getLeftValue(),node.wordOccurrences.get(k).getRightValue(), word));
					}
				}
			}
		}
		return triple;
	}

	/**
	 * Searches the document for sections that contain all the words in the 'wordsRequired' parameter
	 * and none of the words in the 'wordsExcluded' parameter.
	 * Implements simple "not" logic when searching for the words.
	 * The words do not need to be on the same lines.
	 *
	 * Run Time : O(n) for the last for loop that has a constant increment of index +1, and assigning array index +
	 * value (constant time). The first for loop would be constant O(n) but calls the method simpleOrSearch which has
	 * the run time efficiency of O(n^3). So the total run time efficiency of this method would be O(n^4)?
	 *
	 *
	**/
	public List<Triple<Integer,Integer,String>> simpleNotSearch(String[] titles, String[] wordsRequired,
																String[] wordsExcluded)
			throws IllegalArgumentException {
		List<Triple<Integer, Integer, String>> triple = new ArrayList<>();
		MyArray<Triple<String, Integer, Integer>> tt = matchIndex(titles);
		MyArray<String> titlesThatDontHaveExcludes = new MyArray<>();
		for(Triple<String, Integer, Integer> section : tt) {
			String[] t = {section.getLeftValue()};
			List<Triple<Integer, Integer, String>> orResults = simpleOrSearch(t, wordsExcluded);
			if(orResults.isEmpty()) {
				titlesThatDontHaveExcludes.add(section.getLeftValue());
			} else {
				//this section had an excluded word; no good to us
				continue;
			}
		}
		String[] titlesToSearch = new String[titlesThatDontHaveExcludes.endIndex];
		for(int i = 0; i < titlesThatDontHaveExcludes.endIndex; i++) {
			titlesToSearch[i] = titlesThatDontHaveExcludes.get(i);
		}
		return simpleAndSearch(titlesToSearch, wordsRequired);
	}

	/**
	 * Searches the document for sections that contain all the words in the 'wordsRequired' parameter
	 * and at least one of the words in the 'orWords' parameter.
	 * Implements simple compound "and/or" logic when searching for the words.
	 * The words do not need to be on the same lines.
	 *
	 *
	 * Runtime: O(n) * O(n) for loop + nested for loop + O(n) = total = O(n^2).
	 *
	 *
	 */
	public List<Triple<Integer,Integer,String>> compoundAndOrSearch(String[] titles, String[] wordsRequired,
																	String[] orWords)
			throws IllegalArgumentException {

		List<Triple<Integer, Integer, String>> results = new ArrayList<>();
		for(String orWord : orWords) {
			String[] searchWords = new String[wordsRequired.length+1];
			for(int i = 0; i < wordsRequired.length; i++) {
				searchWords[i] = wordsRequired[i];
			}
			searchWords[searchWords.length-1] = orWord;
			List<Triple<Integer, Integer, String>> sr = simpleAndSearch(titles, searchWords);
			for(Triple<Integer, Integer, String> t: sr) {
				results.add(t);
			}

		}

		return results;
	}


	/**
	 * Private helper method to sort indexFile into a Triple of Title(String), Starting Line number, and
	 * ending Line Number.
	 *
	 * Run-Time Efficiency: O(n^2) : an if statement nested inside 2 for loops. It would take O(n) for the inner loop
	 * to function while the outer for loop would also take O(n). Totalling O(n^2) efficiency time.
	 *
	 * @param titles
	 * @return
	 */

	private MyArray<Triple<String, Integer, Integer>> matchIndex(String[] titles) {
		MyArray<Triple<String, Integer, Integer>> returnList = new MyArray<>();
		for(int i=0; i<indexes.endIndex; i++) {
			for(int j=0; j<titles.length; j++) {
				if(indexes.get(i).getLeftValue().equals(titles[j])) {
					int endIndex = (i < indexes.endIndex - 1) ? indexes.get(i+1).getRightValue() : Integer.MAX_VALUE;
					returnList.add(new Triple(titles[j], endIndex, indexes.get(i).getRightValue()));
				}
			}
		}
		return returnList;
	}
}
