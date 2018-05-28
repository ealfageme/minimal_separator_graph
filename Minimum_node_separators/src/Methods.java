
import java.io.*;
import java.util.*;


public class Methods {
    private static int bfs(ELGraph graph, Vertex vertex){
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

    private static ELGraph<String, String> readGraph() throws IOException {
        ELGraph<String, String> graph = new ELGraph<>();
        File file = new File("src/graph");
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        String nodes = br.readLine();
        HashMap<String, Vertex<String>> maps = new HashMap<>();
        String[] sequenceNodes = nodes.split(",");
        for (String newNode:sequenceNodes){

            Vertex<String> aux = graph.insertVertex(newNode);
            maps.put(newNode,aux);
        }
        while((line=br.readLine())!=null) {
            line = line.replace("[","");
            line = line.replace("]","");
            String[] edges= line.split(",");
            try {
                graph.insertEdge(maps.get(edges[0]), maps.get(edges[1]), "value");
            }catch (RuntimeException e){
                System.out.println("[Error] '" + edges[0] +"' or '" + edges[1]+"' isn't a vertex");
                e.printStackTrace();
            }
        }
        return graph;
    }

    private static Vertex getRandomVertex(Graph graph){
        ArrayList<Vertex> vertexList = new ArrayList<>();
        for(Object e :graph.vertices()) vertexList.add((Vertex) e);
        int random = (int) (Math.random()*(vertexList.size()-1)-0);
        return vertexList.get(random);
    }

    public static void main(String[] args) throws IOException {


        int alpha = 2;

        ELGraph<String, String> graph = readGraph();

        Vertex startVertex = getRandomVertex(graph);
        while (graph.getSize()== bfs(graph,startVertex)){

            Vertex deleted = getRandomVertex(graph);
            System.out.println("Vertex :"+ deleted +" deleted");
            graph.removeVertex(deleted);
            startVertex = getRandomVertex(graph);

        }
        System.out.println(graph);

    }
}
