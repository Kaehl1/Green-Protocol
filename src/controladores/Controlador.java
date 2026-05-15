package controladores;

import entidades.Entidad;
import escenario.Tablero;

public abstract class Controlador {
    protected Entidad personaje;

    public Controlador(Entidad personaje) {
        this.personaje = personaje;
    }

    public abstract String ejecutarTurno(Tablero tablero);

    //Getters&Setters
    public Entidad getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Entidad personaje) {
        this.personaje = personaje;
    }
}
