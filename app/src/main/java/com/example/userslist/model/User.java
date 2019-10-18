package com.example.userslist.model;

import java.util.List;

/**
 * Created by Tourdyiev Roman on 2019-10-18.
 */
public class User {

    private String name;
    private String image;
    private List<String> items;

    private User() {
    }

    public User(String name, String image, List<String> items) {
        this.name = name;
        this.image = image;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", items.size=" + items.size() +
                '}';
    }
}
