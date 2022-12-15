package com.example.laboratorio08;

import java.io.Serializable;

public class Alumno implements Serializable {
    // Campos: información a guardar
    private String nombre, aPaterno;
    private String fecNacimiento;
    private String colProcedencia;
    private String postula;

    public String getFecNacimiento() {
        return fecNacimiento;
    }

    public String getColProcedencia() {
        return colProcedencia;
    }

    public String getPostula() {
        return postula;
    }

    public void setFecNacimiento(String fecNacimiento) {
        this.fecNacimiento = fecNacimiento;
    }

    public void setColProcedencia(String colProcedencia) {
        this.colProcedencia = colProcedencia;
    }

    public void setPostula(String postula) {
        this.postula = postula;
    }

    // Constructor para asignar datos
    Alumno(String nombre, String lugar) {
        this.nombre = nombre;
        this.aPaterno = lugar;
    }
    Alumno(String nombre, String lugar,String fechaNac, String colProc, String postula) {
        this.nombre = nombre;
        this.aPaterno = lugar;
        this.fecNacimiento = fechaNac;
        this.colProcedencia = colProc;
        this.postula = postula;
    }

    // Métodos para leer datos (getters)
    String getNombre() {
        return nombre;
    }

    String getaPaterno() {
        return aPaterno;
    }

}