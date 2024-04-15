package comprehensive;

import java.io.File;
import java.util.InputMismatchException;
import java.util.PriorityQueue;

public class GenerativeModel
{
    //TODO generative model implementation
    private File inputFile;

    //The total number of words in the model
    private int totalSize;

    /**
     *
     */
    public GenerativeModel()
    {
        this.totalSize = 0;
    }

    /**
     * Gets the number of elements in the model
     * @return the number of elements in the model
     */
    private int getTotalSize()
    {
        return this.totalSize;
    }


    /**
     * Contains a definition for our nodes, with some getter and setter values
     * <P>
     * TODO fill in
     * @author Eli Parker and Jorden Dickerson
     * @version feb 27, 2024
     */
    private class Node
    {
        private String value;
        private PriorityQueue<Node> nextNodes;
        private double frequency;

        /**
         * Normal generator for the linked list. All points defined this way MUST have a next and previous unless they are the head/tail.
         * Null values are used to show the start and end of the list
         * @param value the element stored
         * @param next the next element
         */
        Node(String value, Node next) {
            this.value = value;
            frequency = (double) 1 / (double) getTotalSize();
            this.nextNodes = new PriorityQueue<Node>();
        }

        /**
         * Gets the next node in the priority queue of nodes
         * @return the next node object
         */
        private Node getNext()
        {
            return nextNodes.peek();
        }

        /**
         * Gets the value of the current node
         * @return the value of the current node object
         */
        private String getValue()
        {
            return this.value;
        }

        /**
         * Sets the pointer to the next node
         * @param next the next node
         */
        private void addNext(Node next){
            nextNodes.add(next);
        }
    }




}
