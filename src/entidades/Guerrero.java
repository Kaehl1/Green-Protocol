package entidades;

/**
 * Clase que representa el arquetipo de combate cuerpo a cuerpo pesado.
 * Destaca por su salud y habilidad de entrar en un estado de rabia temporal.
 */
public class Guerrero extends Entidad{
    private boolean estaRabioso;
    public int turnosRabia;

    /**
     * Construye un nuevo Guerrero con estadísticas predefinidas (Vida: 120, Def: 15, Mov: 2, Daño: 22).
     *
     * @param posX Coordenada X inicial en el tablero.
     * @param posY Coordenada Y inicial en el tablero.
     * @param nombre Nombre del guerrero.
     */
    public Guerrero(int posX, int posY, String nombre) {
        super(posX, posY, nombre, 120, 15, 2, 22);
        estaRabioso=false;
        turnosRabia=2;
    }

    /**
     * Activa el modificador interno que duplica el daño que recibe e incrementa su daño infligido.
     */
    public void rabiar(){
        this.estaRabioso=true;
    }

    /**
     * Invoca la habilidad especial de la clase, aplicando el estado de Rabia.
     *
     * @return El mensaje de grito de guerra del personaje.
     */
    @Override
    public String usarHabilidad() {
        this.rabiar();
        return "¡"+getNombre()+" DESATA TODA SU RABIA! Ten cuidado.";
    }

    /**
     * Administra la duración de la habilidad Rabia al comienzo de cada turno, apagándola si expira.
     *
     * @return Mensaje indicando que el guerrero se ha calmado, o cadena vacía si continúa en su estado actual.
     */
    @Override
    public String actualizarEstado() {
        if (estaRabioso) {
            turnosRabia--;
            if (turnosRabia <= 0) {
                estaRabioso = false;
                turnosRabia = 2;
                return"¡" + getNombre() + " se ha calmado! Su daño vuelve a la normalidad.";
            }
        }
        return "";
    }

    /**
     * @return Texto con la condición actual y los turnos restantes de la Rabia para la interfaz.
     */
    public String obtenerEstado() {
        return estaRabioso ? "¡RABIOSO!(Quedan "+turnosRabia+" turnos)" : "Normal";
    }

    /**
     * Devuelve al Guerrero a su estado inicial, restableciendo salud, estados de Rabia y estado vital.
     */
    @Override
    public void reiniciar() {
        this.estaRabioso = false;
        this.turnosRabia = 2;
        this.setVidaActual(this.getVidaMax());
        this.setEstaVivo(true);
    }

    /**
     * Calcula el daño ofensivo aplicando un multiplicador de x1.5 si el estado Rabioso está activo.
     *
     * @return El daño ofensivo definitivo a calcular contra el oponente.
     */
    @Override
    public int obtenerDañoReal() {
        if(estaRabioso){
            return (int)(dañoBase*1.5);
        }
        return dañoBase;
    }

    /**
     * Procesa el daño recibido. Si el guerrero está bajo los efectos de la rabia, recibe el doble de daño antes de aplicar su defensa.
     *
     * @param daño Daño físico bruto entrante.
     */
    @Override
    public void recibirDaño(int daño){
        if(estaRabioso){
            daño=daño*2;
        }
        super.recibirDaño(daño);
    }

    /**
     * @return El símbolo que representa gráficamente al Guerrero en el tablero.
     */
    @Override
    public String representar() {
        return "〴";
    }
}
