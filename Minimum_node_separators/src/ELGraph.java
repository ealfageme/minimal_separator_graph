import java.util.*;

public class ELGraph<V,E> implements Graph <V,E> {
	
	private class ELVertex <T> implements Vertex <T> {
	    private T vertexValue;
	    private final Graph<T,E> graph;
	    
	    @Override
	    public T getValue() {
	        return vertexValue;
	    }
	    
	    public void setValue(T value) {
	        vertexValue = value;
	    }
	    
	    public ELVertex(T value, Graph<T,E> graph) {
	        vertexValue = value;
	        this.graph = graph;
	    }

        public Graph<T,E> getGraph() {
	        return graph;
	    }
	    @Override
        public String toString(){
	        return String.valueOf(this.getValue());
        }
	}
	
	private class ELEdge <T> implements Edge <T> {
	    private T edgeValue;
	    private final Graph<V, T> graph;
	    
	    private final Vertex <V> startVertex;
	    private final Vertex <V> endVertex;

	    @Override
	    public int hashCode() {
	        int hash = 3;
	        
	        final int min = Math.min(Objects.hashCode(this.startVertex), Objects.hashCode(this.endVertex));
	        final int max = Math.max(Objects.hashCode(this.startVertex), Objects.hashCode(this.endVertex));
	        
	        hash = 67 * hash + Objects.hashCode(this.getGraph());
	        hash = 67 * hash + min;
	        hash = 67 * hash + max;
	        return hash;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final ELEdge<T> other = (ELEdge<T>) obj;
	        if (!Objects.equals(this.graph, other.graph)) {
	            return false;
	        }
	        
	        final int min1 = Math.min(Objects.hashCode(this.startVertex), Objects.hashCode(this.endVertex));
	        final int max1 = Math.max(Objects.hashCode(this.startVertex), Objects.hashCode(this.endVertex));

	        final int min2 = Math.min(Objects.hashCode(other.startVertex), Objects.hashCode(other.endVertex));
	        final int max2 = Math.max(Objects.hashCode(other.startVertex), Objects.hashCode(other.endVertex));

	        if (!Objects.equals(min1, min2)) {
	            return false;
	        }
	        if (!Objects.equals(max1, max2)) {
	            return false;
	        }
	        return true;
	    }
	    
	    @Override
	    public T getValue() {
	        return edgeValue;
	    }

	    public ELEdge(T value,Vertex<V> startVertex, Vertex<V> endVertex, Graph<V,T> graph) {
	        edgeValue = value;
	        this.startVertex = startVertex;
	        this.endVertex = endVertex;
	        this.graph = graph;
	    }
	    @Override
        public String toString(){
	        return "[" + String.valueOf(this.startVertex) +", " + String.valueOf(this.endVertex)+"]";
        }

	    public void setValue(T value) {
	        edgeValue = value;
	    }

	    public Vertex <V> getStartVertex() {
	        return startVertex;
	    }

	    public Vertex <V> getEndVertex() {
	        return endVertex;
	    }

	    public Graph<V,T> getGraph() {
	        return graph;
	    }
	}

    private final Set <ELVertex<V>> vertexList = new HashSet<>();
    private final Set <ELEdge<E>> edgeList = new HashSet<>();
    private int size = 0;
    
    public Iterable <? extends Vertex<V> > vertices() {
        return Collections.unmodifiableCollection(vertexList);
    }

    public Iterable <? extends Edge<E> > edges() {
        return Collections.unmodifiableCollection(edgeList);
    }

    public Iterable<? extends Edge<E>> incidentEdges(Vertex <V> v) {
        HashSet <Edge <E> > incidentEdges = new HashSet<>();
        for (ELEdge <E> e : edgeList){
            if (e.getStartVertex().equals(v))
                incidentEdges.add(e);
            if (e.getEndVertex().equals(v))
                incidentEdges.add(e);            
        }
        return incidentEdges;
    }
    
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        ELEdge<E> elv = checkEdge(e);
        if (elv.getStartVertex().equals(v))
            return elv.getEndVertex();
        else if (elv.getEndVertex().equals(v))
            return elv.getStartVertex();
        else
            throw new RuntimeException("The vertex is not in the edge");
    }

    public Iterable<Vertex<V>> endVertices(Edge<E> edge) {
        ELEdge<E> elv = checkEdge(edge);
        ArrayList <Vertex<V> > output = new ArrayList<>();
        output.add(elv.getStartVertex());
        output.add(elv.getEndVertex());
        return output;
    }

    public boolean areAdjacent(Vertex<V> v1, Vertex<V> v2) {
        for (ELEdge<E> edge : edgeList)
        {
            if ((edge.getStartVertex().equals(v1)) && (edge.getEndVertex().equals(v2)))
                return true;
            else if ((edge.getStartVertex().equals(v2)) && (edge.getEndVertex().equals(v1)))
                return true;
        }
        return false;
    }

    public V replace(Vertex<V> vertex, V vertexValue) {
        ELVertex<V> v = checkVertex(vertex);
        V aux = vertex.getValue();
        v.setValue(vertexValue);
        return aux;
    }

    public E replace(Edge<E> edge, E edgeValue) {
        ELEdge<E> e = checkEdge(edge);
        E aux = edge.getValue();
        e.setValue(edgeValue);
        return aux;
    }

    public Vertex <V> insertVertex(V value) {
        ELVertex<V> v = new ELVertex<>(value,this);
        vertexList.add(v);
        this.size++;
        return v;
    }

    public Edge <E> insertEdge(Vertex<V> v1, Vertex<V> v2, E edgeValue) {
        if (!vertexList.contains(v1))
            throw new RuntimeException("The vertex v1 doesn't belong to this graph");
        if (!vertexList.contains(v2))
            throw new RuntimeException("The vertex v2 doesn't belong to this graph");

        ELEdge<E> e = new ELEdge<>(edgeValue,v1,v2,this);

        if (edgeList.contains(e))
            edgeList.remove(e);
        edgeList.add(e);
        return e;
    }

    public int getSize(){
       return this.size;
    }
    public V removeVertex(Vertex<V> vertex) {
        ELVertex<V> v = checkVertex(vertex);
        V aux = vertex.getValue();
        vertexList.remove(v);

        //You need an aux set, because you can't remove from a set while you iterate it
        List <ELEdge<E>> removeEdgeList = new ArrayList<>();
        for (ELEdge<E> edge : edgeList) {
            if ((edge.getStartVertex().equals(vertex)) || (edge.getEndVertex().equals(vertex))) {
                removeEdgeList.add(edge);
            }
        }
        
        for (ELEdge<E> edge : removeEdgeList) {
            edgeList.remove(edge);
        }
        this.size--;
        return aux;        
    }

    public E removeEdge(Edge<E> edge) {
        ELEdge<E> e = checkEdge(edge);
        E aux = edge.getValue();
        edgeList.remove(e);
        return aux;
    }    
    
    private ELEdge<E> checkEdge(Edge<E> edge) {
        if (edge instanceof ELEdge){
            ELEdge<E> e = (ELEdge<E>)edge;
            if (e.getGraph() == this)
                return e;
        }
        throw new RuntimeException("The edge is not in the graph");
    }

    private ELVertex<V> checkVertex(Vertex<V> vertex) {
        if (vertex instanceof ELVertex){
            ELVertex<V> v = (ELVertex<V>)vertex;
            if (v.getGraph() == this)
                return v;
        }
        throw new RuntimeException("The vertex is not in the graph");        
    }

    @Override
    public String toString() {
        String vertex = "Vertex"+"\n" + this.vertices() + "\n";
        String edges = "Edges:" +"\n" + this.edges() + "\n";
        String toString = vertex + edges;
        return toString;
    }
}
