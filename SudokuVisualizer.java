
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SudokuVisualizer extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private SudokuSolver solver;

    public SudokuVisualizer() {
        setTitle("Sudoku Solver Visualizer");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 20));
                gridPanel.add(cells[row][col]);
            }
        }

        solver = new SudokuSolver(this);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(e -> {
            int[][] board = getBoardFromCells();
            new Thread(() -> {
                if (solver.solve(board)) {
                    updateCellsFromBoard(board);
                } else {
                    JOptionPane.showMessageDialog(this, "No solution exists!");
                }
            }).start();
        });

        JButton generateButton = new JButton("Generate Puzzle");
        generateButton.addActionListener(e -> generatePuzzle());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearBoard());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);

        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private int[][] getBoardFromCells() {
        int[][] board = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                if (text.isEmpty()) {
                    board[row][col] = 0;
                } else {
                    board[row][col] = Integer.parseInt(text);
                }
            }
        }
        return board;
    }

    private void updateCellsFromBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText(board[row][col] == 0 ? "" : String.valueOf(board[row][col]));
            }
        }
    }

    public void updateCell(int row, int col, int value) {
        SwingUtilities.invokeLater(() -> cells[row][col].setText(value == 0 ? "" : String.valueOf(value)));
    }

    private void generatePuzzle() {
        Random random = new Random();
        int[][] board = new int[SIZE][SIZE];
        clearBoard();

        for (int i = 0; i < SIZE * 3; i++) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            int num = random.nextInt(SIZE) + 1;

            if (solver.isValid(board, row, col, num)) {
                board[row][col] = num;
                cells[row][col].setText(String.valueOf(num));
            }
        }
    }

    private void clearBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText("");
            }
        }
    }

    public static void main(String[] args) {
        new SudokuVisualizer();
    }
}
