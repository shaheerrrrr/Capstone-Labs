//a Country object for the game Risk.
public class Country
{
/** Data field: name of the player that controls the Country. */
   private String owner;
/** Data field: the number of armies the owner has placed on the Country. */   
   private int numArmies;

 /**
 * 2 arg constructor for a Country object. 
 *
 * @param  o    the Country's owner
 * @param  n    the number of army units on the Country
 */
   public Country(String o, int n)
   {
      owner = o;
      numArmies = n;
   }

/**
 * Default constructor initializes the Country to have no owner ("NONE") and zero armies placed on it.
 *
 */
   public Country()
   {
      owner = "NONE";
      numArmies = 0;
   }

 /**
 * Finds the name of the player that conrols the Country
 *
 * @return  the Country's owner data-field
 */
   public String getOwner()
   {
      return owner;
   }
   
/**
 * Changes a Country to have a new name for the player that controls it.
 *
 * @param  o a non-null String that is the name of the player that gets ownership of the Country.
 */ 
   public void setOwner(String o)
   {
      owner = o;
   }

 /**
 * Finds the number of armies that are placed on the Country
 *
 * @return  the Country's numArmies data-field
 */
   public int getArmies()
   {
      return numArmies;
   }

/**
 * Changes a Country to have a new value for the number of armies placed on it.
 *
 * @param  n a non-negative value to represent the number of armies on the Country.
 */ 
   public void setArmies(int n)
   {
      numArmies = n;
   }

/**
 * Returns a String of the Country's information.
 * In the form of: 
 *  OWNER: NONE, NUMARMIES: 0
 *
 * @return String of the Country's data fields.
 */
   public String toString()
   {
      return "OWNER: " + owner + ", NUMARMIES: " + numArmies;
   }
}