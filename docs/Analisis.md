# Análisis del problema — Sistema AulaLogic

**Variante asignada:** B — límite de aprobación = 12 (constante `LIMITE_APROBACION` en `Estudiante.java`).

## 1. Descripción del problema

La Universidad Técnica de Ambato requiere un sistema de consola que registre, consulte, procese y almacene las calificaciones de los estudiantes de una asignatura. Los registros deben conservarse en un archivo de texto entre ejecuciones y el sistema debe generar indicadores de rendimiento del curso.

## 2. Entrada

| Dato | Tipo | Restricción |
|---|---|---|
| Opción del menú | `int` | 1 a 6; cualquier otro valor o texto se rechaza sin cerrar el programa |
| Código del estudiante | `String` | No vacío, sin `;`, no repetido |
| Nombre completo | `String` | No vacío, sin `;` |
| Calificación 1, 2 y 3 | `int` | Entre 0 y 20; si no cumple, se solicita de nuevo |
| Código a buscar | `String` | Cualquier texto |
| Archivo `datos/estudiantes.txt` | Texto | Formato `codigo;nombre;n1;n2;n3` |

## 3. Proceso

1. Al iniciar, `GestorArchivos.leer()` abre el archivo, separa cada línea con `split(";")`, convierte las notas con `Integer.parseInt` y crea los objetos `Estudiante`. Si el archivo no existe, se empieza con la lista vacía; las líneas dañadas se omiten.
2. El menú se repite con `do-while` hasta elegir 6, y `switch` dirige cada opción.
3. **Registro:** se valida el código (vacío, `;`, duplicado mediante búsqueda secuencial), el nombre y cada nota (ciclo `do-while` hasta que esté en 0–20). Las notas se guardan en un `int[3]` y el objeto en el `ArrayList`.
4. **Cálculos por estudiante:** promedio = suma / 3.0 (división real); máximo y mínimo recorriendo el arreglo; estado = APROBADO si promedio ≥ 12, de lo contrario REPROBADO.
5. **Búsqueda:** recorrido secuencial del `ArrayList` comparando códigos.
6. **Reporte:** contadores (aprobados), acumuladores (suma de promedios), promedio general = suma / total, porcentaje = aprobados × 100 / total, mayor y menor promedio (en empate queda el primero registrado), estudiantes bajo el promedio general y listado ordenado con burbuja de mayor a menor. Si la lista está vacía se informa y no se divide.
7. **Guardado:** `FileWriter` + `BufferedWriter` sobrescriben el archivo completo con la lista actual, de modo que no se duplican registros. Se ejecuta con la opción 5 y automáticamente al salir.

## 4. Salida

Mensajes de confirmación o error en el registro; tabla con código, nombre, notas, promedio, máxima, mínima y estado; ficha del estudiante buscado o mensaje de inexistencia; reporte académico con los indicadores del RF04; archivo `datos/estudiantes.txt` actualizado.

## 5. Pseudocódigo

```
INICIO
    lista ← GestorArchivos.leer()            // recuperación automática
    HACER
        MOSTRAR menú
        LEER opcion (si no es número, pedir otra vez)
        SEGÚN opcion
            1: LEER codigo
               SI codigo vacío O contiene ";" O ya existe ENTONCES
                   MOSTRAR error
               SINO
                   REPETIR LEER nombre HASTA nombre válido
                   PARA i ← 0 HASTA 2
                       REPETIR LEER nota[i] HASTA 0 ≤ nota[i] ≤ 20
                   FIN PARA
                   AGREGAR Estudiante(codigo, nombre, nota) a lista
               FIN SI
            2: SI lista vacía MOSTRAR "No hay estudiantes"
               SINO PARA cada e EN lista MOSTRAR datos de e
            3: LEER codigo
               e ← busquedaSecuencial(lista, codigo)
               SI e = nulo MOSTRAR "No existe" SINO MOSTRAR detalle de e
            4: SI lista vacía ENTONCES MOSTRAR "No hay estudiantes"
               SINO
                   suma ← 0; aprobados ← 0
                   PARA cada e EN lista
                       suma ← suma + promedio(e)
                       SI promedio(e) ≥ LIMITE ENTONCES aprobados ← aprobados + 1
                   FIN PARA
                   promGeneral ← suma / total
                   porcentaje ← aprobados * 100 / total
                   MOSTRAR total, aprobados, total − aprobados, promGeneral, porcentaje
                   MOSTRAR mayor y menor promedio
                   MOSTRAR estudiantes con promedio < promGeneral
                   copia ← burbuja(lista, descendente)
                   MOSTRAR copia
               FIN SI
            5: GestorArchivos.guardar(lista)
            6: GestorArchivos.guardar(lista); MOSTRAR "Hasta pronto"
            OTRO: MOSTRAR "Opción incorrecta"
        FIN SEGÚN
    MIENTRAS opcion ≠ 6
FIN
```

## 6. Prueba de escritorio

### 6.1 `calcularPromedio()` y `obtenerEstado()` con E004 (10, 12, 14) — caso límite

| i | notas[i] | suma |
|---|---|---|
| — | — | 0 |
| 0 | 10 | 10 |
| 1 | 12 | 22 |
| 2 | 14 | 36 |

Promedio = 36 / 3.0 = **12.00**. Como 12.00 ≥ 12, el estado es **APROBADO**. Si se hubiera usado `36 / 3` el resultado coincidiría aquí, pero con E005 (11, 12, 12) la suma 35 / 3 daría 11 en división entera en lugar de 11.67; por eso se divide para `3.0`.

### 6.2 `obtenerNotaMaxima()` con E001 (15, 18, 12)

| i | notas[i] | ¿notas[i] > maxima? | maxima |
|---|---|---|---|
| inicio | — | — | 15 |
| 1 | 18 | Sí | 18 |
| 2 | 12 | No | 18 |

### 6.3 Ordenamiento burbuja con E001=15.00, E002=10.00, E003=19.00

| Pasada i | j | Comparación | ¿Intercambia? | Lista |
|---|---|---|---|---|
| inicio | — | — | — | [15, 10, 19] |
| 0 | 0 | 15 < 10 | No | [15, 10, 19] |
| 0 | 1 | 10 < 19 | Sí | [15, 19, 10] |
| 1 | 0 | 15 < 19 | Sí | [19, 15, 10] |

Resultado: E003 (19.00), E001 (15.00), E002 (10.00).

### 6.4 Reporte con los datos iniciales del archivo

| Código | Promedio | Estado |
|---|---|---|
| E001 | 15.00 | APROBADO |
| E002 | 10.00 | REPROBADO |
| E003 | 19.00 | APROBADO |
| E004 | 12.00 | APROBADO |
| E005 | 11.67 | REPROBADO |

Suma de promedios = 67.67; promedio general = 67.67 / 5 = **13.53**; aprobados = 3; porcentaje = 3 × 100 / 5 = **60.00 %**; mayor promedio E003; menor promedio E002; bajo el promedio general: E002, E004 y E005.
