// Time: O(n), Space: O(n)

// INSERT(root, value):
//     if root is null:
//         return new Node(value)
//     if value < root.value:
//         root.left = INSERT(root.left, value)
//     else:
//         root.right = INSERT(root.right, value)
//     return root
// PRINT_LEVEL_ORDER(root):
//     if root is null:
//         return
//     create a queue Q
//     enqueue root into Q
//     while Q is not empty:
//         levelSize = size of Q
//         for i = 1 to levelSize:
//             node = dequeue Q
//             if node is not null:
//                 print node.value + ", "
//                 enqueue node.left
//                 enqueue node.right
//             else:
//                 print "., "
//                 enqueue null
//                 enqueue null
//         print newline
import java.util.LinkedList;
import java.util.Queue;

class Node {

    int value;
    Node left, right;

    Node(int v) {
        value = v;
        left = right = null;
    }
}

public class BinaryTreeDataStructure {

    public static Node insert(Node root, int value) {
        if (root == null) {
            return new Node(value);
        }
        if (value < root.value) {
            root.left = insert(root.left, value);
        } else {
            root.right = insert(root.right, value);
        }
        return root;
    }

    public static void printLevelOrder(Node root) {
        if (root == null) {
            return;
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {

            int levelSize = q.size();
            boolean allNull = true;

            for (int i = 0; i < levelSize; i++) {
                Node node = q.poll();

                if (node != null) {
                    System.out.print(node.value + ", ");
                    allNull = false;
                    q.add(node.left);
                    q.add(node.right);
                } else {
                    System.out.print("., ");
                    q.add(null);
                    q.add(null);
                }
            }

            System.out.println();

            // Stop if next level has only nulls
            if (allNull) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] values = {6, 4, 8, 3, 5, 7, 9};

        Node root = null;
        for (int v : values) {
            root = insert(root, v);
        }

        printLevelOrder(root);
    }
}
