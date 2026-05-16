package entidades;

public class Item extends ObjetoMapa{
    private EfectoPocion efecto;
    private int cantidad;

    public Item(int posX, int posY, EfectoPocion efecto, int cantidad) {
        super(posX, posY);
        this.efecto = efecto;
        this.cantidad = cantidad;
    }

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
