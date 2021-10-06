package cl.ucn.disc.hpc;

import java.util.Arrays;

/**
 * A class that represents a board of sudoku. With all the methods
 * needed for the algorithm to solve it.
 */
public class Board {

    private final int size;
    private final int boxSize;
    private int[][] board;
    private int[][] defaultBoard;



    public Board(int size, int boxSize, int[][] startingBoard) {
        this.size = size;
        this.boxSize = boxSize;
        this.defaultBoard = startingBoard;
        this.board = new int[size][size];

    }

    /**
     * Checks if a value is repeated in a given row
     *
     * @param row   the index of the row to check
     * @param value the value that is going to be put on that row.
     * @return True if the value is already on the row. False otherwise.
     */
    public boolean checkRow(int row, int value) {

        //Goes through all the row
        for (int col = 0; col < this.size; col++) {
            //Checks if the value is repeated
            if (board[row][col] == value) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a value is repeated in a given column
     *
     * @param col   the index of the column to check
     * @param value the value that is going to be put in that column
     * @return True if the value is already on the column. False otherwise
     */
    public boolean checkColumn(int col, int value) {

        //Goes through all the columm
        for (int row = 0; row < size; row++) {
            //Checks if the value is repeated
            if (board[row][col] == value) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a value can be putted on a cell based of the values that are already in the
     * same box of the sudoku board.
     *
     * @param row   the row of the cell where the value is going to be put in the board.
     * @param col   the column of the cell where the value is going to be put in the board.
     * @param value the value to be put in the board.
     * @return True if the value is repeated in the same box of the sudoku. False otherwise.
     */
    public boolean checkBox(int row, int col, int value) {
        int startingBoxRow = row - row % this.boxSize;
        int startingBoxColumn = col - col % this.boxSize;

        for (int i = startingBoxRow; i < startingBoxRow + this.boxSize; i++) {
            for (int j = startingBoxColumn; j < startingBoxColumn + this.boxSize; j++) {
                if (this.board[i][j] == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if a value can be put on a given cell.
     *
     * @param value value to be put.
     * @param row   row of the cell.
     * @param col   column of the cell.
     * @return True if the value can be put on that cell, false otherwise.
     */
    public final boolean isValidPlacement(int value, int row, int col) {
        return !checkColumn(col, value) &&
                !checkRow(row, value) &&
                !checkBox(row, col, value);
    }

    /**
     * Checks if the board is completed or not.
     *
     * @return True if the board is completed, meaning there's no empty cells. False in the other case.
     */
    public final boolean isCompleted() {
        //Goes through all the cells in the board
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                // Checks if there's not an empty cell
                if (board[col][row] != 0) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Get the size of the board
     *
     * @return Size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Print the board with the actual values in their position.
     */
    public void printBoard() {
        // A counter for the rows
        int countRow = 0;


        for (int[] row : board) {
            // A counter for the columns
            int countCol = 0;

            System.out.print("|");
            for (int value : row) {
                System.out.print(" " + value + " ");
                if ((countCol + 1) % boxSize == 0) {
                    System.out.print("|");
                }
                countCol++;
            }

            System.out.println();
            if ((countRow + 1) % boxSize == 0) {
                System.out.println("");
            }
            countRow++;
        }
    }

    public boolean isEmpty(int row, int column) {
        return board[row][column]==0;
    }

    public void fillWithValue(int testingValue, int row, int column) {
        this.board[row][column]=testingValue;
    }

    public void eraseValue(int row, int column) {
        this.board[row][column]=0;
    }
    public void initializeSolvingBoard(){
        for(int i=0; i<this.size; i++)
            for(int j=0; j<defaultBoard[i].length; j++)
                board[i][j]=defaultBoard[i][j];

    }


}
