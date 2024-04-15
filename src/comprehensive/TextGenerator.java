package comprehensive;

import java.io.File;
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
    //TODO another class for processing file
    //TODO determine data structure to use for this (I vote directed graph of some sort)
    /**
     * Method where actual code is run.
     * TODO outline process in this method
     *
     *  FROM THE ASSIGNMENT
     *  As a part of your program, you need to create at least two Java classes.
     *  One of these represents the model, which processes the input and provides text generation capability.
     *  The other represents the command-line application that allows the user to interface with the model.
     *
     * @param args command line arguments to add to program, usage detailed above TODO detail command line args
     * @throws IllegalArgumentException if the number of command line arguments is incorrect
     */
    public static void main(String[] args) throws IllegalArgumentException, FileNotFoundException
    {
        //initialize file
        File textFile;
        if(args.length == 3)
        {
            textFile = new File(args[0]);

        }else if (args.length == 4)
        {

        }else
        {
            throw new IllegalArgumentException("Command line argument has incorrect length");
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
