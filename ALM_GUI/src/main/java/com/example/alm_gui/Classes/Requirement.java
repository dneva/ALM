package com.example.alm_gui.Classes;
public class Requirement {
    private int id;
    private int id_item;
    private String description;
    private String analysis_estimate;
    private String development_estimate;
    private String testing_estimate;
    private String release_date;
    private String time;
    private int changed_by;
    private String title;
    private String version;
    private String state;
    private int assign;
    public Requirement() {this.id_item=0;}
    public Requirement(int id_item, String description, String analysis_estimate, String development_estimate,
    String testing_estimate, String release_date, String time, int changed_by, String title,
    String version, String state, int assign)
    {
        this.id_item=id_item;
        this.description=description;
        this.analysis_estimate=analysis_estimate;
        this.development_estimate=development_estimate;
        this.testing_estimate=testing_estimate;
        this.release_date=release_date;
        this.time=time;
        this.changed_by=changed_by;
        this.title=title;
        this.version=version;
        this.state=state;
        this.assign=assign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
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

    public String getAnalysis_estimate() {
        return analysis_estimate;
    }

    public String getDevelopment_estimate() {
        return development_estimate;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTesting_estimate() {
        return testing_estimate;
    }

    public void setAnalysis_estimate(String analysis_estimate) {
        this.analysis_estimate = analysis_estimate;
    }

    public void setDevelopment_estimate(String development_estimate) {
        this.development_estimate = development_estimate;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setTesting_estimate(String testing_estimate) {
        this.testing_estimate = testing_estimate;
    }

}
