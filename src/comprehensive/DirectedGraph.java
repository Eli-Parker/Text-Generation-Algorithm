package comprehensive;

import java.util.*;

/**
 *  //TODO Deleteme WOW THIS HAS BEEN A JOURNEY
 *
 * @author Eli Parker & Jorden Dickerson
 * @version Apr 15, 2024
 */
public class DirectedGraph {

    private HashMap<String,ArrayList<Edge>> adjList;
    private Random rng;

    /**
     * Creates a new DirectedGraph object
     */
    public DirectedGraph() {
        this.adjList = new HashMap<>();
        rng = new Random();
    }

    /**
     * Adds an edge to the graph
     * @param source the source node
     * @param destination the destination node
     */
    public void addEdge(String source, String destination)
    {
        if(adjList.containsKey(source))
        {
            ArrayList<Edge> edges = adjList.get(source);
            boolean found = false;
            for(int i=0;i<edges.size();i++)
            {
                if(edges.get(i).getDestination().equals(destination))
                {
                    edges.get(i).increaseWeight();
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                edges.add(new Edge(destination,1));
            }
        }
        else
        {
            ArrayList<Edge> edges = new ArrayList<>();
            edges.add(new Edge(destination,1));
            adjList.put(source,edges);
        }
    }

    /**
     * Gets the edge of the node with the highest weight
     * @param node the word to get the most probable value from
     * @return
     */
    public String getMax(String node)
    {
        if(adjList.containsKey(node))
        {
            ArrayList<Edge> edges = adjList.get(node);
            var currentMaxWeight = -1.0;
            var currentMaxValue = "";
            for(int i=0;i< edges.size();i++)
            {
                if(edges.get(i).getWeight() > currentMaxWeight)
                {
                    currentMaxWeight = edges.get(i).getWeight();
                    currentMaxValue =  edges.get(i).getDestination();
                }
            }
            return currentMaxValue;
        }
        else
        {
            return "";
        }
    }


    /**
     * A class to represent an edge from one node to another, containing the weight as well.
     * It's worth noting that the weight is defined as the
     * frequency of the word pair / all word pairs.
     * <p>
     * EXAMPLE:
     * if i have a word "hello" and in my text the words that come after hello are "world", "hi", and "jim",
     * then "jim" would have a frequency of 1/3
     */
    public class Edge {

        private final String destination;
        private double weight;

        /**
         * Creates a new Edge object
         * @param destination the destination node
         * @param weight the weight of the edge
         */
        public Edge(String destination, double weight)
        {
            this.destination = destination;
            this.weight = weight;
        }

        public String getDestination()
        {
            return this.destination;
        }

        public double getWeight()
        {
            return this.weight;
        }

        public void increaseWeight()
        {
            this.weight++;
        }
    }
}
