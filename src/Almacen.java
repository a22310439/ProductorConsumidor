import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;

public class Almacen extends JPanel {
    private int capacidad;
    private final Queue<Integer> buffer = new LinkedList<>();

    public Almacen(int capacidad) {
        this.capacidad = capacidad;
        setPreferredSize(new Dimension(300, 100));  // Dimensiones del almacén
    }

    public synchronized void almacenar(int item) throws InterruptedException {
        while (buffer.size() == capacidad) {
            wait();  // Esperar si el buffer está lleno
        }
        buffer.add(item);
        repaint();  // Redibuja el almacén
        notifyAll();  // Notificar a los consumidores
    }

    public synchronized int extraer() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();  // Esperar si el buffer está vacío
        }
        int item = buffer.remove();
        repaint();  // Redibuja el almacén
        notifyAll();  // Notificar a los productores
        return item;
    }

    public synchronized void aumentarCapacidad() {
        capacidad++;
        repaint();  // Redibujar el almacén
    }

    public synchronized void disminuirCapacidad() {
        if (capacidad > 1 && capacidad > buffer.size()) {  // Evitar reducir por debajo del tamaño del buffer
            capacidad--;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int rectWidth = getWidth() / capacidad;
        for (int i = 0; i < capacidad; i++) {
            if (i < buffer.size()) {
                g.setColor(Color.GREEN);  // Casilla ocupada
            } else {
                g.setColor(Color.LIGHT_GRAY);  // Casilla vacía
            }
            g.fillRect(i * rectWidth, 0, rectWidth - 2, getHeight());
        }
    }
}
