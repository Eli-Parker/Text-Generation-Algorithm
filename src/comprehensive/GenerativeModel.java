package comprehensive;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;


/**
 * Contains a generative model algorithm which generates predicted text based on a given input.
 * The model is based on a directed graph of word pairs which can be used to generate text.
 * <p>
 * The main processes in this method are to parse text for the DirectedGraph object, and to serve as an interface
 * for the random and maximum word generation methods. The actual storage is done in the DirectedGraph class.
 * @see DirectedGraph for the graph implementation
 * @author Eli Parker & Jorden Dickerson
 * @version Apr 22, 2024
 */
public class GenerativeModel
{
    //The adjacency list representation of the graph
    private DirectedGraph graph;

    //to use to remove bad formatting from words
    private static final Pattern regexPattern = Pattern.compile("[^\\w\\s]");

    /**
     * Creates a new GenerativeModel object, initializes the graph
     * and fills it with the words from the given file.
     */
    public GenerativeModel(String filePath) throws FileNotFoundException
    {
        //initialize the graph by calling a helper method
        graph = new DirectedGraph();
        try {
            createGraph(filePath);
        } catch (IOException e) {
            throw new FileNotFoundException("File not found");
        }
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
     * Fills the model with the words from the given file
     * @param filePath the file path of the text file to parse
     * @throws IOException if the file path is invalid
     */
    private void createGraph(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //The word that comes before the current one in the loop
        //Stored outside, so it's not overwritten with new lines
        String previousWord = "";
        String line;
        //store already formatted words to avoid extra calls to formatWord
        HashMap<String, String> formattedWords = new HashMap<>();
        //for each line in the file, separate the words and add them to the graph as connections
        while((line = reader.readLine()) != null)
        {
            ArrayList<String> words = split(line);
            String unformattedWord;
            for(int i = 0; i < words.size(); i++)
            {
                //check if the word is already formatted
                unformattedWord = words.get(i);
                String formattedWord = formattedWords.get(unformattedWord);
                if (formattedWord == null)
                {
                    //if not, format the word & add to the map
                    formattedWord = formatWord(unformattedWord);
                    formattedWords.put(unformattedWord, formattedWord);
                }

                if(!formattedWord.isEmpty())
                {
                    if(!previousWord.isEmpty()) {
                        //if the word pair is not empty, add the connection to the graph
                        this.graph.addConnection(previousWord, formattedWord);
                    }

                    //if the formatted word is not empty, set the previous word to the current word
                    previousWord = formattedWord;
                }
            }

        }
        reader.close();
    }


    /**
     * Splits the line into words separated by spaces.
     * This method is ever so slightly faster than the String.split() method.
     * @param line the line to split
     * @return an ArrayList of the words in the line split by spaces
     */
    public static ArrayList<String> split(String line) {
        ArrayList<String> returnValues = new ArrayList<>();

        //split the line into characters
        char[] chars = line.toCharArray();

        //StringBuilder to store the current word
        StringBuilder splitWord = new StringBuilder();
        int i = 0;
        for (char letter : chars)
        {
            //if the letter is not a space, add it to the current word
            if (letter != ' ')
                splitWord.append(letter);
            else
            //if the letter is a space, add the current word to the list and clear the StringBuilder
            {
                returnValues.add(splitWord.toString());
                splitWord.delete(0, splitWord.length());
            }
        }
        //add the last word to the list
        returnValues.add(splitWord.toString());
        splitWord.delete(0, splitWord.length());

        return returnValues;
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

            //if the current word is empty, set it to the seed word
            if (curWord.isEmpty())
                curWord = formatWord(seed);
        }
        //add the last word to the result without whitespace
        if(numWords > 0)
        {
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

        //add the last word to the result without whitespace
        if(numWords > 0)
        {
            if(!curWord.isEmpty())
                result.append(curWord);
            else
                result.append(seed);
        }
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
        var matcher = regexPattern.matcher(word);
        return (matcher.find()) ? word.substring(0,matcher.start()).toLowerCase(): word.toLowerCase();
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
}
