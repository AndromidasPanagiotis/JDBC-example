/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Panos
 */
public class Assignments {
    
    private int id;
    private String title, description;

    public Assignments() {
    }

    public Assignments(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Assignment: {" + "id = " + id + ", title = " + title + ", description = " + description + '}';
    }
    
}
