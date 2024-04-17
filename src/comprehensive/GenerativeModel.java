package comprehensive;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Contains a generative model algorithm which generates predicted text based on a given input.
 * <P>
 *
 */
public class GenerativeModel
{
    //TODO generative model implementation

    //The adjacency list representation of the graph
    private DirectedGraph graph;

    /**
     * Creates a new GenerativeModel object, initializes the graph
     * and fills it with the words from the given file.
     */
    public GenerativeModel(String filePath) throws FileNotFoundException
    {
        //initialize the graph
        graph = new DirectedGraph();
        createGraph(filePath);
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
                    this.graph.addConnection(previousWord, formattedWord);
                }
                previousWord = formattedWord;
            }
        }
    }

    /**
     * Gets the graph as a string
     * @return the graph represented as a string
     */
    public String getGraph()
    {
        String[] vertexes = graph.getVertexes();
        String[] result = new String[vertexes.length];
        int i = 0;
        for(String vertex : vertexes)
        {
            result[i] = vertex + " - " + graph.getMostProbableList(vertex, 9999999);
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
            //if the character is valid (abc,0-9), add it to the result. ignore any other characters
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
     * Generates random text based on the given seed word and number of words to generate.
     * This is the "all" generation type, which generates a random word each time.
     * @param seed the seed word to generate text from
     * @param numWords the number of words to generate
     */

    private void generateRandomText(String seed, int numWords) {
        StringBuilder result = new StringBuilder();
        String curWord = formatWord(seed);
        //for the number of words to generate, add the formatted word to the result and get the next word
        for(int i = 0; i <= numWords; i++){
            result.append(curWord).append(" ");
            curWord = graph.getRandom(curWord); // set the current word to a random next word
            //if the current word is empty, set it to a random word
            if(curWord.isEmpty()){
                curWord = graph.getRandom(seed);
            }
        }
        //print the result to the console
        System.out.println(result.toString());
    }

    /**
     * Generates text based on the given seed word and number of words to generate.
     * This is the "one" generation type, which generates the most probable next word each time
     * @param seed the seed word to generate text from
     * @param numWords the number of words to generate
     */
    private void generateMostProbableText(String seed, int numWords)
    {
        StringBuilder result = new StringBuilder();
        String curWord = formatWord(seed);
        //for the number of words to generate, add the formatted word to the result and get the next word
        for(int i = 0; i <= numWords; i++){
            result.append(curWord).append(" ");
            curWord = graph.getMax(curWord); // set the current word to a random next word
            //if the current word is empty, set it to a random word
            if(curWord.isEmpty()){
                curWord = graph.getMax(seed);
            }
        }
        //print the result to the console
        System.out.println(result.toString());
    }

    private void generateKMostProbable(String seed, int K)
    {
        String curWord = formatWord(seed);
        System.out.println(graph.getMostProbableList(curWord, K));

    }


    /**
     * Generates text based on the given seed word and number of words to generate,
     * the type of generation to use is specified by the generationType
     * @param seed the seed word to generate text from
     * @param numOfWordsToGenerate the number of words to generate
     * @param generationType the type of generation to use, must be either "all" or "one"
     * @throws IllegalArgumentException if the generation type is not "all" or "one"
     */
    public void generateText(String seed, int numOfWordsToGenerate, String generationType)
    {
        if(generationType.equalsIgnoreCase("all"))
        {
            System.out.println("Generating random text...");
            generateRandomText(seed, numOfWordsToGenerate);
        }
        else if(generationType.equalsIgnoreCase("one"))
        {
            System.out.println("Generating most probable text...");
            generateMostProbableText(seed, numOfWordsToGenerate);
        }
        else
        {
            throw new IllegalArgumentException("Invalid generation type");
        }

    }

}
