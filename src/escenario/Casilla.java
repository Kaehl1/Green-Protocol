package escenario;

import entidades.Entidad;
import entidades.Item;

/**
 * Representa una unidad de espacio individual (celda) dentro de la cuadrícula del tablero.
 * Puede contener personajes, objetos consumibles y definir si es transitable o no.
 */
public class Casilla {
    private boolean esTransitable;
    private Entidad personaje;
    private Item objeto;

    /**
     * Construye una casilla vacía y transitable por defecto.
     */
    public Casilla (){
        this.personaje=null;
        this.objeto=null;
        this.esTransitable=true;
    }

    /**
     * Evalúa si la casilla está disponible para que una entidad se mueva a ella.
     *
     * @return true si la casilla es transitable y no está ocupada por ningún personaje; false en caso contrario.
     */
    public boolean estaLibre(){
        if(this.personaje!=null){
            return false;
        }
        if(!this.esTransitable){
            return false;
        }
        return true;
    }

    /**
     * Devuelve el símbolo visual adecuado para representar el estado actual de la casilla.
     *
     * @return El icono del personaje, el icono del objeto, el icono de obstáculo o el icono de suelo vacío, según la prioridad.
     */
    public String representar(){
        if(this.personaje!=null){
            return personaje.representar();
        }
        if(this.objeto!=null){
            return objeto.representar();
        }
        if(!this.esTransitable){
            return "⛰";
        }
        return "⛶";
    }

    //Getters&Setters
    public boolean isEsTransitable() {
        return esTransitable;
    }

    public void setEsTransitable(boolean esTransitable) {
        this.esTransitable = esTransitable;
    }

    public Entidad getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Entidad personaje) {
        this.personaje = personaje;
    }

    public Item getObjeto() {
        return objeto;
    }

    public void setObjeto(Item objeto) {
        this.objeto = objeto;
    }
}
