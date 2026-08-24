import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameBoard {
    private final char[][] board = new char[3][3];
    private char currentPlayerTurn = 'X'; // X always goes first
    private boolean isGameOver = false;
    
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public GameBoard() {
        // Initialize board with empty slots represented by '-'
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    // The synchronized keyword ensures Mutual Exclusion - only one thread can execute this at a time
    public synchronized boolean makeMove(int row, int col, char player) {
        // 1. Turn Coordination (Wait/Notify)
        while (currentPlayerTurn != player && !isGameOver) {
            try {
                logStatus("Waiting for turn...");
                wait(); // Thread pauses and releases the lock until notified
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logStatus("Thread was interrupted.");
                return false;
            }
        }

        // Deadlock Prevention: If the game ended while this thread was waiting, exit safely
        if (isGameOver) {
            return false;
        }

        // 2. Mutual Exclusion Check: Prevent overwriting a cell
        if (board[row][col] != '-') {
            logStatus("Attempted to move to occupied cell: " + row + "," + col + ". Retrying...");
            return false; // Cell is taken, player must try again (turn doesn't change)
        }

        // 3. Execute the move
        board[row][col] = player;
        logStatus("Placed '" + player + "' at [" + row + "," + col + "]");
        printBoard();

        // 4. Arbiter Check (Win/Draw)
        if (checkWin(player)) {
            logStatus("Player " + player + " WINS!");
            isGameOver = true;
        } else if (checkDraw()) {
            logStatus("Game ended in a DRAW!");
            isGameOver = true;
        } else {
            // Change turns only if the game is still going
            currentPlayerTurn = (currentPlayerTurn == 'X') ? 'O' : 'X';
        }

        // 5. Turn Coordination: Wake up the other waiting thread
        notifyAll(); 
        
        return true;
    }

    public synchronized boolean isGameOver() {
        return isGameOver;
    }

    // Check rows, columns, and both diagonals for three matching symbols.
    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player
                    || board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        return board[0][0] == player && board[1][1] == player && board[2][2] == player
                || board[0][2] == player && board[1][1] == player && board[2][0] == player;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') return false; // Found an empty spot
            }
        }
        return true;
    }

    // Helper to fulfill the mandatory timestamp and Thread ID logging requirement
    private void logStatus(String message) {
        String time = LocalTime.now().format(timeFormatter);
        String threadId = Thread.currentThread().getName();
        System.out.println("[" + time + "] [" + threadId + "] " + message);
    }

    private void printBoard() {
        System.out.println("\n  Current Board State");
        System.out.println("  ===================");
        
        // Loop through rows
        for (int i = 0; i < 3; i++) {
            System.out.print("    "); // Indent the board for better visibility
            // Loop through columns
            for (int j = 0; j < 3; j++) {
                // Print the cell content ('X', 'O', or '-')
                System.out.print(" " + board[i][j] + " ");
                
                // Add vertical separators, except for the last column
                if (j < 2) {
                    System.out.print("|");
                }
            }
            System.out.println(); // Move to the next line
            
            // Add horizontal separators between the two rows
            if (i < 2) {
                System.out.println("   ---+---+---");
            }
        }
        System.out.println("  ===================\n");
    }

    
}