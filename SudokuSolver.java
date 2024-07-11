

public class SudokuSolver {
    private static final int SIZE = 9;
    private SudokuVisualizer visualizer;

    public SudokuSolver(SudokuVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            visualizer.updateCell(row, col, num);
                            try { Thread.sleep(50); } catch (InterruptedException ignored) { }

                            if (solve(board)) {
                                return true;
                            } else {
                                board[row][col] = 0;
                                visualizer.updateCell(row, col, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num || 
                board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }
}
