
public interface Graph <V,E> {

    Iterable <? extends Vertex <V> > vertices();

    Iterable <? extends Edge <E> > edges();

    Iterable <? extends Edge <E> > incidentEdges(Vertex <V> v);

    Vertex <V> opposite(Vertex <V> v, Edge <E> e);

    Iterable <Vertex <V> > endVertices(Edge <E> edge);

    boolean areAdjacent(Vertex <V> v1, Vertex <V> v2);

    V replace(Vertex <V> vertex, V vertexValue);

    E replace(Edge <E> edge, E edgeValue);

    Vertex <V> insertVertex(V value);

    Edge <E> insertEdge(Vertex <V> v1, Vertex <V> v2, E edgeValue);

    V removeVertex(Vertex <V> vertex);

    E removeEdge(Edge <E> edge);    
}
