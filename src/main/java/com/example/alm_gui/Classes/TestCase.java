package com.example.alm_gui.Classes;
public class TestCase {
    private int id;
    private int id_item;
    private String steps;
    private int priority;
    private String auto_status;
    private String dev;
    private String time;
    private int changed_by;
    private String title;
    private String version;
    private String state;
    private int assign;
    public TestCase(){this.id_item=0; this.steps="1.\n2.\n-----------------------------------------------------------------\nОжидаемый результат:\n";}
    public TestCase(int id_item, String steps, int priority, String auto_status, String dev, String time, int changed_by, String title, String version,
                    String state, int assign){
        this.id_item = id_item;
        this.steps=steps;
        this.priority=priority;
        this.auto_status=auto_status;
        this.dev = dev;
        this.time = time;
        this.changed_by = changed_by;
        this.title = title;
        this.version = version;
        this.state = state;
        this.assign = assign;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_item() {
        return id_item;
    }

    public int getChanged_by() {
        return changed_by;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public void setChanged_by(int changed_by) {
        this.changed_by = changed_by;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAssign() {
        return assign;
    }

    public String getVersion() {
        return version;
    }

    public void setAssign(int assign) {
        this.assign = assign;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getSteps() {
        return steps;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getAuto_status() {
        return auto_status;
    }

    public void setAuto_status(String auto_status) {
        this.auto_status = auto_status;
    }

}
