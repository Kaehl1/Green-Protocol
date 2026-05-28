package entidades;

import excepciones.AtaqueInvalidoException;

/**
 * Clase que representa el arquetipo defensivo con daño híbrido.
 * Destaca por su alta defensa y la capacidad de transformar su ataque normal en daño mágico penetrante.
 */
public class Paladin extends Entidad{
    private boolean estaBendecido;
    public int turnosBendicion;

    /**
     * Construye un nuevo Paladín con estadísticas predefinidas (Vida: 100, Def: 30, Mov: 2, Daño: 19).
     *
     * @param posX Coordenada X inicial en el tablero.
     * @param posY Coordenada Y inicial en el tablero.
     * @param nombre Nombre del paladín.
     */
    public Paladin(int posX, int posY, String nombre) {
        super(posX, posY, nombre, 100, 30, 2, 19);
        estaBendecido = false;
        turnosBendicion = 3;
    }

    /**
     * Activa el estado interno de bendición permitiendo que los próximos ataques ignoren la defensa rival.
     */
    public void bendicionMagica(){
        this.estaBendecido = true;
    }

    /**
     * Invoca la habilidad especial de la clase, aplicando el estado de Bendición.
     *
     * @return El mensaje temático de invocación de la Luz.
     */
    public String usarHabilidad(){
        this.bendicionMagica();
        return"¡La Luz bendice a "+getNombre()+"! Ten cuidado.";
    }

    /**
     * Descuenta los turnos restantes de la habilidad al iniciar la ronda, restableciéndola a la normalidad si llega a cero.
     *
     * @return Aviso de que el efecto mágico ha concluido, o cadena vacía en caso de que aún persista.
     */
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

    /**
     * @return El resumen del estado actual y duración de la bendición para mostrar en la interfaz de usuario.
     */
    public String obtenerEstado(){
        return estaBendecido ? "¡BENDECIDO! (Quedan "+turnosBendicion+" turnos)" : "Normal";
    }

    /**
     * Devuelve al Paladín a sus variables por defecto, limpiando modificadores y restaurando salud.
     */
    @Override
    public void reiniciar() {
        this.estaBendecido = false;
        this.turnosBendicion = 2;
        this.setVidaActual(this.getVidaMax());
        this.setEstaVivo(true);
    }

    /**
     * Ejecuta el golpe sobre el objetivo. Si el personaje está bajo la Bendición Mágica, el golpe inflige
     * daño mágico directo (ignorando mitigación de defensa); si no, realiza un ataque físico normal.
     *
     * @param objetivo La entidad enemiga que recibirá el impacto.
     * @return Un registro descriptivo de la acción y el daño final efectuado.
     * @throws AtaqueInvalidoException Si el ataque se intenta lanzar fuera de rango o hacia un ente destruido.
     */
    @Override
    public String atacar(Entidad objetivo) throws AtaqueInvalidoException {
        if(estaBendecido){
            if (puedeAtacar(objetivo)) {
                int vidaAntes = objetivo.getVidaActual();
                objetivo.recibirDañoMagico(this.getDañoBase());
                int dañoCausado = vidaAntes-objetivo.getVidaActual();
                return this.getNombre() + " usa su arma resplandeciente sobre " + objetivo.getNombre() + " (" + dañoCausado + " daño).";
            }
        }else {
            return super.atacar(objetivo);
        }
        return "";
    }

    /**
     * @return El símbolo que representa gráficamente al Paladín en el tablero.
     */
    @Override
    public String representar() {
        return "☩";
    }
}
