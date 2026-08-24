# Distributed Systems: Multithreading Architecture Simulator


## 📌 Project Overview (Assignment)
This repository contains a Java-based simulation of the **Thread-per-Object** distributed system architecture. The project strictly focuses on Operating System multithreading concepts, including Concurrency, Synchronization, Mutual Exclusion, and Resource Sharing, without relying on network sockets or external databases.

### Selected Architecture: Architecture C (Thread-per-Object)
**Scenario:** A turn-based multiplayer game (Tic-Tac-Toe) on a shared 3x3 grid.
**Model:** One thread per player (`Player X` and `Player O`) interacting concurrently with a single, shared `GameBoard` object.

---

## ⚙️ Core Technical Achievements

This implementation successfully addresses the assignment's mandatory challenges:

1. **Turn Coordination (Wait/Notify):** Utilizes standard Java `wait()` and `notifyAll()` mechanisms to ensure Player X and Player O strictly alternate turns without busy-waiting.
2. **Mutual Exclusion:** Implements the `synchronized` keyword to protect the shared 3x3 array, guaranteeing that two threads cannot write to the same grid cell simultaneously.
3. **Deadlock Prevention:** Incorporates state checks (`isGameOver`) immediately after thread wake-ups to ensure threads do not wait indefinitely once the arbiter declares a win or draw.
4. **Thread-Safe Logging:** Console outputs include synchronized timestamps and explicit Thread IDs for accurate execution tracing.

---

## 🚀 How to Run the Simulation

**Prerequisites:** 
* Java SE 17 or higher
* No external libraries or GUI required (Console-only execution).

**Execution via Terminal:**
1. Clone the repository:
   ```bash
   git clone [https://github.com/your-username/DS-Assignment01-Multithreading.git](https://github.com/your-username/DS-Assignment01-Multithreading.git)
   cd DS-Assignment01-Multithreading/src