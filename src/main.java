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
    private HashSet <String> CityList = new HashSet<String>();
    //We use HashSet to keep city list is because there are some of the duplicate city inside the file

    //Use SimpleWeightedGraph (for tbe sake of using direct property and collecting the weight of each nodes - nodes)
    private SimpleWeightedGraph<String, DefaultWeightedEdge> SWG = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    private Graph<String, DefaultWeightedEdge> G = (Graph<String, DefaultWeightedEdge>)SWG;
    private KruskalMinimumSpanningTree<String, DefaultWeightedEdge> KMS;

    private void PrintCityList() {
        ArrayList PrintCityList = new ArrayList(CityList);
        for(int i = 0; i < PrintCityList.size(); i++)
        {
            System.out.println(PrintCityList.get(i));
        }
    }

    MyDirectWeightGraph()
    {
        boolean askFile = true;
        Scanner scanFile = null;

        while(askFile)
        {
            try
            {
                System.out.print("Enter graph file: ");
                Scanner scan = new Scanner(System.in);
                String fileName = scan.next();

                scanFile = new Scanner(new File(fileName));

                askFile = false;
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }

        while(scanFile.hasNext())
        {
            String line = scanFile.nextLine();
            String buf[] = line.split(" ");

            String city1 = buf[0];
            String city2 = buf[1];
            int time = Integer.parseInt(buf[2]);

            CityList.add(city1);
            CityList.add(city2);

            Graphs.addEdgeWithVertices(G,city1,city2,time);
        }

        //PrintCityList();  //Just for check that is there any duplicate city inside the list

    }


}

public class main {
    public static void main(String[] argv)
    {
        new MyDirectWeightGraph();
    }
}
