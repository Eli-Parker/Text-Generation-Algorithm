package comprehensive;

import java.io.FileNotFoundException;

/**
 * Contains a generative text algorithm which generates predicted text based on a given input
 * <P>
 * Implements compression on the given text and uses a weighted directed graph to determine most
 * probable next word given a prompt.
 * @version Apr 13, 2024
 * @author Eli Parker and Jorden Dickerson
 */
public class TextGenerator
{
    //for the third command line argument, dictates how many words to generate
    private int K;
    //TODO finish readme
    /**
     * Method where actual code is run.
     * TODO outline process in this method
     *
     * @param args command line arguments to add to program, usage detailed above TODO detail command line args
     * @throws FileNotFoundException if the filepath is invalid
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        //take the file path argument and pass into generative model

        GenerativeModel model;
        //check for valid filepath
        model = new GenerativeModel();

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
