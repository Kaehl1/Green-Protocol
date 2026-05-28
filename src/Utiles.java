/**
 * Clase de utilidades que proporciona métodos estáticos de apoyo general para el proyecto.
 */
public class Utiles {

    /**
     * Formatea una cadena de texto para que su primera letra sea mayúscula y el resto minúsculas.
     *
     * @param frase La cadena de texto original a formatear.
     * @return La cadena de texto formateada correctamente (ej: "hErOe" -> "Heroe").
     */
    public static String primeraMayus(String frase){
        return frase.substring(0, 1).toUpperCase()+frase.substring(1).toLowerCase();
    }
}
