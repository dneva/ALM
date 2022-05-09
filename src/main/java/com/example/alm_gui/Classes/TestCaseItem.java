package com.example.alm_gui.Classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TestCaseItem {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty id_item;
    private SimpleStringProperty title;
    private SimpleStringProperty execution;
    public TestCaseItem(){this.id_item=new SimpleIntegerProperty(0);}
    public TestCaseItem(int id, int id_item, String title, String execution){
        this.id = new SimpleIntegerProperty(id);
        this.id_item = new SimpleIntegerProperty(id_item);
        this.title = new SimpleStringProperty(title);
        this.execution = new SimpleStringProperty(execution);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public int getId_item() {
        return id_item.get();
    }

    public String getTitle() {
        return title.get();
    }

    public void setId_item(int id_item) {
        this.id_item = new SimpleIntegerProperty(id_item);
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public void setExecution(String execution) {
        this.execution = new SimpleStringProperty(execution);
    }

    public String getExecution() {
        return execution.get();
    }
}
