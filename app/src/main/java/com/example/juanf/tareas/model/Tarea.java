package com.example.juanf.tareas.model;

/**
 * Created by paco
 */

import java.io.Serializable;
import java.util.Date;

public class Tarea implements Serializable {
    private int id;
    private String name;
    private String description;
    private int importancia;
    private String deadLine;
    private String link;
    private String image;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getDeadLine() {
        return deadLine;
    }
    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public  Tarea() {}

    public Tarea(int id, String name, String description, int importancia, String deadLine, String link, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.importancia = importancia;
        this.deadLine = deadLine;
        this.link = link;
        this.image = image;
    }

    public Tarea(String name, String description, int importancia, String deadLine, String link, String image) {
        this.name = name;
        this.description = description;
        this.importancia = importancia;
        this.deadLine = deadLine;
        this.link = link;
        this.image = image;
    }

    @Override
    public String toString() {
        return  name + '\n' + description;
    }
}
