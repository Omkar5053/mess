package com.gramtarang.mess.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gramtarang.mess.enums.AmbulanceStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Ambulance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ambulance_id;

    @Enumerated(value = EnumType.ORDINAL)
    private AmbulanceStatus ambulanceStatus;
    private String ambulanceName;
    private String licensePlate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm:ss")
    private LocalDateTime lastMaintenanceDate;
    @ManyToOne
//    @JsonManagedReference
//    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public void setLastMaintenanceDate(LocalDateTime lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public LocalDateTime getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

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



    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public AmbulanceStatus getAmbulanceStatus() {
        return ambulanceStatus;
    }

    public void setAmbulanceStatus(AmbulanceStatus ambulanceStatus) {
        this.ambulanceStatus = ambulanceStatus;
    }
}
