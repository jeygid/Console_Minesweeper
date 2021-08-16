package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("How many mines do you want on the field?");
        Scanner scanner = new Scanner(System.in);
        int minesCounter = scanner.nextInt();

        int n = 9;

        String[][] grid = new String[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                String cell = ".";

                double rand = Math.random();

                if (rand >= 0.3 && minesCounter > 0) {
                    cell = "X";
                    minesCounter--;
                }

                grid[i][j] = cell;

            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int count = 0;

                if (!grid[i][j].equals("X")) {

                    // Checking angles

                    if (i == 0 && j == 0) {
                        if (grid[0][1].equals("X")) count++;
                        if (grid[1][1].equals("X")) count++;
                        if (grid[1][0].equals("X")) count++;
                    }

                    if (i == 0 && j == n - 1) {
                        if (grid[0][j - 1].equals("X")) count++;
                        if (grid[1][j - 1].equals("X")) count++;
                        if (grid[1][j].equals("X")) count++;
                    }

                    if (i == n - 1 && j == 0) {
                        if (grid[i - 1][0].equals("X")) count++;
                        if (grid[i - 1][1].equals("X")) count++;
                        if (grid[i][1].equals("X")) count++;
                    }

                    if (i == n - 1 && j == n - 1) {
                        if (grid[i][j - 1].equals("X")) count++;
                        if (grid[i - 1][j - 1].equals("X")) count++;
                        if (grid[i - 1][j].equals("X")) count++;
                    }

                    // Checking sides

                    if (i == 0 && j != 0 && j != n - 1) {
                        if (grid[i][j - 1].equals("X")) count++;
                        if (grid[i + 1][j - 1].equals("X")) count++;
                        if (grid[i + 1][j].equals("X")) count++;
                        if (grid[i + 1][j + 1].equals("X")) count++;
                        if (grid[i][j + 1].equals("X")) count++;
                    }

                    if (i == n - 1 && j != 0 && j != n - 1) {
                        if (grid[i][j - 1].equals("X")) count++;
                        if (grid[i - 1][j - 1].equals("X")) count++;
                        if (grid[i - 1][j].equals("X")) count++;
                        if (grid[i - 1][j + 1].equals("X")) count++;
                        if (grid[i][j + 1].equals("X")) count++;
                    }

                    if (i != 0 && i != n - 1 && j == 0) {
                        if (grid[i - 1][j].equals("X")) count++;
                        if (grid[i - 1][j + 1].equals("X")) count++;
                        if (grid[i][j + 1].equals("X")) count++;
                        if (grid[i + 1][j + 1].equals("X")) count++;
                        if (grid[i + 1][j].equals("X")) count++;
                    }

                    if (i != 0 && i != n - 1 && j == n - 1) {
                        if (grid[i + 1][j].equals("X")) count++;
                        if (grid[i + 1][j - 1].equals("X")) count++;
                        if (grid[i][j - 1].equals("X")) count++;
                        if (grid[i - 1][j - 1].equals("X")) count++;
                        if (grid[i - 1][j].equals("X")) count++;
                    }

                    // Checking standard cell

                    if (i != 0 && i != n - 1 && j != 0 && j != n - 1) {
                        if (grid[i + 1][j - 1].equals("X")) count++;
                        if (grid[i][j - 1].equals("X")) count++;
                        if (grid[i - 1][j - 1].equals("X")) count++;
                        if (grid[i - 1][j].equals("X")) count++;
                        if (grid[i + 1][j].equals("X")) count++;
                        if (grid[i - 1][j + 1].equals("X")) count++;
                        if (grid[i][j + 1].equals("X")) count++;
                        if (grid[i + 1][j + 1].equals("X")) count++;
                    }

                }

                if (count > 0) grid[i][j] = String.valueOf(count);

                System.out.print(grid[i][j]);

            }

            System.out.println();
        }



    }
}
