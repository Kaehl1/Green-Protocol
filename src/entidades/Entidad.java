package entidades;


import excepciones.AtaqueInvalidoException;

public abstract class Entidad extends  ObjetoMapa{
    private String nombre;
    private  int vidaMax;
    private int vidaActual;
    private int defensa;
    private int rangoMovimiento;
    private int movimientoActual;
    protected int dañoBase;
    private boolean estaVivo;

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

    //Ataque, daño y curas
    public void recibirDaño(int daño){
        int dañoFinal=daño-Math.round((float)(daño*this.defensa)/100);
        int nuevaVida=this.vidaActual-dañoFinal;
        this.vidaActual=Math.max(0,nuevaVida);
        if(this.vidaActual==0){
            setEstaVivo(false);
        }
    }

    public void recibirDañoMagico(int daño){
        int nuevaVida=this.vidaActual-daño;
        this.vidaActual=Math.max(0,nuevaVida);
        if(this.vidaActual==0){
            setEstaVivo(false);
        }
    }

    public void curar(int cura){
        setVidaActual(Math.min(this.vidaActual+cura,this.vidaMax));
    }

    public void bufarAtaque(int aumento){
        setDañoBase(dañoBase+aumento);
    }

    public void bufarDefensa(int aumento){
        setDefensa(this.defensa + aumento);
    }

    public int obtenerDañoReal(){
        return dañoBase;
    }

    public boolean puedeAtacar(Entidad objetivo){
        if(!objetivo.getEstaVivo()){
            return false;
        }
        if(distanciaA(objetivo)>1){
            return false;
        }
        return true;
    }

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

    //movimiento
    public boolean puedeMoverse(){
        if(movimientoActual<=0){
            return false;
        }
        return true;
    }

    public void gastarMovimiento(){
        if(puedeMoverse()){
            movimientoActual--;
        }
    }

    public void reiniciarMovimiento(){
        movimientoActual=rangoMovimiento;
    }

    //Estados
    public abstract String usarHabilidad();

    public abstract String actualizarEstado();

    public abstract String obtenerEstado();

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
