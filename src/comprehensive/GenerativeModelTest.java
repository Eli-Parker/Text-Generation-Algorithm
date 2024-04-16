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
            model = new GenerativeModel("GPT-0.1/abc.txt");
        }catch(FileNotFoundException e)
        {
            System.out.println("fuck");
        }
    }

    @org.junit.jupiter.api.Test
    void testBadWord()
    {
        assertEquals("",GenerativeModel.formatWord("'ere's"));
        assertEquals("normal",GenerativeModel.formatWord("normal"));
        assertEquals("normal",GenerativeModel.formatWord("NORMAL"));
        assertEquals("normal",GenerativeModel.formatWord("N#OR@M%A/?.>,<!~`][;|}{:=-+_)(*&^%$#@#L"));
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
}
