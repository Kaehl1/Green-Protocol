package controladores;

import entidades.Entidad;
import escenario.Direccion;
import escenario.Tablero;
import excepciones.AtaqueInvalidoException;
import excepciones.MovimientoInvalidoException;

/**
 * Controlador que representa la Inteligencia Artificial del juego.
 * Se encarga de manejar el comportamiento automático, el cálculo de rutas y la toma de decisiones del jefe enemigo.
 */
public class Maquina implements Controlador {
    private Entidad personaje;
    private Entidad objetivo;
    private boolean faseActiva;

    /**
     * Construye un nuevo controlador para la inteligencia artificial.
     *
     * @param personaje La entidad que actuará como enemigo y será controlada por la máquina.
     * @param objetivo La entidad del jugador humano a la que la IA debe perseguir y atacar.
     */
    public Maquina(Entidad personaje, Entidad objetivo) {
        this.personaje = personaje;
        this.objetivo = objetivo;
        this.faseActiva = false;
    }

    /**
     * Evalúa el estado inicial del turno de la máquina y actualiza la condición del jefe.
     * Restablece el movimiento y activa la habilidad especial automáticamente si la vida cae por debajo del 70%.
     *
     * @param tablero El escenario actual del juego.
     * @return Un mensaje indicando la actualización del estado o la activación de la habilidad especial para el historial.
     */
    @Override
    public String ejecutarTurno(Tablero tablero) {
        personaje.reiniciarMovimiento();
        String mensaje = personaje.actualizarEstado();
        if (personaje.getVidaActual() <= (personaje.getVidaMax() * 0.7) && !this.faseActiva) {
            mensaje = personaje.usarHabilidad();
            faseActiva = true;
        }
        return mensaje;
    }

    /**
     * Calcula y ejecuta el siguiente paso de movimiento del jefe hacia el héroe.
     * Determina la dirección óptima y mueve a la entidad si dispone de puntos de movimiento y no está a rango de ataque.
     *
     * @param tablero El escenario actual del juego para comprobar colisiones y efectuar el movimiento.
     * @return true si la máquina se ha movido exitosamente una casilla; false si ha fallado por colisión, falta de movimiento o ya está a rango.
     */
    public boolean intentarMover(Tablero tablero) {
        if(personaje.getMovimientoActual()>0&&!personaje.puedeAtacar(objetivo)) {
            Direccion dir = personaje.direccionHacia(objetivo);
            try {
                boolean movido = tablero.moverEntidad(personaje, dir);
                if (movido) {
                    personaje.gastarMovimiento();
                    return true;
                }
            }catch (MovimientoInvalidoException e){
                return false;
            }
        }
        return false;
    }

    /**
     * Ejecuta la acción ofensiva de la máquina contra el héroe si se encuentra en el rango y posición adecuada.
     *
     * @return Un texto descriptivo con el resultado del ataque para mostrar en el historial, o cadena vacía si no puede atacar.
     * @throws AtaqueInvalidoException Si el ataque no se puede realizar debido a validaciones internas (ej. fuera de rango o un muro bloquea la visión).
     */
    public String ejecutarAtaque() throws AtaqueInvalidoException {
        if (personaje.puedeAtacar(objetivo)) {
            return personaje.atacar(objetivo);
        }
        return "";
    }

    /**
     * Restablece las variables internas y la fase de la Inteligencia Artificial
     * al comenzar una nueva simulación o partida.
     */
    public void reiniciarControlador() {
        this.faseActiva = false;
    }
}
