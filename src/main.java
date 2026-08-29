import java.util.*;
/**
 *
 * @author ojela
 */

public class main {
    public static void main(String[] args) {
        Scanner nat = new Scanner(System.in);
        
        ArbolBMasClase arbol = new ArbolBMasClase();
        TablaHash tablaHash = new TablaHash();
        MonticuloMaximo maxHeap = new MonticuloMaximo();
        MonticuloMinimo minHeap = new MonticuloMinimo();
        GestorDeArchivos gestorDeArchivos = new GestorDeArchivos();

        boolean salir = true;

        do {
            System.out.println("Sistema de Administracion de Archivos");
            System.out.println("\tMENU");
            System.out.println("Elija una opcion:");
            System.out.println("1. Carga de libros.");
            System.out.println("2. Carga de Prestamos.");
            System.out.println("3. Carga de Existencias.");
            System.out.println("4. Visualizacion de informacion.");
            System.out.println("5. Salir.");
            int opcion = nat.nextInt();

            switch(opcion){
                case 1:
                    System.out.println("\tCarga de libros.");
                    System.out.println("Cargando libros al sistema...");
                    gestorDeArchivos.cargarLibros("libros.txt", tablaHash, arbol, maxHeap, minHeap);
                    break;
                case 2:
                    System.out.println("\tCarga de prestamos.");
                    System.out.println("Cargando prestamos...");
                    gestorDeArchivos.cargarPrestamos("prestamos.txt", tablaHash, maxHeap);
                    break;
                case 3:
                    System.out.println("\tCarga de existencias.");
                    System.out.println("Actualizando existencias...");
                    gestorDeArchivos.cargarExistencias("existencias.txt", tablaHash, minHeap);
                    break;
                case 4:
                    
                    boolean menuPrincipal = false;

                    do { 
                        System.out.println("\tVisualizacion de informacion.");
                    System.out.println("Elija una opcion:");
                    System.out.println("i. Visualizacion de arbol B+ (niveles y nodos).");
                    System.out.println("ii. Visualizacion de monticulo maximo (nodos).");
                    System.out.println("iii. Visualizacion de monticulo minimo (nodos).");
                    System.out.println("iv. Visualizacion de la tabla hash (posicion + colisiones).");
                    System.out.println("v. Volver al menu principal.");
                    String eleccion = nat.next();

                    switch (eleccion) {
                        case "i":
                            System.out.println("\tVisualizacion de arbol B+ (niveles y nodos).");
                            arbol.imprimir();
                            break;
                        case "ii":
                            System.out.println("\tVisualizacion de monticulo maximo (nodos).");
                            maxHeap.mostrarVisualizacion();
                            break;
                        case "iii":
                            System.out.println("\tVisualizacion de monticulo minimo (nodos).");
                            minHeap.mostrarVisualizacion();
                            break;
                        case "iv":
                            System.out.println("\tVisualizacion de la tabla hash (posicion + colisiones).");
                            tablaHash.mostrarTabla();
                            break;
                        case "v":
                            menuPrincipal = true;
                            System.out.println("Volviendo al menu principal...");
                            break;
                        default:
                            throw new AssertionError();
                    }
                    } while (!menuPrincipal);
                    break;
                case 5:
                    salir = false;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
                    break;
            }
        } while (salir);
    }
}