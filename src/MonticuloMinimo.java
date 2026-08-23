/**
 *
 * @author ojela
 */

public class MonticuloMinimo { //Orden por cantidad de libros disponibles
    private NodoMonticulo raiz;

    public boolean estaVacio() {
        return raiz == null;
    }

    // Método principal para procesar existencias (Requisito del proyecto)
    public void actualizarOInsertar(Libro libro, int cantidadDisponible) {
        // 1. Buscamos si el libro ya existe en el montículo
        NodoMonticulo existente = buscarNodoPorISBN(raiz, libro.getISBN());

        if (existente != null) {
            // 2. Si ya existe, sumamos las cantidades
            int nuevaCantidad = existente.getLibro().getCantidadDisponible() + cantidadDisponible;
            existente.getLibro().setCantidadDisponible(nuevaCantidad);
            
            // Como la cantidad AUMENTÓ, hacemos que se hunda el nodo en el Min Heap
            burbujaAbajo(existente);
        } else {
            // 3. Si no existe, nos aseguramos que traiga la cantidad del préstamo e insertamos
            libro.setCantidadDisponible(cantidadDisponible);
            insertar(libro);
        }
    }

    // Inserción en orden de árbol completo (Nivel por Nivel de izquierda a derecha)
    public void insertar(Libro libro) {
        NodoMonticulo nuevo = new NodoMonticulo(libro);
        if (raiz == null) {
            raiz = nuevo;
            return;
        }

        // Buscamos el primer padre disponible para mantener la propiedad de árbol completo
        NodoMonticulo padre = buscarPrimerPadreDisponible(raiz);
        if (padre != null) {
            if (padre.izquierdo == null) {
                padre.izquierdo = nuevo;
            } else {
                padre.derecho = nuevo;
            }
            nuevo.padre = padre;
            burbujaArriba(nuevo);
        }
    }

    // Flota el nodo hacia arriba si su cantidad disponible es menor que la de su padre
    private void burbujaArriba(NodoMonticulo nodo) {
        while (nodo.padre != null && nodo.getLibro().getCantidadDisponible() < nodo.padre.getLibro().getCantidadDisponible()) {
            // Intercambiamos los objetos Libro
            Libro temp = nodo.getLibro();
            nodo.setLibro(nodo.padre.getLibro());
            nodo.padre.setLibro(temp);

            nodo = nodo.padre;
        }
    }

    //Hunde el libro si su cantidad disponible es mayor que la de su padre
    private void burbujaAbajo(NodoMonticulo nodo){
        while(nodo != null){
            NodoMonticulo menor = nodo;
            if(nodo.izquierdo != null && nodo.izquierdo.getLibro().getCantidadDisponible() < menor.libro.getCantidadDisponible()){
                menor = nodo.izquierdo;
            }
            if(nodo.derecho != null && nodo.derecho.getLibro().getCantidadDisponible() < menor.libro.getCantidadDisponible()){
                menor = nodo.derecho;
            }
            if(menor == nodo) break; //ya esta en su lugar
            
            Libro temp = nodo.libro;
            nodo.libro = menor.libro;
            menor.libro = temp;
            nodo = menor;
        }
    }

    // Busca un nodo por el ISBN del libro (Búsqueda en árbol)
    private NodoMonticulo buscarNodoPorISBN(NodoMonticulo actual, String isbn) {
        if (actual == null) return null;
        if (actual.getLibro().getISBN() == isbn) return actual;

        NodoMonticulo izq = buscarNodoPorISBN(actual.izquierdo, isbn);
        if (izq != null) return izq;

        return buscarNodoPorISBN(actual.derecho, isbn);
    }

    // Busca el primer nodo disponible que le falte un hijo (izquierdo o derecho)
    private NodoMonticulo buscarPrimerPadreDisponible(NodoMonticulo actual) {
        if (actual == null) return null;
        if (actual.izquierdo == null || actual.derecho == null) return actual;

        // Búsqueda por niveles sencilla
        NodoMonticulo izq = buscarPrimerPadreDisponible(actual.izquierdo);
        if (izq != null) return izq;

        return buscarPrimerPadreDisponible(actual.derecho);
    }

    // Visualización de montículo mínimo (nodos)
    public void mostrarMonticulo(NodoMonticulo nodo, String prefijo, boolean esIzquierdo) { //Prefijo usado para tabular los nodos y que se vean mejor
        if (nodo != null) {
            System.out.println(prefijo + (esIzquierdo ? "├── " : "└── ") +
                               "ISBN: " + nodo.getLibro().getISBN() +
                               " | Prestados: " + nodo.getLibro().getCantidadPrestada());
            mostrarMonticulo(nodo.izquierdo, prefijo + (esIzquierdo ? "│   " : "    "), true);
            mostrarMonticulo(nodo.derecho, prefijo + (esIzquierdo ? "│   " : "    "), false);
        }
    }

    public void mostrarVisualizacion() {
        if (raiz == null) {
            System.out.println("El monticulo minimo esta vacio.");
        } else {
            mostrarMonticulo(raiz, "", false);
        }
    }
}