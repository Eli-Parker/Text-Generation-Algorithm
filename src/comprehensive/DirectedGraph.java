package comprehensive;


import java.util.*;


/**
 *  Represents a directed graph of word pairs with the ability to both grab
 *  the maximum frequency connection and a random connection from any node in the graph.
 *  getMax() has a big O of O(K) and getRandom() has a big O of O(K + log(K)), where K is the
 *  size of the edge list for the given vertex.
 *  <p>
 *  The graph is represented using a HashMap of Strings mapped to ArrayLists of Edges,
 *  where each Edge contains the destination word and the number of times we see the word pair.
 *  Every edge has a weight which is defined as the frequency of the word pair / the number of edges,
 *  and this weight is used to determine a more probable random word.
 *
 * @author Eli Parker & Jorden Dickerson
 * @version Apr 22, 2024
 */
public class DirectedGraph {

    private HashMap<String,ArrayList<Edge>> adjList; // the adjacency list representation of the graph
    private HashMap<String, Integer> totalEdges; // the total number of edges for each vertex
    private Random rng; // random number generator

    /**
     * Creates a new DirectedGraph object.
     */
    public DirectedGraph() {
        this.adjList = new HashMap<>();
        this.totalEdges = new HashMap<>();
    }


    /**
     * Adds a connection to the graph.
     * If the connection already exists, it increases the weight of
     * that connection relative to the other ones for the vertex.
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
            boolean found = false;
            //iterates through edges to check for existing word pair. if the word pair exists, increase the occurrences
            for (int i = 0; i < edges.size(); i++){
                if (edges.get(i).getDestination().equals(destination)) {
                    edges.get(i).increaseOccurrences();
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                //if pair isn't found, create a new edge pointing to the destination word
                edges.add(new Edge(source, destination));
            }
        }
        else
        {
            //If source isn't found, add the source to the HashMap
            ArrayList<Edge> edges = new ArrayList<>();
            //add the destination to the source
            // add a new edge with the destination str & 1 occurrence
            edges.add(new Edge(source, destination));
            adjList.put(source, edges);
        }
        //increase the total number of edges
        totalEdges.put(source, totalEdges.getOrDefault(source, 0) + 1);
    }


    /**
     * Gets the connection to the given node with the highest weight, or the most "probability".
     * @param source the word to get the most probably value from
     * @return the most probable word to come after the source word, or an empty string if there is no connection
     */
    public String getMax(String source)
    {
        if(adjList.containsKey(source))
        {
            //get the first element in the priority queue, return it
            Edge edge = Collections.min(adjList.get(source));
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
        //if the random number generator is null, instantiate it
        if(rng == null)
        {
            rng = new Random();
        }

        if(adjList.containsKey(source))
        {
            var sourceEdges = adjList.get(source); // get the list of edges
            double[] prefixSum = new double[sourceEdges.size() + 1];
            for(int i = 0; i < sourceEdges.size(); i++)
            {
                prefixSum[i + 1] = prefixSum[i] + sourceEdges.get(i).getWeight();
            }

            int resultIndex = Arrays.binarySearch(prefixSum, rng.nextDouble());
            if(resultIndex < 0)
            {
                resultIndex = (-resultIndex) - 2;
            }
            else
            {
                resultIndex--;
            }
            return sourceEdges.get(resultIndex).getDestination();
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
        if(adjList.containsKey(source))
        {
            var pointer = adjList.get(source); // get the list of edges
            Collections.sort(pointer);


            int i;
            //iterate until we go through entire list or get to K
            for(i=0;i < pointer.size() - 1 && i < K - 1;i++)
            {
                //append the destination of the edge to the result
                result.append(pointer.get(i).getDestination()).append(" ");


            }
            //add the last element without whitespace
            if(pointer.get(i) != null)
                result.append(pointer.get(i).getDestination());
            return result.toString();
        }
        return ""; //return an empty String if there are no connections
    }



    /**
     * Returns all vertices in the graph object.
     * @return an array of strings representing the vertices in the graph
     */
    public String[] getVertexes(){
        return this.adjList.keySet().toArray(new String[0]);
    }






    /**
     * A class to represent an edge in the graph,
     * containing the value of the destination vertex and the number of times we see the word pair.
     * <p>
     * The weight is defined as the frequency of the word pair / the number of edges.
     * (Ex: if a word "hello" has "world", "hi", and "jim" coming after it,
     * then "jim" would have a weight of 1/3).
     * <p>
     * Edge objects are compared by weight, then alphanumerically.
     * @author Eli Parker & Jorden Dickerson
     * @version Apr 16, 2024
     */
    private class Edge implements Comparable<Edge> {


        private final String destination; // the connection's destination
        private int occurrences; // number of times we see the word pair
        private final String source; // saves a pointer to the parent list for weight calculation


        /**
         * Creates a new Edge object with 1 occurrence
         * @param destination the destination node
         */
        public Edge(String source, String destination)
        {
            if(destination == null || source == null)
            {
                throw new IllegalArgumentException("Source and destination cannot be null");
            }
            this.source = source;
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
         * Gets the number of times we see the edge object.
         * @return a double which is the occurrences of the edge
         */
        public double getWeight()
        {
            return (double) occurrences / totalEdges.get(source);
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

