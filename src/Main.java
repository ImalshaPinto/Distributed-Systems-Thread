public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Architecture C: Thread-per-Object Simulation");
        System.out.println("=====================================================\n");
        
        // 1. Create the single shared resource (The Game Board)
        GameBoard sharedBoard = new GameBoard();
        
        // 2. Create the Runnable tasks, passing the SAME board to both
        Player playerX = new Player(sharedBoard, 'X');
        Player playerO = new Player(sharedBoard, 'O');
        
        // 3. Create the Threads and explicitly name them for the console logs
        Thread threadX = new Thread(playerX, "Thread-PlayerX");
        Thread threadO = new Thread(playerO, "Thread-PlayerO");
        
        // 4. Start the threads
        threadX.start();
        threadO.start();
        
        // 5. Ensure the Main thread waits for the game to finish
        try {
            threadX.join(); // Main thread pauses until threadX finishes
            threadO.join(); // Main thread pauses until threadO finishes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }
        
        System.out.println("\nSimulation complete. Both threads terminated safely.");
    }
}