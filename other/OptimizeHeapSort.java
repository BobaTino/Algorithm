// Each player have { name, score }
// Build Max-Heap using Bottom-Up Construction
// function buildMaxHeap(players):
//     n = length(players)
//     for i = floor(n / 2) - 1 down to 0:
//         heapify(players, n, i)
//     return players

// Heapify ensures max-heap property
// function heapify(players, n, i):
//     largest = i
//     left = 2 * i + 1
//     right = 2 * i + 2
//     if left < n and players[left].score > players[largest].score:
//         largest = left
//     if right < n and players[right].score > players[largest].score:
//         largest = right
//     if largest != i:
//         swap(players[i], players[largest])
//         heapify(players, n, largest)
// Update leaderboard after a tournament round
// function updateLeaderboard(newScores):
//     players = collectAllPlayersWithUpdatedScores(newScores)
//     Build heap in O(n) using bottom-up method
//     maxHeap = buildMaxHeap(players)
//     Extract top 10 players for display
//     topPlayers = []
//     for i = 1 to 10:
//         topPlayers.append(extractMax(maxHeap))
//     display(topPlayers)
// Extract player with highest score
// function extractMax(heap):
//     if heap is empty:
//         return null
//     top = heap[0]
//     heap[0] = heap[last]
//     remove last element
//     heapify(heap, length(heap), 0)
//     return top
import java.util.ArrayList;
import java.util.List;

class Player {

    String name;
    int score;

    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}

public class OptimizeHeapSort {

    // Build Max Heap using Bottom-Up Construction
    public static void buildMaxHeap(List<Player> players) {
        int n = players.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(players, n, i);
        }
    }

    // Heapify a subtree rooted at index i
    public static void heapify(List<Player> players, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && players.get(left).score > players.get(largest).score) {
            largest = left;
        }

        if (right < n && players.get(right).score > players.get(largest).score) {
            largest = right;
        }

        if (largest != i) {
            swap(players, i, largest);
            heapify(players, n, largest);
        }
    }

    // Swap two players
    public static void swap(List<Player> players, int i, int j) {
        Player temp = players.get(i);
        players.set(i, players.get(j));
        players.set(j, temp);
    }

    // Extract top score player
    public static Player extractMax(List<Player> players) {
        if (players.size() == 0) {
            return null;
        }

        Player top = players.get(0);
        players.set(0, players.get(players.size() - 1));
        players.remove(players.size() - 1);
        heapify(players, players.size(), 0);
        return top;
    }

    public static void updateLeaderboard(List<Player> players) {
        buildMaxHeap(players);

        System.out.println("Top Players:");
        int topCount = Math.min(10, players.size());

        for (int i = 0; i < topCount; i++) {
            Player top = extractMax(players);
            System.out.println((i + 1) + ". " + top.name + " - " + top.score);
        }
    }

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Alice", 95));
        players.add(new Player("Bob", 88));
        players.add(new Player("Chris", 120));
        players.add(new Player("Diana", 110));
        players.add(new Player("Ethan", 102));
        players.add(new Player("Faye", 99));

        updateLeaderboard(players);
    }
}
