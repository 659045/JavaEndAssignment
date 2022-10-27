package com.example.javaendassignment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Database {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public ArrayList<User> getUsers(){ return users; }
    public ArrayList<Item> getItems(){ return items; }

    public Item getItemByID(int id){
        for (Item i: items) {
            if (Objects.equals(i.id, id)){
                return i;
            }
        }
        return null;
    }

    public User getUserByID(int id){
        for (User u: users) {
            if (Objects.equals(u.id, id)){
                return u;
            }
        }
        return null;
    }

    public void setItemStatusFalse(Item item){
        item.status = false;
    }

    Date d1 = new Date(2000, Calendar.OCTOBER,10);

    public Database() {
        users.add(new User(1, "J", "1", d1));
        users.add(new User(2, "David", "1234", d1));
        items.add(new Item(1, true, "Java Book", "Jack the Great"));
        items.add(new Item(2, true, "CSS Book", "Zac Wills"));
    }
}
