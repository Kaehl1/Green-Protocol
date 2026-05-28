package datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Motor principal encargado de la persistencia de datos.
 * Conecta el estado del juego con la base de datos local XML para registrar las estadísticas y el historial de las partidas.
 */
public class GestorEstadisticas {

    /**
     * Lee el archivo XML existente, inyecta la nueva partida justo antes del cierre de la etiqueta contenedora de partidas,
     * y sobrescribe el documento de forma segura.
     *
     * @param registro Objeto que contiene todos los datos de la partida que acaba de finalizar.
     * @throws IOException Si ocurre algún error durante la lectura o escritura del archivo físico.
     */
    public void guardarPartida(RegistroPartida registro) throws IOException {
        Path ruta = Path.of("GreenProtocol.xml");
        ArrayList<String> contenidoMemoria = new ArrayList<>();
        //LECTURA
        BufferedReader lector = Files.newBufferedReader(ruta);
        String linea;
        while ((linea = lector.readLine()) != null) {
            if (linea.contains("</partidas>")) {
                contenidoMemoria.add(generarStringXML(registro));
            }
            contenidoMemoria.add(linea);
        }
        lector.close();
        //ESCRITURA
        BufferedWriter escritor = Files.newBufferedWriter(ruta); //Esto vacía el archivo original
        for (String lineaMemoria : contenidoMemoria) {
            escritor.write(lineaMemoria);
            escritor.newLine();
        }
        escritor.close();
    }

    /**
     * Transforma los datos del objeto RegistroPartida en una estructura de texto formateada con etiquetas XML.
     *
     * @param partida El objeto con la información a formatear.
     * @return Cadena de texto con el formato XML lista para ser inyectada en el archivo.
     */
    private String generarStringXML(RegistroPartida partida) {
        return "            <partida id=\"" + partida.getId() + "\">\n" +
                "                <nombreHeroe>" + partida.getNombreHeroe() + "</nombreHeroe>\n" +
                "                <clase>" + partida.getClaseHeroe() + "</clase>\n" +
                "                <jefe>\n" +
                "                    <nombre>" + partida.getNombreJefe() + "</nombre>\n" +
                "                    <clase>" + partida.getClaseJefe() + "</clase>\n" +
                "                </jefe>\n" +
                "                <puntos>" + partida.getPuntos() + "</puntos>\n" +
                "            </partida>";
    }

    /**
     * Escanea el archivo XML buscando el último identificador (ID) registrado para calcular el siguiente de forma secuencial.
     *
     * @return El siguiente identificador disponible en formato String de 3 dígitos (ejemplo: "001").
     * @throws IOException Sí ocurre un error al intentar acceder o leer el archivo local.
     */
    public String obtenerSiguienteId() throws IOException {
        Path ruta = Path.of("GreenProtocol.xml");
        int maxId = 0; // Si no hay partidas, empezará en 0
        if (Files.exists(ruta)) {
            BufferedReader lector = Files.newBufferedReader(ruta);
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.contains("<partida id=\"")) {
                    int inicio = linea.indexOf("id=\"") + 4;
                    int fin = linea.indexOf("\"", inicio);
                    int idActual = Integer.parseInt(linea.substring(inicio, fin));
                    if (idActual > maxId) {
                        maxId = idActual;
                    }
                }
            }
            lector.close();
        }
        return String.format("%03d", maxId + 1);
    }
}
