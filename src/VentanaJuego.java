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

    // --- CONSTRUCTOR ---
    public VentanaJuego(Partida partida) {
        this.partida = partida;
        // 1. Configuración de la Ventana
        setTitle("Green Protocol v1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar la 'X', se apaga el programa por completo
        setResizable(false); // Bloquea el tamaño para evitar que el mapa se descuadre
        setLayout(new BorderLayout());
        // 2. Configuración del Lienzo
        areaTexto = new JTextArea();
        areaTexto.setEditable(false); // El usuario no puede escribir ni borrar el mapa
        areaTexto.setBackground(Color.BLACK); // Fondo negro
        areaTexto.setForeground(Color.GREEN); // Texto verde
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 35));
        // 3. Añadimos el lienzo a la ventana
        JPanel panelFondo = new JPanel(new GridBagLayout());
        panelFondo.setBackground(Color.BLACK);
        panelFondo.add(areaTexto);
        add(panelFondo,  BorderLayout.CENTER);
        // --- PANEL DERECHO: HISTORIAL CON TÍTULO ---
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.BLACK);
        // Título personalizado
        JLabel tituloRegistro = new JLabel(" Registro de Batalla ");
        tituloRegistro.setFont(new Font("Monospaced", Font.BOLD, 14));
        tituloRegistro.setForeground(Color.GREEN);
        tituloRegistro.setBackground(Color.DARK_GRAY);
        tituloRegistro.setOpaque(true); // Fondo gris visible
        tituloRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        tituloRegistro.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Borde verde fino
        // Área de texto del historial
        historialCombate = new JTextArea(10, 35);
        historialCombate.setEditable(false);
        historialCombate.setBackground(Color.DARK_GRAY);
        historialCombate.setForeground(Color.WHITE);
        historialCombate.setFont(new Font("Monospaced", Font.BOLD, 14));
        historialCombate.setMargin(new Insets(10, 10, 10, 10));
        historialCombate.setLineWrap(true); // Activa el salto de línea automático al llegar al borde
        historialCombate.setWrapStyleWord(true); // Obliga a que el salto se haga en espacios para no partir una palabra por la mitad
        JScrollPane scrollHistorial = new JScrollPane(historialCombate);
        scrollHistorial.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        // Montamos el panel derecho: título al NORTE, historial al CENTRO
        panelDerecho.add(tituloRegistro, BorderLayout.NORTH);
        panelDerecho.add(scrollHistorial, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);
        // --- PANEL INFERIOR: ESTADÍSTICAS EN DOS FILAS ---
        // Usamos GridLayout(2, 1) para obligar a tener 2 filas (Héroe arriba, Jefe abajo)
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));
        panelInferior.setBackground(Color.DARK_GRAY);
        etiquetaStatsHeroe = new JLabel();
        etiquetaStatsJefe = new JLabel();
        // Estilo para las etiquetas
        Font fuenteStats = new Font("Monospaced", Font.BOLD, 16);
        etiquetaStatsHeroe.setFont(fuenteStats);
        etiquetaStatsHeroe.setForeground(Color.YELLOW);
        etiquetaStatsHeroe.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaStatsJefe.setFont(fuenteStats);
        etiquetaStatsJefe.setForeground(Color.ORANGE); // Naranja para el jefe resalta bien
        etiquetaStatsJefe.setHorizontalAlignment(SwingConstants.CENTER);
        panelInferior.add(etiquetaStatsHeroe);
        panelInferior.add(etiquetaStatsJefe);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(panelInferior, BorderLayout.SOUTH);
        // 5. Configuración del "Oído"
        addKeyListener(this); // La ventana lee sus propias teclas
        setFocusable(true); // Ventana foco de atención del teclado
        // 6. Renderizado final
        pack(); // Adapta la ventana al contenido inicial
        setSize(1080, 900); // Tamaño de la ventana en pixeles
        setLocationRelativeTo(null); // Centra la ventana en medio del monitor
        setVisible(true); // Enciende la pantalla
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