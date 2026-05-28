package escenario;

import entidades.Entidad;
import entidades.Item;
import excepciones.MovimientoInvalidoException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Gestor principal del entorno de juego.
 * Controla la cuadrícula física, la generación procedimental de mapas,
 * las colisiones y el posicionamiento de todos los elementos interactivos.
 */
public class Tablero {
    private int filas;
    private int columnas;
    private Casilla[][] casillas;
    private boolean[][] muros;

    /**
     * Construye un nuevo tablero y desencadena la generación de un mapa aleatorio jugable.
     */
    public Tablero() {
        generarMapaAleatorio();
    }

    /**
     * Crea proceduralmente un mapa de dimensiones aleatorias (entre 8x8 y 12x12).
     * Coloca muros con una probabilidad del 15% y asegura que el mapa generado
     * sea 100% transitable sin zonas bloqueadas repitiendo el proceso si es necesario.
     */
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

    /**
     * Algoritmo de Búsqueda en Anchura (BFS) para validar que todas las casillas transitables
     * están conectadas entre sí y no hay zonas cerradas por muros.
     *
     * @return true si el laberinto generado tiene solución completa; false si hay áreas inaccesibles.
     */
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

    /**
     * Busca y devuelve las coordenadas de una casilla que no sea un muro y que no contenga ninguna entidad.
     *
     * @return Un array de enteros donde [0] es la coordenada X y [1] es la coordenada Y.
     */
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

    /**
     * Comprueba si unas coordenadas específicas se encuentran dentro de los límites del tablero.
     *
     * @param x Coordenada X (columna).
     * @param y Coordenada Y (fila).
     * @return true si la coordenada es válida dentro de la matriz.
     */
    public boolean esCoordenadaValida(int x, int y){
        return x >= 0 && x < columnas && y >= 0 && y < filas;
    }

    /**
     * Obtiene la referencia directa al objeto Casilla de unas coordenadas dadas.
     *
     * @param x Coordenada X solicitada.
     * @param y Coordenada Y solicitada.
     * @return La Casilla correspondiente, o null si está fuera de los límites.
     */
    public Casilla obtenerCasilla(int x, int y){
        if(!esCoordenadaValida(x,y)){
            return null;
        }
        return casillas[x][y];
    }

    /**
     * Ubica a un personaje en una coordenada inicial de forma directa (sin validar el movimiento previo).
     *
     * @param entidad El personaje a posicionar.
     * @param x Coordenada X destino.
     * @param y Coordenada Y destino.
     * @return true si se ha colocado exitosamente; false si la casilla no es válida, es muro o está ocupada.
     */
    public boolean colocarEntidad(Entidad entidad, int x, int y){
        if(!esCoordenadaValida(x,y) || muros[x][y] || !casillas[x][y].estaLibre()){
            return false;
        }
        entidad.setX(x);
        entidad.setY(y);
        casillas[x][y].setPersonaje(entidad);
        return true;
    }

    /**
     * Intenta desplazar a una entidad una casilla en la dirección indicada aplicando lógicas de colisión
     * y recogiendo cualquier ítem presente en la casilla de destino.
     *
     * @param entidad El personaje que va a realizar el movimiento.
     * @param direccion La dirección cardinal hacia la que se desea mover.
     * @return true si el movimiento se ha resuelto sin problemas.
     * @throws MovimientoInvalidoException Si el destino está fuera de los límites, es un obstáculo o está bloqueado por otro personaje.
     */
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

    /**
     * Genera un objeto consumible aleatorio (Vida, Ataque o Defensa) y lo ubica en una coordenada libre del mapa.
     */
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

    /**
     * Traduce el estado lógico del tablero actual a una representación en formato String para la interfaz gráfica.
     *
     * @return Una cadena de texto con saltos de línea mostrando todos los muros, ítems y entidades representados visualmente.
     */
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

    /**
     * Recorre todo el tablero eliminando temporalmente las referencias a personajes y objetos en el suelo
     * (útil para limpiezas o cambios de estado globales).
     */
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
