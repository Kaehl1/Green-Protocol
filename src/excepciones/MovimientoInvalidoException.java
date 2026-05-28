package excepciones;

/**
 * Excepción personalizada que se lanza cuando una entidad intenta desplazarse
 * a una coordenada prohibida (fuera de los límites del tablero, hacia un muro o hacia una casilla ocupada).
 */
public class MovimientoInvalidoException extends Exception {
    public MovimientoInvalidoException(String message) {
        super(message);
    }
}
