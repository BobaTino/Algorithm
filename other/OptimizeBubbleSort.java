// Time: O(n^2)
// Space: O(n)
// Optimize Time: O(n)

// OPTIMIZE-BUBBLESORT(A)
//   swapped = false
//   for j = A.length down to i + 1
//       if A[j] < A[j - 1]
//           exchange A[j] with A[j = 1]
//           swapped = true
//   if swapped == false
//       break
public class OptimizeBubbleSort {

    public static void bubbleSort(int[] A) {
        int n = A.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = n - 1; j > i; j--) {
                if (A[j] < A[j - 1]) {
                    int temp = A[j];
                    A[j] = A[j - 1];
                    A[j - 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        bubbleSort(arr);
        System.out.println("Sorted array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
