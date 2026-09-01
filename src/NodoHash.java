/**
 *
 * @author ojela
 */
//Clase que representa cada nodo que formará parte de la tabla Hash
public class NodoHash {
    private Libro libro;
    private NodoHash siguiente;
    //Constructor
    public NodoHash(Libro libro) {
        this.libro = libro;
        this.siguiente = null;
    }

    // Getters y Setters
    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }
    public NodoHash getSiguiente() { return siguiente; }
    public void setSiguiente(NodoHash siguiente) { this.siguiente = siguiente; }
}