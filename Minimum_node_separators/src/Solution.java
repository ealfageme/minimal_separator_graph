import java.util.ArrayList;


public class Solution {
    private ELGraph<String, String> graph;
    private ArrayList<Vertex<String>> vertexDeleted;

    public Solution(ELGraph<String, String> graph) {
        this.graph = graph;
        this.vertexDeleted = new ArrayList<>();
    }
    public void insertVertexDeleted(Vertex<String> v){
        this.vertexDeleted.add(v);
    }

    @Override
    public String toString() {
        return "Solution:\n"
                + graph + "\n"
                + "vertexDeleted:"+vertexDeleted.size()+"\n " + vertexDeleted;
    }
}


