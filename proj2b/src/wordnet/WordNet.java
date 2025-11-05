package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private HashMap<String, List<Integer>> wordToId;
    private HashMap<Integer, List<String>> idToSynset;
    private Graph graph=new Graph();

    public WordNet(String hyponymsFile,String synsetsFile){
        wordToId=new HashMap<>();
        idToSynset=new HashMap<>();
        In synsets=new In(synsetsFile);
        while(synsets.hasNextLine()){
            String line=synsets.readLine();
            String[] parts=line.split(",");
            int Id= Integer.parseInt(parts[0].trim());
            String[] synset=parts[1].split(" ");
            idToSynset.put(Id, Arrays.asList(synset));
            for(String word : synset){
                wordToId.putIfAbsent(word,new ArrayList<>());
                wordToId.get(word).add(Id);
            }
        }
        synsets.close();

        In hyponyms=new In(hyponymsFile);
        while (hyponyms.hasNextLine()){
            String line=hyponyms.readLine();
            String[] parts=line.split(",");
            Iterator iterator= Arrays.stream(parts).iterator();
            int node=Integer.parseInt((String) iterator.next());
            while(iterator.hasNext()){
                graph.addEdge(node, Integer.parseInt((String) iterator.next()));
            }
        }
        hyponyms.close();
    }

    public Set<String> getHyponyms(String word){
        Set<String> result=new TreeSet<>();
        List<Integer> ids=wordToId.get(word);
        List<Integer> hyponyms = new ArrayList<>();
        for(Integer node : ids){
            hyponyms.addAll(graph.getallneighbors(node));
        }
        for(Integer id : hyponyms){
            result.addAll(idToSynset.get(id));
        }
        return result;
    }
}
