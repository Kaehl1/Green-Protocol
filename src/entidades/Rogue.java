package entidades;


public class Rogue extends Entidad{
    public Rogue(int posX, int posY, String nombre) {
        super(posX, posY, nombre, 80, 10, 4, 35);
    }

    @Override
    public String atacar(Entidad objetivo) {
        if(puedeAtacar(objetivo)){
            // --- PRIMER GOLPE ---
            int vidaAntesPrimero = objetivo.getVidaActual(); // Miramos la vida antes de tocarle
            int dañoPrimero = (int) (this.dañoBase * 0.60);
            objetivo.recibirDaño(dañoPrimero);
            int dañoCausadoPrimero = vidaAntesPrimero - objetivo.getVidaActual(); // Calculamos el daño real del primer tajo
            String reporte = this.getNombre() + " asesta un tajo rápido a " + objetivo.getNombre() + " (" + dañoCausadoPrimero + " daño).";// Preparamos la primera parte del mensaje
            if(!objetivo.getEstaVivo()){
                return reporte + " ¡" + objetivo.getNombre() + " cae fulminado!"; // Si el enemigo muere con este primer golpe, no damos el segundo. Devolvemos el texto y paramos.
            }
            // --- SEGUNDO GOLPE ---
            int vidaAntesSegurndo = objetivo.getVidaActual(); // Volvemos a mirar la vida
            int dañoSegundo = (int) (this.dañoBase * 0.45);
            objetivo.recibirDaño(dañoSegundo);
            int dañoCausadoSegundo = vidaAntesSegurndo - objetivo.getVidaActual(); // Calculamos el daño real del segundo tajo
            reporte = reporte + "\n > " + this.getNombre() + " remata con un segundo golpe (" + dañoCausadoSegundo + " daño)."; // Sumamos el segundo golpe al texto original
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
