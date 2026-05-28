package datos;

/**
 * Clase modelo de tipo Plain Old Java Object (POJO) utilizada para empaquetar
 * y transportar los datos resultantes de una partida al finalizar el combate.
 */
public class RegistroPartida {
    private String id;
    private String nombreHeroe;
    private String claseHeroe;
    private String nombreJefe;
    private String claseJefe;
    private int puntos;

    /**
     * Constructor que inicializa y empaqueta toda la información relevante de la sesión de juego.
     *
     * @param id Identificador secuencial único de la partida.
     * @param nombreHeroe Nombre personalizado elegido por el jugador.
     * @param claseHeroe Clase seleccionada por el jugador.
     * @param nombreJefe Nombre asignado aleatoriamente al jefe enemigo.
     * @param claseJefe Clase aleatoria generada para el jefe enemigo.
     * @param puntos Puntuación final calculada en función de los turnos consumidos.
     */
    public RegistroPartida(String id, String nombreHeroe, String claseHeroe, String nombreJefe, String claseJefe, int puntos) {
        this.id = id;
        this.nombreHeroe = nombreHeroe;
        this.claseHeroe = claseHeroe;
        this.nombreJefe = nombreJefe;
        this.claseJefe = claseJefe;
        this.puntos = puntos;
    }

    //Getters
    public String getId() {
        return id;
    }

    public String getNombreHeroe() {
        return nombreHeroe;
    }

    public String getClaseHeroe() {
        return claseHeroe;
    }

    public String getNombreJefe() {
        return nombreJefe;
    }

    public String getClaseJefe() {
        return claseJefe;
    }

    public int getPuntos() {
        return puntos;
    }
}
