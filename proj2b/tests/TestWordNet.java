import org.junit.jupiter.api.Test;
import wordnet.Graph;
import wordnet.WordNet;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestWordNet {
    // wordnet Files
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";

    @Test
    //test the basic method of graph
    public void basic_test(){
        WordNet wordnet=new WordNet(SMALL_HYPONYM_FILE,SMALL_SYNSET_FILE);

        assertThat(wordnet.getHyponyms("change")).isEqualTo("[alteration, change, demotion, increase, jump, leap, modification, saltation, transition, variation]");
    }
}
