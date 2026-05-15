package controladores;

import entidades.Entidad;
import escenario.Tablero;

public class Jugador extends Controlador {
    public Jugador(Entidad personaje) {
        super(personaje);
    }

    @Override
    public String ejecutarTurno(Tablero tablero) {
        personaje.reiniciarMovimiento();
        return personaje.actualizarEstado();
    }

    public void procesarEntrada(int codigoTecla, Tablero tablero) {
        if(codigoTecla == java.awt.event.KeyEvent.VK_W || codigoTecla == java.awt.event.KeyEvent.VK_S || codigoTecla == java.awt.event.KeyEvent.VK_A || codigoTecla == java.awt.event.KeyEvent.VK_D){
            if(personaje.getMovimientoActual()>0){
                boolean movido = false;
                switch (codigoTecla) {
                    case java.awt.event.KeyEvent.VK_W:
                        movido=tablero.moverEntidad(personaje, escenario.Direccion.NORTE);
                        break;
                    case java.awt.event.KeyEvent.VK_S:
                        movido=tablero.moverEntidad(personaje, escenario.Direccion.SUR);
                        break;
                    case java.awt.event.KeyEvent.VK_A:
                        movido=tablero.moverEntidad(personaje, escenario.Direccion.OESTE);
                        break;
                    case java.awt.event.KeyEvent.VK_D:
                        movido=tablero.moverEntidad(personaje, escenario.Direccion.ESTE);
                        break;
                }
                if(movido){
                    personaje.gastarMovimiento();
                }
            }
        }
    }
}