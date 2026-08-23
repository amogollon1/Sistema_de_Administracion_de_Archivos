/**
 *
 * @author ojela
 */

class TablaHash {
    private Libro[] tabla = new Libro[13];
    private boolean[] eliminado = new boolean[13];

    private int hash(int codigo) {
        return Math.abs(codigo) % 13;
    }

    Libro buscar(int codigo) {
        int posicion = hash(codigo);

        for (int i = 0; i < 13; i++) {
            int indice = (posicion + i) % 13;

            if (tabla[indice] != null && tabla[indice].getISBN() == codigo)
                return tabla[indice];

            if (tabla[indice] == null && !eliminado[indice])
                return null;
        }

        return null;
    }

    int obtenerPosicion(int codigo) {
        int posicion = hash(codigo);

        for (int i = 0; i < 13; i++) {
            int indice = (posicion + i) % 13;

            if (tabla[indice] != null && tabla[indice].getISBN() == codigo)
                return indice;

            if (tabla[indice] == null && !eliminado[indice])
                return -1;
        }

        return -1;
    }

    boolean insertar(Libro libro) {
        if (buscar(libro.getISBN()) != null)
            return false;

        int posicion = hash(libro.getISBN());

        for (int i = 0; i < 13; i++) {
            int indice = (posicion + i) % 13;

            if (tabla[indice] == null) {
                tabla[indice] = libro;
                eliminado[indice] = false;
                return true;
            }
        }

        return false;
    }

    boolean eliminar(int codigo) {
        int posicion = obtenerPosicion(codigo);

        if (posicion == -1)
            return false;

        tabla[posicion] = null;
        eliminado[posicion] = true;
        return true;
    }

    void mostrarTabla() {
        System.out.println("\nTABLA HASH");

        for (int i = 0; i < 13; i++) {
            if (tabla[i] == null)
                System.out.println("[" + i + "] VACIA");
            else
                System.out.println("[" + i + "] Codigo: " + tabla[i].getISBN() + " | " + tabla[i].getTitulo());
        }
    }
}