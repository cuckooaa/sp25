package wordnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    private HashMap<Integer, ArrayList<Integer>> graph;

    public Graph(){
        graph=new HashMap<>();
    }

    public void addEdge(Integer n,Integer neighbor){
        graph.putIfAbsent(n,new ArrayList<>());
        graph.putIfAbsent(neighbor,new ArrayList<>());
        graph.get(n).add(neighbor);
    }

    public ArrayList<Integer> getneighbors(Integer n){
        ArrayList<Integer> neighbors = graph.getOrDefault(n, new ArrayList<>());
        return neighbors;
    }

    public ArrayList<Integer> getallneighbors(Integer n){
        ArrayList<Integer> result=new ArrayList<>();
        helper(n,result);
        return result;
    }

    private void helper(Integer n,ArrayList<Integer> result){
        if(getneighbors(n)==null){
            return;
        }
        result.add(n);
        for(Integer n1 : getneighbors(n)){
            helper(n1,result);
        }
    }
}
