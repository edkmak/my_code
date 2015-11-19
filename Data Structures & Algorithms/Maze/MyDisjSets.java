/*CSE 373 HW #4 A-Maze-ing Union Find
* Edwin Mak 11/7/13
* This program implements a DisjointSets data structure, which allows the 
* user to union, find, check if setName, check number of elements, check number of 
* elements in given set, print set, and get the elements
*/

public class MyDisjSets implements DisjointSets{

   private int[] up;               //refers to up tree
	
   
	//creates a number of DisjSets given a number of elements
	public MyDisjSets(int numElements){ 
		up = new int [numElements];
      for(int i = 0; i < up.length; i++){
         up[i] = -1;
      }
	} 
	
   //given two sets, combines them into one set 
   //throws new InvalidElementException if the number is not an acceptable number
   //throws new InvalidSetNameException if number is not a set name
	public void union(int set1, int set2){
      if(set1 <= 0 || set2 <= 0 ||  set1 > up.length || set2 > up.length){
         throw new InvalidElementException();
      }   
      if(!isSetName(set1) || !isSetName(set2)){
         throw new InvalidSetNameException("sets must be set names");
      }if(up[set1 - 1] > up[set2 - 1]){                   //set 2 larger
         up[set2 - 1] = up[set1 - 1] + up[set2 - 1];
   		up[set1 - 1] = set2;
      }else{
         up[set1 - 1] = up[set1 - 1] + up[set2 - 1];
   		up[set2 - 1] = set1;
      }   
	}
	
   //finds which set the given number belongs to
   //throws new InvalidElementException if element is not an acceptable number
	public int find(int x){
      if(x <= 0 || x > up.length){
         throw new InvalidElementException();
      }                    
		int z = x;
		while(up[x-1] > 0){
         x = up[x-1];
      }
      int oldParent = up[z - 1];
      while(z != x){
         up[z - 1] = x;
         z = oldParent;
         oldParent = up[z - 1];
      }
      return x;
	}
   
   /*public int find(int i){
      if(i <= 0 || i > up.length){
         throw new InvalidElementException();
      }                    
		int r = i;
		while(up[r - 1] > 0){
         r = up[r - 1];
      }
      if(i == r){
         return r;
      }
      int oldParent = up[i - 1];
      while(oldParent != r){
         up[i - 1] = r;
         i = oldParent;
         oldParent = up[i - 1];
      }
      return r;
	}*/
	
   
   //returns total number of sets in the entire disjoint set
	public int numSets(){
		int count = 0;
		for(int i = 0; i < up.length ; i++){
			if(up[i] < 0){
				count++;
			}
		}
      return count;
	}
	
   //Returns true if given number is a name of a set, false if not
   //throws new InvalidElementException if element is not an acceptable number
	public boolean isSetName(int x){
      if(x < 0 || x > up.length){
         throw new InvalidElementException();
      }
      return (up[x - 1] < 0);
	}
   
   //Returns the number of elements in given set
   //throws new InvalidElementException if the number is not an acceptable number
   //throws new InvalidSetNameException if number is not a set name
   public int numElements(int setNum){
      if(setNum < 0 || setNum > up.length){
         throw new InvalidElementException();
      }
      if(!isSetName(setNum)){
         throw new InvalidSetNameException("number must be a set name");
      }
      return(-1 * up[setNum - 1]);
   }
   
   
   //prints the elements in the given set
   //throws new InvalidElementException if the number is not an acceptable number
   //throws new InvalidSetNameException if number is not a set name
   public void printSet(int setNum){
      if(setNum < 0 || setNum > up.length){
         throw new InvalidElementException();
      }
      if(!isSetName(setNum)){
         throw new InvalidSetNameException("number is not a set name");
      }
      int [] temp = getElements(setNum);
      System.out.print("{");
      for(int i = 0; i < temp.length ; i++){
         if(i == temp.length - 1){
            System.out.print(temp[i] + "}");
         }else {
            System.out.print(temp[i] + ", ");
         }
      }
   }
   
   //returns the elements of the given set in the form of an array
   //throws new InvalidElementException if the number is not an acceptable number
   //throws new InvalidSetNameException if number is not a set name
   public int [] getElements(int setNum){
      if(setNum < 0 || setNum > up.length){
         throw new InvalidElementException();
      }
      if(!isSetName(setNum)){
         throw new InvalidSetNameException("number is not a set name");
      }
      int [] temp = new int[numElements(setNum)];
      int j = 0;
      for(int i = 1; i <= up.length; i++){
         if(find(i) == setNum){
            temp[j] = i;
            j++;
         }
      }
      return temp;
   }

}



