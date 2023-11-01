// Counting sort in Java programming

import java.util.ArrayList;
import java.util.Arrays;

public class CountingSort {
  public static void sort(ArrayList<Integer> input) {
    int size = input.size();
    int[] output = new int[size + 1];

    // Find the largest element of the array
    int max = input.get(0);
    for (Integer i: input) {
      if (i > max)
        max = i;
    }
    int[] count = new int[max + 1];
    Arrays.fill(count, 0);

    // Store the count of each element
    for (int i = 0; i < size; i++) {
      count[input.get(i)]++;
    }

    // Store the cummulative count of each array
    for (int i = 1; i <= max; i++) {
      count[i] += count[i - 1];
    }

    // Find the index of each element of the original array in count array, and
    // place the elements in output array
    for (int i = size - 1; i >= 0; i--) {
      output[count[input.get(i)] - 1] = input.get(i);
      count[input.get(i)]--;
    }

    // Copy the sorted elements into original array
    for (int i = 0; i < size; i++) {
      input.set(i, output[i]);
    }

  }

}