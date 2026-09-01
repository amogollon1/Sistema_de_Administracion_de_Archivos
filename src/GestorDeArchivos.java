import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author ojela
 */

public class GestorDeArchivos {
    //Método para separar la información brindada en los .txt
    private String[] separadorPorPipeLine(String linea, int camposEsperados){
        String[] resultado = new String[camposEsperados];
        int index = 0;
        String acumulador = "";

        for(int i = 0; i < linea.length(); i++){
            char c = linea.charAt(i);
            if(c == '|'){
                if(index < camposEsperados){
                    resultado[index] = acumulador.trim();
                    acumulador = "";
                    index++;
                }
            }else{
                acumulador += c;
            }
            if(index < camposEsperados){
                resultado[index] = acumulador.trim();
            }
        }
        return resultado;
    }
    //Método para obtener y guardar los libros provenientes de "libros.txt" en las estructuras
    public void cargarLibros(String ruta, TablaHash tablaHash, ArbolBMasClase arbolB, MonticuloMaximo maxHeap, MonticuloMinimo minHeap){
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            String linea;
            while((linea = br.readLine()) != null){
                if(linea.trim().isEmpty()) continue;

                String[] datos = separadorPorPipeLine(linea,8);
                
                //Se salta la primer línea del .txt debido a que es un encabezado
                if(datos[0].trim().equalsIgnoreCase("ISBN") || datos[2].trim().equalsIgnoreCase("cantidad")){
                    continue;
                }
                if(datos[0] != null){
                    Libro libro = new Libro(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], Integer.parseInt(datos[6]), Integer.parseInt(datos[7]));

                    tablaHash.insertar(libro);
                    arbolB.insertar(libro.getISBN());
                    maxHeap.insertar(libro);
                    minHeap.insertar(libro);

                    System.out.println("Libros cargados correctamente.");
                }
            }
        }catch(IOException e){
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }
    //Método para obtener, guardar y actualizar los prestamos provenientes de "prestamos.txt" en el montículo máximo
    public void cargarPrestamos(String ruta, TablaHash tablaHash, MonticuloMaximo maxHeap){
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            String linea;
            while((linea = br.readLine()) != null){
                if(linea.trim().isEmpty()) continue;
                //Se llama al método para obtener los datos según el formato proporcionado
                String[] datos = separadorPorPipeLine(linea, 3);
                //Se obtienen los datos para poder actualizar al libro original (se ignora la fecha ya que solo es un valor de registro extra)
                if(datos[0] != null){
                    String isbn = datos[0].trim(); //Se toma el ISBN
                    //Se salta la primer línea del .txt debido a que es un encabezado
                    if(datos[0].trim().equalsIgnoreCase("ISBN") || datos[2].equalsIgnoreCase("cantidad")){
                        continue;
                    }
                    int cantidadPrestada = Integer.parseInt(datos[2].trim()); //Se toma la cantidad prestada

                    Libro libro = tablaHash.buscar(isbn); //Se obtiene el libro original usando la taba hash

                    if(libro != null){
                        int totalPrestamos = libro.getCantidadPrestada() + cantidadPrestada; //Se calcula la nueva cantidad total en préstamo
                        libro.setCantidadPrestada(totalPrestamos); //Se actualiza el valor antiguo por el nuevo
                    }
                    maxHeap.actualizarOInsertar(libro, cantidadPrestada); //Se reordena luego de modificar la cantidad prestada
                    System.out.println("Prestamos agregados correctamente.");
                }
            }
        }catch(IOException e){
            System.out.println("Error al cargar los prestamos: " + e.getMessage());
        }
    }
    //Método para obtener, guardar y actualizar las existencias provenientes de "existencias.txt" en el montículo mínimo
    public void cargarExistencias(String ruta, TablaHash tablaHash, MonticuloMinimo minHeap){
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            String linea;
            while((linea = br.readLine()) != null){
                if(linea.trim().isEmpty()) continue;
                //Se llama al método para obtener los datos según el formato proporcionado
                String[] datos = separadorPorPipeLine(linea, 3);
                //Se obtienen los datos para poder actualizar al libro original (se ignora la fecha ya que solo es un valor de registro extra)
                if(datos[0] != null){
                    String isbn = datos[0].trim(); //Se toma el ISBN
                    //Se salta la primer línea del .txt debido a que es un encabezado
                    if(datos[0].trim().equalsIgnoreCase("ISBN") || datos[2].equalsIgnoreCase("cantidad")){
                        continue;
                    }
                    int cantidadDisponible = Integer.parseInt(datos[2].trim()); //Se toma la cantidad disponible

                    Libro libro = tablaHash.buscar(isbn); //Se obtiene el libro original usando la taba hash

                    if(libro != null){
                        int totalDisponible = libro.getCantidadDisponible() + cantidadDisponible; //Se calcula la nueva cantidad total disponible
                        libro.setCantidadDisponible(totalDisponible); //Se actualiza el valor antiguo por el nuevo
                    }
                    minHeap.actualizarOInsertar(libro, cantidadDisponible); //Se reordena luego de modificar la cantidad disponible
                    System.out.println("Disponibilidad actualizada correctamente.");
                }
            }
        }catch(IOException e){
            System.out.println("Error al cargar los prestamos: " + e.getMessage());
        }
    }
}