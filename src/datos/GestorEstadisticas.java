package datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GestorEstadisticas {
    public void guardarPartida(RegistroPartida registro) {
        // Definimos la ruta del archivo XML usando Path
        Path ruta = Path.of("GreenProtocol.xml");
        // Creamos una lista en memoria para almacenar las líneas del archivo temporalmente
        ArrayList<String> contenidoMemoria = new ArrayList<>();
        // --- FASE DE LECTURA ---
        try {
            // Abrimos el archivo para lectura
            BufferedReader lector = Files.newBufferedReader(ruta);
            String linea;
            // Leemos el archivo línea por línea hasta el final
            while ((linea = lector.readLine()) != null) {
                // Si la línea actual contiene el cierre de la sección de partidas
                if (linea.contains("</partidas>")) {
                    // Inyectamos nuestro bloque de texto XML con la nueva partida antes del cierre
                    contenidoMemoria.add(generarStringXML(registro));
                }
                // Añadimos siempre la línea original que acabamos de leer a nuestra memoria
                contenidoMemoria.add(linea);
            }
            // Cerramos el flujo del lector para liberar el archivo
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo XML: " + e.getMessage());
            return; // Salimos del método para no borrar el archivo al escribir
        }
        // --- FASE DE ESCRITURA ---
        try {
            // Abrimos el archivo para escritura
            BufferedWriter escritor = Files.newBufferedWriter(ruta); //Esto vacía el archivo original
            // Recorremos toda la lista donde guardamos el documento modificado
            for (String lineaMemoria : contenidoMemoria) {
                escritor.write(lineaMemoria); // Escribimos la línea
                escritor.newLine();           // Insertamos el salto de línea correspondiente
            }
            // Cerramos el flujo del escritor para guardar definitivamente los cambios en el disco
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
            // Si el archivo existe, lo leemos para buscar el último ID
            if (Files.exists(ruta)) {
                BufferedReader lector = Files.newBufferedReader(ruta);
                String linea;
                while ((linea = lector.readLine()) != null) {
                    // Buscamos las líneas que contengan la etiqueta de partida
                    if (linea.contains("<partida id=\"")) {
                        // Localizamos dónde empieza el número (después de 'id="')
                        int inicio = linea.indexOf("id=\"") + 4;
                        // Localizamos dónde termina el número (las siguientes comillas)
                        int fin = linea.indexOf("\"", inicio);
                        // Extraemos el texto del número y lo convertimos a entero matemático
                        int idActual = Integer.parseInt(linea.substring(inicio, fin));
                        // Si es mayor que nuestro máximo registrado, lo actualizamos
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
        // Sumamos 1 al máximo encontrado y lo formateamos para que tenga 3 dígitos con ceros a la izquierda
        return String.format("%03d", maxId + 1);
    }
}
