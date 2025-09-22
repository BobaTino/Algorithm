
public class BinaryCounter {

    public static void increment(int[] A) {
        int i = 0;
        while (i < A.length && A[i] == 1) {
            A[i] = 0;
            i++;
        }
        if (i < A.length) {
            A[i] = 1;
        }
    }

    public static void printArray(int[] A) {
        for (int i = A.length - 1; i >= 0; i--) {
            System.out.print(A[i]);
        }
        System.out.println();
    }

    public static void printBinaryCounter(int n) throws InterruptedException {
        int[] A = new int[n];
        int total = (int) Math.pow(2, n);
        for (int count = 0; count < total; count++) {
            printArray(A);
            increment(A);
            Thread.sleep(2000); // Pause for 2 seconds
        }
    }

    public static void main(String[] args) throws InterruptedException {
        printBinaryCounter(8);
    }
}
