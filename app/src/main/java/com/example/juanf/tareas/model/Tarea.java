package com.example.juanf.tareas.model;

/**
 * Created by paco
 */

import java.io.Serializable;
import java.util.Date;

public class Tarea implements Serializable {
    private int id;
    private String nombre;
    private String description;
    private int importancia;
    private String deadline;
    private String link;
    private String image;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return nombre;
    }
    public void setName(String nombre) {
        this.nombre = nombre;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getImportancia() {
        return importancia;
    }
    public void setImportancia(int importancia) {
        this.importancia = importancia;
    }
    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public  Tarea() {}

    public Tarea(int id, String nombre, String description, int importancia, String deadline, String link, String image) {
        this.id = id;
        this.nombre = nombre;
        this.description = description;
        this.importancia = importancia;
        this.deadline = deadline;
        this.link = link;
        this.image = image;
    }

    public Tarea(String nombre, String description, int importancia, String deadline, String link, String image) {
        this.nombre = nombre;
        this.description = description;
        this.importancia = importancia;
        this.deadline = deadline;
        this.link = link;
        this.image = image;
    }

    @Override
    public String toString() {
        return  nombre + '\n' + description;
    }
    public String toJson(){
        return " {\n" +
        "\"id\": "+id+",\n" +
        "\"nombre\": "+nombre+",\n" +
        "\"description\": "+description+",\n" +
        "\"importancia\": "+importancia+",\n" +
        "\"deadline\": "+deadline+",\n" +
        "\"link\": "+link+",\n" +
        "\"image\": "+image+"\n" +
        "}";
    }
}
