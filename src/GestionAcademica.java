import java.util.ArrayList;

public class GestionAcademica {

    // Lista dinamica donde se guardan los objetos Estudiante
    private ArrayList<Estudiante> estudiantes;

    /**
     * Constructor: crea la lista vacia.
     */
    public GestionAcademica() {
        estudiantes = new ArrayList<Estudiante>();
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ArrayList<Estudiante> lista) {
        estudiantes = lista;
    }

    // REGISTRO Y VALIDACIONES

    public boolean esTextoValido(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        if (texto.contains(";")) {
            return false;
        }
        return true;
    }

    public boolean existeCodigo(String codigo) {
        return buscarEstudiante(codigo) != null;
    }


    public boolean registrarEstudiante(Estudiante nuevo) {
        if (existeCodigo(nuevo.getCodigo())) {
            return false;
        }
        estudiantes.add(nuevo);
        return true;
    }

    // BUSQUEDA Y LISTADO

    public Estudiante buscarEstudiante(String codigo) {
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante e = estudiantes.get(i);
            if (e.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return e;   // encontrado: se termina la busqueda
            }
        }
        return null;        // se recorrio toda la lista y no se encontro
    }

    /**
     * Muestra una tabla con todos los estudiantes registrados.
     */
    public void listarEstudiantes() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        imprimirEncabezado();
        for (int i = 0; i < estudiantes.size(); i++) {
            imprimirFila(estudiantes.get(i));
        }
    }

    // Imprime los titulos de la tabla
    private void imprimirEncabezado() {
        System.out.printf("%-8s %-25s %4s %4s %4s %8s %4s %4s %-10s%n",
                "CODIGO", "NOMBRE", "N1", "N2", "N3", "PROMEDIO", "MAX", "MIN", "ESTADO");
        System.out.println("-------------------------------------------------------------------------------");
    }

    // Imprime una fila de la tabla con los datos de un estudiante
    private void imprimirFila(Estudiante e) {
        int[] n = e.getNotas();
        System.out.printf("%-8s %-25s %4d %4d %4d %8.2f %4d %4d %-10s%n",
                e.getCodigo(), e.getNombre(), n[0], n[1], n[2],
                e.calcularPromedio(), e.obtenerNotaMaxima(),
                e.obtenerNotaMinima(), e.obtenerEstado());
    }

    public ArrayList<Estudiante> ordenarPorPromedio() {
        ArrayList<Estudiante> copia = new ArrayList<Estudiante>();
        for (int i = 0; i < estudiantes.size(); i++) {
            copia.add(estudiantes.get(i));
        }

        int n = copia.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (copia.get(j).calcularPromedio() < copia.get(j + 1).calcularPromedio()) {
                    Estudiante aux = copia.get(j);
                    copia.set(j, copia.get(j + 1));
                    copia.set(j + 1, aux);
                }
            }
        }
        return copia;
    }

    // REPORTE ACADEMICO

    public double calcularPromedioGeneral() {
        if (estudiantes.isEmpty()) {
            return 0;
        }
        double suma = 0;   // acumulador de promedios
        for (int i = 0; i < estudiantes.size(); i++) {
            suma = suma + estudiantes.get(i).calcularPromedio();
        }
        return suma / estudiantes.size();
    }

    /**
     * Cuenta cuantos estudiantes estan APROBADOS (contador).
     */
    public int contarAprobados() {
        int contador = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).obtenerEstado().equals("APROBADO")) {
                contador++;
            }
        }
        return contador;
    }

    public Estudiante obtenerMejorPromedio() {
        if (estudiantes.isEmpty()) {
            return null;
        }
        Estudiante mejor = estudiantes.get(0);
        for (int i = 1; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).calcularPromedio() > mejor.calcularPromedio()) {
                mejor = estudiantes.get(i);
            }
        }
        return mejor;
    }

    /**
     * Busca al estudiante con el promedio MAS BAJO (misma logica con "<").
     */
    public Estudiante obtenerPeorPromedio() {
        if (estudiantes.isEmpty()) {
            return null;
        }
        Estudiante peor = estudiantes.get(0);
        for (int i = 1; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).calcularPromedio() < peor.calcularPromedio()) {
                peor = estudiantes.get(i);
            }
        }
        return peor;
    }

    public void generarReporte() {
        System.out.println("=========== REPORTE ACADEMICO ===========");
        System.out.println("Variante: " + Estudiante.VARIANTE
                + "  |  Limite de aprobacion: " + Estudiante.LIMITE_APROBACION);

        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados. No se puede generar el reporte.");
            return;
        }

        int total = estudiantes.size();
        int aprobados = contarAprobados();
        int reprobados = total - aprobados;
        double promedioGeneral = calcularPromedioGeneral();
        double porcentajeAprobados = (aprobados * 100.0) / total;

        System.out.println("Total de estudiantes : " + total);
        System.out.println("Aprobados            : " + aprobados);
        System.out.println("Reprobados           : " + reprobados);
        System.out.printf("Promedio general     : %.2f%n", promedioGeneral);
        System.out.printf("Porcentaje aprobados : %.2f %%%n", porcentajeAprobados);

        Estudiante mejor = obtenerMejorPromedio();
        Estudiante peor = obtenerPeorPromedio();
        System.out.printf("Promedio mas alto    : %s - %s (%.2f)%n",
                mejor.getCodigo(), mejor.getNombre(), mejor.calcularPromedio());
        System.out.printf("Promedio mas bajo    : %s - %s (%.2f)%n",
                peor.getCodigo(), peor.getNombre(), peor.calcularPromedio());

        // Estudiantes por debajo del promedio general
        System.out.println();
        System.out.println("--- Estudiantes con promedio inferior al promedio general ---");
        int contadorInferiores = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante e = estudiantes.get(i);
            if (e.calcularPromedio() < promedioGeneral) {
                System.out.printf("%s - %s (%.2f)%n",
                        e.getCodigo(), e.getNombre(), e.calcularPromedio());
                contadorInferiores++;
            }
        }
        if (contadorInferiores == 0) {
            System.out.println("Ninguno.");
        }

        // Listado ordenado de mayor a menor
        System.out.println();
        System.out.println("--- Listado ordenado de mayor a menor promedio ---");
        ArrayList<Estudiante> ordenados = ordenarPorPromedio();
        imprimirEncabezado();
        for (int i = 0; i < ordenados.size(); i++) {
            imprimirFila(ordenados.get(i));
        }
    }
}

