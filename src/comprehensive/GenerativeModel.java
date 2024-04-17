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
     * Creates a new GenerativeModel object, initializing the graph
     */
    public GenerativeModel(){
        graph = new DirectedGraph();
    }


    /**
     * Fills the graph with the words from the given file
     * @param filePath the file path to the file to parse
     * @throws FileNotFoundException if the file path is invalid
     */
    private void createGraph(String filePath) throws FileNotFoundException {
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
            result[i] = vertex + " - " + Arrays.toString(formatWords(graph.getMostProbableList(vertex, 9989999)));
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
            if (character == '\'' || character == '.'  || character == '!' || character == '?' || character == ';' || character == ':' || character == ',') {
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

    /**
     * Formats an array of words
     * @param words the words to format
     * @return an array of formatted words
     */
    public static String[] formatWords(String[] words)
    {
        ArrayList<String> result = new ArrayList<>();
        for(String word : words)
        {
            var formattedWord = formatWord(word);
            if(!formattedWord.equals(""))
            {
                result.add(formattedWord);
            }
        }
        return result.toArray(new String[0]);
    }


    /**
     * Generates text based on the given seed word and number of words to generate
     * @param filename the file to generate text from
     * @param seed the seed word to generate text from
     * @param numOfWordsToGenerate the number of words to generate
     * @throws FileNotFoundException if the file path is invalid
     */

    public void generateText(String filename, String seed, int numOfWordsToGenerate) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        createGraph(filename); // fill the graph with the words from the file
        String curWord = seed;
        //for the number of words to generate, add the formatted word to the result and get the next word
        for(int i = 0; i < numOfWordsToGenerate; i++){
            result.append(formatWord(curWord)).append(" ");
            curWord = graph.getRandom(curWord); // set the current word to a random next word
            //if the current word is empty, set it to a random word
            //TODO: this is a temporary fix, we need to find a better way to handle this
            if(curWord.equals("")){
                curWord = graph.getRandom(seed);
            }
        }
        System.out.println(result.toString());
    }

    /**
     * Generates text based on the given seed word and number of words to generate, the type of generation to use is specified by the generationType
     * @param filename the file to generate text from
     * @param seed the seed word to generate text from
     * @param numOfWordsToGenerate the number of words to generate
     * @param generationType the type of generation to use, must be either "all" or "one"
     * @throws FileNotFoundException if the file path is invalid
     */
    public void generateText(String filename, String seed, int numOfWordsToGenerate, String generationType){
        StringBuilder result = new StringBuilder();

    }

}
