import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.ArrayList;
/** a Linked-Web.  */
public class LinkedWeb<anyType> implements Webby<anyType>
{
/** Data field: collection of Territories of which some share borders. */
   private List<WebNode<anyType>> board;

   public LinkedWeb()
   {
      board = new ArrayList<>();
   }

   @Override
   public boolean add(String n, List<String> nei, anyType v) {
      board.add(new WebNode<>(n,nei,v));
      return true;
   }

   @Override
   public boolean addNeighbors(String n, List<String> nei) {
      WebNode x = get(n);
      int temp = board.indexOf(x);
      board.get(temp).addNeighbors(nei);
      return true;
   }

   @Override
   public boolean areNeighbors(String n, String b) {
      int temp = -1;
      for (int i = 0; i < board.size(); i++)
      {
         if (board.get(i).getName().equals(n))
         {
            temp = i;
         }
         else
            continue;
      }
      if (temp == -1)
         return false;
      return board.get(temp).getNeighbors().contains(b);
   }

   @Override
   public void clear() {
      board.clear();
   }

   @Override
   public boolean containsName(String n) {
      for (int i = 0; i < board.size(); i++)
      {
         if (board.get(i).getName().equals(n))
            return true;
      }
      return false;
   }

   @Override
   public WebNode get(String n) {
      WebNode x = null;
      if (!board.contains(x))
         return null;
      for (int i = 0; i < board.size(); i++)
      {
         if (board.get(i).getName().equals(n))
            x = board.get(i);
      }
      return x;
   }

   @Override
   public WebNode remove(String n) {
      WebNode x = get(n);
      if (!board.contains(x))
         return null;
      else
         return board.remove(board.indexOf(x));
   }

   @Override
   public WebNode set(String n, List<String> nei, anyType v) {
      WebNode x = get(n);
      return board.set(board.indexOf(x), new WebNode<>(n, nei, v));
   }

   @Override
   public String showUnlinked() {
      String result = "";
      for (WebNode<anyType> current : board)
      {
         result += current.getName() + ", BORDERS: [";
         List<String> neighbors = current.getNeighbors();
         for (String s : neighbors)
         {
            if (!this.containsName(s))
               result += "*" + s + "*";
            else
               result += s;
            if (neighbors.indexOf(s) != neighbors.size() -1)
               result += ", ";
         }
         result += "], " + current.getValue();
         result += "\n";
      }
      return result;
   }

   @Override
   public int size() {
      return board.size();
   }

   @Override
   public Object[] toArray() {
      return board.toArray();
   }
}