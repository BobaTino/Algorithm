// RANDOM-SELECT(A, p, r, i)
//     if p == r
//         return A[p]
//     q = RANDOMIZED-PARTITION(A, p, r)
//     k = q - p + 1
//     if i == k
//         return A[q]
//     else if i < k 
//         return RANDOMIZED-SELECT(A, p, q - 1, i)
//     else 
//         return RANDOMIZED-SELECT(A, q + 1, r, i - k)

// RANDOMIZED-PARTITION(A, p, r)
//     pivot = MEDIAN-OF-MEDIANS(A, p, r)
//     swap pivot with A[r]
//     recturn PARTITION(A, p, r)
// MEDIAN-OF-MEDIANS(A, p, r)
//     devide A[p..r] into groups of 5
//     for each groups
//         sort the group
//         find the median
//     collect all medians into array M
//     if length(M) == 1
//         return M[0]
//     else 
//         return MEDIAN-OF-MEDIANS(M, 0, length(M) - 1)
// PARTITION(A, p, r)
//     x = A[r]
//     i = p - 1
//     for j = p to r - 1
//         if A[j] <= x
//             i = i + 1
//             swap A[i] with A[j]
//     swap A[i + 1] with A[r]
//     return i + 1
import java.util.*;

public class RandomSelect {

    public static int randomSelect(int[] A, int p, int r, int i) {
        if (p == r) {
            return A[p];
        }
        int q = randomizedPartition(A, p, r);
        int k = q - p + 1;
        if (i == k) {
            return A[q];
        } else if (i < k) {
            return randomSelect(A, p, q - 1, i);
        } else {
            return randomSelect(A, q + 1, r, i - k);
        }
    }

    private static int randomizedPartition(int[] A, int p, int r) {
        int pivot = medianOfMedians(A, p, r);
        int pivotIndex = findIndex(A, p, r, pivot);
        swap(A, pivotIndex, r);
        return partition(A, p, r);
    }

    private static int partition(int[] A, int p, int r) {
        int x = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (A[j] <= x) {
                i++;
                swap(A, i, j);
            }
        }
        swap(A, i + 1, r);
        return i + 1;
    }

    private static int medianOfMedians(int[] A, int p, int r) {
        int n = r - p + 1;
        if (n <= 5) {
            Arrays.sort(A, p, r + 1);
            return A[p + n / 2];
        }

        int numMedians = (int) Math.ceil((double) n / 5);
        int[] medians = new int[numMedians];
        for (int i = 0; i < numMedians; i++) {
            int groupStart = p + i * 5;
            int groupEnd = Math.min(groupStart + 4, r);
            Arrays.sort(A, groupStart, groupEnd + 1);
            medians[i] = A[groupStart + (groupEnd - groupStart) / 2];
        }
        return medianOfMedians(medians, 0, numMedians - 1);
    }

    private static int findIndex(int[] A, int p, int r, int value) {
        for (int i = p; i <= r; i++) {
            if (A[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public static void main(String[] args) {
        int[] A = {1, 3, 5, 12, 54, 23, 4, 7, 9, 11, 26};
        int i = 4;
        int result = randomSelect(A, 0, A.length - 1, i);
        System.out.println(i + "th smallest element is: " + result);
    }
}
