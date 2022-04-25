package com.example.alm_gui.Classes;

public class Issue {
    private int id;
    private int id_item;
    private String issue_type;
    private String found_build;
    private String steps;
    private String description;
    private int priority;
    private String time;
    private int changed_by;
    private String title;
    private String version;
    private String state;
    private int assign;
    public Issue(){this.id_item=0;};
    public Issue(int id_item,String issue_type, String found_build, String steps,
               String description, int priority,
               String time, int changed_by, String title, String version, String state, int assign){
        this.id_item=id_item;
        this.issue_type=issue_type;
        this.found_build=found_build;
        this.steps=steps;
        this.description=description;
        this.priority=priority;
        this.time=time;
        this.changed_by=changed_by;
        this.title=title;
        this.version=version;
        this.state=state;
        this.assign=assign;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public String getState() {
        return state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public void setFound_build(String found_build) {
        this.found_build = found_build;
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

    public String getFound_build() {
        return found_build;
    }

    public int getPriority() {
        return priority;
    }

    public int getId_item() {
        return id_item;
    }

    public String getDescription() {
        return description;
    }

    public String getIssue_type() {
        return issue_type;
    }

    public String getSteps() {
        return steps;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssue_type(String issue_type) {
        this.issue_type = issue_type;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

}
