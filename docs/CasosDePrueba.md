# Casos de prueba — Sistema AulaLogic

**Variante:** B (límite de aprobación = 12). Datos iniciales: los cinco registros de `datos/estudiantes.txt` (E001 a E005).

| Caso | Situación | Entrada | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|---|
| CP01 | Registro correcto | Opción 1; código `E006`; nombre `Pedro Gil`; notas 14, 13, 15 | Se agrega a la lista con promedio 14.00 y estado APROBADO | `Estudiante registrado. Promedio: 14.00 - APROBADO` | Aprobado |
| CP02 | Código duplicado | Opción 1; código `E001` (ya existe) | Se rechaza el registro sin pedir más datos | `Error: el codigo E001 ya existe. Registro cancelado.` | Aprobado |
| CP03 | Calificación fuera de rango (caso límite) | Opción 1; código `E007`; nombre `Rosa Paz`; nota 1 = `25`, luego `-1`, luego `16`; notas 2 y 3 = 12, 17 | Se muestra error y se vuelve a pedir la nota 1 hasta recibir un valor de 0 a 20 | Dos veces `Error: la calificacion debe estar entre 0 y 20.`; luego registro con promedio 15.00 | Aprobado |
| CP04 | Promedio igual al límite (caso límite) | Opción 3; código `E004` (notas 10, 12, 14) | Promedio 12.00 y estado APROBADO, porque la condición es `>=` | `Promedio : 12.00` — `Estado : APROBADO` | Aprobado |
| CP05 | Búsqueda de estudiante existente | Opción 3; código `E004` | Ficha con código, nombre, notas, promedio, máxima, mínima y estado | Ficha completa: notas 10 12 14, máx. 14, mín. 10, APROBADO | Aprobado |
| CP06 | Búsqueda de estudiante inexistente | Opción 3; código `E999` | Mensaje informativo sin cerrar el programa | `No existe un estudiante con el codigo E999.` | Aprobado |
| CP07 | Reporte sin estudiantes (caso límite) | Ejecutar sin `datos/estudiantes.txt`; opción 4 | Aviso de archivo inexistente, lista vacía y reporte sin división entre cero | `No se encontro datos/estudiantes.txt. Se iniciara con una lista vacia.` y `No hay estudiantes registrados. No se puede generar el reporte.` | Aprobado |
| CP08 | Guardado y recuperación al reiniciar | Tras CP01 y CP03, opción 6 (guarda); volver a ejecutar; opción 3 con `E007` | El archivo contiene 7 líneas sin duplicados; al reiniciar se recuperan 7 registros y E007 se encuentra con promedio recalculado | `Registros recuperados: 7`; ficha de E007 con promedio 15.00 y APROBADO | Aprobado |

## Pruebas adicionales de robustez

| Caso | Situación | Entrada | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|---|
| CP09 | Opción de menú inexistente | `9` | Mensaje de error y el menú se repite | `Opcion incorrecta. Ingrese un numero del 1 al 6.` | Aprobado |
| CP10 | Texto en lugar de número | `abc` en el menú | Se captura `NumberFormatException` y se pide de nuevo | `Valor no valido. Ingrese un numero entero:` | Aprobado |
| CP11 | Archivo con líneas dañadas | Líneas con 4 campos, nota `x`, código repetido y nota `25` | Las líneas incorrectas se omiten con aviso y se cargan solo las válidas | Cuatro avisos y `Registros recuperados: 1` | Aprobado |
| CP12 | Código o nombre con `;` o vacío | Código vacío o `E0;1` | Se rechaza porque rompería el formato del archivo | `Error: el codigo no puede estar vacio ni contener ';'.` | Aprobado |

