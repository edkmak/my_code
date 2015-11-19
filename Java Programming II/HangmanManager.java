/*Edwin Mak 2/22/13
 * Victoria Wagner Section BE
 * HW #5 Evil Hangman
 * In this assignment we create a manager to play 
 * a game of Evil Hangman, a version of Hangman in which the computer
 * delays choosing a word until it is forced to.
 */

import java.util.*;

public class HangmanManager{

	private Set<String> avaWords;							//set of words being considered
	private int guessesLeft;								//guesses left before game ends
	private SortedSet<Character> guessedLetters;		//the set of previously guessed letters by user
	
	// initailizes the state of game by passing a list of words, the length of words to consider,
   // 	and the number of wrong guesses the user can make before game ends.
   // throws an IllegalArgumentException if length is less than 1, or the maximum 
   // 	number of wrong guesses the user can make is less than 0
	public HangmanManager(List<String> dictionary, int length, int max) {
		if(length < 1 || max < 0) {
			throw new IllegalArgumentException("Length is less than 1 or max is less than 0");
		}
		guessesLeft = max;
		guessedLetters = new TreeSet<Character>();
		avaWords = new TreeSet<String>(); 
		for(String word : dictionary) {
			if(word.length() == length) {
				avaWords.add(word);
			}
		}
	}
	
	//returns the list of words being considered
	public Set<String> words() {
		return avaWords;
	}
	
	//returns the number of guesses that the user has left
	public int guessesLeft() {
		return guessesLeft;
	}
	
	//returns a set of characters that the user guessed previously
	public SortedSet<Character> guesses() {
		return guessedLetters;
	}
	
	//returns the current pattern of the game. 
	//shows the character if character is guessed, and a dash if not yet guessed.
	//throws IllegalArgumentException if list of words being considered is empty
	public String pattern() {
		if(avaWords.isEmpty()) {
			throw new IllegalArgumentException("list of words is empty");
		}
		String pattern = "";
		for(String word: avaWords) {
			String tempPattern = "";
			for(int i = 0; i < word.length(); i++) {
				if(guessedLetters.contains(word.charAt(i))) {
					tempPattern += word.charAt(i) + " ";
				}else {
					tempPattern += "- " ;
				}
			}
			pattern = tempPattern;
		}
		return pattern.trim();
	}
	
	//decides which set of words to use after the player guesses a character.
	//returns the number of times the guessed character occurs in the pattern.
	//decreases guessesLeft if guess does not appear in the desired pattern.
	//throws IllegalStateException if number of guesses left is less than one or 
	//		set of considered words is empty.
	//throws IllgealArgumentException if list of words is nonempty and character being guessed was
	//		already guessed.
	public int record(char guess) {
		if (guessesLeft < 1 || avaWords.isEmpty()) {
         throw new IllegalStateException("Guesses less than 1 or there is no words in list.");
		}
		if(guessedLetters.contains(guess)) {
			throw new IllegalArgumentException("Character already guessed");
		}
		guessedLetters.add(guess);
		Map<String, Set<String>> patternWords = new TreeMap<String, Set<String>>();
		patternToMap(patternWords, guess);	
		String desiredPattern = getDesiredPattern(patternWords);	
		avaWords.retainAll(patternWords.get(desiredPattern));
		int numOccur = getNumOccur(desiredPattern, guess);
		if(numOccur == 0) {
			guessesLeft--;
		}
		return numOccur;
		
	}
	
	//returns the number of times the guessed character appears in the current pattern.
	private int getNumOccur (String desiredPattern, char guess) {
		int numberChars = 0;
		for(int i = 0; i < desiredPattern.length(); i++) {
			if(desiredPattern.charAt(i) == guess) {
				numberChars++;
			}
		}
		return numberChars;
	}
		
	//returns the pattern of the largest group of words with the same pattern
	//to decide which set of words to use for next guess.
	//chooses first set if all sets are the same size
	private String getDesiredPattern (Map<String, Set<String>> patternWords) {
		int maxSize = 0;
		String desiredPattern = "";			
		for(String pattern : patternWords.keySet()) {
			if(patternWords.get(pattern).size() > maxSize){
				maxSize = patternWords.get(pattern).size();
				desiredPattern = pattern;
			}
		}
		return desiredPattern;
	}
	
	//makes a pattern for each word in the words being considered and pairs patterns with
	//words with the same pattern into the same set.
	private void patternToMap (Map<String,Set<String>> patternWords, char guess) {
		for(String word : avaWords) {
			String tempPattern = "" ;
			for(int i = 0; i < word.length(); i++) {
				if(word.charAt(i) == guess) {
					tempPattern += guess + " " ;
				}else {
					tempPattern += "- " ;
				}
			}
			if(!patternWords.containsKey(tempPattern)) {
				patternWords.put(tempPattern, new TreeSet<String>());
			}
			Set<String> samePattern = patternWords.get(tempPattern);
			samePattern.add(word);
		}
	}
}

