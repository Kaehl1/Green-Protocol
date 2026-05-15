import controladores.Controlador;
import controladores.Jugador;
import controladores.Maquina;
import entidades.Entidad;
import escenario.Tablero;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Partida {
    private VentanaJuego ventana;
    private Tablero tablero;
    private ArrayList<Controlador> controladores;
    private int turnoActual;
    private Entidad heroe;
    private Entidad jefe;
    private boolean juegoTerminado = false;


    public Partida(Tablero tablero, Entidad heroe, Entidad jefe) {
        this.tablero = tablero;
        this.heroe = heroe;
        this.jefe = jefe;
        this.controladores = new ArrayList<>();
        this.turnoActual = 0;
        this.ventana = new VentanaJuego(this);
        this.ventana.actualizarPantalla(actualizarMapa());
        this.refrescarInterfaz();
    }

    public void agregarControlador(Controlador c) {
        this.controladores.add(c);
    }

    public void avanzarTurno() {
        turnoActual++;
        if (turnoActual == controladores.size()) {
            turnoActual = 0;
        }
        Controlador siguiente = controladores.get(turnoActual);
        String reporte = siguiente.ejecutarTurno(this.tablero);
        if (reporte != null&& !reporte.isEmpty()) {
            this.registrarLog(reporte);
        }
        this.refrescarInterfaz();
        if (siguiente instanceof Maquina) {
            this.ventana.actualizarPantalla(actualizarMapa());
            this.refrescarInterfaz();
            verificarFinPartida();
            if (!juegoTerminado){
                avanzarTurno();
            }
        }
    }

    public void gestionarTecla(int codigoTecla) {
        if (juegoTerminado) {
            if(codigoTecla == KeyEvent.VK_ENTER){
                reiniciarPartida();
            }
            return;
        }
        Controlador actual = controladores.get(turnoActual);
        if (actual instanceof Jugador) {
            if(codigoTecla== KeyEvent.VK_E){
                if (heroe.puedeAtacar(jefe)){
                    String reporteAtaque = heroe.atacar(jefe);
                    registrarLog(reporteAtaque);
                    this.refrescarInterfaz();
                    verificarFinPartida();
                    if (!juegoTerminado){
                        avanzarTurno();
                    }
                }else {
                    registrarLog("El enemigo está demasiado lejos.");
                }
            }else if (codigoTecla == KeyEvent.VK_ENTER) {
                avanzarTurno();
            }else if (codigoTecla == KeyEvent.VK_R) {
                registrarLog(heroe.usarHabilidad());
                this.refrescarInterfaz();
                avanzarTurno();
            }else {
                int vidaAntes=heroe.getVidaActual();
                int dañoAntes= heroe.getDañoBase();
                ((Jugador) actual).procesarEntrada(codigoTecla, this.tablero);
                int vidaDespues=heroe.getVidaActual();
                int dañoDespues= heroe.getDañoBase();
                if(vidaDespues>vidaAntes){
                    int cura=vidaDespues-vidaAntes;
                    registrarLog("¡Has recogido una pocion y te curas "+cura+"HP!");
                }
                if (dañoDespues>dañoAntes){
                    int buff=dañoDespues-dañoAntes;
                    registrarLog("¡Has recogido una pocion y tu daño aumenta "+buff+"!");
                }
                String mapaActualizado = actualizarMapa();
                this.ventana.actualizarPantalla(mapaActualizado);
                this.refrescarInterfaz();
            }
        }
    }

    private String actualizarMapa() {
        return tablero.generarMapaTexto();
    }

    private void verificarFinPartida() {
        if (!heroe.getEstaVivo()) {
            juegoTerminado = true;
            registrarLog(">>> GAME OVER: El héroe ha caído...");
            ventana.cambiarColorMapa(Color.RED);
            ventana.actualizarPantalla(generarPantallaDerrota());
        } else if (!jefe.getEstaVivo()) {
            juegoTerminado = true;
            registrarLog(">>> ¡VICTORIA! La amenaza ha sido eliminada.");
            ventana.cambiarColorMapa(Color.YELLOW);
            ventana.actualizarPantalla(generarPantallaVictoria());
        }
    }

    private String generarPantallaVictoria() {
        return "\n\n" +
                "          ___________          \n" +
                "         '._==_==_=_.'         \n" +
                "         .-\\:      /-.        \n" +
                "        | (|:.     |) |        \n" +
                "         '-|:.     |-'         \n" +
                "           \\::.    /          \n" +
                "            '::. .'            \n" +
                "              ) (              \n" +
                "            _.' '._            \n" +
                "           `-------`           \n" +
                "         ¡VICTORIA ÉPICA!       ";
    }

    private String generarPantallaDerrota() {
        return "\n\n" +
                "           .---------.           \n" +
                "          /           \\          \n" +
                "         /    R.I.P    \\         \n" +
                "        |               |        \n" +
                "        |     KAEHL     |        \n" +
                "        |               |        \n" +
                "      .-|               |-.      \n" +
                "      \\-------------------/      \n" +
                "                                 \n" +
                "          GAME OVER              \n" +
                "       Has sido derrotado        ";
    }

    private void refrescarInterfaz() {
        String statsHeroe = heroe.getNombre() + " [HP: " + heroe.getVidaActual() + "/" + heroe.getVidaMax() + " | Mov: " + heroe.getMovimientoActual() + " | Est: " + heroe.obtenerEstado() + "]";
        String statsJefe = jefe.getNombre() + " [HP: " + jefe.getVidaActual() + "/" + jefe.getVidaMax() + " | Mov: " + jefe.getMovimientoActual() + " | Est: " + jefe.obtenerEstado() + "]";
        this.ventana.actualizarStats(statsHeroe, statsJefe);
    }

    public void registrarLog(String mensaje) {
        this.ventana.imprimirLog(mensaje);
    }

    private void reiniciarPartida() {
        this.juegoTerminado = false;
        this.turnoActual = 0;
        tablero.limpiarTablero();
        ventana.limpiarHistorial();
        ventana.cambiarColorMapa(java.awt.Color.GREEN);
        heroe.reiniciar();
        jefe.reiniciar();
        for (Controlador c : controladores) {
            if (c instanceof Maquina) {
                ((Maquina) c).reiniciarControlador();
            }
        }
        tablero.colocarEntidad(heroe, 0, 0);
        tablero.colocarEntidad(jefe, 9, 9);
        this.ventana.actualizarPantalla(actualizarMapa());
        this.refrescarInterfaz();
        registrarLog("--- SISTEMA REINICIADO: NUEVA SIMULACIÓN ---");
    }
}
