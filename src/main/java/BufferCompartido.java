import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class BufferCompartido {
    private BlockingQueue<Bola> buffer;
    private AtomicBoolean activo = new AtomicBoolean(true); //Variable de control

    public BufferCompartido(int bufferCapacity) {
        buffer = new LinkedBlockingQueue<>(bufferCapacity);
    }

    public void almacenar(Bola bola) throws InterruptedException {
        buffer.put(bola);
    }

    public Bola extraer() throws InterruptedException {
        return buffer.take();
    }

    // Método para detener el consumo
    public void setTerminar(boolean b) {
        activo.set(false);
    }

    // Verificar si el buffer está activo
    public boolean estaActivo() {
        return activo.get();
    }
}
