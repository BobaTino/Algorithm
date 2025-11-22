// Time: O(n)
// Space: O(n2^n) Optimize: O(n)
// BOTTOM_UP_PARALLEL_MERGE_SORT(A):
//     Input: Array A[1..n] of database records or numeric values
//     Output: Sorted array A in ascending order

//     let n = length(A)
//     let Temp[1..n]  // single shared temporary array for merging
//     for sz = 1 to n step sz * 2:
//         // Process each pair of subarrays of size sz
//         parallel for left = 1 to n step 2*sz:
//             mid = min(left + sz - 1, n)
//             right = min(left + 2*sz - 1, n)
//             if mid < right:
//                 MERGE(A, Temp, left, mid, right)
//     return A
// MERGE(A, Temp, left, mid, right):
//     i = left
//     j = mid + 1
//     k = left
//     while i <= mid AND j <= right:
//         if A[i] <= A[j]:
//             Temp[k] = A[i]
//             i = i + 1
//         else:
//             Temp[k] = A[j]
//             j = j + 1
//         k = k + 1
//     while i <= mid:
//         Temp[k] = A[i]
//         i = i + 1
//         k = k + 1
//     while j <= right:
//         Temp[k] = A[j]
//         j = j + 1
//         k = k + 1
//     for p = left to right
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class BottomUpParallelMergeSort {

    static class MergeTask extends RecursiveAction {

        private double[] arr, temp;
        private int left, mid, right;

        MergeTask(double[] arr, double[] temp, int left, int mid, int right) {
            this.arr = arr;
            this.temp = temp;
            this.left = left;
            this.mid = mid;
            this.right = right;
        }

        @Override
        protected void compute() {
            int i = left, j = mid, k = left;
            while (i < mid && j < right) {
                if (arr[i] <= arr[j]) {
                    temp[k++] = arr[i++];
                } else {
                    temp[k++] = arr[j++];
                }
            }
            while (i < mid) {
                temp[k++] = arr[i++];
            }
            while (j < right) {
                temp[k++] = arr[j++];
            }
            System.arraycopy(temp, left, arr, left, right - left);
        }
    }

    public static void parallelBottomUpMergeSort(double[] arr) {
        int n = arr.length;
        double[] temp = new double[n];
        ForkJoinPool pool = new ForkJoinPool(); // Use all available cores

        for (int sz = 1; sz < n; sz *= 2) {
            int finalSz = sz;
            for (int left = 0; left < n; left += 2 * finalSz) {
                int mid = Math.min(left + finalSz, n);
                int right = Math.min(left + 2 * finalSz, n);
                if (mid < right) {
                    pool.invoke(new MergeTask(arr, temp, left, mid, right));
                }
            }
        }
    }

    public static void main(String[] args) {
        double[] transactionAmounts = {102.5, 500.0, 23.1, 750.2, 310.0, 12.0, 999.99};
        System.out.println("Before Sorting: " + Arrays.toString(transactionAmounts));

        parallelBottomUpMergeSort(transactionAmounts);

        System.out.println("After Sorting: " + Arrays.toString(transactionAmounts));
        // Real-time analytics example: Top transactions
        System.out.println("Top 3 transactions: "
                + Arrays.toString(Arrays.copyOfRange(transactionAmounts, transactionAmounts.length - 3, transactionAmounts.length)));
    }
}
