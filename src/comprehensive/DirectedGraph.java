package comprehensive;

import java.util.*;

/**
 *  Represents a directed graph of word pairs with the ability to both grab
 *  the maximum frequency connection and a random connection from any node in the graph.
 *  Both getMax() and getRandom() are O(1) operations,
 *  while getFullList() is O(k) where k is how many terms to get from k.
 *  <p>
 *  The graph is represented using a HashMap of Strings mapped to ArrayLists of Edges,
 *  and a HashMap of Strings mapped to PriorityQueues of Edges.
 *
 * @author Eli Parker & Jorden Dickerson
 * @version Apr 16, 2024
 */
public class DirectedGraph {

    private HashMap<String,ArrayList<Edge>> adjList; // for random values
    private HashMap<String,PriorityQueue<Edge>> priorityAdjList; // for the maximum value

    /**
     * Creates a new DirectedGraph object.
     */
    public DirectedGraph() {
        this.adjList = new HashMap<>();
        this.priorityAdjList = new HashMap<>();
    }

    /**
     * Adds a connection to the graph.
     * @param source the source node
     * @param destination the destination node
     */
    public void addConnection(String source, String destination)
    {
        //Checks to see if source is there
        if(adjList.containsKey(source))
        {
            //gets the edge lists for that source
            ArrayList<Edge> edges = adjList.get(source);
            PriorityQueue<Edge> priorityEdges = priorityAdjList.get(source);
            boolean found = false;
            //iterates through edges to check for existing word pair
            for (Edge value : edges) {
                if (value.getDestination().equals(destination)) {
                    //since the priorityQueue order must be maintained, we remove the edge
                    //and put it back after it's been edited
                    priorityEdges.remove(value);
                    value.increaseOccurrences();
                    priorityEdges.add(value);
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                //if pair isn't found, add it
                var edge = new Edge(destination);
                edges.add(edge);
                priorityEdges.add(edge);
            }
        }
        else
        {
            //If source isn't found, add the source to the HashMaps
            ArrayList<Edge> edges = new ArrayList<>();
            PriorityQueue<Edge> priorityEdges = new PriorityQueue<>(Comparator.comparing(Edge::getWeight).reversed());

            var edge = new Edge(destination); // add a new edge with the destination str & 1 occurrence
            priorityEdges.add(edge);
            edges.add(edge);
            adjList.put(source,edges);
            priorityAdjList.put(source,priorityEdges);
        }
    }

    /**
     * Gets the connection to the given node with the highest weight, or the most "probability".
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
     * Gets a random connection to the source node.
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
     * Gets the list of K most probable words that come after the source word.
     * in order from most probable to least probable.
     * @param source the word to get all the connections of
     * @param K the number of words to return. Note that if K is greater than the number of connections, it will return all connections
     * @return an ordered list of K words that come after the source word, or an empty list if there are no connections
     */
    public String[] getMostProbableList(String source, int K)
    {
        if(priorityAdjList.containsKey(source)&& !priorityAdjList.get(source).isEmpty()){
            //copy the priorityQueue so we don't compromise the original
            var originalQueue = new PriorityQueue<>(priorityAdjList.get(source));

            String[] list = new String[priorityAdjList.get(source).size()];//return value

            //iterate until we go through entire list or get to K
            for(int i=0;i < priorityAdjList.size() && i < K;i++)
            {
                list[i] = originalQueue.poll().getDestination();
            }
            return list;
        }
        return new String[0]; //return an empty array if there are no connections
    }

    /**
     * Returns the number of vertices in the graph.
     * @return an integer representing the number of vertices in the graph
     */
    public int size()
    {
        return adjList.size();
    }


    /**
     * A class to represent an edge in the graph, containing the value and the number of times we see the word pair.
     * <p>
     * Note: The weight is defined as the
     * frequency of the word pair / all words in the adjacency list.
     * <p>
     * EXAMPLE:
     * if i have a word "hello" and in my text the words that come after hello are "world", "hi", and "jim",
     * then "jim" would have a frequency of 1/3
     * @author Eli Parker & Jorden Dickerson
     * @version Apr 16, 2024
     */
    public class Edge {

        private final String destination; // the connection's destination
        private int occurrences; // number of times we see the word pair

        /**
         * Creates a new Edge object with 1 occurrence
         * @param destination the destination node
         */
        public Edge(String destination)
        {
            this.destination = destination;
            this.occurrences = 1;
        }

        /**
         * Gets the destination of the edge object.
         * @return a string which is the destination of the edge
         */
        public String getDestination()
        {
            return this.destination;
        }

        /**
         * Gets the weight of the edge object.
         * @return a double which is the weight of the edge
         */
        public double getWeight()
        {
            return (double) occurrences / (double) adjList.size();
        }

        /**
         * Increases the number of occurrences of the edge object by 1.
         */
        public void increaseOccurrences()
        {
            occurrences++;
        }
    }
}
