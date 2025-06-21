// Name: Shaheer Khan
// Partner (if applicable):
// Date:
import java.util.*;
import java.io.*;
/** A program to implement Huffman style compression to encode/compress text into binary. */
public class Huffman_shell {
   /**
    * Data field to read input from the client at the keyboard
    */
   public static Scanner keyboard = new Scanner(System.in);

   /**
    * a main function to run
    *
    * @param args 'cause, you know, we need this
    * @throws IOException in case we can't find a file we are looking for
    */
   public static void main(String[] args) throws IOException {
      //Prompt for two strings
      System.out.println("Encoding using Huffman codes");
      System.out.println("Enter a message to compress in code ");
      String message = keyboard.nextLine();

      System.out.println("Enter middle part of filename:  ");
      String middlePart = keyboard.nextLine();

      huffmanize(message, middlePart);
   }

   /**
    * Given a message to encode and a name for a file, this will figure out the Huffman scheme and compress/enocde the message,
    * then the encoded message and scheme will be written out to files.
    * The message will be written out to ("message." + middlePart + ".txt") and the scheme will be written out to ("scheme." + middlePart + ".txt").
    *
    * @param message    the message that the client wants to compress; pre-condition: message is not null
    * @param middlePart the valid file name that we want to use; pre-condition: message is not null and consistes of alpha-numeric characters (no meta-characters).
    * @throws IOException in case we can't find a file we are looking for
    */
   public static void huffmanize(String message, String middlePart) throws IOException {
      //Make a frequency table of the letters
      //
      Map<Character, Integer> map = new HashMap<>();
      for (Character a : message.toCharArray()) {
         map.put(a, map.getOrDefault(a, 0) + 1);
      }
      //Put each letter-frequency pair into a HuffmanTreeNode. Put each node into a priority queue (or a min-heap).
      //
      PriorityQueue<HuffmanTreeNode> pq = new PriorityQueue<>();
      for (Character key : map.keySet()) {
         pq.add(new HuffmanTreeNode(key, map.get(key)));
      }
      System.out.println(pq);
      //Use the priority queue of nodes to build the Huffman tree
      //
      while (pq.size() > 1) {
         HuffmanTreeNode left = pq.poll();
         HuffmanTreeNode right = pq.poll();
         HuffmanTreeNode parent = new HuffmanTreeNode('*', left.getFreq() + right.getFreq());
         parent.setLeft(left);
         parent.setRight(right);
         pq.add(parent);
      }
      HuffmanTreeNode root = pq.poll();
      //Process the string letter-by-letter and search the tree for the letter. It's recursive. As you recur, build the path through the tree, left is 0 and right is 1.
      //System.out.println the binary message
      //Write the binary message to the hard drive using the file name ("message." + middlePart + ".txt")
      //
      String binary = "";
      for (char c : message.toCharArray()) {
         System.out.println(c);
         binary += findPath(c, root, "");
      }
      System.out.println("Binary message: " + binary);

      try (FileWriter fw = new FileWriter("message." + middlePart + ".txt")) {
         fw.write(binary);
      }
      //System.out.println the scheme from the tree--needs a recursive helper method
      //Write the scheme to the hard drive using the file name ("scheme." + middlePart + ".txt")
      //
      try (FileWriter sw = new FileWriter("scheme." + middlePart + ".txt")) {
         for (char c : map.keySet()) {
            String line = c + findPath(c, root, "");
            System.out.println(line);
            sw.write(line + "\n");
         }
      }
   }

   public static String findPath(char c, HuffmanTreeNode node, String path) {
      if (node == null)
         return "";
      if (node.getLeft() == null && node.getRight() == null && node.getLetter() == c)
         return path;
      String leftPath = findPath(c, node.getLeft(), path + "0");
      if (!leftPath.isEmpty())
         return leftPath;

      String rightPath = findPath(c, node.getRight(), path + "1");
      return rightPath;
   }
}
   /**
    * This tree node stores two values: a letter and its frequency, as well as pointers to their left and right subtrees
    * The compareTo method must ensure that the lowest frequency has the highest priority.
    */
   class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
      final private char letter;
      final private int freq;
      private HuffmanTreeNode left, right;

      public HuffmanTreeNode(char c, int f, HuffmanTreeNode l, HuffmanTreeNode r) {
         letter = c;
         freq = f;
         left = l;
         right = r;
      }

      public HuffmanTreeNode(char c, int f) {
         letter = c;
         freq = f;
         left = null;
         right = null;
      }

      public char getLetter() {
         return letter;
      }

      public void setLeft(HuffmanTreeNode l) {
         left = l;
      }

      public void setRight(HuffmanTreeNode r) {
         right = r;
      }

      public int getFreq() {
         return freq;
      }

      public HuffmanTreeNode getLeft() {
         return left;
      }

      public HuffmanTreeNode getRight() {
         return right;
      }

      /**
       * Compare Huffman Tree Nodes by their frequencies
       *
       * @param that another node that we want to compare this against by frequency
       * @return a negative number if that has a higher-freq than this, a positive number if this has a higher-freq than that, zero if the freq are the same
       */
      @Override
      public int compareTo(HuffmanTreeNode that) {
         return this.freq - that.freq;
      }

      @Override
      public String toString() {
         return "Letter: " + letter + " Frequency " + freq;
      }
   }