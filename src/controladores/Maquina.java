package controladores;

import entidades.Entidad;
import escenario.Direccion;
import escenario.Tablero;

public class Maquina extends Controlador {
    private Entidad objetivo;
    private boolean faseActiva;
    public Maquina(Entidad personaje, Entidad objetivo) {
        super(personaje);
        this.objetivo = objetivo;
        this.faseActiva = false;
    }

    @Override
    public String ejecutarTurno(Tablero tablero) {
        personaje.reiniciarMovimiento();
        String mensaje = personaje.actualizarEstado();
        if (personaje.getVidaActual() <= (personaje.getVidaMax() * 0.7) && !this.faseActiva) {
            mensaje = personaje.usarHabilidad();
            faseActiva = true;
        }
        return mensaje;
    }

    public boolean intentarMover(Tablero tablero) {
        if(personaje.getMovimientoActual()>0&&!personaje.puedeAtacar(objetivo)) {
            Direccion dir = personaje.direccionHacia(objetivo);
            boolean movido=tablero.moverEntidad(personaje, dir);
            if (movido) {
                personaje.gastarMovimiento();
                return true;
            }
        }
        return false;
    }

    public String ejecutarAtaque(){
        if (personaje.puedeAtacar(objetivo)) {
            return personaje.atacar(objetivo);
        }
        return "";
    }

    public void reiniciarControlador() {
        this.faseActiva = false;
    }
}
