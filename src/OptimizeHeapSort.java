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
public class OptimizeHeapSort {

}
