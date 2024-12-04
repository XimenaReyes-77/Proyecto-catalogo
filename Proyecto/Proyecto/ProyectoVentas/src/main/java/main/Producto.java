package main;

public class Producto {
	 private int id;
	    private String nombre;
	    private double precio;
	    private String categoria;
	    private String descripcion;

	    // Constructor vacío para Jackson
	    public Producto() {}

	    public Producto(int id, String nombre, double precio, String categoria, String descripcion) {
	        this.id = id;
	        this.nombre = nombre;
	        this.precio = precio;
	        this.categoria = categoria;
	        this.descripcion = descripcion;
	    }

	    // Getters y Setters
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public double getPrecio() {
	        return precio;
	    }

	    public void setPrecio(double precio) {
	        this.precio = precio;
	    }

	    public String getCategoria() {
	        return categoria;
	    }

	    public void setCategoria(String categoria) {
	        this.categoria = categoria;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    @Override
	    public String toString() {
	        return String.format("ID: %d, Nombre: %s, Precio: $%.2f, Categoría: %s, Descripción: %s", id, nombre, precio, categoria, descripcion);
	    }
}
