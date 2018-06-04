
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
    private static int bfs(ELGraph graph, Vertex start, Vertex end){
        int longitude = 1000;
        LinkedList queue = new LinkedList();
        List<Vertex> list = new ArrayList<>();
        list.add(start);
        queue.add(list);
        Set nodeExplored = new HashSet();
        nodeExplored.add(start);
        System.out.println("entre");
        while(!queue.isEmpty()) {
            System.out.println("entre1");
            List<Vertex> listAux = (List) queue.poll();
            Vertex searched =  listAux.get(listAux.size() - 1);
            HashSet <Edge> incidentEdges = (HashSet<Edge>) graph.incidentEdges(searched);
            for (Edge edge : incidentEdges){
                Vertex oposite = graph.opposite(searched,edge);
                if (oposite.equals(end)){
                    nodeExplored.add(oposite);
                    listAux.add(oposite);
                    longitude = listAux.size();
                    System.out.println("list" + listAux);
                    break;
                    
                }
                if (!nodeExplored.contains(oposite)){
                    nodeExplored.add(oposite);
                    listAux.add(oposite);
                    queue.add(listAux);
                }
            }
        }

        return longitude;
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

    private static Vertex getMaxGrade(ELGraph graph){
        int numEdges = 0;
        Vertex vert = null;
        Set<Vertex> allVertex = graph.getVertexList();
        for (Vertex s : allVertex) {
            if(s.getEdges() > numEdges){
                vert = s;
                numEdges = s.getEdges();
            }
        }

        return vert;
    }

//    private static Vertex getMoreCloseness(ELGraph graph){
//
//    }
    private static double closeness(ELGraph graph, Vertex v){
        double value = 0;
        Set<Vertex> allVertex = graph.getVertexList();
        for (Vertex s: allVertex){
            if (!v.equals(s))
                value += bfs(graph, v, s);
        }
        return allVertex.size()/value;

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
//        ELGraph<String, String> graph = readGraph(graph1);
        ELGraph<String, String> graph = readGraph(graph2);
        Solution solution = new Solution(graph);

        int n = graph.getSize();

        double param = alpha * n;

        System.out.println("Param :" +param);
//        while (!checkAlpha(graph, param)){
////            Vertex deleted = getRandomVertex(graph);
//            Vertex deleted = getMaxGrade(graph);
//            System.out.println("Vertex: "+ deleted.getValue() +" deleted with "+ deleted.getEdges());
//            graph.removeVertex(deleted);
//            solution.insertVertexDeleted(deleted);
//
//        }
        Vertex a = getRandomVertex(graph);
        Vertex b = getRandomVertex(graph);
        System.out.println("a " + a.getValue() + " b " + b.getValue());
        System.out.println(bfs(graph,a,b));
//        System.out.println(solution.toString());

    }
}
