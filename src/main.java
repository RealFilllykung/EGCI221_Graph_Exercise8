import java.io.*;
import java.util.*;

import com.sun.javafx.geom.Edge;
import org.jgrapht.*;
import org.jgrapht.alg.DijkstraShortestPath;
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

    //String for keeping the name of file
    private String fileName;
    private Scanner scanFile;

    private void PrintCityList() {
        ArrayList PrintCityList = new ArrayList(CityList);
        for(int i = 0; i < PrintCityList.size(); i++)
        {
            System.out.println(PrintCityList.get(i));
        }
    }

    //The constructor of the graph
    MyDirectWeightGraph()
    {
        boolean askFile = true;
        scanFile = null;

        //Ask for the file input
        while(askFile)
        {
            try
            {
                System.out.print("Enter graph file: ");
                Scanner scan = new Scanner(System.in);
                fileName = scan.next();

                scanFile = new Scanner(new File(fileName));

                askFile = false;
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }

        //Put tie information into the graph
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
        scanFile.reset();

        //PrintCityList();  //Just for check that is there any duplicate city inside the list

        //Asking for shortest path phase
        Scanner scanInput = new Scanner(System.in);

        //Ask for the source and target city
        System.out.printf("Source       = ");
        String c1 = scanInput.next().toUpperCase();
        System.out.printf("Destination  = ");
        String c2 = scanInput.next().toUpperCase();

        //Create the list that contain the shortest path
        List<DefaultWeightedEdge> sp = DijkstraShortestPath.findPathBetween(G,c1,c2);

        //Find the total distance of the trip
        int tempDistance = 0;
        for (DefaultWeightedEdge e : sp) {
            tempDistance += (int)G.getEdgeWeight(e);
        }

        //Print out all paths that we have to use
        System.out.printf("Shortest path takes %d minutes : ",tempDistance);
        for(DefaultWeightedEdge e : sp) {
            System.out.printf("%s -> %s (%d)    ",G.getEdgeSource(e),G.getEdgeTarget(e),(int)G.getEdgeWeight(e));
        }
        System.out.printf("\n\n");

        //countSP is for...
        /*
            if the loop is just begin, skip the first step
            if it reaches the end city (which we don't want to continue the loop on that phase), it will stop the loop
         */
        int countSP = 0;
        //This loop is for finding the new path if e path is close
        for(DefaultWeightedEdge e : sp) {
            if (countSP == 0)
            {
                countSP++;
                continue;
            }
            else if (countSP == sp.size())
            {
                break;
            }

            countSP++;
            System.out.printf("If %s is closed\r\n",G.getEdgeSource(e));
            String tempc1 = G.getEdgeSource(e);
            String tempc2 = G.getEdgeTarget(e);

            //G.removeEdge(e);
            G.removeVertex(G.getEdgeSource(e));

            List<DefaultWeightedEdge> sp2 = DijkstraShortestPath.findPathBetween(G,c1,c2);

            int tempDistance2 = 0;
            for (DefaultWeightedEdge e2 : sp2) {
                tempDistance2 += (int)G.getEdgeWeight(e2);
            }

            System.out.printf("Shortest path takes %d minutes : ",tempDistance2);
            for(DefaultWeightedEdge e2 : sp2) {
                System.out.printf("%s -> %s (%d)    ",G.getEdgeSource(e2),G.getEdgeTarget(e2),(int)G.getEdgeWeight(e2));
            }
            System.out.printf("\n\n");
            //Graphs.addEdgeWithVertices(G,tempc1,tempc2,tempDistance2);

            try
            {
                scanFile = new Scanner(new File(fileName));
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
            }
            catch (Exception er)
            {
                System.err.println(er);
            }


        }
    }
}

public class main {
    public static void main(String[] argv)
    {
        new MyDirectWeightGraph();
    }
}
