// MERGESORT(Products)
//     n = length(Products)
//     Temp = new array of size n
//     SORT-RANGE(Products, 0, n - 1, Temp)
// SORT-RANGE(A, LEFT, RIGHT, TEMP)
//     if LEFT >= RIGHT
//         return
//     MID = (LEFT + RIGHT) / 2
//     SORT-RANGE(A, LEFT, MID, TEMP)
//     SORT-RANGE(A, MID + 1, RIGHT, TEMP)
//     MERGE(A, LEFT, MID, RIGHT, TEMP)
// MERGE(A, LEFT, MID, RIGHT, TEMP)
//     i = LEFT
//     j = MID + 1
//     k = LEFT
//     while i <= MID and j <= RIGHT
//         if A[i].score <= A[j].score
//             TEMP[k] = A[i]
//             i = i + 1
//         else
//             TEMP[k] = A[j]
//             j = j + 1
//     while i <= MID
//         TEMP[k] = A[i]
//         i = i + 1
//         k = k + 1
//     while j <= RIGHT
//         TEMP[k] = A[j]
//         j = j + 1
//         k = k + 1
//     for x = LEFT to RIGHT
//         A[x] = TEMP[x]

class Product {

    String name;
    double score; // Could be price, relevance score, rating, etc.

    public Product(String name, double score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + " (" + score + ")";
    }
}

public class OptimizeMergeSort {

    public static void mergeSort(Product[] arr) {
        Product[] temp = new Product[arr.length]; // Single shared buffer
        sortRange(arr, 0, arr.length - 1, temp);
    }

    private static void sortRange(Product[] arr, int left, int right, Product[] temp) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) / 2;
        sortRange(arr, left, mid, temp);
        sortRange(arr, mid + 1, right, temp);
        merge(arr, left, mid, right, temp);
    }

    private static void merge(Product[] arr, int left, int mid, int right, Product[] temp) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i].score <= arr[j].score) { // Sort by price/rank/rating
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            temp[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            temp[k] = arr[j];
            j++;
            k++;
        }

        for (int x = left; x <= right; x++) {
            arr[x] = temp[x];
        }
    }

    // Example
    public static void main(String[] args) {
        Product[] products = {
            new Product("Phone", 899.99),
            new Product("Headphones", 199.99),
            new Product("Laptop", 1299.49),
            new Product("Smartwatch", 249.99),
            new Product("Keyboard", 99.99)
        };

        System.out.println("Before sorting:");
        for (Product p : products) {
            System.out.println(p);
        }

        mergeSort(products);

        System.out.println("\nAfter sorting by score:");
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
