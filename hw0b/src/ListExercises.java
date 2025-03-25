import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int sum=0;
        int len=L.size();
        for(int i=0;i<len;i++){
            sum+=L.get(i);
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> result=new ArrayList<>();
        for(int i=0;i<L.size();i++){
            if(L.get(i)%2==0){
                result.add(L.get(i));
            }
        }
        return result;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> result=new ArrayList<>();
        for(int p1=0;p1<L1.size();p1++){
            for(int p2=0;p2<L2.size();p2++){
                if(L1.get(p1).equals(L2.get(p2))){
                    result.add(L1.get(p1));
                    break;
                }
            }
        }
        return result;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int occurrences=0;
        for(int i=0;i< words.size();i++){
            for(int j=0;j< words.get(i).length();j++) {
                if (words.get(i).charAt(j) == c) {
                    occurrences++;
                }
            }
        }
        return occurrences;
    }
}
