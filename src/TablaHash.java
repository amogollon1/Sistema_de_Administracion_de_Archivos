/**
 *
 * @author ojela
 */
//
public class TablaHash {
    private static final int CAPACIDAD = 13;
    private NodoHash[] tabla;

    public TablaHash() {
        this.tabla = new NodoHash[CAPACIDAD];
    }

    //Función Hash para cadenas
    private int hash(String isbn) {
        int hash = 0;
        for (int i = 0; i < isbn.length(); i++) {
            char c = isbn.charAt(i);
            if (c != '-') { //Ignora guiones si existen
                hash = (hash * 31 + c) % CAPACIDAD; //Fórmula Hash
            }
        }
        return Math.abs(hash);
    }

    //Insertar o Actualizar si el ISBN ya existe
    public boolean insertar(Libro libro) {
        int posicion = hash(libro.getISBN());
        NodoHash actual = tabla[posicion];

        //Si la casilla está vacía, insertamos directamente
        if (actual == null) {
            tabla[posicion] = new NodoHash(libro);
            return true;
        }

        //Si hay colisión, recorremos la lista enlazada
        NodoHash anterior = null;
        while (actual != null) {
            // Si el ISBN ya existe, actualizamos la información
            if (actual.getLibro().getISBN().equals(libro.getISBN())) {
                actual.setLibro(libro);
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }

        //Si no existía en la lista, lo agregamos al final de la lista enlazada
        anterior.setSiguiente(new NodoHash(libro));
        return true;
    }

    //Buscar un libro por ISBN
    public Libro buscar(String isbn) {
        int posicion = hash(isbn);
        NodoHash actual = tabla[posicion];

        while (actual != null) {
            if (actual.getLibro().getISBN().equals(isbn)) {
                return actual.getLibro();
            }
            actual = actual.getSiguiente();
        }
        return null; //No encontrado
    }

    //Visualización de la tabla hash
    public void mostrarTabla() {
        System.out.println("\nVISUALIZACION DE TABLA HASH");
        for (int i = 0; i < CAPACIDAD; i++) {
            System.out.print("[" + i + "] ");
            NodoHash actual = tabla[i];
            
            if (actual == null) {
                System.out.println("VACIA");
            } else {
                while (actual != null) {
                    System.out.print("[ISBN: " + actual.getLibro().getISBN() +
                                       " - " + actual.getLibro().getTitulo() + "]");
                    actual = actual.getSiguiente();
                    if (actual != null) {
                        System.out.print(" -> "); //Indica la colisión encadenada
                    }
                }
                System.out.println();
            }
        }
    }
}