// BUCKET_SORT_FINANCIAL(stockPrices, numBuckets):
//     if stockPrices.length <= 1:
//         return stockPrices
//     Find min and max prices
//     minPrice = min(stockPrices)
//     maxPrice = max(stockPrices)

//     Create buckets for price ranges
//     create list of buckets: B[0..numBuckets-1]
//     Distribute stock prices into buckets
//     for each price in stockPrices:
//         index = (price - minPrice) * (numBuckets - 1) / (maxPrice - minPrice)
//         B[index].add(price)
//     Sort each bucket using Randomized Quick Sort
//     for each bucket in B:
//         if bucket.size > 1:
//             RANDOMIZED_QUICKSORT(bucket, 0, bucket.size - 1)
//     Concatenate sorted buckets to final array
//     result = empty list
//     for each bucket in B:
//         append bucket to result
//     return result
import java.util.*;

public class OptimizeRandomQS {

    // Sort stock prices using bucket sort + randomized quick sort
    public static void bucketQuickSort(int[] stockPrices, int numBuckets) {
        if (stockPrices.length <= 1) {
            return;
        }

        int minPrice = Integer.MAX_VALUE;
        int maxPrice = Integer.MIN_VALUE;

        // Find min and max for bucket range
        for (int price : stockPrices) {
            minPrice = Math.min(minPrice, price);
            maxPrice = Math.max(maxPrice, price);
        }

        // Create buckets
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < numBuckets; i++) {
            buckets.add(new ArrayList<>());
        }

        // Distribute stock prices into buckets
        for (int price : stockPrices) {
            int index = (int) ((long) (price - minPrice) * (numBuckets - 1) / (maxPrice - minPrice + 1));
            buckets.get(index).add(price);
        }

        // Sort each bucket using randomized quick sort
        int idx = 0;
        Random rand = new Random();
        for (List<Integer> bucket : buckets) {
            if (bucket.size() > 1) {
                int[] temp = bucket.stream().mapToInt(i -> i).toArray();
                randomizedQuickSort(temp, 0, temp.length - 1, rand);
                for (int t : temp) {
                    stockPrices[idx++] = t;
                }
            } else {
                for (int t : bucket) {
                    stockPrices[idx++] = t;
                }
            }
        }
    }

    private static void randomizedQuickSort(int[] arr, int low, int high, Random rand) {
        if (low < high) {
            int pivotIndex = randomPartition(arr, low, high, rand);
            randomizedQuickSort(arr, low, pivotIndex - 1, rand);
            randomizedQuickSort(arr, pivotIndex + 1, high, rand);
        }
    }

    private static int randomPartition(int[] arr, int low, int high, Random rand) {
        int randomIndex = rand.nextInt(high - low + 1) + low;
        swap(arr, randomIndex, high);
        return partition(arr, low, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    //sorting stock prices in real-time financial system
    public static void main(String[] args) {
        int[] stockPrices = {1025, 1010, 1038, 1020, 1015, 1030, 1022};
        System.out.println("Before Sorting: " + Arrays.toString(stockPrices));

        bucketQuickSort(stockPrices, 5);

        System.out.println("After Sorting: " + Arrays.toString(stockPrices));
    }
}
