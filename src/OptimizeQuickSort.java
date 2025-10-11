// FUNCTION OptimizeQuickSort(objects, low, high):
//     if low < high:
//         pivotIndex = randomizedPartition(objects, low, high)
//         OptimizeQuickSort(objects, low, pivotIndex - 1)
//         OptimizeQuickSort(objects, pivotIndex + 1, high)

// FUNCTION randomizedPartition(objects, low, high):
//     randomIndex = RANDOM(low, high)
//     SWAP(objects[randomIndex], objects[high])
//     pivot = objects[high].renderZ 
//     i = low - 1
//     for j = low to high - 1:
//         if objects[j].renderZ < pivot:
//             i = i + 1
//             SWAP(objects[i], objects[j])
//     SWAP(objects[i + 1], objects[high])
//     return i + 1
import java.util.Random;

class GameObject {

    String name;
    int renderZ;  // Depth or rendering order value

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

    public static void OptimizeQuickSort(GameObject[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high);
            OptimizeQuickSort(arr, low, pivotIndex - 1);
            OptimizeQuickSort(arr, pivotIndex + 1, high);
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

    private static void swap(GameObject[] arr, int i, int j) {
        GameObject temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Example usage
    public static void main(String[] args) {
        GameObject[] objects = {
            new GameObject("Player", 5),
            new GameObject("Enemy", 2),
            new GameObject("Background", 10),
            new GameObject("Projectile", 3),
            new GameObject("NPC", 7)
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
