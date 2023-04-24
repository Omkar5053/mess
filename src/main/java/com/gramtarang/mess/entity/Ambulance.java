package com.gramtarang.mess.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Ambulance {

    private int id;

    private String ambulanceName;
    private String licensePlate;
    private Date lastMaintenanceDate;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAmbulanceName() { return ambulanceName; }
    public void setAmbulanceName(String ambulanceName) { this.ambulanceName = ambulanceName; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public Date getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(Date lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
