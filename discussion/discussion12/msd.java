
import java.util.ArrayList;
import java.util.List;

class msd{
    public static void main(String[] args) {
        // Test case 1: Normal case
        List<String> items1 = new ArrayList<>();
        items1.add("apple");
        items1.add("application");
        items1.add("apply");
        items1.add("ape");
        System.out.println("Test 1 - Normal case:");
        System.out.println("Original list: " + items1);
        System.out.println("Sorted result: " + msd(new ArrayList<>(items1)));
        
        // Test case 2: Single element
        List<String> items2 = new ArrayList<>();
        items2.add("hello");
        System.out.println("\nTest 2 - Single element:");
        System.out.println("Original list: " + items2);
        System.out.println("Sorted result: " + msd(new ArrayList<>(items2)));
        
        // Test case 3: Different first letters
        List<String> items3 = new ArrayList<>();
        items3.add("zebra");
        items3.add("apple");
        items3.add("mango");
        items3.add("banana");
        System.out.println("\nTest 3 - Different first letters:");
        System.out.println("Original list: " + items3);
        System.out.println("Sorted result: " + msd(new ArrayList<>(items3)));
        
        // Test case 4: Same prefix
        List<String> items4 = new ArrayList<>();
        items4.add("cat");
        items4.add("car");
        items4.add("card");
        items4.add("care");
        System.out.println("\nTest 4 - Same prefix:");
        System.out.println("Original list: " + items4);
        System.out.println("Sorted result: " + msd(new ArrayList<>(items4)));
    }
    
    public static List<String> msd(List<String> items){
        return msd(items,0);
    }

    private static List<String> msd(List<String> items,int index){
        if(items.size()==1) return items;
        
        // Check if index is out of bounds
        int minLen = Integer.MAX_VALUE;
        for(String item : items){
            minLen = Math.min(minLen, item.length());
        }
        if(index >= minLen) return items;

        int[] buckets=new int[26];
        for(String item : items){
            char c=item.charAt(index);
            int idx=c-'a';
            buckets[idx]++;
        }
        stableSort(items, index);

        int start=0;
        List<String> answer=new ArrayList<>();

        for(int i=0;i<26;i++){
            if(buckets[i]!=0){
                answer.addAll(msd(items.subList(start, start+buckets[i]),index+1));
                start+=buckets[i];
            }
        }

        return answer;
    }

    private static void stableSort(List<String> items, int index){
        List<String>[] buckets = new List[26];
        for(int i = 0; i < 26; i++){
            buckets[i] = new ArrayList<>();
        }
        
        for(String item : items){
            char c = item.charAt(index);
            int idx = c - 'a';
            buckets[idx].add(item);
        }
        
        int pos = 0;
        for(int i = 0; i < 26; i++){
            for(String item : buckets[i]){
                items.set(pos++, item);
            }
        }
    }
}