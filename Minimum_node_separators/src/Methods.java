
import java.io.*;
import java.util.*;


public class Methods {
    private static Set<Vertex> bfs(ELGraph graph, Vertex vertex){
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
//        System.out.println("bfs: " + nodeExplored.size());
        return nodeExplored;
    }

    private static ELGraph<String, String> readGraph(String filePath) throws IOException {
        ELGraph<String, String> graph = new ELGraph<>();
        File file = new File(filePath);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        String heads = br.readLine();
        String head[] = heads.split(" ");
        System.out.println("Vertex: "+head[0]+"\nEdges: "+head[1]+"\nAlpha: "+head[2]);
        HashMap<String, Vertex<String>> maps = new HashMap<>();
        for (int i = 0; i < Integer.parseInt(head[0]); i++){
            Vertex<String> vert = graph.insertVertex(i+"");
            maps.put(i+"",vert);
        }
        for (int j = 0; j < Integer.parseInt(head[1]);j++){
            line = br.readLine();
            String[] edges = line.split(" ");
            graph.insertEdge(maps.get(edges[0]), maps.get(edges[1]), null);
        }

        return graph;
    }

    private static Vertex getRandomVertex(Graph graph){
        ArrayList<Vertex> vertexList = new ArrayList<>();
        for(Object e :graph.vertices()) vertexList.add((Vertex) e);
        int random = (int) (Math.random()*(vertexList.size()-1)-0);
        return vertexList.get(random);
    }
    private static boolean checkAlpha(ELGraph graph, double param){
        boolean isInAlpha = true;
        Set<Vertex> allVertex = graph.getVertexList();
        while(allVertex.size()>param){
            ArrayList<Vertex> arrayVertex = new ArrayList<>(graph.getVertexList());
            Vertex aux = arrayVertex.get(0);
            Set<Vertex> bfsResult = bfs(graph, aux);
            if (bfsResult.size() <= param) {
                Set<Vertex> intersection = new HashSet<>(allVertex);
                intersection.retainAll(bfsResult);
                allVertex = intersection;
                System.out.println();
            } else {
                isInAlpha = false;
                break;
            }
        }
        return isInAlpha;
    }
    public static void main(String[] args) throws IOException {
        double alpha = 0.4;
        String graph1 = "erdos_renyi_small/erdos_renyi_100_0.05_0.2_0.txt";
        String graph2 = "erdos_renyi_small/grafo.txt";
        ELGraph<String, String> graph = readGraph(graph1);
        Solution solution = new Solution(graph);

        int n = graph.getSize();

        double param = alpha * n;

        System.out.println("Param :" +param);
        while (!checkAlpha(graph, param)){
            Vertex deleted = getRandomVertex(graph);
            System.out.println("Vertex: "+ deleted.getValue() +" deleted");
            graph.removeVertex(deleted);
            solution.insertVertexDeleted(deleted);

        }
        System.out.println(solution.toString());

    }
}
