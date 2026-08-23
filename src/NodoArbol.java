/**
 *
 * @author ojela
 */
import java.util.ArrayList;

public class NodoArbol {
    boolean esHoja;
    ArrayList<Integer> claves;
    ArrayList<NodoArbol> hijos;

    public NodoArbol(boolean esHoja) {
        this.esHoja = esHoja;
        this.claves = new ArrayList<>();
        this.hijos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Nodo{" + "esHoja=" + esHoja + ", claves=" + claves + ", hijos=" + hijos + '}';
    }
}
