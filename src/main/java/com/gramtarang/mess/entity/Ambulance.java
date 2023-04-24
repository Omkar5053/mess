package com.gramtarang.mess.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Ambulance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ambulance_id;

    private String ambulanceName;
    private String licensePlate;
    private Date lastMaintenanceDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    public int getAmbulance_id() {
        return ambulance_id;
    }

    public void setAmbulance_id(int ambulance_id) {
        this.ambulance_id = ambulance_id;
    }

    public String getAmbulanceName() { return ambulanceName; }
    public void setAmbulanceName(String ambulanceName) { this.ambulanceName = ambulanceName; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public Date getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(Date lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
