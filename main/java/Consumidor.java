import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumidor implements Runnable {
    private static final Logger logger = Logger.getLogger(Consumidor.class.getName());
    private int id;
    private BufferCompartido sharedBuffer;

    public Consumidor(int id, BufferCompartido sharedBuffer) {
        this.id = id;
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (sharedBuffer.estaActivo()) { // Verificar la variable de control
            try {
                Bola bola = sharedBuffer.extraer();
                if (bola != null) {
                    System.out.println("Consumidor " + id + " extrajo la bola " + bola.getNum());
                    Thread.sleep(random.nextInt(1000));
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Consumidor " + id + " interrumpido: " + e);
                Thread.currentThread().interrupt(); // Importante para manejar la interrupción correctamente
            }
        }
    }
}