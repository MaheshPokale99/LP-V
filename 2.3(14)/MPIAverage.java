import java.util.Random;
import java.util.Scanner;

public class MPIAverage {
    public static void main(String[] args) throws InterruptedException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter number of processors: ");
            int processors = sc.nextInt();

            System.out.print("Enter elements per processor: ");
            int elementsPerProcessor = sc.nextInt();

            int totalElements = processors * elementsPerProcessor;
            int[] numbers = new int[totalElements];
            Random random = new Random(10);

            System.out.println("Random array generated at root process:");
            for (int i = 0; i < totalElements; i++) {
                numbers[i] = random.nextInt(50) + 1;
                System.out.print(numbers[i] + " ");
            }
            System.out.println();

            Worker[] workers = new Worker[processors];

            System.out.println("Root process scattering equal parts to workers...");
            for (int i = 0; i < processors; i++) {
                int[] part = new int[elementsPerProcessor];
                for (int j = 0; j < elementsPerProcessor; j++) {
                    part[j] = numbers[(i * elementsPerProcessor) + j];
                }

                workers[i] = new Worker(i, part);
                workers[i].start();
            }

            double averageSum = 0;
            for (int i = 0; i < processors; i++) {
                workers[i].join();
                averageSum = averageSum + workers[i].getAverage();
            }

            double finalAverage = averageSum / processors;
            System.out.println("Final average at root process: " + finalAverage);
        }
    }

    static class Worker extends Thread {
        private final int processId;
        private final int[] values;
        private double average;

        Worker(int processId, int[] values) {
            this.processId = processId;
            this.values = values;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int value : values) {
                sum = sum + value;
            }

            average = (double) sum / values.length;
            System.out.println("Process " + processId + " computed local average: " + average);
        }

        double getAverage() {
            return average;
        }
    }
}

/*
Real MPI library code using MPJ Express:

import mpi.*;
import java.util.Random;

public class MPIAverage {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int elementsPerProcess = 2;
        int totalElements = size * elementsPerProcess;

        int[] sendBuffer = null;
        int[] recvBuffer = new int[elementsPerProcess];

        if (rank == 0) {
            sendBuffer = new int[totalElements];
            Random random = new Random(10);

            System.out.println("Random array at root process:");
            for (int i = 0; i < totalElements; i++) {
                sendBuffer[i] = random.nextInt(50) + 1;
                System.out.print(sendBuffer[i] + " ");
            }
            System.out.println();
        }

        MPI.COMM_WORLD.Scatter(sendBuffer, 0, elementsPerProcess, MPI.INT,
                recvBuffer, 0, elementsPerProcess, MPI.INT, 0);

        int localSum = 0;
        for (int value : recvBuffer) {
            localSum = localSum + value;
        }

        double localAverage = (double) localSum / elementsPerProcess;
        System.out.println("Process " + rank + " local average: " + localAverage);

        double[] allAverages = null;
        if (rank == 0) {
            allAverages = new double[size];
        }

        double[] localAvgBuffer = { localAverage };

        MPI.COMM_WORLD.Gather(localAvgBuffer, 0, 1, MPI.DOUBLE,
                allAverages, 0, 1, MPI.DOUBLE, 0);

        if (rank == 0) {
            double averageSum = 0;
            for (double average : allAverages) {
                averageSum = averageSum + average;
            }

            double finalAverage = averageSum / size;
            System.out.println("Final average at root process: " + finalAverage);
        }

        MPI.Finalize();
    }
}
*/
