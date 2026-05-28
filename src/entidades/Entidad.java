package entidades;


import excepciones.AtaqueInvalidoException;

/**
 * Clase base abstracta para todos los personajes interactivos (héroes y enemigos) del juego.
 * Gestiona estadísticas principales, lógica de daños, combates y reglas de movimiento.
 */
public abstract class Entidad extends  ObjetoMapa{
    private String nombre;
    private  int vidaMax;
    private int vidaActual;
    private int defensa;
    private int rangoMovimiento;
    private int movimientoActual;
    protected int dañoBase;
    private boolean estaVivo;

    /**
     * Construye una nueva Entidad con sus estadísticas iniciales completas.
     *
     * @param posX Posición X inicial en el tablero.
     * @param posY Posición Y inicial en el tablero.
     * @param nombre Nombre identificativo del personaje o jefe.
     * @param vidaMax Puntos máximos de salud.
     * @param defensa Porcentaje de reducción de daño físico recibido.
     * @param rangoMovimiento Número de casillas máximas que puede recorrer en un solo turno.
     * @param dañoBase Poder ofensivo base antes de modificadores.
     */
    public Entidad(int posX, int posY, String nombre, int vidaMax, int defensa, int rangoMovimiento, int dañoBase) {
        super(posX, posY);
        this.nombre = nombre;
        this.vidaMax = vidaMax;
        this.vidaActual = vidaMax;
        this.defensa = defensa;
        this.rangoMovimiento = rangoMovimiento;
        this.movimientoActual = rangoMovimiento;
        this.dañoBase = dañoBase;
        this.estaVivo = true;
    }

    /**
     * Procesa un impacto físico restando la mitigación de la defensa y reduce la vida actual.
     * Modifica el estado de vida a "muerto" si la salud llega a 0.
     *
     * @param daño La cantidad de daño físico bruto entrante.
     */
    public void recibirDaño(int daño){
        int dañoFinal=daño-Math.round((float)(daño*this.defensa)/100);
        int nuevaVida=this.vidaActual-dañoFinal;
        this.vidaActual=Math.max(0,nuevaVida);
        if(this.vidaActual==0){
            setEstaVivo(false);
        }
    }

    /**
     * Procesa un impacto mágico directo que ignora la estadística de defensa física de la entidad.
     *
     * @param daño La cantidad de daño mágico verdadero entrante.
     */
    public void recibirDañoMagico(int daño){
        int nuevaVida=this.vidaActual-daño;
        this.vidaActual=Math.max(0,nuevaVida);
        if(this.vidaActual==0){
            setEstaVivo(false);
        }
    }

    /**
     * Restaura los puntos de salud del personaje asegurando que nunca sobrepasen su vida máxima.
     *
     * @param cura Cantidad numérica de salud a restaurar.
     */
    public void curar(int cura){
        setVidaActual(Math.min(this.vidaActual+cura,this.vidaMax));
    }

    /**
     * Incrementa permanentemente el daño base de la entidad.
     *
     * @param aumento Valor a sumar al daño físico bruto.
     */
    public void bufarAtaque(int aumento){
        setDañoBase(dañoBase+aumento);
    }

    /**
     * Incrementa permanentemente el porcentaje de defensa de la entidad.
     *
     * @param aumento Valor porcentual a sumar a la defensa base.
     */
    public void bufarDefensa(int aumento){
        setDefensa(this.defensa + aumento);
    }

    /**
     * Obtiene el daño efectivo a realizar en el combate en función de los estados activos de la clase.
     *
     * @return El cálculo del daño final listo para aplicarse a la defensa del objetivo.
     */
    public int obtenerDañoReal(){
        return dañoBase;
    }

    /**
     * Verifica si el objetivo es válido para realizar un ataque (está vivo y adyacente a máximo 1 casilla).
     *
     * @param objetivo La entidad a evaluar como blanco de ataque.
     * @return true si cumple los criterios físicos para ser atacado, false en caso contrario.
     */
    public boolean puedeAtacar(Entidad objetivo){
        if(!objetivo.getEstaVivo()){
            return false;
        }
        if(distanciaA(objetivo)>1){
            return false;
        }
        return true;
    }

    /**
     * Ejecuta la rutina estándar de ataque contra una entidad enemiga, calculando la vida perdida del objetivo.
     *
     * @param objetivo La entidad que recibirá el ataque.
     * @return Un resumen del combate en texto para imprimir en el historial de la pantalla.
     * @throws AtaqueInvalidoException Si el enemigo está muerto o fuera de alcance.
     */
    public String atacar(Entidad objetivo) throws AtaqueInvalidoException {
        if (!puedeAtacar(objetivo)) {
            throw new excepciones.AtaqueInvalidoException("El enemigo está fuera de rango o la línea de visión está bloqueada.");
        }
        if (!objetivo.getEstaVivo()) {
            throw new excepciones.AtaqueInvalidoException("¡El enemigo ya está derrotado!");
        }
        int vidaAntes = objetivo.getVidaActual();
        objetivo.recibirDaño(obtenerDañoReal());
        int dañoCausado = vidaAntes-objetivo.getVidaActual();
        return this.getNombre()+" ataca a "+objetivo.getNombre()+" y causa "+dañoCausado+" de daño.";
    }

    /**
     * Evalúa si a la entidad le quedan puntos de movimiento en el turno actual.
     *
     * @return true si tiene puntos de movimiento disponibles.
     */
    public boolean puedeMoverse(){
        if(movimientoActual<=0){
            return false;
        }
        return true;
    }

    /**
     * Resta un punto del presupuesto de movimiento del turno actual de la entidad.
     */
    public void gastarMovimiento(){
        if(puedeMoverse()){
            movimientoActual--;
        }
    }

    /**
     * Restaura por completo los puntos de movimiento del personaje (se usa al inicio de su turno).
     */
    public void reiniciarMovimiento(){
        movimientoActual=rangoMovimiento;
    }

    /**
     * Ejecuta el efecto especial o habilidad única activada por el jugador o la IA.
     *
     * @return Mensaje descriptivo de la habilidad usada.
     */
    public abstract String usarHabilidad();

    /**
     * Revisa y procesa temporizadores de estados alterados, mermas o ventajas al inicio del turno.
     *
     * @return Mensaje si el estado del personaje caduca o sufre cambios, o una cadena vacía.
     */
    public abstract String actualizarEstado();

    /**
     * @return Una etiqueta de texto breve indicando la condición actual (ej: "Normal", "¡RABIOSO!").
     */
    public abstract String obtenerEstado();

    /**
     * Devuelve todas las estadísticas y estados internos a su valor por defecto de inicio de simulación.
     */
    public abstract void reiniciar();

    //Getters&Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getRangoMovimiento() {
        return rangoMovimiento;
    }

    public void setRangoMovimiento(int rangoMovimiento) {
        this.rangoMovimiento = rangoMovimiento;
    }

    public int getMovimientoActual() {
        return movimientoActual;
    }

    public void setMovimientoActual(int movimientoActual) {
        this.movimientoActual = movimientoActual;
    }

    public int getDañoBase() {
        return dañoBase;
    }

    public void setDañoBase(int dañoBase) {
        this.dañoBase = dañoBase;
    }

    public boolean getEstaVivo() {
        return estaVivo;
    }

    public void setEstaVivo(boolean estaVivo) {
        this.estaVivo = estaVivo;
    }
}
