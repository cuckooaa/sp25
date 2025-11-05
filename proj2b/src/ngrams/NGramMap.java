package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private Map<String,TimeSeries> wordsData;
    private TimeSeries yearcounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordsData=new HashMap<>();
        In wordsIn=new In(wordsFilename);
        while(wordsIn.hasNextLine()){
            String line=wordsIn.readLine();
            String[] parts=line.split("\t");

            String word=parts[0].trim();
            int year=Integer.parseInt(parts[1].trim());
            double counts=Double.parseDouble(parts[2].trim());

            TimeSeries ts=wordsData.get(word);
            if(ts==null){
                ts=new TimeSeries();
                wordsData.put(word,ts);
            }
            ts.put(year,counts);
        }
        wordsIn.close();

        yearcounts=new TimeSeries();
        In totalcountsIn=new In(countsFilename);
        while(totalcountsIn.hasNextLine()){
            String line=totalcountsIn.readLine();
            String[] parts=line.split(",");

            int year=Integer.parseInt(parts[0].trim());
            double counts=Double.parseDouble(parts[1].trim());
            if (year < MIN_YEAR || year > MAX_YEAR) continue;
            yearcounts.put(year,counts);
        }
        totalcountsIn.close();
    }


    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result=new TimeSeries(wordsData.get(word),startYear,endYear);
        return result;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries result=new TimeSeries();
        result.putAll(wordsData.get(word));
        return result;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries result=new TimeSeries();
        result.putAll(yearcounts);
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries worddata=new TimeSeries();
        worddata.putAll(wordsData.get(word).subMap(startYear,true,endYear,true));
        return worddata.dividedBy(yearcounts);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries worddata=wordsData.get(word);
        return worddata.dividedBy(yearcounts);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result=new TimeSeries();
        Iterator<String> iterator=words.iterator();
        result.putAll(wordsData.get(iterator.next()));
        while(iterator.hasNext()){
            result=result.plus(wordsData.get(iterator.next()));
        }
        result=result.dividedBy(yearcounts);
        return new TimeSeries(result,startYear,endYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries result=new TimeSeries();
        Iterator<String> iterator=words.iterator();
        result.putAll(wordsData.get(iterator.next()));
        while(iterator.hasNext()){
            result=result.plus(wordsData.get(iterator.next()));
        }
        return result.dividedBy(yearcounts);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
