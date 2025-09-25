// LCS-MEMO(x, y)
//     length(x) = m
//     length(y) = n
//     empty dictionary = MEMO
//     return LCS-HELPER(x, y, m, n, MEMO)

// LCS-HELPER(x, y, i, j, MEMO)
//     if (i == 0 or j == 0)
//         return 0
//     if (i , j) in MEMO
//         return MEMO[(i, j)]
//     if x[i] == y[j]
//         MEMO[(i, j)] = 1 + LCS-HELPER(x, y, i - 1, j - 1, MEMO)
//     else
//         MEMO[(i, j)] = max(LCS-HELPER(x, y, i - 1, j, MEMO), LCS-HELPER(x, y, i, j - 1, MEMO))
//     result = MEMO[(i, j)]
//     return result
// TimeO(n^c)
// SpaceO(n^c)
import java.util.HashMap;
import java.util.Map;

public class LCSMemoized {

    static class Pair {

        int i, j;

        Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair pair = (Pair) o;
            return i == pair.i && j == pair.j;
        }

        @Override
        public int hashCode() {
            return 31 * i + j;
        }
    }

    public static int lcs(String x, String y) {
        Map<Pair, Integer> memo = new HashMap<>();
        return dp(x, y, x.length(), y.length(), memo);
    }

    private static int dp(String x, String y, int i, int j, Map<Pair, Integer> memo) {
        if (i == 0 || j == 0) {
            return 0;
        }
        Pair key = new Pair(i, j);

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int result;
        if (x.charAt(i - 1) == y.charAt(j - 1)) {
            result = 1 + dp(x, y, i - 1, j - 1, memo);
        } else {
            // Characters don't match
            result = Math.max(dp(x, y, i - 1, j, memo),
                    dp(x, y, i, j - 1, memo));
        }

        memo.put(key, result);
        return result;
    }

    public static void main(String[] args) {
        String X = "AGGTAB";
        String Y = "GXTXAYB";

        System.out.println("Length of LCS: " + lcs(X, Y));
    }
}
