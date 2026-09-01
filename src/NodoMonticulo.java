/**
 *
 * @author ojela
 */
//Clase que representa cada nodo que formará parte de los montículos (max y min)
public class NodoMonticulo {
    Libro libro;
    NodoMonticulo izquierdo;
    NodoMonticulo derecho;
    NodoMonticulo padre;
    //Constructor
    public NodoMonticulo(Libro libro) {
        this.libro = libro;
        this.izquierdo = null;
        this.derecho = null;
        this.padre = null;
    }
    //Getters y Setters
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