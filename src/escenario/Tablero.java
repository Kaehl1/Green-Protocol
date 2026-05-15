package escenario;

import entidades.Entidad;
import entidades.Item;

public class Tablero {
    private int filas;
    private int columnas;
    private Casilla[][] casillas;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        casillas = new Casilla[columnas][filas];
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    public boolean esCoordenadaValida(int x, int y){
        if(x<0 || x>=columnas || y<0 || y>=filas){
            return false;
        }
        return true;
    }

    public Casilla obtenerCasilla(int x, int y){
        if(!esCoordenadaValida(x,y)){
            return null;
        }
        return casillas[x][y];
    }

    public boolean colocarEntidad(Entidad entidad, int x, int y){
        if(!esCoordenadaValida(x,y)){
            return false;
        }
        if(!casillas[x][y].estaLibre()){
            return false;
        }
        entidad.setX(x);
        entidad.setY(y);
        casillas[x][y].setPersonaje(entidad);
        return true;
    }

    public boolean moverEntidad(Entidad entidad, Direccion direccion){
        Casilla casillaActual=casillas[entidad.getX()][entidad.getY()];
        int nuevaX =  entidad.getX();
        int nuevaY =  entidad.getY();
        switch (direccion){
            case NORTE:
                nuevaY--;
                break;
            case SUR:
                nuevaY++;
                break;
            case ESTE:
                nuevaX++;
                break;
            case OESTE:
                nuevaX--;
                break;
        }
        if (!esCoordenadaValida(nuevaX, nuevaY) || !casillas[nuevaX][nuevaY].estaLibre()){
            return false;
        }
        entidad.setY(nuevaY);
        entidad.setX(nuevaX);
        casillas[nuevaX][nuevaY].setPersonaje(entidad);
        casillaActual.setPersonaje(null);
        Item item = casillas[nuevaX][nuevaY].getObjeto();
        if(item != null){
            item.aplicarEfecto(entidad);
            casillas[nuevaX][nuevaY].setObjeto(null);
            System.out.println("Se ha recogido una poción de "+item.getEfecto());
        }
        return true;
    }

    public String generarMapaTexto() {
        String mapa = "";
        for (int y = 0; y < this.filas; y++) {
            for (int x = 0; x < this.columnas; x++) {
                Casilla casilla = casillas[x][y];
                mapa=mapa+casilla.representar();
            }
            mapa += "\n";
        }
        return mapa;
    }

    public void limpiarTablero() {
        for (int x = 0; x < columnas; x++) {
            for (int y = 0; y < filas; y++) {
                casillas[x][y].setPersonaje(null);
                casillas[x][y].setObjeto(null); //borra ítems que no se recogieron
            }
        }
    }
}
