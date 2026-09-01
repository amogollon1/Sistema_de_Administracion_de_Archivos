/**
 *
 * @author ojela
 */
import java.util.ArrayList;
//Clase que representa cada nodo que formará parte del árbol B+
public class NodoArbol {
    boolean esHoja; //Indica si el nodo es una hoja, necesario para saber si es una clave o un valor
    ArrayList<String> claves;
    ArrayList<NodoArbol> hijos;
    //Constructor
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