import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Main {
    private static final List<Productor> productores = new ArrayList<>();
    private static final List<Consumidor> consumidores = new ArrayList<>();
    private static final JPanel productoresPanel = new JPanel(new GridLayout(1, 2));
    private static final JPanel consumidoresPanel = new JPanel(new GridLayout(1, 2));

    public static void main(String[] args) {
        // Configuración de la interfaz gráfica
        JFrame frame = new JFrame("Problema del Productor y Consumidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Crear almacén en el centro
        Almacen almacen = new Almacen(3);  // Capacidad inicial del almacén
        frame.add(almacen, BorderLayout.CENTER);

        // Paneles de control para productores y consumidores con botones
        JPanel panelControlProductores = new JPanel(new BorderLayout());
        JPanel panelControlConsumidores = new JPanel(new BorderLayout());

        // Panel para productores y sus controles
        JPanel controlProductores = new JPanel();
        controlProductores.setLayout(new GridLayout(1, 2));
        JButton addProductorBtn = new JButton("Agregar Productor");
        JButton removeProductorBtn = new JButton("Eliminar Productor");
        controlProductores.add(addProductorBtn);
        controlProductores.add(removeProductorBtn);

        panelControlProductores.add(controlProductores, BorderLayout.SOUTH);
        panelControlProductores.add(productoresPanel, BorderLayout.CENTER);

        // Panel para consumidores y sus controles
        JPanel controlConsumidores = new JPanel();
        controlConsumidores.setLayout(new GridLayout(1, 2));
        JButton addConsumidorBtn = new JButton("Agregar Consumidor");
        JButton removeConsumidorBtn = new JButton("Eliminar Consumidor");
        controlConsumidores.add(addConsumidorBtn);
        controlConsumidores.add(removeConsumidorBtn);

        panelControlConsumidores.add(controlConsumidores, BorderLayout.NORTH);
        panelControlConsumidores.add(consumidoresPanel, BorderLayout.CENTER);

        // Panel para los botones de cambiar la capacidad del almacén
        JPanel controlAlmacen = new JPanel();
        JButton aumentarAlmacenBtn = new JButton("Aumentar Almacén");
        JButton disminuirAlmacenBtn = new JButton("Disminuir Almacén");
        controlAlmacen.add(aumentarAlmacenBtn);
        controlAlmacen.add(disminuirAlmacenBtn);

        frame.add(controlAlmacen, BorderLayout.WEST);  // Panel de control del almacén a la izquierda

        // Agregar los paneles de control y sus botones a la interfaz
        frame.add(panelControlProductores, BorderLayout.NORTH);
        frame.add(panelControlConsumidores, BorderLayout.SOUTH);

        // Funcionalidad de agregar y eliminar productores
        addProductorBtn.addActionListener(e -> agregarProductor(almacen));
        removeProductorBtn.addActionListener(e -> eliminarProductor());

        // Funcionalidad de agregar y eliminar consumidores
        addConsumidorBtn.addActionListener(e -> agregarConsumidor(almacen));
        removeConsumidorBtn.addActionListener(e -> eliminarConsumidor());

        // Funcionalidad de aumentar y disminuir el tamaño del almacén
        aumentarAlmacenBtn.addActionListener(e -> almacen.aumentarCapacidad());
        disminuirAlmacenBtn.addActionListener(e -> almacen.disminuirCapacidad());

        // Mostrar la ventana
        frame.setVisible(true);
    }

    private static void agregarProductor(Almacen almacen) {
        JPanel productorPanel = new JPanel();
        productorPanel.setBackground(Color.LIGHT_GRAY);  // Inicialmente gris (esperando)
        JLabel productorEstado = new JLabel("Productor: Esperando...");
        productorPanel.add(productorEstado);
        productoresPanel.add(productorPanel);
        productoresPanel.revalidate();  // Actualiza la interfaz

        Productor productor = new Productor(almacen, productorEstado, productorPanel);
        productores.add(productor);
        productor.start();
    }

    private static void eliminarProductor() {
        if (!productores.isEmpty()) {
            Productor productor = productores.remove(productores.size() - 1);
            productor.detener();  // Detener el hilo
            productoresPanel.remove(productoresPanel.getComponentCount() - 1);
            productoresPanel.revalidate();
            productoresPanel.repaint();
        }
    }

    private static void agregarConsumidor(Almacen almacen) {
        JPanel consumidorPanel = new JPanel();
        consumidorPanel.setBackground(Color.LIGHT_GRAY);  // Inicialmente gris (esperando)
        JLabel consumidorEstado = new JLabel("Consumidor: Esperando...");
        consumidorPanel.add(consumidorEstado);
        consumidoresPanel.add(consumidorPanel);
        consumidoresPanel.revalidate();  // Actualiza la interfaz

        Consumidor consumidor = new Consumidor(almacen, consumidorEstado, consumidorPanel);
        consumidores.add(consumidor);
        consumidor.start();
    }

    private static void eliminarConsumidor() {
        if (!consumidores.isEmpty()) {
            Consumidor consumidor = consumidores.remove(consumidores.size() - 1);
            consumidor.detener();  // Detener el hilo
            consumidoresPanel.remove(consumidoresPanel.getComponentCount() - 1);
            consumidoresPanel.revalidate();
            consumidoresPanel.repaint();
        }
    }
}
