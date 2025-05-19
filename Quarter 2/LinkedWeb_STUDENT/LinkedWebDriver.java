//A proposed linked web driver. 
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

public class LinkedWebDriver
{
  //fileName has one WebNode per-line, starting with the country name, followed by each neighbor name separated by a space.
   public static LinkedWeb<Country> readFile(String fileName) throws IOException
   {
      Scanner input = new Scanner(new FileReader(fileName));
      LinkedWeb<Country> risk = new LinkedWeb();
      while (input.hasNextLine())		//while there is another line in the file
      {
         String t = input.nextLine();		
         if(t.startsWith("//"))
            continue;	
         String [] parts = t.split(" ");
         String name = parts[0];
         ArrayList <String> borders = new ArrayList<>();
         for(int i=1; i<parts.length; i++)
            borders.add(parts[i]);
         risk.add(name, borders, new Country());
      }
      input.close();	
      return risk;
   }

   public static void main(String[]arg)throws IOException
   {
      //LinkedWeb<Country> risk = readFile("RiskMapIncomplete.txt");   //for testing showUnlinked with an incomplete set of data
      LinkedWeb<Country> risk = readFile("RiskMap.txt");
      System.out.println(risk.showUnlinked());
   }

/*Should output something like:
 ALASKA, BORDERS:[KAMCHAKA, NORTH-WEST-TERRITORY, ALBERTA]
 OWNER: NONE, NUMARMIES: 0
 NORTH-WEST-TERRITORY, BORDERS:[ALASKA, ALBERTA, ONTARIO, GREENLAND]
 OWNER: NONE, NUMARMIES: 0
 GREENLAND, BORDERS:[NORTH-WEST-TERRITORY, ONTARIO, EASTERN-CANADA, *ICELAND*]
 OWNER: NONE, NUMARMIES: 0
 ALBERTA, BORDERS:[ALASKA, NORTH-WEST-TERRITORY, ONTARIO, WESTERN-UNITED-STATES]
 OWNER: NONE, NUMARMIES: 0
 ONTARIO, BORDERS:[ALBERTA, NORTH-WEST-TERRITORY, GREENLAND, EASTERN-CANADA, WESTERN-UNITED-STATES, EASTERN-UNITED-STATES]
 OWNER: NONE, NUMARMIES: 0
 EASTERN-CANADA, BORDERS:[ONTARIO, GREENLAND, EASTERN-UNITED-STATES]
 OWNER: NONE, NUMARMIES: 0
 WESTERN-UNITED-STATES, BORDERS:[ALBERTA, ONTARIO, EASTERN-UNITED-STATES, CENTRAL-AMERICA]
 OWNER: NONE, NUMARMIES: 0
 EASTERN-UNITED-STATES, BORDERS:[WESTERN-UNITED-STATES, ONTARIO, EASTERN-CANADA, CENTRAL-AMERICA]
 OWNER: NONE, NUMARMIES: 0
 CENTRAL-AMERICA, BORDERS:[EASTERN-UNITED-STATES, WESTERN-UNITED-STATES, VENEZUELA]
 OWNER: NONE, NUMARMIES: 0
*/
}