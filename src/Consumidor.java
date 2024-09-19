import java.awt.*;
import javax.swing.*;

public class Consumidor extends Thread {
    private final Almacen almacen;
    private final JLabel consumidorEstado;
    private final JPanel consumidorPanel;
    private boolean running = true;

    public Consumidor(Almacen almacen, JLabel consumidorEstado, JPanel consumidorPanel) {
        this.almacen = almacen;
        this.consumidorEstado = consumidorEstado;
        this.consumidorPanel = consumidorPanel;
    }

    public void detener() {
        running = false;  // Detener el hilo
    }

    private void consumir() throws InterruptedException {
        consumidorEstado.setText("Consumidor: Consumiendo...");
        consumidorPanel.setBackground(Color.GREEN);  // Cambiar a verde cuando consume
        consumidorEstado.revalidate();
        consumidorPanel.revalidate();
        consumidorPanel.repaint();  // Asegura que los cambios se reflejan inmediatamente en la GUI
        almacen.extraer();  // Interactúa con el almacén

        consumidorEstado.setText("Consumidor: Esperando...");
        consumidorPanel.setBackground(Color.LIGHT_GRAY);  // Cambiar a gris cuando espera
        consumidorEstado.revalidate();
        consumidorPanel.revalidate();
        consumidorPanel.repaint();
    }

    @Override
    public void run() {
        try {
            while (running) {
                consumir();
                Thread.sleep(1500);  // Simula tiempo de consumo.
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
