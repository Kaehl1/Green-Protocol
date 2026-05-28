package entidades;

import escenario.Direccion;

/**
 * Clase abstracta que representa cualquier elemento interactivo posicionado dentro del tablero de juego.
 * Proporciona la lógica matemática base para el cálculo de coordenadas, distancias y orientación.
 */
public abstract class ObjetoMapa {
    private int posX;
    private int posY;

    /**
     * Constructor base para posicionar el objeto en el mapa.
     *
     * @param posX Coordenada X (columna) en el tablero.
     * @param posY Coordenada Y (fila) en el tablero.
     */
    public ObjetoMapa(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Calcula la distancia de Manhattan entre este objeto y otro objeto en el tablero.
     *
     * @param otro El objeto destino contra el que se calcula la distancia.
     * @return El número de casillas totales de distancia entre ambos objetos.
     */
    public int distanciaA(ObjetoMapa otro){
        int difX=Math.abs(this.posX-otro.posX);
        int difY=Math.abs(this.posY-otro.posY);
        return difX+difY;
    }

    /**
     * Determina la dirección cardinal óptima para acercarse a otro objeto.
     *
     * @param otro El objeto objetivo al que se desea apuntar o acercarse.
     * @return La Dirección (NORTE, SUR, ESTE u OESTE) con mayor diferencia de casillas hacia el objetivo.
     */
    public Direccion direccionHacia(ObjetoMapa otro){
        int difX=otro.posX-this.posX;
        int difY=otro.posY-this.posY;
        if(Math.abs(difX)>Math.abs(difY)){
            return(difX>0)?Direccion.ESTE:Direccion.OESTE;
        }else {
            return (difY>0)?Direccion.SUR:Direccion.NORTE;
        }
    }

    /**
     * Define la representación visual del objeto en la interfaz de texto.
     *
     * @return Un carácter o símbolo visual que representa a este objeto específico en el mapa.
     */
    public abstract String representar();

    //Getters&Setters
    public int getX() {
        return posX;
    }

    public void setX(int posX) {
        this.posX = posX;
    }

    public int getY() {
        return posY;
    }

    public void setY(int posY) {
        this.posY = posY;
    }
}
