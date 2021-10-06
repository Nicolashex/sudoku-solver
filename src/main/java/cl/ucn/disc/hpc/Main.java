package cl.ucn.disc.hpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        //Define the number of tests per core made.
        final int numberOfIterations = 15;

        for (int n = minCores; n <= maxCores; n++) {


            List<Long> times = new ArrayList<>();
            for (int m = 1; m <= numberOfIterations; m++) {
                board.initializeSolvingBoard();
                long time = findSolutionsWithMultipleCores(n, board);
                times.add(time);

            }

            long min = Collections.min(times);
            long max = Collections.max(times);

            //Erase two non-characteristic values
            times.remove(min);
            times.remove(max);


            //Get the average with stream magic!
            double average = times.stream().mapToLong((x) -> x).average().getAsDouble();
            log.info("Average time with {} cores: {} nano sec. Max time: {} nano sec. Min time: {} nano sec.", n, average, max, min);

            //Print the board with the solution
            board.printBoard();


        }


    }

    private static long findSolutionsWithMultipleCores(int cores, Board board) throws InterruptedException {

        final ExecutorService executorService = Executors.newFixedThreadPool(cores);


        StopWatch sw = StopWatch.createStarted();
        executorService.submit(() -> {
            solveSudoku(board);

        });
        executorService.shutdown();
        long time = sw.getTime(TimeUnit.NANOSECONDS);
        int maxTime = 5;

        if (executorService.awaitTermination(maxTime, TimeUnit.MINUTES)) {
            //log.info("Founded a solution! with a time of {} ms:",time);
        } else {
            log.warn("The executor didn't finish in {} minutes", maxTime);
        }
        return time;


    }

    /**
     * Search for a solution for the sudoku board given.
     *
     * @param board the board requested for a solution
     * @return True if the sudoku has a solution. False otherwise
     */
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

    /**
     * Fill the board instance with a given board in a text file.
     *
     * @param file A file with the board.
     * @return The board filled with the positions given
     */
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
