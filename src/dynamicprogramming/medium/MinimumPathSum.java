package dynamicprogramming.medium;

/**
 * Created by Yiyun On 2020/1/27 20:49
 *
 * 64. Minimum Path Sum
 * https://leetcode.com/problems/minimum-path-sum/
 */
public class MinimumPathSum {

    // because there are only 2 dirs: right/down, thus we use a directive dp.
    public int minPathSum_review20200912(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // initialization
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) dp[i][0] = grid[i][0] + dp[i-1][0];
        for (int i = 1; i < n; i++) dp[0][i] = grid[0][i] + dp[0][i-1];

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i][j-1], dp[i-1][j]);
            }
        }

        return dp[m-1][n-1];

    }

    public int minPathSum(int[][] grid) {
        if (grid.length == 0) return 0;
        int row = grid.length;
        int column = grid[0].length;
        int[][] arr = new int[row][column];
        arr[0][0] = grid[0][0];

        // initialize arr
        for (int i = 1; i < row; i++) {
            arr[i][0] = arr[i-1][0] + grid[i][0];
        }

        for (int i = 1; i < column; i++) {
            arr[0][i] = arr[0][i-1] + grid[0][i];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < column; j++) {
                arr[i][j] = Math.min(arr[i-1][j], arr[i][j-1]) + grid[i][j];
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < column; j++) {
                arr[i][j] = Math.min(arr[i-1][j], arr[i][j-1]) + grid[i][j];

            }
        }

        return arr[row-1][column-1];
    }

}
