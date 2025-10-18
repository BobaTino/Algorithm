// THRESHOLD = 10   // When subarray is small, switch to insertion sort
// FUNCTION OptimizeQuickSort(objects, low, high):
//     WHILE low < high:
//         IF (high - low + 1) < THRESHOLD:
//             insertionSort(objects, low, high)
//             RETURN
//         pivotIndex = randomizedPartition(objects, low, high)
//         // Recursively sort the smaller side first to reduce stack depth
//         IF pivotIndex - low < high - pivotIndex:
//             OptimizeQuickSort(objects, low, pivotIndex - 1)
//             low = pivotIndex + 1   // Tail call elimination
//         ELSE:
//             OptimizeQuickSort(objects, pivotIndex + 1, high)
//             high = pivotIndex - 1
// FUNCTION randomizedPartition(objects, low, high):
//     randomIndex = RANDOM(low, high)
//     SWAP(objects[randomIndex], objects[high])
//     pivot = objects[high].renderZ
//     i = low - 1
//     FOR j = low TO high - 1:
//         IF objects[j].renderZ < pivot:
//             i = i + 1
//             SWAP(objects[i], objects[j])
//     SWAP(objects[i + 1], objects[high])
//     RETURN i + 1
// FUNCTION insertionSort(objects, low, high):
//     FOR i = low + 1 TO high:
//         key = objects[i]
//         j = i - 1
//         WHILE j >= low AND objects[j].renderZ > key.renderZ:
//             objects[j + 1] = objects[j]
//             j = j - 1
//         objects[j + 1] = key

import java.util.Random;

class GameObject {

    String name;
    int renderZ;

    public GameObject(String name, int renderZ) {
        this.name = name;
        this.renderZ = renderZ;
    }

    @Override
    public String toString() {
        return name + " (Z=" + renderZ + ")";
    }
}

public class OptimizeQuickSort {

    private static final Random rand = new Random();
    private static final int THRESHOLD = 10; // Switch point to insertion sort

    public static void OptimizeQuickSort(GameObject[] arr, int low, int high) {
        while (low < high) {
            if (high - low + 1 < THRESHOLD) {
                insertionSort(arr, low, high);
                return;
            }

            int pivotIndex = randomizedPartition(arr, low, high);

            // Sort smaller partition first (tail recursion optimization)
            if (pivotIndex - low < high - pivotIndex) {
                OptimizeQuickSort(arr, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                OptimizeQuickSort(arr, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private static int randomizedPartition(GameObject[] arr, int low, int high) {
        int randomIndex = rand.nextInt(high - low + 1) + low;
        swap(arr, randomIndex, high);

        int pivotValue = arr[high].renderZ;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].renderZ < pivotValue) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void insertionSort(GameObject[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            GameObject key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j].renderZ > key.renderZ) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void swap(GameObject[] arr, int i, int j) {
        GameObject temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        GameObject[] objects = {
            new GameObject("Player", 5),
            new GameObject("Enemy", 2),
            new GameObject("Background", 10),
            new GameObject("Projectile", 3),
            new GameObject("NPC", 7),
            new GameObject("UI", 15),
            new GameObject("Particle", 6),
            new GameObject("Shield", 4)
        };

        System.out.println("Before Sorting (Rendering Order):");
        for (GameObject obj : objects) {
            System.out.println(obj);
        }

        OptimizeQuickSort(objects, 0, objects.length - 1);

        System.out.println("\nAfter Sorting (Rendering Order):");
        for (GameObject obj : objects) {
            System.out.println(obj);
        }
    }
}
