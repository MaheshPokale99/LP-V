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

/*
MPI MULTIPLICATION - CHECK/RUN/INPUT:
1. Check setup: java -version and javac -version.
2. Compile simulation in this folder: javac MPIMultiplication.java
3. Run simulation: java MPIMultiplication
4. Input number of processors.
5. Then input that many integers, one by one.
6. Output shows each process work and final multiplication at root.

MPJ EXPRESS LIBRARY RUN COMMANDS:
1. Install MPJ Express and set MPJ_HOME.
2. Replace the simulation code above with the "Real MPI library code" block.
3. Add MPJ jar while compiling:
   javac -cp "%MPJ_HOME%\lib\mpj.jar" MPIMultiplication.java
4. Run with 4 MPI processes:
   "%MPJ_HOME%\bin\mpjrun.bat" -np 4 MPIMultiplication
5. On Linux/Mac use $MPJ_HOME/lib/mpj.jar and $MPJ_HOME/bin/mpjrun.sh.
*/

/* 

====================================================
🔹 SETUP STEPS (RUN IN TERMINAL - LINUX)
====================================================

# Create working folder
mkdir mpi-work
cd mpi-work

# Install Java
sudo apt update
sudo apt install openjdk-8-jdk -y

# Download MPJ Express
wget https://downloads.sourceforge.net/project/mpjexpress/mpj-v0_44.tar.gz

# Extract MPJ
tar -xvf mpj-v0_44.tar.gz
mv mpj-v0_44 mpj

# Set environment variables
echo 'export MPJ_HOME=$HOME/mpi-work/mpj' >> ~/.bashrc
echo 'export PATH=$MPJ_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc

# Verify installation
mpjrun.sh -help

====================================================
🔹 HOW TO RUN THIS PROGRAM
====================================================

# Compile (IMPORTANT: include mpj.jar)
javac -cp .:$MPJ_HOME/lib/mpj.jar MPIMultiplication.java

# Run with 4 processes
mpjrun.sh -np 4 MPIMultiplication

====================================================
🔹 MPI CONCEPTS USED
====================================================

rank → process ID (0,1,2,3)
size → total processes

Process 0 = ROOT
Others = WORKERS

Flow:
1. Root creates data
2. Scatter → distribute data
3. Each process computes
4. Reduce → combine result

====================================================
🔹 COMMON ERRORS
====================================================

Error: mpi package not found
→ Fix: use -cp .:$MPJ_HOME/lib/mpj.jar

Error: mpjrun.sh not found
→ Fix: source ~/.bashrc

Error: nothing runs
→ Check: echo $MPJ_HOME

====================================================
*/
