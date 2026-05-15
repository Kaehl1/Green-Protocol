import controladores.Jugador;
import controladores.Maquina;
import entidades.*;
import escenario.Tablero;

public class Main {
    public static void main(String[] args) {
        Tablero tablero = new Tablero(10,10);
        Item pocion = new Item(1,0, EfectoPocion.ATAQUE,5);
        Entidad heroeProvisional = new Guerrero(0,0,"Kaehl");
        Entidad jefe = new Paladin(9,9,"Vaarun");
        Partida partida = new Partida(tablero, heroeProvisional, jefe);
    }
}
