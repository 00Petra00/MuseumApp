package com.example.museumapp;

public class Model {
  String id, title, imageURL, date, description, price;

    public Model() {

    }

    public Model(String id, String title, String imageURL, String date, String description, String price) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.date = date;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
