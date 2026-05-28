package entidades;

/**
 * Clase que representa el arquetipo de velocidad y emboscada.
 * Destaca por un movimiento superior a la media y su habilidad pasiva que garantiza siempre un doble golpe por turno de ataque.
 */
public class Rogue extends Entidad{

    /**
     * Construye un nuevo Pícaro (Rogue) con estadísticas predefinidas (Vida: 80, Def: 10, Mov: 4, Daño: 35).
     *
     * @param posX Coordenada X inicial en el tablero.
     * @param posY Coordenada Y inicial en el tablero.
     * @param nombre Nombre del pícaro.
     */
    public Rogue(int posX, int posY, String nombre) {
        super(posX, posY, nombre, 80, 10, 4, 35);
    }

    /**
     * Sobrescribe el ataque estándar para ejecutar una habilidad pasiva de "Doble Golpe".
     * Realiza un primer tajo equivalente al 60% de su Daño Base, y si el objetivo sobrevive,
     * remata inmediatamente con un segundo impacto del 45% del Daño Base.
     *
     * @param objetivo La entidad que sufre la secuencia de ataques rápidos.
     * @return Un bloque de texto narrando cada golpe en secuencia y el daño exacto propinado.
     */
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

    /**
     * Habilidad manual del Pícaro. Al depender completamente de su pasiva, esta clase carece de habilidad activable.
     *
     * @return Una cadena vacía, ya que no se ejerce ninguna acción extra.
     */
    public String usarHabilidad(){
        return "";
    }

    /**
     * La clase carece de mermas o modificadores basados en turnos.
     *
     * @return Una cadena vacía.
     */
    public String actualizarEstado(){
        return "";
    }

    /**
     * @return La leyenda de su rasgo pasivo perenne en la barra de información.
     */
    public String obtenerEstado(){
        return "¡Ataque Doble!";
    }

    /**
     * Restablece la salud al máximo al iniciar un nuevo combate.
     */
    @Override
    public void reiniciar() {
        this.setVidaActual(this.getVidaMax());
        this.setEstaVivo(true);
    }

    /**
     * @return El símbolo que identifica de manera visual al Rogue/Pícaro en la matriz del escenario.
     */
    @Override
    public String representar() {
        return "☠";
    }
}
