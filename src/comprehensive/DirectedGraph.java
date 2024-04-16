package comprehensive;

import java.util.*;

/**
 * An implementation of a heap where the item at the top is the largest, and smaller items are further in the heap.
 * The "largest" item is the item with the most weight.
 * Originally from a previous assignment, this has an added peekRandom() method
 * @author Eli Parker & Jorden Dickerson
 * @version Apr 15, 2024
 */
public class DirectedGraph {

    private HashMap<String,ArrayList<WordNode>> map;
    private ArrayList<Integer> backingQueueWeight;
    private ArrayList<String> backingQueueString;
    private Random rng;

    /**
     * Creates a new DirectedGraph object
     */
    public DirectedGraph() {
        this.map = new HashMap<>();
        this.backingQueueWeight = new ArrayList<>();
        this.backingQueueString = new ArrayList<>();
        rng = new Random();
    }


    /**
     * Adds the given item to this priority queue.
     * O(1) in the average case, O(log N) in the worst case
     *
     * @param item the item to add
     */
    public void add(String item, int weight)
    {
        //add item to the end of the array
        backingQueueString.add(item);
        backingQueueWeight.add(weight);
        //shift it to the correct position
        percolateUp(backingQueueWeight.size() - 1);
    }

    /**
     * Returns, but does not remove, the maximum item this priority queue.
     *
     * @return the maximum item
     * @throws NoSuchElementException if this priority queue is empty
     */
    public String peek() throws NoSuchElementException {
        if(backingQueueWeight.isEmpty())
            throw new NoSuchElementException("The priority queue is empty");
        return backingQueueString.get(0);
    }

    /**
     * Returns, but does not remove, a random item this priority queue.
     * @return the maximum item
     * @throws NoSuchElementException if this priority queue is empty
     */
    public String peekRandom() throws NoSuchElementException {
        if(backingQueueWeight.isEmpty())
            throw new NoSuchElementException("The priority queue is empty");
        return backingQueueString.get(rng.nextInt(0, backingQueueWeight.size()));
    }

    /**
     * Returns the number of items in this priority queue.
     * O(1)
     */
    public int size() {
        return backingQueueWeight.size();
    }

    /**
     * Returns true if this priority queue is empty, false otherwise.
     * O(1)
     */
    public boolean isEmpty() {
        return backingQueueWeight.isEmpty();
    }

    /**
     * Empties this priority queue of items.
     * O(1)
     */
    public void clear() {
        backingQueueWeight = new ArrayList<>();
        backingQueueString = new ArrayList<>();
    }

    /**
     * Creates and returns an array of the items in this priority queue,
     * in the same order they appear in the backing array.
     * O(N)
     * <p>
     * (NOTE: This method is needed for grading purposes. The root item
     * must be stored at index 0 in the returned array, regardless of
     * whether it is in stored there in the backing array.)
     */
    public String[] toArray() {

        return backingQueueWeight.toArray(new String[0]);
    }

    /**
     * percolates the object down, starting from the top and going down
     * @param index The index of the object to compare
     */
    private void percolateDown(int index)
    {
        int i = index;
        //assign the item and it's children to variables
        var thisItem = backingQueueWeight.get(i);
        //set the left and right children, set to null if they don't exist
        var leftChild = (i*2) + 1 >= backingQueueWeight.size() ? null : backingQueueWeight.get((i*2) + 1);
        var rightChild = (i*2) + 2 >= backingQueueWeight.size() ? null : backingQueueWeight.get((i*2) + 2);

        if (leftChild == null && rightChild == null){
            return;
        }
        //loop through while the item is less than either of its children
        //while(((thisItem != null && leftChild != null )&& (comparator.compare(thisItem, leftChild) < 0)) || ((thisItem != null && rightChild != null) && (comparator.compare(thisItem, rightChild) < 0))){
        while(((thisItem != null && leftChild != null )&& (thisItem.compareTo(leftChild) < 0)) || ((thisItem != null && rightChild != null) && (thisItem.compareTo(rightChild) < 0))){

            //if the left child is greater than the right child
            if(rightChild == null)
            {
                //swap the left child with the item and change the index to the new spot
                swap(i, (i*2) + 1);
                //i = (i * 2) + 1; TODO deleteMe if no bugs
                break;
            }


            if(leftChild.compareTo(rightChild) > 0){
                //swap the left child with the item and change the index to the new spot
                swap(i, (i*2) + 1);
                i = (i * 2) + 1;
            }
            else{
                //else, swap the right child with the item and change the index to the new spot
                swap(i, (i*2) + 2);
                i = (i * 2) + 2;
            }

            if (i >= backingQueueWeight.size() || (i*2) + 1 >= backingQueueWeight.size() || (i*2) + 2 >= backingQueueWeight.size()){
                break;
            }

            //update item values
            thisItem = backingQueueWeight.get(i);
            leftChild = (i*2) + 1 >= backingQueueWeight.size() ? null : backingQueueWeight.get((i*2) + 1);
            rightChild = (i*2) + 2 >= backingQueueWeight.size() ? null : backingQueueWeight.get((i*2) + 2);
        }


    }

    /**
     * percolates an element from the bottom up, swapping through elements until it reaches a correct spot
     * @param index the index of the element to swap
     */
    private void percolateUp(int index)
    {
        //find a starting index
        int currentindex = index;
        int divIndex = currentindex/2;
        //this item
        var thisItem = backingQueueWeight.get(currentindex);
        var parentItem = backingQueueWeight.get(divIndex); // parent element
        while(thisItem.compareTo(parentItem) > 0)
        {
            //swap elements and iterate
            swap(currentindex,divIndex);
            currentindex = divIndex;
            divIndex /= 2;
            thisItem = backingQueueWeight.get(currentindex);
            parentItem = backingQueueWeight.get(divIndex);
        }
    }

    /**
     * swaps two items at a given index
     * @param index1 the first index to swap
     * @param index2 the second index to swap
     */
    private void swap(int index1, int index2)
    {
        //set temporary variables
        var swapWeight1 = backingQueueWeight.get(index1);
        var swapWeight2 = backingQueueWeight.get(index2);

        var swapString1 = backingQueueString.get(index1);
        var swapString2 = backingQueueString.get(index2);
        //swap variables
        backingQueueWeight.set(index1, swapWeight2);
        backingQueueWeight.set(index2, swapWeight1);

        backingQueueString.set(index2, swapString1);
        backingQueueString.set(index1, swapString2);
    }

    /**
     * takes the backing array and makes it a heap.
     */
    private void buildHeap()
    {
        //find the minimum for the array for every item in the array using a buildHeap operation
        int startingIndex = (backingQueueWeight.size()/2) - 1 ;

        for(int i = startingIndex;i >= 0;i--)
        {
            percolateDown(i);
        }

    }
    private class WordNode
    {
        private String word;
        private int weight;
        public WordNode(String word, int weight)
        {
        }

        public String getWord()
        {

        }
    }
}
