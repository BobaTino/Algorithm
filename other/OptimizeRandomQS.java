// function OptimizeRandomQS(orders, low, high):
//     while low < high:
//         // Use median-of-three to choose pivot index
//         pivotIndex = medianOfThree(orders, low, high)
//         swap(orders[pivotIndex], orders[high])
//         pivot = partition(orders, low, high)
//         if (pivot - low) < (high - pivot):
//             quickSortOrders(orders, low, pivot - 1)
//             low = pivot + 1
//         else:
//             quickSortOrders(orders, pivot + 1, high)
//             high = pivot - 1

// function medianOfThree(orders, low, high):
//     mid = (low + high) / 2
//     if orders[low].price > orders[mid].price:
//         swap(orders[low], orders[mid])
//     if orders[low].price > orders[high].price:
//         swap(orders[low], orders[high])
//     if orders[mid].price > orders[high].price:
//         swap(orders[mid], orders[high])
//     return mid
// function partition(orders, low, high):
//     pivotPrice = orders[high].price
//     i = low - 1
//     for j from low to high - 1:
//         if orders[j].price <= pivotPrice:
//             i = i + 1
//             swap(orders[i], orders[j])
//     swap(orders[i + 1], orders[high])
//     return i + 1
class Order {

    double price;
    String id;

    public Order(double price, String id) {
        this.price = price;
        this.id = id;
    }
}

public class OptimizeRandomQS {

    public static void quickSort(Order[] orders, int low, int high) {
        while (low < high) {
            int pivotIndex = medianOfThree(orders, low, high);
            swap(orders, pivotIndex, high);

            int pivot = partition(orders, low, high);

            if (pivot - low < high - pivot) {
                quickSort(orders, low, pivot - 1);
                low = pivot + 1;
            } else {
                quickSort(orders, pivot + 1, high);
                high = pivot - 1;
            }
        }
    }

    private static int medianOfThree(Order[] orders, int low, int high) {
        int mid = (low + high) / 2;

        if (orders[low].price > orders[mid].price) {
            swap(orders, low, mid);
        }
        if (orders[low].price > orders[high].price) {
            swap(orders, low, high);
        }
        if (orders[mid].price > orders[high].price) {
            swap(orders, mid, high);
        }

        return mid;
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivotPrice = orders[high].price;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (orders[j].price <= pivotPrice) {
                i++;
                swap(orders, i, j);
            }
        }

        swap(orders, i + 1, high);
        return i + 1;
    }

    private static void swap(Order[] orders, int i, int j) {
        Order temp = orders[i];
        orders[i] = orders[j];
        orders[j] = temp;
    }

    public static void main(String[] args) {
        Order[] orders = {
            new Order(102.5, "Tesla"),
            new Order(101.0, "AMD"),
            new Order(103.2, "NVIDIA"),
            new Order(99.8, "AAPL"),
            new Order(102.5, "MSFT")
        };

        quickSort(orders, 0, orders.length - 1);

        System.out.println("Sorted Orders (by Price):");
        for (Order o : orders) {
            System.out.println(o.id + " - $" + o.price);
        }
    }
}
