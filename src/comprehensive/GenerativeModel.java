package comprehensive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
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
        //for each line in the file, separate the words and add them to the graph as connections
        while(scanner.hasNextLine())
        {
            String[] words = scanner.nextLine().split(" ");
            String previousWord = "";
            for(String word : words)
            {
                var formattedWord = formatWord(word); // format the word
                if(!previousWord.equals("") && !formattedWord.equals(""))
                {
                    //if the word has a previous word, add a connection from the previous word to the current word
                    this.graph.addConnection(previousWord, word);
                }
                previousWord = formattedWord;
            }
        }
    }

    /**
     * spits out the graph's vertexes and nodes
     *
     */
    public String getGraph()
    {
        String[] vertexes = graph.getVertexes();
        String[] result = new String[vertexes.length];
        int i = 0;
        for(String vertex : vertexes)
        {
            result[i] = formatWord(vertex + " - " + Arrays.toString(graph.getMostProbableList(vertex, 9989999)));
            i++;
        }
        return Arrays.toString(result);
    }

    /**
     * Gets the number of vertices in the model
     * @return the number of vertices in the model
     */
    public int size()
    {
        return graph.size();
    }

    /**
     * Removes bad formatting from the given word
     * @param word the word to format
     * @return a formatted word, or an empty string if the word is not valid
     */
    public static String formatWord(String word)
    {
        //initialize a string builder to hold the formatted word
        StringBuilder result = new StringBuilder(word.length());
        //convert the word to a character array and loop through each character
        for (char character : word.toCharArray()) {
            //if the character is a punctuation character, break the loop (no characters after an apostrophe are valid)
            if (character == '\'' || character == '.' || character == ',' || character == '!' || character == '?' || character == ';' || character == ':') {
                break;
            }
            //if the character is a letter, add it to the result. ignore any other characters
            if(!(Character.toString(character).matches("[^\\w\\s]") || Character.toString(character).matches("_")))
            {
                result.append(character);
            }
        }

        return result.toString().toLowerCase();
    }





}
