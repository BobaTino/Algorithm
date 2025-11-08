// function generateMatrix(rows, cols):
//     create matrix[rows][cols]
//     for i in 0..rows:
//         for j in 0..cols:
//             matrix[i][j] = random()
//     return matrix

// function multiplyMatrices(A, B):
//     n = A.rows
//     m = A.cols
//     p = B.cols
//     create C[n][p]
//     for i in 0..n:
//         for j in 0..p:
//             C[i][j] = 0
//             for k in 0..m:
//                 C[i][j] += A[i][k] * B[k][j]
//     return C
// function parallelMultiply(A, B):
//     divide the rows of A into chunks
//     assign each chunk to a thread or ForkJoinTask
//     each thread computes partial result for its rows
//     join all results into C
import java.util.Random;
import java.util.concurrent.*;

public class ParallelMatrix {

    // Generate random matrix
    static double[][] generateMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextDouble();
            }
        }
        return matrix;
    }

    // Sequential multiplication
    static double[][] multiply(double[][] A, double[][] B) {
        int n = A.length;
        int m = A[0].length;
        int p = B[0].length;
        double[][] C = new double[n][p];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                double sum = 0;
                for (int k = 0; k < m; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        }
        return C;
    }

    // Parallel task for rows
    static class MultiplyTask extends RecursiveAction {

        static final int THRESHOLD = 100; // smaller = more parallel
        double[][] A, B, C;
        int start, end;

        MultiplyTask(double[][] A, double[][] B, double[][] C, int start, int end) {
            this.A = A;
            this.B = B;
            this.C = C;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= THRESHOLD) {
                int p = B[0].length;
                int m = B.length;
                for (int i = start; i < end; i++) {
                    for (int j = 0; j < p; j++) {
                        double sum = 0;
                        for (int k = 0; k < m; k++) {
                            sum += A[i][k] * B[k][j];
                        }
                        C[i][j] = sum;
                    }
                }
            } else {
                int mid = (start + end) / 2;
                invokeAll(new MultiplyTask(A, B, C, start, mid),
                        new MultiplyTask(A, B, C, mid, end));
            }
        }
    }

    static double[][] parallelMultiply(double[][] A, double[][] B) {
        double[][] C = new double[A.length][B[0].length];
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MultiplyTask(A, B, C, 0, A.length));
        pool.shutdown();
        return C;
    }

    public static void main(String[] args) {
        int size = 500;
        double[][] X = generateMatrix(size, size);
        double[][] W = generateMatrix(size, size);

        long start1 = System.currentTimeMillis();
        double[][] result1 = multiply(X, W);
        long end1 = System.currentTimeMillis();
        System.out.println("Sequential Time: " + (end1 - start1) + " ms");

        long start2 = System.currentTimeMillis();
        double[][] result2 = parallelMultiply(X, W);
        long end2 = System.currentTimeMillis();
        System.out.println("Parallel Time: " + (end2 - start2) + " ms");
    }
}
