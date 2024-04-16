package comprehensive;

import java.util.*;

/**
 *  //TODO Deleteme WOW THIS HAS BEEN A JOURNEY
 *
 * @author Eli Parker & Jorden Dickerson
 * @version Apr 15, 2024
 */
public class DirectedGraph {

    private HashMap<String,ArrayList<Edge>> adjList; // for random values
    private HashMap<String,PriorityQueue<Edge>> priorityAdjList; // for the maximum value

    /**
     * Creates a new DirectedGraph object
     */
    public DirectedGraph() {
        this.adjList = new HashMap<>();
        this.priorityAdjList = new HashMap<>();
    }

    /**
     * Adds an edge to the graph
     * @param source the source node
     * @param destination the destination node
     */
    public void addEdge(String source, String destination)
    {
        //Checks to see if source is there
        if(adjList.containsKey(source))
        {
            //gets the edge lists for that source
            ArrayList<Edge> edges = adjList.get(source);
            PriorityQueue<Edge> priorityEdges = priorityAdjList.get(source);
            boolean found = false;
            //iterates through edges to check for existing word pair
            for(int i=0;i<edges.size();i++)
            {
                if(edges.get(i).getDestination().equals(destination))
                {
                    //since the priorityQueue order must be maintained, we remove the edge
                    //and put it back after it's been edited
                    priorityEdges.remove(edges.get(i));
                    edges.get(i).increaseWeight();
                    priorityEdges.add(edges.get(i));
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                //if pair isn't found, add it
                var edge = new Edge(destination,1.0/ (double)adjList.size());
                edges.add(edge);
                priorityEdges.add(edge);
            }
        }
        else
        {
            //If source isn't found, add to the HashMaps
            ArrayList<Edge> edges = new ArrayList<>();
            PriorityQueue<Edge> priorityEdges = new PriorityQueue<>(Comparator.comparing(Edge::getWeight).reversed());

            var edge = new Edge(destination,1.0/ (double)adjList.size());
            priorityEdges.add(edge);
            edges.add(edge);
            adjList.put(source,edges);
            priorityAdjList.put(source,priorityEdges);
        }
    }

    /**
     * Gets the connection to the given node with the highest weight, or the most "probability"
     * @param source the word to get the most probably value from
     * @return the most probable word to come after the source word, or an empty string if there is no connection
     */
    public String getMax(String source)
    {
        if(priorityAdjList.containsKey(source))
        {
            return priorityAdjList.get(source).peek().getDestination();
        }
        return "";
    }

    /**
     * Gets a random connection to the source node
     * @param source the word to get a random value from
     * @return a random word that comes after the source, or an empty string if there is no connection
     */
    public String getRandom(String source)
    {
        if(adjList.containsKey(source))
        {
            Random rng = new Random();
            return adjList.get(source).get(rng.nextInt(adjList.get(source).size())).getDestination();
        }
        return "";
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
