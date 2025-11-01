// function WINOGRAD_CONVOLUTION(image, kernel):
//     n ← number of rows in image
//     result ← new matrix of size (n-2) × (n-2)

//     // Loop over each 3×3 window in the image
//     for i ← 0 to n - 3:
//         for j ← 0 to n - 3:
//             // Extract 3×3 patch from image
//             d ← new array[9]
//             idx ← 0
//             for ki ← 0 to 2:
//                 for kj ← 0 to 2:
//                     d[idx] ← image[i + ki][j + kj]
//                     idx ← idx + 1
//             // Apply Winograd-style precomputation to reduce multiplications
//             // Instead of 9 multiplications, combine terms into 5 groups
//             m1 ← (d[0] + d[1] + d[2]) * (kernel[0][0] + kernel[0][1] + kernel[0][2])
//             m2 ← (d[3] + d[4] + d[5]) * (kernel[1][0] + kernel[1][1] + kernel[1][2])
//             m3 ← (d[6] + d[7] + d[8]) * (kernel[2][0] + kernel[2][1] + kernel[2][2])
//             m4 ← (d[0] + d[3] + d[6]) * (kernel[0][0] + kernel[1][0] + kernel[2][0])
//             m5 ← (d[2] + d[5] + d[8]) * (kernel[0][2] + kernel[1][2] + kernel[2][2])
//             // Combine partial results to approximate the convolution output
//             result[i][j] ← (m1 + m2 + m3 + m4 + m5) / 9.0
//     return result
public class WinogradSquareMatrix {

    // Example of 3x3 blur kernel
    private static final double[][] BLUR_KERNEL = {
        {1 / 9.0, 1 / 9.0, 1 / 9.0},
        {1 / 9.0, 1 / 9.0, 1 / 9.0},
        {1 / 9.0, 1 / 9.0, 1 / 9.0}
    };

    public static double[][] normalConvolution(double[][] image, double[][] kernel) {
        int n = image.length;
        double[][] result = new double[n - 2][n - 2];
        for (int i = 0; i < n - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                double sum = 0;
                for (int ki = 0; ki < 3; ki++) {
                    for (int kj = 0; kj < 3; kj++) {
                        sum += image[i + ki][j + kj] * kernel[ki][kj];
                    }
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    // Simplified Winograd version with reduces multiplications
    public static double[][] winogradConvolution(double[][] image, double[][] kernel) {
        int n = image.length;
        double[][] result = new double[n - 2][n - 2];

        // Precompute row & column sums 
        for (int i = 0; i < n - 2; i++) {
            for (int j = 0; j < n - 2; j++) {

                double[] d = new double[3 * 3];
                int idx = 0;
                for (int ki = 0; ki < 3; ki++) {
                    for (int kj = 0; kj < 3; kj++) {
                        d[idx++] = image[i + ki][j + kj];
                    }
                }

                // Winograd minimal filtering F(2x2,3x3) idea:
                // Fewer multiplications by grouping terms
                double m1 = (d[0] + d[1] + d[2]) * (kernel[0][0] + kernel[0][1] + kernel[0][2]);
                double m2 = (d[3] + d[4] + d[5]) * (kernel[1][0] + kernel[1][1] + kernel[1][2]);
                double m3 = (d[6] + d[7] + d[8]) * (kernel[2][0] + kernel[2][1] + kernel[2][2]);
                double m4 = (d[0] + d[3] + d[6]) * (kernel[0][0] + kernel[1][0] + kernel[2][0]);
                double m5 = (d[2] + d[5] + d[8]) * (kernel[0][2] + kernel[1][2] + kernel[2][2]);

                // Combine partial results
                result[i][j] = (m1 + m2 + m3 + m4 + m5) / 9.0;
            }
        }

        return result;
    }

    public static void printMatrix(double[][] M) {
        for (double[] row : M) {
            for (double val : row) {
                System.out.printf("%8.3f", val);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        double[][] image = {
            {52, 55, 61, 66, 70},
            {63, 59, 55, 90, 109},
            {85, 104, 120, 110, 100},
            {90, 94, 89, 88, 75},
            {70, 65, 60, 55, 50}
        };

        System.out.println("Normal Convolution (3x3 Blur):");
        printMatrix(normalConvolution(image, BLUR_KERNEL));

        System.out.println("\nWinograd-Optimized Convolution:");
        printMatrix(winogradConvolution(image, BLUR_KERNEL));
    }
}
