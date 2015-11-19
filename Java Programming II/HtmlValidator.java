/*Edwin Mak 1/24/13
 *Victoria Wagner Section BE
 *HW #2 HTML Validator
 *In this assignment we are make a program that checks the HTML tags 
 *of websites for thier validity. 
 */
 
import java.util.*;

public class HtmlValidator {

	private Queue <HtmlTag> queue;
	
	//makes a new queue of tags
	public HtmlValidator() {
		queue = new LinkedList <HtmlTag>();
	}
	
	//copies the passed tags to a new queue
	//throws IllegalArugmentException if passed tag is null
	public HtmlValidator(Queue<HtmlTag> tags) {
		if(tags == null){	
			throw new IllegalArgumentException("Cannot be null");
		}
		this.queue = tags;
	}
	
	//adds a user inputed tag onto the queue
	//throws IllegalArugmentException if passed tag is null
	public void addTag(HtmlTag tag) {
		if (tag == null){
			throw new IllegalArgumentException();
		}
		queue.add(tag);
	}

	//returns a queue of the tags
	public Queue<HtmlTag> getTags() {
		return queue;
	}
	
	//removes a certain tag from the list
	//input desired element without brackets
	public void removeAll (String Element) {
		if (Element == null) {
			throw new IllegalArgumentException("Cannot be null");
		}
		String target = Element;
		int qlength = queue.size();
		for(int i = 0; i < qlength; i++) {
			if(!(queue.peek().getElement().equals(target))) {
				queue.add(queue.remove());
			}else {
				queue.remove();
			}
		}
	}
	
	//prints text of the tags according to type and order
	//Throws errors when queue contains unclosed tags, tags that are misplaced,
	//and extra tags.
	public void validate() {
		Stack <HtmlTag> unclosed = new Stack <HtmlTag>();
		int indentTimes = 0;
		for(int i = 0; i < queue.size(); i++){
			if(queue.peek().isOpenTag()) {						
				if(queue.peek().isSelfClosing()) {		
					indent(indentTimes);												
					System.out.println("" + queue.peek());
					queue.add(queue.remove());
				}else {											
					indent(indentTimes);
					System.out.println("" + queue.peek());
					indentTimes++;
					unclosed.push(queue.peek());
					queue.add(queue.remove());
				}
			}else if(unclosed.size()!= 0 && queue.peek().matches(unclosed.peek())) { 
				indentTimes--;
				indent(indentTimes);
				System.out.println("" + queue.peek());
				unclosed.pop();
				queue.add(queue.remove());
			}else {
				System.out.println("ERROR unexpected tag: " + queue.peek());
				queue.add(queue.remove());
			}
		}
		while(!unclosed.isEmpty()) {           									//errors the unclosed tags
			System.out.println("ERROR unclosed tag: " + unclosed.pop());
		}
	}

	//prints an indent
	private static void indent(int indentTimes) {
		for(int i = 0; i < indentTimes; i++) {
			System.out.print("    ");
		}
	}
}