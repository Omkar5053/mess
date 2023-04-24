package com.gramtarang.mess.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maintenanceId;

    private String maintenanceType;

    private String image;

    private String description;

    private Date date;

    private User user;
    private Hostel hostel;

    public int getMaintenanceId() { return maintenanceId;}
    public void setMaintenanceId() { this.maintenanceId = maintenanceId; }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @OneToMany
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hostel getHostel() {
        return hostel;
    }
    @OneToMany
    @JoinColumn(name = "hostel_id")
    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }
}
