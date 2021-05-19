package com.example.soporteinv;

public class Autor {
    private int id;
    private String nombreAutor;

    public Autor(int id, String nombreAutor) {
        this.id = id;
        this.nombreAutor = nombreAutor;
    }

    public Autor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    @Override
    public String toString(){
        return
                "ID: " +id +
                        " | Nombre:" +nombreAutor;
    }

}
