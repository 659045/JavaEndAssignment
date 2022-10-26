package com.example.javaendassignment;

import java.time.LocalDate;

public class Item {
    public int id;
    public boolean status;
    public String title;
    public String author;
    public LocalDate date;



    public Item(int id, boolean status, String title, String author) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.author = author;
    }
}

