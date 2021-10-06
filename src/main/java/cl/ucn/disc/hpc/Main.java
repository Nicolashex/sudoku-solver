package cl.ucn.disc.hpc;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main Class.
 */

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {

        //Get the number of cores plus one
        final int maxCores = Runtime.getRuntime().availableProcessors() + 1;
        //Set the minimum amount of cores used
        final int minCores = 1;

        //Get the text file
        File file = new File("sudoku.txt");


        Board board = readBoard(file);
        board.printBoard();
        if(solveSudoku(board)){
            System.out.println("Solved Succesfully");
            board.printBoard();
        }
        else{
            System.out.println("Cannot solve the board given");
        }
    }

    private static boolean solveSudoku(Board board) {
        int size = board.getSize();
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (board.isEmpty(row, column)) {
                    for (int testingValue = 1; testingValue <= size; testingValue++) {
                        if (board.isValidPlacement(testingValue, row, column)) {
                            board.fillWithValue(testingValue, row, column);
                            if (solveSudoku(board)) {
                                return true;
                            } else {
                                board.eraseValue(row, column);
                            }
                        }

                    }
                    return false;

                }
            }


        }
        return true;
    }

    private static Board readBoard(File file) {

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String currentLine;
            String size = br.readLine();

            int boardSize = Integer.parseInt(size);
            double boxSize = Math.sqrt(boardSize);

            if (boxSize % 1 != 0) {
                throw new IllegalArgumentException("The size of the sudoku is invalid, it should be a" +
                        "square number");
            }

            ArrayList<String> entries = new ArrayList<>();

            //Read until there's no lines left
            while ((currentLine = br.readLine()) != null) {

                //To erase space at the start of the line
                currentLine.trim().split("\\s+");
                // Separates the string into multiple instances when there's a space.
                String[] rowNumbers = currentLine.split(" ");

                entries.addAll(Arrays.asList(rowNumbers));

            }

            // Create a matrix with the given size
            int[][] outputBoard = new int[boardSize][boardSize];

            int acc = 0;
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    outputBoard[i][j] = Integer.parseInt(entries.get(acc++));
                }
            }
            Board board = new Board(boardSize, (int) boxSize, outputBoard);

            return board;

        } catch (Exception e) {
            e.getMessage();
        }

        return null;
    }
}
