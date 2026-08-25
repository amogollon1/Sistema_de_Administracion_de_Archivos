/**
 *
 * @author ojela
 */
import java.util.Collections;

public class ArbolBMasClase {
    private NodoArbol raiz;
    private final int grado = 2; //Número máximo de hijos por nodo

    public ArbolBMasClase() {
        raiz = new NodoArbol(true);
    }

    public void insertar(String clave) {
        NodoArbol r = raiz;
        if (r.esHoja && r.claves.size() < 2 * grado - 1) {
            r.claves.add(clave);
            Collections.sort(r.claves);
            return;
        }
        if (r.esHoja && r.claves.size() == 2 * grado - 1) {
            NodoArbol nuevo = new NodoArbol(false);
            nuevo.hijos.add(r);
            dividir(nuevo, 0);
            raiz = nuevo;
            insertarNoLleno(raiz, clave);
            return;
        }
        if (!r.esHoja) {
            insertarNoLleno(r, clave);
            return;
        }
    }

    private void insertarNoLleno(NodoArbol nodo, String clave) {
        if (nodo.esHoja) {
            nodo.claves.add(clave);
            Collections.sort(nodo.claves);
        } else {
            int i = 0;
            while (i < nodo.claves.size() && clave.compareTo(nodo.claves.get(i)) >= 0) 
                i++;
            NodoArbol hijo = nodo.hijos.get(i);
            // Solo divide el hijo si está lleno
            if (hijo.claves.size() == 2 * grado - 1) {
                dividir(nodo, i);
                if (clave.compareTo(nodo.claves.get(i)) >= 0){
                    i++;
                } 
            }
            insertarNoLleno(nodo.hijos.get(i), clave);
        }
    }

    private void dividir(NodoArbol padre, int indice) {
        NodoArbol hijo = padre.hijos.get(indice);
        NodoArbol nuevo = new NodoArbol(hijo.esHoja);
        int medio = grado - 1;

        if (hijo.esHoja) {
            nuevo.claves.addAll(hijo.claves.subList(medio, hijo.claves.size()));
            hijo.claves.subList(medio, hijo.claves.size()).clear();
            String claveSeparadora = nuevo.claves.get(0);
            padre.claves.add(indice, claveSeparadora);
            padre.hijos.add(indice + 1, nuevo);
        } else {
            
            // Se obtiene la clave separadora antes de borrar
            String claveSeparadora = hijo.claves.get(medio);

            // Se pasa las claves y los hijos
            nuevo.claves.addAll(hijo.claves.subList(medio + 1, hijo.claves.size()));
            nuevo.hijos.addAll(hijo.hijos.subList(medio + 1, hijo.hijos.size()));

            // Ya se puede limpiar
            hijo.claves.subList(medio, hijo.claves.size()).clear();
            hijo.hijos.subList(medio + 1, hijo.hijos.size()).clear();

            // Se agrega la clave al padre
            padre.claves.add(indice, claveSeparadora);
            padre.hijos.add(indice + 1, nuevo);
        }
    }

    public void imprimir() {
        imprimirNodo(raiz, 0);
        System.out.println("");
    }

    private void imprimirNodo(NodoArbol nodo, int nivel) {
        System.out.println("Nivel: " + nivel + ": " + nodo.claves);
        if (!nodo.esHoja) {
            System.out.println("Hijos del nodo en nivel " + nivel + ": " + nodo.hijos.size());
            for (NodoArbol hijo : nodo.hijos) {
                imprimirNodo(hijo, nivel + 1);
            }
        }
    }

    // Búsqueda del dato (se cambio a boolean porque es más versátil)
    public boolean buscar(String clave) {
        return buscarRecursivo(raiz, clave);
    }

    private boolean buscarRecursivo(NodoArbol nodo, String clave) {
        if (nodo.esHoja) {
            return nodo.claves.contains(clave);
        }
        int i = 0;
        while (i < nodo.claves.size() && clave.compareTo(nodo.claves.get(i)) >= 0) {
            i++;
        }
        return buscarRecursivo(nodo.hijos.get(i), clave);
    }

    //eliminar dato
    public void eliminar(String clave) {
        if (raiz == null) return;

        eliminarRecursivo(raiz, clave);

        // Caso especial: La raíz se quedó vacía después de una fusión
        if (!raiz.esHoja && raiz.claves.isEmpty()) {
            // El primer hijo se convierte en la nueva raíz
            raiz = raiz.hijos.get(0);
        }
    }

    private void eliminarRecursivo(NodoArbol nodo, String clave) {
        int i = 0;
        while (i < nodo.claves.size() && clave.compareTo(nodo.claves.get(i)) >= 0) {
            i++;
        }

        if (nodo.esHoja) {
            // Caso Base, Eliminar de la hoja
            nodo.claves.remove(clave);
            return;
        }

        // Paso Recursivo: Eliminar en el hijo correspondiente
        // Si la clave es mayor o igual a nodo.claves.get(i-1), va por el hijo 'i'
        NodoArbol hijo = nodo.hijos.get(i);
        eliminarRecursivo(hijo, clave);

        // Al regresar de la recursión, verificamos si el hijo sufrió UNDERFLOW
        int minClaves = grado - 1; // Para grado = 2, el mínimo es 1
        if (hijo.claves.size() < minClaves) {
            rebalancear(nodo, i);
        }

        // Si la clave borrada estaba en el índice del padre, actualizamos clave guía
        actualizarClavesGuia(nodo);
    }

    private void rebalancear(NodoArbol padre, int idxHijo) {
        NodoArbol hijo = padre.hijos.get(idxHijo);

        // 1. Intentar pedir PRESTADO al hermano izquierdo
        if (idxHijo > 0) {
            NodoArbol hermanoIzq = padre.hijos.get(idxHijo - 1);
            if (hermanoIzq.claves.size() > grado - 1) {
                prestarDeIzquierda(padre, idxHijo, hermanoIzq, hijo);
                return;
            }
        }

        // 2. Intentar pedir PRESTADO al hermano derecho
        if (idxHijo < padre.hijos.size() - 1) {
            NodoArbol hermanoDer = padre.hijos.get(idxHijo + 1);
            if (hermanoDer.claves.size() > grado - 1) {
                prestarDeDerecha(padre, idxHijo, hermanoDer, hijo);
                return;
            }
        }

        // 3. Si no se puede prestar -> FUSIÓN (Merge)
        if (idxHijo > 0) {
            // Fusionar con el hermano izquierdo
            fusionar(padre, idxHijo - 1);
        } else {
            // Fusionar con el hermano derecho
            fusionar(padre, idxHijo);
        }
    }

    private void prestarDeIzquierda(NodoArbol padre, int idxHijo, NodoArbol hermanoIzq, NodoArbol hijo) {
        if (hijo.esHoja) {
            // Toma la última clave del hermano izquierdo
            String clavePrestada = hermanoIzq.claves.remove(hermanoIzq.claves.size() - 1);
            hijo.claves.add(0, clavePrestada);
            // Actualiza la clave divisora en el padre
            padre.claves.set(idxHijo - 1, hijo.claves.get(0));
        } else {
            // Rotación en nodos internos
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
            // Toma la primera clave del hermano derecho
            String clavePrestada = hermanoDer.claves.remove(0);
            hijo.claves.add(clavePrestada);
            // Actualiza la clave divisora en el padre
            padre.claves.set(idxHijo, hermanoDer.claves.get(0));
        } else {
            // Rotación en nodos internos
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
            // Pasa las claves de la derecha a la izquierda
            izq.claves.addAll(der.claves);
            // Quita la clave del padre y el hijo sobrante
            padre.claves.remove(idxIzq);
            padre.hijos.remove(idxIzq + 1);
        } else {
            // En nodos internos, la clave del padre baja a la fusión
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
            // La clave en el nodo interno debe corresponder a la primera clave de su subárbol derecho
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
