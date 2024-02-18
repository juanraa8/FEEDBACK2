import java.util.Random;

public class Productor implements Runnable {
    private int id;
    private int numOperaciones;
    private BufferCompartido sharedBuffer;

    public Productor(int id, int numOperaciones, BufferCompartido sharedBuffer) {
        this.id = id;
        this.numOperaciones = numOperaciones;
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < numOperaciones; i++) {
            Bola bola = new Bola(id, i);
            try {
                sharedBuffer.almacenar(bola);
                System.out.println("Productor " + id + " almacenó la bola " + i);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.err.println("Productor " + id + " interrumpido: " + e);
            }
        }
    }
}
