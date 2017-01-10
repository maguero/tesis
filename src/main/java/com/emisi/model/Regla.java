package com.emisi.model;

import java.io.Serializable;

/**
 * Created by maguero on 11/8/16.
 */
public class Regla implements Serializable {

    private String id;
    private String nombre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
