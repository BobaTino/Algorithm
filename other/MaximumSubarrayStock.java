// MAXIMUM-SUBARRAY-STOCK(prices[1..n])
//     maxSoFar = 0
//     maxEndingHere = 0
//     buy = 1
//     sell = 1
//     tempBuy = 1
//     for i = 2 to n 
//         change = prices[i] - prices[i - 1]
//         if maxEndingHere + change > 0 
//             maxEndingHere = maxEndingHere + change
//             if maxEndingHere > maxSoFar
//                 maxSoFar = maxEndingHere
//                 buy = tempBuy
//                 sell = i
//         else 
//             maxEndingHere = 0
//             tempBuy = i
//     return (buy, sell, maxSoFar)

public class MaximumSubarrayStock {

    public static int[] maxProfit(int[] prices) {
        int n = prices.length;
        if (n < 2) {
            return new int[]{-1, -1, 0};
        }
        int maxSoFar = 0;
        int maxEndingHere = 0;
        int buy = 0;
        int sell = 0;
        int tempBuy = 0;

        for (int i = 1; i < n; i++) {
            int change = prices[i] - prices[i - 1];
            if (maxEndingHere + change > 0) {
                maxEndingHere += change;
                if (maxEndingHere > maxSoFar) {
                    maxSoFar = maxEndingHere;
                    buy = tempBuy;
                    sell = i;
                }
            } else {
                maxEndingHere = 0;
                tempBuy = i;
            }
        }
        return new int[]{buy, sell, maxSoFar};
    }

    public static void main(String[] args) {
        int[] prices = {100, 120, 167, 231, 324, 567, 632, 965};
        int[] result = maxProfit(prices);

        System.out.println("Buy on day: " + result[0]);
        System.out.println("Sell on day: " + result[1]);
        System.out.println("Max Profit: " + result[2]);
    }
}
