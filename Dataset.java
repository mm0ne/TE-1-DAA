import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.*;
import java.util.*;

public class Dataset {
    public static List<Integer> generateRandom(int n){
        List<Integer> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++){
            list.add(rand.nextInt(4096));
        }
        return list;
    }
    public static List<Integer> generateSorted(int n){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++){
            list.add(i);
        }
        return list;
    }
    public static List<Integer> generateReverseSorted(int n){
        List<Integer> list = new ArrayList<>();
        for (int i = n; i > 0; i--){
            list.add(i);
        }
        return list;
    }

    // save data into file
    public static void generateDataset(){
        ExecutorService executor = Executors.newFixedThreadPool(4);
        int[] nValues = {500, 5000, 50000};
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int n : nValues) {
            tasks.add(() -> {
                List<Integer> random = generateRandom(n);
                List<Integer> sorted = generateSorted(n);
                List<Integer> reverseSorted = generateReverseSorted(n);

                serializeArrayList((ArrayList<Integer>) random, "./dataset/random" + n + ".ser");
                serializeArrayList((ArrayList<Integer>) sorted, "./dataset/sorted" + n + ".ser");
                serializeArrayList((ArrayList<Integer>) reverseSorted, "./dataset/reversed" + n + ".ser");

                return null;
            });
        }

        try {
            // Submit all tasks and wait for their completion
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
    // load data from file
    public static Map<String, ArrayList<Integer>> loadData(){
        String[] fileNames = {"random500.ser", "sorted500.ser", "reversed500.ser",
                              "random5000.ser", "sorted5000.ser", "reversed5000.ser",
                              "random50000.ser", "sorted50000.ser", "reversed50000.ser"};

        Map<String, ArrayList<Integer>> map = new TreeMap<>();
        for (String x: fileNames){
            map.put(x, deserializeArrayList("./dataset/" + x ));
        }
        return map;
    }

    // Method to serialize the ArrayList and save it to a file
    private static void serializeArrayList(ArrayList<Integer> list, String filePath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(list);
            System.out.println("ArrayList saved to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to deserialize the ArrayList from the file
    private static ArrayList<Integer> deserializeArrayList(String filePath) {
        ArrayList<Integer> deserializedList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            deserializedList = (ArrayList<Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializedList;
    }
}