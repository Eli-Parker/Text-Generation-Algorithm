package comprehensive;

import java.util.*;

/**
 *  Represents a directed graph of word pairs with the ability to both grab
 *  the maximum frequency connection and a random connection from any node in the graph.
 *  Both getMax() and getRandom() are O(1) operations,
 *  while getFullList() is O(k) where k is how many terms to get from k, or O(N) if K is bigger than the list.
 *  <p>
 *  The graph is represented using a HashMap of Strings mapped to ArrayLists of Edges,
 *  and a HashMap of Strings mapped to PriorityQueues of Edges.
 *  The ArrayList representation is used for getRandom() and the PriorityQueue representation is used for getMax() and getMostProbableList().
 *  This results in very fast access times for both operations at the cost of more memory usage.
 *
 * @author Eli Parker & Jorden Dickerson
 * @version Apr 16, 2024
 */
public class DirectedGraph {

    private HashMap<String,ArrayList<Edge>> adjList; // for accessing random values
    private HashMap<String,PriorityQueue<Edge>> priorityAdjList; // for accessing the maximum value & the sorted list

    /**
     * Creates a new DirectedGraph object.
     */
    public DirectedGraph() {
        this.adjList = new HashMap<>();
        this.priorityAdjList = new HashMap<>();
    }

    /**
     * Adds a connection to the graph.
     * If the connection already exists, it increases the occurrences.
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
            //iterates through edges to check for existing word pair. if the word pair exists, increase the occurrences
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
                //if pair isn't found, create a new edge pointing to the destination word
                var newEdge = new Edge(destination,edges);
                edges.add(newEdge);
                priorityEdges.add(newEdge);
            }
        }
        else
        {
            //If source isn't found, add the source to the HashMaps
            ArrayList<Edge> edges = new ArrayList<>();
            PriorityQueue<Edge> priorityEdges = new PriorityQueue<>();
            //add the destination to the source
            if(!destination.isEmpty())
            {
                var edge = new Edge(destination, edges); // add a new edge with the destination str & 1 occurrence
                edges.add(edge);
                priorityEdges.add(edge);
            }
            adjList.put(source, edges);
            priorityAdjList.put(source, priorityEdges);
        }
    }

    /**
     * Gets the connection to the given node with the highest weight, or the most "probability".
     * @param source the word to get the most probably value from
     * @return the most probable word to come after the source word, or an empty string if there is no connection
     */
    public String getMax(String source)
    {
        if(priorityAdjList.containsKey(source) && !priorityAdjList.get(source).isEmpty())
        {
            //get the first element in the priority queue, return it
            Edge edge = priorityAdjList.get(source).peek();
            return (edge != null) ? edge.getDestination() : "";
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
            //make a weighted random number which skews toward higher weights
            double skew = 0.5;
            int randResult = (int) Math.floor(Math.pow(rng.nextDouble(), skew) * adjList.get(source).size());
            return adjList.get(source).get(randResult).getDestination();
        }
        return "";
    }

    /**
     * Gets a list of K most probable words that come after the source word,
     * in order from most probable to least probable.
     * @param source the word to get all the connections of
     * @param K the number of words to return. Note that if K is greater than the number of connections, it will return all connections
     * @return an ordered list of K words that come after the source word, or an empty list if there are no connections
     */
    public String getMostProbableList(String source, int K)
    {
        StringBuilder result = new StringBuilder();
        if(priorityAdjList.containsKey(source)&& !priorityAdjList.get(source).isEmpty()){
            //copy the priorityQueue so we don't compromise the original
            var originalQueue = new PriorityQueue<>(priorityAdjList.get(source));


            //iterate until we go through entire list or get to K
            for(int i=0;i < priorityAdjList.get(source).size() - 1 && i < K - 1;i++)
            {
                //append the destination of the edge to the result
                if(originalQueue.peek() != null)
                    result.append(originalQueue.poll().getDestination()).append(" ");

            }
            //add the last element without whitespace
            if(originalQueue.peek() != null)
                result.append(originalQueue.poll().getDestination());
            return result.toString();
        }
        return ""; //return an empty String if there are no connections
    }

    /*
       Testing methods for DirectedGraph below
     */

    /**
     * Gets the list of K most probable weights that come after the source word. TODO delete in final submission
     * in order from most probable to least probable.
     * @param source the word to get all the connections of
     * @param K the number of word-weights to return. Note that if K is greater than the number of connections, it will return all connection weights
     * @return an ordered list of K weights that come after the source word, or an empty list if there are no connections
     */
    public Double[] getMostProbableListWeights(String source, int K)
    {
        if(priorityAdjList.containsKey(source)&& !priorityAdjList.get(source).isEmpty()){
            //copy the priorityQueue so we don't compromise the original
            var originalQueue = new PriorityQueue<>(priorityAdjList.get(source));

            Double[] list = new Double[priorityAdjList.get(source).size()];//return value

            //iterate until we go through entire list or get to K
            for(int i=0;i < originalQueue.size() && i < K;i++)
            {
                if(originalQueue.peek() != null)
                    list[i] = originalQueue.poll().getWeight();
            }
            return list;
        }
        return new Double[0]; //return an empty array if there are no connections
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
     * Returns the edges for a given vertex in the graph,
     * or "NOTHING FOUND" if the vertex is not in the graph.
     * @param source the source node
     * @return a string representing the edges of the graph or "NOTHING FOUND" if the vertex is not in the graph.
     */
    public String getEdges(String source)
    {
        if(adjList.containsKey(source))
        {
            return adjList.get(source).toString();
        }
        return "NOTHING FOUND";
    }

    /**
     * Returns all vertices in the graph object.
     * @return an array of strings representing the vertices in the graph
     */
    public String[] getVertexes(){
        return adjList.keySet().toArray(new String[0]);
    }



    /**
     * A class to represent an edge in the graph, containing the value and the number of times we see the word pair.
     * <p>
     * The weight is defined as the frequency of the word pair / the number of edges.
     * (Ex: if a word "hello" has "world", "hi", and "jim" coming after it,
     * then "jim" would have a weight of 1/3)
     * Edge objects are comparable by weight, then alphanumerically.
     * @author Eli Parker & Jorden Dickerson
     * @version Apr 16, 2024
     */
    private class Edge implements Comparable<Edge> {

        private final String destination; // the connection's destination
        private int occurrences; // number of times we see the word pair
        private final ArrayList<Edge> parentList; // saves a pointer to the parent list for weight calculation

        /**
         * Creates a new Edge object with 1 occurrence
         * @param destination the destination node
         */
        public Edge(String destination, ArrayList<Edge> parentList)
        {
            if(destination == null || parentList == null)
            {
                throw new IllegalArgumentException("Destination and parentList cannot be null");
            }
            this.destination = destination;
            this.occurrences = 1;
            this.parentList = parentList;
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
         * Gets the number of times we see the edge object.
         * @return a double which is the occurrences of the edge
         */
        public double getWeight()
        {
            return (double) occurrences / parentList.size();
        }
        /**
         * Increases the number of occurrences of the edge object by 1.
         */
        public void increaseOccurrences()
        {
            occurrences++;
        }

        /**
         * Compares the edge object to another edge object by weight, then alphanumerically.
         * @param edge the object to be compared.
         * @return a negative integer, zero, or a positive integer if this object is less than, equal to, or greater than the specified object respectively.
         */
        @Override
        public int compareTo(Edge edge)
        {
            int comparison = Double.compare(edge.getWeight(), this.getWeight());
            return comparison == 0 ? this.getDestination().compareTo(edge.getDestination()) : comparison;
        }
    }
}
