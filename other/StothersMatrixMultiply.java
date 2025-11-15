// Function StothersMatrixMultiply (A, B)
//     n = size(A)
//     if n == 1 then
//         return A * B
//     Partition A and B into A11, A12, A21, A22 and B11, B12, B21, B22
//     // Apply Stothers’ optimized recursive multiplications
//     M1 = (A11 + A22) * (B11 + B22)
//     M2 = (A21 + A22) * B11
//     M3 = A11 * (B12 - B22)
//     M4 = A22 * (B21 - B11)
//     M5 = (A11 + A12) * B22
//     M6 = (A21 - A11) * (B11 + B12)
//     M7 = (A12 - A22) * (B21 + B22)
//     // Combine results
//     C11 = M1 + M4 - M5 + M7
//     C12 = M3 + M5
//     C21 = M2 + M4
//     C22 = M1 - M2 + M3 + M6
//     return Combine(C11, C12, C21, C22)

// Function RotateVertices(vertices, θ)
//     R = [[cos(θ), -sin(θ)],
//          [sin(θ),  cos(θ)]]   // Rotation matrix
//     for each vertex v in vertices:
//         v' = SquareMatrixMultiply_Stothers(R, v)
//         print v'
// Example:
//     vertices = [[1,0], [0,1], [-1,0], [0,-1]]
//     θ = 45°
//     RotateVertices(vertices, θ)
public class StothersMatrixMultiply {

    public static void main(String[] args) {
        // Rotate 3D points around Z-axis
        double angle = Math.toRadians(45);
        double[][] rotationMatrix = {
            {Math.cos(angle), -Math.sin(angle), 0},
            {Math.sin(angle), Math.cos(angle), 0},
            {0, 0, 1}
        };

        // Example vertex positions (x, y, z)
        double[][] vertices = {
            {1, 0, 0},
            {0, 1, 0},
            {-1, 0, 0},
            {0, -1, 0}
        };

        // Apply transformation using Stothers-inspired multiplication
        double[][] transformed = multiply(rotationMatrix, transpose(vertices));

        // Transpose result back to readable point format
        transformed = transpose(transformed);

        System.out.println("Original Vertices:");
        printMatrix(vertices);
        System.out.println("\nRotated Vertices (45° around Z-axis):");
        printMatrix(transformed);
    }

    public static double[][] multiply(double[][] A, double[][] B) {
        int n = A.length;
        int m = B[0].length;
        int p = B.length;

        // Base case: normal multiplication for small matrices
        if (n <= 3 || p <= 3) {
            return multiplyBase(A, B);
        }

        int newSize = n / 2;
        double[][][] a = split(A);
        double[][][] b = split(B);

        double[][] M1 = multiply(add(sub(add(a[0], a[3]), a[1]), a[3]),
                add(sub(add(b[0], b[3]), b[1]), b[3]));
        double[][] M2 = multiply(add(sub(add(a[2], a[3]), a[0]), a[1]),
                add(sub(add(b[0], b[1]), b[3]), b[2]));
        double[][] M3 = multiply(add(sub(add(a[0], a[1]), a[3]), a[2]),
                add(sub(add(b[3], b[1]), b[0]), b[2]));
        double[][] M4 = multiply(add(sub(add(a[3], a[0]), a[2]), a[1]),
                add(sub(add(b[0], b[2]), b[1]), b[3]));
        double[][] M5 = multiply(add(sub(a[1], a[0]), a[2]),
                add(sub(b[0], b[1]), b[3]));
        double[][] M6 = multiply(sub(a[0], a[3]), sub(b[1], b[2]));
        double[][] M7 = multiply(sub(a[1], a[2]), add(b[0], b[3]));

        double[][] C11 = add(sub(add(M1, M6), M5), M7);
        double[][] C12 = add(M3, M5);
        double[][] C21 = add(M2, M4);
        double[][] C22 = add(sub(add(M1, M3), M2), M6);

        return combine(C11, C12, C21, C22);
    }

    public static double[][] multiplyBase(double[][] A, double[][] B) {
        int rows = A.length, cols = B[0].length, inner = B.length;
        double[][] C = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < inner; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    // Matrix utilities
    public static double[][] add(double[][] A, double[][] B) {
        int n = A.length, m = A[0].length;
        double[][] C = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    public static double[][] sub(double[][] A, double[][] B) {
        int n = A.length, m = A[0].length;
        double[][] C = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    public static double[][][] split(double[][] M) {
        int n = M.length;
        int mid = n / 2;
        double[][] A11 = new double[mid][mid];
        double[][] A12 = new double[mid][mid];
        double[][] A21 = new double[mid][mid];
        double[][] A22 = new double[mid][mid];

        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                A11[i][j] = M[i][j];
                A12[i][j] = M[i][j + mid];
                A21[i][j] = M[i + mid][j];
                A22[i][j] = M[i + mid][j + mid];
            }
        }

        return new double[][][]{A11, A12, A21, A22};
    }

    public static double[][] combine(double[][] C11, double[][] C12, double[][] C21, double[][] C22) {
        int n = C11.length * 2;
        double[][] C = new double[n][n];
        int mid = n / 2;

        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                C[i][j] = C11[i][j];
                C[i][j + mid] = C12[i][j];
                C[i + mid][j] = C21[i][j];
                C[i + mid][j + mid] = C22[i][j];
            }
        }
        return C;
    }

    public static double[][] transpose(double[][] M) {
        double[][] T = new double[M[0].length][M.length];
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                T[j][i] = M[i][j];
            }
        }
        return T;
    }

    public static void printMatrix(double[][] M) {
        for (double[] row : M) {
            System.out.print("[");
            for (int i = 0; i < row.length; i++) {
                System.out.printf("%.3f", row[i]); // 3 decimal places
                if (i < row.length - 1) {
                    System.out.print(", "); // add comma and space between values
                }
            }
            System.out.println("]"); // close the bracket and move to next line
        }
    }
}
