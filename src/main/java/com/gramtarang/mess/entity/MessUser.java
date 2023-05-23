package com.gramtarang.mess.entity;

import com.gramtarang.mess.enums.FoodType;
import com.gramtarang.mess.enums.RegistrationStatus;
import jakarta.persistence.*;

@Entity
public class MessUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messUserId;
    private FoodType foodType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;
    @ManyToOne
    @JoinColumn(name = "mess_mess_id")
    private Mess mess;
    @Enumerated(value = EnumType.ORDINAL)
    private RegistrationStatus breakFast;
    @Enumerated(value = EnumType.ORDINAL)
    private RegistrationStatus lunch;
    @Enumerated(value = EnumType.ORDINAL)
    private RegistrationStatus dinner;

    public MessUser() {
    }

    public int getMessUserId() {
        return messUserId;
    }

    public void setMessUserId(int messUserId) {
        this.messUserId = messUserId;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Hostel getHostel() { return hostel; }
    public void setHostel(Hostel hostel) { this.hostel = hostel; }


    public Mess getMess() { return mess; }
    public void setMess(Mess mess) { this.mess = mess; }

    public RegistrationStatus getBreakFast() {
        return breakFast;
    }

    public void setBreakFast(RegistrationStatus breakFast) {
        this.breakFast = breakFast;
    }

    public RegistrationStatus getLunch() {
        return lunch;
    }

    public void setLunch(RegistrationStatus lunch) {
        this.lunch = lunch;
    }

    public RegistrationStatus getDinner() {
        return dinner;
    }

    public void setDinner(RegistrationStatus dinner) {
        this.dinner = dinner;
    }
}
