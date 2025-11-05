package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import wordnet.WordNet;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet wordnet;
    NGramMap ngrammap;
    int startYear;
    int endYear;

    public HyponymsHandler(WordNet wordnet,NGramMap ngrammap){
        this.wordnet=wordnet;
        this.ngrammap=ngrammap;
    }


    @Override
    public String handle(NgordnetQuery q) {
        List<String> words=q.words();
        startYear = q.startYear();
        endYear = q.endYear();
        int k=q.k();

        Iterator<String> iterator=words.iterator();

        if(k==0){
            Set<String> result=new TreeSet<>();
            if(iterator.hasNext()){result=wordnet.getHyponyms(iterator.next());}
            while(iterator.hasNext()) {
                result.retainAll(wordnet.getHyponyms(iterator.next()));
            }
            return result.toString();
        }

        Set<String> counts_order_words=new TreeSet<>(new countsComparator());
        if(iterator.hasNext()){counts_order_words=wordnet.getHyponyms(iterator.next());}
        while(iterator.hasNext()) {
            counts_order_words.retainAll(wordnet.getHyponyms(iterator.next()));
        }

        Set<String> result=new TreeSet<>();
        for(String word : counts_order_words){
            if(total_counts(word)!=0 && --k>=0){
                result.add(word);
            }
        }

        return result.toString();
    }


    private Double total_counts(String word){
        TimeSeries timeseries=ngrammap.countHistory(word,startYear,endYear);
        if(timeseries==null){
            return (double) 0;
        }
        Double result= (double) 0;
        for(Double count : timeseries.values()){
            result+=count;
        }
        return result;
    }

    class countsComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return (int) (total_counts(o1)-total_counts(o2));
        }
    }
}
