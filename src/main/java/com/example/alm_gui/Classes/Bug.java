package com.example.alm_gui.Classes;

public class Bug {
    private int id;
    private int id_item;
    private String steps;
    private String environment;
    private String found_build;
    private String integreted_build;
    private String os_ver;
    private String verified;
    private String dev;
    private String how_found;
    private String localization;
    private int priority;
    private int severity;
    private String time;
    private int changed_by;
    private String title;
    private String version;
    private String state;
    private int assign;
    public Bug(){
        this.id_item=0;
    };
    public Bug(int id_item,String steps, String environment, String found_build, String integreted_build,
    String os_ver, String verified, String dev, String how_found, String localization, int priority, int severity,
    String time, int changed_by, String title, String version, String state, int assign){
        this.id_item=id_item;
        this.steps=steps;
        this.environment=environment;
        this.found_build=found_build;
        this.integreted_build=integreted_build;
        this.os_ver=os_ver;
        this.verified=verified;
        this.dev=dev;
        this.how_found=how_found;
        this.localization=localization;
        this.priority=priority;
        this.severity=severity;
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

    public int getId_item() {
        return id_item;
    }

    public int getPriority() {
        return priority;
    }

    public String getEnvironment() {
        return environment;
    }

    public int getSeverity() {
        return severity;
    }

    public String getDev() {
        return dev;
    }

    public String getFound_build() {
        return found_build;
    }

    public int getChanged_by() {
        return changed_by;
    }

    public String getHow_found() {
        return how_found;
    }

    public String getIntegreted_build() {
        return integreted_build;
    }

    public String getLocalization() {
        return localization;
    }

    public String getOs_ver() {
        return os_ver;
    }

    public String getSteps() {
        return steps;
    }

    public String getTime() {
        return time;
    }

    public String getVerified() {
        return verified;
    }

    public String getTitle() {
        return title;
    }

    public void setChanged_by(int changed_by) {
        this.changed_by = changed_by;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setFound_build(String found_build) {
        this.found_build = found_build;
    }

    public void setHow_found(String how_found) {
        this.how_found = how_found;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public void setIntegreted_build(String integreted_build) {
        this.integreted_build = integreted_build;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public void setOs_ver(String os_ver) {
        this.os_ver = os_ver;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public void setSteps(String steps) {
        this.steps = steps;
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

    public void setVerified(String verified) {
        this.verified = verified;
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

}
