package comprehensive;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TimerTemplateExtention extends TimerTemplate {




    /**
     * Create a timer
     *
     * @param problemSizes array of N's to use (length of array)
     * @param timesToLoop  number of times to repeat the tests
     */
    public TimerTemplateExtention(int[] problemSizes, int timesToLoop) {
        super(problemSizes, timesToLoop);
    }

    private static final int starting  = 1;
    private static final int increment = 1;
    private static final int ending = 2;


    public static void main(String[] args){

        ArrayList<Integer> ns = new ArrayList<>();
        for(double n = starting ; n <= ending; n += increment){
            ns.add((int)n);
        }

        //convert to int[]
        int[] problemSizes = new int[ns.size()];
        for(int i = 0; i < problemSizes.length; i++){
            problemSizes[i] = ns.get(i);
        }

        var timer = new TimerTemplateExtention(problemSizes, 10);
        var results = timer.run();
        System.out.println("n, time");
        for(var result: results){
            if(result.n() > 10) {
                System.out.println(result.n() + ", " + result.avgNanoSecs());
            }
            else
            {
                System.out.println("0" + result.n() + ", " + result.avgNanoSecs());

            }
        }
    }




    @Override
    protected void setup(int n) {

    }


    @Override
    protected void timingIteration(int n)
    {
        try {
            GenerativeModel model = new GenerativeModel("src/warAndPeace.txt");
            //model.generateText("the", n,"all");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    @Override
    protected void compensationIteration(int n) {

    }
}
