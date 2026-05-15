import controladores.Jugador;
import controladores.Maquina;
import entidades.*;
import escenario.Tablero;

public class Main {
    public static void main(String[] args) {
        Tablero tablero = new Tablero(10,10);
        Item pocion = new Item(1,0, EfectoPocion.ATAQUE,5);
        Entidad muñeco = new Guerrero(0,0,"Kaehl");
        Entidad muñecoJefe = new Paladin(9,9,"Vaarun");
        tablero.obtenerCasilla(pocion.getX(), pocion.getY()).setObjeto(pocion);
        tablero.colocarEntidad(muñeco,muñeco.getX(),muñeco.getY());
        tablero.colocarEntidad(muñecoJefe, muñecoJefe.getX(), muñecoJefe.getY());
        Jugador jugador = new Jugador(muñeco);
        Maquina jefe = new Maquina(muñecoJefe, muñeco);
        Partida partida = new Partida(tablero,muñeco,muñecoJefe);
        partida.agregarControlador(jugador);
        partida.agregarControlador(jefe);
        partida.avanzarTurno();
    }
}
