package datos;

public class RegistroPartida {
    private String id;
    private String nombreHeroe;
    private String claseHeroe;
    private String nombreJefe;
    private String claseJefe;
    private int puntos;

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
