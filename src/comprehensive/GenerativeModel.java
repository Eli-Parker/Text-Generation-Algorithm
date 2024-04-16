package comprehensive;

import java.io.File;
import java.util.ArrayList;


public class GenerativeModel
{
    //TODO generative model implementation
    private File inputFile;
    private ArrayList<DirectedGraph> adjList;

    //The total number of words in the model

    /**
     * Creates a new GenerativeModel object, parses the given
     */
    public GenerativeModel()
    {
        //i
        adjList = new ArrayList<>();
    }

    /**
     * Gets the number of vertices in the model
     * @return the number of vertices in the model
     */
    private int size()
    {
        return adjList.size();
    }




}
