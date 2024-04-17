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
 * @version Apr 17, 2024
 * @author Eli Parker and Jorden Dickerson
 */
public class TextGenerator
{
    //for the third command line argument, dictates how many words to generate
    private int K;
    //TODO finish readme
    /**
     * Method where actual code is run.
     * @param args command line arguments to add to program, usage detailed in Class Javadoc
     * @throws FileNotFoundException if the given filepath is invalid
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        //take the file path argument and pass into generative model

        GenerativeModel model;
        //check for valid filepath
        model = new GenerativeModel(args[0]);

        if(args.length == 3)
        {
            //return k words for the given word

        }else if (args.length == 4)
        {
            //return k predicted words for the given seed word


        }else
        {
            throw new IllegalArgumentException("Incorrect number of command line arguments");
        }
        //TODO the process
        /*
            1. handle command line arguments.
                - first, We will have to first take the file as the first argument and process it,
                  we should likely make a second class in the package dedicated to this process.
                - second, the seed word. This is what we'll generate our text from
                - a variable K for the third argument, tells us how many words to predict
                - handle fourth arg according to assignment spec
         */
    }
}
