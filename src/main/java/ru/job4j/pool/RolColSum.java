package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    private static int rowSum(int[][] matrix, int i) {
        int result = 0;
        for (int index = 0; index < matrix[i].length; index++) {
            result += matrix[i][index];
        }
        return result;
    }

    private static int colSum(int[][] matrix, int i) {
        int result = 0;
        for (int index = 0; index < matrix.length; index++) {
           result += matrix[index][i];
        }
        return result;
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int index = 0; index < matrix.length; index++) {
            Sums sum = new Sums();
            sum.setColSum(colSum(matrix, index));
            sum.setRowSum(rowSum(matrix, index));
            result[index] = sum;
        }
        return result;
    }

    private static CompletableFuture<Sums> getSum(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums();
            sum.setColSum(colSum(matrix, i));
            sum.setRowSum(rowSum(matrix, i));
            return sum;
        });
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int index = 0; index < matrix.length; index++) {
            result[index] = getSum(matrix, index).get();
        }
        return result;
    }
}
