package Pathfinding_LAB;//Urav Tanna, Douglas Oberle, 2/2024
//A single interactive JPanel that will be part of a tile that lives in a 2-D map of Tiles
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tile extends JPanel
{
   protected boolean traversable;   //true if it is a path, false if it is a wall
   protected int row, col;          //the row,col of where this JPanel will live on the map
   
   //Constr:    map size, x-coord,  y-coord,   row, col,      path or wall?,    ui   
   public Tile(int size, int xPos, int yPos, int r, int c, boolean traversable, UI ui)
   {
      super();
      setLocation(xPos, yPos);
      row = r;
      col = c;
      setSize(size,size);
      setBorder(BorderFactory.createLineBorder(Color.black));
   
      this.traversable = traversable;
   
      if(traversable)
      {
         setBackground(Color.WHITE);
      }
      else
      {
         setBackground(Color.RED);
      }
   
      addMouseListener(
         new MouseAdapter()
         {
            @Override
            public void mousePressed(MouseEvent e)
            {
               int button = e.getButton();
               if(button == MouseEvent.BUTTON1)
               {
                  setTraversable(!getTraversable());
                  if(getTraversable())
                  {
                     setBackground(Color.WHITE);
                  }
                  else
                  {
                     setBackground(Color.RED);
                  }
               }
               else if(button == MouseEvent.BUTTON3)
               {
                  Point start = ui.getEntrance();
                  Point end = ui.getExit();
                  Point location = new Point(row, col);
                  if(start == null)
                  {                                                  //place a new start (entrance) marker
                     ui.setEntrance(location);
                     ui.entranceLabel.setText("Start point: "+row+","+col);
                     if(end != null && end.getX()==row && end.getY()==col)
                     {                                               //if our start position is the same as end, clear the end position
                        ui.map.resetHighlights();
                        ui.setExit(null);
                        ui.exitLabel.setText("End   point: upper-right");
                     }
                     setBackground(Color.CYAN);
                  }
                  else if(end == null && !(start!=null && row==start.getX() && col==start.getY()))
                  {                                                  //make sure we don't put down the end in the same place the start is
                     ui.setExit(location);
                     ui.exitLabel.setText("End   point: "+row+","+col);
                     setBackground(Color.GREEN);
                  }
                  else
                  {                                                  //clear the end marker and just record the start marker
                     ui.map.resetHighlights();
                     ui.setEntrance(location);
                     ui.entranceLabel.setText("Start point: "+row+","+col);
                     setBackground(Color.CYAN);
                     ui.setExit(null);
                     ui.exitLabel.setText("End   point: upper-right");
                  }
               }
            }
         
            @Override
            public void mouseEntered(MouseEvent e)
            {
               ui.mousePosition.setText("Position: " + row + ", " + col);
            }
         });
   }

   //post: change whether or not this tile is a path (traversable is true) or an obstacle (traversable is false)
   public void setTraversable(boolean t)
   {
      this.traversable = t;
   }

   //post: return whether or not this tile is a path (traversable is true) or an obstacle (traversable is false)
   public boolean getTraversable()
   {
      return traversable;
   }
}
