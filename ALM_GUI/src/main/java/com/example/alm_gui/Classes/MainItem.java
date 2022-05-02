package com.example.alm_gui.Classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MainItem {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty time_create;
    private SimpleStringProperty modify_item;
    private SimpleStringProperty type;
    private SimpleStringProperty state;
    private SimpleStringProperty assign;

    public MainItem(int id,String title, String time_create, String modify_item, String type, String state, String assign){
        this.id= new SimpleIntegerProperty(id);
        this.title= new SimpleStringProperty(title);
        this.time_create= new SimpleStringProperty(time_create);
        this.modify_item= new SimpleStringProperty(modify_item);
        this.type=new SimpleStringProperty(type);
        this.state=new SimpleStringProperty(state);
        this.assign=new SimpleStringProperty(assign);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getTitle() {
        return title.get();
    }

    public String getState() {
        return state.get();
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public void setAssign(String assign) {
        this.assign = new SimpleStringProperty(assign);
    }

    public void setState(String state) {
        this.state = new SimpleStringProperty(state);
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    public void setTime_create(String time_create) {
        this.time_create = new SimpleStringProperty(time_create);
    }

    public void setModify_item(String modify_item) {
        this.modify_item = new SimpleStringProperty(modify_item);
    }

    public String getTime_create() {
        return time_create.get();
    }

    public String getModify_item() {
        return modify_item.get();
    }

    public String getAssign() {
        return assign.get();
    }

    public String getType() {
        return type.get();
    }

}