package com.example.alm_gui.Classes;
public class Task {
    private int id;
    private int id_item;
    private double original_effort;
    private double remaining_effort;
    private String expected_resolve;
    private String resolve;
    private String description;
    private String dev;
    private String time;
    private int changed_by;
    private String title;
    private String version;
    private String state;
    private int assign;
    public Task(){
        this.id_item=0;
        this.expected_resolve="";
        this.resolve="";
    }
    public Task( int id_item, double original_effort, double remaining_effort, String expected_resolve,
    String resolve,String description, String dev, String time, int changed_by, String title, String version,
    String state, int assign) {
        this.id_item = id_item;
        this.original_effort = original_effort;
        this.remaining_effort = remaining_effort;
        this.expected_resolve = expected_resolve;
        this.resolve = resolve;
        this.description = description;
        this.dev = dev;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getDev() {
        return dev;
    }

    public double getOriginal_effort() {
        return original_effort;
    }

    public double getRemaining_effort() {
        return remaining_effort;
    }

    public String getExpected_resolve() {
        return expected_resolve;
    }

    public String getResolve() {
        return resolve;
    }

    public void setExpected_resolve(String expected_resolve) {
        this.expected_resolve = expected_resolve;
    }

    public void setOriginal_effort(double original_effort) {
        this.original_effort = original_effort;
    }

    public void setRemaining_effort(double remaining_effort) {
        this.remaining_effort = remaining_effort;
    }

    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

}
