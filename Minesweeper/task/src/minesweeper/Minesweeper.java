package minesweeper;

import java.util.Scanner;

public class Minesweeper {

    public static int n = 9;
    public static int mines = 0;
    public static int minesSwept = 0;
    public static String[][] gameGrid = new String[n][n];
    public static String[][] minesGrid = new String[n][n];
    public static Scanner scanner = new Scanner(System.in);

    public static void createGrid() {

        System.out.println("How many mines do you want on the field?");

        mines = scanner.nextInt();
        int minesCounter = mines;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                String cell = ".";
                gameGrid[i][j] = cell;
                double rand = Math.random();

                if (rand >= 0.85 && minesCounter > 0) {
                    cell = "X";
                    minesCounter--;
                }

                minesGrid[i][j] = cell;

            }
        }
    }

    public static void drawGrid() {

        System.out.print(" |");
        for (int i = 1; i <= n; i++) {
            System.out.print(i);
        }
        System.out.println("|");
        

        System.out.print("-|");
        for (int i = 1; i <= n; i++) {
            System.out.print("-");
        }
        System.out.println("|");

        for (int i = 0; i < n; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < n; j++) {

                int count = 0;

                if (!minesGrid[i][j].equals("X")) {
                    count = Minesweeper.minesAroundCount(i,j);
                }

                if (count > 0) {
                    gameGrid[i][j] = String.valueOf(count);
                }

                System.out.print(gameGrid[i][j]);
                
            }

            System.out.println("|");
        }

        System.out.print("-|");
        for (int i = 1; i <= n; i++) {
            System.out.print("-");
        }
        System.out.println("|");

    }

    public static void play() {

        while (true) {

            drawGrid();

            while (true) {
                System.out.println("Set/delete mines marks (x and y coordinates):");

                int x = scanner.nextInt();
                int y = scanner.nextInt();

                if (gameGrid[y - 1][x - 1].matches("[1-9]")) {
                    System.out.println("There is a number here!");
                } else {
                    if (gameGrid[y - 1][x - 1].equals("*")) {
                        gameGrid[y - 1][x - 1] = ".";
                        if (minesGrid[y - 1][x - 1].equals("X")) minesSwept--;
                    } else {
                        gameGrid[y - 1][x - 1] = "*";
                        if (minesGrid[y - 1][x - 1].equals("X")) minesSwept++;
                    }
                    break;
                }
            }

            if (mines == minesSwept) {
                System.out.println("Congratulations! You found all mines!");
                break;
            }


        }
    }

    public static int minesAroundCount(int i, int j) {

        int count = 0;

        // Checking angles

        if (i == 0 && j == 0) {
            if (minesGrid[0][1].equals("X")) count++;
            if (minesGrid[1][1].equals("X")) count++;
            if (minesGrid[1][0].equals("X")) count++;
        }

        if (i == 0 && j == n - 1) {
            if (minesGrid[0][j - 1].equals("X")) count++;
            if (minesGrid[1][j - 1].equals("X")) count++;
            if (minesGrid[1][j].equals("X")) count++;
        }

        if (i == n - 1 && j == 0) {
            if (minesGrid[i - 1][0].equals("X")) count++;
            if (minesGrid[i - 1][1].equals("X")) count++;
            if (minesGrid[i][1].equals("X")) count++;
        }

        if (i == n - 1 && j == n - 1) {
            if (minesGrid[i][j - 1].equals("X")) count++;
            if (minesGrid[i - 1][j - 1].equals("X")) count++;
            if (minesGrid[i - 1][j].equals("X")) count++;
        }

        // Checking sides

        if (i == 0 && j != 0 && j != n - 1) {
            if (minesGrid[i][j - 1].equals("X")) count++;
            if (minesGrid[i + 1][j - 1].equals("X")) count++;
            if (minesGrid[i + 1][j].equals("X")) count++;
            if (minesGrid[i + 1][j + 1].equals("X")) count++;
            if (minesGrid[i][j + 1].equals("X")) count++;
        }

        if (i == n - 1 && j != 0 && j != n - 1) {
            if (minesGrid[i][j - 1].equals("X")) count++;
            if (minesGrid[i - 1][j - 1].equals("X")) count++;
            if (minesGrid[i - 1][j].equals("X")) count++;
            if (minesGrid[i - 1][j + 1].equals("X")) count++;
            if (minesGrid[i][j + 1].equals("X")) count++;
        }

        if (i != 0 && i != n - 1 && j == 0) {
            if (minesGrid[i - 1][j].equals("X")) count++;
            if (minesGrid[i - 1][j + 1].equals("X")) count++;
            if (minesGrid[i][j + 1].equals("X")) count++;
            if (minesGrid[i + 1][j + 1].equals("X")) count++;
            if (minesGrid[i + 1][j].equals("X")) count++;
        }

        if (i != 0 && i != n - 1 && j == n - 1) {
            if (minesGrid[i + 1][j].equals("X")) count++;
            if (minesGrid[i + 1][j - 1].equals("X")) count++;
            if (minesGrid[i][j - 1].equals("X")) count++;
            if (minesGrid[i - 1][j - 1].equals("X")) count++;
            if (minesGrid[i - 1][j].equals("X")) count++;
        }

        // Checking standard cell

        if (i != 0 && i != n - 1 && j != 0 && j != n - 1) {
            if (minesGrid[i + 1][j - 1].equals("X")) count++;
            if (minesGrid[i][j - 1].equals("X")) count++;
            if (minesGrid[i - 1][j - 1].equals("X")) count++;
            if (minesGrid[i - 1][j].equals("X")) count++;
            if (minesGrid[i + 1][j].equals("X")) count++;
            if (minesGrid[i - 1][j + 1].equals("X")) count++;
            if (minesGrid[i][j + 1].equals("X")) count++;
            if (minesGrid[i + 1][j + 1].equals("X")) count++;
        }

        return count;
    }
}
