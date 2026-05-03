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
