package entidades;


public class Rogue extends Entidad{
    public Rogue(int posX, int posY, String nombre) {
        super(posX, posY, nombre, 80, 10, 4, 35);
    }

    @Override
    public String atacar(Entidad objetivo) {
        if(puedeAtacar(objetivo)){
            //PRIMER GOLPE
            int vidaAntesPrimero = objetivo.getVidaActual();
            int dañoPrimero = (int) (this.dañoBase * 0.60);
            objetivo.recibirDaño(dañoPrimero);
            int dañoCausadoPrimero = vidaAntesPrimero - objetivo.getVidaActual();
            String reporte = this.getNombre() + " asesta un tajo rápido a " + objetivo.getNombre() + " (" + dañoCausadoPrimero + " daño).";
            if(!objetivo.getEstaVivo()){
                return reporte + " ¡" + objetivo.getNombre() + " cae fulminado!";
            }
            //SEGUNDO GOLPE
            int vidaAntesSegurndo = objetivo.getVidaActual();
            int dañoSegundo = (int) (this.dañoBase * 0.45);
            objetivo.recibirDaño(dañoSegundo);
            int dañoCausadoSegundo = vidaAntesSegurndo - objetivo.getVidaActual();
            reporte = reporte + "\n > " + this.getNombre() + " remata con un segundo golpe (" + dañoCausadoSegundo + " daño).";
            return reporte;
        }
        return "";
    }

    public String usarHabilidad(){
        return "";
    }

    public String actualizarEstado(){
        return "";
    }

    public String obtenerEstado(){
        return "¡Ataque Doble!";
    }

    @Override
    public void reiniciar() {
        this.setVidaActual(this.getVidaMax());
        this.setEstaVivo(true);
    }

    @Override
    public String representar() {
        return "☠";
    }
}
