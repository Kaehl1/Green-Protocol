import entidades.*;
import escenario.Tablero;

public class Main {
    public static void main(String[] args) {
        Tablero tablero = new Tablero();
        Entidad heroeProvisional = new Guerrero(0,0,"Kaehl");
        Entidad jefeProvisional = new Paladin(9,9,"Vaarun");
        Partida partida = new Partida(tablero, heroeProvisional, jefeProvisional);
    }
}
