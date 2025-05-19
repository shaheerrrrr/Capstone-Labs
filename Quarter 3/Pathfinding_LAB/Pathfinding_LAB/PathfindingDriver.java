package Pathfinding_LAB;//Urav Tanna, Douglas Oberle, 2/2024
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class PathfindingDriver
{
   public static void main(String[] args) throws IOException
   {
      UI ui = new UI();
      Map map = new Map(ui);
        /*if you want to load a map immediately after the program starts do it like this:
        //--------------------------------------------------------------------------------
        map.loadFile(new File("maps\\finalboss_144_moves.plmap"));
        //--------------------------------------------------------------------------------
        //*/
   }
   private static List<Point> reconstructPath(HashMap<Point, Point> cameFrom, Point current)
   {
      List<Point> path = new ArrayList<>();
      path.add(current);

      while (cameFrom.containsKey(current))
      {
         current = cameFrom.get(current);
         path.addFirst(current);
      }
      return path;
   }
   //pre:   map, initial and destination are not null.
   //       initial and destination have (x,y) values that represent valid (row,col) indexes of the 2D-array in map.
   //post:  returns a List of Point objects that mark the shorted path in the map from initial to destination, avoiding obstacles. 
   //called in UI.java
   //*************************************YOUR SOLUTION GOES HERE*************************************
   protected static boolean activatePathButton1 = true;
   public static List<Point> shortestPath1(Map map, Point initial, Point destination)
   {
      Queue<Point> queue = new LinkedList<>();
      HashMap<Point, Point> previousLocations = new HashMap<>();
      Set<Point> visited = new HashSet<>();

      queue.add(initial);
      visited.add(initial);

      while (!queue.isEmpty()) {
         Point current = queue.poll();

         if (current.equals(destination))
            return reconstructPath(previousLocations, destination);

         for (Point neighbor : map.getNeighbors(current))
         {
            if (!visited.contains(neighbor))
            {
               visited.add(neighbor);
               previousLocations.put(neighbor, current);
               queue.add(neighbor);
            }
         }
      }
      return new ArrayList<>();
   }
    
    //****If you wish to test more than one algorithm and compare/contrast results, activate the appropriate buttons and complete the methods for up to 4 different algorithms*** 
   protected static boolean activatePathButton2 = true;     //set to true to activate the button in the UI that will run this method
   public static List<Point> shortestPath2(Map map, Point initial, Point destination)
   {
      PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
      HashMap<Point, Double> distances = new HashMap<>();
      HashMap<Point, Point> cameFrom = new HashMap<>();
      Set<Point> visited = new HashSet<>();

      distances.put(initial, 0.0);
      pq.add(new Node(initial, 0.0));

      while (!pq.isEmpty())
      {
         Point current = pq.poll().point;

         if (current.equals(destination))
            return reconstructPath(cameFrom, destination);

         if (visited.contains(current))
            continue;
         visited.add(current);

         for (Point neighbor : map.getNeighbors(current))
         {
            double newDist = distances.get(current) + Math.sqrt(Math.pow(Math.abs(current.getX()-neighbor.getX()),2) + Math.pow(Math.abs(current.getY()-neighbor.getY()),2));
            if (newDist < distances.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
               distances.put(neighbor, newDist);
               cameFrom.put(neighbor, current);
               pq.add(new Node(neighbor, newDist));
            }
         }
      }
      return new ArrayList<>(); // No path found
   }
   
   protected static boolean activatePathButton3 = false;       //set to true to activate the button in the UI that will run this method
   public static List<Point> shortestPath3(Map map, Point initial, Point destination)
   {
      ArrayList<Point> points = new ArrayList<>();
      return points;
   }
   
   protected static boolean activatePathButton4 = false;       //set to true to activate the button in the UI that will run this method
   public static List<Point> shortestPath4(Map map, Point initial, Point destination)
   {
      ArrayList<Point> points = new ArrayList<>();
      return points;
   }
   private static class Node {
      Point point;
      double distance;  // Used for Dijkstra's
      double fScore;    // Used for A*

      Node(Point point, double score) {
         this.point = point;
         this.distance = score;
         this.fScore = score;
      }
   }
}