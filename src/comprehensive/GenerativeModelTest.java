package comprehensive;

import static org.junit.jupiter.api.Assertions.*;

public class GenerativeModelTest {

    GenerativeModel model;
    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        //model = new GenerativeModel();
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

}
