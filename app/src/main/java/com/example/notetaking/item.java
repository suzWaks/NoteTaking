package com.example.notetaking;

import java.io.Serializable;

public class item implements Serializable {
    String title;
    String description;

    public item(String title, String description) {
        this.title = title;
        this.description = description;
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

    public String toString() {
        return "Note{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
