public class Main {

    public static void main(String[] args) {
        // Create graph
        ELGraph graph = new ELGraph();

        Vertex a = graph.insertVertex("a");
        Vertex b = graph.insertVertex("b");
        Vertex c = graph.insertVertex("c");
        Vertex d = graph.insertVertex("d");
        Vertex e = graph.insertVertex("e");
        Vertex f = graph.insertVertex("f");
        graph.insertEdge(a,b,5);
        graph.insertEdge(a,c,3);
        graph.insertEdge(b,e,2);
        graph.insertEdge(c,f,6);
        graph.insertEdge(d,f,4);
        graph.insertEdge(d,e,3);
        System.out.println(graph);

        // Obtain alpha
        int alpha = 2;
        //          Iterator
        //white     Delete Random Node
        //          Check Iterator
        // Show Solution
    }
}
