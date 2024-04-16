package comprehensive;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    DirectedGraph graph;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        graph = new DirectedGraph();
        graph.addConnection("hello", "world");
        graph.addConnection("hello", "its");
        graph.addConnection("hello", "its");
        graph.addConnection("its", "me");
        graph.addConnection("me", "world");
    }


    @org.junit.jupiter.api.Test
    void getMax() {
        assertEquals("its",graph.getMax("hello"));
    }

    @org.junit.jupiter.api.Test
    void getRandom()
    {
        assertTrue("world".equals(graph.getRandom("hello")) || "its".equals(graph.getRandom("hello")));
    }

    @org.junit.jupiter.api.Test
    void getMostProbableList()
    {
        var expected = new String[]{"world","its"};
    }

    @org.junit.jupiter.api.Test
    void size()
    {
        assertEquals(3,graph.size());
    }
}