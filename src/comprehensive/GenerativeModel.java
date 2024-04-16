package comprehensive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Contains a generative model algorithm which generates predicted text based on a given input.
 *
 */
public class GenerativeModel
{
    //TODO generative model implementation

    //The adjacency list representation of the graph
    private DirectedGraph graph;

    /**
     * Creates a new GenerativeModel object, parses the given file and creates a graph from it.
     */
    public GenerativeModel(String filePath) throws FileNotFoundException {
        this.graph = new DirectedGraph();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
        {
            String[] words = scanner.nextLine().split("");
            String previousWord = "";
            for(String word : words)
            {
                var formattedWord = formatWord(word);
                if(!previousWord.equals(""))
                {
                    this.graph.addConnection(previousWord, word);
                }
                previousWord = word;
            }
        }
    }

    /**
     * Gets the number of vertices in the model
     * @return the number of vertices in the model
     */
    private int size()
    {
        return graph.size();
    }

    /**
     * Determines if a word has bad formatting
     * @param word
     * @return
     */
    private String formatWord(String word)
    {
//        return word.contains(".!@#$%^&*()");
        return "TODO";
    }





}
