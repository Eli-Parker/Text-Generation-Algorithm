# Introduction

This is a representation of a very simple text prediction algorithm which processes a 
text file and returns K words of predicted text based on a "seed" file provided in a command line argument. 
The implementation uses a directed graph to assign weights to each word based on the frequency of that word 
coming after the previous one, and uses Dijkstra's algorithm to make a "path" from word to word.

Notably this is a similar model to that of ChatGPT at a *much* smaller scale, hence the tongue in cheek name. 

 ** **This program has _NO_ affiliation with OpenAI. We are only using the GPT suffix as a 
quick reference to identify the program's function.** **

### Usage

The program requires either 3 or 4 arguments passed into the `args[]` parameter when running the program.
The first three arguments are always the same. The inclusion of the 4th argument changes the program's behavior. 
**Note: The program will not run unless the three arguments are passed properly.**
- **Argument 1** contains the filepath of a `.txt` file. The text file should contain a list of words. All punctuation, 
    word case (ex: HELLO & Hello == "hello"),
    words with strange punctuation (ex: 'ere's | .ea,p.m), and words containing anything except the alphabet will be ignored
    in the algorithm
- **Argument 2** contains a starting word, or "seed word". This is the word which the algorithm will use as 
    a starting point for the generation
- **Argument 3**, referred to as K, is the number of words to generate after the seed word
- **Argument 4** is the special argument. The argument must contain the strings `all` or `one`, or should not be present at all
  - if `all` is passed in, the output will be generated by randomly selecting words based on probability
  - if `one` is passed in, the output will always select the most probable word for the one it's looking at. 
  It will break ties lexicographically
  - if no argument is passed, it will output the K most probable words for the given word. 
  If less than K probable words exist for the given one, it will display however many it has
### Example

A valid command line call for this function would be the following:

`javac comprehensive/TextGenerator.java comprehensive/GenerativeModel.java`


`java comprehensive.TextGenerator sample.txt hello 10 one`

Or, without the 4th argument it would be:

`javac comprehensive/TextGenerator.java comprehensive/GenerativeModel.java`


`java comprehensive.TextGenerator sample.txt hello 4`


### Acknowledgements

This program was written in conjunction with my partner for the class, **Jorden Dickerson**. 
He is an incredible programmer, and he's been a joy to work with over the past months! 
I owe much of the impeccable programming done here to him and our teamwork.
Thank you Jorden :) your inclusion has been much appreciated.

## Original Assignment Description
To properly show the requirements the assignment is under, I've included the broad strokes of the assignment 
description below which detail grading structure and code functionality requirements.

All text shown below is directly taken from the assignment page.
### The Problem:

You have been asked to create a program that can take a text file as input and generate some text as output that has some similar characteristics as the input. 
It is not necessary that the output represent correct language or even be meaningful, but it needs to contain similar words in similar patterns. 
As a part of your program, you need to create at least two Java classes. One of these represents the model, which processes the input and provides text generation capability. 
The other represents the command-line application that allows the user to interface with the model. Specific requirements are detailed below.

To simplify the issues related to dealing with capitalization and  punctuation (periods at the end of sentences, apostrophes for possessives/contractions, etc), 
we'll tweak the input when building our model to reduce the number of different words we consider.  For the purposes of this assignment, words cannot contain whitespace or punctuation.  
If a sequence of characters without whitespace contains punctuation characters, the "word" is defined as the characters before the first punctuation character, if any.

As an example, in this string: "I'm a little Teapot short and stout.  Here's my handle, 'ere's my spout" the words are:
[i, a, little, teapot, short, and, stout, here, my, handle, my, spout]

All letters are lowercased, and anything after a punctuation character is dropped.  
Note that "'ere's" contributes no words to the output because there are no letters before the apostrophe.

This "word simplification" strategy should help us produce more interesting outputs from smaller training input files.

For this assignment, we are defining the probability of a word coming after another as the number of times this occurred in the input text 
divided by the total number of words that came after the first word. This is a number between 0.0 and 1.0 inclusive. 
This means that somewhere in your code you need to be keeping track of this probability for each possible two-word sequence. 
There are many ways to accomplish that. Consider the most efficient data structures and algorithms for this application while considering the required functionality.


### Requirements:

Create a new class named TextGenerator in a new package called comprehensive. The user starts your program by running this class (i.e., by executing TextGenerator's main method). There are several command-line arguments required that must match these specifications exactly and in this order.
- **First argument** - The path and name of the input text file. This can be an absolute or relative path.
- **Second argument** - The "seed" word.  We'll use your model to predict which word(s) come after the seed.  You can assume that the seed is a word contained in the input file
- **Third argument** - The number of words your program should generate as output (we'll call it K). This must be an integer that is not negative and would not overflow the int type.
- **(Optional) Fourth argument** - The fourth argument determines what your program should output.
  - If there is no fourth argument, the output will be the K most probable words that could come after the seed word. The words must be output in descending order from most probable to least. If there are fewer than K possible next words for the first word, fewer than K words will be output. For an input file containing "hello world" example, with seed "hello", if K = 3 the output would just be one word: "world".
  - If there is a fourth argument present, it must be either the word all or the word one. If it is all, the output will be generated while selecting randomly from all possible next words based on their probabilities. If it is one, generation will only select the next word with greatest probability. Ties for greatest probability will be broken using the lexicographical ordering of the words. For example, if "apple" and "zoo" were tied for greatest probability, the word "apple" would be selected. This means that the output using argument one is deterministic, while the output for all is not. In either case, the first word in the output must be the "seed" word.
  - If you encounter a situation where there is no possible next word and you need to generate more words, the next word will again be the seed word. For example, If the input contains the text "hello world", and the number of needed output words is 7, and the seed is "hello", then the output will be "hello world hello world hello world hello".

**Note that your solution must handle both three and four argument cases.**

 

We will not test your program with invalid command-line inputs (input files will always exist, seed words are always included in the input, K will always be a positive number, the 4th argument, if it exists will be "all" or "one")
The output must be printed to System.out and follow these specifications.
      • The only printed output is the generated text. Don't print anything else before or after the text.
      • Word must be separated by whitespace (spaces, newlines, tabs are all OK)
      • Output should only consist of lower-case English letters, numbers 0 to 9, and underscores. There should be no other punctuation.

 

  You may create any other new classes as needed. You should avoid putting all of your logic into the TextGenerator class. That class should mainly serve as a user interface for your application. You are free to use any of the Java Collection classes. Make sure that all code files required by your program are in the comprehensive package.
  Because all grading occurs outside of Eclipse/IntelliJ, you must make certain that your program compiles and runs correctly from the command-line without Eclipse. 
    
  For example:

    javac comprehensive/TextGenerator.java comprehensive/OtherNeededClass.java  
    java comprehensive.TextGenerator input.txt word 25 all

  In this example, your working directory contains both the input.txt file and your comprehensive package (folder). The compile line will include all .java files in your package.

  Command-line arguments can also be specified in Eclipse or IntelliJ, using the Run Configurations menu.

  Take care to design your solution to be as efficient as possible.  See the section below for details of how your text generator is evaluated for running-time efficiency.

**NOTE: It is intentional that you are being given no guidance as to how to solve the problem. It is critical that you gain experience solving a problem "from scratch," designing the structure of your classes and methods, as well as choosing the best data structures and algorithms for the problem. Because the readers of your code have no assumptions about how it is organized, you must document it well.**

# OUR SOLUTION

//TODO add solution


