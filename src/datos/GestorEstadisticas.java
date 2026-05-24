package datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GestorEstadisticas {
    public void guardarPartida(RegistroPartida registro) {
        Path ruta = Path.of("GreenProtocol.xml");
        ArrayList<String> contenidoMemoria = new ArrayList<>();
        //LECTURA
        try {
            BufferedReader lector = Files.newBufferedReader(ruta);
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.contains("</partidas>")) {
                    contenidoMemoria.add(generarStringXML(registro));
                }
                contenidoMemoria.add(linea);
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo XML: " + e.getMessage());
            return; // Salimos del método para no borrar el archivo al escribir
        }
        //ESCRITURA
        try {
            BufferedWriter escritor = Files.newBufferedWriter(ruta); //Esto vacía el archivo original
            for (String lineaMemoria : contenidoMemoria) {
                escritor.write(lineaMemoria);
                escritor.newLine();
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo XML: " + e.getMessage());
        }
    }

    // --- MÉTODO AUXILIAR ---
    // Transforma el objeto de datos en un bloque de texto formateado con etiquetas XML
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

    // --- MÉTODO PARA GENERAR ID SECUENCIAL ---
    public String obtenerSiguienteId() {
        Path ruta = Path.of("GreenProtocol.xml");
        int maxId = 0; // Si no hay partidas, empezará en 0
        try {
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
        } catch (Exception e) {
            System.out.println("Error al calcular el ID secuencial: " + e.getMessage());
        }
        return String.format("%03d", maxId + 1);
    }
}
