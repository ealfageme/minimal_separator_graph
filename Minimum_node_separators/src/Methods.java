
import java.io.*;

import java.util.*;


public class Methods {
    // To calculate the number of nodes connected
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
    // Bfs, return the path more sorter between two nodes
    private static List<Vertex> bfs(ELGraph graph, Vertex start, Vertex end) {
        LinkedList queue = new LinkedList();
        List<Vertex> list = new ArrayList<>();
        if (start.equals(end)) return list;
        Set nodeExplored = new HashSet();
        list.add(start);
        queue.add(list);
        nodeExplored.add(start);
        while (!queue.isEmpty()) {
            List<Vertex> listAux = (List) queue.poll();
            Vertex searched = listAux.get(listAux.size() - 1);
            HashSet<Edge> incidentEdges = (HashSet<Edge>) graph.incidentEdges(searched);
            for (Edge edge : incidentEdges) {
                Vertex opposite = graph.opposite(searched, edge);
                if (!nodeExplored.contains(opposite)) {
                    if (opposite.equals(end)) {
                        return listAux;
                    } else {
                        nodeExplored.add(opposite);
                        ArrayList<Vertex> listToQueue = new ArrayList<>(listAux);
                        listToQueue.add(opposite);
                        queue.add(listToQueue);
                    }
                }
            }
        }
        list.clear();
        return list;
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
        System.out.println(        "Vertex:"+"\t| "+head[0]
                                +"\nEdges: "+"\t| "+head[1]
                                +"\nAlpha: "+"\t| "+head[2]);
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

    private static Vertex getRandomVertex(Set<Vertex> vertex){
        ArrayList<Vertex> vertexList = new ArrayList<>(vertex);
        int random = (int) (Math.random()*(vertexList.size()-1)-0);
        return vertexList.get(random);

    }

    private static double closeness(ELGraph graph, Vertex v){
        double value = 0;
        int bfsValue;
        Set<Vertex> allVertex = graph.getVertexList();
        for (Vertex s: allVertex){
            if (!v.equals(s)) {
                bfsValue = bfs(graph, v, s).size();
                if (bfsValue != 0) value += bfsValue;
                else value += 1000;
            }
        }
        return allVertex.size()/value;
    }

    private static int betweeness(ELGraph graph, Vertex vertex){
        int value = 0;
        List<Vertex> bfsList;
        Set<Vertex> allVertex = graph.getVertexList();
        for (Vertex v: allVertex){
            for (Vertex w :allVertex){
                if (!w.equals(vertex) && !v.equals(vertex) && !v.equals(w)){
                    bfsList = bfs(graph, v,w);
                    if (bfsList.contains(vertex)) {
                        value++;
                        // Value should be divided by the number of path between the vertex
                        // v and Vertex w. Now, value is divided by 1 though there are more
                        // path between v and w.
                    }
                }
            }
        }
        return value/2;
    }

    private static Map<Vertex,Float> pageRank(ELGraph graph) {
        int size = graph.getSize();
        Map<Vertex, Float> maps = new HashMap<>();
        Set<Vertex> allVertex = graph.getVertexList();
        Float[] values = new Float[size];
        Float[] newValues = new Float[size];
        Vertex[] vertices = allVertex.toArray(new Vertex[size]);
        for (int i = 0; i < values.length; i++) {
            values[i] = (float) 1 / size;
        }
        for(int i = 0 ; i <2; i++) {
            for (int j= 0; j<newValues.length;j++){
                newValues[j] = 0f;
            }
            for (int j = 0; j < vertices.length; j++) {
                Vertex v = vertices[j];
                HashSet<Edge> incidentEdges = (HashSet<Edge>) graph.incidentEdges(v);
                for (Edge edge : incidentEdges) {
                    Vertex oposite = graph.opposite(v, edge);
                    int edgesOp = ((HashSet<Edge>) graph.incidentEdges(oposite)).size();
                    newValues[j] += values[getIndex(vertices,oposite)] / edgesOp;
                }
            }
            System.arraycopy(newValues, 0, values, 0, newValues.length);
        }
        for(int i = 0 ; i<vertices.length;i++){
            maps.put(vertices[i],values[i]);
        }
        return maps;

    }
    // By array of Vertex, return the index of array that the vertex is located
    private static int getIndex(Vertex[] vs, Vertex op){
        for (int i = 0; i < vs.length;i ++){
            if (vs[i].equals(op))
            return i;
        }
        return -1;
    }

    private static Vertex getMore(ELGraph graph, String type){
        Vertex maxVertex = getRandomVertex(graph);
        Set<Vertex> allVertex = graph.getVertexList();
        switch (type){
            case "closeness":
                for (Vertex v: allVertex){
                    System.out.println("vertex " + v.toString() +
                            " | Closeness: " + closeness(graph,v));
                    if (closeness(graph,v) > closeness(graph,maxVertex)){
                        maxVertex = v;
                    }
                }
                break;
            case "betweeness":
                for (Vertex v: allVertex){
                    System.out.println("vertex " + v.toString() +
                            " | Betweeness: " + betweeness(graph,v));
                    if (betweeness(graph,v) > betweeness(graph,maxVertex)){
                        maxVertex = v;
                    }
                }
                break;
            case "pageRank":
                Map<Vertex,Float> pageRankMaps = pageRank(graph);
                maxVertex=pageRankMaps
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .get()
                        .getKey();
                break;
            case "grade":
                int numEdges = 0;
                for (Vertex s : allVertex) {
                    if(s.getEdges() > numEdges){
                        maxVertex = s;
                        numEdges = s.getEdges();
                    }
                }
        }
        return maxVertex;
    }
    // By graph, type and range, return a set with vertex that
    private static Set<Vertex> getListCandidates(ELGraph graph, String type, double range){
        Set<Vertex> list = new HashSet<>();
        Set<Vertex> allVertex = graph.getVertexList();
        if (type.equals("closeness")) {
            for (Vertex v : allVertex)
                if (range < closeness(graph, v)) list.add(v);
        }
        return list;
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

    private static double getNumber(ELGraph graph, String option, String value) {
        Vertex random = getRandomVertex(graph);
        double vertex = 0.0f;
        Set<Vertex> allVertex = graph.getVertexList();
        switch (option) {
            case "closeness":
                vertex = closeness(graph,random);
                for (Vertex v : allVertex) {
                    if (value.equals("min")) {
                        if (closeness(graph, v) < vertex) vertex = closeness(graph,v);
                    } else if (value.equals("max")) {
                        if (closeness(graph, v) > vertex) vertex = closeness(graph,v);
                    }
                }
        }
//        System.out.println(option + " | " + value + " | " + vertex);
        return vertex;
    }

    private static Solution improve(Solution solution) {
        ELGraph graph = solution.getGraph();
        ArrayList<Vertex> deletedVertex = solution.getVertexDeleted();
        Set<Vertex> setVertex = graph.getVertexList();
        ArrayList<Vertex> allVertex = new ArrayList<>();
        allVertex.addAll(setVertex);
        System.out.println("allVertex " + allVertex);
        System.out.println("deletedVertex " + deletedVertex);
        System.out.println("***********************");
        boolean improve = true;
        while (improve) {
            outerloop:
            for (int i = 0; i < deletedVertex.size(); i++) {
                for (int j = i; j < deletedVertex.size();j++) {
                    Vertex dv = deletedVertex.get(i);
                    Vertex dw = deletedVertex.get(j);
                    if (!dv.equals(dw)){
                        for (Vertex anAllVertex : allVertex) {
                            if (twoForOne(solution, dv, dw, anAllVertex)) {
                                System.out.println("Change " + dv.toString() + ", "
                                        + dw.toString() + " x " + anAllVertex.toString());
                                break outerloop;
                            }
                        }
                    }
                    if ((i == deletedVertex.size()-1)&&(j == i))
                        improve = false;
                }
            }
        }
    return solution;
    }

    private static boolean twoForOne(Solution solution, Vertex dv, Vertex dw, Vertex v) {
        System.out.println("Changing "+dv.getValue()+" "+dw.getValue() +" for "+ v.getValue());
        ELGraph graph = solution.getGraph();
        ELGraph originalGraph = solution.getOriginalGraph();
        Vertex aa = graph.insertVertex(dv.getValue());
//        System.out.println("add vertex " + aa.getValue());
        Vertex bb = graph.insertVertex(dw.getValue());
//        System.out.println("add vertex " + bb.getValue());
        Vertex a = originalGraph.constains(dv.getValue());
        Vertex b = originalGraph.constains(dw.getValue());
        if (a != null && b!= null){
            HashSet <Edge> incidentEdgesA = (HashSet<Edge>) originalGraph.incidentEdges(a);
            HashSet <Edge> incidentEdgesB = (HashSet<Edge>) originalGraph.incidentEdges(b);
            for (Edge edge : incidentEdgesA) {
                if ((graph.constains(edge.getEndVertex().getValue())!=null)&&
                        (graph.constains(edge.getStartVertex().getValue())!=null)) {
                    graph.addEdgeByValue(edge.getEndVertex().getValue(), edge.getStartVertex().getValue(), null);
//                    System.out.println("add edge "+
//                            edge.getEndVertex().getValue()+" "+
//                            edge.getStartVertex().getValue());
                }
            }
            for (Edge edge : incidentEdgesB){
                if ((graph.constains(edge.getEndVertex().getValue())!=null)&&
                        (graph.constains(edge.getStartVertex().getValue())!=null)){
                    graph.addEdgeByValue(edge.getEndVertex().getValue(), edge.getStartVertex().getValue(),null);
//                    System.out.println("add edge "+
//                            edge.getEndVertex().getValue()+" "+
//                            edge.getStartVertex().getValue());
                }
            }
        } else{
            System.out.println("I dont find one or more vertex: "
                    + dv.getValue()+" and/or"+dw.getValue());
        }
        Vertex find = graph.constains(v.getValue());
        graph.removeVertex(find);
//        System.out.println("remove vertex " + find);
//        System.out.println("New graph");
//        System.out.println(graph);

        if (checkAlpha(graph, solution.getParam())){
            System.out.println(" CHECK CONDITION");
//            System.out.println(graph);
//            System.out.println(" --------------+");
            solution.insertVertexDeleted(find);
            solution.deleteVertexDeleted(aa);
            solution.deleteVertexDeleted(bb);
            System.out.println("Solution");
            System.out.println(solution);
            System.out.println(" ");

            return true;
        }else{
            System.out.println("dont check the condition. restored");
            Vertex restored = graph.insertVertex(find.getValue());
//            System.out.println("Add " +restored.getValue());
            Vertex restoredAux = originalGraph.constains(restored.getValue());
            HashSet <Edge> incidentEdges = (HashSet<Edge>) originalGraph.incidentEdges(restoredAux);
            for (Edge edge:incidentEdges){
                if ((graph.constains(edge.getEndVertex().getValue())!=null)&&
                        (graph.constains(edge.getStartVertex().getValue())!=null)){
                    graph.addEdgeByValue(edge.getEndVertex().getValue(), edge.getStartVertex().getValue(),null);
//                    System.out.println("Add " +
//                            edge.getStartVertex().getValue() +" "+
//                            edge.getEndVertex().getValue()
//                    );
                }
            }
//            System.out.println("Vertex: "+ aa.getValue() +" deleted with " + aa.getEdges());
            graph.removeVertex(aa);
//
//            System.out.println("Vertex: "+ bb.getValue() +" deleted with " + bb.getEdges());
            graph.removeVertex(bb);
//            System.out.println("***********************************");
//            System.out.println(graph);
            System.out.println("***********************************");
            return false;

        }

    }

    public static void main(String[] args) throws IOException {
        double alpha = 0.3;
////        String graph1 = "erdos_renyi_small/erdos_renyi_100_0.05_0.2_0.txt";
//          String graph1 = "erdos_renyi_small/grafo.txt";
        String graph1 = "erdos_renyi_small/0-graph1";
        ELGraph<String, String> originalGraph = readGraph(graph1);
        ELGraph<String, String> graph = readGraph(graph1);
        System.out.println("_____________________"+"\n");
        System.out.println("deleting vertex");
        int n = graph.getSize();
        double param = alpha * n;
        Solution solution = new Solution(originalGraph, graph, param);
        String option = "closeness";
        double gMin, gMax;
        double mu;
        float muParam = 0.75f;

        Vertex first  =getRandomVertex(graph);
        System.out.println("Vertex: " + first.getValue() + " deleted random with " + first.getEdges() + " edges");
        graph.removeVertex(first);
        solution.insertVertexDeleted(first);

        while (!checkAlpha(graph,param)){
            gMin = getNumber(graph, option, "min");
            gMax = getNumber(graph, option, "max");
            mu = gMax - muParam * (gMax - gMin);
            Set<Vertex> candidates = getListCandidates(graph,option, mu);
            Vertex deleted = getRandomVertex(candidates);
            System.out.println("Vertex: "+ deleted.getValue() +" deleted with "+ deleted.getEdges() + " edges");
            graph.removeVertex(deleted);
            solution.insertVertexDeleted(deleted);
        }
        System.out.println("_____________________"+"\n");
        solution = improve(solution);
        System.out.println("_____________________FINAL____________Ç"+"\n");
        System.out.println(solution.toString());



    }
}
