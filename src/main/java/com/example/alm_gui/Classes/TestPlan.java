package com.example.alm_gui.Classes;
public class TestPlan {
    private int id;
    private int id_item;
    private String time;
    private int changed_by;
    private String title;
    private String version;
    private String state;
    private int assign;
    public TestPlan(){this.id_item=0;}
    public TestPlan(int id_item, String time, int changed_by, String title,
                    String version,String state, int assign){
        this.id_item = id_item;
        this.time = time;
        this.changed_by = changed_by;
        this.title = title;
        this.version = version;
        this.state = state;
        this.assign = assign;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAssign(int assign) {
        this.assign = assign;
    }

    public String getVersion() {
        return version;
    }

    public int getAssign() {
        return assign;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public void setChanged_by(int changed_by) {
        this.changed_by = changed_by;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public int getChanged_by() {
        return changed_by;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
