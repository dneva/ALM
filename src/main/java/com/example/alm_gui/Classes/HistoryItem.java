package com.example.alm_gui.Classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HistoryItem {
    private SimpleIntegerProperty id;
    private SimpleStringProperty time;
    private SimpleStringProperty login;
    private SimpleStringProperty state;
    public HistoryItem(int id, String time, String login, String state){
        this.id = new SimpleIntegerProperty(id);
        this.time = new SimpleStringProperty(time);
        this.login = new SimpleStringProperty(login);
        this.state = new SimpleStringProperty(state);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public int getId() {
        return id.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getLogin() {
        return login.get();
    }

    public String getState() {
        return state.get();
    }

    public String getTime() {
        return time.get();
    }
}
