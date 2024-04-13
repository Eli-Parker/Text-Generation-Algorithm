package comprehensive;

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
    //TODO finish readme
    //TODO another class for processing file
    //TODO determine data structure to use for this (I vote directed graph of some sort)
    /**
     * Method where actual code is run.
     * TODO outline process in this method
     *
     * @param args command line arguments to add to program TODO use these to get text docs
     */
    public static void main(String[] args)
    {
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
