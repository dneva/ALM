package com.example.alm_gui.Classes;
public class Link {
    private int id;
    private int id_item1;
    private int id_item2;
    private String link_type;
    public Link(int id_item1, int id_item2, String link_type){
        this.id_item1=id_item1;
        this.id_item2=id_item2;
        this.link_type=link_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_item1() {
        return id_item1;
    }

    public int getId_item2() {
        return id_item2;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setId_item1(int id_item1) {
        this.id_item1 = id_item1;
    }

    public void setId_item2(int id_item2) {
        this.id_item2 = id_item2;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

}
