import java.io.*;
import java.util.*;

import org.jgrapht.*;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

class MyDirectWeightGraph
{
    private HashSet <String> CityList = new HashSet<String>();
    //We are using HashSet to keep city list is because there are some of the duplicate city inside the file

    //Use SimpleWeightedGraph (for tbe sake of using direct property and collecting the weight of each nodes - nodes)
    private SimpleWeightedGraph<String, DefaultWeightedEdge> SWG = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    private Graph<String, DefaultWeightedEdge> G = (Graph<String, DefaultWeightedEdge>)SWG;

    //String for keeping the name of file
    private String fileName;
    private Scanner scanFile;

    //The constructor of the graph
    MyDirectWeightGraph()
    {
        boolean askFile = true;
        scanFile = null;

        //Ask for the file input
        while(askFile)
        {
            try {
                System.out.print("Enter graph file: ");
                Scanner scan = new Scanner(System.in);
                fileName = scan.next();

                scanFile = new Scanner(new File(fileName));

                askFile = false;
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }

        //Put tie information into the graph
        while(scanFile.hasNext()) {
            String line = scanFile.nextLine();
            String buf[] = line.split(" ");

            String city1 = buf[0];
            String city2 = buf[1];
            int time = Integer.parseInt(buf[2]);

            CityList.add(city1);
            CityList.add(city2);

            Graphs.addEdgeWithVertices(G,city1,city2,time);
        }

        //Reset the position of the file pointer
        scanFile.reset();

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
            if (countSP == 0) {
                countSP++;
                continue;
            }
            else if (countSP == sp.size()) {
                break;
            }

            countSP++;
            System.out.printf("If %s is closed\r\n",G.getEdgeSource(e));

            G.removeVertex(G.getEdgeSource(e));

            //Finding the new fastest path
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

            //Try to reopen the file and put all information into the graph again
            try {
                scanFile = new Scanner(new File(fileName));
                while(scanFile.hasNext()) {
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
            catch (Exception er) {
                System.err.println(er);
            }
        }
    }
}

//Main class for this project is just create the new MyDirectWeightGraph object
public class main {
    public static void main(String[] argv)
    {
        new MyDirectWeightGraph();
    }
}
