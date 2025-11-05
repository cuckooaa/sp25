import org.junit.jupiter.api.Test;
import wordnet.Graph;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestGraph {
    @Test
    //test the basic method of graph
    public void basic_test(){
        Graph graph=new Graph();
        graph.addEdge(2,1);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(1, 5);
        graph.addEdge(1, 6);

        assertThat(graph.getneighbors(2)).isEqualTo(List.of(1,3,4));
        assertThat(graph.getneighbors(1)).isEqualTo(List.of(5,6));
        assertThat(graph.getallneighbors(2)).isEqualTo(List.of(2, 1, 5, 6, 3, 4));
    }
}
