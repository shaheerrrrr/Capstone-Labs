import java.util.*;

public interface Webby<anyType>
{
    public boolean add(anyType a, ArrayList<String> surrounding);

    public anyType remove(anyType a);

    public anyType getNode();

    public ArrayList<String> getSurroundingNodes(anyType currNode);

    public anyType setNode();

    public void addLink(anyType curr, anyType target);

    public void removeLink (anyType curr, anyType target);

    
}
