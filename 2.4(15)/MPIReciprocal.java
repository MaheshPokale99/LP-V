import java.util.Scanner;

public class MPIReciprocal {
    public static void main(String[] args) throws InterruptedException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter number of processors: ");
            int processors = sc.nextInt();

            double[] numbers = new double[processors];
            System.out.println("Enter " + processors + " numbers:");
            for (int i = 0; i < processors; i++) {
                numbers[i] = sc.nextDouble();
            }

            Worker[] workers = new Worker[processors];

            System.out.println("Root process distributing array elements...");
            for (int i = 0; i < processors; i++) {
                workers[i] = new Worker(i, numbers[i]);
                workers[i].start();
            }

            double[] result = new double[processors];
            for (int i = 0; i < processors; i++) {
                workers[i].join();
                result[i] = workers[i].getReciprocal();
            }

            System.out.println("Resultant reciprocal array at root process:");
            for (int i = 0; i < processors; i++) {
                System.out.print(result[i] + " ");
            }
            System.out.println();
        }
    }

    static class Worker extends Thread {
        private final int processId;
        private final double value;
        private double reciprocal;

        Worker(int processId, double value) {
            this.processId = processId;
            this.value = value;
        }

        @Override
        public void run() {
            if (value == 0) {
                reciprocal = 0;
                System.out.println("Process " + processId + " received 0, reciprocal not possible");
            } else {
                reciprocal = 1 / value;
                System.out.println("Process " + processId + " received " + value
                        + " and reciprocal is " + reciprocal);
            }
        }

        double getReciprocal() {
            return reciprocal;
        }
    }
}

/*
Real MPI library code using MPJ Express:

import mpi.*;

public class MPIReciprocal {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        double[] sendBuffer = null;
        double[] recvBuffer = new double[1];

        if (rank == 0) {
            sendBuffer = new double[size];
            for (int i = 0; i < size; i++) {
                sendBuffer[i] = i + 1;
            }
        }

        MPI.COMM_WORLD.Scatter(sendBuffer, 0, 1, MPI.DOUBLE,
                recvBuffer, 0, 1, MPI.DOUBLE, 0);

        double reciprocal = 1 / recvBuffer[0];
        System.out.println("Process " + rank + " reciprocal: " + reciprocal);

        double[] result = null;
        if (rank == 0) {
            result = new double[size];
        }

        double[] reciprocalBuffer = { reciprocal };

        MPI.COMM_WORLD.Gather(reciprocalBuffer, 0, 1, MPI.DOUBLE,
                result, 0, 1, MPI.DOUBLE, 0);

        if (rank == 0) {
            System.out.println("Resultant reciprocal array at root process:");
            for (double value : result) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        MPI.Finalize();
    }
}
*/
