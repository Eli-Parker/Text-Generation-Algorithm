package comprehensive;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GenerativeModelTest {

    GenerativeModel model;
    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        try {
            model = new GenerativeModel("abc.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Oh no!");
        }

    }

    @org.junit.jupiter.api.Test
    void testBadWord()
    {
        assertEquals("",GenerativeModel.formatWord("'ere's"));
        assertEquals("normal",GenerativeModel.formatWord("normal"));
        assertEquals("normal",GenerativeModel.formatWord("NORMAL"));
        assertEquals("n",GenerativeModel.formatWord("N#O#R#M#.A#L"));
        assertEquals("hehe_he",GenerativeModel.formatWord("hehe_he'ha"));
    }

    @org.junit.jupiter.api.Test
    void testTrickyFormatting() throws FileNotFoundException {
        var file = "trickyFormatting.txt";
        var model2 = new GenerativeModel(file);
        System.out.println(model2.getGraph());
    }

    @org.junit.jupiter.api.Test
    void testBasicOutput()
    {
        assertEquals("[wow - a, a - wow z b c d e f g h i j k l m n o p q r s t u v w x y, b - a z, c - a z, d - a, e - a, f - a, g - a, h - a, i - a, j - a, k - a, l - a, m - a, n - a, o - a, p - a, q - a, r - a, s - a, t - a, u - a, v - a, w - a, x - a, y - a, z - c a b z]", model.getGraph());
    }

    @org.junit.jupiter.api.Test
    void testGenerateText() throws FileNotFoundException
    {
        //testing the abc file to see the proportions of the values it answers on
        var sample1 = "the-old-english-physiologus.txt";
        var beeMovie = "beeMovie.txt";
        var model2 = new GenerativeModel("src/warAndPeace.txt");
        model2.generateText( "the", 10, "all");
    }

}
