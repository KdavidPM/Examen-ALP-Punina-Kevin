import java.util.Scanner;

public class Main {

    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        GestionAcademica gestion = new GestionAcademica();
        GestorArchivos archivos = new GestorArchivos();

        // RF05: recuperacion automatica al iniciar el programa
        gestion.setEstudiantes(archivos.leer());
        System.out.println("Registros recuperados: " + gestion.getEstudiantes().size());

        int opcion;
        // do-while: el menu se muestra al menos una vez y se repite hasta elegir 6
        do {
            mostrarMenu();
            opcion = leerEntero("");

            switch (opcion) {
                case 1:
                    registrar(gestion);
                    break;
                case 2:
                    gestion.listarEstudiantes();
                    break;
                case 3:
                    buscar(gestion);
                    break;
                case 4:
                    gestion.generarReporte();
                    break;
                case 5:
                    if (archivos.guardar(gestion.getEstudiantes())) {
                        System.out.println("Registros guardados en " + GestorArchivos.RUTA_ARCHIVO);
                    }
                    break;
                case 6:
                    // Antes de salir se guardan los registros
                    if (archivos.guardar(gestion.getEstudiantes())) {
                        System.out.println("Registros guardados en " + GestorArchivos.RUTA_ARCHIVO);
                    }
                    System.out.println("Gracias por usar AulaLogic. Hasta pronto.");
                    break;
                default:
                    // Opcion incorrecta: se avisa y el menu se repite
                    System.out.println("Opcion incorrecta. Ingrese un numero del 1 al 6.");
            }
            System.out.println();
        } while (opcion != 6);

        teclado.close();
    }

    //Imprime el menu principal obligatorio.

    public static void mostrarMenu() {
        System.out.println("=====================================");
        System.out.println("SISTEMA AULALOGIC");
        System.out.println("=====================================");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Listar estudiantes");
        System.out.println("3. Buscar estudiante");
        System.out.println("4. Generar reporte academico");
        System.out.println("5. Guardar registros");
        System.out.println("6. Salir");
        System.out.println("=====================================");
        System.out.print("Seleccione una opcion: ");
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine();
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                System.out.print("Valor no valido. Ingrese un numero entero: ");
                mensaje = "";
            }
        }
    }

    
     //Pide una calificacion y la repite mientras este fuera del rango 0-20.
    public static int leerNota(int numero) {
        int nota;
        do {
            nota = leerEntero("Calificacion " + numero + " ("
                    + Estudiante.NOTA_MINIMA + "-" + Estudiante.NOTA_MAXIMA + "): ");
            if (nota < Estudiante.NOTA_MINIMA || nota > Estudiante.NOTA_MAXIMA) {
                System.out.println("Error: la calificacion debe estar entre "
                        + Estudiante.NOTA_MINIMA + " y " + Estudiante.NOTA_MAXIMA + ".");
            }
        } while (nota < Estudiante.NOTA_MINIMA || nota > Estudiante.NOTA_MAXIMA);
        return nota;
    }

    
    //RF01: registra un estudiante pidiendo y validando cada dato.

    public static void registrar(GestionAcademica gestion) {
        System.out.println("--- Registro de estudiante ---");

        // 1. Codigo: no vacio, sin ";" y no repetido
        String codigo;
        System.out.print("Codigo: ");
        codigo = teclado.nextLine().trim();
        if (!gestion.esTextoValido(codigo)) {
            System.out.println("Error: el codigo no puede estar vacio ni contener ';'.");
            return;
        }
        if (gestion.existeCodigo(codigo)) {
            System.out.println("Error: el codigo " + codigo + " ya existe. Registro cancelado.");
            return;
        }

        // 2. Nombre: se repite hasta que sea valido
        String nombre;
        do {
            System.out.print("Nombre completo: ");
            nombre = teclado.nextLine().trim();
            if (!gestion.esTextoValido(nombre)) {
                System.out.println("Error: el nombre no puede estar vacio ni contener ';'.");
            }
        } while (!gestion.esTextoValido(nombre));

        // 3. Calificaciones: se guardan en un arreglo de 3 posiciones
        int[] notas = new int[Estudiante.CANTIDAD_NOTAS];
        for (int i = 0; i < notas.length; i++) {
            notas[i] = leerNota(i + 1);
        }

        // 4. Crear el objeto y agregarlo a la lista
        Estudiante nuevo = new Estudiante(codigo, nombre, notas);
        if (gestion.registrarEstudiante(nuevo)) {
            System.out.printf("Estudiante registrado. Promedio: %.2f - %s%n",
                    nuevo.calcularPromedio(), nuevo.obtenerEstado());
        }
    }

    /**
     * RF03: busca un estudiante por codigo y muestra su informacion.
     */
    public static void buscar(GestionAcademica gestion) {
        System.out.print("Ingrese el codigo a buscar: ");
        String codigo = teclado.nextLine().trim();
        Estudiante e = gestion.buscarEstudiante(codigo);
        if (e == null) {
            System.out.println("No existe un estudiante con el codigo " + codigo + ".");
        } else {
            e.mostrarDetalle();
        }
    }
}

