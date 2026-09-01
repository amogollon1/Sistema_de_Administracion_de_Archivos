import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author ojela
 */

public class MonticuloMaximo { //Orden por cantidad de libros prestados
    private NodoMonticulo raiz;

    public boolean estaVacio() {
        return raiz == null;
    }

    //Método principal para procesar préstamos
    public void actualizarOInsertar(Libro libro, int cantidadPrestada) {
        //Buscamos si el libro ya existe en el montículo
        NodoMonticulo existente = buscarNodoPorISBN(raiz, libro.getISBN());

        if (existente != null) {
            //Si ya existe, sumamos las cantidades
            int nuevaCantidad = existente.getLibro().getCantidadPrestada() + cantidadPrestada;
            existente.getLibro().setCantidadPrestada(nuevaCantidad);
            
            //Como la cantidad aumenta, hacemos flotar el nodo en el Max Heap
            burbujaArriba(existente);
        } else {
            //Si no existe, nos aseguramos que traiga la cantidad del préstamo e insertamos
            libro.setCantidadPrestada(cantidadPrestada);
            insertar(libro);
        }
    }
    //Método para insertar un libro al maxHeap
    public void insertar(Libro libro) {
        //Crear una copia independiente del libro para que la tabla hash no afecte las posiciones del heap
        Libro copiaLibro = new Libro(libro.getISBN(), libro.getTitulo(), libro.getAutor(), libro.getEditorial(), libro.getYear(), libro.getCategoria(), libro.getCantidadDisponible(), libro.getCantidadPrestada());
        copiaLibro.setCantidadPrestada(libro.getCantidadPrestada());

        NodoMonticulo nuevo = new NodoMonticulo(copiaLibro);
        if (raiz == null) {
            raiz = nuevo;
            return;
        }
        //Buscamos el primer padre disponible para mantener la propiedad de árbol completo
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

    //Flota el nodo hacia arriba si su cantidad prestada es mayor que la de su padre
    private void burbujaArriba(NodoMonticulo nodo) {
        while (nodo.padre != null && nodo.getLibro().getCantidadPrestada() > nodo.padre.getLibro().getCantidadPrestada()) {
            //Copiamos temporalmente el libro actual
            Libro temp = nodo.getLibro();
            
            //Intercambiamos referencias de libro entre nodos
            nodo.setLibro(nodo.padre.getLibro());
            nodo.padre.setLibro(temp);

            //Subimos al padre
            nodo = nodo.padre;
        }
    }

    //Busca un nodo por el ISBN del libro
    private NodoMonticulo buscarNodoPorISBN(NodoMonticulo actual, String isbn) {
        if (actual == null) return null;
        if (actual.getLibro().getISBN().equals(isbn)) return actual;

        NodoMonticulo izq = buscarNodoPorISBN(actual.izquierdo, isbn);
        if (izq != null) return izq;

        return buscarNodoPorISBN(actual.derecho, isbn);
    }

    //Busca el primer nodo disponible que le falte un hijo (izquierdo o derecho)
    private NodoMonticulo buscarPrimerPadreDisponible(NodoMonticulo actual) {
        if (actual == null) return null;

    Queue<NodoMonticulo> cola = new LinkedList<>();
    cola.add(actual);

    while (!cola.isEmpty()) {
        NodoMonticulo temp = cola.poll();

        //Si le falta alguno de los dos hijos, este es el padre disponible
        if (temp.izquierdo == null || temp.derecho == null) {
            return temp;
        }

        cola.add(temp.izquierdo);
        cola.add(temp.derecho);
    }

    return null;
    }

    //Visualización de montículo máximo
    private void mostrarMonticulo(NodoMonticulo nodo, String prefijo, boolean esDerecho) {
        if (nodo != null) {
            //Procesar primero el hijo derecho (aparecerá en la parte superior)
            mostrarMonticulo(nodo.derecho, prefijo + (esDerecho ? "    " : "│   "), true);

            //Imprimir el nodo actual
            System.out.println(prefijo + (esDerecho ? "┌── " : "└── ") +
                            "ISBN: " + nodo.getLibro().getISBN() +
                            " | Prestados: " + nodo.getLibro().getCantidadPrestada());

            //Procesar el hijo izquierdo (aparecerá en la parte inferior)
            mostrarMonticulo(nodo.izquierdo, prefijo + (esDerecho ? "│   " : "    "), false);
        }
    }

    public void mostrarVisualizacion() {
        if (raiz == null) {
            System.out.println("El monticulo maximo esta vacio.");
        } else {
            mostrarMonticulo(raiz, "", true);
        }
    }
}