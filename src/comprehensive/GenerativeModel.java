package comprehensive;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Contains a generative model algorithm which generates predicted text based on a given input.
 * TODO add more details
 * @author Eli Parker & Jorden Dickerson
 * @version Apr 17, 2024
 */
public class GenerativeModel
{
    //The adjacency list representation of the graph
    private DirectedGraph graph;

    /**
     * Creates a new GenerativeModel object, initializes the graph
     * and fills it with the words from the given file.
     */
    public GenerativeModel(String filePath) throws FileNotFoundException
    {
        //initialize the graph by calling a helper method
        graph = new DirectedGraph();
        createGraph(filePath);
    }


    /**
     * Generates text based on the given seed word and number of words to generate,
     * The type of generation to use is specified by the generationType.
     * This represents the 4 command-line argument case.
     * @param seed the seed word to generate text from
     * @param numOfWordsToGenerate the number of words to generate
     * @param generationType the type of generation to use, must be either "all" or "one"
     * @throws IllegalArgumentException if the generation type is not "all" or "one"
     */
    public void generateText(String seed, int numOfWordsToGenerate, String generationType)
    {
        //switch on the generation type
        switch(generationType.toLowerCase())
        {
            case "all":
                //generate random text
                generateRandomText(seed, numOfWordsToGenerate);
                break;
            case "one":
                //generate the most probable text
                generateMostProbableText(seed, numOfWordsToGenerate);
                break;
            default:
                //generation type is invalid, throw an exception
                throw new IllegalArgumentException("Invalid generation type");
        }
    }

    /**
     * Generates text based on the given seed word and number of words to generate.
     * This represents the 3 command-line argument case.
     * @param seed the seed word to generate text from
     * @param K the number of most probable words to return
     */
    public void generateText(String seed, int K)
    {
        //get the most probable words that come after the seed word
        String curWord = formatWord(seed);
        System.out.println(graph.getMostProbableList(curWord, K));
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
        String previousWord = "";
        while(scanner.hasNextLine())
        {
            String[] words = scanner.nextLine().trim().replaceAll(" +", " ").split(" ");
            for(String word : words)
            {
                var formattedWord = formatWord(word); // format the word
                if(!previousWord.isEmpty() && !formattedWord.isEmpty())
                {
                    //if the word has a previous word, add a connection from the previous word to the current word
                    this.graph.addConnection(previousWord, formattedWord);
                }
                //if the formatted word is not empty, set the previous word to the current word
                if(!formattedWord.isEmpty()) {
                    previousWord = formattedWord;
                }
            }
        }
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
        for (int i = 0; i < numWords - 1; i++) {
            result.append(curWord).append(" ");
            curWord = graph.getRandom(curWord); // set the current word to a random next word

//            //if the current word is empty, set it to a random word
//            if (curWord.isEmpty())
//                curWord = graph.getRandom(seed);
            //if the current word is empty, set it to the seed word
            if (curWord.isEmpty())
                curWord = formatWord(seed);
        }
        //add the last word to the result without whitespace
        if(numWords > 0) {
            if(!curWord.isEmpty())
                result.append(curWord);
            else
                result.append(seed);
        }


        //print the result to the console
        System.out.println(result);
    }

    /**
     * Generates text based on the given seed word and number of words to generate.
     * This is the "one" generation type, which generates the most probable next word each time.
     * @param seed the seed word to generate text from
     * @param numWords the number of words to generate
     */
    private void generateMostProbableText(String seed, int numWords)
    {
        StringBuilder result = new StringBuilder();
        String curWord = formatWord(seed);
        //for the number of words to generate, add the formatted word to the result and get the next word
        for(int i = 0; i < numWords - 1; i++){
            result.append(curWord).append(" ");
            curWord = graph.getMax(curWord); // set the current word to the maximum word
            //if the current word is still empty, set it to the seed word
            if (curWord.isEmpty())
                curWord = formatWord(seed);
        }
        if(numWords > 0 && !curWord.isEmpty())
            result.append(curWord);
        else if(curWord.isEmpty())
            result.append(seed);
        //print the result to the console
        System.out.println(result);
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
            if (Character.toString(character).matches("[^\\w\\s]")){
                break;
            }
            //if the character is valid (abc,0-9), add it to the result. ignore any other characters
            result.append(character);
        }

        return result.toString().toLowerCase();
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
     * Formats an array of words TODO why do we have this?
     * @param words the words to format
     * @return an array of formatted words
     */
    public static String[] formatWords(String[] words)
    {
        ArrayList<String> result = new ArrayList<>();
        for(String word : words)
        {
            var formattedWord = formatWord(word);
            if(!formattedWord.isEmpty())
            {
                result.add(formattedWord);
            }
        }
        return result.toArray(new String[0]);
    }

}
