package excepciones;

/**
 * Excepción personalizada que se lanza cuando una entidad intenta ejecutar un ataque
 * que incumple las reglas del juego (por ejemplo: objetivo fuera de rango u objetivo ya muerto).
 */
public class AtaqueInvalidoException extends Exception {
    public AtaqueInvalidoException(String message) {
        super(message);
    }
}
