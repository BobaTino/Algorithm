// Time: O(n^2)
// Space: O(n)

// LOSSY-COMPRESS(I)                                // I = input image
//    Convert I to YCbCr color space
//    Divide I into 8×8 pixel blocks B
//    for each block b in B
//         D ← DCT(b)                               // transform to frequency domain
//         Q ← QUANTIZE(D)                          // divide by quantization table (lossy)
//         E ← ZIGZAG(Q)                            // 2D → 1D ordering
//         H ← HUFFMAN-ENCODE(E)                    // entropy compression
//         append H to OUTPUT-FILE
//    return OUTPUT-FILE
// DCT(b)
//    for each (u,v) in block
//         compute D[u][v] using cosine transform formula
//    return D
// QUANTIZE(D)
//    for i=1 to 8
//         for j=1 to 8
//              D[i][j] ← round( D[i][j] / QUANT_TABLE[i][j] )
//    return D
// ZIGZAG(M)
//    read M in zigzag diagonal order into array Z[1..64]
//    return Z
// HUFFMAN-ENCODE(E)
//    build frequency table of values in E
//    construct Huffman tree
//    encode values using tree
//    return encoded bitstream
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class LossyCompression {

    // Standard JPEG quantization table 
    private static final int[][] QUANT = {
        {16, 11, 10, 16, 24, 40, 51, 61},
        {12, 12, 14, 19, 26, 58, 60, 55},
        {14, 13, 16, 24, 40, 57, 69, 56},
        {14, 17, 22, 29, 51, 87, 80, 62},
        {18, 22, 37, 56, 68, 109, 103, 77},
        {24, 35, 55, 64, 81, 104, 113, 92},
        {49, 64, 78, 87, 103, 121, 120, 101},
        {72, 92, 95, 98, 112, 100, 103, 99}
    };

    public static void compress(String input, String output) throws Exception {
        BufferedImage img = ImageIO.read(new File(input));
        int width = img.getWidth(), height = img.getHeight();

        ArrayList<int[]> encodedBlocks = new ArrayList<>();

        for (int y = 0; y < height; y += 8) {
            for (int x = 0; x < width; x += 8) {

                double[][] block = extractBlock(img, x, y);
                double[][] dct = applyDCT(block);
                int[][] quant = quantize(dct);
                int[] zig = zigzag(quant);
                encodedBlocks.add(zig);
            }
        }

        System.out.println("Compression complete. Total blocks: " + encodedBlocks.size());
    }

    private static double[][] extractBlock(BufferedImage img, int x, int y) {
        double[][] block = new double[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                block[i][j] = (x + j < img.getWidth() && y + i < img.getHeight())
                        ? img.getRGB(x + j, y + i) & 0xFF
                        : 0;
            }
        }
        return block;
    }

    private static double[][] applyDCT(double[][] b) {
        int N = 8;
        double[][] out = new double[N][N];

        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                double sum = 0;
                for (int x = 0; x < N; x++) {
                    for (int y = 0; y < N; y++) {
                        sum += b[x][y]
                                * Math.cos((2 * x + 1) * u * Math.PI / 16)
                                * Math.cos((2 * y + 1) * v * Math.PI / 16);
                    }
                }
                out[u][v] = sum / 4.0;
            }
        }
        return out;
    }

    private static int[][] quantize(double[][] dct) {
        int[][] q = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                q[i][j] = (int) Math.round(dct[i][j] / QUANT[i][j]);
            }
        }
        return q;
    }

    private static int[] zigzag(int[][] m) {
        int[] result = new int[64];
        int index = 0;

        for (int sum = 0; sum <= 14; sum++) {
            if (sum % 2 == 0) {
                for (int row = Math.min(sum, 7); row >= 0; row--) {
                    int col = sum - row;
                    if (col < 8 && row < 8) {
                        result[index++] = m[row][col];
                    }
                }
            } else {
                for (int row = 0; row <= Math.min(sum, 7); row++) {
                    int col = sum - row;
                    if (col < 8 && row < 8) {
                        result[index++] = m[row][col];
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        compress("exp/Banana.jpg", "exp/Banana.lzjpg");
    }
}
