# 🏗️ Estructura del Proyecto: Green Protocol

Este documento explica de forma sencilla cómo están organizadas las piezas del juego y cómo se comunican entre sí. El proyecto sigue un patrón de diseño limpio (Modelo-Vista-Controlador), separando la lógica matemática, la interfaz gráfica y el almacenamiento de datos.

---

## 1. El Motor Central (Lógica del Juego)
El "árbitro" de la partida. Es el bloque que decide quién gana, de quién es el turno y qué pasa en el tablero.

* **`Partida.java`**: Es el corazón del juego. Controla los estados (menú principal, introducción de nombre, selección de personaje, juego activo). Gestiona el sistema de turnos, procesa las pulsaciones del teclado y unifica el proceso de guardado para que solo se lea y escriba en el disco duro una única vez al terminar el combate.
* **`Tablero.java`**: Se encarga exclusivamente de las coordenadas. Sabe dónde están las paredes, dónde deben aparecer las pociones aleatorias y dónde están los personajes.

---

## 2. La Interfaz Gráfica (La Pantalla)
El "televisor" y el "mando a distancia". Solo se encarga de dibujar cosas por pantalla y captar lo que teclea el usuario.

* **`VentanaJuego.java`**: Crea la ventana principal usando la librería Swing de Java. Utiliza un sistema de cartas (`CardLayout`) para cambiar instantáneamente entre el Menú, la Selección de Clase y el Tablero. Captura las teclas que pulsas (KeyListener) y se las manda a `Partida` para que procese la acción. También imprime los errores del sistema en el historial visual.

---

## 3. Los Actores (Entidades)
Son las "fichas" del tablero. Guardan la vida, el daño, la defensa y las reglas únicas de cada clase.

* **`Entidad.java`** *(Clase Padre)*: El molde principal. Define que todo personaje debe tener atributos básicos y métodos como recibir daño o moverse.
* **`Guerrero.java`**: Hereda de Entidad. Su habilidad especial duplica su daño temporalmente (Rabia).
* **`Paladin.java`**: Hereda de Entidad. Su habilidad especial hace que sus ataques ignoren la defensa y apliquen daño mágico (Bendición).
* **`Rogue.java`**: Hereda de Entidad. Su habilidad pasiva hace que siempre ataque dos veces seguidas por turno.

---

## 4. Los Cerebros (Controladores)
Son los que deciden cómo mover a las entidades por el tablero.

* **`Controlador.java`**: Obliga a que cualquiera que juegue tenga un método `ejecutarTurno`.
* **`Jugador.java`**: Traduce tus pulsaciones de teclado (W, A, S, D, E, R) en movimientos o ataques reales para tu personaje.
* **`Maquina.java`**: Es la Inteligencia Artificial. Evalúa la posición del tablero, intenta acercarse a ti y te ataca si estás a rango. Utiliza un retardo de tiempo (**Timer**) para que el jugador humano pueda ver cómo se mueve paso a paso.

---

## 5. El Sistema de Guardado (Base de Datos XML)
El "departamento de archivo". Entra en acción de forma segura únicamente cuando termina el combate.

* **`RegistroPartida.java`**: Es una caja de transporte (POJO). Empaqueta de golpe el ID, nombre, clase, jefe, resultado y puntos calculados por contrarreloj.
* **`GestorEstadisticas.java`**: Es el "bibliotecario". Abre el archivo físico de tu ordenador (`GreenProtocol.xml`), busca el último ID secuencial, inyecta la nueva partida y lo vuelve a cerrar. Delega cualquier error de lectura/escritura hacia la `Partida` para no perder datos.
* **Los Archivos Web** (`GreenProtocol.xml`, `GreenProtocol.xsl`, `SchemaSalami.xsd`, `estilos.css`): Trabajan en equipo para que un navegador web lea los datos crudos, verifique que la estructura es correcta mediante XSD y los pinte en una tabla HTML dinámica utilizando reglas CSS.

---

## 6. Herramientas y Seguridad
Los sistemas de protección para evitar que el juego se cierre inesperadamente.

* **`Utiles.java`**: Un pequeño maletín de herramientas globales. Contiene métodos como el formateo de nombres para poner siempre la primera letra en mayúscula.
* **`Excepciones`** (`AtaqueInvalidoException`, `MovimientoInvalidoException`): Son alarmas controladas. Si alguien intenta moverse a un muro o atacar al aire, el juego intercepta el fallo y muestra un mensaje de texto en la pantalla en lugar de colapsar.