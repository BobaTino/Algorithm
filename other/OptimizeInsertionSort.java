// MERGE-SORT(F, M)
//     RUNS = SPLIT-AND-SORT(F, M)
//     while length(RUNS) > 1
//         NEW = EMPTY-LIST 
//         for i = 1 to length(RUNS) step 2
//             if i + 1 <= length(RUNS)
//                 APPEND NEW, MERGE-FILES(RUNS[i], RUNS[i+1])
//             else 
//                 APPEND NEW, RUNS[i]
//         RUNS = NEW
//     RENAME RUNS[1] AS F_sorted
// SPLIT-AND-SORT(F, M)
//     OPEN F
//     RUNS ← EMPTY-LIST
//      while NOT EOF(F)
//         B ← READ M LINES
//         SORT(B)
//         APPEND RUNS, WRITE-TEMP(B)
//     CLOSE F
//     return RUNS
// MERGE-FILES(F1, F2)
//     OPEN F1, F2
//     T = CREATE=TEMP 
//     x = READ(F1), y = READ(F2)
//     while x != EOF OR y != EOF
//         if y = EOF OR (x != EOF AND x <= y)
//             WRITE(T, x)
//             x = READ(F1)
//         else 
//             WRITE(T, y)
//             y = READ(F2)
//     CLOSE F1, F2, T
//     return T

import java.io.*;
import java.util.*;

public class OptimizeInsertionSort {

    // Size of each chunk
    static final int CHUNK_SIZE = 10000;

    public static void main(String[] args) throws Exception {
        String inputFile = "exp\\input.txt";
        String outputFile = "exp\\sorted.txt";

        List<File> sortedChunks = splitAndSortChunks(inputFile);
        File result = mergeSortedFiles(sortedChunks);
        result.renameTo(new File(outputFile));

        System.out.println("Sorting complete: " + outputFile);
    }

    private static List<File> splitAndSortChunks(String inputFile) throws IOException {
        List<File> chunkFiles = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<Integer> buffer = new ArrayList<>(CHUNK_SIZE);

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.add(Integer.parseInt(line));
            if (buffer.size() == CHUNK_SIZE) {
                chunkFiles.add(writeChunkToFile(buffer));
                buffer.clear();
            }
        }

        if (!buffer.isEmpty()) {
            chunkFiles.add(writeChunkToFile(buffer));
        }

        reader.close();
        return chunkFiles;
    }

    private static File writeChunkToFile(List<Integer> chunk) throws IOException {
        Collections.sort(chunk);
        File tempFile = File.createTempFile("chunk", ".txt");
        tempFile.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        for (int num : chunk) {
            writer.write(Integer.toString(num));
            writer.newLine();
        }
        writer.close();

        return tempFile;
    }

    private static File mergeSortedFiles(List<File> files) throws IOException {
        PriorityQueue<FileBuffer> pq = new PriorityQueue<>();
        for (File f : files) {
            FileBuffer fb = new FileBuffer(f);
            if (!fb.isEmpty()) {
                pq.add(fb);
            }
        }

        File outputFile = File.createTempFile("merged", ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        while (!pq.isEmpty()) {
            FileBuffer fb = pq.poll();
            writer.write(Integer.toString(fb.peek()));
            writer.newLine();
            fb.pop();
            if (fb.isEmpty()) {
                fb.close();
            } else {
                pq.add(fb);
            }
        }

        writer.close();
        return outputFile;
    }

    // Manage reading from sorted chunk files
    static class FileBuffer implements Comparable<FileBuffer> {

        private BufferedReader reader;
        private Integer cache;

        public FileBuffer(File file) throws IOException {
            reader = new BufferedReader(new FileReader(file));
            reload();
        }

        public boolean isEmpty() {
            return cache == null;
        }

        public Integer peek() {
            return cache;
        }

        public void pop() throws IOException {
            reload();
        }

        private void reload() throws IOException {
            String line = reader.readLine();
            cache = (line == null) ? null : Integer.parseInt(line);
        }

        public void close() throws IOException {
            reader.close();
        }

        @Override
        public int compareTo(FileBuffer other) {
            return this.peek().compareTo(other.peek());
        }
    }
}
