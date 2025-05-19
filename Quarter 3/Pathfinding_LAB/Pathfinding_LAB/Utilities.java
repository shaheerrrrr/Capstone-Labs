package Pathfinding_LAB;//Urav Tanna, Douglas Oberle, 2/2024
//Utility class for reading information from files.
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Utilities
{
    //pre:  file != null and points to a real file containing lines of text
    //post: returns the number of lines in the file, O(n)
    public static int getFileSize(File file)throws IOException
    {
        Scanner input = new Scanner(new FileReader(file));
        int size=0;
        while (input.hasNextLine())				               //while there is another line in the file
        {
            size++;										            //add to the size
            input.nextLine();							            //go to the next line in the file
        }
        input.close();									            //always close the files when you are done
        return size;
    }

    //pre:  file != null and points to a real file containing lines of text that describe a map of walls and paths
    //post: returns a String array of all the elements in the file, O(n)
    public static String[] readFile(File file)throws IOException
    {
        int size = getFileSize(file);                       //holds the # of elements in the file
        String[] list = new String[size];                   //a heap will not use index 0;
        Scanner input = new Scanner(new FileReader(file));
        int i = 0;                                          //index for placement in the array
        String line;
        while (input.hasNextLine())                         //while there is another line in the file
        {
            line = input.nextLine();                        //read in the next Line in the file and store it in line
            list[i] = line;                                 //add the line into the array
            i++;                                            //advance the index of the array
        }
        input.close();                                      //always close the files when you are done
        return list;
    }
}
