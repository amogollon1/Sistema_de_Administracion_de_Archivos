/**
 *
 * @author ojela
 */

public class NodoMonticulo {
    Libro libro;
    NodoMonticulo izquierdo;
    NodoMonticulo derecho;
    NodoMonticulo padre;

    public NodoMonticulo(Libro libro) {
        this.libro = libro;
        this.izquierdo = null;
        this.derecho = null;
        this.padre = null;
    }

    public Libro getLibro() {
        return libro;
    }
    public void setLibro(Libro libro){
        this.libro = libro;
    }
    public NodoMonticulo getIzquierdo() {
        return izquierdo;
    }

    public NodoMonticulo getDerecho() {
        return derecho;
    }

    public NodoMonticulo getPadre() {
        return padre;
    }
}