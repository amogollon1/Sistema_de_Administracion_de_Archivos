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

    public void cargarLibros(String ruta, TablaHash tablaHash, ArbolBMasClase arbolB){
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            String linea;
            while((linea = br.readLine()) != null){
                if(linea.trim().isEmpty()) continue;

                String[] datos = separadorPorPipeLine(linea,6);

                if(datos[0] != null){
                    Libro libro = new Libro(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], 0, 0);

                    tablaHash.insertar(libro);
                    arbolB.insertar(libro.getISBN());
                }
            }
        }catch(IOException e){
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }
}
