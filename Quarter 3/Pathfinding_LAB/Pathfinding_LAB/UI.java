package Pathfinding_LAB;//Urav Tanna, Douglas Oberle, 2/2024
//Stores the user-interface information like the map that is shown, buttons to press and information the client needs to see.
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
//870896112
public class UI
{
   protected JFrame frame;                            //the frame that contains the information we want to show in buttonsPanel                 
   protected JPanel buttonsPanel;                     //the canvas in which we will place our text, buttons and map
   protected Map map;                                 //the map of walls and paths
   protected JTextField sizeText;                     //will let the client type in a new map size between 10 and 50
   protected JTextField seedText;                     //will let the client pick a new seed for the random number generator
   protected JTextField densityText;                  //will let the client type in a new map wall density between 0 and 100
   protected int size;                                //the map dimensions (size x size)
   protected int density;                             //the density of walls in the map
   protected int seed;                                //the seed for the random number generator
   protected Point entrance, exit;                    //the start and stop points for the pathfinding algorithm to connect with the least number of moves
   protected JLabel mousePosition;                    //used to display the row,col position of the mouse on the map
   protected JLabel controlsLabel1, controlsLabel2;   //used to display the information on what the mouse buttons do (left-click, right-click)
   protected JLabel entranceLabel, exitLabel;         //used to display where the start and stop points for the pathfinding are (default to lower-left and upper-right corners)
   protected JLabel pathLengthLabel;                  //used to let the client know how many steps their pathfinding algorithm made
   protected JLabel timeLabel;                        //used to let the client know how much time their pathfinding algorithm took to run
 
   public UI()
   {
      frame = new JFrame("PathfindingLab");
      frame.setSize(1000,800);
      frame.setLayout(null);
   
      JMenu menu;
      JMenuItem load = new JMenuItem(new LoadAction());
      JMenuItem save = new JMenuItem(new SaveAction());
      JMenuBar mb = new JMenuBar();
      menu = new JMenu("File");
      menu.add(load);
      menu.add(save);
      mb.add(menu);
      frame.setJMenuBar(mb);
   
      buttonsPanel = new JPanel();
      buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
      buttonsPanel.setBounds(0,0, 141, 800);
   
      JLabel controlsLabel1 = new JLabel("L-click:walls on and off");      //displays instructions on what mouse clicks do
      buttonsPanel.add(controlsLabel1);
      JLabel controlsLabel2 = new JLabel("R-click:start/end points");
      buttonsPanel.add(controlsLabel2);
   
      if(PathfindingDriver.activatePathButton1)
      {
         JButton startButton1 = new JButton("Breadth First Search");                //button to run the shortestPath1 algorithm
         startButton1.addActionListener(new StartAction1());
         buttonsPanel.add(startButton1);
      }
      if(PathfindingDriver.activatePathButton2)
      {
         JButton startButton2 = new JButton("Dijkstra's Algorithm");                //button to run the shortestPath2 algorithm
         startButton2.addActionListener(new StartAction2());
         buttonsPanel.add(startButton2);
      }
      if(PathfindingDriver.activatePathButton3)
      {
         JButton startButton3 = new JButton("Find path 3");                //button to run the shortestPath3 algorithm 
         startButton3.addActionListener(new StartAction3());
         buttonsPanel.add(startButton3);
      }
      if(PathfindingDriver.activatePathButton4)
      {
         JButton startButton4 = new JButton("Find path 4");                //button to run the shortestPath4 algorithm 
         startButton4.addActionListener(new StartAction3());
         buttonsPanel.add(startButton4);
      }
   
      JButton resetButton = new JButton("Clear  path");                     //button to clear markers for the path found
      resetButton.addActionListener(new ResetAction());
      buttonsPanel.add(resetButton);
   
      JButton regenButton = new JButton("New    map");                      //button to create a new random map
      regenButton.addActionListener(new RegenAction());
      buttonsPanel.add(regenButton);
   
      JLabel sizeLabel = new JLabel("Map dimension (10-50):");                  
      buttonsPanel.add(sizeLabel);
      sizeText = new JTextField("" + Map.DEFAULT_MAP_SIZE);                //field so client can enter a new map size (10-50)
      sizeText.setPreferredSize( new Dimension(200,30));
      sizeText.setMaximumSize(sizeText.getPreferredSize());
      buttonsPanel.add(sizeText);
      
      JLabel densityLabel = new JLabel("Wall complexity (0-100):");
      buttonsPanel.add(densityLabel);
      densityText = new JTextField("" + Map.DEFAULT_DIFFICULTY);           //field so client can enter a new wall complexity (0-100)
      densityText.setPreferredSize( new Dimension(200,30));
      densityText.setMaximumSize(densityText.getPreferredSize());
      buttonsPanel.add(densityText);
   
      JLabel seedLabel = new JLabel("Map seed:");
      buttonsPanel.add(seedLabel);
      seedText = new JTextField("" + Map.DEFAULT_MAP_SEED);                //field so client can enter a new seed for the random # generator
      seedText.setPreferredSize( new Dimension(200,30));
      seedText.setMaximumSize(sizeText.getPreferredSize());
      buttonsPanel.add(seedText);
   
      mousePosition = new JLabel("Mouse Position: ");                      //displays (row,col) of mouse position
      buttonsPanel.add(mousePosition);
   
      if(this.getEntrance() != null)
      {                                                                    //the client has picked a starting point using right-click on the mouse
         entranceLabel = new JLabel("Start point: "+ this.getEntrance());
      }
      else
      {                                                                    //default to the lower-left corner if no start point has been picked
         entranceLabel = new JLabel("Start point: lower-left");
      }
      if(this.getExit() != null)
      {                                                                    //the client has picked a stop point using right-click on the mouse
         exitLabel =     new JLabel("End   point: "+ this.getExit());
      }
      else
      {                                                                    //default to the upper-right corner if no end point has been picked
         exitLabel =     new JLabel("End   point: upper-right");
      }
      buttonsPanel.add(entranceLabel);
      buttonsPanel.add(exitLabel);
   
      pathLengthLabel =  new JLabel("Path length: 0");                     //displays the length of the shortest path found
      buttonsPanel.add(pathLengthLabel);
      
      timeLabel =  new JLabel("Time elapsed:0");                           //displays the time to find the shortest path
      buttonsPanel.add(timeLabel);
   
      size = Integer.parseInt(sizeText.getText());                         //record values in the fields to populate data-fields
      density = Integer.parseInt(densityText.getText());
      seed = Integer.parseInt(seedText.getText());
   
      frame.add(buttonsPanel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }

   //post:  returns the start point for the pathfinding algorithm to connect with the least number of moves
   public Point getEntrance()
   {
      return entrance;
   }

   //post:  returns the stop point for the pathfinding algorithm to connect with the least number of moves
   public Point getExit()
   {
      return exit;
   }

   //pre:  Point in contains a (row, col) pair that represents a valid array index of the 2-D array in the map
   //post: change the start point for the pathfinding algorithm to connect with the least number of moves
   public void setEntrance(Point in)
   {
      entrance = in;
   }

   //pre:  Point out contains a (row, col) pair that represents a valid array index of the 2-D array in the map
   //post: change the stop point for the pathfinding algorithm to connect with the least number of moves
   public void setExit(Point out)
   {
      exit = out;
   }

   //pre:  map != null
   public void setMap(Map map)
   {
      this.map = map;
   }

   //The client has chosen to load a map from a file in the maps subfolder.
   class LoadAction extends AbstractAction                        
   {
      public LoadAction() 
      {
         super("Load");
      }
   
      @Override
      public void actionPerformed(ActionEvent e)
      {
         String userhome = System.getProperty("user.dir");        //get the file directory that this program is in
         JFileChooser j = new JFileChooser(userhome +"\\maps");   //add the maps subfolder to the working directory
         j.setFileFilter(new FileNameExtensionFilter("*.plmap", "plmap"));
         int result = j.showOpenDialog(frame);
         if (result == JFileChooser.APPROVE_OPTION) 
         {
            File selectedFile = j.getSelectedFile();
            try
            {
               map.loadFile(selectedFile);
               entrance = null;                              //clear the start and end points
               entranceLabel.setText("Start point: lower-left");
               exit = null;
               exitLabel.setText("End   point: upper-right");
               pathLengthLabel.setText("Path length: 0"); 
               timeLabel.setText("Time elapsed:0");
            } catch (IOException ex)
            {
               throw new RuntimeException(ex);
            }
         }
      }
   }

   //The client has chosen to save a map and store in the maps subfolder.
   class SaveAction extends AbstractAction                        
   {
      public SaveAction() 
      {
         super("Save");
      }
   
      @Override
      public void actionPerformed(ActionEvent e)
      {
         String userhome = System.getProperty("user.dir");        //get the file directory that this program is in
         JFileChooser j = new JFileChooser(userhome +"\\maps");   //add the maps subfolder to the working directory
         j.setFileFilter(new FileNameExtensionFilter("*.plmap", "plmap"));
         int result = j.showSaveDialog(frame);
         if (result == JFileChooser.APPROVE_OPTION) 
         {
            try
            {
               FileWriter fw = new FileWriter("maps\\"+j.getSelectedFile().getName() + ".plmap");
               fw.write(map.toString());
               fw.close();
            } catch (IOException ex)
            {
               throw new RuntimeException(ex);
            }
         }
      }
   }

   //The client has chosen to start the shortestPath1 algorithm to try to find the shortest path from start to end.
   class StartAction1 implements ActionListener               
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         map.resetHighlights();
         Point start = new Point(0,0);
         Point end = new Point(map.getSize() - 1, map.getSize() - 1);
         if(entrance != null)
         {
            start = entrance;
         }
         if(exit != null)
         {
            end = exit;
         }
         long startTime = System.currentTimeMillis();
         List<Point> points = PathfindingDriver.shortestPath1(map, start, end);
         long endTime = System.currentTimeMillis();
         double timeElapsed = ((endTime - startTime)/1000.0);
         map.highlightTiles(points, new Color(0,255,255));
         pathLengthLabel.setText("Path length: "+points.size());
         timeLabel.setText("Time elapsed:" + timeElapsed);
      }
   }
   
   //The client has chosen to start the shortestPath2 algorithm to try to find the shortest path from start to end.
   class StartAction2 implements ActionListener               
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         map.resetHighlights();
         Point start = new Point(0,0);
         Point end = new Point(map.getSize() - 1, map.getSize() - 1);
         if(entrance != null)
         {
            start = entrance;
         }
         if(exit != null)
         {
            end = exit;
         }
         long startTime = System.currentTimeMillis();
         List<Point> points = PathfindingDriver.shortestPath2(map, start, end);
         long endTime = System.currentTimeMillis();
         double timeElapsed = ((endTime - startTime)/1000.0);
         map.highlightTiles(points, new Color(0,255,255));
         pathLengthLabel.setText("Path length: "+points.size());
         timeLabel.setText("Time elapsed:" + timeElapsed);
      }
   }
   
   //The client has chosen to start the shortestPath3 algorithm to try to find the shortest path from start to end.
   class StartAction3 implements ActionListener               
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         map.resetHighlights();
         Point start = new Point(0,0);
         Point end = new Point(map.getSize() - 1, map.getSize() - 1);
         if(entrance != null)
         {
            start = entrance;
         }
         if(exit != null)
         {
            end = exit;
         }
         long startTime = System.currentTimeMillis();
         List<Point> points = PathfindingDriver.shortestPath3(map, start, end);
         long endTime = System.currentTimeMillis();
         double timeElapsed = ((endTime - startTime)/1000.0);
         map.highlightTiles(points, new Color(0,255,255));
         pathLengthLabel.setText("Path length: "+points.size());
         timeLabel.setText("Time elapsed:" + timeElapsed);
      }
   }
   
   //The client has chosen to start the shortestPath4 algorithm to try to find the shortest path from start to end.
   class StartAction4 implements ActionListener               
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         map.resetHighlights();
         Point start = new Point(0,0);
         Point end = new Point(map.getSize() - 1, map.getSize() - 1);
         if(entrance != null)
         {
            start = entrance;
         }
         if(exit != null)
         {
            end = exit;
         }
         long startTime = System.currentTimeMillis();
         List<Point> points = PathfindingDriver.shortestPath4(map, start, end);
         long endTime = System.currentTimeMillis();
         double timeElapsed = ((endTime - startTime)/1000.0);
         map.highlightTiles(points, new Color(0,255,255));
         pathLengthLabel.setText("Path length: "+points.size());
         timeLabel.setText("Time elapsed:" + timeElapsed);
      }
   }

   //The client has chosen to reset any highlights marked by the shortestPath algorithm.
   class ResetAction implements ActionListener
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         map.resetHighlights();
         pathLengthLabel.setText("Path length: 0");
      }
   }

   //The client has chosen to make a new random map.
   class RegenAction implements ActionListener          
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         String tempSizeText = sizeText.getText();
         String tempDensityText = densityText.getText();
         String tempSeedText = seedText.getText();
      
         try
         {
            size = Integer.parseInt(tempSizeText);
            size = Math.min(Math.max(size, 10), 50);       
            sizeText.setText("" + size);
         
            density = Integer.parseInt(tempDensityText);
            density = Math.min(Math.max(density, 0), 100);       
            densityText.setText("" + density);
         
            if(Integer.parseInt(tempSeedText) == seed)
            {
               seed = (int) ((Math.random() * ((long) Integer.MAX_VALUE - Integer.MIN_VALUE)) + Integer.MIN_VALUE);
               seedText.setText("" + seed);
            }
            else
            {
               seed = Integer.parseInt(tempSeedText);
            }
         
            map.setSize(size);
            map.setDensity(density);
            map.setSeed(seed);
         
            map.regenMap();
            entrance = null;                              //clear the start and end points
            entranceLabel.setText("Start point: lower-left");
            exit = null;
            exitLabel.setText("End   point: upper-right");
            pathLengthLabel.setText("Path length: 0"); 
            timeLabel.setText("Time elapsed:0");          
         }
         catch(Exception exception)
         {
            throw new RuntimeException("Size or Seed cannot be parsed!");
         }
      }
   }
}
