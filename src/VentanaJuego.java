import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VentanaJuego extends JFrame implements KeyListener {
    // --- ATRIBUTOS ---
    private JTextArea areaTexto;
    private Partida partida;
    private JTextArea historialCombate;
    private JLabel etiquetaStatsHeroe;
    private JLabel etiquetaStatsJefe;
    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private JPanel panelJuego;
    private JPanel panelMenu;

    // --- CONSTRUCTOR ---
    public VentanaJuego(Partida partida) {
        this.partida = partida;
        setTitle("Green Protocol v1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        // --- SISTEMA DE CARTAS ---
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        add(panelContenedor);
        // CREACIÓN DEL PANEL DE JUEGO
        panelJuego = new JPanel(new BorderLayout());
        // El Lienzo del Mapa
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setBackground(Color.BLACK);
        areaTexto.setForeground(Color.GREEN);
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 35));
        JPanel panelFondo = new JPanel(new GridBagLayout());
        panelFondo.setBackground(Color.BLACK);
        panelFondo.add(areaTexto);
        panelJuego.add(panelFondo, BorderLayout.CENTER); // <-- Añadido a panelJuego
        // El Panel Derecho
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.BLACK);
        JLabel tituloRegistro = new JLabel(" Registro de Batalla ");
        tituloRegistro.setFont(new Font("Monospaced", Font.BOLD, 14));
        tituloRegistro.setForeground(Color.GREEN);
        tituloRegistro.setBackground(Color.DARK_GRAY);
        tituloRegistro.setOpaque(true);
        tituloRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        tituloRegistro.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        historialCombate = new JTextArea(10, 35);
        historialCombate.setEditable(false);
        historialCombate.setBackground(Color.DARK_GRAY);
        historialCombate.setForeground(Color.WHITE);
        historialCombate.setFont(new Font("Monospaced", Font.BOLD, 14));
        historialCombate.setMargin(new Insets(10, 10, 10, 10));
        historialCombate.setLineWrap(true);
        historialCombate.setWrapStyleWord(true);
        JScrollPane scrollHistorial = new JScrollPane(historialCombate);
        scrollHistorial.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        panelDerecho.add(tituloRegistro, BorderLayout.NORTH);
        panelDerecho.add(scrollHistorial, BorderLayout.CENTER);
        panelJuego.add(panelDerecho, BorderLayout.EAST); // <-- Añadido a panelJuego
        // El Panel Inferior
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));
        panelInferior.setBackground(Color.DARK_GRAY);
        etiquetaStatsHeroe = new JLabel();
        etiquetaStatsJefe = new JLabel();
        Font fuenteStats = new Font("Monospaced", Font.BOLD, 16);
        etiquetaStatsHeroe.setFont(fuenteStats);
        etiquetaStatsHeroe.setForeground(Color.YELLOW);
        etiquetaStatsHeroe.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaStatsJefe.setFont(fuenteStats);
        etiquetaStatsJefe.setForeground(Color.ORANGE);
        etiquetaStatsJefe.setHorizontalAlignment(SwingConstants.CENTER);
        panelInferior.add(etiquetaStatsHeroe);
        panelInferior.add(etiquetaStatsJefe);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelJuego.add(panelInferior, BorderLayout.SOUTH); // <-- Añadido a panelJuego
        // 2. CREACIÓN DEL PANEL DE MENÚ
        panelMenu = new JPanel(new BorderLayout());
        panelMenu.setBackground(Color.BLACK);
        JTextArea textoMenu = new JTextArea();
        textoMenu.setEditable(false);
        textoMenu.setBackground(Color.BLACK);
        textoMenu.setForeground(Color.GREEN);
        textoMenu.setFont(new Font("Monospaced", Font.BOLD, 20));
        textoMenu.setText("\n\n\n\n\n\n\n\n" +
                "       _____                    _____           _                  _ \n" +
                "      / ____|                  |  __ \\         | |                | |\n" +
                "     | |  __ _ __ ___  ___ _ __| |__) | __ ___ | |_ ___   ___ ___ | |\n" +
                "     | | |_ | '__/ _ \\/ _ \\ '_ \\  ___/ '__/ _ \\| __/ _ \\ / __/ _ \\| |\n" +
                "     | |__| | | |  __/  __/ | | | |   | | | (_) | || (_) | (_| (_) | |\n" +
                "      \\_____|_|  \\___|\\___|_| |_|_|   |_|  \\___/ \\__\\___/ \\___\\___/|_|\n" +
                "\n\n\n\n\n" +
                "                            [1] ¡JUGAR!\n" +
                "                            [2] SALIR");
        panelMenu.add(textoMenu, BorderLayout.CENTER);
        // 2.5 CREACIÓN DEL PANEL DE SELECCIÓN (NUEVO)
        JPanel panelSeleccion = new JPanel(new BorderLayout());
        panelSeleccion.setBackground(Color.BLACK);
        JTextArea textoSeleccion = new JTextArea();
        textoSeleccion.setEditable(false);
        textoSeleccion.setBackground(Color.BLACK);
        textoSeleccion.setForeground(Color.GREEN);
        textoSeleccion.setFont(new Font("Monospaced", Font.BOLD, 20));
        textoSeleccion.setText("\n\n\n\n\n\n" +
                "       >>> SELECCIONA TU CLASE <<<\n" +
                "       ===========================\n\n" +
                "       [1] GUERRERO : Mucho HP, Daño físico masivo con rabia.\n" +
                "       [2] PALADÍN  : Equilibrado, Daño mágico con bendición.\n" +
                "       [3] PÍCARO   : Frágil, Ataque doble por turno.\n" +
                "       [4] Salir                                     \n4"+
                "\n\n\n\n\n\n\n" +
                "                          [ PULSA 1, 2 O 3 ]");
        panelSeleccion.add(textoSeleccion, BorderLayout.CENTER);
        // 3. AÑADIR PANELES AL CONTENEDOR Y MOSTRAR MENÚ
        panelContenedor.add(panelMenu, "MENU");
        panelContenedor.add(panelJuego, "JUEGO");
        panelContenedor.add(panelSeleccion, "SELECCION");
        cardLayout.show(panelContenedor, "MENU");
        addKeyListener(this);
        setFocusable(true);
        pack();
        setSize(1080, 900);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // --- MÉTODOS DE LA INTERFAZ ---
    // Dibuja el mapa de texto en la pantalla
    public void actualizarPantalla(String mapaTexto) {
        areaTexto.setText(mapaTexto);
    }

    // Actualiza los textos de vida y estados
    public void actualizarStats(String textoHeroe, String textoJefe) {
        etiquetaStatsHeroe.setText(textoHeroe);
        etiquetaStatsJefe.setText(textoJefe);
    }
    //Imprime los eventos en el registro de batalla
    public void imprimirLog(String mensaje) {
        if (mensaje != null && !mensaje.isEmpty()) {
            historialCombate.append(" > " + mensaje + "\n");
            // Autoscroll hacia abajo
            historialCombate.setCaretPosition(historialCombate.getDocument().getLength());
        }
    }

    // Cambia el color del texto del mapa. Para el final de la partida.
    public void cambiarColorMapa(Color color) {
        areaTexto.setForeground(color);
    }

    // Reemplaza todo el texto por "nada"
    public void limpiarHistorial() {
        historialCombate.setText("");
    }

    //Enseña el panel de menu
    public void mostrarMenu() {
        cardLayout.show(panelContenedor, "MENU");
    }
    //Enseña el panel de juego
    public void mostrarJuego() {
        cardLayout.show(panelContenedor, "JUEGO");
    }

    //Ensela el panel de seleccion
    public void mostrarSeleccion() {
        cardLayout.show(panelContenedor, "SELECCION");
    }

    // --- MÉTODOS DEL KEYLISTENER ---
    @Override
    public void keyPressed(KeyEvent e) {
        // 1. Captura el código numérico de la tecla que se pulsa
        int codigoTecla = e.getKeyCode();
        // 2. Usamos el cable de comunicación con Partida pasándole la tecla exacta.
        partida.gestionarTecla(codigoTecla);
    }

    // Métodos son obligatorios por el "implements KeyListener"
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}