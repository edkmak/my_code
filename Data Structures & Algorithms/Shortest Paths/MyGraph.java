//This is an example of a homework assignment I completed to implement Dijkstra's Algorithm 
//using graphs.
//*doesn't run without some of the necessecary classes

/*Edwin Mak
CSE 373 Hw #5
This program represents a graph which allows the client to 
get all of the vertices, get all of the edges, see all the 
adjacent vertices, calculate the cost of an edge, and find the
shortest path between two vertices. Assumes we don't have 
negative cost edges in the graph.
*/

import java.util.*;
import java.util.ArrayList;


public class MyGraph implements Graph {
   
     private Map<Vertex,List<Edge>> vertexWeights;       

    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e){
       vertexWeights = new HashMap<Vertex, List<Edge>>();
       for(Vertex temp : v){
          List<Edge> adjList = new ArrayList<Edge>();
          if(!vertexWeights.containsKey(temp)){
            vertexWeights.put(temp,adjList);
          }
       }
       for(Edge temp: e){
          if(!vertexWeights.containsKey(temp.getSource()) || temp.getWeight() < 0 
                                 || !vertexWeights.containsKey(temp.getDestination())){
             throw new IllegalArgumentException("edge not valid");     
                        //edge not even in collection of vertices or weight of edge is negative
          }
          List<Edge> vAdjList = vertexWeights.get(temp.getSource());
          for(Edge a: vAdjList){                    
            if(a.getSource().equals(temp.getSource()) && a.getDestination().equals(temp.getDestination())
               && a.getWeight() != temp.getWeight()){      // same edge but different weights
               throw new IllegalArgumentException("cannot have same edge with different weights");
            }
          }
          if(!vAdjList.contains(temp)){             //dont add repeated edge with same weight
             vAdjList.add(temp);
          }
       }
    }

    /** 
     * Returns the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
    public Collection<Vertex> vertices() {
         List<Vertex> copy = new ArrayList<Vertex>();
         for(Vertex v: vertexWeights.keySet()){
            copy.add(v);
         }
         return copy;
    }

    /** 
     * Return the collection of edges of this graph
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {
         List<Edge> edg = new ArrayList<Edge>();
         for(Vertex v : vertexWeights.keySet()){
            List temp = vertexWeights.get(v);
            edg.addAll(temp);
         }
         return edg;
    }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
     
    public Collection<Vertex> adjacentVertices(Vertex v) {
      if(!vertexWeights.containsKey(v)){
         throw new IllegalArgumentException();
      }
      ArrayList<Vertex> adjVerts = new ArrayList<Vertex>();
      List<Edge> destList = vertexWeights.get(v);
      for(Edge e : destList){
         adjVerts.add(e.getDestination());
      }
      return adjVerts;
    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {
      if(!vertexWeights.containsKey(a) || !vertexWeights.containsKey(b)){
         throw new IllegalArgumentException();
      }
      Collection<Vertex> adj = new ArrayList<Vertex>();
      adj = adjacentVertices(a);
      if(adj.contains(b)){
         List<Edge> z = vertexWeights.get(a);   
         for(Edge e: z){
            if(e.getDestination().equals(b)){
               return e.getWeight();
            }
         }
      }
      return -1;
    }

    /**
     * Returns the shortest path from a to b in the graph, or null if there is
     * no such path.  Assumes all edge weights are nonnegative.
     * Uses Dijkstra's algorithm.
     * @param a the starting vertex
     * @param b the destination vertex
     * @return a Path where the vertices indicate the path from a to b in order
     *   and contains a (first) and b (last) and the cost is the cost of 
     *   the path. Returns null if b is not reachable from a.
     * @throws IllegalArgumentException if a or b does not exist.
     */
     
    public Path shortestPath(Vertex a, Vertex b) {
       if(!vertexWeights.containsKey(a) || !vertexWeights.containsKey(b)){
         throw new IllegalArgumentException();
       }
       Collection<Vertex> unknown = new ArrayList<Vertex>();     
       unknown = vertices();
       
       Map<Vertex,Vertex> previous = new HashMap<Vertex,Vertex>();
       Map<Vertex,Integer> costs = new HashMap<Vertex,Integer>();
       for(Vertex v: vertexWeights.keySet()){
          costs.put(new Vertex(v.getLabel()), Integer.MAX_VALUE);
       }
       
       costs.put(a,0);
       PriorityQueue pq = new BinaryHeap(previous,costs);
       addVerticestoPQ(pq);
       while(!pq.isEmpty()){
          Vertex currNode = pq.deleteMin();
          unknown.remove(currNode);
          List<Edge> adjList = new ArrayList<Edge>();
          adjList = vertexWeights.get(currNode);
          for(Edge pathOption: adjList){
             Vertex optionDest = pathOption.getDestination();
             int distThroughTarget = costs.get(currNode) + pathOption.getWeight();
             if(unknown.contains(optionDest) && distThroughTarget < costs.get(optionDest)){
                pq.decreaseKey(optionDest, distThroughTarget);
                previous.put(optionDest,currNode);
             }
          }
       }
       return findShortestPath(b, previous, costs);
    }
   
   //adds vertices to priority queue
   private void addVerticestoPQ(PriorityQueue pq){
      for(Vertex v: vertexWeights.keySet()){
         pq.insert(v);
      }
   }
   
   //returns a Path given a vertex, map of the costs, and map of the paths.
   //returns null if there is no path from target to destination.
   private Path findShortestPath(Vertex dest,Map<Vertex,Vertex> previous, 
                                 Map<Vertex,Integer> costs){
      List<Vertex> shortestPath = new ArrayList<Vertex>();
      int cost = costs.get(dest);
      shortestPath.add(dest);
      Vertex vertex = dest;
      while(previous.get(vertex) != null){
         shortestPath.add(previous.get(vertex));
         vertex = previous.get(vertex);
      }
      Collections.reverse(shortestPath);
      Path path = new Path(shortestPath, cost);
      if(path.cost == Integer.MAX_VALUE){
         path = null;
      }
      return path; 
   }
  
}
