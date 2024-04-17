package comprehensive;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GenerativeModelTest {

    GenerativeModel model;
    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        model = new GenerativeModel();

    }

    @org.junit.jupiter.api.Test
    void testBadWord()
    {
        assertEquals("",GenerativeModel.formatWord("'ere's"));
        assertEquals("normal",GenerativeModel.formatWord("normal"));
        assertEquals("normal",GenerativeModel.formatWord("NORMAL"));
        assertEquals("norm",GenerativeModel.formatWord("N#O#R#M#.A#L"));
        assertEquals("hehehe",GenerativeModel.formatWord("hehehe'ha"));
    }

    @org.junit.jupiter.api.Test
    void testSize()
    {
    }

    @org.junit.jupiter.api.Test
    void testBasicOutput()
    {
//        assertEquals("[a - [b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z]]", model.getGraph());
        System.out.println(model.getGraph());
    }

    @org.junit.jupiter.api.Test
    void testGenerateText() throws FileNotFoundException
    {
        model.generateText("the-old-english-physiologus.txt", "the", 10);
    }
}
