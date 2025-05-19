import java.util.*;

public interface Webby<anyType>
{
    public boolean add(String n, List<String> nei, anyType v);

    public boolean addNeighbors(String n, List<String> nei);

    public boolean areNeighbors(String n, String b);

    public void clear();

    public boolean containsName(String n);

    public WebNode get(String n);

    public WebNode remove(String n);

    public WebNode set(String n, List<String> nei, anyType v);

    public String showUnlinked();

    public int size();

    public Object[] toArray();
}