import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import java.util.Set;

public class Methods {
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
        ArrayList<Vertex> vertexList = new ArrayList<>();

        Vertex a = graph.insertVertex("a");
        vertexList.add(a);
        Vertex b = graph.insertVertex("b");
        vertexList.add(b);
        Vertex c = graph.insertVertex("c");
        vertexList.add(c);
        Vertex d = graph.insertVertex("d");
        vertexList.add(d);
        Vertex e = graph.insertVertex("e");
        vertexList.add(e);
        Vertex f = graph.insertVertex("f");
        vertexList.add(f);
        graph.insertEdge(a,b,5);
        graph.insertEdge(a,c,5);
//        graph.insertEdge(a,d,5);
//        graph.insertEdge(a,e,5);
//        graph.insertEdge(a,f,3);
        graph.insertEdge(b,c,2);
//        graph.insertEdge(b,d,2);
//        graph.insertEdge(b,e,2);
//        graph.insertEdge(b,f,2);
        graph.insertEdge(c,d,6);
        graph.insertEdge(c,e,6);
        graph.insertEdge(c,f,6);
        graph.insertEdge(d,f,4);
        graph.insertEdge(d,e,3);
//        System.out.println(graph);
//        System.out.println(bfs(graph, a));

        // Obtain alpha
        int alpha = 2;
        int random = (int) (Math.random()*(vertexList.size()-1)-0);
        Vertex startVertex = vertexList.get(random);
        while (graph.getSize()== bfs(graph,startVertex)){
            int newRandom = (int) (Math.random()*(vertexList.size()-1)-0);
            Vertex deleted = vertexList.get(newRandom);
            vertexList.remove(newRandom);
            graph.removeVertex(deleted);
            System.out.println("Vertex :"+ deleted+" deleted");
            random = (int) (Math.random()*(vertexList.size()-1)-0);
            startVertex = vertexList.get(random);

        }
        System.out.println(graph);
        //          Iterator
        //white     Delete Random Node
        //          Check Iterator
        // Show Solution
    }
}
