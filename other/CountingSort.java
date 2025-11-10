// Time: O(n + k) Space: O(n + k)

import java.util.Vector;

interface Countable<T> {

    int getIndex();      // Get integer index for the object

    int getMaxIndex();   // Get the maximum possible index

    T getDefault();      // Get default (zero-like) value
}

public class CountingSort {

    public static <T extends Countable<T>> void counting_sort(Vector<T> input, Vector<T> output, T maxItem) {
        int k = maxItem.getMaxIndex();

        // Initialize count array
        int[] count = new int[k + 1];
        for (int i = 0; i <= k; i++) {
            count[i] = 0;
        }

        // Count occurrences
        for (int i = 0; i < input.size(); i++) {
            count[input.elementAt(i).getIndex()]++;
        }

        // Accumulate counts
        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
        }

        // Build output
        output.setSize(input.size());
        for (int i = input.size() - 1; i >= 0; i--) {
            T item = input.elementAt(i);
            count[item.getIndex()]--;
            output.setElementAt(item, count[item.getIndex()]);
        }
    }

    public static void main(String[] args) {
        class Letter implements Countable<Letter> {

            char value;

            public Letter(char value) {
                this.value = value;
            }

            public Letter() {
                this.value = 'A';
            }

            public int getIndex() {
                return value - 'A';
            }

            public int getMaxIndex() {
                return 25;
            }  // A-Z

            public Letter getDefault() {
                return new Letter('A');
            }

            public String toString() {
                return Character.toString(value);
            }
        }

        Vector<Letter> letters = new Vector<>();
        letters.add(new Letter('D'));
        letters.add(new Letter('B'));
        letters.add(new Letter('A'));
        letters.add(new Letter('C'));
        letters.add(new Letter('E'));
        letters.add(new Letter('F'));
        letters.add(new Letter('P'));

        Vector<Letter> sortedLetters = new Vector<>();
        counting_sort(letters, sortedLetters, new Letter('Z'));

        System.out.println("Original: " + letters);
        System.out.println("Sorted: " + sortedLetters);
    }
}
