package controladores;

import escenario.Tablero;

/**
 * Interfaz que define el comportamiento básico de cualquier entidad que pueda interactuar en el tablero.
 * Garantiza que tanto el jugador humano como la inteligencia artificial tengan un método común para jugar su turno.
 */
public interface Controlador {

    /**
     * Procesa la lógica correspondiente al turno de la entidad.
     *
     * @param tablero El escenario actual del juego donde se evalúan las posiciones y colisiones.
     * @return Un texto descriptivo con la acción realizada para imprimirlo en el historial, o null si no hay acción relevante.
     */
    String ejecutarTurno(Tablero tablero);
}
