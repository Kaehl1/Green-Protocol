package controladores;

import entidades.Entidad;
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
        if(personaje.getVidaActual()<=(personaje.getVidaMax()*0.7)&&!this.faseActiva){
            mensaje = personaje.usarHabilidad();
            faseActiva = true;
        }
        while (personaje.getMovimientoActual()>0){
            escenario.Direccion dir = personaje.direccionHacia(objetivo);
            boolean movido = tablero.moverEntidad(personaje, dir);
            if (movido){
                personaje.gastarMovimiento();
            }else {
                break; //Si llega aqui es porque esta al lado o se ha chocado.
            }
        }
        if(personaje.puedeAtacar(objetivo)){
            String textoAtaque = personaje.atacar(objetivo);
            if(mensaje!=null&&!mensaje.isEmpty()){
                mensaje=mensaje+"\n >"+textoAtaque;
            }else {
                mensaje=textoAtaque;
            }
        }
        return mensaje;
    }

    public void reiniciarControlador() {
        this.faseActiva = false;
    }
}
