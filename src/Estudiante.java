public class Estudiante {

    // Variante B=12
    public static final String VARIANTE = "B";
    public static final int LIMITE_APROBACION = 12;   // promedio minimo para aprobar
    public static final int NOTA_MINIMA = 0;          // menor calificacion permitida
    public static final int NOTA_MAXIMA = 20;         // mayor calificacion permitida
    public static final int CANTIDAD_NOTAS = 3;       // numero de calificaciones

    private String codigo;
    private String nombre;
    private int[] notas;      // arreglo unidimensional con las 3 calificaciones

    /**
     * Constructor: crea un estudiante con sus datos.
     * @param codigo codigo unico del estudiante (ej. E001)
     * @param nombre nombre completo
     * @param notas  arreglo con las tres calificaciones
     */
    public Estudiante(String codigo, String nombre, int[] notas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.notas = notas;
    }

    // ---------------- Metodos de acceso (getters) ----------------

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int[] getNotas() {
        return notas;
    }

    // ---------------- Metodos de calculo ----------------

    public double calcularPromedio() {
        int suma = 0;                          // acumulador
        for (int i = 0; i < notas.length; i++) {
            suma = suma + notas[i];
        }
        return suma / (double) notas.length;   // division real, no entera
    }

    public int obtenerNotaMaxima() {
        int maxima = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] > maxima) {
                maxima = notas[i];
            }
        }
        return maxima;
    }

    public int obtenerNotaMinima() {
        int minima = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] < minima) {
                minima = notas[i];
            }
        }
        return minima;
    }

    /**
     * Devuelve "APROBADO" si el promedio es MAYOR O IGUAL al limite,
     * caso contrario "REPROBADO". 
     */
    public String obtenerEstado() {
        if (calcularPromedio() >= LIMITE_APROBACION) {
            return "APROBADO";
        } else {
            return "REPROBADO";
        }
    }

    /**
     * Convierte al estudiante en una linea con el formato del archivo:
     * E001;Ana Torres;15;18;12
     */
    public String aLineaArchivo() {
        String linea = codigo + ";" + nombre;
        for (int i = 0; i < notas.length; i++) {
            linea = linea + ";" + notas[i];
        }
        return linea;
    }

    /**
     * Muestra en consola todos los datos del estudiante.
     */
    public void mostrarDetalle() {
        System.out.println("Codigo       : " + codigo);
        System.out.println("Nombre       : " + nombre);
        System.out.print("Calificaciones: ");
        for (int i = 0; i < notas.length; i++) {
            System.out.print(notas[i] + "  ");
        }
        System.out.println();
        System.out.printf("Promedio     : %.2f%n", calcularPromedio());
        System.out.println("Nota maxima  : " + obtenerNotaMaxima());
        System.out.println("Nota minima  : " + obtenerNotaMinima());
        System.out.println("Estado       : " + obtenerEstado());
    }
}

