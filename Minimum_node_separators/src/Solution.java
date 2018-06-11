import java.util.ArrayList;


public class Solution {
    private ELGraph<String, String> originalGraph;
    private ELGraph<String, String> graph;
    private ArrayList<Vertex<String>> vertexDeleted;

    public Solution(ELGraph<String, String> original,ELGraph<String, String> graph) {
        this.originalGraph = original;
        this.graph = graph;
        this.vertexDeleted = new ArrayList<>();
    }
    public void insertVertexDeleted(Vertex<String> v){
        this.vertexDeleted.add(v);
    }

    @Override
    public String toString() {
        return "Original Graph:\n"
                + originalGraph + "\n"
                + "Solution:\n"
                + graph + "\n"
                + "vertexDeleted:"+vertexDeleted.size()+"\n " + vertexDeleted;
    }
}


