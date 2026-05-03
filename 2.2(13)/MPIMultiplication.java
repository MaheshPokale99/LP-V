import java.util.Scanner;

public class MPIMultiplication {
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

            int finalMultiplication = 1;
            for (int i = 0; i < processors; i++) {
                workers[i].join();
                finalMultiplication = finalMultiplication * workers[i].getIntermediateMultiplication();
            }

            System.out.println("Final multiplication at root process: " + finalMultiplication);
        }
    }

    static class Worker extends Thread {
        private final int processId;
        private final int value;
        private int intermediateMultiplication;

        Worker(int processId, int value) {
            this.processId = processId;
            this.value = value;
        }

        @Override
        public void run() {
            intermediateMultiplication = value;
            System.out.println("Process " + processId + " received " + value
                    + " and intermediate multiplication is " + intermediateMultiplication);
        }

        int getIntermediateMultiplication() {
            return intermediateMultiplication;
        }
    }
}

/*
Real MPI library code using MPJ Express:

import mpi.*;

public class MPIMultiplication {
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

        int localMultiplication = recvBuffer[0];
        System.out.println("Process " + rank + " intermediate multiplication: "
                + localMultiplication);

        int[] localMultiplicationBuffer = { localMultiplication };
        int[] result = new int[1];

        MPI.COMM_WORLD.Reduce(localMultiplicationBuffer, 0,
                result, 0, 1, MPI.INT, MPI.PROD, 0);

        if (rank == 0) {
            System.out.println("Final multiplication at root process: " + result[0]);
        }

        MPI.Finalize();
    }
}
*/
