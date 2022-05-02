package com.example.alm_gui.Classes;

public class Item {
    private int id;
    private String time_create;
    private int type;
    private String modify_item;
    public Item(){};
    public Item(String time_create, int type, String modify_item){
        this.time_create=time_create;
        this.type=type;
        this.modify_item=modify_item;
    }

    public void setId(int id) {
        this.id = this.id;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getModify_item() {
        return modify_item;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setModify_item(String modify_item) {
        this.modify_item = modify_item;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public void setType(int type) {
        this.type = type;
    }
}
