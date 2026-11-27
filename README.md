# Examen Práctico Integrador — Sistema AulaLogic

**Universidad Técnica de Ambato** — Carrera de Ingeniería de Software
**Asignatura:** Algoritmos y Lógica de Programación
**Estudiante:** Punina Kevin
**Variante asignada:** B — promedio mínimo de aprobación: **12**

## Descripción del problema

Sistema de consola en Java que registra, consulta, procesa y almacena las calificaciones de los estudiantes de una asignatura. Cada estudiante tiene código, nombre y tres calificaciones (0–20). El sistema calcula promedio, nota máxima, nota mínima y estado académico, genera un reporte del curso y conserva los datos en `datos/estudiantes.txt` entre ejecuciones.

## Estructura del proyecto

```
Examen-ALP-Apellido-Nombre/
|-- README.md
|-- src/
|   |-- Main.java              -> menú y flujo principal
|   |-- Estudiante.java        -> datos y cálculos de un estudiante (+ constantes de la variante)
|   |-- GestionAcademica.java  -> ArrayList, registro, búsqueda, ordenamiento y reporte
|   `-- GestorArchivos.java    -> lectura y escritura del archivo
|-- datos/
|   `-- estudiantes.txt
|-- docs/
|   |-- Analisis.md            -> entrada, proceso, salida, pseudocódigo y prueba de escritorio
|   `-- CasosDePrueba.md       -> matriz CP01–CP08 (+ pruebas adicionales)
`-- evidencias/
    |-- Ejecucion.png          -> captura de la ejecución en VS Code
    `-- salida_consola_pruebas.txt
```

## Compilación y ejecución

Requisito: JDK 11 o superior. Todos los comandos se ejecutan desde la carpeta raíz del proyecto, para que la ruta `datos/estudiantes.txt` se resuelva correctamente.

```bash
javac -encoding UTF-8 -d bin src/*.java
java -cp bin Main
```

En Visual Studio Code se abre la carpeta raíz y se ejecuta `Main.java` con el botón **Run**, o con la configuración "Ejecutar AulaLogic" incluida en `.vscode/launch.json`.

## Funcionalidades implementadas

- **RF01** Registro con validación de código único, textos no vacíos y sin `;`, y notas entre 0 y 20 solicitadas de nuevo si son incorrectas. Las notas se guardan en un arreglo `int[]` y los estudiantes en un `ArrayList`.
- **RF02** Promedio sin división entera, nota máxima, nota mínima y estado APROBADO/REPROBADO (promedio ≥ límite).
- **RF03** Búsqueda secuencial por código con mensaje informativo si no existe.
- **RF04** Reporte con total, aprobados, reprobados, promedio general, porcentaje de aprobados, mayor y menor promedio, estudiantes bajo el promedio general y listado ordenado de mayor a menor mediante burbuja. Controla la lista vacía.
- **RF05** Persistencia con `FileWriter`/`BufferedWriter` y `FileReader`/`BufferedReader`, recuperación automática al iniciar, tratamiento del archivo inexistente y de líneas dañadas, reescritura completa sin duplicados y recálculo de promedios a partir de las notas.
- Menú repetitivo con control de opciones inválidas y de texto no numérico; guardado automático al salir.

## Limitaciones

- Los registros nuevos solo se escriben en el archivo al elegir la opción 5 o al salir con la opción 6; si el programa se cierra de forma forzada, se pierden los cambios no guardados.
- La búsqueda por código no distingue mayúsculas de minúsculas (`e001` equivale a `E001`).
- Los decimales se muestran con el separador del sistema operativo.
