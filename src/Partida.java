import controladores.Controlador;
import controladores.Jugador;
import controladores.Maquina;
import datos.GestorEstadisticas;
import datos.RegistroPartida;
import entidades.Entidad;
import entidades.Guerrero;
import entidades.Paladin;
import entidades.Rogue;
import escenario.Tablero;
import excepciones.AtaqueInvalidoException;
import excepciones.MovimientoInvalidoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Partida {
    private VentanaJuego ventana;
    private Tablero tablero;
    private ArrayList<Controlador> controladores;
    private int turnoActual;
    private Entidad heroe;
    private Entidad jefe;
    private boolean juegoTerminado = false;
    private boolean enMenu = true;
    private boolean enSeleccion = false;
    private boolean enIntroduccionNombre = false;
    private int contadorTurnosPocion = 0;
    private int turnosParaNuevaPocion = 0;
    private Random rand = new Random();
    private String nombreUsuario="";
    private Entidad claseElegidaTemp;
    private int turnosTotales = 0;


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
        if (siguiente instanceof Jugador) {
            this.turnosTotales++;
            contadorTurnosPocion++;
            if (contadorTurnosPocion >= turnosParaNuevaPocion) {
                tablero.generarPocionAleatoria();
                registrarLog("SISTEMA: Una nueva poción ha aparecido en la zona.");
                ventana.actualizarPantalla(actualizarMapa());
                contadorTurnosPocion = 0;
                turnosParaNuevaPocion = rand.nextInt(5) + 4;
            }
        }
        if(siguiente instanceof Jugador) {
            heroe.reiniciarMovimiento();
            String reporte = siguiente.ejecutarTurno(this.tablero);
            if(reporte != null&&!reporte.isEmpty()){
                this.registrarLog(reporte);
            }
            this.refrescarInterfaz();
        }else if (siguiente instanceof Maquina) {
            Maquina maquina = (Maquina) siguiente;
            String reporteInicio = maquina.ejecutarTurno(this.tablero);
            if(reporteInicio != null&&!reporteInicio.isEmpty()){
                this.registrarLog(reporteInicio);
            }
            this.refrescarInterfaz();
            Timer timerMaquina = new Timer(400, evento -> {
                boolean movido = maquina.intentarMover(this.tablero);
                ventana.actualizarPantalla(actualizarMapa());
                refrescarInterfaz();
                if (!movido) {
                    ((Timer) evento.getSource()).stop();
                    String textoAtaque = null;
                    try {
                        textoAtaque = maquina.ejecutarAtaque();
                    } catch (AtaqueInvalidoException e) {
                        throw new RuntimeException(e);
                    }
                    if (!textoAtaque.isEmpty()) {
                        registrarLog(textoAtaque);
                        refrescarInterfaz();
                        verificarFinPartida();
                    }
                    if (!juegoTerminado) {
                        avanzarTurno();
                    }
                }
            });
            timerMaquina.start();
        }
    }

    public void gestionarTecla(int codigoTecla) {
        //En el menu principal
        if(enMenu){
            if(codigoTecla == KeyEvent.VK_1 || codigoTecla == KeyEvent.VK_NUMPAD1){
                enMenu = false;
                enSeleccion = true;
                ventana.mostrarSeleccion();
            } else if (codigoTecla == KeyEvent.VK_2 || codigoTecla == KeyEvent.VK_NUMPAD2) {
                System.exit(0);
            }
            return;
        }
        //En el menu de seleccion
        if(enSeleccion){
            if(codigoTecla == KeyEvent.VK_1 || codigoTecla == KeyEvent.VK_NUMPAD1){
                this.claseElegidaTemp = new Guerrero(0,0, "Heroe");
                this.enSeleccion = false;
                this.enIntroduccionNombre = true;
                ventana.mostrarJuego();
                ventana.mostrarPantallaNombre(nombreUsuario);
            } else if (codigoTecla == KeyEvent.VK_2 || codigoTecla == KeyEvent.VK_NUMPAD2) {
                this.claseElegidaTemp = new Paladin(0,0, "Heroe");
                this.enSeleccion = false;
                this.enIntroduccionNombre = true;
                ventana.mostrarJuego();
                ventana.mostrarPantallaNombre(nombreUsuario);
            } else if (codigoTecla == KeyEvent.VK_3 || codigoTecla == KeyEvent.VK_NUMPAD3) {
                this.claseElegidaTemp = new Rogue(0,0, "Heroe");
                this.enSeleccion = false;
                this.enIntroduccionNombre = true;
                ventana.mostrarJuego();
                ventana.mostrarPantallaNombre(nombreUsuario);
            } else if (codigoTecla == KeyEvent.VK_4 || codigoTecla == KeyEvent.VK_NUMPAD4) {
                System.exit(0);
            }
            return;
        }
        //En la introduccion de nombre
        if(enIntroduccionNombre){
            if (codigoTecla == KeyEvent.VK_ENTER && nombreUsuario.length() > 0) {
                claseElegidaTemp.setNombre(nombreUsuario);
                configurarNuevaPartida(claseElegidaTemp);
            }
            else if (codigoTecla == KeyEvent.VK_BACK_SPACE && nombreUsuario.length() > 0) {
                nombreUsuario = nombreUsuario.substring(0, nombreUsuario.length() - 1);
                ventana.mostrarPantallaNombre(nombreUsuario);
            }
            else if (codigoTecla >= KeyEvent.VK_A && codigoTecla <= KeyEvent.VK_Z) {
                if (nombreUsuario.length() < 10) {
                    nombreUsuario += (char) codigoTecla;
                    nombreUsuario = Utiles.primeraMayus(nombreUsuario);
                    ventana.mostrarPantallaNombre(nombreUsuario);
                }
            }
            return;
        }
        //En el final de la partida
        if (juegoTerminado) {
            if(codigoTecla == KeyEvent.VK_ENTER){
                juegoTerminado = false;
                enMenu = true;
                ventana.mostrarMenu();
            }
            return;
        }
        //Jugando
        Controlador actual = controladores.get(turnoActual);
        if (actual instanceof Jugador) {
            if(codigoTecla== KeyEvent.VK_E){
                try {
                    String reporteAtaque = heroe.atacar(jefe);
                    registrarLog(reporteAtaque);
                    this.refrescarInterfaz();
                    verificarFinPartida();
                    if (!juegoTerminado){
                        avanzarTurno();
                    }
                } catch (AtaqueInvalidoException e) {
                    registrarLog("SISTEMA: " + e.getMessage());
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
                int defensaAntes = heroe.getDefensa();
                try {
                    ((Jugador) actual).procesarEntrada(codigoTecla, this.tablero);
                }catch (MovimientoInvalidoException e){
                    registrarLog(e.getMessage());
                }
                int vidaDespues=heroe.getVidaActual();
                int dañoDespues= heroe.getDañoBase();
                int defensaDespues = heroe.getDefensa();
                if(vidaDespues>vidaAntes){
                    int cura=vidaDespues-vidaAntes;
                    registrarLog("¡Has recogido una poción y te curas "+cura+"HP!");
                }
                if (dañoDespues>dañoAntes){
                    int buffAtq=dañoDespues-dañoAntes;
                    registrarLog("¡Has recogido una poción y tu daño aumenta "+buffAtq+"!");
                }
                if (defensaDespues>defensaAntes){
                    int buffDef=defensaDespues-defensaAntes;
                    registrarLog("¡Has recogido una poción y tu defensa aumenta "+buffDef+"!");
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
        boolean heroeMuerto = !heroe.getEstaVivo();
        boolean jefeMuerto = !jefe.getEstaVivo();
        //Si ambos siguen vivos salimos del metodo sin tocar el XML
        if(!heroeMuerto && !jefeMuerto){
            return;
        }
        //Si llega aquí es que alguien ha muerto
        juegoTerminado = true;
        int puntosFinales = 0;
        if(heroeMuerto){
            registrarLog(">>> GAME OVER: El héroe ha caído...");
            ventana.cambiarColorMapa(Color.RED);
            ventana.actualizarPantalla(generarPantallaDerrota());
        }else{
            puntosFinales = Math.max(0, 1000 - (this.turnosTotales * 10));
            registrarLog(">>> ¡VICTORIA! La amenaza ha sido eliminada en " + this.turnosTotales + " turnos.");
            ventana.cambiarColorMapa(Color.YELLOW);
            ventana.actualizarPantalla(generarPantallaVictoria(puntosFinales));
        }
        try {
            GestorEstadisticas gestor = new GestorEstadisticas();
            String idPartida = gestor.obtenerSiguienteId();
            RegistroPartida registro = new RegistroPartida(idPartida, heroe.getNombre(), heroe.getClass().getSimpleName(), jefe.getNombre(), jefe.getClass().getSimpleName(), puntosFinales);
            gestor.guardarPartida(registro);
        } catch (Exception e) {
            registrarLog("Error al procesar o guardar las estadísticas en el archivo XML.");
        }
    }

    private String generarPantallaVictoria(int puntos) {
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
                "         ¡VICTORIA ÉPICA!      \n" +
                "         PUNTUACIÓN: " + puntos + "      \n"+
                "          [Pulsa ENTER]          ";
    }

    private String generarPantallaDerrota() {
        String nombre = this.heroe.getNombre();
        int espaciosTotales = 15 - nombre.length();
        int espaciosIzquierda = espaciosTotales / 2;
        int espaciosDerecha = espaciosTotales - espaciosIzquierda;
        String nombreCentrado = " ".repeat(espaciosIzquierda) + nombre + " ".repeat(espaciosDerecha);

        return "\n\n" +
                "           .---------.           \n" +
                "          /           \\          \n" +
                "         /    R.I.P    \\         \n" +
                "        |               |        \n" +
                "        |" + nombreCentrado + "|        \n" +
                "        |               |        \n" +
                "      .-|               |-.      \n" +
                "      \\-------------------/      \n" +
                "          GAME OVER              \n" +
                "       Has sido derrotado        \n"+
                "         [Pulsa ENTER]            ";
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
        this.turnosTotales = 0;
        tablero.generarMapaAleatorio();
        ventana.limpiarHistorial();
        ventana.cambiarColorMapa(Color.GREEN);
        int pocionesIniciales = rand.nextInt(3);
        for(int i = 0; i < pocionesIniciales; i++){
            tablero.generarPocionAleatoria();
        }
        turnosParaNuevaPocion = rand.nextInt(5) + 4;
        contadorTurnosPocion = 0;
        heroe.reiniciar();
        jefe.reiniciar();
        heroe.reiniciarMovimiento();
        for (Controlador c : controladores) {
            if (c instanceof Maquina) {
                ((Maquina) c).reiniciarControlador();
            }
        }
        int[] posHeroe = tablero.buscarCasillaVaciaAleatoria();
        int[] posJefe;
        do {
            posJefe = tablero.buscarCasillaVaciaAleatoria();
        } while (posJefe[0] == posHeroe[0] && posJefe[1] == posHeroe[1]);
        tablero.colocarEntidad(heroe, posHeroe[0], posHeroe[1]);
        tablero.colocarEntidad(jefe, posJefe[0], posJefe[1]);
        this.ventana.actualizarPantalla(actualizarMapa());
        this.refrescarInterfaz();
        registrarLog("SISTEMA: INICIANDO NUEVA SIMULACIÓN...");
    }

    private void configurarNuevaPartida(Entidad nuevaClase) {
        String[]nombres={"Vaarun", "Orionis", "Malakor", "Krux", "Bazaal"};
        int indiceNombre = rand.nextInt(nombres.length);
        String nombre = nombres[indiceNombre];
        int claseJefe = rand.nextInt(3);
        switch (claseJefe){
            case 0:
                this.jefe = new Guerrero(0,0, nombre);
                break;
            case 1:
                this.jefe = new Paladin(0,0, nombre);
                break;
            case 2:
                this.jefe = new Rogue(0,0, nombre);
                break;
        }
        this.heroe = nuevaClase;
        this.controladores.clear();
        this.controladores.add(new Jugador(this.heroe));
        this.controladores.add(new Maquina(this.jefe, this.heroe));
        this.enSeleccion = false;
        this.enMenu = false;
        this.enIntroduccionNombre = false;
        reiniciarPartida();
        ventana.mostrarJuego();
    }
}
