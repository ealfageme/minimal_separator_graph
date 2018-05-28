import java.util.HashSet;
import java.util.LinkedList;

import java.util.Set;

public class Main {
    public static int bfs(ELGraph graph, Vertex vertex){
        LinkedList queue = new LinkedList();
        queue.add(vertex);
        Set nodeExplored = new HashSet();
        nodeExplored.add(vertex);
        while(!queue.isEmpty()) {
            Vertex searched = (Vertex) queue.poll();
            HashSet <Edge> incidentEdges = (HashSet<Edge>) graph.incidentEdges(searched);
            for (Edge edge : incidentEdges){
                Vertex oposite = graph.opposite(searched,edge);
                if (!nodeExplored.contains(oposite)){
                    nodeExplored.add(oposite);
                    queue.add(oposite);
                }
            }
        }
        return nodeExplored.size();



    }
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
        System.out.println(bfs(graph, a));

        // Obtain alpha
        int alpha = 2;
        //          Iterator
        //white     Delete Random Node
        //          Check Iterator
        // Show Solution
    }
}
