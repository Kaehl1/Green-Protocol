import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VentanaJuego extends JFrame implements KeyListener {
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
        panelJuego.add(panelFondo, BorderLayout.CENTER); //Añadido a panelJuego
        //Historial de combate
        historialCombate = new JTextArea(6,50);
        historialCombate.setEditable(false);
        historialCombate.setBackground(Color.BLACK);
        historialCombate.setForeground(Color.GREEN);
        historialCombate.setFont(new Font("Monospaced", Font.BOLD, 14));
        historialCombate.setMargin(new Insets(5, 10, 5, 10));
        historialCombate.setLineWrap(true);
        historialCombate.setWrapStyleWord(true);
        JScrollPane scrollHistorial = new JScrollPane(historialCombate);
        scrollHistorial.setBackground(Color.BLACK);
        scrollHistorial.getViewport().setBackground(Color.BLACK);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), " Registro de Batalla ", TitledBorder.CENTER, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 14), Color.GREEN));
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
        JPanel panelSurGlobal = new JPanel(new BorderLayout());
        panelSurGlobal.add(scrollHistorial, BorderLayout.CENTER);
        panelSurGlobal.add(panelInferior, BorderLayout.SOUTH);
        panelJuego.add(panelSurGlobal, BorderLayout.SOUTH);
        // CREACIÓN DEL PANEL DE MENÚ
        panelMenu = new JPanel(new BorderLayout());
        panelMenu.setBackground(Color.BLACK);
        JTextArea textoMenu = new JTextArea();
        textoMenu.setEditable(false);
        textoMenu.setBackground(Color.BLACK);
        textoMenu.setForeground(Color.GREEN);
        textoMenu.setFont(new Font("Monospaced", Font.BOLD, 20));
        textoMenu.setText("\n\n\n\n\n\n\n\n" +
                "             _____                    _____            _                  _ \n" +
                "            / ____|                  |  __ \\          | |                | |\n" +
                "           | |  __ _ __ ___  ___ _ __| |__) | __  ___ | |_ ___   ___ ___ | |\n" +
                "           | | |_ | '__/ _ \\/ _ \\ '_ \\  ___// '__/ _ \\| __/ _ \\ / __/ _ \\| |\n" +
                "           | |__| | | |  __/  __/ | | | |   | | | (_) | || (_) | (_| (_) | |\n" +
                "            \\_____|_|  \\___|\\___|_| |_|_|   |_|  \\___/ \\__\\___/ \\___\\___/|_|\n" +
                "\n\n\n\n\n" +
                "                                     [1] ¡JUGAR!\n" +
                "                                     [2] SALIR");
        panelMenu.add(textoMenu, BorderLayout.CENTER);
        // CREACIÓN DEL PANEL DE SELECCIÓN
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
                "       [4] Salir                                     \n"+
                "\n\n\n\n\n\n\n" +
                "                                  [ PULSA 1, 2 O 3 ]");
        panelSeleccion.add(textoSeleccion, BorderLayout.CENTER);
        // AÑADIR PANELES AL CONTENEDOR Y MOSTRAR MENÚ
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
    public void actualizarPantalla(String mapaTexto) {
        areaTexto.setText(mapaTexto);
    }

    public void actualizarStats(String textoHeroe, String textoJefe) {
        etiquetaStatsHeroe.setText(textoHeroe);
        etiquetaStatsJefe.setText(textoJefe);
    }
    public void imprimirLog(String mensaje) {
        if (mensaje != null && !mensaje.isEmpty()) {
            historialCombate.append(" > " + mensaje + "\n");
            historialCombate.setCaretPosition(historialCombate.getDocument().getLength());
        }
    }

    public void cambiarColorMapa(Color color) {
        areaTexto.setForeground(color);
    }

    public void limpiarHistorial() {
        historialCombate.setText("");
    }

    public void mostrarMenu() {
        cardLayout.show(panelContenedor, "MENU");
    }

    public void mostrarJuego() {
        cardLayout.show(panelContenedor, "JUEGO");
    }

    public void mostrarSeleccion() {
        cardLayout.show(panelContenedor, "SELECCION");
    }

    public void mostrarPantallaNombre(String nombreTecleado) {
        cambiarColorMapa(Color.GREEN);
        String pantalla = "\n\n\n\n\n\n" +
                "      ===============================\n" +
                "             NOMBRA A TU HÉROE      \n" +
                "      ===============================\n\n" +
                "            Nombre: " + nombreTecleado + "_\n\n\n\n" +
                "      [Escribe tu nombre y pulsa ENTER]\n" +
                "         [Pulsa Retroceso para borrar]";
        actualizarPantalla(pantalla);
    }

    // --- MÉTODOS DEL KEYLISTENER ---
    @Override
    public void keyPressed(KeyEvent e) {
        int codigoTecla = e.getKeyCode();
        partida.gestionarTecla(codigoTecla);
    }

    // Métodos son obligatorios por el "implements KeyListener"
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}