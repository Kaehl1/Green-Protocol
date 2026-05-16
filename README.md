# Green-Protocol
Green Protocol es mi proyecto final de la asignatura de programación 1º-DAM.

Se trata de un juego de estrategia por turnos 1v1 en el que el jugador se enfrenta contra un jefe de una clase aleatoria.

El jugador puede elegir entre una de las 3 clases jugables: Guerrero, Paladín o Pícaro para enfrentar a un jefe que será también una de estas 3 clases elegido de forma aleatoria al comenzar cada partida.

---
## Requisitos e Instalación

### Requisitos Previos
* **Java Development Kit (JDK):** Versión 17 o superior (Se recomienda JDK 21).
* **IDE Recomendado:** IntelliJ IDEA, Eclipse o NetBeans.

### Cómo Ejecutar el Juego
1. **Importar el proyecto:** Abre tu IDE favorito e importa o abre la carpeta raíz del proyecto (`Green-Protocol`).
2. **Configurar el SDK:** Asegúrate de que el IDE tenga seleccionado un JDK compatible (mínimo JDK 21).
3. **Iniciar la partida:** Ejecuta el archivo `Main.java` dentro del paquete principal (`src/Main.java`).

### Controles de Combate

Durante tu turno en el mapa, puedes realizar las siguientes acciones utilizando el teclado:

* **Desplazamiento:** Teclas `W` (Norte), `A` (Oeste), `S` (Sur) y `D` (Este).
* **Ataque Base:** Tecla `E` (Requiere que el enemigo esté en rango de ataque y sin muros de por medio).
* **Habilidad Especial:** Tecla `R` (Disponible actualmente para las clases Guerrero y Paladín).

### Leyenda del Mapa

* **〴** -> Personaje o Jefe de clase **Guerrero**.
* **☩** -> Personaje o Jefe de clase **Paladín**.
* **☠** -> Personaje o Jefe de clase **Pícaro**.
* **❤** -> Poción de **Salud**.
* **⚗** -> Poción de **Ataque**.
* **⛉** -> Poción de **Defensa**.
* **⛆** -> Casilla no transitable (Muro / Obstáculo).

---

## Historial de Versiones (Changelog)

v2.0 Mejoras de interfaz, mapas y pociones aleatorios. Tratamiento de Excepciones.

    Se ha mejorado la interfaz moviendo el registro de batalla a la parte inferior para tener una mejor vision
    del registro y de las vidas y estadisticas de los personajes.
    
    Se ha implementado la generación de mapas aleatorios al iniciar cada partida. Ahora cuando empieces una nueva
    partida el mapa será totalmente diferente al anterior, añadiendo casillas no transitables, haciendo el juego
    más dinamico y entretenido. Además se ha implementado la generación de pociones aleatorias por todo el mapa
    en turnos aleatorios.

    Se han tratado algunas excepciones para evitar crasheos inesperados.

    Nuevos iconos que pueden aparecer en pantalla:
        - ⚗ -> Poción de Ataque.
        - ❤ -> Poción de Salud.
        - ⛉ -> Poción de Defensa.
        - ⛆ -> Casilla no transitable.

---

v1.2 Mejora de IA.

    Se han hecho cambios en el funcionamiento de la ejecucion del turno de la Maquina añadiendo un temporizador al mover
    para que el jugador vea como la maquina mueve casilla a casilla.

---

v1.1 Mejoras de interfaz.

    Se ha implementado el menú principal y la pantalla de selección de clase.

---

v1.0 Primera version jugable. Se muestra el mapa estandar con posiciones predefinidas.

    -El jugador se mueve con W A S D.
    -El jugador ataca con E.
    -Las clases Guerrero y Paladin pueden usar su habilidad especial con R.
    -Los diferentes simbolos en el mapa son:
        - 〴 -> Personaje o Jefe de clase Guerrero.
        - ☩ -> Personaje o Jefe de clase Paladín.
        - ☠ -> Personaje o Jefe de clase Pícaro.

---