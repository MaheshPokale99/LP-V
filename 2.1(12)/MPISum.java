import java.util.Scanner;

public class MPISum {
    public static void main(String[] args) throws InterruptedException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter number of processors: ");
            int processors = sc.nextInt();

            int[] numbers = new int[processors];
            System.out.println("Enter " + processors + " numbers:");
            for (int i = 0; i < processors; i++) {
                numbers[i] = sc.nextInt();
            }

            Worker[] workers = new Worker[processors];

            System.out.println("Root process distributing array elements...");
            for (int i = 0; i < processors; i++) {
                workers[i] = new Worker(i, numbers[i]);
                workers[i].start();
            }

            int finalSum = 0;
            for (int i = 0; i < processors; i++) {
                workers[i].join();
                finalSum = finalSum + workers[i].getIntermediateSum();
            }

            System.out.println("Final sum at root process: " + finalSum);
        }
    }

    static class Worker extends Thread {
        private final int processId;
        private final int value;
        private int intermediateSum;

        Worker(int processId, int value) {
            this.processId = processId;
            this.value = value;
        }

        @Override
        public void run() {
            intermediateSum = value;
            System.out.println("Process " + processId + " received " + value
                    + " and intermediate sum is " + intermediateSum);
        }

        int getIntermediateSum() {
            return intermediateSum;
        }
    }
}

/*
Real MPI library code using MPJ Express:

import mpi.*;

public class MPISum {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] sendBuffer = null;
        int[] recvBuffer = new int[1];

        if (rank == 0) {
            sendBuffer = new int[size];
            for (int i = 0; i < size; i++) {
                sendBuffer[i] = i + 1;
            }
        }

        MPI.COMM_WORLD.Scatter(sendBuffer, 0, 1, MPI.INT,
                recvBuffer, 0, 1, MPI.INT, 0);

        int localSum = recvBuffer[0];
        System.out.println("Process " + rank + " intermediate sum: " + localSum);

        int[] localSumBuffer = { localSum };
        int[] result = new int[1];

        MPI.COMM_WORLD.Reduce(localSumBuffer, 0,
                result, 0, 1, MPI.INT, MPI.SUM, 0);

        if (rank == 0) {
            System.out.println("Final sum at root process: " + result[0]);
        }

        MPI.Finalize();
    }
}
*/

/*
MPI SUM - CHECK/RUN/INPUT:
1. Check setup: java -version and javac -version.
2. Compile simulation in this folder: javac MPISum.java
3. Run simulation: java MPISum
4. Input number of processors.
5. Then input that many integers, one by one.
6. Output shows each process work and final sum at root.

MPJ EXPRESS LIBRARY RUN COMMANDS:
1. Install MPJ Express and set MPJ_HOME.
2. Replace the simulation code above with the "Real MPI library code" block.
3. Add MPJ jar while compiling:
   javac -cp "%MPJ_HOME%\lib\mpj.jar" MPISum.java
4. Run with 4 MPI processes:
   "%MPJ_HOME%\bin\mpjrun.bat" -np 4 MPISum
5. On Linux/Mac use $MPJ_HOME/lib/mpj.jar and $MPJ_HOME/bin/mpjrun.sh.
*/
