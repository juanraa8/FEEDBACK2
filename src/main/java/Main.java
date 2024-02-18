import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("logs.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (java.io.IOException e) {
            System.err.println("Failed to setup logger handler: " + e);
        }
    }

    public static void main(String[] args) {
        setupLogger();

        logger.info("Application started.");

        int bufferCapacity = 5;
        int numProducers = 3;
        int numConsumers = 2;

        BufferCompartido sharedBuffer = new BufferCompartido(bufferCapacity);
        ExecutorService executor = Executors.newCachedThreadPool();

        // Iniciar productores
        for (int i = 0; i < numProducers; i++) {
            executor.execute(new Productor(i, 10, sharedBuffer));
        }

        // Iniciar consumidores
        for (int i = 0; i < numConsumers; i++) {
            executor.execute(new Consumidor(i, sharedBuffer));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Presiona enter para terminar...");
        scanner.nextLine();

        sharedBuffer.setTerminar(true); // Indica a los consumidores que terminen

        executor.shutdownNow(); // Intenta detener todos los hilos activos

        logger.info("Application ended.");
    }
}
