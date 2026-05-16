package entidades;


public class Guerrero extends Entidad{
    private boolean estaRabioso;
    public int turnosRabia;

    public Guerrero(int posX, int posY, String nombre) {
        super(posX, posY, nombre, 120, 15, 3, 25);
        estaRabioso=false;
        turnosRabia=2;
    }

    public void rabiar(){
        this.estaRabioso=true;
    }

    @Override
    public String usarHabilidad() {
        this.rabiar();
        return "¡"+getNombre()+" DESATA TODA SU RABIA! Ten cuidado.";
    }
    @Override
    public String actualizarEstado() {
        if (estaRabioso) {
            turnosRabia--;
            if (turnosRabia <= 0) {
                estaRabioso = false;
                turnosRabia = 2; // Lo reiniciamos por si vuelve a usar la habilidad
                return"¡" + getNombre() + " se ha calmado! Su daño vuelve a la normalidad.";
            }
        }
        return "";
    }

    public String obtenerEstado() {
        return estaRabioso ? "¡RABIOSO!(Quedan "+turnosRabia+" turnos)" : "Normal";
    }

    @Override
    public void reiniciar() {
        this.estaRabioso = false;
        this.turnosRabia = 2;
        this.setVidaActual(this.getVidaMax());
        this.setEstaVivo(true);
    }

    @Override
    public int obtenerDañoReal() {
        if(estaRabioso){
            return dañoBase*2;
        }
        return dañoBase;
    }

    @Override
    public void recibirDaño(int daño){
        if(estaRabioso){
            daño=daño*2;
        }
        super.recibirDaño(daño);
    }

    @Override
    public String representar() {
        return "〴";//〴
    }
}
