/*Edwin Mak 3/14/13
 * Victoria Wagner Section BE
 * HW #8 HuffmanTree
 * In this assignment we create a HuffmanTree that helps 
 * compress a txt file and also decode a compressed txt file.
 */
 
import java.util.*;
import java.io.*;

public class HuffmanTree {

	private HuffmanNode overallRoot;

	//makes a new tree based on the frequency of the characters that appear in the file,
	//  a character is given a value which is represented by counts[i] 
	public HuffmanTree(int[] counts){
		Queue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
		for(int i = 0; i < counts.length ; i++){
			if(counts[i] != (0)){
				pq.add(new HuffmanNode(i, counts[i]));
			}
		}
		pq.add(new HuffmanNode(counts.length, 1));
		while(pq.size() > 1){
			HuffmanNode first = pq.remove();
			HuffmanNode second = pq.remove();
			pq.add(new HuffmanNode(-1, first.freq + second.freq, first, second));
		}
		overallRoot = pq.remove();
	}
	
	//writes the tree into a file using a standard format where
	//  one line representds the value of the character followed by 
	//  a binary line of code where a 0 is to go left and a 1 is to go right
	public void write(PrintStream output) {
		String binary = "";
		write(output,overallRoot,binary);
	}
	
	//writes the tree into a file using a standard format
	private void write(PrintStream output, HuffmanNode root,String binary){
		if(root.right == null && root.left == null){
			output.println(root.character);
			output.println(binary);
		}else{
			write(output, root.left, binary + "0");
			write(output, root.right, binary + "1");
		}
	}
	
	//constructs a tree based on the passed code file
	//assumes standard format of the code file
	public HuffmanTree(Scanner input) {
		while(input.hasNext()){
			int chars = Integer.parseInt(input.nextLine());
			String binary = input.nextLine();
			overallRoot = buildTree(overallRoot, binary, chars);
		}
	}
	
	//builds a tree based on the passed code file
	//freqeuncy in the nodes are considered insignificant and are given a value of -1
	private HuffmanNode buildTree(HuffmanNode root,String binary, int chars){
		if(binary.length() == 0){
			return new HuffmanNode(chars, -1);
		}else{
			if(root == null){
				root = new HuffmanNode(0, -1);
			}
			if(binary.charAt(0) == '0') {
				root.left = buildTree(root.left, binary.substring(1), chars);
			}else {
				root.right = buildTree(root.right, binary.substring(1), chars);
			}
		}
		return root;
	}
	
	//decompresses the passed code file
	//writes the characters using the output
	//stops reading when it reaches the eof value
	public void decode(BitInputStream input, PrintStream output, int eof){
		int chars = getChar(overallRoot, input);
		while(chars != eof){
			output.write(chars);
			chars = getChar(overallRoot, input);
		}
	}
	
	//finds the character based on its value
	public int getChar(HuffmanNode root, BitInputStream input){
		while ((root.left != null) && (root.right != null)){
			if(input.readBit() == 0){
				root = root.left;
			}else{
				root = root.right;
			}
		}
		return root.character;
	}

	// A node used by the class HuffmanTree that stores character value and frequency
	public class HuffmanNode implements Comparable<HuffmanNode>{
		private HuffmanNode left;
		private HuffmanNode right;
		private int freq;								//how many times a character shows up
		private int character;						//the value of the character
		
		//constructs a new leaf node of HuffmanNode
		public HuffmanNode(int character, int freq){
			this(character, freq, null, null);
		}
		
		//constructs a new branch node of HuffmanNode given the character value, frequency,
		//left/right subtrees.
		public HuffmanNode(int character, int freq, HuffmanNode left, HuffmanNode right){
			this.freq = freq;
			this.character = character;
			this.left = left;
			this.right = right;
		}
		
		// a HuffmanNode is larger or smaller than one other based on its frequency value
		// and equal if they have the same frequency
		public int compareTo(HuffmanNode otherHuffmanNode){
			return this.freq - otherHuffmanNode.freq;
		}
	}
}