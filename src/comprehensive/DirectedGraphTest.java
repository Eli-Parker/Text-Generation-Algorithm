package comprehensive;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    DirectedGraph graph;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        graph = new DirectedGraph();
        graph.addConnection("hello", "world");
        graph.addConnection("hello", "world");
        graph.addConnection("hello", "its");
        graph.addConnection("hello", "its");
        graph.addConnection("hello", "its");
        graph.addConnection("hello", "third");
        graph.addConnection("its", "me");
        graph.addConnection("its", "johnny");
        graph.addConnection("me", "world");
    }


    @org.junit.jupiter.api.Test
    void getMax() {
        assertEquals("its",graph.getMax("hello"));
    }

    @org.junit.jupiter.api.Test
    void getRandom()
    {
        assertTrue(
                "world".equals(graph.getRandom("hello"))
                || "its".equals(graph.getRandom("hello"))
                || "third".equals(graph.getRandom("hello")));
    }

    @org.junit.jupiter.api.Test
    void getMostProbableList()
    {
        var expected = "its world";
        var actual = graph.getMostProbableList("hello",2);
        assertEquals(expected,actual);
    }

    @org.junit.jupiter.api.Test
    void testRandomnessWeight()
    {
        int probablilityFirst = 0;
        int probablilitySecond = 0;
        int probablilityThird = 0;
        for(int i =0; i < 100000; i++)
        {
            var random = graph.getRandom("hello");
            switch(random)
            {
                case "its":
                    probablilityFirst++;
                    break;
                case "world":
                    probablilitySecond++;
                    break;
                case "third":
                    probablilityThird++;
                    break;

            }
        }
        System.out.println("weight three (expected 1/2%): " + probablilityFirst );
        System.out.println("weight two (expected 1/3%): "   + probablilitySecond);
        System.out.println("weight one (expected 1/6%): "   + probablilityThird );
    }

    @org.junit.jupiter.api.Test
    void size()
    {
        assertEquals(3,graph.size());
    }
}