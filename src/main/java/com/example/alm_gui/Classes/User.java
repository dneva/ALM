package com.example.alm_gui.Classes;
public class User {
    private int id = 0;
    private String login;
    private String password;
    private String position;

    public User(){}
    public User(String login,String password, String position){
        this.login =login;
        this.password=password;
        this.position=position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPosition() {
        return position;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
