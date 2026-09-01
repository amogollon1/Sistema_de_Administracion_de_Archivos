/**
 *
 * @author ojela
 */
//Clase libro con todos sus atributos, constructor y métodos de acceso
public class Libro {
    private String ISBN;
    private String titulo;
    private String autor;
    private String editorial;
    private String year;
    private String categoria;
    private int cantidadDisponible;
    private int cantidadPrestada;
    //Constructor para el objeto de tipo Libro
    public Libro(String ISBN, String titulo, String autor, String editorial, String year, String categoria, int cantidadDisponible, int cantidadPrestada){
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.year = year;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadPrestada = cantidadPrestada;
    }
    //getters y setters públicos para acceder a la información
    public String getISBN(){
        return ISBN;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public String getTitulo(){
        return titulo;
    }
    public void setAutor(String autor){
        this.autor = autor;
    }
    public String getAutor(){
        return autor;
    }
    public void setEditorial(String editorial){
        this.editorial = editorial;
    }
    public String getEditorial(){
        return editorial;
    }
    public void setYear(String year){
        this.year = year;
    }
    public String getYear(){
        return year;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
    public String getCategoria(){
        return categoria;
    }
    public void setCantidadDisponible(int cantidadDisponible){
        this.cantidadDisponible = cantidadDisponible;
    }
    public int getCantidadDisponible(){
        return cantidadDisponible;
    }
    public void setCantidadPrestada(int cantidadPrestada){
        this.cantidadPrestada = cantidadPrestada;
    }
    public int getCantidadPrestada(){
        return cantidadPrestada;
    }

    @Override
    public String toString(){
        return ISBN + " - " + titulo + " - Autor: " + autor + "(" + year + ") - " + categoria;
    }
}