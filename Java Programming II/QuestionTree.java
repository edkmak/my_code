/*Edwin Mak 2/28/13
 * Victoria Wagner Section BE
 * HW #7 QuestionTree
 * In this assignment we create a QuestionTree to find 
 * play a game of 20 Questions with the user. 
 */

import java.io.*;
import java.util.*;

public class QuestionTree{

	private QuestionNode overallRoot;
	private UserInterface ui;
	private int totalGames;
	private int gamesWon;

	//constructs a new QuestionTree with an object "computer"
	//throws IllegalArgumentException if passed UserInterface is null
	public QuestionTree(UserInterface ui){
		if(ui == null){
			throw new IllegalArgumentException();
		}
		overallRoot = new QuestionNode("computer");
		this.ui = ui;
	}
	
	//plays a game of 20 Questions with the user
	public void play(){
		overallRoot = play(overallRoot);
	}
	
	////plays a game of 20 Questions with the user,by asking y/n questions
	//If the computer loses the game, expands the current tree by 
	//asking the user for a new object and a a distinguishing question
	private QuestionNode play(QuestionNode root){
		if(root.right == null && root.left == null){
			totalGames++;
			ui.print("Would your object happen to be " + root.data + "?");
			if(ui.nextBoolean()) {
				ui.println("I win!");
				gamesWon++;
				return root;
			}else{
				ui.print("I lose. What is your object?");
				String newAnswer = ui.nextLine();
				ui.print("Type a yes/no question to distinguish your item from " + 
								root.data + ":");
				String newQuestion = ui.nextLine();
				ui.print("And what is the answer for your object?");
				String answeryn = ui.nextLine();
				return makeQuestionNode(newAnswer,newQuestion,root,answeryn);
			}
		}else{
			ui.print(root.data);
			if(ui.nextBoolean()){
				root.left = play(root.left);
			}else{
				root.right = play(root.right);
			}
		}
		return root;
	}
	
	//makes a new QuestionNode when the computer loses a game based on a new object,
	//a distinguishing question, and the answer to the question. 
	private QuestionNode makeQuestionNode(String newAnswer, String newQuestion,QuestionNode root,String answeryn){
		QuestionNode newQuestionNode = new QuestionNode(newQuestion);
		if(answeryn.toLowerCase().startsWith("y")){
			newQuestionNode.left = new QuestionNode(newAnswer);
			newQuestionNode.right = new QuestionNode(root.data);
		}else{
			newQuestionNode.right = new QuestionNode(newAnswer);
			newQuestionNode.left = new QuestionNode(root.data);
		}
		return newQuestionNode;
		
	}
	
	//saves the tree into a file in a preorder order
	//"Q:" represents a question, and "A:" represents an answer
	//throws IllegalArgumentException if output is null
	public void save(PrintStream output){
		if(output == null){
			throw new IllegalArgumentException();
		}
		save(output, overallRoot);
	}
	
	//saves the tree into a file 
	private void save(PrintStream output, QuestionNode root){
		if(root.left == null && root.right == null){
			output.print("A:");
			output.println(root.data);
		}else{
			output.print("Q:");
			output.println(root.data);
			save(output,root.left);
			save(output,root.right);
		}
	}
	
	//retreives a file and replaces the current tree with the data from the new file
	//assumes file exists and is in proper format
	public void load(Scanner input){
		if(input == null){
			throw new IllegalArgumentException();
		}
		overallRoot = loadHelper(input);
	}
	
	private QuestionNode loadHelper(Scanner input){
		String line = input.nextLine();
		String [] tokens = line.split(":");
		QuestionNode tempNode = new QuestionNode(tokens[1]);
		if(tokens[0].startsWith("Q")){
			tempNode.left = loadHelper(input);
			tempNode.right = loadHelper(input);
		}
		return tempNode;
	}
	
	//return total nuber of games played
	public int totalGames(){
		return totalGames;
	}
	
	//returns the number of games won by the computer
	//(i.e. when the computer guesses the correct word)
	public int gamesWon(){
		return gamesWon;
	}
	
}