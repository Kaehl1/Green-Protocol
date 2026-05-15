package entidades;


public class Paladin extends Entidad{
    private boolean estaBendecido;
    public int turnosBendicion;

    public Paladin(int posX, int posY, String nombre) {
        super(posX, posY, nombre, 100, 30, 2, 19);
        estaBendecido = false;
        turnosBendicion = 3;
    }

    public void bendicionMagica(){
        this.estaBendecido = true;
    }

    public String usarHabilidad(){
        this.bendicionMagica();
        return"¡"+getNombre()+" SA BENDESIDO! Ten cuidadito.";
    }

    @Override
    public String actualizarEstado() {
        if (estaBendecido) {
            turnosBendicion--;
            if (turnosBendicion <= 0) {
                estaBendecido = false;
                turnosBendicion = 2; // Lo reiniciamos
                return "¡La bendición de " + getNombre() + " se ha desvanecido!";
            }
        }
        return "";
    }

    public String obtenerEstado(){
        return estaBendecido ? "¡BENDECIDO! (Quedan "+turnosBendicion+" turnos)" : "Normal";
    }

    @Override
    public void reiniciar() {
        this.estaBendecido = false;
        this.turnosBendicion = 2;
        this.setVidaActual(this.getVidaMax());
        this.setEstaVivo(true);
    }

    @Override
    public String atacar(Entidad objetivo) {
        if(estaBendecido){
            if (puedeAtacar(objetivo)) {
                int vidaAntes = objetivo.getVidaActual();
                objetivo.recibirDañoMagico(this.getDañoBase());
                int dañoCausado = vidaAntes-objetivo.getVidaActual();
                return this.getNombre() + " usa luz celestial sobre " + objetivo.getNombre() + " (" + dañoCausado + " daño).";
            }
        }else {
            return super.atacar(objetivo);
        }
        return "";
    }

    @Override
    public String representar() {
        return "☩";
    }
}
