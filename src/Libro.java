/**
 *
 * @author ojela
 */
public class Libro {
    private int ISBN;
    private String titulo;
    private String autor;
    private String editorial;
    private String year;
    private String categoria;
    private int cantidadDisponible;
    private int cantidadPrestada;

    public Libro(int ISBN, String titulo, String autor, String editorial, String year, String categoria, int cantidadDisponible, int cantidadPrestada){
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.year = year;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadPrestada = cantidadPrestada;
    }

    public int getISBN(){
        return ISBN;
    }
    public int getCantidadDisponible(){
        return cantidadDisponible;
    }
    public int getCantidadPrestada(){
        return cantidadPrestada;
    }

    @Override
    public String toString(){
        return ISBN + " - " + titulo + " - Autor: " + autor + "(" + year + ") - " + categoria;
    }
}
