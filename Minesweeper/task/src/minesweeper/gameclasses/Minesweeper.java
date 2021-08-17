package minesweeper.gameclasses;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Minesweeper {

    public static int n = 9;
    public static int mines = 0;
    public static int minesSwept = 0;

    public static String[][] gameGrid = new String[n][n];
    public static String[][] minesGrid = new String[n][n];
    public static Queue<Cell> cellQueue = new LinkedList<>();

    public static Scanner scanner = new Scanner(System.in);


    public static void openFreeCells(int i, int j) {

        cellQueue.add(new Cell(i, j));

        while (!cellQueue.isEmpty()) {

            Cell cell = cellQueue.poll();

            int y = cell.getI();
            int x = cell.getJ();

            int counter = minesAroundCount(y, x);

            if (counter == 0) {
                gameGrid[y][x] = "/";
                checkCellsAround(y, x);
            } else {
                gameGrid[y][x] = String.valueOf(counter);
            }
        }

    }

    public static void createGrid() {

        String line;

        do {
            System.out.println("How many mines do you want on the field?");
            line = scanner.nextLine();
        } while (!line.matches("[1-9][0-9]*"));

        mines = Integer.parseInt(line);
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

            System.out.println("Set/unset mines marks or claim a cell as free:");

            String line = scanner.nextLine();

            if (!line.matches("[1-9][ ][1-9][ ](free|mine)")) {
                System.out.println("Your command doesn't follow our requirements. For example:1 1 mine");
                continue;
            }

            String[] commands = line.split(" ");

            int x = Integer.parseInt(commands[0]);
            int y = Integer.parseInt(commands[1]);
            String command = commands[2];

            if (gameGrid[y - 1][x - 1].matches("[1-8]")) {
                System.out.println("There is a number here!");
                continue;
            } else if (gameGrid[y - 1][x - 1].matches("/")) {
                System.out.println("There is a open cell here!");
                continue;
            }

            if (command.equals("mine")) {

                if (gameGrid[y - 1][x - 1].equals("*")) {
                    gameGrid[y - 1][x - 1] = ".";
                    if (minesGrid[y - 1][x - 1].equals("X")) minesSwept--;
                } else {
                    gameGrid[y - 1][x - 1] = "*";
                    if (minesGrid[y - 1][x - 1].equals("X")) minesSwept++;
                }


            } else if (command.equals("free")) {

                if (gameGrid[y - 1][x - 1].equals("*")) {
                    System.out.println("There is a marked cell here!");
                    continue;
                }

                if (minesGrid[y - 1][x - 1].equals("X")) {

                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            if (minesGrid[i][j].equals("X")) {
                                gameGrid[i][j] = "X";
                            }
                        }
                    }

                    drawGrid();

                    System.out.println("You stepped on a mine and failed!");
                    break;
                } else {
                    openFreeCells(y - 1, x - 1);
                }

            }

            if (mines == minesSwept) {
                drawGrid();
                System.out.println("Congratulations! You found all mines!");
                break;
            }
        }
    }

    public static void checkCellsAround(int i, int j) {

        if (i == 0 && j == 0) {
            if (minesGrid[0][1].equals(".") && gameGrid[0][1].matches("([.]|[*])")) cellQueue.add(new Cell(0, 1));
            if (minesGrid[1][1].equals(".") && gameGrid[1][1].matches("([.]|[*])")) cellQueue.add(new Cell(1, 1));
            if (minesGrid[1][0].equals(".") && gameGrid[1][0].matches("([.]|[*])")) cellQueue.add(new Cell(1, 0));
        }

        if (i == 0 && j == n - 1) {
            if (minesGrid[0][j - 1].equals(".") && gameGrid[0][j - 1].matches("([.]|[*])")) cellQueue.add(new Cell(0, j - 1));
            if (minesGrid[1][j - 1].equals(".") && gameGrid[1][j - 1].matches("([.]|[*])")) cellQueue.add(new Cell(1, j - 1));
            if (minesGrid[1][j].equals(".") && gameGrid[1][j].matches("([.]|[*])")) cellQueue.add(new Cell(1, j));
        }

        if (i == n - 1 && j == 0) {
            if (minesGrid[i - 1][0].equals(".") && gameGrid[i - 1][0].matches("([.]|[*])")) cellQueue.add(new Cell(i - 1, 0));
            if (minesGrid[i - 1][1].equals(".") && gameGrid[i - 1][1].matches("([.]|[*])")) cellQueue.add(new Cell(i - 1, 1));
            if (minesGrid[i][1].equals(".") && gameGrid[i][1].matches("([.]|[*])")) cellQueue.add(new Cell(i, 1));
        }

        if (i == n - 1 && j == n - 1) {
            if (minesGrid[i][j - 1].equals(".") && gameGrid[i][j - 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j - 1));
            if (minesGrid[i - 1][j - 1].equals(".") && gameGrid[i - 1][j - 1].equals("."))
                cellQueue.add(new Cell(i - 1, j - 1));
            if (minesGrid[i - 1][j].equals(".") && gameGrid[i - 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i - 1, j));
        }

        // Checking sides

        if (i == 0 && j != 0 && j != n - 1) {
            if (minesGrid[i][j - 1].equals(".") && gameGrid[i][j - 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j - 1));
            if (minesGrid[i + 1][j - 1].equals(".") && gameGrid[i + 1][j - 1].equals("."))
                cellQueue.add(new Cell(i + 1, j - 1));
            if (minesGrid[i + 1][j].equals(".") && gameGrid[i + 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i + 1, j));
            if (minesGrid[i + 1][j + 1].equals(".") && gameGrid[i + 1][j + 1].equals("."))
                cellQueue.add(new Cell(i + 1, j + 1));
            if (minesGrid[i][j + 1].equals(".") && gameGrid[i][j + 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j + 1));
        }

        if (i == n - 1 && j != 0 && j != n - 1) {
            if (minesGrid[i][j - 1].equals(".") && gameGrid[i][j - 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j - 1));
            if (minesGrid[i - 1][j - 1].equals(".") && gameGrid[i - 1][j - 1].equals("."))
                cellQueue.add(new Cell(i - 1, j - 1));
            if (minesGrid[i - 1][j].equals(".") && gameGrid[i - 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i - 1, j));
            if (minesGrid[i - 1][j + 1].equals(".") && gameGrid[i - 1][j + 1].equals("."))
                cellQueue.add(new Cell(i - 1, j + 1));
            if (minesGrid[i][j + 1].equals(".") && gameGrid[i][j + 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j + 1));
        }

        if (i != 0 && i != n - 1 && j == 0) {
            if (minesGrid[i - 1][j].equals(".") && gameGrid[i - 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i - 1, j));
            if (minesGrid[i - 1][j + 1].equals(".") && gameGrid[i - 1][j + 1].equals("."))
                cellQueue.add(new Cell(i - 1, j + 1));
            if (minesGrid[i][j + 1].equals(".") && gameGrid[i][j + 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j + 1));
            if (minesGrid[i + 1][j + 1].equals(".") && gameGrid[i + 1][j + 1].equals("."))
                cellQueue.add(new Cell(i + 1, j + 1));
            if (minesGrid[i + 1][j].equals(".") && gameGrid[i + 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i + 1, j));
        }

        if (i != 0 && i != n - 1 && j == n - 1) {
            if (minesGrid[i + 1][j].equals(".") && gameGrid[i + 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i + 1, j));
            if (minesGrid[i + 1][j - 1].equals(".") && gameGrid[i + 1][j - 1].equals("."))
                cellQueue.add(new Cell(i + 1, j - 1));
            if (minesGrid[i][j - 1].equals(".") && gameGrid[i][j - 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j - 1));
            if (minesGrid[i - 1][j - 1].equals(".") && gameGrid[i - 1][j - 1].equals("."))
                cellQueue.add(new Cell(i - 1, j - 1));
            if (minesGrid[i - 1][j].equals(".") && gameGrid[i - 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i - 1, j));
        }

        // Checking standard cell

        if (i != 0 && i != n - 1 && j != 0 && j != n - 1) {
            if (minesGrid[i + 1][j - 1].equals(".") && gameGrid[i + 1][j - 1].equals("."))
                cellQueue.add(new Cell(i + 1, j - 1));
            if (minesGrid[i][j - 1].equals(".") && gameGrid[i][j - 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j - 1));
            if (minesGrid[i - 1][j - 1].equals(".") && gameGrid[i - 1][j - 1].equals("."))
                cellQueue.add(new Cell(i - 1, j - 1));
            if (minesGrid[i - 1][j].equals(".") && gameGrid[i - 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i - 1, j));
            if (minesGrid[i + 1][j].equals(".") && gameGrid[i + 1][j].matches("([.]|[*])")) cellQueue.add(new Cell(i + 1, j));
            if (minesGrid[i - 1][j + 1].equals(".") && gameGrid[i - 1][j + 1].equals("."))
                cellQueue.add(new Cell(i - 1, j + 1));
            if (minesGrid[i][j + 1].equals(".") && gameGrid[i][j + 1].matches("([.]|[*])")) cellQueue.add(new Cell(i, j + 1));
            if (minesGrid[i + 1][j + 1].equals(".") && gameGrid[i + 1][j + 1].equals("."))
                cellQueue.add(new Cell(i + 1, j + 1));
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

    /* Used for debugging purposes */
    public static void drawMinesGrid() {

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

                System.out.print(minesGrid[i][j]);

            }

            System.out.println("|");
        }

        System.out.print("-|");
        for (int i = 1; i <= n; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }
}
