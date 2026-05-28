package entidades;

/**
 * Representa una poción o consumible en el mapa que otorga beneficios temporales o permanentes a las entidades.
 */
public class Item extends ObjetoMapa{
    private EfectoPocion efecto;
    private int cantidad;

    /**
     * Construye un nuevo ítem en el tablero.
     *
     * @param posX Coordenada X del ítem en el mapa.
     * @param posY Coordenada Y del ítem en el mapa.
     * @param efecto El tipo de bonificador que aplicará (VIDA, ATAQUE o DEFENSA).
     * @param cantidad La magnitud numérica del beneficio otorgado.
     */
    public Item(int posX, int posY, EfectoPocion efecto, int cantidad) {
        super(posX, posY);
        this.efecto = efecto;
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el icono visual correspondiente al tipo de poción.
     *
     * @return Símbolo gráfico en formato de cadena de texto dependiendo de su efecto.
     */
    @Override
    public String representar() {
        if (efecto == EfectoPocion.VIDA) {
            return "❤";
        }
        if (efecto==EfectoPocion.ATAQUE) {
            return "⚗";
        }
        return "⛉";
    }

    /**
     * Transfiere el beneficio numérico del ítem a las estadísticas de la entidad objetivo.
     *
     * @param objetivo La entidad (héroe o jefe) que recoge o consume la poción.
     */
    public void aplicarEfecto(Entidad objetivo){
        switch (this.efecto){
            case VIDA:
                objetivo.curar(this.cantidad);
                break;
            case ATAQUE:
                objetivo.bufarAtaque(this.cantidad);
                break;
            case DEFENSA:
                objetivo.bufarDefensa(this.cantidad);
                break;
        }
    }

    //Getters&Setters
    public EfectoPocion getEfecto() {
        return efecto;
    }

    public void setEfecto(EfectoPocion efecto) {
        this.efecto = efecto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
