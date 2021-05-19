package com.example.soporteinv;

public class Alumno {

    private String carnet;
    private String nombre;
    private String apellidos;

    public Alumno() {
    }

    public Alumno(String carnet, String nombre, String apellidos) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString(){
        return
                "Carnet: " +carnet +
                        " | Nombre: " +nombre +
                        " | Apellidos: " + apellidos
                ;
    }

}
