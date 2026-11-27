import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GestorArchivos {

    public static final String CARPETA = "datos";
    public static final String RUTA_ARCHIVO = "datos/estudiantes.txt";


    public boolean guardar(ArrayList<Estudiante> estudiantes) {
        // Si no existe la carpeta datos/, se crea
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        BufferedWriter escritor = null;
        try {
            // UTF_8 para que las tildes (Perez -> Pérez) se guarden bien
            escritor = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, StandardCharsets.UTF_8));
            for (int i = 0; i < estudiantes.size(); i++) {
                escritor.write(estudiantes.get(i).aLineaArchivo());
                escritor.newLine();   // salto de linea
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
            return false;
        } finally {
            // Cerrar el archivo SIEMPRE (haya error o no)
            try {
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo: " + e.getMessage());
            }
        }
    }

    public ArrayList<Estudiante> leer() {
        ArrayList<Estudiante> lista = new ArrayList<Estudiante>();
        BufferedReader lector = null;

        try {
            lector = new BufferedReader(new FileReader(RUTA_ARCHIVO, StandardCharsets.UTF_8));
            String linea;
            int numeroLinea = 0;

            // Leer linea por linea hasta que readLine() devuelva null (fin)
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.trim().isEmpty()) {
                    continue;   // ignorar lineas vacias
                }
                Estudiante e = convertirLinea(linea, numeroLinea);
                if (e != null) {
                    // Evitar codigos repetidos dentro del archivo
                    if (existeEnLista(lista, e.getCodigo())) {
                        System.out.println("Aviso: linea " + numeroLinea
                                + " omitida (codigo repetido " + e.getCodigo() + ").");
                    } else {
                        lista.add(e);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Tratamiento del archivo inexistente: se empieza sin datos
            System.out.println("No se encontro " + RUTA_ARCHIVO
                    + ". Se iniciara con una lista vacia.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } finally {
            try {
                if (lector != null) {
                    lector.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo: " + e.getMessage());
            }
        }
        return lista;
    }

    private Estudiante convertirLinea(String linea, int numeroLinea) {
        String[] partes = linea.split(";");

        // Deben existir 2 textos + 3 notas = 5 partes
        if (partes.length != 2 + Estudiante.CANTIDAD_NOTAS) {
            System.out.println("Aviso: linea " + numeroLinea + " con formato incorrecto, se omite.");
            return null;
        }

        String codigo = partes[0].trim();
        String nombre = partes[1].trim();
        if (codigo.isEmpty() || nombre.isEmpty()) {
            System.out.println("Aviso: linea " + numeroLinea + " con codigo o nombre vacio, se omite.");
            return null;
        }

        int[] notas = new int[Estudiante.CANTIDAD_NOTAS];
        try {
            for (int i = 0; i < Estudiante.CANTIDAD_NOTAS; i++) {
                notas[i] = Integer.parseInt(partes[i + 2].trim());
                // Validar que la nota recuperada este en el rango 0-20
                if (notas[i] < Estudiante.NOTA_MINIMA || notas[i] > Estudiante.NOTA_MAXIMA) {
                    System.out.println("Aviso: linea " + numeroLinea + " con nota fuera de rango, se omite.");
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Aviso: linea " + numeroLinea + " con nota no numerica, se omite.");
            return null;
        }

        return new Estudiante(codigo, nombre, notas);
    }

    // Busqueda secuencial auxiliar para detectar codigos repetidos en el archivo
    private boolean existeEnLista(ArrayList<Estudiante> lista, String codigo) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }
}

