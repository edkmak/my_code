/*CSE 373 HW#4 A-Maz-ing Union Find
* Edwin Mak 11/7/13
* This program outputs a maze given a height and a width
* dimension
*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;


public class MazeBuilder {

   public static void main(String[] args){
      
      if(args.length != 3) {
            System.err.println(" Incorrect number of arguments");
            System.err.println(" Usage: ");
            System.err.
            println("\tjava MazeBuilder <height> <width> <output file>");
            System.exit(1);
      }
      int height = Integer.parseInt(args[0]);
      int width = Integer.parseInt(args[1]);
      if(height <= 0 || width <= 0){
            System.err.println("Dimensions must be greater than 0"); 
            System.exit(1);
      }

      try{
        
         PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(args[2])));
         int edges = (height * (width - 1)) + ((height - 1) * width);
            
         DisjointSets connectedSets = new MyDisjSets (height * width);
         ArrayList<Set> virginSets = new ArrayList<Set>(); //unprocessed internal edges
         ArrayList<Set> inMazeSets = new ArrayList<Set>(); //edges that will print 
            
         Random generator = new Random();
         initializeV(height, width, virginSets);
         
         while(connectedSets.numSets() > 1){
            int rand = generator.nextInt(virginSets.toArray().length);
            Set temp = virginSets.get(rand);
            int first = temp.getX();
            int second = temp.getY();
                  
            int v = connectedSets.find(first);
            int u = connectedSets.find(second);
                      
            if(v == u){
                Set z = new Set(first, second);
                inMazeSets.add(z);
            }else{
                connectedSets.union(v,u);
            }
            virginSets.remove(temp);
         }
            
         //add the virgins to the M
         virginToMaze(virginSets, inMazeSets);
           
         //output Maze
         outputMaze(height, width, inMazeSets, fileOut);
            
         //close the file
         fileOut.close();
 
      }
        catch(IOException ioe) {
            System.err.println
            ("Error opening/reading/writing input or output file.");
            System.exit(1);
        }
        catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
    }
    
    
   //puts all the internal edges into virginSets
    public static void initializeV(int height, int width, ArrayList virginSets){
       int cell = 1;
       for(int i = 1; i <= height * width; i++){
          int x = cell;
          int y = cell + 1;                    //internal edge to the right
          int under = cell + width;            //internal edge underneath
          if(under <= height * width){
             Set temp = new Set(x, under);
             virginSets.add(temp);
          } 
          if(cell % width == 0){               
            cell++;                  
          }else{
             Set temp = new Set(x, y);
             virginSets.add(temp);
             cell++;
          }
       }
    }
    
    //inserts the the remaining unchosen edges into the inMazeSets to be printed
    public static void virginToMaze(ArrayList virginSets, ArrayList inMazeSets){
      int size = virginSets.toArray().length;
      for(int i = 0; i < size; i++){
         inMazeSets.add(virginSets.remove(0));
      }
    } 
    
    //prints the maze
    public static void outputMaze(int height, int width, ArrayList inMazeSets,
                                  PrintWriter fileOut)
      {
      int i = 0;                                            //i = row # , j = column #
      int cell = 0;
      int betweenRows = 1;                                  //spaces between rows
      while(i <= height * 2){
         for(int j = 0; j <= width * 2; j++){                          
            if( j == 0 && i  == 1 ){                        //inital openings at corners
               fileOut.print(" ");
            }
            else if( i == height * 2 - 1 && j == width * 2){
               fileOut.print(" ");
            }
            
            else if(j % 2 == 0 && i % 2 == 0){              //i and j both even,a plus always
               fileOut.print("+");
            }
            else if( i % 2 == 0 && j % 2 == 1){             //even i, odd j
               Set temp = new Set (betweenRows, betweenRows + width);
               if(i == 0 || i == height * 2){               // "-" on the border
                  fileOut.print("-");
               }
               else if(inMazeSets.contains(temp)){
                  fileOut.print("-");
                  betweenRows++;
               }else {
                  fileOut.print(" ");
                  betweenRows++;
               } 
            }
            else if(j % 2 == 0 && i % 2 == 1){              //even j odd i
               Set temp = new Set (cell, cell + 1);
               if(inMazeSets.contains(temp) || j == 0 || j == width * 2){
                  fileOut.print("|");
               }else{                            //print space if not a border or a valid wall 
                  fileOut.print(" ");
               }
            }
            else {                                         //i and j both odd, a space always
               fileOut.print(" ");
               cell++;
            }
         }
         i++;                                    //advances to next row
         fileOut.println();
      }
   }
  
    //To represent internal edges as sets
    public static class Set{
    
      private int x;    //refers to first cell
      private int y;    //refers to second cell
      
      //creats a new set given two cells
      public Set(int x, int y){
         this.x = x;
         this.y = y;
      }
      
      //returns first cell of set
      public int getX(){
         return x;
      }
      
      //returns second cell of set
      public int getY(){
         return y;
      }
      
      //determines whether or not two sets contain same cells
      @Override  
      public boolean equals (Object obj){
         if( obj == null){
            return false;
         }
         final Set other = (Set) obj;
         if( this.x != other.x || this.y != other.y){
            return false;
         }
         return true;
      }
    }
      
    
} 



