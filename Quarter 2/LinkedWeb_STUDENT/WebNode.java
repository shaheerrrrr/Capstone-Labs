import java.util.*;

public class WebNode<anyType> {
    private String name;
    private List<String> neighbors;
    private anyType value;

    public WebNode(String n, List<String> neigh, anyType val)
    {
        name = n;
        neighbors = neigh;
        value = val;
    }

    public String getName()
    {
        return name;
    }

    public List<String> getNeighbors()
    {
        return neighbors;
    }

    public anyType getValue()
    {
        return value;
    }

    public void setName(String n)
    {
        name = n;
    }

    public void setNeighbors(List<String> nei)
    {
        neighbors = nei;
    }

    public void setValue(anyType v)
    {
        value = v;
    }

    public int numNeighbors()
    {
        return neighbors.size();
    }

    public boolean addNeighbor(String n)
    {
        if (neighbors.contains(n))
            return false;
        neighbors.add(n);
        return true;
    }

    public boolean addNeighbors(List<String> nei)
    {
        if (neighbors.containsAll(nei))
            return false;
        neighbors.addAll(nei);
        return true;
    }

    public boolean isBordering(String n)
    {
        return neighbors.contains(n);
    }

    public boolean removeNeighbor(String n)
    {
        neighbors.remove(n);
        return false;
    }

    public String toString()
    {
        return name + ", BORDERS: " + neighbors + ", " + value;
    }
}
