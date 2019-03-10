import java.io.*;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

class city {
    private String name;

    city(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}

class MyDirectWeightGraph
{
    private HashMap <String, city> AllCity;
    private HashSet <String> CityList;
    //We use HashSet to keep city list is because there are some of the duplicate city inside the file

    //Use SimpleWeightedGraph (for tbe sake of using direct property and collecting the weight of each nodes - nodes)
    private SimpleWeightedGraph<String, DefaultWeightedEdge> SWG;
    private Graph<String, DefaultWeightedEdge> G;
    private KruskalMinimumSpanningTree<String, DefaultWeightedEdge> KMS;



}

public class main {
    public static void main(String[] argv)
    {

    }
}
