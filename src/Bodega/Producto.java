/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bodega;

/**
 *
 * @author juans
 */
public class Producto {
    private String nombre;
    private String descripcion;
    private String categoria;
    private String medida;
    private int stock;

    public Producto(String nombre, String descripcion, String categoria, String medida, int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.medida = medida;
        this.stock = stock;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getMedida() { return medida; }
    public void setMedida(String medida) { this.medida = medida; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
