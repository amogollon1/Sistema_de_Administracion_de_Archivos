import java.util.ArrayList;

/**
 * @author ojela
 */
public class ArbolBMasClase {
    private NodoArbol raiz;
    private final int grado = 2; // t = 2 -> máx 3 claves por nodo (2*t - 1)

    public ArbolBMasClase() {
        raiz = new NodoArbol(true);
    }

    private int compararClaves(String s1, String s2) {
        try {
            int n1 = Integer.parseInt(s1);
            int n2 = Integer.parseInt(s2);
            return Integer.compare(n1, n2);
        } catch (NumberFormatException e) {
            return s1.compareTo(s2);
        }
    }

    public void insertar(String clave) {
        insertarRecursivo(raiz, clave);
        // Si la raíz superó el límite de claves, se divide y se crea una nueva raíz
        if (raiz.claves.size() >= 2 * grado) {
            NodoArbol nuevaRaiz = new NodoArbol(false);
            nuevaRaiz.hijos.add(raiz);
            dividirHijo(nuevaRaiz, 0);
            raiz = nuevaRaiz;
        }
    }

    private void insertarRecursivo(NodoArbol nodo, String clave) {
        if (nodo.esHoja) {
            int pos = 0;
            while (pos < nodo.claves.size() && compararClaves(clave, nodo.claves.get(pos)) > 0) {
                pos++;
            }
            nodo.claves.add(pos, clave);
        } else {
            int i = 0;
            while (i < nodo.claves.size() && compararClaves(clave, nodo.claves.get(i)) >= 0) {
                i++;
            }
            NodoArbol hijo = nodo.hijos.get(i);
            insertarRecursivo(hijo, clave);

            // Al regresar, si el hijo sobrepasó el límite (4 claves), se divide
            if (hijo.claves.size() >= 2 * grado) {
                dividirHijo(nodo, i);
            }
        }
    }

    private void dividirHijo(NodoArbol padre, int i) {
        NodoArbol hijo = padre.hijos.get(i);
        NodoArbol nuevo = new NodoArbol(hijo.esHoja);
        int medio = hijo.claves.size() / 2;

        if (hijo.esHoja) {
            // Pasar la segunda mitad al nuevo nodo hoja
            nuevo.claves.addAll(new ArrayList<>(hijo.claves.subList(medio, hijo.claves.size())));
            hijo.claves.subList(medio, hijo.claves.size()).clear();

            // En B+, se copia la primera clave de la hoja derecha al padre
            padre.claves.add(i, nuevo.claves.get(0));
            padre.hijos.add(i + 1, nuevo);
        } else {
            // En nodos internos, la clave del medio sube al padre y no se mantiene en los hijos
            String clavePromovida = hijo.claves.get(medio);

            nuevo.claves.addAll(new ArrayList<>(hijo.claves.subList(medio + 1, hijo.claves.size())));
            nuevo.hijos.addAll(new ArrayList<>(hijo.hijos.subList(medio + 1, hijo.hijos.size())));

            hijo.claves.subList(medio, hijo.claves.size()).clear();
            hijo.hijos.subList(medio + 1, hijo.hijos.size()).clear();

            padre.claves.add(i, clavePromovida);
            padre.hijos.add(i + 1, nuevo);
        }
    }

    public void imprimir() {
        imprimirNodo(raiz, 0);
        System.out.println("");
    }

    private void imprimirNodo(NodoArbol nodo, int nivel) {
        System.out.println("Nivel " + nivel + " (" + (nodo.esHoja ? "Hoja" : "Interno") + "): " + nodo.claves);
        if (!nodo.esHoja) {
            System.out.println("Hijos del nodo en nivel " + nivel + ": " + nodo.hijos.size());
            for (NodoArbol hijo : nodo.hijos) {
                imprimirNodo(hijo, nivel + 1);
            }
        }
    }

    public boolean buscar(String clave) {
        return buscarRecursivo(raiz, clave);
    }

    private boolean buscarRecursivo(NodoArbol nodo, String clave) {
        if (nodo.esHoja) {
            return nodo.claves.contains(clave);
        }
        int i = 0;
        while (i < nodo.claves.size() && compararClaves(clave, nodo.claves.get(i)) >= 0) {
            i++;
        }
        return buscarRecursivo(nodo.hijos.get(i), clave);
    }

    public void eliminar(String clave) {
        if (raiz == null) return;
        eliminarRecursivo(raiz, clave);

        if (!raiz.esHoja && raiz.claves.isEmpty()) {
            raiz = raiz.hijos.get(0);
        }
    }

    private void eliminarRecursivo(NodoArbol nodo, String clave) {
        int i = 0;
        while (i < nodo.claves.size() && compararClaves(clave, nodo.claves.get(i)) >= 0) {
            i++;
        }

        if (nodo.esHoja) {
            nodo.claves.remove(clave);
            return;
        }

        NodoArbol hijo = nodo.hijos.get(i);
        eliminarRecursivo(hijo, clave);

        int minClaves = grado - 1;
        if (hijo.claves.size() < minClaves) {
            rebalancear(nodo, i);
        }

        actualizarClavesGuia(nodo);
    }

    private void rebalancear(NodoArbol padre, int idxHijo) {
        NodoArbol hijo = padre.hijos.get(idxHijo);

        if (idxHijo > 0) {
            NodoArbol hermanoIzq = padre.hijos.get(idxHijo - 1);
            if (hermanoIzq.claves.size() > grado - 1) {
                prestarDeIzquierda(padre, idxHijo, hermanoIzq, hijo);
                return;
            }
        }

        if (idxHijo < padre.hijos.size() - 1) {
            NodoArbol hermanoDer = padre.hijos.get(idxHijo + 1);
            if (hermanoDer.claves.size() > grado - 1) {
                prestarDeDerecha(padre, idxHijo, hermanoDer, hijo);
                return;
            }
        }

        if (idxHijo > 0) {
            fusionar(padre, idxHijo - 1);
        } else {
            fusionar(padre, idxHijo);
        }
    }

    private void prestarDeIzquierda(NodoArbol padre, int idxHijo, NodoArbol hermanoIzq, NodoArbol hijo) {
        if (hijo.esHoja) {
            String clavePrestada = hermanoIzq.claves.remove(hermanoIzq.claves.size() - 1);
            hijo.claves.add(0, clavePrestada);
            padre.claves.set(idxHijo - 1, hijo.claves.get(0));
        } else {
            String clavePadre = padre.claves.get(idxHijo - 1);
            String ultimaClaveHermano = hermanoIzq.claves.remove(hermanoIzq.claves.size() - 1);
            NodoArbol ultimoHijoHermano = hermanoIzq.hijos.remove(hermanoIzq.hijos.size() - 1);

            hijo.claves.add(0, clavePadre);
            hijo.hijos.add(0, ultimoHijoHermano);
            padre.claves.set(idxHijo - 1, ultimaClaveHermano);
        }
    }

    private void prestarDeDerecha(NodoArbol padre, int idxHijo, NodoArbol hermanoDer, NodoArbol hijo) {
        if (hijo.esHoja) {
            String clavePrestada = hermanoDer.claves.remove(0);
            hijo.claves.add(clavePrestada);
            padre.claves.set(idxHijo, hermanoDer.claves.get(0));
        } else {
            String clavePadre = padre.claves.get(idxHijo);
            String primeraClaveHermano = hermanoDer.claves.remove(0);
            NodoArbol primerHijoHermano = hermanoDer.hijos.remove(0);

            hijo.claves.add(clavePadre);
            hijo.hijos.add(primerHijoHermano);
            padre.claves.set(idxHijo, primeraClaveHermano);
        }
    }

    private void fusionar(NodoArbol padre, int idxIzq) {
        NodoArbol izq = padre.hijos.get(idxIzq);
        NodoArbol der = padre.hijos.get(idxIzq + 1);

        if (izq.esHoja) {
            izq.claves.addAll(der.claves);
            padre.claves.remove(idxIzq);
            padre.hijos.remove(idxIzq + 1);
        } else {
            String clavePadre = padre.claves.remove(idxIzq);
            izq.claves.add(clavePadre);
            izq.claves.addAll(der.claves);
            izq.hijos.addAll(der.hijos);
            padre.hijos.remove(idxIzq + 1);
        }
    }

    private void actualizarClavesGuia(NodoArbol nodo) {
        if (nodo.esHoja) return;
        for (int j = 0; j < nodo.claves.size(); j++) {
            NodoArbol subArbolDerecho = nodo.hijos.get(j + 1);
            while (!subArbolDerecho.esHoja) {
                subArbolDerecho = subArbolDerecho.hijos.get(0);
            }
            if (!subArbolDerecho.claves.isEmpty()) {
                nodo.claves.set(j, subArbolDerecho.claves.get(0));
            }
        }
    }
}