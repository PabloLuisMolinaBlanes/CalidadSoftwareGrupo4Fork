package com.example.pinbox;

import java.io.Serializable;
import java.util.Objects;

public class Producto implements Serializable {
    private String nombre;
    private int cantidad;
    private long idProducto;

    private boolean favorito;

    public Producto(String nombre, int cantidad, boolean favorito) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.favorito = favorito;
        this.idProducto= this.hashCode();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isFavorito() { return favorito;}

    public void setFavorito(boolean favorito) { this.favorito = favorito;}
    @Override
    public int hashCode(){
     return Objects.hash(this.nombre,this.cantidad,this.favorito);
    }
    public long getIdProducto(){
        return this.idProducto;
    }
    public void setIdProducto(long id){
        this.idProducto=id;
    }
}

