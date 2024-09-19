import java.awt.*;
import javax.swing.*;

public class Productor extends Thread {
    private final Almacen almacen;
    private final JLabel productorEstado;
    private final JPanel productorPanel;
    private boolean running = true;

    public Productor(Almacen almacen, JLabel productorEstado, JPanel productorPanel) {
        this.almacen = almacen;
        this.productorEstado = productorEstado;
        this.productorPanel = productorPanel;
    }

    public void detener() {
        running = false;  // Detener el hilo
    }

    private void producir(int item) throws InterruptedException {
        productorEstado.setText("Productor: Produciendo...");
        productorPanel.setBackground(Color.GREEN);  // Cambiar a verde cuando produce
        productorEstado.revalidate();
        productorPanel.revalidate();
        productorPanel.repaint();  // Asegura que los cambios se reflejan inmediatamente en la GUI
        almacen.almacenar(item);  // Interactúa con el almacén

        productorEstado.setText("Productor: Esperando...");
        productorPanel.setBackground(Color.LIGHT_GRAY);  // Cambiar a gris cuando espera
        productorEstado.revalidate();
        productorPanel.revalidate();
        productorPanel.repaint();
    }

    @Override
    public void run() {
        int item = 0;
        try {
            while (running) {
                producir(item++);
                Thread.sleep(1000);  // Simula tiempo de producción.
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
