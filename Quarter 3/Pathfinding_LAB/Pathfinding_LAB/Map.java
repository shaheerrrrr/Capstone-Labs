package Pathfinding_LAB;//Urav Tanna, Douglas Oberle, 2/2024
//The map of cells that are either paths or walls in which we are going to try to find the shortes path from a start point to and end point.
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map
{
   protected static final int DEFAULT_MAP_SIZE = 25;     //10-50, the dimensions of the map will have DEFAULT_MAP_SIZE rows and columns
   protected static final int DEFAULT_MAP_SEED = 6;      //seed value for the random number generator.  A seed of 6 will have the default start and end points open (not walls)
   protected static final int DEFAULT_DIFFICULTY = 30;   //0-100, the probability that any given cell will be a wall.  0 has no walls, 100 has all walls
   private Random rng;                                   //our random number generator
   private int seed;                                     //seed for random number gen for creating a map
   private int size;                                     //world of Tiles will be of dimensions size x size, initialized to DEFAULT_MAP_SIZE
   private Tile[][] world;                               //the map itself
   private int density;                                  //the density of walls when a map is created, initialized to DEFAULT_DIFFICULTY
   private JFrame frame;                                 //the frame that will hold our JPanel containerPanel
   protected JPanel containerPanel;                      //the panel that displays our map
   protected UI ui;                                      //User interface that has our buttons, text fields and map

   public Map(UI ui)
   {
      this.frame = ui.frame;
      this.size = ui.size;
      this.density = ui.density;
      this.seed = ui.seed;
      this.ui = ui;
      rng = new Random(seed);
      world = new Tile[size][size];
      ui.setMap(this);
   
      containerPanel = new JPanel();
      containerPanel.setLayout(null);
      containerPanel.setSize(frame.getWidth(),frame.getHeight());
   
      Dimension dimensions = frame.getSize();
      double startX = dimensions.getWidth() / 2f - dimensions.getHeight() / 2.23f;
      double endX = dimensions.getWidth() / 2f + dimensions.getHeight() / 2.23f;
      int currX = (int) startX;
      int currY = 0;
      int xIncrement = (int) (endX - startX) / size;
      int yIncrement = (int) (endX - startX) / size;
      for(int i = 0; i < world.length; i++)
      {
         for(int j = 0; j < world[i].length; j++)
         {
            if(rng.nextFloat() > 1 - (this.density/100.0))
            {                                            //make this Tile an obstacle (not traversable)
               world[i][j] = new Tile(xIncrement, currX,currY, j, world.length - i - 1, false, ui);
            }
            else
            {                                            //make this Tile a path (traversable)
               world[i][j] = new Tile(xIncrement, currX,currY, j, world.length - i - 1, true, ui);
            }
            containerPanel.add(world[i][j]);
            containerPanel.repaint();
            currX += xIncrement;
         }
         currX = (int) startX;
         currY += yIncrement;
      }
      resetHighlights();
      frame.add(containerPanel);
      frame.repaint();
   }
   
   //pre:  Point p is not null.
   //post: returns whether or not a grid location is in bounds of the defined map.
   public boolean onTheMap(Point p)
   {
      return (p.x < size && p.x >= 0 && p.y < size && p.y >= 0);
   }

   //pre:  Point p is not null.
   //post: returns true if a grid location is traversable (a path, white), returns false if it is an obstacle (a wall, red) .
   public boolean isTraversable(Point p)
   {
      int[] indices = pointToArrayIndex(p);
      return world[indices[0]][indices[1]].traversable;
   }

   public List<Point> getNeighbors(Point p)
   {
      List<Point> neighbors = new ArrayList<>();
      int[] directionx = {-1,0,1,0};
      int[] directiony = {0, -1, 0, 1};
      for (int i = 0; i < 4; i++)
      {
         Point neighbor = new Point(p.x + directionx[i], p.y + directiony[i]);
         if (onTheMap(neighbor) && isTraversable(p))
            neighbors.add(neighbor);
      }
      return neighbors;
   }

   //pre:  (row, col) represent a valid array index of the 2-D array in the map
   //post: return a Point pbject that represents that 2-D array index (row,col) pair
   private Point arrayIndexToPoint(int row, int col)
   {
      return new Point(col, world.length - row - 1);
   }
   
   //pre:  Point p contains a (row, col) pair that represents a valid array index of the 2-D array in the map
   //post: returns an int array with two elements that represents that 2-D array index (row,col) pair
   public int[] pointToArrayIndex(Point p)
   {
      return new int[] {world.length - p.y - 1, p.x};
   }

   //pre:  Point p and Color c are not null, Point p represents a valid (row,col) index of the 2-D array in the map
   //post: Sets the tile at position P to the color C, useful for debugging.
   public void highlightTile(Point p, Color c)
   {
      int[] indices = pointToArrayIndex(p);
      world[indices[0]][indices[1]].setBackground(c);
   }

   //pre:  Point List points and Color c are not null, all elements of points represent a valid (row,col) index of the 2-D array in the map
   //post: Sets all of the tiles in the points List to the color C, useful for debugging.
   public void highlightTiles(List<Point> points, Color c)
   {
      for(Point p : points)
      {
         highlightTile(p, c);
      }
      frame.repaint();
   }

   //post:  clears any highlighed tiles by setting all traversable tiles back to white
   public void resetHighlights()
   {
      for(int i = 0; i < world.length; i++)
      {
         for (int j = 0; j < world[i].length; j++)
         {
            if(world[i][j].traversable)
            {
               world[i][j].setBackground(Color.white);
            }
            else
            {
               world[i][j].setBackground(Color.red);
            }
         }
      }
      frame.repaint();
   }


   //pre:  file != null and points to a real file containing lines of text that describe a map of walls and paths
   //post: builds the map from the contents of the file and adjusts the ui size field
   public void loadFile(File file) throws IOException
   {
      String[] fileLines = Utilities.readFile(file);
      size = fileLines.length;
      int[][] newWorld = new int[size][size];
      for(int i = 0; i < fileLines.length; i++)
      {
         String[] split = fileLines[i].split(" ");
         for(int j = 0; j < split.length; j++)
         {
            newWorld[i][j] = Integer.parseInt(split[j]);
         }
      }  
      frame.remove(containerPanel);
      containerPanel = new JPanel();
      containerPanel.setLayout(null);
      containerPanel.setSize(frame.getWidth(),frame.getHeight()); 
      world = new Tile[size][size];
      Dimension dimensions = frame.getSize();
      double startX = dimensions.getWidth() / 2f - dimensions.getHeight() / 2.23f;
      double endX = dimensions.getWidth() / 2f + dimensions.getHeight() / 2.23f;
      int currX = (int) startX;
      int currY = 0;
      int xIncrement = (int) (endX - startX) / size;
      int yIncrement = (int) (endX - startX) / size;
      for(int i = 0; i < newWorld.length; i++)
      {
         for(int j = 0; j < newWorld[i].length; j++)
         {
            world[i][j] = new Tile(xIncrement, currX,currY, j, world.length - i - 1, newWorld[i][j] == 1, ui);
            containerPanel.add(world[i][j]);
            currX += xIncrement;
         }
         currX = (int) startX;
         currY += yIncrement;
      }
      frame.add(containerPanel);
      ui.size = size;
      ui.sizeText.setText("" + size);
      resetHighlights();
      frame.repaint();     
   }

   //post: builds the map of random walls, placed based on the density (0-100) which describes the probability that a cell is a wall
   public void regenMap()
   {
      size = ui.size;
      rng = new Random(ui.seed);
      frame.remove(containerPanel);
      containerPanel = new JPanel();
      containerPanel.setLayout(null);
      containerPanel.setSize(frame.getWidth(),frame.getHeight());  
      world = new Tile[size][size]; 
      Dimension dimensions = frame.getSize();
      double startX = dimensions.getWidth() / 2f - dimensions.getHeight() / 2.23f;
      double endX = dimensions.getWidth() / 2f + dimensions.getHeight() / 2.23f;
      int currX = (int) startX;
      int currY = 0;
      int xIncrement = (int) (endX - startX) / size;
      int yIncrement = (int) (endX - startX) / size;
      for(int i = 0; i < world.length; i++)
      {
         for(int j = 0; j < world[i].length; j++)
         {
            if(rng.nextFloat() > 1 - (this.density/100.0))
            {
               world[i][j] = new Tile(xIncrement, currX,currY, j, world.length - i - 1, false, ui);
            }
            else
            {
               world[i][j] = new Tile(xIncrement, currX,currY, j, world.length - i - 1, true, ui);
            }
            containerPanel.add(world[i][j]);
         
            currX += xIncrement;
         }
         currX = (int) startX;
         currY += yIncrement;
      }
      resetHighlights();
      frame.add(containerPanel);
      frame.repaint();
   }

   //post: return the dimension of the world of Tiles that is size x size
   public int getSize()
   {
      return size;
   }

   //pre:  s>=10 and s<=50
   //post: change the dimension of the world of Tiles that is size x size
   public void setSize(int s)
   {
      this.size = s;
      ui.sizeText.setText("" + s);
      ui.size = s;
   }
   
   //post: return the density of walls when a map is created
   public int getDensity()
   {
      return density;
   }

   //pre:  d>=0 and d<=100 
   //post: change the density of walls when a map is created
   public void setDensity(int d)
   {
      this.density = d;
      ui.densityText.setText("" + d);
      ui.density = d;
   }

   //post:  change the seed for random number gen for creating a map
   public void setSeed(int seed)
   {
      this.seed = seed;
      ui.seedText.setText("" + seed);
      ui.seed = seed;
   }

   public int getCost()
   {
      return 1;
   }

   //post: returns the map as a String where 1 represents a traversable path and 0 represents a wall
   @Override
   public String toString()
   {
      String str = "";
      for(int i = 0; i < world.length; i++)
      {
         for(int j = 0; j < world[i].length; j++)
         {
            if(world[i][j].traversable)
            {
               str += "1 ";
            }
            else
            {
               str += "0 ";
            }
         }
         str += "\n";
      }
      return str;
   }
}

