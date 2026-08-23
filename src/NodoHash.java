public class NodoHash {
    private Libro libro;
    private NodoHash siguiente;

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