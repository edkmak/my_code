/*Edwin Mak 2/7/13
 * Victoria Wagner Section BE
 * HW #4 GrammarSolver
 * In this assignment use a program calledGrammarSolver 
 * to generate a sentence, word or phrase from a given set of rules.
 */

import java.util.*;

public class GrammarSolver {

	private Map<String, String[]> gramTerms;    //to access map

	// takes given list of rules and stores them according to nonterminal
	// throws an IllegalArgumentException if the rules size is 0, rules is null,
   // or if there are two or more entries in the rules for the same nonterminal 
	public GrammarSolver(List<String> rules) {
		if(rules.size() == 0 || rules == null) {
			throw new IllegalArgumentException();
		}
		gramTerms = new TreeMap<String, String[]>();
		for(String s : rules) {
			String [] tokens = s.split("::=");
			if (gramTerms.containsKey(tokens[0])){
            throw new IllegalArgumentException("Cannot have same entry for nonterminal : "
				+ tokens[0]);
			}
			gramTerms.put(tokens[0], tokens[1].trim().split("[|]"));
		}
	}
		
	// returns true if symbol is a non-terminal, and false otherwise	
	// throws IllegalArgumentException passed string is null or has length of 0
	public boolean contains(String symbol) {
		if(symbol == null || symbol.length() == 0) {
			throw new IllegalArgumentException("Symbol cannot be null or size 0");
		}
		return gramTerms.containsKey(symbol);
	}
	
	// returns a set of non-terminals in sorted order
	public Set<String> getSymbols() {
		return gramTerms.keySet();
	}
	
	// returns a string of terminal/s according to the given rules.
   // throws an IllegalArgumentException if the passed string is null 
	//	or has length of 0
	public String generate(String symbol) {
		if(symbol == null || symbol.length() == 0) {
			throw new IllegalArgumentException("Symbol cannot be null or size 0");
		}
		if(!contains(symbol)) {
			return symbol;
		}else {
			String[] choices = gramTerms.get(symbol);
			Random rand = new Random();
			int randomNumber = rand.nextInt(choices.length);
			String target = (choices[randomNumber]);
			String phrase = "";
         for (String s : target.trim().split("[ \t]+")) {
				if(!contains(s)) {
					phrase += " ";
				}
         	phrase += generate(s);
			}
			return phrase;
		}
	}
	
}