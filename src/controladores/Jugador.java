package controladores;

import entidades.Entidad;
import escenario.Direccion;
import escenario.Tablero;
import excepciones.MovimientoInvalidoException;

import java.awt.event.KeyEvent;

/**
 * Controlador encargado de traducir las interacciones del usuario (teclado)
 * en acciones dentro del juego para el héroe.
 */
public class Jugador implements Controlador {
    private Entidad personaje;

    /**
     * Construye un nuevo controlador para el jugador humano.
     *
     * @param personaje La entidad que será controlada por el jugador.
     */
    public Jugador(Entidad personaje) {
        this.personaje = personaje;
    }

    /**
     * Inicia el turno del jugador y actualiza la condición inicial del personaje.
     * Aunque la acción de combate o movimiento queda a la espera de la entrada manual por teclado,
     * este método procesa cualquier cambio de estado al empezar la ronda.
     *
     * @param tablero El escenario actual del juego.
     * @return Un texto descriptivo con la actualización del estado del héroe, o cadena vacía si no hay cambios relevantes.
     */
    @Override
    public String ejecutarTurno(Tablero tablero) {
        personaje.reiniciarMovimiento();
        return personaje.actualizarEstado();
    }

    /**
     * Procesa la tecla pulsada por el usuario y ejecuta el movimiento correspondiente.
     * Convierte las pulsaciones de las teclas WASD en desplazamientos físicos en el mapa.
     *
     * @param codigoTecla El código numérico de la tecla pulsada (ej. KeyEvent.VK_W para moverse al Norte).
     * @param tablero El escenario actual del juego para evaluar colisiones e interactuables.
     * @throws MovimientoInvalidoException Si el jugador intenta moverse a una casilla no transitable o fuera de los límites del mapa.
     */
    public void procesarEntrada(int codigoTecla, Tablero tablero) throws MovimientoInvalidoException {
        if(codigoTecla == KeyEvent.VK_W || codigoTecla == KeyEvent.VK_S || codigoTecla == KeyEvent.VK_A || codigoTecla == KeyEvent.VK_D){
            if(personaje.getMovimientoActual()>0){
                boolean movido = false;
                switch (codigoTecla) {
                    case KeyEvent.VK_W:
                        movido=tablero.moverEntidad(personaje, Direccion.NORTE);
                        break;
                    case KeyEvent.VK_S:
                        movido=tablero.moverEntidad(personaje, Direccion.SUR);
                        break;
                    case KeyEvent.VK_A:
                        movido=tablero.moverEntidad(personaje, Direccion.OESTE);
                        break;
                    case KeyEvent.VK_D:
                        movido=tablero.moverEntidad(personaje, Direccion.ESTE);
                        break;
                }
                if(movido){
                    personaje.gastarMovimiento();
                }
            }
        }
    }
}