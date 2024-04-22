package comprehensive;

import java.io.FileNotFoundException;

/**
 * Contains a generative text algorithm which generates predicted text based on a given input
 * <P>
 * Implementation uses a weighted directed graph to determine most probable next word given a prompt,
 * referred to as a Markov Chain.
 * <P>
 * Command line arguments are as follows:
 * <p>
 * - 1st arg: file path of the text file to generate text from, filepath must be valid
 * <p>
 * - 2nd arg: seed word, used to start the text generation
 * (note: program will return nothing if no references to the word are found in the given text file)
 * <p>
 * - 3rd arg: K, the number of words to generate
 * <p>
 * - 4th arg: (optional) the type of generation, either "all" to get a random word
 * from the valid pairs or "one" for the most probable one. If not given,
 * program defaults to returning the K most probable words to come after the seed word.
 * @see GenerativeModel for more specific details on the implementation
 * @see DirectedGraph for the graph implementation
 * @version Apr 22, 2024
 * @author Eli Parker and Jorden Dickerson
 */
public class TextGenerator
{
    /**
     * Method where actual code is run.
     * @param args command line arguments to add to program, usage detailed in Class Javadoc
     * @throws FileNotFoundException if the given filepath is invalid
     * @throws IllegalArgumentException if the 3rd argument is not an integer, or if the number of arguments is incorrect
     */
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException
    {
        //take the file path argument and pass into generative model
        GenerativeModel model = new GenerativeModel(args[0]);

        //check to see that args[2] is a valid integer
        try
        {
            Integer.parseInt(args[2]);
        }
        catch(NumberFormatException e)
        {
            throw new IllegalArgumentException("Third argument must be an integer");
        }

        //switch on the number of command line arguments
        switch(args.length)
        {
            case 3:
                //return k most probable words for the given word
                model.generateText(args[1], Integer.parseInt(args[2]));
                break;
            case 4:
                //return k predicted words for the given seed word
                model.generateText(args[1], Integer.parseInt(args[2]), args[3]);
                break;
            default:
                //incorrect number of command line arguments, throw exception
                throw new IllegalArgumentException("Incorrect number of command line arguments");
        }
    }
}
