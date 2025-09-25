// Time O(log n)
// Space O(n)

public class OptimizeBinarySearch {

    public static int binarySearch(int[] A, int T) {
        int L = 0;
        int R = A.length - 1;
        while (L <= R) {
            int m = L + (R - L) / 2;
            if (A[m] == T) {
                return m;
            } else if (A[m] < T) {
                L = m + 1;
            } else {
                R = m - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 8, 2, 9, 6, 7, 5, 10};
        int target = 8;
        int result = binarySearch(arr, target);

        if (result != -1) {
            System.out.println("Elemement found at index: " + result);
        } else {
            System.out.println("That element doesnt exitst");
        }
    }
}
