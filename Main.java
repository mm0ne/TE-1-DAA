import java.util.ArrayList;
import java.util.Map;

public class Main{

    public static void main(String[] args) {
        // long start = System.currentTimeMillis();
        // Dataset.generateDataset();
        // long end = System.currentTimeMillis();
        // System.out.println("Finished generating dataset in " + (end - start) + " ms.");

        System.out.println("++++++++++++++++++++++ Sorting with Counting Sort ++++++++++++++++++++++++++");
        Map<String, ArrayList<Integer>> repository1 = Dataset.loadData();
        Runtime.getRuntime().gc();

        for (Map.Entry<String, ArrayList<Integer>> entry: repository1.entrySet()){
            String key = entry.getKey();
            ArrayList<Integer> arr = entry.getValue();
            
            Runtime.getRuntime().gc();

            long start = System.nanoTime();
            long beforeMemory = getFreeMemory();

            CountingSort.sort(arr);

            long afterMemory = getFreeMemory();
            long end = System.nanoTime();

            System.out.println("[Finished] " + key + " " + ( (double)(end- start) / 1000000) + " ms using " + (beforeMemory - afterMemory) + " bytes.");

        }

        System.out.println("++++++++++++++++++++++ Sorting with BCIS ++++++++++++++++++++++++++");
        Map<String, ArrayList<Integer>> repository2 = Dataset.loadData();
        Runtime.getRuntime().gc();
        
        for (Map.Entry<String, ArrayList<Integer>> entry: repository2.entrySet()){
            String key = entry.getKey();
            ArrayList<Integer> arr = entry.getValue();
            Runtime.getRuntime().gc();

            long start = System.nanoTime();
            long beforeMemory = getFreeMemory();

            BICS.sort(arr);

            long afterMemory = getFreeMemory();
            long end = System.nanoTime();

            System.out.println("[Finished] " + key + " " + ( (double)(end- start) / 1000000) + " ms using " + (beforeMemory - afterMemory) + " bytes.");

        }

        
        for (Map.Entry<String, ArrayList<Integer>> entry: repository2.entrySet()){
            String key = entry.getKey();
            ArrayList<Integer> arr2 = entry.getValue();
            ArrayList<Integer> arr1 = repository1.get(key);
            for (int i = 0; i < arr1.size(); i++){
                if ((int)arr2.get(i) != (int)arr1.get(i)){
                    System.err.println("[Error] " + arr1.get(i) + "on arr1 doesn't equal to " + arr2.get(i) + " on arr2 at index " + i);
                    System.exit(1);
                }
            }
        }
        
        System.out.println("Finished and verified all sorting");
    };

    static long getFreeMemory(){
        return Runtime.getRuntime().freeMemory();
      }

};