import java.util.Random;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Player implements Runnable {
    private final GameBoard board;
    private final char symbol;
    private final Random random = new Random();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    // The shared board is passed via the constructor
    public Player(GameBoard board, char symbol) {
        this.board = board;
        this.symbol = symbol;
    }

    @Override
    public void run() {
        logStatus("Joined the game and ready to play.");
        
        // Keep attempting moves until the Arbiter declares a win or draw
        while (!board.isGameOver()) {
            int row = random.nextInt(2); // Random row: 0 or 1
            int col = random.nextInt(3); // Random col: 0, 1, or 2

            // Attempt to make a move on the shared board
            boolean moveSuccessful = board.makeMove(row, col, symbol);
            
            if (moveSuccessful) {
                try {
                    // Sleep to simulate "thinking" time and make the console output readable
                    Thread.sleep(800); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logStatus("Thread was interrupted.");
                }
            } else if (!board.isGameOver()) {
                // If the move failed (e.g., cell was already taken), wait briefly before retrying
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        logStatus("Game over detected. Exiting thread gracefully.");
    }

    // Helper to fulfill the mandatory timestamp and Thread ID logging requirement
    private void logStatus(String message) {
        String time = LocalTime.now().format(timeFormatter);
        String threadId = Thread.currentThread().getName();
        System.out.println("[" + time + "] [" + threadId + "] " + message);
    }
}