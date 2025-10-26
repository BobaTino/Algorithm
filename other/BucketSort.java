
import java.util.*;

public class BucketSort {

    public static void main(String[] args) {
        String input = "TommorowIsAMysteryTodayIsAGifts!";

        //Convert string to char array
        char[] chars = input.toCharArray();

        int bucketCount = 128;
        List<List<Character>> buckets = new ArrayList<>(bucketCount);

        // Initialize buckets
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        // Place chars into buckets
        for (char c : chars) {
            buckets.get((int) c).add(c);
        }
        // Sort each bucket
        StringBuilder sorted = new StringBuilder();
        for (List<Character> bucket : buckets) {
            for (char c : bucket) {
                sorted.append(c);
            }
        }
        System.out.println("Original String: " + input);
        System.out.println("Sorted String: " + sorted.toString());
    }
}
