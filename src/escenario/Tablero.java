package escenario;

import entidades.Entidad;
import entidades.Item;
import excepciones.MovimientoInvalidoException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Tablero {
    private int filas;
    private int columnas;
    private Casilla[][] casillas;
    private boolean[][] muros;

    public Tablero() {
        generarMapaAleatorio();
    }

    public void generarMapaAleatorio() {
        Random rand = new Random();
        this.columnas = rand.nextInt(5) + 8;
        this.filas = rand.nextInt(5) + 8;
        do {
            casillas = new Casilla[columnas][filas];
            muros = new boolean[columnas][filas];
            for (int x = 0; x < columnas; x++) {
                for (int y = 0; y < filas; y++) {
                    casillas[x][y] = new Casilla();
                    if (rand.nextFloat() < 0.15f) {
                        muros[x][y] = true;
                    }
                }
            }
        } while (!verificarConectividad());
    }

    private boolean verificarConectividad() {
        int totalCaminables = 0;
        int inicioX = -1;
        int inicioY = -1;

        for (int x = 0; x < columnas; x++) {
            for (int y = 0; y < filas; y++) {
                if (!muros[x][y]) {
                    totalCaminables++;
                    if (inicioX == -1) {
                        inicioX = x;
                        inicioY = y;
                    }
                }
            }
        }
        if (totalCaminables == 0){
            return false;
        }
        boolean[][] visitado = new boolean[columnas][filas];
        Queue<int[]> cola = new LinkedList<>();
        cola.add(new int[]{
            inicioX, inicioY
        });
        visitado[inicioX][inicioY] = true;
        int contados = 0;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            contados++;
            for (int i = 0; i < 4; i++) {
                int nx = actual[0] + dx[i];
                int ny = actual[1] + dy[i];

                if (nx >= 0 && nx < columnas && ny >= 0 && ny < filas) {
                    if (!muros[nx][ny] && !visitado[nx][ny]) {
                        visitado[nx][ny] = true;
                        cola.add(new int[]{nx, ny});
                    }
                }
            }
        }
        return contados == totalCaminables;
    }

    public int[] buscarCasillaVaciaAleatoria() {
        Random rand = new Random();
        while (true) {
            int x = rand.nextInt(columnas);
            int y = rand.nextInt(filas);
            if (!muros[x][y] && casillas[x][y].estaLibre()) {
                return new int[]{x, y};
            }
        }
    }

    public boolean esCoordenadaValida(int x, int y){
        return x >= 0 && x < columnas && y >= 0 && y < filas;
    }

    public Casilla obtenerCasilla(int x, int y){
        if(!esCoordenadaValida(x,y)){
            return null;
        }
        return casillas[x][y];
    }

    public boolean colocarEntidad(Entidad entidad, int x, int y){
        if(!esCoordenadaValida(x,y) || muros[x][y] || !casillas[x][y].estaLibre()){
            return false;
        }
        entidad.setX(x);
        entidad.setY(y);
        casillas[x][y].setPersonaje(entidad);
        return true;
    }

    public boolean moverEntidad(Entidad entidad, Direccion direccion) throws MovimientoInvalidoException {
        Casilla casillaActual = casillas[entidad.getX()][entidad.getY()];
        int nuevaX = entidad.getX();
        int nuevaY = entidad.getY();
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
        if (!esCoordenadaValida(nuevaX, nuevaY)){
            throw new excepciones.MovimientoInvalidoException("¡Te sales de los límites del mapa!");
        }
        if (muros[nuevaX][nuevaY]){
            throw new excepciones.MovimientoInvalidoException("¡Hay un obstáculo en tu camino!");
        }
        if (!casillas[nuevaX][nuevaY].estaLibre()){
            throw new excepciones.MovimientoInvalidoException("¡Esa casilla ya está ocupada!");
        }
        entidad.setY(nuevaY);
        entidad.setX(nuevaX);
        casillas[nuevaX][nuevaY].setPersonaje(entidad);
        casillaActual.setPersonaje(null);
        Item item = casillas[nuevaX][nuevaY].getObjeto();
        if(item != null){
            item.aplicarEfecto(entidad);
            casillas[nuevaX][nuevaY].setObjeto(null);
        }
        return true;
    }

    public void generarPocionAleatoria() {
        int[] pos = buscarCasillaVaciaAleatoria();
        java.util.Random rand = new java.util.Random();
        int tipo = rand.nextInt(3);
        Item pocion = null;
        switch (tipo) {
            case 0:
                pocion = new Item(pos[0], pos[1], entidades.EfectoPocion.VIDA, 20);
                break;
            case 1:
                pocion = new Item(pos[0], pos[1], entidades.EfectoPocion.ATAQUE, 5);
                break;
            case 2:
                pocion = new Item(pos[0], pos[1], entidades.EfectoPocion.DEFENSA, 5);
                break;
        }
        casillas[pos[0]][pos[1]].setObjeto(pocion);
    }

    public String generarMapaTexto() {
        String mapa = "";
        for (int y = 0; y < this.filas; y++) {
            for (int x = 0; x < this.columnas; x++) {
                if (muros[x][y]) {
                    mapa = mapa + "⛆";
                } else {
                    mapa = mapa + casillas[x][y].representar();
                }
            }
            mapa = mapa + "\n";
        }
        return mapa;
    }

    public void limpiarTablero() {
        for (int x = 0; x < columnas; x++) {
            for (int y = 0; y < filas; y++) {
                if (casillas[x][y] != null) {
                    casillas[x][y].setPersonaje(null);
                    casillas[x][y].setObjeto(null);
                }
            }
        }
    }
}
