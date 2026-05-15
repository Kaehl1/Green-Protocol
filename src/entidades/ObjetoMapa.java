package entidades;

import escenario.Direccion;

public abstract class ObjetoMapa {
    private int posX;
    private int posY;

    public ObjetoMapa(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int distanciaA(ObjetoMapa otro){
        int difX=Math.abs(this.posX-otro.posX);
        int difY=Math.abs(this.posY-otro.posY);
        return difX+difY;
    }

    public Direccion direccionHacia(ObjetoMapa otro){
        int difX=otro.posX-this.posX;
        int difY=otro.posY-this.posY;
        if(Math.abs(difX)>Math.abs(difY)){
            return(difX>0)?Direccion.ESTE:Direccion.OESTE;
        }else {
            return (difY>0)?Direccion.SUR:Direccion.NORTE;
        }
    }

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
