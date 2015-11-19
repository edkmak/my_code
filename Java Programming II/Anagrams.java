/*Edwin Mak 2/28/13
 * Victoria Wagner Section BE
 * HW #6 Anagrams
 * In this assignment we create a manager to find 
 * the anagram phrases that match a given word or phrase
 */
 
import java.util.*;

public class Anagrams{
	
	private Map<String, LetterInventory> dictWords;

	// initializes the state of game by pairing the dictionary words
	//		to thier LetterInventories
   // throws an IllegalArgumentException if passed dictionary is null
	public Anagrams(Set<String> dictionary){
		if(dictionary == null){
			throw new IllegalArgumentException();
		}
		dictWords = new HashMap<String, LetterInventory>();
		for(String word : dictionary){
			dictWords.put(word, new LetterInventory(word));
		}
	}
	
	// returns an alphabetized set of words which letters are present in the passed 
	// 	phrase.
	public Set<String> getWords(String phrase){
		if(phrase == null){
			throw new IllegalArgumentException();
		}
		LetterInventory phraseLetters = new LetterInventory(phrase);
		SortedSet<String> avaWords = new TreeSet<String>();
		for(String word : dictWords.keySet()){
			if(phraseLetters.contains(word)){
				avaWords.add(word);
			}
		}
		return avaWords;
	}
	
	// prints all the anagrams formed by the given phrase
	// throws IllegalArgumentException if the phrase is null
	public void print(String phrase){
		if(phrase == null){
			throw new IllegalArgumentException("phrase cannot be null");
		}
		LetterInventory phraseLetters = new LetterInventory(phrase);
		int max = -1;																			//dummy max
		explore(phraseLetters,getWords(phrase), new Stack<String>(), max);
		
	}
	
	// prints the anagrams formed by the given phrase that contains max number of words
	// also prints the anagrams that contain less than max number of words
	// throws IllegalArgumentException if phrase is null or max is less than 0
	public void print(String phrase, int max){
		if(phrase == null || max < 0){
			throw new IllegalArgumentException("phrase cannot null or max cannot be less than zero");
		}
		LetterInventory phraseLetters = new LetterInventory(phrase);
		explore(phraseLetters,getWords(phrase), new Stack<String>(), max);
	}
	
	//finds and prints the anagrams found for the phrase
	private void explore(LetterInventory phraseLetters,Set<String> avaWords, 
								Stack<String>answer, int max){
		if(max != -1 && phraseLetters.isEmpty() && (answer.size() <= max|| max == 0)){
				System.out.println(answer);
		}else if(max == -1 && phraseLetters.isEmpty()){
			System.out.println(answer);	
		}else{
			for(String word : avaWords){
				if(phraseLetters.contains(dictWords.get(word))){
					answer.add(word);
					phraseLetters.subtract(dictWords.get(word));
					explore(phraseLetters,avaWords,answer, max);
					phraseLetters.add(dictWords.get(word));
					answer.remove(answer.size()-1);
				}
			}
		}
	}
				
}
