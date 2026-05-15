package escenario;

import entidades.Entidad;
import entidades.Item;

public class Casilla {
    private boolean esTransitable;
    private Entidad personaje;
    private Item objeto;

    public Casilla (){
        this.personaje=null;
        this.objeto=null;
        this.esTransitable=true;
    }

    public boolean estaLibre(){
        if(this.personaje!=null){
            return false;
        }
        if(!this.esTransitable){
            return false;
        }
        return true;
    }

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
