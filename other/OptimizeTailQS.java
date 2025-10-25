// function OptimizeTailQS(arr, n):
//     Create manual stack to replace recursion
//     stack = new array of size n
//     top = -1
//     Push initial range (low, high)
//     top = top + 1
//     stack[top] = 0         // low
//     top = top + 1
//     stack[top] = n - 1     // high
//     while top >= 1:
//         high = stack[top]
//         top = top - 1
//         low = stack[top]
//         top = top - 1
//         pivot = partition(arr, low, high)
//         if pivot - 1 > low:
//             top = top + 1
//             stack[top] = low
//             top = top + 1
//             stack[top] = pivot - 1
//         if pivot + 1 < high:
//             top = top + 1
//             stack[top] = pivot + 1
//             top = top + 1
//             stack[top] = high
// function partition(arr, low, high):
//     pivot = arr[high]
//     i = low - 1
//     for j from low to high - 1:
//         if arr[j] <= pivot:
//             i = i + 1
//             swap(arr[i], arr[j])
//     swap(arr[i + 1], arr[high])
//     return i + 1

public class OptimizeTailQS {

    public static void iterativeQuickSort(int[] data) {
        int n = data.length;
        int[] stack = new int[n];
        int top = -1;
        stack[++top] = 0;
        stack[++top] = n - 1;

        while (top >= 1) {
            int high = stack[top--];
            int low = stack[top--];

            int pivot = partition(data, low, high);

            // Push left side if elements exist
            if (pivot - 1 > low) {
                stack[++top] = low;
                stack[++top] = pivot - 1;
            }

            // Push right side if elements exist
            if (pivot + 1 < high) {
                stack[++top] = pivot + 1;
                stack[++top] = high;
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
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

    public static void main(String[] args) {
        int[] sensorData = {45, 20, 38, 52, 11, 29, 41};

        System.out.println("Before Sorting:");
        for (int v : sensorData) {
            System.out.print(v + " ");
        }

        iterativeQuickSort(sensorData);

        System.out.println("\nAfter Sorting:");
        for (int v : sensorData) {
            System.out.print(v + " ");
        }
    }
}
