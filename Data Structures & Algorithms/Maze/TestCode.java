public class TestCode {
	
   //check test.union(5,7) then test.union(7,5)
	public static void main (String[] args){
		DisjointSets test = new MyDisjSets(9);
		test.union(6,8);
      test.union(6,9);
      test.union(3,6);
      //test.union(2,3);
      test.union(7,1);
      test.union(2,7);
      //test.printSet(2);
      //test.printSet(4);
      System.out.println(test.find(9));
      System.out.println(test.numElements(9));
      
	}
   
}
