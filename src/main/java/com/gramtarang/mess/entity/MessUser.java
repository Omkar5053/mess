package com.gramtarang.mess.entity;

import jakarta.persistence.*;

@Entity
public class MessUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messUserId;
    private String foodType;
    private User user;
    private Hostel hostel;
    private Mess mess;


    public MessUser() {
    }

    public int getMessUserId() {
        return messUserId;
    }

    public void setMessUserId(int messUserId) {
        this.messUserId = messUserId;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) { this.foodType = foodType; }
    @OneToMany
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany
    @JoinColumn(name = "hostel_id")
    public Hostel getHostel() { return hostel; }
    public void setHostel(Hostel hostel) { this.hostel = hostel; }

    @OneToMany
    @JoinColumn(name = "mess_mess_id")
    public Mess getMess() { return mess; }
    public void setMess(Mess mess) { this.mess = mess; }


}
