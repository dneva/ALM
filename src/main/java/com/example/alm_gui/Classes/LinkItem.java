package com.example.alm_gui.Classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LinkItem {
    private SimpleIntegerProperty id1;
    private SimpleIntegerProperty id2;
    private SimpleStringProperty type;
    public LinkItem(int id1,int id2, String type){
        this.id1 = new SimpleIntegerProperty(id1);
        this.id2 = new SimpleIntegerProperty(id2);
        this.type = new SimpleStringProperty(type);
    }

    public int getId1() {
        return id1.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setId1(int id) {
        this.id1.set(id);
    }

    public String getType() {
        return type.get();
    }

    public void setId2(int id2) {
        this.id2.set(id2);
    }

    public int getId2() {
        return id2.get();
    }
}
