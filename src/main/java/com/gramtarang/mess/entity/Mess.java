package com.gramtarang.mess.entity;

import jakarta.persistence.*;

@Entity
public class Mess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messId;
    private String messName;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Mess() {
    }

    public int getMessId() {
        return messId;
    }

    public void setMessId(int messId) {
        this.messId = messId;
    }

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
