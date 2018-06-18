import java.util.ArrayList;


public class Solution {
    private ELGraph<String, String> originalGraph;
    private ELGraph<String, String> graph;
    private ArrayList<Vertex> vertexDeleted;
    private double param;

    public Solution(ELGraph<String, String> original,ELGraph<String, String> graph, double param) {
        this.originalGraph = original;
        this.graph = graph;
        this.vertexDeleted = new ArrayList<>();
        this.param = param;
    }
    public void insertVertexDeleted(Vertex v){
        this.vertexDeleted.add(v);
    }

    @Override
    public String toString() {
        return "-------Original Graph-------\n"
                + originalGraph + "\n"
                + "-------Solution-------\n"
                + graph + "\n"
                + "vertexDeleted: "+vertexDeleted.size()+"\n " + vertexDeleted;
    }

    public ArrayList<Vertex> getVertexDeleted() {
        return vertexDeleted;
    }

    public ELGraph<String, String> getGraph() {
        return graph;
    }

    public ELGraph<String, String> getOriginalGraph() {
        return originalGraph;
    }

    public double getParam() {
        return param;
    }
    public void deleteVertexDeleted(Vertex vertex){
        Vertex aux = null;
        for (Vertex v: vertexDeleted){
            if (v.getValue().equals(vertex.getValue()))
                aux = v;
        }
        vertexDeleted.remove(aux);
    }
}


